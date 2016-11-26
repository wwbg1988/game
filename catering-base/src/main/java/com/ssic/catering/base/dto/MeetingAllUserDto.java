package com.ssic.catering.base.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class MeetingAllUserDto implements Serializable{

	@Getter
	@Setter
	private String projId;
	@Getter
	@Setter
	private List<MeetingRecordDto> listMr;
	
}
