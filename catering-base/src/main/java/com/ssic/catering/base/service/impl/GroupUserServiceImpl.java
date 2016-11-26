package com.ssic.catering.base.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.catering.base.dao.GroupUserDao;
import com.ssic.catering.base.dto.GroupInfoDto;
import com.ssic.catering.base.dto.GroupUserDto;
import com.ssic.catering.base.dto.QjyCateringUserDto;
import com.ssic.catering.base.pojo.GroupUser;
import com.ssic.catering.base.service.IGroupInfoService;
import com.ssic.catering.base.service.IGroupUserService;
import com.ssic.catering.base.service.IQjyCateringService;
import com.ssic.game.common.dao.ImsUserDao;
import com.ssic.game.common.dao.QjyFriendDao;
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.dto.QjyFriendDto;
import com.ssic.game.common.pojo.ImsUsers;
import com.ssic.util.BeanUtils;
import com.ssic.util.UUIDGenerator;

@Service
public class GroupUserServiceImpl implements IGroupUserService{

	@Autowired
	private GroupUserDao groupUserDao;
	@Autowired
	private QjyFriendDao qjyFriendDao; 
	@Autowired
	private IGroupInfoService groupInfoService;
	@Autowired
	private ImsUserDao imsUserDao;
	@Autowired
	private IQjyCateringService qjyCateringService;
	
	@Override
	public List<GroupUserDto> findBy(GroupUserDto groupUserDto) {
		// TODO Auto-generated method stub
		GroupUser groupUser = new GroupUser();
		BeanUtils.copyProperties(groupUserDto, groupUser);
		List<GroupUser> list= groupUserDao.findBy(groupUser);
		List<GroupUserDto> listdto = BeanUtils.createBeanListByTarget(list, GroupUserDto.class);
		return listdto;
	}

	@Override
	public void insertGroupUser(GroupUserDto groupUserDto) {
		// TODO Auto-generated method stub
		GroupUser groupUser = new GroupUser();
		BeanUtils.copyProperties(groupUserDto, groupUser);
		groupUserDao.insertGroupUser(groupUser);
	}

	@Override
	public void updateGroupUser(GroupUserDto groupUserDto) {
		// TODO Auto-generated method stub
		GroupUser groupUser = new GroupUser();
		BeanUtils.copyProperties(groupUserDto, groupUser);
		groupUserDao.updateGroupUser(groupUser);
	}

	@Override
	public GroupUserDto findById(String id) {
		// TODO Auto-generated method stub
		GroupUser groupUser = groupUserDao.findById(id);
		GroupUserDto groupUserDto = new GroupUserDto();
		BeanUtils.copyProperties(groupUser, groupUserDto);
		return groupUserDto;
	}

	@Override
	public String findByGroupUser(String infoid) {
		// TODO Auto-generated method stub
		String userIds ="";
	    GroupUser groupUser = new GroupUser();
	    groupUser.setInfoId(infoid);
		List<GroupUser> listuser =  groupUserDao.findBy(groupUser);
		if(listuser==null ){
			return userIds;
		}else{
			for(GroupUser gUser:listuser ){
				String userId = gUser.getUserId();
				userIds = userIds+userId+",";
			}
			return userIds;
		}
	}

	@Override
	public void grantUser(String userId, String rolesId) {
		// TODO Auto-generated method stub
  	  //查询群组信息
        GroupInfoDto groupInfoDto = groupInfoService.findById(userId);
       //删除这个群组下的所有用户
        String infoId = groupInfoDto.getId();
        String groupId = groupInfoDto.getGroupId();
        GroupUser groupUser = new GroupUser();
        groupUser.setInfoId(infoId);
        List<GroupUser> listguserDto = groupUserDao.findBy(groupUser);
                	     //添加群组下的用户
                        if(listguserDto!=null && listguserDto.size()>0){
                        	for(GroupUser groupUser2 : listguserDto){
                        		String userId2 =   groupUser2.getUserId();
                        		String userAccount2 = groupUser2.getUserAccount();
                        		String groupuserid = groupUser2.getId();
                        		String qjyAccount = groupUser2.getQjyAccount();
                        		//群创建者不能删除
                        		if(!groupInfoDto.getCreateuserid().equals(userId2)){
                        			//删除这个群组下的这些用户
                            		GroupUser groupU2 = new GroupUser();
                            		groupU2.setId(groupuserid);
                            		groupU2.setStat(0);
                            	    groupUserDao.updateGroupUser(groupU2);
                            	    //亲加云删除群成员
                            	    QjyCateringUserDto qjyCateringUserDto = new QjyCateringUserDto();
                            	    qjyCateringUserDto.setGroupId(groupId);
                            	    qjyCateringUserDto.setUserAccount(qjyAccount);
                            	    qjyCateringService.delGroupMemberNew(qjyCateringUserDto);
                        		}
                        		
                        	}
                        }
                        
                	     if (rolesId != null && rolesId.length() > 0) {
                             for (String roleId :rolesId.split(",")) {
                            	 if(!groupInfoDto.getCreateuserid().equals(roleId)){
                            		   //插入t_ctr_group_user
                                     ImsUsers user=  imsUserDao.findByUserId(roleId);
                                     if(user!=null){
                                    	 GroupUser groupUser3 = new GroupUser();
                                         groupUser3.setId(UUIDGenerator.getUUID());
                                         groupUser3.setInfoId(infoId);
                                         groupUser3.setQjyAccount(user.getQjyAccount());
                                         groupUser3.setStat(1);
                                         groupUser3.setUserAccount(user.getUserAccount());
                                         groupUser3.setUserId(roleId);
                                         groupUser3.setCreateTime(new Date());
                                         groupUserDao.insertGroupUser(groupUser3);
                                         //亲加云添加群成员
                                         QjyCateringUserDto qjyCateringUserDto2 = new QjyCateringUserDto();
                                         qjyCateringUserDto2.setGroupId(groupId);
                                         qjyCateringUserDto2.setUserAccount(user.getQjyAccount());
                                         qjyCateringService.addGroupMemberNew(qjyCateringUserDto2);
                                     }
                                    
                            	 }
                             }
                         }
	}
}
