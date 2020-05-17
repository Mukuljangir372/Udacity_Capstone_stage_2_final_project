package com.mu.jan.homeworkout.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mu.jan.homeworkout.R;
import com.mu.jan.homeworkout.activities.WorkoutListActivity;
import com.mu.jan.homeworkout.model.WorkoutList;
import com.mu.jan.homeworkout.utils.AppConstant;

import java.util.List;

import androidx.annotation.BinderThread;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkoutListAdapter extends RecyclerView.Adapter<WorkoutListAdapter.ViewHolder> {

    private Context context;
    private List<WorkoutList> list;
    public WorkoutListAdapter(Context context,List<WorkoutList> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_workout_item_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //load image
        Glide.with(context).load(list.get(position).getImage_id()).into(holder.workout_img);
        //workout name
        holder.workout_name.setText(list.get(position).getWorkout_name());

        //onClick
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, WorkoutListActivity.class);
                i.putExtra(AppConstant.INTENT_WORKOUT_NAME_KEY,list.get(position).getWorkout_name());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.workout_name_text) TextView workout_name;
        @BindView(R.id.workout_image) ImageView workout_img;
        @BindView(R.id.workout_item_relative_layout) RelativeLayout relativeLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            //bind
            ButterKnife.bind(this,itemView);
        }
    }
}
