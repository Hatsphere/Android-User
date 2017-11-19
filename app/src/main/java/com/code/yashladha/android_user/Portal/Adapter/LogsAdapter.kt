package com.code.yashladha.android_user.Portal.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.code.yashladha.android_user.Models.Logs
import com.code.yashladha.android_user.R
import kotlinx.android.synthetic.main.log_item.view.*

/**
 * Created by yashladha on 19/11/17.
 * Log Adapter for showing the order placed Log
 */

class LogsAdapter(val logsList: ArrayList<Logs>, val context: Context) : RecyclerView.Adapter<LogsAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder?, position: Int): Unit = holder!!.bind(logsList[position])

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.log_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = logsList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Logs) = with(itemView) {
            itemView.log_timestamp.text = item.timeStamp
            val productCountString = "No. of products ordered: " + item.items.size.toString()
            itemView.logs_products_number.text = productCountString

            itemView.setOnClickListener {
                Toast.makeText(context, item.timeStamp, Toast.LENGTH_SHORT).show()
            }
        }
    }

}

