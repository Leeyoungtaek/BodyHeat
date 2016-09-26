package com.naxesa.bodyheat.NewsFeed;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.naxesa.bodyheat.R;

/**
 * Created by Lee young teak on 2016-09-25.
 */

public class NewsFeedHolder extends RecyclerView.ViewHolder {

    TextView date, content;
    ImageView image;

    public NewsFeedHolder(View itemView) {
        super(itemView);
        image = (ImageView)itemView.findViewById(R.id.image);
        date = (TextView)itemView.findViewById(R.id.date);
        content = (TextView)itemView.findViewById(R.id.content);
    }
}
