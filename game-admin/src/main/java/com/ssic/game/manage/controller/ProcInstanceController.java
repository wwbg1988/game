/**
 * 
 */
package com.ssic.game.manage.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.game.admin.pageModel.DataGrid;
import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.common.dto.DataGridDto;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.dto.ProcInstantsDto;
import com.ssic.game.common.dto.ProcessDto;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.game.common.dto.TasksDto;
import com.ssic.game.common.service.IProcInstanceService;

/**		
 * <p>Title: ProcInstanceController </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年7月24日 下午3:56:24	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年7月24日 下午3:56:24</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping("/procInstanceController")
public class ProcInstanceController
{

    @Autowired
    private IProcInstanceService procInstanceService;

    
    /**
     * 跳转到资源管理页面
     * 
     * @return
     */
    @RequestMapping("/manager")
    public String manager(HttpServletRequest request,String id)
    {
        request.setAttribute("id", id);
        return "ims/procInstance";
    }
    
    @ResponseBody
    @RequestMapping("/dataGrid")
    public DataGridDto dataGrid(ProcInstantsDto instanceDto, PageHelper ph) throws IllegalAccessException, InvocationTargetException
    {

        Integer beginRow = (ph.getPage() - 1) * ph.getRows();
        PageHelperDto phDto = new PageHelperDto();
        phDto.setBeginRow(beginRow);
        BeanUtils.copyProperties(ph, phDto);
        phDto.setRows(30);
        DataGridDto dataGrid = procInstanceService.findAll(instanceDto, phDto);
        return dataGrid;
    }
}
