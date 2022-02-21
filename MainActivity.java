package com.example.mysaveimage;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.os.Environment.getExternalStorageDirectory;

public class MainActivity extends AppCompatActivity {

    private Bitmap finalEditedImage;
    private RelativeLayout Relative;
    ImageView img1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Relative=findViewById(R.id.RelativeLayout);
        img1=findViewById(R.id.img1);
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDirectoryAndSaveFile(finalEditedImage);
                finalEditedImage = getMainFrameBitmap(Relative);
                Toast.makeText(MainActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void createDirectoryAndSaveFile(Bitmap imageToSave) {
        String ts = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()).toString();
        File direct = new File(getExternalStorageDirectory() + "/ My Qu");

        if (!direct.exists()) {
            File wallpaperDirectory = new File("/sdcard/My Qu/");
            wallpaperDirectory.mkdirs();
        }

        File file = new File("/sdcard/My Qu/", ts+".png");
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private Bitmap getMainFrameBitmap(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(bitmap));
        return bitmap;
    }
}