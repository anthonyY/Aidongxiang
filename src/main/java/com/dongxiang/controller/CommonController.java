package com.dongxiang.controller;

import com.dongxiang.base.comm.ApiData;
import com.dongxiang.constant.AIIStatus;
import com.dongxiang.model.comm.DefaultRespBody;
import com.dongxiang.model.component.SwitchActionComponent;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * @author Anthony
 * 常用协议分发Controller， 地址相同时，我们用namespace来分发
 * @createTime 2017-10-16
 * @version 1.0
 */
@Controller
public class CommonController {

    Logger logger = LoggerFactory.getLogger(CommonController.class);
    @Autowired
    SwitchActionComponent switchActionComponent;

    @RequestMapping(value = "/api", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String requestBaseApi(String json) {
        Gson gson = new Gson();
        try {
            JSONObject jsonObject = new JSONObject(json);
            String namespace = jsonObject.optString("n");
            String session = jsonObject.optString("s");
            String query = jsonObject.optString("q");

            if(namespace.equals("SwitchAction")){
                return switchActionComponent.dispatchRequest(namespace, session, query);
            }

            DefaultRespBody respBody = new DefaultRespBody();
            respBody.desc = "协议不存在";
            respBody.status = AIIStatus.NO_AGREEMENT;
            return gson.toJson(new ApiData<>(respBody));
        } catch (JSONException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }


        DefaultRespBody respBody = new DefaultRespBody();
        respBody.desc = "请求参数不完整";
        respBody.status = AIIStatus.INCOMPLETE_PARAMETERS;

        return gson.toJson(new ApiData<>(respBody));
    }
}
