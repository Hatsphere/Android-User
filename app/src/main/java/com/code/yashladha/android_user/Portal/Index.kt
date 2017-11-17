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
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_index.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class Index : AppCompatActivity(), AnkoLogger {

    private val TAG = javaClass.simpleName

    var mDrawerToggle: ActionBarDrawerToggle? = null
    var mTitle: CharSequence? = null
    var mDrawerTitle: CharSequence? = null
    private lateinit var firestore: FirebaseFirestore
    private lateinit var cateogries: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_index)

        setSupportActionBar(main_toolbar)

        firestore = FirebaseFirestore.getInstance()
        cateogries = ArrayList()

        mTitle = title
        mDrawerTitle = title

        val listEntries: ArrayList<ListItem> = ArrayList()
        val listAdapter = ListItemAdapter(listEntries, baseContext)

        InflatesCateogryList(listEntries, listAdapter)


        left_drawer.adapter = listAdapter

        left_drawer.setOnItemClickListener(DrawerClickListener(firestore, baseContext))

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

    /**
     * Inflates the cateogry list in the List View of Navigation Drawer
     * of Index layout
     * @param listEntries : List of the item in the list
     * @param listAdapter : Adapter for the list view
     */
    private fun InflatesCateogryList(listEntries: ArrayList<ListItem>, listAdapter: ListItemAdapter) {
        firestore.collection("Cateogries")
                .document("Details")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        info("Cateogries Fetched successfully")
                        val res = task.result
                        var obj = res.data
                        obj = obj.toSortedMap()

                        for (cat in obj) {
                            val item = cat.value as String
                            val image = when (item) {
                                "Home Decor" -> R.drawable.home_decor
                                "Personal Accessories" -> R.drawable.personal_accessories
                                "Gifts" -> R.drawable.gifts
                                "Paintings/Wall Hangings" -> R.drawable.painting
                                else -> R.drawable.other
                            }

                            listEntries.add(ListItem(image, item))
                        }
                        listAdapter.notifyDataSetChanged()
                    }
                }
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

            val bottomBarAnim = ViewAnimationUtils.createCircularReveal(view, cx.toInt(), 0, startRadius, endRadius)
            val appBarAnim = ViewAnimationUtils.createCircularReveal(appBarLayout, cx2.toInt(), 0, startRadius, endRadius2)

            bottomBarAnim.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationStart(animation)
                    view.setBackgroundColor(color)
                }
            })

            appBarAnim.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationStart(animation)
                    appBarLayout.setBackgroundColor(color)
                }
            })

            bottomBarAnim.duration = 650
            bottomBarAnim.start()

            appBarAnim.duration = 650
            appBarAnim.start()
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