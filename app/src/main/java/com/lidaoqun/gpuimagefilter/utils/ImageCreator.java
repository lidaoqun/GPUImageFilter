package com.lidaoqun.gpuimagefilter.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.util.DisplayMetrics;

import com.lidaoqun.gpuimagefilter.FilterApplication;

import java.util.List;

public abstract class ImageCreator {
    public  Context context;

    public ImageCreator(Context context) {
        this.context = context;
    }

    public void loadImage(String url) {
        Bitmap loadedImage = BitmapFactory.decodeFile(url);
        int needWidth = dip2px(80);
        //开启异步执行
        new SmallPicTask().execute(ThumbnailUtils.extractThumbnail(loadedImage, needWidth, needWidth));
    }

    //dp2px
    public static int dip2px(int dip) {
        DisplayMetrics metrics = FilterApplication.sContext.getApplicationContext().getResources().getDisplayMetrics();
        float density = metrics.density;
        return (int) (dip * density + 0.5f);
    }


    private class SmallPicTask extends AsyncTask<Bitmap, Void, List<Bitmap>> {

        @Override
        protected List<Bitmap> doInBackground(Bitmap[] params) {
            return DataHandler.getSmallPic(context, params[0]);
        }

        @Override
        protected void onPostExecute(List<Bitmap> bitmapList) {
            getSmallImage(bitmapList);
        }
    }


    public abstract void getSmallImage(List<Bitmap> smallList);

}
