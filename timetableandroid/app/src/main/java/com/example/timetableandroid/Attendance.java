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

public class Attendance extends AppCompatActivity implements JsonResponse {

    ListView l1;

    String[] student_name, course_name, department_name,statusoftheday, datetime, value;

    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        l1 = (ListView) findViewById(R.id.listatt);

        // Initialize SharedPreferences
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Attendance.this;
        String q = "/attendance_view?log_id=" + sh.getString("log_id", "");
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
                course_name = new String[ja1.length()];
                department_name = new String[ja1.length()];
                statusoftheday = new String[ja1.length()];
                datetime = new String[ja1.length()];
                value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    student_name[i] = ja1.getJSONObject(i).getString("student_name");
                    course_name[i] = ja1.getJSONObject(i).getString("course_name");
                    department_name[i] = ja1.getJSONObject(i).getString("department_name");
                    statusoftheday[i] = ja1.getJSONObject(i).getString("status");
                    datetime[i] = ja1.getJSONObject(i).getString("datetime");

                    value[i] = "NAME: " + student_name[i] + "\nCOURSE: " + course_name[i] + "\nDEPARTMENT: " + department_name[i] + "\nSTATUS: " + statusoftheday[i]  + "\nDateTime: " + datetime[i];
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
