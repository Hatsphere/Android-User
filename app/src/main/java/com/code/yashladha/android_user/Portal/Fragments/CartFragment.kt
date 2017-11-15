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
import com.code.yashladha.android_user.Models.Item
import com.code.yashladha.android_user.Models.Order
import com.code.yashladha.android_user.Models.Product
import com.code.yashladha.android_user.Portal.Adapter.CartItemListAdapter
import com.code.yashladha.android_user.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_cart.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by yashladha on 17/10/17.
 * Cart fragment for the android user
 */

class CartFragment: Fragment(), AnkoLogger {

    companion object {
        val TAG = "CartFragment"
    }

    private var itemList : RecyclerView? = null
    private var totalCost : TextView? = null
    private lateinit var list : ArrayList<Product>
    private lateinit var firestore : FirebaseFirestore
    private lateinit var userId : String
    private lateinit var adapter : CartItemListAdapter


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_cart, container, false)

        itemList = view.cart_item_list
        totalCost = view.cart_subtotal
        list = ArrayList()
        firestore = FirebaseFirestore.getInstance()
        userId = FirebaseAuth.getInstance().currentUser!!.uid

        info(userId)

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

        val lm = LinearLayoutManager(view.context)
        itemList!!.layoutManager = lm
        itemList!!.setHasFixedSize(true)

        adapter = CartItemListAdapter(list, view.context)
        itemList!!.adapter = adapter

        return view
    }

    private fun checkout() {
        firestore.collection(userId + "/purchase")
                .document(getTimeStamp())

        val writeBatch = firestore.batch()
        for (item in list) {
            val orderRef = firestore.collection(item.sellerId + "/orders/" + item.name).document(userId)
            val orderObject = Order()

            orderObject.uid = userId
            orderObject.order_date = getTimeStamp()
            orderObject.status = "Accepted"

            writeBatch.set(orderRef, orderObject)
        }

        writeBatch.commit().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                debug("Batch write success, cart ordered")
                toast("Order placed")
                list = ArrayList()
                adapter.notifyDataSetChanged()
            } else {
                toast("Error while ordering")
                error("Error while writing the batch, order not created")
            }
        }
    }

    private fun getTimeStamp() : String {
        var timstamp = ""
        val simpleDateFormat = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            SimpleDateFormat("dd-MM-yyyy-hh-mm-ss")
        } else {
            null
        }

        if (simpleDateFormat != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                timstamp = simpleDateFormat.format(Date())
            }
        }
        return timstamp
    }

    private fun setTempData(): ArrayList<Item> {
        val items = ArrayList<Item>()
        items.add(Item(0, "25", "0", 0, 0, 0 ))
        return items
    }
}
