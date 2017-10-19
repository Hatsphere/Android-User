package com.code.yashladha.android_user.Portal.Fragments

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.code.yashladha.android_user.R

/**
 * Created by yashladha on 17/10/17.
 * Fragment for accounts configuration of the user
 */

class AccountsFragment: Fragment() {

    companion object {
        val TAG = "AccountsFragment"
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_accounts, container, false)
        return view
    }
}