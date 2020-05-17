package com.mu.jan.homeworkout.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.mu.jan.homeworkout.R;
import com.mu.jan.homeworkout.adapters.WorkoutListAdapter;
import com.mu.jan.homeworkout.data.WorkoutListDataContainer;
import com.mu.jan.homeworkout.model.WorkoutList;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_recyclerView) RecyclerView recyclerView;
    @BindView(R.id.adView_banner) AdView adView;
    @BindView(R.id.main_toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //bind
        ButterKnife.bind(this);
        //toolbar
        setSupportActionBar(toolbar);
        //ads init
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        //recyclerView layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        //load list
        recyclerView.setAdapter(new WorkoutListAdapter(MainActivity.this,new WorkoutListDataContainer().provideWorkoutDataList(MainActivity.this)));
        //load ad
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.favorite:
                Intent i = new Intent(this,FavoriteVideoActivity.class);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);

    }
}
