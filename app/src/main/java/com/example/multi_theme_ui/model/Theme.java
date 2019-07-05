package com.example.multi_theme_ui.model;

import com.example.multi_theme_ui.prefrences.GsonUtils;

/**
 * Created by sandeepsaini on 27,June,2019
 */
public class Theme {
    private int id;
    private int primaryColor;
    private int primaryDarkColor;
    private int accentColor;

    public Theme(int primaryColor, int primaryDarkColor, int accentColor) {
        this.primaryColor = primaryColor;
        this.primaryDarkColor = primaryDarkColor;
        this.accentColor = accentColor;
    }

    public Theme(int id, int primaryColor, int primaryDarkColor, int accentColor) {
        this.id = id;
        this.primaryColor = primaryColor;
        this.primaryDarkColor = primaryDarkColor;
        this.accentColor = accentColor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(int primaryColor) {
        this.primaryColor = primaryColor;
    }

    public int getPrimaryDarkColor() {
        return primaryDarkColor;
    }

    public void setPrimaryDarkColor(int primaryDarkColor) {
        this.primaryDarkColor = primaryDarkColor;
    }

    public int getAccentColor() {
        return accentColor;
    }

    public void setAccentColor(int accentColor) {
        this.accentColor = accentColor;
    }

    @Override
    public String toString() {
        return GsonUtils.convertToJSON(this);
    }
}
