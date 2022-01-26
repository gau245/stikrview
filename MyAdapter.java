package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.Viewholder> {
    ArrayList<VideoRVModel>  list;
    Context context;
    private  VideoClickInterface videoClickInterface;

    public MyAdapter(ArrayList<VideoRVModel> list, Context context, VideoClickInterface videoClickInterface) {
        this.list = list;
        this.context = context;
        this.videoClickInterface = videoClickInterface;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from( parent.getContext()).inflate( R.layout.itme_file,parent,false );
        return new Viewholder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        VideoRVModel videoRVModel=list.get( position );
        holder.thumbnailIv.setImageBitmap( videoRVModel.getThumbNail() );
        holder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoClickInterface.onVideoclick( position );
            }
        } );
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Viewholder extends RecyclerView.ViewHolder {

        ImageView thumbnailIv;
        public Viewholder(@NonNull View itemView) {
            super( itemView );
            thumbnailIv=itemView.findViewById( R.id.idIvThumbNail );
        }
    }
    public interface VideoClickInterface{
        void onVideoclick(int position);
    }
}
