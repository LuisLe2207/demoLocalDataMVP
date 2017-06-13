package com.example.luisle.localdbwithmvp.data;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.luisle.localdbwithmvp.sqlite.PlaceLocalSQLiteDataSource;

/**
 * Created by LuisLe on 6/13/2017.
 */

public class Injection {
    public static PlaceRepository providePlaceRepository(@NonNull Context context) {
        return PlaceRepository.getInstance(PlaceLocalSQLiteDataSource.getInstance(context));
    }
}
