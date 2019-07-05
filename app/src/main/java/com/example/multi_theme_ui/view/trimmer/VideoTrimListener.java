package com.example.multi_theme_ui.view.trimmer;

/**
 * Created by sandeepsaini on 04,July,2019
 */
public interface VideoTrimListener {
    void onStartTrim();
    void onFinishTrim(String url);
    void onCancel();
}
