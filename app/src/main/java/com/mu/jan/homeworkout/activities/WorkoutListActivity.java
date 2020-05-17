package com.mu.jan.homeworkout.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mu.jan.homeworkout.Component.DaggerUniversalComponent;
import com.mu.jan.homeworkout.Component.UniversalComponent;
import com.mu.jan.homeworkout.Module.UniversalDaggerModule;
import com.mu.jan.homeworkout.R;
import com.mu.jan.homeworkout.adapters.VideoAdapter;
import com.mu.jan.homeworkout.api.YoutubeApiInterface;
import com.mu.jan.homeworkout.model.VideoItem;
import com.mu.jan.homeworkout.model.VideoResult;
import com.mu.jan.homeworkout.utils.AppConstant;

import java.util.List;

import javax.inject.Inject;

public class WorkoutListActivity extends AppCompatActivity {


    @Inject Retrofit retrofit;
    @Inject YoutubeApiInterface youtubeApiInterface;
    @Inject LinearLayoutManager linearLayoutManager;

    @BindView(R.id.progress_Bar) ProgressBar progressBar;
    @BindView(R.id.video_list_recyclerView) RecyclerView recyclerView;

    private String workout_name = "";
    private UniversalComponent component;
    private Observable<VideoResult> videoResultObservable;
    private List<VideoItem> videoItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_list);

        //bind
        ButterKnife.bind(this);
        //init dagger 2
        component = DaggerUniversalComponent.builder()
                .universalDaggerModule(new UniversalDaggerModule(WorkoutListActivity.this))
                .build();
        //inject
        component.inject(WorkoutListActivity.this);
        //recycler layout manager
        recyclerView.setLayoutManager(linearLayoutManager);

        //get Intent
        Intent i = getIntent();
        if(i.hasExtra(AppConstant.INTENT_WORKOUT_NAME_KEY)){
            workout_name = i.getStringExtra(AppConstant.INTENT_WORKOUT_NAME_KEY);
            //set text to actionbar
            getSupportActionBar().setTitle(workout_name);
        }

        videoResultObservable = youtubeApiInterface.getSearchVideos(getResources().getString(R.string.api_key),"snippet",workout_name+"for men at home","video",49);
        videoResultObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<VideoResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                      progressBar.setVisibility(View.VISIBLE);
                    }
                    @Override
                    public void onNext(VideoResult value) {
                        videoItemList = value.getVideoItemList();
                    }
                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(WorkoutListActivity.this,getResources().getString(R.string.internet_error),Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onComplete() {
                        progressBar.setVisibility(View.GONE);
                        if(videoItemList!=null)recyclerView.setAdapter(new VideoAdapter(WorkoutListActivity.this,videoItemList));
                    }
                });
    }
}
