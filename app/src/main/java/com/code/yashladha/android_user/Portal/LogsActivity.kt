package com.code.yashladha.android_user.Portal

import android.content.Context
import android.content.Intent
import android.content.Intent.getIntent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.code.yashladha.android_user.Models.OrderPlaced
import com.code.yashladha.android_user.Models.ProductSold
import com.code.yashladha.android_user.Adapter.LogDetailAdapter
import com.code.yashladha.android_user.Adapter.LogsAdapter
import com.code.yashladha.android_user.Portal.Fragments.LogsFragment
import com.code.yashladha.android_user.R
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.list.list

/**
 * Created by User on 11/4/2017.
 */
class LogsActivity {

    fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_logs, container, false)

        val intent:Intent? = null
        val strDate: String = intent!!.getStringExtra(LogsFragment().ORDER_DATE)
        val order_id : String = intent!!.getStringExtra(LogsFragment().ORDER_ID)

        val firestore = FirebaseFirestore.getInstance()

        val itemRef: CollectionReference = firestore.collection("Item")
        val product: CollectionReference = firestore.collection("Product")

        val query1: Query = itemRef.whereEqualTo("order_id", order_id)

        val task: Task<QuerySnapshot> = query1.get()
        val sold_products : ArrayList<ProductSold> = ArrayList<ProductSold>()

        if (task.isSuccessful()) {

            val list = task.result.documents

            for (item in list) {
                val item_no = item.getData()
                val item_id = item.getId() as Int
                val pid = item_no.get("pid")

                val query1: Query = product.whereEqualTo("pid", pid)
                val task: Task<QuerySnapshot> = query1.get()

                val list1 = task.result.documents
                val product_sold = list1[0].getData()
                val price = product_sold.get("price") as Int
                val p_name = product_sold.get("p_name") as String
                val image_id = product_sold.get("image_id") as Int
                val soldProduct = ProductSold(item_id,image_id, price, p_name)

                sold_products!!.add(soldProduct)

            }
            val adapter = LogDetailAdapter(this.activity,sold_products,0)
            val listView = list as ListView
            listView!!.setAdapter(adapter)

        }
        return view
    }
}