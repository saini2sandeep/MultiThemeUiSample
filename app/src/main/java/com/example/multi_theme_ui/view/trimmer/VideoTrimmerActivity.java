package com.example.multi_theme_ui.view.trimmer;

import android.app.ProgressDialog;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.multi_theme_ui.AppConstants;
import com.example.multi_theme_ui.BaseActivity;
import com.example.multi_theme_ui.R;

public class VideoTrimmerActivity extends BaseActivity implements VideoTrimListener {


    private ProgressDialog mProgressDialog;
    VideoTrimmerView trimmerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_trimmer);


        inItUi();
    }

    private void inItUi() {

        trimmerView = findViewById(R.id.trimmer_view);

        if (getIntent() != null) {
            Bundle bd = getIntent().getExtras();
            String path = "";
            if (bd != null) path = bd.getString(AppConstants.BUNDLE_VIDEO_PATH_KEY);
            if (trimmerView != null) {
                trimmerView.setOnTrimVideoListener(this);
                trimmerView.initVideoByURI(Uri.parse(path));


            }

        }

    }

    @Override
    public void onStartTrim() {
        buildDialog(getResources().getString(R.string.trimming)).show();
    }

    @Override
    public void onFinishTrim(String url) {
        if (mProgressDialog.isShowing()) mProgressDialog.dismiss();
        Toast.makeText(this, getString(R.string.trimmed_done),Toast.LENGTH_SHORT).show();
        finish();
        //TODO: please handle your trimmed video url here!!!
        //String out = StorageUtil.getCacheDir() + File.separator + COMPRESSED_VIDEO_FILE_NAME;
        //buildDialog(getResources().getString(R.string.compressing)).show();
        //VideoCompressor.compress(this, in, out, new VideoCompressListener() {
        //  @Override public void onSuccess(String message) {
        //  }
        //
        //  @Override public void onFailure(String message) {
        //  }
        //
        //  @Override public void onFinish() {
        //    if (mProgressDialog.isShowing()) mProgressDialog.dismiss();
        //    finish();
        //  }
        //});
    }

    @Override
    public void onCancel() {
        trimmerView.onDestroy();
        finish();
    }

    private ProgressDialog buildDialog(String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog.show(this, "", msg);
        }
        mProgressDialog.setMessage(msg);
        return mProgressDialog;
    }
}
