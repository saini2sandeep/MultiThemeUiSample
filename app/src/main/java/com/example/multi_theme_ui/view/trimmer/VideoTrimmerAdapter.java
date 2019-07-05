package com.example.multi_theme_ui.view.trimmer;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.multi_theme_ui.R;
import com.example.multi_theme_ui.utils.VideoTrimmerUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandeepsaini on 03,July,2019
 */
public class VideoTrimmerAdapter extends RecyclerView.Adapter {
    private List<Bitmap> mBitmaps = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TrimmerViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.video_thumb_item_layout, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((TrimmerViewHolder) viewHolder).thumbImageView.setImageBitmap(mBitmaps.get(i));
    }

    @Override
    public int getItemCount() {
        return mBitmaps == null ? 0 : mBitmaps.size();
    }

    public void addBitmaps(Bitmap bitmap) {
        mBitmaps.add(bitmap);
        notifyDataSetChanged();
    }

    private final class TrimmerViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbImageView;

        TrimmerViewHolder(View itemView) {
            super(itemView);
            thumbImageView = itemView.findViewById(R.id.thumb);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) thumbImageView.getLayoutParams();
            layoutParams.width = VideoTrimmerUtil.VIDEO_FRAMES_WIDTH / VideoTrimmerUtil.MAX_COUNT_RANGE;
            thumbImageView.setLayoutParams(layoutParams);
        }
    }
}
