package com.example.luisle.localdbwithmvp.data;

import android.provider.BaseColumns;

/**
 * Created by LuisLe on 6/12/2017.
 */

public final class PlacePersistenceContract {
    private PlacePersistenceContract() {}

    public static abstract class PlaceEntry implements BaseColumns {
        public static final String TABLE_NAME = "Places";

        public static final String COLUMN_NAME_ID = "PlaceID";
        public static final String COLUMN_NAME_PLACENAME = "PlaceName";
        public static final String COLUMN_NAME_PLACEADDRESS = "PlaceAddress";
        public static final String COLUMN_NAME_PLACEIMAGE = "PlaceImage";
    }
}
