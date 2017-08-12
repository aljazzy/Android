package com.jatmiko.juli.popularmovie2.utility;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by Miko on 10/08/2017.
 */

public class Constant {
    public static class Data {
        public static final String[] MOVIE_LIST_TYPE = new String[]{"popular", "top_rated"};
        public static final String MOVIE_INTENT = "movie";
        public static final String MOVIE_RELEASE_DATE_FORMAT_AFTER = "MMMM dd, yyyy";
        public static final String MOVIE_RELEASE_DATE_FORMAT_BEFORE = "yyyy-MM-dd";
        public static final int MOVIE_IMAGE_LIST_WIDTH = 200;
    }

    public static class Function {

        public static int getColumnsCount(Activity activity) {
            Display display = activity.getWindowManager().getDefaultDisplay();
            DisplayMetrics outMetrics = new DisplayMetrics();
            display.getMetrics(outMetrics);

            float density = activity.getResources().getDisplayMetrics().density;
            float dpWidth = outMetrics.widthPixels / density;
            return Math.round(dpWidth / Data.MOVIE_IMAGE_LIST_WIDTH);
        }


        public static void setImageResource(Context context, String urlImage, ImageView imageView) {
            Glide.with(context)
                    .load(urlImage)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE).dontAnimate().centerCrop())
                    .into(imageView);
        }
    }
}
