package com.example.luisle.localdbwithmvp.place;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.example.luisle.localdbwithmvp.data.IPlaceDataSource;
import com.example.luisle.localdbwithmvp.data.PlaceRepository;
import com.example.luisle.localdbwithmvp.dbmodel.Place;
import com.makeramen.roundedimageview.RoundedDrawable;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Created by LuisLe on 6/13/2017.
 */

public class PlacePresenter implements PlaceContract.Presenter {

    private final PlaceRepository placeRepository;

    private final PlaceContract.View view;


    public PlacePresenter(@NonNull PlaceRepository placeRepository, @NonNull PlaceContract.View view) {
        this.placeRepository = placeRepository;
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void start() {
        loadPlaces();
    }

    @Override
    public void loadPlaces() {
        placeRepository.getPlaces(new IPlaceDataSource.LoadPlacesCallback() {
            @Override
            public void onPlacesLoaded(List<Place> places) {
                view.showPlaces(places);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void addNewPlace() {
        view.showAddPlaceUi();
    }


    public byte[] imageViewToByte(ImageView img){

        RoundedDrawable drawable = (RoundedDrawable) img.getDrawable();
        Bitmap bmp = drawable.getSourceBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
