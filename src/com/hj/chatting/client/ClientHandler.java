package com.hj.chatting.client;

import java.awt.Color;
import java.net.Socket;

import javax.swing.JOptionPane;

import com.hj.chatting.entity.ChatStatus;
import com.hj.chatting.entity.TransferInfo;
import com.hj.chatting.io.IOStream;

/**
 * 客户端开启一个线程，来一起读消息
 * 
 * @author huang
 *
 */
public class ClientHandler extends Thread {

	Socket socket;
	//登录窗体
	LoginFrame loginFrame;
	//聊天窗体
	ChatFrame chatFrame;
	public ClientHandler(Socket socket, LoginFrame loginFrame) {
		this.socket = socket;
		this.loginFrame = loginFrame;
	}

	@Override
	public void run() {

		// 默认重复拿
		while (true) {
			// 模拟一直拿消息
			Object object = IOStream.readMessage(socket);

			if (object instanceof TransferInfo) {
				TransferInfo transferInfo = (TransferInfo) object;
				if (transferInfo.getStatusEnum() == ChatStatus.LOGIN) {
					//登录消息
					loginResult(transferInfo);
				}else if (transferInfo.getStatusEnum() == ChatStatus.CHAT) {
					//聊天消息
					chatResult(transferInfo);
					
				}else if (transferInfo.getStatusEnum() == ChatStatus.NOTICE) {
					//聊天消息
					noticeResult(transferInfo);
					
				}else if (transferInfo.getStatusEnum() == ChatStatus.ULIST) {
					//刷新当前在线用户列表
					onlineUsersResult(transferInfo);
				}
				
				
			}else {
				
			}
		}
	}
	/**
	 * 登录消息处理
	 * @param transferInfo
	 */
	public void loginResult(TransferInfo transferInfo) {
		if (transferInfo.getLoginSuccess()) {
			//登录成功，跳转主页面
			String username = transferInfo.getUsername();
			
			chatFrame = new ChatFrame(username, socket);
			
			//关闭登录窗口
			loginFrame.dispose();

		}else{
			//登录失败
			System.out.println("客户端接收到登录失败");
			System.out.println(transferInfo.getNotice());
		}
	}
	/**
	 * 系统消息处理
	 * @param transferInfo
	 */
	public void noticeResult(TransferInfo transferInfo) {
		//在公屏上显示系统消息
		String originText = chatFrame.acceptPane.getText();
		if (!originText.isEmpty()) {
			chatFrame.acceptPane.setText(originText + "\n" + transferInfo.getNotice());
		}else{
			chatFrame.acceptPane.setText(transferInfo.getNotice());
		}
		
	}
	/**
	 * 聊天消息处理
	 * @param transferInfo
	 */
	public void chatResult(TransferInfo transferInfo) {
		String sender = transferInfo.getSender();
		String originText = chatFrame.acceptPane.getText();
		chatFrame.acceptPane.setText(originText + "\n" + "> " + sender + ":" + transferInfo.getContent());
	}
	
	/**
	 * 刷新当前界面的用户列表
	 * @param transferInfo
	 */
	public void onlineUsersResult(TransferInfo transferInfo) {
		String[] userOnlineArray = transferInfo.getUserOnlineArray();
		chatFrame.lstUser.setListData(userOnlineArray);
	}
}
