package com.ssic.game.app.controller.catering;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.common.util.AppResponse;
import com.ssic.catering.common.util.CateringProject;
import com.ssic.game.common.dto.MenuDto;
import com.ssic.game.common.dto.ProcessDto;
import com.ssic.game.common.dto.WorkProcessDto;
import com.ssic.game.common.dto.WorksDto;
import com.ssic.game.common.service.IMenuService;
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
 * @date 2015年9月8日 上午11:03:24
 * @version 1.0
 *<p>修改人：yuanbin</p>
 *<p>修改时间：2015年9月8日 上午11:03:24</p>
 *<p>修改备注：</p>
 */
@Controller
@RequestMapping("/http/api/fastWorkController")
public class FastWorkController
{

    @Autowired
    private WorkService workService;

    @Autowired
    private WorkRoleService workRoleService;

    @Autowired
    private IMenuService iMenuService;
    
    
    
    /**
     * work：传请假、出差、报销  
     * 
     * @param work
     * @return
     * @exception
     * @author yuanbin
     * @date 2015年9月8日 上午11:10:34
     */
    @RequestMapping(value = "/work.do", method = {RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Response<List<WorksDto>> workProcess(String projId)
    {
        Response<List<WorksDto>> result = new Response<List<WorksDto>>();

        if(StringUtils.isEmpty(projId)){
        	result.setStatus(AppResponse.RETURN_FAILE);
        	result.setMessage("项目ID不能为空!");
        	return result;
        }
        
        
        //请假、出差、报销List
        List<WorksDto> workProcessList = new ArrayList<WorksDto>();
        List<MenuDto> menuList = iMenuService.findByProjectId(projId);

        for (MenuDto menu : menuList)
        {
            WorksDto worksDtos = new WorksDto();
            worksDtos.setProcessId(menu.getProcId());
            worksDtos.setProcType(menu.getProcType());//1.请假    2.报销   3.出差  
            if (StringUtils.isEmpty(menu.getProcId()))
            {
                continue;
            }
         
            ProcessDto process = workService.findById(menu.getProcId());
            if (process != null)
            {
                worksDtos.setImageUrl(process.getImageUrl());
                worksDtos.setProcName(process.getProcName());
            }
            worksDtos.setProjectId(projId);
            workProcessList.add(worksDtos);
        }

        if (!StringUtils.isEmpty(workProcessList))
        {
            result.setData(workProcessList);
            result.setStatus(AppResponse.RETURN_SUCCESS);
            result.setMessage("返回成功!");
            return result;
        }
        result.setData(workProcessList);
        result.setStatus(AppResponse.RETURN_FAILE);
        return result;
    }

}
