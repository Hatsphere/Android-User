package com.code.yashladha.android_user.Portal

import android.app.Fragment
import android.app.FragmentManager
import android.app.FragmentTransaction
import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import com.code.yashladha.android_user.Portal.Fragments.AccountsFragment
import com.code.yashladha.android_user.Portal.Fragments.CartFragment
import com.code.yashladha.android_user.Portal.Fragments.HomeFragment
import com.code.yashladha.android_user.Portal.Fragments.LogsFragment
import com.code.yashladha.android_user.R
import com.code.yashladha.android_user.R.id.*
import kotlinx.android.synthetic.main.activity_index.*

class Index : AppCompatActivity() {

    private val TAG = javaClass.simpleName

    var mDrawerToggle: ActionBarDrawerToggle? = null
    var mTitle : CharSequence? = null
    var mDrawerTitle : CharSequence? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_index)

        setSupportActionBar(main_toolbar)

        mTitle = title
        mDrawerTitle = title

        left_drawer.adapter = ArrayAdapter<String>(this, R.layout.drawer_list_item, resources.getStringArray(R.array.drawer_list_item))

        mDrawerToggle = object : ActionBarDrawerToggle(this, index_drawer, main_toolbar,
                R.string.drawer_open, R.string.drawer_closed) {
            override fun onDrawerClosed(drawerView: View?) {
                super.onDrawerClosed(drawerView)
                supportActionBar!!.title = mTitle
                invalidateOptionsMenu()
            }

            override fun onDrawerOpened(drawerView: View?) {
                super.onDrawerOpened(drawerView)
                supportActionBar!!.title = mDrawerTitle
                invalidateOptionsMenu()
            }
        }

        mDrawerToggle!!.isDrawerIndicatorEnabled = true

        index_drawer.setDrawerListener(mDrawerToggle)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
        mDrawerToggle!!.syncState()

        bottom_nav_index.setOnNavigationItemSelectedListener { item ->
            val fragment: Fragment = when (item.itemId) {
                menu_home -> HomeFragment()
                menu_cart_home -> CartFragment()
                menu_account_home -> AccountsFragment()
                menu_logs -> LogsFragment()
                else -> HomeFragment()
            }

            Log.d(TAG, fragment.javaClass.toString())

            val title: String = when (fragment::class) {
                HomeFragment::class -> HomeFragment.TAG
                CartFragment::class -> CartFragment.TAG
                AccountsFragment::class -> AccountsFragment.TAG
                LogsFragment::class -> LogsFragment.TAG
                else -> HomeFragment.TAG
            }

            supportActionBar!!.title = title

            // Fragment transaction
            fragmentManager.inTransaction {
                replace(R.id.frame_content_main, fragment, title)
            }
            true
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        mDrawerToggle!!.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        mDrawerToggle!!.onConfigurationChanged(newConfig)
    }
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}