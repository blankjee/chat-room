package com.hj.chatting.entity;

import java.io.Serializable;

public class TransferInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8501366066504250232L;
	private String username;
	private String password;
	private String content;				//聊天内容
	private Boolean loginSuccess;		//登录成功标志
	private String notice;				//系统消息
	private ChatStatus statusEnum;		//消息类型
	private String[] userOnlineArray;	//在线用户列表
	private String sender;				//消息发送人
	private String reciver;				//消息接收者
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Boolean getLoginSuccess() {
		return loginSuccess;
	}
	public void setLoginSuccess(Boolean loginSuccess) {
		this.loginSuccess = loginSuccess;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public ChatStatus getStatusEnum() {
		return statusEnum;
	}
	public void setStatusEnum(ChatStatus statusEnum) {
		this.statusEnum = statusEnum;
	}
	public String[] getUserOnlineArray() {
		return userOnlineArray;
	}
	public void setUserOnlineArray(String[] userOnlineArray) {
		this.userOnlineArray = userOnlineArray;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReciver() {
		return reciver;
	}
	public void setReciver(String reciver) {
		this.reciver = reciver;
	}
	
}
