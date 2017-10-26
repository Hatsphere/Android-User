package com.code.yashladha.android_user.Portal.Fragments

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.code.yashladha.android_user.R
import com.code.yashladha.android_user.Login.Login;
import com.code.yashladha.android_user.Models.Product;
import com.code.yashladha.android_user.Models.Item;


/**
 * Created by yashladha on 17/10/17.
 */

class LogsFragment: Fragment() {

    companion object {
        val TAG = "LogsFragment"
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_logs, container, false)
        return view
    }

    val user_id;
    user_id = Login.user.getUid();

    val list: List<Product> = ArrayList<Product>()

    val itemRef: CollectionReference = db.collection("Item")

   // Create a query against the collection.

    val query: Query = itemRef.whereEqualTo("uid","user_id")

    val task: Task<QuerySnapshot> = query.get()

    if(task.isSuccessful())
    {
        int n = task.getResult().size()
        val list: List<DocumentSnapshot> = task.getResult().getDocuments()

        for(i in n)
        {
            val data: Map<item_id, Item> = list[i].getData()

            val item: Item = data.getValue()
            val item_id = item.item_id
            val product_id = item.pid
            val date = item.order_date
            val order_id = item.order_id


            val productRef: CollectionReference = db.collection("Product")

            // Create a query against the collection.

            val query1: Query = productRef.whereEqualTo("pid","product_id")

            val task1: Task<QuerySnapshot> = query1.get()

            val list1: List<DocumentSnapshot> = task1.getRsult().getDocuments()

            val data1: Map<pid, Product> = list1[0].getData()

            val pro: Product = data1.getValue()
            val pname: String = pro.p_name
            val price: Int = pro.price
            val image_id: Int = pro.image_id

            val soldItem: ProductSold = ProductSold(item_id, order_id, product_id, image_id, date,price, p_name)

        }
    }

    else {
        Toast.makeText(getActivity(), "No products purchased till now", Toast.LENGTH_SHORT).show();
    }

}
