/**
 * 
 */
package com.ssic.game.manage.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.game.admin.pageModel.DataGrid;
import com.ssic.game.admin.pageModel.Json;
import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.admin.service.ITasksService;
import com.ssic.game.admin.service.ProjectService;
import com.ssic.game.common.dto.DictDto;
import com.ssic.game.common.dto.FieldDictDto;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.game.common.service.IProcInstanceService;
import com.ssic.game.manage.service.IDictService;
import com.ssic.game.manage.service.IFieldDictService;
import com.ssic.game.manage.service.IFieldsService;
import com.ssic.game.manage.service.IFormsService;

/**		
 * <p>Title: DictController </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年6月25日 下午2:42:10	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年6月25日 下午2:42:10</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping("/dictController")
public class DictController
{
    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private IDictService dictService;    
    
    @Autowired
    private IFieldDictService fieldDictService;
    
    @Autowired
    private IFieldsService fieldsService;

    @Autowired
    private IFormsService formService;

    @Autowired
    private ITasksService taskService;

    @Autowired
    private IProcInstanceService proceInstantsService;

    
    /**
     * 跳转到资源管理页面
     * 
     * @return
     */
    @RequestMapping("/manager")
    public String manager() {
        return "ims/dict/dict";
    }
    
    
   
    /**
     * 跳转到表单添加字段管理页面
     * 
     * @return
     */
    @RequestMapping("/fieldDictAdd")
    public String fieldDictAdd() {
        return "ims/fieldDict/fieldDictAdd";
    }
    
    
    /**
     * 获取字段grid表单
     * 
     * @param fieldsDto
     * @return
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public DataGrid dataGrid(DictDto dictDto, PageHelper ph) {
        return dictService.dataGrid(dictDto, ph);
    }
    
    
    /**
     * 跳转到添加用户页面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/addPage")
    public String addPage(HttpServletRequest request) {
        DictDto u = new DictDto();
        u.setId(UUID.randomUUID().toString());
        request.setAttribute("dictDto", u);
        return "ims/dict/dictAdd";
    }

    /**
     * 添加字段
     * 
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Json add(DictDto dictDto) {
        Json j = new Json();
        DictDto tempDict = new DictDto();
      
        try {
            dictService.add(dictDto);
            j.setSuccess(true);
            j.setMsg("添加字典成功！");
            j.setObj(tempDict);
        } catch (Exception e) {
            // e.printStackTrace();
            j.setMsg(e.getMessage());
        }
        return j;
    }
    
    /**
     * 删除字段
     * 
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Json delete(String id) {
        Json j = new Json();
        
      List<FieldDictDto> dtos=  fieldDictService.findByDictId(id);
      //如果查询出的集合>0,则不能删除该字典;
      if(dtos.size()>0){
          j.setMsg("该字典被字段引用,无法删除哦,亲！");
          j.setSuccess(true);
          return j;
      }
        
        DictDto tempDict = new DictDto();
        tempDict.setId(id);
        dictService.delete(tempDict);
        j.setMsg("删除字典成功！");
        j.setSuccess(true);
        return j;
    }
    

    /**
     * 跳转到字段编辑页面
     * 
     * @return
     */
    @RequestMapping("/editPage")
    public String editPage(HttpServletRequest request, String id) {
        
        DictDto r = dictService.findById(id);
        List<ProjectDto> projects = projectService.findAll();
        request.setAttribute("dictDto", r);
        request.setAttribute("projects", projects);
        return "ims/dict/dictEdit";
    }
    
    /**
     * 编辑字段
     * 
     * @param resource
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Json edit(DictDto dictDto) {
        Json j = new Json();
        dictService.update(dictDto);
        j.setSuccess(true);
        j.setMsg("编辑成功！");
        return j;
    }
}

