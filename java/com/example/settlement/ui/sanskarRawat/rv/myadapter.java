package com.example.settlement.ui.sanskarRawat.rv;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.settlement.MainActivity;
import com.example.settlement.R;
import com.example.settlement.TransactionActivity;
import com.example.settlement.ui.GroupDescriptionActivity;
import com.example.settlement.ui.sql.MyHelper;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class myadapter extends RecyclerView.Adapter<myadapter.myviewholder>
{
    ArrayList<datamodel> dataholder;
    private Context context;
   // private final View.OnClickListener mOnClickListener = new MyOnClickListener();

    public myadapter(Context context,ArrayList<datamodel> dataholder) {
        this.dataholder = dataholder;
        this.context=context;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_design,parent,false);
      //  view.setOnClickListener(mOnClickListener);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position)
    {
        holder.img.setImageResource(dataholder.get(position).getImage());
        holder.header.setText(dataholder.get(position).getHeader());
        holder.desc.setText(dataholder.get(position).getDesc());
    }

    @Override
    public int getItemCount() {
        return dataholder.size();
    }
    //private static ClickListener clickListener;
    class myviewholder extends RecyclerView.ViewHolder implements  View.OnClickListener
    {
        ImageView img;
        TextView header,desc;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setOnClickListener(this);
            //itemView.setOnLongClickListener(this);
            //context=itemView.getContext();
            img=itemView.findViewById(R.id.img1);
            header=itemView.findViewById(R.id.t1);
            desc=itemView.findViewById(R.id.t2);
            img.setOnClickListener(this);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    ////////////////////////////////////////////////////////////////////////
                    new AlertDialog.Builder(context)
                            //  .setIcon(R.drawable.ic_baseline_warning_24)
                            .setTitle("Select Option:")
                            .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int j) {
                                    Intent intent=new Intent(context,GroupDescriptionActivity.class);
                                    intent.putExtra("groupNameIntent",header.getText().toString());
                                    context.startActivity(intent);

                                    // Toast.makeText(context, "edit!--"+header.getText().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }).setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                           // Toast.makeText(context, "delete!", Toast.LENGTH_SHORT).show();
                            //////////////////////////////NESTED DIALOG BOX
                            new AlertDialog.Builder(context)
                                      .setIcon(R.drawable.ic_baseline_warning_24)
                                    .setTitle("Delete entry")
                                    .setMessage("Are you sure you want to delete this group?")
                                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int j) {
                                            MyHelper helper=new MyHelper(context);
                                            SQLiteDatabase database=helper.getWritableDatabase();
                                            int position=getAdapterPosition();
                                            Calendar calendar=Calendar.getInstance();
                                            String current= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                                            String time=calendar.getTime().toString();
                                            helper.insertDataIntoActivity("Group Deleted :-> "+header.getText().toString()+" deleted! ",current,database);
                                            helper.deletePersonTable(database,header.getText().toString());
                                            helper.deleteTransactionTable(database,header.getText().toString());
                                            helper.deleteGroupNameFromGroupsTable(database,header.getText().toString());
                                            dataholder.remove(position);
                                            notifyItemRemoved(position);
                                            notifyItemRangeChanged(position, getItemCount());
//                                            list.remove(position);
//                                            recycler.removeViewAt(position);
//                                            mAdapter.notifyItemRemoved(position);
//                                            mAdapter.notifyItemRangeChanged(position, list.size());
                                            //context.startActivity(new Intent(context, MainActivity.class));
                                            // Toast.makeText(GroupDescriptionActivity.this, String.valueOf(i)+" "+list.get(i).toString(), Toast.LENGTH_SHORT).show();
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
                    ////////////////////////////////////////////////////////////////////////
                   // Toast.makeText(context, "longClick", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }

        @Override
        public void onClick(View view) {
            int position=this.getAdapterPosition();
            Intent intent=new Intent(context, TransactionActivity.class);
            intent.putExtra("groupName",dataholder.get(position).header);
            context.startActivity(intent);
            //Toast.makeText(context, "onclick"+String.valueOf(position)+"  "+dataholder.get(position).header, Toast.LENGTH_SHORT).show();

        }

//        @Override
//        public boolean onLongClick(View view) {
//            Toast.makeText(context, "on long click", Toast.LENGTH_SHORT).show();
//
//            return true;
//        }

    }

}

