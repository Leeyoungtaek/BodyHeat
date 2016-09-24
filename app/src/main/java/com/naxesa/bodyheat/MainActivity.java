package com.naxesa.bodyheat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.naxesa.bodyheat.BodyHeat.BodyHeatActivity;
import com.naxesa.bodyheat.Clock.ClockActivity;
import com.naxesa.bodyheat.Tip.TipActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Views
    private Button btnBodyHeat, btnClock, btnNewsFeed, btnTip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // View Reference
        btnBodyHeat = (Button)findViewById(R.id.body_heat);
        btnClock = (Button)findViewById(R.id.clock);
        btnNewsFeed = (Button)findViewById(R.id.news_feed);
        btnTip = (Button)findViewById(R.id.tip);

        // View Event
        btnBodyHeat.setOnClickListener(this);
        btnClock.setOnClickListener(this);
        btnNewsFeed.setOnClickListener(this);
        btnTip.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.body_heat:
                intent = new Intent(getApplicationContext(), BodyHeatActivity.class);
                break;
            case R.id.clock:
                intent = new Intent(getApplicationContext(), ClockActivity.class);
                break;
            case R.id.news_feed:
                intent = new Intent(getApplicationContext(), NewsFeedActivity.class);
                break;
            case R.id.tip:
                intent = new Intent(getApplicationContext(), TipActivity.class);
                break;
            default:
                break;
        }
        startActivity(intent);
    }
}
