package com.ssic.catering.base.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.ssic.game.common.dto.ImUserDto;
import com.ssic.game.common.dto.ImsUsersDto;

import lombok.Getter;
import lombok.Setter;

public class GroupInfoDto implements Serializable{
        @Getter
        @Setter
	    private String id;
        @Getter
        @Setter
	    private String groupId;
        @Getter
        @Setter
	    private String groupName;
        @Getter
        @Setter
	    private String groupInfo;
        @Getter
        @Setter
	    private String groupHead;
        @Getter
        @Setter
	    private Date createTime;
        @Getter
        @Setter
	    private Date lastUpdateTime;
        @Getter
        @Setter
	    private String createuserid;
        @Getter
        @Setter
	    private Integer stat;
        @Getter
        @Setter
	    private String groupUserId;
        @Getter
        @Setter
	    private String parentId;
        @Getter
        @Setter
        private int isfrist;  // 1 显示最上级   0 不显示最上级
        @Getter
    	@Setter
    	private String users;
        @Getter
        @Setter
        private String createusername;
        @Getter
        @Setter
        private String createuserImage;
        @Getter
        @Setter
        private String pid;    //上级群ID
        @Getter
        @Setter
        private String projId;
        @Getter
        @Setter
        private List<ImUserDto> list;
}
