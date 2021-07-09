package com.example.settlement.ui.home;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.settlement.R;
import com.example.settlement.ui.GroupDescriptionActivity;
import com.example.settlement.ui.sanskarRawat.rv.datamodel;
import com.example.settlement.ui.sanskarRawat.rv.myadapter;
import com.example.settlement.ui.sql.MyHelper;

import java.util.ArrayList;
import java.util.Random;

public class HomeFragment extends Fragment {
    //private HomeViewModel homeViewModel;
    RecyclerView recyclerView;

    Button fab;
    ArrayList<datamodel> dataholder;
    MyHelper helper=new MyHelper(getActivity());

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);


       // final TextView textView = view.findViewById(R.id.text_home);
        //textView.setText("Overall, you will get Rs500 and you need to give Rs50.");
       fab=view.findViewById(R.id.button3);
       //////////////////////////////////////////////
        MyHelper helper=new MyHelper(view.getContext());
        SQLiteDatabase database=helper.getWritableDatabase();
        //////////////////////////////////////////////////////////
        recyclerView=view.findViewById(R.id.group_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dataholder=new ArrayList<>();

//

//        final RecyclerView recyclerView= root.findViewById(R.id.group_rv);
//        ArrayList<GroupModel> list=new ArrayList<>();
//        list.add(new GroupModel(R.drawable.ss,"Goa Trip","You will get Rs.50","5 Friends"));
//        list.add(new GroupModel(R.drawable.ss,"Goa Trip","Settled!","4 Friends"));
//        list.add(new GroupModel(R.drawable.ss,"manali Trip","Settled!","4 Friends"));
//        list.add(new GroupModel(R.drawable.ss,"indore Trip","Settled!","4 Friends"));
//        list.add(new GroupModel(R.drawable.logo,"hpp Trip","Settled!","4 Friends"));
//        list.add(new GroupModel(R.drawable.logo,"Flat","You will get Rs.50 and you need to give Rs.30","4 Friends"));
//
//        recyclerView.setHasFixedSize(true);
//        GroupAdapter adapter=new GroupAdapter(list,root.getContext());
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        /////////////////////////////////////////////
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(view.getContext(), "hello", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(new Intent(getActivity().getApplication(), GroupDescriptionActivity.class));
                intent.putExtra("groupNameIntent"," ");
               startActivity(intent);
            }
        });
        /////////////////////////////////////////////
        ArrayList<String> personList=new ArrayList<>();
        try {
            Cursor cursor = database.rawQuery("SELECT DISTINCT GROUPNAME FROM GROUPS", new String[]{});
            if (cursor != null) {
                cursor.moveToFirst();
                do {
                    String groupName = cursor.getString(0);
                    personList.clear();
                    try{
                        Cursor cursor1=database.rawQuery("SELECT NAME FROM "+groupName,new String[] {});
                        if(cursor1!=null)
                            cursor1.moveToFirst();
                        do {
                            String name=cursor1.getString(0);
                            personList.add(name);
                        }while (cursor1.moveToNext());
                    }catch(Exception e)
                    {
                        //Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    dataholder.add(new datamodel(fun(), groupName, String.valueOf(personList.size())+" Group Members"));
                    recyclerView.setAdapter(new myadapter(view.getContext(), dataholder));
                } while (cursor.moveToNext());
            }
        }catch(Exception e)
        {
            //Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return view;
    }
    public int fun(){
        Random rand =new Random();
        int ran ;
        int image;
        ran = rand.nextInt(14);
        image=R.drawable.ss;
        switch(ran)
        {
            case 1: image=R.drawable.group5;break;
            case 2: image=R.drawable.group4;break;
            case 3: image=R.drawable.group3;break;
            case 4: image=R.drawable.group2;break;
            case 5: image=R.drawable.group6;break;
            case 6: image=R.drawable.group7;break;
            case 7: image=R.drawable.group9;break;
            case 8: image=R.drawable.group8;break;
            case 9: image=R.drawable.group10;break;
            case 10: image=R.drawable.group11;break;
            case 11: image=R.drawable.group12;break;
            case 12: image=R.drawable.group13;break;
            case 13: image=R.drawable.group8;break;
            default: image= R.drawable.group1;
        }
        return image;
    }
}