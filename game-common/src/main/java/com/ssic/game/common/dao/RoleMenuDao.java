package com.ssic.game.common.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.game.common.mapper.RoleMenuMapper;
import com.ssic.game.common.pojo.RoleMenu;
import com.ssic.game.common.pojo.RoleMenuExample;
import com.ssic.game.common.pojo.RoleMenuExample.Criteria;



@Repository
public class RoleMenuDao {

	@Autowired
	private RoleMenuMapper mapper;
	
	
	/**
	 * 
	 * findBy：根据 roleid, menid等条件返回结果
	 * @param param
	 * @return
	 * @exception	
	 * @author 朱振
	 * @date 2015年8月21日 上午9:08:45
	 */
	public List<RoleMenu> findBy(RoleMenu param){
		RoleMenuExample example = new RoleMenuExample();
		Criteria criteria = example.createCriteria();
		if(!StringUtils.isEmpty(param.getId())){
			criteria.andIdEqualTo(param.getId());
		}
		if(!StringUtils.isEmpty(param.getRoleId()))
		{
		    criteria.andRoleIdEqualTo(param.getRoleId());
		}
		
		if(!StringUtils.isEmpty(param.getMenuId()))
		{
		    criteria.andMenuIdEqualTo(param.getMenuId());
		}
		
		//stat默认情况下是1
		if(!StringUtils.isEmpty(param.getStat()))
		{
		   criteria.andStatEqualTo(param.getStat());
		}
		else
		{
		    criteria.andStatEqualTo(1);
		}
		
		return mapper.selectByExample(example);
	}
	
	public RoleMenu findById(String id){
		return mapper.selectByPrimaryKey(id);
	}
	
	public void insertRoleMenu(RoleMenu roleMenu){
		mapper.insert(roleMenu);
	}
	
	public void updateRoleMenu(RoleMenu roleMenu){
		mapper.updateByPrimaryKeySelective(roleMenu);
	}
	
	/**
	 * 
	 * @param roleMenuDto
	 * @exception	
	 * @author 朱振
	 * @date 2015年8月21日 上午11:06:16
	 */
	public void unGrantByRoleId(String roleId)
	{
	    RoleMenuExample example = new RoleMenuExample();
        Criteria criteria = example.createCriteria(); 
        criteria.andRoleIdEqualTo(roleId); 
     
        RoleMenu record = new RoleMenu(); 
        record.setStat(0);
        
	    mapper.updateByExampleSelective(record, example);
	}
	
}
