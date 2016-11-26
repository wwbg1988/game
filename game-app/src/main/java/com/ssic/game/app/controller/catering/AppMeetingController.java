package com.ssic.game.app.controller.catering;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.record.BoolErrRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.base.dto.AddressDto;
import com.ssic.catering.base.dto.DeptMeetingDto;
import com.ssic.catering.base.dto.MeetRecordUserDto;
import com.ssic.catering.base.dto.MeetUserDeptDto;
import com.ssic.catering.base.dto.MeetingAddressDto;
import com.ssic.catering.base.dto.MeetingAllUserDto;
import com.ssic.catering.base.dto.MeetingDto;
import com.ssic.catering.base.dto.MeetingRecordDto;
import com.ssic.catering.base.dto.MeetingUserDto;
import com.ssic.catering.base.eventBus.EventRegisterC;
import com.ssic.catering.base.pojo.Address;
import com.ssic.catering.base.service.IAddressService;
import com.ssic.catering.base.service.IMeetingAddressService;
import com.ssic.catering.base.service.IMeetingService;
import com.ssic.catering.base.service.IMeetingUserService;
import com.ssic.catering.common.util.AppResponse;
import com.ssic.game.app.controller.dto.AppMeetAddDto;
import com.ssic.game.app.service.ISendInfoService;
import com.ssic.game.common.dao.DeptUserDao;
import com.ssic.game.common.dto.DeptDto;
import com.ssic.game.common.dto.DeptUsersDto;
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.dto.MeetingPerDto;
import com.ssic.game.common.dto.ProjectUsersDto;
import com.ssic.game.common.dto.WorkSearchDto;
import com.ssic.game.common.service.DeptUserService;
import com.ssic.game.common.service.IDeptService;
import com.ssic.game.common.service.IImsUserService;
import com.ssic.game.common.service.ProjectServices;
import com.ssic.game.common.util.CateringProjectG;
import com.ssic.util.UUIDGenerator;
import com.ssic.util.model.Response;



@Controller
@RequestMapping("/appMeetingController")
public class AppMeetingController {

	@Autowired
	private IMeetingService meetingService;
	@Autowired
	private IMeetingAddressService   meetingAddressService;
	@Autowired
	private IMeetingUserService   meetingUserService;
	@Autowired
	private IDeptService deptService;
    @Autowired
	private IImsUserService imsUserService;
    @Autowired
    private DeptUserService deptUserService;
    @Autowired
    private ISendInfoService sendInfoService;
    @Autowired
    private IAddressService addressService;
    @Autowired
    private ProjectServices projectServices;
	
    
    private static String cateringDept = CateringProjectG.cater_dept;   //团餐人事部财务部
    
    private static String waihuiDept = CateringProjectG.waihui_dept;   // 外会人事部财务部
    
    private static String kpsDept = CateringProjectG.kps_dept;  //人事部财务部  康帕斯
    
    private static String jujuDept = CateringProjectG.juju_dept;  //人事部 聚运动
    
    private static String cater_project = CateringProjectG.cater_project;   //团餐项目
    
    private static String patiwaihui_project = CateringProjectG.patiwaihui_project;  // 趴体外会项目
    
    private static String kps_project = CateringProjectG.kps_project;   //康帕斯项目
    
    private static String juju_project = CateringProjectG.juju_project;   //聚运动项目
    

	/**
	 * 
	 * 查询所有部门和部门下的人员
	 * **/
	
	@RequestMapping(value ="/findMUser.do", method = {RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Response<List<MeetUserDeptDto>>   findMUser(String userId){
		
		Response<List<MeetUserDeptDto>> result = new Response<List<MeetUserDeptDto>>();
		String projId="";
		Map<String, Object> mapproj = findProjByUserId(userId);
		Boolean projState = (Boolean) mapproj.get("SUCCESS");
		if(projState){
			ProjectUsersDto projectUsersDto = (ProjectUsersDto) mapproj.get("projectUsersDto");
			projId =projectUsersDto.getProjId();
		}else{
			 result.setMessage("项目ID不存在！");
			 result.setStatus(AppResponse.RETURN_FAILE);
			 return result;
		}
		
		
		List<MeetUserDeptDto> list = meetingUserService.findUserDept(userId,projId);
		
		if(list!=null && list.size()>0){
			 result.setData(list);
			 result.setMessage("部门人员返回成功！");
			 result.setStatus(AppResponse.RETURN_SUCCESS);
		 }else{
			 result.setMessage("部门人员不存在！");
			 result.setStatus(AppResponse.RETURN_FAILE);
		 }
		
		return result;		
	}
	/**
	 * 查询出所有的会议室
	 * 查询出所有的会议室并且查询出该会议室对应的省份城市大区
	 * @throws ParseException 
	 * 
	 * **/
	@RequestMapping(value ="/findMAddress.do", method = {RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Response<List<AppMeetAddDto>> findMAddress(String projId,String starTime,String endTime) throws ParseException{
		Response<List<AppMeetAddDto>>  result = new Response<List<AppMeetAddDto>>();
		
		if(projId==null || "".equals(projId)){
			result.setMessage("项目ID不能为空!");
			result.setStatus(AppResponse.RETURN_FAILE);
			return result;
		}
		
		if(starTime==null || "".equals(starTime)){
			result.setMessage("会议开始时间不能为空");
			result.setStatus(AppResponse.RETURN_FAILE);
			return result;
		}
		
		if(endTime==null || "".equals(endTime)){
			result.setMessage("会议结束时间不能为空");
			result.setStatus(AppResponse.RETURN_FAILE);
			return result;
		}
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");//小写的mm表示的是分钟  
		Date DstarTime=  sdf.parse(starTime);
		Date DendTime = sdf.parse(endTime);
		Date nowDate = new Date();
		
		 //2015-08-10
		 //查询 年月日  的该会议室 是否有约
		//开始时间 结束时间 的 yyyy-mm-dd   必须是同一个
		 String starTime2 = starTime.substring(0, 10);
		 String endTime2 = endTime.substring(0, 10);
		 if(!starTime2.equals(endTime2)){
			 result.setMessage("会议室的开始时间结束时间必须是同一天！");
			 result.setStatus(AppResponse.RETURN_FAILE);
			 return result;
		 }
		 
		 SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd");
		//查询出会议起始时间结束时间有交叉的会议室
		 List<MeetingDto> listm = meetingService.findAddByStarEndTime(projId,DstarTime,DendTime);
		 List<String> listjx = new ArrayList<String>();
		 if(listm!=null && listm.size()>0){
			 for(MeetingDto meetingDtojx :listm){
				 listjx.add(meetingDtojx.getAddressId());
			 }
		 }
		

			//把list的大区分组，省份分组，城市分组，会议室分组
			List<AppMeetAddDto> list = new ArrayList<AppMeetAddDto>();
			List<MeetingAddressDto> list_area= meetingService.findGroupArea(projId);
			for(MeetingAddressDto maddto1:list_area){
				String larg = maddto1.getLargeArea();
				String largName = maddto1.getLargeAreaName();
				//判断这个大区下的会议室是否全部被占用，如果全部被占用则不显示该大区
				MeetingAddressDto meetingAddressDtoA = new MeetingAddressDto();
				meetingAddressDtoA.setLargeArea(larg);
				meetingAddressDtoA.setProjId(projId);
				List<MeetingAddressDto> listAd = meetingAddressService.findBy(meetingAddressDtoA);
				AppMeetAddDto appDto1= new AppMeetAddDto();
				if(listAd!=null && listAd.size()>0){
					//该大区下有被占用的会议室
					int flag=0;  //全部被占用
					for(MeetingAddressDto meetingAddressDto11 : listAd){
						if(listjx.contains(meetingAddressDto11.getId())){
							//被占用
						}else{
							//不被占用
							flag=1;
							break;
						}
					}
					//如果不是全部被占用,展示该大区
					if(flag==1){
						//添加大区
						appDto1.setAddressID(larg);
						appDto1.setAddressName(largName);
						list.add(appDto1);
					}
				}else{
					//该大区下没有被占用的会议室
					//添加大区
					appDto1.setAddressID(larg);
					appDto1.setAddressName(largName);
					list.add(appDto1);
				}
				
				List<AppMeetAddDto> list1 = new ArrayList<AppMeetAddDto>();
			    //根据大区分组查询省份
				List<MeetingAddressDto> list2= meetingService.findGroupPer(larg);
				for(MeetingAddressDto maddto2:list2){
					String province = maddto2.getProvince();
					String provinceName = maddto2.getProvinceName();
					//判断这个省份下的会议室是否全部被占用，如果全部被占用则不显示该省
					//获取该省下面的全部会议室
					MeetingAddressDto meetingAddressDtoB = new MeetingAddressDto();
					meetingAddressDtoB.setProvince(province);
					meetingAddressDtoB.setProjId(projId);
					List<MeetingAddressDto> listBd = meetingAddressService.findBy(meetingAddressDtoB);
					AppMeetAddDto amadto2 = new AppMeetAddDto();
					if(listBd!=null && listBd.size()>0){
						//该省下有被占用的会议室
						int flagB=0;  //全部被占用
						for(MeetingAddressDto meetingAddressDto22 : listBd){
							if(listjx.contains(meetingAddressDto22.getId())){
								//被占用
							}else{
								//不被占用
								flagB=1;
								break;
							}
						}
						//如果不是全部被占用,展示该省
						if(flagB==1){
							//添加省
							amadto2.setAddressID(province);
							amadto2.setAddressName(provinceName);
							list1.add(amadto2);
						}
					}else{
						//添加省
						amadto2.setAddressID(province);
						amadto2.setAddressName(provinceName);
						list1.add(amadto2);
					}

					List<AppMeetAddDto> list3 = new ArrayList<AppMeetAddDto>();
					//根据省份查城市
					List<MeetingAddressDto> list4=  meetingService.findGroupCity(province);
					for(MeetingAddressDto maddto4:list4){
						String city = maddto4.getCity();
						String cityName = maddto4.getCityName();
						AppMeetAddDto amadto4 = new AppMeetAddDto();
						//判断这个城市下的会议室是否全部被占用，如果全部被占用则不显示该城市
						//获取该城市下面的全部会议室
						MeetingAddressDto meetingAddressDtoC = new MeetingAddressDto();
						meetingAddressDtoC.setCity(city);
						meetingAddressDtoC.setProjId(projId);
						List<MeetingAddressDto> listCd = meetingAddressService.findBy(meetingAddressDtoC);
						
						if(listCd!=null && listCd.size()>0){
							//该省下有被占用的会议室
							int flagC=0;  //全部被占用
							for(MeetingAddressDto meetingAddressDto33 : listCd){
								if(listjx.contains(meetingAddressDto33.getId())){
									//被占用
								}else{
									//不被占用
									flagC=1;
									break;
								}
							}
							//如果不是全部被占用,展示该省
							if(flagC==1){
								//添加城市
								amadto4.setAddressID(city);
								amadto4.setAddressName(cityName);
								list3.add(amadto4);
							}
						}else{
							//添加城市
							amadto4.setAddressID(city);
							amadto4.setAddressName(cityName);
							list3.add(amadto4);
						}
						
						MeetingAddressDto madto5 = new MeetingAddressDto();
						List<AppMeetAddDto> list7 = new ArrayList<AppMeetAddDto>();
						madto5.setCity(city);
						//根据城市查会议室
						List<MeetingAddressDto> list6=  meetingAddressService.findBy(madto5);
						for(MeetingAddressDto madto6:list6){
							String id6=  madto6.getId();
							String name6 =  madto6.getName();
							//String address6 = madto6.geta
							//时间有交叉的会议室不显示
							if(listjx!=null && listjx.size()>0){
								if(listjx.contains(id6)){
								
								}else{
									AppMeetAddDto amadto6 = new AppMeetAddDto();
									amadto6.setAddressID(id6);
									amadto6.setAddressName(name6);
									list7.add(amadto6);
								}
							}else{
								AppMeetAddDto amadto6 = new AppMeetAddDto();
								amadto6.setAddressID(id6);
								amadto6.setAddressName(name6);
								list7.add(amadto6);
							}
						}
						amadto4.setList(list7);
					}
					amadto2.setList(list3);
				}
				appDto1.setList(list1);
		//		appk.setList(list1);
			}
            if(list.size()==0){
            	result.setData(list);
    			result.setMessage("会议室为空！");
    			result.setStatus(AppResponse.RETURN_FAILE);
            }else{
            	result.setData(list);
    			result.setMessage("会议室返回成功！");
    			result.setStatus(AppResponse.RETURN_SUCCESS);
            }
			
			
		return result;
	}
	
	
	/**
	 * 添加会议
	 * 
	 * **/
	@RequestMapping(value ="/addMeeting.do", method = {RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Response<String> addMeeting(String title,String starTime ,String endTime,String addressId,String[] users,String userId) throws ParseException{
		Response<String> result = new Response<String>();
		
		//示例参数
//		title = "卫生检查会议";
//		starTime = "2015-08-11 13:28";
//		endTime = "2015-08-11 14:30";
//		addressId = "1";
//		String[] a = {"cfa5bb6d-4b73-40ba-abdd-167167081821","2530fa8b-216a-4a90-b121-001527aa8765"};
//		users =a;
		if(addressId==null || "".equals(addressId)){
			result.setMessage("会议室不能为空");
			result.setStatus(AppResponse.RETURN_FAILE);
			return result;
		}
		//参数addressId 是name需要转成会议室的ID
		MeetingAddressDto meetingAddressDto1 = new MeetingAddressDto();
		meetingAddressDto1.setName(addressId);
		List<MeetingAddressDto> listm1 =meetingAddressService.findBy(meetingAddressDto1);
		if(listm1!=null && listm1.size()>0){
			addressId = listm1.get(0).getId();
		}else{
			result.setMessage("会议室不存在!");
			result.setStatus(AppResponse.RETURN_FAILE);
			return result;
		}
		String projId="";
		Map<String, Object> mapproj = findProjByUserId(userId);
		Boolean projState = (Boolean) mapproj.get("SUCCESS");
		if(projState){
			ProjectUsersDto projectUsersDto = (ProjectUsersDto) mapproj.get("projectUsersDto");
			projId =projectUsersDto.getProjId();
		}else{
			 result.setMessage("项目ID不存在！");
			 result.setStatus(AppResponse.RETURN_FAILE);
			 return result;
		}
		
		
		
		if(title==null || "".equals(title)){
			result.setMessage("会议标题不能为空！");
			result.setStatus(AppResponse.RETURN_FAILE);
			return result;
		}
		
		if(title.length()>15){
			result.setMessage("会议标题长度不能大于15！");
			result.setStatus(AppResponse.RETURN_FAILE);
			return result;
		}
		
		if(userId==null || "".equals(userId)){
			result.setMessage("登录用户ID不能为空！");
			result.setStatus(AppResponse.RETURN_FAILE);
			return result;
		}
		
		if(users==null || users.length==0){
			result.setMessage("会议人员不能为空！");
			result.setStatus(AppResponse.RETURN_FAILE);
			return result;
		}
		
		if(validTimestamp(starTime)==false){
			result.setMessage("会议开始时间格式错误！");
			result.setStatus(AppResponse.RETURN_FAILE);
			return result;
		}
		
		if(validTimestamp(endTime)==false){
			result.setMessage("会议结束时间格式错误！");
			result.setStatus(AppResponse.RETURN_FAILE);
			return result;
		}

		if(addressId==null || "".equals(addressId)){
			result.setMessage("会议室不能为空！");
			result.setStatus(AppResponse.RETURN_FAILE);
			return result;
		}
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");//小写的mm表示的是分钟  
		Date DstarTime=  sdf.parse(starTime);
		Date DendTime = sdf.parse(endTime);
		Date nowDate = new Date();
		if(DstarTime.before(nowDate)){
			result.setMessage("会议开始时间不能小于当前时间！");
			result.setStatus(AppResponse.RETURN_FAILE);
			return result;
		}
		
		if(DstarTime.getTime()>DendTime.getTime()){
			result.setMessage("会议开始时间不能大于会议结束时间！");
			result.setStatus(AppResponse.RETURN_FAILE);
			return result;
		}
		
		
		//校验 能不能预约该会议室
		
		//开始时间 结束时间 的 yyyy-mm-dd   必须是同一个
		 String starTime2 = starTime.substring(0, 10);
		 String endTime2 = endTime.substring(0, 10);
		 if(!starTime2.equals(endTime2)){
			 result.setMessage("会议室的开始时间结束时间必须是同一天！");
			 result.setStatus(AppResponse.RETURN_FAILE);
			 return result;
		 }
		 
		 //2015-08-10
		 //查询 年月日  的该会议室 是否有约
		 
		 SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd");
		 Date dstarTime2 = sdf2.parse(starTime2);
		 //会议室  年月日  是不是已经存在
		 int ymdExist = meetingService.findNYR(addressId,dstarTime2);
		 if(ymdExist==0){
			 //之前不存在预约， 可以预定会议室
			 createMeeting(addressId,DstarTime,DendTime,title,users,userId,projId);
		     result.setMessage("预定会议室成功");
			 result.setStatus(AppResponse.RETURN_SUCCESS);
			 return result;
		 }else{
			 //之前该会议室存在预约
			 //校验  该会议室  开始时间为年月日  时间不交叉
			 int flagDate = meetingService.findStarEndTime(DstarTime, DendTime,addressId,dstarTime2,projId);
			 if(flagDate==0){
				 //当天会议室时间不交叉
				 createMeeting(addressId,DstarTime,DendTime,title,users,userId,projId);
				 
				 result.setMessage("会议室预订成功！");
				 result.setStatus(AppResponse.RETURN_SUCCESS);
				 return result;
			 }else{
				 //当天会议室时间交叉 , 查询出当天该会议室的安排 提示
				 MeetingDto meetingDto3 = new MeetingDto();
				 meetingDto3.setAddressId(addressId);
				 meetingDto3.setNyrDate(dstarTime2);
				 List<MeetingDto> listMdto = meetingService.findNYRMeet(meetingDto3);
				 String addressInfo = "";
				 if(listMdto!=null && listMdto.size()>0){
					 for(MeetingDto mDto4:listMdto){
						 String  addressname4 =   mDto4.getAddressName();
						 String startime4 = sdf.format(mDto4.getMStarTime());
						 String endTime4 = sdf.format(mDto4.getMEndTime());
						 String meetingName4 = mDto4.getName();
						 addressInfo = addressInfo + "会议:["+meetingName4 +"] 开始时间:[" + startime4 + "] 结束时间:["+endTime4+"] 会议室:["+addressname4+"]" +",";
					 }
				 }
				 
				 result.setMessage("会议室已经有人预定，请修改时间重新预订！");
				 result.setData(addressInfo);
				 result.setStatus(AppResponse.MEETING_TIME_CROSSING);
				 return result;
			 }
		 }
	}
	
	//查看该用户是否有查看会议记录的权限（“我的”，“部门”，“全部”）
	@RequestMapping(value ="/checkMeeting.do", method = {RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
    public Response<MeetingPerDto> checkMeeting(String userId, String projId,String mdeptCode ){
    	Response<MeetingPerDto> result = new Response<MeetingPerDto>();
    	//  caterType   QINGJIA=1;BAOXIAO=2;CHUCHAI=3; HUIYI=4; BAOGAO=5; XINWEN=6;JINGGAO=7;   
    	//   团餐财务部	b58bce24-1a4e-4ac4-8b2c-3da177172f72	泰勒  0607
    	//   团餐人事部	243bfa85-7bbd-47e7-a216-85dbe2e6bbff	特丽  0606
    	
    	MeetingPerDto meetingPerDto =  deptService.checkMeeting(userId, projId, mdeptCode);
    	String errorCode = meetingPerDto.getErrorCode();
    	String message = meetingPerDto.getMessage();
    	 if("210".equals(errorCode)){
    		 result.setMessage(message);
    		 result.setStatus(Integer.parseInt(errorCode));
    	 }else{
    		 result.setData(meetingPerDto);
    		 result.setMessage(message);
    		 result.setStatus(Integer.parseInt(errorCode));
    	 }
    	return result;
    }
	
	//获取“我的”会议记录
	@RequestMapping(value ="/findMyMeeting.do", method = {RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Response<List<MeetingRecordDto>> findMyMeeting(String userId) throws ParseException{
		Response<List<MeetingRecordDto>> result = new Response<List<MeetingRecordDto>>();
		List<MeetingRecordDto> listmr = new ArrayList<MeetingRecordDto>();
		if(userId==null || "".equals(userId)){
			result.setMessage("用户ID不能为空");
			result.setStatus(AppResponse.RETURN_FAILE);
			return result;
		}
		
		//  查询 该用户当天参加的 会议信息
		MeetingDto meetingDto = new MeetingDto();
		meetingDto.setMuserId(userId);
		
	    SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd");
	    SimpleDateFormat sdf3=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date today = sdf2.parse(sdf2.format(new Date()))  ;
		//meetingDto.setCreateTime(today);
		meetingDto.setMStarTime(today);
		
		//获取项目ID
		String projId="";
		Map<String, Object> mapproj = findProjByUserId(userId);
		Boolean projState = (Boolean) mapproj.get("SUCCESS");
		if(projState){
			ProjectUsersDto projectUsersDto = (ProjectUsersDto) mapproj.get("projectUsersDto");
			projId =projectUsersDto.getProjId();
		}else{
			 result.setMessage("项目ID不存在！");
			 result.setStatus(AppResponse.RETURN_FAILE);
			 return result;
		}
		meetingDto.setProjId(projId);
		
		//先查询   该用户当天的会议ID

		List<MeetingRecordDto> listm=  meetingService.findMyMeet(meetingDto);
		if(listm!=null && listm.size()>0){
			for(MeetingRecordDto mrDto:listm){
				String creatUId=  mrDto.getCreateUserID();
				String creatUName = mrDto.getCreatUName()  ;   //会议创建者姓名
				String dName = mrDto.getDName();   //会议室名称
				String mName = mrDto.getMName() ;  //会议名称
				String mid = mrDto.getMid() ; //会议编号
				Date mStarTime = mrDto.getMStarTime(); //会议开始时间
				Date mEndTime = mrDto.getMEndTime(); //会议结束时间
				String mst = sdf3.format(mStarTime);
				String met = sdf3.format(mEndTime);
				
			   // 获取会议时间段   08-12 12:00--15:00
				String mstyr = mst.substring(5, mst.length());
				String methm = met.substring(11,met.length());
				String starEndTime = mstyr +"--"+methm;
				mrDto.setStarEndTime(starEndTime);
			   // 当前时间与会议结束时间比较   ：
			   // 当前时间大于会议结束时间   	会议结束isFinish=0  
			   // 当前时间大于开始时间小于结束时间  会议进行中isFinish=1 
			   // 当前时间小于开始时间    会议未开始   isFinish=2   算出还有多久开始
				Map map = compareDate(mStarTime,mEndTime);
				int isFinish = (int) map.get("isFinish");
				String toStarTime = (String) map.get("toStarTime");
				mrDto.setIsFinish(isFinish);
				mrDto.setToStarTime(toStarTime);
				// isInMeeting  当前用户能否参加这个会议     0 不能参加，不显示参加拒绝界面   1能参加，显示参加拒绝界面
				//  当前用户等于会议创建者ID则不能参加
				if(userId.equals(creatUId)||isFinish==0){
					mrDto.setIsInMeeting(0);
				}else{
					mrDto.setIsInMeeting(1);
				}
				//如果该用户已经参加则不能在参加
				int ustate = mrDto.getUstate();
				if(ustate!=2){
					mrDto.setIsInMeeting(0);
				}
				
				//根据会议ID查询参加会议的人员 uNames
				String uNames="";
				MeetingUserDto muDto = new MeetingUserDto();
				muDto.setMeetingId(mid);
				muDto.setStat(1);
				List<MeetingUserDto> listmudto = meetingUserService.findBy(muDto);
				if(listmudto!=null && listmudto.size()>0){
					for(MeetingUserDto muDto2:listmudto){
						String userId2 = muDto2.getUserId();
						ImsUsersDto userdto2= imsUserService.findByUserId(userId2);
						String userName2 = userdto2.getName();
						uNames = uNames + userName2 +",";
					}
					uNames = uNames.substring(0, uNames.length()-1);
				}
				mrDto.setUNames(uNames);
				listmr.add(mrDto);
			}
			
			result.setData(listmr);
			result.setMessage("我的会议记录返回成功");
			result.setStatus(AppResponse.RETURN_SUCCESS);
			return result;
		}else{
			result.setMessage(sdf2.format(new Date())+"  用户今天没有会议！");
			result.setStatus(AppResponse.RETURN_SUCCESS);
			return result;
		}
	}
	
	//用户点击参与或者拒绝  inMeetingType 0 拒绝  1 参加
	@RequestMapping(value ="/editInMeeting.do", method = {RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Response<String> editInMeeting(String meetingId,String userId,int inMeetingType){
		Response<String> result = new Response<String>();
		if(meetingId==null || "".equals(meetingId)){
			result.setMessage("会议编号不能为空!");
			result.setStatus(AppResponse.RETURN_FAILE);
			return result;
		}
		if(userId==null || "".equals(userId)){
			result.setMessage("用户编号不能为空!");
			result.setStatus(AppResponse.RETURN_FAILE);
			return result;
		}
		 MeetingUserDto meetingUserDto = new MeetingUserDto();
		 meetingUserDto.setMeetingId(meetingId);
		 meetingUserDto.setUserId(userId);
		if(inMeetingType==1){
			//参加
			meetingUserDto.setState(0);
			meetingUserService.updateInMeeting(meetingUserDto);
			result.setMessage("参加会议成功！");
			result.setStatus(AppResponse.RETURN_SUCCESS);
			return result;
		}else if(inMeetingType==0){
			//拒绝
			meetingUserDto.setState(1);
			meetingUserService.updateInMeeting(meetingUserDto);
			result.setMessage("拒绝参加会议成功!");
			result.setStatus(AppResponse.RETURN_SUCCESS);
			return result;
		}else{
			result.setMessage("参加会议类型只能为0或1");
			result.setStatus(AppResponse.RETURN_FAILE);
			return result;
		}
	}
	
	//查看部门的会议记录
	@RequestMapping(value ="/findDeptMeeting.do", method = {RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Response<DeptMeetingDto> findDeptMeeting(String userId) throws ParseException{
		Response<DeptMeetingDto> result = new Response<DeptMeetingDto>();
		if(userId==null || "".equals(userId)){
			result.setMessage("用户ID不能为空");
			result.setStatus(AppResponse.RETURN_FAILE);
			return result;
		}
		DeptMeetingDto dept2= new DeptMeetingDto();
		
		//获取项目ID
				String projId="";
				Map<String, Object> mapproj = findProjByUserId(userId);
				Boolean projState = (Boolean) mapproj.get("SUCCESS");
				if(projState){
					ProjectUsersDto projectUsersDto = (ProjectUsersDto) mapproj.get("projectUsersDto");
					projId =projectUsersDto.getProjId();
				}else{
					 result.setMessage("项目ID不存在！");
					 result.setStatus(AppResponse.RETURN_FAILE);
					 return result;
				}
				
		
		//该用户是否有查看部门的权限  isDept 0没有 1有
		MeetingPerDto meetingPerDto = new MeetingPerDto();
		if(cater_project.equals(projId)){
			meetingPerDto =  deptService.checkMeeting(userId, projId, cateringDept);
		}else if(patiwaihui_project.equals(projId)){
			meetingPerDto =  deptService.checkMeeting(userId, projId, waihuiDept);
		}else if(kps_project.equals(projId)){
			meetingPerDto = deptService.checkMeeting(userId, projId, kpsDept);
		}else if(juju_project.equals(projId)){
			meetingPerDto = deptService.checkMeeting(userId, projId, jujuDept);
		}
		
		
		int isDept = meetingPerDto.getIs_dept();
		if(isDept==0){
			//不是部门管理员
			result.setMessage("您没有权限查看部门");
			result.setStatus(AppResponse.RETURN_FAILE);
			return result;
		}
		
		//部门下的全部会议记录
		List<MeetingRecordDto> listmrdto = new ArrayList<MeetingRecordDto>();
		//部门下的全部用户
		List<MeetRecordUserDto> listusers = new ArrayList<MeetRecordUserDto>();
		 SimpleDateFormat sdf3=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		//查询该用户所在的部门
		DeptUsersDto deptUsersDto = new DeptUsersDto();
		deptUsersDto.setUserId(userId);
		deptUsersDto.setProjId(projId);
	    List<DeptUsersDto> listdeptuser=	deptUserService.findAllBy(deptUsersDto);
		if(listdeptuser!=null && listdeptuser.size()>0){
				  String deptId2 = listdeptuser.get(0).getDeptId();
				  DeptDto deptdto2= deptService.findById(deptId2);
				  String deptName2 = deptdto2.getDeptName();
				 //查询该部门下所有的用户
				  DeptUsersDto deptUsersDto2 = new DeptUsersDto();
				  deptUsersDto2.setDeptId(deptId2);
				  List<DeptUsersDto> listdud2=  deptUserService.findAllBy(deptUsersDto2);
				  if(listdud2!=null && listdud2.size()>0){
					  for(DeptUsersDto deptu3:listdud2){
						  String userId3 = deptu3.getUserId();
						  ImsUsersDto userdto3 = imsUserService.findByUserId(userId3);
						  String userName3 = userdto3.getName();
						  deptu3.setUserName(userName3);
						  
						  MeetRecordUserDto musdto4 = new MeetRecordUserDto();
						  musdto4.setUserId(userId3);
						  musdto4.setUserName(userName3);
						  listusers.add(musdto4);  
					  }
				  }
				  
				  dept2.setListusers(listusers);
				  // 查询该部门下当天所有的会议
				  SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd");
				  Date today = sdf2.parse(sdf2.format(new Date()))  ;
				  List<MeetingRecordDto> listmrdto2 = meetingService.findDeptMeeting(deptId2,today);
				  System.out.println("listmrdto2=="+listmrdto2);
				  
				  if(listmrdto2!=null && listmrdto2.size()>0){
					  for(MeetingRecordDto mrDto:listmrdto2){
						    Date mStarTime = mrDto.getMStarTime(); //会议开始时间
							Date mEndTime = mrDto.getMEndTime(); //会议结束时间
							String mst = sdf3.format(mStarTime);
							String met = sdf3.format(mEndTime);
							//查询会议创建人
							if(mrDto.getCreateUserID()!=null){
							 ImsUsersDto imsUsersDto_1 =	imsUserService.findByUserId(mrDto.getCreateUserID());
							 String creatNa_1= imsUsersDto_1.getName();
							 mrDto.setCreatUName(creatNa_1);
							}
							
						   //查询会议室
							String addressId = mrDto.getAddressId();
						    MeetingAddressDto meetingAddressDto = meetingAddressService.findById(addressId);
						    if(meetingAddressDto!=null){
						    	String addressName = meetingAddressDto.getName();
						    	mrDto.setDName(addressName);
						    }
						   // 获取会议时间段   08-12 12:00--15:00
							String mstyr = mst.substring(5, mst.length());
							String methm = met.substring(11,met.length());
							String starEndTime = mstyr +"--"+methm;
							mrDto.setStarEndTime(starEndTime);
							
							Map map = compareDate(mStarTime,mEndTime);
							int isFinish = (int) map.get("isFinish");
							String toStarTime = (String) map.get("toStarTime");
							mrDto.setIsFinish(isFinish);
							mrDto.setToStarTime(toStarTime);
							listmrdto.add(mrDto);
							
							//根据会议ID查询参加会议的人员 uNames
							String uNames="";
							MeetingUserDto muDto = new MeetingUserDto();
							muDto.setMeetingId(mrDto.getMid());
							muDto.setStat(1);
							List<MeetingUserDto> listmudto = meetingUserService.findBy(muDto);
							if(listmudto!=null && listmudto.size()>0){
								for(MeetingUserDto muDto2:listmudto){
									String userId2 = muDto2.getUserId();
									ImsUsersDto userdto2= imsUserService.findByUserId(userId2);
									String userName2 = userdto2.getName();
									uNames = uNames + userName2 +",";
								}
								uNames = uNames.substring(0, uNames.length()-1);
							}
							mrDto.setUNames(uNames);
					  }
				  }
				  dept2.setListmrdto(listmrdto);
				//  listmrdto.
				//  DeptMeetingDto deptmeet3 = new DeptMeetingDto();					  
//				  deptmeet3.setDeptId(deptId2);
//				  deptmeet3.setDeptName(deptName2);
//				  //获取每个用户当天的会议记录信息
//				  Map<Object, Object> map3 = new HashMap<Object, Object>();
//				  for(DeptUsersDto deptu3:listdud2){
//					  String userId3 = deptu3.getUserId();
//					  //获取用户姓名
//					  ImsUsersDto userdto3 = imsUserService.findByUserId(userId3);
//					  String userName3 = userdto3.getName();
//					  Response<List<MeetingRecordDto>> resp3 = findMyMeeting(userId3);
//					  List<MeetingRecordDto> list3  = resp3.getData();
//					  map3.put(userName3, list3);
// 				  }
//				  deptmeet3.setListallm(map3);
				  result.setData(dept2);
				  result.setMessage("返回部门会议记录成功！");
				  result.setStatus(AppResponse.RETURN_SUCCESS);
				  return result;
		   }else{
			result.setMessage("该用户所在的部门为空！");
			result.setStatus(AppResponse.RETURN_SUCCESS);
			return result;
		  }
		    
		
		
		
		}
	
	//查看该项目下当天全部的会议记录
	@RequestMapping(value ="/findMeetingAll.do", method = {RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Response<MeetingAllUserDto> findMeetingAll(String userId) throws ParseException{
		Response<MeetingAllUserDto> result = new Response<MeetingAllUserDto>();
		MeetingAllUserDto meetingAllUserDto = new MeetingAllUserDto();
		List<MeetingRecordDto> listmrdto = new ArrayList<MeetingRecordDto>();
		
		//获取项目ID
		String projId="";
		Map<String, Object> mapproj = findProjByUserId(userId);
		Boolean projState = (Boolean) mapproj.get("SUCCESS");
		if(projState){
			ProjectUsersDto projectUsersDto = (ProjectUsersDto) mapproj.get("projectUsersDto");
			projId =projectUsersDto.getProjId();
		}else{
			 result.setMessage("项目ID不存在！");
			 result.setStatus(AppResponse.RETURN_FAILE);
			 return result;
		}
		
		//该用户是否拥有查看全部记录的权限    isAll 0 不能  1能
		MeetingPerDto meetingPerDto = new MeetingPerDto();
		if(cater_project.equals(projId)){
			meetingPerDto =  deptService.checkMeeting(userId, projId, cateringDept);
		}else if(patiwaihui_project.equals(projId)){
			meetingPerDto =  deptService.checkMeeting(userId, projId, waihuiDept);
		}else if(kps_project.equals(projId)){
			meetingPerDto = deptService.checkMeeting(userId, projId, kpsDept);
		}else if(juju_project.equals(projId)){
			meetingPerDto = deptService.checkMeeting(userId, projId, jujuDept);
		}
		
		
		int isAll = meetingPerDto.getIs_all();
		if(isAll==0){
			result.setMessage("您没有权限查看全部");
			result.setStatus(AppResponse.M_IS_NOT_ALL);
			return result;
		}
		
		SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf3=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date today = sdf2.parse(sdf2.format(new Date()))  ;
		//获取当天全部的会议记录
		MeetingDto meetingDto2 = new MeetingDto();
		meetingDto2.setNyrDate(today);
		meetingDto2.setProjId(projId);
 	    List<MeetingDto> listmdto =	meetingService.findTodayM(meetingDto2);
 	    //获取每一个会议的信息
 	    if(listmdto!=null && listmdto.size()>0){
 	    	for(MeetingDto mDto3:listmdto){
 	    		MeetingRecordDto mrdto3 = new MeetingRecordDto();
 	    		String mid = mDto3.getId();
 	    		String mName = mDto3.getName();
 	    		String title = mDto3.getTitle();
 	    		Date mStarTime = mDto3.getMStarTime();
 	    		Date mEndTime = mDto3.getMEndTime();
 	    		String addressId = mDto3.getAddressId();
 	    		String dName = mDto3.getAddressName();
 	    		String createUserID = mDto3.getCreateuserid();
 	    		String creatUName = mDto3.getCreateuserName();
 	    		mrdto3.setMid(mid);
 	    		mrdto3.setMName(mName);
 	    		mrdto3.setTitle(title);
 	    		mrdto3.setMStarTime(mStarTime);
 	    		mrdto3.setMEndTime(mEndTime);
 	    		mrdto3.setAddressId(addressId);
 	    		mrdto3.setDName(dName);
 	    		mrdto3.setCreateUserID(createUserID);
 	    		mrdto3.setCreatUName(creatUName);
 	    		String mst = sdf3.format(mStarTime);
				String met = sdf3.format(mEndTime);
				
			   // 获取会议时间段   08-12 12:00--15:00
				String mstyr = mst.substring(5, mst.length());
				String methm = met.substring(11,met.length());
				String starEndTime = mstyr +"--"+methm;
				mrdto3.setStarEndTime(starEndTime);
				//获取距离开会时间
				 // 当前时间与会议结束时间比较   ：
				   // 当前时间大于会议结束时间   	会议结束isFinish=0  
				   // 当前时间大于开始时间小于结束时间  会议进行中isFinish=1 
				   // 当前时间小于开始时间    会议未开始   isFinish=2   算出还有多久开始
				Map map = compareDate(mStarTime,mEndTime);
				int isFinish = (int) map.get("isFinish");
				String toStarTime = (String) map.get("toStarTime");
				mrdto3.setIsFinish(isFinish);
				mrdto3.setToStarTime(toStarTime);
				
				//获取会议创建者的部门名称
				DeptUsersDto dudto3 = new DeptUsersDto();
				dudto3.setUserId(createUserID);
				List<DeptUsersDto> listdudto3= deptUserService.findDeptUser(dudto3);
				String deptName3 = listdudto3.get(0).getDeptName();
				mrdto3.setDeptName(deptName3);
				
				listmrdto.add(mrdto3);
 	    	}
 	    	meetingAllUserDto.setListMr(listmrdto);
 	    	
 	    	   result.setMessage("发挥全部会议记录成功！");
 	    	   result.setData(meetingAllUserDto);
               result.setStatus(AppResponse.RETURN_SUCCESS);
               return result;
 	    }else{
 	    	result.setMessage(sdf2.format(new Date())+"没有会议！");
 	    	result.setStatus(AppResponse.RETURN_SUCCESS);
 	    	return result;
 	    }
	}
	
	
	//显示初始化的会议部门
	@RequestMapping("/initMeetingDept.do")
	@ResponseBody
	public Response<DeptDto> initMeetingDept (String userId){
		Response<DeptDto> result = new Response<DeptDto>();
		DeptDto deptDtok = new DeptDto();
		List<ImsUsersDto> listusers = new ArrayList<ImsUsersDto>();
		String projId="";
		Map<String, Object> mapproj = findProjByUserId(userId);
		Boolean projState = (Boolean) mapproj.get("SUCCESS");
		if(projState){
			ProjectUsersDto projectUsersDto = (ProjectUsersDto) mapproj.get("projectUsersDto");
			projId =projectUsersDto.getProjId();
		}else{
			 result.setMessage("项目ID不存在！");
			 result.setStatus(AppResponse.RETURN_FAILE);
			 return result;
		}
		
		DeptDto deptDto = new DeptDto();
		deptDto.setPid("");     //查询父节点为空的部门
		deptDto.setProjId(projId);
		deptDto.setStat(1);
		List<DeptDto> list1=  deptService.findBy(deptDto);
		
		if(list1==null || list1.size()==0){
			result.setMessage("父节点为空的部门不存在");
			result.setStatus(AppResponse.RETURN_FAILE);
			return result;
		}
		
		if(list1.size()>1){
			result.setMessage("同一个项目下父节点为空的部门只能为一个");
			result.setStatus(AppResponse.RETURN_FAILE);
			return result;
		}
		
		deptDtok.setId(list1.get(0).getId());
		deptDtok.setDeptName(list1.get(0).getDeptName());
		
		DeptUsersDto deptUsersDto2 = new DeptUsersDto();
		deptUsersDto2.setDeptId(list1.get(0).getId());
		List<DeptUsersDto> list2=  deptUserService.findDeptUser(deptUsersDto2);
		
		if( list2!=null && list2.size()>0 ){
			for(DeptUsersDto  deptUsersDto3 : list2){
				ImsUsersDto imsUsersDto2 =  imsUserService.findByUserId(deptUsersDto3.getUserId());
				if(!userId.equals(imsUsersDto2.getId())){
					listusers.add(imsUsersDto2);
				}
			}
		}
		
		deptDtok.setUsers(listusers);
		result.setData(deptDtok);
		result.setMessage("返回成功");
		result.setStatus(AppResponse.RETURN_SUCCESS);
		return result;
		
		
	}
	
	
	
	//测试  发送会议信息 ，新闻信息，公告信息
	@RequestMapping(value ="/sendMeeting.do", method = {RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Response<List<WorkSearchDto>> sendMeeting() throws ParseException{
		Response<List<WorkSearchDto>> result  = new Response<List<WorkSearchDto>>();
		String cateringProjId = ""; 
		//发送会议信息
		// List<WorkSearchDto> list= sendInfoService.sendMeeting("243bfa85-7bbd-47e7-a216-85dbe2e6bbff", "2015-08-12", 0, 10);
		 List<WorkSearchDto> list=sendInfoService.sendNews(cateringProjId, "2015-08-21", 0, 10,"");
		// List<WorkSearchDto> list= sendInfoService.sendNotify("243bfa85-7bbd-47e7-a216-85dbe2e6bbff", "2015-08-21", 0, 10);
	    if(list!=null && list.size()>0){
	    	result.setMessage("返回会议信息列表成功！");
	    	result.setData(list);
	    	result.setStatus(AppResponse.RETURN_SUCCESS);
	    }else{
	    	result.setMessage("返回列表信息为空！");
	    	result.setStatus(AppResponse.RETURN_SUCCESS);
	    }
		return result;
	}
	
	
	
	
	//获取时间差
	public Map compareDate(Date mStarTime,Date mEndTime){
		Map map = new HashMap();
		int isFinish;
		String toStarTime;
		Date nowDate = new Date();
		if(nowDate.after(mEndTime)){
			//当前时间在结束时间之后
			isFinish = 0;
			toStarTime = "";
		}else if(nowDate.after(mStarTime)&&nowDate.before(mEndTime)){
			 // 当前时间大于开始时间小于结束时间  会议进行中isFinish=1 
			isFinish = 1;
			toStarTime = "";
		}else{
			// 当前时间小于开始时间    会议未开始   isFinish=2   算出还有多久开始
			isFinish = 2;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			long l= mStarTime.getTime()-nowDate.getTime();
			long day=l/(24*60*60*1000);   
			long hour=(l/(60*60*1000)-day*24);   
			long min=((l/(60*1000))-day*24*60-hour*60);   
			long s=(l/1000-day*24*60*60-hour*60*60-min*60);   
			System.out.println(hour+"小时"+min+"分"+s+"秒"); 
			if(hour==0 && min==0){
				isFinish=1 ;
				toStarTime = "";
			}else{
				toStarTime=hour+"小时"+min+"分后";
			}
			
		}
		map.put("isFinish", isFinish);
		map.put("toStarTime", toStarTime);
		return map;
	}
	
	
	public void createMeeting(String addressId,Date DstarTime,Date DendTime,String title,String[] users,String userId,String projID){
		//插入会议
		String meetingId = UUIDGenerator.getUUID();
		MeetingDto meetingDto = new MeetingDto();
		meetingDto.setAddressId(addressId);
		meetingDto.setMStarTime(DstarTime);
		meetingDto.setMEndTime(DendTime);
		meetingDto.setTitle(title);
		meetingDto.setName(title);
		meetingDto.setId(meetingId);
		meetingDto.setCreateTime(new Date());
		meetingDto.setStat(1);
		meetingDto.setState(0);
		meetingDto.setCreateuserid(userId);
		meetingDto.setProjId(projID);
		meetingService.insertMeeting(meetingDto);
		//插入会议人员
		for(String MuserID:users){
			MeetingUserDto meetingUserDto = new MeetingUserDto();
			meetingUserDto.setMeetingId(meetingId);
			meetingUserDto.setId(UUIDGenerator.getUUID());
			meetingUserDto.setUserId(MuserID);
			meetingUserDto.setStat(1);
			meetingUserDto.setCreateTime(new Date());
			meetingUserDto.setState(2);
			meetingUserService.insertMeetingU(meetingUserDto);
		}
	
		MeetingUserDto meetingUserDto2 = new MeetingUserDto();
		meetingUserDto2.setMeetingId(meetingId);
		meetingUserDto2.setId(UUIDGenerator.getUUID());
		meetingUserDto2.setUserId(userId);
		meetingUserDto2.setStat(1);
		meetingUserDto2.setCreateTime(new Date());
		meetingUserDto2.setState(2);
		meetingUserService.insertMeetingU(meetingUserDto2);
	}
	
	
	
	//验证日期格式是否正确
	 public   boolean   validTimestamp(String str){
		  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		         try{
		                 Date date =  formatter.parse(str);
		                 return  str.equals(formatter.format(date));
		         }catch(Exception   e){
		               return   false;
		         }
		 }
	
	 //根据userid查询项目id
	 public Map<String, Object> findProjByUserId(String userid){
		ProjectUsersDto projectUsersDto = projectServices.findByUserID(userid);
		 Map<String, Object> map = new HashMap<String, Object>();
		 if(projectUsersDto!=null && projectUsersDto.getProjId()!=null){
			 map.put("SUCCESS", true);
			 map.put("projectUsersDto", projectUsersDto);
		 }else{
			 map.put("SUCCESS", false);
			 map.put("projectUsersDto", projectUsersDto);
		 }
		 return  map;
	 }
	 
}
