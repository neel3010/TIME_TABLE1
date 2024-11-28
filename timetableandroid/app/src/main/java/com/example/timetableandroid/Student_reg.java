package com.example.timetableandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Student_reg extends AppCompatActivity implements JsonResponse, AdapterView.OnItemSelectedListener {
    final int CAMERA_PIC_REQUEST = 0, GALLERY_CODE = 201;

    private Uri mImageCaptureUri,selectedVideoUri;

    public static String encodedImage = "", path = "",name,date,place,gender,pincode,phone,email,username,post,district,password,type;
    EditText e1, e2, e3, e4, e5, e6, e7, e8, e9,e10;
    Spinner sa1,sa2;
    RadioButton male, female;
    ImageButton m1;
    String[] course_id;
    String[] course;
    String[] batch_id;
    String[] start_year;
    String[] batch;
    String[] end_year;
    String[] val;

    SharedPreferences sh;


    public static String c_id;


    RadioButton r1,r2;
    Button b1;
    private byte[] byteArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_reg);


        sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        e1 = findViewById(R.id.name);
        e2 = findViewById(R.id.editTextDate);
        r1=(RadioButton)findViewById(R.id.male);
        r2=(RadioButton)findViewById(R.id.female);
        e3 = findViewById(R.id.place);
        e4 = findViewById(R.id.post);
        e5 = findViewById(R.id.pincode);
        e6 = findViewById(R.id.district);
        e7 = findViewById(R.id.email);
        e8 = findViewById(R.id.phone);
        e9 = findViewById(R.id.username);
        e10 = findViewById(R.id.password);
        b1=findViewById(R.id.button);

        b1.setOnClickListener(new View.OnClickListener() {
            private byte[] byteArray;

            @Override
            public void onClick(View v) {


                name=e1.getText().toString();
                date=e2.getText().toString();
                if (r1.isChecked()) {
                    type = "male";
                } else{
                    type = "female";
                }
                place=e3.getText().toString();
                post=e4.getText().toString();
                pincode=e5.getText().toString();
                district=e6.getText().toString();
                email=e7.getText().toString();
                phone=e8.getText().toString();
                username=e9.getText().toString();
                password=e10.getText().toString();

                SharedPreferences.Editor e=sh.edit();
                e.putString("name",name);
                e.putString("course",c_id);
                e.putString("dob",date);
                e.putString("gender",type);
                e.putString("plc",place);
                e.putString("post",post);
                e.putString("pin",pincode);
                e.putString("dis",district);
                e.putString("email",email);
                e.putString("phn",phone);
                e.putString("uname",username);
                e.putString("psd",password);
                e.commit();
                sendAttach();

            }

        });



        sa1 = findViewById(R.id.spinner);
        sa1.setOnItemSelectedListener(this);
        JsonReq JR = new JsonReq();
        JR.json_response = this;
        String q = "/course";
        q = q.replace(" ", "%20");
        JR.execute(q);

//        sa2 = findViewById(R.id.spinner2);
//        sa2.setOnItemSelectedListener(this);
//        JsonReq JR1 = new JsonReq();
//        JR1.json_response = this;
//        String q1 = "/batch";
//        q1 = q1.replace(" ", "%20");
//        JR1.execute(q1);



        m1 = findViewById(R.id.imageButton2);
        m1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImageOption();
            }

            private void selectImageOption() {
                /*Android 10+ gallery code
        android:requestLegacyExternalStorage="true"*/

                final CharSequence[] items = { "Choose from Gallery", "Cancel"};

                android.app.AlertDialog.Builder builder = new AlertDialog.Builder(Student_reg.this);
                builder.setTitle("Take Photo!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {

                        if (items[item].equals("Choose from Gallery")) {
                            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(i, GALLERY_CODE);

                        } else if (items[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });

    }


    private void sendAttach() {

        try {
            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//	            String uid = sh.getString("uid", "");
//                String q = "http://" + IpSetting.ip + "/smart city/apis.php";
            String q = "http://" +IPSetting.ip+"/userreg";
//            Toast.makeText(getApplicationContext(), "Byte Array:" + byteArray.length, Toast.LENGTH_LONG).show();
            Map<String, byte[]> aa = new HashMap<>();
            aa.put("image", byteArray);

//
            aa.put("log_id",sh.getString("log_id","").getBytes());
            aa.put("name",name.getBytes());
            aa.put("course",sh.getString("course","").getBytes());
            aa.put("dob",sh.getString("dob","").getBytes());
            aa.put("gender",sh.getString("gender","").getBytes());
            aa.put("plc",sh.getString("plc","").getBytes());
            aa.put("post",sh.getString("post","").getBytes());
            aa.put("pin",sh.getString("pin","").getBytes());
            aa.put("dis",sh.getString("dis","").getBytes());
            aa.put("email",sh.getString("email","").getBytes());
            aa.put("phn",sh.getString("phn","").getBytes());
            aa.put("uname",sh.getString("uname","").getBytes());
            aa.put("psd",sh.getString("psd","").getBytes());
//            Toast.makeText(getApplicationContext(), "sendeeeeeeeee", Toast.LENGTH_LONG).show();


            FileUploadAsync fua = new FileUploadAsync(q);
            fua.json_response = (JsonResponse) Student_reg.this;
            fua.execute(aa);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Exception upload : " + e, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK && null != data) {

            mImageCaptureUri = data.getData();
            System.out.println("Gallery Image URI : " + mImageCaptureUri);
            //   CropingIMG();

            Uri uri = data.getData();
            Log.d("File Uri", "File Uri: " + uri.toString());
            // Get the path
            //String path = null;
            try {
                path = FileUtils.getPath(this, uri);
//                Toast.makeText(getApplicationContext(), "path : " + path, Toast.LENGTH_LONG).show();

                File fl = new File(path);
                int ln = (int) fl.length();

                InputStream inputStream = new FileInputStream(fl);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] b = new byte[ln];
                int bytesRead = 0;

                while ((bytesRead = inputStream.read(b)) != -1) {
                    bos.write(b, 0, bytesRead);
                }
                inputStream.close();
                byteArray = bos.toByteArray();

                Bitmap bit = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                m1.setImageBitmap(bit);

                String str = Base64.encodeToString(byteArray, Base64.DEFAULT);
                encodedImage = str;
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == CAMERA_PIC_REQUEST && resultCode == Activity.RESULT_OK) {

            try {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                m1.setImageBitmap(thumbnail);
                byteArray = baos.toByteArray();

                String str = Base64.encodeToString(byteArray, Base64.DEFAULT);
                encodedImage = str;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//

    }

    @Override
    public void response(JSONObject jo) {
        try {
            String method = jo.getString("method");
            Log.d("pearl", method);

            if (method.equalsIgnoreCase("course")) {
                String status = jo.getString("status");
                Log.d("pearl", method);
                if (status.equalsIgnoreCase("success")) {
//                    Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();

                    JSONArray ja1 = jo.getJSONArray("data");
                    course_id = new String[ja1.length()];
                    course = new String[ja1.length()];

                    for (int i = 0; i < ja1.length(); i++) {
                        course_id[i] = ja1.getJSONObject(i).getString("course_id");
                        course[i] = ja1.getJSONObject(i).getString("course_name");
                    }
                    ArrayAdapter<String> ar = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, course);
                    sa1.setAdapter(ar);

                }
            }
            if(method.equalsIgnoreCase("user_pets")){
                Toast.makeText(this, "Registration success !!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),Login.class));
                String status=jo.getString("status");

                if (status.equalsIgnoreCase("success")){
                    Toast.makeText(this, "qwertyuikjhgfdsdfg", Toast.LENGTH_SHORT).show();

                    Toast.makeText(this, "Registration success !!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),Login.class));
                }
            }
//
//            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }


    public void onItemSelected(AdapterView<?> parent, View v, int position, long id ){
        Spinner spin = (Spinner)parent;
        Spinner spin2 = (Spinner)parent;
        if(spin.getId() == R.id.spinner) {
            Toast.makeText(this, "Selected  Course :" + course_id[position], Toast.LENGTH_SHORT).show();
            c_id =course_id[position];
        }
//        if(spin2.getId() == R.id.spinner2)
//        {
//            Toast.makeText(this, "Selected Batch :" + batch_id[position],Toast.LENGTH_SHORT).show();
//        }





































































































































    }




















































































































































































































































































































































































    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
