package com.example.timetableandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Login extends Activity  implements JsonResponse{
	Button b1;
	EditText e1,e2;
	String uname,pass;
	TextView tv1,t2,t3;
	SharedPreferences sh;
	public static String logid,usertype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b1=(Button)findViewById(R.id.btnlogin);
        tv1=(TextView)findViewById(R.id.tvuserreg);
//        t2=(TextView)findViewById(R.id.tvplace);
//        t3=(TextView)findViewById(R.id.tvtravel);
        e1=(EditText)findViewById(R.id.etunm);
        e2=(EditText)findViewById(R.id.etpass);
        b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				uname=e1.getText().toString();
				pass=e2.getText().toString();
				
				
				if(uname.equalsIgnoreCase(""))
				{
				  e1.setError("please enter username");
				  e1.setFocusable(true);
				}
				else if(pass.equalsIgnoreCase(""))
				{
				  e2.setError("enter correct Password");
				  e2.setFocusable(true);
				}

				else{
				JsonReq JR=new JsonReq();
		        JR.json_response=(JsonResponse) Login.this;
		        String q = "/login?username="+uname+"&password="+pass;
		        q=q.replace(" ","%20");
		        JR.execute(q);
				}
				
				
			
			}
		});

		tv1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final CharSequence[] items = { "Student", "Staff"};

				android.app.AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
				builder.setTitle("Registration !");
				builder.setItems(items, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int item) {

						if (items[item].equals("Student")) {
							Intent il = new Intent(getApplicationContext(), Student_reg.class);
							startActivity(il);
						}else if (items[item].equals("Staff")) {
							Intent il = new Intent(getApplicationContext(), Staff_reg.class);
							startActivity(il);




						}
					}
				});
				builder.show();
			}


		});


    }




	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		try {
			String status=jo.getString("status");
			Log.d("pearl",status);
			//Toast.makeText(getApplicationContext(),status, Toast.LENGTH_LONG).show();

			if(status.equalsIgnoreCase("success")){
				JSONArray ja1=(JSONArray)jo.getJSONArray("data");
				logid=ja1.getJSONObject(0).getString("login_id");
				usertype=ja1.getJSONObject(0).getString("usertype");
				
				SharedPreferences.Editor e=sh.edit();
				e.putString("log_id", logid);
				e.commit();
				 if(usertype.equals("student"))
				{

			           Toast.makeText(getApplicationContext()," You are Login Successfully!...,",Toast.LENGTH_LONG).show();
			           startActivity(new Intent(getApplicationContext(),student_home.class));

				}
				if(usertype.equals("teacher"))
				{


					Toast.makeText(getApplicationContext()," You are Login Successfully!...,",Toast.LENGTH_LONG).show();
					startActivity(new Intent(getApplicationContext(),Staffhome.class));

				}
				
             
			
			}	
			else {
				Toast.makeText(getApplicationContext(),"Login failed..!Please enter correct username or password ",Toast.LENGTH_LONG).show();
//				Intent i=new Intent(getApplicationContext(),MainLogin.class);
			startActivity(new Intent(getApplicationContext(),Login.class));
			}
				
			
		}catch (Exception e) {
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

	public void onBackPressed()
	{
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent b=new Intent(getApplicationContext(),IPSetting.class);			
		startActivity(b);
	}
    
}
