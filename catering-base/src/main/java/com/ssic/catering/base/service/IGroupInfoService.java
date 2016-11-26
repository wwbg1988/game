package com.ssic.catering.base.service;

import java.util.List;

import com.ssic.catering.base.dto.GroupInfoDto;
import com.ssic.catering.base.dto.Tree;
import com.ssic.catering.base.pojo.PageHelper;

public interface IGroupInfoService {

	List<GroupInfoDto> findBy(GroupInfoDto groupInfoDto);
	
	void insertGroupInfo(GroupInfoDto groupInfoDto);
	
	void updateGroupInfo(GroupInfoDto groupInfoDto);
	
	List<GroupInfoDto> findBy(GroupInfoDto groupInfoDto,PageHelper ph);
	
	int findCount(GroupInfoDto groupInfoDto);
	
	List<GroupInfoDto> findFirst(GroupInfoDto groupInfoDto);
	
	List<Tree>   tree();
	
	GroupInfoDto findById(String id);
	
	GroupInfoDto findLTGroupInfo(GroupInfoDto groupInfoDto);
	
	void editLTGroupName(GroupInfoDto groupInfoDto);
	
	void addLTGroupUser(GroupInfoDto groupInfoDto);
	
	void leaveLTGroup(GroupInfoDto groupInfoDto);
	
	String delLTGroup(GroupInfoDto groupInfoDto);
	
}
