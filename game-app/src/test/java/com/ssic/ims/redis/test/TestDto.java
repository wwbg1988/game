package com.ssic.ims.redis.test;

import java.io.Serializable;

import com.ssic.base.redis.RedisKeyPrefix;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@RedisKeyPrefix(prefixValue="test:TestDto:{userName}:{password}")
public class TestDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2480755996930439035L;

	@Getter
	@Setter
	private String userName;
	
	@Getter
	@Setter
	private String password;
	
	@Getter
	@Setter
	private Integer age;

}