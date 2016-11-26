/**
 * 
 */
package com.ssic.catering.base.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.mapper.CarteTypeMapper;
import com.ssic.catering.base.pojo.CarteType;
import com.ssic.catering.base.pojo.CarteTypeExample;
import com.ssic.catering.base.pojo.CarteTypeExample.Criteria;
import com.ssic.util.StringUtils;
import com.ssic.util.base.MyBatisBaseDao;
import com.ssic.util.constants.DataStatus;

/**		
 * <p>Title: CarteTypeDao </p>
 * <p>Description: 菜品类型Dao</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年8月4日 上午9:20:07	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年8月4日 上午9:20:07</p>
 * <p>修改备注：</p>
 */
@Repository
public class CarteTypeDao extends MyBatisBaseDao<CarteType>
{
    @Autowired
    @Getter
    private CarteTypeMapper mapper;

    /**     
     * findAllBy：一句话描述方法功能
     * @param cartType
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年8月4日 上午11:33:53	 
     */
    public List<CarteType> findAllBy(CarteType param, PageHelperDto ph)
    {
        CarteTypeExample example = new CarteTypeExample();
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
        if (!StringUtils.isEmpty(param.getCarteTypeName()))
        {
            criteria.andCarteTypeNameLike("%" + param.getCarteTypeName() + "%");

        }
        if (!StringUtils.isEmpty(param.getCafetoriumId()))
        {
            criteria.andCafetoriumIdEqualTo(param.getCafetoriumId());
        }
        if (!StringUtils.isEmpty(param.getUpperLimit()))
        {
            criteria.andUpperLimitEqualTo(param.getUpperLimit());
        }

        criteria.andStatEqualTo(1);
        return mapper.selectByExample(example);
    }

    /**     
     * findCountBy：一句话描述方法功能
     * @param cartType
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年8月4日 上午11:41:13	 
     */
    public int findCountBy(CarteType param)
    {
        CarteTypeExample example = new CarteTypeExample();
        Criteria criteria = example.createCriteria();

        criteria.andStatEqualTo(1);

        if (!StringUtils.isEmpty(param.getId()))
        {
            criteria.andIdEqualTo(param.getId());
        }
        if (!StringUtils.isEmpty(param.getCarteTypeName()))
        {
            criteria.andCarteTypeNameLike("%" + param.getCarteTypeName() + "%");

        }

        if (!StringUtils.isEmpty(param.getUpperLimit()))
        {
            criteria.andUpperLimitEqualTo(param.getUpperLimit());
        }
        if (!StringUtils.isEmpty(param.getCafetoriumId()))
        {
            criteria.andCafetoriumIdEqualTo(param.getCafetoriumId());
        }
        int count = mapper.countByExample(example);
        return count;
    }

    /**     
     * findById：一句话描述方法功能
     * @param id
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年8月4日 下午3:29:36	 
     */
    public CarteType findById(String id)
    {
        CarteTypeExample example = new CarteTypeExample();
        Criteria criteria = example.createCriteria();
        example.setDistinct(true);
        criteria.andStatEqualTo(1);

        if (!StringUtils.isEmpty(id))
        {
            criteria.andIdEqualTo(id);

            example.setOrderByClause("create_time asc");

            List<CarteType> list = mapper.selectByExample(example);
            if (!CollectionUtils.isEmpty(list))
            {
                return list.get(0);
            }
        }

        return null;
    }

    /**     
     * findByName：一句话描述方法功能
     * @param carteTypeName
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年8月6日 下午1:03:43	 
     */
    public CarteType findByName(String carteTypeName, String cafetoriumId)
    {
        CarteTypeExample example = new CarteTypeExample();
        Criteria criteria = example.createCriteria();
        example.setDistinct(true);
        criteria.andStatEqualTo(1);

        if (!StringUtils.isEmpty(carteTypeName))
        {
            criteria.andCarteTypeNameEqualTo(carteTypeName);
        }
        if (!StringUtils.isEmpty(cafetoriumId))
        {
            criteria.andCafetoriumIdEqualTo(cafetoriumId);
        }
        List<CarteType> list = mapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list))
        {
            return list.get(0);
        }
        return null;
    }

    /**     
     * finByCafetoriumId：一句话描述方法功能
     * @param cafetoriumId
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年8月7日 上午10:12:30	 
     */
    public List<CarteType> finByCafetoriumId(String cafetoriumId)
    {
        CarteTypeExample example = new CarteTypeExample();
        Criteria criteria = example.createCriteria();
        example.setDistinct(true);
        criteria.andStatEqualTo(DataStatus.ENABLED);
        if (!StringUtils.isEmpty(cafetoriumId))
        {
            criteria.andCafetoriumIdEqualTo(cafetoriumId);
        }
        example.setOrderByClause("create_time asc");
        return mapper.selectByExample(example);
    }
}
