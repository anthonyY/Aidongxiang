package com.dongxiang.controller;

import com.dongxiang.model.component.UploadComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
//@RequestMapping("/aidongxiang")
@Controller
public class UploadFileController {


    @Autowired
    UploadComponent uploadComponent;

    /*
     *采用spring提供的上传文件的方法
     */
    @RequestMapping(value = "/UploadFile", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String  springUpload(HttpServletRequest request) throws IllegalStateException, IOException
    {
        return uploadComponent.uploadFile(request);
    }


    // 上传图片
    @RequestMapping(value = "/jspUploadFile", method = RequestMethod.GET)
    public String uploadFile(ModelMap modelMap) {
        return "upload/uploadFile";
    }


}
