package com.ssic.catering.manage.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.admin.pageModel.DataGrid;
import com.ssic.catering.admin.pageModel.Json;
import com.ssic.catering.admin.service.UserServiceI;
import com.ssic.catering.base.dto.AddressDto;
import com.ssic.catering.base.dto.MeetingAddressDto;
import com.ssic.catering.base.pojo.Address;
import com.ssic.catering.base.service.IAddressService;
import com.ssic.catering.base.service.IMeetingAddressService;
import com.ssic.catering.base.service.IMeetingService;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.util.UUIDGenerator;


@Controller
@RequestMapping("/meetingAddressController")
public class MeetingAddressController {

    private static final Logger log = Logger.getLogger(MeetingAddressController.class);
    
	@Autowired
	private IMeetingAddressService meetingAddressService;
	@Autowired
	private IAddressService addressService;
	@Autowired
	private IMeetingService meetingService;
	
	@Autowired
	private UserServiceI userService;
	
	@RequestMapping("/findBy")
	@ResponseBody
	public List<MeetingAddressDto> findBy(MeetingAddressDto meetingAddressDto){
		return meetingAddressService.findBy(meetingAddressDto);
	}
	
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request){
		List<AddressDto> list= meetingService.findLargeArea();
		 request.setAttribute("listAddDto", list);
		return "carte/meetingAddress/meetingAddress";
	}
	
	@RequestMapping("/addMeetingAddress")
	public String addMeetingAddress(HttpServletRequest request,MeetingAddressDto meetingAddressDto,String addressId){
		request.setAttribute("addressId", addressId);
		return "carte/meetingAddress/meetingAddressAdd";
	}
	
	@RequestMapping("/editMeetingAddress")
	public String editMeetingAddress(HttpServletRequest request,MeetingAddressDto meetingAddressDto){
		MeetingAddressDto maDto = meetingAddressService.findById(meetingAddressDto.getId());
		request.setAttribute("maDto", maDto);
		return "carte/meetingAddress/meetingAddressEdit";
	}
	
	
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(MeetingAddressDto meetingAddressDto,com.ssic.catering.base.pojo.PageHelper ph, HttpServletRequest request){
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
	    
		List<MeetingAddressDto> list = meetingAddressService.findBy(meetingAddressDto, projectIds, ph);
		
		int count =0;
		if(!CollectionUtils.isEmpty(list))
		{
		    List<AddressDto> addresses = addressService.findAll();
		    if(!CollectionUtils.isEmpty(addresses))
		    {
		        //会议室地址
		        for(MeetingAddressDto meetingAddress:list)
		        {
		            //所有区域
		            for(AddressDto address: addresses)
	                {
	                    if(meetingAddress.getLargeArea() !=null && meetingAddress.getLargeArea().equals(address.getId()))
	                    {
	                        meetingAddress.setLargeAreaName(address.getAddressName());
	                    }
	                    
	                    if(meetingAddress.getProvince() !=null && meetingAddress.getProvince().equals(address.getId()))
                        {
                            meetingAddress.setProvinceName(address.getAddressName());
                        }
	                    
	                    if(meetingAddress.getCity() !=null && meetingAddress.getCity().equals(address.getId()))
                        {
                            meetingAddress.setCityName(address.getAddressName());
                        }
	                }
		            
		            //所有的 项目
		            for(ProjectDto project: projects)
                    {
		                if(meetingAddress.getProjId() != null && meetingAddress.getProjId().equals(project.getId()))
		                {
		                    meetingAddress.setProjectName(project.getProjName());
		                }
                    }
		        }
		      
		    }
		    
		    
		    count = meetingAddressService.findCountBy(meetingAddressDto, projectIds);
		    
		    dataGrid.setRows(list);
	        dataGrid.setTotal(Long.valueOf(count));
		}
		
		return dataGrid;
	}
	
	
	@RequestMapping("/findById")
	@ResponseBody
	public MeetingAddressDto findById(String id){
		return meetingAddressService.findById(id);
	}
	
	@RequestMapping("/insertMeetingA")
	@ResponseBody
	public Json insertMeetingA(MeetingAddressDto meetingAddressDto,HttpServletRequest request){
		Json j = new Json();
		if(meetingAddressDto.getName()==null || "".equals(meetingAddressDto.getName())){
			j.setMsg("会议室名称不能为空!");
			j.setSuccess(false);
			return j;
		}
		if(meetingAddressDto.getCity()==null || "".equals(meetingAddressDto.getCity())){
			j.setMsg("会议室所在城市不能为空!");
			j.setSuccess(false);
			return j;
		}
		
	    HttpSession session = request.getSession();
	    if(session == null)
	    {
	    	j.setMsg("session不能为空");
	    	j.setSuccess(false);
	    	return j;
	    }
	    
	    List<ProjectDto> pjds = userService.getProjectBySession(session);
	    if(pjds==null || pjds.size()==0){
	    	j.setMsg("项目ID不能为空");
	    	j.setSuccess(false);
	    	return j;
	    }
		
	    if(pjds.size()==1){
	    	meetingAddressDto.setProjId(pjds.get(0).getId());
	    }else{
	    	meetingAddressDto.setProjId("");    //后台登陆用户为超管，projid为空
	    }
		
		//查询这个城市ID对应的省份大区
		Address addressDto_pro= addressService.findAddressById(meetingAddressDto.getCity());
		if(addressDto_pro!=null){
			String province = addressDto_pro.getParentId();
			meetingAddressDto.setProvince(province);
		    Address address_area=	addressService.findAddressById(province);
		  if(address_area!=null){
			   String area = address_area.getParentId();
			   meetingAddressDto.setLargeArea(area);
		  }
		}
		
		meetingAddressDto.setId(UUIDGenerator.getUUID());
		meetingAddressDto.setStat(1);
		meetingAddressDto.setCreateTime(new Date());
		meetingAddressService.insertMeetingA(meetingAddressDto);
		j.setMsg("新增会议室成功！");
		j.setSuccess(true);
		return j;
	}
	
	@RequestMapping("/updateMeetingA")
	@ResponseBody
	public Json updateMeetingA(MeetingAddressDto meetingAddressDto){
		Json j = new Json();
		
		if(meetingAddressDto.getName()==null || "".equals(meetingAddressDto.getName())){
			j.setMsg("会议室名称不能为空");
			j.setSuccess(false);
			return j;
		}
		if(meetingAddressDto.getId()==null || "".equals(meetingAddressDto.getId())){
			j.setMsg("会议室ID不能为空！");
			j.setSuccess(false);
			return j;
		}
		meetingAddressDto.setLastUpdateTime(new Date());
		meetingAddressService.updateMeetingA(meetingAddressDto);
		j.setMsg("更新会议室成功！");
		j.setSuccess(true);
		return j;
	}
	
	@RequestMapping("/deleteMeetingA")
	@ResponseBody
	public Json deleteMeetingA(MeetingAddressDto meetingAddressDto){
		Json j = new Json();
		if(meetingAddressDto.getId()==null || "".equals(meetingAddressDto.getId())){
			j.setMsg("会议室ID不能为空！");
			j.setSuccess(false);
			return j;
		}
		meetingAddressDto.setStat(0);
		meetingAddressDto.setLastUpdateTime(new Date());
		meetingAddressService.updateMeetingA(meetingAddressDto);
		j.setMsg("删除会议室成功！");
		j.setSuccess(true);
		return j;
	}
	
	// 获取当前的所有大区
	public List<AddressDto> findLargeArea(){
		List<AddressDto> list= meetingService.findLargeArea();
		 return list;
	}
	// 根据大区获取所有的省
	// 根据省获取所有的市
	@RequestMapping("/findChildId")
	@ResponseBody
	public List<AddressDto> findChildId(String id){
		List<AddressDto> list = addressService.findChildListByParentId(id);
		return list;
	}
	
}
