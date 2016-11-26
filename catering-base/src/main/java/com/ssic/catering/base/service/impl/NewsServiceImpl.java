package com.ssic.catering.base.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.catering.base.dao.NewsDao;
import com.ssic.catering.base.dto.NewsDto;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.pojo.News;
import com.ssic.catering.base.pojo.PageHelper;
import com.ssic.catering.base.service.INewsService;
import com.ssic.util.BeanUtils;

@Service
public class NewsServiceImpl implements INewsService{

    private static Logger log = Logger.getLogger(NewsServiceImpl.class);
    
	@Autowired
	private NewsDao newsDao;
	
	@Override
	public List<NewsDto> findBy(NewsDto newsDto) {
		News news = new News();
		BeanUtils.copyProperties(newsDto, news);
		List<News> listNews= newsDao.findBy(news);
		List<NewsDto> list =  BeanUtils.createBeanListByTarget(listNews, NewsDto.class);
		return list;
	}

	@Override
	public List<NewsDto> findBy(NewsDto newsDto, PageHelper ph) {
		// TODO Auto-generated method stub
		PageHelperDto phdto = new PageHelperDto();
		 phdto.setOrder(ph.getOrder());
		 phdto.setPage(ph.getPage());
		 phdto.setRows(ph.getRows());
		 phdto.setSort(ph.getSort());
		 phdto.setBeginRow((ph.getPage()-1)*ph.getRows());
		
		 News news = new News();
		 BeanUtils.copyProperties(newsDto, news);
		 List<News> listnews = newsDao.findBy(news,phdto);
		 List<NewsDto> listdto = BeanUtils.createBeanListByTarget(listnews, NewsDto.class);
		 
		return listdto;
	}
	
	@Override
	public int findCount(NewsDto newsDto) {
		// TODO Auto-generated method stub
		News news = new News();
		BeanUtils.copyProperties(newsDto, news);
		return newsDao.findCount(news);
	}

	@Override
	public void insertNews(NewsDto newsDto) {
		// TODO Auto-generated method stub
		News news = new News();
		BeanUtils.copyProperties(newsDto, news);
		newsDao.insertNews(news);
	}

	@Override
	public void updateNews(NewsDto newsDto) {
		// TODO Auto-generated method stub
		News news = new News();
		BeanUtils.copyProperties(newsDto, news);
		newsDao.updateNews(news);
	}

	@Override
	public NewsDto findById(String id) {
		// TODO Auto-generated method stub
		News news = newsDao.findById(id);
		NewsDto newsDto = new NewsDto();
		if(news!=null){
			BeanUtils.copyProperties(news, newsDto);
			return newsDto;
		}else{
			return null;
		}		
	}

	@Override
	public List<NewsDto> findAll() {
		// TODO Auto-generated method stub
		List<News> list = newsDao.findAll();
	    List<NewsDto> listdto = BeanUtils.createBeanListByTarget(list, NewsDto.class);
		return listdto;
	}

	@Override
	public List<NewsDto> findBy(NewsDto newsDto, int beginRow, int endRow ,String searchDate) {
		// TODO Auto-generated method stub
		 News news = new News();
		 BeanUtils.copyProperties(newsDto, news);
		 List<News> list = newsDao.findBypage(news,beginRow,endRow,searchDate);
		 List<NewsDto> listdto = BeanUtils.createBeanListByTarget(list, NewsDto.class);
		return listdto;
	}

	/**
     * 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.INewsService#findBy(com.ssic.catering.base.dto.NewsDto, java.util.List, com.ssic.catering.base.pojo.PageHelper)
     */
    @Override
    public List<NewsDto> findBy(NewsDto newsDto, List<String> projectIds, PageHelper ph)
    {
        log.info("parameters:newsDto="+newsDto+";projectIds="+projectIds+";ph="+ph);
        if(newsDto == null)
        {
            log.error("参数newsDto为空");
            return null;
        }
        
        if(ph == null)
        {
            log.error("参数PageHelper为空");
            return null;
        }
        
        PageHelperDto phdto = new PageHelperDto();
        phdto.setOrder(ph.getOrder());
        phdto.setPage(ph.getPage());
        phdto.setRows(ph.getRows());
        phdto.setSort(ph.getSort());
        phdto.setBeginRow((ph.getPage()-1)*ph.getRows());
       
        News news = new News();
        BeanUtils.copyProperties(newsDto, news);
        List<News> listnews = newsDao.findBy(news, projectIds, phdto);
        List<NewsDto> listdto = BeanUtils.createBeanListByTarget(listnews, NewsDto.class);
        
       return listdto;
    }
    
    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.INewsService#findCountBy(com.ssic.catering.base.dto.NewsDto, java.util.List)
     */
    @Override
    public Integer findCountBy(NewsDto newsDto, List<String> projectIds)
    {
        log.info("parameters:newsDto="+newsDto+";projectIds="+projectIds);
        if(newsDto == null)
        {
            log.error("参数newsDto为空");
            return null;
        }
       
        News news = new News();
        BeanUtils.copyProperties(newsDto, news);
        
        return newsDao.findCountBy(news, projectIds);
    }

}
