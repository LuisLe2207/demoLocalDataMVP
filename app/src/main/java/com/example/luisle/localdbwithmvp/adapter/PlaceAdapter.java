package com.example.luisle.localdbwithmvp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.luisle.localdbwithmvp.R;
import com.example.luisle.localdbwithmvp.dbmodel.Place;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/**
 * Created by LuisLe on 6/13/2017.
 */

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> {

    private Context context;
    private List<Place> places;
    private LayoutInflater layoutInflater;
    private OnActionCallback callback;

    public PlaceAdapter(Context context, List<Place> places, OnActionCallback callback) {
        this.context = context;
        this.callback = callback;
        setPlaces(places);
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    public void replaceData(List<Place> places) {
        setPlaces(places);
        notifyDataSetChanged();
    }

    @Override
    public PlaceAdapter.PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = layoutInflater.inflate(R.layout.row_place, parent, false);
        return new PlaceViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(PlaceViewHolder holder, int position) {
        Place place = places.get(position);

        byte[] imgByte = place.getPlaceImage();
        if (imgByte != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
            holder.imgPlace.setImageBitmap(bitmap);
        } else {
            holder.imgPlace.setImageResource(R.mipmap.ic_no_image);
        }

        holder.txtPlaceName.setText(place.getPlaceName());
        holder.txtPlaceAddress.setText(place.getPlaceAddress());
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    class PlaceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private RoundedImageView imgPlace;
        private TextView txtPlaceName, txtPlaceAddress;

        public PlaceViewHolder(View itemView) {
            super(itemView);

            imgPlace = (RoundedImageView) itemView.findViewById(R.id.imgFragPlace_Image);
            txtPlaceName = (TextView) itemView.findViewById(R.id.txtFragPlace_PlaceName);
            txtPlaceAddress = (TextView) itemView.findViewById(R.id.txtFragPlace_PlaceAddress);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int position = getLayoutPosition();
            Place place = places.get(position);
            callback.showPlaceDetailUi(place.getPlaceID());
        }
    }

    public interface OnActionCallback {
        void showPlaceDetailUi(@NonNull String placeID);
    }
}
