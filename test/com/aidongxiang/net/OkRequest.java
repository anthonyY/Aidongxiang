package com.aidongxiang.net;



import okhttp3.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class OkRequest {

    private OkHttpClient client;
    private List<Cookie> cookies;

    public OkRequest() {
        client = new OkHttpClient();
    }


    /**
     * 请求上传图片， 可以包含图片和文字参数
     * @param url api路径
     * @param datas 文件和文字参数Map
     * @param aiiResponse 回调
     */
    public void requestFile(String url, Map<String, Object> datas, AIIResponse aiiResponse) {

        request(url, combinationFileParems(url, datas), aiiResponse);
    }
    /**
     * 请求协议
     * @param url api路径
     * @param datas 参数Map
     * @param aiiResponse 回调
     */
    public void get(String url, Map<String, String> datas, AIIResponse aiiResponse) {

        request(combinationGetStringParems(url, datas), null, aiiResponse);
    }
    public void post(String url, Map<String, String> datas, AIIResponse aiiResponse) {

        request(url, combinationStringParems(datas), aiiResponse);
    }

    /**
     * OkCallback 里的回调是子线程的，所以new 了一个handler, 而handler需要在主线程new,这里却不能保证是主线程，所以判断下，
     * 如果是子线程，就回到主线程来调用。
     * @param url
     * @param requestBody
     * @param aiiResponse
     */

    private void request(String url, RequestBody requestBody, final AIIResponse aiiResponse) {
        Request.Builder builder = new Request.Builder().url(url);
        
        if(requestBody != null){
            builder.post(requestBody);
        }

        final Call call = client.newBuilder().cookieJar(new CookieJar() {
            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                OkRequest.this.cookies = cookies;
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                return cookies == null? new ArrayList<Cookie>() : cookies;
            }
        }).build().newCall(builder.build());

        call.enqueue(new OkCallback(aiiResponse));


    }

    private String combinationGetStringParems(String url, Map<String, String> datas) {

        FormBody.Builder builder = new FormBody.Builder();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(url).append("?");
        if (datas != null && !datas.isEmpty()) {
            for (Entry<String, String> entry : datas.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
                stringBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            if(stringBuilder.toString().endsWith("&")){
                stringBuilder.deleteCharAt(stringBuilder.length()-1);
            }
        }
        if(stringBuilder.toString().endsWith("?")){
            stringBuilder.deleteCharAt(stringBuilder.length()-1);
        }
        print(stringBuilder.toString());

        return stringBuilder.toString();
    }

    private RequestBody combinationStringParems(Map<String, String> datas) {

        FormBody.Builder builder = new FormBody.Builder();
        StringBuilder stringBuilder = new StringBuilder();
        if (datas != null && !datas.isEmpty()) {
            for (Entry<String, String> entry : datas.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
                stringBuilder.append(entry.getKey()).append(":").append(entry.getValue()).append("  \n");
            }
        }
        RequestBody formBody = builder.build();
        print(stringBuilder.toString());

        return formBody;
    }


    public static <T> MultipartBody combinationFileParems(String url, Map<String, Object> datas) {
        StringBuilder sb = new StringBuilder();
        sb.append(url).append("?");
        MultipartBody.Builder multipart = new MultipartBody.Builder();
        multipart.setType(MultipartBody.FORM);
        if (datas != null && !datas.isEmpty()) {
            Iterator<Entry<String, Object>> it = datas.entrySet().iterator();
            while (it.hasNext()) {
                Entry<String, Object> entry = (Entry<String, Object>) it.next();
                MediaType mediaType = MediaType.parse("application/octet-stream");
                if (entry.getValue().getClass().equals(File.class)) {
                    File value = (File) entry.getValue();

                    RequestBody fileBody = RequestBody.create(mediaType, value);
                    // multipart.addPart(getFileHeader(value.getName()),
                    // fileBody);
                    multipart.addFormDataPart("file[]", value.getName(), fileBody);
                    sb.append(entry.getKey()).append(":").append(value.length()).append("\n");
                } else if (InputStream.class.isAssignableFrom(entry.getValue()
                        .getClass())) {
                    InputStream value = (InputStream) entry.getValue();
                    try {
                        byte[] data = toByteArray(value);
                        RequestBody fileBody = RequestBody.create(mediaType, data);
                        // multipart.addPart(getFileHeader(entry.getKey()),
                        // fileBody);
                        multipart.addFormDataPart("file[]", entry.getKey(), fileBody);
                        sb.append(entry.getKey()).append(":").append(data.length).append("\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else if (entry.getValue().getClass().equals(byte[].class)) {
                    byte[] value = (byte[]) entry.getValue();
                    RequestBody fileBody = RequestBody.create(mediaType, value);
                    // multipart.addPart(getFileHeader(entry.getKey()),
                    // fileBody);
                    multipart.addFormDataPart("file[]", entry.getKey(), fileBody);
                    sb.append(entry.getKey()).append(":").append(value.length).append("\n");
                } else if (entry.getValue().getClass().equals(String.class)) {
                    String value = (String) entry.getValue();
                    if (value != null) {
                        multipart.addFormDataPart(entry.getKey(), value);
                    }
                    sb.append(entry.getKey()).append(":").append(value).append("\n");
                }

            }
        }
//        if (sb.toString().endsWith("&")) {
//            sb.deleteCharAt(sb.length() - 1);
//        }
//
//        if (!sb.toString().endsWith("?")) {
//            print(sb.toString());
//        }
        return multipart.build();
    }


    /**
     * 获得指定文件的byte数组
     * @param filePath 文件路径
     * @return byte数组
     */
    public static byte[] getBytes(String filePath){
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }
    /**
     * inputStream转byte[]
     * @param input
     * @return
     * @throws IOException
     */
    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }
    public static void print(String text){
        System.out.println(text);
    }

}
