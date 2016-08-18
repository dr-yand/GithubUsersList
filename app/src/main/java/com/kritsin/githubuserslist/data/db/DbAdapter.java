package com.kritsin.githubuserslist.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbAdapter extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "githubusers.db";

    public static final int DATABASE_VERSION = 4;

    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE IF NOT EXISTS "+ UsersDbAdapter.DATABASE_TABLE+" ("
                    + UsersDbAdapter.ROW_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + UsersDbAdapter.ID+" INTEGER, "
                    + UsersDbAdapter.LOGIN+" TEXT, "
                    + UsersDbAdapter.AVATAR_URL+" TEXT "
                    + ", UNIQUE("+UsersDbAdapter.ID+"));" ;

    public DbAdapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UsersDbAdapter.DATABASE_TABLE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public SQLiteDatabase open(){
        return getWritableDatabase();
    }

}
