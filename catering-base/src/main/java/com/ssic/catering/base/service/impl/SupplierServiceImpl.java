/**
 * 
 */
package com.ssic.catering.base.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.catering.base.service.ISupplierService;
import com.ssic.catering.lbs.dao.SupplierDao;
import com.ssic.catering.lbs.dto.SupplierDto;
import com.ssic.catering.lbs.pojo.Supplier;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.util.BeanUtils;
import com.ssic.util.constants.DataStatus;

/**		
 * <p>Title: SupplierServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年12月3日 下午4:21:01	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年12月3日 下午4:21:01</p>
 * <p>修改备注：</p>
 */
@Service
public class SupplierServiceImpl implements ISupplierService
{

    @Autowired
    private SupplierDao dao;

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.ITransportDestService#findALL(com.ssic.catering.lbs.dto.TransportDestDto, com.ssic.catering.base.dto.PageHelperDto)   
    */
    @Override
    public List<SupplierDto> findALL(SupplierDto supplierDto, PageHelperDto phDto)
    {
        List<SupplierDto> result = new ArrayList<SupplierDto>();
        Supplier dest = new Supplier();
        BeanUtils.copyProperties(supplierDto, dest);
        List<Supplier> list = dao.findBy(dest, phDto);
        if (list != null && list.size() > 0)
        {
            result = BeanUtils.createBeanListByTarget(list, SupplierDto.class);
        }
        return result;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.ITransportDestService#findCount()   
    */
    @Override
    public int findCount(SupplierDto supplierDto)
    {
        Supplier dest = new Supplier();
        BeanUtils.copyProperties(supplierDto, dest);
        int flag = dao.findCount(dest);
        if (flag > 0)
        {
            return flag;
        }
        return 0;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.ITransportDestService#add(com.ssic.catering.lbs.dto.TransportDestDto)   
    */
    @Override
    public void add(SupplierDto supplierDto)
    {
        Supplier dest = new Supplier();
        BeanUtils.copyProperties(supplierDto, dest);
        dest.setStat(DataStatus.ENABLED);
        dest.setCreateTime(new Date());
        dao.insert(dest);

    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.ITransportDestService#findById(java.lang.String)   
    */
    @Override
    public SupplierDto findById(String id)
    {
        SupplierDto supplierDto = new SupplierDto();
        Supplier dest = dao.findById(id);
        BeanUtils.copyProperties(dest, supplierDto);
        return supplierDto;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.ITransportDestService#update(com.ssic.catering.lbs.dto.TransportDestDto)   
    */
    @Override
    public void update(SupplierDto r)
    {
        Supplier dest = new Supplier();
        BeanUtils.copyProperties(r, dest);
        dest.setStat(DataStatus.ENABLED);
        dest.setLastUpdateTime(new Date());
        dao.updateByPrimaryKeySelective(dest);

    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.ITransportDestService#delete(com.ssic.catering.lbs.dto.TransportDestDto)   
    */
    @Override
    public void delete(SupplierDto r)
    {
        Supplier dest = new Supplier();
        BeanUtils.copyProperties(r, dest);
        dest.setStat(DataStatus.DISABLED);
        dest.setLastUpdateTime(new Date());
        dao.updateByPrimaryKey(dest);
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.ITransportDestService#findByLongitudeAndLatitude(java.lang.String, java.lang.String)   
    */
    @Override
    public List<SupplierDto> findByLongitudeAndLatitude(String longitude, String latitude)
    {
        List<SupplierDto> result = new ArrayList<SupplierDto>();
        Supplier dest = new Supplier();
        dest.setLongitude(longitude);
        dest.setLatitude(latitude);
        List<Supplier> list = dao.findBy(dest, null);
        if (list != null && list.size() > 0)
        {
            result = BeanUtils.createBeanListByTarget(list, SupplierDto.class);
        }
        return result;
    }

    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.ITransportDestService#findBy(com.ssic.catering.lbs.dto.TransportDestDto)
     */
    @Override
    public List<SupplierDto> findBy(SupplierDto supplierDto)
    {
        if (supplierDto != null)
        {
            Supplier dest = new Supplier();
            BeanUtils.copyProperties(supplierDto, dest);

            List<Supplier> result = dao.findBy(dest);
            if (!CollectionUtils.isEmpty(result))
            {
                return BeanUtils.createBeanListByTarget(result, SupplierDto.class);
            }
        }
        else
        {
            List<Supplier> result = dao.findBy(null);
            if (!CollectionUtils.isEmpty(result))
            {
                return BeanUtils.createBeanListByTarget(result, SupplierDto.class);
            }
        }
        
        return null;

    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.ISupplierService#findByName(java.lang.String)   
    */
    @Override
    public SupplierDto findByName(String supplierName)
    {
        SupplierDto supplierDto = new SupplierDto();
        Supplier dest = new Supplier();
        dest.setSupplierName(supplierName);
        List<Supplier> list = dao.findBy(dest, null);
        if (list != null && list.size() > 0)
        {
            BeanUtils.copyProperties(list.get(0), supplierDto);
        }
        return supplierDto;
    }
}
