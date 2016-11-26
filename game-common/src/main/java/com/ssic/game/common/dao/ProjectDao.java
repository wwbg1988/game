package com.ssic.game.common.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.game.common.dto.ProjectUsersDto;
import com.ssic.game.common.mapper.TImsProjectExMapper;

@Repository
public class ProjectDao {
	
	@Autowired
	private TImsProjectExMapper mapper;
	
	
	public List<ProjectDto> findAll(){
		return mapper.findAll();
	}


	public ProjectDto findById(String id) {
		return mapper.findById(id);
	}
	
	public List<ProjectDto> findByIdName(ProjectDto projectDto){
		return mapper.findByIdName(projectDto);
	}
	
	public void insert(ProjectDto projectDto){
		 mapper.insert(projectDto);
	}
	
	public void update(ProjectDto projectDto){
		 mapper.update(projectDto);
	}
	
	public void deleteById(ProjectDto projectDto){
		 mapper.deleteById(projectDto);
	}
	
	public List<ProjectDto> findByIdNameAll(ProjectDto projectDto,PageHelperDto ph){
		return mapper.findByIdNameAll(projectDto,ph);
	}
	
	public int findCount(ProjectDto projectDto){
		return mapper.findCount(projectDto);
	}
	
	public ProjectUsersDto findByUserID(String userId){
		return mapper.findByUserID(userId);
	}
	
}
