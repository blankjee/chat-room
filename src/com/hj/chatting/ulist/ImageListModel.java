package com.hj.chatting.ulist;

import java.util.List;
import java.util.ArrayList;

import javax.swing.AbstractListModel;

import com.hj.chatting.entity.User;

/**
 * JList的模型类，构成JList的模型元素
 * @author huang
 *
 */
public class ImageListModel extends AbstractListModel<User> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8116378668465150072L;
	
	
	private List<User> list = new ArrayList<User>();
	
	public void addElement(User str) {
		list.add(str);
	}
	@Override
	public User getElementAt(int index) {
	
		return list.get(index);
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return list.size();
	}

}
