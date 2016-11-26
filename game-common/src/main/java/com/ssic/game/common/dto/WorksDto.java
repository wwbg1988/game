/**
 * 
 */
package com.ssic.game.common.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: WorkDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author yuanbin	
 * @date 2015年8月8日 上午11:20:52	
 * @version 1.0
 * <p>修改人：yuanbin</p>
 * <p>修改时间：2015年8月8日 上午11:20:52</p>
 * <p>修改备注：</p>
 */
public class WorksDto implements Serializable{
	
	@Getter
    @Setter
    private String processId;//流程Id；权限控制接口：1.今日评价  2.历史评价  3.预警  4.菜单
	
	@Getter
	@Setter
	private String procName;//流程名称
	
	@Getter
	@Setter
	private String imageUrl;//流程图片
	
    @Getter
    @Setter
    private String procType;//1.请假    2.报销   3.出差  
	
	@Getter
	@Setter
	private String url;//流程图片,权限控制后4个图标
	
	@Getter
	@Setter
	private String userId;
	
	@Getter
	@Setter
	private String remark;//页面图标名字
	
	@Getter
	@Setter
	private String projectId;
	
	@Getter
    @Setter
    private String formId;//表单id(请假 出差 报销用)
	
	@Getter
    @Setter
    private String actionId;//动作id(请假 出差 报销用)
	
}

