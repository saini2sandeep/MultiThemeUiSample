package com.example.multi_theme_ui.prefrences;

import com.example.multi_theme_ui.model.Theme;

/**
 * Created by sandeepsaini on 27,June,2019
 */
public class AppPreferences {

    private PreferenceManager preferenceManager;

    public AppPreferences(PreferenceManager preferenceManager) {
        this.preferenceManager = preferenceManager;
    }

    public void saveCurrentTheme(Integer theme) {
        preferenceManager.save(PreferenceKey.CURRENT_THEME, theme);
    }

    public int getCurrentTheme() {
        return preferenceManager.getInt(PreferenceKey.CURRENT_THEME);
    }

    public void clear() {
        preferenceManager.removeAll();
    }


}
