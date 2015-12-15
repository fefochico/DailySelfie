package com.lab.gabriel.dailyselfie;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.File;


/**
 * Created by fefochico on 01/12/2015.
 */
public class LoadIconTask implements Runnable {
    private Handler handler = new Handler();
    private ImageView imageView;
    private Context context;
    private ProgressBar mProgressBar;
    String dir;
    public LoadIconTask(View v, ProgressBar progressBar, int iv, String path){
        imageView=(ImageView)v.findViewById(iv);
        dir= path;
        mProgressBar= progressBar;

    }
    @Override
    public void run() {
        handler.post(new Runnable(){

            @Override
            public void run() {
                mProgressBar.setVisibility(View.VISIBLE);
            }
        });

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize=15;
        String path= dir;
        path= path.substring(path.indexOf(File.separator)+1, path.length());
        path= path.substring(path.indexOf(File.separator)+1, path.length());
        final Bitmap bm= BitmapFactory.decodeFile(path, options);

        handler.post(new Runnable() {
            @Override
            public void run() {
                imageView.setImageBitmap(bm);
            }
        });

        handler.post(new Runnable(){

            @Override
            public void run() {
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });


    }
}
