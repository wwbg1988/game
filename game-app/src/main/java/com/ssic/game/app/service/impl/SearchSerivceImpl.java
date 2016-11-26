/**
 * 
 */
package com.ssic.game.app.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ssic.catering.base.util.MyComparator;
import com.ssic.game.app.service.ISendInfoService;
import com.ssic.game.app.service.ISensitiveWarningOperateService;
import com.ssic.game.app.service.SearchService;
import com.ssic.game.common.constant.WorkSearchConstants;
import com.ssic.game.common.dao.ImsUserDao;
import com.ssic.game.common.dao.MenuDao;
import com.ssic.game.common.dao.ProcessDao;
import com.ssic.game.common.dto.DeptUsersDto;
import com.ssic.game.common.dto.WorkSearchDto;
import com.ssic.game.common.pojo.ImsUsers;
import com.ssic.game.common.pojo.Menu;
import com.ssic.game.common.service.DeptUserService;
import com.ssic.ims.documents.FormData;
import com.ssic.ims.dto.FormDataQuery;
import com.ssic.ims.service.IFormDataQueryService;
import com.ssic.util.constants.DataStatus;
import com.ssic.util.model.Response;

/**		
 * <p>Title: UserOperateSerivce </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author milkteaye	
 * @date 2015年6月24日 下午8:26:26	
 * @version 1.0
 */

@Service
public class SearchSerivceImpl implements SearchService
{

    @Autowired
    private ProcessDao processDao;
    @Autowired
    private MenuDao menuDao;
    @Autowired
    private IFormDataQueryService queryService;
    @Autowired
    private ISensitiveWarningOperateService sensitiveWarningService;
    @Autowired
    private ISendInfoService sendInfoService;

    @Autowired
    private ImsUserDao imsUserDao;
    @Autowired
    private DeptUserService deptUserService;

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.app.service.SearchService#findAll(java.lang.String, java.lang.String, java.lang.String, int, int)   
    */
    @Override
    public Response<List<WorkSearchDto>> findAll(String userId, String projId, String searchDate,
        int beginRows, int endRows, int eventType)
    {
        int count = 0;

        final Log logger = LogFactory.getLog(SearchSerivceImpl.class);
        List<WorkSearchDto> resultList = new ArrayList<WorkSearchDto>();
        List<WorkSearchDto> tempList = new ArrayList<WorkSearchDto>();
        ImsUsers imsUsers = imsUserDao.findByUserId(userId);
        String userName = "";
        if (imsUsers != null)
        {
            userName = imsUsers.getName();
        }
        // eventType 0显示首页  1显示待办  3显示当天日程
        // List<WorkSearchDto> warningList = null;
        List<WorkSearchDto> warningList = new ArrayList<WorkSearchDto>();
        if (eventType == 0)
        {
            //警告
            List<WorkSearchDto> warningListJG =
                sensitiveWarningService.sensitiveWarningList(userId, searchDate, beginRows, endRows);
            if (!CollectionUtils.isEmpty(warningListJG))
            {
                int p = 0;
                for (WorkSearchDto workJG : warningListJG)
                {
                    if (p >= 4)
                    {
                        break;
                    }
                    workJG.setWorkName("预警");
                    warningList.add(workJG);
                    p++;
                }
                // tempList.addAll(notifyList);
                //放入前5条
                //   tempList.addAll(warningList);
            }

            //请假、出差、报销
            List<WorkSearchDto> QJList = findQJ(userId, projId, searchDate, beginRows, endRows);
            Collections.sort(QJList, new MyComparator());
            count += QJList.size();
            List<WorkSearchDto> QJList2 = new ArrayList<WorkSearchDto>();
            if (!CollectionUtils.isEmpty(QJList))
            {
                int t = 0;
                for (WorkSearchDto workXW : QJList)
                {
                    if (t >= 4)
                    {
                        break;
                    }

                    QJList2.add(workXW);
                    t++;
                }
        
                //放入前4条
                tempList.addAll(QJList2);
            }

            //新闻列表
            List<WorkSearchDto> newsList =
                sendInfoService.sendNews(userId, searchDate, beginRows, endRows, projId);

            List<WorkSearchDto> newsList2 = new ArrayList<WorkSearchDto>();
            if (!CollectionUtils.isEmpty(newsList))
            {
                int j = 0;
                for (WorkSearchDto workXW : newsList)
                {
                    if (j >= 4)
                    {
                        break;
                    }
                    workXW.setWorkName("新闻");
                    newsList2.add(workXW);
                    j++;
                }
                //tempList.addAll(newsList);
                //放入前5条
                tempList.addAll(newsList2);
            }

            //通告列表
            List<WorkSearchDto> notifyList =
                sendInfoService.sendNotify(userId, searchDate, beginRows, endRows, projId);

            List<WorkSearchDto> notifyList2 = new ArrayList<WorkSearchDto>();
            if (!CollectionUtils.isEmpty(notifyList))
            {
                int k = 0;
                for (WorkSearchDto workTG : notifyList)
                {
                    if (k >= 4)
                    {
                        break;
                    }
                    workTG.setWorkName("通知");
                    notifyList2.add(workTG);
                    k++;
                }
                //    tempList.addAll(notifyList);
                //放入前5条
                tempList.addAll(notifyList2);
            }

            //会议列表
            List<WorkSearchDto> meetList =
                sendInfoService.sendMeeting(userId, searchDate, beginRows, endRows);
            count += meetList.size();
            List<WorkSearchDto> meetList2 = new ArrayList<WorkSearchDto>();
            if (!CollectionUtils.isEmpty(meetList))
            {
                int r = 0;
                for (WorkSearchDto workHY : meetList)
                {
                    if (r >= 4)
                    {
                        break;
                    }
                    workHY.setWorkName(userName);
                    meetList2.add(workHY);
                    r++;
                }
                //tempList.addAll(meetList);
                //放入前5条
                tempList.addAll(meetList2);
            }
        }
        else
        {

            //请假、出差、报销
            List<WorkSearchDto> QJList = findQJ(userId, projId, searchDate, beginRows, endRows);
            if (!CollectionUtils.isEmpty(QJList))
            {

                tempList.addAll(QJList);
            }

            //会议列表
            List<WorkSearchDto> meetList =
                sendInfoService.sendMeeting(userId, searchDate, beginRows, endRows);
            if (!CollectionUtils.isEmpty(meetList))
            {
                for (WorkSearchDto workHY : meetList)
                {
                    workHY.setWorkName(userName);
                }
                tempList.addAll(meetList);
            }

        }
        Collections.sort(tempList, new MyComparator());
        if (!CollectionUtils.isEmpty(warningList))
        {
            resultList.addAll(warningList);
        }

        resultList.addAll(tempList);
        Collections.sort(resultList, new MyComparator());
        for (WorkSearchDto dto : resultList)
        {
            dto.setFirstPageCount(count);
        }
        return new Response<List<WorkSearchDto>>(DataStatus.HTTP_SUCCESS, "查询成功", resultList);

    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.app.service.SearchService#findQJ(java.lang.String, java.lang.String)   
    */

    private List<WorkSearchDto> findQJ(String userId, String projId, String searchDate, int beginRows,
        int endRows)
    {
        List<WorkSearchDto> resultList = new ArrayList<WorkSearchDto>();

        List<Map<Object, Object>> mapList =
            processDao.getLoadAgencyNotProc(userId, projId, beginRows, endRows, searchDate);
        if (CollectionUtils.isEmpty(mapList))
        {
            return null;
        }
        Date d1 = new Date();
        //去mengo数据库查找所有当前项目下的表单数据; 
        List<Map<String, Object>> fieldsMaps2 = findAllMangoData(projId);
        //processInstanceId
        for (Map<Object, Object> map : mapList)
        {
            if (!CollectionUtils.isEmpty(fieldsMaps2))
            {
                for (Map<String, Object> fieldsMap : fieldsMaps2)
                { //获取流程实例id
                    String procInstceId = fieldsMap.get("processInstanceId").toString();
                    //部门编码
                    String deptId = fieldsMap.get("deptId").toString();
                    //数据库查找的对象与mengo的表单数据的流程实例相同，才加入返回结果中;
                    if (map.get("id").equals(procInstceId))
                    {
                        WorkSearchDto param = new WorkSearchDto();

                        /* if (!StringUtils.isEmpty(fieldsMap.get("userId"))
                             && (!userId.equals(fieldsMap.get("userId"))))
                         {
                             continue;
                         }
                        */
                        //查找部门编码
                        DeptUsersDto deptUserDto = new DeptUsersDto();
                        deptUserDto.setUserId(userId);
                        List<DeptUsersDto> deptUserList = deptUserService.findDeptUser(deptUserDto);
                        if (!CollectionUtils.isEmpty(deptUserList) && !StringUtils.isEmpty(deptId))
                        {//获取部门用户对象
                            DeptUsersDto deptUser = deptUserList.get(0);
                            //如果查找的表单所属部门不是当前登录用户的部门,则返回;
                            String isAdmin = deptUser.getIsAdmin();
                            if (!StringUtils.isEmpty(isAdmin) && isAdmin.equals("0"))
                            {
                                continue;
                            }
                            if (!deptUser.getDeptId().equals(deptId))
                            {
                                continue;
                            }

                        }

                        if (!StringUtils.isEmpty(fieldsMap.get("markCreateTime")))
                        {
                            param.setCreateTime((Date) fieldsMap.get("markCreateTime"));
                            Date createTime = (Date) fieldsMap.get("markCreateTime");
                            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                            String cTime = sdf2.format(createTime);
                            String monthTime = cTime.substring(5, 7);
                            String dayTime = cTime.substring(8, 10);
                            String meetMD = monthTime + "月" + dayTime + "日";
                            param.setMeetMD(meetMD);
                            fieldsMap.remove("markCreateTime");
                        }

                        //查询创建人
                        String dateUserName = "";
                        FormDataQuery query = new FormDataQuery();
                        query.setProcessInstanceId((String) map.get("id"));
                        Page<FormData> page = queryService.findByPage(query, new PageRequest(0, 999999));
                        if (page != null && page.getContent().size() > 0)
                        {
                            List<FormData> formDataList = page.getContent();
                            if (formDataList != null)
                            {
                                String cc = formDataList.get(0).getUserName();
                                System.out.println(cc);
                                dateUserName = cc;
                            }

                        }

                        param.setUserId(userId);
                        param.setProcId(map.get("proc_id").toString());
                        param.setProjId(projId);
                        param.setUrl("/http/api/ims/app/loadAgency.do?userId=" + userId + "&projId=" + projId
                            + "&procId=" + map.get("proc_id") + "&procInstId=" + map.get("id"));
                        Menu menu = new Menu();
                        menu.setProcId(map.get("proc_id").toString());
                        menu.setStat(DataStatus.ENABLED);
                        List<Menu> menuList = menuDao.findBy(menu);
                        if (!CollectionUtils.isEmpty(menuList))
                        {
                            ImsUsers imsUser = imsUserDao.findByUserId(userId);
                            String userName = "";
                            if (imsUser != null)
                            {
                                userName = imsUser.getName();
                            }

                            if (menuList.get(0).getProcType() == WorkSearchConstants.QINGJIA)
                            {
                                String startTime = "";
                                String endTime = "";
                                String reason = "";
                                if (fieldsMap.get("startDate") != null)
                                {
                                    startTime = fieldsMap.get("startDate").toString();
                                }
                                if (fieldsMap.get("endDate") != null)
                                {
                                    endTime = fieldsMap.get("endDate").toString();
                                }
                                if (fieldsMap.get("qingJiaReason") != null)
                                {
                                    reason = fieldsMap.get("qingJiaReason").toString();
                                }
                                String message =
                                    dateUserName + "申请请假" + startTime + "至" + endTime + "请假理由" + reason;
                                param.setMessage(message);
                                param.setType(WorkSearchConstants.QINGJIA);
                            }
                            else if (menuList.get(0).getProcType() == WorkSearchConstants.BAOXIAO)
                            {
                                String fee = "";
                                String reason = "";
                                if (fieldsMap.get("reason") != null)
                                {
                                    reason = fieldsMap.get("reason").toString();
                                }
                                if (fieldsMap.get("fee") != null)
                                {
                                    fee = fieldsMap.get("fee").toString();
                                }
                                String message = dateUserName + "申请报销,报销理由:" + reason + "报销金额" + fee + "元";
                                param.setMessage(message);
                                param.setType(WorkSearchConstants.BAOXIAO);
                            }
                            else if (menuList.get(0).getProcType() == WorkSearchConstants.CHUCHAI)
                            {
                                String startTime = "";
                                String endTime = "";
                                String reason = "";
                                String city = "";
                                if (fieldsMap.get("startDate") != null)
                                {
                                    startTime = fieldsMap.get("startDate").toString();
                                }
                                if (fieldsMap.get("endDate") != null)
                                {
                                    endTime = fieldsMap.get("endDate").toString();
                                }
                                if (fieldsMap.get("reason") != null)
                                {
                                    reason = fieldsMap.get("reason").toString();
                                }
                                if (fieldsMap.get("city") != null)
                                {
                                    city = fieldsMap.get("city").toString();
                                }
                                String message =
                                    dateUserName + "申请于" + startTime + "至" + endTime + "前往" + city + "出差理由:"
                                        + reason;
                                param.setMessage(message);
                                param.setType(WorkSearchConstants.CHUCHAI);
                            }
                        }
                        param.setWorkName(dateUserName);
                        resultList.add(param);
                    }
                }
            }
        }
        Date d2 = new Date();
        System.out.println("查找请假:----------------------------------------" + (d2.getTime() - d1.getTime()));
        return resultList;
    }

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

        Page<FormData> page = queryService.findByPage(query, new PageRequest(0, 999999));
        if (page != null && page.getContent().size() > 0)
        {
            List<FormData> formDataList = page.getContent();
            if (CollectionUtils.isEmpty(formDataList))
            {
                return resultMap;
            }
            resultMap = formDataList.get(0).getValues();
            resultMap.put("userId", formDataList.get(0).getUserId());
            resultMap.put("markCreateTime", formDataList.get(0).getCreateTime());
            resultMap.put("markFormId", formDataList.get(0).getFormId());
        }
        return resultMap;
    }

    private List<Map<String, Object>> findAllMangoData(String projId)
    {
        List<Map<String, Object>> resultMap = new ArrayList<>();

        FormDataQuery query = new FormDataQuery();
        query.setProjectId(projId);
        Page<FormData> page = queryService.findByPage(query, new PageRequest(0, 999999));
        if (page != null && page.getContent().size() > 0)
        {
            List<FormData> formDataList = page.getContent();
            if (CollectionUtils.isEmpty(formDataList))
            {
                return resultMap;
            }
            for (FormData formData : formDataList)
            {
                Map<String, Object> maps = new HashMap<String, Object>();
                maps = formData.getValues();
                maps.put("userId", formData.getUserId());
                maps.put("markCreateTime", formData.getCreateTime());
                maps.put("markFormId", formData.getFormId());
                maps.put("processInstanceId", formData.getProcessInstanceId());
                maps.put("deptId", formData.getDeptId());
                resultMap.add(maps);
            }
        }
        return resultMap;
    }

}
