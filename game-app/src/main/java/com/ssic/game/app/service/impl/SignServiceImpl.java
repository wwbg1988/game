/**
 * 
 */
package com.ssic.game.app.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssic.catering.base.dto.DeptMeetingDto;
import com.ssic.catering.base.dto.MeetRecordUserDto;
import com.ssic.catering.base.dto.SignDeptUserDto;
import com.ssic.catering.common.util.AppResponse;
import com.ssic.game.app.service.SignService;
import com.ssic.game.common.constant.SignTypeConstants;
import com.ssic.game.common.dao.DeptUserDao;
import com.ssic.game.common.dao.ImsUserDao;
import com.ssic.game.common.dao.SignDao;
import com.ssic.game.common.dto.DeptUsersDto;
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.dto.ProjectUsersDto;
import com.ssic.game.common.dto.SignDto;
import com.ssic.game.common.pojo.DeptUsers;
import com.ssic.game.common.pojo.ImsUsers;
import com.ssic.game.common.pojo.Sign;
import com.ssic.game.common.service.DeptUserService;
import com.ssic.game.common.service.IImsUserService;
import com.ssic.game.common.service.ProjectServices;
import com.ssic.game.common.util.CateringProjectG;
import com.ssic.util.BeanUtils;
import com.ssic.util.constants.DataStatus;
import com.ssic.util.model.Response;

/**		
 * <p>Title: SignServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年8月10日 下午1:18:17	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年8月10日 下午1:18:17</p>
 * <p>修改备注：</p>
 */
@Service
public class SignServiceImpl implements SignService
{

    @Autowired
    private SignDao signDao;
    
    @Autowired
    private DeptUserDao deptUserDao;
    
    @Autowired
    private ImsUserDao imsUserDao;
    
    @Autowired
    private DeptUserService deptUserService;
    
    @Autowired
	private IImsUserService imsUserService;
    
    @Autowired
    private ProjectServices projectServices;
    
    
    private static String cateringDeptRenshi = CateringProjectG.cater_dept_renshi;   //团餐人事部
    
    private static String waihuiDeptRenshi = CateringProjectG.waihui_dept_renshi;   //外会人事部
    
    private static String kpsDeptRenshi = CateringProjectG.kps_dept_renshi;   //康帕斯人事部
    
    private static String jujuRenshi = CateringProjectG.juju_dept_renshi; //聚运动人事部
    
    private static String cater_project = CateringProjectG.cater_project;   //团餐项目
    
    private static String patiwaihui_project = CateringProjectG.patiwaihui_project;  // 趴体外会项目
    
    private static String kps_project = CateringProjectG.kps_project;  //康帕斯项目
    
    private static String juju_project = CateringProjectG.juju_project;  //聚运动
    
    


    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.app.service.SignService#insert(com.ssic.game.common.dto.SignDto)   
    */
    @Override
    public Response<SignDto> insert(SignDto signDto)
    {
        if (StringUtils.isEmpty(signDto.getXPosition()))
        {
            return new Response<SignDto>(AppResponse.RETURN_FAILE, "维度不能为空", null);
        }

        if (StringUtils.isEmpty(signDto.getYPosition()))
        {
            return new Response<SignDto>(AppResponse.RETURN_FAILE, "经度不能为空", null);
        }
        if(signDto.getSignType()==0){
            return new Response<SignDto>(AppResponse.RETURN_FAILE, "签到、考勤类型不能为空", null);
        }
        if (StringUtils.isEmpty(signDto.getUserId()))
        {
            return new Response<SignDto>(AppResponse.RETURN_FAILE, "用户不能为空", null);
        }
        if(StringUtils.isEmpty(signDto.getProjectid())){
            return new Response<SignDto>(AppResponse.RETURN_FAILE,"项目编号不能为空",null);
        }
        if(StringUtils.isEmpty(signDto.getAddress())){
        	return new Response<SignDto>(AppResponse.RETURN_FAILE,"地址不能为空",null);
        }
        //一天考勤多次只显示第一次和最后一次
        // 同一个人当天只能显示两次考勤
        //同一个人一天可以有多次签到
        if (signDto.getSignType() == SignTypeConstants.SIGN_TYPE_WORK)
        {
            Sign sign = new Sign();
            sign.setUserId(signDto.getUserId());
            sign.setCreateTime(new Date());
            sign.setSignType(signDto.getSignType());
            sign.setProjectid(signDto.getProjectid());
            int temps = signDao.findCountByTime(sign);
            //如果大于1条记录则倒叙排列把最后条记录STAT至0
            if (temps > 1)
            {
               List<Sign> signList = signDao.findBy(sign,0,0);
               if(!CollectionUtils.isEmpty(signList)){
                   signDao.delSignByPrimaryKey(signList.get(0).getId());
               }
            }
            if(temps>0){
            	// isWorkTime;      0上班   1下班
            	signDto.setIsworktime(1);
            }else{
            	signDto.setIsworktime(0);
            }
            
        }
        

        DeptUsers deptUsers = new DeptUsers();
        deptUsers.setProjId(signDto.getProjectid());
        deptUsers.setUserId(signDto.getUserId());
        List<DeptUsers> deptList = deptUserDao.findAllBy(deptUsers);
        if(!CollectionUtils.isEmpty(deptList)){
           signDto.setDeptid(deptList.get(0).getDeptId());
        }
        int count = signDao.insert(signDto);
        if (count <= 0)
        {
            return new Response<SignDto>(AppResponse.RETURN_FAILE, "添加失败", null);
        }
        return new Response<SignDto>(AppResponse.RETURN_SUCCESS, "添加成功", null);
    }

    /** 
    * (non-Javadoc)   
     * @throws ParseException 
    * @see com.ssic.game.app.service.SignService#findByName(com.ssic.game.common.dto.SignDto)   
    */
    @Override
    public Response<SignDeptUserDto> find(SignDto signDto,int beginRows,int endRows) throws ParseException
    {
    	
        if (StringUtils.isEmpty(signDto.getUserId()))
        {
            return new Response<SignDeptUserDto>(AppResponse.RETURN_FAILE, "用户不能为空", null);
        }
        if(StringUtils.isEmpty(signDto.getProjectid())){
            return new Response<SignDeptUserDto>(AppResponse.RETURN_FAILE,"项目编号不能为空",null);
        }
        if(endRows==0){
            return new Response<SignDeptUserDto>(AppResponse.RETURN_FAILE,"结束条数不能为空",null);
        }
    	//该部门下的请假考勤信息
        List<SignDto> resultList = new ArrayList<SignDto>();
        //该部门下的人员信息
        List<MeetRecordUserDto> listusers = new ArrayList<MeetRecordUserDto>();
        SignDeptUserDto  signDeptUserDto = new SignDeptUserDto();
       //验证该用户是否拥有查看部门的权限
        if(signDto.getIsDept()==1){
        	//查询用户是否是该部门的部门管理员
    		//根据userid查询出该用户在团餐项目下的唯一部门
    		DeptUsersDto ddto2 = new DeptUsersDto();
    		ddto2.setUserId(signDto.getUserId());
    		ddto2.setStat(1);
    		ddto2.setProjId(signDto.getProjectid());//团餐项目
    		List<DeptUsersDto> listduDto= deptUserDao.findDeptUser(ddto2);
      	 // ImsUsersDto userdto3 = imsUserService.findByUserId(signDto.getUserId());
      	    if(listduDto.get(0).getIsAdmin().equals("0")){
      	    	return new Response<SignDeptUserDto>(AppResponse.RETURN_FAILE, "您没有权限查看部门", null);
      	    }
      	    //当该用户是部门管理员是查询该部门下的所有记录
//      	  DeptUsersDto deptUsersDto4 = new DeptUsersDto();
//		  deptUsersDto4.setUserId(signDto.getUserId());
//		  List<DeptUsersDto> listdud4=  deptUserService.findAllBy(deptUsersDto4);
//      	  Sign si = new Sign();
//      	  si.setDeptid(listdud4.get(0).getDeptId());
//      	    signDao.findBy(si, 0, 0);
        }
        
 
        
        //返回这个部门下的所有人员
        DeptMeetingDto dept2= new DeptMeetingDto();
        
        Sign sign = new Sign();
        if(signDto.getIsDept()==1){
            DeptUsers deptUsers = new DeptUsers();
            deptUsers.setProjId(signDto.getProjectid());
            deptUsers.setUserId(signDto.getUserId());
            List<DeptUsers> deptList = deptUserDao.findAllBy(deptUsers);
            if(!CollectionUtils.isEmpty(deptList)){
               signDto.setDeptid(deptList.get(0).getDeptId());
               signDto.setUserId(null);
            }
            
            //查询该部门下的所有人员
			  DeptUsersDto deptUsersDto2 = new DeptUsersDto();
			  deptUsersDto2.setDeptId(deptList.get(0).getDeptId());
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
        }
        BeanUtils.copyProperties(signDto, sign);
        SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd");
		Date today = sdf2.parse(sdf2.format(new Date()))  ;
		sign.setCreateTime(today);	
        List<SignDto> tempLists = signDao.findByDate(sign,beginRows,endRows);
        
        if (CollectionUtils.isEmpty(tempLists))
        {
            return new Response<SignDeptUserDto>(AppResponse.RETURN_SUCCESS, "查无数据", null);
        }
        
        //如果是考勤    isWorkTime 0上班   1下班   isCheck 0 迟到   1 早退   2正常
//        
//        if(signDto.getSignType()==1){
//        	String starWorkTime = "09:00:00";  
//        	String endWorkTime = "18:00:00" ;
//        	 SimpleDateFormat sdfw=new SimpleDateFormat("HH:mm:ss");
//        		 Date datestar = sdfw.parse(starWorkTime);
//        		 Date dateend = sdfw.parse(endWorkTime);
//        	 
//        	 for(SignDto signDtow : tempLists){
//        		 String userId = signDtow.getUserId();
//        		 SignDto signdto3 = new SignDto();
//        		 signdto3.setCreateTime(today);
//        		 signdto3.setSignType(1);
//        		 signdto3.setUserId(userId);
//        		List<SignDto> listsi =  signDao.findByUserId(signdto3);
//        		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss"); 
//        			if(listsi.size()==2){
//        				listsi.get(0).setIsWorkTime(0);
//        				listsi.get(1).setIsWorkTime(1);
//        				
//        				String strDate = df.format(listsi.get(0).getCreateTime());
//        				Date singcreat = sdfw.parse(strDate);
//        				if(singcreat.after(datestar)){
//        					listsi.get(0).setIsCheck(0);
//        				}else{
//        					listsi.get(0).setIsCheck(2);
//        				}
//        				String endDate = df.format(listsi.get(1).getCreateTime());
//        				Date singcreat2 = sdfw.parse(endDate);
//        				if(singcreat2.before(dateend)){
//        					listsi.get(1).setIsCheck(1);
//        				}else{
//        					listsi.get(1).setIsCheck(2);
//        				}
//        				
//        			}else if(listsi.size()==1){
//        				listsi.get(0).setIsWorkTime(0);
//        			}
//        			
//        	 }
//        }
        
        
    //    List<SignDto> tempLists = BeanUtils.createBeanListByTarget(tempList, SignDto.class);
        for(SignDto signDtos : tempLists){
            ImsUsers imsUsers = imsUserDao.findByUserId(signDtos.getUserId());
            if(imsUsers!=null){
                signDtos.setUserName(imsUsers.getName());
            }
            Date createDate = signDtos.getCreateTime();
            String strDate=  changeDate(createDate);
            signDtos.setTimeStr(strDate);
            compareDate(signDtos);

            //如果是考勤    isWorkTime 0上班   1下班   isCheck 0 迟到   1 早退   2正常
//            if(signDto.getSignType()==1){
//            	String starWorkTime = "09:00:00";  
//            	String endWorkTime = "18:00:00" ;
//            	 SimpleDateFormat sdfw=new SimpleDateFormat("HH:mm:ss");
//            		 Date datestar = sdfw.parse(starWorkTime);
//            		 Date dateend = sdfw.parse(endWorkTime);
//            		 String userId = signDtos.getUserId();
//            
//            		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss"); 
//            			if(listsi.size()==2){
//            				listsi.get(0).setIsWorkTime(0);
//            				listsi.get(1).setIsWorkTime(1);
//            				
//            				String strDate11 = df.format(listsi.get(0).getCreateTime());
//            				Date singcreat = sdfw.parse(strDate11);
//            				if(singcreat.after(datestar)){
//            					listsi.get(0).setIsCheck(0);
//            				}else{
//            					listsi.get(0).setIsCheck(2);
//            				}
//            				String endDate11 = df.format(listsi.get(1).getCreateTime());
//            				Date singcreat2 = sdfw.parse(endDate11);
//            				if(singcreat2.before(dateend)){
//            					listsi.get(1).setIsCheck(1);
//            				}else{
//            					listsi.get(1).setIsCheck(2);
//            				}
//            				
//            			}else if(listsi.size()==1){
//            				listsi.get(0).setIsWorkTime(0);
//            			}
//            }

            resultList.add(signDtos);
        }
        signDeptUserDto.setSignlist(resultList);
        signDeptUserDto.setListusers(listusers);
        return new Response<SignDeptUserDto>(AppResponse.RETURN_SUCCESS, "查询成功", signDeptUserDto);
    }

	@Override
	public Response<List<SignDto>> findAllSign(SignDto signDto,int beginRows,int endRows) throws ParseException {
		// TODO Auto-generated method stub
		Response<List<SignDto>> result = new Response<List<SignDto>>();
		if(signDto.getUserId()==null || "".equals(signDto.getUserId())){
			result.setMessage("用户ID不能为空!");
			result.setStatus(AppResponse.RETURN_FAILE);
			return result;
		}
		
		//获取项目ID
				String projId="";
				Map<String, Object> mapproj = findProjByUserId(signDto.getUserId());
				Boolean projState = (Boolean) mapproj.get("SUCCESS");
				if(projState){
					ProjectUsersDto projectUsersDto = (ProjectUsersDto) mapproj.get("projectUsersDto");
					projId =projectUsersDto.getProjId();
				}else{
					 result.setMessage("项目ID不存在！");
					 result.setStatus(AppResponse.RETURN_FAILE);
					 return result;
				}
		
		 // 团餐人事组   Terry
		 ImsUsersDto userdto3 = imsUserService.findByUserId(signDto.getUserId());
		 //查询团餐人事组组长
		  DeptUsersDto deptUsersDto2 = new DeptUsersDto();
		  if(cater_project.equals(projId)){
			  deptUsersDto2.setDeptId(cateringDeptRenshi);
		  }else if(patiwaihui_project.equals(projId)){
			  deptUsersDto2.setDeptId(waihuiDeptRenshi);
		  }else if(kps_project.equals(projId)){
			  deptUsersDto2.setDeptId(kpsDeptRenshi);
		  }else if(juju_project.equals(projId)){
			  deptUsersDto2.setDeptId(jujuRenshi);
		  }
		  deptUsersDto2.setIsAdmin("1");
		  List<DeptUsersDto> listdud2=  deptUserService.findAllBy(deptUsersDto2);
		 if(listdud2!=null && listdud2.size()>0){
			 if(!userdto3.getId().equals(listdud2.get(0).getUserId())){
					result.setMessage("您没有权限查看全部");
					result.setStatus(AppResponse.RETURN_FAILE);
					return result;
				}
		 }
		  
		Sign sign = new Sign();
		sign.setStat(1);
		sign.setSignType(signDto.getSignType());
		sign.setProjectid(projId);
		  SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd");
			Date today = sdf2.parse(sdf2.format(new Date()))  ;
			sign.setCreateTime(today);
		List<SignDto> tempList = signDao.findByDate(sign,beginRows,endRows);
		SimpleDateFormat df = new SimpleDateFormat("HH:mm"); 
		
		if(tempList==null || tempList.size()==0 ){
			result.setMessage("记录为空!");
			result.setStatus(AppResponse.RETURN_SUCCESS);
			return result;
		}else{
			
			for(SignDto sd2:tempList){
				String timeStr = df.format(sd2.getCreateTime());
				sd2.setTimeStr(timeStr);
				compareDate(sd2);
			}
			
			result.setData(tempList);
			result.setMessage("返回成功!");
			result.setStatus(AppResponse.RETURN_SUCCESS);
			return result;
		}
	}
 
	//转换时间为 hh:mm   07:10
	public String changeDate(Date date){
		SimpleDateFormat df = new SimpleDateFormat("HH:mm"); 
		String strDate = df.format(date);
		return strDate;
	}
	
	//比较时间是否迟到早退正常
	public void compareDate(SignDto signDtos) throws ParseException{
		  //如果是考勤    isWorkTime 0上班   1下班   isCheck 0 迟到   1 早退   2正常
        if(signDtos.getSignType()==1){
        	String starWorkTime = "08:50:00";  
        	String endWorkTime = "18:10:00" ;
        	SimpleDateFormat sdfw=new SimpleDateFormat("HH:mm:ss");
            Date datestar = sdfw.parse(starWorkTime);
        	Date dateend = sdfw.parse(endWorkTime);
        	String strDate11 = sdfw.format(signDtos.getCreateTime());
			Date singcreat = sdfw.parse(strDate11);
			if(signDtos.getIsworktime()==0){
				if(singcreat.before(datestar)){
            		signDtos.setIsCheck(2);
            	}else{
            		signDtos.setIsCheck(0);
            	}
			}else{
				if(singcreat.after(dateend)){
					signDtos.setIsCheck(2);
				}else{
					signDtos.setIsCheck(1);
				}
			}
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
