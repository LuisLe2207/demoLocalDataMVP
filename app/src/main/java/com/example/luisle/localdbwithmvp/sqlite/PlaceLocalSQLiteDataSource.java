package com.example.luisle.localdbwithmvp.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.example.luisle.localdbwithmvp.data.IPlaceDataSource;
import com.example.luisle.localdbwithmvp.dbmodel.Place;

import java.util.ArrayList;
import java.util.List;

import static com.example.luisle.localdbwithmvp.data.PlacePersistenceContract.PlaceEntry.COLUMN_NAME_ID;
import static com.example.luisle.localdbwithmvp.data.PlacePersistenceContract.PlaceEntry.COLUMN_NAME_PLACEADDRESS;
import static com.example.luisle.localdbwithmvp.data.PlacePersistenceContract.PlaceEntry.COLUMN_NAME_PLACEIMAGE;
import static com.example.luisle.localdbwithmvp.data.PlacePersistenceContract.PlaceEntry.COLUMN_NAME_PLACENAME;
import static com.example.luisle.localdbwithmvp.data.PlacePersistenceContract.PlaceEntry.TABLE_NAME;

/**
 * Created by LuisLe on 6/12/2017.
 */

public class PlaceLocalSQLiteDataSource implements IPlaceDataSource {

    private static PlaceLocalSQLiteDataSource INSTANCE;
    private PlaceDbHelper dbHelper;

    private PlaceLocalSQLiteDataSource(@NonNull Context context) {
        dbHelper = new PlaceDbHelper(context);
    }

    public static PlaceLocalSQLiteDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new PlaceLocalSQLiteDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void getPlaces(@NonNull LoadPlacesCallback callback) {
        List<Place> places = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        String[] columns = {
                COLUMN_NAME_ID,
                COLUMN_NAME_PLACENAME,
                COLUMN_NAME_PLACEADDRESS,
                COLUMN_NAME_PLACEIMAGE
        };

        Cursor cursor = database.query(TABLE_NAME, columns, null, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String placeID = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_ID));
                String placeName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_PLACENAME));
                String placeAddress = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_PLACEADDRESS));
                byte[] placeImage = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_NAME_PLACEIMAGE));

                Place place = new Place(placeID, placeName, placeAddress, placeImage);
                places.add(place);
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        database.close();

        if (places.isEmpty()) {
            callback.onDataNotAvailable();
        } else {
            callback.onPlacesLoaded(places);
        }
    }

    @Override
    public void getPlace(@NonNull String placeID, @NonNull GetPlaceCallback callback) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        String[] columns = {
                COLUMN_NAME_ID,
                COLUMN_NAME_PLACENAME,
                COLUMN_NAME_PLACEADDRESS,
                COLUMN_NAME_PLACEIMAGE
        };

        String selection = COLUMN_NAME_ID + " LIKE ?";
        String[] selectionArgs = {placeID};

        Cursor cursor = database.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);


        Place place = null;

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            String ID = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_ID));
            String placeName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_PLACENAME));
            String placeAddress = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_PLACEADDRESS));
            byte[] placeImage = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_NAME_PLACEIMAGE));

            place = new Place(ID, placeName, placeAddress, placeImage);
        }

        if (cursor != null) {
            cursor.close();
        }

        database.close();

        if (place == null) {
            callback.onDataNotAvailable();
        } else {
            callback.onPlaceLoaded(place);
        }
    }

    @Override
    public void addPlace(@NonNull Place place) {

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_ID, place.getPlaceID());
        values.put(COLUMN_NAME_PLACENAME, place.getPlaceName());
        values.put(COLUMN_NAME_PLACEADDRESS, place.getPlaceAddress());
        values.put(COLUMN_NAME_PLACEIMAGE, place.getPlaceImage());

        database.insert(TABLE_NAME, null, values);
        database.close();

    }

    @Override
    public void updatePlace(@NonNull Place place) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_ID, place.getPlaceID());
        values.put(COLUMN_NAME_PLACENAME, place.getPlaceName());
        values.put(COLUMN_NAME_PLACEADDRESS, place.getPlaceAddress());
        values.put(COLUMN_NAME_PLACEIMAGE, place.getPlaceImage());

        String whereClause = COLUMN_NAME_ID + " = ?";
        String[] whereArgs = {place.getPlaceID()};

        database.update(TABLE_NAME, values, whereClause, whereArgs);
        database.close();
    }

    @Override
    public void deletePlace(@NonNull String placeID) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        String whereClause = COLUMN_NAME_ID + " = ?";
        String[] whereArgs = {placeID};

        database.delete(TABLE_NAME, whereClause, whereArgs);
        database.close();
    }

    @Override
    public void refreshPlaces() {

    }
}
