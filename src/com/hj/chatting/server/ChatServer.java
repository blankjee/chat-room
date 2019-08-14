package com.hj.chatting.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务器启动入口
 * @author huang
 *
 */
public class ChatServer {
	public ChatServer() {
		try{
			//建立服务器Socket监听 
			ServerSocket serverSocket = new ServerSocket(8888);	//指定端口号：8888
			//打开服务器界面
			ServerFrame serverFrame = new ServerFrame();
			//解决多客户端连接的循环
			while(true){
				//等待连接，阻塞实现，会得到一个客户端连接
				Socket socket = serverSocket.accept();
				//启动一个线程来一直接收消息
				ServerHandler serverHandler = new ServerHandler(socket, serverFrame);
				serverHandler.start();
				
				System.out.println("服务器接收到客户端的连接：" + socket);
			}
			
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		new ChatServer();
	}
}
