package com.code.yashladha.android_user.Portal.Fragments

import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.constraint.R.id.parent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import com.code.yashladha.android_user.Login.Login
import com.code.yashladha.android_user.Adapter.LogsAdapter
import com.code.yashladha.android_user.Models.OrderPlaced
import com.code.yashladha.android_user.Portal.LogsActivity
import com.code.yashladha.android_user.R
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.list.list

/**
 * Created by yashladha on 17/10/17.
 */

class LogsFragment: Fragment() {

    companion object {
        val TAG = "LogsFragment"
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_logs, container, false)
        return view
    }
}
