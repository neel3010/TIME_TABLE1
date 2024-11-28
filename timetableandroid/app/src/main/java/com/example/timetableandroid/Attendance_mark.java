package com.example.timetableandroid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Attendance_mark extends AppCompatActivity implements JsonResponse , AdapterView.OnItemClickListener{
    GridView g1;
    SharedPreferences sh;
    String[] stid,student_name, course_name, department_name, dob, gender, place, post, pincode, district, phone, photo, value;
    public  static String stids;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_mark);


            sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            g1 = (GridView) findViewById(R.id.studiew);
            g1.setOnItemClickListener(this);

            JsonReq JR = new JsonReq();
            JR.json_response = (JsonResponse) Attendance_mark.this;
            String q = "/attendancemarkofstud?log_id=" + sh.getString("log_id", "");
            q = q.replace(" ", "%20");
            JR.execute(q);


        }

        @Override
        public void response (JSONObject jo){

            try {

                String status = jo.getString("status");
                Log.d("pearl", status);

                if (status.equalsIgnoreCase("success")) {

//                    Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_LONG).show();

                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                    stid = new String[ja1.length()];
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
                        stid[i] = ja1.getJSONObject(i).getString("student_id");
                        student_name[i] = ja1.getJSONObject(i).getString("student_name");
                        course_name[i] = ja1.getJSONObject(i).getString("course_name");
                        department_name[i] = ja1.getJSONObject(i).getString("department_name");
//                        dob[i] = ja1.getJSONObject(i).getString("dob");
//                        gender[i] = ja1.getJSONObject(i).getString("gender");
//                        place[i] = ja1.getJSONObject(i).getString("place");
//                        post[i] = ja1.getJSONObject(i).getString("post");
//                        pincode[i] = ja1.getJSONObject(i).getString("pincode");
//                        district[i] = ja1.getJSONObject(i).getString("district");
//                        phone[i] = ja1.getJSONObject(i).getString("phone_number");
                        photo[i] = ja1.getJSONObject(i).getString("photo");

                        value[i] = "NAME : " + student_name[i] + "\nCOURSE : " + course_name[i] + "\nDEPARTMENT : " + department_name[i] + "\n";
                    }
//                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
//                g1.setAdapter(ar);
                    Attendance_mark_coustme p1 = new Attendance_mark_coustme(Attendance_mark.this,student_name, course_name, department_name, dob, gender, place, post, pincode, district, phone, photo);

                    g1.setAdapter(p1);
                }


            } catch (Exception e) {
                // TODO: handle exception

                    Toast.makeText(getApplicationContext(), "No Students", Toast.LENGTH_LONG).show();
            }

        }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        final CharSequence[] items = {"Add Mark","Cancel"};
        stids=stid[i];
        SharedPreferences.Editor e=sh.edit();
        e.putString("stids", stids);
        e.commit();

        AlertDialog.Builder builder = new AlertDialog.Builder(Attendance_mark.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {


                if (items[item].equals("Add Mark")) {


                   startActivity(new Intent(getApplicationContext(),Markcalculating.class));
                }

                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }

            }

        });
        builder.show();
    }





        @Override
        public void onItemSelected (AdapterView < ? > parent, View view,int position, long id){

        }
    }
