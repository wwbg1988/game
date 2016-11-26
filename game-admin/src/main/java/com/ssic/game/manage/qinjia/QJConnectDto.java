package com.ssic.game.manage.qinjia;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class QJConnectDto implements Serializable{
      
	@Getter
	@Setter
	   private String email;  // 开发者账号，必选项
	@Getter
	@Setter
	   private String devpwd;  // 开发者密码，必选项
	@Getter
	@Setter
	   private String appkey;  // 应用的appkey，必选项
	@Getter
	@Setter
	   private String from;   // 发送方账号，必选项，该账号必须存在
	@Getter
	@Setter
	   private String toType;  // 发送对象类型，必选项。0发往用户，1发往聊天室，2发往群
	@Getter
	@Setter
	   private List<String> toIdList;   // 给发送的id,50个以内为佳，最多不要超过100个
	@Getter
	@Setter
	   private String toIds;    // 发送ID的字符串，以逗号隔开
	@Getter
	@Setter
	   private String save;   // 此条数据是否保存（离线消息和历史记录），必选项。0不保存；1保存
	@Getter
	@Setter
	   private String msgType;   // 发送消息类型，必选项。0文本
	@Getter
	@Setter
	   private String text;    // 发送内容
	@Getter
	@Setter
	private List<Object>  userList;    //需要导入的用户
	@Getter
	@Setter
	private String userAccount;   //添加好友被操作的账号
	@Getter
	@Setter
	private String friendAccount;    //添加为好友的账号
	//获取用户聊天记录
	@Getter
	@Setter
	private String extra_data;    //经过base64处理过的消息内的附加信息
	@Getter
	@Setter
	private String msg_content;   //消息内容，经过base64处理过的内容。
	@Getter
	@Setter
	private String msg_id;    //消息的全局唯一ID
	@Getter
	@Setter
	private String msg_type ;   //消息类型，0文本，2小图片，3大图片，4语音
	@Getter
	@Setter
	private String receiver_id;  //单聊为接收方账号，聊天室和群聊为ID
	@Getter
	@Setter
	private String receiver_type ;  //接收者类型
	@Getter
	@Setter
	private String sender_account;  //发送者账号
	@Getter
	@Setter
	private String sender_date;   //发送时间，以秒为单位的时间戳
	@Getter
	@Setter
	private String file;  //图片解析为base64的字符串 
}
