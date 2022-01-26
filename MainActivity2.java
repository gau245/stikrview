package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

public class MainActivity2 extends AppCompatActivity {

    TextView videoNameTV,videoTimeTV;
    ImageButton backid,forwarid,playpuseid;
    SeekBar seekBar;
    VideoView videoView;
    RelativeLayout controlsRl,videoRl;
    boolean isOpen=true;
    String name,path;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main2 );
        name=getIntent().getStringExtra( "videoname" );
        path=getIntent().getStringExtra( "videoPath" );
        videoNameTV=findViewById( R.id.texttital );
        videoTimeTV=findViewById( R.id.texttime );
        backid=findViewById( R.id.idIBback );
        forwarid=findViewById( R.id.idIBforward );
        playpuseid=findViewById( R.id.idIBplay );
        seekBar=findViewById( R.id.seekbar );
        videoView=findViewById( R.id.idvideoview );
        controlsRl=findViewById( R.id.idRlControls );
        videoRl=findViewById( R.id.idRLvideo );


        videoView.setVideoURI( Uri.parse( path ) );
        videoView.setOnPreparedListener( new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                seekBar.setMax( videoView.getDuration() );
                videoView.start();
            }
        } );
        videoNameTV.setText( name );
        backid.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.seekTo( videoView.getDuration()-10000 );

            }
        } );
        forwarid.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.seekTo( videoView.getDuration()+10000 );
            }
        } );
        playpuseid.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (videoView.isPlaying()){
                    videoView.pause();
                    playpuseid.setImageDrawable(getResources().getDrawable( R.drawable.ic_play ) );
                }else {

                    videoView.start();
                    playpuseid.setImageDrawable( getResources().getDrawable( R.drawable.ic_pause ) );
                }

            }
        } );
        videoRl.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOpen){
                    hideControls();
                }else {
                    showControls();
                    isOpen=true;
                }
            }
        } );
        setHandler();
        initializeSeekBar();
    }

    private void showControls() {
        controlsRl.setVisibility( View.VISIBLE );
        final Window window=this.getWindow();
        if(window==null){
            return;
        }
        window.clearFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN );
        window.addFlags( WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN );
        View decorView=window.getDecorView();
        if (decorView!=null){
            int uioption =decorView.getSystemUiVisibility();
            if(Build.VERSION.SDK_INT>=14){
                uioption&= ~View.SYSTEM_UI_FLAG_LOW_PROFILE;
            }if(Build.VERSION.SDK_INT>=16){
                uioption&= ~View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            }if(Build.VERSION.SDK_INT>=19){
                uioption&= ~View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            }
            decorView.setSystemUiVisibility( uioption );
        }
    }

    private void hideControls() {
        controlsRl.setVisibility( View.GONE );
        final Window window=this.getWindow();
        if(window==null){
            return;
        }
        window.clearFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN );
        window.addFlags( WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN );
        View decorView=window.getDecorView();
        if (decorView!=null){
            int uioption =decorView.getSystemUiVisibility();
            if(Build.VERSION.SDK_INT>=14){
                uioption |=View.SYSTEM_UI_FLAG_LOW_PROFILE;
            }if(Build.VERSION.SDK_INT>=16){
                uioption|= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            }if(Build.VERSION.SDK_INT>=19){
                uioption|= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            }
            decorView.setSystemUiVisibility( uioption );
        }

    }
    private void initializeSeekBar(){

        seekBar.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(seekBar.getId()==R.id.seekbar){
                    if(b){
                        videoView.seekTo( i );
                        videoView.start();
                        int cupos=videoView.getCurrentPosition();
                        videoTimeTV.setText( ""+convertTime( videoView.getDuration()-cupos ) );
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        } );
    }
    private void setHandler(){
        Handler handler=new Handler();
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                if (videoView.getDuration()>0){

                    int curpos=videoView.getCurrentPosition();
                    seekBar.setProgress( curpos );
                    videoTimeTV.setText( ""+convertTime( videoView.getDuration()-curpos ) );
                }
                handler.postDelayed( this,0 );

            }
        };
        handler.postDelayed( runnable,5000 );
    }
    private String convertTime(int ms){
        String time;
        int x,Seconds,minutes,hours;
        x=ms/1000;
        Seconds=x%60;
        x /=60;
        minutes= x%60;
        x /=60;
        hours= x%24;
        if (hours!=0){
            time=String.format( "%02d",hours )+":"+String.format( "%02d",minutes )+":"+String.format( "%02d",Seconds );

        }else {
            time=String.format( "%02d",minutes )+":"+String.format( "%02d",Seconds );
        }
        return time;
    }
}