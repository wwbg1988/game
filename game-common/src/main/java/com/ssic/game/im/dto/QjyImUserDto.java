package com.ssic.game.im.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class QjyImUserDto implements Serializable{

	@Getter
	@Setter
	   private String email;  // 开发者账号，必选项
	@Getter
	@Setter
	   private String devpwd;  // 开发者密码，必选项
	@Getter
	@Setter
	   private String nickname;  //昵称
	@Getter
	@Setter
	   private String pwd;   //密码
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
	//群管理字段
	@Getter
	@Setter
	private String ownerAccount;   //所有者账号，必选项
	@Getter
	@Setter
	private String groupName;    //群名称
	@Getter
	@Setter
	private String groupInfo;    //群扩展信息，可选项
	@Getter
	@Setter
	private String groupId;   //群ID
	@Getter
	@Setter
	private String groupHead;   //群头像
	
	
	
}
