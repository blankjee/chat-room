package com.hj.chatting.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.hj.chatting.constants.Constants;
import com.hj.chatting.entity.ServerInfoBean;

/**
 * 服务器启动入口
 * @author huang
 *
 */
public class ChatServer {
	static Map<String, Socket> userSocketMap = new HashMap<>();
	public ServerFrame serverFrame;
	public ChatServer() {
		try{
			//建立服务器Socket监听 
			ServerSocket serverSocket = new ServerSocket(Constants.SERVER_PORT);	//指定端口号：8888
			//打开服务器界面
			serverFrame = new ServerFrame();
			ServerInfoBean serverInfo = getServerIP();
			loadServerInfo(serverInfo);
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
	/**
	 * 获取服务器的主机名和IP地址
	 * @return 返回服务器IP等信息
	 */
	public ServerInfoBean getServerIP() {
		ServerInfoBean sib = null;
		try {
			InetAddress serverAddress = InetAddress.getLocalHost();
			byte[] ipAddress = serverAddress.getAddress();
			sib = new ServerInfoBean();
			sib.setIp(serverAddress.getHostAddress());
			sib.setHostName(serverAddress.getHostName());
			sib.setPort(Constants.SERVER_PORT);
			
			System.out.println("Server IP is:" + (ipAddress[0] & 0xff) + "."
					+ (ipAddress[1] & 0xff) + "." + (ipAddress[2] & 0xff) + "."
					+ (ipAddress[3] & 0xff));
		} catch (Exception e) {
			System.out.println("###Cound not get Server IP." + e);
		}
		return sib;
	}
	public void loadServerInfo(ServerInfoBean serverInfo) {
		serverFrame.serverInfoPanel.txtIP.setText(serverInfo.getIp());
		serverFrame.serverInfoPanel.txtServerName.setText(serverInfo.getHostName());
		serverFrame.serverInfoPanel.txtLog.setText("服务器成功启动...");
		
	}
}
