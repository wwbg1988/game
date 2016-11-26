/**
 * 
 */
package com.ssic.game.common.service;

import java.util.List;

import com.ssic.game.common.dto.ProcessDto;
import com.ssic.game.common.dto.WorksDto;
import com.ssic.game.common.pojo.Process;

/**		
 * <p>Title: WorkService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author yuanbin	
 * @date 2015年8月11日 上午8:56:19	
 * @version 1.0
 * <p>修改人：yuanbin</p>
 * <p>修改时间：2015年8月11日 上午8:56:19</p>
 * <p>修改备注：</p>
 */
public interface WorkService {  
	
	 List<WorksDto> findProcess (ProcessDto processDto);
	 
	 ProcessDto findById(String procId);

}

