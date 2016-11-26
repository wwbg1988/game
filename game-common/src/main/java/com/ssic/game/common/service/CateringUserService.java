/**
 * 
 */
package com.ssic.game.common.service;

import java.util.List;

import com.ssic.game.common.dto.DeptUsersDto;
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.dto.PageHelperDto;

/**		
 * <p>Title: ImsUserService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author yuanbin	
 * @date 2015年8月7日 下午6:18:46	
 * @version 1.0
 * <p>修改人：yuanbin</p>
 * <p>修改时间：2015年8月7日 下午6:18:46</p>
 * <p>修改备注：</p>
 */
public interface CateringUserService {

    /**     
     * findUser：查询用户信息
     * @param query
     * @return
     * @exception	
     * @author yuanbin
     * @date 2015年8月7日 下午6:16:34	 
     */
    ImsUsersDto findByUserId(String userId);

    List<ImsUsersDto>  findUsers(ImsUsersDto  imsUsersDto);
    
    List<ImsUsersDto>  findUsersAll(ImsUsersDto imsUsersDto,PageHelperDto ph);
    
    void insertUser(ImsUsersDto imsUsersDto);
    
    void updateUser(ImsUsersDto imsUsersDto);
    
    void updateUserByuserId(ImsUsersDto imsUsersDto);
    
    void deleteUser(ImsUsersDto imsUsersDto);
    
    int findCount(ImsUsersDto imsUsersDto);
    
    int vailUserAccount(ImsUsersDto imsUsersDto);
    
    String findUserRole(String ids);
    
    void grant(String ids, ImsUsersDto user);
    
    void upPasswod(ImsUsersDto imsUsersDto);
    
    void deleteproUser(ImsUsersDto imsUsersDto);
    
    void updateDept(DeptUsersDto deptUsersDto);
    
    void deleteDept(DeptUsersDto deptUsersDto);
    
    /**
     * 
     * updateUserSelectiveById：根据id和imsUsersDto中的非空字段修改t_ims_users
     * @param imsUsersDto imsUsersDto必须有id
     * @exception	
     * @author 朱振
     * @date 2015年8月20日 下午1:24:34
     */
    void updateUserSelectiveById(ImsUsersDto imsUsersDto);

	
	/**     
	 * userAccountByUser：一句话描述方法功能
	 * @param name
	 * @return
	 * @exception	
	 * @author yuanbin
	 * @date 2015年8月25日 上午10:11:03	 
	 */
	ImsUsersDto userAccountByUser(String name);

}

