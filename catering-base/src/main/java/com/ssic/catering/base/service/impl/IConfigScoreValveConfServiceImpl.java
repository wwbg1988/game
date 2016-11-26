package com.ssic.catering.base.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssic.catering.base.dao.ConfigScoreValveConfDao;
import com.ssic.catering.base.dto.ConfigScoreValveConfDto;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.pojo.ConfigScoreValveConf;
import com.ssic.catering.base.service.IConfigScoreValveConfService;
import com.ssic.util.BeanUtils;
import com.ssic.util.constants.DataStatus;

/**
 * 
 * <p>
 * Title: IConfigScoreValveConfServiceImpl
 * </p>
 * <p>
 * Description: 查询评分设定的阀值
 * </p>
 * <p>
 * Copyright (c) 2015
 * </p>
 * <p>
 * Company: 上海天坊信息科技有限公司
 * </p>
 * 
 * @author pengcheng.yang
 * @date 2015年8月10日 上午10:57:10
 * @version 1.0
 *          <p>
 *          修改人：pengcheng.yang
 *          </p>
 *          <p>
 *          修改时间：2015年8月10日 上午10:57:10
 *          </p>
 *          <p>
 *          修改备注：
 *          </p>
 */
@Service
public class IConfigScoreValveConfServiceImpl implements IConfigScoreValveConfService
{
    @Autowired
    private ConfigScoreValveConfDao configScoreValueConfDao;
    /**
     * 
     * (non-Javadoc)
     * 
     * @see com.ssic.catering.base.service.IConfigScoreValveConfService#queryConfig(java.lang.String, java.lang.String)
     */
    @Override
    @Cacheable(value = "default", key = "'catering.base.dto.ConfigScoreValveConfDto.cafetoriumId:'+ #cafetoriumId")
    public List<ConfigScoreValveConfDto> queryConfigId(String cafetoriumId)
    {
        if (StringUtils.isEmpty(cafetoriumId))
        {
            return null;
        }
        List<ConfigScoreValveConf> conf = configScoreValueConfDao.queryConfigId(cafetoriumId);
        if (conf != null)
        {
            return BeanUtils.createBeanListByTarget(conf,ConfigScoreValveConfDto.class);
        }
        return null;
    }
    
     /** 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.IConfigScoreValveConfService#insertConfScoreValue(com.ssic.catering.base.dto.ConfigScoreValveConfDto)   
     */
    @Override
    @CacheEvict(value="default", key = "'catering.base.dto.ConfigScoreValveConfDto.cafetoriumId:'+ #dto.getCafetoriumId()", beforeInvocation=true)
    public int insertConfScoreValue(ConfigScoreValveConfDto dto)
    {
        return this.configScoreValueConfDao.insertConfScoreValue(dto);
    }
    
     /** 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.IConfigScoreValveConfService#updateConfScoreValue(com.ssic.catering.base.dto.ConfigScoreValveConfDto)   
     */
    @Override
    @CacheEvict(value="default", key = "'catering.base.dto.ConfigScoreValveConfDto.cafetoriumId:'+ #dto.getCafetoriumId()", beforeInvocation=true)
    public int updateConfScoreValue(ConfigScoreValveConfDto dto)
    {
       return this.configScoreValueConfDao.updateConfScoreValue(dto);
    }
    
     /** 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.IConfigScoreValveConfService#deleteConfScoreValue(com.ssic.catering.base.dto.ConfigScoreValveConfDto)   
     */
    @Override
    @CacheEvict(value="default", key = "'catering.base.dto.ConfigScoreValveConfDto.cafetoriumId:'+ #dto.getCafetoriumId()", beforeInvocation=true)
    public int deleteConfScoreValue(ConfigScoreValveConfDto dto)
    {
        return this.configScoreValueConfDao.deleteConfScoreValue(dto);
    }

    
     /** 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.IConfigScoreValveConfService#findBy(com.ssic.catering.base.pojo.ConfigScoreValveConf, com.ssic.catering.base.dto.PageHelperDto)  
     * @author pengcheng.yang 
     */
    @Override
    public List<ConfigScoreValveConfDto> findBy(ConfigScoreValveConfDto dto,List<String> listStr, PageHelperDto ph)
    {
        ConfigScoreValveConf conf = new ConfigScoreValveConf();
        BeanUtils.copyProperties(dto,conf);
        List<ConfigScoreValveConf> list = configScoreValueConfDao.findBy(conf,listStr, ph);
        if(!StringUtils.isEmpty(list)){
            return BeanUtils.createBeanListByTarget(list,ConfigScoreValveConfDto.class);
        }
        return null;
    }

    
     /** 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.IConfigScoreValveConfService#findCountBy()   
     * @author pengcheng.yang
     */
    @Override
    public int findCountBy(List<String> list)
    {
        int flag = configScoreValueConfDao.findCountBy(list);
        if(flag>0){
            return flag;
        }
        return 0;
    }

    
     /** 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.IConfigScoreValveConfService#getConfigConfById(java.lang.String)   
     */
    @Override
    public ConfigScoreValveConf getConfigConfById(String id)
    {
        return configScoreValueConfDao.getConfigConfById(id);
    }

    
     /** 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.IConfigScoreValveConfService#UpdateConfigConfById(com.ssic.catering.base.pojo.ConfigScoreValveConf)   
     */
    @Override
    @CacheEvict(value="default", key = "'catering.base.dto.ConfigScoreValveConfDto.cafetoriumId:'+ #dto.getCafetoriumId()", beforeInvocation=true)
    public int deleteConfigConfById(ConfigScoreValveConfDto dto)
    {
        ConfigScoreValveConf conf = new ConfigScoreValveConf();
        BeanUtils.copyProperties(dto, conf);
        conf.setStat(DataStatus.DISABLED);
        conf.setLastUpdateTime(new Date());
        return configScoreValueConfDao.deleteConfigConfById(conf);
    }

    
     /** 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.IConfigScoreValveConfService#updateConfigConfById(com.ssic.catering.base.pojo.ConfigScoreValveConf)   
     */
    @Override
    @CacheEvict(value="default", key = "'catering.base.dto.ConfigScoreValveConfDto.cafetoriumId:'+ #dto.getCafetoriumId()", beforeInvocation=true)
    public int updateConfigConfById(ConfigScoreValveConfDto dto)
    {
        if(!StringUtils.isEmpty(dto)){
            ConfigScoreValveConf conf = new ConfigScoreValveConf();
            BeanUtils.copyProperties(dto, conf);
            return configScoreValueConfDao.updateConfigConfById(conf);
        }
        return 0;
    }
    
    
}
