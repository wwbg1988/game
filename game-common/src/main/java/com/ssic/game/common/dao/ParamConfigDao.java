package com.ssic.game.common.dao;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.game.common.dto.KeyValueDto;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.dto.ParamConfigDto;
import com.ssic.game.common.mapper.ParamConfigExMapper;
import com.ssic.game.common.mapper.ParamConfigMapper;
import com.ssic.game.common.pojo.ParamConfig;
import com.ssic.game.common.pojo.ParamConfigExample;
import com.ssic.game.common.pojo.ParamConfigExample.Criteria;
import com.ssic.util.ArrayUtils;
import com.ssic.util.UUIDGenerator;
import com.ssic.util.constants.DataStatus;

@Repository
public class ParamConfigDao {

    private static final Logger log = Logger.getLogger(ParamConfigDao.class);
    
	@Autowired
	private ParamConfigMapper mapper;
	
	@Autowired
	private ParamConfigExMapper exMapper;
	
	
	public List<ParamConfig> findBy(ParamConfig param){
		ParamConfigExample example = new ParamConfigExample();
		Criteria criteria = example.createCriteria();
		
		//id
		if(!StringUtils.isEmpty(param.getId())){
			criteria.andIdEqualTo(param.getId());
		}
		
		//name
	    if(!StringUtils.isEmpty(param.getParamName())){
            criteria.andParamNameEqualTo(param.getParamName());
        }
        
		//ParamType
		if(param.getParamType()!=null){
			criteria.andParamTypeEqualTo(param.getParamType());
		}
		
		//ProjId
		if(!StringUtils.isEmpty(param.getProjId()))
		{
		    if(param.getProjId().contains(","))
            {
                String[] projectIds = param.getProjId().split(",");
                if(!ArrayUtils.isEmpty(projectIds))
                {
                    criteria.andProjIdIn(Arrays.asList(projectIds));
                }
            }
            else
            {
                criteria.andProjIdEqualTo(param.getProjId());
            }
		}
		
		
		 criteria.andStatEqualTo(DataStatus.ENABLED);
		 example.setOrderByClause("  create_time desc  ");
		return mapper.selectByExample(example);	
	}
	
	public void insertParam(ParamConfig param){
		mapper.insert(param);
	}
	
	public void updateParam(ParamConfig param){
		mapper.updateByPrimaryKeySelective(param);
	}
	
	public ParamConfig findById(String id){
		return mapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 	 
	 * @author 朱振	
	 * @date 2015年10月29日 上午10:04:01	
	 * @version 1.0
	 * @param param
	 * @return
	 * <p>修改人：朱振</p>
	 * <p>修改时间：2015年10月29日 上午10:04:01</p>
	 * <p>修改备注：</p>
	 */
	public Map<String, String> findParametersBy(ParamConfig param)
	{
	    if(param == null)
	    {
	        log.error("参数ParamConfig不能为空");
	        return null;
	    }
	    
	    ParamConfigExample example = new ParamConfigExample();
	    ParamConfigExample.Criteria criteria = example.createCriteria();
	    
	    //id
	    if(!StringUtils.isEmpty(param.getId()))
	    {
	        criteria.andIdEqualTo(param.getId());
	    }
	    
	    //=projectId
	    if(!StringUtils.isEmpty(param.getProjId()))
	    {
	        criteria.andProjIdEqualTo(param.getProjId());
	    }
	    
	    //=type
	    if(null != param.getParamType())
	    {
	        criteria.andParamTypeEqualTo(param.getParamType());
	    }
	    
	    //like name
	    if(!StringUtils.isEmpty(param.getParamName()))
	    {
	        criteria.andParamNameLike("%"+param.getParamName()+"%");
	    }
	    
	    //like value
	    if(!StringUtils.isEmpty(param.getParamValue()))
	    {
	        criteria.andParamValueLike("%"+param.getParamValue()+"%");
	    }
	    
	    //like decribe
	    if(!StringUtils.isEmpty(param.getParamDescribe()))
	    {
	        criteria.andParamDescribeLike("%"+param.getParamDescribe()+"%");
	    }
	    
	   
	    criteria.andStatEqualTo(DataStatus.ENABLED);
	    
	    example.setOrderByClause("param_type asc, create_time desc");
	    
	    List<KeyValueDto<String>> resulset = exMapper.findParametersBy(example);
	    if(CollectionUtils.isEmpty(resulset))
	    {
	        return null;
	    }
	    
	    //转换为map
	    Map<String, String> result = new HashMap<>();
	    for(KeyValueDto<String> parameter:resulset)
	    {
	        if(!StringUtils.isEmpty(parameter))
	        {
	            //如果有相同参数名的只取第一个查询到的
	            if(!result.containsKey(parameter))
	            {
	                result.put(parameter.getKey(), parameter.getValue());
	            }
	        }
	    }
	    
	    if(result.isEmpty())
	    {
	        return null;
	    }
	    
	    return result;
	}
	
	/**
	 * 	 
	 * @author 朱振	
	 * @date 2015年11月16日 下午3:34:36	
	 * @version 1.0
	 * @param param
	 * @return
	 * <p>修改人：朱振</p>
	 * <p>修改时间：2015年11月16日 下午3:34:36</p>
	 * <p>修改备注：</p>
	 */
	public long findCountBy(ParamConfig param)
	{
	    if(param == null)
	    {
	        return 0;
	    }
	    
	    ParamConfigExample example = new ParamConfigExample();
	    ParamConfigExample.Criteria criteria = example.createCriteria();
	    
	    
	    //id
	    if(!StringUtils.isEmpty(param.getId()))
	    {
	      criteria.andIdEqualTo(param.getId());
	    }
	    //paramName
	    if(!StringUtils.isEmpty(param.getParamName()))
	    {
	      criteria.andParamNameEqualTo(param.getParamName());
	    }
	    //paramValue
	    if(!StringUtils.isEmpty(param.getParamValue()))
	    {
	      criteria.andParamValueEqualTo(param.getParamValue());
	    }
	    //paramDescribe
	    if(!StringUtils.isEmpty(param.getParamDescribe()))
	    {
	      criteria.andParamDescribeEqualTo(param.getParamDescribe());
	    }
	    //createTime
	    //lastUpdateTime
	    //stat
	    if(null == param.getStat())
	    {
	      criteria.andStatEqualTo(DataStatus.ENABLED);
	    }
	    //paramType
	    if(null != param.getParamType())
	    {
	      criteria.andParamTypeEqualTo(param.getParamType());
	    }
	    //projId
	    if(!StringUtils.isEmpty(param.getProjId()))
	    {
	        if(param.getProjId().contains(","))
            {
                String[] projectIds = param.getProjId().split(",");
                if(!ArrayUtils.isEmpty(projectIds))
                {
                    criteria.andProjIdIn(Arrays.asList(projectIds));
                }
            }
            else
            {
                criteria.andProjIdEqualTo(param.getProjId());
            }
	    }

	    return exMapper.findCountBy(example);
	}
	
	/**
	 * 	 
	 * @author 朱振	
	 * @date 2015年11月16日 下午3:42:49	
	 * @version 1.0
	 * @param param   查询条件
	 * @param ph
	 * @return
	 * <p>修改人：朱振</p>
	 * <p>修改时间：2015年11月16日 下午3:42:49</p>
	 * <p>修改备注：</p>
	 */
	public List<ParamConfig> findParamForPageBy(ParamConfig param, PageHelperDto ph)
	{
	    ParamConfigExample example = new ParamConfigExample();
	    ParamConfigExample.Criteria criteria = example.createCriteria();
	    
	    if(param != null)
        {
	      //id
	        if(!StringUtils.isEmpty(param.getId()))
	        {
	          criteria.andIdEqualTo(param.getId());
	        }
	        //paramName
	        if(!StringUtils.isEmpty(param.getParamName()))
	        {
	          criteria.andParamNameEqualTo(param.getParamName());
	        }
	        //paramValue
	        if(!StringUtils.isEmpty(param.getParamValue()))
	        {
	          criteria.andParamValueEqualTo(param.getParamValue());
	        }
	        //paramDescribe
	        if(!StringUtils.isEmpty(param.getParamDescribe()))
	        {
	          criteria.andParamDescribeEqualTo(param.getParamDescribe());
	        }
	        //createTime
	        //lastUpdateTime
	        //stat
	        if(null == param.getStat())
	        {
	          criteria.andStatEqualTo(DataStatus.ENABLED);
	        }
	        //paramType
	        if(null != param.getParamType())
	        {
	          criteria.andParamTypeEqualTo(param.getParamType());
	        }
	        //projId
	        if(!StringUtils.isEmpty(param.getProjId()))
	        {
	            if(param.getProjId().contains(","))
                {
                    String[] projectIds = param.getProjId().split(",");
                    if(!ArrayUtils.isEmpty(projectIds))
                    {
                        criteria.andProjIdIn(Arrays.asList(projectIds));
                    }
                }
                else
                {
                    criteria.andProjIdEqualTo(param.getProjId());
                }
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
                ph.setRows(1);;
            }
            
            ph.setBeginRow((ph.getPage()-1)*ph.getRows());
	        
	        //排序字段 
	        if(!StringUtils.isEmpty(ph.getSort()) && !StringUtils.isEmpty(ph.getOrder()))
	        {
	            example.setOrderByClause(ph.getSort()+" "+ph.getOrder()+" limit "+ph.getBeginRow()+","+ph.getRows());
	        }
	        else
	        {
	           example.setOrderByClause("create_time desc limit "+ph.getBeginRow()+","+ph.getRows());
	        }
	        
	    }
        
	    
	    return mapper.selectByExample(example);
	}
	
	/**
	 * 多表查询	 
	 * @author 朱振	
	 * @date 2015年11月16日 下午4:21:28	
	 * @version 1.0
	 * @param param
	 * @param ph
	 * @return
	 * <p>修改人：朱振</p>
	 * <p>修改时间：2015年11月16日 下午4:21:28</p>
	 * <p>修改备注：</p>
	 */
	public List<ParamConfigDto> findParamConfigDtoBy(ParamConfig param, PageHelperDto ph)
	{
	    ParamConfigExample example = new ParamConfigExample();
        ParamConfigExample.Criteria criteria = example.createCriteria();
        
        if(param != null)
        {
          //id
            if(!StringUtils.isEmpty(param.getId()))
            {
              criteria.andIdEqualTo(param.getId());
            }
            //paramName
            if(!StringUtils.isEmpty(param.getParamName()))
            {
              criteria.andParamNameEqualTo(param.getParamName());
            }
            //paramValue
            if(!StringUtils.isEmpty(param.getParamValue()))
            {
              criteria.andParamValueEqualTo(param.getParamValue());
            }
            //paramDescribe
            if(!StringUtils.isEmpty(param.getParamDescribe()))
            {
              criteria.andParamDescribeEqualTo(param.getParamDescribe());
            }
            //createTime
            //lastUpdateTime
            //stat
            if(null == param.getStat())
            {
              criteria.andStatEqualTo(DataStatus.ENABLED);
            }
            //paramType
            if(null != param.getParamType())
            {
              criteria.andParamTypeEqualTo(param.getParamType());
            }
            //projId
            if(!StringUtils.isEmpty(param.getProjId()))
            {
                if(param.getProjId().contains(","))
                {
                    String[] projectIds = param.getProjId().split(",");
                    if(!ArrayUtils.isEmpty(projectIds))
                    {
                        criteria.andProjIdIn(Arrays.asList(projectIds));
                    }
                }
                else
                {
                    criteria.andProjIdEqualTo(param.getProjId());
                }
            }


        }
        else
        {
            criteria.andStatEqualTo(DataStatus.ENABLED);
        }
        
        if(ph != null)
        {
            //开始下标
            if(ph.getBeginRow()==null || ph.getBeginRow()< 0)
            {
                ph.setBeginRow(0);
            }
            
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
               example.setOrderByClause("create_time desc limit "+ph.getBeginRow()+","+ph.getRows());
            }
            
        }
        
        
        return exMapper.findParamConfigDtoBy(example);
	}
	
	/**
	 * 
	 * insertParamConfig：添加param
	 * @param paramConDto
	 * @return
	 * @exception	
	 * @author zhuzhen
	 * @date 2015年11月25日 下午1:08:47
	 */
	public int insertParamConfig(ParamConfig param)
	{
	    if(param != null)
	    {
	        if(StringUtils.isEmpty(param.getId()))
	        {
	            param.setId(UUIDGenerator.getUUID());
	        }
	        
	        param.setCreateTime(new Date());
	        param.setStat(1);
	        return mapper.insert(param);
	    }
	    
	    return 0;
	}
	
	/**
	 * 
	 * updateParamConfig：一句话描述方法功能
	 * @param param
	 * @return
	 * @exception	
	 * @author zhuzhen
	 * @date 2015年12月1日 上午10:17:44
	 */
	public int updateParamConfig(ParamConfig param)
	{
	    if(param != null)
        {
            param.setLastUpdateTime(new Date());
            return mapper.updateByPrimaryKeySelective(param);
        }
        
        return 0;
	}
	
}
