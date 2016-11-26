package com.ssic.catering.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.catering.admin.dao.SupplierAdminUsersAdminDao;
import com.ssic.catering.admin.service.ISupplierAdminUsersAdminService;
import com.ssic.catering.lbs.dto.SupplierAdminUsersDto;
import com.ssic.catering.lbs.dto.SupplierDto;
import com.ssic.catering.lbs.pojo.SupplierAdminUsers;
import com.ssic.util.BeanUtils;

/**
 * 		
 * <p>Title: SupplierAdminUsersAdminServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author zhuzhen	
 * @date 2015年12月8日 上午11:27:23	
 * @version 1.0
 * <p>修改人：zhuzhen</p>
 * <p>修改时间：2015年12月8日 上午11:27:23</p>
 * <p>修改备注：</p>
 */
@Service
public class SupplierAdminUsersAdminServiceImpl implements ISupplierAdminUsersAdminService
{
    @Autowired
    private SupplierAdminUsersAdminDao supplierAdminUsersAdminDao;
    
    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.catering.admin.service.ISupplierAdminUsersAdminService#findSuppliersBy(com.ssic.catering.lbs.dto.SupplierAdminUsersDto)
     */
    @Override
    public List<SupplierDto> findSuppliersBy(SupplierAdminUsersDto supplierAdminUsersDto)
    {
        if(supplierAdminUsersDto != null)
        {
            SupplierAdminUsers param = new SupplierAdminUsers();
            BeanUtils.copyProperties(supplierAdminUsersDto, param);
            return supplierAdminUsersAdminDao.findSupplierDtosBy(param);
        }
        else
        {
            return supplierAdminUsersAdminDao.findSupplierDtosBy(null);
        }
    }

   
}

