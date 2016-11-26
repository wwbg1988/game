package com.ssic.game.admin.dao;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.ssic.game.admin.model.Tresourcetype;


@Repository
public class ResourceTypeDao  {

//	@Cacheable(value = "resourceTypeDaoCache", key = "#id")
//	public Tresourcetype getById(String id) {
//		return super.get(Tresourcetype.class, id);
//	}

}
