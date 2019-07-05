package com.example.multi_theme_ui;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.multi_theme_ui.utils.BaseUtils;

import nl.bravobit.ffmpeg.FFmpeg;

/**
 * Created by sandeepsaini on 04,July,2019
 */
public class MultiThemeUiApp extends Application {

    @Override public void onCreate() {
        super.onCreate();
        BaseUtils.init(this);
        initFFmpegBinary(this);
    }

    private void initFFmpegBinary(Context context) {
        if (!FFmpeg.getInstance(context).isSupported()) {
            Log.e("MultiThemeUiApp","Android cup arch not supported!");
        }
    }
}
