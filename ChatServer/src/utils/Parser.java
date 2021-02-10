package utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

//将接收到的消息按照规则转成字符串数组
public class Parser {
	//解析消息拿到消息头（指令）
	public String parseHeader(String message) {
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(message, JsonObject.class);
		String header = json.get("operation").getAsString();
		return header;
	}
	//解析从消息中获取qq
	public int parseGetQQ(String message) {
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(message, JsonObject.class);
		String qqStr = json.get("qq").getAsString();
		int qq = Integer.parseInt(qqStr);
		return qq;
	}
	//解析从消息中获取密码
	public String parseGetPassword(String message) {
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(message, JsonObject.class);
		String password = json.get("password").getAsString();
		return password;
	}
	//解析从消息中获取用户名
	public String parseGetUsername(String message) {
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(message, JsonObject.class);
		String username = json.get("username").getAsString();
		return username;
	}
	//解析消息获得修改前的用户名
	public String parseGetOldUsername(String message) {
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(message, JsonObject.class);
		String username = json.get("oldName").getAsString();
		return username;
	}
	//解析消息获得修改后的用户名
	public String parseGetNewUsername(String message) {
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(message, JsonObject.class);
		String username = json.get("newName").getAsString();
		return username;
	}
	//解析消息获得发送消息的用户的用户名
	public String parseGetFrom(String message) {
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(message, JsonObject.class);
		String username = json.get("from").getAsString();
		return username;
	}
	//解析消息获得消息发往的用户的用户名
	public String parseGetTo(String message) {
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(message, JsonObject.class);
		String username = json.get("to").getAsString();
		return username;
	}
	//解析消息获得消息内容
	public String parseGetMessage(String message) {
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(message, JsonObject.class);
		String username = json.get("message").getAsString();
		return username;
	}
	//解析消息获得申请添加好友用户的qq
	public int parseGetSourceQQ(String message) {
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(message, JsonObject.class);
		int qs = json.get("qs").getAsInt();
		return qs;
	}
	//解析消息获得申请添加好友目的qq
	public String parseGetOtherQQ(String message) {
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(message, JsonObject.class);
		String qo = json.get("qo").getAsString();
		return qo;
	}
	//解析消息获得申请添加好友回应结果
	public boolean parseGetReply(String message) {
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(message, JsonObject.class);
		boolean agree = json.get("agree").getAsBoolean();
		return agree;
	}
}
