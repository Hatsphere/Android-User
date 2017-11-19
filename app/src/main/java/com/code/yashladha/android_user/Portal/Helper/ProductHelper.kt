package com.code.yashladha.android_user.Portal.Helper

import android.content.Context
import android.util.Log
import com.code.yashladha.android_user.Models.Product
import com.code.yashladha.android_user.Models.TrendingObject
import com.code.yashladha.android_user.R.id.quantity
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by yashladha on 11/11/17.
 * Helper function for showing the product list
 */

class ProductHelper {
    companion object {
        fun getProductInterested(interestedProducts: ArrayList<Product>,
                                 sellerReference: CollectionReference,
                                 firebase: FirebaseFirestore,
                                 context: Context,
                                 callback: ProductCallback) {
            val sellerId = ArrayList<String>()

            sellerReference.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result.mapTo(sellerId) { it.id }
                    sellerId
                            .map { firebase.collection(it + "/Products/Info") }
                            .forEach {

                                it.get().addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        val sellerIdInfo = it.parent.parent.id
                                        for (item in task.result) {
                                            val data = item.data

                                            if (interestedProducts.size < 16) {
                                                interestedProducts.add(ExtractProduct(sellerIdInfo, item.id, data))
                                            }
                                        }
                                        callback.updateProductUI(context)
                                    }
                                }
                            }
                }
            }
        }

        private fun ExtractProduct(sellerId: String, productName: String, data: Map<String, Any>): Product {
            val sale = data.get("Sale") as Boolean
            val description = data.get("Description") as String
            val price = data.get("Price") as String
            val availability = data.get("Availability") as Boolean
            val productClass = data.get("Class") as String
            val images = data.get("Images") as HashMap<*, *>
            val primaryImage = images.get("primaryImage") as String
            val leftImage = images.get("leftImage") as String
            val rightImage = images.get("rightImage") as String

            return Product(
                    sellerId,
                    productName,
                    sale,
                    description,
                    price.toInt(),
                    availability,
                    productClass,
                    primaryImage,
                    leftImage,
                    rightImage
            )
        }

        fun getTrendingProducts(mainProducts: ArrayList<Product>,
                                sellerReference: CollectionReference,
                                firebase: FirebaseFirestore,
                                context: Context,
                                callback: ProductCallback) {
            val sellerdId = ArrayList<String>()


            sellerReference.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Complete Listener", "Seller fetched successfully")
                    task.result.mapTo(sellerdId) { it.id }
                    sellerdId
                            .map { firebase.collection(it + "/count/Info") }
                            .forEach {

                                it.get().addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        var trendingObjects = ArrayList<TrendingObject>()
                                        val sellerId = it.parent.parent.id
                                        for (item in task.result) {
                                            val id = item.id
                                            val data = item.data
                                            if (data.get("itemSold") != null) {
                                                val count = data.get("itemSold") as Long
                                                trendingObjects.add(TrendingObject(id, count, sellerId))
                                            }
                                        }
                                        trendingObjects = ArrayList(trendingObjects.sortedWith(compareByDescending ({ it.soldItems })))
                                        for (item in trendingObjects) {
                                            Log.d("ItemSold", item.soldItems.toString() + " " + item.productName)
                                        }
                                        Log.d("Trending", trendingObjects.toString())
                                        for (item in trendingObjects) {
                                            val productRef = firebase.document(item.sellerId + "/Products/Info/" + item.productName)
                                            productRef.get()
                                                    .addOnCompleteListener { task2 ->
                                                        if (task2.isSuccessful) {
                                                            Log.d("Item", item.productName + " " + item.soldItems)
                                                            val result = task2.result
                                                            Log.i("Result Id: ", result.id)
                                                            val tempProduct = ExtractProduct(item.sellerId, item.productName, result.data)
                                                            tempProduct.quantity = item.soldItems.toString().toInt()
                                                            mainProducts.add(tempProduct)
                                                            callback.updateProductUI(context)
                                                        }
                                                    }
                                        }
                                    }
                                }
                            }
                }
            }
        }
    }
}