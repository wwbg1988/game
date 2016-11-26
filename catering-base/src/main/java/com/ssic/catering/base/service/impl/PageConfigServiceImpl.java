package com.ssic.catering.base.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssic.catering.base.dao.PageConfigDao;
import com.ssic.catering.base.dto.PageConfigDto;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.pojo.PageConfig;
import com.ssic.catering.base.service.IPageConfigService;
import com.ssic.util.BeanUtils;

/**
 * 		
 * <p>Title: PageConfigServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author zhuzhen	
 * @date 2015年11月23日 下午5:21:17	
 * @version 1.0
 * <p>修改人：zhuzhen</p>
 * <p>修改时间：2015年11月23日 下午5:21:17</p>
 * <p>修改备注：</p>
 */
@Service
public class PageConfigServiceImpl implements IPageConfigService
{
    private static final Logger log = Logger.getLogger(PageConfigServiceImpl.class);
    
    @Autowired
    private PageConfigDao pageConfigDao;

    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.IPageConfigService#getPageConfigDtoBy(com.ssic.catering.base.dto.PageConfigDto, com.ssic.catering.base.dto.PageHelperDto)
     */
    @Override
    public List<PageConfigDto> getPageConfigDtoBy(PageConfigDto param, PageHelperDto ph)
    {
        return pageConfigDao.findBy(param, ph);
    }

    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.IPageConfigService#getPageConfigDtoCountBy(com.ssic.catering.base.dto.PageConfigDto)
     */
    @Override
    public long getPageConfigDtoCountBy(PageConfigDto param)
    {
        return pageConfigDao.findPageConfigDtoCount(param);
    }
    
    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.IPageConfigService#addPageConfig(com.ssic.catering.base.dto.PageConfigDto)
     */
    @Override
    public int addPageConfig(PageConfigDto pageConfigDto)
    {
        if(pageConfigDto != null)
        {
            PageConfig param = new PageConfig();
            BeanUtils.copyProperties(pageConfigDto, param);
            int count = pageConfigDao.insert(param);
            if(count > 0)
            {
                return count;
            }
            else
            {
                log.error("数据库添加记录失败"+pageConfigDto);
            }
        }
        else
        {
            log.warn("参数pageConfigDto为空");
        }
        
        
        return 0;
    }
    
    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.IPageConfigService#updatePageConfig(com.ssic.catering.base.dto.PageConfigDto)
     */
    @Override
    public int updatePageConfig(PageConfigDto pageConfigDto)
    {
        if(pageConfigDto != null)
        {
            if(StringUtils.isEmpty(pageConfigDto.getId()))
            {
                log.warn("参数中没有包含id信息");
                return 0;
            }
            
            PageConfig param = new PageConfig();            
            //更新要更新的数据
            BeanUtils.copyProperties(pageConfigDto, param);
            int count = pageConfigDao.updateByPrimaryKeySelective(param);
            if(count > 0)
            {
                return count;
            }
            else
            {
                log.error("数据库添加记录失败"+pageConfigDto);
            }
           
        }
        else
        {
            log.warn("参数pageConfigDto为空");
        }
        
        
        return 0;
    }
    
    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.IPageConfigService#findPageConfigDtoById(java.lang.String)
     */
    @Override
    public PageConfigDto findPageConfigDtoById(String id)
    {
        if(!StringUtils.isEmpty(id))
        {
            PageConfigDto param = new PageConfigDto();
            param.setId(id);
            List<PageConfigDto> result = pageConfigDao.findBy(param, null);
            if(!CollectionUtils.isEmpty(result))
            {
               return result.get(0);
            }
        }
       
        log.error("id不能为空");
        return null;
    }

}

