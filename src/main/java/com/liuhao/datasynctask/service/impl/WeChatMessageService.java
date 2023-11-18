package com.liuhao.datasynctask.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.*;
import com.alibaba.fastjson.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
 
import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.util.*;
 
/**
 * 企业微信消息推送实现类
 */
@Data
@Component
@Slf4j
public class WeChatMessageService {
    /**
     * 企业Id
     */
    @Value("${wechat.corpid}")
    private String corpid;
    /**
     * 应用私钥
     */
    @Value("${wechat.corpsecret}")
    private String corpsecret;
    /**
     * 获取访问权限码（access_token）URL  GET请求
     */
    @Value("${wechat.accessTokenUrl}")
    private String accessTokenUrl;
    /**
     * 发送消息URL POST请求
     */
    @Value("${wechat.sendMessageUrl}")
    private String sendMessageUrl;
    /**
     * 获取企业微信用户userid POST请求
     */
    @Value("${wechat.getUseridUrl}")
    private String getUseridUrl;
    /**
     * token
     */
    private String token;
    /**
     * 获取部门列表地址 get请求
     */
    @Value("${wechat.getDepartmentListUrl}")
    private String getDepartmentListUrl;
    /**
     * 获取部门成员详情地址 get请求
     */
    @Value("${wechat.getUserListUrl}")
    private String getUserListUrl;
    //应用ID
    @Value("${wechat.agentId}")
    private String agentId;
 
    public String getAccessToken() {
        //获取token
        String url = accessTokenUrl.replaceAll("CORPID", corpid).replaceAll("CORPSECRET", corpsecret);
 
        String result = HttpUtil.get(url);
        JSONObject jsonObject = JSONObject.parseObject(result);
        return jsonObject.getString("access_token");
    }
 
    /**
     * 根据电话号码获取userId
     *
     * @param token
     * @param employeePhone
     * @return
     */
    public String getUserId(String token, String employeePhone) {
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        sb.append("\"mobile\":" + "\"" + employeePhone + "\"");
        sb.append("}");
        String json = sb.toString();
        String result = "";
        String url = getUseridUrl + token;
        try {
            HttpsURLConnection http = getPostHttp(url, "");
            OutputStream os = http.getOutputStream();
            os.write(json.getBytes("UTF-8"));
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            result = new String(jsonBytes, "UTF-8");
            os.flush();
            os.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        JSONObject jsonObject = JSONObject.parseObject(result);
        return jsonObject.getString("userid");
    }
 
    public static HttpsURLConnection getPostHttp(String action, String token) throws Exception {
        URL url = null;
        HttpsURLConnection http = null;
        try {
            url = new URL(action);
            http = (HttpsURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            if (StrUtil.isNotBlank(token)) {
                http.setRequestProperty("token", token);
            }
            http.setDoOutput(true);
            http.setDoInput(true);
            //连接超时30秒
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
            //读取超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000");
            http.connect();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return http;
    }
 
    /**
     * 发送微信消息
     *
     * @param token
     * @param json
     * @return
     */
    public String sendMessage(String token, String json) {
        //请求链接
        String action = sendMessageUrl + token;
        try {
            return HttpRequest.post(action)
                    .body(json)
                    .execute().body();
        } catch (Exception e) {
            log.error(e.getMessage());
            return "";
        }
    }
 
    /**
     * 获取微信所有用户
     *
     * @return
     */
    public List<JSONObject> getWechatAllUser() {
        // 获取token
        String token = getAccessToken();
        String template = getUserListUrl + "?access_token={}&department_id=1&fetch_child=1";
        String str = StrUtil.format(template, token);
        String userResult = HttpUtil.get(str);
        JSONObject jsonUser = JSON.parseObject(userResult);
 
        // 返回结果
        List<JSONObject> resultJsonList = new ArrayList<>();
        if ("0".equals(jsonUser.getString("errcode"))) {
            String userListStr = jsonUser.getString("userlist");
            JSONArray userList = JSON.parseArray(userListStr);
            resultJsonList = JSONObject.parseArray(userList.toJSONString(userList.size()), JSONObject.class);
            return resultJsonList;
        } else {
            return resultJsonList;
        }
    }


}