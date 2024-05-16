package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class NguyenTienTung_Sqlite extends SQLiteOpenHelper {
    private static final String DBName="NguyenTienTung_Sqlite.db";
    private static final int  VERSION=1;
    private static final String TABLENAME="Contact_211200893";
    private  static  String Id="_id";
    private static String Name="name";
    private static  String Phone="phone";
    private SQLiteDatabase myDB;

    public NguyenTienTung_Sqlite(@Nullable Context context) {
        super(context, DBName,null, VERSION);
    }

    public static String getId() {
        return Id;
    }

    public static String getName() {
        return Name;
    }

    public static String getPhone() {
        return Phone;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryTable = "CREATE TABLE " + TABLENAME +
                "( " + Id + " INTEGER PRIMARY KEY," +
                Name + " TEXT NOT NULL, " +
                Phone + " INTEGER NOT NULL " + ")";
        db.execSQL(queryTable);
        ArrayList<Contact> contacts=new ArrayList<>();
        ArrayList<Contact> list = new ArrayList<>();
        list.add(new Contact(1, "Nam", "0987654321"));
        list.add(new Contact(2, "Thang", "0987654322"));
        list.add(new Contact(3, "Thanh", "0987654323"));
        list.add(new Contact(4, "Nguyen Tien Tung", "0987654324"));
        list.add(new Contact(5, "Lam", "0987654325"));
        list.add(new Contact(6, "Lan", "0987654326"));
        for (Contact contact : list) {
            ContentValues values = new ContentValues();
            values.clear();
            values.put(Id, contact.getId());
            values.put(Name,contact.getName());
            values.put(Phone, contact.getPhone());
            db.insert(TABLENAME, null, values);
        }



    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void openDb(){
        myDB= getWritableDatabase();
    }
    public void closeDb(){
        if(myDB !=null && myDB.isOpen()){
            myDB.close();
        }
    }
    public long Insert(int id,String name, String phone){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Id,id);
        values.put(Name,name);
        values.put(Phone,phone);
        return db.insert(TABLENAME,null,values);
    }
    public Cursor DisplayAll(){
        SQLiteDatabase db = getReadableDatabase(); // Use getReadableDatabase() instead of myDB
        String query = "SELECT * FROM " + TABLENAME;
        return db.rawQuery(query, null);
    }
    public long Update(int id,String name,String phone){
        ContentValues values=new ContentValues();
        values.put(Id,id);
        values.put(Name,name);
        values.put(Phone,phone);
        String where=Id+" = "+id;
        return  myDB.update(TABLENAME,values,where,null);
    }
    public long Delete(int id){
        String where=Id+ " = "+ id;
        return  myDB.delete(TABLENAME,where,null);
    }
}
