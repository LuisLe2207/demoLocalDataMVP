package com.example.luisle.localdbwithmvp.addoredit;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.luisle.localdbwithmvp.data.IPlaceDataSource;
import com.example.luisle.localdbwithmvp.data.PlaceRepository;
import com.example.luisle.localdbwithmvp.dbmodel.Place;

/**
 * Created by LuisLe on 6/13/2017.
 */

public class AddOrEditPresenter implements AddOrEditContract.Presenter, IPlaceDataSource.GetPlaceCallback {

    private final PlaceRepository placeRepository;

    private final AddOrEditContract.View view;

    @Nullable
    private String placeID;

    public AddOrEditPresenter(PlaceRepository placeRepository, AddOrEditContract.View view, String placeID) {
        this.placeRepository = placeRepository;
        this.view = view;
        this.placeID = placeID;
        this.view.setPresenter(this);
    }

    private boolean isNewPlace() {
        return placeID == null;
    }

    @Override
    public void start() {
        if (!isNewPlace()) {
            populatePlace();
        }
    }

    @Override
    public void save(@NonNull String placeName, @NonNull String placeAddress, @Nullable byte[] placeImage) {
        view.showProgressDlg();
        if (placeID == null) {
            Place place = new Place(placeName, placeAddress, placeImage);
            placeRepository.addPlace(place);
        } else {
            Place place = new Place(placeID, placeName, placeAddress, placeImage);
            placeRepository.updatePlace(place);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.hideProgressDlg();
                view.showPlaces();
            }
        }, 2000);

    }

    @Override
    public void populatePlace() {
        placeRepository.getPlace(placeID, this);
    }

    @Override
    public void onPlaceLoaded(Place place) {
        view.setData(place);
    }

    @Override
    public void onDataNotAvailable() {

    }
}
