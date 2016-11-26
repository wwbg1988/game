package com.ssic.shop.manage.dao;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssic.shop.manage.dto.PageHelperDto;
import com.ssic.shop.manage.mapper.IntegralExchangeExMapper;
import com.ssic.shop.manage.mapper.IntegralExchangeMapper;
import com.ssic.shop.manage.pojo.IntegralExchange;
import com.ssic.shop.manage.pojo.IntegralExchangeExample;
import com.ssic.shop.manage.pojo.IntegralExchangeExample.Criteria;
import com.ssic.util.StringUtils;
import com.ssic.util.base.MyBatisBaseDao;
import com.ssic.util.constants.DataStatus;

/**		
 * <p>Title: IntegralExchangeDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振	
 * @date 2015年9月18日 上午11:23:25	
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年9月18日 上午11:23:25</p>
 * <p>修改备注：</p>
 */
@Repository
public class IntegralExchangeDao extends MyBatisBaseDao<IntegralExchange>
{
    @Autowired
    @Getter
    private IntegralExchangeMapper mapper;
    
    @Autowired
    private IntegralExchangeExMapper exMapper;
    
    
    /**
     * 	 
     * @author 朱振	
     * @date 2015年9月18日 上午11:27:37	
     * @version 1.0
     * @param integralExchange
     * @param pageHelperDto
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年9月18日 上午11:27:37</p>
     * <p>修改备注：</p>
     */
    public List<IntegralExchange> findAll(IntegralExchange integralExchange, PageHelperDto ph)
    {
        IntegralExchangeExample example = new IntegralExchangeExample();
        Criteria criteria = example.createCriteria();
        
        if(integralExchange != null)
        {
            // = id
            if(!StringUtils.isEmpty(integralExchange.getId()))
            {
                List<IntegralExchange> list = null;
                IntegralExchange item = mapper.selectByPrimaryKey(integralExchange.getId());
                if(item != null)
                {
                    list = new ArrayList<>();
                    list.add(item);
                }
                
                return list;
            }
            
            // like 姓名
            if(!StringUtils.isEmpty(integralExchange.getExchangeName()))
            {
                criteria.andExchangeNameLike("%"+integralExchange.getExchangeName()+"%");
            }
            
            //<= 要兑换的积分  
            if(integralExchange.getExchangeIntegral() != null)
            {
                criteria.andExchangeIntegralLessThanOrEqualTo(integralExchange.getExchangeIntegral());
            }
            
            //like 描述
            if(!StringUtils.isEmpty(integralExchange.getExchangeDescribe()))
            {
                criteria.andExchangeDescribeLike("%"+integralExchange.getExchangeDescribe()+"%");
            }
        }
        
        //分页
        if(ph != null)
        {
            //排序
            if(!StringUtils.isEmpty(ph.getSort()) && !StringUtils.isEmpty(ph.getOrder()))
            {
                StringBuffer orderStr = new StringBuffer();
                orderStr.append(ph.getSort());          
                orderStr.append(" ");
                orderStr.append(ph.getOrder());
                
                //limit ph.getBeginRow() , ph.getRows()
                if(ph.getBeginRow() >= 0 && ph.getRows() >0)
                {
                    orderStr.append(" limit ");
                    orderStr.append(ph.getBeginRow());
                    orderStr.append(",");
                    orderStr.append(ph.getRows());
                }
                
                example.setOrderByClause(orderStr.toString());
            }
            
        }
        
        
        return mapper.selectByExampleWithBLOBs(example);
    }
    
    /**
     * 
     * @author 朱振	
     * @date 2015年9月21日 上午11:49:22	
     * @version 1.0
     * @param ph
     * {
     * beginRow 开始  >=0
     * rows 页面大小 >0
     * sort 排序字段
     * order 升序（asc）/降序(desc)
     * }
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年9月21日 上午11:49:22</p>
     * <p>修改备注：</p>
     */
    public List<IntegralExchange> findAll(PageHelperDto ph)
    {
        IntegralExchangeExample example = new IntegralExchangeExample();
        
        //排序 order by ph.getSort ph.getOrder
        StringBuffer orderStr = new StringBuffer();
        orderStr.append(ph.getSort());          
        orderStr.append(" ");
        orderStr.append(ph.getOrder());
        
        //limit ph.getBeginRow() , ph.getRows()
        orderStr.append(" limit ");
        orderStr.append(ph.getBeginRow());
        orderStr.append(",");
        orderStr.append(ph.getRows());
        
        example.setOrderByClause(orderStr.toString());
        
        return mapper.selectByExampleWithBLOBs(example);
    }
    
    
    /**
     * 查询byID	 
     * @author 朱振	
     * @date 2015年9月22日 下午5:32:45	
     * @version 1.0
     * @param id
     * @return 只能有一条匹配记录
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年9月22日 下午5:32:45</p>
     * <p>修改备注：</p>
     */
    public IntegralExchange findById(String id)
    {
        return mapper.selectByPrimaryKey(id);
    }
    
     
    /**
     * 按	 
     * @author 朱振	
     * @date 2015年10月9日 上午9:34:19	
     * @version 1.0
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年10月9日 上午9:34:19</p>
     * <p>修改备注：</p>
     */
    public List<IntegralExchange> findAll()
    {
        IntegralExchangeExample example = new IntegralExchangeExample();
        Criteria criteria = example.createCriteria();
        criteria.andStatEqualTo(DataStatus.ENABLED);
        example.setOrderByClause("exchange_integral,exchange_type_id asc");
        
        return mapper.selectByExampleWithBLOBs(example);
    }
    
    /**
     * 通过积分兑换类别查询积分物品 	 
     * @author 朱振	
     * @date 2015年10月20日 下午4:35:05	
     * @version 1.0
     * @param cafetoriumId
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年10月20日 下午4:35:05</p>
     * <p>修改备注：</p>
     */
    public List<IntegralExchange> findAllByCafetoriumId(String cafetoriumId)
    {
        return exMapper.findExchangesByCafetoriumId(cafetoriumId);        
    }
    
    /**
     * 	 
     * @author 朱振	
     * @date 2015年11月6日 下午5:58:12	
     * @version 1.0
     * @param param
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月6日 下午5:58:12</p>
     * <p>修改备注：</p>
     */
    public List<IntegralExchange> findBy(IntegralExchange param)
    {
        IntegralExchangeExample example = new IntegralExchangeExample();
        IntegralExchangeExample.Criteria criteria = example.createCriteria();
        
      //id
        if(!StringUtils.isEmpty(param.getId()))
        {
          criteria.andIdEqualTo(param.getId());
        }
        //exchangeTypeId
        if(!StringUtils.isEmpty(param.getExchangeTypeId()))
        {
          criteria.andExchangeTypeIdEqualTo(param.getExchangeTypeId());
        }
        //exchangeName
        if(!StringUtils.isEmpty(param.getExchangeName()))
        {
          criteria.andExchangeNameEqualTo(param.getExchangeName());
        }
        //exchangeDescribe
        if(!StringUtils.isEmpty(param.getExchangeDescribe()))
        {
          criteria.andExchangeDescribeEqualTo(param.getExchangeDescribe());
        }
        //exchangeIntegral
        if(null != param.getExchangeIntegral())
        {
          criteria.andExchangeIntegralEqualTo(param.getExchangeIntegral());
        }
        //createTime
        //stat
      criteria.andStatEqualTo(DataStatus.ENABLED);
      
      example.setOrderByClause("create_time asc");
          
          
        return mapper.selectByExampleWithBLOBs(example);
    }
   
}

