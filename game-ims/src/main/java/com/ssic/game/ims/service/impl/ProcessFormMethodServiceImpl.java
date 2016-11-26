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
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ssic.game.common.constant.ActionTypeConstants;
import com.ssic.game.common.constant.CountSignerTypeConstants;
import com.ssic.game.common.constant.FormTypeConstants;
import com.ssic.game.common.dao.ActionUserPersDao;
import com.ssic.game.common.dto.ActionsDto;
import com.ssic.game.common.dto.DeptDto;
import com.ssic.game.common.dto.DeptUsersDto;
import com.ssic.game.common.dto.FieldsDto;
import com.ssic.game.common.dto.FormsDto;
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.dto.ProcInstantsDto;
import com.ssic.game.common.dto.TasksDto;
import com.ssic.game.common.pojo.ActionUser;
import com.ssic.game.common.service.DeptUserService;
import com.ssic.game.common.service.IActionService;
import com.ssic.game.common.service.IDeptService;
import com.ssic.game.common.service.IFieldsService;
import com.ssic.game.common.service.IFormsService;
import com.ssic.game.common.service.IImsUserService;
import com.ssic.game.common.service.IProcInstanceService;
import com.ssic.game.common.service.ITasksService;
import com.ssic.game.common.util.HttpDataResponse;
import com.ssic.game.im.dto.QjyImUserDto;
import com.ssic.game.ims.eventBus.EventRegister;
import com.ssic.game.ims.model.ProcessFormRequest;
import com.ssic.game.ims.service.IProcessFormMethodService;
import com.ssic.game.ims.validator.ProcessFormValidator;
import com.ssic.ims.documents.ActionRecord;
import com.ssic.ims.documents.FormData;
import com.ssic.ims.dto.ActionRecordQuery;
import com.ssic.ims.dto.FormDataQuery;
import com.ssic.ims.service.IActionRecordService;
import com.ssic.ims.service.IFormDataOptService;
import com.ssic.ims.service.IFormDataQueryService;
import com.ssic.util.UUIDGenerator;
import com.ssic.util.constants.DataStatus;
import com.ssic.util.model.RequestResult;
import com.ssic.util.model.Response;

/**		
 * <p>Title: ProcessFormMethodServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年7月27日 上午9:17:11	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年7月27日 上午9:17:11</p>
 * <p>修改备注：</p>
 */

@Service
public class ProcessFormMethodServiceImpl implements IProcessFormMethodService
{

    @Autowired
    private IFormsService formsService;

    @Autowired
    private IFieldsService fieldService;

    @Autowired
    private IFormDataOptService formDataOptService;

    @Autowired
    private IProcInstanceService procInstanceService;

    @Autowired
    private IActionService actionService;

    @Autowired
    private IFormDataQueryService queryService;

    @Autowired
    private ITasksService taskService;

    @Autowired
    private IActionRecordService recordService;

    @Autowired
    private IImsUserService imsUserService;

    @Autowired
    private DeptUserService deptUserService;

    @Autowired
    private IDeptService deptService;

    @Autowired
    private ActionUserPersDao actionUserDao;

    @Autowired
    private ProcessFormValidator formValidator;

    @Autowired
    private EventRegister er;

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.ims.service.IProcessFormMethodService#processFormSaveOrUpdate(com.ssic.game.ims.model.ProcessFormRequest)   
    */
    private void saveOrUpdate(ProcessFormRequest formRequest, int isFinish, Integer taskType,
        Integer countSign, List<FieldsDto> fieldDtoLists, FormsDto forms)
    {
        FormsDto form = formsService.findByFormId(formRequest.getFormId());
        List<FieldsDto> fieldDtoList = fieldService.findByFormId(formRequest.getFormId());
        // Map<String, Object> fields = formRequest.getFields();
        FormData data = null;
        //如果是多人重复提交表单;
        if (countSign.equals(CountSignerTypeConstants.MORE_REPEAT_SUBMIT))
        {
            data =
                formDataQuery(form.getId(),
                    formRequest.getProcInstanceId(),
                    formRequest.getProjectId(),
                    formRequest.getUserId());
        }
        else
        {
            data =
                formDataQuery(form.getId(), formRequest.getProcInstanceId(), formRequest.getProjectId(), null);
        }

        if (data != null)
        {//如果formData对象存在，则更新;
            updateForm(data, formRequest, form, isFinish, taskType, countSign);
            //保存日志记录 
            saveActionRecord(formRequest, forms, fieldDtoLists, taskType, countSign, null);

        }
        else
        {//如果formData对象不存在，则新增formData数据;
            saveForm(formRequest, fieldDtoList, form, isFinish, taskType, countSign);
            //保存日志记录
            saveActionRecord(formRequest,
                forms,
                fieldDtoLists,
                taskType,
                countSign,
                ActionTypeConstants.ACTION_TYPE_NO_CHECKED);
        }

    }

    /** 
     * 表单提交  
     */
    private void saveForm(ProcessFormRequest formRequest, List<FieldsDto> fieldDtoList, FormsDto form,
        int isFinish, Integer taskType, Integer countSign)
    {
        ImsUsersDto userDto = imsUserService.findByUserId(formRequest.getUserId());
        String id = UUIDGenerator.getUUID32Bit();
        FormData formData = new FormData();
        formData.setId(id);
        //插入项目id
        if (!StringUtils.isEmpty(formRequest.getProjectId()))
        {
            formData.setProjectId(formRequest.getProjectId());
        }
        //插入流程id
        if (!StringUtils.isEmpty(form.getProcId()))
        {
            formData.setProcessId(form.getProcId());
        }
        //插入任务节点id
        if (!StringUtils.isEmpty(form.getTaskId()))
        {
            formData.setTaskId(form.getTaskId());
        }
        //插入任务节点类型
        if (!StringUtils.isEmpty(taskType))
        {
            formData.setType(taskType);
        }
        //插入表单id
        if (!StringUtils.isEmpty(form.getId()))
        {
            formData.setFormId(form.getId());
        }

        //插入流程实例id
        if (!StringUtils.isEmpty(formRequest.getProcInstanceId()))
        {
            formData.setProcessInstanceId(formRequest.getProcInstanceId());
        }
        //插入动作id
        if (!StringUtils.isEmpty(formRequest.getActionId()))
        {
            formData.setActionId(formRequest.getActionId());
        }
        //插入用户id
        if (!StringUtils.isEmpty(formRequest.getUserId()))
        {
            formData.setUserId(formRequest.getUserId());
        }

        //插入用户账号
        if (!StringUtils.isEmpty(userDto.getUserAccount()))
        {
            formData.setUserAccount(userDto.getUserAccount());
        }
        //插入用户名称
        if (!StringUtils.isEmpty(userDto.getName()))
        {
            formData.setUserName(userDto.getName());
        }
        DeptUsersDto deptUserDto = new DeptUsersDto();

        deptUserDto.setUserId(formRequest.getUserId());
        deptUserDto.setProjId(formRequest.getProjectId());
        List<DeptUsersDto> deptUsers = deptUserService.findAllBy(deptUserDto);

        if (!CollectionUtils.isEmpty(deptUsers))
        {
            DeptDto dd = deptService.findById(deptUsers.get(0).getDeptId());

            if (dd != null)
            {
                formData.setDeptId(dd.getId());
                formData.setDeptCode(dd.getDeptCode());
                formData.setFieldValue("deptId", dd.getId());
            }
        }
        //插入paramName值;
        Map<String, Object> fields = formRequest.getFields();
        for (FieldsDto dto : fieldDtoList)
        {
            for (String key : fields.keySet())
            {
                //判断是否是数组
                boolean isArray = fields.get(key) instanceof String[];
                if (isArray)
                {
                    String[] fieldArray = (String[]) fields.get(key);

                    if (key.equals(dto.getParamName()))
                    {
                        String filedList = "";
                        for (int i = 0; i < fieldArray.length; i++)
                        {

                            if (!StringUtils.isEmpty(fieldArray[i]))
                            {
                                if (i > 0)
                                {
                                    filedList += ",";
                                }
                                filedList += fieldArray[i];
                            }
                        }
                        formData.setFieldValue(dto.getParamName(), filedList);
                    }

                }
                else
                {
                    if (key.equals(dto.getParamName()))
                    {
                        formData.setFieldValue(dto.getParamName(), fields.get(key));
                    }
                }

            }
        }
        formData.setCountersign(countSign);
        formData.setFormStat(isFinish);
        //表单第一次提交，放入actionType值:7 (5审批通过 6:审批拒绝;7：未审批)
        formData.setFieldValue("actionType", ActionTypeConstants.ACTION_TYPE_NO_CHECKED);
        //放入指派人id
        //formData.setFieldValue("assignUserId", formRequest.getAssignUserId());
        formDataOptService.save(formData);
    }

    private void updateForm(FormData data, ProcessFormRequest formRequest, FormsDto form, int isFinish,
        Integer taskType, Integer countSign)
    {
        String instanceId = formRequest.getProcInstanceId();
        ProcInstantsDto instance = procInstanceService.findByInstanceId(instanceId);
        ActionsDto actionDto = actionService.findById(formRequest.getActionId());
        List<FieldsDto> fieldDtoList = fieldService.findByFormId(formRequest.getFormId());
        Map<String, Object> fields = formRequest.getFields();
        for (FieldsDto dto : fieldDtoList)
        {
            for (String key : fields.keySet())
            {
                if (key.equals(dto.getParamName()))
                {
                    data.setFieldValue(key, fields.get(key));
                }
            }
        }
   /*     if (!StringUtils.isEmpty(formRequest.getUserId()))
        {
            ImsUsersDto userDto = imsUserService.findByUserId(formRequest.getUserId());
            data.setUserId(formRequest.getUserId());
            data.setUserName(userDto.getName());
            data.setUserAccount(userDto.getUserAccount());
        }*/

        if (!StringUtils.isEmpty(taskType))
        {
            data.setType(taskType);
        }
        if (!StringUtils.isEmpty(formRequest.getActionId()))
        {
            data.setActionId(formRequest.getActionId());
        }
        DeptUsersDto deptUserDto = new DeptUsersDto();

        deptUserDto.setUserId(formRequest.getUserId());
        deptUserDto.setProjId(formRequest.getProjectId());
        List<DeptUsersDto> deptUsers = deptUserService.findAllBy(deptUserDto);

        if (!CollectionUtils.isEmpty(deptUsers))
        {
            DeptDto dd = deptService.findById(deptUsers.get(0).getDeptId());

            if (dd != null)
            {
                data.setDeptId(dd.getId());
                data.setDeptCode(dd.getDeptCode());
            }
        }

        FormsDto formDto = formsService.findByTaskId(instance.getNowTaskId());
        data.setFormId(formDto.getId());
        //data.setFormId(formRequest.getFormId());
        data.setTaskId(instance.getNowTaskId());
        data.setFormStat(isFinish);
        data.setCountersign(countSign);
        //表单更新提交，放入actionType值: (5审批通过 6:审批拒绝;7：未审批)
        data.setFieldValue("actionType",actionDto.getType());
        //更新操作
        formDataOptService.updateById(data);

    }

    public void saveOrUpdateFormAndRecord(ProcessFormRequest formRequest, List<FieldsDto> fieldDtoList,
        FormsDto form, int isFinish, Integer taskType, Integer countSign)
    {
        //表单保存或更新
        saveOrUpdate(formRequest, isFinish, taskType, countSign, fieldDtoList, form);
        //保存日志记录
        // saveActionRecord(formRequest, form, fieldDtoList, taskType, countSign);
    }

    /** 
     * 表单提交记录日志
     */
    public void saveActionRecord(ProcessFormRequest formRequest, FormsDto form, List<FieldsDto> fieldDtoList,
        Integer taskType, Integer countSign, Integer actionType)
    {
        String instanceId = formRequest.getProcInstanceId();
        ProcInstantsDto instance = procInstanceService.findByInstanceId(instanceId);
        String id = UUIDGenerator.getUUID32Bit();
        //获取action对象
        ActionsDto action = actionService.findById(formRequest.getActionId());
        //获取用户对象
        ImsUsersDto userDto = imsUserService.findByUserId(formRequest.getUserId());
        ActionRecord record = new ActionRecord();

        record.setId(id);
        //插入项目id
        if (!StringUtils.isEmpty(formRequest.getProjectId()))
        {
            record.setProjectId(formRequest.getProjectId());
        }
        //插入流程id
        if (!StringUtils.isEmpty(form.getProcId()))
        {
            record.setProcessId(form.getProcId());
        }
        //插入任务节点id
        if (!StringUtils.isEmpty(form.getTaskId()))
        {
            record.setTaskId(form.getTaskId());
        }
        //插入表单id
        if (!StringUtils.isEmpty(form.getId()))
        {
            record.setFormId(form.getId());
        }

        //插入流程实例id
        if (!StringUtils.isEmpty(formRequest.getProcInstanceId()))
        {
            record.setProcessInstanceId(formRequest.getProcInstanceId());
        }
        //插入动作id
        if (!StringUtils.isEmpty(formRequest.getActionId()))
        {
            record.setActionId(formRequest.getActionId());
        }

        //插入动作类型
        if (!StringUtils.isEmpty(action.getType()))
        {
            record.setActionType(action.getType());
            if (actionType != null)
            {
                record.setFieldValue("actionType", actionType);
            }
            else
            {
                //表单更新提交，放入actionType值:7 (5审批通过 6:审批拒绝;7：未审批)
                record.setFieldValue("actionType", action.getType());
                FormsDto formDto = formsService.findByTaskId(instance.getNowTaskId());
                record.setFormId(formDto.getId());
                record.setTaskId(instance.getNowTaskId());
            }
        }

        //插入paramName值;
        Map<String, Object> fields = formRequest.getFields();
        for (FieldsDto dto : fieldDtoList)
        {
            for (String key : fields.keySet())
            {
                //判断是否是数组
                boolean isArray = fields.get(key) instanceof String[];
                if (isArray)
                {
                    String[] fieldArray = (String[]) fields.get(key);

                    if (key.equals(dto.getParamName()))
                    {
                        String filedList = "";
                        for (int i = 0; i < fieldArray.length; i++)
                        {

                            if (!StringUtils.isEmpty(fieldArray[i]))
                            {
                                if (i > 0)
                                {
                                    filedList += ",";
                                }
                                filedList += fieldArray[i];
                            }
                        }
                        record.setFieldValue(dto.getParamName(), filedList);
                    }

                }
                else
                {
                    if (key.equals(dto.getParamName()))
                    {
                        record.setFieldValue(dto.getParamName(), fields.get(key));
                    }
                }

            }
        }

        //插入用户id
        if (!StringUtils.isEmpty(formRequest.getUserId()))
        {
            record.setUserId(formRequest.getUserId());
         }
        //插入用户账号
        if (!StringUtils.isEmpty(userDto.getUserAccount()))
        {
            record.setUserAccount(userDto.getUserAccount());
        }
        //插入用户名称
        if (!StringUtils.isEmpty(userDto.getName()))
        {
            record.setUserName(userDto.getName());
        }
        DeptUsersDto deptUserDto = new DeptUsersDto();
        deptUserDto.setUserId(formRequest.getUserId());
        deptUserDto.setProjId(formRequest.getProjectId());
        List<DeptUsersDto> deptUsers = deptUserService.findAllBy(deptUserDto);

        if (!CollectionUtils.isEmpty(deptUsers))
        {
            DeptDto dd = deptService.findById(deptUsers.get(0).getDeptId());

            if (dd != null)
            {
                record.setDeptId(dd.getId());
                record.setDeptCode(dd.getDeptCode());
            }
        }

        record.setCreateTime(new Date());
        record.setLastUpdateTime(new Date());
        record.setStat(1);
        //放入taskType
        record.setType(taskType);
        //放入icCountSign
        record.setCountersign(countSign);
        //放入指派人id
        //record.setFieldValue("assignUserId", formRequest.getAssignUserId());
        recordService.save(record);
    }

    /**     
     * formDataQuery：查询formData对象
     * @param formId
     * @param procInstanceId
     * @date 2015年7月2日 下午2:49:58     
     */
    private FormData formDataQuery(String formId, String procInstanceId, String projId, String userId)
    {
        FormDataQuery query = new FormDataQuery();
        if (!StringUtils.isEmpty(formId))
        {
            query.setFormId(formId);
        }
        if (!StringUtils.isEmpty(procInstanceId))
        {
            query.setProcessInstanceId(procInstanceId);
        }
        if (!StringUtils.isEmpty(projId))
        {
            query.setProjectId(projId);
        }
        if (!StringUtils.isEmpty(userId))
        {
            query.setUserId(userId);
        }
        Page<FormData> page = queryService.findByPage(query, new PageRequest(0, 20));
        //只会有一个formData;
        for (FormData data : page.getContent())
        {
            return data;
        }
        return null;
    }

    /**     
     * removeUserActionRecord：删除用户的actionRecord记录;
     * @param formId
     * @param procInstanceId
     * @date 2015年7月2日 下午2:49:58     
     */
    public void removeUserActionRecord(String userId, String procInstanceId, String formId)
    {
        ActionRecordQuery query = new ActionRecordQuery();
        query.setUserId(userId);
        query.setProcessInstanceId(procInstanceId);
        query.setFormId(formId);
        query.addOrder("createTime", Direction.DESC);
        Page<ActionRecord> page = recordService.findByPage(query, new PageRequest(0, 100));

        List<ActionRecord> records = page.getContent();
        if (!CollectionUtils.isEmpty(records))
        {
            for (ActionRecord record : records)
            {
                if (record.getActionType() == ActionTypeConstants.ACTION_TTPE_PASS)
                {
                    recordService.delete(record.getId());

                }
            }

        }

    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.ims.service.IProcessFormMethodService#mostRepeatSubmit(com.ssic.game.ims.model.ProcessFormRequest, int, java.lang.Integer)   
    */
    @Override
    public Response<RequestResult> mostRepeatSubmit(ProcessFormRequest formRequest, int finished,
        Integer countSign)
    {
        //获取表单下的字段集合
        List<FieldsDto> fieldDtoList = fieldService.findByFormId(formRequest.getFormId());
        //获取表单对象
        FormsDto form = formsService.findByFormId(formRequest.getFormId());
        //当前表单所属任务节点
        TasksDto taskDto = taskService.findByTaskId(form.getTaskId());
        //提交表单
        saveOrUpdateFormAndRecord(formRequest,
            fieldDtoList,
            form,
            FormTypeConstants.FINISHED,
            taskDto.getType(),
            countSign);
        //更新流程实例，发送卿佳云信息;
        updateInstanceAndSendMessage(formRequest);

        return new Response<RequestResult>(DataStatus.HTTP_SUCCESS,
            HttpDataResponse.FORM_SUBMIT_SUCCESS,
            new RequestResult(true, HttpDataResponse.FORM_SUBMIT_SUCCESS));
    }

    public boolean findUserActionRecord(String userId, String procInstanceId, String formId)
    {
        ActionRecordQuery query = new ActionRecordQuery();
        query.setUserId(userId);
        query.setProcessInstanceId(procInstanceId);
        query.setFormId(formId);
        query.addOrder("createTime", Direction.DESC);
        Page<ActionRecord> page = recordService.findByPage(query, new PageRequest(0, 100));

        List<ActionRecord> records = page.getContent();
        if (!CollectionUtils.isEmpty(records))
        {
            for (ActionRecord record : records)
            {
                if (record.getActionType() == ActionTypeConstants.ACTION_TTPE_PASS)
                {
                    return true;
                }
            }

        }
        return false;
    }

    /**     
     * updateProcInstance：更新流程实例
     * @param nowTaskId
     * @param procInstanceId
     * @date 2015年7月6日 下午8:55:58     
     */
    public void updateProcInstance(String procInstanceId, String nowTaskId)
    {
        //表单数据清空后，更新t_ims_proc_instance的now_task_id(上一个任务节点的taskId)
        ProcInstantsDto procInstanceDto = procInstanceService.findByInstanceId(procInstanceId);
        procInstanceDto.setNowTaskId(nowTaskId);
        //更新流程实例
        procInstanceService.updateProcInstance(procInstanceDto);

    }
    
    /**     
     * updateProcInstance：更新流程实例owner
     * @param nowTaskId
     * @param procInstanceId
     * @date 2015年7月6日 下午8:55:58     
     */
    public void updateProcInstanceOwner(String procInstanceId, String userId)
    {
        //表单数据清空后，更新t_ims_proc_instance的now_task_id(上一个任务节点的taskId)
        ProcInstantsDto procInstanceDto = procInstanceService.findByInstanceId(procInstanceId);
        procInstanceDto.setOwner(userId);
        //更新流程实例
        procInstanceService.updateProcInstance(procInstanceDto);

    }

    public void updateInstanceAndSendMessage(ProcessFormRequest formRequest)
    {
        //获取action对象;
        ActionsDto action = actionService.findById(formRequest.getActionId());
        //获取表单
        FormsDto form = formsService.findByFormId(formRequest.getFormId());
        //当前表单所属任务节点
        TasksDto taskDto = taskService.findByTaskId(form.getTaskId());
        //获取流程实例
        ProcInstantsDto procInstance = procInstanceService.findByInstanceId(formRequest.getProcInstanceId());
        ActionUser param = new ActionUser();
        param.setActionId(formRequest.getActionId());
        List<ActionUser> actionUsers = actionUserDao.findUserAll(param);
        if (!CollectionUtils.isEmpty(actionUsers))
        {
            //没有点提交的用户id集合;
            List<String> noSubmitUserIdList = new ArrayList<String>();
            for (ActionUser user : actionUsers)
            {// 判断当前动作关联的每一个用户是否是已经点过提交按钮了，如果都点提交，才可以跳转到下一个任务节点;
                boolean isAlreadySubmit =
                    findUserActionRecord(user.getUserId(),
                        formRequest.getProcInstanceId(),
                        formRequest.getFormId());
                if (!isAlreadySubmit)
                {
                    noSubmitUserIdList.add(user.getUserId());
                }
            }
            //如果当前会签节点还有用户没点提交
            if (!CollectionUtils.isEmpty(noSubmitUserIdList))
            {
                //未提交用户姓名集合
                List<String> noSubmitUserNameList = new ArrayList<String>();
                //未提交青佳云用户账号集合
                List<String> noSubmitQJYAccountList = new ArrayList<String>();
                for (String localUserId : noSubmitUserIdList)
                {
                    ImsUsersDto userDto = imsUserService.findByUserId(localUserId);
                    noSubmitUserNameList.add(userDto.getName());
                    noSubmitQJYAccountList.add(userDto.getQjyAccount());
                }
                //没提交的用户还有一个的时候，推送一条提示信息;
                if (noSubmitUserIdList.size() == 1)
                {
                    if (taskDto != null && !StringUtils.isEmpty(taskDto.getName()))
                    {
                        String message =
                            "您在" + procInstance.getProcName() + "的" + taskDto.getName()
                                + HttpDataResponse.NOT_FOR_BUSSINESS;
                        //亲家云推送提示信息
                        sendQJYMessage(noSubmitQJYAccountList, message);
                    }
                }
            }
            else
            {
                //表单数据提交或更新后，更新t_ims_proc_instance的now_task_id(插入当前action的nextTaskId)
                if (!StringUtils.isEmpty(action.getNextTaskId()))
                {
                    updateProcInstance(formRequest.getProcInstanceId(), action.getNextTaskId());
                    //亲家云推送提示信息
                    TasksDto taskDtos = taskService.findByTaskId(action.getNextTaskId());
                    if (taskDtos != null && !StringUtils.isEmpty(taskDtos.getName()))
                    {
                        String message =
                            "您在" + procInstance.getProcName() + "的" + taskDtos.getName()
                                + HttpDataResponse.NOT_FOR_BUSSINESS;
                        formValidator.sendMessageToUsers(formRequest.getProjectId(),
                            procInstance.getProcId(),
                            action.getNextTaskId(),
                            message);
                    }
                }
            }
        }

    }

    private void sendQJYMessage(List<String> accountList, String message)
    {

        QjyImUserDto qjyUser = new QjyImUserDto();
        qjyUser.setToIdList(accountList);
        qjyUser.setText(message);
        //亲家云推送提示信息
        er.getQjyEvent().post(qjyUser);

    }

}
