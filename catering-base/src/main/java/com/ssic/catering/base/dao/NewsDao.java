package com.ssic.catering.base.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import lombok.Getter;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.mapper.NewsMapper;
import com.ssic.catering.base.pojo.News;
import com.ssic.catering.base.pojo.NewsExample;
import com.ssic.catering.base.pojo.NewsExample.Criteria;
import com.ssic.catering.common.util.DateUtil;
import com.ssic.util.constants.DataStatus;

@Repository
public class NewsDao {

	@Autowired
	@Getter
	private NewsMapper mapper;
	
	   public List<News> findBy(News param){
		   NewsExample example = new NewsExample();
		   Criteria criteria = example.createCriteria();
	        if(!StringUtils.isEmpty(param.getId())){
	            criteria.andIdEqualTo(param.getId());
	        }
	       criteria.andStatEqualTo(DataStatus.ENABLED);
	       return   mapper.selectByExample(example);
	    }
	
	   public List<News> findBy(News param,PageHelperDto phdto){
		   NewsExample example = new NewsExample();
		   Criteria criteria = example.createCriteria();
	        if(!StringUtils.isEmpty(param.getId())){
	            criteria.andIdEqualTo(param.getId());
	        }
	        if(!StringUtils.isEmpty(param.getName())){
	        	criteria.andNameEqualTo(param.getName());
	        }
	        if(!StringUtils.isEmpty(param.getTitle())){
	        	criteria.andTitleEqualTo(param.getTitle());
	        }
	        if(param.getState()!=null){
	        	criteria.andStateEqualTo(param.getState());
	        }
	        if(!StringUtils.isEmpty(param.getListTitle())){
	        	criteria.andListTitleEqualTo(param.getListTitle());
	        }
	        if(!StringUtils.isEmpty(param.getProjId())){
	        	criteria.andProjIdEqualTo(param.getProjId());
	        }
	       criteria.andStatEqualTo(DataStatus.ENABLED);
	       example.setOrderByClause("  news_time desc  limit "+ phdto.getBeginRow()+ ","+phdto.getRows());
	       return   mapper.selectByExample(example);
	   }
	   
	   /**
	    * 支持使用多个projectId做参数	 
	    * @author 朱振	
	    * @date 2015年10月27日 上午11:49:06	
	    * @version 1.0
	    * @param param
	    * @param projectIds
	    * @param phdto
	    * @return
	    * <p>修改人：朱振</p>
	    * <p>修改时间：2015年10月27日 上午11:49:06</p>
	    * <p>修改备注：</p>
	    */
	   public List<News> findBy(News param,List<String> projectIds, PageHelperDto phdto){
           NewsExample example = new NewsExample();
           Criteria criteria = example.createCriteria();
           
           //id
            if(!StringUtils.isEmpty(param.getId())){
                criteria.andIdEqualTo(param.getId());
            }
            
            //name
            if(!StringUtils.isEmpty(param.getName())){
                criteria.andNameEqualTo(param.getName());
            }
            
            //titile
            if(!StringUtils.isEmpty(param.getTitle())){
                criteria.andTitleEqualTo(param.getTitle());
            }
            
            //state
            if(param.getState()!=null){
                criteria.andStateEqualTo(param.getState());
            }
            
            //listTitle
            if(!StringUtils.isEmpty(param.getListTitle())){
                criteria.andListTitleEqualTo(param.getListTitle());
            }
//            if(!StringUtils.isEmpty(param.getProjId())){
//                criteria.andProjIdEqualTo(param.getProjId());
//            }
            if(!CollectionUtils.isEmpty(projectIds))
            {
                criteria.andProjIdIn(projectIds);
            }
           criteria.andStatEqualTo(DataStatus.ENABLED);
           example.setOrderByClause("  news_time desc  limit "+ phdto.getBeginRow()+ ","+phdto.getRows());
           return   mapper.selectByExample(example);
       }
	   
	   /**
	    * 查询符合条件的结果集的条数	 
	    * @author 朱振	
	    * @date 2015年10月27日 下午12:42:09	
	    * @version 1.0
	    * @param param
	    * @param projectIds
	    * @return
	    * <p>修改人：朱振</p>
	    * <p>修改时间：2015年10月27日 下午12:42:09</p>
	    * <p>修改备注：</p>
	    */
	   public Integer findCountBy(News param,List<String> projectIds)
	   {
	       NewsExample example = new NewsExample();
           Criteria criteria = example.createCriteria();
           
           //id
           if(!StringUtils.isEmpty(param.getId())){
                criteria.andIdEqualTo(param.getId());
            }
           
           //name
           if(!StringUtils.isEmpty(param.getName())){
                criteria.andNameEqualTo(param.getName());
            }
           
           //title
            if(!StringUtils.isEmpty(param.getTitle())){
                criteria.andTitleEqualTo(param.getTitle());
            }
            
            //state
            if(param.getState()!=null){
                criteria.andStateEqualTo(param.getState());
            }
            
            //listTitle
            if(!StringUtils.isEmpty(param.getListTitle())){
                criteria.andListTitleEqualTo(param.getListTitle());
            }
            
            //projectIds            
            if(!CollectionUtils.isEmpty(projectIds))
            {
                criteria.andProjIdIn(projectIds);
            }
           criteria.andStatEqualTo(DataStatus.ENABLED);
           return mapper.countByExample(example);
	   }
	   
	   public List<News> findBypage(News param,int beginRow, int endRow ,String searchDate){
		   NewsExample example = new NewsExample();
		   Criteria criteria = example.createCriteria();
		   if(!StringUtils.isEmpty(param.getId())){
	            criteria.andIdEqualTo(param.getId());
	        }
		   SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		   Date localDate;
		   if (searchDate != null)
	        { //查询日期为localDate的记录;
	          //获取当天日期
	            try
	            {
	                localDate = simpleDateFormat.parse(searchDate);
	                //获取下一天
	                Date nextDate = DateUtil.getSpecifiedDayBefore(searchDate);
	                criteria.andCreateTimeBetween(localDate, nextDate);
	            }
	            catch (java.text.ParseException e)
	            {
	                //  对异常进行简要描述
	            }
	        }
		   if(!StringUtils.isEmpty(param.getProjId())){
			   criteria.andProjIdEqualTo(param.getProjId());
		   }
		   criteria.andStatEqualTo(DataStatus.ENABLED);
		   example.setOrderByClause("  news_time desc  limit "+ beginRow+ ","+endRow);
	       return   mapper.selectByExample(example);
	   }
	   
	   public int findCount(News param){
		   NewsExample example = new NewsExample();
		   Criteria criteria = example.createCriteria();
		   if(!StringUtils.isEmpty(param.getId())){
	            criteria.andIdEqualTo(param.getId());
	        }
		   if(!StringUtils.isEmpty(param.getName())){
	        	criteria.andNameEqualTo(param.getName());
	        }
	        if(!StringUtils.isEmpty(param.getTitle())){
	        	criteria.andTitleEqualTo(param.getTitle());
	        }
	        if(param.getState()!=null){
	        	criteria.andStateEqualTo(param.getState());
	        }
	        if(!StringUtils.isEmpty(param.getListTitle())){
	        	criteria.andListTitleEqualTo(param.getListTitle());
	        }
		   criteria.andStatEqualTo(DataStatus.ENABLED);
		   return mapper.countByExample(example);
	   }
	   
	   public void insertNews(News param){
		    mapper.insert(param);
	   }
	
	   public void updateNews(News param){
		   mapper.updateByPrimaryKeySelective(param);
	   }
	   
	   public News findById(String id){
		  return  mapper.selectByPrimaryKey(id);
	   }
	   
	   public List<News> findAll(){
		   NewsExample example = new NewsExample();
		   Criteria criteria = example.createCriteria();
		   criteria.andStatEqualTo(DataStatus.ENABLED);
		   example.setOrderByClause("  create_time desc   ");
		   return mapper.selectByExample(example);
	   }
	   
	   
}
