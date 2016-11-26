/**
 * 
 */
package com.ssic.shop.manage.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssic.shop.manage.mapper.WeixnUserCafetoriumExMapper;
import com.ssic.shop.manage.mapper.WeixnUserCafetoriumMapper;
import com.ssic.shop.manage.pojo.WeixnUserCafetorium;
import com.ssic.shop.manage.pojo.WeixnUserCafetoriumExample;
import com.ssic.shop.manage.pojo.WeixnUserCafetoriumExample.Criteria;
import com.ssic.util.StringUtils;
import com.ssic.util.base.MyBatisBaseDao;
import com.ssic.util.constants.DataStatus;

/**		
 * <p>Title: WeixnUserCafetoriumDao </p>
 * <p>Description: 微信用户食堂关系</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年10月27日 下午4:09:05	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年10月27日 下午4:09:05</p>
 * <p>修改备注：</p>
 */
@Repository
public class WeixnUserCafetoriumDao extends MyBatisBaseDao<WeixnUserCafetorium>
{

    @Autowired
    @Getter
    private WeixnUserCafetoriumMapper mapper;
    
    @Autowired
    private WeixnUserCafetoriumExMapper exMapper;

    /**
     * 
     * findAll：查询s
     * @param param
     * @param 
     * @return
     * @exception   
     * @author 刘博
     * @date 2015年8月28日 上午9:58:07
     */
    public List<WeixnUserCafetorium> findAll(WeixnUserCafetorium param)
    {
        WeixnUserCafetoriumExample example = new WeixnUserCafetoriumExample();
        Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(param.getOpenId()))
        {
            criteria.andOpenIdEqualTo(param.getOpenId());
        }
        if (!StringUtils.isEmpty(param.getCafetoriumId()))
        {
            criteria.andCafetoriumIdEqualTo(param.getCafetoriumId());
        }
        example.setOrderByClause("create_time desc ");
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return mapper.selectByExample(example);
    }
    
    /**
     * 	 
     * @author 朱振	
     * @date 2015年11月2日 上午11:28:20	
     * @version 1.0
     * @param openId
     * @param cafetoriumId
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月2日 上午11:28:20</p>
     * <p>修改备注：</p>
     */
    public Integer updateDefaultCafetorium(String openId, String cafetoriumId)
    {
        return exMapper.updateDefaultCafetorium(openId, cafetoriumId)+exMapper.updateUnDefaultOtherCafetorium(openId, cafetoriumId);
    }

}
