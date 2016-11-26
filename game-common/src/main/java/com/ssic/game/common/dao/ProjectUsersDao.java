package com.ssic.game.common.dao;

import java.util.List;
import java.util.Map;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.game.common.dto.ProjectDto;
import com.ssic.game.common.dto.ProjectUsersDto;
import com.ssic.game.common.mapper.ProjectUsersEXMapper;
import com.ssic.game.common.mapper.ProjectUsersMapper;
import com.ssic.game.common.pojo.Project;
import com.ssic.game.common.pojo.ProjectUsers;
import com.ssic.game.common.pojo.ProjectUsersExample;
import com.ssic.game.common.pojo.ProjectUsersExample.Criteria;
import com.ssic.util.BeanUtils;
import com.ssic.util.base.MyBatisBaseDao;

@Repository
public class ProjectUsersDao extends MyBatisBaseDao<ProjectUsers>{

	
	
    @Autowired
	private ProjectUsersEXMapper projectUsersEXMapper;
    
    @Autowired
    @Getter
    private ProjectUsersMapper mapper;
	
	public List<ProjectUsersDto> findAll(ProjectUsersDto projectUsersDto){
    	
    	List<ProjectUsersDto>  list = projectUsersEXMapper.findAll(projectUsersDto);
    	
    	return list;
    	
    }
	public List<Map<Object,Object>> findAllUsersBySearchName(String projId,String searchName){
	    return projectUsersEXMapper.findAllUserRole(projId, searchName);
	}
	public List<Map<Object,Object>> findAllNotExistsUserIdBySearchName(String projId,String searchName,String userId){
	    return projectUsersEXMapper.findAllUserNotExist(projId, searchName, userId);
	}
	public List<ProjectUsers> findAllNotExistUserId(String projId,List<String> userIds){
	    ProjectUsersExample example = new ProjectUsersExample();
	    Criteria criteria = example.createCriteria();
	    criteria.andProjIdEqualTo(projId);
	    if(userIds!=null&&userIds.size()>0){
	        criteria.andUserIdNotIn(userIds);
	    }
	    criteria.andStatEqualTo(1);
	    example.setOrderByClause("  dept_id");
	    return mapper.selectByExample(example);
	}
	
	public void insertPUser(ProjectUsersDto projectUsersDto){
		projectUsersEXMapper.insertPUser(projectUsersDto);
	};
	
	public void updatePUser(ProjectUsersDto projectUsersDto){
		projectUsersEXMapper.updatePUser(projectUsersDto);
	};
	
	public void deletePUser(ProjectUsersDto projectUsersDto){
		projectUsersEXMapper.deletePUser(projectUsersDto);
	};
	
	public void deleteByUserId(ProjectUsersDto projectUsersDto){
	    ProjectUsersExample example = new ProjectUsersExample();
	    Criteria criteria = example.createCriteria();
	    if(!StringUtils.isEmpty(projectUsersDto.getUserId())){
	    	criteria.andUserIdEqualTo(projectUsersDto.getUserId());
	    }
	    mapper.deleteByExample(example);
	    	    
	}
	
	public void deleteByProjid(ProjectUsersDto projectUsersDto){
		projectUsersEXMapper.deleteByProjid(projectUsersDto);
	}
	public List<Map<Object,Object>>getUserRoleBySearchName(String projId,String searhName){
	    return projectUsersEXMapper.findAllUserRole(projId, searhName);
	}
	
	public List<ProjectUsersDto> findDept(ProjectUsersDto projectUsersDto){
		return  projectUsersEXMapper.findDept(projectUsersDto);
	}
	public List<ProjectUsersDto> findUsers(ProjectUsersDto projectUsersDto){
		return projectUsersEXMapper.findUsers(projectUsersDto);
	}
	public List<ProjectUsersDto> findProDept(ProjectUsersDto projectUsersDto){
		return projectUsersEXMapper.findProDept(projectUsersDto);
	}
	
	/**
	 * 通过ims_user的id查询出他所属的项目	 
	 * @author 朱振	
	 * @date 2015年10月29日 上午11:43:49	
	 * @version 1.0
	 * @param userId
	 * @return
	 * <p>修改人：朱振</p>
	 * <p>修改时间：2015年10月29日 上午11:43:49</p>
	 * <p>修改备注：</p>
	 */
	public ProjectDto findProjectsByIMSUserId(String userId)
	{
	    Project resultSet = projectUsersEXMapper.findProjectByIMSUserId(userId);
	    if(null != resultSet)
	    {
	        ProjectDto result = new ProjectDto();
	        BeanUtils.copyProperties(resultSet, result);
	        return result;
	    }
	    
	    return null;
	}
}
