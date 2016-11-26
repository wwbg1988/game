package com.ssic.game.manage.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssic.game.admin.util.MD5Util;
import com.ssic.game.common.dto.DeptDto;
import com.ssic.game.common.dto.DeptUsersDto;
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.dto.LTUserDto;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.dto.ParamConfigDto;
import com.ssic.game.common.dto.ProjectUsersDto;
import com.ssic.game.common.dto.RoleUsersDto;
import com.ssic.game.common.service.DeptUserService;
import com.ssic.game.common.service.IImsUserService;
import com.ssic.game.common.service.ILiaoTianService;
import com.ssic.game.common.service.IParamConfigService;
import com.ssic.game.common.service.ITmsRoleservice;
import com.ssic.game.common.util.CateringProjectG;
import com.ssic.game.manage.service.DeptService;
import com.ssic.game.manage.service.IOperateUserService;
import com.ssic.game.manage.service.IProjectUsersService;
import com.ssic.util.UUIDGenerator;

@Service
public class OperateUserServiceImpl implements IOperateUserService{
	
    private static String role_qj = CateringProjectG.role_qj; //填写请假单名单  团餐项目

    private static String role_cc = CateringProjectG.role_cc; //填写出差单名单  团餐项目

    private static String role_bx = CateringProjectG.role_bx; //填写报销单名单  团餐项目

    private static String role_qj_pt = CateringProjectG.role_qj_pt; //填写请假单名单  趴体外烩

    private static String role_cc_pt = CateringProjectG.role_cc_pt; //填写出差单名单  趴体外烩

    private static String role_bx_pt = CateringProjectG.role_bx_pt; //填写报销单名单  趴体外烩

    private static String cater_project = CateringProjectG.cater_project; //团餐项目

    private static String patiwaihui_project = CateringProjectG.patiwaihui_project; // 趴体外会项目

    private static String role_bx_kps = CateringProjectG.role_bx_kps; //填写报销单名单  康帕斯

    private static String role_cc_kps = CateringProjectG.role_cc_kps; //填写出差单名单  康帕斯

    private static String role_qj_kps = CateringProjectG.role_qj_kps; //填写请假单名单  康帕斯

    private static String kps_project = CateringProjectG.kps_project; //康帕斯项目id
    
   private static String  role_qj_juju  =  CateringProjectG.role_qj_juju;   //填写请假单 聚运动
    
    private static String role_cc_juju = CateringProjectG.role_cc_juju;  //填写出差单 聚运动
    
    private static String role_bx_juju = CateringProjectG.role_bx_juju;  //填写报销单 聚运动
    
    private static String juju_project = CateringProjectG.juju_project ;  //聚运动项目
    
    private static int paramType = 7;  //参数表中请假出差报销的角色类型

	@Autowired
	private IImsUserService iImsUserService;
	@Autowired
	private DeptUserService deptUserService;
	@Autowired
	private IProjectUsersService caterProjectUsersService;
	@Autowired
	private DeptService deptService;
	@Autowired
	private ILiaoTianService liaoTianService;
	@Autowired
	private ITmsRoleservice iTmsRoleservice;
	@Autowired
	private IParamConfigService iParamConfigService;
	
	@Override
	public List<ImsUsersDto> dataGrid(ImsUsersDto imsUsersDto,
			PageHelperDto phdto) {  
		// TODO Auto-generated method stub
		 List<ImsUsersDto> list = iImsUserService.findUsersAll(imsUsersDto, phdto);
	        for (int i = 0; i < list.size(); i++)
	        {
	            ImsUsersDto iuDto = list.get(i);
	            String userId = iuDto.getId();
	            DeptUsersDto deptUsersDto = new DeptUsersDto();
	            deptUsersDto.setUserId(userId);
	            List<DeptUsersDto> deptUsers = deptUserService.findDeptUser(deptUsersDto);

	            String projectNames = "";
	            String deptIdNames = "";
	            if (deptUsers != null && deptUsers.size() > 0)
	            {
	                for (int j = 0; j < deptUsers.size(); j++)
	                {
	                    DeptUsersDto ddto = deptUsers.get(j);
	                    projectNames = projectNames + ddto.getProjName() + ",";
	                    deptIdNames = deptIdNames + ddto.getDeptName() + ",";
	                }
	                projectNames = projectNames.substring(0, projectNames.length() - 1);
	                deptIdNames = deptIdNames.substring(0, deptIdNames.length() - 1);
	                iuDto.setProjectNames(projectNames);
	                iuDto.setDeptNames(deptIdNames);
	                iuDto.setIsAdmin(Integer.valueOf(deptUsers.get(0).getIsAdmin()));
	            }
	        }
		return list;
	}

	@Override
	@Transactional
	public void insertDept(ImsUsersDto user, String isExistManager) {
		// TODO Auto-generated method stub
        //插入t_ims_dept_users
        DeptUsersDto deptUsersDto = new DeptUsersDto();
        deptUsersDto.setId(UUIDGenerator.getUUID());
        deptUsersDto.setUserId(user.getId());
        deptUsersDto.setProjId(user.getProjectIds());
        deptUsersDto.setDeptId(user.getDeptIds());
        deptUsersDto.setCreateTime(new Date());
        deptUsersDto.setStat(1);
        deptUsersDto.setIsAdmin(isExistManager);
        deptUserService.insertDeptUser(deptUsersDto);
        //插入project_users
        ProjectUsersDto projectUsersDto = new ProjectUsersDto();
        projectUsersDto.setProjId(user.getProjectIds());
        projectUsersDto.setUserId(user.getId());
        projectUsersDto.setStat(1);
        projectUsersDto.setCreateTime(new Date());
        projectUsersDto.setId(UUIDGenerator.getUUID());
        caterProjectUsersService.insertPUser(projectUsersDto);
        //插入角色表
        String prijId = user.getProjectIds();
        //查询该项目的请假出差报销角色ID
        ParamConfigDto paramConfigDto = new ParamConfigDto();
        paramConfigDto.setProjId(prijId);
        paramConfigDto.setParamType(paramType);
        List<ParamConfigDto> listparam = iParamConfigService.findBy(paramConfigDto);
        if(!CollectionUtils.isEmpty(listparam)){
        	for(ParamConfigDto paramDto : listparam){
        		 RoleUsersDto roleUsersDto_qj = new RoleUsersDto();
        	     roleUsersDto_qj.setId(UUIDGenerator.getUUID());
        	     roleUsersDto_qj.setUserId(user.getId());
        	     roleUsersDto_qj.setRoleId(paramDto.getParamValue());
        	     roleUsersDto_qj.setStat(1);
        	     roleUsersDto_qj.setCreateTime(new Date());
        	     iTmsRoleservice.insertRoleUser(roleUsersDto_qj);
        	}
        }
	}

	@Override
	@Transactional
	public void updateDept(ImsUsersDto user) {
		// TODO Auto-generated method stub
	    String[] proj_ids = user.getProjectIds().split(",");
        String[] dept_ids = user.getDeptIds().split(",");
		//先把原来的dept_user 的user_id 删除掉
        //再把新的项目和部门插入
        DeptUsersDto deptUsersDto = new DeptUsersDto();
        deptUsersDto.setUserId(user.getId());
        iImsUserService.deleteDept(deptUsersDto);
        for (int i = 0; i < proj_ids.length; i++)
        {
            String proj_id = proj_ids[i];
            String dept_id = dept_ids[i];
            deptUsersDto.setCreateTime(new Date());
            deptUsersDto.setId(UUIDGenerator.getUUID());
            deptUsersDto.setStat(1);
            deptUsersDto.setProjId(proj_id);
            //验证该部门在该项目下，如果不在则循环找到项目对应的部门
            DeptDto deptDto = new DeptDto();
            deptDto.setProjId(proj_id);
            deptDto.setId(dept_id);
            List<DeptDto> listDept = deptService.finddeptByProj(deptDto);
            if (listDept == null || listDept.size() == 0)
            {
                for (String deptId1 : dept_ids)
                {
                    DeptDto deptDto1 = new DeptDto();
                    deptDto1.setId(deptId1);
                    deptDto1.setProjId(proj_id);
                    List<DeptDto> listDept1 = deptService.finddeptByProj(deptDto);
                    if (listDept1 != null && listDept1.size() > 0)
                    {
                        dept_id = deptId1;
                    }
                }
            }
            deptUsersDto.setDeptId(dept_id);
            //插入t_ims_dept_users
            deptUserService.insertDeptUser(deptUsersDto);
        }
	}

	@Override
	@Transactional
	public String insertUser(ImsUsersDto user) {
		// TODO Auto-generated method stub
		 //添加IM聊天
        LTUserDto lTUserDto = new LTUserDto();
        lTUserDto.setUserAccount(user.getUserAccount());
        lTUserDto.setPassword(MD5Util.md5(user.getPassword()));
        liaoTianService.addLTUser(lTUserDto);
        //查询该账号是否注册成功，如果不存在，则返回失败
        String states = liaoTianService.findIsExistByAccount(lTUserDto);
        if("200".equals(states)){
        	user.setPassword(MD5Util.md5(user.getPassword()));
            iImsUserService.createUser(user);
        }
        return states;
	}

	@Override
	@Transactional
	public void insertUserDept2(ImsUsersDto user, String isExistManager) {
		// TODO Auto-generated method stub
		 //添加IM聊天
        LTUserDto lTUserDto = new LTUserDto();
        lTUserDto.setUserAccount(user.getUserAccount());
        lTUserDto.setPassword(MD5Util.md5(user.getPassword()));
        liaoTianService.addLTUser(lTUserDto);
        //查询该账号是否注册成功，如果不存在，则返回失败
        String states = liaoTianService.findIsExistByAccount(lTUserDto);
        if("200".equals(states)){
        	user.setPassword(MD5Util.md5(user.getPassword()));
            iImsUserService.createUser(user);
        }
        //插入t_ims_dept_users
        DeptUsersDto deptUsersDto = new DeptUsersDto();
        deptUsersDto.setId(UUIDGenerator.getUUID());
        deptUsersDto.setUserId(user.getId());
        deptUsersDto.setProjId(user.getProjId());
        deptUsersDto.setDeptId(user.getDeptId());
        deptUsersDto.setCreateTime(new Date());
        deptUsersDto.setStat(1);
        deptUsersDto.setIsAdmin(isExistManager);
        deptUserService.insertDeptUser(deptUsersDto);
        //插入project_users
        ProjectUsersDto projectUsersDto = new ProjectUsersDto();
        projectUsersDto.setProjId(user.getProjId());
        projectUsersDto.setUserId(user.getId());
        projectUsersDto.setStat(1);
        projectUsersDto.setCreateTime(new Date());
        projectUsersDto.setId(UUIDGenerator.getUUID());
        caterProjectUsersService.insertPUser(projectUsersDto);
        //插入角色表
        String prijId = user.getProjectIds();
        //查询该项目的请假出差报销角色ID
        ParamConfigDto paramConfigDto = new ParamConfigDto();
        paramConfigDto.setProjId(prijId);
        paramConfigDto.setParamType(paramType);
        List<ParamConfigDto> listparam = iParamConfigService.findBy(paramConfigDto);
        if(!CollectionUtils.isEmpty(listparam)){
        	for(ParamConfigDto paramDto : listparam){
        		 RoleUsersDto roleUsersDto_qj = new RoleUsersDto();
        	     roleUsersDto_qj.setId(UUIDGenerator.getUUID());
        	     roleUsersDto_qj.setUserId(user.getId());
        	     roleUsersDto_qj.setRoleId(paramDto.getParamValue());
        	     roleUsersDto_qj.setStat(1);
        	     roleUsersDto_qj.setCreateTime(new Date());
        	     iTmsRoleservice.insertRoleUser(roleUsersDto_qj);
        	}
        }
	}

}
