package com.example.healthapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PhysicalData.db";
    private static final int DATABASE_VERSION = 3;

    static final String TABLE_NAME = "PhysicalData";
    static final String COLUMN_EMAIL = "email";
    static final String COLUMN_WEIGHT = "weight";
    static final String COLUMN_AGE = "age";
    static final String COLUMN_HEIGHT = "height";
    static final String COLUMN_NECK_CIRCUMFERENCE = "neckCircumference";
    static final String COLUMN_WAIST_CIRCUMFERENCE = "waistCircumference";
    static final String COLUMN_GENDER = "gender";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_EMAIL + " TEXT," +
                    COLUMN_WEIGHT + " INTEGER," +
                    COLUMN_AGE + " INTEGER," +
                    COLUMN_HEIGHT + " INTEGER," +
                    COLUMN_NECK_CIRCUMFERENCE + " INTEGER," +
                    COLUMN_WAIST_CIRCUMFERENCE + " INTEGER," +
                    COLUMN_GENDER + " TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

