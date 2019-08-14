package com.hj.chatting.client;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.tools.Tool;

import com.hj.chatting.entity.ChatStatus;
import com.hj.chatting.entity.TransferInfo;
import com.hj.chatting.io.IOStream;

/**
 * 聊天界面
 * 
 * @author huang
 *
 */
public class ChatFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1709267049032639256L;

	// 聊天窗体宽度
	public static final Integer FRAME_WIDTH = 750;

	// 聊天窗体高度
	public static final Integer FRAME_HEIGHT = 600;
	
	//消息接受框
	public JTextPane acceptPane;
	//当前在线用户列表
	public JList lstUser;
	
	String username;
	Socket socket;

	public ChatFrame(String username, Socket socket) {
		this.setTitle("聊天室");
		this.username = username;
		this.socket = socket;
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screenSize.width;
		int height = screenSize.height;
		setLocation((width - FRAME_WIDTH) / 2, (height - FRAME_HEIGHT) / 2);

		// 加载窗体的背景图片
		ImageIcon imageIcon = new ImageIcon("src/image/chat-bg.jpg");
		// 创建一个标签并将图片添加进去
		JLabel frameBg = new JLabel(imageIcon);
		// 设置图片的位置和大小
		frameBg.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
		this.add(frameBg);

		// 接收框
		acceptPane = new JTextPane();
		acceptPane.setOpaque(false); // 透明
		acceptPane.setFont(new Font("宋体", 0, 16));

		// 接收滚动条
		JScrollPane scrollPaneOne = new JScrollPane(acceptPane);
		scrollPaneOne.setBounds(225, 20, 500, 332);
		scrollPaneOne.setOpaque(false);
		scrollPaneOne.getViewport().setOpaque(false);
		frameBg.add(scrollPaneOne);

		// 当前在线用户列表
		lstUser = new JList();
		lstUser.setFont(new Font("宋体", 0, 14));
		lstUser.setVisibleRowCount(17);
		lstUser.setFixedCellWidth(180);
		lstUser.setFixedCellHeight(18);

		JScrollPane spUser = new JScrollPane(lstUser);
		spUser.setFont(new Font("宋体", 0, 14));
		spUser.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		spUser.setBounds(15, 17, 200, 507);
		frameBg.add(spUser);

		// 输入框
		JTextPane sendPane = new JTextPane();
		sendPane.setOpaque(false);
		sendPane.setFont(new Font("宋体", 0, 16));

		JScrollPane scoPane = new JScrollPane(sendPane);// 设置滚动条
		scoPane.setBounds(225, 400, 500, 122);
		scoPane.setOpaque(false);
		scoPane.getViewport().setOpaque(false);
		frameBg.add(scoPane);

		// 添加表情选择
		JLabel lblface = new JLabel(new ImageIcon("src/image/face.png"));
		lblface.setBounds(225, 363, 25, 25);
		frameBg.add(lblface);

		// 添加抖动效果
		JLabel lbldoudong = new JLabel(new ImageIcon("src/image/shake.png"));
		lbldoudong.setBounds(252, 363, 25, 25);
		frameBg.add(lbldoudong);

		// 设置字体选择
		JLabel lblfontChoose = new JLabel(new ImageIcon("src/image/font.png"));
		lblfontChoose.setBounds(253, 363, 80, 25);
		frameBg.add(lblfontChoose);

		// 字体下拉选项
		JComboBox fontFamilyCmb = new JComboBox();
		GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] str = graphicsEnvironment.getAvailableFontFamilyNames();
		for (String string : str) {
			fontFamilyCmb.addItem(string);
		}
		fontFamilyCmb.setSelectedItem("楷体");
		fontFamilyCmb.setBounds(315, 363, 150, 25);
		frameBg.add(fontFamilyCmb);

		/*
		 * 发送按钮
		 */
		JButton send = new JButton("发 送");
		send.setBounds(600, 533, 125, 25);
		send.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String content = sendPane.getText();
				TransferInfo transferInfo = new TransferInfo();
				transferInfo.setContent(content);
				//发送人
				transferInfo.setSender(username);
				//接收人
				transferInfo.setReciver("ALL");
				transferInfo.setStatusEnum(ChatStatus.CHAT);
				IOStream.writeMessage(socket, transferInfo);
				sendPane.setText("");
			}
		});
		frameBg.add(send);

		setVisible(true);

	}
}
