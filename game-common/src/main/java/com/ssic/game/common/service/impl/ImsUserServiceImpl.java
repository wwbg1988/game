/**
 * 
 */
package com.ssic.game.common.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssic.game.common.dao.DeptUserDao;
import com.ssic.game.common.dao.ImsUserDao;
import com.ssic.game.common.dto.DeptUsersDto;
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.dto.LTUserDto;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.pojo.ImsUsers;
import com.ssic.game.common.service.IImsUserService;
import com.ssic.game.common.service.ILiaoTianService;
import com.ssic.game.common.weixinUtil.MD5Util;
import com.ssic.util.BeanUtils;

/**		
 * <p>Title: ImsUserServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author rkzhang	
 * @date 2015年6月30日 下午1:32:27	
 * @version 1.0
 * <p>修改人：rkzhang</p>
 * <p>修改时间：2015年6月30日 下午1:32:27</p>
 * <p>修改备注：</p>
 */
@Service("ImsUserServiceImpl")
public class ImsUserServiceImpl implements IImsUserService {
    
    private static final Logger log = Logger.getLogger(ImsUserServiceImpl.class);
    
    @Autowired
    private ImsUserDao imsUserDao;
    @Autowired
    private ILiaoTianService liaoTianService;
    @Autowired
    private DeptUserDao deptUserDao;

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.common.service.IImsUserService#findByUserAccount(java.lang.String)   
    */
    @Override
    //@Cacheable(value="default", key="'game.common.dto.ImsUsersDto:' + #userId")
    public ImsUsersDto findByUserId(String userId) {
	ImsUsers user = imsUserDao.findByUserId(userId);
	if(user == null) {
	    return null;
	}
	ImsUsersDto aaDto =  BeanUtils.createBeanByTarget(user, ImsUsersDto.class);
	aaDto.setIsAdmin(user.getIsadmin());
	return aaDto;
    }

	@Override
	public List<ImsUsersDto> findUsers(ImsUsersDto imsUsersDto) {
		// TODO Auto-generated method stub
		return imsUserDao.findUsers(imsUsersDto);
	}

	@Override
	public void insertUser(ImsUsersDto imsUsersDto) {
		// TODO Auto-generated method stub
		imsUserDao.insertUser(imsUsersDto);
	}

	@Override
	 @CacheEvict(value="default", key = "'game.common.dto.ImsUsersDto:' + #imsUsersDto.getId()", beforeInvocation=true)
	public void updateUser(ImsUsersDto imsUsersDto) {
		// TODO Auto-generated method stub
		imsUserDao.updateUser(imsUsersDto);
	}

	@Override
	 @CacheEvict(value="default", key = "'game.common.dto.ImsUsersDto:' + #imsUsersDto.getId()", beforeInvocation=true)
	public void deleteUser(ImsUsersDto imsUsersDto) {
		// TODO Auto-generated method stub
		imsUserDao.deleteUser(imsUsersDto);
	}

	@Override
	public List<ImsUsersDto> findUsersAll(ImsUsersDto imsUsersDto,
			PageHelperDto ph) {
		// TODO Auto-generated method stub	
		
		return imsUserDao.findUsersAll(imsUsersDto, ph);
	}

	@Override
	public int findCount(ImsUsersDto imsUsersDto) {
		
		return imsUserDao.findCount(imsUsersDto);
	}

	@Override
	public int vailUserAccount(ImsUsersDto imsUsersDto) {
		// TODO Auto-generated method stub
		return imsUserDao.vailUserAccount(imsUsersDto);
	}
	
	public String findUserRole(String userId) {
		 List<String> list=  imsUserDao.findRoleId(userId);
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
			imsUserDao.deleteRole(ids);
			imsUserDao.insertRole(roles, ids);	
	   }
	}

	@Override
	public void upPasswod(ImsUsersDto imsUsersDto) {
		imsUserDao.upPasswod(imsUsersDto);
	}

	@Override
	public void deleteproUser(ImsUsersDto imsUsersDto) {
		imsUserDao.deleteproUser(imsUsersDto);
	}

	@Override
	public void updateDept(DeptUsersDto deptUsersDto) {
		// TODO Auto-generated method stub
		imsUserDao.updateDept(deptUsersDto);
	}

	@Override
	public void deleteDept(DeptUsersDto deptUsersDto) {
		// TODO Auto-generated method stub
		imsUserDao.deleteDept(deptUsersDto);
	}

	@Override
	public ImsUsers findImsUsersToWeixin(String openId) {
		ImsUsers imsUsers=imsUserDao.findImsUsersToWeixin(openId);
		return imsUsers;
	}

	@Override
	public ImsUsers findImsUserByUserAccount(String userAccount) {
		return imsUserDao.findImsUserByUserAccount(userAccount);
	}

	@Override
	public Integer updateImsUsers(ImsUsers imsUsers) {
		
		return imsUserDao.updateImsUsers(imsUsers);
	}
	
	/**
	 * 
	 * (non-Javadoc)   
	 * @see com.ssic.game.common.service.IImsUserService#updateImsUsersBy(com.ssic.game.common.dto.ImsUsersDto)
	 */
	@Override
	public Integer updateImsUsersBy(ImsUsersDto imsUsers)
	{
	    if(imsUsers == null)
	    {
	        return 0;
	    }
	    
	    ImsUsers param = new ImsUsers();
	    BeanUtils.copyProperties(imsUsers, param);
	  
	    return imsUserDao.updateImsUsers(param);
	}
	
	/**
	 * 
	 * (non-Javadoc)   
	 * @see com.ssic.game.common.service.IImsUserService#findBy(com.ssic.game.common.dto.ImsUsersDto)
	 */
	@Override
	public List<ImsUsersDto> findBy(ImsUsersDto imsUsers)
	{
	   if(imsUsers == null)
	   {
	       log.error("该方法不支持无参");
	       return null;
	   }
	   
	   ImsUsers param = new ImsUsers();
	   BeanUtils.copyProperties(imsUsers, param);
	   
	   List<ImsUsers> resultSet = imsUserDao.findBy(param);
	   if(!CollectionUtils.isEmpty(resultSet))
	   {
	       return BeanUtils.createBeanListByTarget(resultSet, ImsUsersDto.class);
	   }
	   
	   return null;
	}

	@Override
	public List<ImsUsersDto> findByProjId(String projId) {
		// TODO Auto-generated method stub
		return imsUserDao.findByProjId(projId);
	}

	@Override
	@Transactional
	public void createUser(ImsUsersDto user) {
		// TODO Auto-generated method stub
        user.setCreatedatetime(new Date());
        user.setStat(1);
        user.setPassword(user.getPassword());
        user.setQjyAccount("qjy_" + user.getUserAccount());
        user.setMobilePhone(user.getPhone());
        //插入t_ims_users
        insertUser(user);
      
	}

	@Override
	@Transactional
	public void delAllUser(ImsUsersDto imsUsers) {
		// TODO Auto-generated method stub
		ImsUsers imsUsers2 = imsUserDao.findByUserId(imsUsers.getId());
		imsUserDao.deleteUser(imsUsers);
	    DeptUsersDto deptUsersDto = new DeptUsersDto();
	    deptUsersDto.setUserId(imsUsers.getId());
	    deptUserDao.deleteDeptUser(deptUsersDto);
	    if(imsUsers!=null){
	    	 //删除IM聊天
		    LTUserDto lTUserDto = new LTUserDto();
	        lTUserDto.setUserAccount(imsUsers2.getUserAccount());
	        liaoTianService.delLTUser(lTUserDto);
	    }
	   
	}

	@Override
	public List<ImsUsersDto> findBySearchName(ImsUsersDto imsUsers) {
		// TODO Auto-generated method stub
		return imsUserDao.findBySearchName(imsUsers);
	}
}

