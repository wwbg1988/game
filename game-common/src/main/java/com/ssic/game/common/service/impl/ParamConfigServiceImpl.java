package com.ssic.game.common.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.game.common.dao.ParamConfigDao;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.dto.ParamConfigDto;
import com.ssic.game.common.pojo.ParamConfig;
import com.ssic.game.common.service.IParamConfigService;
import com.ssic.util.BeanUtils;

@Service
public class ParamConfigServiceImpl implements IParamConfigService
{

    private static final Logger log = Logger.getLogger(ParamConfigServiceImpl.class);

    @Autowired
    private ParamConfigDao paramConfigDao;

    @Override
    public List<ParamConfigDto> findBy(ParamConfigDto paramConfigDto)
    {
        log.info("参数："+paramConfigDto);
        if(paramConfigDto == null)
        {
            log.error("参数ParamConfigDto为空");
            return null;
        }
        
        ParamConfig paramConfig = new ParamConfig();
        BeanUtils.copyProperties(paramConfigDto, paramConfig);
        List<ParamConfig> list = paramConfigDao.findBy(paramConfig);
        
        log.info("查询出来的参数列表："+list);
        
        if(!CollectionUtils.isEmpty(list))
        {
            return BeanUtils.createBeanListByTarget(list, ParamConfigDto.class);
        }
        
        log.error("查询出来的参数列表为空");
        
        return null;
    }

    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.game.common.service.IParamConfigService#insertParam(com.ssic.game.common.dto.ParamConfigDto)
     */
    @Override
    public void insertParam(ParamConfigDto paramConfigDto)
    {
        // TODO Auto-generated method stub
        ParamConfig paramConfig = new ParamConfig();
        BeanUtils.copyProperties(paramConfigDto, paramConfig);
        paramConfigDao.insertParam(paramConfig);
    }

    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.game.common.service.IParamConfigService#updateParam(com.ssic.game.common.dto.ParamConfigDto)
     */
    @Override
    public void updateParam(ParamConfigDto paramConfigDto)
    {
        // TODO Auto-generated method stub
        ParamConfig paramConfig = new ParamConfig();
        BeanUtils.copyProperties(paramConfigDto, paramConfig);
        paramConfigDao.updateParam(paramConfig);
    }

    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.game.common.service.IParamConfigService#findByID(java.lang.String)
     */
    @Override
    public ParamConfigDto findByID(String id)
    {
        // TODO Auto-generated method stub
        ParamConfig paramConfig = paramConfigDao.findById(id);
        ParamConfigDto paramConfigDto = new ParamConfigDto();
        BeanUtils.copyProperties(paramConfig, paramConfigDto);
        return paramConfigDto;
    }
    
    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.game.common.service.IParamConfigService#getParametersBy(com.ssic.game.common.dto.ParamConfigDto)
     */
    @Override
    public Map<String, String> getParametersBy(ParamConfigDto paramConfigDto)
    {
        log.info("参数列表："+paramConfigDto);
        if(paramConfigDto == null)
        {
            log.error("参数paramConfigDto不能为空");
            return null;
        }
        
        ParamConfig param = new ParamConfig();
        BeanUtils.copyProperties(paramConfigDto, param);
        
        return paramConfigDao.findParametersBy(param);
    }
    
    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.game.common.service.IParamConfigService#getParamConfigBy(com.ssic.game.common.dto.ParamConfigDto, com.ssic.game.common.dto.PageHelperDto)
     */
    @Override
    public List<ParamConfigDto> getParamConfigBy(ParamConfigDto paramConfigDto,
        PageHelperDto pageHelperDto)
    {
        log.info("参数列表："+paramConfigDto);
        if(paramConfigDto == null)
        {
            log.error("参数paramConfigDto不能为空");
            return null;
        }
        
        log.info("参数列表："+pageHelperDto);
        if(pageHelperDto == null)
        {
            log.error("参数pageHelperDto不能为空");
            return null;
        }
        
        ParamConfig param = new ParamConfig();
        BeanUtils.copyProperties(paramConfigDto, param);
        
        List<ParamConfig> result = paramConfigDao.findParamForPageBy(param, pageHelperDto);
        if(!CollectionUtils.isEmpty(result))
        {
            return BeanUtils.createBeanListByTarget(result, ParamConfigDto.class);
        }
        
        return null;
    }
    
    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.game.common.service.IParamConfigService#getRowsBy(com.ssic.game.common.dto.ParamConfigDto)
     */
    @Override
    public long getRowsBy(ParamConfigDto paramConfigDto)
    {
        log.info("参数列表："+paramConfigDto);
        if(paramConfigDto == null)
        {
            log.error("参数paramConfigDto不能为空");
            return 0;
        }
        
        ParamConfig param = new ParamConfig();
        BeanUtils.copyProperties(paramConfigDto, param);
        
        return paramConfigDao.findCountBy(param);
    }
    
    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.game.common.service.IParamConfigService#getParamConfigDtoBy(com.ssic.game.common.dto.ParamConfigDto, com.ssic.game.common.dto.PageHelperDto)
     */
    @Override
    public List<ParamConfigDto> getParamConfigDtoBy(ParamConfigDto paramConfigDto,
        PageHelperDto pageHelperDto)
    {
        log.info("参数列表："+paramConfigDto);
        if(paramConfigDto == null)
        {
            log.error("参数paramConfigDto不能为空");
            return null;
        }
        
        log.info("参数列表："+pageHelperDto);
        if(pageHelperDto == null)
        {
            log.error("参数pageHelperDto不能为空");
            return null;
        }
        
        ParamConfig param = new ParamConfig();
        BeanUtils.copyProperties(paramConfigDto, param);
        
        return paramConfigDao.findParamConfigDtoBy(param, pageHelperDto);
    }
    
    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.game.common.service.IParamConfigService#addParamConfig(com.ssic.game.common.dto.ParamConfigDto)
     */
    @Override
    public int addParamConfig(ParamConfigDto paramConfigDto)
    {
        if(paramConfigDto != null)
        {
            ParamConfig param = new ParamConfig();
            BeanUtils.copyProperties(paramConfigDto, param);
            
            return paramConfigDao.insertParamConfig(param);
        }
        
        return 0;
    }
    
    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.game.common.service.IParamConfigService#updateParamConfig(com.ssic.game.common.dto.ParamConfigDto)
     */
    @Override
    public int updateParamConfig(ParamConfigDto paramConfigDto)
    {
        if(paramConfigDto != null)
        {
            ParamConfig param = new ParamConfig();
            BeanUtils.copyProperties(paramConfigDto, param);
            
            return paramConfigDao.updateParamConfig(param);
        }
        
        return 0;
    }

}
