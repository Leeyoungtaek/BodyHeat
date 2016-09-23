package com.naxesa.bodyheat.Clock;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.naxesa.bodyheat.R;

/**
 * Created by Lee young teak on 2016-09-23.
 */

public class ClockHolder extends RecyclerView.ViewHolder {

    TextView name;
    ImageButton btnDelete;

    public ClockHolder(View itemView) {
        super(itemView);
        name = (TextView)itemView.findViewById(R.id.name);
        btnDelete = (ImageButton)itemView.findViewById(R.id.delete);
    }
}
