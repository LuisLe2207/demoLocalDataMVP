package com.example.luisle.localdbwithmvp.place;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luisle.localdbwithmvp.R;
import com.example.luisle.localdbwithmvp.adapter.PlaceAdapter;
import com.example.luisle.localdbwithmvp.addoredit.AddOrEditFragment;
import com.example.luisle.localdbwithmvp.addoredit.AddOrEditPresenter;
import com.example.luisle.localdbwithmvp.data.Injection;
import com.example.luisle.localdbwithmvp.dbmodel.Place;

import java.util.ArrayList;
import java.util.List;

import static com.example.luisle.localdbwithmvp.ActivityUtils.ADD_EDIT_FRAGMENT_TAG;
import static com.example.luisle.localdbwithmvp.ActivityUtils.PLACE_FRAGMENT_TAG;

/**
 * Created by LuisLe on 6/13/2017.
 */

public class PlaceFragment extends Fragment implements PlaceContract.View, PlaceAdapter.OnActionCallback {

    private PlaceContract.Presenter presenter;

    private TextView txtNoData;
    private FloatingActionButton fabAddNew;
    private RecyclerView rcvPlace;
    private PlaceAdapter placeAdapter;


    public static PlaceFragment getInstance() {
        return new PlaceFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        placeAdapter = new PlaceAdapter(getContext(), new ArrayList<Place>(0), this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_place, container, false);
        mappingLayout(root);

        rcvPlace.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rcvPlace.setAdapter(placeAdapter);

        fabAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.addNewPlace();
            }
        });

        Toast.makeText(getContext(), "Hello", Toast.LENGTH_SHORT).show();

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();

    }

    @Override
    public void setPresenter(PlaceContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showPlaces(List<Place> places) {
        placeAdapter.replaceData(places);
        txtNoData.setVisibility(View.GONE);
    }

    @Override
    public void showAddPlaceUi() {
//        AddOrEditFragment addOrEditFragment = AddOrEditFragment.getInstance();
//        PlaceFragment placeFragment = (PlaceFragment) getActivity().getSupportFragmentManager().findFragmentByTag(PLACE_FRAGMENT_TAG);
//        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//        if (placeFragment != null) {
//            transaction.hide(placeFragment);
//        }
//        transaction.replace(android.R.id.content, addOrEditFragment, ADD_EDIT_FRAGMENT_TAG).addToBackStack(null).commit();
//        AddOrEditPresenter presenter = new AddOrEditPresenter(Injection.providePlaceRepository(getContext()), addOrEditFragment, null);
        openAddEditUI(null);
    }

    @Override
    public void updateUI() {
        presenter.loadPlaces();
    }

    private void mappingLayout(View root) {
        rcvPlace = (RecyclerView) root.findViewById(R.id.rcvFragPlace);
        fabAddNew = (FloatingActionButton) root.findViewById(R.id.fabFragPlace_AddNew);
        txtNoData = (TextView) root.findViewById(R.id.txtFragPlace_NoData);
    }

    @Override
    public void showPlaceDetailUi(@NonNull String placeID) {
        openAddEditUI(placeID);
    }

    private void openAddEditUI(@Nullable String placeID) {
        AddOrEditFragment addOrEditFragment = AddOrEditFragment.getInstance();
        PlaceFragment placeFragment = (PlaceFragment) getActivity().getSupportFragmentManager().findFragmentByTag(PLACE_FRAGMENT_TAG);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        if (placeFragment != null) {
            transaction.hide(placeFragment);
        }
        transaction.replace(android.R.id.content, addOrEditFragment, ADD_EDIT_FRAGMENT_TAG).addToBackStack(null).commit();
        AddOrEditPresenter presenter = new AddOrEditPresenter(Injection.providePlaceRepository(getContext()), addOrEditFragment, placeID);
    }
}
