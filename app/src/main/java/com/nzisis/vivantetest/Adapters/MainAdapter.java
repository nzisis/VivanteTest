package com.nzisis.vivantetest.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nzisis.vivantetest.Listeners.MainListener;
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
    private MainListener listener;

    public MainAdapter(ArrayList<Repository> items, Context context,MainListener listener) {
        this.repositories = items;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.main_item, parent, false);

        return new MainHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(MainHolder holder, int position) {

        Repository item  = repositories.get(position);

        holder.tvRepositoryName.setText(item.getName());


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