package com.code.yashladha.android_user.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.code.yashladha.android_user.Models.Order;
import com.code.yashladha.android_user.Models.OrderPlaced;
import com.code.yashladha.android_user.Models.ProductSold;
import com.code.yashladha.android_user.Portal.LogsActivity;
import com.code.yashladha.android_user.Portal.LogsDetailFragment;
import com.code.yashladha.android_user.R;

import java.util.ArrayList;
/**
 * Created by User on 10/24/2017.
 */
public class LogsAdapter extends ArrayAdapter<OrderPlaced> {

    public LogsAdapter(Context context, ArrayList<OrderPlaced> soldItems, int resource) {
        super(context, 0, soldItems);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View pro_list_view = convertView;

        final Context mContext = convertView.getContext();

        if (pro_list_view == null) {
            pro_list_view = LayoutInflater.from(getContext()).inflate(
                    R.layout.order_placed, parent, false);
        }
        final OrderPlaced currentPro = getItem(position);

        TextView pDateTextView = (TextView) pro_list_view.findViewById(R.id.purchase_date);
        pDateTextView.setText(currentPro.getOrder_date());

        TextView orderTextView = (TextView) pro_list_view.findViewById(R.id.pname);
        orderTextView.setText(currentPro.getOrder_id());

        TextView costTextView = (TextView) pro_list_view.findViewById(R.id.total_cost);
        costTextView.setText(currentPro.getTotal_cost());

        TextView productsTextView = (TextView) pro_list_view.findViewById(R.id.products);

        productsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(mContext, LogsDetailFragment.class);
                i.putExtra("order_id",currentPro.getOrder_id());
            }
        });

        return pro_list_view;
    }
}