package com.code.yashladha.android_user.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.code.yashladha.android_user.Login.Login;
import com.code.yashladha.android_user.Login.SignUp;

/**
 * Created by yashladha on 16/10/17.
 */

public class LoginPageAdapter extends FragmentStatePagerAdapter {

    private static int TAB_COUNT = 2;

    public LoginPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return Login.newInstance();
            case 1:
                return SignUp.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return Login.TITLE;
            case 1:
                return SignUp.TITLE;
        }
        return super.getPageTitle(position);
    }
}
