package com.hj.chatting.server.ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import com.hj.chatting.constants.Constants;
import com.hj.chatting.server.ServerFrame;

/**
 * 服务器参数选项卡
 * @author huang
 *
 */
public class ServerInfoPanel {
	public JTextField txtServerName;
	public JTextField txtIP;
	public JTextField txtNumber;
	public JTextPane txtLog;
	/**
	 * 第一个选项卡信息
	 * 
	 * @return
	 */
	public JLabel getServerInfoPanel() {
		// 整个第一个服务选项卡面板，包括日志区域
		JPanel pnlServer = new JPanel();
		pnlServer.setOpaque(false);
		pnlServer.setLayout(null);
		pnlServer.setBounds(0, 0, ServerFrame.FRAME_WIDTH, ServerFrame.FRAME_HEIGHT);

		// 日志标签
		JLabel lblLog = new JLabel("[服务器日志]");
		lblLog.setForeground(Color.BLACK);
		lblLog.setFont(new Font("宋体", 0, 16));
		lblLog.setBounds(130, 5, 100, 30);
		pnlServer.add(lblLog);

		// 日志区域
		txtLog = new JTextPane();
		txtLog.setBackground(Color.BLACK);
		txtLog.setForeground(Color.white);
		txtLog.setOpaque(true);
		txtLog.setFont(new Font("宋体", 0, 14));
		

		JScrollPane scoPaneOne = new JScrollPane(txtLog);// 设置滚动条
		scoPaneOne.setBounds(130, 35, 380, 360);
		scoPaneOne.setOpaque(false);
		scoPaneOne.getViewport().setOpaque(false);

		pnlServer.add(scoPaneOne);

		pnlServer.add(stopBtn());

		pnlServer.add(saveLogBtn());

		pnlServer.add(getServerParam());

		// 加载窗体的背景图片
		ImageIcon imageIcon = new ImageIcon("src\\image\\chat-bg.jpg");
		// 创建一个标签并将图片添加进去
		JLabel lblBackground = new JLabel(imageIcon);
		// 设置图片的位置和大小
		lblBackground.setBounds(0, 200, 300, 300);
		// 添加到当前窗体中
		lblBackground.add(pnlServer);

		return lblBackground;
	}

	/**
	 * 服务器参数信息界面
	 * 
	 * @return
	 */
	public JPanel getServerParam() {
		// 服务器参数信息面板，不包括日志区域
		JPanel serverParamPanel = new JPanel();
		serverParamPanel.setOpaque(false);
		serverParamPanel.setBounds(5, 35, 100, 360);
		serverParamPanel.setFont(new Font("宋体", 0, 14));
		serverParamPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(""),
				BorderFactory.createEmptyBorder(1, 1, 1, 1)));

		JLabel lblNumber = new JLabel("当前在线人数:");
		lblNumber.setFont(new Font("宋体", 0, 14));
		serverParamPanel.add(lblNumber);

		txtNumber = new JTextField("0 人", 10);
		txtNumber.setFont(new Font("宋体", 0, 14));
		txtNumber.setEditable(false);
		serverParamPanel.add(txtNumber);

		JLabel lblServerName = new JLabel("服务器名称:");
		lblServerName.setFont(new Font("宋体", 0, 14));
		serverParamPanel.add(lblServerName);

		txtServerName = new JTextField(10);
		txtServerName.setFont(new Font("宋体", 0, 14));
		txtServerName.setEditable(false);
		serverParamPanel.add(txtServerName);

		JLabel lblIP = new JLabel("服务器IP:");
		lblIP.setFont(new Font("宋体", 0, 14));
		serverParamPanel.add(lblIP);

		txtIP = new JTextField(10);
		txtIP.setFont(new Font("宋体", 0, 14));
		txtIP.setEditable(false);
		serverParamPanel.add(txtIP);

		JLabel lblPort = new JLabel("服务器端口:");
		lblPort.setFont(new Font("宋体", 0, 14));
		serverParamPanel.add(lblPort);

		JTextField txtPort = new JTextField(Constants.SERVER_PORT + "", 10);
		txtPort.setFont(new Font("宋体", 0, 14));
		txtPort.setEditable(false);
		serverParamPanel.add(txtPort);

		return serverParamPanel;

	}

	public JButton stopBtn() {
		JButton stopBtn = new JButton("关闭服务器");
		stopBtn.setBackground(Color.WHITE);
		stopBtn.setFont(new Font("宋体", 0, 14));
		stopBtn.setBounds(200, 400, 110, 30);

		return stopBtn;
	}

	public JButton saveLogBtn() {
		JButton saveLogBtn = new JButton("保存日志");
		saveLogBtn.setBackground(Color.WHITE);
		saveLogBtn.setFont(new Font("宋体", 0, 14));
		saveLogBtn.setBounds(320, 400, 110, 30);

		return saveLogBtn;
	}
}
