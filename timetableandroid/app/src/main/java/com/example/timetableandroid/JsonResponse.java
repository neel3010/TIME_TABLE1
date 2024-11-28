package com.example.timetableandroid;

import android.view.View;
import android.widget.AdapterView;

import org.json.JSONObject;

public interface JsonResponse {
    public void response(JSONObject jo);

    void onItemClick(AdapterView<?> adapterView, View view, int i, long l);

    void onItemSelected(AdapterView<?> parent, View view, int position, long id);
}
