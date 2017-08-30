package com.lidaoqun.gpuimagefilter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.loader.GlideImageLoader;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ImagePicker mImagePicker;
    private final static int CHOOSE_CODE = 998;
    private final static int FILTER_CODE = 6565;
    private ImageView imageView;
    private String newUrl;
    private Bitmap newBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = ((ImageView) findViewById(R.id.imageview));

    }

    /**
     * 选择头像或者拍照
     */
    private void chosenImage(boolean isMultiMode, int requestCode) {
        mImagePicker = ImagePicker.getInstance();
        mImagePicker.setImageLoader(new GlideImageLoader());
        mImagePicker.setShowCamera(true);//显示拍照按钮
        mImagePicker.setSelectLimit(20);//设置图片限制数量
        mImagePicker.setMultiMode(isMultiMode);//禁用多选，开启单选
        mImagePicker.setCrop(false);//允许裁剪
        mImagePicker.setStyle(CropImageView.Style.RECTANGLE);//裁剪模式为正方形
        mImagePicker.setFocusWidth(1000);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        mImagePicker.setFocusHeight(1000);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        Intent intent = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent, requestCode);
    }

    public void ChooseClick(View view) {
        switch (view.getId()){
            case R.id.choose_image:
                chosenImage(false,CHOOSE_CODE);
                 break;
            case R.id.filter_image:
                if (newUrl!=null && !newUrl.isEmpty()){
                    Intent intent = new Intent(MainActivity.this,FilterActivity.class);
                    intent.putExtra("newImage",newUrl);
                    startActivityForResult(intent,FILTER_CODE);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_CODE && data!=null){  //选择图片
            ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            if (images.size()>0){
                newUrl = images.get(0).path;
                newBitmap = BitmapFactory.decodeFile(newUrl);
                imageView.setImageBitmap(newBitmap);
            }
        }else if (requestCode == FILTER_CODE && resultCode == 15){  //美化后结果
            newUrl = data.getExtras().getString("image");
            newBitmap = BitmapFactory.decodeFile(newUrl);
            imageView.setImageBitmap(newBitmap);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (newBitmap!=null && !newBitmap.isRecycled()){
            newBitmap.recycle();
            newBitmap = null;
        }
    }
}
