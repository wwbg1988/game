package com.ssic.shop.manage.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssic.shop.manage.dao.CarteUserDao;
import com.ssic.shop.manage.dao.IntegralExchangeDao;
import com.ssic.shop.manage.dao.UserIntegralExchangeDao;
import com.ssic.shop.manage.dto.PageHelperDto;
import com.ssic.shop.manage.dto.UserIntegralExchangeDto;
import com.ssic.shop.manage.pojo.CarteUser;
import com.ssic.shop.manage.pojo.IntegralExchange;
import com.ssic.shop.manage.pojo.UserIntegralExchange;
import com.ssic.shop.manage.service.IUserIntegralExchangeService;
import com.ssic.util.BeanUtils;

/**		
 * <p>Title: UserIntegralExchangeServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振	
 * @date 2015年9月18日 下午3:36:17	
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年9月18日 下午3:36:17</p>
 * <p>修改备注：</p>
 */
@Service
public class UserIntegralExchangeServiceImpl implements IUserIntegralExchangeService
{

    @Autowired
    private UserIntegralExchangeDao userIntegralExchangeDao;
    
    @Autowired
    private CarteUserDao carteUserDao;
    
    @Autowired
    private IntegralExchangeDao integralExchangeDao;
    
   /**
    * 
    * (non-Javadoc)   
    * @see com.ssic.shop.manage.service.IUserIntegralExchangeService#getListBy(com.ssic.shop.manage.dto.UserIntegralExchangeDto, com.ssic.shop.manage.dto.PageHelperDto)
    */
    @Override
    public List<UserIntegralExchangeDto> getListBy(UserIntegralExchangeDto userIntegralExchangeDto, PageHelperDto ph)
    {
        UserIntegralExchange userIntegralExchange = new UserIntegralExchange();
        
        if(userIntegralExchangeDto != null)
        {
            BeanUtils.copyProperties(userIntegralExchangeDto, userIntegralExchange);
        }
        
        List<UserIntegralExchange> list = userIntegralExchangeDao.findAll(userIntegralExchange, ph);
        if(!CollectionUtils.isEmpty(list))
        {
            return BeanUtils.createBeanListByTarget(list, UserIntegralExchangeDto.class);
        }
        
        return null;
    }

   /**
    * 
    * (non-Javadoc)   
    * @see com.ssic.shop.manage.service.IUserIntegralExchangeService#addUserIntegralExchange(com.ssic.shop.manage.dto.UserIntegralExchangeDto)
    */
    @Override
    @Transactional
    public int addUserIntegralExchange(UserIntegralExchangeDto userIntegralExchangeDto)
    {
        CarteUser carteUser = carteUserDao.findById(userIntegralExchangeDto.getCarteUserId());
        if(carteUser != null)
        {
            IntegralExchange integralExchange = integralExchangeDao.findById(userIntegralExchangeDto.getIntegralExchangeId());
            if(integralExchange != null && carteUser.getIntegral() >= integralExchange.getExchangeIntegral())
            {
                CarteUser param = new CarteUser();
                param.setId(carteUser.getId());
                //更新个人的总积分
                param.setIntegral(carteUser.getIntegral()-integralExchange.getExchangeIntegral());
                carteUserDao.updateByPrimaryKeySelective(param);
                
                UserIntegralExchange userIntegralExchange = new UserIntegralExchange();
                BeanUtils.copyProperties(userIntegralExchangeDto, userIntegralExchange);
                  
                return userIntegralExchangeDao.insert(userIntegralExchange);
                
            }
        }
        
       return -1;
    }

}

