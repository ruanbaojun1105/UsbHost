package com.hwx.usbhost.usbhost.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hwx.usbhost.usbhost.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class DrawableUtil {

    public static void displayImage(Context context, ImageView imageView, String url) {
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.nocolor)
                .crossFade()
                .into(imageView);
    }
    public static void displayImage(Context context, ImageView imageView, int res) {
        Glide.with(context)
                .load(res)
                .placeholder(R.drawable.nocolor)
                .crossFade()
                .into(imageView);
    }

    /**
     * 把drawable转成BITMAP
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
//		Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
//				.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
//				: Bitmap.Config.RGB_565);
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }

   public static Drawable getImgResourceId(Context context,String name){
       Drawable drawable =null;
       try {
           int resID=0;
           resID = context.getResources().getIdentifier(name, "drawable",context.getApplicationInfo().packageName);
           return context.getResources().getDrawable(resID);
       } catch (Exception e) {
           e.printStackTrace();
           return null;
       }
    }
    public static int getDrawableResourceId(Context context,String name){
        int resID = context.getResources().getIdentifier(name, "drawable", context.getApplicationInfo().packageName);
        //Drawable image = context.getResources().getDrawable(resID);
        return resID;
    }
    public static int getResourceId(Context context,String name,String type){
        int resID = context.getResources().getIdentifier(name, type, context.getApplicationInfo().packageName);
        //Drawable image = context.getResources().getDrawable(resID);
        return resID;
    }
    /**
     * 处理图片
     *
     * @param bm
     *            所要转换的bitmap
     * @ newWidth新的宽
     * @ newHeight新的高
     * @return 指定宽高的bitmap
     */
    public static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix,true);
    }

    public static  Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while ( baos.toByteArray().length / 1024>100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }
    /**
     *
     * TODO<图片圆角处理>
     *
     * @throw
     * @return Bitmap
     * @param srcBitmap
     *            源图片的bitmap
     * @param ret
     *            圆角的度数
     */
    public static Bitmap getRoundImage(Bitmap srcBitmap, float ret) {

        if (null == srcBitmap) {
            LogUtils.e("the srcBitmap is null");
            return null;
        }

        int bitWidth = srcBitmap.getWidth();
        int bitHight = srcBitmap.getHeight();

        BitmapShader bitmapShader = new BitmapShader(srcBitmap,
                Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(bitmapShader);

        RectF rectf = new RectF(0, 0, bitWidth, bitHight);

        Bitmap outBitmap = Bitmap.createBitmap(bitWidth, bitHight,
                Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(outBitmap);
        canvas.drawRoundRect(rectf, ret, ret, paint);
        canvas.save();
        canvas.restore();

        return outBitmap;
    }



}

