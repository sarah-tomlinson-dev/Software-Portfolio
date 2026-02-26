package com.example.sarahtomlinson_inventoryfinalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import java.util.Objects;

public class SQLiteDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "UsersData.DB";
    public static final String USERS_TABLE_NAME = "UsersTable";
    public static final String COLUMN_0_ID = "id";
    public static final String COLUMN_1_NAME = "name";
    public static final String COLUMN_2_PASSWORD = "password";

    private static final String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS " +
            USERS_TABLE_NAME + " (" +
            COLUMN_0_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMN_1_NAME + " VARCHAR, " +
            COLUMN_2_PASSWORD + " VARCHAR);";

    public SQLiteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE_NAME);
        onCreate(db);
    }

    /**
     * Database CRUD (Create, Read, Update, Delete) Operations
     */

    // Add user to database
    public void createUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_1_NAME, user.getUserName());
        values.put(COLUMN_2_PASSWORD, user.getUserPass());

        db.insert(USERS_TABLE_NAME, null, values);
        db.close();
    }

    // Read user from Database
    public User readUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(USERS_TABLE_NAME,
                new String[] { COLUMN_0_ID, COLUMN_1_NAME, COLUMN_2_PASSWORD }, COLUMN_0_ID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        User user = new User(Integer.parseInt(Objects.requireNonNull(cursor).getString(0)),
                cursor.getString(1), cursor.getString(2));

        cursor.close();

        return user;
    }

    // Update user in database
    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_1_NAME, user.getUserName());
        values.put(COLUMN_2_PASSWORD, user.getUserPass());

        return db.update(USERS_TABLE_NAME, values, COLUMN_0_ID + " = ?", new String[] { String.valueOf(user.getId()) });
    }

    // Delete user from database
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(USERS_TABLE_NAME, COLUMN_0_ID + " = ?", new String[] { String.valueOf(user.getId()) });
        db.close();
    }

}