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
import com.ssic.catering.base.dto.CarteDto;
import com.ssic.catering.base.dto.CommentDto;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.service.ICommentService;
import com.ssic.util.BeanUtils;

/**		
 * <p>Title: CommentController </p>
 * <p>Description: 食堂评论controller</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年8月13日 下午1:55:47	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年8月13日 下午1:55:47</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping("/commentController")
public class CommentController
{

    @Autowired
    private ICommentService commentService;

    /**
     * 菜品管理页面
     * 
     * @return
     */
    @RequestMapping("/manager")
    public String manager(HttpServletRequest request, String caftrotiumId)
    {

        request.setAttribute("caftrotiumId", caftrotiumId);
        return "carte/comment/comment";
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
        List<CommentDto> list = commentService.findALL(caftrotiumId, phDto);
        int count = commentService.findCount(caftrotiumId);
        dataGrid.setRows(list);
        dataGrid.setTotal(Long.valueOf(count));
        return dataGrid;
    }

    /**
     * 删除评论
     * 
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Json delete(String id)
    {
        Json j = new Json();
        if (!StringUtils.isEmpty(id))
        {
            CommentDto tempCarte = commentService.findById(id);
            commentService.delete(tempCarte);
            j.setMsg("删除评论成功！");
            j.setSuccess(true);
        }
        else
        {
            j.setMsg("删除评论失败，id不存在！");
            j.setSuccess(false);
        }
        return j;
    }
}
