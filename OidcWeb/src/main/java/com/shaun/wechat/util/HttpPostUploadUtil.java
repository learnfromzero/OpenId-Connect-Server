package com.shaun.wechat.util;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

/**
 * @author huangteng
 * @version 1.0.0
 * @description 多媒体上传
 * @time 15:33 2017/11/24
 * @modified by:
 * @modified time:
 */
public class HttpPostUploadUtil {

    public String urlStr;

    /**
     * 构造初始化
     */
    public HttpPostUploadUtil(){
        urlStr = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=Hq9ZJNprnfHYeDsFx1lxlYxkAEqJHN_Jhf6reSaLp3WdbhAzN-lMhtGBklBUZ-LvANXbtc_jOckw6Mgfaht0Oxnv44ivyCy58TY473eOSAmS73Fhninc0qGnBrO1jZqgQFPcABADSC&type=image";
    }

    /**
     * 图片上传
     * @param textMap
     * @param fileMap
     * @return
     */
    @SuppressWarnings("rawtypes")
    public String formUpload(Map<String, String> textMap, Map<String, String> fileMap){
        String res = "";
        HttpURLConnection conn = null;
        String BOUNDARY = "---------------------------123821742118716";
        try{
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            conn.setRequestProperty("Content-Type","multipart/form-data; boundary=" + BOUNDARY);
            OutputStream os = new DataOutputStream(conn.getOutputStream());

            // 文本
            if(textMap!=null){
                StringBuffer sb = new StringBuffer();
                Iterator<?> iter = textMap.entrySet().iterator();
                while(iter.hasNext()){
                    Map.Entry entry = (Map.Entry) iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    sb.append("\r\n").append("--").append(BOUNDARY).append(
                            "\r\n");
                    sb.append("Content-Disposition: form-data; name=\""
                            + inputName + "\"\r\n\r\n");
                    sb.append(inputValue);
                }
                os.write(sb.toString().getBytes());
            }

            // 文件
            if(fileMap!=null){
                Iterator<?> iter = fileMap.entrySet().iterator();
                while (iter.hasNext()){
                    Map.Entry entry = (Map.Entry) iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    File file = new File(inputValue);
                    String filename = file.getName();
                    String contentType = new MimetypesFileTypeMap().getContentType(file);
                    if (filename.endsWith(".jpg")) {
                        contentType = "image/jpg";
                    }
                    if (contentType == null || contentType.equals("")) {
                        contentType = "application/octet-stream";
                    }

                    StringBuffer strBuf = new StringBuffer();
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\""
                            + inputName + "\"; filename=\"" + filename
                            + "\"\r\n");
                    strBuf.append("Content-Type:" + contentType + "\r\n\r\n");

                    os.write(strBuf.toString().getBytes());

                    DataInputStream in = new DataInputStream(new FileInputStream(file));
                    int bytes = 0;
                    byte[] bufferOut = new byte[1024];
                    while ((bytes = in.read(bufferOut)) != -1) {
                        os.write(bufferOut, 0, bytes);
                    }
                    in.close();
                }
            }
            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            os.write(endData);
            os.flush();
            os.close();

            // 读取返回数据
            StringBuffer strBuf = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                strBuf.append(line).append("\n");
            }
            res = strBuf.toString();
            reader.close();
            reader = null;

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
        return res;
    }
}