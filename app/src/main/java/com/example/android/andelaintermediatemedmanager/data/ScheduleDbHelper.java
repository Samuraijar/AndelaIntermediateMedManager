package com.example.android.andelaintermediatemedmanager.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.os.Build.VERSION_CODES.M;
import static com.example.android.andelaintermediatemedmanager.data.ScheduleContract.ScheduleEntry.COLUMN_TIMESTAMP;
import static com.example.android.andelaintermediatemedmanager.data.ScheduleContract.ScheduleEntry.TABLE_NAME;

/**
 * Created by NORMAL on 4/15/2018.
 */

public class ScheduleDbHelper extends SQLiteOpenHelper {


    // The database name
    private static final String DATABASE_NAME = "medmanager.db";

    // If you change the database schema, you must increment the database version
    private static final int DATABASE_VERSION = 1;

    // Constructor
    public ScheduleDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Create a table to hold waitlist data
        final String SQL_CREATE_WAITLIST_TABLE = "CREATE TABLE " + ScheduleContract.ScheduleEntry.TABLE_NAME + " (" +
                ScheduleContract.ScheduleEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ScheduleContract.ScheduleEntry.COLUMN_MED_DESCRIPTION + " TEXT NOT NULL, " +
                ScheduleContract.ScheduleEntry.COLUMN_MED_INSTRUCTION + " TEXT NOT NULL, " +
                ScheduleContract.ScheduleEntry.COLUMN_USAGE_STATUS + " TEXT NOT NULL, " +
                ScheduleContract.ScheduleEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";

        sqLiteDatabase.execSQL(SQL_CREATE_WAITLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // For now simply drop the table and create a new one. This means if you change the
        // DATABASE_VERSION the table will be dropped.
        // In a production app, this method might be modified to ALTER the table
        // instead of dropping it, so that existing data is not deleted.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ScheduleContract.ScheduleEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertInto(ContentValues cv) {
        SQLiteDatabase db = this.getWritableDatabase();
        long results = db.insert(ScheduleContract.ScheduleEntry.TABLE_NAME, null, cv);
        if (results == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor selectAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from " + ScheduleContract.ScheduleEntry.TABLE_NAME;
        Cursor result = db.rawQuery(query, null);
        return result;
    }

    public Cursor updateSchedule(MedData medData) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE "
                + ScheduleContract.ScheduleEntry.TABLE_NAME
                + " SET "
                + ScheduleContract.ScheduleEntry.COLUMN_MED_DESCRIPTION + "='" + medData.getMedDescription()
                + ScheduleContract.ScheduleEntry.COLUMN_MED_INSTRUCTION + "='" + medData.getMedInstruction()
                + ScheduleContract.ScheduleEntry.COLUMN_USAGE_STATUS + "='" + medData.getUsageStatus()
                + "' WHERE " + ScheduleContract.ScheduleEntry._ID + "='" + medData.getScheduleId() + "'";
        Cursor results = db.rawQuery(query, null);
        return results;
    }

    public Cursor deleteSchedule (int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + ScheduleContract.ScheduleEntry.TABLE_NAME
                + " WHERE "
                + ScheduleContract.ScheduleEntry._ID + "='"
                + id + "'";
        Cursor result = db.rawQuery(query, null);
        return result;

    }
}
