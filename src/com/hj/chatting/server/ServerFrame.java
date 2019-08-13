package com.hj.chatting.server;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import com.hj.chatting.server.ui.OnlineUserPanel;
import com.hj.chatting.server.ui.ServerInfoPanel;

/**
 * 服务器端主界面
 * 
 * @author huang
 *
 */
public class ServerFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8235369520375669451L;

	// 服务器窗体宽度
	public static final Integer FRAME_WIDTH = 550;
	// 服务器窗体高度
	public static final Integer FRAME_HEIGHT = 500;

	public ServerFrame() {

		this.setTitle("聊天室服务器端");
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setResizable(false);

		/* 获取屏幕像素并确定计算窗口位置居中 */
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screenSize.width;
		int screenheight = screenSize.height;
		setLocation((screenWidth - FRAME_WIDTH) / 2, (screenheight - FRAME_HEIGHT) / 2);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 选项卡
		JTabbedPane tpServer = new JTabbedPane(JTabbedPane.TOP);
		tpServer.setBackground(Color.WHITE);
		tpServer.setFont(new Font("宋体", 0, 18));

		ServerInfoPanel serverInfoPanel = new ServerInfoPanel();
		OnlineUserPanel onlineUserPanel = new OnlineUserPanel();
		tpServer.add("服务器信息", serverInfoPanel.getServerInfoPanel());
		tpServer.add("在线用户列表", onlineUserPanel.getUserPanel());
		
		add(tpServer);
		setVisible(true);
	}

	

	public static void main(String[] args) {
		new ServerFrame();
	}

}
