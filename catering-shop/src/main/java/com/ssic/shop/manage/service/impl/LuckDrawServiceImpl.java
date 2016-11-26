/**
 * 
 */
package com.ssic.shop.manage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.shop.manage.dao.LuckyDrawDao;
import com.ssic.shop.manage.dto.GoodsDto;
import com.ssic.shop.manage.dto.LuckyDrawDto;
import com.ssic.shop.manage.pojo.LuckyDraw;
import com.ssic.shop.manage.service.LuckDrawService;
import com.ssic.util.BeanUtils;

/**		
 * <p>Title: LuckDrawServiceImpl </p>
 * <p>Description: 转盘抽奖Service层实现类</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年9月21日 下午2:46:36	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年9月21日 下午2:46:36</p>
 * <p>修改备注：</p>
 */
@Service
public class LuckDrawServiceImpl implements LuckDrawService
{

    @Autowired
    private LuckyDrawDao drawDao;

    /** 
    * (non-Javadoc)   
    * @see com.ssic.shop.manage.service.LuckDrawService#findLuckDrawById(java.lang.String)   
    */
    @Override
    public LuckyDrawDto findLuckDrawById(String id)
    {
        LuckyDraw luckDraw = drawDao.findLuckDrawById(id);
        if (luckDraw != null)
        {
            return BeanUtils.createBeanByTarget(luckDraw, LuckyDrawDto.class);
        }
        return null;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.shop.manage.service.LuckDrawService#updateLuckDraw(com.ssic.shop.manage.dto.LuckDrawDto)   
    */
    @Override
    public void updateLuckDraw(LuckyDrawDto param)
    {
        LuckyDraw luckDraw = new LuckyDraw();
        BeanUtils.copyProperties(param, luckDraw);
        drawDao.updateByPrimaryKeySelective(luckDraw);

    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.shop.manage.service.LuckDrawService#findByCafetoriumId(java.lang.String)   
    */
    @Override
    public List<LuckyDrawDto> findByCafetoriumId(String cafetoriumId)
    {
        List<LuckyDrawDto> result = new ArrayList<LuckyDrawDto>();
        List<LuckyDraw> luckDraws = drawDao.findByCafetoriumId(cafetoriumId);
        if (!CollectionUtils.isEmpty(luckDraws))
        {
            result = BeanUtils.createBeanListByTarget(luckDraws, LuckyDrawDto.class);
        }
        return result;
    }

}
