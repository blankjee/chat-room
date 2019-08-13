package com.hj.chatting.client;

import java.net.Socket;

import javax.swing.JOptionPane;

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
	LoginFrame loginFrame;
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
				loginResult(transferInfo);
			}
			System.out.println("客户端接到的消息：" + object);
			if ("登录成功".equals(object)) {
				System.out.println("客户端收到登录成功的消息");
			}

		}
	}
	/**
	 * 登录结果处理
	 * @param transferInfo
	 */
	public void loginResult(TransferInfo transferInfo) {
		if (transferInfo.getLoginSuccess()) {
			//登录成功，跳转主页面
			ChatFrame chatFrame = new ChatFrame();
			//关闭登录窗口
			loginFrame.dispose();
		}else{
			//登录失败
			System.out.println("客户端接收到登录失败");
		}
	}
}
