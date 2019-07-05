package com.example.multi_theme_ui.prefrences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by sandeepsaini on 27,June,2019
 */
public class JsonHashMap extends LinkedHashMap<String, Object> {

    public JsonHashMap() {
        super();
    }

    public JsonHashMap(String jsonString) {
        super();
        putAll((Map<? extends String, ?>) new Gson().fromJson(jsonString, new TypeToken<LinkedHashMap<String, Object>>() {
        }.getType()));
    }


    @Override
    public String toString() {
        return GsonUtils.convertToJSON(this);
    }
}