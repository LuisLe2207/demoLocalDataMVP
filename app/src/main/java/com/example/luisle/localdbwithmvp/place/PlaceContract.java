package com.example.luisle.localdbwithmvp.place;

import com.example.luisle.localdbwithmvp.BasePresenter;
import com.example.luisle.localdbwithmvp.BaseView;
import com.example.luisle.localdbwithmvp.dbmodel.Place;

import java.util.List;

/**
 * Created by LuisLe on 6/13/2017.
 */

public interface PlaceContract {
    interface View extends BaseView<Presenter> {
        void showPlaces(List<Place> places);
        void showAddPlaceUi();
        void updateUI();
    }

    interface Presenter extends BasePresenter{
        void loadPlaces();
        void addNewPlace();
    }

}
