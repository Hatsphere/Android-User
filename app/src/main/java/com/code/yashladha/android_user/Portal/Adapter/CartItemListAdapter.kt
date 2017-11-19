package com.code.yashladha.android_user.Portal.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.code.yashladha.android_user.Models.Product
import com.code.yashladha.android_user.R
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cart_item.view.*
import kotlinx.android.synthetic.main.fragment_cart.view.*

/**
 * Created by yashladha on 24/10/17.
 * Adapter for inflating the cart list item
 */

class CartItemListAdapter(val uid: String, val firestore: FirebaseFirestore, val items: ArrayList<Product>, val context: Context, val view: View) : RecyclerView.Adapter<CartItemListAdapter.ViewHolder>() {

    val totalCost = view.cart_subtotal

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) = holder!!.bind(items[position], firestore, uid, totalCost)

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Product, firestore: FirebaseFirestore, uid: String, totalCost: TextView) = with(itemView) {
            itemView.tv_cart_item_name.text = item.name
            if (item.primaryImage != "") {
                Picasso.with(context)
                        .load(item.primaryImage)
                        .fit()
                        .into(itemView.cart_item_product_image)
            } else {
                Picasso.with(context)
                        .load(R.drawable.logo)
                        .fit()
                        .into(itemView.cart_item_product_image)
            }
            val priceValue = "Rs. " + item.price.toString()
            itemView.cart_quantity.text = item.quantity.toString()

            itemView.cart_increase_button.setOnClickListener {
                itemView.cart_quantity.text = (item.quantity + 1).toString()
                item.quantity += 1

                val text = totalCost.text
                if (text != null) {
                    var amount = text.toString().toLong()
                    amount += item.price
                    totalCost.text = amount.toString()
                } else {
                    totalCost.text = item.price.toString()
                }

                sendItemIncrease(item, firestore, uid)
            }

            itemView.cart_decrease_button.setOnClickListener {
                if (item.quantity - 1 >= 0) {
                    itemView.cart_quantity.text = (item.quantity - 1).toString()
                    item.quantity -= 1

                    val text = totalCost.text
                    if (text != null) {
                        var amount = text.toString().toLong()
                        amount -= item.price
                        if (amount >= 0) {
                            totalCost.text = amount.toString()
                        }
                    } else {
                        totalCost.text = "0"
                    }


                    sendItemDecrease(item, firestore, uid)
                }
            }

            itemView.cart_item_price.text = priceValue
        }

        private fun sendItemDecrease(item: Product, firestore: FirebaseFirestore, uid: String) {
            firestore.document(uid + "/cart/Info/" + item.name + "_" + item.sellerId)
                    .update("quantity", item.quantity)
                    .addOnSuccessListener {
                        Log.d("Increase", "Item incremented")
                    }
                    .addOnFailureListener {
                        Log.e("Increase", "Item not incremented")
                        item.quantity += 1
                    }
        }

        private fun sendItemIncrease(item: Product, firestore: FirebaseFirestore, uid: String) {
            firestore.document(uid + "/cart/Info/" + item.name + "_" + item.sellerId)
                    .update("quantity", item.quantity)
                    .addOnSuccessListener {
                        Log.d("Increase", "Item incremented")
                    }
                    .addOnFailureListener {
                        Log.e("Increase", "Item not incremented")
                        if (item.quantity - 1 >= 0) {
                            item.quantity -= 1
                        }
                    }
        }
    }

}