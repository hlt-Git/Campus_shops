package com.controller;

import com.alibaba.fastjson.JSONObject;
import com.entity.UserInfo;
import com.entity.chat.ChatMsg;
import com.service.ChatmsgService;
import com.service.NoticesService;
import com.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 * @ServerEndpoint 可以把当前类变成websocket服务类
 */
@Controller
@ServerEndpoint(value = "/websocket/{userno}")
public class ChatWebSocket {
    // 这里使用静态，让 service 属于类
    private static ChatmsgService chatMsgService;
    private static UserInfoService mineService;
    // 注入的时候，给类的 service 注入
    @Autowired
    public void setChatService(ChatmsgService chatService,UserInfoService mineService) {
       ChatWebSocket.chatMsgService = chatService;
       ChatWebSocket.mineService=mineService;
    }
    //注入通知类
    @Autowired
    private NoticesService noticesService;

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static ConcurrentHashMap<String, ChatWebSocket> webSocketSet = new ConcurrentHashMap<String, ChatWebSocket>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session WebSocketsession;
    //当前发消息的人员编号
    private String userno = "";


    /**
     * 连接建立成功调用的方法
     *
     * session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(@PathParam(value = "userno") String param, Session WebSocketsession, EndpointConfig config) {
        userno = param;//接收到发送消息的人员编号
        this.WebSocketsession = WebSocketsession;
        webSocketSet.put(param, this);//加入map中
        addOnlineCount();     //在线数加1 System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
        mineService.UpdateUserInfo(new UserInfo().setUserid(userno).setStatus("online")); //更新用户的状态为在线
    }


    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (!userno.equals("")) {
            webSocketSet.remove(userno); //从set中删除
            subOnlineCount();     //在线数减1  System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
            mineService.UpdateUserInfo(new UserInfo().setUserid(userno).setStatus("offline")); //更新用户的状态为离线
        }
    }


    /**
     //给指定的人发消息
     * @param session 可选的参数
     */
    @SuppressWarnings("unused")
	@OnMessage
    public void onMessage(String mine, Session session) {
        JSONObject jsonObject = JSONObject.parseObject(mine);
        String msgtype=jsonObject.getString("type");
        if(msgtype.equals("friend")){
            sendToUser(jsonObject.toJavaObject(UserInfo.class));
        }else if(msgtype.equals("group")){
            sendAll(jsonObject.toJavaObject(UserInfo.class));
        }
    }


    /**
     * 给指定的人发送消息
     *
     * @param message
     */
    public void sendToUser(UserInfo message) {
        String reviceUserid = message.getToid();
        chatMsgService.insertChatmsg(new ChatMsg().setMsgtype("0").setReciveuserid(reviceUserid).setSenduserid(message.getId()).setContent(message.getContent()));
        try {
            if (webSocketSet.get(reviceUserid) != null) {
                webSocketSet.get(reviceUserid).sendMessage(JSONObject.toJSONString(message));//转成json形式发送出去
            }else{
                webSocketSet.get(userno).sendMessage("0");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 给群组中的所有人发消息
     *
     * @param message
     */
    private void sendAll(UserInfo message) {

    }


    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }


    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     *
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.WebSocketsession.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }


    public static synchronized int getOnlineCount() {
        return onlineCount;
    }


    public static synchronized void addOnlineCount() {
        ChatWebSocket.onlineCount++;
    }


    public static synchronized void subOnlineCount() {
        ChatWebSocket.onlineCount--;
    }

}

