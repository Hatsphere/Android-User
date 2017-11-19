package com.code.yashladha.android_user.Portal.Helper

import android.content.Context
import android.util.Log
import com.code.yashladha.android_user.Models.Product
import com.code.yashladha.android_user.Models.TrendingObject
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
        fun getProductInterested(sellerReference: CollectionReference,
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
                                val sellerProducts = ArrayList<Product>()

                                it.get().addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        val sellerIdInfo = it.parent.parent.id
                                        for (item in task.result) {
                                            val data = item.data

                                            if (sellerProducts.size < 16) {
                                                sellerProducts.add(ExtractProduct(sellerIdInfo, item.id, data))
                                            }
                                        }
                                        callback.updateProductUI(sellerProducts, context)
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

        fun getTrendingProducts(sellerReference: CollectionReference,
                                firebase: FirebaseFirestore,
                                context: Context,
                                callback: ProductCallback) {
            val sellerdId = ArrayList<String>()


            sellerReference.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Complete Listener", "Seller fetched successfully")
                    task.result.mapTo(sellerdId) { it.id }
                    sellerdId
                            .map { firebase.collection(it + "/orders/waiting") }
                            .forEach {

                                it.get().addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        val products = ArrayList<Product>()
                                        val trendingObjects = ArrayList<TrendingObject>()
                                        for (item in task.result) {
                                            val data = item.data
                                            if (data.get("productName") != null) {
                                                val pName = data.get("productName") as String
                                                val quantity = data.get("quantity") as Long
                                                val sellerId = data.get("sellerId") as String

                                                trendingObjects.add(TrendingObject(pName, quantity, sellerId))
                                            }
                                        }
                                        trendingObjects.sortedWith(compareBy({ it.quantity }))
                                        for (item in trendingObjects) {
                                            val productRef = firebase.document(item.sellerId + "/Products/Info/" + item.productName)
                                            productRef.get()
                                                    .addOnCompleteListener { task2 ->
                                                        if (task2.isSuccessful) {
                                                            val result = task2.result
                                                            Log.i("Result Id: ", result.id)
                                                            products.add(ExtractProduct(item.sellerId, item.productName, result.data))
                                                            callback.updateProductUI(products, context)
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