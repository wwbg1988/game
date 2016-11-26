/**
 * 
 */
package com.ssic.catering.manage.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.admin.pageModel.DataGrid;
import com.ssic.catering.admin.pageModel.Json;
import com.ssic.catering.admin.pageModel.PageHelper;
import com.ssic.catering.base.dto.CommentDto;
import com.ssic.catering.base.dto.ConfigScoreDto;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.service.IConfigScoreService;
import com.ssic.util.BeanUtils;

/**		
 * <p>Title: ConfigScoreController </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博
 * @date 2015年8月14日 下午1:35:47	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年8月14日 下午1:35:47</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping("/configScoreController")
public class ConfigScoreController
{

    @Autowired
    private IConfigScoreService configScoreService;
    
    /**
     * 食堂配置项分数管理页面
     * 
     * @return
     */
    @RequestMapping("/manager")
    public String manager(HttpServletRequest request, String caftrotiumId)
    {

        request.setAttribute("caftrotiumId", caftrotiumId);
        return "carte/configScore/configScore";
    }

    /**
     * 
     * 评论管理grid
     * @param caftrotiumId
     * @return
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public DataGrid dataGrid(String caftrotiumId, PageHelper ph)
    {
        DataGrid dataGrid = new DataGrid();
        PageHelperDto phDto = new PageHelperDto();
        Integer beginRow = (ph.getPage() - 1) * ph.getRows();

        BeanUtils.copyProperties(ph, phDto);
        phDto.setBeginRow(beginRow);
        List<ConfigScoreDto> list = configScoreService.findALL(caftrotiumId, phDto);
        int count = configScoreService.findCount(caftrotiumId);
        dataGrid.setRows(list);
        dataGrid.setTotal(Long.valueOf(count));
        return dataGrid;
    }
    
    /**
     * 食堂配置项分数管理页面
     * 
     * @return
     */
    @RequestMapping("/configScoreValveConf")
    public String configScoreValveConf(HttpServletRequest request, String caftrotiumId)
    {
        
        return "";
    }

    
}

