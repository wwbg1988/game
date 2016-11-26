package com.ssic.game.app.service;

import java.text.ParseException;
import java.util.List;

import com.ssic.game.common.dto.WorkSearchDto;



public interface ISendInfoService {

	List<WorkSearchDto> sendMeeting(String userId, String searchDate, int beginRow, int endRow) ;
	
    List<WorkSearchDto> sendNews(String userId, String searchDate,int beginRow, int endRow,String projId);
    
    List<WorkSearchDto> sendNotify(String userId, String searchDate,int beginRow, int endRow,String projId);
}
