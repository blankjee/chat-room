package com.hj.chatting.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.management.remote.TargetedNotification;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.hj.chatting.entity.ChatStatus;
import com.hj.chatting.entity.TransferInfo;
import com.hj.chatting.io.IOStream;

/**
 * 登录界面
 * 
 * @author huang
 *
 */
public class LoginFrame extends JFrame {

	private static final long serialVersionUID = -1038810706341282031L;

	// 登录窗体宽度
	private static final Integer FRAME_WIDTH = 600;

	// 登录窗体高度
	private static final Integer FRAME_HEIGHT = 400;

	public LoginFrame() {
		/* 设置基础窗体 */
		this.setTitle("登录");
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		/* 获取屏幕像素并确定计算窗口位置居中 */
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screenSize.width;
		int screenheight = screenSize.height;
		setLocation((screenWidth - FRAME_WIDTH) / 2, (screenheight - FRAME_HEIGHT) / 2);

		/* 设置窗口内容 */
		// 背景图片
		ImageIcon imageIcon = new ImageIcon("src/image/login-bg.jpg");
		JLabel jlbBg = new JLabel(imageIcon);
		// 设置图片的位置和大小
		jlbBg.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
		// 设置布局为空布局
		jlbBg.setLayout(null);
		// 添加到当前窗体中
		this.add(jlbBg);

		// 标题
		JLabel lblTitle = new JLabel("欢迎使用在线聊天系统");
		lblTitle.setBounds(170, 30, 300, 50);
		lblTitle.setFont(new Font("宋体", Font.BOLD, 26));
		lblTitle.setForeground(Color.WHITE);
		jlbBg.add(lblTitle);

		// 账户
		JLabel lblUid = new JLabel("账号：");
		// 设置位置、大小，setSize和setLocation的结合体
		lblUid.setBounds(180, 120, 120, 30);
		lblUid.setFont(new Font("宋体", 0, 16));
		// 设置标签文本的颜色为白色
		lblUid.setForeground(Color.WHITE);
		jlbBg.add(lblUid);

		JTextField textUid = new JTextField();
		textUid.setBounds(250, 120, 160, 30);
		jlbBg.add(textUid);

		// 密码
		JLabel lblPwd = new JLabel("密码：");
		lblPwd.setBounds(180, 170, 120, 30);
		lblPwd.setFont(new Font("宋体", 0, 16));
		// 设置字体颜色为白色
		lblPwd.setForeground(Color.WHITE);
		jlbBg.add(lblPwd);

		JPasswordField textPwd = new JPasswordField();
		textPwd.setBounds(250, 170, 160, 30);
		jlbBg.add(textPwd);

		// 创建一个文字按钮
		JButton enter = new JButton("登 录");
		// 设置位置、大小
		enter.setBounds(210, 250, 80, 25);
		//加监听
		enter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = textUid.getText();
				String password = textPwd.getText();
				TransferInfo transferInfo = new TransferInfo();
				transferInfo.setUsername(username);
				transferInfo.setPassword(password);
				transferInfo.setStatusEnum(ChatStatus.LOGIN);
				connectionServer(transferInfo);
			}
		});
		jlbBg.add(enter);

		// 创建一个取消按钮
		JButton cancel = new JButton("取 消");
		// 设置按钮的位置、大小
		cancel.setBounds(320, 250, 80, 25);
		// 添加到背景图片上
		jlbBg.add(cancel);

		setVisible(true);
	}

	/**
	 * 连接服务器的方法
	 * @param transferInfo
	 */
	public void connectionServer(TransferInfo transferInfo) {
		try {
			Socket socket = new Socket("127.0.0.1", 8888);
			
			//写一个消息
			IOStream.writeMessage(socket, transferInfo);
			
			//开启客户端子线程，接收消息
			ClientHandler clientHandler = new ClientHandler(socket, this);
			clientHandler.start();
			
			
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("客户端连接");
		System.out.println("点击了登录按钮之后触发");
	}
	
	public static void main(String[] args) {
		new LoginFrame();

	}

}
