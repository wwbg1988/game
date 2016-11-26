package com.ssic.game.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.admin.pageModel.Role;
import com.ssic.game.admin.pageModel.Tree;
import com.ssic.game.admin.service.ProjectService;
import com.ssic.game.common.dao.DeptDao;
import com.ssic.game.common.dao.DeptUserDao;
import com.ssic.game.common.dao.ImsUserDao;
import com.ssic.game.common.dao.ProjectDao;
import com.ssic.game.common.dao.ProjectUsersDao;
import com.ssic.game.common.dto.DeptDto;
import com.ssic.game.common.dto.DeptUsersDto;
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.game.common.dto.ProjectUsersDto;
import com.ssic.util.UUIDGenerator;

@Service
public class ProjectServiceImpl implements ProjectService
{

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private ProjectUsersDao projectUsersDao;

    @Autowired
    private DeptDao deptDao;

    @Autowired
    private ImsUserDao imsUserDao;
    @Autowired
    private DeptUserDao deptUserDao;
    

    public List<ProjectDto> findAll()
    {
        return projectDao.findAll();
    }

    public ProjectDto findById(String id)
    {
        return projectDao.findById(id);
    }

    public List<ProjectDto> findByIdName(ProjectDto projectDto)
    {
        List<ProjectDto> list = projectDao.findByIdName(projectDto);

        if (list != null && list.size() > 0)
        {
            for (int i = 0; i < list.size(); i++)
            {
                ProjectDto pdato_1 = list.get(i);
                ProjectUsersDto projectUsersDto = new ProjectUsersDto();
                projectUsersDto.setProjId(pdato_1.getId());
                projectUsersDto.setStat(1);
                List<ProjectUsersDto> listpu = projectUsersDao.findAll(projectUsersDto);
                String usersName = "";
                if (listpu != null && listpu.size() > 0)
                {
                    for (int j = 0; j < listpu.size(); j++)
                    {
                        ProjectUsersDto pud_1 = listpu.get(j);
                        String uName = pud_1.getUserName();
                        usersName = usersName + uName + ",";
                    }
                    pdato_1.setUserNames(usersName);
                }

            }
        }

        return list;
    }

    @CacheEvict(value="default", key = "'game.common.dto.ProjectDto:' + #projectDto.getId()", beforeInvocation=true)
    public void insert(ProjectDto projectDto)
    {
        projectDao.insert(projectDto);
    }
    
    @CacheEvict(value="default", key = "'game.common.dto.ProjectDto:' + #projectDto.getId()", beforeInvocation=true)
    public void update(ProjectDto projectDto)
    {
        projectDao.update(projectDto);
    }
    
    @CacheEvict(value="default", key = "'game.common.dto.ProjectDto:' + #projectDto.getId()", beforeInvocation=true)
    public void deleteById(ProjectDto projectDto)
    {
        projectDao.deleteById(projectDto);
    }

    public String findUserPers(String id)
    {

        ProjectUsersDto projectUsersDto = new ProjectUsersDto();
        projectUsersDto.setProjId(id);
        List<ProjectUsersDto> list = projectUsersDao.findAll(projectUsersDto);

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

    public List<Tree> userTree(String searchName, String projId)
    {
        List<Tree> result = new ArrayList<Tree>();

        // ProjectUsersDto projectUsersDto = new ProjectUsersDto();
        // projectUsersDto.setProjId(projId);
        //查询该项目下的所有部门
        //List<ProjectUsersDto> puList=  projectUsersDao.findDept(projectUsersDto);
         
        //查询出该项目下的所有部门，
        DeptDto deptDto = new DeptDto();
        deptDto.setProjId(projId);
//        if(searchName!=null && !searchName.equals("")){
//        	deptDto.setDeptName("%"+searchName+"%");
//        }
        List<DeptDto> list =     deptDao.findAll(deptDto);
       
        if(list!=null && list.size()>0){
        	for(DeptDto dept_1:list){
        		String pid_1 = dept_1.getPid();
        		if(pid_1==null){
        			Tree tree_1 = new Tree();
	        		tree_1.setId(dept_1.getId());
	        		tree_1.setText(dept_1.getDeptName());
	        		tree_1.setIconCls("server_database");
	        		result.add(tree_1);
	        		
	        		addDeptTree(searchName,projId,dept_1,result);
		    	    
        		}else{
        			Tree tree_2 = new Tree();
        			tree_2.setId(dept_1.getId());
        			tree_2.setText(dept_1.getDeptName());
        			tree_2.setIconCls("server_database");
        			tree_2.setPid(dept_1.getPid());
        			result.add(tree_2);
        			
        			addDeptTree(searchName,projId,dept_1,result);
        		}
        	}
        }
        
//        if (list != null && list.size() > 0)
//        {
//            for (DeptDto dDto : list)
//            {
//                Tree tree = new Tree();
//                tree.setId(dDto.getId());
//                tree.setText(dDto.getDeptName());
//                tree.setIconCls("server_database");
//                DeptUsersDto deptUsersDto = new DeptUsersDto();
//                deptUsersDto.setDeptId(dDto.getId());
//                if(searchName!=null && !searchName.equals("")){
//                	deptUsersDto.setUserName("%"+searchName+"%");
//                }
//                
//                 List<DeptUsersDto> listdp =   deptUserDao.findUser(deptUsersDto);
//                 if(listdp!=null && listdp.size()>0){
//                	 result.add(tree);
//                	 for(DeptUsersDto deptUsersDto2:listdp){
//                		 Tree tree2 = new Tree();
//                		 tree2.setId(deptUsersDto2.getUserId());
//                		 tree2.setText(deptUsersDto2.getUserName());
//                		 tree2.setPid(dDto.getId());
//                		 tree2.setIconCls("status_online");
//                		 result.add(tree2);
//                	 }
//                 }
//                
//               
//            }
//        }

        return result;
    }
    
	public void addDeptTree(String searchName,String projId,DeptDto deptDto,List<Tree> result){
		//添加对应部门的用户
		DeptUsersDto deptUsersDto1 = new DeptUsersDto();
		if(searchName!=null && !searchName.equals("")){
			   deptUsersDto1.setUserName("%"+searchName+"%");
		   }
		  // deptUsersDto1.setProjId(projId);
		   deptUsersDto1.setDeptId(deptDto.getId());
	    List<DeptUsersDto> listUser = deptUserDao.findUser(deptUsersDto1);
	    if(listUser!=null && listUser.size()>0){
	    	for(DeptUsersDto deptUser_1 :listUser ){
	    		Tree treeUser1 = new Tree();
	    		treeUser1.setId(deptUser_1.getUserId());
	    		treeUser1.setText(deptUser_1.getUserName());
	    		treeUser1.setIconCls("status_online");
	    		treeUser1.setPid(deptDto.getId());
	    		result.add(treeUser1);
	    	}
	    }
	}

    public void grantUser(String projid, String rolesId)
    {

        ProjectUsersDto projectUsersDto = new ProjectUsersDto();
        projectUsersDto.setProjId(projid);
        projectUsersDto.setLastUpdateTime(new Date());
        projectUsersDao.deleteByProjid(projectUsersDto);

        if (rolesId != null && rolesId.length() > 0)
        {
            List<String> roles = new ArrayList<String>();
            if (projid != null)
            {
                for (String roleId : rolesId.split(","))
                {
                    roles.add(roleId);
                }
            }

            for (String rId : roles)
            {
                //如果这个ID是用户ID则插入project_user
            	//如果这个ID是部门ID则不插入
            	ImsUsersDto imsUsersDto = new ImsUsersDto();
            	imsUsersDto.setId(rId);
            	List<ImsUsersDto> listuser= imsUserDao.findUsers(imsUsersDto);
            	if(listuser!=null && listuser.size()>0){
                    ProjectUsersDto pudto = new ProjectUsersDto();
                    pudto.setProjId(projid);
                    pudto.setId(UUIDGenerator.getUUID());
                    pudto.setUserId(rId);
                    pudto.setStat(1);
                    pudto.setCreateTime(new Date());
                    projectUsersDao.insertPUser(pudto);
            	}
            }
        }
    }

    @Override
    public List<Role> treeGrid(ProjectDto projectDto)
    {
        List<Role> listRole_1 = new ArrayList<Role>();
        List<Role> listRole_2 = new ArrayList<Role>();

        List<ProjectDto> list = projectDao.findByIdName(projectDto);

        if (list != null && list.size() > 0)
        {
            for (int i = 0; i < list.size(); i++)
            {
                ProjectDto pdato_1 = list.get(i);
                Role role_1 = new Role();
                role_1.setId(pdato_1.getId());
                role_1.setPname(null);
                role_1.setName(pdato_1.getProjName());
                role_1.setRemark(pdato_1.getDescribes());
                role_1.setPid(null);
                listRole_1.add(role_1);
            }
        }

        if (list != null && list.size() > 0)
        {
            for (int j = 0; j < list.size(); j++)
            {
                ProjectDto pdato_2 = list.get(j);
                ProjectUsersDto projectUsersDto = new ProjectUsersDto();
                projectUsersDto.setProjId(pdato_2.getId());
                projectUsersDto.setStat(1);
                List<ProjectUsersDto> listpu = projectUsersDao.findAll(projectUsersDto);
                for (int k = 0; k < listpu.size(); k++)
                {
                    Role role_2 = new Role();
                    ProjectUsersDto p_2 = listpu.get(k);
                    role_2.setId(p_2.getId());
                    role_2.setName(p_2.getUserName());
                    role_2.setPid(pdato_2.getId());
                    role_2.setPname(pdato_2.getProjName());
                    listRole_1.add(role_2);
                }
            }

        }

        return listRole_1;
    }

    @Override
    public List<ProjectDto> findByIdNameAll(ProjectDto projectDto, PageHelper ph)
    {
        PageHelperDto phdto = new PageHelperDto();
        phdto.setOrder(ph.getOrder());
        phdto.setPage(ph.getPage());
        phdto.setRows(ph.getRows());
        phdto.setSort(ph.getSort());
        phdto.setBeginRow((ph.getPage() - 1) * ph.getRows());
        List<ProjectDto> list = projectDao.findByIdNameAll(projectDto, phdto);

        if (list != null && list.size() > 0)
        {
            for (int i = 0; i < list.size(); i++)
            {
                ProjectDto pdato_1 = list.get(i);
                ProjectUsersDto projectUsersDto = new ProjectUsersDto();
                projectUsersDto.setProjId(pdato_1.getId());
                projectUsersDto.setStat(1);
                List<ProjectUsersDto> listpu = projectUsersDao.findAll(projectUsersDto);
                String usersName = "";
                if (listpu != null && listpu.size() > 0)
                {
                    for (int j = 0; j < listpu.size(); j++)
                    {
                        ProjectUsersDto pud_1 = listpu.get(j);
                        String uName = pud_1.getUserName();
                        usersName = usersName + uName + ",";
                    }
                    pdato_1.setUserNames(usersName.substring(0,usersName.length()-1));
                }

            }
        }
        return list;
    }

    public int findCount(ProjectDto projectDto)
    {
        return projectDao.findCount(projectDto);
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.admin.service.ProjectService#allTree()   
    */
    @Override
    public List<Tree> allTree()
    {
        List<Tree> lt = new ArrayList<Tree>();
        List<ProjectDto> list_project = new ArrayList<ProjectDto>();
        list_project = projectDao.findAll();
        //组装数据tree模型
        if (list_project != null && list_project.size() > 0)
        {
            for (ProjectDto projectDto : list_project)
            {
                Tree tree = new Tree();
                BeanUtils.copyProperties(projectDto, tree);
                tree.setText(projectDto.getProjName());
                tree.setIconCls("bin");
                lt.add(tree);
            }
        }
        return lt;
    }

}
