package com.code.yashladha.android_user.Portal

import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.content.Intent.getIntent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.code.yashladha.android_user.Models.OrderPlaced
import com.code.yashladha.android_user.Models.ProductSold
import com.code.yashladha.android_user.Adapter.LogDetailAdapter
import com.code.yashladha.android_user.Adapter.LogsAdapter
import com.code.yashladha.android_user.Login.Login
import com.code.yashladha.android_user.Models.Item
import com.code.yashladha.android_user.Models.Order
import com.code.yashladha.android_user.Portal.Fragments.LogsFragment
import com.code.yashladha.android_user.R
import com.code.yashladha.android_user.R.id.list
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.list.list

/**
 * Created by Jaya on 11/4/2017.
 */
class LogsDetailFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.sold_item, container, false)
        val logContext = view.context

        val intent:Intent? = null
        val order_id : String = intent!!.getStringExtra(LogsFragment().ORDER_ID)

        val login = Login.newInstance()
        val user_id = login.getUserId()

        val firestore = FirebaseFirestore.getInstance()
        val sold_products : ArrayList<ProductSold> = ArrayList<ProductSold>()

        firestore.collection("Item")
                .whereEqualTo("order_id",order_id)
                .get().addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
            if (task.isSuccessful) {
                for (item in task.result) {
                    val items = item.toObject(Item::class.java)




                }
            } else {
                Log.d(LogsFragment.TAG, "Error getting documents: ", task.exception)
            }
        })

        /* if (task.isSuccessful()) {

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
             }*/
        val adapter = LogDetailAdapter(this.activity,sold_products,0)
        val listView = R.id.list as ListView
        listView!!.setAdapter(adapter)

        return view

    }
}