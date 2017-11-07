package com.code.yashladha.android_user.Portal.Fragments

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.code.yashladha.android_user.Portal.Adapter.HomeIndexAdapter
import com.code.yashladha.android_user.R
import kotlinx.android.synthetic.main.fragment_home.view.*

/**
 * Created by yashladha on 17/10/17.
 * Fragment class for the home portal for the user app
 */

class HomeFragment: Fragment() {

    private var horizontalLm: LinearLayoutManager? = null
    private lateinit var interestedThingsRv : RecyclerView
    private lateinit var interestedThingsAdap : HomeIndexAdapter

    companion object {
        val TAG = "HomeFragment"
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_home, container, false)
        horizontalLm = LinearLayoutManager(view.context, LinearLayout.HORIZONTAL, false)
        interestedThingsRv = view.home_rv_interested_things
        interestedThingsRv.setHasFixedSize(true)

        interestedThingsAdap = HomeIndexAdapter(ArrayList(), view.context)

        return view
    }

}