/**
 * 
 */
package com.ssic.game.common.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.game.common.dao.DeptUserDao;
import com.ssic.game.common.dto.DeptUsersDto;
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.pojo.DeptUsers;
import com.ssic.game.common.service.DeptUserService;
import com.ssic.util.BeanUtils;

/**		
 * <p>Title: DeptUserServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年7月9日 下午7:51:45	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年7月9日 下午7:51:45</p>
 * <p>修改备注：</p>
 */
@Service
public class DeptUserServiceImpl implements DeptUserService
{
    
    @Autowired
    private DeptUserDao deptUserDao;

    
     /** 
     * (non-Javadoc)   
     * @see com.ssic.game.common.service.DeptUserService#findAllBy(com.ssic.game.common.dto.DeptUsersDto)   
     */
    @Override
    public List<DeptUsersDto> findAllBy(DeptUsersDto deptUserDto)
    {
        DeptUsers param = new DeptUsers();
        BeanUtils.copyProperties(deptUserDto, param);
        List<DeptUsers> list = deptUserDao.findAllBy(param);
        if(list!=null&&list.size()>0){
            return BeanUtils.createBeanListByTarget(list, DeptUsersDto.class);
        }
        return null;
    }


    
     /** 
     * (non-Javadoc)   
     * @see com.ssic.game.common.service.DeptUserService#findAllBySearch(java.lang.String, java.lang.String)   
     */
    @Override
    public List<Map<Object, Object>> findAllBySearch(String searchName, String projId)
    {
        return deptUserDao.findBySearchName(searchName, projId);
    }



    
     /** 
     * (non-Javadoc)   
     * @see com.ssic.game.common.service.DeptUserService#findAllNotExistUsersIds(java.lang.String, java.util.List)   
     */
    @Override
    public List<DeptUsersDto> findAllNotExistUsersIds(String projId, List<String> usersIds)
    {
        if(deptUserDao.findAllNotExists(projId, usersIds)!=null){
            return BeanUtils.createBeanListByTarget(deptUserDao.findAllNotExists(projId, usersIds), DeptUsersDto.class);
        }
        return null;
    }



    
     /** 
     * (non-Javadoc)   
     * @see com.ssic.game.common.service.DeptUserService#insert(com.ssic.game.common.dto.DeptUsersDto)   
     */
    @Override
    public void insert(DeptUsersDto deptUserDto)
    {
        if(deptUserDto!=null){
            DeptUsers deptUsers= new DeptUsers();
            BeanUtils.copyProperties(deptUserDto, deptUsers);
            deptUsers.setStat(1);
            deptUsers.setCreateTime(new Date());
            deptUsers.setLastUpdateTime(new Date());
            deptUserDao.insertSelective(deptUsers);
        }
    }
    
    
	@Override
	public List<DeptUsersDto> findDeptUser(DeptUsersDto deptUserDto) {
		return deptUserDao.findDeptUser(deptUserDto);
	}

	@Override
	public void insertDeptUser(DeptUsersDto deptUserDto) {
		deptUserDao.insertDeptUser(deptUserDto);
	}


	@Override
	public void deleteDeptUser(DeptUsersDto deptUserDto) {
		deptUserDao.deleteDeptUser(deptUserDto);
	}



	@Override
	public List<ImsUsersDto> searchUser(String deptId, String userName) {
		// TODO Auto-generated method stub
		userName="%"+userName+"%";
		return deptUserDao.searchUser(deptId,userName);
	}

}

