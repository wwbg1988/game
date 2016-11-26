package com.ssic.game.ims.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
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
import com.ssic.game.common.dao.ActionRoleDao;
import com.ssic.game.common.dao.ActionUserPersDao;
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
import com.ssic.game.common.dao.ProcessDao;
import com.ssic.game.common.dao.ProcessUsersDao;
import com.ssic.game.common.dao.ProjectDao;
import com.ssic.game.common.dao.TasksDao;
import com.ssic.game.common.dto.ActionsDto;
import com.ssic.game.common.dto.DeptDto;
import com.ssic.game.common.dto.DeptUsersDto;
import com.ssic.game.common.dto.DictDto;
import com.ssic.game.common.dto.FormsDto;
import com.ssic.game.common.dto.TasksDto;
import com.ssic.game.common.pojo.ActionRole;
import com.ssic.game.common.pojo.ActionUser;
import com.ssic.game.common.pojo.Actions;
import com.ssic.game.common.pojo.FieldRole;
import com.ssic.game.common.pojo.FieldUser;
import com.ssic.game.common.pojo.Fields;
import com.ssic.game.common.pojo.FiledsClone;
import com.ssic.game.common.pojo.ImsRole;
import com.ssic.game.common.pojo.ImsUsers;
import com.ssic.game.common.pojo.ProcInstance;
import com.ssic.game.common.pojo.ProcessUsers;
import com.ssic.game.common.pojo.RoleUsers;
import com.ssic.game.common.pojo.Tasks;
import com.ssic.game.common.service.DeptUserService;
import com.ssic.game.common.service.IActionService;
import com.ssic.game.common.service.IDeptService;
import com.ssic.game.ims.model.ActionRecordModel;
import com.ssic.game.ims.model.CountSignUser;
import com.ssic.game.ims.model.Field;
import com.ssic.game.ims.model.FormDataModel;
import com.ssic.game.ims.model.LoadCompletionDto;
import com.ssic.game.ims.model.LoadFormData;
import com.ssic.game.ims.model.LoadProcess;
import com.ssic.game.ims.model.NewFormDataResult;
import com.ssic.game.ims.model.NewLoadTask;
import com.ssic.game.ims.model.TaskData;
import com.ssic.game.ims.service.ILoadProcessService;
import com.ssic.ims.documents.ActionRecord;
import com.ssic.ims.documents.FormData;
import com.ssic.ims.dto.ActionRecordQuery;
import com.ssic.ims.dto.Condition;
import com.ssic.ims.dto.FormDataQuery;
import com.ssic.ims.dto.Opt;
import com.ssic.ims.service.IActionRecordService;
import com.ssic.ims.service.IFormDataOptService;
import com.ssic.ims.service.IFormDataQueryService;
import com.ssic.util.DateUtils;
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
public class LoadProcessServiceImpl implements ILoadProcessService
{
    @Autowired
    private ProcessDao processDao;
    @Autowired
    private IFormDataQueryService queryService;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private FormsDao formDao;
    @Autowired
    private FieldsDao fieldsDao;

    @Autowired
    private FieldsUserDao fieldsUserDao;

    @Autowired
    private FieldsRoleDao fieldsRoleDao;
    @Autowired
    private FieldDictDao fieldDictDao;
    @Autowired
    private FieldsCloneDao fieldsCloneDao;
    @Autowired
    private ProcInstantsDao procInstantsDao;
    @Autowired
    private ImsRoleUserDao imsRoleUserDao;
    @Autowired
    private ImsRolesDao imsRoleDao;

    @Autowired
    private DeptUserService deptUserService;

    @Autowired
    private IDeptService deptService;

    @Autowired
    private TasksDao tasksDao;

    @Autowired
    private IActionRecordService recordService;

    @Autowired
    private ProcessUsersDao processUsersDao;

    @Autowired
    private ImsUserDao imsUserDao;
    @Autowired
    private ActionUserPersDao actionUserPersDao;

    @Autowired
    private IActionService actionService;

    @Autowired
    private ActionRoleDao actionRoleDao;

    @Autowired
    private ActionUserPersDao actionUserDao;

    @Autowired
    private IFormDataOptService formDataOptService;

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.ims.service.ILoadProcessService#loadProcess(java.lang.String)   
    */
    @Override
    public Response<List<LoadProcess>> loadProcess(String userId, String projId)
    {
        List<LoadProcess> resultList = new ArrayList<LoadProcess>();
        //        List<Map<Object, Object>> mapList = processDao.getLoadProcess(userId);
        //        if (mapList != null && mapList.size() > 0)
        //        {
        //            for (Map<Object, Object> mapper : mapList)
        //            {
        //                if (projId.equals(mapper.get("projId").toString()))
        //                {
        //                    LoadProcess loadProcess = new LoadProcess();
        //                    loadProcess.setUserName(mapper.get("userAccount").toString());
        //                    loadProcess.setProcId(mapper.get("processId").toString());
        //                    loadProcess.setProcName(mapper.get("procName").toString());
        //                    loadProcess.setProjId(mapper.get("projId").toString());
        //                    String actionUrl =
        //                        "/http/api/ims/createFlow.do?userId=" + userId + "&processId="
        //                            + loadProcess.getProcId() + "&projectId=" + loadProcess.getProjId() + "";
        //                    loadProcess.setActionUrl(actionUrl);
        //                    resultList.add(loadProcess);
        //                }
        //
        //            }
        //            return new Response<List<LoadProcess>>(DataStatus.HTTP_SUCCESS, "sucess", resultList);
        //        }
        //        return new Response<List<LoadProcess>>(DataStatus.HTTP_FAILE, "can not find process list", null);
        ProcessUsers paramUser = new ProcessUsers();
        paramUser.setUserId(userId);
        List<ProcessUsers> userList = processUsersDao.findBy(paramUser);
        if (userList != null && userList.size() > 0)
        {
            for (ProcessUsers pro : userList)
            {
                LoadProcess loadProcess = new LoadProcess();
                String procId = pro.getProcId();
                ImsUsers imsUserDto = imsUserDao.findByUserId(userId);
                com.ssic.game.common.pojo.Process process = processDao.findById(procId);
                if (process != null)
                {
                    loadProcess.setProcName(process.getProcName());
                    loadProcess.setProcId(process.getId());
                    loadProcess.setProjId(process.getProjId());
                }
                if (imsUserDto != null)
                {
                    loadProcess.setUserName(imsUserDto.getName());
                }
                String actionUrl =
                    "/http/api/ims/createFlow.do?userId=" + userId + "&processId=" + loadProcess.getProcId()
                        + "&projectId=" + loadProcess.getProjId() + "";
                loadProcess.setActionUrl(actionUrl);
                resultList.add(loadProcess);
            }
            return new Response<List<LoadProcess>>(DataStatus.HTTP_SUCCESS, "sucess", resultList);
        }
        return new Response<List<LoadProcess>>(DataStatus.HTTP_FAILE, "can not find process list", null);
    }

    /**
     * 
     * findUserActionRecord：查询已办列表
     * @param userId
     * @param procInstanceId
     * @param formId
     * @return
     * @exception   
     * @author Administrator
     * @date 2015年7月22日 上午10:50:18
     */
    private List<ActionRecord> findUserActionFinish(String userId, String projId, String procId,
        String posinId, String formId)
    {
        ActionRecordQuery query = new ActionRecordQuery();
        if (!StringUtils.isEmpty(userId))
        {
            query.setUserId(userId);
        }
        query.setProcessId(procId);
        query.setProjectId(projId);
        if (!StringUtils.isEmpty(posinId))
        {
            query.setProcessInstanceId(posinId);
        }
        if (!StringUtils.isEmpty(formId))
        {
            query.setFormId(formId);
        }
        query.setActionType(ActionTypeConstants.ACTION_TTPE_PASS);
        //        query.setCountersign(CountSignerTypeConstants.IS_COUNT_SIGN);
        query.addCondition(new Condition("countersign", Opt.gte, 1));
        query.addOrder("createTime", Direction.DESC);
        Page<ActionRecord> page = recordService.findByPage(query, new PageRequest(0, 100));
        if (page != null && page.getContent().size() > 0)
        {
            return page.getContent();
        }
        else
        {
            return null;
        }

    }

    private List<ActionRecord> findActionRecordList(ActionRecordModel actionModel)
    {
        ActionRecordQuery query = new ActionRecordQuery();
        if (!StringUtils.isEmpty(actionModel.getProjId()))
        {
            query.setProjectId(actionModel.getProjId());
        }
        if (!StringUtils.isEmpty(actionModel.getProcId()))
        {
            query.setProcessId(actionModel.getProcId());
        }
        if (!StringUtils.isEmpty(actionModel.getFormId()))
        {
            query.setFormId(actionModel.getFormId());
        }
        if (!StringUtils.isEmpty(actionModel.getTaskId()))
        {
            query.setTaskId(actionModel.getTaskId());
        }
        if (!StringUtils.isEmpty(actionModel.getProcInsId()))
        {
            query.setProcessInstanceId(actionModel.getProcInsId());
        }
        if (!StringUtils.isEmpty(actionModel.getIsCountSigner()))
        {
            query.setCountersign(Integer.valueOf(actionModel.getIsCountSigner()));
        }
        query.addOrder("createTime", Direction.DESC);
        Page<ActionRecord> page = recordService.findByPage(query, new PageRequest(0, 100));
        if (page != null && page.getContent().size() > 0)
        {
            return page.getContent();
        }
        return null;
    }

    /**
     * 
     * findUserActionRecord：会签列表时判断当前用户是否已经提交
     * @param userId
     * @param procInstanceId
     * @param formId
     * @return
     * @exception   
     * @author Administrator
     * @date 2015年7月22日 上午10:50:18
     */
    private boolean findUserActionRecord(String userId, String procInstanceId, String formId)
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

    private CountSignUser findCountSignUser(String userId, String procInstanceId, String formId)
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

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.ims.service.ILoadProcessService#loadCompletion(java.lang.String, java.lang.String)   
    */
    @Override
    public Response<List<LoadCompletionDto>> loadCompletion(String userId, String projId, String procId,
        int beginRow, int size)
    {
        List<LoadCompletionDto> resultList = new ArrayList<LoadCompletionDto>();
        //        if (StringUtils.isEmpty(userId))
        //        {
        //            return new Response<List<LoadCompletionDto>>(DataStatus.HTTP_FAILE,
        //                "userId can not be null",
        //                null);
        //        }
        if (StringUtils.isEmpty(projId))
        {
            return new Response<List<LoadCompletionDto>>(DataStatus.HTTP_FAILE,
                "projId can not be null",
                null);
        }
        if (StringUtils.isEmpty(procId))
        {
            return new Response<List<LoadCompletionDto>>(DataStatus.HTTP_FAILE,
                "procId can not be null",
                null);
        }

        if (size == 0)
        {
            return new Response<List<LoadCompletionDto>>(DataStatus.HTTP_FAILE, "size can not be null", null);
        }

        //1.到procinstantce表中寻找ID
        ProcInstance paramss = new ProcInstance();
        paramss.setProjId(projId);
        paramss.setProcId(procId);
        //1.如果是全部查询 则不填写userId如果查询部门下人活着自己 则填写userId
        if (!StringUtils.isEmpty(userId))
        {
            paramss.setOwner(userId);
        }
        List<ProcInstance> procList = procInstantsDao.findAllIncludeFinish(paramss);
        if (CollectionUtils.isEmpty(procList))
        {
            return new Response<List<LoadCompletionDto>>(DataStatus.HTTP_FAILE,
                "can not find result data",
                null);
        }
        for (ProcInstance procInstance : procList)
        {
            if (!StringUtils.isEmpty(userId) && !procInstance.getOwner().equals(userId))
            {
                continue;
            }
            //非会签的已办列表
            LoadFormData loadFormData = new LoadFormData();
            loadFormData.setProcId(procInstance.getProcId());
            loadFormData.setParamInstId(procInstance.getId());
            loadFormData.setProjId(projId);
            loadFormData.setBeginRow(beginRow);
            loadFormData.setSize(size);
            resultList = addLoadCompletion(loadFormData, resultList);
            //会签已办列表
            //                resultList =
            //                    addActionRecordCompletion(procInstance.getProcId(),
            //                        procInstance.getId(),
            //                        projId,
            //                        resultList);
        }
        Collections.sort(resultList, new CompletionComparator());
        return new Response<List<LoadCompletionDto>>(DataStatus.HTTP_SUCCESS, "返回成功", resultList);
    }

    private List<LoadCompletionDto> addActionRecordCompletion(String procId, String paramInstId,
        String projId, List<LoadCompletionDto> resultList)
    {
        ActionRecordModel actionRecordModel = new ActionRecordModel();
        actionRecordModel.setProcInsId(paramInstId);
        actionRecordModel.setProjId(projId);
        actionRecordModel.setIsCountSigner(CountSignerTypeConstants.IS_COUNT_SIGN + "");
        List<ActionRecord> actionRecordList = findActionRecordList(actionRecordModel);
        if (CollectionUtils.isEmpty(resultList))
        {
            return resultList;
        }
        for (ActionRecord action : actionRecordList)
        {
            LoadCompletionDto loadCompletion = new LoadCompletionDto();
            loadCompletion.setFields(action.getValues());
            loadCompletion.setCreateDate(action.getCreateTime());
            loadCompletion.setUrl("/http/api/ims/app/loadCompTask.do?projId=" + projId + "&procId=" + procId
                + "&procInsId=" + paramInstId);
            String procInstId = action.getProcessInstanceId();
            if (!StringUtils.isEmpty(procInstId))
            {
                ProcInstance procin = procInstantsDao.findById(procInstId);
                //首先判断formstat 如果等于finished则判断procinstance内state参数，如果等于拒绝 则未通过 反之等于通过
                if (action.getActionType() == ActionTypeConstants.ACTION_TTPE_PASS)
                {
                    loadCompletion.setStat("审批通过");
                }
                else if (action.getActionType() == ActionTypeConstants.ACTION_TTPE_REFUSE)
                {
                    loadCompletion.setStat("审批拒绝");
                }
                else
                {
                    loadCompletion.setStat("未审批");

                }

                ImsUsers ownUser = imsUserDao.findByUserId(procin.getOwner());
                if (ownUser != null)
                {
                    loadCompletion.setCreateUser(ownUser.getName());
                }
                DeptUsersDto deptUserDto = new DeptUsersDto();
                deptUserDto.setUserId(procin.getOwner());
                deptUserDto.setProjId(projId);
                List<DeptUsersDto> deptUserList = deptUserService.findDeptUser(deptUserDto);
                if (!CollectionUtils.isEmpty(deptUserList))
                {
                    DeptDto deptDto = deptService.findById(deptUserList.get(0).getDeptId());
                    if (deptDto != null)
                    {
                        loadCompletion.setDeptName(deptDto.getDeptName());
                    }
                }

                loadCompletion.setApproval(action.getUserName());
                DeptUsersDto apporDept = new DeptUsersDto();
                apporDept.setUserId(action.getId());
                apporDept.setProjId(projId);
                List<DeptUsersDto> approDeptList = deptUserService.findDeptUser(apporDept);
                if (!CollectionUtils.isEmpty(approDeptList))
                {
                    DeptDto deptDtos = deptService.findById(approDeptList.get(0).getDeptId());
                    if (deptDtos != null)
                    {
                        loadCompletion.setApprovalDept(deptDtos.getDeptName());
                    }
                }

            }
            resultList.add(loadCompletion);

        }
        return resultList;

    }

    private List<LoadCompletionDto> addLoadCompletion(LoadFormData loadFormData,
        List<LoadCompletionDto> resultList)
    {
        FormDataModel formDataModel = new FormDataModel();
        if (!StringUtils.isEmpty(loadFormData.getParamInstId()))
        {
            formDataModel.setProcessInstanceId(loadFormData.getParamInstId());
        }
        formDataModel.setProjectId(loadFormData.getProjId());
        if (loadFormData.getSize() != 0)
        {
            formDataModel.setBeginRow(loadFormData.getBeginRow());
            formDataModel.setSize(loadFormData.getSize());
        }

        if (!StringUtils.isEmpty(loadFormData.getDeptId()))
        {
            formDataModel.setDeptId(loadFormData.getDeptId());
        }
        if (!StringUtils.isEmpty(loadFormData.getProcId()))
        {
            formDataModel.setProcId(loadFormData.getProcId());
        }
        formDataModel.setCountSigner(CountSignerTypeConstants.IS_NOT_COUNT_SIGN);
        Page<FormData> page = findFormBy(formDataModel);
        if (page == null || page.getContent().size() <= 0)
        {
            return resultList;
        }
        List<FormData> list = page.getContent();
        for (FormData formData : list)
        {
            String types = formData.getValues().get("actionType").toString();
            LoadCompletionDto loadCompletion = new LoadCompletionDto();
            loadCompletion.setFields(formData.getValues());
            if (formData.getCreateTime() != null)
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                loadCompletion.setCreateDateForString(format.format(formData.getCreateTime()));
                loadCompletion.setCreateDate(formData.getCreateTime());
            }

            if (!StringUtils.isEmpty(formData.getValues().get("startDate"))
                && !StringUtils.isEmpty(formData.getValues().get("endDate")))
            {

                String startTime = formData.getValues().get("startDate").toString();
                String endTime = formData.getValues().get("endDate").toString();
                if (!StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime))
                {
                    SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
                    Date startDate = null;
                    Date endDate = null;
                    try
                    {
                        startDate = sim.parse(startTime);
                        endDate = sim.parse(endTime);
                        int dayDiff = DateUtils.dayDiff(endDate, startDate);
                        if (dayDiff == 0)
                        {
                            dayDiff = 1;
                        }
                        else if (dayDiff < 0)
                        {
                            dayDiff = 0;
                        }
                        loadCompletion.setDateDiff(dayDiff);
                    }
                    catch (Exception e)
                    {
                        //  对异常进行简要描述

                    }

                }

            }

            loadCompletion.setUrl("/http/api/ims/app/loadCompTask.do?projId=" + loadFormData.getProjId()
                + "&procId=" + loadFormData.getProcId() + "&procInsId=" + loadFormData.getParamInstId());
            //放入项目id
            loadCompletion.setProjId(loadFormData.getProjId());
            //放入流程id
            loadCompletion.setProcId(loadFormData.getProcId());
            //放入流程实例id
            loadCompletion.setProcInsId(loadFormData.getParamInstId());
            String procInstId = formData.getProcessInstanceId();
            if (!StringUtils.isEmpty(procInstId))
            {
                ProcInstance procin = procInstantsDao.findById(procInstId);
                if (procin == null)
                {
                    resultList.add(loadCompletion);
                    return resultList;
                }
                //首先判断formstat 如果等于finished则判断procinstance内state参数，如果等于拒绝 则未通过 反之等于通过
                /*          if (formData.getFormStat() == FormTypeConstants.FINISHED)
                          {
                              if (procin.getState() == 4)
                              {
                                  loadCompletion.setStat("审批拒绝");
                              }
                              else
                              {
                                  loadCompletion.setStat("审批通过");
                              }
                          }
                          else
                          {
                              loadCompletion.setStat("未审批");

                          }
                          
                          
                */
                //根据formData的actionType值来判断通过拒绝(5:通过;6:拒绝7:未审核)
                if (!StringUtils.isEmpty(types))
                {
                    if (types.equals("7"))
                    {
                        loadCompletion.setStat("未审批");
                    }
                    else if (types.equals("5"))
                    {
                        loadCompletion.setStat("审批通过");
                    }
                    else if (types.equals("6"))
                    {
                        loadCompletion.setStat("审批拒绝");
                    }
                }
                //这个取值有问题，逻辑不清楚
                ImsUsers ownUser = imsUserDao.findByUserId(procin.getOwner());

                if (!StringUtils.isEmpty(formData.getUserName()))
                {
                    loadCompletion.setCreateUser(formData.getUserName());
                }
                /*  if (ownUser != null)
                  {
                      loadCompletion.setCreateUser(formData.getUserName());
                  }*/
                DeptUsersDto deptUserDto = new DeptUsersDto();

                deptUserDto.setUserId(procin.getOwner());
                deptUserDto.setProjId(loadFormData.getProjId());
                List<DeptUsersDto> deptUserList = deptUserService.findDeptUser(deptUserDto);
                if (!CollectionUtils.isEmpty(deptUserList))
                {
                    if (!StringUtils.isEmpty(deptUserList.get(0).getDeptName()))
                    {
                        loadCompletion.setDeptName(deptUserList.get(0).getDeptName());
                    }
                }

                ActionRecordModel actionModel = new ActionRecordModel();

                actionModel.setProcInsId(procInstId);

                actionModel.setProjId(loadFormData.getProjId());

                actionModel.setTaskId(formData.getTaskId());

                List<ActionRecord> recordPage = findActionRecordList(actionModel);
                if (recordPage != null
                    && recordPage.size() > 0
                    && (recordPage.get(0).getActionType() == ActionTypeConstants.ACTION_TTPE_PASS || recordPage.get(0)
                        .getActionType() == ActionTypeConstants.ACTION_TTPE_REFUSE))
                {
                    loadCompletion.setApproval(recordPage.get(0).getUserName());
                    ImsUsers user = imsUserDao.findByUserId(recordPage.get(0).getUserId());
                    if (user != null && !StringUtils.isEmpty(user.getUserImage()))
                    {
                        loadCompletion.setUserImg(user.getUserImage());
                    }
                    DeptUsersDto apporDept = new DeptUsersDto();
                    apporDept.setUserId(recordPage.get(0).getUserId());
                    apporDept.setProjId(loadFormData.getProjId());
                    List<DeptUsersDto> approDeptList = deptUserService.findDeptUser(apporDept);
                    if (!CollectionUtils.isEmpty(approDeptList))
                    {
                        if (!StringUtils.isEmpty(approDeptList.get(0).getDeptName()))
                        {
                            loadCompletion.setApprovalDept(approDeptList.get(0).getDeptName());
                        }

                    }
                }

            }
            resultList.add(loadCompletion);

        }
        return resultList;

    }

    private Page<FormData> findFormBy(FormDataModel formDataModel)
    {
        FormDataQuery query = new FormDataQuery();
        if (!StringUtils.isEmpty(formDataModel.getUserId()))
        {
            query.setUserId(formDataModel.getUserId());
        }
        if (!StringUtils.isEmpty(formDataModel.getProjectId()))
        {
            query.setProjectId(formDataModel.getProjectId());
        }
        if (formDataModel.getFormStat() != -1)
        {
            query.setFormStat(formDataModel.getFormStat());
        }
        if (!StringUtils.isEmpty(formDataModel.getDeptCode()))
        {
            query.setDeptCode(formDataModel.getDeptCode());
        }
        if (!StringUtils.isEmpty(formDataModel.getProcessInstanceId()))
        {
            query.setProcessInstanceId(formDataModel.getProcessInstanceId());
        }
        if (!StringUtils.isEmpty(formDataModel.getProcId()))
        {
            query.setProcessId(formDataModel.getProcId());
        }
        if (!StringUtils.isEmpty(formDataModel.getFormId()))
        {
            query.setFormId(formDataModel.getFormId());
        }
        if (formDataModel.getCountSigner() != -1)
        {
            query.setCountersign(formDataModel.getCountSigner());
        }

        if (!StringUtils.isEmpty(formDataModel.getDeptId()))
        {
            List<Condition> tempList = new ArrayList<Condition>();
            tempList.add(new Condition("deptId", Opt.eq, formDataModel.getDeptId()));
            query.setFields(tempList);
        }
        //        query.addOrder("processInstanceId", Direction.DESC);
        query.addOrder("createTime", Direction.DESC);
        //        int endRow = formDataModel.getBeginRow() + formDataModel.getSize();
        Page<FormData> page = null;
        if (formDataModel.getSize() == 0)
        {
            page = queryService.findByPage(query, new PageRequest(0, 5000));
        }
        else
        {
            page =
                queryService.findByPage(query,
                    new PageRequest(formDataModel.getBeginRow(), formDataModel.getSize()));
        }

        return page;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.ims.service.ILoadProcessService#loadTaskData(java.lang.String, java.lang.String, java.lang.String)   
    */
    @Override
    public Response<TaskData> loadTaskData(String userId, String projId, String procId, String formId,
        String proinsId)
    {

        if (StringUtils.isEmpty(userId))
        {
            return new Response<TaskData>(DataStatus.HTTP_FAILE, "userId can not be null", null);
        }
        if (StringUtils.isEmpty(projId))
        {
            return new Response<TaskData>(DataStatus.HTTP_FAILE, "projId can not be null", null);
        }
        if (StringUtils.isEmpty(userId))
        {
            return new Response<TaskData>(DataStatus.HTTP_FAILE, "userId can not be null", null);
        }
        if (StringUtils.isEmpty(formId))
        {
            return new Response<TaskData>(DataStatus.HTTP_FAILE, "formId can not be null", null);
        }

        TaskData taskData = new TaskData();
        taskData.setFormId(formId);
        //寻找user对应的角色列表
        List<ImsRole> roleList = findRoleInfo(userId);

        FormDataModel formDataModel = new FormDataModel();
        formDataModel.setProcessInstanceId(proinsId);
        formDataModel.setUserId(userId);
        formDataModel.setProjectId(projId);
        formDataModel.setFormId(formId);
        formDataModel.setFormStat(FormTypeConstants.FINISHED);
        formDataModel.setBeginRow(0);
        formDataModel.setSize(500);
        //        Page<FormData> page = findFormBy(proinsId, null, userId, projId, formId, FormTypeConstants.FINISHED, 0, 10);
        Page<FormData> page = findFormBy(formDataModel);
        Map<String, Object> fieldMap = null;
        String userName = null;
        if (page != null && page.getContent() != null && page.getContent().size() > 0)
        {
            fieldMap = page.getContent().get(0).getValues();
            //            lastUpdateTime = page.getContent().get(0).getLastUpdateTime();
            userName = page.getContent().get(0).getUserName();
        }
        else
        {
            List<ActionRecord> actionRecordList =
                findUserActionFinish(userId, projId, procId, proinsId, formId);
            if (actionRecordList != null && actionRecordList.size() > 0)
            {
                ActionRecord actionRecords = actionRecordList.get(0);
                fieldMap = actionRecords.getValues();
                //                lastUpdateTime = actionRecords.getLastUpdateTime();
                userName = actionRecords.getUserName();
            }
        }
        //        taskData.setLastUpdateTime(lastUpdateTime);
        taskData.setUserName(userName);
        //会签列表
        Tasks paramTask = new Tasks();
        paramTask.setProcId(procId);
        paramTask.setProjId(projId);
        paramTask.setFormId(formId);

        List<Tasks> taskList = tasksDao.findAllBy(paramTask);
        boolean isCountFlag = false;
        if (taskList != null && taskList.size() > 0)
        {

            if (taskList.get(0).getCountersign() == 1)
            {
                isCountFlag = true;
                List<CountSignUser> countList = new ArrayList<CountSignUser>();
                ActionsDto actionsDto = new ActionsDto();
                actionsDto.setProjId(projId);
                actionsDto.setProcId(procId);
                actionsDto.setTaskId(taskList.get(0).getId());
                actionsDto.setType(ActionTypeConstants.ACTION_TTPE_PASS);
                List<Actions> actionList = actionService.findBy(actionsDto);
                if (actionList != null && actionList.size() > 0)
                {
                    Actions tempAction = actionList.get(0);
                    String actionId = tempAction.getId();
                    ActionUser param = new ActionUser();
                    param.setActionId(actionId);
                    List<ActionUser> actionUserList = actionUserPersDao.findUserAll(param);
                    if (actionUserList != null && actionUserList.size() > 0)
                    {
                        for (ActionUser acions : actionUserList)
                        {
                            CountSignUser tempCountUser =
                                findCountSignUser(acions.getUserId(), proinsId, formId);
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

        taskData.setFields(findFields(roleList,
            userId,
            projId,
            procId,
            formId,
            fieldMap,
            proinsId,
            isCountFlag));

        return new Response<TaskData>(DataStatus.HTTP_SUCCESS, "", taskData);
    }

    //    @Override
    //    public Response<List<LoadCompletionDto>> loadSubDeptFormData(String userId, String projId)
    //    {
    //
    //        List<LoadCompletionDto> resultList = new ArrayList<LoadCompletionDto>();
    //        if (StringUtils.isEmpty(userId))
    //        {
    //            return new Response<List<LoadCompletionDto>>(DataStatus.HTTP_FAILE,
    //                HttpDataResponse.User_IS_NULL,
    //                null);
    //        }
    //        if (StringUtils.isEmpty(projId))
    //        {
    //            return new Response<List<LoadCompletionDto>>(DataStatus.HTTP_FAILE,
    //                HttpDataResponse.PROJECT_IS_NULL,
    //                null);
    //        }
    //
    //        DeptUsersDto deptUserDto = new DeptUsersDto();
    //        deptUserDto.setUserId(userId);
    //        deptUserDto.setProjId(projId);
    //        List<DeptUsersDto> deptUsers = deptUserService.findAllBy(deptUserDto);
    //        if (!CollectionUtils.isEmpty(deptUsers))
    //        {
    //            DeptUsersDto dto = deptUsers.get(0);
    //            DeptDto deptDto = deptService.findById(dto.getDeptId());
    //            String deptCode = deptDto.getDeptCode();
    //            //查询条件里的部门编码正则匹配;
    //            String patternDeptCode = "^" + deptCode + ".*";
    //            FormDataModel formDataModel = new FormDataModel();
    //            formDataModel.setDeptCode(patternDeptCode);
    //            formDataModel.setProjectId(projId);
    //            formDataModel.setFormStat(FormTypeConstants.FINISHED);
    //            formDataModel.setBeginRow(0);
    //            formDataModel.setSize(500);
    //            Page<FormData> page = findFormBy(formDataModel);
    //
    //            if (page != null && page.getContent().size() > 0)
    //            {
    //
    //                Map<String, LoadCompletionDto> map = new HashMap<String, LoadCompletionDto>();
    //                for (FormData formData : page.getContent())
    //                {
    //                    String procInstId = formData.getProcessInstanceId();
    //                    //Map值中不存在 新建一个map 类
    //
    //                    if (map.get(procInstId) == null)
    //                    {
    //                        List<FormDataEndDto> formDataEndlist = new ArrayList<FormDataEndDto>();
    //                        LoadCompletionDto loadCompletionDto = new LoadCompletionDto();
    //                        ProcInstance param = new ProcInstance();
    //                        param.setId(procInstId);
    //                        List<ProcInstance> procList = procInstantsDao.findAllBy(param);
    //                        if (procList != null && procList.size() > 0)
    //                        {
    //                            loadCompletionDto.setProcessInsName(procList.get(0).getProcName());
    //                            loadCompletionDto.setProcInstanceId(procInstId);
    //
    //                        }
    //                        FormDataEndDto formDatas = new FormDataEndDto();
    //                        TasksDto tasksDto = tasksDao.findById(formData.getTaskId());
    //                        if (tasksDto != null)
    //                        {
    //                            formDatas.setTaskName(tasksDto.getName());
    //                        }
    //                        String actionUri =
    //                            "/http/api/ims/app/loadFormData.do?projId=" + formData.getProjectId()
    //                                + "&procId=" + formData.getProcessId() + "&userId=" + formData.getUserId()
    //                                + "&formId=" + formData.getFormId() + "&proinsId="
    //                                + formData.getProcessInstanceId();
    //                        formDatas.setActionUrl(actionUri);
    //                        formDataEndlist.add(formDatas);
    //                        loadCompletionDto.setFormDataEndDto(formDataEndlist);
    //                        map.put(procInstId, loadCompletionDto);
    //
    //                    }
    //
    //                    //若不是 则加入 map
    //                    else
    //                    {
    //                        LoadCompletionDto mapLoad = map.get(procInstId);
    //                        FormDataEndDto formDatas = new FormDataEndDto();
    //                        TasksDto tasksDto = tasksDao.findById(formData.getTaskId());
    //                        if (tasksDto != null)
    //                        {
    //                            formDatas.setTaskName(tasksDto.getName());
    //                        }
    //                        String actionUri =
    //                            "/http/api/ims/app/loadFormData.do?projId=" + formData.getProjectId()
    //                                + "&procId=" + formData.getProcessId() + "&userId=" + formData.getUserId()
    //                                + "&formId=" + formData.getFormId() + "&proinsId="
    //                                + formData.getProcessInstanceId();
    //                        formDatas.setActionUrl(actionUri);
    //                        List<FormDataEndDto> mapFormDataList = mapLoad.getFormDataEndDto();
    //                        mapFormDataList.add(formDatas);
    //                        mapLoad.setFormDataEndDto(mapFormDataList);
    //                        map.remove(procInstId);
    //                        map.put(procInstId, mapLoad);
    //                    }
    //
    //                }
    //
    //                for (String key : map.keySet())
    //                {
    //                    LoadCompletionDto loadCompletionDto = map.get(key);
    //                    resultList.add(loadCompletionDto);
    //                }
    //            }
    //        }
    //
    //        return new Response<List<LoadCompletionDto>>(DataStatus.HTTP_SUCCESS, "处理成功:返回数据:"
    //            + resultList.size() + "条", resultList);
    //    }

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

    //寻找字段
    private List<Field> findFields(List<ImsRole> roleList, String userId, String projId, String procId,
        String formId, Map<String, Object> fieldMap, String posinId, boolean isCountFlag)
    {
        FormsDto param = new FormsDto();
        param.setId(formId);
        List<FormsDto> formList = formDao.findAll(param, null);
        if (CollectionUtils.isEmpty(formList))
        {
            return null;
        }
        FormsDto temp = formList.get(0);
        Fields fields = new Fields();
        fields.setFormId(temp.getId());
        fields.setProjId(projId);
        List<Fields> fieldsList = fieldsDao.findAllBy(fields);
        if (fieldsList != null && fieldsList.size() > 0)
        {

            List<Field> resultList = new ArrayList<Field>();
            for (Fields tempFields : fieldsList)
            {
                RequestResult fiPer = validateFields(roleList, userId, projId, tempFields.getId());

                //1.user 2.role
                String fieldRoleType = "";

                if (!fiPer.success)
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

                //                    //加入fieldValue
                //                    String fieldCloneId = tempFields.getFieldsCloneId();
                //                    if (!StringUtils.isEmpty(fieldCloneId))
                //                    {
                //                        FiledsClone fieldClone = fieldsCloneDao.selectByPrimaryKey(fieldCloneId);
                //                        if (fieldClone != null)
                //                        {
                //                            //查询mango db
                //                            String cloneForm = fieldClone.getFormId();
                //                            FormsDto mangoForm = new FormsDto();
                //                            mangoForm.setId(cloneForm);
                //                            List<FormsDto> mangoFormList = formDao.findAll(mangoForm, null);
                //                            if (mangoFormList != null && mangoFormList.size() > 0)
                //                            {
                //                                String procsId = mangoFormList.get(0).getProcId();
                //                                ProcInstance proc = new ProcInstance();
                //                                proc.setProcId(procsId);
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
                //
                //                            }
                //
                //                        }
                //                    }

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
                        query.setProcessInstanceId(posinId);
                        query.setFormId(cloneForm);
                        Page<FormData> page = queryService.findByPage(query, new PageRequest(0, 20));
                        if (page.getContent() != null && page.getContent().size() > 0)
                        {

                            Map<String, Object> cloneMap = page.getContent().get(0).getValues();
                            for (String keyMap : cloneMap.keySet())
                            {
                                if (keyMap.equals(fieldClone.getParamName()))
                                {
                                    field.setFieldValue(cloneMap.get(keyMap).toString());
                                    break;
                                }
                            }

                        }

                    }
                }
                field.setPermission(HttpConstants.FIELD_READ_PER);

                //如果是会签的话 那么字段里面的值需要重新
                if (isCountFlag == true)
                {

                    List<ActionRecord> recordList =
                        findActionRecordSave(userId,
                            projId,
                            posinId,
                            formId,
                            ActionTypeConstants.ACTION_TTPE_PASS);
                    if (recordList != null && recordList.size() > 0
                        && recordList.get(0).getValues().get(tempFields.getParamName()) != null)
                    {
                        String fieldValue =
                            recordList.get(0).getValues().get(tempFields.getParamName()).toString();
                        field.setFieldValue(fieldValue);
                    }

                }
                else
                {

                    if (fieldMap.get(tempFields.getParamName()) != null)
                    {
                        field.setFieldValue(fieldMap.get(tempFields.getParamName()).toString());
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

        return null;
    }

    /**
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
    private List<ActionRecord> findActionRecordSave(String userId, String projId, String posinId,
        String formId, int actionType)
    {
        ActionRecordQuery query = new ActionRecordQuery();
        query.setUserId(userId);
        //        query.setProcessId(procId);
        query.setProjectId(projId);
        if (!StringUtils.isEmpty(posinId))
        {
            query.setProcessInstanceId(posinId);
        }
        if (!StringUtils.isEmpty(formId))
        {
            query.setFormId(formId);
        }
        query.setActionType(actionType);
        //        query.setCountersign(CountSignerTypeConstants.IS_COUNT_SIGN);
        query.addOrder("createTime", Direction.DESC);
        Page<ActionRecord> page = recordService.findByPage(query, new PageRequest(0, 100));
        if (page != null && page.getContent().size() > 0)
        {
            return page.getContent();
        }
        else
        {
            return null;
        }

    }

    private RequestResult validateFields(List<ImsRole> roleList, String userId, String projId, String fieldId)
    {

        FieldUser fieldsUser = new FieldUser();
        fieldsUser.setUserId(userId);
        fieldsUser.setProjId(projId);
        fieldsUser.setFieldId(fieldId);
        List<FieldUser> userList = fieldsUserDao.findUserAllBy(fieldsUser);
        if (userList != null && userList.size() > 0)
        {
            return new RequestResult(true, "1");
        }
        if (roleList != null && roleList.size() > 0)
        {
            for (int i = 0; i < roleList.size(); i++)
            {
                ImsRole role = roleList.get(i);
                if (!StringUtils.isEmpty(role.getId()))
                {
                    FieldRole frole = new FieldRole();
                    //                    frole.setProcId(request.getProcInstanceId());
                    frole.setProjId(projId);
                    frole.setFieldId(fieldId);
                    frole.setRoleId(role.getId());
                    List<FieldRole> tempList = fieldsRoleDao.findAllBy(frole);
                    if (tempList != null && tempList.size() > 0)
                    {
                        return new RequestResult(true, "2");
                    }
                }

            }
        }

        return new RequestResult(false, "3");

    }

    private List<ActionRecord> findUserActionRecordList(String deptCode, String projId, Integer actionType)
    {

        ActionRecordQuery query = new ActionRecordQuery();
        if (!StringUtils.isEmpty(projId))
        {
            query.setProjectId(projId);
        }
        if (!StringUtils.isEmpty(deptCode))
        {
            query.setDeptCode(deptCode);
        }
        if (!StringUtils.isEmpty(actionType))
        {
            query.setActionType(actionType);
        }
        query.addOrder("createTime", Direction.DESC);
        Page<ActionRecord> page = recordService.findByPage(query, new PageRequest(0, 50));

        List<ActionRecord> records = page.getContent();
        if (!CollectionUtils.isEmpty(records))
        {
            return records;

        }

        return null;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.ims.service.ILoadProcessService#loadNewAgency(java.lang.String, java.lang.String, java.lang.String)   
    * 新版待办列表
    */
    @Override
    public Response<List<NewFormDataResult>> loadNewAgency(String userId, String projId, String procId,
        String procInstId)
    {

        List<NewFormDataResult> resultList = new ArrayList<NewFormDataResult>();
        if (StringUtils.isEmpty(userId))
        {
            return new Response<List<NewFormDataResult>>(DataStatus.HTTP_FAILE,
                "userId can not be null",
                null);
        }
        if (StringUtils.isEmpty(projId))
        {
            return new Response<List<NewFormDataResult>>(DataStatus.HTTP_FAILE,
                "projId can not be null",
                null);
        }
        if (StringUtils.isEmpty(procId))
        {
            return new Response<List<NewFormDataResult>>(DataStatus.HTTP_FAILE,
                "procId can not be null",
                null);
        }
        DeptUsersDto deptUserDto = new DeptUsersDto();
        deptUserDto.setUserId(userId);
        deptUserDto.setProjId(projId);
        List<DeptUsersDto> listDept = deptUserService.findAllBy(deptUserDto);
        if (!CollectionUtils.isEmpty(listDept))
        {
            DeptUsersDto deptUserDt = listDept.get(0);
            if (!deptUserDt.getIsAdmin().equals("1"))
            {
                return new Response<List<NewFormDataResult>>(DataStatus.HTTP_FAILE, "您没有权限审批", null);
            }

        }
        List<Map<Object, Object>> mapList = processDao.getLoadAgencyTasks(userId, projId, procId, procInstId);
        if (CollectionUtils.isEmpty(mapList))
        {
            return null;
        }

        for (Map<Object, Object> map : mapList)
        {
            NewFormDataResult param = new NewFormDataResult();
            //加载字段
            Map<String, Object> fieldsMap = findFields(map);
            if (!CollectionUtils.isEmpty(fieldsMap))
            {
                //把当前用户所属的部门跟mengo数据库的部门id去对比，如果不相同，则返回;
                if (!StringUtils.isEmpty(fieldsMap.get("deptId")) && (!CollectionUtils.isEmpty(listDept)))
                {
                    String mengoDeptId = fieldsMap.get("deptId").toString();
                    String dept_Id = listDept.get(0).getDeptId();
                    if (!mengoDeptId.equals(dept_Id))
                    {
                        continue;
                    }
                }
                if (!StringUtils.isEmpty(fieldsMap.get("markCreateTime")))
                {
                    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                    String cTime = sdf2.format((Date) fieldsMap.get("markCreateTime"));
                    param.setCreateDate((Date) fieldsMap.get("markCreateTime"));
                    param.setCreateDateForString(cTime);
                    Date createDates = (Date) fieldsMap.get("markCreateTime");
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    param.setCreateDateForString(format.format(createDates));
                    fieldsMap.remove("markCreateTime");
                }
                if (!StringUtils.isEmpty(fieldsMap.get("markFormId")))
                {
                    param.setFormId(fieldsMap.get("markFormId").toString());
                    fieldsMap.remove("markFormId");
                }
                if (!StringUtils.isEmpty(fieldsMap.get("markName")))
                {
                    param.setUserName(fieldsMap.get("markName").toString());
                    fieldsMap.remove("markName");
                }
                if (!StringUtils.isEmpty(fieldsMap.get("dateDiff")))
                {
                    param.setDateDiff(Integer.parseInt(fieldsMap.get("dateDiff").toString()));
                    fieldsMap.remove("dateDiff");
                }
                ImsUsers user = imsUserDao.findByUserId(userId);
                if (user != null && !StringUtils.isEmpty(user.getUserImage()))
                {
                    param.setUserImg(user.getUserImage());
                }
                param.setUserId(userId);
                param.setProjectId(projId);
                param.setProcInstanceId(map.get("id").toString());
                param.setFields(fieldsMap);
                param.setActions(findAction(map, userId));
                resultList.add(param);
            }

        }
        Collections.sort(resultList, new CompletionComparator());
        return new Response<List<NewFormDataResult>>(DataStatus.HTTP_SUCCESS, "", resultList);
    }

    //加载动作
    private List<Actions> findAction(Map<Object, Object> map, String userId)
    {
        List<Actions> result = new ArrayList<Actions>();
        if (StringUtils.isEmpty(map.get("proj_id")) || StringUtils.isEmpty(map.get("proc_id"))
            || StringUtils.isEmpty(map.get("now_task_id")))
        {
            return result;
        }
        ActionsDto actionsDto = new ActionsDto();
        actionsDto.setProjId(map.get("proj_id").toString());
        actionsDto.setProcId(map.get("proc_id").toString());
        actionsDto.setTaskId(map.get("now_task_id").toString());
        List<Actions> list = actionService.findBy(actionsDto);
        if (CollectionUtils.isEmpty(list))
        {
            return result;
        }
        for (Actions actions : list)
        {
            Actions action = new Actions();
            //判断用户所在的角色组内
            List<ImsRole> roleList = findRoleInfo(userId);
            //判断ACTION权限
            boolean actionPers = isValiAction(roleList, userId, actions.getId(), map.get("id").toString());
            if (actionPers == true)
            {
                action.setActionUrl(actions.getActionUrl());
                action.setId(actions.getId());
                action.setName(actions.getName());
                action.setType(actions.getType());
                result.add(action);
            }
        }
        return result;
    }

    //加载字段
    private Map<String, Object> findFields(Map<Object, Object> map)
    {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (StringUtils.isEmpty(map.get("id")) || StringUtils.isEmpty(map.get("now_task_id")))
        {
            return resultMap;
        }
        //        String taskId = map.get("now_task_id").toString();
        String procInstId = map.get("id").toString();

        FormDataQuery query = new FormDataQuery();
        //        query.setTaskId(taskId);
        query.setProcessInstanceId(procInstId);

        Page<FormData> page = queryService.findByPage(query, new PageRequest(0, 20));
        if (page != null && page.getContent().size() > 0)
        {
            List<FormData> formDataList = page.getContent();
            if (CollectionUtils.isEmpty(formDataList))
            {
                return resultMap;
            }
            resultMap = formDataList.get(0).getValues();
            resultMap.put("markCreateTime", formDataList.get(0).getCreateTime());
            //放入的formId应该为流程实例表的nowTaskId对应的formId
            ProcInstance instance = procInstantsDao.findById(procInstId);
            resultMap.put("markFormId", formDataList.get(0).getFormId());
            resultMap.put("markName", formDataList.get(0).getUserName());
            if (StringUtils.isEmpty(resultMap.get("startDate"))
                || StringUtils.isEmpty(resultMap.get("endDate")))
            {
                return resultMap;
            }
            String startTime = resultMap.get("startDate").toString();
            String endTime = resultMap.get("endDate").toString();
            if (!StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime))
            {
                SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
                Date startDate = null;
                Date endDate = null;
                try
                {
                    startDate = sim.parse(startTime);
                    endDate = sim.parse(endTime);
                    int dayDiff = DateUtils.dayDiff(endDate, startDate);
                    if (dayDiff == 0)
                    {
                        dayDiff = 1;
                    }
                    else if (dayDiff < 0)
                    {
                        dayDiff = 0;
                    }
                    resultMap.put("dateDiff", dayDiff);
                }
                catch (Exception e)
                {
                    //  对异常进行简要描述

                }

            }
        }
        return resultMap;
    }

    //判断Action 权限
    private boolean isValiAction(List<ImsRole> roleList, String userId, String actionId, String procInstId)
    {
        boolean flag = false;
        ActionUser actionUser = new ActionUser();
        actionUser.setActionId(actionId);
        //        actionUser.setUserId(userId);
        List<ActionUser> userList = actionUserDao.findUserAll(actionUser);
        if (!CollectionUtils.isEmpty(userList))
        {
            for (ActionUser actionUsers : userList)
            {
                if (isUserActions(actionUsers, procInstId, userId))
                {
                    flag = true;
                    return flag;
                }
            }
        }

        if (roleList != null && roleList.size() > 0)
        {
            for (int i = 0; i < roleList.size(); i++)
            {
                ImsRole role = roleList.get(i);
                if (!StringUtils.isEmpty(role.getId()))
                {
                    ActionRole actionRole = new ActionRole();
                    actionRole.setActionId(actionId);
                    //                  actionRole.setRoleId(role.getId());
                    List<ActionRole> tempList = actionRoleDao.findRoleAll(actionRole);
                    if (!CollectionUtils.isEmpty(tempList))
                    {
                        for (ActionRole actionRoles : tempList)
                        {
                            if (isRoleActions(actionRoles, procInstId, userId, role.getId()))
                            {
                                flag = true;
                                return flag;
                            }
                        }
                    }
                }

            }
        }

        return flag;

    }

    //判断用户action权限
    private boolean isUserActions(ActionUser actionUsers, String procInstId, String userId)
    {
        if ("owner".equals(actionUsers.getUserId()))
        {
            ProcInstance tempProc = procInstantsDao.findById(procInstId);
            if (tempProc != null && userId.equals(tempProc.getOwner()))
            {
                return true;
            }
        }
        else if (userId.equals(actionUsers.getUserId()))
        {
            return true;
        }

        return false;
    }

    //破案段角色action权限
    private boolean isRoleActions(ActionRole actionRole, String procInstId, String userId, String roleId)
    {
        if ("owner".equals(actionRole.getRoleId()))
        {
            ProcInstance tempProc = procInstantsDao.findById(procInstId);
            if (tempProc != null && userId.equals(tempProc.getOwner()))
            {
                return true;
            }
        }
        else if (roleId.equals(actionRole.getRoleId()))
        {
            return true;
        }

        return false;
    }

    /** 
    * (non-Javadoc)   
     * @throws ParseException 
    * @see com.ssic.game.ims.service.ILoadProcessService#loadNewTaskData()   
    */
    @Override
    public Response<List<NewLoadTask>> loadNewTaskData(ActionRecordModel actionModel)
    {
        List<NewLoadTask> resultList = new ArrayList<NewLoadTask>();
        String projId = actionModel.getProjId();
        String procId = actionModel.getProcId();
        Tasks tasks = new Tasks();
        tasks.setProjId(projId);
        tasks.setProcId(procId);
        List<Tasks> list = tasksDao.findAllBy(tasks);
        if (list != null && list.size() > 0)
        {
            for (Tasks ta : list)
            {
                NewLoadTask newLoadTask = new NewLoadTask();
                newLoadTask.setTaskName(ta.getName());
                if (ta.getType() == 1)
                {
                    newLoadTask.setIsApproval(1);
                }
                else
                {
                    newLoadTask.setIsApproval(2);
                }
                //到actionRecord表中去寻找。若找寻到则返回信息
                ActionRecordModel param = new ActionRecordModel();
                param.setTaskId(ta.getId());
                param.setProcInsId(actionModel.getProcInsId());
                List<ActionRecord> actionRecord = findActionRecordList(param);
                if (actionRecord != null && actionRecord.size() > 0)
                {

                    //动作类型：7未审批 5已同意 6未通过
                    String actionType = actionRecord.get(0).getValues().get("actionType").toString();
                    if (actionType.equals("5"))
                    {
                        actionType = "已同意";
                    }
                    else if (actionType.equals("6"))
                    {
                        actionType = "未通过";
                    }
                    else if (actionType.equals("7"))
                    {
                        actionType = "未审批";
                    }

                    TasksDto taskDto = tasksDao.findById(actionRecord.get(0).getTaskId());
                    String displayText = "";
                    //提交请假单，出差单，报销单
                    if (taskDto.getName().contains("提交"))
                    {

                        //请假
                        if (taskDto.getName().contains("请假"))
                        {
                            String startDate = actionRecord.get(0).getValues().get("startDate").toString();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                            SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd HH:mm");
                            String endDate = actionRecord.get(0).getValues().get("endDate").toString();
                            try
                            {
                                startDate = sdf2.format(sdf.parse(startDate));
                                endDate = sdf2.format(sdf.parse(endDate));
                            }
                            catch (ParseException e)
                            {
                                //  对异常进行简要描述

                            }

                            //请假理由
                            String qingJiaReason =
                                actionRecord.get(0).getValues().get("qingJiaReason").toString();
                            //请假类型
                            String qingJiaType =
                                actionRecord.get(0).getValues().get("qingJiaType").toString();
                            displayText += "申请请假:" + startDate + "至" + endDate;
                            displayText += "\n";
                            displayText += qingJiaType + "理由:" + qingJiaReason;
                        }
                        //出差
                        if (taskDto.getName().contains("出差"))
                        {
                            String startDate = actionRecord.get(0).getValues().get("startDate").toString();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                            SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd HH:mm");
                            String endDate = actionRecord.get(0).getValues().get("endDate").toString();
                            try
                            {
                                startDate = sdf2.format(sdf.parse(startDate));
                                endDate = sdf2.format(sdf.parse(endDate));
                            }
                            catch (ParseException e)
                            {
                                //  对异常进行简要描述

                            }

                            //出差理由
                            String reason = actionRecord.get(0).getValues().get("reason").toString();
                            displayText += "申请出差:" + startDate + "至" + endDate;
                            displayText += "\n";
                            displayText += "出差理由:" + reason;
                        }
                        //报销
                        if (taskDto.getName().contains("报销"))
                        {
                            //报销理由
                            String reason = actionRecord.get(0).getValues().get("reason").toString();
                            //报销费用
                            String fee = actionRecord.get(0).getValues().get("fee").toString();
                            displayText += "报销费用:" + fee;
                            displayText += "\n";
                            displayText += "报销理由:" + reason;
                        }
                        newLoadTask.setApproveName(displayText);
                    }
                    else if (taskDto.getName().contains("审批"))
                    {
                        newLoadTask.setApproveName(actionRecord.get(0).getUserName() + " " + actionType);
                    }

                    newLoadTask.setCreateTime(actionRecord.get(0).getCreateTime());
                    if (actionRecord.get(0).getActionType() == ActionTypeConstants.ACTION_TTPE_PASS)
                    {
                        newLoadTask.setStat("审核通过");
                    }
                    else if (actionRecord.get(0).getActionType() == ActionTypeConstants.ACTION_TTPE_REFUSE)
                    {
                        newLoadTask.setStat("审核未通过");
                    }
                    else
                    {
                        newLoadTask.setStat("未审核");
                    }
                    resultList.add(newLoadTask);
                }
                /*               else
                               {
                                   newLoadTask.setStat("未审核");
                                   ActionsDto actionsDto = new ActionsDto();
                                   actionsDto.setTaskId(ta.getId());
                                   List<Actions> actionList = actionService.findBy(actionsDto);
                                   if (!CollectionUtils.isEmpty(actionList))
                                   {
                                       for (Actions actions : actionList)
                                       {
                                           //                                if (actions.getType() == ActionTypeConstants.ACTION_TTPE_PASS
                                           //                                    || actions.getType() == ActionTypeConstants.ACTION_TTPE_REFUSE)
                                           if (actions.getType() == ActionTypeConstants.ACTION_TTPE_PASS)
                                           {
                                               ActionUser userParam = new ActionUser();
                                               userParam.setActionId(actions.getId());
                                               List<ActionUser> userList = actionUserPersDao.findUserAll(userParam);
                                               if (!CollectionUtils.isEmpty(userList))
                                               {
                                                   ImsUsers imsUsers =
                                                       imsUserDao.findByUserId(userList.get(0).getUserId());
                                                   if (imsUsers != null)
                                                   {
                                                       newLoadTask.setApproveName(imsUsers.getName());
                                                       break;
                                                   }

                                               }
                                               else
                                               {
                                                   ActionRole params = new ActionRole();
                                                   params.setActionId(actions.getId());
                                                   List<ActionRole> roleList = actionRoleDao.findRoleAll(params);
                                                   if (!CollectionUtils.isEmpty(roleList))
                                                   {
                                                       ImsRole imsRole =
                                                           imsRoleDao.findById(roleList.get(0).getRoleId());
                                                       if (imsRole != null)
                                                       {
                                                           newLoadTask.setApproveName(imsRole.getName());
                                                       }
                                                   }
                                               }
                                           }
                                       }
                                   }
                               }*/

                //  }

            }
        }

        return new Response<List<NewLoadTask>>(DataStatus.HTTP_SUCCESS, "", resultList);
    }

    //查找部门下人员
    @Override
    public List<DeptUsersDto> findDeptUser(String userId, String projId)
    {
        List<DeptUsersDto> resultList = new ArrayList<DeptUsersDto>();
        DeptUsersDto deptUserDto = new DeptUsersDto();
        deptUserDto.setUserId(userId);
        deptUserDto.setProjId(projId);
        List<DeptUsersDto> deptUserList = deptUserService.findAllBy(deptUserDto);
        if (CollectionUtils.isEmpty(deptUserList))
        {
            return null;
        }
        String deptId = deptUserList.get(0).getDeptId();
        DeptUsersDto param = new DeptUsersDto();
        param.setDeptId(deptId);
        List<DeptUsersDto> tempList = deptUserService.findAllBy(param);
        if (CollectionUtils.isEmpty(tempList))
        {
            return null;
        }
        for (DeptUsersDto deptUsersDto : tempList)
        {
            String userIds = deptUsersDto.getUserId();
            ImsUsers imsUsers = imsUserDao.findByUserId(userIds);
            if (imsUsers == null)
            {
                continue;
            }
            deptUsersDto.setUserName(imsUsers.getName());
            resultList.add(deptUsersDto);
        }
        return resultList;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.ims.service.ILoadProcessService#loadDeptCompletion(java.lang.String, java.lang.String, java.lang.String, int, int)   
    */
    @Override
    public List<LoadCompletionDto> loadDeptCompletion(String deptId, String projId,
        String procId)
    {
        List<LoadCompletionDto> resultList = new ArrayList<LoadCompletionDto>();

        ProcInstance paramss = new ProcInstance();
        paramss.setProjId(projId);
        paramss.setProcId(procId);
       
        List<ProcInstance> procList = procInstantsDao.findAllIncludeFinish(paramss);
        if (CollectionUtils.isEmpty(procList))
        {
            return new ArrayList<LoadCompletionDto>();
        }
        for (ProcInstance procInstance : procList)
        {
            
            //非会签的已办列表
            LoadFormData loadFormData = new LoadFormData();
            loadFormData.setProcId(procId);
            loadFormData.setParamInstId(procInstance.getId());
            loadFormData.setProjId(projId);
            loadFormData.setDeptId(deptId);
            loadFormData.setProcId(procId);

            resultList = addLoadCompletion(loadFormData, resultList);
        }
        Collections.sort(resultList, new CompletionComparator());
        return resultList;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.ims.service.ILoadProcessService#deleteMyData(java.lang.String, java.lang.String, java.lang.String)   
    */
    @Override
    public Response<RequestResult> deleteMyData(String projectId, String procId, String procInsId)
    {
        FormDataModel formDataModel = new FormDataModel();
        if (!StringUtils.isEmpty(projectId))
        {
            formDataModel.setProjectId(projectId);
        }
        if (!StringUtils.isEmpty(procId))
        {
            formDataModel.setProcId(procId);
        }
        if (!StringUtils.isEmpty(procInsId))
        {
            formDataModel.setProcessInstanceId(procInsId);
        }
        Page<FormData> page = findFormBy(formDataModel);
        if (page == null || page.getContent().size() <= 0)
        {
            RequestResult validateResult = new RequestResult(false, "该记录在数据库中不存在");
            //验证失败返回错误信息
            return new Response<RequestResult>(DataStatus.HTTP_FAILE, "删除失败", validateResult);
        }
        //获取mengoDB里的formData记录;
        FormData formData = page.getContent().get(0);
        //删除这条formData记录;
        formDataOptService.delete(formData.getId());
        RequestResult validateResult = new RequestResult(true, "删除成功");
        //验证失败返回错误信息
        return new Response<RequestResult>(DataStatus.HTTP_SUCCESS, "删除成功", validateResult);
    }
}
