package com.example.multi_theme_ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.multi_theme_ui.prefrences.AppPreferences;
import com.example.multi_theme_ui.prefrences.PreferenceManager;
import com.example.multi_theme_ui.utils.BaseUtils;
import com.example.multi_theme_ui.utils.ThemeUtils;

/**
 * Created by sandeepsaini on 27,June,2019
 */
public class BaseActivity extends AppCompatActivity {

    public static int mTheme; // = ThemeUtils.THEME_RED;
    public static boolean mIsNightMode = false;

    AppPreferences appPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyPref", 0);
        PreferenceManager preferenceManager = new PreferenceManager(sharedPreferences);
        appPreferences = new AppPreferences(preferenceManager);


//        BaseUtils.init(this);

        if(appPreferences.getCurrentTheme() != -1){
            mTheme = appPreferences.getCurrentTheme();
        } else {
            mTheme = 0;
        }

        setTheme(ThemeUtils.getThemeId(mTheme));
    }
}
