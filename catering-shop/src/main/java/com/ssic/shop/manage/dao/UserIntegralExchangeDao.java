package com.ssic.shop.manage.dao;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssic.shop.manage.dto.PageHelperDto;
import com.ssic.shop.manage.mapper.UserIntegralExchangeMapper;
import com.ssic.shop.manage.pojo.UserIntegralExchange;
import com.ssic.shop.manage.pojo.UserIntegralExchangeExample;
import com.ssic.shop.manage.pojo.UserIntegralExchangeExample.Criteria;
import com.ssic.util.StringUtils;
import com.ssic.util.base.MyBatisBaseDao;

/**		
 * <p>Title: UserIntegralExchangeDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振	
 * @date 2015年9月18日 下午2:38:32	
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年9月18日 下午2:38:32</p>
 * <p>修改备注：</p>
 */
@Repository
public class UserIntegralExchangeDao extends MyBatisBaseDao<UserIntegralExchange>
{
    @Autowired
    @Getter
    private UserIntegralExchangeMapper mapper;

    /**
     * 	 
     * @author 朱振	
     * @date 2015年9月18日 下午2:46:04	
     * @version 1.0
     * @param userIntegralExchange
     * @param dto
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年9月18日 下午2:46:04</p>
     * <p>修改备注：</p>
     */
    public List<UserIntegralExchange> findAll(UserIntegralExchange userIntegralExchange, PageHelperDto ph)
    {
        UserIntegralExchangeExample example = new UserIntegralExchangeExample();
        Criteria criteria = example.createCriteria();
        
        if(userIntegralExchange != null)
        {
            //=id
            if(!StringUtils.isEmpty(userIntegralExchange.getId()))
            {
                //criteria.andIdEqualTo(userIntegralExchange.getId());
                List<UserIntegralExchange> list = null;
                UserIntegralExchange item = mapper.selectByPrimaryKey(userIntegralExchange.getId());
                if(item != null)
                {
                    list = new ArrayList<>();
                    list.add(item);
                }
                
                return list;
            }
            
            //=商城用户id
            if(!StringUtils.isEmpty(userIntegralExchange.getCarteUserId()))
            {
                criteria.andCarteUserIdEqualTo(userIntegralExchange.getCarteUserId());
            }
            
            //=兑换物品id
            if(!StringUtils.isEmpty(userIntegralExchange.getIntegralExchangeId()))
            {
                criteria.andIntegralExchangeIdEqualTo(userIntegralExchange.getIntegralExchangeId());
            }
            
            //like 收货人姓名
            if(!StringUtils.isEmpty(userIntegralExchange.getConsigneeName()))
            {
                criteria.andConsigneeNameLike("%"+userIntegralExchange.getConsigneeName()+"%");
            }
            
            //like 收货人城市
            if(!StringUtils.isEmpty(userIntegralExchange.getConsigneeCity()))
            {
                criteria.andConsigneeCityLike("%"+userIntegralExchange.getConsigneeCity()+"%");
            }
            
            //=手机号码
            if(!StringUtils.isEmpty(userIntegralExchange.getPhoneNumber()))
            {
                criteria.andPhoneNumberEqualTo(userIntegralExchange.getPhoneNumber());
            }
        }
        
        
        if(ph != null)
        {
            if(!StringUtils.isEmpty(ph.getSort()) && !StringUtils.isEmpty(ph.getOrder()))
            {
                StringBuffer orderStr = new StringBuffer();
                orderStr.append(ph.getSort());
                orderStr.append(" ");
                orderStr.append(ph.getOrder());
                
                if(ph.getBeginRow() >=0 && ph.getRows() > 0)
                {
                    orderStr.append(" limmit ");
                    orderStr.append(ph.getBeginRow());
                    orderStr.append(",");
                    orderStr.append(ph.getRows());
                }
                
                example.setOrderByClause(orderStr.toString());
            }
        }
        
        
        return mapper.selectByExample(example);
        
    }
    
}

