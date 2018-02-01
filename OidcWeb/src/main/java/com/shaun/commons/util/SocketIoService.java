package com.shaun.commons.util;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/18.
 */
@Service("socketIoService")
public class SocketIoService {
    static SocketIOServer server;
    static Map<String,SocketIOClient> clientsMap  = new HashMap<String, SocketIOClient>();

    public void startServer()throws InterruptedException {
        Configuration config = new Configuration();
        //服务器主机ip
        String hostName = "192.168.31.214";
        config.setHostname(hostName);
        //端口
        int socketPort = 9092;
        config.setPort(socketPort);
        config.setMaxFramePayloadLength(1024 * 1024);
        config.setMaxHttpContentLength(1024 * 1024);
        server = new SocketIOServer(config);

        //添加客户端连接事件
        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
                String sa = client.getRemoteAddress().toString();
                String clientIp = sa.substring(1,sa.indexOf(":"));//获取设备ip
                /*System.out.println(clientIp+"-------------------------"+"客户端已连接");*/
                Map params = client.getHandshakeData().getUrlParams();

                //获取客户端连接的uuid参数
                Object object = params.get("uuid");
                String uuid = "";
                if(object != null){
                    uuid = ((List<String>)object).get(0);
                    //将uuid和连接客户端对象进行绑定
                    clientsMap.put(uuid,client);
                }
                //给客户端发送消息
                client.sendEvent("connect_msg",clientIp+"客户端你好，我是服务端，能帮助你吗？");
            }
        });
        server.start();
        Thread.sleep(Integer.MAX_VALUE);
        server.stop();
    }


    /**
     *  给所有连接客户端推送消息
     * @param eventType 推送的事件类型
     * @param message  推送的内容
     */
    public void sendMessageToAllClient(String eventType, String message){
        Collection<SocketIOClient> clients = server.getAllClients();
        for(SocketIOClient client: clients){
            client.sendEvent(eventType,message);
        }
    }

    /**
     * 停止服务
     */
    public void stopServer(){
        if(server != null){
            server.stop();
            server = null;
        }
    }

    public static SocketIOServer getServer() {
        return server;
    }
}
