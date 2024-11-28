package com.example.timetableandroid;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class student_home extends Activity {

    ImageView img1,img2,img3,img4,img5,img6,img7,img8,img9,img10;
    SharedPreferences sh;
    TextView t1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
//        startService(new Intent(getApplicationContext(),Notification_Alert.class));
//        startService(new Intent(getApplicationContext(), LocationService.class));

        sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        img1=(ImageView) findViewById(R.id.imgprofile);
        img1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                startActivity(new Intent(getApplicationContext(),Profile_view.class));

//                }
            }
        });
        img2=(ImageView) findViewById(R.id.imgattendnace);
        img2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                startActivity(new Intent(getApplicationContext(),Attendance.class));

//                }
            }
        });
        img3=(ImageView) findViewById(R.id.imglogout);
        img3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                startActivity(new Intent(getApplicationContext(),Login.class));

//                }
            }
        });
        img4=(ImageView) findViewById(R.id.imgcemarkk);
        img4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                    startActivity(new Intent(getApplicationContext(),Markview.class));

//                }
            }
        });
        img5=(ImageView) findViewById(R.id.imgenquiry);
        img5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                startActivity(new Intent(getApplicationContext(),Complaint.class));

//                }
            }
        });

//        t1=(TextView) findViewById(R.id.textView4);
//        t1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(),Web_view_timetable.class));
//            }
//        });

//

    }
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), student_home.class));
    }
}