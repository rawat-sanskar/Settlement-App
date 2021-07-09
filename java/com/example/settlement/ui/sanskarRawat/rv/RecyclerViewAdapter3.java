package com.example.settlement.ui.sanskarRawat.rv;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.settlement.MainActivity;
import com.example.settlement.R;
import com.example.settlement.TransactionActivity;
import com.example.settlement.TransactionDescriptionActivity;
import com.example.settlement.ui.GroupDescriptionActivity;
import com.example.settlement.ui.sql.MyHelper;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter3 extends RecyclerView.Adapter<RecyclerViewAdapter3.ViewHolder> {
    ArrayList<TransactionModel> transactions;
    Context context;
    String tableName;

    public RecyclerViewAdapter3(ArrayList<TransactionModel> transactions, Context context,String tableName) {
        this.transactions = transactions;
        this.context = context;
        this.tableName=tableName;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter3.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.row3,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter3.ViewHolder holder, int position) {
          holder.payerView.setText(transactions.get(position).getPayer());
        holder.payeeView.setText(transactions.get(position).getPayee());
        holder.amountView.setText(transactions.get(position).getAmount());
        holder.descriptionView.setText(transactions.get(position).getDescription());
        if(transactions.get(position).getPayer().matches("PAYER"))
        {
            holder.payerView.setTextColor(Color.parseColor("#FF000000"));
        }
        if(transactions.get(position).getPayee().matches("PAYEE"))
        {
            holder.payeeView.setTextColor(Color.parseColor("#FF000000"));
        }
        if(transactions.get(position).getAmount().matches("AMOUNT"))
        {
            holder.amountView.setTextColor(Color.parseColor("#FF000000"));
        }

    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView payerView,payeeView,amountView,descriptionView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            payerView=itemView.findViewById(R.id.textView8);
            payeeView=itemView.findViewById(R.id.textView9);
            amountView=itemView.findViewById(R.id.textView10);
            descriptionView=itemView.findViewById(R.id.textView12);

            if(!transactions.get(0).getPayer().matches("PAYER")) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, TransactionDescriptionActivity.class);
                        intent.putExtra("payerIntent", payerView.getText().toString());
                        intent.putExtra("payeeIntent", payeeView.getText().toString());
                        intent.putExtra("amountIntent", amountView.getText().toString());
                        intent.putExtra("descriptionIntent", descriptionView.getText().toString());
                        intent.putExtra("tableNameIntent", tableName);
                        intent.putExtra("msg", "view");
                        context.startActivity(intent);
                    }
                });
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        int position = getAdapterPosition();
                        //////////////////////////////////////////////////////////
                        new AlertDialog.Builder(context)
                                //  .setIcon(R.drawable.ic_baseline_warning_24)
                                .setTitle("Select Option:")
                                .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int j) {
                                        Intent intent = new Intent(context, TransactionDescriptionActivity.class);
                                        intent.putExtra("payerIntent", payerView.getText().toString());
                                        intent.putExtra("payeeIntent", payeeView.getText().toString());
                                        intent.putExtra("amountIntent", amountView.getText().toString());
                                        intent.putExtra("descriptionIntent", descriptionView.getText().toString());
                                        intent.putExtra("tableNameIntent", tableName);
                                        intent.putExtra("msg", "edit");
                                        context.startActivity(intent);

                                        //Toast.makeText(context, "edit!--", Toast.LENGTH_SHORT).show();
                                    }
                                }).setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Toast.makeText(context, "delete!", Toast.LENGTH_SHORT).show();
                                //////////////////////////////NESTED DIALOG BOX
                                new AlertDialog.Builder(context)
                                        .setIcon(R.drawable.ic_baseline_warning_24)
                                        .setTitle("Delete entry")
                                        .setMessage("Are you sure you want to delete this transaction?")
                                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int j) {
                                                MyHelper helper = new MyHelper(context);
                                                SQLiteDatabase database = helper.getWritableDatabase();
                                                ////////////////
                                                Calendar calendar1=Calendar.getInstance();
                                                String current1= DateFormat.getDateInstance(DateFormat.FULL).format(calendar1.getTime());
                                                helper.insertDataIntoActivity("Transaction Deleted :-> Transaction Deleted in which "+payerView.getText().toString()+" paid Rs."+amountView.getText().toString()+" to "+payeeView.getText().toString()+" in group "+tableName,current1,database);
                                                database.delete(tableName + "transaction", "DATE=? AND PAYEE=?", new String[]{descriptionView.getText().toString(), payeeView.getText().toString()});
                                                //////////////
                                                transactions.remove(position);
                                                notifyItemRemoved(position);
                                                // notifyItemRangeChanged(position, getItemCount());
                                                // notifyDataSetChanged();
//                                            helper.deletePersonTable(database,header.getText().toString());
//                                            helper.deleteTransactionTable(database,header.getText().toString());
//                                            helper.deleteGroupNameFromGroupsTable(database,header.getText().toString());
                                                //context.startActivity(new Intent(context, TransactionActivity.class));
                                                // Toast.makeText(context, String.valueOf(id)+" "+descriptionView.getText().toString(), Toast.LENGTH_LONG).show();
                                            }
                                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //   Toast.makeText(GroupDescriptionActivity.this, "Negative", Toast.LENGTH_SHORT).show();
                                        dialogInterface.dismiss();
                                    }
                                }).show();
                                //////////////////////////////NESTED DIALOG BOX ENDS
                            }
                        }).show();
                        ///////////////////////////////////////////////////////////
                        return true;
                    }
                });
            }
        }
    }
}
