package com.code.yashladha.android_user.Portal.Fragments

import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.constraint.R.id.parent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import com.code.yashladha.android_user.Login.Login
import com.code.yashladha.android_user.Adapter.LogsAdapter
import com.code.yashladha.android_user.Models.OrderPlaced
import com.code.yashladha.android_user.Portal.LogsActivity
import com.code.yashladha.android_user.R
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.list.list

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

    val orderRef: CollectionReference = firestore.collection("Order")

    val login = Login.newInstance();
    val user_id = login.getUserId()

    val query: Query = orderRef.whereEqualTo("uid", user_id)

    val task: Task<QuerySnapshot> = query.get()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_logs, container, false)

        val orders : ArrayList<OrderPlaced> = ArrayList<OrderPlaced>()

        if (task.isSuccessful()) {
            val list = task.result.documents

            for (order in list) {

                val order_no = order.getData()
                currentOrder = order_no.get("order_date") as String
                val order_id = order_no.get("order_id") as Long
                val status = order_no.get("status")
                val o1 = OrderPlaced(order_id,currentOrder!!)
                orders.add(o1)

            }
            val adapter = LogsAdapter(this.activity,orders,0)

            val listView = list as ListView
            listView!!.setAdapter(adapter)

            listView.onItemClickListener = object: AdapterView.OnItemClickListener {
                override fun onItemClick(parent: AdapterView<out Adapter?>?, view: View?, position: Int, id: Long){

                    val order = orders[position]
                    val order_id= order.order_id as String
                    val order_date = order.order_date
                    val i = newIntent(LogsFragment(), order_id, order_date)
                    startActivity(i)
                }};
        }
        else {
            Toast.makeText(getActivity(), "No products purchased till now", Toast.LENGTH_SHORT).show();
        }
        return view
    }

    private fun newIntent(Context: LogsFragment, order_id :String, order_date:String ): Intent {
        val intent = Intent(Context, LogsActivity::class.java)
        intent.putExtra(ORDER_ID, order_id)
        intent.putExtra(ORDER_DATE, order_date)
        return intent
    }
}
