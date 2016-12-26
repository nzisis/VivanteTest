package com.nzisis.vivantetest.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nzisis.vivantetest.Model.Repository;
import com.nzisis.vivantetest.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by vromia on 12/26/16.
 */
public class MainAdapter extends RecyclerView.Adapter<MainHolder> {

    private ArrayList<Repository> repositories;
    private Context context;

    public MainAdapter(ArrayList<Repository> items, Context context) {
        this.repositories = items;
        this.context = context;
    }

    @Override
    public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.main_item, parent, false);

        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(MainHolder holder, int position) {
//        LeaderBoardItem item = items.get(position);
//
//        holder.tvName.setText(item.getName());
//        holder.tvPoints.setText(item.getPoints() + "");
//        holder.tvRank.setText(item.getRank() + "");
//
//
//        Picasso.with(context).load(item.getAvatar_url()).into(holder.ivAvatar);
//
//        if(item.getStar().equals("/assets/images/bronze_star.png")) holder.ivStar.setImageResource(R.mipmap.bronze);
//        else if(item.getStar().equals("/assets/images/silver_star.png")) holder.ivStar.setImageResource(R.mipmap.silver);
//        else if(item.getStar().equals("/assets/images/gold_star.png")) holder.ivStar.setImageResource(R.mipmap.gold);


    }

    @Override
    public int getItemCount() {
        return repositories.size();
    }

    public void notify(ArrayList<Repository> items) {
        this.repositories = items;
        notifyDataSetChanged();
    }

}