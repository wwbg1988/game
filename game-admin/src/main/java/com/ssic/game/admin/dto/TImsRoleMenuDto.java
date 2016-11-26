package com.ssic.game.admin.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class TImsRoleMenuDto  implements Serializable {
    
	
     /**   
     * serialVersionUID: （一句话描述这个变量表示什么）      
     */   
    
    private static final long serialVersionUID = 1L;
    @Getter
	@Setter
	private String id;
	@Getter
	@Setter
	private String role_id;
	@Getter
	@Setter
	private String menu_id;
	@Getter
	@Setter
	private Integer stat;
}
