package com.ssic.game.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.catering.base.dao.NextWeekCarteDao;
import com.ssic.catering.base.dto.NextWeekCarteDto;
import com.ssic.catering.base.dto.NextWeekCarteDtos;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.pojo.Carte;
import com.ssic.catering.base.pojo.NextWeekCarte;
import com.ssic.game.app.service.NextMenuOperateSerivce;
import com.ssic.util.BeanUtils;

/**		
 * <p>Title: NextMenuOperateSerivce </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author yuanbin
 * @date 2015年8月4日 下午13:37:00	
 * @version 1.0
 */
@Service
public class NextMenuOperateSerivceImpl implements NextMenuOperateSerivce
{

    @Autowired
    private NextWeekCarteDao nextWeekCarteDao;

    /**     
     * queryProjectInfo：查询项目信息
     * @param userId
     * @return  
     * @exception	
     * @author yuanbin
     * @date 2015年8月5日 下午1:32:58	 
     */
    public List<NextWeekCarteDto> findAll(NextWeekCarteDto nextWeekCarteDto, PageHelperDto phDto)
    {

        List<NextWeekCarteDto> result = new ArrayList<NextWeekCarteDto>();
        NextWeekCarte nextWeekCarte = new NextWeekCarte();
        BeanUtils.copyProperties(nextWeekCarteDto, nextWeekCarte);
        List<NextWeekCarte> list = nextWeekCarteDao.findAll(nextWeekCarte, phDto);
        if (!CollectionUtils.isEmpty(list))
        {
            result = BeanUtils.createBeanListByTarget(list, NextWeekCarteDto.class);
            return result;
        }
        return result;
    }

    /**     
     * findNextWeekCarteList：重复菜单的数量
     * @param nextWeekCarteDto
     * @return
     * @exception	
     * @author yuanbin
     * @date 2015年8月6日 下午5:56:47	 
     */
    @Override
    public List<NextWeekCarteDtos> findNextWeekCarteList()
    {
        return nextWeekCarteDao.findNextWeekCarteList();

    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.app.service.NextMenuOperateSerivce#count()   
    */
    @Override
    public int count()
    {

        return nextWeekCarteDao.count();
    }

    @Override
    public int insertNextWeekCarte(NextWeekCarte nextWeekCarte)
    {
        return nextWeekCarteDao.insertNextWeekCarte(nextWeekCarte);
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.app.service.NextMenuOperateSerivce#findDistinctCarteWeekByCafeId(java.lang.String)   
    */
    @Override
    public List<NextWeekCarteDtos> findDistinctCarteWeekByCafeId(String id)
    {
        return nextWeekCarteDao.findDistinctCarteWeekByCafeId(id);

    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.app.service.NextMenuOperateSerivce#findDishPercent(com.ssic.catering.base.dto.NextWeekCarteDtos)   
    */
    @Override
    public String findDishPercent(NextWeekCarteDtos dtos)
    {
        return nextWeekCarteDao.findDishPercent(dtos);
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.game.app.service.NextMenuOperateSerivce#findAllDistinctDish(com.ssic.catering.base.dto.NextWeekCarteDtos)   
    */
    @Override
    public List<NextWeekCarteDtos> findAllDistinctDish(NextWeekCarteDtos nextWeekCarteDtos)
    {
        return nextWeekCarteDao.findAllDistinctDish(nextWeekCarteDtos);
    }

	@Override
	public List<NextWeekCarte> findNextWeekCarteByCarteWeek(int localWeek) {
		return nextWeekCarteDao.findNextWeekCarteByCarteWeek(localWeek);
	}

	@Override
	public List<NextWeekCarte> findNextWeekCarteByCarteWeekAndOpenId(
			int localWeek, String userId) {
		return nextWeekCarteDao.findNextWeekCarteByCarteWeekAndOpenId(localWeek,userId);
	}

	@Override
	public List<NextWeekCarte> findNextWeekCarteByCarteWeekAndOpenIdAndCafetoriumId(
			int localWeek, String userId, String cafetoriumId) {
		// TODO 添加方法注释
		return nextWeekCarteDao.findNextWeekCarteByCarteWeekAndOpenIdAndCafetoriumId(localWeek,userId,cafetoriumId);
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
	@Override
	public Integer findNextWeekVoteByLocalWeekAndCarteNameAndCafetoriumId(
			int localWeek, String carteName, String cafetoriumId) {
		// TODO 添加方法注释
		Integer count=nextWeekCarteDao.findNextWeekVoteByLocalWeekAndCarteNameAndCafetoriumId(localWeek,carteName,cafetoriumId);
		return count;
	}

}
