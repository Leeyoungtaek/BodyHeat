package com.naxesa.bodyheat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by DSM_055 on 2016-09-21.
 */

public class ClockActivity extends Activity implements View.OnClickListener{

    // Views
    private Button btnAdd;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);

        // View Reference
        btnAdd = (Button)findViewById(R.id.add);

        // View Event
        btnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.add:
                intent = new Intent(getApplicationContext(), AddClockActivity.class);
                break;
            default:
                break;
        }
        startActivity(intent);
    }
}
