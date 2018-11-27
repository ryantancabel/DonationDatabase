package ict376.murdoch.edu.au.donationdatabase;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class DonationProvider extends ContentProvider {

    public static final String DATABASE_NAME = "DonationsDatabase.db";
    public static final int DATABASE_VERSION = 1;


    public DBHelper mydbHelper;


    @Override
    public boolean onCreate() {

        Log.d(TAG, "onCreate: WE ARE IN CREATE");



        return true;
    }



    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {

        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {


        mydbHelper = new DBHelper(getContext());
        SQLiteDatabase db = mydbHelper.getWritableDatabase();

        long id = db.insert("donations", null, contentValues);

        if (id > 0) {
            Log.d(TAG, "insert: done" + id);
        } else {
            throw new android.database.SQLException("Failed to insert row into: " + uri);
        }


        return null;

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
