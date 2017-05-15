/*
 * Copyright 2009 Cedric Priscal
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

package com.hwx.usbhost.usbhost;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;


import com.hwx.usbhost.usbhost.bluetooth.ScaleActivity;
import com.hwx.usbhost.usbhost.db.Accessories;
import com.hwx.usbhost.usbhost.db.Glass;
import com.hwx.usbhost.usbhost.db.Ornament;
import com.hwx.usbhost.usbhost.db.manager.AccessoriesMoreDaoManager;
import com.hwx.usbhost.usbhost.db.manager.CocktailManager;
import com.hwx.usbhost.usbhost.db.manager.GlassDaoManager;
import com.hwx.usbhost.usbhost.db.manager.GreenDaoManager;
import com.hwx.usbhost.usbhost.db.manager.OrnamentMoreDaoManager;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

public class Application extends android.app.Application implements
        Thread.UncaughtExceptionHandler{
    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        Intent intent = new Intent(this, ScaleActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    public static Context mContext;
    public static OkHttpClient mOkHttpClient;
    private static Application _instance;
    public static Application getInstance() {
        return _instance;
    }

    public static String[] glassArrs=new String[]{
            "红粉佳人","玛格丽特","旁车","日出","长岛冰茶",
            "自由古巴","螺丝刀","新加坡司令","得其利","黑俄罗斯",
            "蓝色夏威夷","梦想","明天","蓝色玛格丽特","草裙",
            "环游世界","无野","墨西哥硬汉","白俄罗斯","尼克佳人",
            "莫吉托","两者之间","金汤力","夏威夷酷饮","百加得鸡尾酒",
            "薄荷滨治","神魂颠倒","晴空","将军","奇迹",
            "灰姑娘","初恋","香橙杂饮","果汁滨治","柠檬什饮"};

    public static Context getContext() {
        return mContext;
    }
    public static OkHttpClient getmOkHttpClient() {
        if (mOkHttpClient==null){
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS);
            mOkHttpClient=builder.connectTimeout(60, TimeUnit.SECONDS).build();
        }
        return mOkHttpClient;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
        initOkHttpClient();
        GreenDaoManager.getInstance();
        AppConfig.getInstance();
        //初始一些数据test
        if (!AppConfig.getInstance().getBoolean("hasAddTest",false)){
            for (int i = 0; i <2 ; i++) {
//                CocktailManager.getCocktailManager().addCocktail(
//                        (i%2==0?"墨西哥日出":"自由古巴")+i,
//                        i%2==0?"http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1211/07/c1/15434519_1352274852894.jpg":"http://5.26923.com/download/pic/000/327/85b37096bd973b96bfd432ed933b554c.jpg",
//                        i%2==0?"冰块":"苏打水",
//                        String.valueOf(i),
//                        i%2==0?"http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1211/07/c1/15434519_1352274852894.jpg":"http://5.26923.com/download/pic/000/327/85b37096bd973b96bfd432ed933b554c.jpg",
//                        i%2==0?"柠檬":"红车匣子");
                OrnamentMoreDaoManager.getOrnamentMoreDaoManager().addOrnament(new Ornament(null,i%2==0?"lemon":"Car box"));
                AccessoriesMoreDaoManager.getAccessoriesMoreDaoManager().addAccessories(new Accessories(null,i%2==0?"Ice block":" Soda water tonic"));
            }
            for (int i = 0; i < 16; i++) {
                GlassDaoManager.getGlassManager().add(new Glass(null,String.valueOf(i+1),i+1));
            }
            AppConfig.getInstance().putBoolean("hasAddTest",true);
        }
    }

    private void initOkHttpClient() {
        mContext = getApplicationContext();
        File sdcache = getExternalCacheDir();
        int cacheSize = 10 * 1024 * 1024;
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .cache(new Cache(sdcache.getAbsoluteFile(), cacheSize));
        mOkHttpClient = builder.build();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px( float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    public static void sendLocalBroadCast(String action) {
        sendLocalBroadCast(action, null);
    }

    public static void sendLocalBroadCast(String action, Bundle bundle) {
        Intent bi = new Intent();
        bi.setAction(action);
        if (bundle != null)
            bi.putExtras(bundle);
        LocalBroadcastManager.getInstance(getContext())
                .sendBroadcast(bi);
    }

}
