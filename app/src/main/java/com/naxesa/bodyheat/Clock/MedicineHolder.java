package com.naxesa.bodyheat.Clock;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.naxesa.bodyheat.R;

/**
 * Created by Lee young teak on 2016-09-22.
 */

public class MedicineHolder extends RecyclerView.ViewHolder {

    TextView date;
    ImageButton btnDelete;

    public MedicineHolder(View itemView) {
        super(itemView);
        date = (TextView)itemView.findViewById(R.id.date);
        btnDelete = (ImageButton)itemView.findViewById(R.id.delete);
    }
}
