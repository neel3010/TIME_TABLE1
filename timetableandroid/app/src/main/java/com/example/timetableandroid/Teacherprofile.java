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

public class Teacherprofile extends AppCompatActivity implements JsonResponse{

    GridView g1;

//    Button b1;

    SharedPreferences sh;


    String[] staff_name, place, district, qualification, image, email, phone,  value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        g1 = (GridView) findViewById(R.id.profileview);

//        b1 = (Button) findViewById(R.id.edit);

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Teacherprofile.this;
        String q = "/teacher_viewprofile?log_id=" + sh.getString("log_id", "");
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
                staff_name = new String[ja1.length()];
                place = new String[ja1.length()];
                district = new String[ja1.length()];
                qualification = new String[ja1.length()];
                image = new String[ja1.length()];
                email = new String[ja1.length()];
                phone = new String[ja1.length()];


                value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    staff_name[i] = ja1.getJSONObject(i).getString("staff_name");
                    place[i] = ja1.getJSONObject(i).getString("place");
                    district[i] = ja1.getJSONObject(i).getString("district");
                    qualification[i] = ja1.getJSONObject(i).getString("qualification");
                    image[i] = ja1.getJSONObject(i).getString("image");
                    email[i] = ja1.getJSONObject(i).getString("email");
                    phone[i] = ja1.getJSONObject(i).getString("phone");


//                    value[i] = "NAME : " + student_name[i] + "\nCOURSE : " + course_name[i] + "\nDEPARTMENT : " + department_name[i]+"\n";
                }
//                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
//                g1.setAdapter(ar);
                Tprofilepiccoustme p1=new Tprofilepiccoustme(Teacherprofile.this, staff_name, place, district, qualification, image, email,phone);

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