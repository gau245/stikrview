package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.INotificationSideChannel;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.exoplayer2.ui.PlayerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MyAdapter.VideoClickInterface {

    RecyclerView idRVvideos;
    ArrayList<VideoRVModel> list;
    MyAdapter myAdapter;
    private  static final int STORAGE_PERMISSION=11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        idRVvideos=findViewById( R.id.idRVvideos );
        list=new ArrayList<>();
        myAdapter =new MyAdapter(list ,this,this::onVideoclick );
        idRVvideos.setLayoutManager(new  GridLayoutManager(this,2) );
        idRVvideos.setAdapter(myAdapter);

        if (ContextCompat.checkSelfPermission( MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE )== PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions( MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION );
        }else {
            getVideo();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );
        if (requestCode==STORAGE_PERMISSION){
                if (grantResults.length>0 &&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText( MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT ).show();
                    getVideo();
                }  else {
                    Toast.makeText( MainActivity.this, "Tha App will not work without permission", Toast.LENGTH_SHORT ).show();
                    finish();
                }
        }
    }

    private void getVideo() {
        ContentResolver contentResolver=getContentResolver();
        Uri uri= MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor=contentResolver.query(uri,null,null,null,null);
        if (cursor!=null && cursor.moveToFirst()){
            do {
                @SuppressLint("Range") String videoTitle=cursor.getString( cursor.getColumnIndex( MediaStore.Video.Media.TITLE));
                @SuppressLint("Range") String videoPath=cursor.getString( cursor.getColumnIndex( MediaStore.Video.Media.DATA));
                @SuppressLint("Range") Bitmap videoThumbnail= ThumbnailUtils.createVideoThumbnail( videoPath,MediaStore.Images.Thumbnails.MICRO_KIND );
                list.add(new VideoRVModel( videoTitle,videoPath,videoThumbnail ));
            }while (cursor.moveToNext());
        }
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void onVideoclick(int position) {

        Intent intent=new Intent(MainActivity.this,MainActivity2.class);
        intent.putExtra( "videoname",list.get( position ).getVideoName());
        intent.putExtra( "videoPath",list.get( position ).getVideoPath());
        startActivity(intent);

    }
}