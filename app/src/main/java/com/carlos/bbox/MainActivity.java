package com.carlos.bbox;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;

import com.carlos.bbox.base.BaseSupportActivity;
import com.carlos.bbox.redenvelope.RedEnvelopeFragment;
import com.carlos.bbox.util.LogUtil;
import com.carlos.bbox.zhihu.view.ZhihuDailyFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends BaseSupportActivity
        implements NavigationView.OnNavigationItemSelectedListener,AccessibilityManager.AccessibilityStateChangeListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    private ZhihuDailyFragment mZhihuFragment;
    private RedEnvelopeFragment mRedEnvelopeFragment;
    private AboutFragment mAboutFragment;

    private MenuItem mSearchMenu;
    private MenuItem mEnterMenu;

    private static final int INDEX_ZHIHU = 101;
    private static final int INDEX_REDENVELOPE_ASSISTANT = 102;
    private static final int INDEX_ABOUT = 109;
    private int hideFragment = INDEX_ZHIHU;
    private int showFragment = INDEX_ZHIHU;

    //AccessibilityService 管理
    private AccessibilityManager accessibilityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //监听AccessibilityService 变化
        accessibilityManager= (AccessibilityManager) getSystemService(Context.ACCESSIBILITY_SERVICE);
        accessibilityManager.addAccessibilityStateChangeListener(this);

        initView();

    }

    private void initView(){
        setToolBar(mToolbar,getResources().getString(R.string.zhihu_daily));
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mNavView.setNavigationItemSelectedListener(this);

        mZhihuFragment=new ZhihuDailyFragment();
        mRedEnvelopeFragment=new RedEnvelopeFragment();
        mAboutFragment=new AboutFragment();
        loadMultipleRootFragment(R.id.fl_zhihu,0,mZhihuFragment,mRedEnvelopeFragment,mAboutFragment);

        showHideFragment(getTargetFragment(showFragment)
                , getTargetFragment(hideFragment));
        mToolbar.setTitle(mNavView.getMenu().findItem(getCurrentItem(showFragment)).getTitle().toString());
        hideFragment = showFragment;
    }

    @Override
    public void onBackPressedSupport() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressedSupport();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to ic_enter.png action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        mSearchMenu= menu.findItem(R.id.action_search);
//        item.setVisible(false);
        mEnterMenu=menu.findItem(R.id.action_enter);
        updateServiceStatus();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_enter:
                Intent intent=new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(intent);
                if(isServiceEnabled()){
                    Toast.makeText(MainActivity.this, "找到抢红包，然后关闭服务。", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "找到抢红包，然后开启服务。", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()){
            case R.id.nav_zhihu:
                showFragment = INDEX_ZHIHU;
                mSearchMenu.setVisible(false);
                mEnterMenu.setVisible(false);
                break;
            case R.id.nav_redenvelope_assistant:
                showFragment = INDEX_REDENVELOPE_ASSISTANT;
                mEnterMenu.setVisible(true);
                break;
            case R.id.nav_about:
                showFragment = INDEX_ABOUT;
                break;
        }
        mToolbar.setTitle(item.getTitle());
        showHideFragment(getTargetFragment(showFragment), getTargetFragment(hideFragment));
        hideFragment = showFragment;
        mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }


    private SupportFragment getTargetFragment(int item) {
        switch (item) {
            case INDEX_ZHIHU:
                return mZhihuFragment;
            case INDEX_REDENVELOPE_ASSISTANT:
                return mRedEnvelopeFragment;
            case INDEX_ABOUT:
                return mAboutFragment;
        }
        return mZhihuFragment;
    }

    private int getCurrentItem(int item) {
        switch (item) {
            case INDEX_ZHIHU:
                return R.id.nav_zhihu;
            case INDEX_REDENVELOPE_ASSISTANT:
                return R.id.nav_redenvelope_assistant;
            case INDEX_ABOUT:
                return R.id.nav_about;
        }
        return R.id.nav_zhihu;
    }

    @Override
    public void onAccessibilityStateChanged(boolean enabled) {
        updateServiceStatus();
    }

    /**
     * 更新当前 QiangHongBaoService 显示状态
     */
    private void updateServiceStatus(){
        if (showFragment!=INDEX_REDENVELOPE_ASSISTANT)
            return;
        else LogUtil.d("gdggdg");
        Toast.makeText(this, "gfgfg", Toast.LENGTH_SHORT).show();

        if(isServiceEnabled()){
            LogUtil.d("red envelope assistant service is opened.");
            mEnterMenu.setVisible(false);
        }else{
            LogUtil.d("red envelope assistant service is closed.");
            mEnterMenu.setVisible(true);
//            list.set(0, "开启服务");
        }
    }

    /**
     * 获取 QiangHongBaoService 是否启用状态
     * @return
     */
    private boolean isServiceEnabled(){
        List<AccessibilityServiceInfo> accessibilityServiceInfoList=accessibilityManager.
                getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC);

        for(AccessibilityServiceInfo info:accessibilityServiceInfoList){
            if(info.getId().equals(getPackageName()+"/.redenvelope.QiangHongBaoService")){
                return true;
            }
        }
        return  false;
    }
}



