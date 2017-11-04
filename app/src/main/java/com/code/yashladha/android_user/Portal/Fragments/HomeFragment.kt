package com.code.yashladha.android_user.Portal.Fragments

import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.code.yashladha.android_user.R
import kotlinx.android.synthetic.main.fragment_home.view.*

/**
 * Created by yashladha on 17/10/17.
 * Fragment class for the home portal for the user app
 */

class HomeFragment: Fragment() {

    companion object {
        val TAG = "HomeFragment"
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_home, container, false)
        view.tvTemp.setOnClickListener {
            val intent = Intent(view!!.context, ItemDetail::class.java)
            startActivity(intent)
        }
        return view
    }

}