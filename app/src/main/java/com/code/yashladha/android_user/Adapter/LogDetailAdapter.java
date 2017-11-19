package com.code.yashladha.android_user.Adapter;

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

import com.code.yashladha.android_user.Models.OrderPlaced;
import com.code.yashladha.android_user.Models.ProductSold;
import com.code.yashladha.android_user.R;

import java.util.ArrayList;

/**
 * Created by User on 10/24/2017.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jaya on 22-10-2017.
 */

public class LogDetailAdapter extends ArrayAdapter<ProductSold> {

    private int mColorResourceId;

    public LogDetailAdapter(Activity context, ArrayList<ProductSold> orders, int colorResourceId) {
        super(context, 0, orders);
        mColorResourceId = colorResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.sold_item, parent, false);
        }
        final int pos = position;
        final ProductSold currentOrder = getItem(position);

        TextView mProductName = (TextView) listItemView.findViewById(R.id.tvProductName);
        mProductName.setText(currentOrder.getP_name());

        TextView mProductPrice = (TextView) listItemView.findViewById(R.id.tvPrice);
        mProductPrice.setText(currentOrder.getPrice());

        TextView mTypeOfRequest = (TextView) listItemView.findViewById(R.id.tvTypeOfRequest);
        mTypeOfRequest.setText(currentOrder.getStatus());

        ImageView mProductImageResource = (ImageView) listItemView.findViewById(R.id.ivProduct);
        if (currentOrder.hasImage()) {
            mProductImageResource.setImageResource(currentOrder.getImage_id());
            mProductImageResource.setVisibility(View.VISIBLE);
        } else {
            mProductImageResource.setVisibility(View.GONE);
        }

        View textContainer = listItemView.findViewById(R.id.linearLayout1);
        int color = ContextCompat.getColor(getContext(), mColorResourceId);
        textContainer.setBackgroundColor(color);

        return listItemView;
        //return super.getView(position, convertView, parent);
    }

}
