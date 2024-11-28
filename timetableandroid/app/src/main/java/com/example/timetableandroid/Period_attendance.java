package com.example.timetableandroid;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class Period_attendance extends AppCompatActivity implements JsonResponse, AdapterView.OnItemSelectedListener {

    Spinner s1, s2;
    Button b1;

    private Switch switchExample;
    private String status = "absent";
    SharedPreferences sh;

    String[] student_id, student_name,p_id,periods, value,value1;
    String selectedDistrict, period,stud,pid;
    RadioGroup g1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_period_attendance);

//        switchExample = findViewById(R.id.click);
//        switchExample.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    // Switch is ON
//                    status = "present"; // Set the variable to "present"
//                    Toast.makeText(getApplicationContext(), "present", Toast.LENGTH_LONG).show();
//
//                } else {
//                    // Switch is OFF
//                    status = "absent"; // Set the variable to "absent"
//                    Toast.makeText(getApplicationContext(), "absent", Toast.LENGTH_LONG).show();
//
//
//                }
//            }
//        });


        g1=findViewById(R.id.grp);
        g1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb=findViewById(checkedId);
                status=rb.getText().toString();
            }
        });

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        s1 = findViewById(R.id.studentdrop);





        s1.setOnItemSelectedListener(this);

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Period_attendance.this;
        String q = "/studentdrop?log_id=" + sh.getString("log_id", "");
        q = q.replace(" ", "%20");
        JR.execute(q);

        s2 = findViewById(R.id.perioddrop);
        s2.setOnItemSelectedListener(this);
        JR = new JsonReq();
        JR.json_response = (JsonResponse) Period_attendance.this;
        q = "/perioddrop";
        q = q.replace(" ", "%20");
        JR.execute(q);

        b1 = findViewById(R.id.button2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonReq JR = new JsonReq();
                JR.json_response = Period_attendance.this;
                String q = "/attendacemark?log_id=" + sh.getString("log_id", "") + "&period=" + pid + "&s_id=" + stud + "&status=" +status ;
                q = q.replace(" ", "%20");
                JR.execute(q);
                startActivity(new Intent(getApplicationContext(), Staffhome.class));
            }
        });




    }

    @Override
    public void response(JSONObject jo) {

        try {
            String method = jo.getString("method");
            String status = jo.getString("status");
            Log.d("pearl", method);

            if (method.equalsIgnoreCase("viewstud")) {
                Log.d("result", status);
//                Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
                if (status.equalsIgnoreCase("success")) {
                    JSONArray ja = jo.getJSONArray("data");

                    student_id = new String[ja.length()];
                    student_name = new String[ja.length()];
                    value = new String[ja.length()];

                    for (int i = 0; i < ja.length(); i++) {
                        student_id[i] = ja.getJSONObject(i).getString("student_id");
                        student_name[i] = ja.getJSONObject(i).getString("student_name");
                        value[i] = student_name[i];
                    }

                    ArrayAdapter<String> ar = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                    s1.setAdapter(ar);
                } else {
                    Toast.makeText(getApplicationContext(), "No messages", Toast.LENGTH_LONG).show();
                }
            }

            if (method.equalsIgnoreCase("sennt")) {
                Log.d("result", status);
//                Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
                if (status.equalsIgnoreCase("success")) {
                    JSONArray ja = jo.getJSONArray("data");

                    student_id = new String[ja.length()];
                    student_name = new String[ja.length()];
                    value = new String[ja.length()];

                    for (int i = 0; i < ja.length(); i++) {
                        student_id[i] = ja.getJSONObject(i).getString("student_id");
                        student_name[i] = ja.getJSONObject(i).getString("student_name");
                        value[i] = student_name[i];
                    }

                    ArrayAdapter<String> ar = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                    s1.setAdapter(ar);


                } else {
                    Toast.makeText(getApplicationContext(), "No messages", Toast.LENGTH_LONG).show();
                }
            }

            if (method.equalsIgnoreCase("viewperiod")){
                Log.d("result", status);
//                Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
                if (status.equalsIgnoreCase("success")) {
                    JSONArray ja = jo.getJSONArray("data");

                    p_id = new String[ja.length()];
                    periods = new String[ja.length()];
                    value1 = new String[ja.length()];

                    for (int i = 0; i < ja.length(); i++) {
                        p_id[i] = ja.getJSONObject(i).getString("p_id");
                        periods[i] = ja.getJSONObject(i).getString("periods");
                        value1[i] = periods[i];
                    }

                    ArrayAdapter<String> ar = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, value1);
                    s2.setAdapter(ar);
//                    Toast.makeText(getApplicationContext(), ""+s2, Toast.LENGTH_LONG).show();
                    s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            period = periods[position];
                            pid= p_id[position];

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                } else {
                    Toast.makeText(getApplicationContext(), "No messages", Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
//            Toast.makeText(getApplicationContext(), "exp : " + e, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

//    @Override
//    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//    }

//    @Override
//    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//
//
//    }

      @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        stud = student_id[position];


//        Toast.makeText(getApplicationContext(), "id : " + stud, Toast.LENGTH_LONG).show() ;

    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Handle nothing selected in the spinner
    }
}
