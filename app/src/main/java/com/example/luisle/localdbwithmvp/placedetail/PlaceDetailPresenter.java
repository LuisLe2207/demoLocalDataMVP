package com.example.luisle.localdbwithmvp.placedetail;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.example.luisle.localdbwithmvp.data.IPlaceDataSource;
import com.example.luisle.localdbwithmvp.data.PlaceRepository;
import com.example.luisle.localdbwithmvp.dbmodel.Place;

/**
 * Created by LuisLe on 6/14/2017.
 */

public class PlaceDetailPresenter implements PlaceDetailContract.Presenter, IPlaceDataSource.GetPlaceCallback {

    private final PlaceRepository placeRepository;

    private final PlaceDetailContract.View view;

    @NonNull
    private String placeID;

    public PlaceDetailPresenter(PlaceRepository placeRepository, PlaceDetailContract.View view, String placeID) {
        this.placeRepository = placeRepository;
        this.view = view;
        this.placeID = placeID;
        this.view.setPresenter(this);
    }

    @Override
    public void start() {
        populatePlace();
    }


    @Override
    public void deletePlace() {
        view.showProgressDlg();
        placeRepository.deletePlace(placeID);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.hideProgressDlg();
                view.showPlaces();
            }
        }, 2000);
    }

    @Override
    public void findRoute(@NonNull Place place) {

    }

    @Override
    public void populatePlace() {
        placeRepository.getPlace(placeID, this);
    }

    @Override
    public void openEditPlaceUi() {
        view.showPlaceEditUi(placeID);
    }

    @Override
    public void openDeleteAlertDlg() {
        view.showAlertDlg();
    }

    @Override
    public void onPlaceLoaded(Place place) {
        view.setData(place);
    }

    @Override
    public void onDataNotAvailable() {

    }
}
