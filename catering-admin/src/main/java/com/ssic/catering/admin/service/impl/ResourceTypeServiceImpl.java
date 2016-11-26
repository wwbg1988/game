package com.ssic.catering.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ssic.catering.admin.dao.ResourceTypeDao;
import com.ssic.catering.admin.dao.TImsMenuTypeDao;
import com.ssic.catering.admin.dto.TImsMenuTypeDto;
import com.ssic.catering.admin.pageModel.ResourceType;
import com.ssic.catering.admin.service.ResourceTypeServiceI;


@Service
public class ResourceTypeServiceImpl implements ResourceTypeServiceI {

	@Autowired
	private ResourceTypeDao resourceType;

	@Autowired
	private TImsMenuTypeDao menuTypeDao;

	
	public List<ResourceType> getResourceTypeList() {
//		List<Tresourcetype> l = resourceType.find("from Tresourcetype t");
//		List<ResourceType> rl = new ArrayList<ResourceType>();
//		if (l != null && l.size() > 0) {
//			for (Tresourcetype t : l) {
//				ResourceType rt = new ResourceType();
//				BeanUtils.copyProperties(t, rt);
//				rl.add(rt);
//			}
//		}
//		return rl;
		return null;
	}

	
	//@Cacheable(value = "menuTypeServiceCache", key = "'menuTypeList'")
    @Cacheable(value = "default", key = "'game.admin.dto.TImsMenuTypeDto'")
	public List<TImsMenuTypeDto> getMenuTypeList() {
		 List<TImsMenuTypeDto> rl = menuTypeDao.finAll();
	     return rl;
  }
	
}
