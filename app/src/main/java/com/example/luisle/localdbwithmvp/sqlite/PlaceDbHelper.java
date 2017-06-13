package com.example.luisle.localdbwithmvp.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.luisle.localdbwithmvp.data.PlacePersistenceContract.PlaceEntry.COLUMN_NAME_ID;
import static com.example.luisle.localdbwithmvp.data.PlacePersistenceContract.PlaceEntry.COLUMN_NAME_PLACEADDRESS;
import static com.example.luisle.localdbwithmvp.data.PlacePersistenceContract.PlaceEntry.COLUMN_NAME_PLACEIMAGE;
import static com.example.luisle.localdbwithmvp.data.PlacePersistenceContract.PlaceEntry.COLUMN_NAME_PLACENAME;
import static com.example.luisle.localdbwithmvp.data.PlacePersistenceContract.PlaceEntry.TABLE_NAME;

/**
 * Created by LuisLe on 6/12/2017.
 */

public class PlaceDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MyPlace.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String BLOB_TYPE = " BLOB";

    public PlaceDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_NAME_ID + TEXT_TYPE + " PRIMARY KEY, "
                + COLUMN_NAME_PLACENAME + TEXT_TYPE + " NOT NULL, "
                + COLUMN_NAME_PLACEADDRESS + TEXT_TYPE + " NOT NULL, "
                + COLUMN_NAME_PLACEIMAGE + BLOB_TYPE + " )";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
