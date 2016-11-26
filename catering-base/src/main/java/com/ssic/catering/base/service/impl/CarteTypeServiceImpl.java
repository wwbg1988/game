/**
 * 
 */
package com.ssic.catering.base.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssic.catering.base.dao.CarteTypeDao;
import com.ssic.catering.base.dto.CafetoriumDto;
import com.ssic.catering.base.dto.CarteTypeDto;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.pojo.CarteType;
import com.ssic.catering.base.service.ICafetoriumService;
import com.ssic.catering.base.service.ICarteService;
import com.ssic.catering.base.service.ICarteTypeService;
import com.ssic.util.BeanUtils;
import com.ssic.util.constants.DataStatus;

/**     
 * <p>Title: CarteTypeServiceImpl </p>
 * <p>Description: 菜品类型Service</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator    
 * @date 2015年8月4日 上午9:18:25    
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年8月4日 上午9:18:25</p>
 * <p>修改备注：</p>
 */
@Service
public class CarteTypeServiceImpl implements ICarteTypeService
{

    @Autowired
    private CarteTypeDao carteTypeDao;
    @Autowired
    private ICarteService carteService;
    @Autowired
    private ICafetoriumService cafetoriumService;

    /** 
     *添加菜品类型  
     */
    @Override
    public void add(CarteTypeDto carteTypeDto)
    {
        CarteType cartType = new CarteType();
        BeanUtils.copyProperties(carteTypeDto, cartType);
        cartType.setStat(DataStatus.ENABLED);
        cartType.setCreateTime(new Date());
        carteTypeDao.insert(cartType);

    }

    /** 
     * 查找菜品类型  
     */
    @Override
    public List<CarteTypeDto> findALL(CarteTypeDto carteTypeDto, PageHelperDto phDto)
    {
        List<CarteTypeDto> result = new ArrayList<CarteTypeDto>();
        CarteType cartType = new CarteType();
        BeanUtils.copyProperties(carteTypeDto, cartType);
        List<CarteType> list = carteTypeDao.findAllBy(cartType, phDto);
        if (list != null && list.size() > 0)
        {
            result = BeanUtils.createBeanListByTarget(list, CarteTypeDto.class);

        }
        for (CarteTypeDto dto : result)
        {
            if (!StringUtils.isEmpty(dto.getCafetoriumId()))
            {
                CafetoriumDto caDto = cafetoriumService.findById(dto.getCafetoriumId());
                if (caDto != null && !StringUtils.isEmpty(caDto.getCafeName()))
                {
                    dto.setCafetoriumName(caDto.getCafeName());
                }
            }
        }
        return result;
    }

    /** 
     *查找菜品类型数量  
     */
    @Override
    public int findCount(CarteTypeDto carteTypeDto)
    {
        CarteType cartType = new CarteType();
        BeanUtils.copyProperties(carteTypeDto, cartType);
        int counts = carteTypeDao.findCountBy(cartType);
        return counts;
    }

    /** 
     *删除菜品类型  
     */
    @Override
    public void delete(CarteTypeDto carteTypeDto)
    {
        CarteType cartType = new CarteType();
        BeanUtils.copyProperties(carteTypeDto, cartType);
        cartType.setStat(DataStatus.DISABLED);
        cartType.setLastUpdateTime(new Date());
        carteTypeDao.updateByPrimaryKey(cartType);
    }

    /** 
     *根据id查找菜品类型
     */
    @Override
    public CarteTypeDto findById(String id)
    {
        CarteTypeDto carteTypeDto = new CarteTypeDto();
        CarteType type = carteTypeDao.findById(id);
        BeanUtils.copyProperties(type, carteTypeDto);
        return carteTypeDto;
    }

    /** 
     *更新菜品类型  
     */
    @Override
    public void update(CarteTypeDto carteTypeDto)
    {
        CarteType cartType = new CarteType();
        BeanUtils.copyProperties(carteTypeDto, cartType);
        cartType.setStat(DataStatus.ENABLED);
        cartType.setLastUpdateTime(new Date());
        carteTypeDao.updateByPrimaryKeySelective(cartType);
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.ICarteTypeService#finByName(java.lang.String)   
    */
    @Override
    public CarteType finByName(String carteTypeName, String cafetoriumId)
    {
        CarteType type = carteTypeDao.findByName(carteTypeName, cafetoriumId);
        return type;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.ICarteTypeService#finByCafetorium(java.lang.String)   
    */
    @Override
    public List<CarteTypeDto> finByCafetoriumId(String cafetoriumId)
    {
        List<CarteTypeDto> result = new ArrayList<CarteTypeDto>();
        List<CarteType> list = carteTypeDao.finByCafetoriumId(cafetoriumId);
        if (list != null && list.size() > 0)
        {
            result = BeanUtils.createBeanListByTarget(list, CarteTypeDto.class);
        }
        return result;
    }
    
    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.ICarteTypeService#updateBy(com.ssic.catering.base.dto.CarteTypeDto)
     */
    @Override
    public int updateBy(CarteTypeDto carteTypeDto)
    {
        if(carteTypeDto != null)
        {
            CarteType param = new CarteType();
            BeanUtils.copyProperties(carteTypeDto, param);
            return carteTypeDao.updateByPrimaryKeySelective(param);
        }
        return 0;
    }

}
