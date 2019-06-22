package com.creativeapp.rllll;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper{
    private static int database_version = 1;
    private static String database_name = "sample_db";
    private static String table_name = "sample_table";
    private static String coloumn_id = "id";
    private static String coloumn_data = "data";
    private static String coloumn_timestamp = "timestamp";

    public DatabaseHelper(Context context) {
        super(context, database_name, null, database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + table_name + "(" + coloumn_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + coloumn_data + " TEXT,"
                + coloumn_timestamp + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                + ")" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + table_name);
        onCreate(sqLiteDatabase);
    }

    public long insertData(String data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(coloumn_data,data);
        long id = db.insert(table_name,null, cv);
        db.close();
        return id;
    }

    public int updateData(Data data) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(coloumn_data, data.getData());

        // updating row
        return db.update(table_name, cv, "id" + " = ?",
                new String[]{String.valueOf(data.getId())});
    }
    public Data getData(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(table_name,
                new String[]{coloumn_id, coloumn_data, coloumn_timestamp},
                coloumn_id + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Data data = new Data(
                cursor.getInt(cursor.getColumnIndex(coloumn_id)),
                cursor.getString(cursor.getColumnIndex(coloumn_data)),
                cursor.getString(cursor.getColumnIndex(coloumn_timestamp)));

        // close the db connection
        cursor.close();

        return data;
    }

    public List<Data> getAllDatas() {
        List<Data> Datas = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + table_name + " ORDER BY " +
                coloumn_timestamp + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Data data = new Data();
                data.setId(cursor.getInt(cursor.getColumnIndex(coloumn_id)));
                data.setData(cursor.getString(cursor.getColumnIndex(coloumn_data)));
                data.setTimeStamp(cursor.getString(cursor.getColumnIndex(coloumn_timestamp)));

                Datas.add(data);
            } while (cursor.moveToNext());
        }
        // close db connection
        db.close();
        // return notes list
        return Datas;
    }

    public int getDataCount() {
        String countQuery = "SELECT  * FROM " + table_name;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        // return count
        return count;
    }

    public void deleteData(Data data) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table_name, coloumn_id + " = ?",
                new String[]{String.valueOf(data.getId())});
        db.close();
    }
}