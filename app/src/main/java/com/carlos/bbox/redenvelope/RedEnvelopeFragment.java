package com.carlos.bbox.redenvelope;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.carlos.bbox.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by caochang on 2017/8/10.
 */

public class RedEnvelopeFragment extends SupportFragment {

    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;
    Unbinder unbinder;
    @BindView(R.id.fl_red_envelope)
    FrameLayout mFlRedEnvelope;

    private QQFragment mQQFragment;
    private WechatFragment mWechatFragment;
    private static final int INDEX_QQ = 101;
    private static final int INDEX_WECHAT = 102;
    private int hideFragment = INDEX_QQ;
    private int showFragment = INDEX_QQ;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_qq:
                showFragment = INDEX_QQ;
                showHideFragment(getTargetFragment(showFragment), getTargetFragment(hideFragment));
                hideFragment = showFragment;
                return true;
            case R.id.navigation_wechat:
                showFragment = INDEX_WECHAT;
                showHideFragment(getTargetFragment(showFragment), getTargetFragment(hideFragment));
                hideFragment = showFragment;
                return true;
        }

////        mToolbar.setTitle(item.getTitle());
//        showHideFragment(getTargetFragment(showFragment), getTargetFragment(hideFragment));
//        hideFragment = showFragment;
////        mDrawerLayout.closeDrawer(GravityCompat.START);
        return false;
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_red_envelope, container, false);
        unbinder = ButterKnife.bind(this, view);


        initView();
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        return view;
    }

    private void initView() {
        mQQFragment = new QQFragment();
        mWechatFragment = new WechatFragment();
        loadMultipleRootFragment(R.id.fl_red_envelope, 0, mQQFragment, mWechatFragment);

        showHideFragment(getTargetFragment(showFragment), getTargetFragment(hideFragment));
//        mToolbar.setTitle(mNavView.getMenu().findItem(getCurrentItem(showFragment)).getTitle().toString());
        hideFragment = showFragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private SupportFragment getTargetFragment(int item) {
        switch (item) {
            case INDEX_QQ:
                return mQQFragment;
            case INDEX_WECHAT:
                return mWechatFragment;

        }
        return mQQFragment;
    }

    private int getCurrentItem(int item) {
        switch (item) {
            case INDEX_QQ:
                return R.id.navigation_qq;
            case INDEX_WECHAT:
                return R.id.navigation_wechat;
        }
        return R.id.navigation_qq;
    }
}
