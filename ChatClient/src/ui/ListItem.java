package ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;

class ListItem{
    //内容
    private String text;
    //图标
    private Icon icon;
    //是否为好友位
    private boolean isFriend;
    //记录原来的icon
    private Icon tmp;
    //未读消息
    private List<String> msgs = new ArrayList<String>();
   
	public ListItem(String text, String icon){
	        this.text = text;
	        this.setIcon(new ImageIcon(ListItem.class.getResource("/recourses/"+icon)));
	        setFriend(false);
	}
	public String getText() {
		return text;
	}
	public void setIcon(String icon) {
		setIcon(new ImageIcon(ListItem.class.getResource("/recourses/"+icon)));
	}
	public void setIcon(Icon icon) {
		this.icon = icon;
	}
	public Icon getIcon() {
		return icon;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isFriend() {
		return isFriend;
	}
	public void setFriend(boolean isFriend) {
		this.isFriend = isFriend;
	}
	//有消息进来，那么这个用户图标显示有消息的图标
	public void havaMessage(String msg) {
		//保存原来的Icon(只在第一条消息过来的时候保存)
		if(msgs.size()==0) {
			tmp = this.getIcon();
			//设置当前的Icon为由未读消息
			this.setIcon(new ImageIcon(ListItem.class.getResource("/recourses/weiduxiaoxi.png")));
		}
		//记录未读消息
		msgs.add(msg);
	}
	//还原原来的图标
	public void recover() {
		setIcon(tmp);
		msgs.clear();
	}
	//拿到消息
	public List<String> getMsgs() {
		return msgs;
	}
}
