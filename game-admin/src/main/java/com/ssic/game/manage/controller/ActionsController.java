package com.ssic.game.manage.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.game.admin.pageModel.DataGrid;
import com.ssic.game.admin.pageModel.Json;
import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.admin.pageModel.Role;
import com.ssic.game.admin.pageModel.Tree;
import com.ssic.game.admin.pageModel.User;
import com.ssic.game.admin.service.IActionsService;
import com.ssic.game.admin.service.ITasksService;
import com.ssic.game.admin.service.ProjectService;
import com.ssic.game.common.constant.ActionTypeConstants;
import com.ssic.game.common.constant.HttpConstants;
import com.ssic.game.common.dto.ActionsDto;
import com.ssic.game.common.dto.FormsDto;
import com.ssic.game.common.dto.ProcInstantsDto;
import com.ssic.game.common.dto.ProcessDto;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.game.common.dto.TasksDto;
import com.ssic.game.common.service.IFormsService;
import com.ssic.game.common.service.IProcInstanceService;
import com.ssic.game.manage.service.ITImsProcessService;
import com.ssic.util.UUIDGenerator;

@Controller
@RequestMapping("/actionsController")
public class ActionsController
{

    @Autowired
    private IActionsService actionService;

    @Autowired
    private ITasksService taskService;

    @Autowired
    private ITImsProcessService iTImsProcessService;

    @Autowired
    private IProcInstanceService proceInstantsService;

    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private IFormsService formService;

    @Autowired
    @ResponseBody
    @RequestMapping("/")
    public Json findAll()
    {

        return null;
    }

    @RequestMapping("/jumps")
    public String jumpActions(HttpServletRequest request)
    {
        List<ProjectDto> list = projectService.findAll();
        request.setAttribute("proj", list);
        return "ims/Actions";
    }

    @RequestMapping("/jumpAddAction")
    public String addAction(String taskId, String projId, String procId, HttpServletRequest request,
        HttpSession session)
    {
        //        TasksDto tasksDto = new TasksDto();
        //        tasksDto.setId(taskId);
        //        List<TasksDto> taskList  = taskService.findTasks(tasksDto);
        //        if(taskList.)
        ProjectDto pro = projectService.findById(projId);
        request.setAttribute("proName", pro.getProjName());
        request.setAttribute("proId", projId);
        
        boolean taskFinsh = false;
        TasksDto tasksDtos = taskService.findById(taskId);
        if(tasksDtos!=null){
            if(tasksDtos.getType()==HttpConstants.TASK_FINISH){
                taskFinsh =true;
            }
        }
        if(taskFinsh==true){
            TasksDto tempTask = new TasksDto();
            tempTask.setId("FINISH");
            tempTask.setName("结束结点无下一步动作结点");
            List<TasksDto> resultTaskList = new ArrayList<TasksDto>();
            resultTaskList.add(tempTask);
            request.setAttribute("taskList",resultTaskList);
        }else{
            TasksDto tasksDto = new TasksDto();
            tasksDto.setProjId(projId);
            tasksDto.setProcId(procId);
            List<TasksDto> taskList = taskService.findAllBy(tasksDto);
            if (taskList != null && taskList.size() > 0)
            {
//                TasksDto addDto = new TasksDto();
//                addDto.setId("FINISH");
//                addDto.setName("流程结束");
//                taskList.add(addDto);
//                request.setAttribute("taskList", taskList);
                List<TasksDto> resultTaskList = new ArrayList<TasksDto>();
                for(TasksDto tempTask : taskList){
                    ActionsDto ac = new ActionsDto();
                    ac.setProjId(projId);
                    ac.setProcId(procId);
                    ac.setNextTaskId(tempTask.getId());
                    List<ActionsDto> tempList = actionService.findAllBy(ac);
                    if(tempList==null||tempList.size()==0){
                        resultTaskList.add(tempTask);
                    }
                }
                request.setAttribute("taskList", resultTaskList);
            }
        }
 
        
        
        
        ActionsDto actionDto = new ActionsDto();
        actionDto.setTaskId(taskId);
        actionDto.setProjId(projId);
        actionDto.setProcId(procId);

        if (session.getAttribute("jumpAdd") != null)
        {
            session.removeAttribute("jumpAdd");
        }
        session.setAttribute("jumpAdd", actionDto);

        return "ims/ActionsAdd";
    }

    @ResponseBody
    @RequestMapping("/add")
    public Json add(ActionsDto actionsDto, HttpSession session)
    {
        Json j = new Json();
        ActionsDto sessionDto = null;
        if (session.getAttribute("jumpAdd") != null)
        {
            sessionDto = (ActionsDto) session.getAttribute("jumpAdd");
        }
        else
        {
            j.setSuccess(false);
            j.setMsg("取不到结点ID，请关闭窗口重新操作");
            return j;
        }
        if(actionsDto.getType()==ActionTypeConstants.ACTION_TTPE_UPDATE||actionsDto.getType()==ActionTypeConstants.ACTION_TTPE_PASS||actionsDto.getType()==ActionTypeConstants.ACTION_TTPE_REVERT||actionsDto.getType()==ActionTypeConstants.ACTION_TTPE_REFUSE){
            ActionsDto findTaskDto= new ActionsDto();
            findTaskDto.setTaskId(sessionDto.getTaskId());
            findTaskDto.setType(actionsDto.getType());
            List<ActionsDto> list3 = actionService.findAllBy(findTaskDto);
            if(list3!=null&&list3.size()>0){
                j.setSuccess(false);
                j.setMsg("同个结点上不能产生重复动作");
                return j;
            }
        }
        
        ActionsDto temp1 = new ActionsDto();
        temp1.setName(actionsDto.getName());
        temp1.setProjId(actionsDto.getProjId());
        //动作名称可以重复！
        /*  List<ActionsDto> list2 = actionService.findAllBy(temp1);
        if (list2 != null && list2.size() > 0)
        {
            j.setSuccess(false);
            j.setMsg("动作名称不能重复");
            return j;
        }*/
        //        if (actionsDto.getType() == 1 || actionsDto.getType() == 4)
        //        {
        //            ActionsDto temp = new ActionsDto();
        //            temp.setProjId(actionsDto.getProjId());
        //            temp.setType(actionsDto.getType());
        //            List<ActionsDto> list = actionService.findAllBy(actionsDto);
        //            if (list != null && list.size() > 0)
        //            {
        //                j.setSuccess(false);
        //                j.setMsg("此项目已经有开始或者结束结点");
        //                return j;
        //            }
        //        }
        if ("FINISH".equals(actionsDto.getNextTaskId()))
        {
            ActionsDto temp = new ActionsDto();
            temp.setProjId(actionsDto.getProjId());
            temp.setTaskId(sessionDto.getTaskId());
            temp.setType(actionsDto.getType());
            temp.setNextTaskId("FINISH");
            List<ActionsDto> list = actionService.findAllBy(temp);
            if (list != null && list.size() > 0)
            {
                for(ActionsDto tempActions  : list){
                    if(tempActions.getType()!=ActionTypeConstants.ACTION_TTPE_REVERT){
                        j.setSuccess(false);
                        j.setMsg("此项目已经有结束结点");
                        return j;
                    }
                }
              
            }
        }
        if (actionsDto.getType()==ActionTypeConstants.ACTION_TTPE_PASS&& StringUtils.isEmpty(actionsDto.getNextTaskId()))
        {
            j.setSuccess(false);
            j.setMsg("下一步结点不能为空");
            return j;
        }
        if (actionsDto.getType() == ActionTypeConstants.ACTION_TTPE_CUSTOM && StringUtils.isEmpty(actionsDto.getActionUrl()))
        {
            j.setSuccess(false);
            j.setMsg("自定义url不能为空");
            return j;
        }
        if(actionsDto.getType() ==ActionTypeConstants.ACTION_TTPE_PASS){
            if (sessionDto.getTaskId().equals(actionsDto.getNextTaskId()))
            {                 
                j.setSuccess(false);
                j.setMsg("开始结点与结束结点不能相同");
                return j;
            } 
        }
                         
     //如果说非提交状态next_task_id为空
        if(actionsDto.getType()!=ActionTypeConstants.ACTION_TTPE_PASS){
            actionsDto.setNextTaskId("");
        }
        //如果是拒绝状态 next_task_id为finish
        if(actionsDto.getType()==ActionTypeConstants.ACTION_TTPE_REFUSE){
            actionsDto.setNextTaskId("FINISH");
        }
        
        actionsDto.setCreateTime(new Date());
        actionsDto.setProcId(sessionDto.getProcId());
        actionsDto.setId(UUIDGenerator.getUUID());
        actionsDto.setStat(1);
        actionsDto.setTaskId(sessionDto.getTaskId());
        actionService.insert(actionsDto);
        j.setSuccess(true);
        j.setMsg("插入成功!");
        return j;
    }

    @RequestMapping("/dataGrid")
    @ResponseBody
    public DataGrid dataGrid(ActionsDto actions, PageHelper ph)
    {
        return actionService.dataGrid(actions, ph);
    }

    @RequestMapping("/del")
    @ResponseBody
    public Json delData(ActionsDto actionsDto)
    {
        Json j = new Json();
        List<ActionsDto> list = actionService.findAllBy(actionsDto);
        if (list != null && list.size() > 0)
        {
            String taskId = list.get(0).getTaskId();
            String projId = list.get(0).getProjId();
            TasksDto tasksDto = new TasksDto();
            tasksDto.setId(taskId);
            List<TasksDto> taskList = taskService.findAllBy(tasksDto);
            if (taskList != null && taskList.size() > 0)
            {
                String proceId = taskList.get(0).getProcId();
                ProcessDto processDto = new ProcessDto();
                processDto.setId(proceId);
                List<ProcessDto> proceList = iTImsProcessService.findProcess(processDto);
                if (proceList != null && proceList.size() > 0)
                {
                    String procesId = proceList.get(0).getId();
                    ProcInstantsDto procInstantsDto = new ProcInstantsDto();
                    procInstantsDto.setProjId(projId);
                    procInstantsDto.setProcId(procesId);
                    List<ProcInstantsDto> proceInstList = proceInstantsService.findAllBy(procInstantsDto);
                    if (proceInstList != null && proceInstList.size() > 0)
                    {
                        String nowTaskId = proceInstList.get(0).getNowTaskId();
                        if (!StringUtils.isEmpty(nowTaskId))
                        {
                            j.setSuccess(true);
                            j.setMsg("此流程已经开始，不能删除实例");
                            return j;
                        }
                    }
                }
                else
                {
                    j.setSuccess(true);
                    j.setMsg("查询流程实例失败");
                    return j;
                }
            }
            else
            {
                j.setSuccess(true);
                j.setMsg("查询结点失败");
                return j;
            }
        }
        else
        {
            j.setSuccess(true);
            j.setMsg("此结点查询失败!");
            return j;
        }
        actionService.del(actionsDto);
        j.setSuccess(true);
        j.setMsg("删除成功");
        return j;
    }

    @RequestMapping("/editPage")
    public String editPage(HttpServletRequest request, String id)
    {
        ActionsDto actionsDto = new ActionsDto();
        actionsDto.setId(id);
        List<ActionsDto> list = actionService.findAllBy(actionsDto);
        if (list != null && list.size() > 0)
        {
            request.setAttribute("actions", list.get(0));
            TasksDto tasksDto = new TasksDto();
            tasksDto.setProjId(list.get(0).getProjId());
            List<TasksDto> taskList = taskService.findAllBy(tasksDto);
//            TasksDto addDto = new TasksDto();
//            addDto.setId("FINISH");
//            addDto.setName("流程结束");
//            taskList.add(addDto);
            List<TasksDto> resultTaskList = new ArrayList<TasksDto>();
            for(TasksDto tempTask : taskList){
                ActionsDto ac = new ActionsDto();
                ac.setProjId(list.get(0).getProjId());
                ac.setProcId(list.get(0).getProcId());
                ac.setNextTaskId(tempTask.getId());
                List<ActionsDto> tempList = actionService.findAllBy(ac);
                if(tempList==null||tempList.size()==0){
                    resultTaskList.add(tempTask);
                }
            }
            
            
            //修改动作下一步任务结点需要将原先任务结点信息带入
            String actionNextId = list.get(0).getNextTaskId();
            TasksDto tempTasksDto = taskService.findById(actionNextId);
            if(tempTasksDto!=null){
                resultTaskList.add(tempTasksDto);
            }
            
            
            request.setAttribute("tasks", resultTaskList);
            TasksDto currDto = new TasksDto();
            currDto.setId(list.get(0).getTaskId());
            List<TasksDto> currTask = taskService.findAllBy(currDto);
            request.setAttribute("currTaskName", currTask.get(0).getName());
            ProjectDto pro = projectService.findById(list.get(0).getProjId());
            request.setAttribute("proName", pro.getProjName());
        }
        //        List<ProjectDto> proList = projectService.findAll();
        //        request.setAttribute("proj", proList);

        return "ims/ActionsEdit";
    }

    @ResponseBody
    @RequestMapping("/edit")
    public Json edit(ActionsDto actionsDto)
    {
        Json j = new Json();
        
        int sourceType = -1;
        String actionsId = actionsDto.getId();
        ActionsDto param = new ActionsDto();
        param.setId(actionsId);
        List<ActionsDto> actionsList = actionService.findAllBy(param);
        if(actionsList!=null&&actionsList.size()>0){
            sourceType = actionsList.get(0).getType();
            
        }
        
        if(actionsDto.getType()==ActionTypeConstants.ACTION_TTPE_UPDATE||actionsDto.getType()==ActionTypeConstants.ACTION_TTPE_PASS||actionsDto.getType()==ActionTypeConstants.ACTION_TTPE_REVERT||actionsDto.getType()==ActionTypeConstants.ACTION_TTPE_REFUSE){
            ActionsDto findTaskDto= new ActionsDto();
            findTaskDto.setTaskId(actionsDto.getTaskId());
            findTaskDto.setType(actionsDto.getType());
            List<ActionsDto> list3 = actionService.findAllBy(findTaskDto);
            if(list3!=null&&list3.size()>0){
                ActionsDto temps = list3.get(0);
                if(!temps.getId().equals(actionsDto.getId())){
                    j.setSuccess(false);
                    j.setMsg("同个结点上不能产生重复动作");
                    return j;  
                }
                
            }
        }
        
//        ActionsDto temp1 = new ActionsDto();
//        temp1.setName(actionsDto.getName());
//        temp1.setProjId(actionsDto.getProjId());
//        List<ActionsDto> list2 = actionService.findAllBy(temp1);
//        if (list2 != null && list2.size() > 0)
//        {
//            for (ActionsDto tempActions : list2)
//            {
//                if (!tempActions.getId().equals(actionsDto.getId()))
//                {
//                    j.setSuccess(false);
//                    j.setMsg("动作名称不能重复");
//                    return j;
//                }
//            }
//
//        }
        //        if (actionsDto.getType() == 1 || actionsDto.getType() == 4)
        //        {
        //            ActionsDto temp = new ActionsDto();
        //            temp.setProjId(actionsDto.getProjId());
        //            temp.setType(actionsDto.getType());
        //            List<ActionsDto> list = actionService.findAllBy(actionsDto);
        //            if (list != null && list.size() > 0)
        //            {
        //                for (ActionsDto temp2Actions : list)
        //                {
        //                    if (!temp2Actions.getId().equals(actionsDto.getId()))
        //                    {
        //                        j.setSuccess(false);
        //                        j.setMsg("此项目已经有开始或者结束结点");
        //                        return j;
        //                    }
        //                }
        //
        //            }
        //        }

        if ("FINISH".equals(actionsDto.getNextTaskId()))
        {
            ActionsDto temp = new ActionsDto();
            temp.setProjId(actionsDto.getProjId());
            temp.setType(actionsDto.getType());
            temp.setTaskId(actionsDto.getTaskId());
            temp.setNextTaskId("FINISH");
            List<ActionsDto> list = actionService.findAllBy(temp);
            if (list != null && list.size() > 0)
            {
                for(ActionsDto tempActions  : list){
                    if(tempActions.getType()!=ActionTypeConstants.ACTION_TTPE_REVERT&&!(tempActions.getId()).equals(actionsDto.getId())){
                        j.setSuccess(false);
                        j.setMsg("此项目已经有结束结点");
                        return j;
                    }
                }
            }
        }
        if (StringUtils.isEmpty(actionsDto.getNextTaskId()))
        {
            j.setSuccess(false);
            j.setMsg("结点不能为空");
            return j;
        }
        if (actionsDto.getType()==ActionTypeConstants.ACTION_TTPE_PASS&& StringUtils.isEmpty(actionsDto.getNextTaskId()))
        {
            j.setSuccess(false);
            j.setMsg("结点不能为空");
            return j;
        }
        if (actionsDto.getType() == ActionTypeConstants.ACTION_TTPE_CUSTOM && StringUtils.isEmpty(actionsDto.getActionUrl()))
        {
            j.setSuccess(false);
            j.setMsg("自定义url不能为空");
            return j;
        }
        
       
                         
        
        
        if(actionsDto.getType() ==ActionTypeConstants.ACTION_TTPE_PASS){
        if (actionsDto.getTaskId().equals(actionsDto.getNextTaskId()))
        {
            j.setSuccess(false);
            j.setMsg("开始结点与结束结点不能相同");
            return j;
        }
        }
        
        //除了提交状态以外下一步结点设置为空
        if(actionsDto.getType()!=ActionTypeConstants.ACTION_TTPE_PASS){
            actionsDto.setNextTaskId("");
        }
        
      //如果是拒绝状态 next_task_id为finish
        if(actionsDto.getType()==ActionTypeConstants.ACTION_TTPE_REFUSE){
            actionsDto.setNextTaskId("FINISH");
        }
        
        //        actionsDto.setCreateTime(new Date());
        //        actionsDto.setId(UUIDGenerator.getUUID());
        //        actionsDto.setStat(DataStatus.HTTP_SUCCESS);
        //        actionService.insert(actionsDto);
        ActionsDto temp3 = new ActionsDto();
        temp3.setId(actionsDto.getId());
        List<ActionsDto> list3 = actionService.findAllBy(temp3);
        actionsDto.setLastUpdateTime(new Date());
        actionsDto.setCreateTime(list3.get(0).getCreateTime());
        actionsDto.setStat(1);
        actionService.updateFun(actionsDto);
        j.setSuccess(true);
        j.setMsg("更新成功!");
        return j;
    }

    /**
     * 跳转到角色授权页面
     * 
     * @return
     */
    @RequestMapping("/grantPage")
    public String grantPage(String ids, HttpServletRequest request, HttpSession session)
    {
        request.setAttribute("ids", ids);
        session.setAttribute("actionRoleActionIds", ids);
        User u = new User();
        if (ids != null && !ids.equalsIgnoreCase(""))
        {
            ActionsDto actionsDto = new ActionsDto();
            actionsDto.setId(ids);
            List<ActionsDto> list = actionService.findAllBy(actionsDto);
            String projId = list.get(0).getProjId();
            String result = actionService.findUserRole(ids, projId);
            u.setRoleIds(result);
            request.setAttribute("user", u);
            if(StringUtils.isEmpty(result)){
                return "ims/ActionGrant";
            }
            String[] tempResult = result.split(",");
            for(int i=0;i<tempResult.length;i++){
                if("owner".equals(tempResult[i].toString())){
                    request.setAttribute("actionRoleOwner","actionRoleOwnerOk");
                    break;
                }
            }

        }
        return "ims/ActionGrant";
    }

    /**
     * 跳转到用户授权页面
     * 
     * @return
     */
    @RequestMapping("/grantUserPage")
    public String grantUserPage(String ids, HttpServletRequest request, HttpSession session)
    {
        request.setAttribute("ids", ids);
        session.setAttribute("grantUserPerss", ids);
        Role u = new Role();
        if (ids != null && !ids.equalsIgnoreCase(""))
        {
            ActionsDto actionsDto = new ActionsDto();
            actionsDto.setId(ids);
            List<ActionsDto> list = actionService.findAllBy(actionsDto);
            String projId = list.get(0).getProjId();
            String result = actionService.findUserPers(ids, projId);
            //            String userPer = actionService.findUserPers(ids, projId);
            u.setResourceIds(result);
            request.setAttribute("role", u);
            if(StringUtils.isEmpty(result)){
                return "ims/ActionserGrant";
            }
            String[] tempResult = result.split(",");
            for(int i=0;i<tempResult.length;i++){
                if("owner".equals(tempResult[i].toString())){
                    request.setAttribute("actionUserOwner","actionUserOwnerOk");
                    break;
                }
            }
          
        }
        return "ims/ActionserGrant";
    }

    /**
     * 用户授权
     * 
     * @param ids
     * @return
     */
    @RequestMapping("/grantUsers")
    @ResponseBody
    public Json grantUsers(String resourceIds, HttpSession session,String ActionUserOwn)
    {
        Json j = new Json();
        if (session.getAttribute("grantUserPerss") != null)
        {
            actionService.grantUser(session.getAttribute("grantUserPerss").toString(), resourceIds,ActionUserOwn);
            j.setSuccess(true);
            j.setMsg("授权成功！");
            return j;
        }
        else
        {
            j.setSuccess(false);
            j.setMsg("寻找不到动作id，请重新赋用户权限!");
            return j;
        }

    }

    /**
     * 角色授权
     * 
     * @param ids
     * @return
     */
    @RequestMapping("/grant")
    @ResponseBody
    public Json grant(String roleIds, String ids,String ActionRoleOwn)
    {
        Json j = new Json();
        //        userService.grant(ids, user);
        String result = actionService.grant(ids, roleIds,ActionRoleOwn);
        if(!"".equals(result)){
            j.setSuccess(false);
            j.setMsg(result);
            return j;
        }else{
            j.setSuccess(true);
            j.setMsg("授权成功！");
            return j;
        }
       
    }

    /**
     * 角色树(只能看到自己拥有的角色)
     * 
     * @return
     */
    @RequestMapping("/tree")
    @ResponseBody
    public List<Tree> tree()
    {
        return actionService.tree();
    }

    /**
     * 角色树(只能看到自己拥有的角色)
     * 
     * @return
     */
    @RequestMapping("/userTree")
    @ResponseBody
    public List<Tree> userTree(String searchName, HttpSession session)
    {
        if (session.getAttribute("grantUserPerss") != null)
        {
            return actionService.userTree(searchName, session.getAttribute("grantUserPerss").toString());
        }
        else
        {
            return null;
        }
    }

    @RequestMapping("/fieldTree")
    @ResponseBody
    public List<Tree> fieldTree(String searchName, HttpSession session)
    {
        if (session.getAttribute("formIdGrant") != null)
        {
            return actionService.fieldTree(searchName, session.getAttribute("formIdGrant").toString());
        }
        else
        {
            return null;
        }
    }

    @RequestMapping("/userTreeBy")
    @ResponseBody
    public List<Tree> userTreeBy(HttpSession session, String searchName)
    {
        List<String> param = null;
        if (session.getAttribute("grantUserIds") != null)
        {
            param = (List<String>) session.getAttribute("grantUserIds");
            session.removeAttribute("grantUserIds");

        }
        String formId = "";
        if (session.getAttribute("formIdGrant") != null)
        {
            formId = session.getAttribute("formIdGrant").toString();
        }
        return actionService.userTreeBy(searchName, param, formId);
    }

    /**
     * 角色树(只能看到自己拥有的角色)
     * 
     * @return
     */
    @RequestMapping("/allTree")
    @ResponseBody
    public List<Tree> allTree(HttpSession session)
    {
        if (session.getAttribute("actionRoleActionIds") != null)
        {
            String actionIds = session.getAttribute("actionRoleActionIds").toString();
            ActionsDto actionsDto = new ActionsDto();
            actionsDto.setId(actionIds);
            List<ActionsDto> list = actionService.findAllBy(actionsDto);
            if (list != null && list.size() > 0)
            {
                String projId = list.get(0).getProjId();
                return actionService.allTree(projId);
            }
        }
        return null;

    }

    @RequestMapping("/allUserRoleTree")
    @ResponseBody
    public List<Tree> allUserTree(HttpSession session)
    {
        if (session.getAttribute("formIdGrant") != null)
        {
            String formId = session.getAttribute("formIdGrant").toString();
            FormsDto formsDto = formService.findByFormId(formId);
            if(formsDto!=null){
                return actionService.allTree(formsDto.getProjId());
            }
        }
        return null;
    }
}
