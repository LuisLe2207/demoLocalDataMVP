package com.example.luisle.localdbwithmvp.placedetail;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.luisle.localdbwithmvp.ActivityUtils;
import com.example.luisle.localdbwithmvp.R;
import com.example.luisle.localdbwithmvp.addoredit.AddOrEditFragment;
import com.example.luisle.localdbwithmvp.addoredit.AddOrEditPresenter;
import com.example.luisle.localdbwithmvp.data.Injection;
import com.example.luisle.localdbwithmvp.dbmodel.Place;
import com.example.luisle.localdbwithmvp.place.PlaceFragment;
import com.google.android.gms.maps.MapView;
import com.makeramen.roundedimageview.RoundedImageView;

import static com.example.luisle.localdbwithmvp.ActivityUtils.ADD_EDIT_FRAGMENT_TAG;
import static com.example.luisle.localdbwithmvp.ActivityUtils.PLACE_FRAGMENT_TAG;

/**
 * Created by LuisLe on 6/14/2017.
 */

public class PlaceDetailFragment extends Fragment implements PlaceDetailContract.View {


    private PlaceDetailContract.Presenter presenter;
    private ActivityUtils.Communicator communicator;

    private RoundedImageView imgPlace;
    private ImageButton ibtnFindRoute, ibtnDelete, ibtnEdit;
    private EditText edtPlaceName, edtPlaceAddress;
    private MapView mapView;

    private ProgressDialog progressDialog;

    public static PlaceDetailFragment getInstance() {
        return new PlaceDetailFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        communicator = (ActivityUtils.Communicator) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getContext().getResources().getString(R.string.txt_delete_dialog));
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_place_detail, container, false);
        mappingLayout(root);

        ibtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.openEditPlaceUi();
            }
        });

        ibtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.openDeleteAlertDlg();
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
        communicator.setActionBarTitle(getContext().getResources().getString(R.string.action_bar_title_detail));
    }

    @Override
    public void setPresenter(PlaceDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showPlaces() {

        //PlaceDetailFragment placeDetailFragment = (PlaceDetailFragment) getActivity().getSupportFragmentManager().findFragmentByTag(DETAIL_FRAGMENT_TAG);
        PlaceFragment placeFragment = (PlaceFragment) getActivity().getSupportFragmentManager().findFragmentByTag(PLACE_FRAGMENT_TAG);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//        if (placeDetailFragment != null) {
//            transaction.remove(placeDetailFragment);
//        }
        transaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out);
        transaction.replace(R.id.layout, placeFragment, PLACE_FRAGMENT_TAG).commit();
        //transaction.show(placeFragment).commit();
        placeFragment.updateUI();
    }

    @Override
    public void showPlaceEditUi(@NonNull String placeID) {
        AddOrEditFragment addOrEditFragment = AddOrEditFragment.getInstance();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.slide_in, R.anim.slide_out);
        transaction.replace(R.id.layout, addOrEditFragment, ADD_EDIT_FRAGMENT_TAG).addToBackStack(null).commit();
        AddOrEditPresenter presenter = new AddOrEditPresenter(Injection.providePlaceRepository(getContext()), addOrEditFragment, placeID);

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
    public void showAlertDlg() {
        final AlertDialog.Builder alertDialog =  new AlertDialog.Builder(getContext());
        alertDialog.setMessage(getContext().getResources().getString(R.string.txt_delete_alert_dialog) + " '" + edtPlaceName.getText() + "'");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.deletePlace();
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialog.setCancelable(true).create().show();

    }

    private void mappingLayout(View root) {
        imgPlace = (RoundedImageView) root.findViewById(R.id.imgFragDetail_Image);
        ibtnFindRoute = (ImageButton) root.findViewById(R.id.ibtnFragDetail_FindRoute);
        ibtnDelete = (ImageButton) root.findViewById(R.id.ibtnFragDetail_Delete);
        ibtnEdit = (ImageButton) root.findViewById(R.id.ibtnFragDetail_Edit);
        edtPlaceName = (EditText) root.findViewById(R.id.edtFragDetail_PlaceName);
        edtPlaceAddress = (EditText) root.findViewById(R.id.edtFragDetail_PlaceAddress);
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
