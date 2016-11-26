/**
 * 
 */
package com.ssic.game.manage.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.game.admin.pageModel.DataGrid;
import com.ssic.game.admin.pageModel.Json;
import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.common.dto.FieldDictDto;
import com.ssic.game.manage.service.IFieldDictService;

/**		
 * <p>Title: FieldDictController </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年6月24日 上午10:32:32	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年6月24日 上午10:32:32</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping("/fieldDictController")
public class FieldDictController{
    
    
    @Autowired
    private IFieldDictService fieldDictService;  
    
    /**
     * 跳转到字段字典管理页面
     * 
     * @return
     */
    @RequestMapping("/manager")
    public String manager(String id,HttpServletRequest request) {
        request.setAttribute("fieldId", id);
        return "ims/fieldDict/fieldDict";
    }

    /**
     * 获取字段拥有的字典Grid
     * 
     * @param user
     * @return
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public DataGrid dataGrid(String fieldId, PageHelper ph) {
        return fieldDictService.dataGrid(fieldId, ph);
    }
    
    /**
     * 添加字段字典新记录
     * 
     * @return
     */
    @RequestMapping("/insert")
    @ResponseBody
    public Json insert(String fieldId,String dictId) {
        Json j = new Json();
        if (!StringUtils.isEmpty(fieldId)&&!StringUtils.isEmpty(dictId)) {
            int counts = fieldDictService.vaildIsExists(fieldId,dictId);
            if(counts>0){
                j.setSuccess(false);
                j.setMsg("该字段字典已存在,请勿重复添加!");
                return j;
            }
            try {
                fieldDictService.insert(fieldId,dictId);
                j.setMsg("添加字段字典数据成功！");
                j.setSuccess(true);
            } catch (Exception e) {
                // e.printStackTrace();
                j.setMsg(e.getMessage());
            }
        }
          return j;
    }
    
    
    /**
     * 删除字段字典记录
     * 
     * @param fieldId
     * @param dictId
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Json delete(String fieldId,String dictId) {
        Json j = new Json();
        fieldDictService.delete(fieldId,dictId);
        j.setMsg("删除字段字典成功！");
        j.setSuccess(true);
        return j;
    }
    
 
    
    
    /**
     * 跳转到字段添加字典页面
     * 
     * @return
     */
    @RequestMapping("/fieldDictAddPage")
    public String formFieldAddPage(String fieldId,HttpServletRequest request) {
        request.setAttribute("fieldId", fieldId);
        return "ims/fieldDict/fieldDictAdd";
    }
    
    
}

