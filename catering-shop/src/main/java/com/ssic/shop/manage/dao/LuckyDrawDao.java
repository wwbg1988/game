/**
 * 
 */
package com.ssic.shop.manage.dao;

import java.util.List;

import lombok.Getter;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssic.shop.manage.mapper.LuckyDrawMapper;
import com.ssic.shop.manage.pojo.LuckyDraw;
import com.ssic.shop.manage.pojo.LuckyDrawExample;
import com.ssic.shop.manage.pojo.LuckyDrawExample.Criteria;
import com.ssic.util.StringUtils;
import com.ssic.util.base.MyBatisBaseDao;
import com.ssic.util.constants.DataStatus;

/**		
 * <p>Title: LuckDrawDao </p>
 * <p>Description: 转盘抽奖dao层</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年9月21日 下午2:43:06	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年9月21日 下午2:43:06</p>
 * <p>修改备注：</p>
 */
@Repository
public class LuckyDrawDao extends MyBatisBaseDao<LuckyDraw>
{
    @Autowired
    @Getter
    private LuckyDrawMapper mapper;

    /**
     * 
     * findLuckDrawById：根据抽奖Id查询对应抽奖的的详细信息
     * @param id
     * @return
     * @exception   
     * @author 刘博
     * @date 2015年9月18日 上午11:42:29
     */
    public LuckyDraw findLuckDrawById(String id)
    {
        LuckyDraw luckDraw = mapper.selectByPrimaryKey(id);
        if (luckDraw != null)
        {
            return luckDraw;
        }
        return null;
    }

    /**     
     * findByCafetoriumId：一句话描述方法功能
     * @param cafetoriumId
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年10月23日 上午10:32:47	 
     */
    public List<LuckyDraw> findByCafetoriumId(String cafetoriumId)
    {
        LuckyDrawExample example = new LuckyDrawExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("create_time desc");

        if (!StringUtils.isEmpty(cafetoriumId))
        {
            criteria.andCafetoriumIdEqualTo(cafetoriumId);
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        List<LuckyDraw> list = mapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list))
        {
            return list;
        }
        return null;
    }

}
