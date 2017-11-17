package com.code.yashladha.android_user.Portal.Fragments

import android.app.Fragment
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.code.yashladha.android_user.Models.Order
import com.code.yashladha.android_user.Models.Product
import com.code.yashladha.android_user.Portal.Adapter.CartItemListAdapter
import com.code.yashladha.android_user.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_cart.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * Created by yashladha on 17/10/17.
 * Cart fragment for the android user
 */

class CartFragment : Fragment(), AnkoLogger {

    companion object {
        val TAG = "CartFragment"
    }

    private var itemList: RecyclerView? = null
    private var totalCost: TextView? = null
    private lateinit var list: ArrayList<Product>
    private lateinit var firestore: FirebaseFirestore
    private lateinit var userId: String
    private lateinit var adapter: CartItemListAdapter


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_cart, container, false)

        itemList = view.cart_item_list
        totalCost = view.cart_subtotal
        list = ArrayList()
        firestore = FirebaseFirestore.getInstance()
        userId = FirebaseAuth.getInstance().currentUser!!.uid

        info(userId)

        InflateCartList()

        val lm = LinearLayoutManager(view.context)
        itemList!!.layoutManager = lm
        itemList!!.setHasFixedSize(true)

        adapter = CartItemListAdapter(list, view.context)
        itemList!!.adapter = adapter

        view.cart_checkout_btn.setOnClickListener {
            checkout()
        }

        return view
    }

    private fun InflateCartList() {
        firestore.collection(userId + "/cart/Info")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        info("Cart item Fetched successfully")
                        task.result
                                .map { it.toObject(Product::class.java) }
                                .forEach { list.add(it) }
                        adapter.notifyDataSetChanged()
                    } else {
                        debug(task.exception)
                    }
                }
                .addOnFailureListener { exception ->
                    exception.stackTrace
                }
    }

    /**
     * Function to clean the cart after the checkout
     * The clean operation is performed using batch write
     */
    private fun cleanCart() {
        val writeBatch = firestore.batch()
        for (item in list) {
            val productRef = firestore.collection(userId + "/cart/Info").document(item.name + "_" + item.sellerId)
            writeBatch.delete(productRef)
        }

        writeBatch.commit()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        debug("Cart Cleaned")
                    } else {
                        error("Error while cleaning Cart")
                    }
                }
    }

    private fun checkout() {

        val writeBatch = firestore.batch()
        val timeStamp = getTimeStamp()
        for (item in list) {
            val orderObject = Order()

            orderObject.uid = userId
            orderObject.order_date = timeStamp
            orderObject.status = "Waiting"
            orderObject.productName = item.name
            orderObject.sellerId = item.sellerId
            orderObject.quantity = 1

            val orderRef = firestore.collection(item.sellerId + "/orders/waiting").document()
            val userOrderRef = firestore.collection(userId + "/orders/waiting").document(orderRef.id)
            orderObject.order_id = orderRef.id
            writeBatch.set(userOrderRef, orderObject)
            writeBatch.set(orderRef, orderObject)
        }

        writeBatch.commit().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                debug("Batch write success, cart ordered")
                toast("Order placed")
                cleanCart()
                list.clear()
                adapter.notifyDataSetChanged()
            } else {
                toast("Error while ordering")
                error("Error while writing the batch, order not created")
            }
        }
    }

    private fun getTimeStamp(): String {
        var timestamp = ""
        val simpleDateFormat = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            SimpleDateFormat("dd-MM-yyyy-hh-mm-ss")
        } else {
            null
        }

        if (simpleDateFormat != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                timestamp = simpleDateFormat.format(Date())
            }
        }
        return timestamp
    }

}
