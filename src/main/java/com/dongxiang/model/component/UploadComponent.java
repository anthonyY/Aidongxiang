package com.dongxiang.model.component;

import com.dongxiang.base.comm.ApiData;
import com.dongxiang.model.comm.FileUploadReqBody;
import com.dongxiang.model.comm.FileUploadRespBody;
import com.dongxiang.model.entity.FileEntity;
import com.dongxiang.model.repository.UploadFileRepository;
import com.dongxiang.utils.MD5Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.MultimediaInfo;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 上传文件小部件 支持秒传
 * @author Anthony
 * @createTime 2017-09-11
 * @version 1.0
 */
@Service
public class UploadComponent {

    private String fileDir = "/uploadfiles/";
    private static Logger logger = LoggerFactory.getLogger(UploadComponent.class);

    @Autowired
    UploadFileRepository uploadFileRepository;


    public String uploadFile(HttpServletRequest request){
        List<FileEntity> files = new ArrayList<>();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        String json = request.getParameter("json");
        FileUploadReqBody fileUploadReqBody= null;
        if(!StringUtils.isEmpty(json)){
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(json);
                String requestQuery = jsonObject.optString("q");
                fileUploadReqBody = gson.fromJson(requestQuery, FileUploadReqBody.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        FileUploadRespBody respBody = new FileUploadRespBody();
//        if(fileUploadReqBody == null){
//            ApiData<FileUploadRespBody> apiData = new ApiData<>(respBody);
//            respBody.desc = "参数错误";
//            respBody.status = 1000;
//            return gson.toJson(apiData);
//        }

        if(fileUploadReqBody != null){
            if(fileUploadReqBody.action==4){
                if(fileUploadReqBody != null  && fileUploadReqBody.getMd5s() != null){
                    List<String> md5s = fileUploadReqBody.getMd5s();
                    files = uploadCheckExsitAction(md5s);
                }
            } else {
                files = upload(request, fileDir);
            }

            if(fileUploadReqBody.action!=4){
                if(files.size() > 0){
                    respBody.desc = "上传成功";
                    respBody.status = 0;
                } else {
                    respBody.desc = "上传失败";
                    respBody.status = 1000;
                }
            } else {
                respBody.desc = "操作成功";
                respBody.status = 0;
            }
        } else {
            files = upload(request, fileDir);
            respBody.desc = "操作成功";
            respBody.status = 0;
        }
        List<Integer> ids = new ArrayList<>();

        for(FileEntity fileEntity : files){
            ids.add(fileEntity.getId());
        }
        respBody.setIds(ids);
        respBody.setFiles(files);
        ApiData<FileUploadRespBody> apiData = new ApiData<>(respBody);
        return gson.toJson(apiData);
    }

    public List<FileEntity> upload(HttpServletRequest request, String fileDir){
        String basePath = request.getSession().getServletContext().getRealPath("/");
        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
                request.getSession().getServletContext());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        fileDir += format.format(new Date())+"/";

        File dir = new File(basePath+fileDir);
        if(!dir.getParentFile().exists()){
            dir.getParentFile().mkdir();
        }
        if(!dir.exists()){
            dir.mkdir();
        }
        //检查form中是否有enctype="multipart/form-data"
        if(multipartResolver.isMultipart(request))
        {
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;

            List<MultipartFile> mutilFiles = multiRequest.getFiles("file[]");

            return saveMultiFiles(mutilFiles, basePath, fileDir);
        }
        return new ArrayList<>();
    }
    /**
     * 用MD5s查询文件是否存在
     * @param md5s MD5列表
     * @return  保存后的文件列表files
     */
    private List<FileEntity> uploadCheckExsitAction(List<String> md5s){
        List<FileEntity> files = new ArrayList<>();
        try {
            List<FileEntity> fileRntitys = uploadFileRepository.findByMd5s(md5s);
            if(fileRntitys != null && fileRntitys.size() >0){
                for(FileEntity fileEntity: fileRntitys){
                    FileEntity uploadFile = new FileEntity();
                    uploadFile.setId(fileEntity.getId());
                    uploadFile.setName(fileEntity.getName());
                    uploadFile.setMd5(fileEntity.getMd5());
                    uploadFile.setPath(fileDir+fileEntity.getName());
                    uploadFile.setTimestamp(fileEntity.getTimestamp());
                    files.add(uploadFile);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return files;
    }

    /**
     * 保存多文件
     * @param mutilFiles 文件列表
     * @param path 保存的文件夹路径
     * @return 保存后的文件列表files
     */
    private List<FileEntity> saveMultiFiles(List<MultipartFile> mutilFiles,String basePath, String path){
        List<FileEntity> files = new ArrayList<>();
        if(mutilFiles != null && mutilFiles.size() > 0){
            for (MultipartFile file : mutilFiles){
                if(file!=null)
                {
                    String filePath =basePath+path+file.getOriginalFilename();
                    File saveFile = new File(filePath);


                    try {
                        file.transferTo(saveFile);
                        FileEntity fileEntity = new FileEntity();
                        fileEntity.setName(file.getOriginalFilename());
                        try {
                            String md5String = MD5Utils.md5(saveFile);
                            fileEntity.setMd5(md5String);
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        }
                        long duration = 0;//音频长度，秒
                        CommonsMultipartFile cf= (CommonsMultipartFile)file;
                        DiskFileItem fi = (DiskFileItem)cf.getFileItem();
                        File source = fi.getStoreLocation();
                        if(MediaFile.isAudioFileType(source.getAbsolutePath()) || MediaFile.isVideoFileType(source.getAbsolutePath())){
                            Encoder encoder = new Encoder();
                            try {
                                MultimediaInfo m = encoder.getInfo(source);
                                long ls = m.getDuration();
                                duration = ls/1000;
                                fileEntity.setDuration(duration);
                            } catch (EncoderException e) {
                                e.printStackTrace();
                            }
                        }

                        fileEntity.setPath(path+fileEntity.getName());
                        fileEntity.setTimestamp(new Date());
                        uploadFileRepository.save(fileEntity);
                        if(!StringUtils.isEmpty(fileEntity.getMd5())){
                            List<FileEntity> byMd5 = uploadFileRepository.findByMd5(fileEntity.getMd5());
                            if(byMd5 != null && byMd5.size() >0){
                                int id = byMd5.get(0).getId();
                                fileEntity.setId(id);
                            }
                        }
                        files.add(fileEntity);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
        return files;
    }

        /*
     * 采用file.Transto 来保存上传的文件
     *//*
    @RequestMapping(value = "admin/fileUpload2", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String  fileUpload2(HttpServletRequest request, @RequestParam("file[]") CommonsMultipartFile[] multipartFiles, ModelMap modelMap) throws IOException {
        String basePath = request.getSession().getServletContext().getRealPath("/");
        List<FileEntity> files = new ArrayList<>();
        FileUploadRespBody respBody = new FileUploadRespBody();
        long  startTime=System.currentTimeMillis();
        String path=basePath+fileDir;
        if(multipartFiles != null){
            files = saveMultiFiles(Arrays.asList(multipartFiles), path);
        }
//        if(files.size() > 0){
            respBody.desc = "上传成功";
            respBody.status = 0;
//        } else {
//            respBody.desc = "上传失败";
//            respBody.status = 1000;
//        }
        long  endTime=System.currentTimeMillis();
        System.out.println("方法二的运行时间："+String.valueOf(endTime-startTime)+"ms");
        Gson gson = new Gson();

        respBody.setFiles(files);
        ApiData apiData = new ApiData<>(respBody);
        return gson.toJson(apiData);
    }
*/
}
