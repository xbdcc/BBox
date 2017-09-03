package com.carlos.bbox.redenvelope;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.carlos.bbox.R;
import com.carlos.bbox.db.QQRedEnvelopeDb;
import com.carlos.bbox.redenvelope.entity.MessageEvent;
import com.carlos.bbox.redenvelope.entity.QQRedEnvelope;
import com.carlos.bbox.util.LogUtil;
import com.carlos.bbox.util.PreferencesUtils;
import com.carlos.bbox.util.Utility;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caochang on 2017/8/30.
 */

public class QQRedEnvelopeRecordActivity extends AppCompatActivity {

    @BindView(R.id.tv_record)
    TextView mTvRecord;
    @BindView(R.id.lv_hongbao_record)
    ListView mLvHongbaoRecord;

    private SimpleAdapter mSimpleAdapter;
    private List<Map<String, String>> list;
    private List<QQRedEnvelope> qqRedEnvelopes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qq_redenvelope_record);
        ButterKnife.bind(this);

        LogUtil.d("qqqqq");
        initData();

    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.d("kkkkkkkddd");
        EventBus.getDefault().register(this);
        EventBus.getDefault().post(new MessageEvent(RedEventConstant.GET_QQ_DATA));
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.d("onstop");
        EventBus.getDefault().unregister(this);
    }

    private void initData() {
        list = new ArrayList<>();
        qqRedEnvelopes=new ArrayList<>();

        mSimpleAdapter = new SimpleAdapter(this,
                list, R.layout.item_redenvelope_record, new String[]{"time", "hb_count"}, new int[]{
                R.id.tv_time, R.id.tv_hb_count});
        mLvHongbaoRecord.setAdapter(mSimpleAdapter);
        Utility.setListViewHeightBasedOnChildren(mLvHongbaoRecord);
        mLvHongbaoRecord.setOnItemClickListener((parent, view, position, id) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("红包详情");
            QQRedEnvelope qqRedEnvelope=qqRedEnvelopes.get(position);
            builder.setMessage("抢到时间:\t" + qqRedEnvelope.getTime() + "\n"
                    + "发送人：:\t" + qqRedEnvelope.getWho_send() + "\n"
                    + "红包种类:\t" + qqRedEnvelope.getType() + "\n"
                    + "红包金额:\t" + qqRedEnvelope.getCount() + "元\n"
            );
            builder.setPositiveButton("确定", null);
            builder.create();
            builder.show();
        });

        LogUtil.d("kkkkk");
        getData();
        EventBus.getDefault().post(new MessageEvent(RedEventConstant.GET_QQ_DATA));
    }

    private void getData(){
        String time = PreferencesUtils.getQQHongbaoRecordTime();
        Float count = PreferencesUtils.getQQHongbaoRecordCount();
        if(count!=0){
            String s=String.format("%.2f", count);//四舍五入保留两位小数
            mTvRecord.setText("亲，从"+time+"开始到现在已经为您抢到"+s+"元QQ红包哦！");
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void backgroundMessage(MessageEvent event){
        switch (event.getMessage()){
            case RedEventConstant.GET_QQ_DATA:
                qqRedEnvelopes= QQRedEnvelopeDb.getAllData();
                LogUtil.d("kkk"+qqRedEnvelopes.size());
                EventBus.getDefault().post(new MessageEvent(RedEventConstant.REFRESH_QQ_DATA));
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.getMessage()) {
            case RedEventConstant.REFRESH_QQ_DATA:
                getData();
                list.clear();
                for (QQRedEnvelope qqRedEnvelope : qqRedEnvelopes) {
                    Map<String, String> map = new HashMap<>();
                    map.put("time", qqRedEnvelope.getTime());
                    map.put("hb_count", qqRedEnvelope.getCount() + "元");
                    Log.e("00000000", qqRedEnvelope.getTime());
                    Log.e("00000000", qqRedEnvelope.getCount() + "元");
                    Log.e("00000000", "来自：" + qqRedEnvelope.getWho_send());
                    list.add(map);
                }
                mSimpleAdapter.notifyDataSetChanged();
                Utility.setListViewHeightBasedOnChildren(mLvHongbaoRecord);
                break;
        }
    }

}
