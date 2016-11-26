/**
 * 
 */
package com.ssic.game.manage.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.game.admin.pageModel.DataGrid;
import com.ssic.game.admin.pageModel.Json;
import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.admin.service.ProjectService;
import com.ssic.game.common.constant.QueryConditionType;
import com.ssic.game.common.dao.FieldsDao;
import com.ssic.game.common.dto.FieldsDto;
import com.ssic.game.common.dto.FormsDto;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.game.common.dto.query.QueryConditionDto;
import com.ssic.game.common.dto.query.QueryDto;
import com.ssic.game.common.pojo.Fields;
import com.ssic.game.manage.service.IFormsService;
import com.ssic.game.manage.service.IQueryConditionService;
import com.ssic.game.manage.service.IQueryService;

/**		
 * <p>Title: QueryConditionController </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博
 * @date 2015年7月29日 下午3:06:14	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年7月29日 下午3:06:14</p>
 * <p>修改备注：</p>
 */

@Controller
@RequestMapping("/queryConditionController")
public class QueryConditionController
{

    @Autowired
    private IQueryConditionService queryConditionService;
    @Autowired
    private IQueryService queryService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private IFormsService formsService;
    @Autowired
    private FieldsDao fieldsDao;

    /**
     * 跳转到查询条件管理页面
     * 
     * @return
     */
    @RequestMapping("/conditionManager")
    public String conditionManager(String queryId, HttpServletRequest request)
    {
        request.setAttribute("queryId", queryId);
        return "ims/queryCondition/queryCondition";
    }

    /**
     * 获取过滤条件grid表单
     * 
     * @param conditionDto
     * @return
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public DataGrid dataGrid(QueryConditionDto conditionDto, PageHelper ph)
    {
        return queryConditionService.dataGrid(conditionDto, ph);
    }

    /**
     * 跳转到查询条件添加页面
     * 
     * @return
     */
    @RequestMapping("/addPage")
    public String formFieldPage(HttpServletRequest request, String queryId)
    {
        QueryConditionDto conditionDto = new QueryConditionDto();
        conditionDto.setId(UUID.randomUUID().toString());
        conditionDto.setQueryId(queryId);
        QueryDto query = queryService.findById(queryId);
        FormsDto form = new FormsDto();
        form.setTaskId(query.getTaskId());
        List<FormsDto> listForm = formsService.findAll(form);
        if (!CollectionUtils.isEmpty(listForm))
        {
            FormsDto dto = listForm.get(0);
            List<Fields> fields = fieldsDao.findByFormId(dto.getId());
            request.setAttribute("fields", fields);
        }
        conditionDto.setQueryName(query.getName());
        ProjectDto proDto = projectService.findById(query.getProjectId());
        conditionDto.setProjName(proDto.getProjName());
        conditionDto.setProjectId(query.getProjectId());
        request.setAttribute("conditionDto", conditionDto);

        return "ims/queryCondition/queryConditionAdd";
    }

    /**
     * 添加字段查询条件
     * 
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Json add(QueryConditionDto conditionDto)
    {
        Json j = new Json();
        QueryConditionDto tempConditionDto = new QueryConditionDto();
        tempConditionDto.setQueryId(conditionDto.getQueryId());
        tempConditionDto.setStat(1);
        tempConditionDto.setFieldsId(conditionDto.getFieldsId());
        tempConditionDto.setOpt(conditionDto.getOpt());
        FieldsDto field = fieldsDao.findById(conditionDto.getFieldsId());
        int nameCount = queryConditionService.validConditionName(conditionDto.getName());
        if (nameCount > 0)
        {
            j.setSuccess(false);
            j.setMsg("过滤名称已存在,请勿重复添加.");
            return j;
        }
        //过滤条件验证
        int counts = queryConditionService.vaildField(tempConditionDto);
        //过滤排序
        int orderCount = queryConditionService.vaildOrder(tempConditionDto);
        if (QueryConditionType.EQ == conditionDto.getOpt() || QueryConditionType.GT == conditionDto.getOpt()
            || QueryConditionType.GTE == conditionDto.getOpt()
            || QueryConditionType.LIKE == conditionDto.getOpt()
            || QueryConditionType.LT == conditionDto.getOpt()
            || QueryConditionType.LTE == conditionDto.getOpt())
        {
            if (counts > 0)
            {
                j.setSuccess(false);
                j.setMsg(field.getParamDesc() + "的查询条件已存在,请勿重复添加.");
                return j;
            }
        }
        else if (QueryConditionType.ORDERBY_ASC == conditionDto.getOpt()
            || QueryConditionType.ORDERBY_DESC == conditionDto.getOpt())
        {
            if (orderCount > 0)
            {
                j.setSuccess(false);
                j.setMsg(field.getParamDesc() + "的排序条件已存在,请勿重复添加.");
                return j;
            }
        }

        try
        {
            conditionDto.setStat(1);
            conditionDto.setCreateTime(new Date());
            queryConditionService.add(conditionDto);
            j.setSuccess(true);
            j.setMsg("添加过滤条件成功！");
            j.setObj(conditionDto);
        }
        catch (Exception e)
        {
            // e.printStackTrace();
            j.setMsg(e.getMessage());
        }
        return j;
    }

    /**
     * 跳转到过滤字段编辑页面
     * 
     * @return
     */
    @RequestMapping("/editPage")
    public String editPage(HttpServletRequest request, String id)
    {
        
        QueryConditionDto conditionDto=  queryConditionService.findById(id);
       
        QueryDto query = queryService.findById(conditionDto.getQueryId());
        FormsDto form = new FormsDto();
        form.setTaskId(query.getTaskId());
        List<FormsDto> listForm = formsService.findAll(form);
        if (!CollectionUtils.isEmpty(listForm))
        {
            FormsDto dto = listForm.get(0);
            List<Fields> fields = fieldsDao.findByFormId(dto.getId());
            request.setAttribute("fields", fields);
        }
        conditionDto.setQueryName(query.getName());
        ProjectDto proDto = projectService.findById(query.getProjectId());
        conditionDto.setProjName(proDto.getProjName());
        conditionDto.setProjectId(query.getProjectId());
        request.setAttribute("conditionDto", conditionDto);
        return "ims/queryCondition/queryConditionEdit";
    }
    

    /**
     * 编辑字段
     * 
     * @param resource
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Json edit(QueryConditionDto conditionDto)
    {
        Json j = new Json();
        QueryConditionDto consDto=  queryConditionService.findById(conditionDto.getId());
        QueryConditionDto tempConditionDto = new QueryConditionDto();
        tempConditionDto.setQueryId(conditionDto.getQueryId());
        tempConditionDto.setStat(1);
        tempConditionDto.setFieldsId(conditionDto.getFieldsId());
        tempConditionDto.setOpt(conditionDto.getOpt());
       
        int nameCount = queryConditionService.validEditConditionName(conditionDto.getName(),conditionDto.getId());
        if (nameCount > 0)
        {
            j.setSuccess(false);
            j.setMsg("过滤名称已存在,请勿修改为重复.");
            return j;
        }
        conditionDto.setStat(1);
        conditionDto.setCreateTime(consDto.getCreateTime());
        conditionDto.setLastUpdateTime(new Date());
        queryConditionService.update(conditionDto);
        j.setSuccess(true);
        j.setMsg("编辑成功！");
        return j;
    }
    //queryConditionId
    @RequestMapping("/delete")
    @ResponseBody
    public Json delete(String queryConditionId){
        Json j = new Json();
        if(StringUtils.isEmpty(queryConditionId)){
            j.setMsg("字段过滤条件ID不能为空！");
            j.setSuccess(false);
            return j;
        }
        queryConditionService.delete(queryConditionId);
        j.setMsg("删除字段过滤条件成功！");
        j.setSuccess(true);
        return j;
    }

}
