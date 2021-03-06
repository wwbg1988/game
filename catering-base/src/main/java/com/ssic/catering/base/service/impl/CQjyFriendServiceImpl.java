package com.ssic.catering.base.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.ssic.catering.base.dto.QjyCateringUserDto;
import com.ssic.catering.base.service.ICQjyFriendService;
import com.ssic.catering.base.service.IQjyCateringService;
import com.ssic.catering.common.util.AppResponse;
import com.ssic.game.common.dao.DeptDao;
import com.ssic.game.common.dao.DeptUserDao;
import com.ssic.game.common.dao.ImsUserDao;
import com.ssic.game.common.dao.QjyFriendDao;
import com.ssic.game.common.dto.DeptDto;
import com.ssic.game.common.dto.DeptUsersDto;
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.dto.ProjectUsersDto;
import com.ssic.game.common.dto.QjyFriendDto;
import com.ssic.game.common.pageModel.PageHelper;
import com.ssic.game.common.pageModel.Tree;
import com.ssic.game.common.pojo.ImsUsers;
import com.ssic.game.common.service.ProjectServices;
import com.ssic.game.common.util.CateringProjectG;
import com.ssic.util.UUIDGenerator;
@Service
public class CQjyFriendServiceImpl implements ICQjyFriendService{
    @Autowired
	private QjyFriendDao qjyFriendDao;
	@Autowired
	private ImsUserDao  imsUserDao;
	@Autowired
    private DeptDao deptDao;
	@Autowired
	private IQjyCateringService qjyCateringService;
	@Autowired
	private DeptUserDao  deptUserDao;
	@Autowired
	private ProjectServices projectServices;
    

    
	public void insertQjyFriend(QjyFriendDto qjyFriendDto){
		qjyFriendDao.insertQJYF(qjyFriendDto);
	}
	
	@Override
	public List<QjyFriendDto> findQJYF(QjyFriendDto qjyFriendDto) {
		
		return qjyFriendDao.findQJYF(qjyFriendDto);
	}

	@Override
	public void insertQJYF(QjyFriendDto qjyFriendDto) {
		// TODO Auto-generated method stub
		qjyFriendDao.insertQJYF(qjyFriendDto);
	}

	@Override
	public void updateQJY(QjyFriendDto qjyFriendDto) {
		// TODO Auto-generated method stub
		qjyFriendDao.updateQJY(qjyFriendDto);
	}

	@Override
	public void deleteQJYF(QjyFriendDto qjyFriendDto) {
		// TODO Auto-generated method stub
		qjyFriendDao.deleteQJYF(qjyFriendDto);
	}

	@Override
	public List<ImsUsersDto> findUser(ImsUsersDto imsUsersDto ,PageHelper ph) {
		PageHelperDto page = new PageHelperDto();
		page.setOrder(ph.getOrder());
		page.setPage(ph.getPage());
		page.setRows(ph.getRows());
		page.setSort(ph.getSort());
	    return qjyFriendDao.findUser(imsUsersDto,page);
	}

	@Override
	public String findUserPers(String ids) {
		QjyFriendDto qjyFriendDto = new QjyFriendDto();
		qjyFriendDto.setUserId(ids);
		List<QjyFriendDto> list =qjyFriendDao.findQJYF(qjyFriendDto);
		  if (list != null && list.size() > 0)
	        {
	            String result = "";
	            for (int i = 0; i < list.size(); i++)
	            {
	                if (i == list.size() - 1)
	                {
	                    result += list.get(i).getFriendUserId();
	                }
	                else
	                {
	                    result += list.get(i).getFriendUserId() + ",";
	                }
	            }
	            return result;
	        }
		return null;
	}

	@Override
	public List<Tree> userTree(String searchName,String userPerss) {
	       List<Tree> result = new ArrayList<Tree>();
      
	   	//获取项目ID
			String projId="";
			Map<String, Object> mapproj = findProjByUserId(userPerss);
			Boolean projState = (Boolean) mapproj.get("SUCCESS");
			if(projState){
				ProjectUsersDto projectUsersDto = (ProjectUsersDto) mapproj.get("projectUsersDto");
				projId =projectUsersDto.getProjId();
			}
	       //查询出该项目下的所有部门，
	        DeptDto deptDto = new DeptDto();
//	        if(cateringProjId.equals(anObject)){
//	        	
//	        }
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
		        		addDeptTree(searchName,userPerss,dept_1,result);
	        		}else{
	        			Tree tree_2 = new Tree();
	        			tree_2.setId(dept_1.getId());
	        			tree_2.setText(dept_1.getDeptName());
	        			tree_2.setIconCls("server_database");
	        			tree_2.setPid(dept_1.getPid());
	        			result.add(tree_2);
	        			addDeptTree(searchName,userPerss,dept_1,result);
	        		}
	        	}
	        }
	       
	       return result;
	}
	
	
	 //根据userid查询项目id
	 public Map<String, Object> findProjByUserId(String userid){
		ProjectUsersDto projectUsersDto = projectServices.findByUserID(userid);
		 Map<String, Object> map = new HashMap<String, Object>();
		 if(projectUsersDto!=null && projectUsersDto.getProjId()!=null){
			 map.put("SUCCESS", true);
			 map.put("projectUsersDto", projectUsersDto);
		 }else{
			 map.put("SUCCESS", false);
			 map.put("projectUsersDto", projectUsersDto);
		 }
		 return  map;
	 }
	
	public void addDeptTree(String searchName,String userPerss,DeptDto deptDto,List<Tree> result){
		//添加对应部门的用户
		DeptUsersDto deptUsersDto1 = new DeptUsersDto();
		if(searchName!=null && !searchName.equals("")){
			   deptUsersDto1.setUserName("%"+searchName+"%");
		   }
		   deptUsersDto1.setDeptId(deptDto.getId());
	    List<DeptUsersDto> listUser = deptUserDao.findUser(deptUsersDto1);
	    if(listUser!=null && listUser.size()>0){
	    	for(DeptUsersDto deptUser_1 :listUser ){
	    		if(!userPerss.equals(deptUser_1.getUserId())){
	    			Tree treeUser1 = new Tree();
		    		treeUser1.setId(deptUser_1.getUserId());
		    		treeUser1.setText(deptUser_1.getUserName());
		    		treeUser1.setIconCls("status_online");
		    		treeUser1.setPid(deptDto.getId());
		    		result.add(treeUser1);
	    		}
	    	}
	    }
	}
	
	

	@Override
	public void grantUser(String userId, String rolesId) {

		  ImsUsersDto imsud = new ImsUsersDto();
    	  imsud.setId(userId);
    	  List<ImsUsersDto> listud =   qjyFriendDao.findUser(imsud);
    	  String userQJ = "";
    	  if(listud!=null && listud.size()>0){
    		  userQJ=  listud.get(0).getQjyAccount();
    	  }
    	  
    		QjyFriendDto qjyFriendDto = new QjyFriendDto();
    		qjyFriendDto.setUserId(userId);
    		qjyFriendDto.setLastUpdateTime(new Date());
		//亲加云删除好友
		//先查询出该用户所有的好友，在调用亲加云删除所有的好友
    		 List<QjyFriendDto> friend_last = qjyFriendDao.findQJYF(qjyFriendDto);
		    if(friend_last!=null && friend_last.size()>0){
		    	for(int k=0;k<friend_last.size();k++){
		    		QjyFriendDto qjyf_last = friend_last.get(k);
		    		QjyCateringUserDto qjc_last = new QjyCateringUserDto();
		    		qjc_last.setUserAccount(userQJ);
		    		qjc_last.setFriendAccount(qjyf_last.getQjyAccount());
		    		qjyCateringService.deletFriend(qjc_last);
		    	}
		    	
		    	
		    }
	
		qjyFriendDao.deleteByUserid(qjyFriendDto);

	      if (rolesId != null && rolesId.length() > 0) {
	          List<String> roles = new ArrayList<String>();
	          if (userId != null) {
	              for (String roleId :rolesId.split(",")) {
	                  roles.add(roleId);
	              }
	          }

	      for(String rId : roles){
	          ImsUsers param = new ImsUsers();
	          param.setId(rId);
	          List<ImsUsers> userList = imsUserDao.findUserAll(param);
	          if(userList!=null&&userList.size()>0){
	        	  
	        	  ImsUsers imsUsers = userList.get(0);
	        	  
	        	  QjyFriendDto qjyDto = new QjyFriendDto();
	        	  qjyDto.setId(UUIDGenerator.getUUID());
	        	  qjyDto.setUserId(userId);
	        	  qjyDto.setQjyAccount(imsUsers.getQjyAccount());
	        	  qjyDto.setStat(1);
	        	  qjyDto.setCreateTime(new Date());
	        	  qjyDto.setFriendUserId(imsUsers.getId());
	        	  qjyFriendDao.insertQJYF(qjyDto);
	 
	             //亲加云添加好友
	        	
	        	 
	        	  QjyCateringUserDto qJConnectDto =new QjyCateringUserDto();
	        	  qJConnectDto.setUserAccount(userQJ);
	        	  qJConnectDto.setFriendAccount(imsUsers.getQjyAccount());
	        	  qjyCateringService.addFriend(qJConnectDto);
	          }
	          
	      }
	     }		
		
	}

	@Override
	public int findUserCount(ImsUsersDto imsUsersDto) {
		
		return qjyFriendDao.findUserCount(imsUsersDto);
	}

	@Override
	public List<ImsUsersDto> findCaterUser(ImsUsersDto imsUsersDto,
			PageHelper ph) {
		PageHelperDto page = new PageHelperDto();
		page.setOrder(ph.getOrder());
		page.setPage(ph.getPage());
		page.setRows(ph.getRows());
		page.setSort(ph.getSort());
	    return qjyFriendDao.findCaterUser(imsUsersDto,page);
	}

	@Override
	public int findCaterUserCount(ImsUsersDto imsUsersDto) {
		// TODO Auto-generated method stub
		return qjyFriendDao.findCaterUserCount(imsUsersDto);
	}

	
}
