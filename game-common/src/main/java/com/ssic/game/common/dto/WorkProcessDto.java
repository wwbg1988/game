/**
 * 
 */
package com.ssic.game.common.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: WorkProcessDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author yuanbin	
 * @date 2015年8月21日 上午11:18:55	
 * @version 1.0
 * <p>修改人：yuanbin</p>
 * <p>修改时间：2015年8月21日 上午11:18:55</p>
 * <p>修改备注：</p>
 */
public class WorkProcessDto implements Serializable{
	
	@Getter
    @Setter
    private List<WorksDto> workProcessList;//請假、報銷、出差 相應參數List
	
	@Getter
    @Setter
    private List<WorksDto> workDoList;//4个权限菜单相应参数

}

