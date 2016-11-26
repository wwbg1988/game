/**
 * 
 */
package com.ssic.game.common.service;

import java.util.List;
import java.util.Map;

import com.ssic.game.common.dto.DeptUsersDto;
import com.ssic.game.common.dto.ImsUsersDto;

/**		
 * <p>Title: DeptUserService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年7月9日 下午7:51:34	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年7月9日 下午7:51:34</p> 
 * <p>修改备注：</p>
 */
public interface DeptUserService
{
    List<DeptUsersDto> findAllBy(DeptUsersDto deptUserDto);
    List<Map<Object,Object>>findAllBySearch(String searchName,String projId);
    List<DeptUsersDto> findAllNotExistUsersIds(String projId,List<String> usersIds);
    void insert(DeptUsersDto deptUserDto);
    
    List<DeptUsersDto> findDeptUser(DeptUsersDto deptUserDto);
    
    void insertDeptUser(DeptUsersDto deptUserDto);
    
    void deleteDeptUser(DeptUsersDto deptUserDto);
    
    List<ImsUsersDto> searchUser(String deptId,String userName);
}

