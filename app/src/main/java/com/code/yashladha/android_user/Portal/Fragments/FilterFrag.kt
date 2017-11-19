package com.code.yashladha.android_user.Portal.Fragments


import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.code.yashladha.android_user.Models.Filter
import com.code.yashladha.android_user.Models.Product
import com.code.yashladha.android_user.Portal.Adapter.HomeIndexAdapter
import com.code.yashladha.android_user.Portal.Helper.ProductHelper
import com.code.yashladha.android_user.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_filter.view.*
import kotlinx.android.synthetic.main.fragment_logs.view.*


/**
 * A simple [Fragment] subclass.
 */
class FilterFrag : Fragment() {

    private lateinit var filterObj: ArrayList<Filter>
    private lateinit var firestore: FirebaseFirestore
    private lateinit var userId: String
    private lateinit var products: ArrayList<Product>
    private lateinit var adapter_: HomeIndexAdapter
    private lateinit var rv: RecyclerView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_filter, container, false)
        val bundle = arguments
        products = ArrayList()
        filterObj = bundle.getSerializable("Filters") as ArrayList<Filter>
        firestore = FirebaseFirestore.getInstance()
        userId = FirebaseAuth.getInstance().currentUser!!.uid

        InflateFilter()

        val lm = GridLayoutManager(view.context, 2)
        rv = view.filter_rv
        rv.setHasFixedSize(true)
        rv.layoutManager = lm
        adapter_ = HomeIndexAdapter(products, view.context)
        rv.adapter = adapter_

        return view
    }

    private fun InflateFilter() {
        for (filterItem in filterObj) {
            val sellerId = filterItem.sellerId
            val itemName = filterItem.getpName()

            firestore.document(sellerId + "/Products/Info/" + itemName)
                    .get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val data = task.result.data
                            products.add(ProductHelper.ExtractProduct(sellerId, itemName, data))
                            adapter_.notifyDataSetChanged()
                        }
                    }
        }
    }

}
