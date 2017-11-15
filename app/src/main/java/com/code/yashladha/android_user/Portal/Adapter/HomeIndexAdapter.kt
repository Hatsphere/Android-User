package com.code.yashladha.android_user.Portal.Adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.code.yashladha.android_user.Models.Product
import com.code.yashladha.android_user.Portal.ProductDetail
import com.code.yashladha.android_user.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_display.view.*

/**
 * Created by yashladha on 6/11/17.
 * Recycler view adapter for the Index adapter
 */

class HomeIndexAdapter(private val items: ArrayList<Product>, val context: Context): RecyclerView.Adapter<HomeIndexAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) = holder!!.bind(items[position])

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder{
        val view = LayoutInflater.from(context).inflate(R.layout.item_display, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Product) = with(itemView) {
            Picasso
                    .with(context)
                    .load(item.primaryImage)
                    .fit()
                    .into(itemView.item_product_image)
            itemView.item_product_name.text = item.name
            if (!item.isAvailability) {
                itemView.item_available_text.text = "Not Available"
                itemView.item_available_text.setTextColor(resources.getColor(R.color.not_available))
            } else {
                itemView.item_available_text.text = "Available"
            }
            itemView.item_card.setOnClickListener{ v ->
                val intent = Intent(v.context, ProductDetail::class.java)
                intent.putExtra("Product", item)
                v.context.startActivity(intent)
            }
        }

    }

}