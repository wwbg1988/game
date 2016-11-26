package com.ssic.catering.admin.service;

import java.util.List;

import com.ssic.catering.lbs.dto.SupplierAdminUsersDto;
import com.ssic.catering.lbs.dto.SupplierDto;

/**
 * 		
 * <p>Title: ISupplierAdminUsersAdminService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author zhuzhen	
 * @date 2015年12月8日 上午11:26:43	
 * @version 1.0
 * <p>修改人：zhuzhen</p>
 * <p>修改时间：2015年12月8日 上午11:26:43</p>
 * <p>修改备注：</p>
 */
public interface ISupplierAdminUsersAdminService
{
    /**
     * 
     * findSuppliersBy：一句话描述方法功能
     * @param supplierAdminUsersDto
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年12月8日 上午11:26:50
     */
    public List<SupplierDto> findSuppliersBy(SupplierAdminUsersDto supplierAdminUsersDto);
}

