package com.ssic.catering.base.service;

import java.util.List;

import com.ssic.catering.base.dto.NotifyDto;
import com.ssic.catering.base.pojo.PageHelper;

public interface INotifyService {

	List<NotifyDto> findBy(NotifyDto notifyDto);
	
	List<NotifyDto> findBy(NotifyDto notifyDto,PageHelper ph);
	
	int findCount(NotifyDto notifyDto);
	
	void insertNotify(NotifyDto notifyDto);
	
	void updateNotify(NotifyDto notifyDto);
	
	void deleteNotify(NotifyDto notifyDto);
	
	NotifyDto findById(String id);
	
	List<NotifyDto> findBy(NotifyDto notifyDto,int beginRow, int endRow,String searchDate);
	
	/**
	 * 查询符合条件的结果集	 
	 * @author 朱振	
	 * @date 2015年10月27日 下午1:50:59	
	 * @version 1.0
	 * @param notifyDto  公告条件
	 * @param projectIds 项目id列表
	 * @return
	 * <p>修改人：朱振</p>
	 * <p>修改时间：2015年10月27日 下午1:50:59</p>
	 * <p>修改备注：</p>
	 */
	List<NotifyDto> findBy(NotifyDto notifyDto,List<String> projectIds,PageHelper ph);
	
	
	/**
     * 获取符合条件的结果集的总条数
     * @author 朱振   
     * @date 2015年10月27日 下午1:50:59  
     * @version 1.0
     * @param notifyDto 公告条件
     * @param projectIds 项目id列表
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年10月27日 下午1:50:59</p>
     * <p>修改备注：</p>
     */
    Integer findCountBy(NotifyDto notifyDto,List<String> projectIds);
}
