package com.ssic.catering.base.service;

import java.util.List;

import com.ssic.catering.lbs.dto.SupplierAdminUsersDto;

/**
 * 		
 * <p>Title: ISupplierAdminUsersService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author zhuzhen	
 * @date 2015年12月4日 下午1:49:19	
 * @version 1.0
 * <p>修改人：zhuzhen</p>
 * <p>修改时间：2015年12月4日 下午1:49:19</p>
 * <p>修改备注：</p>
 */
public interface ISupplierAdminUsersService
{
    /**
     * 
     * findSupplierAdminUsersBy：一句话描述方法功能
     * @param supplierAdminUsersDto
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年12月4日 下午1:50:39
     */
    public List<SupplierAdminUsersDto> findSupplierAdminUsersBy(SupplierAdminUsersDto supplierAdminUsersDto);
    
    /**
     * 
     * addSupplierAdminUsers：一句话描述方法功能
     * @param supplierAdminUsersDto
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年12月4日 下午1:51:05
     */
    public int addSupplierAdminUsers(SupplierAdminUsersDto supplierAdminUsersDto);
    
    /**
     * 
     * deleteSupplierById：真正的删除
     * @param id
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年12月4日 下午2:24:45
     */
    public int deleteSupplierById(String id);
    
    /**
     * 
     * bindingSuppliers：一句话描述方法功能
     * @param supplierAdminUsersDto
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年12月4日 下午2:27:31
     */
    public int bindingSuppliers(SupplierAdminUsersDto supplierAdminUsersDto);
    
    /**
     * 
     * deleteSuppliersBy：一句话描述方法功能
     * @param supplierAdminUsersDto
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年12月4日 下午2:36:32
     */
    public int deleteSuppliersBy(SupplierAdminUsersDto supplierAdminUsersDto);
}

