package com.example.settlement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.settlement.ui.sanskarRawat.rv.datamodel;
import com.example.settlement.ui.sanskarRawat.rv.myadapter;
import com.example.settlement.ui.sql.MyHelper;

public class TransactionDescriptionActivity extends AppCompatActivity {
  TextView payer,payee,amount,date,descTextView;
  Button save,share;
  EditText descEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_description);
        MyHelper helper=new MyHelper(TransactionDescriptionActivity.this);
        SQLiteDatabase database=helper.getWritableDatabase();
        Intent intent=getIntent();
        String groupName=intent.getStringExtra("tableNameIntent");
        String amountIntent=intent.getStringExtra("amountIntent");
        String payerIntent=intent.getStringExtra("payerIntent");
        String payeeIntent=intent.getStringExtra("payeeIntent");
        String dateIntent=intent.getStringExtra("descriptionIntent");
        String msg=intent.getStringExtra("msg");
        getSupportActionBar().setTitle(groupName.toUpperCase());

        //String amountIntent=intent.getStringExtra("amountIntent");
        save=findViewById(R.id.saveDes);
        share=findViewById(R.id.shareDes);
        payer=findViewById(R.id.textView22);
        payee=findViewById(R.id.textView24);
        amount=findViewById(R.id.textView26);
        date=findViewById(R.id.textView28);
        descTextView=findViewById(R.id.textView30);
        payer.setText(payerIntent);
        payee.setText(payeeIntent);
        amount.setText(amountIntent);
        date.setText(dateIntent);
        descEditText=findViewById(R.id.editTextTextPersonName5);
        String descrip="null";
        if(msg.matches("edit"))
        {
            descEditText.setVisibility(View.VISIBLE);
            descEditText.setEnabled(true);
            descTextView.setVisibility(View.INVISIBLE);
            descTextView.setEnabled(false);
            save.setVisibility(View.VISIBLE);
            save.setEnabled(true);
            share.setVisibility(View.INVISIBLE);
            share.setEnabled(false);

        }
        else{
            descEditText.setVisibility(View.INVISIBLE);
            descEditText.setEnabled(false);
            descTextView.setVisibility(View.VISIBLE);
            descTextView.setEnabled(true);
            share.setVisibility(View.VISIBLE);
            share.setEnabled(true);
            save.setVisibility(View.INVISIBLE);
            save.setEnabled(false);
        }
        try {
            Cursor cursor = database.rawQuery("SELECT DESCRIPTION FROM "+groupName+"transaction"+" WHERE PAYER=? AND PAYEE=? AND AMOUNT=? AND DATE=?", new String[]{payerIntent,payeeIntent,amountIntent,dateIntent});
            if (cursor != null) {
                cursor.moveToFirst();
                do {
                    descrip = cursor.getString(0);

//                    dataholder.add(new datamodel(fun(), groupName, "Trip"));
//                    recyclerView.setAdapter(new myadapter(view.getContext(), dataholder));
                } while (cursor.moveToNext());
            }
        }catch(Exception e)
        {
            //Toast.makeText(TransactionDescriptionActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        if(descrip.trim().matches(""))
            descrip="No Description!";
        descTextView.setText(descrip);
        descEditText.setText(descrip);
       // Toast.makeText(TransactionDescriptionActivity.this, descrip, Toast.LENGTH_LONG).show();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values =new ContentValues();
                values.put("DESCRIPTION",descEditText.getText().toString());
                database.update(groupName + "transaction",values,"PAYER=? AND PAYEE=? AND AMOUNT=? AND DATE=?", new String[]{payerIntent,payeeIntent,amountIntent,dateIntent});
//                Intent intent=new Intent(TransactionDescriptionActivity.this, TransactionActivity.class);
//                intent.putExtra("groupName",groupName);
//                startActivity(intent);
                Toast.makeText(TransactionDescriptionActivity.this, "Saved Successfully!", Toast.LENGTH_SHORT).show();
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Create an ACTION_SEND Intent*/
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                /*This will be the actual content you wish you share.*/
                String shareBody = "Hi There! "+payerIntent+" paid Rs."+amountIntent+" to "+payeeIntent+" on "+dateIntent+" in group "+groupName+".";
                /*The type of the content is text, obviously.*/
                intent.setType("text/plain");
                /*Applying information Subject and Body.*/
                //intent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.share_subject));
                intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                /*Fire!*/
                startActivity(Intent.createChooser(intent, "Share It Using..."));
            }
        });
    }

}