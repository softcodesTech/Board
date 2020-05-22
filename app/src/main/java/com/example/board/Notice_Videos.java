package com.example.board;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;
import java.util.ArrayList;


public class Notice_Videos extends AppCompatActivity implements MediaPlayer.OnCompletionListener{


    VideoView videoView;
    VideoView vw;
    ArrayList<Integer> videolist = new ArrayList<>();
    int currvideo = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.video_list);
        vw = findViewById(R.id.video_news);
        vw.setMediaController(new MediaController(this));
        vw.setOnCompletionListener(this);

        // video name should be in lower case alphabet.
        videolist.add(R.raw.middle);
        videolist.add(R.raw.fishvideo);
        videolist.add(R.raw.oneday);
        setVideo(videolist.get(0));

    }

    public void setVideo(int id)
    {
        String uriPath
                = "android.resource://"
                + getPackageName() + "/" + id;

        Uri uri = Uri.parse(uriPath);
        vw.setVideoURI(uri);
        vw.start();
    }

    public void onCompletion(MediaPlayer mediapalyer)
    {
        AlertDialog.Builder obj = new AlertDialog.Builder(this);
        obj.setTitle("Playback Finished!");
        obj.setIcon(R.mipmap.ic_launcher);
        MyListener m = new MyListener();
        obj.setPositiveButton("Replay", m);
        obj.setNegativeButton("Next", m);
        obj.setMessage("Want to replay or play next video?");
        obj.show();
    }

    public void reply_video(View view) {
        Intent reply = new Intent(getApplicationContext(),Reply_Comment.class);
        startActivity(reply);
        finish();
    }

    class MyListener implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which)
        {
            if (which == -1) {
                vw.seekTo(0);
                vw.start();
            }
            else {
                ++currvideo;
                if (currvideo == videolist.size())
                    currvideo = 0;
                setVideo(videolist.get(currvideo));
            }
        }
    }
}
