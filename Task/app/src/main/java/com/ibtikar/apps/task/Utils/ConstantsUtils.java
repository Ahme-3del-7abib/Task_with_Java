package com.ibtikar.apps.task.Utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;

public class ConstantsUtils {

    public static final String BASE_URL = "https://api.themoviedb.org";
    public static final String BASE_IMAGE_SOURCE = "http://image.tmdb.org/t/p/w185";

    public static final String CATEGORY = "popular";
    public static final String API_KEY = "737fa690e0b082a189535d1b1df6614f";
    public static final String LANGUAGE = "en-US";
    public static final int PAGE = 1;

    public static boolean isNetworkConnected(Activity activity) {

        ConnectivityManager cm = (ConnectivityManager)
                activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}
