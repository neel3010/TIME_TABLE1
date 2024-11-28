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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Complaint extends AppCompatActivity  implements JsonResponse {
    EditText t1;
    Button b1;
    ListView l1;
    String complaints;
    SharedPreferences sh;
    String [] s_id,comp,rply,date,val;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);


        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        t1 = (EditText) findViewById(R.id.complaint);
        b1 = (Button) findViewById(R.id.cbtn);
        l1 =(ListView) findViewById(R.id.view);
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Complaint.this;
        String q = "/viewcomp?log_id=" + sh.getString("log_id", "");
        q = q.replace(" ", "%20");
        JR.execute(q);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                complaints = t1.getText().toString();

                JsonReq JR = new JsonReq();
                JR.json_response = (JsonResponse) Complaint.this;
                String q = "/User_Complaint?log_id=" + sh.getString("log_id", "") + "&complaints=" + complaints ;
                q = q.replace(" ", "%20");
                JR.execute(q);
            }
        });
    }






    @Override
    public void response(JSONObject jo) {

        try {



            String method = jo.getString("method");
            Log.d("result", method);
//               Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();

            if (method.equalsIgnoreCase("User_Complaint")){
                String status = jo.getString("status");
                if (status.equalsIgnoreCase("success")) {


                    Toast.makeText(getApplicationContext(), "Send Successfull", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),Complaint.class));
                    //startService(new Intent(getApplicationContext(),Notiservise.class));
                }
//

            }
           else if (method.equalsIgnoreCase("viewcomp")) {
               String status = jo.getString("status");
               Log.d("result", status);
               Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
               if (status.equalsIgnoreCase("success")) {

                    JSONArray ja1 = jo.getJSONArray("data");
                    s_id = new String[ja1.length()];
                    comp = new String[ja1.length()];
                    rply = new String[ja1.length()];
                    date = new String[ja1.length()];
                    val = new String[ja1.length()];

                    for (int i = 0; i < ja1.length(); i++) {
                        s_id[i] = ja1.getJSONObject(i).getString("student_name");
                        comp[i] = ja1.getJSONObject(i).getString("complaint");
                        rply[i] = ja1.getJSONObject(i).getString("reply");
                        date[i] = ja1.getJSONObject(i).getString("datetime");

                        val[i] = "NAME: " + s_id[i] + "\nCOMPLAINT: " + comp[i] + "\nREPLY: " + rply[i]  + "\nDateTime: " + date[i];
                    }

                    ArrayAdapter<String> ar = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, val);
                    l1.setAdapter(ar);


                }

                    }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b = new Intent(getApplicationContext(), student_home.class);
        startActivity(b);
    }
}



