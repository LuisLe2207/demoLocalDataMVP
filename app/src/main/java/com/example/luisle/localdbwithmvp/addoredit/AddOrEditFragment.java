package com.example.luisle.localdbwithmvp.addoredit;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.luisle.localdbwithmvp.ActivityUtils;
import com.example.luisle.localdbwithmvp.R;
import com.example.luisle.localdbwithmvp.dbmodel.Place;
import com.makeramen.roundedimageview.RoundedImageView;

import static android.app.Activity.RESULT_OK;
import static com.example.luisle.localdbwithmvp.ActivityUtils.DETAIL_FRAGMENT_TAG;
import static com.example.luisle.localdbwithmvp.ActivityUtils.PLACE_FRAGMENT_TAG;
import static com.example.luisle.localdbwithmvp.ActivityUtils.imageViewToByte;

/**
 * Created by LuisLe on 6/13/2017.
 */

public class AddOrEditFragment extends Fragment implements AddOrEditContract.View{


    private AddOrEditContract.Presenter presenter;
    private ActivityUtils.Communicator communicator;

    private RoundedImageView imgPlace;
    private EditText edtPlaceName, edtPlaceAddress;
    private Button btnSave;

    private ProgressDialog progressDialog;

    public static final int REQUEST_IMAGE_CAPTURE = 1;

    public static AddOrEditFragment getInstance() {
        return new AddOrEditFragment();
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

        imgPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.openCamera();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data !=null) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imgPlace.setImageBitmap(imageBitmap);
        }
    }

    @Override
    public void setPresenter(AddOrEditContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void redirectUI(boolean result) {
        Fragment resultFragment;
        String tag;
        if (result) {
            resultFragment =  getActivity().getSupportFragmentManager().findFragmentByTag(PLACE_FRAGMENT_TAG);
            tag = PLACE_FRAGMENT_TAG;
        } else {
            resultFragment =  getActivity().getSupportFragmentManager().findFragmentByTag(DETAIL_FRAGMENT_TAG);
            tag = DETAIL_FRAGMENT_TAG;
        }
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out);
        transaction.replace(R.id.layout, resultFragment, tag);
        if (!result) {
            getActivity().getSupportFragmentManager().popBackStack();
        }
        resultFragment.onResume();
        transaction.commit();
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
    public void showCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    public void updateActionBarTitle(boolean isAdd) {
        if (isAdd) {
            communicator.setActionBarTitle(getContext().getResources().getString(R.string.action_bar_title_add));
        } else {
            communicator.setActionBarTitle(getContext().getResources().getString(R.string.action_bar_title_modify));
        }
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
