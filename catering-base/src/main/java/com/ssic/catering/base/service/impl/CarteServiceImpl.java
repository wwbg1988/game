/**
 * 
 */
package com.ssic.catering.base.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ssic.catering.base.dao.CarteDao;
import com.ssic.catering.base.dto.CarteDto;
import com.ssic.catering.base.dto.CarteTypeDto;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.pojo.Carte;
import com.ssic.catering.base.pojo.CarteType;
import com.ssic.catering.base.service.ICarteService;
import com.ssic.catering.base.service.ICarteTypeService;
import com.ssic.catering.common.util.DateUtil;
import com.ssic.util.BeanUtils;
import com.ssic.util.StringUtils;
import com.ssic.util.constants.DataStatus;

/**		
 * <p>Title: CarteServiceImpl </p>
 * <p>Description: 菜单service层</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年8月4日 上午9:12:31	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年8月4日 上午9:12:31</p>
 * <p>修改备注：</p>
 */
@Service
public class CarteServiceImpl implements ICarteService
{

    @Autowired
    private CarteDao carteDao;
    @Autowired
    private ICarteTypeService carteTypeService;

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.IDishService#findALL(java.lang.String, com.ssic.catering.base.dto.PageHelperDto)   
    */
    @Override
    public List<CarteDto> findALL(String carteTypeId, PageHelperDto phDto)
    {
        List<CarteDto> result = new ArrayList<CarteDto>();
        Carte carte = new Carte();
        carte.setCarteTypeId(carteTypeId);
        List<Carte> list = carteDao.findAll(carte, phDto);
        if (!CollectionUtils.isEmpty(list))
        {
            result = BeanUtils.createBeanListByTarget(list, CarteDto.class);
            for (CarteDto dto : result)
            {
                if (!StringUtils.isEmpty(dto.getCarteTypeId()))
                {
                    CarteTypeDto typeDto = carteTypeService.findById(dto.getCarteTypeId());
                    dto.setCarteTypeName(typeDto.getCarteTypeName());
                }
            }
            return result;
        }
        return result;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.IDishService#findCount(java.lang.String)   
    */
    @Override
    public int findCount(String carteTypeId)
    {
        Carte cart = new Carte();
        cart.setCarteTypeId(carteTypeId);
        int counts = carteDao.findCount(cart);
        return counts;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.ICarteService#findById(java.lang.String)   
    */
    @Override
    public CarteDto findById(String id)
    {
        Carte carte = carteDao.selectByPrimaryKey(id);
        CarteDto dto = new CarteDto();
        BeanUtils.copyProperties(carte, dto);
        return dto;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.ICarteService#delete(com.ssic.catering.base.dto.CarteDto)   
    */
    @Override
    public void delete(CarteDto tempCarte)
    {
        Carte carte = carteDao.selectByPrimaryKey(tempCarte.getId());
        carte.setStat(DataStatus.DISABLED);
        carte.setLastUpdateTime(new Date());
        carteDao.updateByPrimaryKey(carte);

    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.ICarteService#findALLByTypeId(java.lang.String)   
    */
    @Override
    public List<Carte> findALLByTypeId(String typeId)
    {
        return carteDao.findALLByTypeId(typeId);
    }

    @Override
    public List<Carte> getCarteListByCarteWeek(CarteDto carteDto)
    {

        return carteDao.getCarteListByCarteWeek(carteDto);
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.ICarteService#insertCartExcel(java.util.List)   
    */
    @Override
    public void insertCartExcel(List<CarteDto> list)
    {
        //当前是一年中的第几周
        int carteWeek = DateUtil.localWeek(new Date());
      
        if (!CollectionUtils.isEmpty(list))
        {
            String cafeId = list.get(0).getCafetoriumId();
            if(!StringUtils.isEmpty(cafeId)){
                Carte param = new Carte();
                param.setCarteWeek(carteWeek);
                param.setCafetoriumId(cafeId);
                //查找数据库中本周已经上传的菜单;如果本周已经上传过，则执行清空操作，否则新增 ;
                List<Carte> carteList = carteDao.findAll(param, null);
                if (!CollectionUtils.isEmpty(carteList))
                {
                    for (Carte carte : carteList)
                    {//删除菜品;
                        carte.setStat(0);
                        carteDao.updateByPrimaryKey(carte);
                    }
                }
            }
          
            for (CarteDto dto : list)
            {//新增菜品到数据库;
                CarteType type = carteTypeService.finByName(dto.getCarteTypeName(), dto.getCafetoriumId());
                //如果类型不存在，则跳出当前循环;
                if (type == null)
                {
                    continue;
                }
                Carte param1 = new Carte();
                param1.setCarteName(dto.getCarteName());
                param1.setCarteTypeId(type.getId());
                param1.setCarteWeek(carteWeek);
                List<Carte> carteParam = carteDao.findAll(param1, null);
                //如果用户上传的菜品名称重复，则只保存一条;
                if (CollectionUtils.isEmpty(carteParam))
                {
                    dto.setId(UUID.randomUUID().toString());
                    dto.setCarteTypeId(type.getId());
                    dto.setCreateTime(new Date());
                    dto.setLastUpdateTime(new Date());
                    dto.setStat(DataStatus.ENABLED);
                    dto.setCarteWeek(carteWeek);
                    Carte carte = new Carte();
                    BeanUtils.copyProperties(dto, carte);
                    carteDao.insert(carte);
                }
            }
        }
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.ICarteService#findAllByCafetorId(java.lang.String)   
    */
    @Override
    public List<Carte> findAllByCafetorId(String id)
    {
        return carteDao.findAllByCafetorId(id);
    }

    @Override
    public void updateImage(CarteDto carteDto)
    {
        // TODO Auto-generated method stub
        Carte carte = new Carte();
        BeanUtils.copyProperties(carteDto, carte);
        carteDao.updateImage(carte);
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.ICarteService#update(com.ssic.catering.base.dto.CarteDto)   
    */
    @Override
    public void update(CarteDto carteDto)
    {
        Carte carte = new Carte();
        BeanUtils.copyProperties(carteDto, carte);
        carte.setStat(DataStatus.ENABLED);
        carte.setLastUpdateTime(new Date());
        carteDao.updateByPrimaryKeySelective(carte);
    }
    
    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.ICarteService#getLastMenuByCafetoriumId(java.lang.String)
     */
    @Override
    public List<CarteDto> getLastMenuByCafetoriumId(String cafetoriumId)
    {
        if(!StringUtils.isEmpty(cafetoriumId))
        {
            return carteDao.findLastMenuByCafetoriumId(cafetoriumId);
        }
        
        return null;
    }
    
    @Override
    public int updateBy(CarteDto carteDto)
    {
        if(carteDto != null)
        {
            Carte param = new Carte();
            BeanUtils.copyProperties(carteDto, param);
            return carteDao.updateBy(param);
        }
        
        return 0;
    }
}
