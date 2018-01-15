package com.sam.springbootstudy;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

@ServerEndpoint("/wstest")
@Component
public class SamWebSocket1 {

	
	
	private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static Vector<Session> room = new Vector<Session>();
    
    @OnOpen
    public void onOpen(Session session){
        System.out.println("client new connection:" +session.getUserProperties().toString());
        room.addElement(session);

        
    }
   
    @OnMessage
    public void onMessage(String message,Session session){
        
    	if(message.indexOf("ping") != -1)
    	{
    		System.out.println(message);
    		return;
    	}
        //遍历聊天室中的所有会话
    	int number1 =0;
        for(Session se : room){
        	 
            //发送消息给远程用户
            if(se != session)
            	{
            		se.getAsyncRemote().sendText(message);

            	}
            number1++;
        	
        }
    }
    
    @OnClose
    public void onClose(Session session){
        room.remove(session);
    }
    
    @OnError
    public void onError(Throwable t){
    }
}