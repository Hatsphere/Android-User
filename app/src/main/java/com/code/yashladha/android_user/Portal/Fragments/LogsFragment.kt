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

   CollectionReference itemRef = db.collection("Item")

   // Create a query against the collection.

   Query query = itemRef.whereEqualTo("uid","user_id")

   Task<QuerySnapshot> task = query.get()

    if(task.isSuccessful())
    {
        int n = task.getResult().size()
        List<DocumentSnapshot> list = task.getResult().getDocuments()

        for(i in n)
        {
            Map<item_id, Item> data = list[i].getData()

            Item item = data.getValue()
            item_id = item.item_id
            int product_id = item.pid
            String date = item.order_date
            int order_id = item.order_id


            CollectionReference productRef = db.collection("Product")

            // Create a query against the collection.

            Query query1 = productRef.whereEqualTo("pid","product_id")

            Task<QuerySnapshot> task1 = query1.get()

            List<DocumentSnapshot> list1 = task1.getRsult().getDocuments()

            Map<pid, Product> data1= list1[0].getData()

            Product pro = data1.getValue()
            String pname = pro.p_name
            int price = pro.price
            int image_id = pro.image_id

            ProductSold soldItem = new ProductSold(item_id, order_id, product_id, image_id, date,price, p_name)

        }
    }

    else {
        Toast.makeText(getActivity(), "No products purchased till now", Toast.LENGTH_SHORT).show();
    }

}
