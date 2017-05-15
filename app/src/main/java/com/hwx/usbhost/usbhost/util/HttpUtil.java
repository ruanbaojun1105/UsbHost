package com.hwx.usbhost.usbhost.util;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;


import com.hwx.usbhost.usbhost.AppConfig;
import com.hwx.usbhost.usbhost.Application;
import com.hwx.usbhost.usbhost.Constants;
import com.hwx.usbhost.usbhost.activity.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/9/12.
 */
public class HttpUtil {
    public static MediaType JSON=MediaType.parse("application/json; charset=utf-8");
    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("text/x-markdown; charset=utf-8");
    /**
     * 根据数据获取请求链接
     * @param api
     * @return
     */
    public static String getHttpRequestUrl(String api){
        return "http://"+ Constants.server_url+"/smallDimple/rest/api/member/"+api;
    }


    //get请求
    public static void getAsynHttp(String url, final InterFaceUtil.OnHttpInterFace todo) {
        //OkHttpClient mOkHttpClient=new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder().url(url);
        //可以省略，默认是GET请求
        //requestBuilder.method("GET",null);
        Request request = requestBuilder.build();
        Call mcall= Application.getmOkHttpClient().newCall(request);
        mcall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (todo!=null)
                    todo.onFail();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (null != response.cacheResponse()) {
                    String str = response.cacheResponse().toString();
                    Log.i("wangshu", "cache---" + str);
                    if (todo != null) {
                        if (response.code() == 200) {
                            todo.onSuccess(str);
                        } else if (response.code() == 500) {
                            todo.onFail();
                        }
                    }
                } else {
                    response.body().string();
                    String str = response.networkResponse().toString();
                    Log.i("wangshu", "network---" + str);
                    if (todo != null) {
                        if (response.code() == 200) {
                            todo.onSuccess(str);
                        } else if (response.code() == 500) {
                            todo.onFail();
                        }
                    }
                }
            }
        });
    }
    /**
     * post请求，json数据为body
     *同步请求
     * @param url
     */
    public static String postExecuteJson(RequestBody body,String url) {
        if (TextUtils.isEmpty(Constants.Deviceid)){
            new Handler(Application.getContext().getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(Application.getContext(),"请先进入后台管理设置设备ID和密码",Toast.LENGTH_SHORT).show();
                }
            });
            return null;
        }

        Request request = new Request.Builder().url(url).post(body).build();
        try {
            Response response = Application.getmOkHttpClient().newCall(request).execute();
            if (response.isSuccessful()) {
                    String str=response.body().string();
                    LogUtils.e(str);
                    JSONObject object=new JSONObject(str);
                    if (object.getInt("status")==200) {
                        return object.get("data").toString();
                    }else if (object.getInt("status")==500) {
                        return null;
                    }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void postJson(RequestBody body,String url, final InterFaceUtil.OnHttpInterFace callback) {

        //RequestBody body = RequestBody.create(JSON, json);
        if (TextUtils.isEmpty(Constants.Deviceid)){
            new Handler(Application.getContext().getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(Application.getContext(),"请先进入后台管理设置设备ID和密码",Toast.LENGTH_SHORT).show();
                }
            });
            if (callback!=null)
                callback.onFail();
            return;
        }

        Request request = new Request.Builder().url(url).post(body).build();
        Application.getmOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null) {
                    callback.onFail();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        try {
                            String str=response.body().string();
                            LogUtils.e(str);
                            JSONObject object=new JSONObject(str);
                            if (object.getInt("status")==200) {
                                if (callback != null)
                                    callback.onSuccess(object.get("data").toString());
                            }else {
                                if (callback != null)
                                    callback.onFail();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            if (callback != null)
                                callback.onFail();
                        }
                    } else {
                        if (callback != null)
                            callback.onFail();
                    }
            }
        });
    }
    //post请求
    public static void testpostAsynHttpforjson(String url,String json) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = Application.mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.i("wangshu", str);
            }
        });
    }
    //上传完文件
    private void postAsynFile() {
        //OkHttpClient mOkHttpClient=new OkHttpClient();
        File file = new File("/sdcard/wangshu.txt");
        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, file))
                .build();
		/*try {
			//同步上传
			mOkHttpClient.newCall(request).execute();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
        Application.mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("wangshu",response.body().string());
            }
        });
    }
    //异步下载文件
    public static void downAsynFile(String url, final File path, final InterFaceUtil.OnHttpInterFace face) {
        if (TextUtils.isEmpty(Constants.Deviceid)){
            new Handler(Application.getContext().getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(Application.getContext(),"请先进入后台管理设置设备ID和密码",Toast.LENGTH_SHORT).show();
                }
            });
            if (face!=null)
                face.onFail();
            return;
        }
        //OkHttpClient mOkHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Application.getmOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (face!=null)
                    face.onFail();
            }

            @Override
            public void onResponse(Call call, Response response) {
                InputStream inputStream = response.body().byteStream();
                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = new FileOutputStream(path);
                    byte[] buffer = new byte[2048];
                    int len = 0;
                    while ((len = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, len);
                    }
                    fileOutputStream.flush();
                } catch (IOException e) {
                    Log.i("wangshu", "IOException");
                    e.printStackTrace();
                }
                if (face!=null)
                    face.onSuccess("");
                Log.d("wangshu", "文件下载成功");
            }
        });
    }

    /**
     * 同步下载
     * @return
     */
    public static boolean downExecuteFile(String url, final File path) {
        if (TextUtils.isEmpty(Constants.Deviceid)){
            new Handler(Application.getContext().getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(Application.getContext(),"请先进入后台管理设置设备ID和密码",Toast.LENGTH_SHORT).show();
                }
            });
            return false;
        }
        //OkHttpClient mOkHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try {
            Response response=Application.getmOkHttpClient().newCall(request).execute();
            if (response!=null){
                InputStream inputStream = response.body().byteStream();
                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = new FileOutputStream(path);
                    byte[] buffer = new byte[2048];
                    int len = 0;
                    while ((len = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, len);
                    }
                    fileOutputStream.flush();
                } catch (IOException e) {
                    Log.i("wangshu", "IOException");
                    e.printStackTrace();
                }
                Log.d("wangshu", "文件下载成功");
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static File getFilePath(String filePath,
                                   String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return file;
    }
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {

        }
    }

    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    //异步上传多文件
    private void sendMultipart(){
        //OkHttpClient mOkHttpClient=new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("title", "wangshu")
                .addFormDataPart("image", "wangshu.jpg",
                        RequestBody.create(MEDIA_TYPE_PNG, new File("/sdcard/wangshu.jpg")))
                .build();

        Request request = new Request.Builder()
                .header("Authorization", "Client-ID " + "...")
                .url("https://api.imgur.com/3/image")
                .post(requestBody)
                .build();

        Application.mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("wangshu", response.body().string());
            }
        });
    }
}
