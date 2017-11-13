package com.code.yashladha.android_user.Portal

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.code.yashladha.android_user.Models.Product
import com.code.yashladha.android_user.R
import com.squareup.picasso.Picasso
import com.synnapps.carouselview.ImageListener
import kotlinx.android.synthetic.main.activity_product_detail.*

class ProductDetail : AppCompatActivity() {

    private lateinit var item : Product
    private lateinit var imageUrls : ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

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

        detail_description.text = item.description
    }
}
