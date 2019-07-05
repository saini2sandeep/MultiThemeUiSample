package com.example.multi_theme_ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by sandeepsaini on 03,July,2019
 */
public class AppPermission {

    private Activity activity;

    public AppPermission(Activity activity) {
        this.activity = activity;
    }


    public boolean hasCameraPermission(Context context) {
        return EasyPermissions.hasPermissions(context, Manifest.permission.CAMERA);
    }

    public boolean hasSmsPermission(Context context) {
        return EasyPermissions.hasPermissions(context, Manifest.permission.READ_SMS);
    }

    public boolean hasStoragePermission(Context context) {
        return EasyPermissions.hasPermissions(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    public boolean hasReadPermission(Context context){
        return EasyPermissions.hasPermissions(context,Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    public boolean hasLocationPermission(Context context) {
        return EasyPermissions.hasPermissions(context, Manifest.permission.ACCESS_FINE_LOCATION);
    }
}
