package com.ssic.catering.base.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ssic.catering.base.service.ISupplierAdminUsersService;
import com.ssic.catering.lbs.dao.SupplierAdminUsersDao;
import com.ssic.catering.lbs.dto.SupplierAdminUsersDto;
import com.ssic.catering.lbs.pojo.SupplierAdminUsers;
import com.ssic.util.BeanUtils;

@Service
public class SupplierAdminUsersServiceImpl implements ISupplierAdminUsersService
{
    
    private static final Logger log = Logger.getLogger(SupplierAdminUsersServiceImpl.class);
    
    @Autowired
    private SupplierAdminUsersDao supplierAdminUsersDao;

    @Override
    public List<SupplierAdminUsersDto> findSupplierAdminUsersBy(SupplierAdminUsersDto supplierAdminUsersDto)
    {
        if(supplierAdminUsersDto != null)
        {
            SupplierAdminUsers supplierAdminUsers = new SupplierAdminUsers();
            BeanUtils.copyProperties(supplierAdminUsersDto, supplierAdminUsers);
            
            List<SupplierAdminUsers> result = supplierAdminUsersDao.findBy(supplierAdminUsers);
            if(!CollectionUtils.isEmpty(result))
            {
                return BeanUtils.createBeanListByTarget(result, SupplierAdminUsersDto.class);
            }
        }
        else
        {
            List<SupplierAdminUsers> result = supplierAdminUsersDao.findBy(null);
            if(!CollectionUtils.isEmpty(result))
            {
                return BeanUtils.createBeanListByTarget(result, SupplierAdminUsersDto.class);
            }
        }
        
        return null;
    }

    @Override
    public int addSupplierAdminUsers(SupplierAdminUsersDto supplierAdminUsersDto)
    {
        if(supplierAdminUsersDto != null)
        {
            SupplierAdminUsers supplierAdminUsers = new SupplierAdminUsers();
            BeanUtils.copyProperties(supplierAdminUsersDto, supplierAdminUsers);
            
            return supplierAdminUsersDao.insert(supplierAdminUsers);
        }
        
        return 0;
    }
    
    @Override
    public int deleteSupplierById(String id)
    {
        if(!StringUtils.isEmpty(id))
        {
            return supplierAdminUsersDao.deleteById(id);
        }
        return 0;
    }
    
    @Override
    @Transactional
    public int bindingSuppliers(SupplierAdminUsersDto supplierAdminUsersDto)
    {
        //后台账号
        if(StringUtils.isEmpty(supplierAdminUsersDto.getAdminUsersId()))
        {
            log.error("后台账号不能为空");
            return 0;
        }
        
        //供应商
        if(StringUtils.isEmpty(supplierAdminUsersDto.getSupplierId()))
        {
            log.error("供应商不能为空");
            return 0;
        }
        
        SupplierAdminUsersDto param = new SupplierAdminUsersDto();
        param.setAdminUsersId(supplierAdminUsersDto.getAdminUsersId());

        this.deleteSuppliersBy(param);//删除与后台账号关联的所有供应商
        
        if(supplierAdminUsersDto.getSupplierId().contains(","))
        {//多个供应商的id
            String[] supplierIds = supplierAdminUsersDto.getSupplierId().split(",");
            int count = 0;
            for(String supplierId:supplierIds)
            {
                SupplierAdminUsersDto newSupplierAdminUsers = new SupplierAdminUsersDto();
                newSupplierAdminUsers.setAdminUsersId(supplierAdminUsersDto.getAdminUsersId());
                newSupplierAdminUsers.setSupplierId(supplierId);
                
                List<SupplierAdminUsersDto> suppliers = this.findSupplierAdminUsersBy(newSupplierAdminUsers);
                
               
                if(CollectionUtils.isEmpty(suppliers))
                {
                    count += this.addSupplierAdminUsers(newSupplierAdminUsers);
                }
            }
            
           return count;
        }
        else
        {
            SupplierAdminUsersDto newSupplierAdminUsers = new SupplierAdminUsersDto();
            newSupplierAdminUsers.setAdminUsersId(supplierAdminUsersDto.getAdminUsersId());
            newSupplierAdminUsers.setSupplierId(supplierAdminUsersDto.getSupplierId());
            
            int count = this.addSupplierAdminUsers(newSupplierAdminUsers);
            if(count > 0)
            {
               return 0;
            }
            
            return count;
        }
    }
    
    @Override
    public int deleteSuppliersBy(SupplierAdminUsersDto supplierAdminUsersDto)
    {
        if(supplierAdminUsersDto != null)
        {
            SupplierAdminUsers supplierAdminUsers = new SupplierAdminUsers();
            BeanUtils.copyProperties(supplierAdminUsersDto, supplierAdminUsers);
            
            return supplierAdminUsersDao.deleteBy(supplierAdminUsers);
        }
        return 0;
    }

}

