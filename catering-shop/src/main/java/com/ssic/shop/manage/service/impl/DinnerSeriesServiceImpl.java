package com.ssic.shop.manage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.shop.manage.dao.DinnerSeriesDao;
import com.ssic.shop.manage.dto.DinnerSeriesDto;
import com.ssic.shop.manage.dto.PageHelperDto;
import com.ssic.shop.manage.pojo.DinnerSeriesWithBLOBs;
import com.ssic.shop.manage.service.IDinnerSeriesService;
import com.ssic.util.BeanUtils;

/**		
 * <p>Title: DinnerSeriesServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振	
 * @date 2015年9月16日 上午10:45:46	
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年9月16日 上午10:45:46</p>
 * <p>修改备注：</p>
 */
@Service
public class DinnerSeriesServiceImpl implements IDinnerSeriesService
{
    @Autowired
    private DinnerSeriesDao dinnerSeriesDao;
    
    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.shop.manage.service.DinnerSeriesService#getListBy(com.ssic.shop.manage.dto.DinnerSeriesDto, com.ssic.shop.manage.dto.PageHelperDto)
     */
    @Override
    public List<DinnerSeriesDto> getListBy(DinnerSeriesDto dinnerSeriesDto, PageHelperDto pageHelperDto)
    {
       List<DinnerSeriesDto> result = new ArrayList<>();

       DinnerSeriesWithBLOBs dinnerSeries = null;
       if(dinnerSeriesDto != null)
       {
           dinnerSeries = new DinnerSeriesWithBLOBs();
           BeanUtils.copyProperties(dinnerSeriesDto, dinnerSeries);
       }
       
//       PageHelper ph = null;
//       if(pageHelperDto != null)
//       {
//           ph = new PageHelper();
//           BeanUtils.copyProperties(pageHelperDto, ph);
//       }
       
       List<DinnerSeriesWithBLOBs> dinnerSerieses =  dinnerSeriesDao.findAllBy(dinnerSeries, pageHelperDto);
       
       if(!CollectionUtils.isEmpty(dinnerSerieses))
       {
           result = BeanUtils.createBeanListByTarget(dinnerSerieses, DinnerSeriesDto.class);
       }
       
       return result;
    }
    
    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.shop.manage.service.DinnerSeriesService#insert(com.ssic.shop.manage.dto.DinnerSeriesDto)
     */
    @Override
    public int insert(DinnerSeriesDto dinnerSeriesDto)
    {
       if(dinnerSeriesDto != null)
       {
           DinnerSeriesWithBLOBs param = new DinnerSeriesWithBLOBs();
           
           BeanUtils.copyProperties(dinnerSeriesDto, param);
           
           return dinnerSeriesDao.insert(param);
       }
       
       return 0;     
    }
    
    @Override
    public List<DinnerSeriesDto> getList()
    {
        List<DinnerSeriesDto> result = null;

        List<DinnerSeriesWithBLOBs> dinnerSerieses =  dinnerSeriesDao.findAll();
        
        if(!CollectionUtils.isEmpty(dinnerSerieses))
        {
            result = new ArrayList<>();
            result = BeanUtils.createBeanListByTarget(dinnerSerieses, DinnerSeriesDto.class);
        }
        
        return result;
    }

}

