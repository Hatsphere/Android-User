package com.code.yashladha.android_user.Portal

import android.content.res.ColorStateList
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.code.yashladha.android_user.Models.Product
import com.code.yashladha.android_user.Models.Seller
import com.code.yashladha.android_user.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import com.synnapps.carouselview.ImageListener
import kotlinx.android.synthetic.main.activity_product_detail.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.toast

class ProductDetail : AppCompatActivity(), AnkoLogger {

    private lateinit var item: Product
    private lateinit var imageUrls: ArrayList<String>
    private lateinit var firestore: FirebaseFirestore
    private lateinit var sellerId: String
    private lateinit var sellerInfo : Seller
    private lateinit var userId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        firestore = FirebaseFirestore.getInstance()
        userId = FirebaseAuth.getInstance().currentUser!!.uid
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
                        .fit()
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
                        val result = task.result
                        sellerInfo = ExtractSeller(result.data)
                        Picasso.with(baseContext)
                                .load(sellerInfo.profileImage)
                                .fit()
                                .into(detail_seller_image)

                        detail_seller_name.text = sellerInfo.name
                    }
                }

        detail_fab_checkout.setOnClickListener {
            debug("Item added to cart")
            AddToCart()
        }

        detail_fab_wishlist.setOnClickListener {
            detail_fab_wishlist.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.like_heart))
        }

        detail_description.text = item.description
    }

    private fun ExtractSeller(data: Map<String, Any>): Seller {
        return Seller(data.get("address").toString(), data.get("contactNo").toString(), data.get("Name").toString(), data.get("planChosen").toString(), data.get("profileImage").toString())
    }

    private fun AddToCart() {

        detail_progress_bar.visibility = View.VISIBLE
        detail_main_layout.alpha = 0.2f

        firestore.document(userId + "/cart/Info/" + item.name + "_" + item.sellerId)
                .set(item)
                .addOnCompleteListener { task ->
                    detail_main_layout.alpha = 1.0f
                    detail_progress_bar.visibility = View.INVISIBLE
                    if (task.isSuccessful) {
                        detail_fab_checkout.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.added_to_cart))
                        toast("Product added to cart")
                    } else {
                        toast("Unable to add to cart")
                        error("Unable to add item to cart")
                    }
                }

    }
}
