package com.mu.jan.homeworkout.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mu.jan.homeworkout.R;
import com.mu.jan.homeworkout.data.WorkoutTipsContainer;
import com.mu.jan.homeworkout.database.MyRoomDatabase;
import com.mu.jan.homeworkout.database.VideoDao;
import com.mu.jan.homeworkout.database.VideoEntity;
import com.mu.jan.homeworkout.model.Video;
import com.mu.jan.homeworkout.utils.AppConstant;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.UUID;

public class WorkoutDetailedActivity extends AppCompatActivity {

    private String video_id;
    private String video_des;
    private String video_thumb;
    private String video_title;
    private VideoEntity videoEntity;
    private MyRoomDatabase db;
    @BindView(R.id.youtubePlayer_view) YouTubePlayerView youTubePlayerView;
    @BindView(R.id.workout_done_btn) Button done_btn;
    @BindView(R.id.saveVideo_btn) Button saveVideo_btn;
    @BindView(R.id.workout_tips_text) TextView textView_tips;
    @BindView(R.id.video_descriptionText) TextView textView_des;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_detailed);

        //bind
        ButterKnife.bind(this);
        //EventBus reg
        EventBus.getDefault().register(this);
        //Room
        db = MyRoomDatabase.getInstance(this);
        //set title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Access database
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                videoEntity = db.videoDao().isVideoSaved(video_id);
                if(videoEntity!=null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            saveVideo_btn.setBackgroundColor(getResources().getColor(R.color.colorAccentDark));
                            saveVideo_btn.setText(getResources().getString(R.string.saved));
                        }
                    });
                }
            }
        });


        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(video_id,0);
            }
        });

        textView_des.setText(video_des);
        textView_tips.setText(new WorkoutTipsContainer().getTip());

        done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(youTubePlayerView!=null)youTubePlayerView.release();
                onBackPressed();
            }
        });

        saveVideo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        videoEntity = db.videoDao().isVideoSaved(video_id);
                        if(videoEntity==null){
                            db.videoDao().insertVideo(new VideoEntity(video_id,video_title,video_des,video_thumb));
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    saveVideo_btn.setBackgroundColor(getResources().getColor(R.color.colorAccentDark));
                                    saveVideo_btn.setText(getResources().getString(R.string.saved));
                                }
                            });
                        }else{
                            db.videoDao().deleteVideo(video_id);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    saveVideo_btn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                                    saveVideo_btn.setText(getResources().getString(R.string.save));
                                }
                            });
                        }
                    }
                });

            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onEventBusVideoData(Video container){
        //get data and init
        video_id = container.getVideo_id();
        video_des = container.getVideo_des();
        video_title = container.getVideo_title();
        video_thumb = container.getVideo_thumb();
        //remove sticky
        EventBus.getDefault().removeStickyEvent(Video.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(youTubePlayerView!=null)youTubePlayerView.release();
        //unRegister
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
