package com.ssic.game.admin.dao;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssic.game.admin.dto.TImsUsersDto;
import com.ssic.game.admin.mapper.TImsUserRoleExMapper;
import com.ssic.game.admin.mapper.TImsUsersExMapper;
import com.ssic.game.admin.pageModel.DataGrid;
import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.admin.pojo.Users;
import com.ssic.game.admin.util.MD5Util;
import com.ssic.util.BeanUtils;
import com.ssic.util.StringUtils;
import com.ssic.util.UUIDGenerator;

@Repository
public class UserDao {
	@Autowired
	private TImsUsersExMapper tImsUsersExMapper;

	@Autowired
	private TImsUserRoleExMapper userRoleMapper;

	public TImsUsersDto login(TImsUsersDto userDto) {
		List<TImsUsersDto> list = tImsUsersExMapper.findBy(userDto);
		if (list != null && list.size() > 0) {
			TImsUsersDto result = list.get(0);
//			TImsUsersDto result = new TImsUsersDto();
//			BeanUtils.copyProperties(temp, result);
			return result;
		}
		return null;

	}
	public int vailUserAccount(TImsUsersDto userDto){
		return tImsUsersExMapper.findCountBy(userDto);
	}

	public void insertBy(TImsUsersDto userDto) {
		if (userDto != null) {
			Users users = new Users();
			BeanUtils.copyProperties(userDto, users);
			users.setCreatedatetime(new Date());
			users.setPassword(MD5Util.md5(users.getPassword()));
			users.setQjyAccount("qjy_" + users.getName());
			users.setStat(1);
			users.setIsAdmin(0);

			tImsUsersExMapper.insertBy(users);
		}
	}

	public int findCountByAccount(TImsUsersDto userDto) {
		if (userDto != null) {
			return tImsUsersExMapper.findCountBy(userDto);
		} else {
			return -1;
		}
	}

	public DataGrid findAll(TImsUsersDto userDto, PageHelper ph) {
		DataGrid dataGrid = new DataGrid();
		if (!StringUtils.isEmpty(userDto.getSearchName())) {
			userDto.setSearchName("%" + userDto.getSearchName() + "%");
		}
		int counts = tImsUsersExMapper.findCountBy(userDto);
		dataGrid.setTotal(Long.valueOf(counts));
		if(ph.getRows()!=0){
		    int tempPage = ph.getPage();
		    int tempRows = ph.getRows();
		    ph.setPage((tempPage-1)*tempRows);
		    ph.setRows(tempPage*tempRows);
		}
		List<TImsUsersDto> list = tImsUsersExMapper.findPageBy(userDto, ph);
		dataGrid.setRows(list);
		return dataGrid;
	}

	public void updateBy(TImsUsersDto user) throws Exception {
//		TImsUsersDto temp = new TImsUsersDto();
//		temp.setUserAccount(user.getUserAccount());
//		List<TImsUsersDto> list = tImsUsersExMapper.findBy(temp);
//		if (list.size() > 0) {
//			throw new Exception("登录名已存在！");
//		} else {
//			tImsUsersExMapper.updateBy(user);
//		}
		tImsUsersExMapper.updateBy(user);
	}

	public void deleteBy(String id) {
		tImsUsersExMapper.updateDelBy(id);
	}
	public void updatePwd(TImsUsersDto userDto){
		tImsUsersExMapper.updatePwd(userDto);
	}

	public void insertRole(List<String> roleList, String ids) {
		// 先删除用户的权限 而后添加
		for (String id : ids.split(",")) {
			if (id != null && !id.equalsIgnoreCase("")) {
				// Tuser t = userDao.get(Tuser.class, id);
				// t.setTroles(new HashSet<Trole>(roles));
				userRoleMapper.deleteRoleByUserId(id);
				if (roleList != null && roleList.size() > 0) {
					for (int i = 0; i < roleList.size(); i++) {
						String pkId=UUIDGenerator.getUUID();
						int counts = userRoleMapper.findCountRoleBy(id, roleList.get(i));
						if(counts>0){
							userRoleMapper.updateBy(id, roleList.get(i));
						}else{
							userRoleMapper.insertBy(pkId,id, roleList.get(i));
						}
					}
				}

			}
		}
	}
	
	public void addImsUsers(TImsUsersDto userDto){
		if (userDto != null) {
			Users users = new Users();
			BeanUtils.copyProperties(userDto, users);
			users.setCreatedatetime(new Date());
			users.setPassword(MD5Util.md5(users.getPassword()));
			users.setQjyAccount("qjy_" + users.getUserAccount());
			users.setStat(1);
			users.setIsAdmin(0);

			tImsUsersExMapper.addImsUsers(users);
		}
	}
}
