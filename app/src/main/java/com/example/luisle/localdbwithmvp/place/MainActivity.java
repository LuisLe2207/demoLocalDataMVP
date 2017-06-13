package com.example.luisle.localdbwithmvp.place;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.luisle.localdbwithmvp.R;
import com.example.luisle.localdbwithmvp.data.Injection;

import static com.example.luisle.localdbwithmvp.ActivityUtils.PLACE_FRAGMENT_TAG;

public class MainActivity extends AppCompatActivity {

    PlacePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PlaceFragment placeFragment = PlaceFragment.getInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.layout, placeFragment, PLACE_FRAGMENT_TAG).commit();

        presenter = new PlacePresenter(Injection.providePlaceRepository(getApplicationContext()), placeFragment);

    }
}
