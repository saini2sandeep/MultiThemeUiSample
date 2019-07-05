package com.example.multi_theme_ui.utils;

import android.util.DisplayMetrics;

/**
 * Created by sandeepsaini on 03,July,2019
 */
public class UnitConverter {



    public static DisplayMetrics getDisplayMetrics(){

        return BaseUtils.getContext().getResources().getDisplayMetrics();
    }

    public static float dpToPx(float dp) {
        return dp * getDisplayMetrics().density;
    }


    public static int dpToPx(int dp) {
        return (int) (dp * getDisplayMetrics().density + 0.5f);
    }
}
