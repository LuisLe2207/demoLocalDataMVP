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
    interface View extends BaseView<Presenter>, BaseView.ViewProgress {
        void redirectUI(boolean result);
        void setData(@NonNull Place place);
        void showCamera();
        void updateActionBarTitle(boolean isAdd);
    }

    interface Presenter extends BasePresenter {
        void save(@NonNull String placeName, @NonNull  String placeAddress, @Nullable byte[] placeImage);
        void populatePlace();
        void openCamera();
        void changeActionBarTitle();
    }
}
