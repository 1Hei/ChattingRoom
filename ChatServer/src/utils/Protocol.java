package utils;

import com.google.gson.JsonObject;

public class Protocol {
	//�����ݴ������Ϣ
	//���Ⱥ����Ϣ
	public String getToAllMessage(int qq, String from, String message) {
		JsonObject json = new JsonObject();
		json.addProperty("operation", "ToAll");
		json.addProperty("qq", qq);
		json.addProperty("from", from);
		json.addProperty("message", message);
		return json.toString();
	}
	//���˽����Ϣ
	public String getToOneMessage(int qq, String from, String to, String message) {
		JsonObject json = new JsonObject();
		json.addProperty("operation", "ToOne");
		json.addProperty("qq", qq);
		json.addProperty("message", message);
		json.addProperty("from", from);
		json.addProperty("to", to);
		return json.toString();
	}
	//����޸��û�����Ϣ
	public String getChangeNameMessage(int qq, String oldName, String newName) {
		JsonObject json = new JsonObject();
		json.addProperty("operation", "ChangeName");
		json.addProperty("qq", qq);
		json.addProperty("oldName", oldName);
		json.addProperty("newName", newName);
		return json.toString();
	}
	//��������û�����Ϣ
	public String getSetNameMessage(int qq, String name) {
		JsonObject json = new JsonObject();
		json.addProperty("operation", "SetName");
		json.addProperty("qq", qq);
		json.addProperty("username", name);
		return json.toString();
	}
	//�����ȡ�����û�������Ϣ
	public String getFindUsersMessage(int qq, String username, boolean isFriend) {
		JsonObject json = new JsonObject();
		json.addProperty("operation", "FindUsers");
		json.addProperty("qq", qq);
		json.addProperty("username", username);
		json.addProperty("isFriend", isFriend);
		return json.toString();
	}
	//��������û�����Ϣ
	public String getLeaveMessage(int qq, String name) {
		JsonObject json = new JsonObject();
		json.addProperty("operation", "Leave");
		json.addProperty("qq", qq);
		json.addProperty("username", name);
		return json.toString();
	}
	//���qq��Ϣ���û�
	public String getRegisterQQMessge(int qq) {
		JsonObject json = new JsonObject();
		json.addProperty("operation", "GetQQ");
		json.addProperty("qq", qq);
		return json.toString();
	}
	//���qq�Ƿ���ڵ��жϽ�����û�
	public String getQQExistMessage(boolean isExist, String reason) {
		JsonObject json = new JsonObject();
		json.addProperty("operation", "CheckQQ");
		json.addProperty("exist", isExist);
		json.addProperty("failReason", reason);
		return json.toString();
	}
	//����һص�������Ϣ���û�
	public String getFindPasswordMessage(String password) {
		JsonObject json = new JsonObject();
		json.addProperty("operation", "FindPassword");
		json.addProperty("password", password);
		return json.toString();
	}
	//�����¼������û�
	public String getLoginMessage(boolean login, String username) {
		JsonObject json = new JsonObject();
		json.addProperty("operation", "Login");
		json.addProperty("login", login);
		json.addProperty("username", username);
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
