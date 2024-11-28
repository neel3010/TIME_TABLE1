package com.example.timetableandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Markview extends AppCompatActivity   implements  JsonResponse{

    ListView l1;

    SharedPreferences sh;

    String[] student_name,teacher_name,mark,date_time,subject_name, value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markview);

        l1 = (ListView) findViewById(R.id.listmark);

        // Initialize SharedPreferences
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Markview.this;
        String q = "/mark_vew?log_id=" + sh.getString("log_id", "");
        q = q.replace(" ", "%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");
            Log.d("pearl", status);

            if (status.equalsIgnoreCase("success")) {
                Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_LONG).show();

                JSONArray ja1 = jo.getJSONArray("data");
                student_name = new String[ja1.length()];
                teacher_name = new String[ja1.length()];
                mark = new String[ja1.length()];
                date_time = new String[ja1.length()];
                subject_name = new String[ja1.length()];
                value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    student_name[i] = ja1.getJSONObject(i).getString("student_name");
                    teacher_name[i] = ja1.getJSONObject(i).getString("staff_name");
                    subject_name[i] = ja1.getJSONObject(i).getString("subject_name");
                    mark[i] = ja1.getJSONObject(i).getString("mark");
                    date_time[i] = ja1.getJSONObject(i).getString("date_time");

                    value[i] = "NAME : " + student_name[i] + "\nTEACHER : " + teacher_name[i] + "\nSUBJECT : " + subject_name[i] + "\nMARK : "+ mark[i] +  "\nDateTime : " + date_time[i];
                }

                ArrayAdapter<String> ar = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                l1.setAdapter(ar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }
}