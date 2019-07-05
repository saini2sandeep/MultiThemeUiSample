package com.example.multi_theme_ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.example.multi_theme_ui.model.Theme;
import com.example.multi_theme_ui.prefrences.AppPreferences;
import com.example.multi_theme_ui.prefrences.PreferenceManager;
import com.example.multi_theme_ui.utils.ThemeUtils;
import com.example.multi_theme_ui.view.TestOneActivity;
import com.example.multi_theme_ui.view.ThemeListAdapter;
import com.example.multi_theme_ui.view.ThemeView;
import com.example.multi_theme_ui.view.select.VideoSelectActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {


    public static List<Theme> mThemeList = new ArrayList<>();
    public static int selectedTheme;
    private ThemeListAdapter mAdapter;
    private RecyclerView mRecyclerView;

    private BottomSheetBehavior mBottomSheetBehavior;

    AppPreferences appPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyPref", 0);
        PreferenceManager preferenceManager = new PreferenceManager(sharedPreferences);
        appPreferences = new AppPreferences(preferenceManager);

        inItBottomSheet();
        prepareThemeData();


        if (appPreferences.getCurrentTheme() != -1) {
            selectedTheme = appPreferences.getCurrentTheme();
        } else {
            selectedTheme = 0;
        }

        ThemeView themeView = findViewById(R.id.theme_selected);
        themeView.setTheme(mThemeList.get(selectedTheme));
    }


    private void inItBottomSheet() {

        // get the bottom sheet view
        LinearLayout llBottomSheet = (LinearLayout) findViewById(R.id.bottom_sheet);

        // init the bottom sheet behavior
        mBottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);

//        SwitchCompat switchCompat = findViewById(R.id.switch_dark_mode);
//        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                mIsNightMode = b;
//                int delayTime = 200;
//                if(mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED){
//                    delayTime = 400;
//                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//                }
//                compoundButton.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        if(mIsNightMode){
//                            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                        }else{
//                            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                        }
//                    }
//                },delayTime);
//
//            }
//        });


        mAdapter = new ThemeListAdapter(mThemeList, new ThemeListAdapter.ThemeItemClickListener() {
            @Override
            public void onThemeClick(View v, int position) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MainActivity.this.recreate();

                        appPreferences.saveCurrentTheme(selectedTheme);
                    }
                }, 400);
            }
        });

        mRecyclerView = findViewById(R.id.recyclerView);


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 4);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void prepareThemeData() {
        mThemeList.clear();
        mThemeList.addAll(ThemeUtils.getThemeList());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.theme_selected:
                // change the state of the bottom sheet
                switch (mBottomSheetBehavior.getState()) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        break;

                    case BottomSheetBehavior.STATE_COLLAPSED:
                        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        break;

                    case BottomSheetBehavior.STATE_EXPANDED:
                        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        break;
                }
                break;

            case R.id.fab:
                Intent intent = new Intent(this, VideoSelectActivity.class);
                startActivity(intent);
        }
    }

}
