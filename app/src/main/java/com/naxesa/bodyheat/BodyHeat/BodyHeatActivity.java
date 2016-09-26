package com.naxesa.bodyheat.BodyHeat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
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

    // Location
    LocationManager locationManager;
    android.location.LocationListener locationListener;
    double Latitude, Longitude;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_heat);

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locationListener = new android.location.LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Latitude = location.getLatitude();
                Longitude = location.getLongitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

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

        //GPS security exception
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION))
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 3);
            else
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 3);
        }

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
//                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION))
                        ActivityCompat.requestPermissions((Activity) getApplicationContext(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 2);
                    else
                        ActivityCompat.requestPermissions((Activity) getApplicationContext(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 2);
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 0, (android.location.LocationListener) locationListener);
                Uri gmmIntentUri = Uri.parse("geo:" + Latitude + ", " + Longitude + "?q=" + Uri.encode("이비인후과"));
                Intent intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
                finish();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(BodyHeatActivity.this, "No", Toast.LENGTH_SHORT).show();
                dialog.cancel();
                finish();
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
        values.put("state", "body_heat");
        values.put("date", date);
        values.put("content", content);
        db.insert("news_feed", null, values);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //GPS security exception
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION))
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 2);
            else
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 2);
        }
        locationManager.removeUpdates((android.location.LocationListener) locationListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 3:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                }else{
                    finish();
                }
                break;
            default:
                Toast.makeText(this, "권한을 설정해주세요.", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }
}
