package com.aidongxiang;

import okhttp3.*;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class UploadFileTest {

    private static final String UPLOAD_URL = "http://localhost:8080/aidongxiang/UploadFile";
//    @Test
    public static void upload(){

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        MediaType mediaType = MediaType.parse("application/octet-stream");
        String[] paths = {"F:/yungo/apk/dmc_dmc_1.0.0722.apk", "F:/yungo/apk/dmc_dmc_1.0.0723.apk"};
        for (int i = 0; i <paths.length ; i++) {
            String filePath =paths[i];
            File f=new File(filePath);
            if (f!=null) {
                builder.addFormDataPart("files[]", f.getName(), RequestBody.create(mediaType, f));
            }
        }
        //添加其它信息
//        builder.addFormDataPart("time",takePicTime);
//        builder.addFormDataPart("mapX", SharedInfoUtils.getLongitude());
//        builder.addFormDataPart("mapY",SharedInfoUtils.getLatitude());
//        builder.addFormDataPart("name",SharedInfoUtils.getUserName());


        OkHttpClient client = new OkHttpClient();
        MultipartBody requestBody = builder.build();
        //构建请求
        Request request = new Request.Builder()
                .url(UPLOAD_URL)//地址
                .post(requestBody)//添加请求体
                .build();

        System.out.println("开始请求");
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                System.out.println("上传失败:e.getLocalizedMessage() = " + e.getLocalizedMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code() == 200){
                    System.out.println("上传文件成功：response = " + response.body().string());
                } else {
                    System.out.println("上传文件失败：code = " + response.code());
                }
            }
        });
    }


    public static void main(String[] args){
//        requestVideoList();
        upload();
    }
    private static void requestVideoList(){
        OkHttpClient client = new OkHttpClient();
        //构建请求
        Request request = new Request.Builder().url("http://localhost:8080/aidongxiang/VideoList").build();

        System.out.println("开始请求");
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                e.printStackTrace();
                System.out.println("失败:e.getLocalizedMessage() = " + e.getLocalizedMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code() == 200){
                    System.out.println("成功：response = " + response.body().string());
                } else {
                    System.out.println("失败：code = " + response.code());
                }
            }
        });
    }
}
