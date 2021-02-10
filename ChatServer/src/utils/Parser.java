package utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

//�����յ�����Ϣ���չ���ת���ַ�������
public class Parser {
	//������Ϣ�õ���Ϣͷ��ָ�
	public String parseHeader(String message) {
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(message, JsonObject.class);
		String header = json.get("operation").getAsString();
		return header;
	}
	//��������Ϣ�л�ȡqq
	public int parseGetQQ(String message) {
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(message, JsonObject.class);
		String qqStr = json.get("qq").getAsString();
		int qq = Integer.parseInt(qqStr);
		return qq;
	}
	//��������Ϣ�л�ȡ����
	public String parseGetPassword(String message) {
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(message, JsonObject.class);
		String password = json.get("password").getAsString();
		return password;
	}
	//��������Ϣ�л�ȡ�û���
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
	//������Ϣ���������Ӻ����û���qq
	public int parseGetSourceQQ(String message) {
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(message, JsonObject.class);
		int qs = json.get("qs").getAsInt();
		return qs;
	}
	//������Ϣ���������Ӻ���Ŀ��qq
	public String parseGetOtherQQ(String message) {
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(message, JsonObject.class);
		String qo = json.get("qo").getAsString();
		return qo;
	}
	//������Ϣ���������Ӻ��ѻ�Ӧ���
	public boolean parseGetReply(String message) {
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(message, JsonObject.class);
		boolean agree = json.get("agree").getAsBoolean();
		return agree;
	}
}
