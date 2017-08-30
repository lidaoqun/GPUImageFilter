# GPUImageFilter
    基于Android的GPUImage滤镜功能实现，比较适合人脸，小清新
### 实际效果
* 滤镜前
    ![Mou icon](https://github.com/lidaoqun/GPUImageFilter/blob/master/filter1.jpg )  
* 滤镜后
    ![Mou icon](https://github.com/lidaoqun/GPUImageFilter/blob/master/filter2.jpg)  
### 自定义滤镜
    在utils/DataHandler下的static代码块，任意添加或删除滤镜。
``` //name,filterType,0
  filters.add(new FilterEffect("原图", GPUImageFilterTools.FilterType.NORMAL, 0)); 
```
### FilterType 滤镜类型
### 感谢以上作者
* https://github.com/CyberAgent/android-gpuimage
* https://github.com/xiangzhihong/gpuImage


如有任何侵权，请联系作者删除。
