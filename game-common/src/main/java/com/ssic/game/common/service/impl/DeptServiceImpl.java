/**
 * 
 */
package com.ssic.game.common.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ssic.game.common.dao.DeptDao;
import com.ssic.game.common.dao.DeptUserDao;
import com.ssic.game.common.dao.ImsUserDao;
import com.ssic.game.common.dto.DeptDto;
import com.ssic.game.common.dto.DeptUsersDto;
import com.ssic.game.common.dto.FormsDto;
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.dto.MeetingPerDto;
import com.ssic.game.common.pojo.DeptUsers;
import com.ssic.game.common.pojo.Forms;
import com.ssic.game.common.pojo.ImsUsers;
import com.ssic.game.common.service.IDeptService;
import com.ssic.game.common.util.CateringProjectG;
import com.ssic.game.common.util.DataStatusUtils;
import com.ssic.game.common.util.HttpDataResponse;
import com.ssic.util.BeanUtils;

/**		
 * <p>Title: DeptServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年7月11日 上午10:10:36	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年7月11日 上午10:10:36</p>
 * <p>修改备注：</p>
 */
@Service("DeptServiceImpl")
public class DeptServiceImpl implements IDeptService
{

    @Autowired
    private DeptDao dao;
    @Autowired
    private ImsUserDao imsUserDao;
    @Autowired
    private DeptUserDao deptUserDao;

    private static String cateringProjId = CateringProjectG.cater_project;   //团餐项目id

    
    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.common.service.IDeptService#findById(java.lang.String)   
    */
    @Override
    @Cacheable(value = "default", key = "'game.common.dto.DeptDto:' + #id")
    public DeptDto findById(String id)
    {
        DeptDto dept = dao.findById(id);
        
        if(isNotExist(dept)) {
            return null;
        }
        return dept;
    }
    
    private boolean isNotExist(DeptDto dept) {
        
        return dept == null || DataStatusUtils.isNotEnabled(dept.getStat());
        }

    
	@Override
	public MeetingPerDto checkMeeting(String userId, String projId,
			String mdeptCode) {
		// TODO Auto-generated method stub
		MeetingPerDto meetingPerDto = new MeetingPerDto();
		if(userId==null || "".equals(userId)){
			meetingPerDto.setErrorCode(HttpDataResponse.CHECK_MEETING_FALID);
			meetingPerDto.setMessage("用户ID不能为空！");
			return meetingPerDto;
		}
		
		if(projId==null || "".equals(projId)){
			meetingPerDto.setErrorCode(HttpDataResponse.CHECK_MEETING_FALID);
			meetingPerDto.setMessage("项目ID不能为空！");
			return meetingPerDto;
		}
		
		if(mdeptCode==null || "".equals(mdeptCode)){
			meetingPerDto.setErrorCode(HttpDataResponse.CHECK_MEETING_FALID);
			meetingPerDto.setMessage("部门号不能为空!");
			return meetingPerDto;
		}
		
		//查询用户是否是该部门的部门管理员
		//根据userid查询出该用户在团餐项目下的唯一部门
		DeptUsersDto ddto2 = new DeptUsersDto();
		ddto2.setUserId(userId);
		ddto2.setStat(1);
		ddto2.setProjId(projId);//团餐项目
		List<DeptUsersDto> listduDto= deptUserDao.findDeptUser(ddto2);
		if(listduDto==null || listduDto.size()==0){
			meetingPerDto.setErrorCode(HttpDataResponse.CHECK_MEETING_FALID);
			meetingPerDto.setMessage("该用户不存在！");
			return meetingPerDto;
		}else{
			String isAdmin = listduDto.get(0).getIsAdmin();
			if(isAdmin==null){
				meetingPerDto.setIs_dept(0);  //不是部门管理员
			}else{
				if(isAdmin.equals("1")){
					meetingPerDto.setIs_dept(1);  //是部门管理员
				}else{
					meetingPerDto.setIs_dept(0);  //不是部门管理员
				}
			}
			
		}
		
		//查询该用户的部门是不是人事部，只有人事部才能看到所有的会议记录
		DeptUsers deptUsers = new DeptUsers();
		deptUsers.setUserId(userId);
		List<DeptUsers> listdeptuser=   deptUserDao.findAllBy(deptUsers);
		if(listdeptuser==null || listdeptuser.size()==0){
			meetingPerDto.setErrorCode(HttpDataResponse.CHECK_MEETING_FALID);
			meetingPerDto.setMessage("该用户所在部门不存在！");
			return meetingPerDto;
		}else{
			String dept_id =  listdeptuser.get(0).getDeptId();
			 DeptDto  deptDto = dao.findById(dept_id);
			 if(deptDto==null){
				 meetingPerDto.setErrorCode(HttpDataResponse.CHECK_MEETING_FALID);
				 meetingPerDto.setMessage("该部门不存在");
				 return meetingPerDto;
			 }else{
				 String deptCode = deptDto.getDeptCode();
				 //等于传过来的deptcode给予全部权限
				 //0607  团餐财务部    0606  团餐人事部
			    	String[] deptCodes= mdeptCode.split(",");
			    	if(deptCodes!=null && deptCodes.length>0){
			    		for(String dCode:deptCodes){
			    			 if(dCode.equals(deptCode)){
								 meetingPerDto.setIs_all(1);
								 meetingPerDto.setErrorCode(HttpDataResponse.CHECK_MEETING_SUCCESS);
								 meetingPerDto.setMessage("返回会议记录成功！");
								 return meetingPerDto;
			    		}
			    	}
			   }
						 meetingPerDto.setIs_all(0);
						 meetingPerDto.setErrorCode(HttpDataResponse.CHECK_MEETING_SUCCESS);
						 meetingPerDto.setMessage("返回会议记录成功!");
	                     return meetingPerDto;
			 }
		}
	}

    
     /** 
     * (non-Javadoc)   
     * @see com.ssic.game.common.service.IDeptService#findBy(com.ssic.game.common.dto.DeptDto)   
     */
    @Override
    public List<DeptDto> findBy(DeptDto param)
    {
       return dao.findAll(param);
    }

}
