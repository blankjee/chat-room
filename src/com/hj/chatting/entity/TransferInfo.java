package com.hj.chatting.entity;

import java.io.Serializable;

public class TransferInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8501366066504250232L;
	private String username;
	private String password;
	private Boolean loginSuccess;		//登录成功标志
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
	
	
}
