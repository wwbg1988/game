package com.ssic.game.ims.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ssic.game.common.constant.ActionTypeConstants;
import com.ssic.game.common.constant.CountSignerTypeConstants;
import com.ssic.game.common.constant.FormTypeConstants;
import com.ssic.game.common.constant.HttpConstants;
import com.ssic.game.common.dao.ActionUserPersDao;
import com.ssic.game.common.dao.ActionsDao;
import com.ssic.game.common.dao.FieldDictDao;
import com.ssic.game.common.dao.FieldsCloneDao;
import com.ssic.game.common.dao.FieldsDao;
import com.ssic.game.common.dao.FieldsRoleDao;
import com.ssic.game.common.dao.FieldsUserDao;
import com.ssic.game.common.dao.FormsDao;
import com.ssic.game.common.dao.ImsRoleUserDao;
import com.ssic.game.common.dao.ImsRolesDao;
import com.ssic.game.common.dao.ImsUserDao;
import com.ssic.game.common.dao.ProcInstantsDao;
import com.ssic.game.common.dao.TasksDao;
import com.ssic.game.common.dto.DictDto;
import com.ssic.game.common.dto.FormsDto;
import com.ssic.game.common.pojo.ActionUser;
import com.ssic.game.common.pojo.Actions;
import com.ssic.game.common.pojo.FieldRole;
import com.ssic.game.common.pojo.FieldUser;
import com.ssic.game.common.pojo.Fields;
import com.ssic.game.common.pojo.FiledsClone;
import com.ssic.game.common.pojo.ImsRole;
import com.ssic.game.common.pojo.ImsUsers;
import com.ssic.game.common.pojo.RoleUsers;
import com.ssic.game.common.pojo.Tasks;
import com.ssic.game.ims.model.AchieveFormRequest;
import com.ssic.game.ims.model.Action;
import com.ssic.game.ims.model.CountSignUser;
import com.ssic.game.ims.model.Field;
import com.ssic.game.ims.model.TaskData;
import com.ssic.game.ims.service.IAchieveFormService;
import com.ssic.game.ims.validator.AchieveFormVaildator;
import com.ssic.ims.documents.ActionRecord;
import com.ssic.ims.documents.FormData;
import com.ssic.ims.dto.ActionRecordQuery;
import com.ssic.ims.dto.FormDataQuery;
import com.ssic.ims.service.IActionRecordService;
import com.ssic.ims.service.IFormDataQueryService;
import com.ssic.util.constants.DataStatus;
import com.ssic.util.model.RequestResult;
import com.ssic.util.model.Response;

/**		
 * <p>Title: AchiveFormServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author milkteaye	
 * @date 2015年6月25日 下午3:37:27	
 * @version 1.0
 * <p>修改人：milkteaye</p>
 * <p>修改时间：2015年6月25日 下午3:37:27</p>
 * <p>修改备注：</p>
 */
@Service
public class AchieveFormServiceImpl implements IAchieveFormService
{

    @Autowired
    private AchieveFormVaildator achieveFormVaildator;

    @Autowired
    private ImsUserDao imsUserDao;

    @Autowired
    private ImsRolesDao imsRoleDao;

    @Autowired
    private ImsRoleUserDao imsRoleUserDao;

    @Autowired
    private ActionsDao actionsDao;

    @Autowired
    private TasksDao tasksDao;

    @Autowired
    private FormsDao formDao;

    @Autowired
    private FieldsDao fieldsDao;

    @Autowired
    private FieldsCloneDao fieldsCloneDao;

    @Autowired
    private IFormDataQueryService queryService;

    @Autowired
    private FieldsUserDao fieldsUserDao;

    @Autowired
    private FieldsRoleDao fieldRoleDao;

    @Autowired
    private ProcInstantsDao procInstantsDao;

    @Autowired
    private FieldDictDao fieldDictDao;

    @Autowired
    private IActionRecordService recordService;

    @Autowired
    private ActionUserPersDao actionUserPersDao;

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.ims.service.IAchieveFormService#achiveForm(com.ssic.game.ims.model.AchieveFormRequest)   
    */
    @Override
    public Response<TaskData> achieveTask(AchieveFormRequest request)
    {

        TaskData taskData = new TaskData();
        taskData.setUserId(request.getUserId());
        //参数非空验证
        RequestResult vail = achieveFormVaildator.vailisEmpty(request);
        if (!vail.success)
        {
            return new Response<TaskData>(DataStatus.HTTP_FAILE, vail.getMessage(), null);
        }

        //        //account对应users
        //        ImsUsers imsUsers = findUserInfo(request);
        //        if(imsUsers==null){
        //            return new Response<TaskData>(DataStatus.HTTP_FAILE, "account can not be find userInfo", null);
        //        }
        String userId = request.getUserId();

        //寻找user对应的角色列表
        List<ImsRole> roleList = findRoleInfo(userId);

        //判断action权限
        RequestResult per = achieveFormVaildator.validateActions(roleList, userId, request);
        boolean isAction = false;

        if (per.success)
        {
            //寻找actionList
            List<Action> actionList = findAction(request, roleList, userId);
            taskData.setActions(actionList);
            isAction = true;
        }
        else
        {
            taskData.setActions(null);
        }

        //寻找TaskData
        Tasks tasks = new Tasks();
        tasks.setId(request.getTaskId());
        List<Tasks> list = tasksDao.findAllBy(tasks);
        //是否是会签的标识
        boolean isCounterSign = false;
        if (list != null && list.size() > 0)
        {
            Tasks tempTask = list.get(0);

            //如果为会签要传CountSignUser列表
            if (tempTask.getCountersign() == 1)
            {
                isCounterSign = true;
                List<CountSignUser> countList = new ArrayList<CountSignUser>();
                if (taskData.getActions() != null && taskData.getActions().size() > 0)
                {
                    for (Action tempAction : taskData.getActions())
                    {
                        if (tempAction.getType() == ActionTypeConstants.ACTION_TTPE_PASS)
                        {
                            String actionId = tempAction.getId();
                            ActionUser param = new ActionUser();
                            param.setActionId(actionId);
                            List<ActionUser> actionUserList = actionUserPersDao.findUserAll(param);
                            if (actionUserList != null && actionUserList.size() > 0)
                            {
                                for (ActionUser acions : actionUserList)
                                {
                                    CountSignUser tempCountUser =
                                        findUserActionRecord(acions.getUserId(),
                                            request.getProcInstanceId(),
                                            tempTask.getFormId());
                                    if (tempCountUser != null)
                                    {
                                        countList.add(tempCountUser);
                                    }
                                    else
                                    {
                                        ImsUsers imsUser = imsUserDao.findByUserId(acions.getUserId());
                                        if (imsUser != null)
                                        {
                                            CountSignUser tempCount = new CountSignUser();
                                            tempCount.setUserId(imsUser.getId());
                                            tempCount.setUserName(imsUser.getName());
                                            tempCount.setSubmit(false);
                                            countList.add(tempCount);
                                        }
                                    }
                                }
                            }
                            taskData.setCountList(countList);
                        }
                    }
                }

            }
            taskData.setId(tempTask.getId());
            taskData.setName(tempTask.getName());
            taskData.setProcInstanceId(request.getProcInstanceId());
            taskData.setProjId(tempTask.getProjId());
            taskData.setState(tempTask.getState());
            taskData.setType(tempTask.getType());

        }
        else
        {
            return new Response<TaskData>(DataStatus.HTTP_FAILE, "can not find task by taskId", null);
        }

        //fieldList 
        List<Field> fieldList = findFields(request, roleList, taskData, isAction,isCounterSign);

        taskData.setFields(fieldList);

        return new Response<TaskData>(DataStatus.HTTP_SUCCESS, "", taskData);
    }

    //寻找账号对应的ID
    //    private ImsUsers findUserInfo(AchieveFormRequest request)
    //    {
    //        return imsUserDao.findbyUserAccount(request.getAccount());
    //    }

    private CountSignUser findUserActionRecord(String userId, String procInstanceId, String formId)
    {
        //        List<CountSignUser> resultList = new ArrayList<CountSignUser>();
        ActionRecordQuery query = new ActionRecordQuery();
        query.setUserId(userId);
        query.setProcessInstanceId(procInstanceId);
        query.setFormId(formId);
        query.addOrder("createTime", Direction.DESC);
        Page<ActionRecord> page = recordService.findByPage(query, new PageRequest(0, 20));

        List<ActionRecord> records = page.getContent();
        if (!CollectionUtils.isEmpty(records))
        {
            for (ActionRecord record : records)
            {

                if (record.getActionType() == ActionTypeConstants.ACTION_TTPE_PASS)
                {
                    CountSignUser count = new CountSignUser();
                    count.setUserId(record.getUserId());
                    count.setUserName(record.getUserName());
                    count.setSubmit(true);
                    return count;
                }
            }

        }

        return null;
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
    private List<Action> findAction(AchieveFormRequest request, List<ImsRole> roleList, String userId)
    {

        Actions param = new Actions();
        param.setProjId(request.getProjectId());
        //            param.setProcId(request.getProcInstanceId());
        param.setTaskId(request.getTaskId());
        List<Actions> list = actionsDao.findAllBy(param);
        if (list != null && list.size() > 0)
        {
            List<Action> result = new ArrayList<Action>();
            for (Actions actions : list)
            {
                Action action = new Action();
                boolean actionPers =  achieveFormVaildator.isValiAction(roleList, userId, actions.getId());
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
    private List<Field> findFields(AchieveFormRequest request, List<ImsRole> roleList, TaskData taskData,
        boolean isAction,boolean isCounterSign)
    {
        FormsDto param = new FormsDto();
        param.setProjId(request.getProjectId());
        param.setTaskId(request.getTaskId());
        List<FormsDto> formList = formDao.findAll(param, null);
        if (formList != null && formList.size() > 0)
        {
            FormsDto temp = formList.get(0);
            taskData.setFormId(temp.getId());
            Fields fields = new Fields();
            fields.setFormId(temp.getId());
            //            fields.setProcId(request.getProcInstanceId());
            fields.setProjId(request.getProjectId());
            List<Fields> fieldsList = fieldsDao.findAllBy(fields);
            if (fieldsList != null && fieldsList.size() > 0)
            {

                List<Field> resultList = new ArrayList<Field>();
                for (Fields tempFields : fieldsList)
                {
                    RequestResult fiPer =
                        achieveFormVaildator.validateFields(roleList,
                            request.getUserId(),
                            request,
                            tempFields.getId());

                    //1.user 2.role
                    String fieldRoleType = "";

                    if (!fiPer.success && isAction == false)
                    {
                        continue;
                    }
                    else
                    {
                        fieldRoleType = fiPer.getMessage();
                    }
                    Field field = new Field();
                    

                    //加入field 字典表
                    int type = tempFields.getType();

                    if (type == 2 || type == 6 || type == 10)
                    {
                        List<DictDto> dictList = fieldDictDao.findDictsByFieldId(tempFields.getId(), null);
                        if (dictList != null && dictList.size() > 0)
                        {
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
                          query.setProcessInstanceId(request.getProcInstanceId());
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
                          
                          
//                            List<FormsDto> mangoFormList = formDao.findAll(mangoForm, null);
//                            if (mangoFormList != null && mangoFormList.size() > 0)
//                            {
//                                String procId = mangoFormList.get(0).getProcId();
//                                ProcInstance proc = new ProcInstance();
////                                proc.setProcId(procId);
//                                proc.setId(request.);
//                                List<ProcInstance> procList = procInstantsDao.findAllBy(proc);
//                                if (procList != null && procList.size() > 0)
//                                {
//                                    FormDataQuery query = new FormDataQuery();
//                                    query.setProcessInstanceId(procList.get(0).getId());
//                                    query.setFormId(cloneForm);
//                                    Page<FormData> page =
//                                        queryService.findByPage(query, new PageRequest(20, 20));
//                                    if (page.getContent() != null && page.getContent().size() > 0)
//                                    {
//
//                                        String fieldValue =
//                                            page.getContent().get(0).getFieldValue("name").toString();
//                                        field.setFieldValue(fieldValue);
//                                    }
//                                }
                                

//                            }

                        }
                    }

                    //加入permission 1.user 2.role   如果Action有权限且字段没有权限（3），则给予读取权限
                    if ("1".equals(fieldRoleType))
                    {
                        FieldUser fieldsUser = new FieldUser();
                        fieldsUser.setUserId(request.getUserId());
                        List<FieldUser> userList = fieldsUserDao.findUserAllBy(fieldsUser);
                        if (userList != null && userList.size() > 0)
                        {
                            field.setPermission(userList.get(0).getReaderWrite());
                        }
                    }
                    else if (isAction == true && "3".equals(fieldRoleType))
                    {
                        field.setPermission(HttpConstants.FIELD_READ_PER);
                    }
                    else
                    {
                        for (int i = 0; i < roleList.size(); i++)
                        {
                            ImsRole role = roleList.get(i);
                            if (!StringUtils.isEmpty(role.getId()))
                            {
                                FieldRole frole = new FieldRole();
                                //                                frole.setProcId(request.getProcInstanceId());
                                frole.setProjId(request.getProjectId());
                                frole.setRoleId(role.getId());
                                List<FieldRole> tempList = fieldRoleDao.findAllBy(frole);
                                if (tempList != null && tempList.size() > 0)
                                {
                                    field.setPermission(tempList.get(0).getReaderWrite());
                                }
                            }
                        }

                    }
                    //为字段加入值
                    //如果是会签列表的话 查询formData 状态finish
                    int stats = FormTypeConstants.UNFINISHED;
                    if(isCounterSign==true){
                        stats = FormTypeConstants.FINISHED;
                    }
                    Page<FormData> page =  findFormBy(request.getProcInstanceId(),taskData.getFormId() ,null, request.getProjectId(), 0, 10,stats);
                    if (page.getContent() != null && page.getContent().size() > 0&&page.getContent().get(0).getValues().get(tempFields.getParamName())!=null)
                    {
                        if(page.getContent().get(0).getValues().get(tempFields.getParamName())!=null){
                            String fieldValue =
                                page.getContent().get(0).getValues().get(tempFields.getParamName()).toString();
                            field.setFieldValue(fieldValue);
                        }

                    }
                    List<ActionRecord> recordList = findActionRecordSave(request.getUserId(), request.getProjectId(), request.getProcInstanceId(), taskData.getFormId());
                    if(recordList!=null&&recordList.size()>0&&recordList.get(0).getValues().get(tempFields.getParamName())!=null){
                        String fieldValue=recordList.get(0).getValues().get(tempFields.getParamName()).toString();
                        field.setFieldValue(fieldValue);
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
    
    private Page<FormData> findFormBy(String procInst,String formId, String userId, String projId,
        int beginRow, int size,int formStat)
    {
        FormDataQuery query = new FormDataQuery();
        if(!StringUtils.isEmpty(userId)){
            query.setUserId(userId);
        }
        if(!StringUtils.isEmpty(formId)){
            query.setFormId(formId);
        }
        query.setProjectId(projId);
        query.setFormStat(formStat);
        if (!StringUtils.isEmpty(procInst))
        {
            query.setProcessInstanceId(procInst);
        }
        query.addOrder("createTime", Direction.DESC);
        int endRow = beginRow+size;
        Page<FormData> page = queryService.findByPage(query, new PageRequest(beginRow, endRow));
        return page;
    }    /**
     * 
     * findUserActionRecord：查询actionRecord保存列表
     * @param userId
     * @param procInstanceId
     * @param formId
     * @return
     * @exception   
     * @author Administrator
     * @date 2015年7月22日 上午10:50:18
     */
    private List<ActionRecord> findActionRecordSave(String userId, String projId,String posinId,String formId)
    {
        ActionRecordQuery query = new ActionRecordQuery();
        query.setUserId(userId);
//        query.setProcessId(procId);
        query.setProjectId(projId);
        if(!StringUtils.isEmpty(posinId)){
            query.setProcessInstanceId(posinId);
        }
        if(!StringUtils.isEmpty(formId)){
            query.setFormId(formId);
        }
        query.setActionType(ActionTypeConstants.ACTION_TTPE_UPDATE);
//        query.setCountersign(CountSignerTypeConstants.IS_COUNT_SIGN);
        query.addOrder("createTime", Direction.DESC);
        Page<ActionRecord> page = recordService.findByPage(query, new PageRequest(0, 100));
        if(page!=null&&page.getContent().size()>0){
            return page.getContent();
        }else{
            return null;
        }
       
    }

}
