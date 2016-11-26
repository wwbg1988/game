package com.ssic.game.manage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.admin.pageModel.Tree;
import com.ssic.game.common.dao.DeptDao;
import com.ssic.game.common.dao.DeptUserDao;
import com.ssic.game.common.dao.ImsUserDao;
import com.ssic.game.common.dao.ProcessDao;
import com.ssic.game.common.dao.ProcessUsersDao;
import com.ssic.game.common.dao.ProjectUsersDao;
import com.ssic.game.common.dto.DeptDto;
import com.ssic.game.common.dto.DeptUsersDto;
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.dto.ProcessDto;
import com.ssic.game.common.dto.ProcessUsersDto;
import com.ssic.game.common.pojo.ImsUsers;
import com.ssic.game.common.pojo.Process;
import com.ssic.game.manage.service.ITImsProcessService;
import com.ssic.util.BeanUtils;
import com.ssic.util.UUIDGenerator;

@Service
public class TImsProcessServiceImpl implements ITImsProcessService{

	@Autowired
	private ProcessDao processDao;
	@Autowired
	private ProcessUsersDao processUsersDao;
    @Autowired
    private DeptDao deptDao;
    
    @Autowired
    private ImsUserDao imsUserDao;
    
	@Autowired
    private ProjectUsersDao  projectUsersDao;
	@Autowired
	private DeptUserDao  deptUserDao;
    
	
	public List<ProcessDto> findProcess(ProcessDto processDto) {
	 
	
		List<ProcessDto> list_p = processDao.findProcess(processDto);
	
		if(list_p!=null && list_p.size()>0){
			for(int i=0;i<list_p.size();i++){
				ProcessDto pdto_1 = list_p.get(i);
				ProcessUsersDto pudto = new ProcessUsersDto();
			    	pudto.setProcId(pdto_1.getId());
			    	pudto.setStat(1);
			    	String procUsers = "";
				 List<ProcessUsersDto> listpu=   processUsersDao.findById(pudto);
				 if(listpu != null && listpu.size()>0){
					 for(int j=0;j<listpu.size();j++){
					    	ProcessUsersDto pu_2 = listpu.get(j);
					    	procUsers = procUsers + pu_2.getUserName() + ",";
					    }
				 }  
				 pdto_1.setUserName(procUsers);

			}
			
		}
		
		return list_p;
	}
	
	
	public List<ProcessDto> findProcessALL(ProcessDto processDto,PageHelper ph){

		 PageHelperDto phdto = new PageHelperDto();
		 phdto.setOrder(ph.getOrder());
		 phdto.setPage(ph.getPage());
		 phdto.setRows(ph.getRows());
		 phdto.setSort(ph.getSort());
		 phdto.setBeginRow((ph.getPage()-1)*ph.getRows());
		
		List<ProcessDto> list_p = processDao.findProcessALL(processDto, phdto);
	
		if(list_p!=null && list_p.size()>0){
			for(int i=0;i<list_p.size();i++){
				ProcessDto pdto_1 = list_p.get(i);
				ProcessUsersDto pudto = new ProcessUsersDto();
			    	pudto.setProcId(pdto_1.getId());
			    	pudto.setStat(1);
			    	String procUsers = "";
				 List<ProcessUsersDto> listpu=   processUsersDao.findById(pudto);
				 if(listpu != null && listpu.size()>0){
					 for(int j=0;j<listpu.size();j++){
					    	ProcessUsersDto pu_2 = listpu.get(j);
					    	procUsers = procUsers + pu_2.getUserName() + ",";
					    }
					 procUsers = procUsers.substring(0, procUsers.length()-1);
				 }  
				 pdto_1.setUserName(procUsers);

			}
			
		}
		
		return list_p;
	}
	

	public void insertPro(ProcessDto processDto) {
		Process process = new Process();
		BeanUtils.copyProperties(processDto, process);
		processDao.insertPro(process);
	}

	public void updatePro(ProcessDto processDto) {
		Process process =new Process();
		BeanUtils.copyProperties(processDto, process);
		processDao.updatePro(process);
	}

	public void deletePro(ProcessDto processDto) {
		Process process = new Process();
		BeanUtils.copyProperties(processDto, process);
		processDao.deletePro(process);
	}

	public List<ProcessDto> findInst(ProcessDto processDto) {
		Process process = new Process();
		List<ProcessDto> result = new ArrayList<ProcessDto>();
		BeanUtils.copyProperties(processDto, process);
		List<Process> listp =  processDao.findInst(process);
		 if(listp!=null && listp.size()>0){
			 result = BeanUtils.createBeanListByTarget(listp, ProcessDto.class);
		 }
		 return result;
	}

	public String findUserPers(String ids) {
		
		
		ProcessUsersDto processUsersDto = new ProcessUsersDto();
		processUsersDto.setProcId(ids);
		 List<ProcessUsersDto> list=  processUsersDao.findById(processUsersDto);
		
		
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

	public List<Tree> userTree(String searchName,String projId) {

		List<Tree> result = new ArrayList<Tree>();
	 
	       //查询出该项目下的所有部门，
	        DeptDto deptDto = new DeptDto();
	        deptDto.setProjId(projId);
	        List<DeptDto> listDept =     deptDao.findAll(deptDto);
	       
	        //最顶级的部门list
	        List<DeptDto> deptList_1 = new ArrayList<DeptDto>();
	        //查询出pid为空的部门，为最顶级的部门，然后根据pid查出整个部门树
	        if(listDept!=null && listDept.size()>0){
	        	for(DeptDto dept_1:listDept){
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
	    
	        
	        
//	       if(listDept!=null && listDept.size()>0){
//	    	   for(DeptDto duDto:listDept){
//	    		   Tree tree = new Tree();
//	    		   tree.setId(duDto.getId());
//	    		   tree.setText(duDto.getDeptName());
//	    		   tree.setIconCls("server_database");
//	    		   DeptUsersDto deptUsersDto2 = new DeptUsersDto();
//	    		   deptUsersDto2.setDeptId(duDto.getId());
//	    		   if(searchName!=null && !searchName.equals("")){
//	    			   deptUsersDto2.setUserName("%"+searchName+"%");
//	    		   }
//	    		   deptUsersDto2.setProjId(projId);
//	    		   List<DeptUsersDto> listUser = deptUserDao.findUser(deptUsersDto2);
//	    		   if(listUser!=null && listUser.size()>0){
//	    			       result.add(tree);
//	    			   for(DeptUsersDto duDto2:listUser){
//	    				   Tree tree2 = new Tree();
//	    				   tree2.setId(duDto2.getUserId());
//	    				   tree2.setText(duDto2.getUserName());
//	    				   tree2.setPid(duDto.getId());
//	    				   tree2.setIconCls("status_online");
//	    				   result.add(tree2);
//	    			   }
//	    		   }
//	    		   
//	    	   }
//	       }
	       return result;
	}

	
	public void addDeptTree(String searchName,String projId,DeptDto deptDto,List<Tree> result){
		//添加对应部门的用户
		DeptUsersDto deptUsersDto1 = new DeptUsersDto();
		if(searchName!=null && !searchName.equals("")){
			   deptUsersDto1.setUserName("%"+searchName+"%");
		   }
		   deptUsersDto1.setProjId(projId);
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
	
	public void grantUser(String procid, String rolesId) {

		    ProcessUsersDto processUsersDto = new ProcessUsersDto();
		    processUsersDto.setProcId(procid);
		    processUsersDto.setLastUpdateTime(new Date());
		    processUsersDao.deleteByProId(processUsersDto);
		    
		     
		      if (rolesId != null && rolesId.length() > 0) {
		          List<String> roles = new ArrayList<String>();
		          if (procid != null) {
		              for (String roleId :rolesId.split(",")) {
		                  roles.add(roleId);
		              }
		          }

		      for(String rId : roles){
		    	  //如果这个ID是用户，则插入process_user
		    	  //如果是部门ID，则不插入
		    	  ImsUsersDto imsUsersDto = new ImsUsersDto();
	              imsUsersDto.setId(rId);
	              List<ImsUsersDto> listuser= imsUserDao.findUsers(imsUsersDto);
	                if(listuser!=null && listuser.size()>0){
	               	  ProcessUsersDto prodto = new ProcessUsersDto();
		        	  prodto.setId(UUIDGenerator.getUUID());
		        	  prodto.setProcId(procid);
		        	  prodto.setUserId(rId);
		        	  prodto.setStat(1);
		        	  prodto.setCreateTime(new Date());
		        	  processUsersDao.insertProcU(prodto);
	                }
	              
		       
		         // }
		      }
		     }		
	}

	@Override
	public void insertImageUrl(ProcessDto processDto) {
		processDao.insertImageUrl(processDto);
	}

	@Override
	public void updateStarTask(ProcessDto processDto) {
		processDao.updateStarTask(processDto);
	}
	
	public int findCount(ProcessDto processDto){
		return  processDao.findCount(processDto);
	}
	
}
