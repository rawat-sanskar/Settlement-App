package com.example.settlement.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.settlement.R;

public class RatingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        Button submit=findViewById(R.id.Submit);
        getSupportActionBar().setTitle("Rating And Feedback");
        /////////////////////////////////////////////
        RatingBar rating=findViewById(R.id.ratingBar);
        TextView rate=findViewById(R.id.textView11);
        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rate.setText("Rating : "+v);
            }
        });
        ///////////////////////////////////////////
        EditText desc=findViewById(R.id.editTextTextMultiLine);

        ////////////////////////////////////////////
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg=desc.getText().toString();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "rawat.sanskar00@gmail.com" });
                intent.putExtra(Intent.EXTRA_SUBJECT, "Rating and Feedback of Settlement App");

                if(msg.trim().matches(""))
                    intent.putExtra(Intent.EXTRA_TEXT, rate.getText().toString()+"/5");
                else
                    intent.putExtra(Intent.EXTRA_TEXT, rate.getText().toString()+"/5, "+" Feedback - "+msg);
                startActivity(Intent.createChooser(intent, "Select Email!"));
            }
        });
    }
}