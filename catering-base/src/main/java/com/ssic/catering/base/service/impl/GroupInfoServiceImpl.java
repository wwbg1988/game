package com.ssic.catering.base.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ssic.catering.base.dao.GroupInfoDao;
import com.ssic.catering.base.dao.GroupUserDao;
import com.ssic.catering.base.dto.GroupInfoDto;
import com.ssic.catering.base.dto.GroupUserDto;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.dto.Tree;
import com.ssic.catering.base.pojo.GroupInfo;
import com.ssic.catering.base.pojo.GroupUser;
import com.ssic.catering.base.pojo.PageHelper;
import com.ssic.catering.base.service.IGroupInfoService;
import com.ssic.game.common.dao.ImsUserDao;
import com.ssic.game.common.dto.ImUserDto;
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.pojo.ImsUsers;
import com.ssic.util.BeanUtils;
import com.ssic.util.UUIDGenerator;

@Service
public class GroupInfoServiceImpl implements IGroupInfoService{

	@Autowired
	private GroupInfoDao groupInfoDao;
	@Autowired
	private GroupUserDao groupUserDao;
	@Autowired
	private ImsUserDao imsUserDao;
	
	@Override
	public List<GroupInfoDto> findBy(GroupInfoDto groupInfoDto) {
		// TODO Auto-generated method stub
		GroupInfo groupInfo = new GroupInfo();
		BeanUtils.copyProperties(groupInfoDto, groupInfo);
		List<GroupInfo> list=  groupInfoDao.findBy(groupInfo);
		List<GroupInfoDto> listdto = BeanUtils.createBeanListByTarget(list, GroupInfoDto.class);
		return listdto;
	}

	@Override
	public void insertGroupInfo(GroupInfoDto groupInfoDto) {
		// TODO Auto-generated method stub
		GroupInfo groupInfo = new GroupInfo();
		BeanUtils.copyProperties(groupInfoDto, groupInfo);
		groupInfoDao.insertGroupInfo(groupInfo);
	}

	@Override
	public void updateGroupInfo(GroupInfoDto groupInfoDto) {
		// TODO Auto-generated method stub
		GroupInfo groupInfo = new GroupInfo();
		BeanUtils.copyProperties(groupInfoDto, groupInfo);
		groupInfoDao.updateGroupInfo(groupInfo);
	}

	@Override
	public List<GroupInfoDto> findBy(GroupInfoDto groupInfoDto, PageHelper ph) {
		// TODO Auto-generated method stub
		PageHelperDto phdto = new PageHelperDto();
		 phdto.setOrder(ph.getOrder());
		 phdto.setPage(ph.getPage());
		 phdto.setRows(ph.getRows());
		 phdto.setSort(ph.getSort());
		 phdto.setBeginRow((ph.getPage()-1)*ph.getRows());
		 
		 GroupInfo groupInfo = new GroupInfo();
	     BeanUtils.copyProperties(groupInfoDto, groupInfo);
		 List<GroupInfo> list=  groupInfoDao.findBy(groupInfo,phdto);
		 List<GroupInfoDto> listdto = BeanUtils.createBeanListByTarget(list, GroupInfoDto.class);
		return listdto;
	}

	@Override
	public int findCount(GroupInfoDto groupInfoDto) {
		// TODO Auto-generated method stub
		 GroupInfo groupInfo = new GroupInfo();
	     BeanUtils.copyProperties(groupInfoDto, groupInfo);
		return groupInfoDao.findCount(groupInfo);
	}

	@Override
	public List<GroupInfoDto> findFirst(GroupInfoDto groupInfoDto) {
		// TODO Auto-generated method stub
		 GroupInfo groupInfo = new GroupInfo();
	     BeanUtils.copyProperties(groupInfoDto, groupInfo);
	     List<GroupInfo> list = groupInfoDao.findFirst(groupInfo);
	     List<GroupInfoDto> listdto = BeanUtils.createBeanListByTarget(list, GroupInfoDto.class);
		return listdto;
	}

	@Override
	public List<Tree> tree() {
		// TODO Auto-generated method stub
		List<Tree> lt = new ArrayList<Tree>();
		GroupInfoDto gidto = new GroupInfoDto();
		List<GroupInfoDto> listdto=  findBy(gidto);
		if(listdto!=null && listdto.size()>0){
			for(GroupInfoDto gidto2 : listdto){
				String id = gidto2.getId();
				String name = gidto2.getGroupName();
				String parentId = gidto2.getParentId();
				Tree tree = new Tree();
				tree.setId(id);
				tree.setText(name);
				tree.setPid(parentId);
				lt.add(tree);
			}
		}
		return lt;
	}

	@Override
	public GroupInfoDto findById(String id) {
		// TODO Auto-generated method stub
		GroupInfo groupInfo = groupInfoDao.findById(id);
		GroupInfoDto groupInfoDto = new GroupInfoDto();
		BeanUtils.copyProperties(groupInfo, groupInfoDto);
		return groupInfoDto;
	}

	@Override
	public GroupInfoDto findLTGroupInfo(GroupInfoDto groupInfoDto) {
		// TODO Auto-generated method stub
		 GroupInfo  groupInfo = groupInfoDao.findById(groupInfoDto.getGroupId());
		 GroupInfoDto groupInfoDto2 = BeanUtils.createBeanByTarget(groupInfo, GroupInfoDto.class);
		 //查询出所有的用户信息
		 ImsUsers imsu2 = new ImsUsers();
		 List<ImsUsers> listus = imsUserDao.findBy(imsu2);
	     if(groupInfoDto2!=null&& !StringUtils.isEmpty(groupInfoDto2.getCreateuserid())){
	    	 ImsUsers iusers = imsUserDao.findByUserId(groupInfoDto2.getCreateuserid());
	    	 if(iusers!=null){
	    		 groupInfoDto2.setCreateusername(iusers.getName());
	    	 }
	     }
		
		 List<ImUserDto> list = new ArrayList<ImUserDto>();
		 if(groupInfo!=null){
			 GroupUser param = new GroupUser();
			 param.setInfoId(groupInfo.getGroupId());
			 List<GroupUser> listuser=  groupUserDao.findBy(param);
			 if(listuser!=null && listuser.size()>0){
				 for(GroupUser user : listuser){
					 ImUserDto imuser = new ImUserDto();
					 imuser.setImCount(user.getUserAccount());
					 imuser.setUserId(user.getUserId());
					 if(!CollectionUtils.isEmpty(listus)){
						 for(ImsUsers imsu3 : listus){
							 if(user.getUserId().equals(imsu3.getId())){
								 imuser.setUserName(imsu3.getName());
								 imuser.setUserImage(imsu3.getUserImage());
							 }
						 }
					 }
					 
//					 ImsUsers imsuser = imsUserDao.findByUserId(user.getUserId());
//					 if(imsuser!=null){
//						 imuser.setUserName(imsuser.getName());
//						 imuser.setUserImage(imsuser.getUserImage());
//					 }
					 list.add(imuser);
				 }
			 }
		 }
		 groupInfoDto2.setList(list);
		return groupInfoDto2;
	}

	@Override
	public void editLTGroupName(GroupInfoDto groupInfoDto) {
		// TODO Auto-generated method stub
		GroupInfo groupinfo = new GroupInfo();
		groupinfo.setId(groupInfoDto.getGroupId());
		groupinfo.setGroupName(groupInfoDto.getGroupName());
		groupInfoDao.updateGroupInfo(groupinfo);
	}

	@Override
	public void addLTGroupUser(GroupInfoDto groupInfoDto) {
		// TODO Auto-generated method stub
		//查询这个群中的人
		GroupUser param = new GroupUser();
		param.setInfoId(groupInfoDto.getGroupId());
		List<GroupUser> listall = groupUserDao.findBy(param);
		List<String> listusers2 = new ArrayList<String>();
		if(!CollectionUtils.isEmpty(listall)){
			for(GroupUser groupUser2:listall){
				listusers2.add(groupUser2.getUserId());
			}
		}
		
		GroupUser groupUser = new GroupUser();
		groupUser.setId(UUIDGenerator.getUUID());
		groupUser.setInfoId(groupInfoDto.getGroupId());
		groupUser.setStat(1);
		
		groupUser.setCreateTime(new Date());
		if(!StringUtils.isEmpty(groupInfoDto.getUsers())){
			String[] usersid = groupInfoDto.getUsers().split(",");
			if(usersid!=null && usersid.length>0){
				for(String userid2 : usersid){
					//如果用户已经存在不插入
					if(listusers2.contains(userid2)){
					}else{
						ImsUsers imsuser = imsUserDao.findByUserId(userid2);
						groupUser.setId(UUIDGenerator.getUUID());
						groupUser.setUserId(userid2);
						if(imsuser!=null){
							groupUser.setUserAccount(imsuser.getUserAccount());
						}
						groupUserDao.insertGroupUser(groupUser);
					}
				}
				
			}
		}
		
	}

	@Override
	public void leaveLTGroup(GroupInfoDto groupInfoDto) {
		// TODO Auto-generated method stub
		GroupUser groupUser = new GroupUser();
		groupUser.setInfoId(groupInfoDto.getGroupId());
		groupUser.setUserId(groupInfoDto.getUsers());
		groupUser.setStat(1);
		List<GroupUser> list=  groupUserDao.findBy(groupUser);
		if(list!=null && list.size()>0){
			String groupuserid = list.get(0).getId();
			GroupUser param = new GroupUser();
			param.setId(groupuserid);
			param.setStat(0);
			groupUserDao.updateGroupUser(param);
		}
	}

	@Override
	public String delLTGroup(GroupInfoDto groupInfoDto) {
		// TODO Auto-generated method stub
		GroupInfo groupinfo = groupInfoDao.findById(groupInfoDto.getGroupId());
		if(groupinfo!=null){
			if(groupInfoDto.getUsers().equals(groupinfo.getCreateuserid())){
				//删除群组成员
				GroupUser guser = new GroupUser();
				guser.setInfoId(groupInfoDto.getGroupId());
				List<GroupUser> listguser =  groupUserDao.findBy(guser);
				if(listguser!=null && listguser.size()>0){
					for(GroupUser guser2 : listguser){
						GroupUser guser3 = new GroupUser();
						guser3.setId(guser2.getId());
						guser3.setStat(0);
						groupUserDao.updateGroupUser(guser3);
					}
				}
				//删除群组
				GroupInfo ginfo = new GroupInfo();
				ginfo.setId(groupInfoDto.getGroupId());
				ginfo.setStat(0);
				groupInfoDao.updateGroupInfo(ginfo);
				return "200";
			}else{
				return "500";
			}
		}
		return "500";
	}

}
