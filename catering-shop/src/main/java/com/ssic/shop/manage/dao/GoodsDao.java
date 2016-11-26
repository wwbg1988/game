/**
 * 
 */
package com.ssic.shop.manage.dao;

import java.util.List;

import lombok.Getter;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssic.shop.manage.dto.GoodsDto;
import com.ssic.shop.manage.dto.PageHelperDto;
import com.ssic.shop.manage.mapper.GoodsMapper;
import com.ssic.shop.manage.pojo.Goods;
import com.ssic.shop.manage.pojo.GoodsExample;
import com.ssic.shop.manage.pojo.GoodsExample.Criteria;
import com.ssic.util.StringUtils;
import com.ssic.util.base.MyBatisBaseDao;
import com.ssic.util.constants.DataStatus;

/**		
 * <p>Title: GoodsDao </p>
 * <p>Description: 商品Dao</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年8月27日 上午9:57:26	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年8月27日 上午9:57:26</p>
 * <p>修改备注：</p>
 */
@Repository
public class GoodsDao extends MyBatisBaseDao<Goods>
{
    @Autowired
    @Getter
    private GoodsMapper mapper;

    /**     
     * findAllBy：后台dataGrid查找所有商品
     * @param cafeList 
     * @param Goods 商品pojo
     * @param PageHelperDto  分页对象
     * @return
     * @exception   
     * @author 刘博
     * @date 2015年8月27日 上午10:01:53    
     */
    public List<Goods> findAllBy(Goods param, PageHelperDto ph, List<String> cafeList)
    {
        GoodsExample example = new GoodsExample();
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
        if (!StringUtils.isEmpty(param.getGoodsName()))
        {
            criteria.andGoodsNameLike("%" + param.getGoodsName() + "%");
        }
        if (param.getPrice() != null)
        {
            criteria.andPriceEqualTo(param.getPrice());
        }
        if (param.getSalesPrice() != null)
        {
            criteria.andSalesPriceEqualTo(param.getSalesPrice());
        }
        if (param.getCountLimit() != null)
        {
            criteria.andCountLimitEqualTo(param.getCountLimit());
        }
        if (param.getPercent() != null)
        {
            criteria.andPercentEqualTo(param.getPercent());
        }
        if (!StringUtils.isEmpty(param.getGoodsTypeId()))
        {
            criteria.andGoodsTypeIdEqualTo(param.getGoodsTypeId());
        }
        if (!StringUtils.isEmpty(param.getCafetoriumId()))
        {
            criteria.andCafetoriumIdEqualTo(param.getCafetoriumId());
        }
        if (!CollectionUtils.isEmpty(cafeList))
        {
            criteria.andCafetoriumIdIn(cafeList);
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return mapper.selectByExample(example);
    }

    /**     
     * findCountBy：后台dataGrid查找所有商品的数量
     * @param Goods 商品pojo
     * @return
     * @exception   
     * @author 刘博
     * @date 2015年8月4日 上午11:41:13    
     */
    public int findCountBy(Goods param)
    {
        GoodsExample example = new GoodsExample();
        Criteria criteria = example.createCriteria();

        criteria.andStatEqualTo(DataStatus.ENABLED);

        if (!StringUtils.isEmpty(param.getId()))
        {
            criteria.andIdEqualTo(param.getId());
        }
        if (!StringUtils.isEmpty(param.getGoodsName()))
        {
            criteria.andGoodsNameLike("%" + param.getGoodsName() + "%");
        }
        if (param.getPrice() != null)
        {
            criteria.andPriceEqualTo(param.getPrice());
        }
        if (param.getSalesPrice() != null)
        {
            criteria.andSalesPriceEqualTo(param.getSalesPrice());
        }
        if (param.getCountLimit() != null)
        {
            criteria.andCountLimitEqualTo(param.getCountLimit());
        }
        if (param.getPercent() != null)
        {
            criteria.andPercentEqualTo(param.getPercent());
        }
        if (!StringUtils.isEmpty(param.getGoodsTypeId()))
        {
            criteria.andGoodsTypeIdEqualTo(param.getGoodsTypeId());
        }
        if (!StringUtils.isEmpty(param.getCafetoriumId()))
        {
            criteria.andCafetoriumIdEqualTo(param.getCafetoriumId());
        }
        int count = mapper.countByExample(example);
        return count;
    }

    /**     
     * findByGoodsIdAndName：通过id和商品名称查找商品
     * @param goodsId
     * @param goodsName
     * @return
     * @exception   
     * @author 刘博
     * @date 2015年8月27日 下午5:59:58    
     */
    public Goods findByGoodsIdAndName(String goodsId, String goodsName)
    {
        GoodsExample example = new GoodsExample();
        Criteria criteria = example.createCriteria();
        criteria.andStatEqualTo(DataStatus.ENABLED);
        if (!StringUtils.isEmpty(goodsName))
        {
            criteria.andGoodsNameEqualTo(goodsName);
        }
        if (!StringUtils.isEmpty(goodsId))
        {
            criteria.andIdNotEqualTo(goodsId);
        }
        List<Goods> list = mapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list))
        {
            return list.get(0);
        }
        return null;

    }

    /**
     * 
     * findIsTurnAllBy：查询对应食堂轮播的商品的内容
     * @param param
     * @param ph
     * @return
     * @exception   
     * @author pengcheng.yang
     * @date 2015年8月28日 上午9:58:07
     */
    public List<Goods> findIsTurnAllBy(Goods param)
    {
        GoodsExample example = new GoodsExample();
        Criteria criteria = example.createCriteria();
        if (param.getIsTurn() != 0)
        {
            criteria.andIsTurnEqualTo(param.getIsTurn());
        }
        if (!StringUtils.isEmpty(param.getCafetoriumId()))
        {
            criteria.andCafetoriumIdEqualTo(param.getCafetoriumId());
        }
        example.setOrderByClause("create_time desc limit 5");
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return mapper.selectByExample(example);
    }

    /**
     * 
     * findIsturn：分页查询限时特购的商品信息和精品推荐商品信息
     * @param param
     * @param ph
     * @return
     * @exception   
     * @author pengcheng.yang
     * @date 2015年8月28日 上午10:54:13
     */
    public List<Goods> findLimitedQualityAllBy(Goods param, PageHelperDto ph)
    {
        GoodsExample example = new GoodsExample();
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
        if (!StringUtils.isEmpty(param.getGoodsTypeId()))
        {
            criteria.andGoodsTypeIdEqualTo(param.getGoodsTypeId());
        }
        if (!StringUtils.isEmpty(param.getCafetoriumId()))
        {
            criteria.andCafetoriumIdEqualTo(param.getCafetoriumId());
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return mapper.selectByExample(example);
    }

    /**
     * 
     * findlimitStickQualityAllBy：查询首页限时特购和精品推荐商品信息
     * @param param
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年9月16日 下午1:54:28
     */
    public List<Goods> findlimitAndFineStickAllBy(Goods param)
    {
        GoodsExample example = new GoodsExample();
        Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(param.getGoodsTypeId()))
        {
            criteria.andGoodsTypeIdEqualTo(param.getGoodsTypeId());
        }
        if (!StringUtils.isEmpty(param.getCafetoriumId()))
        {
            criteria.andCafetoriumIdEqualTo(param.getCafetoriumId());
        }
        example.setOrderByClause("create_time desc limit 2");
        criteria.andStatEqualTo(DataStatus.ENABLED);
        criteria.andIsStickEqualTo(DataStatus.ENABLED);

        return mapper.selectByExample(example);
    }

    /**
     * 
     * update：更新商品库存
     * @param GoodsDto
     * @return
     * @exception   
     * @author yuanbin
     * @date 2015年9月17日 下午2:10:01
     */
    public void update(Goods record)
    {
        GoodsExample example = new GoodsExample();
        Criteria criteria = example.createCriteria();
        String id = record.getId();
        if (!StringUtils.isEmpty(id))
        {
            criteria.andIdEqualTo(id);
        }
        mapper.updateByPrimaryKey(record);
    }

    /**
     * 
     * findGoodsById：根据商品Id查询对应商品的详细信息
     * @param id
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年9月18日 上午11:42:29
     */
    public Goods findGoodsById(String id)
    {
        Goods goods = mapper.selectByPrimaryKey(id);
        if (goods != null)
        {
            return goods;
        }
        return null;
    }

}
