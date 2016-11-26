/**
 * 
 */
package com.ssic.game.app.controller.catering;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.common.util.AppResponse;
import com.ssic.game.app.controller.dto.ProjectDto;
import com.ssic.game.app.listener.InitApplicationContext;
import com.ssic.game.app.service.IUserOperateSerivce;
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.dto.MenuDto;
import com.ssic.game.common.dto.ProcessDto;
import com.ssic.game.common.dto.WorkProcessDto;
import com.ssic.game.common.dto.WorksDto;
import com.ssic.game.common.pojo.ImsRole;
import com.ssic.game.common.service.IImsUserService;
import com.ssic.game.common.service.IMenuService;
import com.ssic.game.common.service.IRolesService;
import com.ssic.game.common.service.WorkRoleService;
import com.ssic.game.common.service.WorkService;
import com.ssic.util.model.Response;

/**
 * <p>Title: WorkController</p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015</p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * 
 * @author yuanbin
 * @date 2015年8月8日 上午11:03:24
 * @version 1.0
 *<p>修改人：yuanbin</p>
 *<p>修改时间：2015年8月8日 上午11:03:24</p>
 *<p>修改备注：</p>
 */
@Controller
@RequestMapping("/http/api/workController")
public class WorkController
{

    protected static final Log logger = LogFactory.getLog(InitApplicationContext.class);

    @Autowired
    private WorkService workService;

    @Autowired
    private WorkRoleService workRoleService;

    @Autowired
    private IMenuService iMenuService;
    @Autowired
    private IRolesService roleService;
    @Autowired
    private IImsUserService iImsUserService;
    @Autowired
    private IUserOperateSerivce userOperateSerivce;

    /**
      * work：传请假、出差、报销  
      * 
      * @param work
      * @return
      * @exception
      * @author yuanbin
      * @date 2015年8月8日 上午11:10:34
      */
    @RequestMapping(value = "/workProcess.do", method = {RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Response<WorkProcessDto> workProcess(String userId)
    {
        logger.info("WorkController workProcess.do start userid:" + userId);

        Response<WorkProcessDto> result = new Response<WorkProcessDto>();

        WorkProcessDto workProcessDto = new WorkProcessDto();
        ImsUsersDto userDto = iImsUserService.findByUserId(userId);
        List<ProjectDto> projDtoList = userOperateSerivce.queryProjectInfo(userId);
        if (!CollectionUtils.isEmpty(projDtoList))
        {
            if (projDtoList.size() > 1)
            {
                logger.info("用户所属多个项目!");
                result.setMessage(userDto.getName() + "用户所属多个项目!");
                result.setStatus(AppResponse.RETURN_FAILE);
                return result;
            }

            //请假、出差、报销List
            List<WorksDto> workProcessList = new ArrayList<WorksDto>();
            List<MenuDto> menuList = iMenuService.findByProjectId(projDtoList.get(0).getId());

            for (MenuDto menu : menuList)
            {
                WorksDto worksDtos = new WorksDto();

                if (StringUtils.isEmpty(menu.getProcId()))
                {
                    logger.info("not find menu procId");
                    continue;
                }
                worksDtos.setProcessId(menu.getProcId());
                worksDtos.setProcType(menu.getProcType());//1.请假    2.报销   3.出差  
                ProcessDto process = workService.findById(menu.getProcId());
                if (process != null)
                {
                    worksDtos.setImageUrl(process.getImageUrl());
                    worksDtos.setProcName(process.getProcName());
                }

                worksDtos.setUserId(userId);
                worksDtos.setProjectId(projDtoList.get(0).getId());
                workProcessList.add(worksDtos);
            }
            workProcessDto.setWorkProcessList(workProcessList);
        }
        else
        {
            logger.info("用户或用户所属项目不存在 !");
            result.setMessage("用户或用户所属项目不存在");
            result.setStatus(AppResponse.RETURN_FAILE);
            return result;
        }

        //后面4个权限List
        List<WorksDto> workDoList = new ArrayList<WorksDto>();
        // 过滤权限：用户-角色-菜单(查找流程)
        List<String> roleIdList = workRoleService.findByUserId(userId);// 根据用户查角色
        if (roleIdList != null && roleIdList.size() > 0)
        {
            for (String roleId : roleIdList)
            {
                ImsRole role = roleService.findById(roleId);
                if (role != null && role.getStat() == 0)
                {
                    continue;
                }
                List<MenuDto> MenuList = iMenuService.findByroleId(roleId);// 根据角色id查找menu
                if (!CollectionUtils.isEmpty(MenuList))
                {
                    for (MenuDto menu : MenuList)
                    {
                        WorksDto worksDtos = new WorksDto();
                        //权限控制接口：1.今日评价  2.历史评价  3.预警  4.菜单
                        worksDtos.setProcessId(menu.getProcId());
                        worksDtos.setRemark(menu.getRemark());//页面图标名字
                        worksDtos.setUrl(menu.getUrl());
                        workDoList.add(worksDtos);
                    }
                }
            }
            workProcessDto.setWorkDoList(workDoList);
        }
        else
        {
            logger.info("user not role meun right");
        }

        if (!StringUtils.isEmpty(workProcessDto))
        {
            result.setData(workProcessDto);
            result.setStatus(AppResponse.RETURN_SUCCESS);
            result.setMessage("返回成功!");
            return result;
        }
        else
        {
            logger.info("user meun right us null !");
            result.setMessage("用户功能查询失败!");
            result.setStatus(AppResponse.RETURN_FAILE);
            return result;
        }

    }
    /**
     * work：通过角色判断后面4个权限menu的名字、ProcessId、Url(图片路径)
     * 
     * @param work
     * @return
     * @exception
     * @author yuanbin
     * @date 2015年8月8日 上午11:10:34
     */
    //	@RequestMapping(value = "/work.do", method = {RequestMethod.GET, RequestMethod.POST })
    //	@ResponseBody
    //	public Response<WorkProcessDto> work(String userId) {
    //		Response<WorkProcessDto> result = new Response<WorkProcessDto>();
    //        
    //		WorkProcessDto workProcessDto=new WorkProcessDto();
    //
    //		List<WorksDto> workList = new ArrayList<WorksDto>();
    //		// 过滤权限：用户-角色-菜单(查找流程)
    //		List<String> roleIdList = workRoleService.findByUserId(userId);// 根据用户查角色
    //		if (roleIdList != null && roleIdList.size() > 0) {
    //			for (String roleId : roleIdList) {
    //				List<MenuDto> MenuList = iMenuService.menuByroleId(roleId);// 根据角色id查找menu
    //				for (MenuDto menu : MenuList) {
    //					WorksDto worksDtos = new WorksDto();
    //					//权限控制接口：1.今日评价  2.历史评价  3.预警  4.菜单
    //					worksDtos.setProcessId(menu.getProcId());
    //					worksDtos.setRemark(menu.getRemark());//页面图标名字
    //					worksDtos.setUrl(menu.getUrl());
    //					workList.add(worksDtos);
    //				}
    //			}
    //		}
    //		workProcessDto.setWorkDoList(workList);
    //		//如果roleIdList为空，说明用户没有角色，没有与之对应的菜单权限
    //
    //		if (!StringUtils.isEmpty(workProcessDto)){
    //			result.setData(workProcessDto);
    //			result.setStatus(AppResponse.RETURN_SUCCESS);
    //			result.setMessage("用户没有角色，没有与之对应的菜单权限!");
    //			return result;
    //		}
    //		result.setData(workProcessDto);
    //		return result;
    //	}
}
