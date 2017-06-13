package com.example.luisle.localdbwithmvp.addoredit;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.luisle.localdbwithmvp.BasePresenter;
import com.example.luisle.localdbwithmvp.BaseView;
import com.example.luisle.localdbwithmvp.dbmodel.Place;

/**
 * Created by LuisLe on 6/13/2017.
 */

public interface AddOrEditContract {
    interface View extends BaseView<Presenter> {
        void showPlaces();
        void setData(@NonNull Place place);
        void showProgressDlg();
        void hideProgressDlg();
    }

    interface Presenter extends BasePresenter {
        void save(@NonNull String placeName, @NonNull  String placeAddress, @Nullable byte[] placeImage);
        void populatePlace();
    }
}
