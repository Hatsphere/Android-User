package com.code.yashladha.android_user.Portal.Adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.code.yashladha.android_user.Models.ProductSold;
import com.code.yashladha.android_user.R;

import java.util.ArrayList;

/**
 * Created by User on 10/24/2017.
 */

public class LogsAdapter extends ArrayAdapter<ProductSold> {

    public LogsAdapter(Context context, ArrayList<ProductSold> soldItems, int resource) {
        super(context, 0, soldItems);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View pro_list_view = convertView;

        if (pro_list_view == null) {
            pro_list_view = LayoutInflater.from(getContext()).inflate(
                    R.layout.sold_item, parent, false);
        }
        ProductSold currentPro = getItem(position);

        TextView nameTextView = (TextView) pro_list_view.findViewById(R.id.pname);
        nameTextView.setText(currentPro.getP_name());

        TextView pDateTextView = (TextView) pro_list_view.findViewById(R.id.purchase_date);
        pDateTextView.setText(currentPro.getDate());

        TextView priceTextView = (TextView) pro_list_view.findViewById(R.id.price);
        priceTextView.setText(currentPro.getPrice());

        TextView dDateTextView = (TextView) pro_list_view.findViewById(R.id.deliver_date);

        ImageView iconView = (ImageView) pro_list_view.findViewById(R.id.image);

        iconView.setVisibility(View.VISIBLE);

    }
}
