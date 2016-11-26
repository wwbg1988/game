package com.ssic.catering.base.dao;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.mapper.MeetingAddressMapper;
import com.ssic.catering.base.pojo.MeetingAddress;
import com.ssic.catering.base.pojo.MeetingAddressExample;
import com.ssic.catering.base.pojo.MeetingAddressExample.Criteria;
import com.ssic.catering.base.pojo.PageHelper;
import com.ssic.util.constants.DataStatus;

@Repository
public class MeetingAddressDao
{

    private static final Logger log = Logger.getLogger(MeetingAddressDao.class);

    @Autowired
    private MeetingAddressMapper mapper;

    public List<MeetingAddress> findBy(MeetingAddress param)
    {
        MeetingAddressExample example = new MeetingAddressExample();
        Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(param.getId()))
        {
            criteria.andIdEqualTo(param.getId());
        }
        if (!StringUtils.isEmpty(param.getLargeArea()))
        {
            criteria.andLargeAreaEqualTo(param.getLargeArea());
        }
        if (!StringUtils.isEmpty(param.getProvince()))
        {
            criteria.andProvinceEqualTo(param.getProvince());
        }
        if (!StringUtils.isEmpty(param.getCity()))
        {
            criteria.andCityEqualTo(param.getCity());
        }
        if (!StringUtils.isEmpty(param.getName()))
        {
            criteria.andNameEqualTo(param.getName());
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return mapper.selectByExample(example);
    }

    /**
     * @param id
     * @return
     */
    public MeetingAddress findById(String id)
    {
        return mapper.selectByPrimaryKey(id);
    }

    public void insertMeetingA(MeetingAddress param)
    {
        mapper.insert(param);
    }

    public void updateMeetingA(MeetingAddress param)
    {
        mapper.updateByPrimaryKeySelective(param);
    }

    public List<MeetingAddress> findBy(MeetingAddress param, PageHelperDto phdto)
    {
        MeetingAddressExample example = new MeetingAddressExample();
        Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(param.getId()))
        {
            criteria.andIdEqualTo(param.getId());
        }
        if (!StringUtils.isEmpty(param.getName()))
        {
            criteria.andNameEqualTo(param.getName());
        }
        if (!StringUtils.isEmpty(param.getLargeArea()))
        {
            criteria.andLargeAreaEqualTo(param.getLargeArea());
        }
        if (!StringUtils.isEmpty(param.getProvince()))
        {
            criteria.andProvinceEqualTo(param.getProvince());
        }
        if (!StringUtils.isEmpty(param.getCity()))
        {
            criteria.andCityEqualTo(param.getCity());
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        example.setOrderByClause("  create_time desc  limit " + phdto.getBeginRow() + "," + phdto.getRows());
        return mapper.selectByExample(example);
    }

    public int findCount(MeetingAddress param)
    {
        MeetingAddressExample example = new MeetingAddressExample();
        Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(param.getId()))
        {
            criteria.andIdEqualTo(param.getId());
        }
        if (!StringUtils.isEmpty(param.getName()))
        {
            criteria.andNameEqualTo(param.getName());
        }
        if (!StringUtils.isEmpty(param.getLargeArea()))
        {
            criteria.andLargeAreaEqualTo(param.getLargeArea());
        }
        if (!StringUtils.isEmpty(param.getProvince()))
        {
            criteria.andProvinceEqualTo(param.getProvince());
        }
        if (!StringUtils.isEmpty(param.getCity()))
        {
            criteria.andCityEqualTo(param.getCity());
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return mapper.countByExample(example);
    }

    /**
     * 	 
     * @author 朱振	
     * @date 2015年10月27日 下午3:16:14	
     * @version 1.0
     * @param param
     * @param projectIds
     * @param ph
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年10月27日 下午3:16:14</p>
     * <p>修改备注：</p>
     */
    public List<MeetingAddress> findBy(MeetingAddress param,List<String> projectIds,PageHelper ph){
	    
	    if(param==null)
	    {
	        log.error("参数MeetingAddress不能为空");
	        return null;
	    }
	    
	    if(ph == null)
        {
            log.error("参数PageHelper不能为空");
            return null;
        }
	    
	    if(ph.getRows() < 0)
	    {
	        log.error("参数rows不能小于0");
            return null;
	    }
	    
	    if(ph.getPage() < 1)
        {
            log.error("参数page不能小于1");
            return null;
        }
	    
        MeetingAddressExample example = new MeetingAddressExample();
        Criteria criteria = example.createCriteria();
        
        //id
        if(!StringUtils.isEmpty(param.getId())){
            criteria.andIdEqualTo(param.getId());
        }
        
        //name
        if(!StringUtils.isEmpty(param.getName())){
            criteria.andNameEqualTo(param.getName());
        }
        
        //largeArea
        if(!StringUtils.isEmpty(param.getLargeArea())){
            criteria.andLargeAreaEqualTo(param.getLargeArea());
        }
        
        //province
        if(!StringUtils.isEmpty(param.getProvince())){
            criteria.andProvinceEqualTo(param.getProvince());
        }
        
        //city
        if(!StringUtils.isEmpty(param.getCity())){
            criteria.andCityEqualTo(param.getCity());
        }
        
        //projectId
        if(!CollectionUtils.isEmpty(projectIds))
        {
            criteria.andProjIdIn(projectIds);
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        example.setOrderByClause("  create_time desc  limit "+ (ph.getPage()-1)*ph.getRows()+ ","+ph.getRows());
        return mapper.selectByExample(example);
    }

    /**
     * 	 
     * @author 朱振	
     * @date 2015年10月27日 下午3:15:48	
     * @version 1.0
     * @param param
     * @param projectIds
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年10月27日 下午3:15:48</p>
     * <p>修改备注：</p>
     */
    public int findCountBy(MeetingAddress param, List<String> projectIds)
    {
        if(param==null)
        {
            log.error("参数MeetingAddress不能为空");
            return 0;
        }
        
        
        MeetingAddressExample example = new MeetingAddressExample();
        Criteria criteria = example.createCriteria();
        
        //id
        if(!StringUtils.isEmpty(param.getId())){
            criteria.andIdEqualTo(param.getId());
        }
        
        //name
        if(!StringUtils.isEmpty(param.getName())){
            criteria.andNameEqualTo(param.getName());
        }
        
        //largeArea
        if(!StringUtils.isEmpty(param.getLargeArea())){
            criteria.andLargeAreaEqualTo(param.getLargeArea());
        }
        
        //province
        if(!StringUtils.isEmpty(param.getProvince())){
            criteria.andProvinceEqualTo(param.getProvince());
        }
        
        //city
        if(!StringUtils.isEmpty(param.getCity())){
            criteria.andCityEqualTo(param.getCity());
        }
        
        //projectId
        if(!CollectionUtils.isEmpty(projectIds))
        {
            criteria.andProjIdIn(projectIds);
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return mapper.countByExample(example);
    }
}
