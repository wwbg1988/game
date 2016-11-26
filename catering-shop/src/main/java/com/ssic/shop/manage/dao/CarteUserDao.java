/**
 * 
 */
package com.ssic.shop.manage.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssic.shop.manage.dto.PageHelperDto;
import com.ssic.shop.manage.mapper.CarteUserMapper;
import com.ssic.shop.manage.pojo.CarteUser;
import com.ssic.shop.manage.pojo.CarteUserExample;
import com.ssic.shop.manage.pojo.CarteUserExample.Criteria;
import com.ssic.util.StringUtils;
import com.ssic.util.base.MyBatisBaseDao;
import com.ssic.util.constants.DataStatus;

/**		
 * <p>Title: CarteUserDao </p>
 * <p>Description: 商城用户DAO层</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年8月27日 上午10:08:11	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年8月27日 上午10:08:11</p>
 * <p>修改备注：</p>
 */
@Repository
public class CarteUserDao extends MyBatisBaseDao<CarteUser>
{
    @Autowired
    @Getter
    private CarteUserMapper mapper;

    /**     
     * findAllBy：后台dataGrid查找所有商城用户
     * @param CarteUser 商城用户pojo
     * @param PageHelperDto  分页对象
     * @return
     * @exception   
     * @author 刘博
     * @date 2015年8月27日 上午10:01:53    
     */
    public List<CarteUser> findAllBy(CarteUser param, PageHelperDto ph)
    {
        CarteUserExample example = new CarteUserExample();
        Criteria criteria = example.createCriteria();
        if (ph != null && !StringUtils.isEmpty(String.valueOf(ph.getBeginRow()))
            && !StringUtils.isEmpty(String.valueOf(ph.getRows())))
        {
            int beginRow = ph.getBeginRow();
            int rows = ph.getRows();
            example.setOrderByClause("create_time desc limit " + beginRow + "," + rows);
        }
        else
        {
            example.setOrderByClause("create_time desc");
        }
        if (!StringUtils.isEmpty(param.getId()))
        {
            criteria.andIdEqualTo(param.getId());
        }
        if (!StringUtils.isEmpty(param.getName()))
        {
            criteria.andNameLike("%" + param.getName() + "%");
        }
        if (!StringUtils.isEmpty(param.getBirthday()))
        {
            criteria.andBirthdayLike("%" + param.getBirthday() + "%");
        }
        if (!StringUtils.isEmpty(param.getPhone()))
        {
            criteria.andPhoneLike("%" + param.getPhone() + "%");
        }
        if(!StringUtils.isEmpty(param.getCafetoriumId()))
        {
            criteria.andCafetoriumIdEqualTo(param.getCafetoriumId());
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return mapper.selectByExample(example);
    }

    /**     
     * findCountBy：后台dataGrid查找所有商城用户的数量
     * @param CarteUser 商城用户pojo
     * @return
     * @exception   
     * @author 刘博
     * @date 2015年8月4日 上午11:41:13    
     */
    public int findCountBy(CarteUser param)
    {
        CarteUserExample example = new CarteUserExample();
        Criteria criteria = example.createCriteria();

        criteria.andStatEqualTo(DataStatus.ENABLED);

        if (!StringUtils.isEmpty(param.getId()))
        {
            criteria.andIdEqualTo(param.getId());
        }
        if (!StringUtils.isEmpty(param.getName()))
        {
            criteria.andNameLike("%" + param.getName() + "%");
        }
        if (!StringUtils.isEmpty(param.getBirthday()))
        {
            criteria.andBirthdayLike("%" + param.getBirthday() + "%");
        }
        if (!StringUtils.isEmpty(param.getPhone()))
        {
            criteria.andPhoneLike("%" + param.getPhone() + "%");
        }
        if(!StringUtils.isEmpty(param.getCafetoriumId()))
        {
            criteria.andCafetoriumIdEqualTo(param.getCafetoriumId());
        }
        int count = mapper.countByExample(example);
        return count;
    }
    
    /**
     * 
     * findAllBy：根据条件查询结果集
     * @param param
     * @return
     * @exception	
     * @author 朱振
     * @date 2015年8月27日 上午11:23:34
     */
    public List<CarteUser> findAllBy(CarteUser param)
    {
        CarteUserExample example = new CarteUserExample();
        Criteria criteria = example.createCriteria();
        
        criteria.andStatEqualTo(DataStatus.ENABLED);
        
        //ID
        if(!StringUtils.isEmpty(param.getId()))
        {
           List<CarteUser> carteUsers =  new ArrayList<CarteUser>();
           CarteUser item = mapper.selectByPrimaryKey(param.getId());
           if(item != null)
           {
               carteUsers.add(item);
           }
           return carteUsers;
        }
        
        //微信标识
        if(!StringUtils.isEmpty(param.getOpenId()))
        {
            criteria.andOpenIdEqualTo(param.getOpenId());
        }
        
        //食堂id
        if(!StringUtils.isEmpty(param.getCafetoriumId()))
        {
            criteria.andCafetoriumIdEqualTo(param.getCafetoriumId());
        }
        
        //姓名
        if(!StringUtils.isEmpty(param.getName()))
        {
            criteria.andNameEqualTo(param.getName());
        }
        
        //生日
        if(!StringUtils.isEmpty(param.getBirthday()))
        {
            criteria.andBirthdayEqualTo(param.getBirthday());
        }
        
        //年龄
        if(param.getIntegral() != null)
        {
            criteria.andIntegralEqualTo(param.getIntegral());
        }
        
        //电话
        if(!StringUtils.isEmpty(param.getPhone()))
        {
            criteria.andPhoneEqualTo(param.getPhone());
        }
        
        return mapper.selectByExample(example);
    }
    
    
    /**
     * 更新非空的属性	 
     * @author 朱振	
     * @date 2015年9月21日 上午10:01:01	
     * @version 1.0
     * @param param
     * @param condition
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年9月21日 上午10:01:01</p>
     * <p>修改备注：</p>
     */
    public int updateCarteUserBy(CarteUser record, CarteUser param)
    {
        CarteUserExample example = new CarteUserExample();
        Criteria criteria = example.createCriteria();
        
        if(!StringUtils.isEmpty(param.getId()))
        {
            record.setId(param.getId());
            return updateByPrimaryKeySelective(record);
        }
        
        //微信标识
        if(!StringUtils.isEmpty(param.getOpenId()))
        {
            criteria.andOpenIdEqualTo(param.getOpenId());
        }
        
        //食堂id
        if(!StringUtils.isEmpty(param.getCafetoriumId()))
        {
            criteria.andCafetoriumIdEqualTo(param.getCafetoriumId());
        }
        
        //姓名
        if(!StringUtils.isEmpty(param.getName()))
        {
            criteria.andNameEqualTo(param.getName());
        }
        
        //生日
        if(!StringUtils.isEmpty(param.getBirthday()))
        {
            criteria.andBirthdayEqualTo(param.getBirthday());
        }
        
        //年龄
        if(param.getIntegral() != null)
        {
            criteria.andIntegralEqualTo(param.getIntegral());
        }
        
        //电话
        if(!StringUtils.isEmpty(param.getPhone()))
        {
            criteria.andPhoneEqualTo(param.getPhone());
        }  
        
        //是否有效
        if(param.getStat() != null)
        {
            criteria.andStatEqualTo(param.getStat());
        }
        
        //加入更新日期
        record.setLastUpdateTime(new Date());            
            
        return mapper.updateByExampleSelective(record, example);
    }
    
    
    /**
     * 查询byID	 
     * @author 朱振	
     * @date 2015年9月22日 下午5:26:21	
     * @version 1.0
     * @param id
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年9月22日 下午5:26:21</p>
     * <p>修改备注：</p>
     */
    public CarteUser findById(String id)
    {
       return mapper.selectByPrimaryKey(id);
    }
  
}
