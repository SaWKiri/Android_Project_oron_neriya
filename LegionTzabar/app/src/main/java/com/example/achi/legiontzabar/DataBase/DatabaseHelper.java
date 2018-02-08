package com.example.achi.legiontzabar.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kiri on 04/02/2018.
 */

public class DatabaseHelper  extends SQLiteOpenHelper{

    public static int databaseEmpty = 0;

    public DatabaseHelper(Context context) {
        super(context, DBHelper.DATABASE_NAME, null, DBHelper.DATABASE_VESTION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + DBHelper.SOLDIERS_TABLE + "( " + DBHelper.SOLDIERS_TABLE_COL_MID + " LONG PRIMARY KEY ,"
                                                                    + DBHelper.SOLDIERS_TABLE_COL_PID + " LONG UNIQUE, "
                                                                    + DBHelper.SOLDIERS_TABLE_COL_FIRST_NAME +  " TEXT, "
                                                                    + DBHelper.SOLDIERS_TABLE_COL_LAST_NAME + " TEXT, "
                                                                    + DBHelper.SOLDIERS_TABLE_COL_GENDER + " TEXT, "
                                                                    + DBHelper.SOLDIERS_TABLE_COL_PHONE_NUMBER + " TEXT, "
                                                                    + DBHelper.SOLDIERS_TABLE_COL_EMAIL + " TEXT, "
                                                                    + DBHelper.SOLDIERS_TABLE_COL_ADDRESS + " TEXT, "
                                                                    + DBHelper.SOLDIERS_TABLE_COL_SERVICE_DAYS + " INTEGER, "
                                                                    + DBHelper.SOLDIERS_TABLE_COL_RANK + " TEXT, "
                                                                    + DBHelper.SOLDIERS_TABLE_COL_OCCUPATION + " TEXT, "
                                                                    + DBHelper.SOLDIERS_TABLE_COL_IN_MATZEVA + " TEXT, "
                                                                    + DBHelper.SOLDIERS_TABLE_COL_COMPANY + " TEXT, "
                                                                    + DBHelper.SOLDIERS_TABLE_COL_MILITARY_OCCUPATION +" TEXT, "
                                                                    + DBHelper.SOLDIERS_TABLE_COL_IMAGE +" BLOB"  +" );");


        db.execSQL("create table " + DBHelper.QUALIFICATION_TABLE + "( " + DBHelper.QUALIFICATION_TABLE_COL_MID + " LONG, "
                                                                         + DBHelper.QUALIFICATION_TABLE_COL_QID + " LONG, "
                                                                         + DBHelper.QUALIFICATION_TABLE_COL_NAME +  " TEXT, "
                                                                         + DBHelper.QUALIFICATION_TABLE_COL_START_DATE + " TEXT, "
                                                                         + DBHelper.QUALIFICATION_TABLE_COL_DURATION + " TEXT,"
                                                                         + "PRIMARY KEY( " +  DBHelper.QUALIFICATION_TABLE_COL_MID +","+ DBHelper.QUALIFICATION_TABLE_COL_QID +" ));");

        db.execSQL("create table " + DBHelper.EVENTS_TABLE + "( " + DBHelper.EVENTS_TABLE_ID + " LONG PRIMARY KEY,"
                                                                  + DBHelper.EVENTS_TABLE_KIND+ " TEXT, "
                                                                  + DBHelper.EVENTS_TABLE_START_DATE +  " TEXT, "
                                                                  + DBHelper.EVENTS_TABLE_PERIOD + " TEXT " + " );");


        db.execSQL("create table " + DBHelper.FILE_TABLE + "( " + DBHelper.FILE_TABLE_EVENT_ID + " LONG,"
                                                                + DBHelper.FILE_TABLE_FILE1_BLOB + " BLOB, "
                                                                + DBHelper.FILE_TABLE_FILE2_BLOB +  " BLOB, "
                                                                + DBHelper.FILE_TABLE_FILE3_BLOB +  " BLOB, "
                                                                + "FOREIGN KEY(" + DBHelper.FILE_TABLE_EVENT_ID + ") REFERENCES " + DBHelper.EVENTS_TABLE +"("+ DBHelper.EVENTS_TABLE_ID +")" + " );");

        db.execSQL("create table " + DBHelper.PARTICIPANTS_TABLE + "(" + DBHelper.PARTICIPANTS_TABLE_EVENT_ID + " LONG,"
                                                                       + DBHelper.PARTICIPANTS_TABLE_MILITARY_ID + " LONG,"
                                                                       + "PRIMARY KEY(" +DBHelper.PARTICIPANTS_TABLE_EVENT_ID +"," + DBHelper.PARTICIPANTS_TABLE_MILITARY_ID +"));" );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + DBHelper.SOLDIERS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DBHelper.QUALIFICATION_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DBHelper.EVENTS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DBHelper.FILE_TABLE);
        onCreate(db);

    }


    public boolean insertNewSolider(long mid, long pid, String first_name, String last_name,String gender, String phone_number
    ,String email, String full_address, int service_days,String rank, String occupation, String in_matzeva,String company, String military_occupation)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.SOLDIERS_TABLE_COL_MID, mid);
        contentValues.put(DBHelper.SOLDIERS_TABLE_COL_PID, pid);
        contentValues.put(DBHelper.SOLDIERS_TABLE_COL_FIRST_NAME,first_name);
        contentValues.put(DBHelper.SOLDIERS_TABLE_COL_LAST_NAME,last_name);
        contentValues.put(DBHelper.SOLDIERS_TABLE_COL_GENDER,gender);
        contentValues.put(DBHelper.SOLDIERS_TABLE_COL_PHONE_NUMBER,phone_number);
        contentValues.put(DBHelper.SOLDIERS_TABLE_COL_EMAIL,email);
        contentValues.put(DBHelper.SOLDIERS_TABLE_COL_ADDRESS,full_address);
        contentValues.put(DBHelper.SOLDIERS_TABLE_COL_SERVICE_DAYS,service_days);
        contentValues.put(DBHelper.SOLDIERS_TABLE_COL_RANK,rank);
        contentValues.put(DBHelper.SOLDIERS_TABLE_COL_OCCUPATION,occupation);
        contentValues.put(DBHelper.SOLDIERS_TABLE_COL_IN_MATZEVA,in_matzeva);
        contentValues.put(DBHelper.SOLDIERS_TABLE_COL_COMPANY,company);
        contentValues.put(DBHelper.SOLDIERS_TABLE_COL_MILITARY_OCCUPATION, military_occupation );

        long result = db.insert(DBHelper.SOLDIERS_TABLE,null ,contentValues);

        if(result == -1)
        {
            return false;
        }
        databaseEmpty++;
        return true;
    }

    public boolean insertNewSolider(long mid, long pid, String first_name, String last_name,String gender, String phone_number
            ,String email, String full_address, int service_days,String rank, String occupation, String in_matzeva,String company, String military_occupation, byte[] image)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.SOLDIERS_TABLE_COL_MID, mid);
        contentValues.put(DBHelper.SOLDIERS_TABLE_COL_PID, pid);
        contentValues.put(DBHelper.SOLDIERS_TABLE_COL_FIRST_NAME,first_name);
        contentValues.put(DBHelper.SOLDIERS_TABLE_COL_LAST_NAME,last_name);
        contentValues.put(DBHelper.SOLDIERS_TABLE_COL_GENDER,gender);
        contentValues.put(DBHelper.SOLDIERS_TABLE_COL_PHONE_NUMBER,phone_number);
        contentValues.put(DBHelper.SOLDIERS_TABLE_COL_EMAIL,email);
        contentValues.put(DBHelper.SOLDIERS_TABLE_COL_ADDRESS,full_address);
        contentValues.put(DBHelper.SOLDIERS_TABLE_COL_SERVICE_DAYS,service_days);
        contentValues.put(DBHelper.SOLDIERS_TABLE_COL_RANK,rank);
        contentValues.put(DBHelper.SOLDIERS_TABLE_COL_OCCUPATION,occupation);
        contentValues.put(DBHelper.SOLDIERS_TABLE_COL_IN_MATZEVA,in_matzeva);
        contentValues.put(DBHelper.SOLDIERS_TABLE_COL_COMPANY,company);
        contentValues.put(DBHelper.SOLDIERS_TABLE_COL_MILITARY_OCCUPATION, military_occupation );
        contentValues.put(DBHelper.SOLDIERS_TABLE_COL_IMAGE, image);

        long result = db.insert(DBHelper.SOLDIERS_TABLE,null ,contentValues);

        if(result == -1)
        {
            return false;
        }
        databaseEmpty++;
        return true;
    }

    public boolean insertNewQualification(long mid, int qid, String name, String startDate, int durationInMonth){

        SQLiteDatabase db = this.getWritableDatabase();

        if(checkUniqeConstraint(db,mid, DBHelper.QUALIFICATION_TABLE,DBHelper.QUALIFICATION_TABLE_COL_MID))
        {
            return false;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.QUALIFICATION_TABLE_COL_MID,mid);
        contentValues.put(DBHelper.QUALIFICATION_TABLE_COL_QID,qid);
        contentValues.put(DBHelper.QUALIFICATION_TABLE_COL_NAME,name);
        contentValues.put(DBHelper.QUALIFICATION_TABLE_COL_START_DATE,startDate);
        contentValues.put(DBHelper.QUALIFICATION_TABLE_COL_DURATION,durationInMonth);

        long result = db.insert(DBHelper.QUALIFICATION_TABLE, null, contentValues);

        if(result == -1)
        {
            return  false;
        }
        return true;
    }

    private boolean checkUniqeConstraint(SQLiteDatabase db, Long mid, String tableName , String TableColumn) {
        Cursor cursor = db.rawQuery("select " + TableColumn + " from " + tableName + " where " + TableColumn + " = " + mid,null);

        if(!cursor.moveToFirst())
        {
            return false;
        }

        if(cursor.getString(0).equals(String.valueOf(mid)))
        {
            return true;
        }
        return false;
    }

    public boolean insertRowByTable(String[] tableData , String tableName)
    {
        switch (tableName) {
            case DBHelper.SOLDIERS_TABLE: {
                return insertNewSolider(Long.parseLong(tableData[0]), Long.parseLong(tableData[1]),
                        tableData[2],tableData[3],tableData[4],tableData[5],tableData[6],tableData[7],
                        Integer.parseInt(tableData[8]),tableData[9],tableData[10],tableData[11],tableData[12],tableData[13]);

            }
            case  DBHelper.QUALIFICATION_TABLE:{
                return insertNewQualification(Long.parseLong(tableData[0]),Integer.parseInt(tableData[1]),
                        tableData[2],tableData[3],Integer.parseInt(tableData[4]));
            }
        }

        return false;
    }


    public Cursor getAllSoldiers()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from " + DBHelper.SOLDIERS_TABLE,null);
    }

    public Cursor getSoldiersByMid(String mid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from " + DBHelper.SOLDIERS_TABLE + " where " + DBHelper.SOLDIERS_TABLE_COL_MID + "= " + mid  ,null);
    }

    public boolean isDbEmpty()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = getAllSoldiers();

        if(!cursor.moveToFirst())
        {
            return true;
        }
        return false;
    }

    public Cursor getAllQualification() {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from " + DBHelper.QUALIFICATION_TABLE, null);
    }

    public String getCompanyByMid(String mid) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+ DBHelper.SOLDIERS_TABLE +" where " + DBHelper.SOLDIERS_TABLE_COL_MID +" = " + mid, null);

        if(!cursor.moveToFirst())
        {
            return "";
        }
        String res = cursor.getString(cursor.getColumnIndex(DBHelper.SOLDIERS_TABLE_COL_COMPANY));
        return  res;
    }

    public String getSoldierFullNameByMid(String mid) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+ DBHelper.SOLDIERS_TABLE +" where " + DBHelper.SOLDIERS_TABLE_COL_MID +" = " + mid, null);

        cursor.moveToFirst();

        String first_name = cursor.getString(cursor.getColumnIndex(DBHelper.SOLDIERS_TABLE_COL_FIRST_NAME));
        String last_name = cursor.getString(cursor.getColumnIndex(DBHelper.SOLDIERS_TABLE_COL_LAST_NAME));

        return first_name + " " + last_name;
    }

    public boolean insertFiles(String TableName, long eid,byte[] file1, byte[] file2, byte[] file3)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.FILE_TABLE_EVENT_ID, eid);
        contentValues.put(DBHelper.FILE_TABLE_FILE1_BLOB, file1);
        contentValues.put(DBHelper.FILE_TABLE_FILE2_BLOB, file2);
        contentValues.put(DBHelper.FILE_TABLE_FILE3_BLOB, file3);


        long result = db.insert(DBHelper.FILE_TABLE, null ,contentValues);

        if(result == -1 )
        {
            return false;
        }
        return true;

    }

    public Cursor getFileByEid(String eid, String fileColumn) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("Select * from " + DBHelper.FILE_TABLE + " where " + DBHelper.FILE_TABLE_EVENT_ID +" = " + eid ,null );
        return cursor;
    }
}
