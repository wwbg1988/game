/**
 * 
 */
package com.ssic.game.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ssic.game.common.dao.CateringUserDao;
import com.ssic.game.common.dto.DeptUsersDto;
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.pojo.ImsUsers;
import com.ssic.game.common.service.CateringUserService;
import com.ssic.game.common.service.IImsUserService;
import com.ssic.util.BeanUtils;

/**		
 * <p>Title: ImsUserServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author yuanbin	
 * @date 2015年8月7日 下午6:17:27	
 * @version 1.0
 * <p>修改人：yuanbin</p>
 * <p>修改时间：2015年8月7日 下午6:17:27</p>
 * <p>修改备注：</p>
 */
@Service("CateringUserServiceImpl")
public class CateringUserServiceImpl implements CateringUserService {
    
    @Autowired
    private CateringUserDao cateringUserDao;

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.common.service.IImsUserService#findByUserAccount(java.lang.String)   
    */
    @Override
    public ImsUsersDto findByUserId(String userId) {
	ImsUsers user = cateringUserDao.findByUserId(userId);
	ImsUsersDto imsUsersDto=new ImsUsersDto();
	BeanUtils.copyProperties(user, imsUsersDto);
	return imsUsersDto;
    }

	@Override
	public List<ImsUsersDto> findUsers(ImsUsersDto imsUsersDto) {
		// TODO Auto-generated method stub
		return cateringUserDao.findUsers(imsUsersDto);
	}

	@Override
	 @CacheEvict(value="default", key = "'game.common.dto.ImsUsersDto:' + #imsUsersDto.getId()", beforeInvocation=true)
	public void insertUser(ImsUsersDto imsUsersDto) {
		// TODO Auto-generated method stub
		cateringUserDao.insertUser(imsUsersDto);
	}

	@Override
	 @CacheEvict(value="default", key = "'game.common.dto.ImsUsersDto:' + #imsUsersDto.getId()", beforeInvocation=true)
	public void updateUser(ImsUsersDto imsUsersDto) {
		// TODO Auto-generated method stub
		cateringUserDao.updateUser(imsUsersDto);
	}
	
	@Override
	public void updateUserByuserId(ImsUsersDto imsUsersDto) {
		// TODO Auto-generated method stub
		cateringUserDao.updateUserByuserId(imsUsersDto);
	}

	@Override
	 @CacheEvict(value="default", key = "'game.common.dto.ImsUsersDto:' + #imsUsersDto.getId()", beforeInvocation=true)
	public void deleteUser(ImsUsersDto imsUsersDto) {
		// TODO Auto-generated method stub
		cateringUserDao.deleteUser(imsUsersDto);
	}

	@Override
	public List<ImsUsersDto> findUsersAll(ImsUsersDto imsUsersDto,
			PageHelperDto ph) {
		// TODO Auto-generated method stub	
		
		return cateringUserDao.findUsersAll(imsUsersDto, ph);
	}

	@Override
	public int findCount(ImsUsersDto imsUsersDto) {
		
		return cateringUserDao.findCount(imsUsersDto);
	}

	@Override
	public int vailUserAccount(ImsUsersDto imsUsersDto) {
		// TODO Auto-generated method stub
		return cateringUserDao.vailUserAccount(imsUsersDto);
	}
	
	public String findUserRole(String userId) {
		 List<String> list=  cateringUserDao.findRoleId(userId);
		if(list!=null&&list.size()>0){
			String result="";
			for(int i=0;i<list.size();i++){
				if(i==list.size()-1){
					result+=list.get(i);
				}else{
					result+=list.get(i)+",";
				}
			}
			return result;
		}
		return null;
	}

	@Override
	public void grant(String ids, ImsUsersDto user) {
		
		if (ids != null && ids.length() > 0) {
			List<String> roles = new ArrayList<String>();
			if (user.getRoleIds() != null) {
				for (String roleId : user.getRoleIds().split(",")) {
					roles.add(roleId);
				}
			}
			//t_ims_role_user  
			//先根据userID删除所有的角色，再把勾选的角色全部插入
			cateringUserDao.deleteRole(ids);
			cateringUserDao.insertRole(roles, ids);	
	   }
	}

	@Override
	public void upPasswod(ImsUsersDto imsUsersDto) {
		cateringUserDao.upPasswod(imsUsersDto);
	}

	@Override
	public void deleteproUser(ImsUsersDto imsUsersDto) {
		cateringUserDao.deleteproUser(imsUsersDto);
	}

	@Override
	public void updateDept(DeptUsersDto deptUsersDto) {
		// TODO Auto-generated method stub
		cateringUserDao.updateDept(deptUsersDto);
	}

	@Override
	public void deleteDept(DeptUsersDto deptUsersDto) {
		// TODO Auto-generated method stub
		cateringUserDao.deleteDept(deptUsersDto);
	}
	
	/**
	 * 
	 * (non-Javadoc)   
	 * @see com.ssic.game.common.service.CateringUserService#updateUserById(com.ssic.game.common.dto.ImsUsersDto)
	 */
	@Override
	public void updateUserSelectiveById(ImsUsersDto imsUsersDto)
	{
	    cateringUserDao.updateUserSelectiveById(imsUsersDto);	    
	}

	
	 /** 
	 * (non-Javadoc)   
	 * @see com.ssic.game.common.service.CateringUserService#userAccountByUser(java.lang.String)  
	 * 
	 *  @author yuanbin
	 */
	@Override
	public ImsUsersDto userAccountByUser(String userAccount) {
		return cateringUserDao.userAccountByUser(userAccount);
	}
}

