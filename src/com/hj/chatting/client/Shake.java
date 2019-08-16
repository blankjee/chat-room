package com.hj.chatting.client;
/**
 * 窗口抖动类
 * @author huang
 *
 */
public class Shake extends Thread {

	ChatFrame chatFrame;
	public Shake(ChatFrame chatFrame) {
		this.chatFrame = chatFrame;
	}
	@Override
	public void run() {

		try {
			for (int i = 0; i < 3; i++){
				chatFrame.setLocation(chatFrame.getX() - 28, chatFrame.getY());
				Thread.sleep(88);
				chatFrame.setLocation(chatFrame.getX(), chatFrame.getY() - 28);
				Thread.sleep(88);
				chatFrame.setLocation(chatFrame.getX() + 28, chatFrame.getY());
				Thread.sleep(88);
				chatFrame.setLocation(chatFrame.getX(), chatFrame.getY() + 28);
				Thread.sleep(88);
			}
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		super.run();
	}
}
