package com.hj.chatting.entity;

import java.io.Serializable;

public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8844200896790186834L;
	
	private String userName;
	private String motto;
	private String nickName;
	private String uiconPath;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMotto() {
		return motto;
	}
	public void setMotto(String motto) {
		this.motto = motto;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getUiconPath() {
		return uiconPath;
	}
	public void setUiconPath(String uiconPath) {
		this.uiconPath = uiconPath;
	}
}
