package com.example.timetableandroid;

import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Tprofilepiccoustme extends ArrayAdapter<String>  {

    private Activity context;       //for to get current activity context
    SharedPreferences sh;
    private String[] staff_name, place, district, qualification, image, email, phone;


    public Tprofilepiccoustme(Activity context,String[] staff_name, String[] place,String[] district,String[]qualification, String[] image, String[] email, String[] phone) {
        //constructor of this class to get the values from main_activity_class

        super(context, R.layout.activity_tprofilepiccoustme, image);
        this.context = context;
        this.staff_name = staff_name;
        this.place=place;
        this.district=district;
        this.qualification = qualification;
        this.image = image;
        this.email = email;
        this.phone = phone;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //override getView() method

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_tprofilepiccoustme, null, true);
        //cust_list_view is xml file of layout created in step no.2

        ImageView im = (ImageView) listViewItem.findViewById(R.id.imageView);
        TextView t=(TextView)listViewItem.findViewById(R.id.textView);
        TextView t1=(TextView)listViewItem.findViewById(R.id.tvname);
//        TextView t2=(TextView)listViewItem.findViewById(R.id.textView2);
//        TextView t3=(TextView)listViewItem.findViewById(R.id.textView3);
//        TextView t4=(TextView)listViewItem.findViewById(R.id.textView4);
//        TextView t5=(TextView)listViewItem.findViewById(R.id.textView5);


        String text = "PLACE : " + place[position]
                + "\nDISTRICT : " + district[position]
                + "\nQUALIFICATION : " + qualification[position]
                + "\nEMAIL : " + place[position]
                ;

//        t.setText("Name : "+ student_name[position] + "\nCourse : "  + course_name[position]
//                + "\nDepartment : " + department_name[position] + "\nDob : " +dob[position]
//                + "\nGender : "+ gender[position]
//                +"\nAddress : " + place[position] +",\n"+ post[position] +",\n"+pincode[position]+",\n"+district[position]
//                + "\nContact-No : " +phone[position]  );
        t1.setText("Name : " + staff_name[position]+"\nPh. : " + phone[position] );
//        t2.setText("Description : "+description[position]);
//        t3.setText("Cost : "+cost[position]);
//        t4.setText("Date : "+date_time[position]);
//        t5.setText("Status : "+wstatus[position]);

        SpannableString spannableText = new SpannableString(text);

//// Change font color for student_name[position]
//        int studentNameStart = text.indexOf(student_name[position]);
//        int studentNameEnd = studentNameStart + student_name[position].length();
//        spannableText.setSpan(new ForegroundColorSpan(Color.RED), studentNameStart, studentNameEnd, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

// Change font color for course_name[position]
//        int studentNameStart = text.indexOf(student_name[position]);
//        int studentNameEnd = studentNameStart + student_name[position].length();
//        spannableText.setSpan(new ForegroundColorSpan(Color.WHITE), studentNameStart, studentNameEnd, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

// Change font color for course_name[position] to white with transparency
        int courseNameStart = text.indexOf(place[position]);
        int courseNameEnd = courseNameStart + place[position].length();
        spannableText.setSpan(new ForegroundColorSpan(Color.BLACK
        ), courseNameStart, courseNameEnd, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        t.setText(spannableText);


        sh=PreferenceManager.getDefaultSharedPreferences(getContext());

        String pth = "http://"+sh.getString("ip", "")+"/"+image[position];
        pth = pth.replace("~", "");
//	       Toast.makeText(context, pth, Toast.LENGTH_LONG).show();

        Log.d("-------------", pth);
        Picasso.with(context)
                .load(pth)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background).into(im);
//
        return  listViewItem;
    }

    private TextView setText(String string) {
        // TODO Auto-generated method stub
        return null;
    }
}