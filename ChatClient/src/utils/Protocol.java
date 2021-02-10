package utils;

import com.google.gson.JsonObject;

public class Protocol {
	//�����ݴ������Ϣ
	//�����ȡ�����û�������Ϣ
	public String getFindUsersMessage(String qq, String username) {
		JsonObject json = new JsonObject();
		json.addProperty("operation", "FindUsers");
		json.addProperty("qq", qq);
		json.addProperty("username", username);
		return json.toString();
	}	
	//���Ⱥ����Ϣ
	public String getToAllMessage(String qq, String from, String message) {
		JsonObject json = new JsonObject();
		json.addProperty("operation", "ToAll");
		json.addProperty("qq", qq);
		json.addProperty("from", from);
		json.addProperty("message", message);
		return json.toString();
	}
	//���˽����Ϣ
	public String getToOneMessage(String qq, String from, String to, String message) {
		JsonObject json = new JsonObject();
		json.addProperty("operation", "ToOne");
		json.addProperty("qq", qq);
		json.addProperty("message", message);
		json.addProperty("from", from);
		json.addProperty("to", to);
		return json.toString();
	}
	//����޸��û�����Ϣ
	public String getChangeNameMessage(String qq, String oldName, String newName) {
		JsonObject json = new JsonObject();
		json.addProperty("operation", "ChangeName");
		json.addProperty("qq", qq);
		json.addProperty("oldName", oldName);
		json.addProperty("newName", newName);
		return json.toString();
	}
	//��������û�����Ϣ
	public String getSetNameMessage(String qq, String name) {
		JsonObject json = new JsonObject();
		json.addProperty("operation", "SetName");
		json.addProperty("qq", qq);
		json.addProperty("username", name);
		return json.toString();
	}
	//���ע������qq��Ϣ
	public String getQQMessage() {
		JsonObject json = new JsonObject();
		json.addProperty("operation", "GetQQ");
		return json.toString();
	}
	//���ע����Ϣ
	public String getRegisterMessage(String qq, String password) {
		JsonObject json = new JsonObject();
		json.addProperty("operation", "Register");
		json.addProperty("qq", qq);
		json.addProperty("password", password);
		return json.toString();
	}
	//����ж�qq�Ƿ������Ϣ
	public String getCheckQQMessage(String qq) {
		JsonObject json = new JsonObject();
		json.addProperty("operation", "CheckQQ");
		json.addProperty("qq", qq);
		return json.toString();
	}
	//����һ�������Ϣ
	public String getFindPasswordMessage(String qq) {
		JsonObject json = new JsonObject();
		json.addProperty("operation", "FindPassword");
		json.addProperty("qq", qq);
		return json.toString();
	}
	//����û���¼��Ϣ
	public String getLoginMessage(String qq, String password) {
		JsonObject json = new JsonObject();
		json.addProperty("operation", "Login");
		json.addProperty("qq", qq);
		json.addProperty("password", password);
		return json.toString();
	}
	//��װ�û���Ϣ
	public String getUserInfo(String qq, String username) {
		return username+"("+qq+")";
	}
	//�����Ӻ���������Ϣ
	public String getAddFriendMessage(String qq_from, String qq_to, String username) {
		JsonObject json = new JsonObject();
		json.addProperty("operation", "AddFriend");
		json.addProperty("qs", qq_from);
		json.addProperty("qo", qq_to);
		json.addProperty("username", username);
		return json.toString();
	}
	//���ɾ������������Ϣ
	public String getDeleteFriendMessage(String qq_from, String qq_to, String username) {
		JsonObject json = new JsonObject();
		json.addProperty("operation", "DeleteFriend");
		json.addProperty("qs", qq_from);
		json.addProperty("qo", qq_to);
		json.addProperty("username", username);
		return json.toString();
	}
	//�����Ӻ��Ѵ���Ϣ
	public String getAddFriendReplyMessage(String qs, String qq, String username,  boolean agree) {
		JsonObject json = new JsonObject();
		json.addProperty("operation", "Reply");
		json.addProperty("qs", qs);//Ҫ�������û���qq
		json.addProperty("qq", qq);//�Լ���qq
		json.addProperty("username", username);//�Լ����û���,����Է��ҵ�
		json.addProperty("agree", agree);
		return json.toString();
	}
	//��װ������Ե���Ϣ
	public String getTextMessage(String from, String to, String message) {
		StringBuffer sb = new StringBuffer();
		sb.append(from);
		sb.append(": ");
		sb.append(message);
		if(null != to) {
			sb.append("(to: ");
			sb.append(to);
			sb.append(')');
		}
		return sb.toString();
	}
}
