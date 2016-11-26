package com.ssic.catering.base.dao;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssic.catering.base.dto.PageConfigDto;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.mapper.PageConfigExMapper;
import com.ssic.catering.base.mapper.PageConfigMapper;
import com.ssic.catering.base.pojo.PageConfig;
import com.ssic.catering.base.pojo.PageConfigExample;
import com.ssic.util.ArrayUtils;
import com.ssic.util.StringUtils;
import com.ssic.util.base.MyBatisBaseDao;
import com.ssic.util.constants.DataStatus;

@Repository
public class PageConfigDao extends MyBatisBaseDao<PageConfig>
{
    @Autowired
    @Getter
    private PageConfigMapper mapper;
    
    @Autowired
    private PageConfigExMapper exMapper;
    
    /**
     * 
     * findBy：分页查询dto，projectId可以作为projectIds,eg:projectId1,projectId2
     * @param param
     * @param ph
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年11月23日 下午3:43:08
     */
    public List<PageConfigDto> findBy(PageConfigDto param, PageHelperDto ph)
    {
        PageConfigExample example = new PageConfigExample();
        PageConfigExample.Criteria criteria = example.createCriteria();
        
        if(param != null)
        {
          //id
            if(!StringUtils.isEmpty(param.getId()))
            {
              criteria.andIdEqualTo(param.getId());
            }
            //cafetoriumId
            if(!StringUtils.isEmpty(param.getCafetoriumId()))
            {
              criteria.andCafetoriumIdEqualTo(param.getCafetoriumId());
            }
            //projectId
            if(!StringUtils.isEmpty(param.getProjectId()))
            {
                if(param.getProjectId().contains(","))
                {
                    String[] projectIds = param.getProjectId().split(",");
                    if(!ArrayUtils.isEmpty(projectIds))
                    {
                        criteria.andProjectIdIn(Arrays.asList(projectIds));
                    }
                }
                else
                {
                    criteria.andProjectIdEqualTo(param.getProjectId());
                }
            }
            //name
            if(!StringUtils.isEmpty(param.getName()))
            {
              criteria.andNameLike(param.getName());
            }
            //isEnabled
            if(null != param.getIsEnabled())
            {
              criteria.andIsEnabledEqualTo(param.getIsEnabled());
            }
            //enabledUrl
            if(!StringUtils.isEmpty(param.getEnabledUrl()))
            {
              criteria.andEnabledUrlEqualTo(param.getEnabledUrl());
            }
            //notEnabledUrl
            if(!StringUtils.isEmpty(param.getNotEnabledUrl()))
            {
              criteria.andNotEnabledUrlEqualTo(param.getNotEnabledUrl());
            }
            //createTime
            //lastUpdateTime
            //stat
            if(null == param.getStat())
            {
              criteria.andStatEqualTo(DataStatus.ENABLED);
            }
        }
        else
        {
            criteria.andStatEqualTo(DataStatus.ENABLED);
        }

        if(ph != null)
        {
            //当前页
            if(ph.getPage() <= 0)
            {
                ph.setPage(1);
            }
            
            //页面大小
            if(ph.getRows() <= 0)
            {
                ph.setRows(1);
            }
            
            ph.setBeginRow((ph.getPage()-1)*ph.getRows());
            
            //排序字段 
            if(!StringUtils.isEmpty(ph.getSort()) && !StringUtils.isEmpty(ph.getOrder()))
            {
                example.setOrderByClause(ph.getSort()+" "+ph.getOrder()+" limit "+ph.getBeginRow()+","+ph.getRows());
            }
            else
            {
               example.setOrderByClause("create_time asc limit "+ph.getBeginRow()+","+ph.getRows());
            }
            
        }

        return exMapper.selectPageConfigDtoBy(example, param);
    }
    
    /**
     * 
     * findBy：使用参数查询 ，projectId可以作为projectIds,eg:projectId1,projectId2
     * @param param
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年11月23日 下午2:53:53
     */
    public List<PageConfig> findBy(PageConfig param)
    {
        PageConfigExample example = new PageConfigExample();
        PageConfigExample.Criteria criteria = example.createCriteria();
        
      //id
        if(!StringUtils.isEmpty(param.getId()))
        {
          criteria.andIdEqualTo(param.getId());
        }
        //cafetoriumId
        if(!StringUtils.isEmpty(param.getCafetoriumId()))
        {
          criteria.andCafetoriumIdEqualTo(param.getCafetoriumId());
        }
        //projectId
        if(!StringUtils.isEmpty(param.getProjectId()))
        {
            if(param.getProjectId().contains(","))
            {
                String[] projectIds = param.getProjectId().split(",");
                if(!ArrayUtils.isEmpty(projectIds))
                {
                    criteria.andProjectIdIn(Arrays.asList(projectIds));
                }
            }
            else
            {
                criteria.andProjectIdEqualTo(param.getProjectId());
            }
        }
        //name
        if(!StringUtils.isEmpty(param.getName()))
        {
          criteria.andNameEqualTo(param.getName());
        }
        //isEnabled
        if(null != param.getIsEnabled())
        {
          criteria.andIsEnabledEqualTo(param.getIsEnabled());
        }
        //enabledUrl
        if(!StringUtils.isEmpty(param.getEnabledUrl()))
        {
          criteria.andEnabledUrlEqualTo(param.getEnabledUrl());
        }
        //notEnabledUrl
        if(!StringUtils.isEmpty(param.getNotEnabledUrl()))
        {
          criteria.andNotEnabledUrlEqualTo(param.getNotEnabledUrl());
        }
        //createTime
        //lastUpdateTime
        //stat
        if(null == param.getStat())
        {
          criteria.andStatEqualTo(DataStatus.ENABLED);
        }


        return mapper.selectByExample(example);
    }
    
    /**
     * 
     * findPageConfigDtoCount：查询总条数,projectId可以作为projectIds,eg:projectId1,projectId2
     * @param param
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年11月23日 下午5:24:14
     */
    public long findPageConfigDtoCount(PageConfigDto param)
    {
        PageConfigExample example = new PageConfigExample();
        PageConfigExample.Criteria criteria = example.createCriteria();
        
        if(param != null)
        {
          //id
            if(!StringUtils.isEmpty(param.getId()))
            {
              criteria.andIdEqualTo(param.getId());
            }
            //cafetoriumId
            if(!StringUtils.isEmpty(param.getCafetoriumId()))
            {
              criteria.andCafetoriumIdEqualTo(param.getCafetoriumId());
            }
            //projectId
            if(!StringUtils.isEmpty(param.getProjectId()))
            {
              if(param.getProjectId().contains(","))
              {
                  String[] projectIds = param.getProjectId().split(",");
                  if(!ArrayUtils.isEmpty(projectIds))
                  {
                      criteria.andProjectIdIn(Arrays.asList(projectIds));
                  }
              }
              else
              {
                  criteria.andProjectIdEqualTo(param.getProjectId());
              }
            }
            //name
            if(!StringUtils.isEmpty(param.getName()))
            {
              criteria.andNameEqualTo(param.getName());
            }
            //isEnabled
            if(null != param.getIsEnabled())
            {
              criteria.andIsEnabledEqualTo(param.getIsEnabled());
            }
            //enabledUrl
            if(!StringUtils.isEmpty(param.getEnabledUrl()))
            {
              criteria.andEnabledUrlEqualTo(param.getEnabledUrl());
            }
            //notEnabledUrl
            if(!StringUtils.isEmpty(param.getNotEnabledUrl()))
            {
              criteria.andNotEnabledUrlEqualTo(param.getNotEnabledUrl());
            }
            //createTime
            //lastUpdateTime
            //stat
            if(null == param.getStat())
            {
              criteria.andStatEqualTo(DataStatus.ENABLED);
            }
        }
        else
        {
            criteria.andStatEqualTo(DataStatus.ENABLED);
        }

        return exMapper.selectPageConfigDtoCountBy(example, param);
    }
    
    /**
     * 
     * findById：一句话描述方法功能
     * @param id
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年11月30日 下午6:15:50
     */
    public PageConfig findById(String id)
    {
        return mapper.selectByPrimaryKey(id);
    }
      
    
       
    
}

