package com.ssic.catering.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.catering.base.dao.VersionDao;
import com.ssic.catering.base.dto.VersionDto;
import com.ssic.catering.base.pojo.Version;
import com.ssic.catering.base.service.IVersionService;
import com.ssic.util.BeanUtils;

@Service
public class VersionServiceImpl implements IVersionService{

	@Autowired
	private VersionDao versionDao;
	
	@Override
	public List<VersionDto> findBy(VersionDto versionDto) {
		// TODO Auto-generated method stub
		Version version = new Version();
        BeanUtils.copyProperties(versionDto, version);
		List<Version> list = versionDao.findBy(version);
		List<VersionDto> listdto = BeanUtils.createBeanListByTarget(list, VersionDto.class);
		return listdto;
	}

	
	
	
}
