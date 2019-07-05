package com.example.multi_theme_ui.view.select;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.multi_theme_ui.AppConstants;
import com.example.multi_theme_ui.AppPermission;
import com.example.multi_theme_ui.BaseActivity;
import com.example.multi_theme_ui.R;
import com.example.multi_theme_ui.utils.FunctionUtils;
import com.example.multi_theme_ui.view.trimmer.VideoTrimmerActivity;
import com.example.multi_theme_ui.view.trimmer.VideoTrimmerView;

import java.net.URISyntaxException;

import pub.devrel.easypermissions.EasyPermissions;

public class VideoSelectActivity extends BaseActivity {


    private AppPermission appPermission;
    private String videoUrl;

    private String videoThumbNail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_select);

        allowPermissions();
    }

    private void allowPermissions() {

        appPermission = new AppPermission(this);


        if (appPermission.hasReadPermission(this)) {
            pickVideoFromGallery();

        } else {
            EasyPermissions.requestPermissions(this, "Storage Permission are compulsory",
                    AppConstants.REQUEST_CODE_STORAGE_PERMISSION, Manifest.permission.READ_EXTERNAL_STORAGE);

        }
    }

    private void pickVideoFromGallery() {

        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Video"), AppConstants.REQUEST_CODE_TAKE_GALLERY_VIDEO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && data != null) {

            if (requestCode == AppConstants.REQUEST_CODE_TAKE_GALLERY_VIDEO) {


                Uri mediaUri = data.getData();
                String mediaLocalPath = null;

                try {
                    mediaLocalPath = FunctionUtils.getLocalPath(this, mediaUri);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }


                if (mediaLocalPath != null && !TextUtils.isEmpty(mediaLocalPath)) {

                    try {
                        Bitmap thumb = ThumbnailUtils.createVideoThumbnail(mediaLocalPath, MediaStore.Video.Thumbnails.MICRO_KIND);

                        videoUrl = mediaLocalPath;
                        videoThumbNail = FunctionUtils.convertBitMapToString(thumb);

                        Intent intent = new Intent(this, VideoTrimmerActivity.class);
                        intent.putExtra(AppConstants.BUNDLE_VIDEO_PATH_KEY,videoUrl);
                        startActivity(intent);



//                        dataBinding.feedIv.setImageBitmap(FunctionUtils.convertStringToBitMap(videoThumbNail));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show();
        }

    }
}
