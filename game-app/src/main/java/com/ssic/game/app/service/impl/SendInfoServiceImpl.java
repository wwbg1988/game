package com.ssic.game.app.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.catering.base.dto.MeetingDto;
import com.ssic.catering.base.dto.MeetingRecordDto;
import com.ssic.catering.base.dto.NewsDto;
import com.ssic.catering.base.dto.NotifyDto;
import com.ssic.catering.base.pojo.PageHelper;
import com.ssic.catering.base.service.IMeetingService;
import com.ssic.catering.base.service.INewsService;
import com.ssic.catering.base.service.INotifyService;
import com.ssic.game.app.controller.catering.WorkController;
import com.ssic.game.app.service.ISendInfoService;
import com.ssic.game.common.constant.WorkSearchConstants;
import com.ssic.game.common.dto.WorkSearchDto;

@Service
public class SendInfoServiceImpl implements ISendInfoService{

	@Autowired
	private IMeetingService meetingService;
	@Autowired
	private INewsService newsService;
	@Autowired
	private INotifyService notifyService;

	//发送会议信息
	@Override
	public List<WorkSearchDto> sendMeeting(String userId, String searchDate,
			int beginRow, int endRow)  {
		// TODO Auto-generated method stub
		List<WorkSearchDto> listws = new ArrayList<WorkSearchDto>();
	    if(userId==null || "".equals(userId)){
			return listws;
		}
		MeetingDto 	 meetingDto = new MeetingDto();
        meetingDto.setMuserId(userId);
        SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf3=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date createtime = null;
		try {
		    if(searchDate!=null && !"".equals(searchDate)){
		        createtime = sdf2.parse(searchDate);
		        meetingDto.setCreateTime(createtime);
		    }
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
        
        meetingDto.setBeginRow(beginRow);
        meetingDto.setEndRow(endRow);
        meetingDto.setNowDate(new Date());
		meetingDto.setFlagis(0);
		meetingDto.setState(2);   //只显示用户待定的会议，用户已经同意拒绝的不显示
		List<MeetingRecordDto> list=  meetingService.findMyMeet(meetingDto);
//		if(meetingDto.getMuserId().equals(userId)){
//			return listws;
//		}else{
			if(list!=null && list.size()>0){
				for(MeetingRecordDto mrdto3:list){
					String creatUname = mrdto3.getCreatUName();
					String createId = mrdto3.getCreateUserID();
					Date mStarTime = mrdto3.getMStarTime();    //会议开始时间
					Date mEndTime = mrdto3.getMEndTime();   //会议结束
					String mstime = sdf3.format(mStarTime);
					
					//会议创建时间改为会议开始时间
					mrdto3.setCreateTime(mrdto3.getMStarTime());
					
					String dName = mrdto3.getDName();
					Date cDate =mrdto3.getCreateTime();
					String mDate = mrdto3.getMonthDate();
					if(mDate!=null && mDate.length()==1){
						mDate = "0"+mDate;
					}
					String dDate = mrdto3.getDayDate();
					if(mDate!=null && mDate.length()==1){
						dDate = "0"+dDate;
					}
					String meetMessage = creatUname +"邀请您于"+mstime +"于"+dName+"参加会议";
					System.out.println(meetMessage);
					String meetMD = mDate +"月"+dDate+"日";
					System.out.println(meetMD);
					//当前会议都是这个用户要参加的，
					//如果当前时间大于结束时间  已完成，其他都是 待处理
					Date nowDate = new Date();
					  //当前时间小于开始时间  的会议展示出来
				//	if(searchDate==null){
					//	if(nowDate.before(mStarTime)){
							WorkSearchDto  wsDto = new WorkSearchDto();
							wsDto.setMessage(meetMessage);
							wsDto.setMeetMD(meetMD);
							wsDto.setBeginRow(beginRow);
							wsDto.setEndRow(endRow);
							wsDto.setCreateTime(cDate);
							wsDto.setSearchDate(searchDate);
							wsDto.setType(WorkSearchConstants.HUIYI);
							//跳转到会议记录界面    
							//    /appMeetingController/checkMeeting.do?userid=&projId=&procId=
							//	  /appMeetingController/findMyMeeting.do?userId
							wsDto.setUrl("");
							wsDto.setUserId(userId);
							//如果创建者是当前用户不显示待处理图标，    is_create  0不是  1是
							if(createId.equals(userId)){
								wsDto.setIsMeetCreate(1);
							}else{
								wsDto.setIsMeetCreate(0);
							}
							listws.add(wsDto);
				}
				return listws;
			}else{
				return listws;
			}
		//}
		
	}
	
	//发送新闻信息
	
	public List<WorkSearchDto> sendNews(String userId, String searchDate,
			int beginRow, int endRow,String projId){
		List<WorkSearchDto> listws = new ArrayList<WorkSearchDto>();
	    if(userId==null || "".equals(userId)){
			return listws;
		}
	    NewsDto newsDto = new NewsDto();
	    newsDto.setState(1);
	    newsDto.setProjId(projId);
		List<NewsDto> list=  newsService.findBy(newsDto,beginRow,endRow,searchDate);
		if(list!=null && list.size()>0){
			for(NewsDto ndto:list){
				String listTitle = ndto.getListTitle();
				String listText = ndto.getListText();
				Date createTime = ndto.getCreateTime();
				
				//新闻创建时间改为发布时间
				ndto.setCreateTime(ndto.getNewsTime());
				
				WorkSearchDto wsDto = new WorkSearchDto();
				wsDto.setMessage(listTitle);
				wsDto.setUrl(ndto.getTextHtml());
				wsDto.setType(WorkSearchConstants.XINWEN);
				wsDto.setUserId(userId);
				wsDto.setBeginRow(beginRow);
				wsDto.setEndRow(endRow);
				wsDto.setSearchDate(searchDate);
				wsDto.setCreateTime(createTime);
				SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd");
				String cTime = sdf2.format(createTime);
				String monthTime = cTime.substring(5,7);
				String dayTime = cTime.substring(8, 10);
				String meetMD = monthTime+"月"+dayTime+"日";
				System.out.println(meetMD);
				wsDto.setMeetMD(meetMD);
				listws.add(wsDto);
			}
			return listws;
		}else{
			return listws;
		}
		
	}
	
	
	//发送公告信息
	public List<WorkSearchDto> sendNotify(String userId, String searchDate,
			int beginRow, int endRow ,String projId){
		List<WorkSearchDto> listws = new ArrayList<WorkSearchDto>();
	    if(userId==null || "".equals(userId)){
			return listws;
		}
	    SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd");
	    
	    
	    NotifyDto notifyDto = new NotifyDto();
	    notifyDto.setState(1);
	    notifyDto.setProjId(projId);
	    List<NotifyDto> listDto = notifyService.findBy(notifyDto,beginRow,endRow,searchDate);
		if(listDto!=null && listDto.size()>0){
			for(NotifyDto nDto:listDto){
				String listTitle = nDto.getListTitle();
				Date createTime = nDto.getCreateTime();
				
				//公告开始时间改为发布时间
				nDto.setCreateTime(nDto.getNotifyTime());
				
				WorkSearchDto wsDto = new WorkSearchDto();
				wsDto.setMessage(listTitle);;
				wsDto.setCreateTime(createTime);
				wsDto.setType(WorkSearchConstants.BAOGAO);
				wsDto.setUrl(nDto.getTextHtml());
				wsDto.setBeginRow(beginRow);
				wsDto.setEndRow(endRow);
				wsDto.setSearchDate(searchDate);
				wsDto.setUserId(userId);
				
				String cTime = sdf2.format(createTime);
				String monthTime = cTime.substring(5,7);
				String dayTime = cTime.substring(8, 10);
				String meetMD = monthTime+"月"+dayTime+"日";
				wsDto.setMeetMD(meetMD);
				listws.add(wsDto);
			}
			return listws;
		}
		return listws;
	}
	

}
