package com.carlos.bbox.redenvelope;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import com.carlos.bbox.R;
import com.carlos.bbox.redenvelope.entity.MessageEvent;
import com.carlos.bbox.util.PreferencesUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by caochang on 2017/8/11.
 */

public class QQFragment extends SupportFragment implements SeekBar.OnSeekBarChangeListener {

    @BindView(R.id.cb_qq_control)
    CheckBox mCbQqControl;
    @BindView(R.id.tv_qq_putong)
    TextView mTvQqPutong;
    @BindView(R.id.sb_qq_putong)
    SeekBar mSbQqPutong;
    @BindView(R.id.tv_qq_kouling)
    TextView mTvQqKouling;
    @BindView(R.id.sb_qq_kouling)
    SeekBar mSbQqKouling;
    @BindView(R.id.tv_qq_lingqu)
    TextView mTvQqLingqu;
    @BindView(R.id.sb_qq_lingqu)
    SeekBar mSbQqLingqu;
    Unbinder unbinder;
    @BindView(R.id.tv_count)
    TextView mTvCount;

    private int t_putong;
    private int t_kouling;
    private int t_lingqu;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qq, container, false);
        unbinder = ButterKnife.bind(this, view);


        mCbQqControl.setOnCheckedChangeListener((buttonView, isChecked) -> {
            PreferencesUtils.setQQUseStatus(isChecked);
        });

        mSbQqPutong.setOnSeekBarChangeListener(this);
        mSbQqKouling.setOnSeekBarChangeListener(this);
        mSbQqLingqu.setOnSeekBarChangeListener(this);

        loadSaveData();


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        mTvCount.setText("已为ddd您到"+PreferencesUtils.getQQHongbaoRecordCount()+"元红包，点击查看详情。");
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void loadSaveData() {
        mCbQqControl.setChecked(PreferencesUtils.getQQUseStatus());
        t_putong = PreferencesUtils.getQQPutongDelay();
        mTvQqPutong.setText("普通红包延迟时间：" + t_putong + "s");
        mSbQqPutong.setProgress((t_putong - 1));

        t_kouling = PreferencesUtils.getQQKoulingDelay();
        mTvQqKouling.setText("口令红包延迟时间：" + t_kouling + "s");
        mSbQqKouling.setProgress(t_kouling - 3);

        t_lingqu = PreferencesUtils.getQQLingquDelay();
        mSbQqLingqu.setProgress(t_lingqu - 3);
        if (t_lingqu == 11) {
            mTvQqLingqu.setText("红包领取页关闭时间：" + "不关闭");
        } else {
            mTvQqLingqu.setText("红包领取页关闭时间：" + t_lingqu + "s");
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.sb_qq_putong:
                t_putong = progress + 1;
                mTvQqPutong.setText("普通红包延迟时间：" + t_putong + "s");
                PreferencesUtils.setQQPutongDelay(t_putong);
                break;
            case R.id.sb_qq_kouling:
                t_kouling = progress + 3;
                mTvQqKouling.setText("口令红包延迟时间：" + t_kouling + "s");
                PreferencesUtils.setQQKoulingDelay(t_kouling);
                break;
            case R.id.sb_qq_lingqu:
                t_lingqu = progress + 3;
                if (progress == 8) {
                    mTvQqLingqu.setText("红包领取页关闭时间：" + "不关闭");
                } else {
                    mTvQqLingqu.setText("红包领取页关闭时间：" + t_lingqu + "s");
                }
                PreferencesUtils.setQQLingquDelay(t_lingqu);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @OnClick(R.id.tv_count)
    public void onViewClicked() {
        startActivity(new Intent(getActivity(),QQRedEnvelopeRecordActivity.class));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.getMessage()) {
            case RedEventConstant.GET_QQ_DATA:
                mTvCount.setText("已为您到"+PreferencesUtils.getQQHongbaoRecordCount()+"元红包，点击查看详情。");
                break;
        }
    }
}
