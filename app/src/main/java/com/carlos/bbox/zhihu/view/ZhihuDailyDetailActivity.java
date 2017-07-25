package com.carlos.bbox.zhihu.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.carlos.bbox.R;
import com.carlos.bbox.util.HtmlUtil;
import com.carlos.bbox.util.ImageUtil;
import com.carlos.bbox.util.LogUtil;
import com.carlos.bbox.zhihu.bean.ZhihuDailyDetailVO;
import com.carlos.bbox.zhihu.contract.ZhihuDailyDetailContract;
import com.carlos.bbox.zhihu.presenter.ZhihuDailyDetailPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;

/**
 * Created by caochang on 2017/7/14.
 */

public class ZhihuDailyDetailActivity extends SwipeBackActivity implements ZhihuDailyDetailContract.View {

    public static final String ID = "id";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.wv_zhihu_daily)
    WebView mWvZhihuDaily;
    @BindView(R.id.iv_daily_image)
    ImageView mIvDailyImage;
    @BindView(R.id.nsv_zhihu_daily)
    NestedScrollView mNsvZhihuDaily;
    @BindView(R.id.al_zhihu_daily)
    AppBarLayout mAlZhihuDaily;

    private ZhihuDailyDetailContract.Presenter mZhihuDailyDetailPresenter;
    boolean isBottomShow = true;

    public static void actionStart(Context context, String data1, String data2) {
        Intent intent = new Intent(context, ZhihuDailyDetailActivity.class);
        intent.putExtra(ID, data1);
        intent.putExtra("2", data2);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhihu_daily_detail);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        back();

        new ZhihuDailyDetailPresenter(this);

        initView();

    }

    private void initView(){
        String id = getIntent().getStringExtra(ID);
        LogUtil.d("000id" + id);

        WebSettings webSettings = mWvZhihuDaily.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);// 缩放至屏幕的大小
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//支持内容重新布局
        webSettings.setSupportZoom(true);//支持缩放


//        mWvZhihuDaily.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public void onReceivedTitle(WebView view, String title) {
//                super.onReceivedTitle(view, title);
//                LogUtil.d("title" + title);
//                mToolbar.setTitle(title);
//            }
//        });
        mWvZhihuDaily.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        mNsvZhihuDaily.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY - oldScrollY > 0 && isBottomShow) {  //下移隐藏
//                    Toast.makeText(ZhihuDailyDetailActivity.this, "下隐藏", Toast.LENGTH_SHORT).show();
//                    isBottomShow = false;
//                    mToolbar.animate().translationY(mAlZhihuDaily.getHeight());
                } else if (scrollY - oldScrollY < 0 && !isBottomShow) {    //上移出现
//                    Toast.makeText(ZhihuDailyDetailActivity.this, "上出现", Toast.LENGTH_SHORT).show();
//                    isBottomShow = true;
//                    mToolbar.animate().translationY(0);
                }
            }
        });

        mZhihuDailyDetailPresenter.getData(id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWvZhihuDaily != null) {
            mWvZhihuDaily.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWvZhihuDaily.clearHistory();

            ((ViewGroup) mWvZhihuDaily.getParent()).removeView(mWvZhihuDaily);
            mWvZhihuDaily.destroy();
            mWvZhihuDaily = null;
        }
    }

    public void back() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationOnClickListener(v -> finish());
        }
    }

    @Override
    public void showContent(ZhihuDailyDetailVO zhihuDailyDetailVO) {
        mToolbarLayout.setTitle(zhihuDailyDetailVO.getTitle());

        String data = HtmlUtil.createHtmlData(zhihuDailyDetailVO.getBody(), zhihuDailyDetailVO.getCss(), zhihuDailyDetailVO.getJs());
        mWvZhihuDaily.loadData(data, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);
        ImageUtil.load(this, zhihuDailyDetailVO.getImage(), mIvDailyImage);
    }

    @Override
    public void setPresenter(ZhihuDailyDetailContract.Presenter presenter) {
        mZhihuDailyDetailPresenter=presenter;
    }

    @Override
    public void showErrorMsg(String msg) {

    }

    @Override
    public void stateLoading() {

    }

    @Override
    public void stateSuccess() {

    }

    @Override
    public void stateFailure() {

    }
}
