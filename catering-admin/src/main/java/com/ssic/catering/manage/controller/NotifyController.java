package com.ssic.catering.manage.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.admin.pageModel.DataGrid;
import com.ssic.catering.admin.pageModel.Json;
import com.ssic.catering.admin.service.UserServiceI;
import com.ssic.catering.base.dto.NotifyDto;
import com.ssic.catering.base.dto.QjyCateringUserDto;
import com.ssic.catering.base.eventBus.EventRegisterC;
import com.ssic.catering.base.pojo.PageHelper;
import com.ssic.catering.base.service.IMeetingService;
import com.ssic.catering.base.service.INotifyService;
import com.ssic.catering.base.service.IQjyCateringService;
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.dto.LTUserDto;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.game.common.service.impl.LiaoTianServiceImpl;
import com.ssic.util.UUIDGenerator;

@Controller
@RequestMapping("/notifyController")
public class NotifyController {

    private static final Logger log = Logger.getLogger(NotifyController.class);
    
    private static String sendMessageUser = "systemUser";  //推送信息人 默认系统管理员
    
    private static String sendMessageType = "message"; //推送信息类型 
    
	@Autowired
	private INotifyService notifyService;
	@Autowired
	private EventRegisterC eventRegister;
	@Autowired
	private IQjyCateringService qjyCateringService;
	@Autowired
	private IMeetingService  meetingService;
	
	@Autowired
	private UserServiceI userService;
	

	
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request){
		return "carte/notify/notify";
	}
	
	@RequestMapping("/addnotify")
	public String addnotify(HttpServletRequest request,NotifyDto notifyDto){
		return "carte/notify/notifyAdd";
	}
	
	@RequestMapping("/editnotify")
	public String editnotify(HttpServletRequest request,NotifyDto notifyDto){
		 NotifyDto nDto =  notifyService.findById(notifyDto.getId());
		 request.setAttribute("nDto", nDto);
		return "carte/notify/notifyEdit";
	}
	
	
	@RequestMapping("/findBy")
	@ResponseBody
	public List<NotifyDto> findBy(NotifyDto notifyDto){
		return notifyService.findBy(notifyDto);
	}
	
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(NotifyDto notifyDto,PageHelper ph, HttpServletRequest request){
	    log.info("parameters:notifyDto="+notifyDto+";ph="+ph);
	    
	    DataGrid dataGrid = new DataGrid();
	    
	    HttpSession session = request.getSession();
        if(session == null)
        {
            log.error("session不能为空");
            return dataGrid;
        }
        
	    List<ProjectDto> projects = userService.getProjectBySession(session);
	    if(CollectionUtils.isEmpty(projects))
	    {
	        log.error("该用户没有对应的projectId");
	        return dataGrid;
	    }
	    
	    List<String> projectIds = new ArrayList<>();
	    for(ProjectDto project:projects)
	    {
	        projectIds.add(project.getId());
	    }
	    
		List<NotifyDto> list = notifyService.findBy(notifyDto,projectIds,ph);
		int count = 0;
		if(!CollectionUtils.isEmpty(list))
		{
		    count = notifyService.findCountBy(notifyDto,projectIds);
		    
		  //添加对应的项目名称
	        for(NotifyDto item2: list)
	        {
	            for(ProjectDto item3: projects)
	            {
	                if(!StringUtils.isEmpty(item2.getProjId()) && item2.getProjId().equals(item3.getId()))
	                {
	                    item2.setProjectName(item3.getProjName());
	                }
	            }
	        }
	        
	        dataGrid.setRows(list);
	        dataGrid.setTotal(Long.valueOf(count));
		}
		
		return dataGrid;
	}
	
	@RequestMapping("/insertNotify")
	@ResponseBody
	public Json insertNotify(NotifyDto notifyDto, HttpServletRequest request){
		Json j = new Json();
		
		if(notifyDto.getName()==null || "".equals(notifyDto.getName())){
			j.setMsg("公告名称不能为空！");
			j.setSuccess(false);
			return j;
		}
		if(notifyDto.getListTitle()==null || "".equals(notifyDto.getListTitle())){
		    j.setMsg("列表标题不能为空！");
		    j.setSuccess(false);
		    return j;
	    }
		if(notifyDto.getListText()==null || "".equals(notifyDto.getListText())){
			j.setMsg("标题内容不能为空！");
			j.setSuccess(false);
			return j;
		}
//		if(notifyDto.getTitle()==null || "".equals(notifyDto.getTitle())){
//			j.setMsg("正文标题不能为空！");
//			j.setSuccess(false);
//			return j;
//		}
//		if(notifyDto.getText()==null || "".equals(notifyDto.getText())){
//			j.setMsg("正文内容不能为空！");
//		    j.setSuccess(false);
//		    return j;
//		}
		
		  HttpSession session = request.getSession();
	        if(session == null)
	        {
	            log.error("session不能为空");
	            j.setMsg("session不能为空");
	            j.setSuccess(false);
	            return j;
	        }
	        
		    List<ProjectDto> projects = userService.getProjectBySession(session);
		    if(CollectionUtils.isEmpty(projects))
		    {
		        log.error("该用户没有对应的projectId");
		        j.setMsg("该用户没有对应的projectId");
		        j.setSuccess(false);
		        return j;
		    }

		    if(projects.size()==1){
				notifyDto.setProjId(projects.get(0).getId());
		    }else{
		    	notifyDto.setProjId("");     //后台登陆用户为超管，projid为空
		    }
		    
		notifyDto.setId(UUIDGenerator.getUUID());
		notifyDto.setStat(1);
		notifyDto.setState(0);
		notifyDto.setCreateTime(new Date());
		notifyService.insertNotify(notifyDto);
		j.setMsg("插入公告成功！");
		j.setSuccess(true);
		return j;
	}
	
	@RequestMapping("/updateNotify")
	@ResponseBody
	public Json updateNotify(NotifyDto notifyDto){
		Json j = new Json();
		if(notifyDto.getId()==null || "".equals(notifyDto.getId())){
			j.setMsg("公告编码不能为空！");
			j.setSuccess(false);
			return j;
		}
		NotifyDto notifyDto2 = notifyService.findById(notifyDto.getId());
		//如果状态为1，已经发布的公告不能再修改
		if(notifyDto2.getState()==1){
			j.setMsg("公告已经发布不能修改！");
			j.setSuccess(false);
			return j;
		}

		if(notifyDto.getName()==null || "".equals(notifyDto.getName())){
			j.setMsg("公告名称不能为空！");
			j.setSuccess(false);
			return j;
		}
		if(notifyDto.getListTitle()==null || "".equals(notifyDto.getListTitle())){
		    j.setMsg("列表标题不能为空！");
		    j.setSuccess(false);
		    return j;
	    }
		if(notifyDto.getListText()==null || "".equals(notifyDto.getListText())){
			j.setMsg("标题内容不能为空！");
			j.setSuccess(false);
			return j;
		}
		if(notifyDto.getTitle()==null || "".equals(notifyDto.getTitle())){
			j.setMsg("正文标题不能为空！");
			j.setSuccess(false);
			return j;
		}
		if(notifyDto.getText()==null || "".equals(notifyDto.getText())){
			j.setMsg("正文内容不能为空！");
		    j.setSuccess(false);
		    return j;
		}
		
		
		notifyDto.setLastUpdateTime(new Date());
		notifyService.updateNotify(notifyDto);
		j.setMsg("更新公告成功！");
		j.setSuccess(true);
		return j;
	}
	
	@RequestMapping("/deleteNotify")
	@ResponseBody
	public Json deleteNotify(NotifyDto notifyDto){
		Json j = new Json();
		if(notifyDto.getId()==null || "".equals(notifyDto.getId())){
			j.setMsg("公告编码不能为空！");
			j.setSuccess(false);
			return j;
		}
		NotifyDto notifyDto2 = notifyService.findById(notifyDto.getId());
		//如果状态为1，已经发布的公告不能再修改
		if(notifyDto2.getState()==1){
			j.setMsg("公告已经发布不能修改！");
			j.setSuccess(false);
			return j;
		}
		notifyDto.setStat(0);
		notifyDto.setLastUpdateTime(new Date());
		notifyService.deleteNotify(notifyDto);
		j.setMsg("删除公告成功！");
		j.setSuccess(true);
		return j;
		
	}
	
	@RequestMapping("/publishNotify")
	@ResponseBody
	public Json publishNotify(NotifyDto notifyDto) throws UnsupportedEncodingException{
		Json j = new Json();
		if(notifyDto.getId()==null || "".equals(notifyDto.getId())){
			j.setMsg("公告ID不能为空!");
			j.setSuccess(false);
			return j;
		}
		NotifyDto noDto = notifyService.findById(notifyDto.getId());
		//验证texthtml不能为空
		if(noDto.getTextHtml()==null || "".equals(noDto.getTextHtml())){
			j.setMsg("正文不能为空!");
			j.setSuccess(false);
			return j;
		}
		if(noDto.getState()==1){
			j.setMsg("该公告已经发布！");
			j.setSuccess(false);
			return j;
		}
		
		notifyDto.setState(1);
		notifyDto.setLastUpdateTime(new Date());
		notifyDto.setNotifyTime(new Date());
		notifyService.updateNotify(notifyDto);
		
		//亲加云发送公告信息    给每个人推送
		//  查询出团餐项目下的所有人，给每个人发信息。
		List<ImsUsersDto> listuser= meetingService.findUserByProj(notifyDto.getProjId());
		
		//亲加云推送消息
//		if(listuser!=null && listuser.size()>0){
//			for(ImsUsersDto imsu:listuser){
//				 QjyCateringUserDto qjyCateringUserDto = new QjyCateringUserDto();
//				 List<String> toIdList = new ArrayList<String>();
//				 toIdList.add(imsu.getQjyAccount());
//				 qjyCateringUserDto.setToIdList(toIdList);
//				 String text= notifyDto.getListTitle();
//				 System.out.println("text====-="+text);
//				 qjyCateringUserDto.setText(text);
//				 eventRegister.getQjyEvent().post(qjyCateringUserDto); 
//			}
//		}
		
		//IM推送消息   messageType = message
		List<String> tolist = new ArrayList<String>();
		LTUserDto ltuser = new LTUserDto();
		ltuser.setFrom(sendMessageUser);
		ltuser.setMessage(notifyDto.getListTitle());
		ltuser.setMessageType(sendMessageType);
		if(!CollectionUtils.isEmpty(listuser)){
			for(ImsUsersDto imsudto : listuser){
				tolist.add(imsudto.getUserAccount());
			}
			ltuser.setTolist(tolist);
		}
		testSendMessage(ltuser);
		
		j.setMsg("发布公告成功!");
		j.setSuccess(true);
		return j;
	}
	
	@RequestMapping("/umeditFun")
	public String umeditFun(HttpServletRequest request,String id){
		request.setAttribute("notifyId", id);
		return "carte/umedit/umNotifys";
	}
	
	@RequestMapping("/insertNotifyInfo")
	@ResponseBody
	public Json insertNotifyInfo (NotifyDto notifyDto,String notifyId){
		Json j = new Json();
		if(notifyDto.getTextHtml()==null || "".equals(notifyDto.getTextHtml())){
			j.setMsg("正文不能为空！");
			j.setSuccess(false);
			return j;
		}
		if(notifyId==null || "".equals(notifyId)){
			j.setMsg("公告ID不能为空!");
			j.setSuccess(false);
			return j;
		}
		NotifyDto notifyDto2 = notifyService.findById(notifyId);
		//如果状态为1，已经发布的公告不能再修改
		if(notifyDto2.getState()==1){
			j.setMsg("公告已经发布不能修改！");
			j.setSuccess(false);
			return j;
		}
		
		notifyDto.setId(notifyId);
		notifyDto.setLastUpdateTime(new Date());
		notifyService.updateNotify(notifyDto);
		j.setMsg("更新正文成功!");
		j.setSuccess(true);
		return j;
	}
	
	@RequestMapping("/showText")
	public String showText(HttpServletRequest request,String id){
		NotifyDto notifyDto = notifyService.findById(id);
		request.setAttribute("nDto", notifyDto);
		 return "carte/notify/notifyShow";
	}
	
	@RequestMapping("/testSendMessage")
	@ResponseBody
	public void testSendMessage(LTUserDto lTUserDto){   //推送信息
		eventRegister.getQjyEvent().post(lTUserDto); 
	}
	
	
}
