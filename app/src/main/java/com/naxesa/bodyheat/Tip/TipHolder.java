package com.naxesa.bodyheat.Tip;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.naxesa.bodyheat.R;

/**
 * Created by Lee young teak on 2016-09-23.
 */

public class TipHolder extends RecyclerView.ViewHolder {

    TextView tipName;
    CardView cardView;

    public TipHolder(View itemView) {
        super(itemView);
        tipName = (TextView)itemView.findViewById(R.id.tip_name);
        cardView = (CardView)itemView.findViewById(R.id.card_view);
    }
}
