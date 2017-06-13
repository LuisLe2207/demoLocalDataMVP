package com.example.luisle.localdbwithmvp.addoredit;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.luisle.localdbwithmvp.R;
import com.example.luisle.localdbwithmvp.dbmodel.Place;
import com.example.luisle.localdbwithmvp.place.PlaceFragment;
import com.makeramen.roundedimageview.RoundedImageView;

import static com.example.luisle.localdbwithmvp.ActivityUtils.ADD_EDIT_FRAGMENT_TAG;
import static com.example.luisle.localdbwithmvp.ActivityUtils.PLACE_FRAGMENT_TAG;
import static com.example.luisle.localdbwithmvp.ActivityUtils.imageViewToByte;

/**
 * Created by LuisLe on 6/13/2017.
 */

public class AddOrEditFragment extends Fragment implements AddOrEditContract.View{


    private AddOrEditContract.Presenter presenter;

    private RoundedImageView imgPlace;
    private EditText edtPlaceName, edtPlaceAddress;
    private Button btnSave;

    private ProgressDialog progressDialog;

    public static AddOrEditFragment getInstance() {
        return new AddOrEditFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getContext().getResources().getString(R.string.txt_saving_dialog));
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_addoredit, container, false);

        imgPlace = (RoundedImageView) root.findViewById(R.id.imgFragAddOrEdit_Image);
        edtPlaceName = (EditText) root.findViewById(R.id.edtFragAddOrEdit_PlaceName);
        edtPlaceAddress = (EditText) root.findViewById(R.id.edtFragAddOrEdit_PlaceAddress);
        btnSave = (Button) root.findViewById(R.id.btnFragAddOrEdit_Save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String placeName = edtPlaceName.getText().toString();
                String placeAddress = edtPlaceAddress.getText().toString();
                byte[] placeImage = imageViewToByte(imgPlace);
                presenter.save(placeName, placeAddress, placeImage);
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void setPresenter(AddOrEditContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showPlaces() {
        AddOrEditFragment addOrEditFragment = (AddOrEditFragment) getActivity().getSupportFragmentManager().findFragmentByTag(ADD_EDIT_FRAGMENT_TAG);
        PlaceFragment placeFragment = (PlaceFragment) getActivity().getSupportFragmentManager().findFragmentByTag(PLACE_FRAGMENT_TAG);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        if (addOrEditFragment != null) {
            transaction.remove(addOrEditFragment);
        }
        transaction.show(placeFragment).commit();
        placeFragment.updateUI();

    }

    @Override
    public void setData(@NonNull Place place) {
        byte[] imgByte = place.getPlaceImage();
        if (imgByte != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
            imgPlace.setImageBitmap(bitmap);
        } else {
            imgPlace.setImageResource(R.mipmap.ic_no_image);
        }

        edtPlaceName.setText(place.getPlaceName());
        edtPlaceAddress.setText(place.getPlaceAddress());
    }

    @Override
    public void showProgressDlg() {
        progressDialog.show();
    }

    @Override
    public void hideProgressDlg() {
        progressDialog.hide();
    }
}
