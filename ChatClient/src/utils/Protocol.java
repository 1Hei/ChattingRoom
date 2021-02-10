package utils;

import com.google.gson.JsonObject;

public class Protocol {
	//将数据打包成消息
	//打包获取在线用户请求消息
	public String getFindUsersMessage(String qq, String username) {
		JsonObject json = new JsonObject();
		json.addProperty("operation", "FindUsers");
		json.addProperty("qq", qq);
		json.addProperty("username", username);
		return json.toString();
	}	
	//打包群发消息
	public String getToAllMessage(String qq, String from, String message) {
		JsonObject json = new JsonObject();
		json.addProperty("operation", "ToAll");
		json.addProperty("qq", qq);
		json.addProperty("from", from);
		json.addProperty("message", message);
		return json.toString();
	}
	//打包私发消息
	public String getToOneMessage(String qq, String from, String to, String message) {
		JsonObject json = new JsonObject();
		json.addProperty("operation", "ToOne");
		json.addProperty("qq", qq);
		json.addProperty("message", message);
		json.addProperty("from", from);
		json.addProperty("to", to);
		return json.toString();
	}
	//打包修改用户名消息
	public String getChangeNameMessage(String qq, String oldName, String newName) {
		JsonObject json = new JsonObject();
		json.addProperty("operation", "ChangeName");
		json.addProperty("qq", qq);
		json.addProperty("oldName", oldName);
		json.addProperty("newName", newName);
		return json.toString();
	}
	//打包发送用户名消息
	public String getSetNameMessage(String qq, String name) {
		JsonObject json = new JsonObject();
		json.addProperty("operation", "SetName");
		json.addProperty("qq", qq);
		json.addProperty("username", name);
		return json.toString();
	}
	//打包注册请求qq消息
	public String getQQMessage() {
		JsonObject json = new JsonObject();
		json.addProperty("operation", "GetQQ");
		return json.toString();
	}
	//打包注册消息
	public String getRegisterMessage(String qq, String password) {
		JsonObject json = new JsonObject();
		json.addProperty("operation", "Register");
		json.addProperty("qq", qq);
		json.addProperty("password", password);
		return json.toString();
	}
	//打包判断qq是否存在消息
	public String getCheckQQMessage(String qq) {
		JsonObject json = new JsonObject();
		json.addProperty("operation", "CheckQQ");
		json.addProperty("qq", qq);
		return json.toString();
	}
	//打包找回密码消息
	public String getFindPasswordMessage(String qq) {
		JsonObject json = new JsonObject();
		json.addProperty("operation", "FindPassword");
		json.addProperty("qq", qq);
		return json.toString();
	}
	//打包用户登录消息
	public String getLoginMessage(String qq, String password) {
		JsonObject json = new JsonObject();
		json.addProperty("operation", "Login");
		json.addProperty("qq", qq);
		json.addProperty("password", password);
		return json.toString();
	}
	//封装用户信息
	public String getUserInfo(String qq, String username) {
		return username+"("+qq+")";
	}
	//打包添加好友申请消息
	public String getAddFriendMessage(String qq_from, String qq_to, String username) {
		JsonObject json = new JsonObject();
		json.addProperty("operation", "AddFriend");
		json.addProperty("qs", qq_from);
		json.addProperty("qo", qq_to);
		json.addProperty("username", username);
		return json.toString();
	}
	//打包删除好友申请消息
	public String getDeleteFriendMessage(String qq_from, String qq_to, String username) {
		JsonObject json = new JsonObject();
		json.addProperty("operation", "DeleteFriend");
		json.addProperty("qs", qq_from);
		json.addProperty("qo", qq_to);
		json.addProperty("username", username);
		return json.toString();
	}
	//打包添加好友答复消息
	public String getAddFriendReplyMessage(String qs, String qq, String username,  boolean agree) {
		JsonObject json = new JsonObject();
		json.addProperty("operation", "Reply");
		json.addProperty("qs", qs);//要发给的用户的qq
		json.addProperty("qq", qq);//自己的qq
		json.addProperty("username", username);//自己的用户名,方便对方找到
		json.addProperty("agree", agree);
		return json.toString();
	}
	//封装界面回显的消息
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
