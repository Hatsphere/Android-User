package com.code.yashladha.android_user;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.code.yashladha.android_user.Adapter.LoginPageAdapter;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private LoginPageAdapter mLoginAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setViewPager();
    }

    private void setViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.login_pager);
        mLoginAdapter = new LoginPageAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mLoginAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.login_tab);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
