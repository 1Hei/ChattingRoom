package utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

//将接收到的消息按规则转成字符串数组
public class Parser {
	
	//解析消息拿到消息头（指令）
	public String parseGetHeader(String message) {
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(message, JsonObject.class);
		String header = json.get("operation").getAsString();
		return header;
	}
	
	//解析从消息中获取qq	
	public String parseGetQQ(String message) {
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(message, JsonObject.class);
		String qq = json.get("qq").getAsString();
		return qq;
	}
	//解析从消息中获取password	
	public String parseGetPassword(String message) {
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(message, JsonObject.class);
		String password = json.get("password").getAsString();
		return password;
	}
	
	//qq是否存在消息
	public boolean parseIsQQExistMessage(String message) {
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(message, JsonObject.class);
		boolean isExist = json.get("exist").getAsBoolean();
		return isExist;
	}
	//解析登录返回消息
	public boolean parseLoginMessage(String message) {
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(message, JsonObject.class);
		boolean login = json.get("login").getAsBoolean();
		return login;
	}
	//解析消息获取登录失败时的错误原因
	public String parseGetFailReason(String message) {
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(message, JsonObject.class);
		String reason = json.get("failReason").getAsString();
		return reason;
	}
	//解析消息获取登录名
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
	//解析获得是否为好友
	public boolean parseIsFriend(String message) {
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(message, JsonObject.class);
		boolean isFriend = json.get("isFriend").getAsBoolean();
		return isFriend;
	}
	//解析消息获得申请添加好友用户的qq
	public String parseGetSourceQQ(String message) {
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(message, JsonObject.class);
		String qs = json.get("qs").getAsString();
		return qs;
	}
	//解析消息获得添加好友回应结果
	public boolean parseIsAgree(String message) {
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(message, JsonObject.class);
		boolean agree = json.get("agree").getAsBoolean();
		return agree;
	}
}

