/**
 * 
 */
package com.ssic.shop.manage.dao;

import java.util.List;

import lombok.Getter;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssic.shop.manage.dto.PageHelperDto;
import com.ssic.shop.manage.mapper.GoodsTypeMapper;
import com.ssic.shop.manage.pojo.GoodsType;
import com.ssic.shop.manage.pojo.GoodsTypeExample;
import com.ssic.shop.manage.pojo.GoodsTypeExample.Criteria;
import com.ssic.util.StringUtils;
import com.ssic.util.base.MyBatisBaseDao;
import com.ssic.util.constants.DataStatus;

/**		
 * <p>Title: GoodsTypeDao </p>
 * <p>Description: 商品类型DAO层</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年8月27日 上午10:03:53	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年8月27日 上午10:03:53</p>
 * <p>修改备注：</p>
 */
@Repository
public class GoodsTypeDao extends MyBatisBaseDao<GoodsType>
{
    @Autowired
    @Getter
    private GoodsTypeMapper mapper;

    /**     
     * findAllBy：后台dataGrid查找所有商品类型
     * @param GoodsType 商品类型pojo
     * @param PageHelperDto    分页对象
     * @return
     * @exception   
     * @author 刘博
     * @date 2015年8月27日 上午10:01:53    
     */
    public List<GoodsType> findAllBy(GoodsType param, PageHelperDto ph)
    {
        GoodsTypeExample example = new GoodsTypeExample();
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
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return mapper.selectByExample(example);
    }

    /**     
     * findCountBy：后台dataGrid查找所有商品类型的数量
     * @param GoodsType 商品类型pojo
     * @return
     * @exception   
     * @author 刘博
     * @date 2015年8月4日 上午11:41:13    
     */
    public int findCountBy(GoodsType param)
    {
        GoodsTypeExample example = new GoodsTypeExample();
        Criteria criteria = example.createCriteria();

        criteria.andStatEqualTo(DataStatus.ENABLED);

        if (!StringUtils.isEmpty(param.getId()))
        {
            criteria.andIdEqualTo(param.getId());
        }
        int count = mapper.countByExample(example);
        return count;
    }

    /**     
     * findByTypeName：一句话描述方法功能
     * @param typeName
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年8月27日 下午5:59:58	 
     */
    public GoodsType findByTypeName(String typeName, String typeId)
    {
        GoodsTypeExample example = new GoodsTypeExample();
        Criteria criteria = example.createCriteria();
        criteria.andStatEqualTo(DataStatus.ENABLED);
        if (!StringUtils.isEmpty(typeName))
        {
            criteria.andTypeNameEqualTo(typeName);
        }
        if (!StringUtils.isEmpty(typeId))
        {
            criteria.andIconNotEqualTo(typeId);
        }
        List<GoodsType> list = mapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list))
        {
            return list.get(0);
        }
        return null;

    }
}
