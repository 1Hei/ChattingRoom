package utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

//�����յ�����Ϣ������ת���ַ�������
public class Parser {
	
	//������Ϣ�õ���Ϣͷ��ָ�
	public String parseGetHeader(String message) {
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(message, JsonObject.class);
		String header = json.get("operation").getAsString();
		return header;
	}
	
	//��������Ϣ�л�ȡqq	
	public String parseGetQQ(String message) {
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(message, JsonObject.class);
		String qq = json.get("qq").getAsString();
		return qq;
	}
	//��������Ϣ�л�ȡpassword	
	public String parseGetPassword(String message) {
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(message, JsonObject.class);
		String password = json.get("password").getAsString();
		return password;
	}
	
	//qq�Ƿ������Ϣ
	public boolean parseIsQQExistMessage(String message) {
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(message, JsonObject.class);
		boolean isExist = json.get("exist").getAsBoolean();
		return isExist;
	}
	//������¼������Ϣ
	public boolean parseLoginMessage(String message) {
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(message, JsonObject.class);
		boolean login = json.get("login").getAsBoolean();
		return login;
	}
	//������Ϣ��ȡ��¼ʧ��ʱ�Ĵ���ԭ��
	public String parseGetFailReason(String message) {
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(message, JsonObject.class);
		String reason = json.get("failReason").getAsString();
		return reason;
	}
	//������Ϣ��ȡ��¼��
	public String parseGetUsername(String message) {
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(message, JsonObject.class);
		String username = json.get("username").getAsString();
		return username;
	}
	//������Ϣ����޸�ǰ���û���
	public String parseGetOldUsername(String message) {
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(message, JsonObject.class);
		String username = json.get("oldName").getAsString();
		return username;
	}
	//������Ϣ����޸ĺ���û���
	public String parseGetNewUsername(String message) {
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(message, JsonObject.class);
		String username = json.get("newName").getAsString();
		return username;
	}
	//������Ϣ��÷�����Ϣ���û����û���
	public String parseGetFrom(String message) {
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(message, JsonObject.class);
		String username = json.get("from").getAsString();
		return username;
	}
	//������Ϣ�����Ϣ�������û����û���
	public String parseGetTo(String message) {
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(message, JsonObject.class);
		String username = json.get("to").getAsString();
		return username;
	}
	//������Ϣ�����Ϣ����
	public String parseGetMessage(String message) {
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(message, JsonObject.class);
		String username = json.get("message").getAsString();
		return username;
	}
	//��������Ƿ�Ϊ����
	public boolean parseIsFriend(String message) {
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(message, JsonObject.class);
		boolean isFriend = json.get("isFriend").getAsBoolean();
		return isFriend;
	}
	//������Ϣ���������Ӻ����û���qq
	public String parseGetSourceQQ(String message) {
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(message, JsonObject.class);
		String qs = json.get("qs").getAsString();
		return qs;
	}
	//������Ϣ�����Ӻ��ѻ�Ӧ���
	public boolean parseIsAgree(String message) {
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(message, JsonObject.class);
		boolean agree = json.get("agree").getAsBoolean();
		return agree;
	}
}

