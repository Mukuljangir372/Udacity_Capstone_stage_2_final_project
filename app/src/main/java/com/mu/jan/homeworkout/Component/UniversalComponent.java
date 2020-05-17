package com.mu.jan.homeworkout.Component;

import com.mu.jan.homeworkout.Module.UniversalDaggerModule;
import com.mu.jan.homeworkout.activities.FavoriteVideoActivity;
import com.mu.jan.homeworkout.activities.WorkoutListActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {UniversalDaggerModule.class})
public interface UniversalComponent {
    public void inject(WorkoutListActivity workoutListActivity);
    public void inject(FavoriteVideoActivity favoriteVideoActivity);
}
