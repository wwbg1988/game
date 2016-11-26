package com.ssic.catering.base.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssic.catering.base.dao.ConfigDao;
import com.ssic.catering.base.dao.ConfigListDao;
import com.ssic.catering.base.dto.ConfigDto;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.dto.Tree;
import com.ssic.catering.base.pojo.Config;
import com.ssic.catering.base.pojo.ConfigList;
import com.ssic.catering.base.service.IConfigService;
import com.ssic.util.BeanUtils;
import com.ssic.util.UUIDGenerator;
import com.ssic.util.constants.DataStatus;

/**
 * 		
 * <p>Title: ConfigServiceImpl </p>
 * <p>Description: 食堂评分服务项逻辑层</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 张亚伟	
 * @date 2015年8月11日 下午7:31:53	
 * @version 1.0
 * <p>修改人：张亚伟</p>
 * <p>修改时间：2015年8月11日 下午7:31:53</p>
 * <p>修改备注：</p>
 */
@Service
public class ConfigServiceImpl implements IConfigService
{

    @Autowired
    private ConfigDao configDao;

    @Autowired
    private ConfigListDao configListDao;

    @Override
    public ConfigDto findConfigToId(String id)
    {
        Config config = configDao.findById(id);
        ConfigDto configDto = new ConfigDto();
        if (config != null)
        {
            BeanUtils.copyProperties(config, configDto);
        }
        return configDto;
    }

    @Override
    public List<Config> findAll()
    {
        return configDao.findBy(new Config());
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.IConfigService#findALL(com.ssic.catering.base.dto.ConfigDto, com.ssic.catering.base.dto.PageHelperDto)   
    */
    @Override
    public List<ConfigDto> findALL(ConfigDto configDto,List<String> listStr,PageHelperDto phDto)
    {
        Config param = new Config();
        BeanUtils.copyProperties(configDto, param);
        List<Config> list = configDao.findAllBy(param,listStr,phDto);
        if (!CollectionUtils.isEmpty(list))
        {
            return BeanUtils.createBeanListByTarget(list, ConfigDto.class);

        }
        return new ArrayList<ConfigDto>();
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.IConfigService#findCount(com.ssic.catering.base.dto.ConfigDto)   
    */
    @Override
    public int findCount(ConfigDto configDto,List<String> listStr)
    {
        Config param = new Config();
        BeanUtils.copyProperties(configDto, param);
        int counts = configDao.findCountBy(param,listStr);
        return counts;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.IConfigService#finByName(java.lang.String)   
    */
    @Override
    public List<ConfigDto> finByName(String configName,String projId)
    {
        Config param = new Config();
        param.setConfigName(configName);
        List<Config> list = configDao.findByProjId(param,projId);
        if (!CollectionUtils.isEmpty(list))
        {
            List<ConfigDto> listDto = BeanUtils.createBeanListByTarget(list, ConfigDto.class);
            return listDto;
        }
        //如果没有不存在返回一个空的对象
        return new ArrayList<ConfigDto>();
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.IConfigService#add(com.ssic.catering.base.dto.ConfigDto)   
    */
    @Override
    public void add(ConfigDto configDto)
    {
        Config config = new Config();
        BeanUtils.copyProperties(configDto, config);
        config.setCreateTime(new Date());
        config.setStat(DataStatus.ENABLED);
        configDao.insert(config);

    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.IConfigService#update(com.ssic.catering.base.dto.ConfigDto)   
    */
    @Override
    public void update(ConfigDto configDto)
    {
        Config config = new Config();
        BeanUtils.copyProperties(configDto, config);
        config.setStat(DataStatus.ENABLED);
        configDao.updateByPrimaryKey(config);
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.IConfigService#delete(com.ssic.catering.base.dto.ConfigDto)   
    */
    @Override
    public void delete(ConfigDto configDto)
    {
        Config config = new Config();
        BeanUtils.copyProperties(configDto, config);
        config.setStat(DataStatus.DISABLED);
        configDao.updateByPrimaryKey(config);
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.IConfigService#findTree()   
    */
    @Override
    public List<Tree> findTree(String searchName,List<String> listStr)
    {
        List<Tree> result = new ArrayList<Tree>();
        Config config = new Config();
        if (!StringUtils.isEmpty(searchName))
        {
            config.setConfigName(searchName);
        }
        List<Config> confList = configDao.findByNameLike(config,listStr);
        if (CollectionUtils.isEmpty(confList))
        {
            return null;
        }
        for (Config temps : confList)
        {
            Tree tree = new Tree();
            tree.setText(temps.getConfigName());
            tree.setId(temps.getId());
            result.add(tree);
        }
        return result;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.IConfigService#grant(java.lang.String, java.lang.String)   
    */
    @Override
    public int grant(String id, String configIds)
    {
        ConfigList param = new ConfigList();
        param.setCafetoriumId(id);
        List<ConfigList> confList = configListDao.findBy(param);
        if (!CollectionUtils.isEmpty(confList))
        {
            for (ConfigList conf : confList)
            {
                configListDao.delByPrimaryKey(conf.getId());
            }
        }
        if (StringUtils.isEmpty(configIds))
        {
            return DataStatus.HTTP_SUCCESS;
        }
        String confs[] = configIds.split(",");
        if (confs.length < 0)
        {
            return DataStatus.HTTP_FAILE;
        }
        for (int i = 0; i < confs.length; i++)
        {
            ConfigList configList = new ConfigList();
            configList.setId(UUIDGenerator.getUUID());
            configList.setCafetoriumId(id);
            configList.setConfigId(confs[i]);
            configList.setStat(DataStatus.ENABLED);
            configList.setCreateTime(new Date());
            configList.setLastUpdateTime(new Date());
            configListDao.insertSelective(configList);

        }
        return DataStatus.HTTP_SUCCESS;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.IConfigService#chooseConfig(java.lang.String)   
    */
    @Override
    public String chooseConfig(String cafeId)
    {
        ConfigList param = new ConfigList();
        param.setCafetoriumId(cafeId);
        List<ConfigList> confList = configListDao.findBy(param);
        if (CollectionUtils.isEmpty(confList))
        {
            return null;
        }
        String result = "";
        for (int i = 0; i < confList.size(); i++)
        {
            if (i == confList.size() - 1)
            {
                result += confList.get(i).getConfigId();
            }
            else
            {
                result += confList.get(i).getConfigId() + ",";
            }
        }
        return result;
    }

    /**
     * @author pengcheng.yang
     * @desc 检查修改的的配置项名称除自己之外是否和其他名称相等
     * @date 2015-11-03
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.IConfigService#findByConfigName(com.ssic.catering.base.pojo.Config, java.lang.String, java.util.List)
     */
    @Override
    public List<ConfigDto> findByConfigName(ConfigDto configDto, List<String> listStr)
    {
        Config config = new Config();
        BeanUtils.copyProperties(configDto, config);
        List<Config> list = configDao.findByConfigName(config,listStr);
        if (list.size()>0)
        {
            List<ConfigDto> listDto = BeanUtils.createBeanListByTarget(list, ConfigDto.class);
            return listDto;
        }
        //如果没有不存在返回一个空的对象
        return new ArrayList<ConfigDto>();
    }

}
