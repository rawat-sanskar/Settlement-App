package com.example.settlement.ui.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.settlement.ui.GroupDescriptionActivity;

public class MyHelper extends SQLiteOpenHelper {
    private  static  final String dbName="settlement";
    private static final int version=1;
    public MyHelper(Context context)
    {
        super(context,dbName,null,version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE GROUPS (_ID INTEGER PRIMARY KEY AUTOINCREMENT,GROUPNAME TEXT,NAME TEXT,IMAGE INTEGER)";
        db.execSQL(sql);
        String sql1="CREATE TABLE ACTIVITIES (_ID INTEGER PRIMARY KEY AUTOINCREMENT,ACTIVITY TEXT,DATE TEXT)";
        db.execSQL(sql1);
    }
    public void insertDataIntoActivity(String activity,String date,SQLiteDatabase database )
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put("ACTIVITY",activity);
        contentValues.put("DATE",date);
        database.insert("ACTIVITIES",null,contentValues);
    }
public void insertData(String groupName,SQLiteDatabase database )
{
    ContentValues contentValues=new ContentValues();
    contentValues.put("GROUPNAME",groupName);
//    contentValues.put("NAME",name);
    database.insert("GROUPS",null,contentValues);
}
    public void createPersonTable(SQLiteDatabase db,String tableName) {
//        try {
            String sql ="CREATE TABLE IF NOT EXISTS "+tableName+" (_ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT)";
            db.execSQL(sql);
//        }catch (Exception e)
//        {
//           // Toast.makeText(GroupDescriptionActivity.contex, "", Toast.LENGTH_SHORT).show();
//        }
    }
    public void deletePersonTable(SQLiteDatabase db,String tableName) {
        db.execSQL("DROP TABLE IF EXISTS "+tableName);
    }
    public void deleteTransactionTable(SQLiteDatabase db,String tableName) {
        tableName=tableName+"transaction";
        db.execSQL("DROP TABLE IF EXISTS "+tableName);
    }
    public void deleteGroupNameFromGroupsTable(SQLiteDatabase db,String tableName) {
        db.delete("GROUPS","GROUPNAME=?",new String[]{tableName});
    }

    public void insertNameIntoPersonTable(String groupName,String name,SQLiteDatabase database )
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put("NAME",name);
//    contentValues.put("NAME",name);
        database.insert(groupName,null,contentValues);
    }
    public void createTransactionTable(SQLiteDatabase db,String tableName) {
        tableName=tableName+"transaction";
        String sql="CREATE TABLE IF NOT EXISTS "+tableName+" (_ID INTEGER PRIMARY KEY AUTOINCREMENT,PAYER TEXT,PAYEE TEXT,AMOUNT INTEGER,DATE TEXT,DESCRIPTION TEXT)";
        db.execSQL(sql);
    }
    public void insertTransactionIntoTransactionTable(String groupName,String payer,String payee,String amount,String date,SQLiteDatabase database )
    {
        groupName=groupName+"transaction";
        ContentValues contentValues=new ContentValues();
        contentValues.put("PAYER",payer);
        contentValues.put("PAYEE",payee);
        contentValues.put("AMOUNT",amount);
        contentValues.put("DATE",date);
//    contentValues.put("NAME",name);
        database.insert(groupName,null,contentValues);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
