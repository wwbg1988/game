/**
 * 
 */
package com.ssic.game.ims.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssic.game.common.dao.ActionRoleDao;
import com.ssic.game.common.dao.ActionUserPersDao;
import com.ssic.game.common.dao.FieldsRoleDao;
import com.ssic.game.common.dao.FieldsUserDao;
import com.ssic.game.common.dao.ImsRoleUserDao;
import com.ssic.game.common.dao.ImsUserDao;
import com.ssic.game.common.pojo.ActionRole;
import com.ssic.game.common.pojo.ActionUser;
import com.ssic.game.common.pojo.FieldRole;
import com.ssic.game.common.pojo.FieldUser;
import com.ssic.game.common.pojo.ImsRole;
import com.ssic.game.common.pojo.ImsUsers;
import com.ssic.game.common.pojo.RoleUsers;
import com.ssic.game.ims.model.CreateProcRequest;
import com.ssic.util.model.RequestResult;

/**		
 * <p>Title: ProcessFormValidator </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author milkteaye	
 * @date 2015年6月30日 上午10:46:14	
 * @version 1.0
 * <p>修改人：milkteaye</p>
 * <p>修改时间：2015年6月30日 上午10:46:14</p>
 * <p>修改备注：</p>
 */
@Service
public class CreateFlowValidator
{
    @Autowired
    private ActionRoleDao actionRoleDao;

    @Autowired
    private ActionUserPersDao actionUserDao;

    @Autowired
    private FieldsUserDao fieldsUserDao;

    @Autowired
    private FieldsRoleDao fieldRoleDao;
    @Autowired
    private ImsRoleUserDao imsRoleUserDao;

    @Autowired
    private ImsUserDao imsUserDao;

    //校验传入参数是否为空
    public RequestResult vailisEmpty(CreateProcRequest request)
    {
        if (StringUtils.isEmpty(request))
        {
            return new RequestResult(false, "CreateProcRequest can not be null");
        }
        if (StringUtils.isEmpty(request.getUserId()))
        {
            return new RequestResult(false, "userId can not be null");
        }
        if (StringUtils.isEmpty(request.getProcessId()))
        {
            return new RequestResult(false, "processInstanceId can not be null");
        }
        if (StringUtils.isEmpty(request.getProjectId()))
        {
            return new RequestResult(false, "projectId can not be null");
        }
        //        if (StringUtils.isEmpty(request.getProcName()))
        //        {
        //            return new RequestResult(false, "procName can not be null");
        //        }
        return new RequestResult(true, "");
    }

    //校验actions
    public Map<Object, Object> validateActions(List<ImsRole> roleList, String userId,
        CreateProcRequest request)
    {
        Map<Object, Object> resultMap = new HashMap<Object, Object>();
        boolean flag = false;
        List<String> userIdList = new ArrayList<String>();
        ActionUser actionUser = new ActionUser();
        actionUser.setProcId(request.getProcessId());
        actionUser.setProjId(request.getProjectId());
        actionUser.setUserId(userId);
        List<ActionUser> userList = actionUserDao.findUserAll(actionUser);
        if (userList != null && userList.size() > 0)
        {
            flag = true;
            for (ActionUser ac : userList)
            {
                ImsUsers imsUsers = imsUserDao.findByUserId(ac.getUserId());
                if (imsUsers != null)
                {
                    if (!StringUtils.isEmpty(imsUsers.getQjyAccount()))
                    {
                        userIdList.add(imsUsers.getQjyAccount());
                    }

                }
            }
            //            return new RequestResult(true,"Action permission pass"); 
        }
        if (roleList != null && roleList.size() > 0)
        {
            for (int i = 0; i < roleList.size(); i++)
            {
                ImsRole role = roleList.get(i);
                if (!StringUtils.isEmpty(role.getId()))
                {
                    ActionRole actionRole = new ActionRole();
                    actionRole.setProcId(request.getProcessId());
                    actionRole.setProjId(request.getProjectId());
                    actionRole.setRoleId(role.getId());
                    List<ActionRole> tempList = actionRoleDao.findRoleAll(actionRole);
                    if (tempList != null && tempList.size() > 0)
                    {
                        flag = true;
                        //                      return new RequestResult(true,"Action permission pass"); 
                        for (ActionRole act : tempList)
                        {
                            String roleId = act.getRoleId();
                            RoleUsers paramRole = new RoleUsers();
                            paramRole.setId(roleId);
                            List<RoleUsers> roleUserList = imsRoleUserDao.findAllBy(paramRole);
                            if (roleUserList != null && roleUserList.size() > 0)
                            {
                                for (RoleUsers roles : roleUserList)
                                {
                                    ImsUsers imsUsers = imsUserDao.findByUserId(userId);
                                    if (imsUsers != null)
                                    {
                                        if (!StringUtils.isEmpty(imsUsers.getQjyAccount()))
                                        {
                                            userIdList.add(imsUsers.getQjyAccount());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }

        if (flag == false)
        {
            resultMap.put("flag", 0);
        }
        else
        {
            resultMap.put("flag", 1);
            resultMap.put("qjy", userIdList);
        }

        return resultMap;

    }

    public boolean isValiAction(List<ImsRole> roleList, String userId, CreateProcRequest request,
        String actionId)
    {
        boolean flag = false;
        ActionUser actionUser = new ActionUser();
        actionUser.setActionId(actionId);
        //        actionUser.setUserId(userId);
        List<ActionUser> userList = actionUserDao.findUserAll(actionUser);
        if (userList != null && userList.size() > 0)
        {
            for (ActionUser actionUserss : userList)
            {
                if ("owner".equals(actionUserss.getUserId()))
                {
                    flag = true;
                    return flag;
                }
                else if (userId.equals(actionUserss.getUserId()))
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
                    actionRole.setRoleId(role.getId());
                    List<ActionRole> tempList = actionRoleDao.findRoleAll(actionRole);
                    if (tempList != null && tempList.size() > 0)
                    {
                        flag = true;
                        return flag;
                    }
                }

            }
        }

        return flag;

    }

    public RequestResult validateFields(List<ImsRole> roleList, String userId, CreateProcRequest request,
        String fieldId)
    {

        FieldUser fieldsUser = new FieldUser();
        fieldsUser.setUserId(userId);
        fieldsUser.setProcId(request.getProcessId());
        fieldsUser.setProjId(request.getProjectId());
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
                    frole.setProcId(request.getProcessId());
                    frole.setProjId(request.getProjectId());
                    frole.setFieldId(fieldId);
                    frole.setRoleId(role.getId());
                    List<FieldRole> tempList = fieldRoleDao.findAllBy(frole);
                    if (tempList != null && tempList.size() > 0)
                    {
                        return new RequestResult(true, "2");
                    }
                }

            }
        }

        return new RequestResult(false, "3");

    }

}
