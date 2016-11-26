package com.ssic.catering.base.dao;

import java.util.List;

import lombok.Getter;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.catering.base.dto.NextWeekCarteDtos;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.mapper.NextWeekCarteExMapper;
import com.ssic.catering.base.mapper.NextWeekCarteMapper;
import com.ssic.catering.base.pojo.NextWeekCarte;
import com.ssic.catering.base.pojo.NextWeekCarteExample;
import com.ssic.catering.base.pojo.NextWeekCarteExample.Criteria;

@Repository
public class NextWeekCarteDao
{

    @Autowired
    @Getter
    private NextWeekCarteMapper nextWeekCarteMapper;

    @Autowired
    private NextWeekCarteExMapper nextWeekCarteExMapper;

    /**
     * findAll：查询所有菜单信息
     * 
     * @param id
     * @return
     * @exception
     * @author yuanbin
     * @date 2015年8月5日 下午1:34:14
     */
    public List<NextWeekCarte> findAll(NextWeekCarte param, PageHelperDto ph)
    {
        NextWeekCarteExample example = new NextWeekCarteExample();
        Criteria criteria = example.createCriteria();
        example.setDistinct(true);
        if (!StringUtils.isEmpty(param.getId()))
        {
            criteria.andIdEqualTo(param.getId());
        }
        if (!StringUtils.isEmpty(param.getDishName()))
        {
            criteria.andDishNameEqualTo(param.getDishName());
        }
        if (!StringUtils.isEmpty(param.getCafetoriumId()))
        {
            criteria.andCafetoriumIdEqualTo(param.getCafetoriumId());
        }
        if (!StringUtils.isEmpty(param.getCarteWeek()))
        {
            criteria.andCarteWeekEqualTo(param.getCarteWeek());
        }
        if (!StringUtils.isEmpty(param.getCarteTypeId()))
        {
            criteria.andCarteTypeIdEqualTo(param.getCarteTypeId());
        }
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

        return nextWeekCarteMapper.selectByExample(example) ;
    }

    /**     
     * findNextWeekCarteList：重复菜单的数量
     * @param nextWeekCarteDto
     * @return
     * @exception	
     * @author yuanbin
     * @date 2015年8月6日 下午5:56:47	 
     */
    public List<NextWeekCarteDtos> findNextWeekCarteList()
    {
        return nextWeekCarteExMapper.findNextWeekCarteList();
    }

    public int insertNextWeekCarte(NextWeekCarte nextWeekCarte)
    {
        return nextWeekCarteMapper.insert(nextWeekCarte);
    }

    /**     
     * count：一句话描述方法功能
     * @return
     * @exception	
     * @author yuanbin
     * @date 2015年8月6日 下午7:47:09	 
     */
    public int count()
    {
        return nextWeekCarteExMapper.count();
    }

    /**     
     * findDistinctCarteWeekByCafeId：一句话描述方法功能
     * @param id
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年8月10日 下午3:03:39	 
     */
    public List<NextWeekCarteDtos> findDistinctCarteWeekByCafeId(String id)
    {
        return nextWeekCarteExMapper.findDistinctCarteWeekByCafeId(id);
    }

    public String findDishPercent(NextWeekCarteDtos dtos)
    {
        return nextWeekCarteExMapper.findDishPercent(dtos);
    }

    public List<NextWeekCarteDtos> findAllDistinctDish(NextWeekCarteDtos nextWeekCarteDtos)
    {
        return nextWeekCarteExMapper.findAllDistinctDish(nextWeekCarteDtos);
    }

    /**
     * findNextWeekCarteByCarteWeek：按照周数获取本周的评价
     * @param localWeek 周数
     * @return
     * @exception	
     * @author 张亚伟
     * @date 2015年8月17日 上午11:23:56
     */
	public List<NextWeekCarte> findNextWeekCarteByCarteWeek(int localWeek) {
		NextWeekCarteExample example=new NextWeekCarteExample();
		Criteria criteria = example.createCriteria();
		criteria.andCarteWeekEqualTo(localWeek+"");
		return nextWeekCarteMapper.selectByExample(example);
	}

	
	/**
     * findNextWeekCarteByCarteWeek：按照周数和用户的唯一标识获取本周的评价
     * @param localWeek 周数
     * @param userId 微信用户的OpenId
     * @return
     * @exception	
     * @author 张亚伟
     * @date 2015年8月17日 上午11:23:56
     */
	public List<NextWeekCarte> findNextWeekCarteByCarteWeekAndOpenId(
			int localWeek, String userId) {
		NextWeekCarteExample example=new NextWeekCarteExample();
		Criteria criteria = example.createCriteria();
		criteria.andCarteWeekEqualTo(localWeek+"");
		criteria.andOpenIdEqualTo(userId);
		return nextWeekCarteMapper.selectByExample(example);
	}

	
	/**
     * findNextWeekCarteByCarteWeekAndOpenIdAndCafetoriumId：按照周数和用户的唯一标识获取食堂的本周的评价
     * @param localWeek 周数
     * @param userId 微信用户的OpenId
     * @param cafetoriumId 食堂Id
     * @return
     * @exception	
     * @author 张亚伟
     * @date 2015年8月17日 上午11:23:56
     */
	public List<NextWeekCarte> findNextWeekCarteByCarteWeekAndOpenIdAndCafetoriumId(
			int localWeek, String userId, String cafetoriumId) {
		NextWeekCarteExample example=new NextWeekCarteExample();
		Criteria criteria = example.createCriteria();
		criteria.andCarteWeekEqualTo(localWeek+"");
		criteria.andOpenIdEqualTo(userId);
		criteria.andCafetoriumIdEqualTo(cafetoriumId);
		example.setOrderByClause("create_time asc");
		return nextWeekCarteMapper.selectByExample(example);
	}

	
	/**
	 * findNextWeekVoteByLocalWeekAndCarteNameAndCafetoriumId：获取菜单投票数
	 * @param localWeek 周数
	 * @param carteName 菜品名称
	 * @param cafetoriumId 食堂ID
	 * @return
	 * @exception	
	 * @author 张亚伟
	 * @date 2015年9月16日 上午10:14:36
	 */
	public Integer findNextWeekVoteByLocalWeekAndCarteNameAndCafetoriumId(
			int localWeek, String carteName, String cafetoriumId) {
		
		NextWeekCarteExample example=new NextWeekCarteExample();
		Criteria criteria = example.createCriteria();
		criteria.andCarteWeekEqualTo(localWeek+"");
		criteria.andDishNameEqualTo(carteName);
		criteria.andCafetoriumIdEqualTo(cafetoriumId);
		return nextWeekCarteMapper.countByExample(example);
	}
}
