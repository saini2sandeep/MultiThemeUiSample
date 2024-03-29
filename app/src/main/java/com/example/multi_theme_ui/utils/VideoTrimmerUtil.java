package com.example.multi_theme_ui.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.multi_theme_ui.view.trimmer.SingleCallBack;
import com.example.multi_theme_ui.view.trimmer.VideoTrimListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import nl.bravobit.ffmpeg.ExecuteBinaryResponseHandler;
import nl.bravobit.ffmpeg.FFmpeg;


/**
 * Created by sandeepsaini on 03,July,2019
 */
public class VideoTrimmerUtil {

    public static final int VIDEO_MAX_TIME = 10;// 10秒
    public static final long MAX_SHOOT_DURATION = VIDEO_MAX_TIME * 1000L;//How long does the video cut for up to 10s?
    public static final int MAX_COUNT_RANGE = 10;  //seekBar的区域内一共有多少张图片
    public static final long MIN_SHOOT_DURATION = 3000L;// 最小剪辑时间3s


    private static final int SCREEN_WIDTH_FULL = DeviceUtil.getDeviceWidth();
    public static final int RECYCLER_VIEW_PADDING = UnitConverter.dpToPx(35);
    public static final int VIDEO_FRAMES_WIDTH = SCREEN_WIDTH_FULL - RECYCLER_VIEW_PADDING * 2;
    private static final int THUMB_WIDTH = (SCREEN_WIDTH_FULL - RECYCLER_VIEW_PADDING * 2) / VIDEO_MAX_TIME;
    private static final int THUMB_HEIGHT = UnitConverter.dpToPx(50);


    public static void shootVideoThumbInBackground(final Context context, final Uri videoUri, final int totalThumbsCount, final long startPosition,
                                                   final long endPosition, final SingleCallBack<Bitmap, Integer> callback) {
//        BackgroundExecutor.execute(new BackgroundExecutor.Task("", 0L, "") {
//            @Override public void execute() {
//                try {

        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(context, videoUri);
        // Retrieve media data use microsecond
        long interval = (endPosition - startPosition) / (totalThumbsCount - 1);
        for (long i = 0; i < totalThumbsCount; ++i) {
            long frameTime = startPosition + interval * i;
            Bitmap bitmap = mediaMetadataRetriever.getFrameAtTime(frameTime * 1000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
            if (bitmap == null) continue;
            try {
                bitmap = Bitmap.createScaledBitmap(bitmap, THUMB_WIDTH, THUMB_HEIGHT, false);
            } catch (final Throwable t) {
                t.printStackTrace();
            }
            callback.onSingleCallback(bitmap, (int) interval);
        }
        mediaMetadataRetriever.release();
//                } catch (final Throwable e) {
//                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
//                }
//            }
//        });
    }

    public static void trim(Context context, String inputFile, String outputFile, long startMs, long endMs, final VideoTrimListener callback) {
        final String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        final String outputName = "trimmedVideo_" + timeStamp + ".mp4";
        outputFile = outputFile + "/" + outputName;

        String start = convertSecondsToTime(startMs / 1000);
        String duration = convertSecondsToTime((endMs - startMs) / 1000);
        //String start = String.valueOf(startMs);
        //String duration = String.valueOf(endMs - startMs);

        /** Crop video ffmpeg instruction description：
         * ffmpeg -ss START -t DURATION -i INPUT -codec copy -avoid_negative_ts 1 OUTPUT
         -ss Start time, such as： 00:00:20，Indicates starting from 20 seconds；
         -t Duration, such as： 00:00:10，Indicates that the video is captured for 10 seconds.；
         -i Input, followed by a space, followed by the input video file；
         -codec copy -avoid_negative_ts 1 Indicates the encoding format of the video and audio to be used. This is specified as copy to indicate the original copy.；
         INPUT，Input video file；
         OUTPUT，Output video file
         */
        //TODO: Here are some instructions
        //https://trac.ffmpeg.org/wiki/Seeking
        //https://superuser.com/questions/138331/using-ffmpeg-to-cut-up-video

        String cmd = "-ss " + start + " -t " + duration + " -accurate_seek" + " -i " + inputFile + " -codec copy -avoid_negative_ts 1 " + outputFile;
//        String cmd = "-ss " + start + " -i " + inputFile + " -ss " + start + " -t " + duration + " -vcodec copy " + outputFile;
        //{"ffmpeg", "-ss", "" + startTime, "-y", "-i", inputFile, "-t", "" + induration, "-vcodec", "mpeg4", "-b:v", "2097152", "-b:a", "48000", "-ac", "2", "-ar", "22050", outputFile}
        //String cmd = "-ss " + start + " -y " + "-i " + inputFile + " -t " + duration + " -vcodec " + "mpeg4 " + "-b:v " + "2097152 " + "-b:a " + "48000 " + "-ac " + "2 " + "-ar " + "22050 "+ outputFile;
        String[] command = cmd.split(" ");
        try {
            final String tempOutFile = outputFile;
            FFmpeg.getInstance(context).execute(command, new ExecuteBinaryResponseHandler() {
                @Override
                public void onFailure(String message) {
                    super.onFailure(message);

                    Log.d("FFmpeg :  ", "Failure -- " + message);
                }

                @Override
                public void onFinish() {
                    super.onFinish();
                    Log.d("FFmpeg : ", " onFinish");

                }

                @Override
                public void onProgress(String message) {
                    super.onProgress(message);
                    Log.d("FFmpeg : ", " onProgress : " + message);

                }

                @Override
                public void onStart() {
                    super.onStart();
                    Log.d("FFmpeg : ", " onStart");
                    callback.onStartTrim();

                }

                @Override
                public void onSuccess(String message) {
                    super.onSuccess(message);
                    callback.onFinishTrim(tempOutFile);
                    Log.d("FFmpeg :  ", " onSuccess " + message);

                }
            });


//            FFmpeg.getInstance(context).execute(command, new ExecuteBinaryResponseHandler() {
//
//                @Override public void onSuccess(String s) {
//                    callback.onFinishTrim(tempOutFile);
//                }
//
//                @Override public void onStart() {
//                    callback.onStartTrim();
//                }
//            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Failed Trimming...", Toast.LENGTH_SHORT).show();
        }
    }


    private static String convertSecondsToTime(long seconds) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (seconds <= 0) {
            return "00:00";
        } else {
            minute = (int) seconds / 60;
            if (minute < 60) {
                second = (int) seconds % 60;
                timeStr = "00:" + unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99) return "99:59:59";
                minute = minute % 60;
                second = (int) (seconds - hour * 3600 - minute * 60);
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    private static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10) {
            retStr = "0" + Integer.toString(i);
        } else {
            retStr = "" + i;
        }
        return retStr;
    }

}
