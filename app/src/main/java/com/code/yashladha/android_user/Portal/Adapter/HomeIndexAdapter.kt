package com.code.yashladha.android_user.Portal.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.code.yashladha.android_user.Models.Item
import com.code.yashladha.android_user.R

/**
 * Created by yashladha on 6/11/17.
 */

class HomeIndexAdapter(val items: ArrayList<Item>, val context: Context): RecyclerView.Adapter<HomeIndexAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) = holder!!.bind(items[position])

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder{
        val view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Item) = with(itemView) {
        }
    }

}