package com.example.timetableandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
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

import kotlinx.coroutines.Job;

public class Markcalculating extends AppCompatActivity implements JsonResponse, AdapterView.OnItemSelectedListener {
    Spinner s1;
    SharedPreferences sh;

    String[]  sem,sem_id, value;

    String semester;
    EditText e1,e2,e3;

    Button  b1;

    public static String a;
    String avg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markcalculating);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        s1 = findViewById(R.id.semdrop);

        e1 = findViewById(R.id.twdays);
        e2 = findViewById(R.id.editTextText2);
        e3 = findViewById(R.id.editTextText3);

        b1 = findViewById(R.id.button3);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avg=e3.getText().toString();
                JsonReq JR = new JsonReq();
                JR.json_response = (JsonResponse) Markcalculating.this;
                String q = "/publishattentmark?log_in="+sh.getString("log_id","")+"&stud_id="+sh.getString("stids","")+"&mark="+avg+"&sem="+semester;
                q = q.replace(" ", "%20");
                JR.execute(q);



            }
        });

        // Add a TextWatcher to e0
        e1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Not needed
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Not needed
            }

            @Override
            public void afterTextChanged(Editable editable) {
                updatePlasticAmount();
            }
        });

        updatePlasticAmount();

        s1.setOnItemSelectedListener(this);

        JsonReq JR1 = new JsonReq();
        JR1.json_response = (JsonResponse) Markcalculating.this;
        String q1 = "/std_att?stids="+sh.getString("stids","");
        q1 = q1.replace(" ", "%20");
        JR1.execute(q1);

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Markcalculating.this;
        String q = "/semdrop";
        q = q.replace(" ", "%20");
        JR.execute(q);



    }

    private void updatePlasticAmount() {
        String pqnty = e1.getText().toString().trim();

        // Check if pqnty is not empty
        if (!pqnty.isEmpty()) {
            // Check if the input starts with a dot
            if (pqnty.startsWith(".")) {
                pqnty = "0" + pqnty; // prepend "0" to make it like "0.5"
            }

            // Parse pqnty as a double to support decimal values
            double plasticQuantity = Double.parseDouble(pqnty);

            // Calculate plastic amount (multiply by 5)
//            double plasticAmount = plasticQuantity * 5;
            double average = (Double.parseDouble(a) / plasticQuantity) *100;

            e3.setText(String.valueOf(average));

            // Set plastic amount in t1
//            pl_amount=String.valueOf(plasticAmount);
//            t1.setText("Total Amount : 5.0*"+String.valueOf(plasticQuantity)+" = ₹"+String.valueOf(plasticAmount)+"/-");
        } else {
            // Handle the case when pqnty is empty
            e3.setText("percentage");

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void response(JSONObject jo) {
        try{

            String method = jo.getString("method");
            String status = jo.getString("status");
            Log.d("pearl", method);

            if (method.equalsIgnoreCase("semview")){

                Log.d("result", status);
//                Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
                if (status.equalsIgnoreCase("success")) {
                    JSONArray ja = jo.getJSONArray("data");

                    sem_id = new String[ja.length()];
                    sem = new String[ja.length()];
                    value = new String[ja.length()];

                    for (int i = 0; i < ja.length(); i++) {
                        sem_id[i] = ja.getJSONObject(i).getString("sem_id");
                        sem[i] = ja.getJSONObject(i).getString("sem");
                        value[i] = sem[i];
                    }

                    ArrayAdapter<String> ar = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                    s1.setAdapter(ar);

                } else {
                    Toast.makeText(getApplicationContext(), "No messages", Toast.LENGTH_LONG).show();
                }
            }

            if (method.equalsIgnoreCase("std_att")) {

                Log.d("result", status);
//                Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
                if (status.equalsIgnoreCase("success")) {

                     a =jo.getString("data1");
                    e2.setText(a);
                }
            }
            if (method.equalsIgnoreCase("publishattentmark"));{

                Log.d("result", status);
//                Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
                if (status.equalsIgnoreCase("ok")) {

                  startActivity(new Intent(getApplicationContext(),Staffhome.class));
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
        semester = sem[position];
        Toast.makeText(getApplicationContext(), "stud :" +semester, Toast.LENGTH_LONG).show();


    }
}