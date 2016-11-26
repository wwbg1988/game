package com.ssic.catering.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssic.catering.base.service.IimsUserService;
import com.ssic.game.common.dao.DeptUserDao;
import com.ssic.game.common.dao.ImsUserDao;
import com.ssic.game.common.dao.ProjectUsersDao;
import com.ssic.game.common.dao.RoleUsersDao;
import com.ssic.game.common.dto.DeptUsersDto;
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.dto.ProjectUsersDto;
import com.ssic.game.common.dto.RoleUsersDto;

@Service
public class ImsUserServiceImpl implements IimsUserService{

	@Autowired
	private ImsUserDao imsUserDao;
	@Autowired
	private DeptUserDao deptUserDao;
	@Autowired
	private ProjectUsersDao projectUsersDao;
	@Autowired
	private RoleUsersDao roleUsersDao;
	
	@Override
	@Transactional
	public void delAllUser(ImsUsersDto imsUsersDto) {
		// TODO Auto-generated method stub
		//删除user
		imsUserDao.deleteUser(imsUsersDto);
		//删除t_ims_dept_users
		DeptUsersDto deptUsersDto = new DeptUsersDto();
		deptUsersDto.setUserId(imsUsersDto.getId());
		deptUserDao.deleteDeptUser(deptUsersDto);
		//删除t_ims_project_users
		ProjectUsersDto projectUsersDto = new ProjectUsersDto();
		projectUsersDto.setUserId(imsUsersDto.getId());
		projectUsersDao.deleteByUserId(projectUsersDto);
		//删除t_ims_role_users
		RoleUsersDto roleUsersDto = new RoleUsersDto();
		roleUsersDto.setUserId(imsUsersDto.getId());
		roleUsersDao.deleteRoleUser(roleUsersDto);
	}

}
