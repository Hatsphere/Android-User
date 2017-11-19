package com.code.yashladha.android_user.Portal.Fragments

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.code.yashladha.android_user.Login.Login
import com.code.yashladha.android_user.Adapter.LogsAdapter
import com.code.yashladha.android_user.Models.OrderPlaced
import com.code.yashladha.android_user.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import android.util.Log
import com.code.yashladha.android_user.Models.Order
import com.code.yashladha.android_user.R.id.list
import com.google.android.gms.tasks.OnCompleteListener


/**
 * Created by yashladha on 17/10/17.
 */

class LogsFragment: Fragment() {

    companion object {
        val TAG = "LogsFragment"
    }
    var currentOrder : String? = ""
    val ORDER_DATE :String? =""
    val ORDER_ID :String? = ""

    val firestore = FirebaseFirestore.getInstance()

    private var mContext : Context? = null
    val login = Login.newInstance()
    val user_id = login.getUserId()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_logs, container, false)

        val orders: ArrayList<OrderPlaced> = ArrayList<OrderPlaced>()

        mContext = view.context

        firestore.collection("Order")
                .whereEqualTo("uid", user_id)
                .get()
                .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                    if (task.isSuccessful) {
                        for (document in task.result) {
                            val order = document.toObject(Order::class.java)
                            currentOrder = order.order_date
                            val orderId = order.order_id
                            val totalCost = order.total_cost
                            val o1 = OrderPlaced(orderId, currentOrder!!,totalCost)
                            orders.add(o1)

                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.exception)
                    }
                })
        val adapter = LogsAdapter(mContext, orders, 0)

        val listView = list as ListView
        listView!!.setAdapter(adapter)

        /* listView.onItemClickListener = object : AdapterView.OnItemClickListener {
             override fun onItemClick(parent: AdapterView<out Adapter?>?, view: View?, position: Int, id: Long) {

                 val order = orders[position]
                 val order_id = order.order_id
                 val order_date = order.order_date
                 val i = newIntent(order_id, order_date)
                 startActivity(i)
             }
         };*/
        return view
    }

    /* private fun newIntent(order_id :String, order_date:String ): Intent {
         val intent = Intent(mContext, LogsActivity::class.java)
         intent.putExtra(ORDER_ID, order_id)
         intent.putExtra(ORDER_DATE, order_date)
         return intent
     }*/
}