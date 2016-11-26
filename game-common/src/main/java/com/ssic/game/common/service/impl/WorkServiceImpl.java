/**
 * 
 */
package com.ssic.game.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.game.common.dao.ProcessDao;
import com.ssic.game.common.dto.ProcessDto;
import com.ssic.game.common.dto.WorksDto;
import com.ssic.game.common.pojo.Process;
import com.ssic.game.common.service.WorkService;
import com.ssic.util.BeanUtils;


/**		
 * <p>Title: WorkServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author yuanbin	
 * @date 2015年8月11日 上午9:05:53	
 * @version 1.0
 * <p>修改人：yuanbin</p>
 * <p>修改时间：2015年8月11日 上午9:05:53</p>
 * <p>修改备注：</p>
 */
@Service
public class WorkServiceImpl implements WorkService {

	@Autowired
	private ProcessDao processDao;
	
    @Override
	public List<WorksDto> findProcess(ProcessDto processDto) {
	 
	    //遍历只返回一个对应的对象
		List<WorksDto> worklist=new ArrayList<WorksDto>();
		WorksDto work =new WorksDto();
		List<ProcessDto> list_p = processDao.findProcess(processDto);
		for(ProcessDto process:list_p){
			String pro=process.getProjId();
			if(pro != null && pro.equals(processDto.getProjId())){	
				work.setProcessId(process.getId());
				work.setProcName(process.getProcName());
				work.setImageUrl(process.getImageUrl());
			}
		}
		return worklist;
	}
    
	public ProcessDto findById(String procId){
    	
    	Process process =processDao.findById(procId);
    	if(process==null){
    		return null;
    	}
    	ProcessDto processDto=new ProcessDto();
    	BeanUtils.copyProperties( process,processDto);
	    
	    return processDto; 
	}
}

