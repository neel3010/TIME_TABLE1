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
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


public class Staff_reg extends AppCompatActivity implements JsonResponse {
    final int CAMERA_PIC_REQUEST = 0, GALLERY_CODE = 201;
    private Uri mImageCaptureUri, selectedVideoUri;

    EditText e1, e2, e3, e4, e5, e6, e7, e8;

    ImageButton imgbtn;

    Button b1;
    private byte[] byteArray;

    public static String encodedImage = "", path = "",name,place,phone,email,username,quali,district,password,type;

    SharedPreferences sh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_reg);

        sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        e1 = findViewById(R.id.staffname);
        e2 = findViewById(R.id.place);
        e3 = findViewById(R.id.district);
        e4 = findViewById(R.id.quali);
        e5 = findViewById(R.id.email);
        e6 = findViewById(R.id.phone);
        e7 = findViewById(R.id.username);
        e8 = findViewById(R.id.password);

        b1=findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name=e1.getText().toString();
                place=e2.getText().toString();
                district=e3.getText().toString();
                quali=e4.getText().toString();
                email=e5.getText().toString();
                phone=e6.getText().toString();
                username=e7.getText().toString();
                password=e8.getText().toString();

                SharedPreferences.Editor e=sh.edit();
                e.putString("name",name);
                e.putString("plc",place);
                e.putString("dis",district);
                e.putString("dis",quali);
                e.putString("email",email);
                e.putString("phn",phone);
                e.putString("uname",username);
                e.putString("psd",password);
                e.commit();
                sendAttach();
            }
        });



        imgbtn = findViewById(R.id.imageButton2);

        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImageOption();
            }

            private void selectImageOption() {


                final CharSequence[] items = {"Choose from Gallery", "Cancel"};

                android.app.AlertDialog.Builder builder = new AlertDialog.Builder(Staff_reg.this);
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
            String q = "http://" + IPSetting.ip + "/staffreg";
//            Toast.makeText(getApplicationContext(), "Byte Array:" + byteArray.length, Toast.LENGTH_LONG).show();
            Map<String, byte[]> aa = new HashMap<>();
            aa.put("image", byteArray);

//
            aa.put("log_id", sh.getString("log_id", "").getBytes());
            aa.put("name", name.getBytes());
            aa.put("place", sh.getString("place", "").getBytes());
            aa.put("district", sh.getString("district", "").getBytes());
            aa.put("quali", sh.getString("quali", "").getBytes());
            aa.put("email", sh.getString("email", "").getBytes());
            aa.put("phone", sh.getString("phone", "").getBytes());
            aa.put("username", sh.getString("username", "").getBytes());
            aa.put("password", sh.getString("password", "").getBytes());
//            Toast.makeText(getApplicationContext(), "sendeeeeeeeee", Toast.LENGTH_LONG).show();


            FileUploadAsync fua = new FileUploadAsync(q);
            fua.json_response = (JsonResponse) Staff_reg.this;
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
                imgbtn.setImageBitmap(bit);

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
                imgbtn.setImageBitmap(thumbnail);
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

        try{

            String staus=jo.getString("status");
            if(staus.equalsIgnoreCase("success")){

                Toast.makeText(this, "Registration success !!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),Login.class));
            }

        }
        catch (Exception e){

            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }
}