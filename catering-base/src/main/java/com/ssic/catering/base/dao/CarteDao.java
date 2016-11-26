/**
 * 
 */
package com.ssic.catering.base.dao;

import java.util.Date;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssic.catering.base.dto.CarteDto;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.mapper.CarteExMapper;
import com.ssic.catering.base.mapper.CarteMapper;
import com.ssic.catering.base.pojo.Carte;
import com.ssic.catering.base.pojo.CarteExample;
import com.ssic.catering.base.pojo.CarteExample.Criteria;
import com.ssic.util.DateUtils;
import com.ssic.util.StringUtils;
import com.ssic.util.base.MyBatisBaseDao;
import com.ssic.util.constants.DataStatus;

/**		
 * <p>Title: ICarteDao </p>
 * <p>Description: 菜单dao</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年8月4日 上午9:13:46	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年8月4日 上午9:13:46</p>
 * <p>修改备注：</p>
 */
@Repository
public class CarteDao extends MyBatisBaseDao<Carte>
{

    @Autowired
    @Getter
    private CarteMapper mapper;
    
    @Autowired
    private CarteExMapper exMapper;

    /**     
     * findAllBy：一句话描述方法功能
     * @param dish
     * @param phDto
     * @return
     * @exception   
     * @author Administrator
     * @date 2015年8月4日 下午7:55:24     
     */
    public List<Carte> findAll(Carte param, PageHelperDto ph)
    {
        CarteExample example = new CarteExample();
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
        if (!StringUtils.isEmpty(param.getCarteTypeId()))
        {
            criteria.andCarteTypeIdEqualTo(param.getCarteTypeId());

        }
        if (!StringUtils.isEmpty(param.getCarteName()))
        {
            criteria.andCarteNameEqualTo(param.getCarteName());
        }
        if (!StringUtils.isEmpty(param.getCafetoriumId()))
        {
            criteria.andCafetoriumIdEqualTo(param.getCafetoriumId());
        }
        if (param.getCarteWeek() != null)
        {
            criteria.andCarteWeekEqualTo(param.getCarteWeek());
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return mapper.selectByExample(example) ;
    }

    /**     
     * findCountBy：一句话描述方法功能
     * @param CarteDto
     * @return
     * @exception   
     * @author Administrator
     * @date 2015年8月4日 上午11:41:13    
     */
    public int findCount(Carte param)
    {
        CarteExample example = new CarteExample();
        Criteria criteria = example.createCriteria();

        criteria.andStatEqualTo(DataStatus.ENABLED);
        if (!StringUtils.isEmpty(param.getId()))
        {
            criteria.andIdEqualTo(param.getId());
        }
        if (!StringUtils.isEmpty(param.getCarteTypeId()))
        {
            criteria.andCarteTypeIdEqualTo(param.getCarteTypeId());

        }
        if (!StringUtils.isEmpty(param.getCarteName()))
        {
            criteria.andCarteNameEqualTo(param.getCarteName());
        }
        int count = mapper.countByExample(example);
        return count;
    }

    /**     
     * findALLByTypeId：一句话描述方法功能
     * @param typeId
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年8月5日 下午1:13:32	 
     */
    public List<Carte> findALLByTypeId(String typeId)
    {
        CarteExample example = new CarteExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("create_time desc");
        criteria.andStatEqualTo(DataStatus.ENABLED);
        if (!StringUtils.isEmpty(typeId))
        {
            criteria.andCarteTypeIdEqualTo(typeId);
        }
        return mapper.selectByExample(example) ;
    }
    
    /**
	 * 通过当年周数查询菜品列表
	 * 
	 * @param carteDto
	 * @return
	 */
	public List<Carte> getCarteListByCarteWeek(CarteDto carteDto) {
		// 获取今年的第一天和最后一天
		Date startDay = DateUtils.StringToDate(
				DateUtils.format(new Date(), DateUtils.Y) + "-01-01",
				DateUtils.YMD_DASH);
		Date endDay = DateUtils.StringToDate(
				DateUtils.format(new Date(), DateUtils.Y) + "-12-31",
				DateUtils.YMD_DASH);

		CarteExample example = new CarteExample();
		CarteExample.Criteria criteria = example.createCriteria();
		criteria.andCafetoriumIdEqualTo(carteDto.getCafetoriumId());
		criteria.andCreateTimeBetween(startDay, endDay);
		criteria.andCarteWeekEqualTo(carteDto.getCarteWeek());
		criteria.andStatEqualTo(1);
		
		example.setOrderByClause("create_time asc");
		
		return mapper.selectByExample(example);
	}

	
	 /**
     * 通过食堂id查询食堂下的所有菜品
     * 
     * @param id 食堂id
     * @return List<Carte> 
     */
    public List<Carte> findAllByCafetorId(String id)
    {
        CarteExample example = new CarteExample();
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("create_time desc");
        criteria.andStatEqualTo(DataStatus.ENABLED);
        if (!StringUtils.isEmpty(id))
        {
            criteria.andCafetoriumIdEqualTo(id);
        }
        return mapper.selectByExample(example) ;
    }

    public void updateImage(Carte carte){
    	mapper.updateByPrimaryKeySelective(carte);
    }

    /**
     * 	 
     * @author 朱振	
     * @date 2015年10月10日 下午2:14:32	
     * @version 1.0
     * @param cafetoriumId
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年10月10日 下午2:14:32</p>
     * <p>修改备注：</p>
     */
    public List<CarteDto> findLastMenuByCafetoriumId(String cafetoriumId)
    {
        if(!StringUtils.isEmpty(cafetoriumId))
        {
            return exMapper.selectMenuByCafetoriumId(cafetoriumId);
        }
        
        return null;
    }
    
    /**
     * 	 
     * @author 朱振	
     * @date 2015年11月4日 上午9:57:53	
     * @version 1.0
     * @param carte
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月4日 上午9:57:53</p>
     * <p>修改备注：</p>
     */
    public int updateBy(Carte carte)
    {
       if(carte != null)
       {
           return mapper.updateByPrimaryKeySelective(carte);
       }
       
       return 0;
    }
    
}
