package com.code.yashladha.android_user.Portal.Fragments

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.code.yashladha.android_user.Models.Item
import com.code.yashladha.android_user.Portal.Adapter.CartItemListAdapter
import com.code.yashladha.android_user.R
import kotlinx.android.synthetic.main.fragment_cart.view.*

/**
 * Created by yashladha on 17/10/17.
 * Cart fragment for the android user
 */

class CartFragment: Fragment() {

    companion object {
        val TAG = "CartFragment"
    }

    private var itemList : RecyclerView? = null
    private var totalCost : TextView? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_cart, container, false)

        itemList = view.cart_item_list
        totalCost = view.cart_subtotal

        val lm = LinearLayoutManager(view.context)
        itemList!!.layoutManager = lm
        itemList!!.setHasFixedSize(true)

        val list = setTempData()
        val adapter = CartItemListAdapter(list, view.context)
        itemList!!.adapter = adapter

        return view
    }

    private fun setTempData(): ArrayList<Item> {
        val items = ArrayList<Item>()
        items.add(Item(0, "25", "0", 0, 0, 0 ))
        items.add(Item(0, "25", "0", 0, 0, 0 ))
        items.add(Item(0, "25", "0", 0, 0, 0 ))
        items.add(Item(0, "25", "0", 0, 0, 0 ))
        return items
    }
}
