package com.example.multi_theme_ui.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.multi_theme_ui.MainActivity;
import com.example.multi_theme_ui.R;
import com.example.multi_theme_ui.model.Theme;

import java.util.List;

/**
 * Created by sandeepsaini on 27,June,2019
 */
public class ThemeListAdapter extends RecyclerView.Adapter<ThemeListAdapter.ThemeListViewHolder> {

    public interface ThemeItemClickListener {
        void onThemeClick(View v , int position);
    }

    private List<Theme> themeList;
    private ThemeItemClickListener listener;


    public ThemeListAdapter(List<Theme> themeList, ThemeItemClickListener themeItemClickListener) {
        this.themeList = themeList;
        this.listener = themeItemClickListener;
    }

    @NonNull
    @Override
    public ThemeListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_theme, viewGroup, false);
        return new ThemeListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ThemeListViewHolder themeListViewHolder, int i) {

        themeListViewHolder.bindItem(themeList.get(i));
    }

    @Override
    public int getItemCount() {
        return themeList.size();
    }

    public class ThemeListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ThemeListViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        public void bindItem(Theme theme) {

            ThemeView themeView = (ThemeView) itemView.findViewById(R.id.themeView);
            themeView.setTheme(theme);

        }

        @Override
        public void onClick(View v) {
            listener.onThemeClick(v, getAdapterPosition());

            MainActivity.selectedTheme = getAdapterPosition();
            MainActivity.mTheme = MainActivity.mThemeList.get(getAdapterPosition()).getId();
//            themeView.setActivated(true);
            ThemeListAdapter.this.notifyDataSetChanged();
        }
    }
}
