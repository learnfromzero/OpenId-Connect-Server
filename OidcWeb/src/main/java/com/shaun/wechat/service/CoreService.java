package com.shaun.wechat.service;

import com.alibaba.fastjson.JSONObject;
import com.shaun.wechat.resp.Image;
import com.shaun.wechat.resp.ImageMessage;
import com.shaun.wechat.req.TextMessage;
import com.shaun.wechat.util.HttpPostUploadUtil;
import com.shaun.wechat.util.MessageUtil;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
* 类名: CoreService </br>
* 描述: 核心服务类 </br>
* 开发人员： souvc </br>
* 创建时间：  2015-9-30 </br>
* 发布版本：V1.0  </br>
 */
public class CoreService {
    private static String tulingAppKey = "5e9fbb5e1e7e4e79a40923c536b02231";
    private static String tulingApiUrl = "http://www.tuling123.com/openapi/api?key=";
    /**
     * 处理微信发来的请求
     * @param request
     * @return xml
     */
    public static String processRequest(HttpServletRequest request) {
        // xml格式的消息数据
        String respXml = null;
        // 默认返回的文本消息内容
        String respContent = "未知的消息类型！";
        try {
            // 调用parseXml方法解析请求消息
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            // 发送方帐号
            String fromUserName = requestMap.get("FromUserName");
            // 开发者微信号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");

            // 回复文本消息
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

            // 文本消息
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                String content = requestMap.get("Content");
                respContent = getTuLingRobetMsg(content);
//                switch (content){
//                    case "1":
//                        respContent = "墨墨还小，不会讲笑话！";
//                        // 设置文本消息的内容
//                        textMessage.setContent(respContent);
//                        // 将文本消息对象转换成xml
//                        respXml = MessageUtil.messageToXml(textMessage);
//                        break;
//                    case "2":
//                        ImageMessage imageMessage = new ImageMessage();
//                        imageMessage.setCreateTime(new Date().getTime());
//                        imageMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_IMAGE);
//                        imageMessage.setFromUserName(toUserName);
//                        imageMessage.setToUserName(fromUserName);
//                        Image image = new Image();
//                        HttpPostUploadUtil uploadUtil = new HttpPostUploadUtil();
//                        Map fileMap = new HashMap();
//                        fileMap.put("userFile","E:\\ywh.jpg");
//                        String resObj = uploadUtil.formUpload(null,fileMap);
//                        String mediaId = JSONObject.parseObject(resObj).getString("media_id");
//                        image.setMediaId("-eIPOK5zZqJQCenmQzlbRJnq0LIZR662kHrSNNjrNt6wEdZszjrUIyX3OwhrYZEG");
//                        imageMessage.setImage(image);
//                        respXml = MessageUtil.messageToXml(imageMessage);
//                        break;
//                    default:
//                        respContent = "你找墨墨有什么事吗？墨墨现在还小，只能陪你" +
//                                "聊5分钟哦，聊完了默默就要睡觉啦！回复【1】，墨墨给你讲笑话," +
//                                "回复【2】，墨墨给你看好看的图片！";
//                        // 设置文本消息的内容
//                        textMessage.setContent(respContent);
//                        // 将文本消息对象转换成xml
//                        respXml = MessageUtil.messageToXml(textMessage);
//                        break;
//                }
            }
            // 图片消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
                ImageMessage imageMessage = new ImageMessage();
                imageMessage.setCreateTime(new Date().getTime());
                imageMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_IMAGE);
                imageMessage.setFromUserName(toUserName);
                imageMessage.setToUserName(fromUserName);
                Image image = new Image();
                HttpPostUploadUtil uploadUtil = new HttpPostUploadUtil();
                Map fileMap = new HashMap();
                fileMap.put("userFile","E:\\ywh.jpg");
                String resObj = uploadUtil.formUpload(null,fileMap);
                String mediaId = JSONObject.parseObject(resObj).getString("media_id");
                image.setMediaId(mediaId);
                imageMessage.setImage(image);
                respXml = MessageUtil.messageToXml(imageMessage);
                respContent = "您发送的是图片消息！";
            }
            // 语音消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
                respContent = "您发送的是语音消息！";
            }
            // 视频消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
                respContent = "您发送的是视频消息！";
            }
            // 视频消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_SHORTVIDEO)) {
                respContent = "您发送的是小视频消息！";
            }
            // 地理位置消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
                respContent = "您发送的是地理位置消息！";
            }
            // 链接消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
                respContent = "您发送的是链接消息！";
            }
            // 事件推送
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                String eventType = requestMap.get("Event");
                // 关注
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                    respContent = "谢谢您的关注！";
                }
                // 取消关注
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
                    // TODO 取消订阅后用户不会再收到公众账号发送的消息，因此不需要回复
                }
                // 扫描带参数二维码
                else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN)) {
                    // TODO 处理扫描带参数二维码事件
                }
                // 上报地理位置
                else if (eventType.equals(MessageUtil.EVENT_TYPE_LOCATION)) {
                    // TODO 处理上报地理位置事件
                }
                // 自定义菜单
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                    // TODO 处理菜单点击事件
                }
            }
            textMessage.setContent(respContent);
            // 将文本消息对象转换成xml
            respXml = MessageUtil.messageToXml(textMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respXml;
    }

    /**
     * 调用图林机器人获取返回结果
     * @param text 户发送的消息
     * @return
     */
    public static String getTuLingRobetMsg(String text){
        String tulingResult = "没有返回结果！";
        try {
            String encodedText  = URLEncoder.encode(text,"utf-8");
            URL url = new URL(tulingApiUrl+tulingAppKey+"&info="+encodedText);
            URLConnection conn = url.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) conn;
            httpURLConnection.setRequestMethod("POST");
            InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream(),"utf-8");
            BufferedReader br = new BufferedReader(inputStreamReader);
            String result;
            StringBuffer sb = new StringBuffer();
            while ((result = br.readLine())!=null){
                sb.append(result);
            }
            String resultMsg = sb.toString();
            JSONObject jsonObject = JSONObject.parseObject(resultMsg);
            if("100000".equals(jsonObject.getString("code"))){
                tulingResult = jsonObject.getString("text");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tulingResult;
    }
}