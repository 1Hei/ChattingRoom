package ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;

class ListItem{
    //����
    private String text;
    //ͼ��
    private Icon icon;
    //�Ƿ�Ϊ����λ
    private boolean isFriend;
    //��¼ԭ����icon
    private Icon tmp;
    //δ����Ϣ
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
	//����Ϣ��������ô����û�ͼ����ʾ����Ϣ��ͼ��
	public void havaMessage(String msg) {
		//����ԭ����Icon(ֻ�ڵ�һ����Ϣ������ʱ�򱣴�)
		if(msgs.size()==0) {
			tmp = this.getIcon();
			//���õ�ǰ��IconΪ��δ����Ϣ
			this.setIcon(new ImageIcon(ListItem.class.getResource("/recourses/weiduxiaoxi.png")));
		}
		//��¼δ����Ϣ
		msgs.add(msg);
	}
	//��ԭԭ����ͼ��
	public void recover() {
		setIcon(tmp);
		msgs.clear();
	}
	//�õ���Ϣ
	public List<String> getMsgs() {
		return msgs;
	}
}
