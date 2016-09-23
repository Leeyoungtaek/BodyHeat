package com.naxesa.bodyheat.Clock;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.naxesa.bodyheat.Clock.Listener.DeleteMedicineEventListener;
import com.naxesa.bodyheat.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Lee young teak on 2016-09-22.
 */

public class RecyclerViewMedicineAdapter extends RecyclerView.Adapter {

    private ArrayList<GregorianCalendar> gregorianCalendars;
    private Context context;
    private DeleteMedicineEventListener deleteMedicineEventListener;

    RecyclerViewMedicineAdapter(Context context, ArrayList<GregorianCalendar> gregorianCalendars, DeleteMedicineEventListener deleteMedicineEventListener){
        this.gregorianCalendars = gregorianCalendars;
        this.context = context;
        this.deleteMedicineEventListener = deleteMedicineEventListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medicine, parent, false);
        return new MedicineHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int finalPosition = position;
        GregorianCalendar gregorianCalendar = gregorianCalendars.get(position);
        ((MedicineHolder)holder).date.setText(gregorianCalendar.get(Calendar.HOUR_OF_DAY) + "시 " + gregorianCalendar.get(Calendar.MINUTE) + "분");
        ((MedicineHolder)holder).btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMedicineEventListener.DeleteMedicineEvent(finalPosition);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return gregorianCalendars.size();
    }
}
