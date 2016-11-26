/**
 * 
 */
package com.ssic.game.ims.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**		
 * <p>Title: FormRequest </p>
 * <p>Description: 请求获取表单信息的包装类</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author rkzhang	
 * @date 2015年6月25日 上午11:07:35	
 * @version 1.0
 * <p>修改人：rkzhang</p>
 * <p>修改时间：2015年6月25日 上午11:07:35</p>
 * <p>修改备注：</p>
 */
@ToString
public class AchieveFormRequest {
    
    	@Getter
	@Setter
    	private String projectId;
    
    	@Getter
	@Setter
    	private String userId;

    	@Getter
	@Setter
	private String procInstanceId;
    	
    	@Getter
	@Setter
    	private String taskId;
    	
    	
}

