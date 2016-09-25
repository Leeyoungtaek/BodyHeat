package com.naxesa.bodyheat.NewsFeed;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.naxesa.bodyheat.R;

import java.util.ArrayList;

/**
 * Created by Lee young teak on 2016-09-25.
 */

public class RecyclerViewNewsFeedAdapter extends RecyclerView.Adapter {

    private ArrayList<String> dates, contents;
    private Context context;

    RecyclerViewNewsFeedAdapter(Context context, ArrayList<String> dates, ArrayList<String> contents){
        this.context = context;
        this.dates = dates;
        this.contents = contents;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_feed, parent, false);
        return new NewsFeedHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((NewsFeedHolder)holder).date.setText(dates.get(position));
        ((NewsFeedHolder)holder).content.setText(contents.get(position));
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }
}
