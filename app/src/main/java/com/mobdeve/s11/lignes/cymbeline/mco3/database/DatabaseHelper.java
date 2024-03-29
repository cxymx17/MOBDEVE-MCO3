package com.mobdeve.s11.lignes.cymbeline.mco3.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "UserInfo.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "user_table";
    private static final String SLEEP_TABLE_NAME = "sleep_table";
    private static final String BRUSH_TABLE_NAME = "brush_table";
    public static final String COL_1 = "ID"; // Added ID column
    public static final String COL_2 = "USERNAME";
    public static final String COL_3 = "PASSWORD";
    public static final String COL_4 = "EMAIL";
    public static final String COL_5 = "BIRTHDAY";
    public static final String COL_SLEEP_DATE = "SLEEP_DATE";
    public static final String COL_SLEEP_HOURS = "SLEEP_HOURS";
    public static final String COL_BRUSH_DATE = "BRUSH_DATE";
    public static final String COL_BRUSH_COUNT = "BRUSH_COUNT";

    private static final String WATER_TABLE_NAME = "water_table";
    private static final String COL_WATER_DATE = "WATER_DATE";
    private static final String COL_WATER_INTAKE = "WATER_INTAKE";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create user_table
        String createUserTableQuery = "CREATE TABLE " + TABLE_NAME + " (" + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_2 + " TEXT, " + COL_3 + " TEXT, " + COL_4 + " TEXT, " + COL_5 + " TEXT)";
        db.execSQL(createUserTableQuery);

        // Create sleep_table
        String createSleepTableQuery = "CREATE TABLE " + SLEEP_TABLE_NAME + " (" + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_2 + " TEXT, " + COL_SLEEP_DATE + " TEXT, " + COL_SLEEP_HOURS + " TEXT)";
        db.execSQL(createSleepTableQuery);

        // Create brush_table
        String createBrushTableQuery = "CREATE TABLE " + BRUSH_TABLE_NAME + " (" + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_2 + " TEXT, " + COL_BRUSH_DATE + " TEXT, " + COL_BRUSH_COUNT + " INTEGER)";
        db.execSQL(createBrushTableQuery);

        // Create water_table
        String createWaterTableQuery = "CREATE TABLE " + WATER_TABLE_NAME + " (" + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_2 + " TEXT, " + COL_WATER_DATE + " TEXT, " + COL_WATER_INTAKE + " INTEGER)";
        db.execSQL(createWaterTableQuery);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SLEEP_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BRUSH_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + WATER_TABLE_NAME);
        onCreate(db);
    }

    // Method to delete sleep hours for a specific date
    public boolean deleteSleepHours(String username, String sleepDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COL_2 + " = ? AND " + COL_SLEEP_DATE + " = ?";
        String[] selectionArgs = {username, sleepDate};
        int rowsDeleted = db.delete(SLEEP_TABLE_NAME, selection, selectionArgs);
        return rowsDeleted > 0;
    }



    // Method to insert or update brush count for a specific date
    public boolean updateOrInsertBrushCount(String username, String brushDate, int brushCount) {
        SQLiteDatabase db = this.getWritableDatabase();

        // First, delete the existing brush count for the specified date and username
        deleteBrushCount(username, brushDate);

        // Then, insert the new brush count
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, username);
        contentValues.put(COL_BRUSH_DATE, brushDate);
        contentValues.put(COL_BRUSH_COUNT, brushCount);
        long result = db.insert(BRUSH_TABLE_NAME, null, contentValues);

        if (result != -1) {
            Log.d("DatabaseHelper", "Brush count updated successfully for date: " + brushDate + ", username: " + username);
            return true;
        } else {
            Log.e("DatabaseHelper", "Failed to update brush count for date: " + brushDate + ", username: " + username);
            return false;
        }
    }

    // Method to delete brush count for a specific date
    public boolean deleteBrushCount(String username, String brushDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COL_2 + " = ? AND " + COL_BRUSH_DATE + " = ?";
        String[] selectionArgs = {username, brushDate};
        int rowsDeleted = db.delete(BRUSH_TABLE_NAME, selection, selectionArgs);
        return rowsDeleted > 0;
    }

    // Method to delete water intake for a specific date
    public boolean deleteWaterIntake(String username, String waterDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COL_2 + " = ? AND " + COL_WATER_DATE + " = ?";
        String[] selectionArgs = {username, waterDate};
        int rowsDeleted = db.delete(WATER_TABLE_NAME, selection, selectionArgs);
        return rowsDeleted > 0;
    }


    // Method to retrieve brush count for a specific date
    public int getBrushCount(String username, String brushDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL_BRUSH_COUNT};
        String selection = COL_2 + " = ? AND " + COL_BRUSH_DATE + " = ?";
        String[] selectionArgs = {username, brushDate};
        try (Cursor cursor = db.query(BRUSH_TABLE_NAME, columns, selection, selectionArgs, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(COL_BRUSH_COUNT);
                if (columnIndex != -1) {
                    int brushCount = cursor.getInt(columnIndex);
                    Log.d("DatabaseHelper", "Retrieved brush count: " + brushCount + " for brushDate: " + brushDate + ", username: " + username);
                    return brushCount;
                } else {
                    Log.e("DatabaseHelper", "Column COL_BRUSH_COUNT does not exist in the cursor");
                }
            } else {
                Log.d("DatabaseHelper", "No brush count found for brushDate: " + brushDate + ", username: " + username);
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error retrieving brush count: " + e.getMessage());
        }
        return -1;
    }


    public boolean insertData(String username, String password, String email, String bday) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, username);
        contentValues.put(COL_3, password);
        contentValues.put(COL_4, email);
        contentValues.put(COL_5, bday);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    private boolean isValidDateFormat(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
            sdf.setLenient(false);
            sdf.parse(dateStr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        try (Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_2 + " = ? AND " + COL_3 + " = ?", new String[]{username, password})) {
            return cursor.getCount() > 0;
        }
    }

    public boolean checkExistingUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        try (Cursor cursor = db.query(TABLE_NAME, null, COL_2 + " = ?", new String[]{username}, null, null, null)) {
            return cursor != null && cursor.getCount() > 0;
        }
    }

    public String getUserName(String loggedInUsername) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL_2};
        String selection = COL_2 + " = ?";
        String[] selectionArgs = {loggedInUsername};
        try (Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(COL_2);
                if (columnIndex != -1) {
                    return cursor.getString(columnIndex);
                } else {                    Log.e("DatabaseHelper", "Column COL_2 does not exist in the cursor");
                }
            } else {
                Log.e("DatabaseHelper", "Cursor is null or empty");
            }
        }
        return "";
    }

    // Method to retrieve current user details
    public Cursor getUserDetails(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {COL_2, COL_3, COL_4, COL_5};
        String selection = COL_2 + " = ?";
        String[] selectionArgs = {username};
        return db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
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

    // Method to insert or update sleep hours for a specific date
    // Method to insert or update sleep hours for a specific date
    public boolean updateOrInsertSleepHours(String username, String sleepDate, String sleepHours) {
        SQLiteDatabase db = this.getWritableDatabase();

        // First, delete the existing sleep hours for the specified date and username
        deleteSleepHours(username, sleepDate);

        // Then, insert the new sleep hours
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, username);
        contentValues.put(COL_SLEEP_DATE, sleepDate);
        contentValues.put(COL_SLEEP_HOURS, sleepHours);
        long result = db.insert(SLEEP_TABLE_NAME, null, contentValues);

        if (result != -1) {
            Log.d("DatabaseHelper", "Sleep hours updated successfully for date: " + sleepDate + ", username: " + username);
            return true;
        } else {
            Log.e("DatabaseHelper", "Failed to update sleep hours for date: " + sleepDate + ", username: " + username);
            return false;
        }
    }



    // Method to retrieve sleep hours for a specific date
    // Modify the getSleepHours method to accept only the sleep date
    public String getSleepHours(String sleepDate, String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL_SLEEP_HOURS};
        String selection = COL_SLEEP_DATE + " = ? AND " + COL_2 + " = ?";
        String[] selectionArgs = {sleepDate, username};
        try (Cursor cursor = db.query(SLEEP_TABLE_NAME, columns, selection, selectionArgs, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(COL_SLEEP_HOURS);
                if (columnIndex != -1) {
                    String sleepHours = cursor.getString(columnIndex);
                    Log.d("DatabaseHelper", "Retrieved sleep hours: " + sleepHours);
                    return sleepHours;
                } else {
                    Log.e("DatabaseHelper", "Column COL_SLEEP_HOURS does not exist in the cursor");
                }
            } else {
                Log.d("DatabaseHelper", "No sleep hours found for date: " + sleepDate + ", username: " + username);
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error retrieving sleep hours: " + e.getMessage());
        }
        return null;
    }

    // Method to retrieve water intake for a specific date
    public int getWaterIntake(String waterDate, String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL_WATER_INTAKE};
        String selection = COL_WATER_DATE + " = ? AND " + COL_2 + " = ?";
        String[] selectionArgs = {waterDate, username};
        try (Cursor cursor = db.query(WATER_TABLE_NAME, columns, selection, selectionArgs, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(COL_WATER_INTAKE);
                if (columnIndex != -1) {
                    int waterIntake = cursor.getInt(columnIndex);
                    Log.d("DatabaseHelper", "Retrieved water intake: " + waterIntake);
                    return waterIntake;
                } else {
                    Log.e("DatabaseHelper", "Column COL_WATER_INTAKE does not exist in the cursor");
                }
            } else {
                Log.d("DatabaseHelper", "No water intake found for date: " + waterDate + ", username: " + username);
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error retrieving water intake: " + e.getMessage());
        }
        return 0;
    }

    // Method to add water intake for a specific date
    public boolean addWaterIntake(String username, String waterDate, int intakeAmount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, username);
        contentValues.put(COL_WATER_DATE, waterDate);
        contentValues.put(COL_WATER_INTAKE, intakeAmount);
        long result = db.insert(WATER_TABLE_NAME, null, contentValues);
        return result != -1;
    }
}




