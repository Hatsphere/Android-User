package com.code.yashladha.android_user;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.code.yashladha.android_user.Adapter.LoginPageAdapter;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private LoginPageAdapter mLoginAdapter;
    private static final int READ_STORAGE = 1;
    private String tag;

    @Override
    protected void onStart() {
        super.onStart();
        requestExternalStoragePermission();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tag = getClass().getSimpleName();

        setViewPager();
    }

    private void requestExternalStoragePermission() {
        if (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_STORAGE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case READ_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(tag, "Permission for external Storage is accepted");
                } else {
                    Log.e(tag, "Permission for external Storage is denied");
                }
            }
        }
    }

    private void setViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.login_pager);
        mLoginAdapter = new LoginPageAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mLoginAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.login_tab);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
