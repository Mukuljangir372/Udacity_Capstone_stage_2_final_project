package com.mu.jan.homeworkout.data;

import android.content.Context;

import com.mu.jan.homeworkout.R;
import com.mu.jan.homeworkout.model.WorkoutList;

import java.util.ArrayList;
import java.util.List;

public class WorkoutListDataContainer {
    public List<WorkoutList> provideWorkoutDataList(Context context){
        List<WorkoutList> list = new ArrayList<>();
        list.add(new WorkoutList(context.getResources().getString(R.string.full_body_workout), R.drawable.full_body_workout));
        list.add(new WorkoutList(context.getResources().getString(R.string.arms_workout),R.drawable.arms_workout));
        list.add(new WorkoutList(context.getResources().getString(R.string.abs_workout),R.drawable.abs_workout));
        list.add(new WorkoutList(context.getResources().getString(R.string.back_and_shoulder_workout),R.drawable.back_workout));
        list.add(new WorkoutList(context.getResources().getString(R.string.upper_body_workout),R.drawable.upper_body_workout));
        list.add(new WorkoutList(context.getResources().getString(R.string.lower_body_workout),R.drawable.leg_workout));
        return list;
    }
}
