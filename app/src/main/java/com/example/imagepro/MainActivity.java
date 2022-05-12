package com.example.imagepro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.opencv.android.OpenCVLoader;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    static {
        if(OpenCVLoader.initDebug()){
            Log.d("MainActivity: ","Opencv is loaded");
        }
        else {
            Log.d("MainActivity: ","Opencv failed to load");
        }
    }


    private String output;
    private Button camera_button;
    private TextView title,day,st_time,ed_time;
    private int MyVersion;
    private String start_time;
    private String end_time;
    private String eventTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        camera_button=findViewById(R.id.camera_button);
        title=findViewById(R.id.title);
        day=findViewById(R.id.day);
        st_time=findViewById(R.id.startTime);
        ed_time=findViewById(R.id.endTime);
        camera_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                MyVersion=BuildConfig.VERSION_CODE;
//                // First check android version
//                if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
////Check if permission is already granted
////thisActivity is your activity. (e.g.: MainActivity.this)
//                    if (ContextCompat.checkSelfPermission(MainActivity.this,
//                            Manifest.permission.CAMERA)
//                            != PackageManager.PERMISSION_GRANTED) {
//
//                        // Give first an explanation, if needed.
//                        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
//                                Manifest.permission.CAMERA)) {
//
//                            // Show an explanation to the user *asynchronously* -- don't block
//                            // this thread waiting for the user's response! After the user
//                            // sees the explanation, try again to request the permission.
//
//                        } else {
//
//                            // No explanation needed, we can request the permission.
//
//                            ActivityCompat.requestPermissions(MainActivity.this,
//                                    new String[]{Manifest.permission.CAMERA},
//                                    1);
//                        }
//                    }
//                }

                Intent intent = new Intent(MainActivity.this,CameraActivity.class);
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                startActivityForResult(intent, 1);
//                Intent intent = new Intent();
//                Log.d("name ",output);
//                 getIntent().getStringExtra("text");


            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                output = data.getStringExtra("text");
                Log.d("name",""+ output);

                String[] arr = output.split("\n");

                for(int i =0;i<arr.length;i++)
                {
                    if(arr[i].contains("MON")){
                        day.setText(arr[i]);
                    }
                    else if(arr[i].contains("9:00")){
                        st_time.setText("9");
                        start_time="9";
                    }
                    else if(arr[i].contains("09:45")){
                        ed_time.setText("45");
                        end_time="45";
                    }
                    else if(arr[i].contains("CSE1008")){
                        title.setText("CSE1008");
                        eventTitle="CSE1008";
                    }
                }
//                title.setText(output);
//                String[]  arr=output.split("\n");
//                day.setText(arr[1]);
//
//                title.setText("" + result);
            }
            if (resultCode == RESULT_CANCELED) {
                output="Nothing selected";
//                Log.d("name",""+ output);
//                title.setText("Nothing selected");
            }
        }
    }

    public  void AddCalendarEvent(View view) {
        Calendar calendarEvent = Calendar.getInstance();
        calendarEvent.set(Calendar.MONTH, 4);
        calendarEvent.set(Calendar.DAY_OF_MONTH, 10);
        calendarEvent.set(Calendar.SECOND, 0);
        //the above values are gonna stay default

        calendarEvent.set(Calendar.HOUR_OF_DAY, Integer.parseInt(start_time));
        calendarEvent.set(Calendar.MINUTE, 0);

//        calendarEvent.se;


        Intent i = new Intent(Intent.ACTION_EDIT);
        i.setType("vnd.android.cursor.item/event");
        i.putExtra("beginTime", calendarEvent.getTimeInMillis()  );

        i.putExtra("allDay", false);

        i.putExtra("rrule", "FREQ=WEEKLY");
        i.putExtra("endTime",  calendarEvent.getTimeInMillis()+Integer.parseInt(end_time)*60*1000);
        i.putExtra(CalendarContract.Events.TITLE, eventTitle);

        startActivity(i);
    }


}