package com.example.timetableandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Staffhome extends AppCompatActivity {


    TextView t1,t2,t3,t4,t5,t6,t7;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staffhome);

        t1 = (TextView) findViewById(R.id.profile);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Teacherprofile.class));

            }
        });
        t2 = (TextView) findViewById(R.id.students);
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Staff_view_stud.class));

            }
        });
        t3 = (TextView) findViewById(R.id.timetable);


        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Web_view_timetable.class));
            }
        });

        t4 = (TextView) findViewById(R.id.periodattendance);
        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Period_attendance_check.class));

            }
        });
        t5 = (TextView) findViewById(R.id.mark);
        t5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Attendance_mark.class));

            }
        });
        t6 = (TextView) findViewById(R.id.cemark);
        t6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Managemark.class));

            }
        });
        t7 = (TextView) findViewById(R.id.logout);
        t7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));

            }
        });




    }
}