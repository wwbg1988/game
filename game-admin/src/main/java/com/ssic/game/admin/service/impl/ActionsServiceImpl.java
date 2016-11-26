/**   
 * bare_field_name   
 * com.ssic.game.admin.service.impl	
 * @return  the bare_field_name 
 */

package com.ssic.game.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ssic.game.admin.pageModel.DataGrid;
import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.admin.pageModel.Tree;
import com.ssic.game.admin.service.IActionsService;
import com.ssic.game.common.constant.ActionTypeConstants;
import com.ssic.game.common.dao.ActionRoleDao;
import com.ssic.game.common.dao.ActionUserPersDao;
import com.ssic.game.common.dao.ActionsDao;
import com.ssic.game.common.dao.DeptDao;
import com.ssic.game.common.dao.DeptUserDao;
import com.ssic.game.common.dao.FormsDao;
import com.ssic.game.common.dao.ImsRolesDao;
import com.ssic.game.common.dao.ImsUserDao;
import com.ssic.game.common.dao.ProjRoleDao;
import com.ssic.game.common.dao.ProjectUsersDao;
import com.ssic.game.common.dao.TasksDao;
import com.ssic.game.common.dto.ActionsDto;
import com.ssic.game.common.dto.DeptDto;
import com.ssic.game.common.dto.ProjectUsersDto;
import com.ssic.game.common.dto.TasksDto;
import com.ssic.game.common.pojo.ActionRole;
import com.ssic.game.common.pojo.ActionUser;
import com.ssic.game.common.pojo.Actions;
import com.ssic.game.common.pojo.DeptUsers;
import com.ssic.game.common.pojo.Forms;
import com.ssic.game.common.pojo.ImsRole;
import com.ssic.game.common.pojo.ImsUsers;
import com.ssic.game.common.pojo.ProjectRole;
import com.ssic.game.common.pojo.Tasks;
import com.ssic.util.BeanUtils;

/**		
 * <p>Title: ActionsServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年6月24日 上午8:59:27	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年6月24日 上午8:59:27</p>
 * <p>修改备注：</p>
 */
@Service
public class ActionsServiceImpl implements IActionsService
{

    @Autowired
    private ActionsDao actionsDao;

    @Autowired
    private ActionRoleDao actionRoleDao;

    @Autowired
    private ActionUserPersDao actionUserPerDao;

    @Autowired
    private ProjRoleDao projDao;

    @Autowired
    private ImsRolesDao imsRoleDao;

    @Autowired
    private DeptDao deptDao;

    @Autowired
    private ImsUserDao imsUserDao;

    @Autowired
    private ProjectUsersDao projectUsersDao;

    @Autowired
    private FormsDao formsDao;

    @Autowired
    private DeptUserDao deptUserDao;

    @Autowired
    private TasksDao tasksDao;

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.admin.service.IActionsService#insert(com.ssic.game.admin.dto.ActionsDto)   
    */
    public void insert(ActionsDto actionsDto)
    {
        Actions actions = new Actions();
        BeanUtils.copyProperties(actionsDto, actions);
        actionsDao.insertSelective(actions);
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.admin.service.IActionsService#findAllBy(com.ssic.game.admin.dto.ActionsDto)   
    */
    public List<ActionsDto> findAllBy(ActionsDto actionsDto)
    {
        Actions actions = new Actions();
        BeanUtils.copyProperties(actionsDto, actions);
        List<Actions> list = actionsDao.findAllBy(actions);
        if (list != null && list.size() > 0)
        {
            List<ActionsDto> result = BeanUtils.createBeanListByTarget(list, ActionsDto.class);
            return result;
        }
        return null;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.admin.service.IActionsService#dataGrid(com.ssic.game.admin.dto.ActionsDto, com.ssic.game.admin.pageModel.PageHelper)   
    */
    public DataGrid dataGrid(ActionsDto actions, PageHelper ph)
    {
        Actions temp = new Actions();
        if (!StringUtils.isEmpty(actions.getSearchPro()) && !"-1".equals(actions.getSearchPro()))
        {
            actions.setProjId(actions.getSearchPro());
        }
        BeanUtils.copyProperties(actions, temp);
        DataGrid dataGrid = new DataGrid();
        dataGrid.setTotal(Long.parseLong(actionsDao.findCountAll(temp) + ""));
        int beginPh = ph.getPage();
        int rows = ph.getRows();
        List<Map<Object, Object>> mapList =
            actionsDao.findPageBy(temp, beginPh, rows, actionsDao.findCountAll(temp));
        //        List<Map<Object, Object>> finishList = actionsDao.findPageFinish(temp, beginPh, rows);
        List<ActionsDto> resultList = new ArrayList<ActionsDto>();
        if (mapList != null && mapList.size() > 0)
        {
            for (Map<Object, Object> map : mapList)
            {
                ActionsDto result = new ActionsDto();
                String id = map.get("id").toString();
                result.setId(id);
                String taskName = map.get("taskName").toString();
                result.setTaskName(taskName);
                int type = Integer.parseInt(map.get("type").toString());
                if (map.get("nextTaskName") == null)
                {
                    result.setNextTaskName("");
                }
                else
                {
                    String nextTask = map.get("nextTaskName").toString();
                    if ("undefault".equals(map.get("nextTaskName").toString())
                        && type == ActionTypeConstants.ACTION_TTPE_CUSTOM)
                    {
                        result.setNextTaskName("自定义提交");
                    }
                    else if ("undefault".equals(map.get("nextTaskName").toString())
                        && type == ActionTypeConstants.ACTION_TTPE_UPDATE)
                    {
                        result.setNextTaskName("流程更新");
                    }
                    else if ("undefault".equals(map.get("nextTaskName").toString())
                        && type == ActionTypeConstants.ACTION_TTPE_REVERT)
                    {
                        result.setNextTaskName("流程回退");
                    }
                    else
                    {
                        result.setNextTaskName(nextTask);
                    }
                }

                String projName = map.get("projName").toString();
                result.setProjName(projName);
                result.setActionUrl(map.get("actionUrl").toString());
                String name = map.get("name").toString();
                result.setName(name);
                //1:发起/启动 2:办理 3:退回 4:办结 5：自定义动作
                if (type == ActionTypeConstants.ACTION_TTPE_ADD)
                {
                    result.setTypeName("保存");
                }
                else if (type == ActionTypeConstants.ACTION_TTPE_UPDATE)
                {
                    result.setTypeName("更新");
                }
                else if (type == ActionTypeConstants.ACTION_TTPE_REVERT)
                {
                    result.setTypeName("回退");
                }
                else if (type == ActionTypeConstants.ACTION_TTPE_CUSTOM)
                {
                    result.setTypeName("自定义操作");
                }
                else if (type == ActionTypeConstants.ACTION_TTPE_PASS)
                {
                    result.setTypeName("提交");
                }
                else if (type == ActionTypeConstants.ACTION_TTPE_REFUSE)
                {
                    result.setTypeName("拒绝");
                }
                else
                {
                    result.setTypeName("未知类型");
                }
                resultList.add(result);

            }

        }

        //        if (finishList != null && finishList.size() > 0)
        //        {
        //            for (Map<Object, Object> mapFinsh : finishList)
        //            {
        //                ActionsDto resultFinsh = new ActionsDto();
        //                String id = mapFinsh.get("id").toString();
        //                resultFinsh.setId(id);
        //                String taskName = mapFinsh.get("taskName").toString();
        //                resultFinsh.setTaskName(taskName);
        //                String nextTask = mapFinsh.get("nextTaskName").toString();
        //                resultFinsh.setNextTaskName(nextTask);
        //                int type = Integer.parseInt(mapFinsh.get("type").toString());
        //                String projName = mapFinsh.get("projName").toString();
        //                resultFinsh.setProjName(projName);
        //                resultFinsh.setActionUrl(mapFinsh.get("actionUrl").toString());
        //                String name = mapFinsh.get("name").toString();
        //                resultFinsh.setName(name);
        //                //1:发起/启动 2:办理 3:退回 4:办结 5：自定义动作
        //                if (type == 1)
        //                {
        //                    resultFinsh.setTypeName("保存");
        //                }
        //                else if (type == 2)
        //                {
        //                    resultFinsh.setTaskName("更新");
        //                }
        //                else if (type == 3)
        //                {
        //                    resultFinsh.setTypeName("回退");
        //                }
        //                else if (type == 4)
        //                {
        //                    resultFinsh.setTypeName("自定义操作");
        //                }
        //                else if (type == 5)
        //                {
        //                    resultFinsh.setTypeName("提交");
        //                }
        //                else
        //                {
        //                    resultFinsh.setTaskName("未知类型");
        //                }
        //                resultList.add(resultFinsh);
        //
        //            }
        //
        //        }

        dataGrid.setRows(resultList);

        return dataGrid;
    }

    /**
     * 删除方法 
    * (non-Javadoc)   
    * @see com.ssic.game.admin.service.IActionsService#del(com.ssic.game.admin.dto.ActionsDto)   
    */
    @CacheEvict(value = "default", key = "'game.common.dto.ActionsDto:' + #actionsDto.getId()", beforeInvocation = true)
    public void del(ActionsDto actionsDto)
    {
        Actions actions = new Actions();
        actions.setId(actionsDto.getId());
        actions.setLastUpdateTime(new Date());
        actions.setStat(0);
        actionsDao.del(actions);
    }

    /** 
     * 修改方法
    * (non-Javadoc)   
    * @see com.ssic.game.admin.service.IActionsService#updateFun(com.ssic.game.admin.dto.ActionsDto)   
    */
    @CacheEvict(value = "default", key = "'game.common.dto.ActionsDto:' + #actionsDto.getId()", beforeInvocation = true)
    public void updateFun(ActionsDto actionsDto)
    {
        Actions actions = new Actions();
        BeanUtils.copyProperties(actionsDto, actions);
        actionsDao.del(actions);
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.admin.service.IActionsService#findUserRole(java.lang.String)   
    */
    public String findUserRole(String ids, String projId)
    {
        ActionRole actionsUserDto = new ActionRole();
        actionsUserDto.setActionId(ids);
        actionsUserDto.setProjId(projId);

        List<ActionRole> list = actionRoleDao.findRoleAll(actionsUserDto);
        if (list != null && list.size() > 0)
        {
            String result = "";
            for (int i = 0; i < list.size(); i++)
            {
                if (i == list.size() - 1)
                {
                    result += list.get(i).getRoleId();
                }
                else
                {
                    result += list.get(i).getRoleId() + ",";
                }
            }
            return result;
        }
        return null;
    }

    public List<Tree> allTree(String projId)
    {
        List<Tree> result = new ArrayList<Tree>();
        ImsRole param = new ImsRole();
        param.setProjId(projId);
        List<ImsRole> list = imsRoleDao.findAllBy(param);
        if (list != null && list.size() > 0)
        {
            for (ImsRole ir : list)
            {
                Tree tree = new Tree();
                tree.setId(ir.getId());
                tree.setText(ir.getName());
                result.add(tree);
            }

        }
        return result;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.admin.service.IActionsService#allUserTree()   
    */
    public List<Tree> allUserTree()
    {
        // TODO 添加方法注释
        return null;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.admin.service.IActionsService#tree()   
    */
    public List<Tree> tree()
    {
        List<Tree> result = new ArrayList<Tree>();
        List<ProjectRole> list = projDao.findAll();
        if (list != null && list.size() > 0)
        {
            for (ProjectRole pro : list)
            {
                Tree tree = new Tree();
                tree.setId(pro.getRoleId());
                ImsRole imsRole = imsRoleDao.selectByPrimaryKey(pro.getRoleId());
                tree.setText(imsRole.getName());
                result.add(tree);
            }
        }
        return result;

    }

    private boolean vailUserProject(String userId, String projId)
    {
        boolean flag = false;
        ProjectUsersDto projectUsersDto = new ProjectUsersDto();
        projectUsersDto.setUserId(userId);
        projectUsersDto.setProjId(projId);
        List<ProjectUsersDto> list = projectUsersDao.findAll(projectUsersDto);
        if (list != null && list.size() > 0)
        {
            flag = true;
        }
        return flag;
    }

    /** 
     * (non-Javadoc)   
     * @see com.ssic.game.admin.service.IActionsService#fieldTree(java.lang.String, java.lang.String)   
     */
    @Override
    public List<Tree> fieldTree(String searchName, String formId)
    {
        List<Tree> result = new ArrayList<Tree>();
        if (!StringUtils.isEmpty(formId))
        {

            Forms forms = formsDao.selectByPrimaryKey(formId);
            if (forms != null && StringUtils.isEmpty(searchName))
            {
                String projId = forms.getProjId();
                DeptDto deptDto = new DeptDto();
                deptDto.setProjId(projId);
                List<DeptDto> listDept = deptDao.findAll(deptDto);
                if (listDept != null && listDept.size() > 0)
                {
                    for (DeptDto deptsDto : listDept)
                    {
                        Tree tree = new Tree();
                        tree.setText(deptsDto.getDeptName());
                        tree.setId(deptsDto.getId());
                        tree.setPid(deptsDto.getPid());
                        tree.setIconCls("server_database");
                        result.add(tree);
                    }
                }
                DeptUsers deptUsers = new DeptUsers();
                deptUsers.setProjId(projId);
                List<DeptUsers> list = deptUserDao.findAllBy(deptUsers);
                if (list != null && list.size() > 0)
                {
                    String deptIdEq = "";
                    for (DeptUsers tempDeptUser : list)
                    {
                        //                        if (!deptIdEq.equals(tempDeptUser.getDeptId())
                        //                            && vailUserProject(tempDeptUser.getUserId(), projId))
                        //                        {
                        //                            DeptDto dept = deptDao.findById(tempDeptUser.getDeptId());
                        //                            if (dept != null)
                        //                            {
                        //                                Tree tree = new Tree();
                        //                                tree.setId(dept.getId());
                        //                                tree.setText(dept.getDeptName());
                        //                                tree.setIconCls("server_database");
                        //                                if(!StringUtils.isEmpty(dept.getPid())){
                        //                                    tree.setPid(dept.getPid());
                        //                                }
                        //                                result.add(tree);
                        //                            }
                        //                            deptIdEq = tempDeptUser.getDeptId();
                        //
                        //                            ImsUsers tempImsUsers = imsUserDao.findByUserId(tempDeptUser.getUserId());
                        //                            if (tempImsUsers != null)
                        //                            {
                        //                                Tree tree = new Tree();
                        //                                tree.setId(tempImsUsers.getId());
                        //                                tree.setText(tempImsUsers.getName());
                        //                                tree.setPid(deptIdEq);
                        //                                tree.setIconCls("status_online");
                        //                                result.add(tree);
                        //                            }
                        //
                        //                        }
                        //                        else if (deptIdEq.equals(tempDeptUser.getDeptId())
                        //                            && vailUserProject(tempDeptUser.getUserId(), projId))
                        //                        {
                        if (vailUserProject(tempDeptUser.getUserId(), projId))
                        {
                            ImsUsers tempImsUsers = imsUserDao.findByUserId(tempDeptUser.getUserId());
                            if (tempImsUsers != null)
                            {
                                Tree tree = new Tree();
                                tree.setId(tempImsUsers.getId());
                                tree.setText(tempImsUsers.getName());
                                tree.setPid(tempDeptUser.getDeptId());
                                tree.setIconCls("status_online");
                                result.add(tree);
                            }
                        }
                    }
                    return result;
                }

            }
            else
            {
                Forms tempForms = formsDao.selectByPrimaryKey(formId);
                String projId = tempForms.getProjId();
                //                List<Map<Object,Object>> mapList = deptUserService.findAllBySearch(searchName, projId);
                List<Map<Object, Object>> mapList = deptUserDao.findBySearchName(searchName, projId);
                if (mapList != null && mapList.size() > 0)
                {
                    String deptIdEq = "";
                    for (Map<Object, Object> map : mapList)
                    {
                        if (!deptIdEq.equals(map.get("deptId").toString())
                            && vailUserProject(map.get("userId").toString(), projId))
                        {
                            Tree tree = new Tree();
                            tree.setId(map.get("deptId").toString());
                            tree.setText(map.get("deptName").toString());
                            tree.setIconCls("server_database");
                            result.add(tree);
                            deptIdEq = map.get("deptId").toString();
                            Tree userTree = new Tree();
                            userTree.setId(map.get("userId").toString());
                            userTree.setText(map.get("userName").toString());
                            userTree.setPid(deptIdEq);
                            tree.setIconCls("status_online");
                            result.add(userTree);
                        }
                        else if (deptIdEq.equals(map.get("deptId").toString())
                            && vailUserProject(map.get("userId").toString(), projId))
                        {
                            Tree userTree = new Tree();
                            userTree.setId(map.get("userId").toString());
                            userTree.setText(map.get("userName").toString());
                            userTree.setPid(deptIdEq);
                            userTree.setIconCls("status_online");
                            result.add(userTree);
                        }
                    }
                    return result;
                }

            }
        }
        return result;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.admin.service.IActionsService#userTree()   
    */
    public List<Tree> userTree(String searchName, String ids)
    {
        List<Tree> result = new ArrayList<Tree>();

        Actions actions = new Actions();
        actions.setId(ids);
        List<Actions> actionsList = actionsDao.findAllBy(actions);
        if (actionsList != null && actionsList.size() > 0)
        {
            Actions tempActions = actionsList.get(0);

            //有查询名称的情况

            if (searchName != null && !"".equals(searchName))
            {
                //               List<Map<Object,Object>> mapList =deptUserService.findAllBySearch(searchName, tempActions.getProjId());
                List<Map<Object, Object>> mapList =
                    deptUserDao.findBySearchName(searchName, tempActions.getProjId());
                if (mapList != null && mapList.size() > 0)
                {
                    String deptIdEq = "";
                    for (Map<Object, Object> map : mapList)
                    {
                        if (!deptIdEq.equals(map.get("deptId").toString())
                            && vailUserProject(map.get("userId").toString(), tempActions.getProjId()))
                        {
                            Tree tree = new Tree();
                            tree.setId(map.get("deptId").toString());
                            tree.setText(map.get("deptName").toString());
                            result.add(tree);
                            deptIdEq = map.get("deptId").toString();
                            Tree userTree = new Tree();
                            userTree.setId(map.get("userId").toString());
                            userTree.setText(map.get("userName").toString());
                            userTree.setPid(deptIdEq);
                            result.add(userTree);
                        }
                        else if (deptIdEq.equals(map.get("deptId").toString())
                            && vailUserProject(map.get("userId").toString(), tempActions.getProjId()))
                        {
                            Tree userTree = new Tree();
                            userTree.setId(map.get("userId").toString());
                            userTree.setText(map.get("userName").toString());
                            userTree.setPid(deptIdEq);
                            result.add(userTree);
                        }
                    }
                    return result;
                }
                return result;
            }

            //没有查询NAME的情况

            String projId = tempActions.getProjId();

            DeptDto deptDto = new DeptDto();
            deptDto.setProjId(projId);
            List<DeptDto> listDept = deptDao.findAll(deptDto);
            if (listDept != null && listDept.size() > 0)
            {
                for (DeptDto deptsDto : listDept)
                {
                    Tree tree = new Tree();
                    tree.setText(deptsDto.getDeptName());
                    tree.setId(deptsDto.getId());
                    tree.setPid(deptsDto.getPid());
                    tree.setIconCls("server_database");
                    result.add(tree);
                }
            }

            DeptUsers deptUsers = new DeptUsers();
            deptUsers.setProjId(projId);
            List<DeptUsers> list = deptUserDao.findAllBy(deptUsers);
            if (list != null && list.size() > 0)
            {
                for (DeptUsers tempDeptUser : list)
                {
                    //                    if (!deptIdEq.equals(tempDeptUser.getDeptId())
                    //                        && vailUserProject(tempDeptUser.getUserId(), projId))
                    //                    {
                    //                        DeptDto dept = deptDao.findById(tempDeptUser.getDeptId());
                    //                        if (dept != null)
                    //                        {
                    //                            Tree tree = new Tree();
                    //                            tree.setId(dept.getId());
                    //                            tree.setText(dept.getDeptName());
                    //                            result.add(tree);
                    //                        }
                    //                        deptIdEq = tempDeptUser.getDeptId();
                    //
                    //                        ImsUsers tempImsUsers = imsUserDao.findByUserId(tempDeptUser.getUserId());
                    //                        if (tempImsUsers != null)
                    //                        {
                    //                            Tree tree = new Tree();
                    //                            tree.setId(tempImsUsers.getId());
                    //                            tree.setText(tempImsUsers.getName());
                    //                            tree.setPid(deptIdEq);
                    //                            result.add(tree);
                    //                        }
                    //
                    //                    }
                    //                    else if (deptIdEq.equals(tempDeptUser.getDeptId())
                    //                        && vailUserProject(tempDeptUser.getUserId(), projId))
                    //                    {
                    if (vailUserProject(tempDeptUser.getUserId(), projId))
                    {
                        ImsUsers tempImsUsers = imsUserDao.findByUserId(tempDeptUser.getUserId());
                        if (tempImsUsers != null)
                        {
                            Tree tree = new Tree();
                            tree.setId(tempImsUsers.getId());
                            tree.setText(tempImsUsers.getName());
                            tree.setPid(tempDeptUser.getDeptId());
                            tree.setIconCls("status_online");
                            result.add(tree);
                        }
                    }
                }
                return result;
            }

            //           ProjectUsersDto projectUserDto = new ProjectUsersDto();
            //           projectUserDto.setProjId(tempActions.getProjId());
            //           List<ProjectUsersDto> proList = projectUsersDao.findAll(projectUserDto);
            //           String deptIdEq="";
            //           if(proList!=null&&proList.size()>0){
            //               for(ProjectUsersDto tempProject : proList){
            //                   if(!deptIdEq.equals(tempProject.getDeptId())){
            //                       DeptDto dept = deptDao.findById(tempProject.getDeptId());
            //                       if(dept!=null){
            //                           Tree tree = new Tree();
            //                           tree.setId(dept.getId());
            //                           tree.setText(dept.getDeptName());
            //                           result.add(tree);
            //                       }else{
            //                           continue;
            //                       }
            //                       deptIdEq=tempProject.getDeptId();
            //                       
            //                       ImsUsers  tempImsUsers = imsUserDao.findByUserId(tempProject.getUserId());
            //                       if(tempImsUsers!=null){
            //                           Tree tree=new Tree();
            //                           tree.setId(tempImsUsers.getId());
            //                           tree.setText(tempImsUsers.getName());
            //                           tree.setPid(deptIdEq);
            //                           result.add(tree);
            //                       }
            //                   }else{
            //                       ImsUsers  tempImsUsers = imsUserDao.findByUserId(tempProject.getUserId());
            //                       if(tempImsUsers!=null){
            //                           Tree tree=new Tree();
            //                           tree.setId(tempImsUsers.getId());
            //                           tree.setText(tempImsUsers.getName());
            //                           tree.setPid(deptIdEq);
            //                           result.add(tree);
            //                       } 
            //                   }
            //               }
            //               return result;
            //           }
            return result;
        }
        return result;

    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.admin.service.IActionsService#userTreeBy(java.lang.String, java.lang.String)   
    */
    @Override
    public List<Tree> userTreeBy(String searchName, List<String> userIds, String formId)
    {

        List<Tree> result = new ArrayList<Tree>();

        if (!StringUtils.isEmpty(formId))
        {

            Forms forms = formsDao.selectByPrimaryKey(formId);
            if (forms != null && StringUtils.isEmpty(searchName))
            {
                String projId = forms.getProjId();
                DeptDto deptDto = new DeptDto();
                deptDto.setProjId(projId);
                List<DeptDto> listDept = deptDao.findAll(deptDto);
                if (listDept != null && listDept.size() > 0)
                {
                    for (DeptDto deptsDto : listDept)
                    {
                        Tree tree = new Tree();
                        tree.setText(deptsDto.getDeptName());
                        tree.setId(deptsDto.getId());
                        tree.setPid(deptsDto.getPid());
                        tree.setIconCls("server_database");
                        result.add(tree);
                    }
                }
                List<DeptUsers> tempUserList = deptUserDao.findAllNotExists(projId, userIds);
                if (tempUserList != null && tempUserList.size() > 0)
                {
                    for (DeptUsers deptsDto : tempUserList)
                    {
                        //                        if (!deptIdEq.equals(deptsDto.getDeptId())
                        //                            && vailUserProject(deptsDto.getUserId(), projId))
                        //                        {
                        //                            DeptDto dept = deptDao.findById(deptsDto.getDeptId());
                        //                            if (dept != null)
                        //                            {
                        //                                Tree tree = new Tree();
                        //                                tree.setId(dept.getId());
                        //                                tree.setText(dept.getDeptName());
                        //                                result.add(tree);
                        //                            }
                        //                            deptIdEq = deptsDto.getDeptId();
                        //
                        //                            ImsUsers tempImsUsers = imsUserDao.findByUserId(deptsDto.getUserId());
                        //                            if (tempImsUsers != null)
                        //                            {
                        //                                Tree tree = new Tree();
                        //                                tree.setId(tempImsUsers.getId());
                        //                                tree.setText(tempImsUsers.getName());
                        //                                tree.setPid(deptIdEq);
                        //                                result.add(tree);
                        //                            }
                        //                        }
                        //                        else if (deptIdEq.equals(deptsDto.getDeptId())
                        //                            && vailUserProject(deptsDto.getUserId(), projId))
                        //                        {

                        if (vailUserProject(deptsDto.getUserId(), projId))
                        {
                            ImsUsers tempImsUsers = imsUserDao.findByUserId(deptsDto.getUserId());
                            if (tempImsUsers != null)
                            {
                                Tree tree = new Tree();
                                tree.setId(tempImsUsers.getId());
                                tree.setText(tempImsUsers.getName());
                                tree.setPid(deptsDto.getDeptId());
                                tree.setIconCls("status_online");
                                result.add(tree);
                            }
                        }
                    }
                    return result;
                }

            }
            else
            {
                Forms tempForms = formsDao.selectByPrimaryKey(formId);
                String projId = tempForms.getProjId();
                String usersIds = "";
                if (userIds != null && userIds.size() > 0)
                {
                    for (int i = 0; i < userIds.size(); i++)
                    {
                        String tempString = userIds.get(i);
                        if (i == userIds.size() - 1)
                        {
                            usersIds += tempString;
                        }
                        else
                        {
                            usersIds += tempString + ",";
                        }
                    }
                }

                String tempSearch = "%" + searchName + "%";
                List<Map<Object, Object>> mapList =
                    deptUserDao.findAllByNotExist(tempSearch, usersIds, projId);
                if (mapList != null && mapList.size() > 0)
                {
                    String deptIdEq = "";
                    for (Map<Object, Object> map : mapList)
                    {
                        if (!deptIdEq.equals(map.get("deptId").toString())
                            && vailUserProject(map.get("userId").toString(), projId))
                        {
                            Tree tree = new Tree();
                            tree.setId(map.get("deptId").toString());
                            tree.setText(map.get("deptName").toString());
                            result.add(tree);
                            deptIdEq = map.get("deptId").toString();
                            Tree userTree = new Tree();
                            userTree.setId(map.get("userId").toString());
                            userTree.setText(map.get("userName").toString());
                            userTree.setPid(deptIdEq);
                            result.add(userTree);
                        }
                        else if (deptIdEq.equals(map.get("deptId").toString())
                            && vailUserProject(map.get("userId").toString(), projId))
                        {
                            Tree userTree = new Tree();
                            userTree.setId(map.get("userId").toString());
                            userTree.setText(map.get("userName").toString());
                            userTree.setPid(deptIdEq);
                            result.add(userTree);
                        }
                    }
                }

            }
        }

        return result;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.admin.service.IActionsService#grantUser(java.lang.String, java.lang.String)   
    */
    public void grantUser(String actionsids, String rolesId,String ActionUserOwn)
    {
        Actions actions = new Actions();
        actions.setId(actionsids);
        List<Actions> actionList = actionsDao.findAllBy(actions);
        String projId = "";
        String procId = "";
        if (actionList != null && actionList.size() > 0)
        {
            projId = actionList.get(0).getProjId();
            procId = actionList.get(0).getProcId();
        }
        ActionUser delUser = new ActionUser();
        delUser.setProjId(projId);
        delUser.setActionId(actionsids);
        actionUserPerDao.delRoles(delUser);
        if (rolesId != null && rolesId.length() > 0)
        {
            List<String> roles = new ArrayList<String>();
            if (actionsids != null)
            {
                for (String roleId : rolesId.split(","))
                {
                    roles.add(roleId);
                }
            }

            for (String rId : roles)
            {
                ImsUsers param = new ImsUsers();
                param.setId(rId);
                List<ImsUsers> userList = imsUserDao.findUserAll(param);
                if (userList != null && userList.size() > 0)
                {
                    ActionUser inserUsers = new ActionUser();
                    inserUsers.setProjId(projId);
                    inserUsers.setProcId(procId);
                    inserUsers.setActionId(actionsids);
                    inserUsers.setUserId(rId);
                    actionUserPerDao.insertRoles(inserUsers);
                }

            }
        }
        if("UserOwnOk".equals(ActionUserOwn)){
            ActionUser inserOwn = new ActionUser();
            inserOwn.setProjId(projId);
            inserOwn.setProcId(procId);
            inserOwn.setActionId(actionsids);
            inserOwn.setUserId("owner");
            actionUserPerDao.insertRoles(inserOwn);
        }
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.admin.service.IActionsService#grant(java.lang.String, java.lang.String)   
    */
    public String grant(String actionsids, String rolesId,String ActionRoleOwn)
    {

        Actions actions = new Actions();
        actions.setId(actionsids);
        List<Actions> actionList = actionsDao.findAllBy(actions);
        String projId = "";
        String procId = "";
        String taskId = "";
        if (actionList != null && actionList.size() > 0)
        {
            projId = actionList.get(0).getProjId();
            procId = actionList.get(0).getProcId();
            taskId = actionList.get(0).getTaskId();
            TasksDto tasksDto = tasksDao.findById(taskId);
            if (tasksDto == null || (tasksDto != null && tasksDto.getCountersign() == 0))
            {
                ActionRole delUsers = new ActionRole();
                delUsers.setProjId(projId);
                delUsers.setActionId(actionsids);
                actionRoleDao.delRoles(delUsers);
                if (rolesId != null && rolesId.length() > 0)
                {
                    List<String> roles = new ArrayList<String>();
                    if (actionsids != null)
                    {
                        for (String roleId : rolesId.split(","))
                        {
                            roles.add(roleId);
                        }
                    }

                    for (String rId : roles)
                    {
                        ActionRole inserUsers = new ActionRole();
                        inserUsers.setProcId(procId);
                        inserUsers.setProjId(projId);
                        inserUsers.setActionId(actionsids);
                        inserUsers.setRoleId(rId);
                        actionRoleDao.insertRoles(inserUsers);
                    }
                }
            }
            else
            {
                return "会签结点不能付角色权限";
            }
            if("roleOwnOk".equals(ActionRoleOwn)){
                ActionRole inserOwn = new ActionRole();
                inserOwn.setProcId(procId);
                inserOwn.setProjId(projId);
                inserOwn.setActionId(actionsids);
                inserOwn.setRoleId("owner");
                actionRoleDao.insertRoles(inserOwn);
            }

        }
        return "角色赋权成功";

    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.admin.service.IActionsService#findUserPers(java.lang.String, java.lang.String)   
    */
    public String findUserPers(String id, String projId)
    {
        ActionUser actionsUserDto = new ActionUser();
        actionsUserDto.setActionId(id);
        actionsUserDto.setProjId(projId);

        List<ActionUser> list = actionUserPerDao.findUserAll(actionsUserDto);
        if (list != null && list.size() > 0)
        {
            String result = "";
            for (int i = 0; i < list.size(); i++)
            {
                if (i == list.size() - 1)
                {
                    result += list.get(i).getUserId();
                }
                else
                {
                    result += list.get(i).getUserId() + ",";
                }
            }
            return result;
        }
        return null;
    }

}
