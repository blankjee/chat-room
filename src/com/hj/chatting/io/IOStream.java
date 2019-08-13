package com.hj.chatting.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
/**
 * IO流工具类
 * @author huang
 *
 */
public class IOStream {
	//读消息
	public static Object readMessage(Socket socket) {
		Object object = null;
		try {
			InputStream inputStream = socket.getInputStream();
			ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
			object = objectInputStream.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}
	//写消息
	public static void writeMessage(Socket socket, Object message) {
		try {
			OutputStream outputStream = socket.getOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
			objectOutputStream.writeObject(message);
			objectOutputStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
