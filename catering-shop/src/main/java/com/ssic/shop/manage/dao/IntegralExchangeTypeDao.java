package com.ssic.shop.manage.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssic.shop.manage.mapper.IntegralExchangeTypeMapper;
import com.ssic.shop.manage.pojo.IntegralExchangeType;
import com.ssic.shop.manage.pojo.IntegralExchangeTypeExample;
import com.ssic.util.StringUtils;
import com.ssic.util.base.MyBatisBaseDao;
import com.ssic.util.constants.DataStatus;

/**		
 * <p>Title: IntergralExchangeTypeDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振	
 * @date 2015年9月21日 下午5:50:19	
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年9月21日 下午5:50:19</p>
 * <p>修改备注：</p>
 */
@Repository
public class IntegralExchangeTypeDao extends MyBatisBaseDao<IntegralExchangeType>
{

    @Autowired
    @Getter   
    private IntegralExchangeTypeMapper mapper;
    
    /**
     * 查询所有	 
     * @author 朱振	
     * @date 2015年9月22日 上午8:59:25	
     * @version 1.0
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年9月22日 上午8:59:25</p>
     * <p>修改备注：</p>
     */
    public List<IntegralExchangeType> findAll()
    {
        IntegralExchangeTypeExample example = new IntegralExchangeTypeExample();
        example.setOrderByClause("create_time asc");
        
        return mapper.selectByExample(example);
    }
    
    
    public IntegralExchangeType findById(String id)
    {
        return mapper.selectByPrimaryKey(id);
    }
    
    /**
     * 	 
     * @author 朱振	
     * @date 2015年11月6日 下午5:12:01	
     * @version 1.0
     * @param param
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月6日 下午5:12:01</p>
     * <p>修改备注：</p>
     */
    public List<IntegralExchangeType> findBy(IntegralExchangeType param)
    {
        IntegralExchangeTypeExample example = new IntegralExchangeTypeExample();
        IntegralExchangeTypeExample.Criteria criteria = example.createCriteria();
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
        //target
        if(!StringUtils.isEmpty(param.getTarget()))
        {
          criteria.andTargetEqualTo(param.getTarget());
        }
//        //createTime
//        if(!StringUtils.isEmpty(param.getCreateTime()))
//        {
//          criteria.andCreateTimeEqualTo(param.getCreateTime());
//        }
//        //lastUpdateTime
//        if(!StringUtils.isEmpty(param.getLastUpdateTime()))
//        {
//          criteria.andLastUpdateTimeEqualTo(param.getLastUpdateTime());
//        }
        //stat
          criteria.andStatEqualTo(DataStatus.ENABLED);
        //cafetoriumId
        if(!StringUtils.isEmpty(param.getCafetoriumId()))
        {
          criteria.andCafetoriumIdEqualTo(param.getCafetoriumId());
        }
        
        example.setOrderByClause("create_time asc");

        return mapper.selectByExample(example);
    }
}

