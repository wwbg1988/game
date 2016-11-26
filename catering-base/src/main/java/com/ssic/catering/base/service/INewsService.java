package com.ssic.catering.base.service;

import java.util.List;

import com.ssic.catering.base.dto.NewsDto;
import com.ssic.catering.base.pojo.PageHelper;


public interface INewsService {

	   List<NewsDto> findBy (NewsDto newsDto);
	   
	   List<NewsDto> findBy (NewsDto newsDto,PageHelper ph);
	   
	   int findCount(NewsDto newsDto);
	   
	   void insertNews(NewsDto newsDto);
	   
	   void updateNews(NewsDto newsDto);
	   
	   NewsDto findById(String id);
	   
	   List<NewsDto> findAll();
	   
	   List<NewsDto> findBy (NewsDto newsDto,int beginRow,int endRow,String searchDate);
	   
	   /**
	    * 当有多个projectId作参数时	 
	    * @author 朱振	
	    * @date 2015年10月27日 上午11:41:41	
	    * @version 1.0
	    * @param newsDto
	    * @param projectIds
	    * @param ph
	    * @return
	    * <p>修改人：朱振</p>
	    * <p>修改时间：2015年10月27日 上午11:41:41</p>
	    * <p>修改备注：</p>
	    */
	   List<NewsDto> findBy (NewsDto newsDto,List<String> projectIds,PageHelper ph);
	   
	   /**
	    * 获取以多个projectId为条件查询的结果集的条数 
	    * @author 朱振	
	    * @date 2015年10月27日 下午12:37:24	
	    * @version 1.0
	    * @param newsDto
	    * @param projectIds
	    * @return
	    * <p>修改人：朱振</p>
	    * <p>修改时间：2015年10月27日 下午12:37:24</p>
	    * <p>修改备注：</p>
	    */
	   Integer findCountBy(NewsDto newsDto,List<String> projectIds);
}
