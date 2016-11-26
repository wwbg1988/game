package com.ssic.catering.base.service;

import java.util.List;

import org.springframework.expression.AccessException;

import com.ssic.catering.base.dto.AdminUsersDto;
import com.ssic.catering.base.dto.AdminUsersRoleDto;
import com.ssic.game.common.dto.PageHelperDto;

/**
 * 		
 * <p>Title: IAdminUsersService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author zhuzhen	
 * @date 2015年12月2日 上午9:32:08	
 * @version 1.0
 * <p>修改人：zhuzhen</p>
 * <p>修改时间：2015年12月2日 上午9:32:08</p>
 * <p>修改备注：</p>
 */
public interface IAdminUsersService
{
    /**
     * 
     * addSupplier：一句话描述方法功能
     * @param newRecord
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年12月2日 上午9:38:34
     */
    int addSupplier(AdminUsersDto newRecord);
    
    
    /**
     * 
     * findAdminUsersDtosBy：一句话描述方法功能
     * @param param
     * @param ph
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年12月2日 下午4:40:39
     */
    List<AdminUsersDto> findAdminUsersDtosBy(AdminUsersRoleDto param, PageHelperDto ph);
    
  
    /**
     * 
     * findAdminUsersDtoCountBy：一句话描述方法功能
     * @param param
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年12月2日 下午4:40:42
     */
    public long findAdminUsersDtoCountBy(AdminUsersRoleDto param);
    
    /**
     * 
     * findAdminUsersDtoByUserAccount：一句话描述方法功能
     * @param userAccount
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年12月3日 上午10:18:53
     */
    public AdminUsersDto findAdminUsersDtoByUserAccount(String userAccount) throws AccessException;
    
   /**
    * 
    * updateAdminUsers：一句话描述方法功能
    * @param adminUsersDto
    * @return
    * @exception	
    * @author zhuzhen
    * @date 2015年12月3日 上午10:42:56
    */
    public int updateAdminUsers(AdminUsersDto adminUsersDto);
}

