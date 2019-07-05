package com.example.multi_theme_ui.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by sandeepsaini on 04,July,2019
 */
public class StorageUtil {

    private static final String TAG = "StorageUtil";

    private static String sDataDir;
    private static String sCacheDir;

    public static String getCacheDir() {
        if (TextUtils.isEmpty(sCacheDir)) {
            File file = null;
            Context context = BaseUtils.getContext();
            try {
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    file = context.getExternalCacheDir();
                    if (file == null || !file.exists()) {
                        file = getExternalCacheDirManual(context);
                    }
                }
                if (file == null) {
                    file = context.getCacheDir();
                    if (file == null || !file.exists()) {
                        file = getCacheDirManual(context);
                    }
                }
                Log.w(TAG, "cache dir = " + file.getAbsolutePath());
                sCacheDir = file.getAbsolutePath();
            } catch (Throwable ignored) {
            }
        }
        return sCacheDir;
    }

    private static File getExternalCacheDirManual(Context context) {
        File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
        File appCacheDir = new File(new File(dataDir, context.getPackageName()), "cache");
        if (!appCacheDir.exists()) {
            if (!appCacheDir.mkdirs()) {//
                Log.w(TAG, "Unable to create external cache directory");
                return null;
            }
            try {
                new File(appCacheDir, ".nomedia").createNewFile();
            } catch (IOException e) {
                Log.i(TAG, "Can't create \".nomedia\" file in application external cache directory");
            }
        }
        return appCacheDir;
    }

    private static File getCacheDirManual(Context context) {
        String cacheDirPath = "/data/data/" + context.getPackageName() + "/cache";
        return new File(cacheDirPath);
    }

    // Function description: delete all files and folders under the folder
    public static boolean delFiles(String path) {
        File cacheFile = new File(path);
        if (!cacheFile.exists()) {
            return false;
        }
        File[] files = cacheFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // Is the file is deleted directly
            if (files[i].exists() && files[i].isFile()) {
                files[i].delete();
            } else if (files[i].exists() && files[i].isDirectory()) {
                // Recursively delete files
                delFiles(files[i].getAbsolutePath());
                // Delete all files below the directory and then delete the folder
                files[i].delete();
            }
        }

        return true;
    }


}
