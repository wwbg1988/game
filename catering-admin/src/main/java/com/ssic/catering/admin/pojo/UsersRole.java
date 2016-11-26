package com.ssic.catering.admin.pojo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class UsersRole implements Serializable{
	    @Getter
	    @Setter
		private String user_id;
	    @Getter
	    @Setter
	    private String role_id;
	    @Getter
	    @Setter
	    private Integer stat;
	    @Getter
	    @Setter
	    private String id;
}
