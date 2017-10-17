package com.code.yashladha.android_user.Portal.Model;

/**
 * Created by yashladha on 17/10/17.
 */

public class ListItem {

    private int icon;
    private String title;

    public ListItem(int icon, String title) {
        this.icon = icon;
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
