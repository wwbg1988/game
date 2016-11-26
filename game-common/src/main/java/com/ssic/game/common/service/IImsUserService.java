/**
 * 
 */
package com.ssic.game.common.service;

import java.util.List;

import com.ssic.game.common.dto.DeptUsersDto;
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.pojo.ImsUsers;

/**		
 * <p>Title: ImsUserService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author rkzhang	
 * @date 2015年6月30日 下午1:18:46	
 * @version 1.0
 * <p>修改人：rkzhang</p>
 * <p>修改时间：2015年6月30日 下午1:18:46</p>
 * <p>修改备注：</p>
 */
public interface IImsUserService {

    
    /**     
     * findUser：查询用户信息
     * @param query
     * @return
     * @exception	
     * @author rkzhang
     * @date 2015年6月30日 下午1:52:34	 
     */
    ImsUsersDto findByUserId(String userId);

    List<ImsUsersDto>  findUsers(ImsUsersDto  imsUsersDto);
    
    List<ImsUsersDto>  findUsersAll(ImsUsersDto imsUsersDto,PageHelperDto ph);
    
    void insertUser(ImsUsersDto imsUsersDto);
    
    void updateUser(ImsUsersDto imsUsersDto);
    
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
     * findImsUsersToWeixin：通过微信用户唯一标识获取用户信息
     * @param openId 微信唯一标识
     * @return
     * @exception	
     * @author 张亚伟
     * @date 2015年8月13日 上午11:41:20
     */
    public ImsUsers findImsUsersToWeixin(String openId);

    /**
     * findImsUserByUserAccount：通过账号获取用户信息
     * @param userAccount
     * @return
     * @exception	
     * @author 张亚伟
     * @date 2015年8月13日 下午1:50:30
     */
	ImsUsers findImsUserByUserAccount(String userAccount);

	
	/**
	 * 
	 * updateImsUsers：更新用户信息
	 * @param imsUsers
	 * @return
	 * @exception	
	 * @author 张亚伟
	 * @date 2015年8月13日 下午2:33:00
	 */
	Integer updateImsUsers(ImsUsers imsUsers);
	
	
	/**
	 * 更新用户信息	 
	 * @author 朱振	
	 * @date 2015年10月29日 下午2:43:31	
	 * @version 1.0
	 * @param imsUsers
	 * @return
	 * <p>修改人：朱振</p>
	 * <p>修改时间：2015年10月29日 下午2:43:31</p>
	 * <p>修改备注：</p>
	 */
    Integer updateImsUsersBy(ImsUsersDto imsUsers);
    
    /**
     * 查询	 
     * @author 朱振	
     * @date 2015年10月29日 下午2:45:48	
     * @version 1.0
     * @param imsUsers
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年10月29日 下午2:45:48</p>
     * <p>修改备注：</p>
     */
    List<ImsUsersDto> findBy(ImsUsersDto imsUsers);
    
    List<ImsUsersDto> findByProjId(String projId);
    
    void createUser(ImsUsersDto imsUsers);  //创建用户
    
    void delAllUser(ImsUsersDto imsUsers);  //删除用户
    
    List<ImsUsersDto> findBySearchName(ImsUsersDto imsUsers);
}

