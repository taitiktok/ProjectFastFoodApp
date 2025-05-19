package com.example.fastfoodstore.Model.Account;

import android.accounts.Account;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MySqliteDbAccount extends SQLiteOpenHelper {
    public MySqliteDbAccount(@Nullable Context context) {
        super(context,"AccountUser.db",null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE AccountAdmin (" +
                "userName TEXT PRIMARY KEY, " +
                "passWord TEXT NOT NULL)";
        db.execSQL(createTable);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS AccountAdmin");
        onCreate(db);
    }
    public boolean insertAccountAdmin(String userName, String passWord) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userName", userName);
        values.put("passWord", passWord);

        long result = db.insert("AccountAdmin", null, values);
        db.close();
        return result != -1;
    }

    public List<AccountAdmin> getAllAccountAdmin() {
        List<AccountAdmin> accountList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT userName, passWord FROM AccountAdmin", null);

        if (cursor.moveToFirst()) {
            do {
                String user = cursor.getString(0);
                String pass = cursor.getString(1);
                AccountAdmin account = new AccountAdmin(user, pass);
                accountList.add(account);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return accountList;
    }


}
