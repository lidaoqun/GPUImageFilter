package com.lidaoqun.gpuimagefilter.utils;

import android.content.Context;
import android.graphics.Bitmap;


import com.lidaoqun.gpuimagefilter.bean.FilterEffect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;

public class DataHandler {
    public static final double UNIT = Math.PI / 180;
    public static final float DIFF_DEGREE = 30.0f;
    public static final float DIFF_DEGREE_INNER = 35.0f;



    public static List<FilterEffect> filters = new ArrayList<FilterEffect>();

    static {
        filters.clear();
        filters.add(new FilterEffect("原图", GPUImageFilterTools.FilterType.NORMAL, 0));
        filters.add(new FilterEffect("蜜粉", GPUImageFilterTools.FilterType.ACV_MORENJIAQIANG, 0));
        filters.add(new FilterEffect("粉嫩", GPUImageFilterTools.FilterType.RGB_DILATION, 0));
        filters.add(new FilterEffect("自然", GPUImageFilterTools.FilterType.COLOR_BALANCE, 0));
        filters.add(new FilterEffect("爱美", GPUImageFilterTools.FilterType.ACV_AIMEI, 0));
        filters.add(new FilterEffect("淡蓝", GPUImageFilterTools.FilterType.ACV_DANLAN, 0));
        filters.add(new FilterEffect("果冻", GPUImageFilterTools.FilterType.ACV_GAOLENG, 0));
        filters.add(new FilterEffect("可爱", GPUImageFilterTools.FilterType.ACV_KEAI, 0));
        filters.add(new FilterEffect("油画", GPUImageFilterTools.FilterType.KUWAHARA, 0));
        filters.add(new FilterEffect("黑白", GPUImageFilterTools.FilterType.GRAYSCALE, 0));
        filters.add(new FilterEffect("模糊", GPUImageFilterTools.FilterType.GAUSSIAN_BLUR, 0));
    }


    public static List<Bitmap> getSmallPic(Context context, Bitmap bitmap) {
        List<Bitmap> filterBitmap = new ArrayList<>();
        GPUImage gpuImage = new GPUImage(context);
        for (FilterEffect effect : DataHandler.filters) {
            gpuImage.deleteImage();
            GPUImageFilter filter = GPUImageFilterTools.createFilterForType(context, effect.getType());
            gpuImage.setFilter(filter);
            gpuImage.setImage(bitmap);
            filterBitmap.add(gpuImage.getBitmapWithFilterApplied());
        }
        return filterBitmap;
    }

    public static void recycleBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
    }

    public static boolean collectionNotEmpty(Collection<?> collection) {
        return (collection != null && collection.size() >= 0);
    }

}


