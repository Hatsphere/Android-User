package com.code.yashladha.android_user.Portal.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.code.yashladha.android_user.Models.Item
import com.code.yashladha.android_user.R
import kotlinx.android.synthetic.main.cart_item.view.*

/**
 * Created by yashladha on 24/10/17.
 * Adapter for inflating the cart list item
 */

class CartItemListAdapter(val items: ArrayList<Item>, val context: Context) : RecyclerView.Adapter<CartItemListAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) = holder!!.bind(items[position])

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Item) = with(itemView) {
            itemView.tv_cart_item_name.text = "Sample Item"
        }
    }

}