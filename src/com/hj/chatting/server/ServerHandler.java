package com.hj.chatting.server;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

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

	public ServerHandler(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {

		// 默认重复拿
		while (true) {
			// 模拟一直拿消息
			Object object = IOStream.readMessage(socket);
			System.out.println("服务端接收到了：" + object);
			if (object instanceof TransferInfo) {
				TransferInfo transferInfo = (TransferInfo) object;
				boolean flag = checkUserLogin(transferInfo);
				transferInfo.setLoginSuccess(false);
				if (flag) {
					//返回登录成功给客户端
					transferInfo.setLoginSuccess(true);
					IOStream.writeMessage(socket, transferInfo);
				}else{
					//返回登录失败给客户端
					IOStream.writeMessage(socket, transferInfo);
				}
			}
			if ("zs".equals(object)) {
				IOStream.writeMessage(socket, "登录成功");
			}

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
}
