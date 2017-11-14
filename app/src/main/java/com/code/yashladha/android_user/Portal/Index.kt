package com.code.yashladha.android_user.Portal

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
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
import android.view.ViewAnimationUtils
import com.code.yashladha.android_user.Portal.Adapter.ListItemAdapter
import com.code.yashladha.android_user.Portal.Fragments.AccountsFragment
import com.code.yashladha.android_user.Portal.Fragments.CartFragment
import com.code.yashladha.android_user.Portal.Fragments.HomeFragment
import com.code.yashladha.android_user.Portal.Fragments.LogsFragment
import com.code.yashladha.android_user.Portal.Model.ListItem
import com.code.yashladha.android_user.R
import com.code.yashladha.android_user.R.id.*
import kotlinx.android.synthetic.main.activity_index.*

class Index : AppCompatActivity() {

    private val TAG = javaClass.simpleName

    var mDrawerToggle: ActionBarDrawerToggle? = null
    var mTitle: CharSequence? = null
    var mDrawerTitle: CharSequence? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_index)

        setSupportActionBar(main_toolbar)

        mTitle = title
        mDrawerTitle = title

        val listEntries: ArrayList<ListItem> = ArrayList()
        listEntries.add(ListItem(R.drawable.ic_home_black_24dp, "Home"))
        listEntries.add(ListItem(R.mipmap.ic_launcher, "About"))

        val listAdapter = ListItemAdapter(listEntries, baseContext)

        left_drawer.adapter = listAdapter

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
            bottomBarOptionSelected(item.itemId)
            bottomBarReveal(bottom_nav_index, item.itemId)
            true
        }

        bottomBarOptionSelected(menu_home)
        bottom_nav_index.setBackgroundColor(resources.getColor(R.color.home_bottom_color))
        appBarLayout.setBackgroundColor(resources.getColor(R.color.home_bottom_color))
    }

    private fun bottomBarReveal(view: View?, id: Int?) {
        if (view != null) {
            val div = when (id) {
                menu_home -> 1
                menu_cart_home -> 2
                menu_account_home -> 3
                menu_logs -> 4
                else -> 1
            }

            val cx = (view.left + view.right) * 0.20 * div
            val cx2 = (appBarLayout.left + appBarLayout.right) / 2.0

            val startRadius = 0.toFloat()
            val endRadius = Math.max(view.width, view.height).toFloat()
            val endRadius2 = Math.max(appBarLayout.width, appBarLayout.height).toFloat()

            val color = when (id) {
                menu_home -> resources.getColor(R.color.home_bottom_color)
                menu_cart_home -> resources.getColor(R.color.cart_bottom_color)
                menu_account_home -> resources.getColor(R.color.account_bottom_color)
                menu_logs -> resources.getColor(R.color.logs_bottom_color)
                else -> resources.getColor(R.color.home_bottom_color)
            }

            val anim = ViewAnimationUtils.createCircularReveal(view, cx.toInt(), 0, startRadius, endRadius)
            val anim2 = ViewAnimationUtils.createCircularReveal(appBarLayout, cx2.toInt(), 0, startRadius, endRadius2)

            anim.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationStart(animation)
                    view.setBackgroundColor(color)
                }
            })

            anim2.addListener(object: AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationStart(animation)
                    appBarLayout.setBackgroundColor(color)
                }
            })

            anim.duration = 650
            anim.start()

            anim2.duration = 650
            anim2.start()
        }
    }


    private fun bottomBarOptionSelected(id: Int) {
        val fragment: Fragment = when (id) {
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

        mTitle = title
        supportActionBar!!.title = title

        // Fragment transaction
        fragmentManager.inTransaction {
            replace(R.id.frame_content_main, fragment, title)
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