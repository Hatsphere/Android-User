package com.code.yashladha.android_user.Portal.Fragments

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.code.yashladha.android_user.Models.Product
import com.code.yashladha.android_user.Portal.Adapter.HomeIndexAdapter
import com.code.yashladha.android_user.Portal.Helper.ProductCallback
import com.code.yashladha.android_user.Portal.Helper.ProductHelper
import com.code.yashladha.android_user.R
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.info

/**
 * Created by yashladha on 17/10/17.
 * Fragment class for the home portal for the user app
 */

class HomeFragment : Fragment(), AnkoLogger {

    private var horizontalLm: LinearLayoutManager? = null
    private lateinit var interestedLayoutManag : LinearLayoutManager
    private lateinit var interestedThingsRv: RecyclerView
    private lateinit var interestedThingsAdap: HomeIndexAdapter
    private lateinit var trendingThingsAdap: HomeIndexAdapter

    private lateinit var firestore: FirebaseFirestore
    private lateinit var sellerRef: CollectionReference
    private lateinit var mContext: Context
    private lateinit var interestedProducts: ArrayList<Product>
    private lateinit var trendingProducts: ArrayList<Product>
    private lateinit var trendingRv : RecyclerView

    companion object {
        val TAG = "HomeFragment"
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_home, container, false)

        mContext = view.context
        firestore = FirebaseFirestore.getInstance()
        sellerRef = firestore.collection("seller/registered/Info")

        horizontalLm = LinearLayoutManager(view.context, LinearLayout.HORIZONTAL, false)
        interestedLayoutManag = LinearLayoutManager(view.context, LinearLayout.HORIZONTAL, false)
        interestedThingsRv = view.home_rv_interested_things
        trendingRv = view.home_rv_trending
        trendingRv.layoutManager = horizontalLm
        trendingRv.setHasFixedSize(true)
        interestedThingsRv.setHasFixedSize(true)

        interestedProducts = ArrayList()
        trendingProducts = ArrayList()

        interestedThingsAdap = HomeIndexAdapter(interestedProducts, mContext)
        trendingThingsAdap = HomeIndexAdapter(trendingProducts, mContext)

        interestedThingsRv.layoutManager = interestedLayoutManag
        interestedThingsRv.adapter = interestedThingsAdap
        trendingRv.adapter = trendingThingsAdap

        ProductHelper.getProductInterested(sellerRef, firestore, mContext, ProductCallback { products, context ->
            debug("Comes under Callback")
            info(products.size)
            interestedProducts.addAll(products)
            interestedThingsAdap.notifyDataSetChanged()
        })

        return view
    }

}