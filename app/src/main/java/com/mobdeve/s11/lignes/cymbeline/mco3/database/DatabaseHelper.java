package com.mobdeve.s11.lignes.cymbeline.mco3.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "UserInfo.db";
    private static final String TABLE_NAME = "user_table";
    public static final String COL_2 = "USERNAME";
    public static final String COL_3 = "PASSWORD";
    public static final String COL_4 = "EMAIL";
    public static final String COL_5 = "BIRTHDAY";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,USERNAME TEXT,PASSWORD TEXT, EMAIL TEXT, BIRTHDAY TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String username, String password, String email, String bday) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, username);
        contentValues.put(COL_3, password);
        contentValues.put(COL_4, email);

        // Validate and format birthday
        String formattedBday = validateAndFormatBday(bday);
        contentValues.put(COL_5, formattedBday);

        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    private String validateAndFormatBday(String bday) {
        // Regular expression for mm/dd/yyyy format
        String regex = "^(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])/(19|20)\\d\\d$";

        // Check if the birthday matches the expected format
        if (bday.matches(regex)) {
            return bday;
        } else {
            throw new IllegalArgumentException("Birthday must be in the format mm/dd/yyyy");
        }
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_2 + " = ? AND " + COL_3 + " = ?", new String[]{username, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
    public boolean checkExistingUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null,
                COL_2 + " = ?",
                new String[]{username}, null, null, null);
        boolean exists = cursor != null && cursor.getCount() > 0;
        if (cursor != null) {
            cursor.close();
        }
        return exists;
    }

    public String getUserName(String loggedInUsername) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL_2};
        String selection = COL_2 + " = ?";
        String[] selectionArgs = {loggedInUsername};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        String username = "";

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COL_2);
            if (columnIndex != -1) {
                username = cursor.getString(columnIndex);
            } else {
                Log.e("DatabaseHelper", "Column COL_2 does not exist in the cursor");
            }
            cursor.close();
        } else {
            Log.e("DatabaseHelper", "Cursor is null or empty");
        }

        return username;
    }
    // Method to retrieve current user details
    public Cursor getUserDetails() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    // Method to update user details
    public boolean updateUserDetails(String username, String password, String email, String bday) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_3, password);
        contentValues.put(COL_4, email);
        contentValues.put(COL_5, bday);

        int rowsAffected = db.update(TABLE_NAME, contentValues, COL_2 + "=?", new String[]{username});
        return rowsAffected > 0;
    }
}
