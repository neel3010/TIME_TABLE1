package com.example.timetableandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Profile_view extends AppCompatActivity implements JsonResponse{

    GridView g1;

//    Button b1;

    SharedPreferences sh;


    String[] student_name, course_name, department_name, dob, gender, place, post, pincode, district, phone, photo, value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        g1 = (GridView) findViewById(R.id.profileview);

//        b1 = (Button) findViewById(R.id.edit);

            JsonReq JR = new JsonReq();
            JR.json_response = (JsonResponse) Profile_view.this;
            String q = "/viewprofile?log_id=" + sh.getString("log_id", "");
            q = q.replace(" ", "%20");
            JR.execute(q);

    }

//    @Override
//    public void response(JSONObject jo) {
//
//    }

    @Override
    public void response(JSONObject jo) {
        try {

            String status = jo.getString("status");
            Log.d("pearl", status);

            if (status.equalsIgnoreCase("success")) {

                Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_LONG).show();

                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                student_name = new String[ja1.length()];
                course_name = new String[ja1.length()];
                department_name = new String[ja1.length()];
                dob = new String[ja1.length()];
                gender = new String[ja1.length()];
                place = new String[ja1.length()];
                post = new String[ja1.length()];
                pincode = new String[ja1.length()];
                district = new String[ja1.length()];
                phone = new String[ja1.length()];
                photo = new String[ja1.length()];

                value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    student_name[i] = ja1.getJSONObject(i).getString("student_name");
                    course_name[i] = ja1.getJSONObject(i).getString("course_name");
                    department_name[i] = ja1.getJSONObject(i).getString("department_name");
                    dob[i] = ja1.getJSONObject(i).getString("dob");
                    gender[i] = ja1.getJSONObject(i).getString("gender");
                    place[i] = ja1.getJSONObject(i).getString("place");
                    post[i] = ja1.getJSONObject(i).getString("post");
                    pincode[i] = ja1.getJSONObject(i).getString("pincode");
                    district[i] = ja1.getJSONObject(i).getString("district");
                    phone[i] = ja1.getJSONObject(i).getString("phone_number");
                    photo[i] = ja1.getJSONObject(i).getString("photo");

                    value[i] = "NAME : " + student_name[i] + "\nCOURSE : " + course_name[i] + "\nDEPARTMENT : " + department_name[i]+"\n";
                }
//                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
//                g1.setAdapter(ar);
                 profilepiccoustme p1=new profilepiccoustme(Profile_view.this, student_name, course_name, department_name, dob, gender, place, post, pincode, district, phone, photo);

                g1.setAdapter(p1);
            }


        } catch (Exception e) {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }
}