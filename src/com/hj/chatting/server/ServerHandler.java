package com.hj.chatting.server;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.JList;
import javax.swing.JTextPane;

import com.hj.chatting.entity.ChatStatus;
import com.hj.chatting.entity.TransferInfo;
import com.hj.chatting.io.IOStream;


/**
 * 服务器开启一个线程，来处理一直读消息
 * 
 * @author huang
 *
 */
public class ServerHandler extends Thread {

	Socket socket;
	ServerFrame serverFrame;

	public ServerHandler(Socket socket, ServerFrame serverFrame) {
		this.socket = socket;
		this.serverFrame = serverFrame;
	}
	
	static List<String> onlineUsers = new ArrayList<>();
	static List<Socket> onlineSockets = new ArrayList<>();

	@Override
	public void run() {

		// 默认重复拿
		while (true) {
			// 模拟一直拿消息
			Object object = IOStream.readMessage(socket);
			System.out.println("服务端接收到了：" + object);
			if (object instanceof TransferInfo) {
				TransferInfo transferInfo = (TransferInfo) object;
				if (transferInfo.getStatusEnum() == ChatStatus.LOGIN) {
					//登录消息类型
					loginHandler(transferInfo);
				}else if (transferInfo.getStatusEnum() == ChatStatus.CHAT) {
					//聊天信息类型
					chatHandler(transferInfo);
				}else if (transferInfo.getStatusEnum() == ChatStatus.SHAKE) {
					//抖动消息类型
					shake(transferInfo);
				}else if (transferInfo.getStatusEnum() == ChatStatus.EXIT) {
					logout(transferInfo);
					try {
						Thread.sleep(1000);
						socket.close();	//关闭socket连接
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					this.interrupt();//关闭当前线程
					break;
				}
			}
			
		}
	}
	
	/**
	 * 进行客户端登录处理
	 * @param transferInfo
	 */
	public void loginHandler(TransferInfo transferInfo) {
		boolean flag = checkUserLogin(transferInfo);
		transferInfo.setLoginSuccess(false);
		if (flag) {
			//返回登录成功给客户端（登录消息）
			transferInfo.setLoginSuccess(true);
			transferInfo.setStatusEnum(ChatStatus.LOGIN);
			IOStream.writeMessage(socket, transferInfo);
			String username = transferInfo.getUsername();
			
			//统计在线人数
			onlineUsers.add(username);
			onlineSockets.add(socket);
			ChatServer.userSocketMap.put(username, socket);
			
			//返回用户上线信息给所有用户（系统消息）
			transferInfo = new TransferInfo();
			transferInfo.setStatusEnum(ChatStatus.NOTICE);
			String notice = ">> 欢迎 " + username + " 来到聊天室";
			transferInfo.setNotice(notice);
			sendAll(transferInfo);	
			
			//发送最新的用户列表给客户端
			transferInfo = new TransferInfo();
			transferInfo.setUserOnlineArray(onlineUsers.toArray(new String[onlineUsers.size()]));
			transferInfo.setStatusEnum(ChatStatus.ULIST);
			sendAll(transferInfo);
			
			//刷新在线用户列表
			flushOnlineUserList();
			
			//发送信息给服务器日志
			log(notice);
		}else{
			//返回登录失败给客户端
			IOStream.writeMessage(socket, transferInfo);
			log(transferInfo.getUsername() + "登录失败");
		}
	}
	public void log(String log) {
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = simpleDateFormat.format(date);
		JTextPane txtLog = serverFrame.serverInfoPanel.txtLog;
		String oldLog = txtLog.getText();
		txtLog.setText(oldLog + "\n" + dateStr + ":" + log);
	}
	/**
	 * 刷新用户列表（当一个用户上线时，便刷新）
	 */
	public void flushOnlineUserList() {
		JList lstUser = serverFrame.onlineUserPanel.lstUser;
		String[] userArray = onlineUsers.toArray(new String[onlineUsers.size()]);
		lstUser.setListData(userArray);
		serverFrame.serverInfoPanel.txtNumber.setText(userArray.length + "");
	}
	/**
	 * 广播给所有人
	 * @param transferInfo
	 */
	public void sendAll(TransferInfo transferInfo) {
		for (int i = 0; i < onlineSockets.size(); i++){
			Socket tempSocket = onlineSockets.get(i);
			IOStream.writeMessage(tempSocket, transferInfo);
		}
	}
	
	/**
	 * 私聊
	 * @param transferInfo
	 */
	public void send(TransferInfo transferInfo) {
		String reciver = transferInfo.getReciver();
		String sender = transferInfo.getSender();
		Socket socket1 = ChatServer.userSocketMap.get(reciver);
		IOStream.writeMessage(socket1, transferInfo);
		
		Socket socket2 = ChatServer.userSocketMap.get(sender);
		IOStream.writeMessage(socket2, transferInfo);
	}
	/**
	 * 处理客户端聊天请求
	 */
	public void chatHandler(TransferInfo transferInfo) {
		String reciver = transferInfo.getReciver();
		if ("ALL".equals(reciver)) {
			//发送给所有人
			sendAll(transferInfo);
		}else{
			//私聊
			send(transferInfo);
		}
	}
	
	/**
	 * 验证登录
	 * @param transferInfo
	 * @return
	 */
	public boolean checkUserLogin(TransferInfo transferInfo) {
		try {
			String username = transferInfo.getUsername();
			String password = transferInfo.getPassword();
			FileInputStream fileInputStream = new FileInputStream(new File("src/user.txt"));
			DataInputStream dataInputStream = new DataInputStream(fileInputStream);
			String row = null;
			while ((row = dataInputStream.readLine()) != null) {
				//从文件中读取的行
				String fileUser = row;
				if ((username + "|" + password).equals(row)) {
					System.out.println("服务端：用户名密码正确");
					return true;
				}
			}
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return false;
	}
	
	/**
	 * 发送抖动消息给其他人的客户端
	 * @param rInfo
	 */
	public void shake(TransferInfo transferInfo) {
		String reciver = transferInfo.getReciver();
		if ("ALL".equals(reciver)) {
			//发送给所有人
			sendAll(transferInfo);
		}else{
			//私发
			send(transferInfo);
		}
	}
	/**
	 * 用户退出处理，清理在线人数，刷新用户列表，告诉所有人，你已经离开
	 * @param transferInfo
	 */
	private void logout(TransferInfo transferInfo) {
		String userName = transferInfo.getUsername();
		//将该用户从用户集合移除
		Iterator<String> userIter = onlineUsers.iterator();
		while(userIter.hasNext()) {
			if(userIter.next().equals(userName)) {
				userIter.remove();
			}
		}
		
		//将该用户从socket集合移除
		Iterator<Socket> socketIter = onlineSockets.iterator();
		while(socketIter.hasNext()) {
			Socket next = socketIter.next();
			if(socket == next) {
				socketIter.remove();
			}
		}
		
		//将user与Socket的关系从Map中移除
		ChatServer.userSocketMap.remove(userName);
		
		//刷新服务器面板的用户列表
		flushOnlineUserList();
		
		//给所有在线的用户发送下线消息
		transferInfo.setStatusEnum(ChatStatus.NOTICE);
		sendAll(transferInfo);
		
		//告诉其他人刷新用户列表
		transferInfo.setUserOnlineArray(onlineUsers.toArray(new String [onlineUsers.size()]));
		transferInfo.setStatusEnum(ChatStatus.ULIST);
		sendAll(transferInfo);
	}

}
