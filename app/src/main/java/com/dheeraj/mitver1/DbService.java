package com.dheeraj.mitver1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbService extends  SQLiteOpenHelper{

        private Context context;
        public static final String DB_NAME = "db10";
        public static final int DB_VERSION = 1;
        public static final String TABLE_NAME = "Session";
        public static final String COL_ID = "Id";
        public static final String COL_SESSION_ID = "Session_id";

        public static final String TABLE_CREATE_QUERY = "CREATE TABLE "+TABLE_NAME+" ( "+COL_ID+" INTEGER, "+COL_SESSION_ID+" TEXT) ";
        public static final String TABLE_UPGRADE_QUERY = "DROP TABLE IF EXISTS "+TABLE_NAME;
        public DbService(Context context){
            super(context,DB_NAME,null,DB_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(TABLE_CREATE_QUERY);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(TABLE_UPGRADE_QUERY);
            onCreate(db);
        }

    public String StoreSession(String sessionid){
        try{
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_SESSION_ID,sessionid);
            sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
            sqLiteDatabase.close();
            return "Success";
        }
        catch(Exception exe){
            return "Exception";
        }
    }


    public String SessionFetch(){
        try
        {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            //Example on query
            //Cursor cursor = sqLiteDatabase.query(TABLE_NAME,null,COL_EMAIL+" = ?",new String[]{email},null,null,null);
            // using raw query

            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME,null);
            cursor.moveToFirst();
            String sessionid = cursor.getString(cursor.getColumnIndex(COL_SESSION_ID));
            return sessionid;
        }
        catch(Exception exe)
        {

            return "nodata";
        }

    }
   }
