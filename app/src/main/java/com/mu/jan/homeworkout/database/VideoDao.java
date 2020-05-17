package com.mu.jan.homeworkout.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface VideoDao {

    @Query("SELECT * FROM videoEntity")
    List<VideoEntity> getFavoriteVideoList();
    @Query("SELECT * FROM videoEntity WHERE videoId LIKE :video_id")
    VideoEntity isVideoSaved(String video_id);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertVideo(VideoEntity video);
    @Query("DELETE FROM videoEntity WHERE videoId LIKE :video_id")
    void deleteVideo(String video_id);

}
