package com.example.timetableandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Managemark extends AppCompatActivity implements JsonResponse , AdapterView.OnItemSelectedListener {
    Spinner s1;

    Button b1;
    SharedPreferences sh;

    EditText e1;

    String[] student_id, student_name, value;

    String stud, mark;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managemark);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        s1 = findViewById(R.id.markstudentdrop);

        s1.setOnItemSelectedListener(this);
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Managemark.this;
        String q = "/markstudentdrop?log_id=" + sh.getString("log_id", "");
        q = q.replace(" ", "%20");
        JR.execute(q);


        b1 = (Button) findViewById(R.id.button4);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mark = e1.getText().toString();


                JsonReq JR = new JsonReq();
                JR.json_response = Managemark.this;
                String q = "/cemarkmanage?log_id=" + sh.getString("log_id", "")   + "&s_id=" + stud   + "&mark=" + mark  ;
                q = q.replace(" ", "%20");
                JR.execute(q);
                startActivity(new Intent(getApplicationContext(), Staffhome.class));

            }
        });

        e1 = (EditText) findViewById(R.id.cemark);




    }

    @Override
    public void response(JSONObject jo) {

        try{

            String method = jo.getString("method");
            String status = jo.getString("status");
            Log.d("pearl", method);

            if (method.equalsIgnoreCase("markstud")){

                Log.d("result", status);
                Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
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

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stud = student_id[position];
                Toast.makeText(getApplicationContext(), "stud :" +stud, Toast.LENGTH_LONG).show();


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
