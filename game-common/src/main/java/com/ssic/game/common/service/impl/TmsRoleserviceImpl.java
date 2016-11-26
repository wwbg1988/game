package com.ssic.game.common.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.game.common.dao.DeptDao;
import com.ssic.game.common.dao.DeptUserDao;
import com.ssic.game.common.dao.ImsUserDao;
import com.ssic.game.common.dao.TimsRoleDao;
import com.ssic.game.common.dto.DeptDto;
import com.ssic.game.common.dto.DeptUsersDto;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.game.common.dto.RoleUsersDto;
import com.ssic.game.common.dto.TmsRoleDto;
import com.ssic.game.common.pageModel.PageHelper;
import com.ssic.game.common.pageModel.Tree;
import com.ssic.game.common.pojo.ImsUsers;
import com.ssic.game.common.service.ITmsRoleservice;
import com.ssic.game.common.util.CateringProjectG;
import com.ssic.util.UUIDGenerator;

@Service
public class TmsRoleserviceImpl implements ITmsRoleservice
{
    @Autowired
    private TimsRoleDao timsRoleDao;

    @Autowired
    private DeptDao deptDao;

    @Autowired
    private ImsUserDao imsUserDao;

    @Autowired
    private DeptUserDao deptUserDao;

    public List<TmsRoleDto> findBy(TmsRoleDto tmsRoleDto)
    {

        return timsRoleDao.findBy(tmsRoleDto);
    }

    public List<TmsRoleDto> findByAll(TmsRoleDto tmsRoleDto, PageHelper ph)
    {
        PageHelperDto phdto = new PageHelperDto();
        phdto.setOrder(ph.getOrder());
        phdto.setPage(ph.getPage());
        phdto.setRows(ph.getRows());
        phdto.setSort(ph.getSort());
        phdto.setBeginRow((ph.getPage() - 1) * ph.getRows());
        return timsRoleDao.findByAll(tmsRoleDto, phdto);
    }

    public void insertRole(TmsRoleDto tmsRoleDto)
    {
        timsRoleDao.insertRole(tmsRoleDto);
    }

    public void updateRole(TmsRoleDto tmsRoleDto)
    {
        timsRoleDao.updateRole(tmsRoleDto);
    }

    public void deleteRole(TmsRoleDto tmsRoleDto)
    {
        timsRoleDao.deleteRole(tmsRoleDto);
    }

    public String findUserPers(String ids)
    {

        RoleUsersDto roleUsersDto = new RoleUsersDto();
        roleUsersDto.setRoleId(ids);
        List<RoleUsersDto> list = timsRoleDao.findRoleUser(roleUsersDto);
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

    public List<Tree> userTree(String searchName)
    {

        List<Tree> result = new ArrayList<Tree>();
        DeptDto deptDto1 = new DeptDto();
        List<DeptDto> deptList = deptDao.findAll(deptDto1);

        //最顶级的部门list
        List<DeptDto> deptList_1 = new ArrayList<DeptDto>();
        //查询出pid为空的部门，为最顶级的部门，然后根据pid查出整个部门树
        if (deptList != null && deptList.size() > 0)
        {
            for (DeptDto dept_1 : deptList)
            {
                String pid_1 = dept_1.getPid();
                if (pid_1 == null)
                {
                    Tree tree_1 = new Tree();
                    tree_1.setId(dept_1.getId());
                    tree_1.setText(dept_1.getDeptName());
                    tree_1.setIconCls("server_database");
                    result.add(tree_1);

                    addDeptTree(searchName, dept_1, result);

                }
                else
                {
                    Tree tree_2 = new Tree();
                    tree_2.setId(dept_1.getId());
                    tree_2.setText(dept_1.getDeptName());
                    tree_2.setIconCls("server_database");
                    tree_2.setPid(dept_1.getPid());
                    result.add(tree_2);

                    addDeptTree(searchName, dept_1, result);
                }
            }
        }

        //	       if(deptList !=null&&deptList.size()>0){
        //	           for(DeptDto deptDto : deptList){
        //	               
        //	               //根据部门查询出部门下的用户
        //	               DeptUsersDto deptUsersDto2 = new DeptUsersDto();
        //	    		   deptUsersDto2.setDeptId(deptDto.getId());
        //	    		   if(searchName!=null && !searchName.equals("")){
        //	    			   deptUsersDto2.setUserName("%"+searchName+"%");
        //	    		   }
        //	               List<DeptUsersDto> imsList = deptUserDao.findUser(deptUsersDto2);
        //	            //   List<ImsUsers> imsList = imsUserDao.findUserAll(param);
        //	               if(imsList!=null&&imsList.size()>0){
        //	                   Tree tree = new Tree();
        //	                   tree.setId(deptDto.getId());
        //	                   tree.setText(deptDto.getDeptName());
        //	                   tree.setIconCls("server_database");
        //	                   result.add(tree);
        //	                   for(DeptUsersDto deptu2 : imsList){
        //	                       Tree trees = new Tree();
        //	                       trees.setId(deptu2.getUserId());
        //	                       trees.setText(deptu2.getUserName());
        //	                       trees.setPid(deptDto.getId());
        //	                       trees.setIconCls("status_online");
        //	                       result.add(trees);
        //	                   }
        //	               }
        //	           }
        //	       }

        return result;

    }

    public List<Tree> cateringUserTree(String searchName, List<ProjectDto> pjds)
    {
        String projects = "";
        int i = 0;
        for (ProjectDto projDto : pjds)
        {
            if (i > 0)
            {
                projects += ",";
            }
            projects += projDto.getId();
            i++;
        }
        List<Tree> result = new ArrayList<Tree>();
        DeptDto deptDto1 = new DeptDto();
        deptDto1.setProjIds(projects);
        List<DeptDto> deptList = deptDao.findAll(deptDto1);
        //最顶级的部门list
        List<DeptDto> deptList_1 = new ArrayList<DeptDto>();
        //查询出pid为空的部门，为最顶级的部门，然后根据pid查出整个部门树
        if (deptList != null && deptList.size() > 0)
        {
            for (DeptDto dept_1 : deptList)
            {
                String pid_1 = dept_1.getPid();
                if (pid_1 == null)
                {
                    Tree tree_1 = new Tree();
                    tree_1.setId(dept_1.getId());
                    tree_1.setText(dept_1.getDeptName());
                    tree_1.setIconCls("server_database");
                    result.add(tree_1);

                    addDeptTree(searchName, dept_1, result);

                }
                else
                {
                    Tree tree_2 = new Tree();
                    tree_2.setId(dept_1.getId());
                    tree_2.setText(dept_1.getDeptName());
                    tree_2.setIconCls("server_database");
                    tree_2.setPid(dept_1.getPid());
                    result.add(tree_2);

                    addDeptTree(searchName, dept_1, result);
                }
            }
        }
        return result;
    }

    public void addDeptTree(String searchName, DeptDto deptDto, List<Tree> result)
    {
        //添加对应部门的用户
        DeptUsersDto deptUsersDto1 = new DeptUsersDto();
        if (searchName != null && !searchName.equals(""))
        {
            deptUsersDto1.setUserName("%" + searchName + "%");
        }
        deptUsersDto1.setDeptId(deptDto.getId());
        List<DeptUsersDto> listUser = deptUserDao.findUser(deptUsersDto1);
        if (listUser != null && listUser.size() > 0)
        {
            for (DeptUsersDto deptUser_1 : listUser)
            {
                Tree treeUser1 = new Tree();
                treeUser1.setId(deptUser_1.getUserId());
                treeUser1.setText(deptUser_1.getUserName());
                treeUser1.setIconCls("status_online");
                treeUser1.setPid(deptDto.getId());
                result.add(treeUser1);
            }
        }
    }

    public void grantUser(String roleId, String rolesId)
    {
        RoleUsersDto roleUsersDto = new RoleUsersDto();
        roleUsersDto.setRoleId(roleId);
        roleUsersDto.setLastUpdateTime(new Date());
        timsRoleDao.delRoleUseById(roleUsersDto);
        if (rolesId != null && rolesId.length() > 0)
        {
            List<String> roles = new ArrayList<String>();
            if (roleId != null)
            {
                for (String roleIds : rolesId.split(","))
                {
                    roles.add(roleIds);
                }
            }

            for (String rId : roles)
            {
                ImsUsers param = new ImsUsers();
                param.setId(rId);
                List<ImsUsers> userList = imsUserDao.findUserAll(param);
                if (userList != null && userList.size() > 0)
                {
                    RoleUsersDto ruDto = new RoleUsersDto();
                    ruDto.setRoleId(roleId);
                    ruDto.setId(UUIDGenerator.getUUID());
                    ruDto.setUserId(rId);
                    ruDto.setStat(1);
                    ruDto.setCreateTime(new Date());
                    timsRoleDao.insertRoleUser(ruDto);

                }

            }
        }

    }

    public int findCount(TmsRoleDto tmsRoleDto)
    {
        return timsRoleDao.findCount(tmsRoleDto);
    }

    @Override
    public List<Tree> tree(String userId)
    {

        List<Tree> lt = new ArrayList<Tree>();

        //查询出所有角色树
        // List<TmsRoleDto> listr1=  timsRoleDao.findTreeRoles(userId);
        TmsRoleDto tmsRoleDto = new TmsRoleDto();
        List<TmsRoleDto> listr1 = timsRoleDao.findBy(tmsRoleDto);
        if (listr1 != null && listr1.size() > 0)
        {
            for (TmsRoleDto roleDto : listr1)
            {
                Tree tree = new Tree();
                tree.setId(roleDto.getId());
                tree.setText(roleDto.getName());
                tree.setPid("1");
                lt.add(tree);
            }
        }

        return lt;
    }

    @Override
    public List<RoleUsersDto> findRoleUser(RoleUsersDto roleUsersDto)
    {
        // TODO Auto-generated method stub
        return timsRoleDao.findRoleUser(roleUsersDto);
    }

    @Override
    public List<RoleUsersDto> findUserNames(RoleUsersDto roleUsersDto)
    {
        // TODO Auto-generated method stub
        return timsRoleDao.findUserNames(roleUsersDto);
    }

    @Override
    public TmsRoleDto findById(String id)
    {
        TmsRoleDto tmsRoleDto = new TmsRoleDto();
        tmsRoleDto.setId(id);

        return timsRoleDao.findBy(tmsRoleDto).get(0);
    }

    @Override
    public void insertRoleUser(RoleUsersDto ruDto)
    {
        // TODO Auto-generated method stub
        timsRoleDao.insertRoleUser(ruDto);
    }

}
