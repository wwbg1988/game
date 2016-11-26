package com.ssic.game.app.controller.weixin;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class WeixinCode {

	/**
	 * 请求类型,1为菜品投票,2为食堂评论
	 */
	@Getter
	@Setter
	private String type;

	/**
	 * 食堂ID
	 */
	@Getter
	@Setter
	private String cafetoriumId;


	@Getter
	@Setter
	private String access_token;
	@Getter
	@Setter
	private String expires_in;
	@Getter
	@Setter
	private String refresh_token;
	@Getter
	@Setter
	private String openid;

}
