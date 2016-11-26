/**
 * 
 */
package com.ssic.shop.manage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.shop.manage.dao.CarteUserDao;
import com.ssic.shop.manage.dto.CarteUserDto;
import com.ssic.shop.manage.dto.PageHelperDto;
import com.ssic.shop.manage.pojo.CarteUser;
import com.ssic.shop.manage.service.ICarteUserService;
import com.ssic.util.BeanUtils;
import com.ssic.util.StringUtils;

/**		
 * <p>Title: CarteUserServiceImpl </p>
 * <p>Description: 商城用户Service实现层</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年8月27日 上午10:14:17	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年8月27日 上午10:14:17</p>
 * <p>修改备注：</p>
 */
@Service
public class CarteUserServiceImpl implements ICarteUserService
{
    private static Logger log = Logger.getLogger(CarteUserServiceImpl.class);
    
    @Autowired
    private CarteUserDao carteUserDao;

    /** 
    * (non-Javadoc)   
    * @see com.ssic.shop.manage.service.ICarteUserService#findAllBy(com.ssic.shop.manage.pojo.CarteUser, com.ssic.game.common.dto.PageHelperDto)   
    */
    @Override
    public List<CarteUserDto> findAllBy(CarteUserDto carteUserDto, PageHelperDto ph)
    {
        List<CarteUserDto> result = new ArrayList<CarteUserDto>();
        CarteUser param = new CarteUser();
        BeanUtils.copyProperties(carteUserDto, param);
        List<CarteUser> list = carteUserDao.findAllBy(param, ph);
        if (!CollectionUtils.isEmpty(list))
        {
            result = BeanUtils.createBeanListByTarget(list, CarteUserDto.class);
        }
        return result;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.shop.manage.service.ICarteUserService#findCount(com.ssic.shop.manage.dto.CarteUserDto)   
    */
    @Override
    public int findCount(CarteUserDto carteUserDto)
    {
        CarteUser param = new CarteUser();
        BeanUtils.copyProperties(carteUserDto, param);
        int counts = carteUserDao.findCountBy(param);
        return counts;
    }
     
    /**
     * (non-Javadoc)   
     * @see com.ssic.shop.manage.service.ICarteUserService#findByWxUniqueIdAndCafetoriumId(java.lang.String, java.lang.String)
     */
    @Override
    public CarteUserDto findByWxUniqueIdAndCafetoriumId(String wxUniqueId, String cafetoriumId)
    {
        log.debug("openId:"+wxUniqueId+"cafetoriumId:"+cafetoriumId);
        
        if(StringUtils.isEmpty(wxUniqueId) || StringUtils.isEmpty(cafetoriumId))
        {
            log.error("参数不合法");
            return null;
        }
        
        CarteUserDto result = null;
        CarteUser param = new CarteUser();
        
        //使用微信id来判断生日记录是否唯一
        param.setOpenId(wxUniqueId);
        param.setCafetoriumId(cafetoriumId);
        
        List<CarteUser> carterUsers =  carteUserDao.findAllBy(param);
        if(!CollectionUtils.isEmpty(carterUsers))
        {
            List<CarteUserDto> list = BeanUtils.createBeanListByTarget(carterUsers, CarteUserDto.class);
            if(list.size() >= 1)
            {
                result = list.get(0);
            }
        }
        
        
        return result;
    }
    
   /**
    * 
    * (non-Javadoc)   
    * @see com.ssic.shop.manage.service.ICarteUserService#insertCarteUser(com.ssic.shop.manage.dto.CarteUserDto)
    */
    @Override
    public int insertCarteUser(CarteUserDto carteUserDto)
    {
        CarteUser carteUser = new CarteUser();        
        BeanUtils.copyProperties(carteUserDto, carteUser);
        
        return carteUserDao.insert(carteUser);        
    }
    
    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.shop.manage.service.ICarteUserService#updateCarteUserBy(com.ssic.shop.manage.dto.CarteUserDto, com.ssic.shop.manage.dto.CarteUserDto)
     */
    @Override
    public int updateCarteUserBy(CarteUserDto carteUserDto, CarteUserDto condition)
    {
        CarteUser record = new CarteUser();        
        BeanUtils.copyProperties(carteUserDto, record);
        
        CarteUser param = new CarteUser();        
        BeanUtils.copyProperties(carteUserDto, param);
        
        
        return carteUserDao.updateCarteUserBy(record, param);
    }
    
    
    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.shop.manage.service.ICarteUserService#updateCarteUserById(com.ssic.shop.manage.dto.CarteUserDto)
     */
    @Override
    public int updateCarteUser(CarteUserDto carteUserDto)
    {
        CarteUser record = new CarteUser();        
        BeanUtils.copyProperties(carteUserDto, record);
        return carteUserDao.updateByPrimaryKeySelective(record);
    }

}
