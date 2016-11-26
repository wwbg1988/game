/**
 * 
 */
package com.ssic.game.ims.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssic.game.common.constant.HttpConstants;
import com.ssic.game.common.dao.ActionsDao;
import com.ssic.game.common.dao.FieldDictDao;
import com.ssic.game.common.dao.FieldsCloneDao;
import com.ssic.game.common.dao.FieldsDao;
import com.ssic.game.common.dao.FieldsRoleDao;
import com.ssic.game.common.dao.FieldsUserDao;
import com.ssic.game.common.dao.FormsDao;
import com.ssic.game.common.dao.ImsRoleUserDao;
import com.ssic.game.common.dao.ImsRolesDao;
import com.ssic.game.common.dao.ProcInstantsDao;
import com.ssic.game.common.dao.ProcessDao;
import com.ssic.game.common.dao.TasksDao;
import com.ssic.game.common.dto.DictDto;
import com.ssic.game.common.dto.FormsDto;
import com.ssic.game.common.dto.TaskRoleDto;
import com.ssic.game.common.dto.TaskUserDto;
import com.ssic.game.common.dto.TasksDto;
import com.ssic.game.common.pojo.Actions;
import com.ssic.game.common.pojo.FieldRole;
import com.ssic.game.common.pojo.FieldUser;
import com.ssic.game.common.pojo.Fields;
import com.ssic.game.common.pojo.FiledsClone;
import com.ssic.game.common.pojo.ImsRole;
import com.ssic.game.common.pojo.ProcInstance;
import com.ssic.game.common.pojo.RoleUsers;
import com.ssic.game.common.pojo.TaskRole;
import com.ssic.game.common.pojo.TaskUser;
import com.ssic.game.common.pojo.Tasks;
import com.ssic.game.im.service.IQjyImService;
import com.ssic.game.ims.model.Action;
import com.ssic.game.ims.model.CreateProcRequest;
import com.ssic.game.ims.model.Field;
import com.ssic.game.ims.model.TaskData;
import com.ssic.game.ims.service.ICreateFlowService;
import com.ssic.game.ims.validator.CreateFlowValidator;
import com.ssic.game.ims.validator.ProcessFormValidator;
import com.ssic.ims.documents.FormData;
import com.ssic.ims.dto.FormDataQuery;
import com.ssic.ims.service.IFormDataQueryService;
import com.ssic.util.UUIDGenerator;
import com.ssic.util.constants.DataStatus;
import com.ssic.util.model.RequestResult;
import com.ssic.util.model.Response;

/**		
 * <p>Title: CreateFlowServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author rkzhang	
 * @date 2015年6月25日 下午3:41:54	
 * @version 1.0
 * <p>修改人：rkzhang</p>
 * <p>修改时间：2015年6月25日 下午3:41:54</p>
 * <p>修改备注：</p>
 */
@Service
public class CreateFlowServiceImpl implements ICreateFlowService {

    @Autowired
    private TasksDao tasksDao;
    
    @Autowired
    private CreateFlowValidator createFlowValidator;
    
    @Autowired
    private ProcInstantsDao procInstantsDao;
    
    @Autowired
    private ProcessDao processDao;
    
    @Autowired
    private ImsRoleUserDao imsRoleUserDao;
    
    @Autowired
    private ImsRolesDao imsRoleDao;
    
    @Autowired
    private ActionsDao actionsDao;
    
    @Autowired
    private FieldsDao fieldsDao;
    
    @Autowired
    private FieldsCloneDao fieldsCloneDao;
    
    
    @Autowired
    private FormsDao formDao;
    
    @Autowired
    private IFormDataQueryService queryService;
    
    
    @Autowired
    private FieldsUserDao fieldsUserDao;
    
    @Autowired
    private FieldsRoleDao fieldRoleDao;
    
    @Autowired
    private FieldDictDao fieldDictDao;
    
    @Autowired
    private IQjyImService iQJYService;
    
    @Autowired
    private ProcessFormValidator processFormVail;
    
    @Autowired
    private ImsRoleUserDao roleUserDao;
    
    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.ims.service.ICreateFlowService#createFlow(com.ssic.game.ims.model.CreateProcRequest)   
    */
    @Override
    public Response<TaskData> createFlow(CreateProcRequest request) {
        
        TaskData taskData = new TaskData();
        taskData.setUserId(request.getUserId());
	
        RequestResult vail = createFlowValidator.vailisEmpty(request);
        if(!vail.isSuccess()){
            return new Response<TaskData>(DataStatus.HTTP_FAILE,vail.getMessage(),null);
        }
        String userId = request.getUserId();
        Tasks tasks = new Tasks();
        tasks.setProjId(request.getProjectId());
        tasks.setProcId(request.getProcessId());
        tasks.setType(1);
        List<Tasks> taskList = tasksDao.findAllBy(tasks);
        String taskId ="";
        if(taskList!=null&&taskList.size()>0){
            taskId = taskList.get(0).getId();
        }else{
            return new Response<TaskData>(DataStatus.HTTP_FAILE,"CAN NOT FIND TASK ID",null);
        }
        //判断是否有权限
        boolean flag = isCreateVail(request, taskId);
        if(flag ==false){
            return new Response<TaskData>(DataStatus.HTTP_FAILE,"没有创建流程权限",null);
        }
        
        //校验名称是否有重复
//        ProcInstance paramInst = new ProcInstance();
//        paramInst.setProcId(request.getProcessId());
//        paramInst.setProjId(request.getProjectId());
//        paramInst.setStat(1);
//        paramInst.setProcName(request.getProcName());
//        int procCounts = procInstantsDao.findCountBy(paramInst);
//        if(procCounts>0){
//            return new Response<TaskData>(DataStatus.HTTP_FAILE,"创建失败,原因:名称不能重复！",null);
//        }
        
        //寻找user对应的角色列表
        List<ImsRole> roleList = findRoleInfo(userId);
        String procInstId = UUIDGenerator.getUUID();
        
            //插入proceInstance 表
            ProcInstance procInst = new ProcInstance();
            procInst.setId(procInstId);
            procInst.setCreateTime(new Date());
            procInst.setLastUpdateTime(new Date());
            procInst.setProcId(request.getProcessId());
            procInst.setProjId(request.getProjectId());
            procInst.setNowTaskId(taskId);
//            procInst.setProcName(request.getProcName());
            procInst.setState(2);
            procInst.setOwner(request.getUserId());
            procInstantsDao.insertSelective(procInst);
        

           Map<Object,Object> resultMaps = createFlowValidator.validateActions(roleList, userId, request);
            boolean isAction = false;
            
            if("1".equals(resultMaps.get("flag").toString())){
                //获取TaskData数据
                //寻找actionList
                List<Action> actionList = findAction(request,taskId,roleList,userId);
                taskData.setActions(actionList);
//                sendMessage((List<String>)resultMaps.get("qjy"));
                //新加云发送消息
                TasksDto taskTextDto = tasksDao.findById(taskId);
                String taskText ="";
                if(taskTextDto!=null){
                    taskText=taskTextDto.getName();
                }
//                String message="您在"+request.getProcName()+"的"+taskText+"还有未办理的事项，请尽快办理";
//                processFormVail.sendMessageToUsers(request.getProjectId(), request.getProcessId(), taskId,message);
                isAction = true;
            }else{
                taskData.setActions(null);
            }
        

       
       
       Tasks ts = new Tasks();
       ts.setId(taskId);
       List<Tasks> list = tasksDao.findAllBy(ts);
       if(list!=null&&list.size()>0){
           Tasks tempTask = list.get(0);
           taskData.setId(tempTask.getId());
           taskData.setName(tempTask.getName());
           taskData.setProcInstanceId(procInst.getId());
           taskData.setProjId(tempTask.getProjId());
           taskData.setState(tempTask.getState());
           taskData.setType(tempTask.getType());
           
       }else{
           return new Response<TaskData>(DataStatus.HTTP_FAILE,"can not find task by taskId", null);
       }
       
       //fieldList 
       List<Field> fieldList = findFields(request,roleList,taskId,taskData,isAction,procInstId);
      
       taskData.setFields(fieldList);
       
        
       return new Response<TaskData>(DataStatus.HTTP_SUCCESS,"",taskData);
    }
    
    //判断是否有创建流程的权限
    private boolean isCreateVail(CreateProcRequest request,String taskId){
        boolean flag = false;
        //查询ROLELIST
        RoleUsers param = new RoleUsers();
        param.setUserId(request.getUserId());
        List<RoleUsers> userList = roleUserDao.findAllBy(param);
        if(userList!=null&&userList.size()>0){
            for(RoleUsers roleUsers : userList){
                String roleId = roleUsers.getRoleId();
                TaskRoleDto taskRoleDto = new TaskRoleDto();
                taskRoleDto.setRoleId(roleId);
                taskRoleDto.setProjId(request.getProjectId());
                taskRoleDto.setProcId(request.getProcessId());
                taskRoleDto.setTaskId(taskId);
                taskRoleDto.setStat(DataStatus.ENABLED);
                List<TaskRole> list = tasksDao.findRoleBy(taskRoleDto);
                if(list!=null&&list.size()>0){
                    flag = true;
                    return flag;
                }
            }
        }
        
        //查询用户是否有权限
        TaskUserDto tasks = new TaskUserDto();
        
        tasks.setProjId(request.getProjectId());
        tasks.setProcId(request.getProcessId());
        tasks.setUserId(request.getUserId());
        tasks.setTaskId(taskId);
        List<TaskUser> list = tasksDao.findUserBy(tasks);
        if(list!=null&&list.size()>0){
            flag = true;
        }
        
        return flag;
    }
    
    //获取用户所在角色信息
    private List<ImsRole> findRoleInfo(String userId)
    {
        RoleUsers roleUsers = new RoleUsers();
        roleUsers.setUserId(userId);
        List<RoleUsers> roleUsersList = imsRoleUserDao.findAllBy(roleUsers);
        if (roleUsersList != null && roleUsersList.size() > 0)
        {
            List<ImsRole> result = new ArrayList<ImsRole>();
            for (RoleUsers tempRoleUser : roleUsersList)
            {
                ImsRole param = new ImsRole();
                param.setId(tempRoleUser.getRoleId());
                List<ImsRole> tempRole = imsRoleDao.findAllBy(param);
                if (tempRole != null && tempRole.size() > 0)
                {
                    result.add(tempRole.get(0));
                }

            }
            return result;

        }
        else
        {
            return null;
        }

    }
    
    //寻找aciton
    private List<Action> findAction(CreateProcRequest request,String taskId,List<ImsRole> roleList,String userId)
    {
        
      
            Actions param = new Actions();
            param.setProcId(request.getProcessId());
            param.setProjId(request.getProjectId());
            param.setTaskId(taskId);
            List<Actions> list = actionsDao.findAllBy(param);
            if (list != null && list.size() > 0)
            {
                List<Action> result = new ArrayList<Action>();
                for (Actions actions : list)
                {
                    Action action = new Action();
                    boolean actionPers =  createFlowValidator.isValiAction(roleList, userId, request, actions.getId());
                    if(actionPers==true){
                        action.setActionUrl(actions.getActionUrl());
                        action.setId(actions.getId());
                        action.setName(actions.getName());
                        action.setType(actions.getType());
                        result.add(action);
                    }

                }
                return result;

            }
        
        return null;
    }
    
    //寻找字段
    private List<Field> findFields(CreateProcRequest request,List<ImsRole> roleList,String taskId,TaskData taskData,boolean isAction,String procInstId){
        FormsDto param = new FormsDto();
        param.setProjId(request.getProjectId());
        param.setTaskId(taskId);
        List<FormsDto> formList = formDao.findAll(param,null);
        if(formList!=null&&formList.size()>0){
            FormsDto temp = formList.get(0);
            taskData.setFormId(temp.getId());
            Fields fields = new Fields();
            fields.setFormId(temp.getId());
            fields.setProcId(request.getProcessId());
            fields.setProjId(request.getProjectId());
            List<Fields> fieldsList = fieldsDao.findAllBy(fields);
            if(fieldsList!=null&&fieldsList.size()>0){
              
                List<Field> resultList = new ArrayList<Field>();
                for(Fields tempFields : fieldsList){
                    RequestResult fiPer =  createFlowValidator.validateFields(roleList, request.getUserId(), request,tempFields.getId());
                    
                    //1.user 2.role
                    String fieldRoleType = "";
                    
                    if(!fiPer.success&&isAction ==false){
                       continue;
                    }else{
                        fieldRoleType=fiPer.getMessage();
                    }
                    

                    Field field = new Field();
                  
                    //加入field 字典表
                    int type = tempFields.getType();
                    
                    if(type==2||type==6||type ==10){
                        List<DictDto>dictList = fieldDictDao.findDictsByFieldId(tempFields.getId(),null);
                        if(dictList!=null&&dictList.size()>0){
                            field.setFieldsDict(dictList);
                        }
                    }
                    
                    
                    //加入fieldValue
                    String fieldCloneId = tempFields.getFieldsCloneId();
                    if (!StringUtils.isEmpty(fieldCloneId))
                    {
                        FiledsClone fieldClone = fieldsCloneDao.selectByPrimaryKey(fieldCloneId);
                        if (fieldClone != null)
                        {
                            //查询mango db
                            String cloneForm = fieldClone.getFormId();
                            FormsDto mangoForm = new FormsDto();
                            mangoForm.setId(cloneForm);
                            FormDataQuery query = new FormDataQuery();
                          query.setProcessInstanceId(procInstId);
                          query.setFormId(cloneForm);
                          Page<FormData> page =
                              queryService.findByPage(query, new PageRequest(0, 20));
                          if (page.getContent() != null && page.getContent().size() > 0)
                          {

                              Map<String,Object> cloneMap =
                                  page.getContent().get(0).getValues();
                              for(String keyMap :cloneMap.keySet()){
                                  if(keyMap.equals(fieldClone.getParamName())){
                                      field.setFieldValue(cloneMap.get(keyMap).toString());
                                      break;
                                  }
                              }
                          }
                          

                        }
                    }
                    
                    
                    
//                    //加入fieldValue
//                    String fieldCloneId = tempFields.getFieldsCloneId();
//                    if(!StringUtils.isEmpty(fieldCloneId)){
//                        FiledsClone fieldClone =  fieldsCloneDao.selectByPrimaryKey(fieldCloneId);
//                        if(fieldClone!=null){
//                            //查询mango db
//                           String cloneForm = fieldClone.getFormId();
//                           FormsDto mangoForm = new FormsDto();
//                           mangoForm.setId(cloneForm);
//                           List<FormsDto> mangoFormList = formDao.findAll(mangoForm,null);
//                           if(mangoFormList!=null&&mangoFormList.size()>0){
//                               String procId = mangoFormList.get(0).getProcId();
//                               ProcInstance proc  = new ProcInstance();
//                               proc.setProcId(procId);
//                               List<ProcInstance> procList = procInstantsDao.findAllBy(proc);
//                               if(procList!=null&&procList.size()>0){
//                                   FormDataQuery query = new FormDataQuery();
//                                   query.setProcessInstanceId(procList.get(0).getId());
//                                   query.setFormId(cloneForm);
//                                   Page<FormData> page = queryService.findByPage(query, new PageRequest(
//                                       20, 20));
//                                   if(page.getContent()!=null&&page.getContent().size()>0){
//                                       
//                                      String fieldValue =  page.getContent().get(0).getFieldValue("name").toString();
//                                      field.setFieldValue(fieldValue);
//                                   }
//                               }
//                               
//                           }
//                      
//                        }
//                    }
                    
                    
                    //加入permission 1.user 2.role   如果Action有权限且字段没有权限（3），则给予读取权限
                    if("1".equals(fieldRoleType)){
                        FieldUser fieldsUser = new FieldUser();
                        fieldsUser.setUserId(request.getUserId());
                        List<FieldUser> userList = fieldsUserDao.findUserAllBy(fieldsUser);
                        if(userList!=null&&userList.size()>0){
                            field.setPermission(userList.get(0).getReaderWrite());
                        }
                    }else if(isAction==true&&"3".equals(fieldRoleType)){
                        field.setPermission(HttpConstants.FIELD_READ_PER);
                    } else{
                        for(int i =0;i<roleList.size();i++){
                            ImsRole role = roleList.get(i);
                            if(!StringUtils.isEmpty(role.getId())){
                                FieldRole frole = new FieldRole();
                                frole.setProcId(request.getProcessId());
                                frole.setProjId(request.getProjectId());
                                frole.setRoleId(role.getId());
                                List<FieldRole> tempList = fieldRoleDao.findAllBy(frole);
                                if(tempList!=null&&tempList.size()>0){
                                    field.setPermission(tempList.get(0).getReaderWrite());
                                }
                            }
                        }
                        
                    }
                    
                    
                    field.setParamDesc(tempFields.getParamDesc());
                    field.setParamName(tempFields.getParamName());
                    field.setPattern(tempFields.getPattern());
                    field.setIsEncrypt(tempFields.getIsEncrypt());
                    field.setIsUniline(tempFields.getIsUniline());
                    field.setIsDiy(tempFields.getIsDiy());
                    field.setLength(tempFields.getLength());
                    field.setHeight(tempFields.getHeight());
                    field.setType(tempFields.getType());
                    field.setDataType(tempFields.getDataType());
                    field.setRequired(tempFields.getIsNeed());
                    field.setOrder(tempFields.getOrderId());
                    
                    resultList.add(field);
                    
                    
                }
                return resultList;
            }
            
            
        }
        return null;
    }
    

}

