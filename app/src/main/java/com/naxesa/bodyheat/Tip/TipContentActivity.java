package com.naxesa.bodyheat.Tip;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.naxesa.bodyheat.R;


/**
 * Created by DSM_055 on 2016-09-21.
 */

public class TipContentActivity extends Activity {

    // Views
    private TextView tvTitle;

    // Data
    private String title;
    private int position;
    private Intent intent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intent = getIntent();
        title = intent.getStringExtra("title");
        position = intent.getIntExtra("position", 0);

        switch (position) {
            case 0:
                setContentView(R.layout.activity_tip_content_1);
                break;
            case 1:
                setContentView(R.layout.activity_tip_content_2);
                break;
            case 2:
                setContentView(R.layout.activity_tip_content_3);
                break;
            default:
                setContentView(R.layout.activity_tip_content_1);
                break;
        }

        tvTitle = (TextView) findViewById(R.id.title);
        tvTitle.setText(title);

        switch (position) {
            case 0:
                TextView tvContent = (TextView) findViewById(R.id.content);
                tvContent.setText(TipContent.CONTENT1);
                break;
            case 1:
                TextView tvSymtoms[] = new TextView[4];
                TextView tvGoodFoods[] = new TextView[4];
                TextView tvBadFoods[] = new TextView[4];
                tvSymtoms[0] = (TextView) findViewById(R.id.symptom1);
                tvSymtoms[1] = (TextView) findViewById(R.id.symptom2);
                tvSymtoms[2] = (TextView) findViewById(R.id.symptom3);
                tvSymtoms[3] = (TextView) findViewById(R.id.symptom4);
                tvGoodFoods[0] = (TextView) findViewById(R.id.good_food1);
                tvGoodFoods[1] = (TextView) findViewById(R.id.good_food2);
                tvGoodFoods[2] = (TextView) findViewById(R.id.good_food3);
                tvGoodFoods[3] = (TextView) findViewById(R.id.good_food4);
                tvBadFoods[0] = (TextView) findViewById(R.id.bad_food1);
                tvBadFoods[1] = (TextView) findViewById(R.id.bad_food2);
                tvBadFoods[2] = (TextView) findViewById(R.id.bad_food3);
                tvBadFoods[3] = (TextView) findViewById(R.id.bad_food4);
                for (int i = 0; i < 4; i++) {
                    tvSymtoms[i].setText(TipContent.symtomsName[i]);
                    tvGoodFoods[i].setText(TipContent.goodFoods[i]);
                    tvBadFoods[i].setText(TipContent.badFoods[i]);
                }
                break;
            case 2:
                TextView tvStartContent;
                TextView tvAct[] = new TextView[5];
                TextView tvActContent[] = new TextView[5];
                tvStartContent = (TextView) findViewById(R.id.start_content);
                tvAct[0] = (TextView) findViewById(R.id.act1);
                tvAct[1] = (TextView) findViewById(R.id.act2);
                tvAct[2] = (TextView) findViewById(R.id.act3);
                tvAct[3] = (TextView) findViewById(R.id.act4);
                tvAct[4] = (TextView) findViewById(R.id.act5);
                tvActContent[0] = (TextView) findViewById(R.id.act_content1);
                tvActContent[1] = (TextView) findViewById(R.id.act_content2);
                tvActContent[2] = (TextView) findViewById(R.id.act_content3);
                tvActContent[3] = (TextView) findViewById(R.id.act_content4);
                tvActContent[4] = (TextView) findViewById(R.id.act_content5);
                tvStartContent.setText(TipContent.startContent);
                for (int i = 0; i < 5; i++) {
                    tvAct[i].setText(TipContent.acts[i]);
                    tvActContent[i].setText(TipContent.actContents[i]);
                }
                break;
            default:
                break;
        }
    }
}
