package com.mu.jan.homeworkout.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.mu.jan.homeworkout.R;
import com.mu.jan.homeworkout.activities.WorkoutDetailedActivity;
import com.mu.jan.homeworkout.model.Video;
import com.mu.jan.homeworkout.model.VideoItem;
import com.mu.jan.homeworkout.utils.AppConstant;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private Context context;
    private List<VideoItem> list;

    public VideoAdapter(Context context, List<VideoItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_workout_video_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //load image using Glide
        try{
            //when loading thumbnails from youtube api
            //sometimes Glide unable to load images and crash
            Glide.with(context).load(list.get(position).getSnippet().getThumbnails().getThumbnail_medium().getUrl()).into(holder.imageView);
        }catch (Exception e){
            Log.d(AppConstant.TAG_FAVORITE_VIDEO,context.getResources().getString(R.string.glide_error));
        }

        //load text
        holder.title_name.setText(list.get(position).getSnippet().getTitle());
        holder.channel_name.setText(list.get(position).getSnippet().getChannel_title());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, WorkoutDetailedActivity.class);
                //push event using EventBus
                EventBus.getDefault().postSticky(new Video(list.get(position).getResourceId().getVideo_id(),list.get(position).getSnippet().getTitle(),list.get(position).getSnippet().getDescription(),list.get(position).getSnippet().getThumbnails().getThumbnail_medium().getUrl()));
                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.video_thumb_imageView) ImageView imageView;
        @BindView(R.id.video_title_text) TextView title_name;
        @BindView(R.id.channel_name_text) TextView channel_name;
        @BindView(R.id.video_layout) RelativeLayout relativeLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            //bind
            ButterKnife.bind(this,itemView);
        }
    }
}
