package com.naxesa.bodyheat.Tip;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.naxesa.bodyheat.R;
import com.naxesa.bodyheat.Tip.Listener.GoToContentEventListener;

import java.util.ArrayList;

/**
 * Created by DSM_055 on 2016-09-21.
 */

public class TipActivity extends Activity {

    // RecyclerView
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private RecyclerViewTipAdapter adapter;

    // Data
    private ArrayList<String> tips;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);

        tips = new ArrayList<String>();
        tips.add("'열'의 가장 흔한 원인은 '감염'");
        tips.add("감기증상에 좋은음식과 나쁜음식");
        tips.add("감기 걸렸을 때 푹 쉬는 방법");

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(TipActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewTipAdapter(TipActivity.this, tips, new GoToContentEventListener() {
            @Override
            public void goToContentEvent(int position) {
                Intent intent = new Intent(getApplicationContext(), TipContentActivity.class);
                intent.putExtra("title", tips.get(position));
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }
}
