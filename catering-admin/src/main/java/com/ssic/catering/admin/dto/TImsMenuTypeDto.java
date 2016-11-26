package com.ssic.catering.admin.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class TImsMenuTypeDto  implements Serializable{

	
     /**   
     * serialVersionUID: （一句话描述这个变量表示什么）      
     */   
    
    private static final long serialVersionUID = 1L;
    @Getter
	@Setter
	private String id;
	@Getter
	@Setter
	private String name;
}
