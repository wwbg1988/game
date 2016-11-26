/**
 * 
 */
package com.ssic.game.ims.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**		
 * <p>Title: TaskData </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author rkzhang	
 * @date 2015年6月25日 上午9:05:02	
 * @version 1.0
 * <p>修改人：rkzhang</p>
 * <p>修改时间：2015年6月25日 上午9:05:02</p>
 * <p>修改备注：</p>
 */
@ToString
public class CountSignUser {

	
	 /**   
	 * id: 会签人员ID
	 */   
	@Getter
	@Setter
	private String userId;
	
	
	 /**   
	 * name: 会签人员名称 
	 */   
	@Getter
	@Setter
	private String userName;
	
	
	 /**   
	 * projId: 会签人员是否提交
	 */   
	@Getter
	@Setter
	private boolean isSubmit;
	
    	
}

