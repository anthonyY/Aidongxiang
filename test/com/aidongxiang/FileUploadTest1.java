package com.aidongxiang;

import com.aidongxiang.net.AIIResponse;
import com.aidongxiang.net.JsonUtils;
import com.aidongxiang.net.OkRequest;
import com.dongxiang.base.comm.ApiData;
import com.dongxiang.model.comm.FileUploadReqBody;
import com.dongxiang.model.comm.FileUploadRespBody;
import com.dongxiang.model.entity.FileEntity;
import com.dongxiang.utils.MD5Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

public class FileUploadTest1 {

    public static void main(String args[]){

        String url = "http://localhost:8080/aidongxiang/UploadFile";
        String[] filePaths = {"F:/key/BaiseLaw.jks", "D:/我的文档/Pictures/xingkong.png"};
//        String[] filePaths = {"D:/Android/SQLiteExpert/license3.key", "D:/Android/SQLiteExpert/使用说明.txt"};
//        List<String> paths = new ArrayList<>();
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        for (String path : filePaths){
            File file = new File(path);
            params.put(file.getName(), file);
        }
        requestFileUpload(url, params, new AIIResponse<FileUploadRespBody>(){
            @Override
            public void onSuccess(FileUploadRespBody response) {
                super.onSuccess(response);
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

                for (FileEntity file : response.getFiles()){
                    print(JsonUtils.formatJson(gson.toJson(file)));
                }
            }
        });


    }
    private static void requestFileUpload(String url,  LinkedHashMap<String, Object> fileParams, final AIIResponse<FileUploadRespBody> finalAiiResponse){

        final int oldAction = 1;
        FileUploadReqBody reqQuery = new FileUploadReqBody();
        OkRequest okRequest = new OkRequest();

        FileUploadReqBody reqBody = new FileUploadReqBody();
        List<String> md5s = new ArrayList<>();
        Iterator<Map.Entry<String, Object>> it = fileParams.entrySet().iterator();

        while(it.hasNext()) {
            Map.Entry<String, Object> next = it.next();
            String key = next.getKey();
            File value = (File) next.getValue();
            try {
                md5s.add(MD5Utils.md5(value));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        reqBody.setMd5s(md5s);
        reqBody.action = 99;
        ApiData<FileUploadReqBody> apiData = new ApiData<>(reqBody);
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        String json = gson.toJson(apiData);

        HashMap<String, String> stringParams = new HashMap<>();
        stringParams.put("json", json);
        okRequest.post(url, stringParams, new AIIResponse<FileUploadRespBody>(){
            @Override
            public void onSuccess(FileUploadRespBody response) {
                super.onSuccess(response);
                List<FileEntity> files = response.getFiles();
                ArrayList<FileEntity> exitedMd5files = new ArrayList();

                if(files != null && files.size() > 0) {
                    Iterator<FileEntity> it = files.iterator();

                    while(it.hasNext()) {
                        FileEntity md5File = it.next();
                        if(md5File.getId() > 0) {
                            exitedMd5files.add(md5File);
                        }
                    }
                }

                if(exitedMd5files.size() > 0) {
                    Iterator<FileEntity> it = exitedMd5files.iterator();

                    while(it.hasNext()) {
                        FileEntity md5File = it.next();
                        Iterator<String> itMd5 = md5s.iterator();
                        while(itMd5.hasNext()) {
                            String md5 = itMd5.next();
                            if(md5File.getMd5().equalsIgnoreCase(md5)) {
                                fileParams.remove(md5File.getName());
                            }
                        }
                    }
                }

                if(fileParams.size() == 0) {
                    finalAiiResponse.onSuccess(response);
                    print("秒传成功");
                } else {
                    reqQuery.action =oldAction;
                    requestUploadFile(url, reqQuery, exitedMd5files, fileParams, finalAiiResponse);
                }

            }

            @Override
            public void onFailure(String content) {
                super.onFailure(content);
                print(""+content);
            }

        });

    }
    private static <T> void requestUploadFile(String url, FileUploadReqBody query, final List<FileEntity> exitedMd5files, LinkedHashMap<String, Object> params, final AIIResponse<FileUploadRespBody> aiiResponse) {
        query.setMd5s((List)null);
        Gson gson = new Gson();
        ApiData<FileUploadReqBody> apiData = new ApiData<>(query);
        String json = gson.toJson(apiData);
        params.put("json", json);
        OkRequest okRequest = new OkRequest();
        if(exitedMd5files.size() == 0) {
            okRequest.requestFile(url, params, aiiResponse);
        } else {
            okRequest.requestFile(url, params, new AIIResponse<FileUploadRespBody>() {


                public void onSuccess(FileUploadRespBody response) {
                    super.onSuccess(response);
                    List<FileEntity> files = response.getFiles();
                    boolean showLog = true;

                    if(showLog) {
                        StringBuffer ids = new StringBuffer();
                        ids.append("部分文件上传成功:");
                        Iterator<FileEntity> var6 = files.iterator();

                        while(var6.hasNext()) {
                            FileEntity file = var6.next();
                            ids.append(file.getPath()).append(",");
                        }

                        if(ids.toString().endsWith(",")) {
                            ids.deleteCharAt(ids.length() - 1);
                        }
                        print(ids.toString());
                    }

                    List<Integer> ids1 = response.getIds();
                    files.addAll(exitedMd5files);
                    Iterator<FileEntity> exitIt  = exitedMd5files.iterator();

                    while(exitIt.hasNext()) {
                        FileEntity e = exitIt.next();
                        ids1.add(e.getId());
                    }

                    try {
                        aiiResponse.onSuccess(response);
                    } catch (Exception var7) {
                        var7.printStackTrace();
                    }
                }
            });
        }

    }

    public static void print(String text){
        System.out.println(text);
    }
}
