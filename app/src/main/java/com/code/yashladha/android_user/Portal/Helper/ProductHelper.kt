package com.code.yashladha.android_user.Portal.Helper

import android.content.Context
import android.util.Log
import com.code.yashladha.android_user.Models.Product
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

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
                                        val uidId = it.parent.parent.id
                                        for (item in task.result) {
                                            val data = item.data
                                            val sale = data.get("Sale") as Boolean
                                            val description = data.get("Description") as String
                                            val price = data.get("Price") as String
                                            val availability = data.get("Availability") as Boolean
                                            val productClass = data.get("Class") as String
                                            val images = data.get("Images") as HashMap<*, *>
                                            val primaryImage = images.get("primaryImage") as String
                                            val leftImage = images.get("leftImage") as String
                                            val rightImage = images.get("rightImage") as String

                                            if (sellerProducts.size < 16) {
                                                sellerProducts.add(Product(it.parent.parent.id, item.id, sale, description, price.toInt(), availability, productClass, primaryImage, leftImage, rightImage))
                                            }
                                        }
                                        callback.updateProductUI(sellerProducts, context)
                                    }
                                }
                            }
                }
            }
        }
    }
}