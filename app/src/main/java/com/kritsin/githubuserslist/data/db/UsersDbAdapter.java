package com.kritsin.githubuserslist.data.db;

import com.kritsin.githubuserslist.model.User;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.List;
 ;

public class UsersDbAdapter {
    public static final String  ROW_ID = "_id",
            ID = "id",
            LOGIN = "login",
            AVATAR_URL = "avatar_url";

    public static final String DATABASE_TABLE = "users";
    private final Context mContext;

    private SQLiteDatabase mDb;

    public UsersDbAdapter(Context context) {
        DbAdapter dbAdapter = new DbAdapter(context);
        this.mDb = dbAdapter.open();
        this.mContext = context;
    }

    public void close(){
        if(mDb.isOpen())
            this.mDb.close();
    }

    public void addUsers(List<User> list){
        try {
            mDb.beginTransaction();
            for(User user:list) {
                ContentValues initialValues = new ContentValues();
                initialValues.put(ID, user.getId());
                initialValues.put(AVATAR_URL, user.getAvatarUrl());
                initialValues.put(LOGIN, user.getLogin());
                this.mDb.insertWithOnConflict(DATABASE_TABLE, null, initialValues, SQLiteDatabase.CONFLICT_REPLACE);
            }
            mDb.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mDb.inTransaction()) {
                mDb.endTransaction();
            }
        }
    }

    public boolean deleteAllUsers() {
        boolean result = false;
        try {
            mDb.beginTransaction();
            result = this.mDb.delete(DATABASE_TABLE, null, null) > 0;
            mDb.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mDb.inTransaction()) {
                mDb.endTransaction();
            }
        }
        return result;
    }

    public Cursor getAllUsersCursor() {
        Cursor result = this.mDb.query(DATABASE_TABLE, null, null, null, null, null, ID);
        return result;
    }


    public  static User fromCursor(Cursor cursor, int position){
        cursor.moveToPosition(position);
        final int ROW_ID_IND = cursor.getColumnIndex(ROW_ID);
        final int ID_IND = cursor.getColumnIndex(ID);
        final int AVATAR_IND = cursor.getColumnIndex(AVATAR_URL);
        final int LOGIN_IND = cursor.getColumnIndex(LOGIN);

        long rowId = cursor.getLong(ROW_ID_IND);
        long id = cursor.getLong(ID_IND);
        String avatarUrl = cursor.getString(AVATAR_IND);
        String login = cursor.getString(LOGIN_IND);


        User result = new User();
        result.setRowId(rowId);
        result.setId(id);
        result.setAvatarUrl(avatarUrl);
        result.setLogin(login);

        return result;
    }


}
