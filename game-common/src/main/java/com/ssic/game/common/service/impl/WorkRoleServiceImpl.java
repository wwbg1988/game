/**
 * 
 */
package com.ssic.game.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.game.common.dao.CateringUserDao;
import com.ssic.game.common.service.WorkRoleService;

/**		
 * <p>Title: WorkRoleServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author yuanbin	
 * @date 2015年8月11日 下午1:25:18	
 * @version 1.0
 * <p>修改人：yuanbin</p>
 * <p>修改时间：2015年8月11日 下午1:25:18</p>
 * <p>修改备注：</p>
 */
@Service
public class WorkRoleServiceImpl implements WorkRoleService{
	
    @Autowired  
	private CateringUserDao cateringUserDao;

	public  List<String> findByUserId(String userId) {
		List<String> list= cateringUserDao.findRoleId(userId);
		return list;
	}
	

}

