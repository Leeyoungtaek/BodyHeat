package com.naxesa.bodyheat.Tip;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.naxesa.bodyheat.Clock.ClockHolder;
import com.naxesa.bodyheat.Clock.Listener.DeleteClockEventListener;
import com.naxesa.bodyheat.R;
import com.naxesa.bodyheat.Tip.Listener.GoToContentEventListener;

import java.util.ArrayList;

/**
 * Created by Lee young teak on 2016-09-23.
 */

public class RecyclerViewTipAdapter extends RecyclerView.Adapter {

    private ArrayList<String> tips;
    private Context context;
    private GoToContentEventListener listener;

    RecyclerViewTipAdapter(Context context, ArrayList<String> tips, GoToContentEventListener listener){
        this.tips = tips;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tip, parent, false);
        return new TipHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int finalPosition = position;
        ((TipHolder)holder).tipName.setText(tips.get(position));
        ((TipHolder)holder).cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.goToContentEvent(finalPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tips.size();
    }
}
