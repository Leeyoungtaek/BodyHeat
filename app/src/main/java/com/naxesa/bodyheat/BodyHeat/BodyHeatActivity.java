package com.naxesa.bodyheat.BodyHeat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.naxesa.bodyheat.BodyHeat.Map.MapActivity;
import com.naxesa.bodyheat.NewsFeed.NewsFeedDatabaseOpenHelper;
import com.naxesa.bodyheat.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by DSM_055 on 2016-09-21.
 */

public class BodyHeatActivity extends Activity implements View.OnClickListener{

    // Views
    private CheckBox option1, option2, option3, option4, option5;
    private Button btnCheckBodyheat, btnOK;
    private TextView bodyHeat;

    // Data
    private double bodyHeatValue = 0;

    // Database
    private SQLiteDatabase db;
    private NewsFeedDatabaseOpenHelper helper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_heat);

        // View Reference
        option1 = (CheckBox)findViewById(R.id.option1);
        option2 = (CheckBox)findViewById(R.id.option2);
        option3 = (CheckBox)findViewById(R.id.option3);
        option4 = (CheckBox)findViewById(R.id.option4);
        option5 = (CheckBox)findViewById(R.id.option5);
        btnCheckBodyheat = (Button)findViewById(R.id.check_body_heat);
        btnOK = (Button)findViewById(R.id.OK);
        bodyHeat = (TextView)findViewById(R.id.body_heat);

        // View Event
        btnCheckBodyheat.setOnClickListener(this);
        btnOK.setOnClickListener(this);

        helper = new NewsFeedDatabaseOpenHelper(this, "news_feed.db", null, 1);
    }

    private boolean checkCheckBox(){
        boolean isCheck = false;
        if(option1.isChecked()){
            isCheck = true;
        } else if(option2.isChecked()){
            isCheck = true;
        } else if(option3.isChecked()){
            isCheck = true;
        } else if(option4.isChecked()){
            isCheck = true;
        } else if(option5.isChecked()){
            isCheck = true;
        } else{
            isCheck = false;
        }
        return isCheck;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.check_body_heat:
                bodyHeatValue = 37.6;
                addNewsFeed(bodyHeatValue);
                bodyHeat.setText(String.valueOf(bodyHeatValue));
                break;
            case R.id.OK:
                String kind;
                if(bodyHeatValue==0){
                    Toast.makeText(this, "체온을 측정해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                } else if(!checkCheckBox()){
                    Toast.makeText(this, "증상을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(bodyHeatValue>=35.8 && bodyHeatValue<=37.5){
                    kind = "정상체온입니다.";
                    Toast.makeText(this, "정상체온입니다.", Toast.LENGTH_SHORT).show();
                    finish();
                } else if(bodyHeatValue>=37.6 && bodyHeatValue<=38){
                    kind = "미열입니다.";
                    OpenDialog(kind);
                } else if(bodyHeatValue>=38.1){
                    kind = "고열입니다.";
                    OpenDialog(kind);
                } else{
                    Toast.makeText(this, "체온을 다시 측정해주세요.", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
    private void OpenDialog(String content){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(content + " 가까운 병원을 찾으시겠습니까?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(BodyHeatActivity.this, "Yes", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(intent);
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(BodyHeatActivity.this, "No", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.setTitle(null);
        alert.setIcon(R.drawable.ic_add_location_white_24dp);
        alert.show();
    }
    private void addNewsFeed(double bodyHeatValue){
        db = helper.getWritableDatabase();

        Date from = new Date();
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = transFormat.format(from);

        String content = "체온 측정 : " + bodyHeatValue + "도";

        ContentValues values = new ContentValues();
        values.put("date", date);
        values.put("content", content);
        db.insert("news_feed", null, values);
    }
}
