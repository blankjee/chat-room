package com.hj.chatting.ulist;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.SwingConstants;

import com.hj.chatting.entity.User;

/**
 * super是JList的渲染器，它继承了JLabel
 * @author huang
 *
 */
public class ImageCellRenderer extends DefaultListCellRenderer {
	
	private static final long serialVersionUID = -6233481640415228808L;

	/**
	 * list：JList对象
	 * value：重点，模型数据
	 * index：当前选择的单元格小标，从0开始
	 * isSelected：单元格选中的状态
	 */
	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, 
			int index, boolean isSelected, boolean cellHasFocus) {
		
		if(value instanceof User) {
			User user = (User)value;
			String uiconPath = user.getUiconPath();
			String userName = user.getUserName();
			String motto = user.getMotto();
			ImageIcon icon = new ImageIcon(uiconPath);
			icon.setImage(icon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
			setIcon(icon);
			String text = "<html><body><span color='#00B8D8' style='font-size:15px;'>" + userName + "</span><br/>" + motto + "</body></html>";
			setText(text);
			setForeground(Color.GRAY);
			setVerticalTextPosition(SwingConstants.TOP);
			setHorizontalTextPosition(SwingConstants.RIGHT);
		}
		return this;
	}
}
