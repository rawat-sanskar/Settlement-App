package com.example.settlement;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.settlement.ui.sanskarRawat.rv.datamodel;
import com.example.settlement.ui.sanskarRawat.rv.myadapter;
import com.example.settlement.ui.sql.MyHelper;

import java.util.ArrayList;
import java.util.Collections;

public class ActivitiesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);
        ArrayList<String> activityList=new ArrayList<>();
        ArrayList<String> dateList=new ArrayList<>();
        MyHelper helper=new MyHelper(ActivitiesActivity.this);
        SQLiteDatabase database=helper.getWritableDatabase();
        try {
            Cursor cursor = database.rawQuery("SELECT ACTIVITY,DATE FROM ACTIVITIES", new String[]{});
            if (cursor != null) {
                cursor.moveToFirst();
                do {
                    String activity = cursor.getString(0);
                    String date = cursor.getString(1);
                    activityList.add(activity);
                    dateList.add(activity+" - "+date);
//                    dataholder.add(new datamodel(fun(), groupName, "Trip"));
//                    recyclerView.setAdapter(new myadapter(view.getContext(), dataholder));
                } while (cursor.moveToNext());
            }
        }catch(Exception e)
        {
            Toast.makeText(ActivitiesActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        Collections.reverse(dateList);
        ListView lv=findViewById(R.id.lv);
        lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,dateList));
    }
}