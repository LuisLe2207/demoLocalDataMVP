package com.example.luisle.localdbwithmvp.data;

import android.support.annotation.NonNull;

import com.example.luisle.localdbwithmvp.dbmodel.Place;
import com.example.luisle.localdbwithmvp.sqlite.PlaceLocalSQLiteDataSource;

import java.util.List;

/**
 * Created by LuisLe on 6/13/2017.
 */

public class PlaceRepository implements IPlaceDataSource {


    private static PlaceRepository INSTANCE;

    private final PlaceLocalSQLiteDataSource sqLiteDataSource;


    private PlaceRepository(@NonNull PlaceLocalSQLiteDataSource dataSource) {
        sqLiteDataSource = dataSource;
    }

    public static PlaceRepository getInstance(@NonNull PlaceLocalSQLiteDataSource dataSource) {
        if (INSTANCE == null) {
            INSTANCE = new PlaceRepository(dataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getPlaces(@NonNull final LoadPlacesCallback callback) {
        sqLiteDataSource.getPlaces(new LoadPlacesCallback() {
            @Override
            public void onPlacesLoaded(List<Place> places) {
                callback.onPlacesLoaded(places);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void getPlace(@NonNull String placeID, @NonNull final GetPlaceCallback callback) {
        sqLiteDataSource.getPlace(placeID, new GetPlaceCallback() {
            @Override
            public void onPlaceLoaded(Place place) {
                callback.onPlaceLoaded(place);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void addPlace(@NonNull Place place) {
        sqLiteDataSource.addPlace(place);
    }

    @Override
    public void updatePlace(@NonNull Place place) {
        sqLiteDataSource.updatePlace(place);
    }

    @Override
    public void deletePlace(@NonNull String placeID) {
        sqLiteDataSource.deletePlace(placeID);
    }

    @Override
    public void refreshPlaces() {

    }
}
