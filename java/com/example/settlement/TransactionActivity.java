package com.example.settlement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.settlement.ui.GroupDescriptionActivity;
import com.example.settlement.ui.SettledActivity;
import com.example.settlement.ui.sanskarRawat.rv.RecyclerViewAdapter3;
import com.example.settlement.ui.sanskarRawat.rv.TransactionModel;
import com.example.settlement.ui.sanskarRawat.rv.datamodel;
import com.example.settlement.ui.sanskarRawat.rv.myadapter;
import com.example.settlement.ui.sql.MyHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TransactionActivity extends AppCompatActivity {
TextView group;
    ArrayList<String> personList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        ////////////////////////////////////
        ////////////////////////////////////
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getSupportActionBar().hide();
        Intent intent=getIntent();
        String groupName=intent.getStringExtra("groupName");
        group=findViewById(R.id.textView4);
        group.setText(groupName);
        Button add=findViewById(R.id.buttonAdd);
        Button viewSettled=findViewById(R.id.button4);
        Spinner person1spinner=findViewById(R.id.textView5);
        Spinner person2spinner=findViewById(R.id.textView6);
        EditText amountText=findViewById(R.id.editTextTextPersonName3);
        RecyclerView recyclerView=findViewById(R.id.TransactionRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        personList=new ArrayList<>();
        ArrayList<String> payerList=new ArrayList<>();
        ArrayList<String> payeeList=new ArrayList<>();
        ArrayList<String> amountList=new ArrayList<>();
        ArrayList<String> descriptionList=new ArrayList<>();
        ArrayList<TransactionModel> transactionList=new ArrayList<>();
//        transactionList.add(new TransactionModel("sanskar","sahil","500","party"));
//        transactionList.add(new TransactionModel("sanskar1","sahil","500","party"));
//        transactionList.add(new TransactionModel("sanskar2","sahil","500","party"));
//        transactionList.add(new TransactionModel("sanskar3","sahil","500","party"));
//        transactionList.add(new TransactionModel("sanskar4","sahil","500","party"));
//        transactionList.add(new TransactionModel("sanskar5","sahil","500","party"));
////        transactionList.add(new TransactionModel("sanskar6","sahil","500","party"));
////        transactionList.add(new TransactionModel("Sanskar rawat","deepak sihare","5004","party"));
////        transactionList.add(new TransactionModel("SANSKAR RAWAT","MAYANK SGIVHARE","500","TEA AT COFFEE SHOP"));
////        transactionList.add(new TransactionModel("sanskar6","sahil","500","trip"));
//        RecyclerViewAdapter3 adapter3=new RecyclerViewAdapter3(transactionList,TransactionActivity.this);
//        recyclerView.setAdapter(adapter3);
        /////////////////////////////
        /////////////////////////////SPINNER
        ArrayList<String> list1=new ArrayList<>();
        ArrayList<String> list2=new ArrayList<>();
        list1.add("Select Payer");
        list2.add("Select Payee");
        list2.add("Divide Equally!");
        MyHelper helper=new MyHelper(TransactionActivity.this);
        SQLiteDatabase database=helper.getWritableDatabase();
        try{
            Cursor cursor=database.rawQuery("SELECT NAME FROM "+groupName,new String[] {});
            if(cursor!=null)
                cursor.moveToFirst();
            do {
                String name=cursor.getString(0);
                list1.add(name);
                list2.add(name);
                personList.add(name);
            }while (cursor.moveToNext());
        }catch(Exception e)
        {

        }
        ArrayAdapter<String> adapter1=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.textView7,list1);
        ArrayAdapter<String> adapter2=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.textView7,list2);
        person1spinner.setAdapter(adapter1);
        person2spinner.setAdapter(adapter2);
        //////////////////////////////////////////////////////////////////////////////
        ArrayList<TransactionModel> transactionList1=new ArrayList<>();
        String groupName1=groupName+"transaction";
        try{
            Cursor cursor1=database.rawQuery("SELECT PAYER,PAYEE,AMOUNT,DATE FROM "+groupName1,new String[] {});
            if(cursor1!=null)
                cursor1.moveToFirst();
            do {
                String payer=cursor1.getString(0);
                String payee=cursor1.getString(1);
                String amount=cursor1.getString(2);
                String date=cursor1.getString(3);
               transactionList.add(new TransactionModel(payer,payee,amount,date));
            }while (cursor1.moveToNext());
        }catch(Exception e)
        {
           // Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        RecyclerViewAdapter3 adapter3=new RecyclerViewAdapter3(transactionList,TransactionActivity.this,groupName);
        recyclerView.setAdapter(adapter3);
        //person1spinner.setPrompt("Select Payee");
        ////////////////////////////////////////
        ////////////////////////////////////////ADD
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amount=amountText.getText().toString();
                String payerSpinner=person1spinner.getSelectedItem().toString();
                String payeeSpinner=person2spinner.getSelectedItem().toString();
//                if(amount.trim().matches(""))
//                    amount="0";
                if(person1spinner.getSelectedItem().toString()=="Select Payer"||person2spinner.getSelectedItem().toString()=="Select Payee"||person1spinner.getSelectedItem().toString()==person2spinner.getSelectedItem().toString())
                {

                    //
                }
                else if(amount.trim().matches(""))
                {
                    amountText.setError("Can not be Empty!");

                }
                else if(Integer.parseInt(amountText.getText().toString())==0)
                {
                    amountText.setError("It can not be zero!");
                }
                else
                    {
                        Calendar calendar=Calendar.getInstance();
//                        String current= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
//                        String time=calendar.getTime().toString();

                        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                         String current = df.format(calendar.getTime());
                         current=DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime())+" "+current;
                        // Toast.makeText(TransactionActivity.this, "Add Clicked", Toast.LENGTH_SHORT).show();
                        hideKeyboard(TransactionActivity.this);
                        if(payeeSpinner.matches("Divide Equally!"))
                        {
//                            Toast.makeText(TransactionActivity.this, "divide equally!", Toast.LENGTH_SHORT).show();
                            Calendar calendar1=Calendar.getInstance();
                            String current1= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                            helper.insertDataIntoActivity("Transaction Added :-> "+payerSpinner+" Paid Rs."+String.valueOf(Integer.parseInt(amount)/(personList.size()))+" to each group member of group "+groupName,current1,database);
                            for(int i=0;i<personList.size();i++)
                            {
                                if(!personList.get(i).matches(payerSpinner))
                                {
                                    helper.insertTransactionIntoTransactionTable(groupName, payerSpinner, personList.get(i), String.valueOf(Integer.parseInt(amount)/(personList.size())), current, database);
                                    transactionList.add(new TransactionModel(payerSpinner, personList.get(i), String.valueOf(Integer.parseInt(amount)/(personList.size())), current));
                                }
                            }

                        }
                    else {
                            Calendar calendar1=Calendar.getInstance();
                            String current1= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                            helper.insertDataIntoActivity("Transaction Added :-> "+payerSpinner+" Paid Rs."+Integer.parseInt(amount)+" to "+payeeSpinner+" in group "+groupName,current1,database);
                            helper.insertTransactionIntoTransactionTable(groupName, payerSpinner, payeeSpinner, amount, current, database);
                            transactionList.add(new TransactionModel(payerSpinner, payeeSpinner, amount, current));

                        }
                        recyclerView.setAdapter(new RecyclerViewAdapter3(transactionList, TransactionActivity.this,groupName));
                    amountText.setText("");
                    person1spinner.setSelection(0);
                    person2spinner.setSelection(0);
                }
            }
        });
        ///////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////
        viewSettled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(TransactionActivity.this, SettledActivity.class);
                intent1.putExtra("groupName",groupName);
                startActivity(intent1);
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////

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