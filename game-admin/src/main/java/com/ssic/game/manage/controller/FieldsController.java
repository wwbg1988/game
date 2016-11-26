/**
 * 
 */
package com.ssic.game.manage.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.game.admin.pageModel.DataGrid;
import com.ssic.game.admin.pageModel.Json;
import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.admin.pageModel.Role;
import com.ssic.game.admin.pageModel.Tree;
import com.ssic.game.admin.pageModel.User;
import com.ssic.game.admin.service.ITasksService;
import com.ssic.game.admin.service.ProjectService;
import com.ssic.game.common.constant.HttpConstants;
import com.ssic.game.common.dto.FieldRoleDto;
import com.ssic.game.common.dto.FieldUserDto;
import com.ssic.game.common.dto.FieldsDto;
import com.ssic.game.common.dto.FormsDto;
import com.ssic.game.common.dto.ProcInstantsDto;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.game.common.dto.TasksDto;
import com.ssic.game.common.pojo.FieldRole;
import com.ssic.game.common.pojo.FieldUser;
import com.ssic.game.common.service.IProcInstanceService;
import com.ssic.game.manage.service.IFieldsService;
import com.ssic.game.manage.service.IFormsService;

/**		
 * <p>Title: FieldsController </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博
 * @date 2015年6月24日 上午10:30:50	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年6月24日 上午10:30:50</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping("/fieldsController")
public class FieldsController
{

    @Autowired
    private ProjectService projectService;

    @Autowired
    private IFieldsService fieldsService;

    @Autowired
    private IFormsService formService;

    @Autowired
    private ITasksService taskService;

    @Autowired
    private IProcInstanceService proceInstantsService;

    /**
     * 跳转到字段管理页面
     * 
     * @return
     */
    @RequestMapping("/manager")
    public String manager(HttpServletRequest request)
    {
        FormsDto formsDto=new FormsDto();
        List<FormsDto> forms=formService.findAll(formsDto);
        request.setAttribute("forms", forms);
        return "ims/field/fields";
    }

    
    @ResponseBody
    @RequestMapping("/findAll")
    public List<FieldsDto> findAll(FieldsDto fieldsDto,HttpServletRequest request){
        List<FieldsDto> list = fieldsService.findAllBy(fieldsDto);
        request.setAttribute("fields", list);
        return list;
    }
    
   
    /**
     * 跳转到表单字段管理页面
     * 
     * @return
     */
    @RequestMapping("/formFieldManager")
    public String formFieldPage( String formId,String projId,String procId, HttpServletRequest request)
    {
        request.setAttribute("formId", formId);
        request.setAttribute("projId", projId);
        request.setAttribute("procId", procId);
        return "ims/formfield/formsFields";
    }

    /**
     * 获取字段grid表单
     * 
     * @param fieldsDto
     * @return
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public DataGrid dataGrid(FieldsDto fieldsDto, PageHelper ph)
    {
        return fieldsService.dataGrid(fieldsDto, ph);
    }

    /**
     * 获取表单字段grid表单
     * 
     * @param fieldsDto
     * @return
     */
    @RequestMapping("/formFieldDataGrid")
    @ResponseBody
    public DataGrid formFieldDataGrid(String formId, PageHelper ph)
    {
        return fieldsService.dataGrid(formId, ph);
    }
    
    
    /**
     * 获取表单字段grid表单
     * 
     * @param fieldsDto
     * @return
     */
    @RequestMapping("/addExistFieldDataGrid")
    @ResponseBody
    public DataGrid addExistFieldDataGrid(FieldsDto fieldsDto, PageHelper ph)
    {
        return fieldsService.addExistFieldDataGrid(fieldsDto, ph);
    }

    /**
     * 跳转到添加用户页面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/addPage")
    public String addPage(HttpServletRequest request,String formId,String projId,String procId)
    {
        
        
        FieldsDto u = new FieldsDto();
        u.setId(UUID.randomUUID().toString());
        u.setFormId(formId);
        u.setProjId(projId);
        u.setProcId(procId);
        request.setAttribute("fieldsDto", u);
        return "ims/field/fieldsAdd";
    }

    /**
     * 添加字段
     * 
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Json add(FieldsDto field)
    {
        Json j = new Json();
        FieldsDto tempFields = new FieldsDto();
        tempFields.setParamName(field.getParamName());
        tempFields.setStat(1);
        tempFields.setFormId(field.getFormId());
        int counts = fieldsService.vaildField(tempFields);
        if (counts > 0)
        {
            j.setSuccess(false);
            j.setMsg("该字段已存在");
            return j;
        }
        try
        {
            field.setStat(1);
            field.setCreateTime(new Date());
            fieldsService.add(field);
            j.setSuccess(true);
            j.setMsg("添加字段成功！");
            j.setObj(field);
        }
        catch (Exception e)
        {
            // e.printStackTrace();
            j.setMsg(e.getMessage());
        }
        return j;
    }

    
    /**
     * 跳转到表单添加已存在字段页面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/formFieldAddPage")
    public String formFieldAddPage(HttpServletRequest request,String formId,String fieldId)
    {
    
        FormsDto  formsDto2=  formService.findById(formId);
        List<FormsDto> forms=formService.findSameProcForms(formsDto2.getProcId(),formId);
        request.setAttribute("formId", formId);
        request.setAttribute("formFieldId", fieldId);
        request.setAttribute("formList", forms);
        return "ims/formfield/formFieldAdd";
    }
  
    
    /**
     * 给表单添加已引用字段
     * @param  formId: 要引用字段的表单id;
     * @param  refFieldId:引用字段的id;
     * @param  formFieldId 表单的字段id;
     * @return
     */
    @RequestMapping("/addExistFields")
    @ResponseBody
    public Json addExistFields(String refFieldId,String formId,String formFieldId) {
        Json j = new Json();
        if (!StringUtils.isEmpty(refFieldId)&&!StringUtils.isEmpty(formId)&&!StringUtils.isEmpty(formFieldId)) {
           
            try {
                fieldsService. insertExistFields(refFieldId,formId,formFieldId);
                j.setMsg("添加引用字段成功！");
                j.setSuccess(true);
            } catch (Exception e) {
                // e.printStackTrace();
                j.setMsg(e.getMessage());
            }
        }
          return j;
    }
    
    /**
     * 删除表单字段
     * 
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Json delete(String id)
    {
           Json j = new Json();
           j= deleteCommon(j,id);
           return j;
    }

    /**
     * 删除表单字段
     * 
     * @param id
     * @return
     */
    @RequestMapping("/formFieldDelete")
    @ResponseBody
    public Json formFieldDelete(String fieldId, String formId)
    {
        Json j = new Json();
       
        j= deleteCommon(j,fieldId);
        return j;

    }
    
    /**
     * 删除引用的字段信息
     * 
     * @param fieldId 引用其他表单字段的当前表单字段id
     * @return
     */
    @RequestMapping("/clearRefrenceField")
    @ResponseBody
    public Json clearRefrenceField(String fieldId)
    {
        Json j = new Json();
        fieldsService.clearRefrenceField(fieldId);
        j.setMsg("清除引用字段成功！");
        j.setSuccess(true);
        return j;

    }
    

    /**
     * 跳转到字段编辑页面
     * 
     * @return
     */
    @RequestMapping("/editPage")
    public String editPage(HttpServletRequest request, String id)
    {

        FieldsDto r = fieldsService.findById(id);
        List<ProjectDto> projects = projectService.findAll();
        request.setAttribute("fieldsDto", r);
        request.setAttribute("projects", projects);
        return "ims/field/fieldsEdit";
    }

    /**
     * 编辑字段
     * 
     * @param resource
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Json edit(FieldsDto fieldDto)
    {
        Json j = new Json();
        fieldsService.update(fieldDto);
        j.setSuccess(true);
        j.setMsg("编辑成功！");
        return j;
    }

    /**
     * 跳转到用户授权页面
     * 
     * @return
     */
    @RequestMapping("/grantPage")
    public String grantPage(String formId, String fieldId, HttpServletRequest request, HttpSession session)
    {
        request.setAttribute("ids", formId);
        session.setAttribute("formIdGrant", formId);
        session.setAttribute("fieldIdGrant", fieldId);
        FieldRoleDto fieldRoleDto = new FieldRoleDto();
        fieldRoleDto.setFormId(formId);
        fieldRoleDto.setFieldId(fieldId);
        fieldRoleDto.setReaderWrite(HttpConstants.FIELD_WRITE_PER);

        List<FieldRole> roleList = fieldsService.findFieldRole(fieldRoleDto);
        User u = new User();
        String ids = "";
        if (roleList != null && roleList.size() > 0)
        {
            for (int i = 0; i < roleList.size(); i++)
            {
                if (i == roleList.size() - 1)
                {
                    ids += roleList.get(i).getRoleId();
                }
                else
                {
                    ids += roleList.get(i).getRoleId() + ",";
                }
            }

        }
        u.setRoleIds(ids);
        request.setAttribute("user", u);

        return "ims/fieldsGrant";
    }
    
    /**
     * 跳转到用户授权页面
     * 
     * @return
     */
    @RequestMapping("/grantUsersWriter")
    public String grantUsersWriter(String formId, String fieldId, HttpServletRequest request, HttpSession session)
    {
        request.setAttribute("ids", formId);
         if(session.getAttribute("formIdGrant")!=null){
            session.removeAttribute("formIdGrant");
        }
        if(session.getAttribute("fieldIdGrant")!=null){
            session.removeAttribute("fieldIdGrant");
        }
        session.setAttribute("formIdGrant", formId);
        session.setAttribute("fieldIdGrant", fieldId);
        
        FieldRoleDto fieldRoleDto = new FieldRoleDto();
        fieldRoleDto.setFormId(formId);
        fieldRoleDto.setFieldId(fieldId);
        fieldRoleDto.setReaderWrite(HttpConstants.FIELD_WRITE_PER);

        List<FieldRole> roleList = fieldsService.findFieldRole(fieldRoleDto);
        Role r = new Role();
        String ids = "";
        if (roleList != null && roleList.size() > 0)
        {
            for (int i = 0; i < roleList.size(); i++)
            {
                if (i == roleList.size() - 1)
                {
                    if(("owner").equals(roleList.get(i).getRoleId())){
                        request.setAttribute("roleOwner", "ownerRoleOk");
                    }else{
                        ids += roleList.get(i).getRoleId();
                    }
                }
                else
                {
                    if(("owner").equals(roleList.get(i).getRoleId())){
                        request.setAttribute("roleOwner", "ownerRoleOk");
                    }else{
                        ids += roleList.get(i).getRoleId() + ",";
                    }
                   
                }
            }

        }
        r.setResourceIds(ids);
        request.setAttribute("roleWriterIdss", r);
        
        
          FieldUserDto fieldUserDto = new FieldUserDto();
          fieldUserDto.setFieldId(fieldId);
          fieldUserDto.setReaderWrite(HttpConstants.FIELD_WRITE_PER);

        List<FieldUser> userList = fieldsService.findFieldUser(fieldUserDto);
        User u = new User();
        String idss = "";
        if (userList != null && userList.size() > 0)
        {
            for (int i = 0; i < userList.size(); i++)
            {
                if (i == userList.size() - 1)
                {
                    if(("owner").equals(userList.get(i).getUserId())){
                        request.setAttribute("userOwner", "ownerUserSOk");
                    }else{
                        idss += userList.get(i).getUserId();
                    }
                   
                }
                else
                {
                    if(("owner").equals(userList.get(i).getUserId())){
                        request.setAttribute("userOwner", "ownerUserSOk");
                    }else{
                        idss += userList.get(i).getUserId() + ",";
                    }
                  
                }
            }

        }
        u.setRoleIds(idss);
        request.setAttribute("userWriterIdss", u);
        

        return "ims/fieldUWriterGrant";
    }
    

    
    /**
     * 用户授权
     * 
     * @param ids
     * @return
     */
    @RequestMapping("/grantUsers")
    @ResponseBody
    public Json grantUsers(String userIds,HttpSession session,String userOwn) {
        Json j = new Json();
        boolean isEmty=false;
        if(StringUtils.isEmpty(userIds)&&StringUtils.isEmpty(userOwn)){
           isEmty=true;
        }
        if(session.getAttribute("fieldIdGrant")!=null){
            FieldsDto fieldDto = new FieldsDto();
            fieldDto.setId(session.getAttribute("fieldIdGrant").toString());
            List<FieldsDto> list = fieldsService.findAllBy(fieldDto);
            if(list!=null&&list.size()>0){
                String projId = list.get(0).getProjId();
                String procId = list.get(0).getProcId();
                if(isEmty==true){
                    fieldsService.delGrantByUser(session.getAttribute("fieldIdGrant").toString(), projId, HttpConstants.FIELD_WRITE_PER);
                    j.setSuccess(true);
                    j.setMsg("");
                    return j;
                }
                fieldsService.grantUser(session.getAttribute("fieldIdGrant").toString(), userIds, projId, procId,userOwn);
                j.setSuccess(true);
                j.setMsg("授权成功！");
                return j;
           }else{
               j.setSuccess(false);
               j.setMsg("无法寻找到字段信息，请重新赋用户权限!");
               return j;
           }
        }else{
            j.setSuccess(false);
            j.setMsg("寻找不到动作id，请重新赋用户权限!");
            return j;
        }
    }   
    
    /**
     * 用户授权
     * 
     * @param ids
     * @return
     */
    @RequestMapping("/grantUsersRead")
    @ResponseBody
    public Json grantUsersRead(String userIdss,HttpSession session) {
        Json j = new Json();
        boolean isEmty =false;
        if(StringUtils.isEmpty(userIdss)){
            isEmty = true;
        }
        if(session.getAttribute("fieldIdGrant")!=null){
            FieldsDto fieldDto = new FieldsDto();
            fieldDto.setId(session.getAttribute("fieldIdGrant").toString());
            List<FieldsDto> list = fieldsService.findAllBy(fieldDto);
            if(list!=null&&list.size()>0){
                String projId = list.get(0).getProjId();
                String procId = list.get(0).getProcId();
                if(isEmty==true){
                    fieldsService.delGrantByUser(session.getAttribute("fieldIdGrant").toString(), projId, HttpConstants.FIELD_READ_PER);
                }
                fieldsService.grantUserRead(session.getAttribute("fieldIdGrant").toString(), userIdss, projId, procId);
                j.setSuccess(true);
                j.setMsg("授权成功！");
                return j;
           }else{
               j.setSuccess(false);
               j.setMsg("无法寻找到字段信息，请重新赋用户权限!");
               return j;
           }
        }else{
            j.setSuccess(false);
            j.setMsg("寻找不到动作id，请重新赋用户权限!");
            return j;
        }
    }   


    
    /**
     * 跳转到用户授权页面
     * 
     * @return
     */
    @RequestMapping("/grantUserPage")
    public String grantUserPage(String formId, String fieldId, HttpServletRequest request, HttpSession session)
    {
        request.setAttribute("ids", formId);
        session.setAttribute("formIdGrant", formId);
        session.setAttribute("fieldIdGrant", fieldId);
          FieldUserDto fieldUserDto = new FieldUserDto();
          fieldUserDto.setFieldId(fieldId);
          fieldUserDto.setReaderWrite(HttpConstants.FIELD_WRITE_PER);

        List<FieldUser> roleList = fieldsService.findFieldUser(fieldUserDto);
        Role u = new Role();
        String ids = "";
        if (roleList != null && roleList.size() > 0)
        {
            for (int i = 0; i < roleList.size(); i++)
            {
                if (i == roleList.size() - 1)
                {
                    ids += roleList.get(i).getUserId();
                }
                else
                {
                    ids += roleList.get(i).getUserId() + ",";
                }
            }

        }
        u.setResourceIds(ids);
        request.setAttribute("role", u);

        return "ims/fieldUWriterGrant";
    }

    /**
     * 跳转到用户授权页面
     * 
     * @return
     */
    @RequestMapping("/grantUserReadPage")
    public String grantUserReadPage(String formId, String fieldId, HttpServletRequest request, HttpSession session)
    {
        request.setAttribute("ids", formId);
        if(session.getAttribute("formIdGrant")!=null){
            session.removeAttribute("formIdGrant");
        }
        if(session.getAttribute("fieldIdGrant")!=null){
            session.removeAttribute("fieldIdGrant");
        }
        session.setAttribute("formIdGrant", formId);
        session.setAttribute("fieldIdGrant", fieldId);
          FieldUserDto fieldUserDto = new FieldUserDto();
          fieldUserDto.setFieldId(fieldId);
          fieldUserDto.setReaderWrite(HttpConstants.FIELD_READ_PER);

        List<FieldUser> userLists = fieldsService.findFieldUser(fieldUserDto);
        User u = new User();
        String ids = "";
        if (userLists != null && userLists.size() > 0)
        {
            for (int i = 0; i < userLists.size(); i++)
            {
                if (i == userLists.size() - 1)
                {
                    ids += userLists.get(i).getUserId();
                }
                else
                {
                    ids += userLists.get(i).getUserId() + ",";
                }
            }

        }
        u.setRoleIds(ids);
        request.setAttribute("userRead", u);
        
        FieldUserDto writerUserDto = new FieldUserDto();
        writerUserDto.setFieldId(fieldId);
        writerUserDto.setReaderWrite(HttpConstants.FIELD_WRITE_PER);
        List<FieldUser> writerList = fieldsService.findFieldUser(writerUserDto);
        if(writerList!=null&&writerList.size()>0){
            List<String> userList = new ArrayList<String>();
            for(FieldUser fu : writerList){
                userList.add(fu.getUserId());
            }
            if(session.getAttribute("grantUserIds")!=null){
                session.removeAttribute("grantUserIds");
            }
            session.setAttribute("grantUserIds", userList);
        }
        grantReadPage(formId,fieldId,request,session);

        return "ims/fieldUReadGrant";
    }
    
    /**
     * 跳转到用户授权页面
     * 
     * @return
     */

    public void grantReadPage(String formId, String fieldId, HttpServletRequest request, HttpSession session)
    {
        FieldRoleDto fieldRoleDto = new FieldRoleDto();
        fieldRoleDto.setFormId(formId);
        fieldRoleDto.setFieldId(fieldId);
        fieldRoleDto.setReaderWrite(HttpConstants.FIELD_READ_PER);

        List<FieldRole> roleList = fieldsService.findFieldRole(fieldRoleDto);
        Role r =new Role();
        String ids = "";
        if (roleList != null && roleList.size() > 0)
        {
            for (int i = 0; i < roleList.size(); i++)
            {
                if (i == roleList.size() - 1)
                {
                    ids += roleList.get(i).getRoleId();
                }
                else
                {
                    ids += roleList.get(i).getRoleId() + ",";
                }
            }

        }
       r.setResourceIds(ids);
        request.setAttribute("roleRead", r);

        FieldRoleDto tempDto = new FieldRoleDto();
        tempDto.setFormId(formId);
        tempDto.setFieldId(fieldId);
        tempDto.setReaderWrite(HttpConstants.FIELD_WRITE_PER);
        List<FieldRole> tempList = fieldsService.findFieldRole(tempDto);
        session.setAttribute("writeGrant", tempList);

    }

    /**
     * 角色字段写入授权
     * 
     * @param ids
     * @return
     */
    @RequestMapping("/grantWriter")
    @ResponseBody
    public Json grant(HttpSession session, String roleWriterIds,String roleOwn)
    {
        
        boolean isEmty =false;
        Json j = new Json();
        if(StringUtils.isEmpty(roleWriterIds)&&StringUtils.isEmpty(roleOwn)){
            isEmty=true;
        }
        //        userService.grant(ids, user);
        if (session.getAttribute("formIdGrant") != null && session.getAttribute("fieldIdGrant") != null)
        {
            String formId = session.getAttribute("formIdGrant").toString();
            String fieldId = session.getAttribute("fieldIdGrant").toString();
            FormsDto formDto = formService.findById(formId);
            fieldsService.delGrant(formId, fieldId, formDto.getProjId(), HttpConstants.FIELD_WRITE_PER);
            if(isEmty==true){
                j.setSuccess(true);
                j.setMsg("授权成功");
                return j;
            }
            if (roleWriterIds != null && roleWriterIds.split(",").length >= 0)
            {
                fieldsService.delGrant(formId, fieldId,  formDto.getProjId(),HttpConstants.FIELD_WRITE_PER);

                for (String tempRole : roleWriterIds.split(","))
                {
                    
                    
                    
                    fieldsService.grantWriter(formId,
                        fieldId,
                        tempRole,
                        formDto.getProjId(),
                        formDto.getProcId(),null);
                }
            }
            if("roleOwnOk".equals(roleOwn)){
                fieldsService.grantWriter(formId,
                    fieldId,
                    null,
                    formDto.getProjId(),
                    formDto.getProcId(),"roleOwnOk");
            }

//            session.removeAttribute("formIdGrant");
//            session.removeAttribute("fieldIdGrant");

        }
        else
        {
            j.setSuccess(false);
            j.setMsg("查询不到表单ID或者字段ID，请退出重新操作");
            return j;
        }
        j.setSuccess(true);
        j.setMsg("授权成功！");
        return j;
    }

    /**
     * 用户授权
     * 
     * @param ids
     * @return
     */
    @RequestMapping("/grantRead")
    @ResponseBody
    public Json grantRead(HttpSession session, String roleIds)
    {
        Json j = new Json();
        boolean isEmty=false;
        if(StringUtils.isEmpty(roleIds)){
            isEmty=true;
        }
        //        userService.grant(ids, user);
        if (session.getAttribute("formIdGrant") != null && session.getAttribute("fieldIdGrant") != null)
        {
            String formId = session.getAttribute("formIdGrant").toString();
            String fieldId = session.getAttribute("fieldIdGrant").toString();
            FormsDto formDto = formService.findById(formId);
            fieldsService.delGrant(formId, fieldId, formDto.getProjId(), HttpConstants.FIELD_READ_PER);
            if(isEmty==true){
                j.setSuccess(true);
                return j;
            }
            if (roleIds != null && roleIds.split(",").length >= 0)
            {
                

                for (String tempRole : roleIds.split(","))
                {
                    fieldsService.grantRead(formId,
                        fieldId,
                        tempRole,
                        formDto.getProjId(),
                        formDto.getProcId());
                }
            }


        }
        else
        {
            j.setSuccess(false);
            j.setMsg("查询不到表单ID或者字段ID，请退出重新操作");
            return j;
        }
        j.setSuccess(true);
        j.setMsg("授权成功！");
        return j;
    }

    /**
     * 角色树(只能看到自己拥有的角色)
     * 
     * @return
     */
    @RequestMapping("/readTree")
    @ResponseBody
    public List<Tree> readTree(HttpSession session)
    {
        List<FieldRole> roleList = (List<FieldRole>) session.getAttribute("writeGrant");
        List<String> list = new ArrayList<String>();
        if (roleList != null && roleList.size() > 0)
        {
            for (FieldRole role : roleList)
            {
                list.add(role.getRoleId());
            }
        }
        session.removeAttribute("writeGrant");
        return fieldsService.findReadTree(list);

    }
    
    

    /**
     * 删除表单字段通用方法 
     * @param Json
     * @param fieldId
     * 
     * @return Json
     */
    public Json deleteCommon(Json j,String id){
        FormsDto formDto = formService.findFormsByFieldId(id);
        FormsDto dto = formService.findById(formDto.getId());
        //根据taskId获取taskType;
        TasksDto tasksDto = new TasksDto();
        tasksDto.setId(dto.getTaskId());
        List<TasksDto> taskDtos = taskService.findAllBy(tasksDto);
        int taskType = taskDtos.get(0).getType();
        //根据form表单的procId可以查询对应的流程实例数据;
        ProcInstantsDto procInstantsDto = new ProcInstantsDto();
        procInstantsDto.setProcId(dto.getProcId());
        List<ProcInstantsDto> proceInstList = proceInstantsService.findAllBy(procInstantsDto);
        //赛事进程实例存在且任务节点类型为3(结束),可以删除表单;
        boolean canDelete = (proceInstList != null && proceInstList.size() > 0 && taskType == 3);
        //赛事进程实例不存在,可以删除;
        boolean canDelete2 = (proceInstList == null);
        if (canDelete || canDelete2)
        {
            FieldsDto tempField = new FieldsDto();
            tempField.setId(id);
            fieldsService.delete(tempField);
            j.setMsg("删除成功！");
            j.setSuccess(true);
            return j;
        }
        else
        {
            j.setSuccess(false);
            j.setMsg("字段所属的表单流程实例已存在，无法删除!");
            return j;
        }
    }
    
}
