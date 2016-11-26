package com.ssic.game.app.controller.catering;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;






import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.base.dto.NewsDto;
import com.ssic.catering.base.dto.NotifyDto;
import com.ssic.catering.base.pojo.PageHelper;
import com.ssic.catering.base.service.INewsService;
import com.ssic.catering.base.service.INotifyService;
import com.ssic.catering.common.util.AppResponse;
import com.ssic.game.app.listener.InitApplicationContext;
import com.ssic.util.model.Response;

@Controller
@RequestMapping("/appNewsController")
public class AppNewsController {

	 protected static final Log logger = LogFactory.getLog(InitApplicationContext.class);
	
	 @Autowired
	 private INewsService newsService;
	 @Autowired
	 private INotifyService notifyService;
	 
	 @RequestMapping(value = "/sendNews.do", method = {RequestMethod.GET, RequestMethod.POST })
	 @ResponseBody
	 public  Response<List<NewsDto>>  sendNews(int page,int rows ,String projId){		 
		 PageHelper ph = new PageHelper();
		 ph.setPage(page);
		 ph.setRows(rows);
		 Response<List<NewsDto>> result = new Response<List<NewsDto>>();
		
		 NewsDto newsDto = new NewsDto();
		 newsDto.setStat(1);
		 newsDto.setState(1);
		 if(projId==null || "".equals(projId)){
			 result.setMessage("项目ID不存在！");
			 result.setStatus(AppResponse.SEND_NEWS_FAILE);
			 return result;
		 }
		 newsDto.setProjId(projId);
		 //查询   所有的新闻   ，新闻的列内容   ，详细内容 ，列内容的图片 
		 //    List<NewsDto> listdto=  newsService.findAll();
		 
		 List<NewsDto> listdto=  newsService.findBy(newsDto,ph);
		 // 如果发布日期为当天的，   SNewsTime 显示 hh:mm  
		 // 如果发布日期是当天之后的 ， SNewsTime 显示 xx月xx日
		 if(listdto!=null && listdto.size()>0){
			 for(NewsDto newsDto2:listdto){
				 Date newsDate2 = newsDto2.getNewsTime();
				 String SnewsDate = changeDate(newsDate2);
				 newsDto2.setSNewsTime(SnewsDate);
			 }
		 }
		
		 if(listdto!=null && listdto.size()>0){
			 result.setData(listdto);
			 result.setMessage("新闻列表返回成功！");
			 result.setStatus(AppResponse.RETURN_SUCCESS);
		 }else{
			 result.setMessage("新闻列表不存在！");
			 result.setStatus(AppResponse.SEND_NEWS_FAILE);
		 }
         return result;
	 }
	 
	 @RequestMapping(value = "/sendNotifys.do", method = {RequestMethod.GET, RequestMethod.POST })
	 @ResponseBody
	 public  Response<List<NotifyDto>>  sendNotifys(int page,int rows ,String projId){
		 PageHelper ph = new PageHelper();
		 ph.setPage(page);
		 ph.setRows(rows);
		 Response<List<NotifyDto>> result = new Response<List<NotifyDto>>();
		 
		 NotifyDto notifyDto = new NotifyDto();
		 if(projId==null || "".equals(projId)){
			 result.setMessage("项目ID不存在！");
			 result.setStatus(AppResponse.SEND_NEWS_FAILE);
			 return result;
		 }
		 notifyDto.setProjId(projId);
		 notifyDto.setStat(1);
		 List<NotifyDto> listdto=  notifyService.findBy(notifyDto, ph);
		 int countNotify=  notifyService.findCount(notifyDto);
		 if(listdto!=null && listdto.size()>0){
			 for(NotifyDto no2 : listdto){
				 no2.setCountNotify(countNotify);
				 String snotifyTime = changeNotifyDate(no2.getNotifyTime());
				 no2.setSNotifyTime(snotifyTime);
			 }
		 }
		 if(listdto!=null && listdto.size()>0){
			 result.setData(listdto);
			 result.setMessage("公告列表返回成功！");
			 result.setStatus(AppResponse.RETURN_SUCCESS);
		 }else{
			 result.setMessage("公告列表不存在！");
			 result.setStatus(AppResponse.SEND_NEWS_FAILE);
		 }
		return result;
	 }
	
	 // 获取时间差	
	 // 如果发布日期为当天的，   SNewsTime 显示 hh:mm  
	 // 如果发布日期是当天之后的 ， SNewsTime 显示 xxxx年xx月xx日
	 // mStarTime当前时间   mEndTime会议发布时间
	 public String changeDate(Date newsTime){
			String toNewsTime;
			Date nowDate = new Date();
			SimpleDateFormat sdf3=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String  snowDate  = sdf3.format(nowDate);
			if(newsTime==null){
				return null;
			}
			String snewsTime = sdf3.format(newsTime);
			String nowNYR = snowDate.substring(0,10);
			String newsNYR = snewsTime.substring(0, 10);
			if(nowNYR.equals(newsNYR)){
				String newsHM = snewsTime.substring(11, snewsTime.length());
				toNewsTime = newsHM.substring(0,2)+"时"+newsHM.substring(3, 5)+"分";
			}else{
				//  toNewsTime = newsNYR.substring(0, 4)+"年"+newsNYR.substring(5, 7)+"月"+newsNYR.substring(8, 10)+"日";
				toNewsTime = newsNYR.substring(5, 7)+"月"+newsNYR.substring(8, 10)+"日";
			}
			return toNewsTime;
		}
	 
	 //修改notifyTime 为 xxxx年xx月xx日
	 
	 public String changeNotifyDate (Date notifyTime){
		  SimpleDateFormat sdf3=new SimpleDateFormat("yyyy-MM-dd");
		  if(notifyTime==null){
			  return null;
		  }
		  String tonotifyTime = sdf3.format(notifyTime);
		//  String tonotifyTime2 = tonotifyTime.substring(0, 4)+"年"+tonotifyTime.substring(5, 7)+"月"+tonotifyTime.substring(8, 10)+"日";
		  String tonotifyTime2 = tonotifyTime.substring(5, 7)+"月"+tonotifyTime.substring(8, 10)+"日";
		  return tonotifyTime2;
	 }
}
