package com.code.yashladha.android_user.Portal.Helper;

import com.code.yashladha.android_user.Models.Filter;

import java.util.ArrayList;

/**
 * Created by yashladha on 19/11/17.
 * Interface for the filter callback
 */

public interface FilterCallback {
    void updateFilterUI (ArrayList<Filter> logs);
}
