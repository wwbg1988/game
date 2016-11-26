/**
 * 
 */
package com.ssic.game.common.dao;

import java.util.List;
import java.util.Map;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.game.common.dto.DeptUsersDto;
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.mapper.DeptUsersExMapper;
import com.ssic.game.common.mapper.DeptUsersMapper;
import com.ssic.game.common.pojo.DeptUsers;
import com.ssic.game.common.pojo.DeptUsersExample;
import com.ssic.game.common.pojo.DeptUsersExample.Criteria;
import com.ssic.util.base.MyBatisBaseDao;
import com.ssic.util.constants.DataStatus;

/**		
 * <p>Title: DeptUserDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年7月9日 下午7:56:13	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年7月9日 下午7:56:13</p>
 * <p>修改备注：</p>
 */
@Repository
public class DeptUserDao extends MyBatisBaseDao<DeptUsers>
{
    @Autowired
    @Getter
    private DeptUsersMapper mapper;
    @Autowired
    @Getter
    private DeptUsersExMapper exMapper;
    
    public List<DeptUsers> findAllBy(DeptUsers deptUsers){
        DeptUsersExample example = new DeptUsersExample();
        Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(deptUsers.getProjId())){
            criteria.andProjIdEqualTo(deptUsers.getProjId());
        }
        if(!StringUtils.isEmpty(deptUsers.getUserId())){
            criteria.andUserIdEqualTo(deptUsers.getUserId());
        }
        if(!StringUtils.isEmpty(deptUsers.getDeptId())){
            criteria.andDeptIdEqualTo(deptUsers.getDeptId());
        }
        if(!StringUtils.isEmpty(deptUsers.getIsAdmin())){
            criteria.andIsAdminEqualTo(deptUsers.getIsAdmin());
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        example.setOrderByClause(" dept_id");
        return mapper.selectByExample(example);
    }
    public List<DeptUsers> findAllNotExists(String projId,List<String>usersIds){
        DeptUsersExample example = new DeptUsersExample();
        Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(projId)){
            criteria.andProjIdEqualTo(projId);
        }
        if(usersIds!=null&&usersIds.size()>0){
            criteria.andUserIdNotIn(usersIds);
        }
        criteria.andStatEqualTo(1);
        example.setOrderByClause(" dept_id");
        return mapper.selectByExample(example);
    }
    
    public List<Map<Object,Object>>findBySearchName(String searchName,String projId){
        String param = "%"+searchName+"%";
        return exMapper.findAllDeptUsr(param, projId);
    }
    public List<Map<Object,Object>>findAllByNotExist(String searchName,String userId,String projId){
        return exMapper.findAllUserNotExist(projId, searchName, userId);
    }
    
    public List<DeptUsersDto> findDeptUser(DeptUsersDto deptUsersDto){
    	return exMapper.findDeptUser(deptUsersDto);
    }
    
    public void insertDeptUser(DeptUsersDto deptUsersDto){
    	exMapper.insertDeptUser(deptUsersDto);
    }
    
    public void deleteDeptUser(DeptUsersDto deptUsersDto){
    	exMapper.deleteDeptUser(deptUsersDto);
    }
    
    public List<DeptUsersDto> findDept(DeptUsersDto deptUsersDto){
    	return  exMapper.findDept(deptUsersDto);
    }
    
    public List<DeptUsersDto> findUser(DeptUsersDto deptUsersDto){
    	return exMapper.findUser(deptUsersDto);
    }

    public List<ImsUsersDto> searchUser(String deptId,String userName){
    	return exMapper.searchUser(deptId,userName);
    }
    
}

