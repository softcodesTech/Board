package com.example.board;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.VideoView;
import androidx.annotation.NonNull;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class VideoAdapter extends ArrayAdapter<Video> {

    private Context mContext;
    private List<Video> mVideos;

    public VideoAdapter(@NonNull Context context, @NonNull List<Video> objects) {
        super(context, R.layout.video_row, objects);

        mContext = context;
        mVideos = objects;
    }

    @NotNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;


        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.video_row, null);
            holder = new ViewHolder();

            holder.videoView = convertView
                    .findViewById(R.id.videoView);

            convertView.setTag(holder);
        } else {

            holder = (ViewHolder) convertView.getTag();

        }

        /***get clicked view and play video url at this position**/
        try {
            Video video = mVideos.get(position);
            //play video using android api, when video view is clicked.
            String url = video.getVideoUrl(); // your URL here
            Uri videoUri = Uri.parse(url);
            holder.videoView.setVideoURI(videoUri);
            holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(true);
                    holder.videoView.start();
                }
            });



        } catch (Exception e) {
            e.printStackTrace();
        }


        return convertView;
    }

    public static class ViewHolder {
        VideoView videoView;

    }
}
