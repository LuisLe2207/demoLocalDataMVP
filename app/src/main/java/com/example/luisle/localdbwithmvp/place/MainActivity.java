package com.example.luisle.localdbwithmvp.place;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.luisle.localdbwithmvp.ActivityUtils;
import com.example.luisle.localdbwithmvp.R;
import com.example.luisle.localdbwithmvp.data.Injection;

import static com.example.luisle.localdbwithmvp.ActivityUtils.PLACE_FRAGMENT_TAG;

public class MainActivity extends AppCompatActivity implements ActivityUtils.Communicator{

    private PlacePresenter presenter;

    private ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();


        PlaceFragment placeFragment = PlaceFragment.getInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.layout, placeFragment, PLACE_FRAGMENT_TAG).commit();

        presenter = new PlacePresenter(Injection.providePlaceRepository(getApplicationContext()), placeFragment);

    }

    @Override
    public void setActionBarTitle(@NonNull String title) {
        actionBar.setTitle(title);
    }
}


