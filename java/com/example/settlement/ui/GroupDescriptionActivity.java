package com.example.settlement.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.state.State;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.settlement.MainActivity;
import com.example.settlement.R;
import com.example.settlement.ui.sanskarRawat.rv.datamodel;
import com.example.settlement.ui.sanskarRawat.rv.myadapter;
import com.example.settlement.ui.sql.MyHelper;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class GroupDescriptionActivity extends AppCompatActivity {
    Button add,save;
    TextView name,groupName;
    ListView listView;
    Boolean bool;
    ArrayList<String> oldNameList;
    ArrayList<String> newNameList;
    ArrayAdapter<String> newArrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_description);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getSupportActionBar().hide();
        hideKeyboard(GroupDescriptionActivity.this);
        ArrayList<String> list=new ArrayList<>();
        Intent intent=getIntent();
        String groupNameIntent=intent.getStringExtra("groupNameIntent");
        add=findViewById(R.id.button2);
        name=findViewById(R.id.editTextTextPersonName);
        groupName=findViewById(R.id.editTextTextPersonName2);
        listView=findViewById(R.id.listView);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,list);
        MyHelper helper=new MyHelper(this);
        SQLiteDatabase database=helper.getWritableDatabase();
        oldNameList=new ArrayList<String>();
        newNameList=new ArrayList<String>();
        groupName.setFocusableInTouchMode(true);
        ///////////////////////////////
         bool=false;
        if(!groupNameIntent.matches(" "))
        {
             bool=true;
           // Toast.makeText(this, "-"+groupNameIntent+"-", Toast.LENGTH_SHORT).show();
             groupName.setText(groupNameIntent);
            //groupName.setFocusable(false);
            groupName.setEnabled(false);
            groupName.setInputType(InputType.TYPE_NULL);
            groupName.setFocusable(false);
            try{
                Cursor cursor=database.rawQuery("SELECT NAME FROM "+groupNameIntent,new String[] {});
                if(cursor!=null)
                    cursor.moveToFirst();
                do {
                    String name=cursor.getString(0);
                    oldNameList.add(name);
                    newNameList.add(name);
                }while (cursor.moveToNext());
            }catch(Exception e)
            {

            }
            listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,newNameList));
        }else{
            //groupName.setFocusableInTouchMode(true);
            //Toast.makeText(this, "bool==false", Toast.LENGTH_SHORT).show();
            groupName.setEnabled(true);
            groupName.setInputType(InputType.TYPE_CLASS_TEXT);
            groupName.setFocusable(true);
        }
        newArrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,newNameList);
//        Cursor cursor=database.rawQuery("SELECT DISTINCT GROUPNAME FROM GROUPS",new String[] {});
//        if(cursor!=null)
//            cursor.moveToFirst();
//        do {
//            String groupName=cursor.getString(0);
//            list.add(groupName);
////             dataholder.add(new datamodel(fun(),groupName,"Embed Programming"));
////            recyclerView.setAdapter(new myadapter(dataholder));
//        }while (cursor.moveToNext());
////        list.add(name.getText().toString().toUpperCase());
//        listView.setAdapter(arrayAdapter);
        //////////////////////////////
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bool==false) {

                    if (list.contains(name.getText().toString().toUpperCase().trim())) {
                        Toast.makeText(GroupDescriptionActivity.this, "Already in the list.", Toast.LENGTH_SHORT).show();
                    } else if (!name.getText().toString().trim().isEmpty()) {
                        list.add(name.getText().toString().toUpperCase());
                        listView.setAdapter(arrayAdapter);
                    }
                    name.setText("");
                    hideKeyboard(GroupDescriptionActivity.this);
                }
                else if(bool==true){
                    if (!newNameList.isEmpty()&&newNameList.contains(name.getText().toString().toUpperCase().trim())) {
                        Toast.makeText(GroupDescriptionActivity.this, "Already in the list.", Toast.LENGTH_SHORT).show();
                    } else if (!name.getText().toString().trim().isEmpty()) {
                        newNameList.add(name.getText().toString().toUpperCase());
                        //newArrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,newNameList);
                        listView.setAdapter(newArrayAdapter);
                    }
                    name.setText("");
                    hideKeyboard(GroupDescriptionActivity.this);
                }
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
               // Toast.makeText(GroupDescriptionActivity.this, "pressed", Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(GroupDescriptionActivity.this)
                      //  .setIcon(R.drawable.ic_baseline_warning_24)
                        .setTitle("Delete entry")
                       .setMessage("Are you sure you want to delete this entry?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int j) {
                                if(bool==false) {
                                    list.remove(i);
                                    listView.setAdapter(arrayAdapter);
                                }
                                else{
                                    newNameList.remove(i);
                                    listView.setAdapter(newArrayAdapter);
                                }
                              // Toast.makeText(GroupDescriptionActivity.this, String.valueOf(i)+" "+list.get(i).toString(), Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                     //   Toast.makeText(GroupDescriptionActivity.this, "Negative", Toast.LENGTH_SHORT).show();
                     dialogInterface.dismiss();
                    }
                }).show();
                return true;
            }
        });
        save=findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String groupNameReplace=groupName.getText().toString().trim().replace(" ","_");
                if(bool==false) {

                    ArrayList<String> groups = new ArrayList<>();

                    try {
                        Cursor cursor = database.rawQuery("SELECT DISTINCT GROUPNAME FROM GROUPS", new String[]{});
                        if (cursor != null) {
                            cursor.moveToFirst();
                            do {
                                String groupName1 = cursor.getString(0);
                                groups.add(groupName1.toLowerCase());
//                            dataholder.add(new datamodel(fun(), groupName, "Trip"));
//                            recyclerView.setAdapter(new myadapter(view.getContext(), dataholder));
                            } while (cursor.moveToNext());
                        }
                    } catch (Exception e) {
                        //Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    if (groupNameReplace.isEmpty()) {
                        groupName.setError("Can not be empty!");
                    } else if (groups.contains(groupNameReplace.toLowerCase()) && bool == false) {
                        Toast.makeText(GroupDescriptionActivity.this, "Group already Exists!", Toast.LENGTH_SHORT).show();
                    } else {
                        startActivity(new Intent(GroupDescriptionActivity.this, MainActivity.class));

                        helper.insertData(groupNameReplace, database);
                        helper.createPersonTable(database, groupNameReplace);
                        helper.createTransactionTable(database, groupNameReplace);
                        Calendar calendar=Calendar.getInstance();
                        String current= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                        String time=calendar.getTime().toString();
                        helper.insertDataIntoActivity("Group Created :-> "+groupNameReplace+" created! ",current,database);
                        for (int ind = 0; ind < list.size(); ind++) {
                            helper.insertNameIntoPersonTable(groupNameReplace, list.get(ind), database);
                        }
                    }
                }
                if(bool==true)
                {
                   helper.deletePersonTable(database,groupNameIntent);
                   helper.createPersonTable(database,groupNameIntent);
                   for(int i=0;i<newNameList.size();i++)
                   {
                       helper.insertNameIntoPersonTable(groupNameIntent,newNameList.get(i),database);
                   }
                   startActivity(new Intent(GroupDescriptionActivity.this,MainActivity.class));
                }

            }
        });
    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}