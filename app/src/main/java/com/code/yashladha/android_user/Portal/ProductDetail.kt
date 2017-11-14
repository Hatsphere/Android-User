package com.code.yashladha.android_user.Portal

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.code.yashladha.android_user.Models.Product
import com.code.yashladha.android_user.Models.Seller
import com.code.yashladha.android_user.R
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import com.synnapps.carouselview.ImageListener
import kotlinx.android.synthetic.main.activity_product_detail.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class ProductDetail : AppCompatActivity(), AnkoLogger {

    private lateinit var item: Product
    private lateinit var imageUrls: ArrayList<String>
    private lateinit var firestore: FirebaseFirestore
    private lateinit var sellerId: String
    private lateinit var sellerInfo : Seller

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        firestore = FirebaseFirestore.getInstance()

        imageUrls = ArrayList()
        item = intent.getSerializableExtra("Product") as Product

        imageUrls.add(item.primaryImage)
        imageUrls.add(item.leftImage)
        imageUrls.add(item.rightImage)

        detail_image_carousal.pageCount = 3

        val imageListener = ImageListener { position, imageView ->
            if (imageUrls.get(position) != "") {
                Picasso.with(baseContext)
                        .load(imageUrls.get(position))
                        .into(imageView)
            } else {
                Picasso.with(baseContext)
                        .load(R.drawable.logo)
                        .fit()
                        .into(imageView)
            }
        }

        detail_image_carousal.setImageListener(imageListener)
        val price = "Rs. " + item.price.toString()
        detail_price.text = price

        sellerId = item.sellerId

        firestore.document("seller/registered/Info/" + sellerId)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        sellerInfo = task.result.toObject(Seller::class.java)
                        Picasso.with(baseContext)
                                .load(sellerInfo.profileImage)
                                .into(detail_seller_image)

                        detail_seller_name.text = sellerInfo.name
                    }
                }

        detail_description.text = item.description
    }
}
