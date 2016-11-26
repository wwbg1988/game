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
import com.ssic.game.common.constant.ProcInstanceConstants;
import com.ssic.game.common.constant.TaskTypeConstants;
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
import com.ssic.game.im.service.IQjyImService;
import com.ssic.game.ims.eventBus.EventRegister;
import com.ssic.game.ims.model.ProcessFormRequest;
import com.ssic.game.ims.service.IProcessFormMethodService;
import com.ssic.game.ims.service.IProcessFormService;
import com.ssic.game.ims.validator.ProcessFormValidator;
import com.ssic.ims.documents.ActionRecord;
import com.ssic.ims.documents.FormData;
import com.ssic.ims.dto.ActionRecordQuery;
import com.ssic.ims.dto.FormDataQuery;
import com.ssic.ims.service.IActionRecordService;
import com.ssic.ims.service.IFormDataOptService;
import com.ssic.ims.service.IFormDataQueryService;
import com.ssic.util.constants.DataStatus;
import com.ssic.util.model.RequestResult;
import com.ssic.util.model.Response;

/**     
 * <p>Title: ProcessFormServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author rkzhang  
 * @date 2015年6月25日 下午3:46:05   
 * @version 1.0
 * <p>修改人：rkzhang</p>
 * <p>修改时间：2015年6月25日 下午3:46:05</p>
 * <p>修改备注：</p>
 */
@Service
public class ProcessFormServiceImpl implements IProcessFormService
{

    @Autowired
    private ProcessFormValidator formValidator;

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
    private IQjyImService qjyImService;
    @Autowired
    private EventRegister er;

    @Autowired
    private IProcessFormMethodService processFormMethodService;

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.ims.service.IProcessFormService#processForm(java.util.Map)   
    */
    @Override
    public Response<RequestResult> processForm(ProcessFormRequest formRequest)
    {
        ActionsDto action = actionService.findById(formRequest.getActionId());
        
        if (StringUtils.isEmpty(formRequest.getProcInstanceId()))
        {
            RequestResult validateResult = new RequestResult(false, HttpDataResponse.PROCINSTANCE_IS_NULL);
            //验证失败返回错误信息
            return new Response<RequestResult>(DataStatus.HTTP_FAILE, "流程实例为空", validateResult);

        }
        
        ProcInstantsDto procInstance = procInstanceService.findByInstanceId(formRequest.getProcInstanceId());
        //如果用户动作存在且动作类型不为null
        if (action != null && !StringUtils.isEmpty(action.getType()))
        {
            //如果是提交表单，才去验证表单;
            if (action.getType() == ActionTypeConstants.ACTION_TTPE_PASS)
            {
                //验证表单
                RequestResult validateResult = formValidator.validate(formRequest);
                if (!validateResult.success)
                {
                    //验证失败返回错误信息
                    return new Response<RequestResult>(DataStatus.HTTP_FAILE,
                        validateResult.message,
                        validateResult);
                }
            }
            List<FieldsDto> fieldDtoList = fieldService.findByFormId(formRequest.getFormId());

            FormsDto form = formsService.findByFormId(formRequest.getFormId());
            //当前表单所属任务节点
            TasksDto taskDto = taskService.findByTaskId(form.getTaskId());
            //测试表单类型为多人重复表单
            //taskDto.setCountersign(CountSignerTypeConstants.MORE_REPEAT_SUBMIT);
            //action动作如果是1或2，1：保存，2：更新,3退回，4自定义，5提交）
            if (action.getType() == ActionTypeConstants.ACTION_TTPE_ADD
                || action.getType() == ActionTypeConstants.ACTION_TTPE_UPDATE)
            {
                //保存或更新表单和保存日志
                processFormMethodService.saveOrUpdateFormAndRecord(formRequest,
                    fieldDtoList,
                    form,
                    FormTypeConstants.UNFINISHED,
                    taskDto.getType(),
                    taskDto.getCountersign());

                return new Response<RequestResult>(DataStatus.HTTP_SUCCESS,
                    HttpDataResponse.FORM_SAVE_SUCCESS,
                    new RequestResult(true, HttpDataResponse.FORM_SAVE_SUCCESS));
            }
            else if (action.getType() == ActionTypeConstants.ACTION_TTPE_REVERT)
            {
                //action动作如果是3（回退）
                revertForm(form, formRequest, FormTypeConstants.UNFINISHED, taskDto.getCountersign());

                //退回时发送QJY信息
                sendQJYMessage(formRequest.getUserId(), HttpDataResponse.REVERT_FORM + taskDto.getName()
                    + HttpDataResponse.SUCCESS);

                //表单每次提交或更新都会生成一条actionRecord日志记录（针对每个action动作）;
                processFormMethodService.saveActionRecord(formRequest,
                    form,
                    fieldDtoList,
                    taskDto.getType(),
                    CountSignerTypeConstants.IS_NOT_COUNT_SIGN,null);

                return new Response<RequestResult>(DataStatus.HTTP_SUCCESS,
                    HttpDataResponse.REVERT_FORM_SUCCESS,
                    new RequestResult(true, HttpDataResponse.REVERT_FORM_SUCCESS));
            }
            else if (action.getType() == ActionTypeConstants.ACTION_TTPE_CUSTOM)
            {//action动作如果是4（自定义）,暂不处理

            }
            else if (action.getType() == ActionTypeConstants.ACTION_TTPE_PASS)
            {
                //action动作如果是5(提交按钮)，才可以往下一步跳转.
                //如果任务节点不是会签;则直接跳到下一个任务节点;
                if (CountSignerTypeConstants.IS_NOT_COUNT_SIGN.equals(taskDto.getCountersign()))
                { //不是会签
                  //跳转前先保存或更新表单和保存日志
                    processFormMethodService.saveOrUpdateFormAndRecord(formRequest,
                        fieldDtoList,
                        form,
                        FormTypeConstants.FINISHED,
                        taskDto.getType(),
                        CountSignerTypeConstants.IS_NOT_COUNT_SIGN);

                    //表单数据提交或更新后，更新t_ims_proc_instance的now_task_id(插入当前action的nextTaskId)
                    if (!StringUtils.isEmpty(action.getNextTaskId()))
                    {
                        processFormMethodService.updateProcInstance(formRequest.getProcInstanceId(),
                            action.getNextTaskId());
                        //亲家云推送提示信息
                        TasksDto taskDtoss = taskService.findByTaskId(action.getNextTaskId());
                        if (taskDtoss != null && !StringUtils.isEmpty(taskDtoss.getName()))
                        {
                            String message =
                                "您在" + procInstance.getProcName() + "的" + taskDtoss.getName()
                                    + HttpDataResponse.NOT_FOR_BUSSINESS;
                            formValidator.sendMessageToUsers(formRequest.getProjectId(),
                                procInstance.getProcId(),
                                action.getNextTaskId(),
                                message);
                        }
                    }
                }
                else if (CountSignerTypeConstants.IS_COUNT_SIGN.equals(taskDto.getCountersign()))
                { //是会签
                    processFormMethodService.mostRepeatSubmit(formRequest,
                        FormTypeConstants.FINISHED,
                        CountSignerTypeConstants.IS_COUNT_SIGN);
                }
                else if (CountSignerTypeConstants.MORE_REPEAT_SUBMIT.equals(taskDto.getCountersign()))
                {//多人重复提交表单(单人)
                    processFormMethodService.mostRepeatSubmit(formRequest,
                        FormTypeConstants.FINISHED,
                        CountSignerTypeConstants.MORE_REPEAT_SUBMIT);
                }
            }
            else if (action.getType() == ActionTypeConstants.ACTION_TTPE_REFUSE)
            { //拒绝

                processFormMethodService.saveOrUpdateFormAndRecord(formRequest,
                    fieldDtoList,
                    form,
                    FormTypeConstants.FINISHED,
                    taskDto.getType(),
                    CountSignerTypeConstants.IS_NOT_COUNT_SIGN);
                //更新流程实例 state为4;(拒绝成功)
                updateProcInstanceState(formRequest.getProcInstanceId(), ProcInstanceConstants.AUDIT_FAILURE);
                return new Response<RequestResult>(DataStatus.HTTP_SUCCESS,
                    HttpDataResponse.AUDIT_FAILURE,
                    new RequestResult(true, HttpDataResponse.AUDIT_FAILURE));
            }
            //如果当前表单所属任务节点已经结束，则需要更新流实例的state=3(结束)
            if (taskDto.getType() == TaskTypeConstants.TASK_TTPE_END)
            {
                //更新流程实例 state为3;(审核通过)
                updateProcInstanceState(formRequest.getProcInstanceId(),ProcInstanceConstants.AUDIT_PASS);
            }

            return new Response<RequestResult>(DataStatus.HTTP_SUCCESS,
                HttpDataResponse.FORM_SUBMIT_SUCCESS,
                new RequestResult(true, HttpDataResponse.FORM_SUBMIT_SUCCESS));
        }
        else
        {
            return new Response<RequestResult>(DataStatus.HTTP_FAILE,
                HttpDataResponse.PROCINSTANCE_INFO_EXCEPTION,
                new RequestResult(false, HttpDataResponse.FORM_SUBMIT_FALID));
        }

    }

    /**     
     * revertForm：回退表单
     * @param form
     * @param procInstanceId
     * @date 2015年7月2日 下午2:37:58     
     */
    public void revertForm(FormsDto form, ProcessFormRequest formRequest, int isFinish, Integer isCountSign)
    {
        //获取上一个任务节点的taskDto对象 如果流程实例存在，取倒数第一个，
        FormData formData = formDataQuery(formRequest.getProcInstanceId(), formRequest.getProjectId());

        if (formData != null)
        {
            ProcInstantsDto procInstance =
                procInstanceService.findByInstanceId(formData.getProcessInstanceId());
            TasksDto taskDto = taskService.findByTaskId(formData.getTaskId());

            //更新上一个任务节点的表单的办理状态：0:未办理
            formData.setFormStat(isFinish);
            formDataOptService.updateById(formData);
            //更新流程实例，往回跳转
            processFormMethodService.updateProcInstance(formRequest.getProcInstanceId(), formData.getTaskId());
            //发送卿佳云推送信息
            if (taskDto != null && procInstance != null)
            {
                ActionUser param = new ActionUser();
                param.setActionId(formData.getActionId());
                List<ActionUser> actionUsers = actionUserDao.findUserAll(param);
                String message =
                    "您在" + procInstance.getProcName() + "的" + taskDto.getName()
                        + HttpDataResponse.NOT_FOR_BUSSINESS;
                if (!CollectionUtils.isEmpty(actionUsers))
                {
                    for (ActionUser user : actionUsers)
                    {
                        //给上一个任务节点的每一个用户推送未办理信息
                        sendQJYMessage(user.getUserId(), message);
                        //同时，删除上一步的ActionRecord记录;
                        processFormMethodService.removeUserActionRecord(user.getUserId(),
                            formRequest.getProcInstanceId(),
                            formData.getFormId());
                    }
                }
            }
        }

    }

    // formDataListQuery
    /**     
     * formDataQuery：查询formData对象
     * @param formId
     * @param procInstanceId
     * @date 2015年7月2日 下午2:49:58     
     */
    private FormData formDataQuery(String procInstanceId, String projId)
    {
        FormDataQuery query = new FormDataQuery();
        query.setProcessInstanceId(procInstanceId);
        query.setProjectId(projId);
        query.addOrder("createTime", Direction.DESC);
        Page<FormData> page = queryService.findByPage(query, new PageRequest(0, 100));
        //只取最新的一条formData;
        if (page != null && page.getContent().size() > 0)
        {
            return page.getContent().get(0);
        }
        return null;
    }

    private void updateProcInstanceState(String procInstanceId,int state)
    {
        ProcInstantsDto procInstanceDto = procInstanceService.findByInstanceId(procInstanceId);
        procInstanceDto.setState(state);
        procInstanceDto.setStat(DataStatus.DISABLED);
        //更新流程实例
        procInstanceService.updateProcInstance(procInstanceDto);
    }

    private void sendQJYMessage(String userId, String message)
    {
        ImsUsersDto userDto = imsUserService.findByUserId(userId);
        List<String> listQJYAccount = new ArrayList<String>();
        listQJYAccount.add(userDto.getQjyAccount());
        QjyImUserDto qjyUser = new QjyImUserDto();
        qjyUser.setToIdList(listQJYAccount);
        qjyUser.setText(message);
        //亲家云推送提示信息
        er.getQjyEvent().post(qjyUser);

    }

}
