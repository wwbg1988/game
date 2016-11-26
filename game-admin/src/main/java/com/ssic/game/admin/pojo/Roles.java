package com.ssic.game.admin.pojo;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class Roles implements Serializable{

	@Getter
    @Setter
	 private String id;
    @Getter
    @Setter
    private Date create_time;
    @Getter
    @Setter
    private String role_name;
    @Getter
    @Setter
    private Integer no;
    @Getter
    @Setter
    private String pj_no;
    @Getter
    @Setter
    private String post_no;
    @Getter
    @Setter
    private String remark;
    @Getter
    @Setter
    private Integer seq;
    @Getter
    @Setter
    private String pid;
    @Getter
    @Setter
    private Integer stat;
    
}
