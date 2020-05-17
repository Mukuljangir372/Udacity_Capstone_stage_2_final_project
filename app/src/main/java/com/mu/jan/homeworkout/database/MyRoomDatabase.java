package com.mu.jan.homeworkout.database;

import android.content.Context;

import com.mu.jan.homeworkout.utils.AppConstant;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {VideoEntity.class},exportSchema = false,version = 860)
public abstract class MyRoomDatabase extends RoomDatabase {

    public static MyRoomDatabase instance;
    public static synchronized MyRoomDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context,MyRoomDatabase.class,
                    AppConstant.DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
       return instance;
    }
    public abstract VideoDao videoDao();
}
