package com.ssic.shop.manage.dao;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssic.shop.manage.dto.PageHelperDto;
import com.ssic.shop.manage.mapper.DinnerSeriesMapper;
import com.ssic.shop.manage.pojo.DinnerSeries;
import com.ssic.shop.manage.pojo.DinnerSeriesExample;
import com.ssic.shop.manage.pojo.DinnerSeriesExample.Criteria;
import com.ssic.shop.manage.pojo.DinnerSeriesWithBLOBs;
import com.ssic.util.StringUtils;
import com.ssic.util.base.MyBatisBaseDao;
import com.ssic.util.constants.DataStatus;

/**		
 * <p>Title: DinnerSeriesDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振	
 * @date 2015年9月15日 下午5:58:25	
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年9月15日 下午5:58:25</p>
 * <p>修改备注：</p>
 */
@Repository
public class DinnerSeriesDao extends MyBatisBaseDao<DinnerSeries>
{
    @Autowired
    @Getter
    private DinnerSeriesMapper mapper;

    /**
     * 	 
     * @author 朱振	
     * @date 2015年9月16日 上午9:15:40	
     * @version 1.0
     * @param ph
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年9月16日 上午9:15:40</p>
     * <p>修改备注：</p>
     */
    public List<DinnerSeriesWithBLOBs> findAllBy(DinnerSeriesWithBLOBs dinnerSeries, PageHelperDto ph)
    {
        DinnerSeriesExample example = new DinnerSeriesExample();
        Criteria criteria = example.createCriteria();
        
        
        if(dinnerSeries != null)
        {
            //id
           if(!StringUtils.isEmpty(dinnerSeries.getId()))
           {
               List<DinnerSeriesWithBLOBs> result = new ArrayList<>();
               DinnerSeriesWithBLOBs item = mapper.selectByPrimaryKey(dinnerSeries.getId());
               if(item != null)
               {
                   result.add(item);
               }
               return result;
           }
           
           //like name
           if(!StringUtils.isEmpty(dinnerSeries.getName()))
           {
               criteria.andNameLike("%"+dinnerSeries.getName()+"%");
           }
           
           //==cafetoriumId
           if(!StringUtils.isEmpty(dinnerSeries.getCafetoriumId()))
           {
               criteria.andCafetoriumIdEqualTo(dinnerSeries.getCafetoriumId());
           }
           
           //>=minPerson
           if(dinnerSeries.getMinPerson() != null)
           {
               criteria.andMinPersonGreaterThanOrEqualTo(dinnerSeries.getMinPerson());
           }
           
           //<=maxPerson
           if(dinnerSeries.getMaxPerson() != null)
           {
               criteria.andMaxPersonLessThanOrEqualTo(dinnerSeries.getMaxPerson());
           }
        }
        
        if(ph != null)
        {
            //排序
            if(!StringUtils.isEmpty(ph.getSort()) && !StringUtils.isEmpty(ph.getOrder()))
            {
                StringBuffer orderStr = new StringBuffer();
                orderStr.append(ph.getSort());          
                orderStr.append(" ");
                orderStr.append(ph.getOrder());
                
                //分页，beginRow从0开始
                if(ph.getBeginRow()>=0 && ph.getRows() > 0)
                {
                    orderStr.append(" limit ");
                    orderStr.append(ph.getBeginRow());
                    orderStr.append(",");
                    orderStr.append(ph.getRows());
                }
                
                example.setOrderByClause(orderStr.toString());
            }
            
        }
       
        //有效记录
        criteria.andStatEqualTo(DataStatus.ENABLED);

            
        return mapper.selectByExampleWithBLOBs(example);
    }
    
    
    public List<DinnerSeriesWithBLOBs> findAll()
    {
        DinnerSeriesExample example = new DinnerSeriesExample();
        Criteria criteria = example.createCriteria();
        
        example.setOrderByClause("prince asc");
            
        //有效记录
        criteria.andStatEqualTo(DataStatus.ENABLED);

            
        return mapper.selectByExampleWithBLOBs(example);
    }
    
    /**
     * 	 
     * @author 朱振	
     * @date 2015年11月9日 上午8:42:14	
     * @version 1.0
     * @param param
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月9日 上午8:42:14</p>
     * <p>修改备注：</p>
     */
    public List<DinnerSeriesWithBLOBs> findBy(DinnerSeries param)
    {
        DinnerSeriesExample example = new DinnerSeriesExample();
        Criteria criteria = example.createCriteria();
        
      //id
        if(!StringUtils.isEmpty(param.getId()))
        {
          criteria.andIdEqualTo(param.getId());
        }
        //name
        if(!StringUtils.isEmpty(param.getName()))
        {
          criteria.andNameEqualTo(param.getName());
        }
        //prince
        if(null != param.getPrince())
        {
          criteria.andPrinceEqualTo(param.getPrince());
        }
        //minPerson
        if(null != param.getMinPerson())
        {
          criteria.andMinPersonEqualTo(param.getMinPerson());
        }
        //maxPerson
        if(null != param.getMaxPerson())
        {
          criteria.andMaxPersonEqualTo(param.getMaxPerson());
        }
        //cafetoriumId
        if(!StringUtils.isEmpty(param.getCafetoriumId()))
        {
          criteria.andCafetoriumIdEqualTo(param.getCafetoriumId());
        }

        //有效记录
        criteria.andStatEqualTo(DataStatus.ENABLED);

            
        return mapper.selectByExampleWithBLOBs(example);
    }
}

