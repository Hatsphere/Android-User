package com.code.yashladha.android_user.Portal.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.code.yashladha.android_user.Models.Product
import com.code.yashladha.android_user.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cart_item.view.*

/**
 * Created by yashladha on 24/10/17.
 * Adapter for inflating the cart list item
 */

class CartItemListAdapter(val items: ArrayList<Product>, val context: Context) : RecyclerView.Adapter<CartItemListAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) = holder!!.bind(items[position])

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Product) = with(itemView) {
            itemView.tv_cart_item_name.text = item.name
            Picasso.with(context)
                    .load(item.primaryImage)
                    .fit()
                    .into(itemView.cart_item_product_image)
            val priceValue = "Rs. " + item.price.toString()
            itemView.cart_quantity.text = item.quantity.toString()

            itemView.cart_increase_button.setOnClickListener {
                itemView.cart_quantity.text = (item.quantity + 1).toString()
                item.quantity += 1

                sendItemIncrease(item)
            }

            itemView.cart_decrease_button.setOnClickListener {
                if (item.quantity - 1 >= 0) {
                    itemView.cart_quantity.text = (item.quantity - 1).toString()
                    item.quantity -= 1

                    sendItemDecrease(item)
                }
            }

            itemView.cart_item_price.text = priceValue
        }

        private fun sendItemDecrease(item: Product) {

        }

        private fun sendItemIncrease(item: Product) {

        }
    }

}