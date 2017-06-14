package com.example.luisle.localdbwithmvp.placedetail;

import android.support.annotation.NonNull;

import com.example.luisle.localdbwithmvp.BasePresenter;
import com.example.luisle.localdbwithmvp.BaseView;
import com.example.luisle.localdbwithmvp.dbmodel.Place;

/**
 * Created by LuisLe on 6/14/2017.
 */

public interface PlaceDetailContract {
    interface View extends BaseView<Presenter>, BaseView.ViewProgress {
        void showPlaces();
        void showPlaceEditUi(@NonNull String placeID);
        void setData(@NonNull Place place);
        void showAlertDlg();
    }

    interface Presenter extends BasePresenter{
        void deletePlace();
        void findRoute(@NonNull Place place);
        void populatePlace();
        void openEditPlaceUi();
        void openDeleteAlertDlg();
    }
}
