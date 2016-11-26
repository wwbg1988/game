package com.ssic.game.common.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class QJHistoryMDto implements Serializable{
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
}
