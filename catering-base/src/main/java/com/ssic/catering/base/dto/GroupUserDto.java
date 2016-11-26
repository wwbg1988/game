package com.ssic.catering.base.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.ssic.game.common.dto.ImUserDto;

import lombok.Getter;
import lombok.Setter;

public class GroupUserDto implements Serializable{
	@Getter
	@Setter
    private String id;
	@Getter
	@Setter
    private String infoId;
	@Getter
	@Setter
    private String userId;
	@Getter
	@Setter
    private Date createTime;
	@Getter
	@Setter
    private Date lastUpdateTime;
	@Getter
	@Setter
    private Integer stat;
	@Getter
	@Setter
	private String userAccount;
	@Getter
	@Setter
	private String qjyAccount;
	@Getter
	@Setter
	private List<ImUserDto> list;
	
}
