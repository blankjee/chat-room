package com.hj.chatting.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.tools.Tool;

import com.hj.chatting.entity.ChatStatus;
import com.hj.chatting.entity.TransferInfo;
import com.hj.chatting.entity.User;
import com.hj.chatting.io.IOStream;
import com.hj.chatting.ulist.ImageCellRenderer;
import com.hj.chatting.ulist.ImageListModel;


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
	ChatFrame chatFrame;
	JTextPane sendPane;
	JComboBox reciverBox;
	
	public ChatFrame(String username, Socket socket) {
		this.setTitle("聊天室");
		this.username = username;
		this.socket = socket;
		chatFrame = this;
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
		acceptPane.setOpaque(true); // 不透明
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
		lstUser.setFixedCellHeight(60);
		
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem privateChat = new JMenuItem("私聊");
		privateChat.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Object reciverObj = lstUser.getSelectedValue();
				if (reciverObj instanceof User) {
					User user = (User) reciverObj;
					String reciver = user.getUserName();
					reciverBox.removeAllItems();
					reciverBox.addItem("ALL");
					reciverBox.addItem(reciver);
					reciverBox.setSelectedItem(reciver);
				}
			}
		});
		popupMenu.add(privateChat);
		JMenuItem blackList = new JMenuItem("黑名单");
		blackList.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		popupMenu.add(blackList);
		//添加点击事件，需要确认是右键。
		lstUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//监听左键|右键
				if (e.isMetaDown() && lstUser.getSelectedIndex() >= 0) {
					//弹出菜单
					popupMenu.show(lstUser, e.getX(), e.getY());
				}
			}
		});
		JScrollPane spUser = new JScrollPane(lstUser);
		spUser.setFont(new Font("宋体", 0, 14));
		spUser.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		spUser.setBounds(15, 17, 200, 507);
		frameBg.add(spUser);

		// 输入框
		sendPane = new JTextPane();
		sendPane.setOpaque(true);
		sendPane.setFont(new Font("宋体", 0, 16));

		JScrollPane scoPane = new JScrollPane(sendPane);// 设置滚动条
		scoPane.setBounds(225, 400, 500, 122);
		scoPane.setOpaque(false);
		scoPane.getViewport().setOpaque(false);
		frameBg.add(scoPane);

		// 添加表情选择
		/*
		JLabel lblface = new JLabel(new ImageIcon("src/image/face.png"));
		lblface.setBounds(225, 363, 25, 25);
		frameBg.add(lblface);
		*/
		// 添加抖动效果
		JLabel lbldoudong = new JLabel(new ImageIcon("src/image/shake.png"));
		//lbldoudong.setBounds(252, 363, 25, 25);
		lbldoudong.setBounds(225, 363, 25, 25);
		lbldoudong.addMouseListener(new MouseAdapter () {
			@Override
			public void mouseClicked(MouseEvent e) {
				new Shake(chatFrame).start();
				TransferInfo transferInfo = new TransferInfo();
				transferInfo.setStatusEnum(ChatStatus.SHAKE);
				transferInfo.setSender(username);
				String reciver = "ALL";
				Object reciverObj = reciverBox.getSelectedItem();
				if (reciverObj != null) {
					reciver = String.valueOf(reciverObj);
				}
				transferInfo.setReciver(reciver);
				IOStream.writeMessage(socket, transferInfo);
			}
		});
		frameBg.add(lbldoudong);

		//私聊标签
		JLabel reciverLabel = new JLabel("聊天对象：");
		reciverLabel.setBounds(505, 362, 80, 25);
		frameBg.add(reciverLabel);
		//ALL、私聊下拉选择框
		reciverBox = new JComboBox();
		reciverBox.setSelectedItem("ALL");
		reciverBox.addItem("ALL");
		reciverBox.setBounds(575, 362, 150, 25);
		frameBg.add(reciverBox);

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
				String reciver = "ALL";
				Object reciverObj = reciverBox.getSelectedItem();
				if (reciverObj != null) {
					reciver = String.valueOf(reciverObj);
				}
				System.out.println("发送消息：" + reciver);
				//接收人
				transferInfo.setReciver(reciver);
				transferInfo.setStatusEnum(ChatStatus.CHAT);
				IOStream.writeMessage(socket, transferInfo);
				sendPane.setText("");
			}
		});
		frameBg.add(send);

		
		//客户端关闭窗体退出
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					System.out.println(username + "窗口关闭");
					TransferInfo tfi = new TransferInfo();
					tfi.setStatusEnum(ChatStatus.EXIT);
					tfi.setUsername(username);
					tfi.setNotice(username + "已离开聊天室.....");
					IOStream.writeMessage(socket, tfi);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
				
		setVisible(true);

	}
}
