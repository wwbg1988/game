package com.ssic.catering.base.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.mapper.SensitiveExMapper;
import com.ssic.catering.base.mapper.SensitiveMapper;
import com.ssic.catering.base.pojo.CarteType;
import com.ssic.catering.base.pojo.CarteTypeExample;
import com.ssic.catering.base.pojo.Sensitive;
import com.ssic.catering.base.pojo.SensitiveExample;
import com.ssic.catering.base.pojo.SensitiveExample.Criteria;
import com.ssic.util.base.MyBatisBaseDao;
import com.ssic.util.constants.DataStatus;

/**
 * 		
 * <p>Title: ConfigSearchDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年8月3日 下午8:54:15	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年8月3日 下午8:54:15</p>
 * <p>修改备注：</p>
 */
@Repository
public class SensitiveDao extends MyBatisBaseDao<Sensitive>
{

    @Autowired
    @Getter
    private SensitiveMapper mapper;

    @Autowired
    private SensitiveExMapper sensitiveExMapper;

    /**
     * 
     * findBy：一句话描述方法功能
     * @param param
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月4日 上午8:49:52
     */
    public List<Sensitive> findBy(Sensitive param)
    {
        SensitiveExample example = new SensitiveExample();
        Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(param.getId()))
        {
            criteria.andIdEqualTo(param.getId());
        }
        if (!StringUtils.isEmpty(param.getSensitiveName()))
        {
            criteria.andSensitiveNameEqualTo(param.getSensitiveName());
        }
        if(!StringUtils.isEmpty(param.getCafetoriumId())){
            criteria.andCafetoriumIdEqualTo(param.getCafetoriumId());
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return mapper.selectByExample(example);
    }

    public List<Sensitive> findAllBy(Sensitive param, PageHelperDto ph,List<String> listStr)
    {
        SensitiveExample example = new SensitiveExample();
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
        if (!StringUtils.isEmpty(param.getSensitiveName()))
        {
            criteria.andSensitiveNameLike("%" + param.getSensitiveName() + "%");
        }
        if (!StringUtils.isEmpty(param.getWarning()))
        {
            criteria.andWarningEqualTo(param.getWarning());
        }
        if(!StringUtils.isEmpty(param.getCafetoriumId())){
            criteria.andCafetoriumIdEqualTo(param.getCafetoriumId());
        }
        criteria.andCafetoriumIdIn(listStr);
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return mapper.selectByExample(example);
    }

    /**     
     * findCountBy：一句话描述方法功能
     * @param Sensitive
     * @return
     * @exception   
     * @author 刘博
     * @date 2015年8月4日 上午11:41:13    
     */
    public int findCountBy(Sensitive param,List<String> listStr)
    {
        SensitiveExample example = new SensitiveExample();
        Criteria criteria = example.createCriteria();
        criteria.andStatEqualTo(DataStatus.ENABLED);
        if (!StringUtils.isEmpty(param.getId()))
        {
            criteria.andIdEqualTo(param.getId());
        }
        if(!StringUtils.isEmpty(param.getSensitiveName())){
            criteria.andSensitiveNameLike("%" + param.getSensitiveName() + "%");
        }
        if(!StringUtils.isEmpty(param.getCafetoriumId())){
            criteria.andCafetoriumIdEqualTo(param.getCafetoriumId());
        }
        if (!StringUtils.isEmpty(param.getWarning()))
        {
            criteria.andWarningEqualTo(param.getWarning());
        }
        
        criteria.andCafetoriumIdIn(listStr);
        int count = mapper.countByExample(example);
        return count;
    }

    /**
     * 
     * findByTop9：查询出敏感词
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月4日 下午1:51:34
     */
    public List<Sensitive> findByTop(String cafetoriumId)
    {
        SensitiveExample example = new SensitiveExample();
        Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(cafetoriumId)){
            criteria.andCafetoriumIdEqualTo(cafetoriumId);
            criteria.andStatEqualTo(DataStatus.ENABLED);
            return mapper.selectByExample(example);
        }
        return null;
    }

    /**
     * 
     * findById：一句话描述方法功能
     * @param id
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月4日 上午9:20:14
     */
    public Sensitive findById(String id)
    {
        if (!StringUtils.isEmpty(id))
        {
            return mapper.selectByPrimaryKey(id);
        }
        return null;
    }

    /**
     * 
     * findBySensitiveTop9：统计用户点击的敏感词的前九个，如果不够九个有几个取几个
     * @param cafetoriumId
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月6日 下午7:09:52
     */
    public List<Sensitive> findBySensitiveTop9(String cafetoriumId)
    {
        if (!StringUtils.isEmpty(cafetoriumId))
        {
            return sensitiveExMapper.querySersitive(cafetoriumId);
        }
        return null;
    }

    /**
     * getSensitiveById：通过ID获取敏感词列表
     * @param id 主键Id
     * @return
     * @exception	
     * @author 张亚伟
     * @date 2015年8月11日 下午9:58:31
     */
    public Sensitive getSensitiveById(String id)
    {
        return mapper.selectByPrimaryKey(id);
    }

}
