package com.fpoly.mob403_lab56;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class GeneralFunc {
    public static Bitmap LoadImageFromLink(String link){
        Bitmap bitmap = null;
        URL mUrl;
        try {
            mUrl = new URL(link);

            bitmap = BitmapFactory.decodeStream(mUrl.openConnection().getInputStream());

        }catch (Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }

    public static void loadImg(String link, ImageView _img_anh1){
        Runnable newRunalbe = new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = GeneralFunc.LoadImageFromLink(link);
                _img_anh1.post(new Runnable() {
                    @Override
                    public void run() {
                        _img_anh1.setImageBitmap(bitmap);
                    }
                });
            }
        };

        Thread newThread = new Thread(newRunalbe);
        newThread.start();
    }

    public static List<Bitmap> LoadImageFuture(List<String> links){
        List<Bitmap> bitmaps = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(links.size());
        for(String url : links){

            try {
                Callable<Bitmap> callable = new Callable() {
                    @Override
                    public Object call() throws Exception {
                        Bitmap bitmap = LoadImageFromLink(url);
                        return bitmap;
                    }
                };
                Future<Bitmap> future = executorService.submit(callable);
                if(future.get() != null){
                    bitmaps.add(future.get());
                }
            }catch (Exception e){

            }
        }

        return bitmaps;
    }
}
