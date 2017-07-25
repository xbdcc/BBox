package com.carlos.bbox.zhihu.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.carlos.bbox.R;
import com.carlos.bbox.base.BaseFragment;
import com.carlos.bbox.util.LogUtil;
import com.carlos.bbox.zhihu.adapter.ZhihuDailyAdapter;
import com.carlos.bbox.zhihu.bean.ZhihuDailyBeforeVO;
import com.carlos.bbox.zhihu.bean.ZhihuDailyItemVO;
import com.carlos.bbox.zhihu.bean.ZhihuDailyTodayVO;
import com.carlos.bbox.zhihu.contract.ZhihuDailyContract;
import com.carlos.bbox.zhihu.presenter.ZhihuDailyPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by caochang on 2017/6/26.
 */

public class ZhihuDailyFragment extends BaseFragment implements ZhihuDailyContract.View {

    @BindView(R.id.rv_zhihudaily)
    RecyclerView mRvZhihudaily;
    @BindView(R.id.fab_zhihudaily)
    FloatingActionButton mFabZhihudaily;
    @BindView(R.id.srl_zhihudaily)
    SwipeRefreshLayout mSrlZhihudaily;
    Unbinder unbinder;

    private ZhihuDailyContract.Presenter mZhihuDailyPresenter;
    private ZhihuDailyAdapter mZhihuDailyAdapter;
    private List<ZhihuDailyItemVO> mZhihuDailyItemVOs;
    private String currentLoadDate = "0";
    boolean isLoading;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zhihudaily, container, false);
        unbinder = ButterKnife.bind(this, view);

        new ZhihuDailyPresenter(this);
        initView();
        return view;
    }

    private void initView() {
        mZhihuDailyItemVOs = new ArrayList<>();
        mZhihuDailyAdapter = new ZhihuDailyAdapter(getContext(), mZhihuDailyItemVOs);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRvZhihudaily.setLayoutManager(linearLayoutManager);
        mRvZhihudaily.setAdapter(mZhihuDailyAdapter);
        mZhihuDailyAdapter.setOnItemClickListener((position, view) -> ZhihuDailyDetailActivity.actionStart(getContext(), mZhihuDailyItemVOs.get(position).getId(), ""));
        mRvZhihudaily.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) //向下滚动
                {
                    int visibleItemCount = linearLayoutManager.getChildCount();
                    int totalItemCount = linearLayoutManager.getItemCount();
                    int pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();
                    if (!isLoading && (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        isLoading = true;
                        LogUtil.d("-------" + currentLoadDate);
                        Toast.makeText(_mActivity, currentLoadDate, Toast.LENGTH_SHORT).show();
                        mZhihuDailyPresenter.getBefore(currentLoadDate);
                    }
                }
            }
        });

        mSrlZhihudaily.setOnRefreshListener(() -> {
//            mViewError.setVisibility(View.GONE);
            mZhihuDailyAdapter.clearData();
            mZhihuDailyPresenter.getTodayData();
        });
//        mSrlZhihudaily.setRefreshing(true);
        mZhihuDailyPresenter.getTodayData();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.fab_zhihudaily)
    public void onViewClicked() {
    }

    @Override
    public void setPresenter(ZhihuDailyContract.Presenter presenter) {
        mZhihuDailyPresenter = presenter;
    }

    @Override
    public void showErrorMsg(String msg) {

    }

    @Override
    public void stateLoading() {

    }

    @Override
    public void stateSuccess() {
        if (mSrlZhihudaily.isRefreshing()) {
            mSrlZhihudaily.setRefreshing(false);
        }
    }

    @Override
    public void stateFailure() {
        if (mSrlZhihudaily.isRefreshing()) {
            mSrlZhihudaily.setRefreshing(false);
        }
    }

    @Override
    public void setTodayData(ZhihuDailyTodayVO zhihuDailyLastVO) {
        mZhihuDailyAdapter.addItems(zhihuDailyLastVO.getStories());
        currentLoadDate = zhihuDailyLastVO.getDate();
    }

    @Override
    public void setBeforeData(ZhihuDailyBeforeVO zhihuDailyBeforeVO) {
        if (isLoading)
            isLoading = false;
        mZhihuDailyAdapter.addItems(zhihuDailyBeforeVO.getStories());
        currentLoadDate = zhihuDailyBeforeVO.getDate();

    }
}
