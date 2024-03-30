package com.mobdeve.s11.lignes.cymbeline.mco3.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "UserInfo.db";
    private static final String TABLE_NAME = "user_table";
    private static final String SLEEP_TABLE_NAME = "sleep_table";
    private static final String BRUSH_TABLE_NAME = "brush_table";
    public static final String COL_2 = "USERNAME";
    public static final String COL_3 = "PASSWORD";
    public static final String COL_4 = "EMAIL";
    public static final String COL_5 = "BIRTHDAY";
    public static final String COL_SLEEP_DATE = "SLEEP_DATE";
    public static final String COL_SLEEP_HOURS = "SLEEP_HOURS";
    public static final String COL_BRUSH_DATE = "BRUSH_DATE";
    public static final String COL_BRUSH_COUNT = "BRUSH_COUNT";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create user_table
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_2 + " TEXT, " + COL_3 + " TEXT, " + COL_4 + " TEXT, " + COL_5 + " TEXT)");

        // Create sleep_table
        db.execSQL("CREATE TABLE " + SLEEP_TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_SLEEP_DATE + " TEXT, " + COL_SLEEP_HOURS + " TEXT)");

        // Create brush_table
        db.execSQL("CREATE TABLE " + BRUSH_TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_BRUSH_DATE + " TEXT, " + COL_BRUSH_COUNT + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SLEEP_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BRUSH_TABLE_NAME);
        onCreate(db);
    }

    // Method to delete sleep hours for a specific date
    public boolean deleteSleepHours(String sleepDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COL_SLEEP_DATE + " = ?";
        String[] selectionArgs = {sleepDate};

        int rowsDeleted = db.delete(SLEEP_TABLE_NAME, selection, selectionArgs);
        return rowsDeleted > 0;
    }

    // Method to delete brush count for a specific date
    public boolean deleteBrushCount(String brushDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COL_BRUSH_DATE + " = ?";
        String[] selectionArgs = {brushDate};

        int rowsDeleted = db.delete(BRUSH_TABLE_NAME, selection, selectionArgs);
        return rowsDeleted > 0;
    }

    // Method to insert or update brush count for a specific date
    public boolean updateOrInsertBrushCount(String brushDate, int brushCount) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_BRUSH_DATE, brushDate);
        contentValues.put(COL_BRUSH_COUNT, brushCount);

        // Check if brush count already exists for the given date
        if (getBrushCount(brushDate) != -1) {
            // Brush count already exists, so update
            int rowsAffected = db.update(BRUSH_TABLE_NAME, contentValues, COL_BRUSH_DATE + "=?", new String[]{brushDate});
            return rowsAffected > 0;
        } else {
            // Brush count doesn't exist, so insert
            long result = db.insert(BRUSH_TABLE_NAME, null, contentValues);
            return result != -1;
        }
    }

    // Method to retrieve brush count for a specific date
    public int getBrushCount(String brushDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL_BRUSH_COUNT};
        String selection = COL_BRUSH_DATE + " = ?";
        String[] selectionArgs = {brushDate};
        Cursor cursor = db.query(BRUSH_TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int brushCount = -1; // Default value if brush count not found

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COL_BRUSH_COUNT);
            if (columnIndex != -1) {
                brushCount = cursor.getInt(columnIndex);
            } else {
                Log.e("DatabaseHelper", "Column COL_BRUSH_COUNT does not exist in the cursor");
            }
            cursor.close();
        }
        return brushCount;
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
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null,
                null);
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

    // Method to insert or update sleep hours for a specific date
    public boolean updateOrInsertSleepHours(String sleepDate, String sleepHours) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Check if sleep hours already exist for the given date
        if (getSleepHours(sleepDate) != null) {
            // Sleep hours already exist, so update
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_SLEEP_DATE, sleepDate);
            contentValues.put(COL_SLEEP_HOURS, sleepHours);

            int rowsAffected = db.update(SLEEP_TABLE_NAME, contentValues, COL_SLEEP_DATE + "=?", new String[]{sleepDate});
            return rowsAffected > 0;
        } else {
            // Sleep hours don't exist, so insert
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_SLEEP_DATE, sleepDate);
            contentValues.put(COL_SLEEP_HOURS, sleepHours);

            long result = db.insert(SLEEP_TABLE_NAME, null, contentValues);
            return result != -1;
        }
    }

    // Method to retrieve sleep hours for a specific date
    public String getSleepHours(String sleepDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL_SLEEP_HOURS};
        String selection = COL_SLEEP_DATE + " = ?";
        String[] selectionArgs = {sleepDate};
        Cursor cursor = db.query(SLEEP_TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        String sleepHours = null;

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COL_SLEEP_HOURS);
            if (columnIndex != -1) {
                sleepHours = cursor.getString(columnIndex);
            } else {
                Log.e("DatabaseHelper", "Column COL_SLEEP_HOURS does not exist in the cursor");
            }
            cursor.close();
        }
        return sleepHours;
    }
}
