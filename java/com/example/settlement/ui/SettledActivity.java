package com.example.settlement.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.settlement.R;
import com.example.settlement.ui.sanskarRawat.rv.RecyclerViewAdapter3;
import com.example.settlement.ui.sanskarRawat.rv.TransactionModel;
import com.example.settlement.ui.sanskarRawat.rv.settledModel;
import com.example.settlement.ui.sql.MyHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class SettledActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settled);
        getSupportActionBar().hide();
        Intent intent=getIntent();
        String groupName=intent.getStringExtra("groupName");
        RecyclerView recyclerView=findViewById(R.id.settledRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<TransactionModel> settledList=new ArrayList<>();
       // settledList.add(new TransactionModel("PAYER","PAYEE","AMOUNT"," "));
//        RecyclerViewAdapter3 adapter=new RecyclerViewAdapter3(settledList,SettledActivity.this);
//        recyclerView.setAdapter(adapter);
        /////////////////////////////////////////////////////////////////////////sql
        MyHelper helper=new MyHelper(SettledActivity.this);
        SQLiteDatabase database=helper.getWritableDatabase();
        ArrayList<settledModel> transactionList1=new ArrayList<>();
        String groupName1=groupName+"transaction";
        try{
            Cursor cursor1=database.rawQuery("SELECT PAYER,PAYEE,AMOUNT FROM "+groupName1,new String[] {});
            if(cursor1!=null)
                cursor1.moveToFirst();
            do {
                String payer=cursor1.getString(0);
                String payee=cursor1.getString(1);
                String amount=cursor1.getString(2);
                //String date=cursor1.getString(3);
                transactionList1.add(new settledModel(payer,payee,amount));
            }while (cursor1.moveToNext());
        }catch(Exception e)
        {
            //Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
       settledList= settle(transactionList1);
        RecyclerViewAdapter3 adapter3=new RecyclerViewAdapter3(settledList,SettledActivity.this,groupName);
        recyclerView.setAdapter(adapter3);
        ////////////////////////////////////////////////////////////////////////////

    }
    public ArrayList<TransactionModel> settle(ArrayList<settledModel> transaction)
    {
        HashMap<String,Integer> settle=new HashMap<>();
        for(int i=0;i<transaction.size();i++)
        {
            int temp=0;
            String p1=transaction.get(i).getPayer1();
            String p2=transaction.get(i).getPayee1();
            int p=Integer.parseInt(transaction.get(i).getAmount1().trim());
            if(settle.containsKey(p1))
            {
                temp=settle.get(p1);
                temp+=p;
                settle.put(p1,temp);
            }else
            {
                settle.put(p1, p);
            }
            if(settle.containsKey(p2))
            {
                temp=settle.get(p2);
                temp-=p;
                settle.put(p2, temp);
            }else if(!settle.containsKey(p2))
            {
                temp=-1*p;
                settle.put(p2, -1*p);
            }
        }
        Boolean bool=false;
        for(int amount:settle.values())
        {
            if(amount!=0)
            {
                bool=true;
                break;
            }
        }
        ArrayList<TransactionModel> settled=new ArrayList<>();
        settled.add(new TransactionModel("PAYER","PAYEE","AMOUNT"," "));
        while(bool==true)
        {
            int max=0;
            int min=0;
            String personMax="";
            String personMin="";
            for(String person :settle.keySet())
            {
                if(settle.get(person)>0&&settle.get(person)>max)
                {
                    personMax=person;
                    max=settle.get(person);
                }
                if(settle.get(person)<0&&settle.get(person)<min)
                {
                    personMin=person;
                    min=settle.get(person);
                }

            }
            min=-1*min;
            if(max>min)
            {
                settled.add(new TransactionModel(personMin,personMax,String.valueOf(min),""));
                settle.put(personMin, 0);
                settle.put(personMax, max-min);
            }
            else {
                settled.add(new TransactionModel(personMin,personMax,String.valueOf(max),""));
                settle.put(personMax, 0);
                settle.put(personMin, -1*(min-max));
            }
            bool=false;
            for(int amount:settle.values())
            {
                if(amount!=0)
                {
                    bool=true;
                    break;
                }
            }
        }
        return settled;
    }
}