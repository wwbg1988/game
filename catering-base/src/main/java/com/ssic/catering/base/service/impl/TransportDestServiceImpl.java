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

import com.ssic.catering.base.service.ITransportDestService;
import com.ssic.catering.lbs.dao.TransportDestDao;
import com.ssic.catering.lbs.dto.TransportDestDto;
import com.ssic.catering.lbs.pojo.TransportDest;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.util.BeanUtils;
import com.ssic.util.constants.DataStatus;

/**		
 * <p>Title: TransportDestServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年11月25日 下午1:14:24	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年11月25日 下午1:14:24</p>
 * <p>修改备注：</p>
 */
@Service
public class TransportDestServiceImpl implements ITransportDestService
{

    @Autowired
    private TransportDestDao dao;

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.ITransportDestService#findALL(com.ssic.catering.lbs.dto.TransportDestDto, com.ssic.catering.base.dto.PageHelperDto)   
    */
    @Override
    public List<TransportDestDto> findALL(TransportDestDto transportDestDto, PageHelperDto phDto)
    {
        List<TransportDestDto> result = new ArrayList<TransportDestDto>();
        TransportDest dest = new TransportDest();
        BeanUtils.copyProperties(transportDestDto, dest);
        List<TransportDest> list = dao.findBy(dest, phDto);
        if (list != null && list.size() > 0)
        {
            result = BeanUtils.createBeanListByTarget(list, TransportDestDto.class);
        }
        return result;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.ITransportDestService#findCount()   
    */
    @Override
    public int findCount(TransportDestDto transportDestDto)
    {
        TransportDest dest = new TransportDest();
        BeanUtils.copyProperties(transportDestDto, dest);
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
    public void add(TransportDestDto transportDestDto)
    {
        TransportDest dest = new TransportDest();
        BeanUtils.copyProperties(transportDestDto, dest);
        dest.setStat(DataStatus.ENABLED);
        dest.setCreateTime(new Date());
        dao.insert(dest);

    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.ITransportDestService#findById(java.lang.String)   
    */
    @Override
    public TransportDestDto findById(String id)
    {
        TransportDestDto transportDestDto = new TransportDestDto();
        TransportDest dest = dao.findById(id);
        BeanUtils.copyProperties(dest, transportDestDto);
        return transportDestDto;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.ITransportDestService#update(com.ssic.catering.lbs.dto.TransportDestDto)   
    */
    @Override
    public void update(TransportDestDto r)
    {
        TransportDest dest = new TransportDest();
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
    public void delete(TransportDestDto r)
    {

        TransportDest dest = new TransportDest();
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
    public List<TransportDestDto> findByLongitudeAndLatitude(String longitude, String latitude)
    {
        List<TransportDestDto> result = new ArrayList<TransportDestDto>();
        TransportDest dest = new TransportDest();
        dest.setLongitude(longitude);
        dest.setLatitude(latitude);
        List<TransportDest> list = dao.findBy(dest, null);
        if (list != null && list.size() > 0)
        {
            result = BeanUtils.createBeanListByTarget(list, TransportDestDto.class);
        }
        return result;
    }
    
    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.ITransportDestService#findBy(com.ssic.catering.lbs.dto.TransportDestDto)
     */
    @Override
    public List<TransportDestDto> findBy(TransportDestDto transportDestDto)
    {
        if(transportDestDto != null)
        {
            TransportDest dest = new TransportDest();
            BeanUtils.copyProperties(transportDestDto, dest);
            
            List<TransportDest> result = dao.findBy(dest);
            if(!CollectionUtils.isEmpty(result))
            {
                return BeanUtils.createBeanListByTarget(result, TransportDestDto.class);
            }
        }
        
        return null;
     
    }
}
