package com.code.yashladha.android_user.Portal

import android.content.Context
import android.content.Intent
import android.content.Intent.getIntent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.code.yashladha.android_user.Models.OrderPlaced
import com.code.yashladha.android_user.Models.ProductSold
import com.code.yashladha.android_user.Adapter.LogDetailAdapter
import com.code.yashladha.android_user.Adapter.LogsAdapter
import com.code.yashladha.android_user.Portal.Fragments.LogsFragment
import com.code.yashladha.android_user.R
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.list.list

/**
 * Created by User on 11/4/2017.
 */
class LogsActivity {

    fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_logs, container, false)
        return view
    }
}