package com.code.yashladha.android_user.Portal.Fragments

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.code.yashladha.android_user.Models.Logs
import com.code.yashladha.android_user.Portal.Adapter.LogsAdapter
import com.code.yashladha.android_user.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_logs.view.*

/**
 * Created by yashladha on 17/10/17.
 */

class LogsFragment: Fragment() {

    private lateinit var logsMainRv: RecyclerView
    private lateinit var firestore: FirebaseFirestore
    private lateinit var userId: String
    private lateinit var logAdapter: LogsAdapter
    private lateinit var logsList: ArrayList<Logs>

    companion object {
        val TAG = "LogsFragment"
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_logs, container, false)
        val lm = LinearLayoutManager(view.context)

        logsList = ArrayList()
        userId = FirebaseAuth.getInstance().currentUser!!.uid
        firestore = FirebaseFirestore.getInstance()

        logsMainRv = view.logs_main_rv
        logsMainRv.layoutManager = lm
        logsMainRv.setHasFixedSize(true)
        logAdapter = LogsAdapter(logsList = logsList, context = view.context)
        logsMainRv.adapter = logAdapter

        val orderRef = firestore.collection(userId + "/orders/waiting")

        firestore.collection(userId + "/logs/orderPlaced")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val result = task.result
                        for (item in result) {
                            val timeStamp = item.id
                            val query = orderRef.whereEqualTo("order_date", timeStamp)

                            query.get().addOnCompleteListener { task2 ->
                                if (task2.isSuccessful) {
                                    val queryRes = task2.result
                                    val logItem = Logs(timeStamp, queryRes.size())
                                    for (snapShots in queryRes) {
                                        logItem.addItem(snapShots.get("productName").toString())
                                        logItem.addSeller(snapShots.get("sellerId").toString())
                                    }
                                    logsList.add(logItem)
                                }
                                logAdapter.notifyDataSetChanged()
                            }
                        }
                    }
                }

        return view
    }
}
