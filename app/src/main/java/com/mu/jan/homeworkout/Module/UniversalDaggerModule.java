package com.mu.jan.homeworkout.Module;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.mu.jan.homeworkout.api.YoutubeApiInterface;
import com.mu.jan.homeworkout.utils.AppConstant;

import javax.inject.Singleton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.versionedparcelable.VersionedParcelize;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class UniversalDaggerModule {

    private Context context;
    public UniversalDaggerModule(Context context){
        this.context = context;
    }

    @Provides
    @Singleton
    public Retrofit retrofit(){
        return new Retrofit.Builder()
                .baseUrl(AppConstant.YOUTUBE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public YoutubeApiInterface youtubeApiInterface(Retrofit retrofit){
        return retrofit.create(YoutubeApiInterface.class);
    }
    @Provides
    @Singleton
    public LinearLayoutManager linearLayoutManager(){
        return new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
    }


}
