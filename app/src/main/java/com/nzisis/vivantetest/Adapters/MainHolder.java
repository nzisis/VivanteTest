package com.nzisis.vivantetest.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nzisis.vivantetest.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by vromia on 12/26/16.
 */
public class MainHolder  extends RecyclerView.ViewHolder {

    public TextView tvRepositoryName;



    public MainHolder(View itemView) {
        super(itemView);

        tvRepositoryName = (TextView) itemView.findViewById(R.id.tvRepositoryName);

    }
}
