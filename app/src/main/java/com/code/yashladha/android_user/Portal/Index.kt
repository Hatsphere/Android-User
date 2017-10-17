package com.code.yashladha.android_user.Portal

import android.app.Fragment
import android.app.FragmentManager
import android.app.FragmentTransaction
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.code.yashladha.android_user.Portal.Fragments.AccountsFragment
import com.code.yashladha.android_user.Portal.Fragments.CartFragment
import com.code.yashladha.android_user.Portal.Fragments.HomeFragment
import com.code.yashladha.android_user.Portal.Fragments.LogsFragment
import com.code.yashladha.android_user.R
import com.code.yashladha.android_user.R.id.*
import kotlinx.android.synthetic.main.activity_index.*

class Index : AppCompatActivity() {

    private val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_index)

        setSupportActionBar(main_toolbar)

        val ab = supportActionBar
        ab!!.setDisplayHomeAsUpEnabled(true)

        bottom_nav_index.setOnNavigationItemSelectedListener { item ->
            val fragment: Fragment = when (item.itemId) {
                menu_home -> HomeFragment()
                menu_cart_home -> CartFragment()
                menu_account_home -> AccountsFragment()
                menu_logs -> LogsFragment()
                else -> HomeFragment()
            }

            Log.d(TAG, fragment.toString())
            // Fragment transaction
            fragmentManager.inTransaction {
                replace(R.id.frame_content_main, fragment, fragment.tag)
            }
            true
        }
    }

}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}