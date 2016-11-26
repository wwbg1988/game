package com.ssic.catering.base.service;

import java.util.List;

import com.ssic.catering.base.dto.MeetingAddressDto;
import com.ssic.game.common.pageModel.PageHelper;

public interface IMeetingAddressService {

	List<MeetingAddressDto> findBy(MeetingAddressDto meetingAddressDto);
	
	MeetingAddressDto findById(String id);
	
	void insertMeetingA(MeetingAddressDto meetingAddressDto);
	
	void updateMeetingA(MeetingAddressDto meetingAddressDto);
	
	List<MeetingAddressDto> findBy(MeetingAddressDto meetingAddressDto,PageHelper ph);
	
	int findCount(MeetingAddressDto meetingAddressDto);
	
	/**
	 * 查询出所有符合条件的结果集	 
	 * @author 朱振	
	 * @date 2015年10月27日 下午2:41:30	
	 * @version 1.0
	 * @param meetingAddressDto 条件
	 * @param projectIds 项目id列表
	 * @param ph
	 * @return
	 * <p>修改人：朱振</p>
	 * <p>修改时间：2015年10月27日 下午2:41:30</p>
	 * <p>修改备注：</p>
	 */
	List<MeetingAddressDto> findBy(MeetingAddressDto meetingAddressDto,List<String> projectIds, com.ssic.catering.base.pojo.PageHelper ph);
	
	/**
	 * 获取所有符合条件的结果集的总条数
	 * @author 朱振	
	 * @date 2015年10月27日 下午2:42:21	
	 * @version 1.0
	 * @param meetingAddressDto
	 * @param projectIds
	 * @return
	 * <p>修改人：朱振</p>
	 * <p>修改时间：2015年10月27日 下午2:42:21</p>
	 * <p>修改备注：</p>
	 */
	Integer findCountBy(MeetingAddressDto meetingAddressDto,List<String> projectIds);
}
