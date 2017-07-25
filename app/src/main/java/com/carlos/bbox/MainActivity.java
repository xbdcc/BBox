package com.carlos.bbox;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.carlos.bbox.base.BaseActivity;
import com.carlos.bbox.zhihu.view.ZhihuDailyFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    private ZhihuDailyFragment mZhihuFragment;
    private AboutFragment mAboutFragment;

    private static final int INDEX_ZHIHU = 101;
    private static final int INDEX_ABOUT = 109;
    private int hideFragment = INDEX_ZHIHU;
    private int showFragment = INDEX_ZHIHU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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
        mAboutFragment=new AboutFragment();
        loadMultipleRootFragment(R.id.fl_zhihu,0,mZhihuFragment,mAboutFragment);

        showHideFragment(getTargetFragment(showFragment), getTargetFragment(hideFragment));
        mToolbar.setTitle(mNavView.getMenu().findItem(getCurrentItem(showFragment)).getTitle().toString());
        hideFragment = showFragment;
    }

//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(false);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_zhihu) {
            showFragment = INDEX_ZHIHU;
//            mSearchMenuItem.setVisible(false);
        }
        if (id == R.id.nav_about) {
            showFragment = INDEX_ABOUT;
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
            case INDEX_ABOUT:
                return mAboutFragment;
        }
        return mZhihuFragment;
    }

    private int getCurrentItem(int item) {
        switch (item) {
            case INDEX_ZHIHU:
                return R.id.nav_zhihu;
            case INDEX_ABOUT:
                return R.id.nav_about;
        }
        return R.id.nav_zhihu;
    }

}
