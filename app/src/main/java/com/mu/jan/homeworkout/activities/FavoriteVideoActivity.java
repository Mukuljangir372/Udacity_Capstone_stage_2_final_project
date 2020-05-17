package com.mu.jan.homeworkout.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.AsyncTask;
import android.os.Bundle;

import com.mu.jan.homeworkout.Component.DaggerUniversalComponent;
import com.mu.jan.homeworkout.Component.UniversalComponent;
import com.mu.jan.homeworkout.Module.UniversalDaggerModule;
import com.mu.jan.homeworkout.R;
import com.mu.jan.homeworkout.adapters.FavoriteVideoAdapter;
import com.mu.jan.homeworkout.database.MyRoomDatabase;
import com.mu.jan.homeworkout.database.VideoEntity;

import java.util.List;

import javax.inject.Inject;

public class FavoriteVideoActivity extends AppCompatActivity {

    @BindView(R.id.favorite_recyclerView) RecyclerView recyclerView;
    @Inject LinearLayoutManager linearLayoutManager;
    private UniversalComponent component;
    private MyRoomDatabase db;
    private List<VideoEntity> videoEntityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_video);

        //bind
        ButterKnife.bind(this);
        //init dagger 2
        component = DaggerUniversalComponent.builder()
                .universalDaggerModule(new UniversalDaggerModule(FavoriteVideoActivity.this))
                .build();
        //inject
        component.inject(FavoriteVideoActivity.this);
        //action bar
        getSupportActionBar().setTitle(getResources().getString(R.string.favorite_video));
        //database
        db = MyRoomDatabase.getInstance(this);
        //recycler layout manager
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                videoEntityList = db.videoDao().getFavoriteVideoList();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setAdapter(new FavoriteVideoAdapter(FavoriteVideoActivity.this,videoEntityList));
                    }
                });
            }
        });

    }
}
