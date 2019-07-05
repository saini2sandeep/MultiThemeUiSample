package com.example.multi_theme_ui.utils;

import android.content.Context;

import java.lang.ref.WeakReference;

/**
 * Created by sandeepsaini on 03,July,2019
 */
public class BaseUtils {

    private static final String ERROR_INIT = "Initialize BaseUtils with invoke init()";

    private static WeakReference<Context> mWeakReferenceContext;

    /**
     * init in Application
     */
    public static void init(Context ctx){
        mWeakReferenceContext = new WeakReference<>(ctx);
        //something to do...
    }

    public static Context getContext() {
        if (mWeakReferenceContext == null) {
            throw new IllegalArgumentException(ERROR_INIT);
        }
        return mWeakReferenceContext.get().getApplicationContext();
    }
}
