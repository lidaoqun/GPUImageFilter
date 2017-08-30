package com.lidaoqun.gpuimagefilter;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import com.lidaoqun.gpuimagefilter.adapter.FilterAdapter;
import com.lidaoqun.gpuimagefilter.bean.FilterEffect;
import com.lidaoqun.gpuimagefilter.utils.DataHandler;
import com.lidaoqun.gpuimagefilter.utils.GPUImageFilterTools;
import com.lidaoqun.gpuimagefilter.utils.ImageCreator;

import java.util.List;

import it.sephiroth.android.library.widget.AdapterView;
import it.sephiroth.android.library.widget.HListView;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageView;

public class FilterActivity extends AppCompatActivity {

    private String url;
    private String fileUrl;
    private FilterAdapter adapter;
    private Bitmap bitmap;
    private GPUImageView gpuimage;
    private HListView listFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        gpuimage = ((GPUImageView) findViewById(R.id.gpuimage));
        listFilter = ((HListView) findViewById(R.id.list_filter));

        initData();
    }

    protected void initData() {
        url = getIntent().getStringExtra("newImage");
        //小图片路径
        if (url.startsWith("content")){
            fileUrl = url;
        }else{
            fileUrl = "file://" +url;
        }
        //找到文件真实位置
        bitmap = BitmapFactory.decodeFile(url);
        float width = bitmap.getWidth();
        float height = bitmap.getHeight();
        float ratio = width / height;
        //设置图片比例
        gpuimage.setRatio(ratio);
        gpuimage.setImage(bitmap);
        loadSmallImage();
    }

    private String findImagePath(Uri uri) {
        String url = null;
        if ("content".equals(uri.getScheme())) {
            // 4.2.2以后
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = this.getContentResolver().query(uri, proj, null, null, null);
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                url = cursor.getString(columnIndex);
            }
            cursor.close();
        }
        if (url==null){
            return uri.toString();
        }
        return url;
    }

    private void loadSmallImage() {
        ImageCreator creator = new ImageCreator(getApplicationContext()) {
            @Override
            public void getSmallImage(List<Bitmap> smallList) {
                initFilter(smallList);
            }
        };
        creator.loadImage(url);
    }

    public void ButtonClick(View view) {
        switch (view.getId()){
            case R.id.filter_close:
                finish();
                break;
            case R.id.filter_completed:
                String fileName = System.currentTimeMillis() + ".jpg";

                gpuimage.saveToPictures("GpuImageFilter", fileName, new GPUImageView.OnPictureSavedListener() {
                    @Override
                    public void onPictureSaved(Uri uri) {
                        //获取到美化完毕的图片地址,并传回上一层
                        Intent intent = new Intent();
                        intent.putExtra("image",findImagePath(uri));
                        setResult(15, intent);
                        //隐藏对话框
                        finish();
                    }
                });
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
      gpuimage.recycleSurface();
        if (bitmap!=null&&!bitmap.isRecycled()){
            bitmap.recycle();
            bitmap = null;
        }
    }

    private void initFilter(List<Bitmap> bitmapList) {
        final List<FilterEffect> filters = DataHandler.filters;
        adapter = new FilterAdapter(this, bitmapList);
        adapter.setSelected(0);
        adapter.setSelectFilter(0);
       listFilter.setAdapter(adapter);

        listFilter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (adapter.getSelectFilter() != arg2) {
                    adapter.setSelectFilter(arg2);
                    adapter.setSelected(arg2);
                    FilterEffect effect = filters.get(arg2);
                    GPUImageFilter filter = GPUImageFilterTools.createFilterForType(getApplicationContext(), effect.getType());
                    gpuimage.setFilter(filter);
                }
            }
        });
    }
}
