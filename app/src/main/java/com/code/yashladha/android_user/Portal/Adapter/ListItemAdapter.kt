package com.code.yashladha.android_user.Portal.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.code.yashladha.android_user.Portal.Model.ListItem
import com.code.yashladha.android_user.R
import kotlinx.android.synthetic.main.drawer_list_item.view.*

/**
 * Created by yashladha on 17/10/17.
 */

class ListItemAdapter(var itemList: ArrayList<ListItem>?, var context: Context?) : BaseAdapter() {

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.drawer_list_item, p2, false)
        val item = getItem(p0)
        view.iv_list_item.setImageResource(item.icon)
        view.tv_list_item.text = item.title
        return view
    }

    override fun getItem(p0: Int): ListItem = itemList!![p0]

    override fun getItemId(p0: Int): Long = p0.toLong()

    override fun getCount(): Int = itemList!!.size

}