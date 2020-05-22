package com.example.board;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class Display_Videos extends AppCompatActivity {

    private List<Video> mVideosList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display__videos);
        //assign video
        ListView mVideosListView = findViewById(R.id.videoListView);

        //create videos
        Video riverVideo = new Video("http://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4");
        Video carsVideo = new Video("https://s3.amazonaws.com/androidvideostutorial/862013714.mp4");
        Video townVideo = new Video("https://www.youtube.com/watch?v=OZLUa8JUR18&feature=youtu.be&list=RDOZLUa8JUR18");
        Video whiteCarVideo = new Video("https://youtu.be/_HMjOiHqE18?t=8");
        Video parkVideo = new Video("https://s3.amazonaws.com/androidvideostutorial/862014834.mp4");
        Video busyCityVideo = new Video("https://s3.amazonaws.com/androidvideostutorial/862017385.mp4");

        mVideosList.add(riverVideo);
        mVideosList.add(carsVideo);
        mVideosList.add(townVideo);
        mVideosList.add(whiteCarVideo);
        mVideosList.add(parkVideo);
        mVideosList.add(busyCityVideo);

        /***populate video list to adapter**/
        VideoAdapter mVideoAdapter = new VideoAdapter(this, mVideosList);
        mVideosListView.setAdapter(mVideoAdapter);
    }
}
