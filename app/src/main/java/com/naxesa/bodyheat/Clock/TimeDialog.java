package com.naxesa.bodyheat.Clock;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TimePicker;

import com.naxesa.bodyheat.Clock.Listener.TimeDialogEventListener;
import com.naxesa.bodyheat.R;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Lee young teak on 2016-09-22.
 */

public class TimeDialog extends Dialog {

    // Const
    private final String TAG = "TimeDialog";

    // Views
    private TimePicker timePicker;
    private Button btnOk, btnCancel;

    // Date Option
    private GregorianCalendar gregorianCalendar;

    // Listener
    TimeDialogEventListener timeDialogEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.dialog_time);
        setLayout();
        setClickListener();
    }

    public TimeDialog(Context context, TimeDialogEventListener timeDialogEventListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.timeDialogEventListener = timeDialogEventListener;
    }

    private void setLayout() {
        // View Reference
        timePicker = (TimePicker) findViewById(R.id.time_picker);
        btnOk = (Button) findViewById(R.id.ok);
        btnCancel = (Button) findViewById(R.id.cancel);

        // current time
        gregorianCalendar = new GregorianCalendar();

        // View Setting
        timePicker.setCurrentHour(gregorianCalendar.get(Calendar.HOUR_OF_DAY));
        timePicker.setCurrentMinute(gregorianCalendar.get(Calendar.MINUTE));
        timePicker.setIs24HourView(Boolean.TRUE);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                gregorianCalendar.set(gregorianCalendar.get(Calendar.YEAR), gregorianCalendar.get(Calendar.MONTH), gregorianCalendar.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
                Log.d(TAG, gregorianCalendar.getTime().toString());
            }
        });
    }

    private void setClickListener() {
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeDialogEventListener.TimeDialogEvent(getGregorianCalendar());
                dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
    }
    
    public GregorianCalendar getGregorianCalendar() {
        return gregorianCalendar;
    }
}
