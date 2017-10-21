package com.code.yashladha.android_user

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

/**
 * Created by yashladha on 21/10/17.
 */

class Helper {
    companion object {
        val ROOTREF = FirebaseFirestore.getInstance()

        fun getAllCollection (obj: ArrayList<QuerySnapshot> ,collectionName: String) {
            ROOTREF.collection(collectionName)
                    .get()
                    .addOnSuccessListener {
                        querySnapshot ->
                        obj.add(querySnapshot)
                    }
        }
    }
}