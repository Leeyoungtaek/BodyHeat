package com.naxesa.bodyheat.Clock;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.naxesa.bodyheat.Clock.Listener.DeleteClockEventListener;
import com.naxesa.bodyheat.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Lee young teak on 2016-09-23.
 */

public class RecyclerViewClockAdapter extends RecyclerView.Adapter {

    private ArrayList<String> names;
    private Context context;
    private DeleteClockEventListener deleteClockEventListener;

    RecyclerViewClockAdapter(Context context, ArrayList<String> names, DeleteClockEventListener deleteClockEventListener){
        this.names = names;
        this.context = context;
        this.deleteClockEventListener = deleteClockEventListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clock, parent, false);
        return new ClockHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int finalPosition = position;
        ((ClockHolder)holder).name.setText(names.get(position));
        ((ClockHolder)holder).btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteClockEventListener.DeleteClockEvent(finalPosition);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return names.size();
    }
}
