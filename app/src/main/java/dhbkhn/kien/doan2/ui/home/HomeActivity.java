package dhbkhn.kien.doan2.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dhbkhn.kien.doan2.R;
import dhbkhn.kien.doan2.ui.about.AboutFragment;
import dhbkhn.kien.doan2.ui.adapter.HomePageAdapter;
import dhbkhn.kien.doan2.ui.base.BaseActivity;
import dhbkhn.kien.doan2.ui.custom.RoundedImageView;
import dhbkhn.kien.doan2.ui.login.LoginActivity;
import dhbkhn.kien.doan2.utils.AppBuildConfig;

public class HomeActivity extends BaseActivity implements HomeMvpView, ViewPager.OnPageChangeListener {

    private static final String TAG = HomeActivity.class.getSimpleName();

    public static final int OFF_SCREEN_PAGE_LIMIT = 2;

    @Inject
    HomeMvpPresenter<HomeMvpView> mPresenter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.drawer_view)
    DrawerLayout mDrawer;

    @BindView(R.id.tabs_sliding)
    TabLayout mTabsSliding;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;

    @BindView(R.id.tv_app_version)
    TextView mTvAppVersion;

    private TextView mTvName;

    private TextView mTvEmail;

    private RoundedImageView mProfileImageView;

    private ActionBarDrawerToggle mDrawerToggle;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(this);

        setUp();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentByTag(AboutFragment.TAG);
        if (fragment == null) {
            super.onBackPressed();
        }
        else {
            onFragmentDetached(AboutFragment.TAG);
        }
    }

    @Override
    protected void setUp() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawer,
                mToolbar,
                R.string.open_drawer,
                R.string.close_drawer
        ){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                hideKeyboard();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        setupNavMenu();
        mPresenter.onNavMenuCreated();
        mPresenter.onViewInitialized();
        initTabs();
    }

    private void initTabs(){
        mViewPager.setAdapter(new HomePageAdapter(getSupportFragmentManager()));
        mViewPager.setOffscreenPageLimit(OFF_SCREEN_PAGE_LIMIT);
        mTabsSliding.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(this);
    }

    void setupNavMenu(){
        View headerLayout = mNavigationView.getHeaderView(0);
        mProfileImageView = (RoundedImageView) headerLayout.findViewById(R.id.img_profile_pic);
        mTvName = (TextView) headerLayout.findViewById(R.id.tv_name);
        mTvEmail = (TextView) headerLayout.findViewById(R.id.tv_email);

        mNavigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_item_about:
                    mPresenter.onDrawerOptionAboutClick();
                    return true;
                case R.id.nav_item_logout:
                    mPresenter.onDrawerOptionLogoutClick();
                    return true;
            }
            mDrawer.closeDrawers();
            return true;
        });
    }

    @Override
    public void openLoginActivity() {
        startActivity(LoginActivity.getStartIntent(this));
        finish();
    }

    @Override
    public void onFragmentAttached() {
        if (mDrawer != null) {
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

    @Override
    public void onFragmentDetached(String tag) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentByTag(tag);
        if (fm != null) {
            fm.beginTransaction()
                    .disallowAddToBackStack()
                    .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                    .remove(fragment)
                    .commitNow();
            if (mDrawer != null) {
                mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED);
            }
        }
    }

    @Override
    public void showAboutFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                .add(R.id.cl_root_view, AboutFragment.newInstance(), AboutFragment.TAG)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_search:
                // action search
                return true;
            case R.id.action_setting:
                // action setting
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateUserName(String currentUserName) {
        mTvName.setText(currentUserName);
    }

    @Override
    public void updateUserEmail(String currentUserEmail) {
        mTvEmail.setText(currentUserEmail);
    }

    @Override
    public void updateUserProfilePic(String currentUserProfilePicUrl) {
        //load profile pic url into ANImageView
    }

    @Override
    public void updateAppVersion() {
        String version = getString(R.string.version) + " " + AppBuildConfig.VERSION_NAME;
        mTvAppVersion.setText(version);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDettach();
        super.onDestroy();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
