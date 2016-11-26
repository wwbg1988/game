package com.ssic.catering.base.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.mapper.ConfigMapper;
import com.ssic.catering.base.pojo.Config;
import com.ssic.catering.base.pojo.ConfigExample;
import com.ssic.catering.base.pojo.SensitiveExample;
import com.ssic.catering.base.pojo.ConfigExample.Criteria;
import com.ssic.util.base.MyBatisBaseDao;
import com.ssic.util.constants.DataStatus;

/**
 * 		
 * <p>Title: ConfigDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年8月4日 上午9:36:35	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年8月4日 上午9:36:35</p>
 * <p>修改备注：</p>
 */
@Repository
public class ConfigDao extends MyBatisBaseDao<Config>
{
    @Autowired
    @Getter
    private ConfigMapper mapper;
    
    /**
     * 
     * findBy：一句话描述方法功能
     * @param param
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月4日 上午9:42:21
     */
    public List<Config> findBy(Config param){
        ConfigExample example = new ConfigExample();
        Criteria criteara = example.createCriteria();
        if(!StringUtils.isEmpty(param.getId())){
            criteara.andIdEqualTo(param.getId());
        }
        if(!StringUtils.isEmpty(param.getConfigName())){
            criteara.andConfigNameEqualTo(param.getConfigName());
        }
        criteara.andStatEqualTo(DataStatus.ENABLED);
        return mapper.selectByExample(example);
    }
    
    /**
     * 
     * findByProjId：根据projId查询配置项信息
     * @param param
     * @param listStr
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年10月28日 上午11:06:23
     */
    public List<Config> findByProjId(Config param,String projId){
        ConfigExample example = new ConfigExample();
        Criteria criteara = example.createCriteria();
        if(!StringUtils.isEmpty(param.getId())){
            criteara.andIdEqualTo(param.getId());
        }
        if(!StringUtils.isEmpty(param.getConfigName())){
            criteara.andConfigNameEqualTo(param.getConfigName());
        }
        criteara.andStatEqualTo(DataStatus.ENABLED);
        criteara.andProjIdEqualTo(projId);
        return mapper.selectByExample(example);
    }
    
    public List<Config> findByNameLike(Config param,List<String> listStr){
        ConfigExample example = new ConfigExample();
        Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(param.getConfigName())){
            criteria.andConfigNameLike("%"+param.getConfigName()+"%");
        }
        criteria.andProjIdIn(listStr);
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return mapper.selectByExample(example);
    }
    
    /**
     * 
     * findById：一句话描述方法功能
     * @param id
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年8月4日 上午9:45:21
     */
    public Config findById(String id){
        if(!StringUtils.isEmpty(id)){
            return mapper.selectByPrimaryKey(id);
        }
        return null;
    }
    
    /**     
     * findAllBy：一句话描述方法功能
     * @param param
     * @param phDto
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年8月14日 上午11:31:32	 
     */
    public List<Config> findAllBy(Config param,List<String> listStr,PageHelperDto ph)
    {
        ConfigExample example = new ConfigExample();
        Criteria criteara = example.createCriteria();
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
            criteara.andIdEqualTo(param.getId());
        }
        if(!StringUtils.isEmpty(param.getProjId())){
            criteara.andProjIdEqualTo(param.getProjId());
        }
        if (!StringUtils.isEmpty(param.getConfigName()))
        {
            criteara.andConfigNameLike("%" + param.getConfigName() + "%");
        }
        criteara.andProjIdIn(listStr);
        criteara.andStatEqualTo(DataStatus.ENABLED);
        return mapper.selectByExample(example);
    }
    
    /**     
     * findCountBy：一句话描述方法功能
     * @param param
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年8月14日 上午11:31:45	 
     */
    public int findCountBy(Config param,List<String> listStr)
    {
        ConfigExample example = new ConfigExample();
        Criteria criteria = example.createCriteria();
        criteria.andStatEqualTo(DataStatus.ENABLED);
        if (!StringUtils.isEmpty(param.getId()))
        {
            criteria.andIdEqualTo(param.getId());
        }
        if(!StringUtils.isEmpty(param.getConfigName())){
            criteria.andConfigNameLike("%" + param.getConfigName() + "%");
        }
        
        criteria.andProjIdIn(listStr);
        int count = mapper.countByExample(example);
        return count;
    }
    
    /**
     * 
     * findByConfigName：检查修改的的配置项名称除自己之外是否和其他名称相等
     * @param param
     * @param projId
     * @param listStr
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年11月3日 下午4:53:53
     */
    public List<Config> findByConfigName(Config param,List<String> listStr){
        ConfigExample example = new ConfigExample();
        Criteria criteara = example.createCriteria();
        criteara.andIdNotIn(listStr);
        criteara.andProjIdEqualTo(param.getProjId());
        criteara.andStatEqualTo(DataStatus.ENABLED);
        
        return mapper.selectByExample(example);
    }
	
}

