package com.example.multi_theme_ui.prefrences;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by sandeepsaini on 27,June,2019
 */


@Retention(RetentionPolicy.SOURCE)
@StringDef({
        PreferenceKey.CURRENT_THEME,
})

public @interface PreferenceKey {
    String CURRENT_THEME = "currentTheme";

}
