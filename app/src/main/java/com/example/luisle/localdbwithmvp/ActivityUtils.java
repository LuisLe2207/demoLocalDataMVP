package com.example.luisle.localdbwithmvp;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.makeramen.roundedimageview.RoundedDrawable;

import java.io.ByteArrayOutputStream;

/**
 * Created by LuisLe on 6/13/2017.
 */

public class ActivityUtils {
    public static final String PLACE_FRAGMENT_TAG = "PLACE_FRAGMENT";
    public static final String ADD_EDIT_FRAGMENT_TAG = "ADD_EDIT_FRAGMENT_TAG";
    public static final String DETAIL_FRAGMENT_TAG = "DETAIL_FRAGMENT_TAG";

    public static byte[] imageViewToByte(ImageView img){

        RoundedDrawable drawable = (RoundedDrawable) img.getDrawable();
        Bitmap bmp = drawable.getSourceBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
