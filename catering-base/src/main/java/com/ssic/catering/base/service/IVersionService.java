package com.ssic.catering.base.service;

import java.util.List;

import com.ssic.catering.base.dto.VersionDto;

public interface IVersionService {

	public List<VersionDto> findBy (VersionDto versionDto);
	
	
}
