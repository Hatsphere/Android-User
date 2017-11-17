package com.code.yashladha.android_user.Portal

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.AdapterView
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Created by yashladha on 16/11/17.
 * Function for List View Item Click Listener
 * mainly for the filtering option
 */

class DrawerClickListener(val firebase: FirebaseFirestore, val context: Context): AdapterView.OnItemClickListener {
    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Log.i("Position selected", position.toString())
        when (id) {
            0L -> HomeDecorFilter()
            1L -> PersonalAccessoriesFilter()
            2L -> GiftsFilter()
            3L -> PaintingsFilter()
            4L -> OtherFilter()
        }
    }

    private fun OtherFilter() {
        getFilterData("Other")
    }

    private fun PaintingsFilter() {
        getFilterData("Painitings")
    }

    private fun GiftsFilter() {
        getFilterData("Gift")
    }

    private fun PersonalAccessoriesFilter() {
        getFilterData("personalAccessories")
    }

    private fun HomeDecorFilter() {
        getFilterData("Home")
    }

    private fun getFilterData(type: String) {
        val typeCollectionRef = firebase.collection(type)

        typeCollectionRef.get()
                .addOnCompleteListener { task ->
                    val result = task.result
                    for (item in result) {
                        Log.d("Filter item", item.id)
                    }
                }
    }
}