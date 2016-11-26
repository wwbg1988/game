package com.ssic.game.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class RoleMenuDto implements Serializable{
	    @Getter
	    @Setter
	    private String id;
	    @Getter
	    @Setter
	    private String roleId;
	    @Getter
	    @Setter
	    private String menuId;
	    @Getter
	    @Setter
	    private Integer stat;
	    @Getter
	    @Setter
	    private Date createTime;
	    @Getter
	    @Setter
	    private Date lastUpdateTime;
}
