package com.ssic.catering.manage.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ssic.catering.admin.pageModel.DataGrid;
import com.ssic.catering.admin.pageModel.Json;
import com.ssic.catering.admin.service.UserServiceI;
import com.ssic.catering.base.dto.NewsDto;
import com.ssic.catering.base.pojo.PageHelper;
import com.ssic.catering.base.service.ICreateImageService;
import com.ssic.catering.base.service.INewsService;
import com.ssic.catering.common.image.ImageInfoDto;
import com.ssic.catering.common.image.PropertiesUtils;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.util.UUIDGenerator;



@Controller
@RequestMapping("/newsController")
public class NewsController {
	@Autowired
	private INewsService  newsService;
    @Autowired
	private ICreateImageService createImageService;
    
    @Autowired
    private UserServiceI userService;
	
	protected static final Log logger = LogFactory.getLog(NewsController.class);
	
	
	@RequestMapping("/findBy")
	@ResponseBody
	public List<NewsDto>  findBy(NewsDto newsDto){
		return newsService.findBy(newsDto);
	}
	
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request){
		String nginxPath = PropertiesUtils.getProperty("nginx.url");
		request.setAttribute("nginxPath", nginxPath);
		return "carte/news/news";
	}
	
	@RequestMapping("/addNews")
	public String addNews(HttpServletRequest request,NewsDto newsDto){
		return "carte/news/newsAdd";
	}
	
	@RequestMapping("/editNews")
	public String editNews(HttpServletRequest request,NewsDto newsDto){
		NewsDto nDto = newsService.findById(newsDto.getId());
		request.setAttribute("nDto", nDto);
		return "carte/news/newsEdit";
	}
	
	@RequestMapping("/upLoadImage")
	public String  upLoadImage(HttpServletRequest request,String id){
		request.setAttribute("newsid", id);
		return "carte/news/newsUpLoad";
	}
	
	@RequestMapping("/umeditFun")
	public String umeditFun(HttpServletRequest request,String id){
		request.setAttribute("newsid", id);
		return "carte/umedit/news";
	}
	
	
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(NewsDto newsDto, PageHelper ph, HttpServletRequest request){
	    
	    DataGrid dataGrid = new DataGrid();

	    HttpSession session = request.getSession();
	    if(session == null)
	    {
	        logger.error("session不能为空");
	        return dataGrid;
	    }
	    
	    List<ProjectDto> pjds = userService.getProjectBySession(session);
	    if(CollectionUtils.isEmpty(pjds))
	    {
	        logger.error("该用户没有对应的projectId");
            return dataGrid;
	    }
	    
	    List<String> projectIds = new ArrayList<String>();
	    for(ProjectDto item: pjds)
        {
            projectIds.add(item.getId());
        }
	    
		List<NewsDto> list=  newsService.findBy(newsDto,projectIds, ph);
		
		int count = 0;
		if(!CollectionUtils.isEmpty(list))
		{
		    count = newsService.findCountBy(newsDto, projectIds);
		  //添加对应的项目名称
	        for(NewsDto item2: list)
	        {
	            for(ProjectDto item3: pjds)
	            {
	                if(!StringUtils.isEmpty(item2.getProjId()) && item2.getProjId().equals(item3.getId()))
	                {
	                    item2.setProjectName(item3.getProjName());
	                }
	            }
	        }
	        
	        dataGrid.setRows(list);
	        dataGrid.setTotal(Long.valueOf(count));
		}
	
		return dataGrid;
	}
	
	@RequestMapping("/insertNews")
	@ResponseBody
	public Json  insertNews(NewsDto newsDto,HttpServletRequest request) throws ParseException{
		Json j = new Json();
	    HttpSession session = request.getSession();
	    if(session == null)
	    {
	    	j.setMsg("session不能为空");
	    	j.setSuccess(false);
	    	return j;
	    }
	    
	    List<ProjectDto> pjds = userService.getProjectBySession(session);
	    if(pjds==null || pjds.size()==0){
	    	j.setMsg("项目ID不能为空");
	    	j.setSuccess(false);
	    	return j;
	    }
	    
	    if(pjds.size()==1){
		    newsDto.setProjId(pjds.get(0).getId());   
	    }else{
		    newsDto.setProjId("");    //后台登陆用户为超管，projid为空
	    }
	    
		newsDto.setId(UUIDGenerator.getUUID());
		newsDto.setStat(1);
		newsDto.setCreateTime(new Date());
		//初始状态0未发布
		newsDto.setState(0);
		newsService.insertNews(newsDto);
		j.setMsg("创建新闻成功！");
		j.setSuccess(true);
		return j;
	}
	
	@RequestMapping("/updateNews")
	@ResponseBody
	public Json updateNews(NewsDto newsDto){
		Json  j = new Json();
		if(newsDto.getId()==null || "".equals(newsDto.getId())){
			j.setMsg("新闻编号不能为空！");
			j.setSuccess(false);
			return j;
		}
		NewsDto newsDto2 = newsService.findById(newsDto.getId());
		//状态为1已发布的不能在修改
		if(newsDto2==null){
			j.setMsg("新闻不能为空！");
			j.setSuccess(false);
			return j;
		}else{
			int state=  newsDto2.getState();
			if(state==1){
				j.setMsg("该新闻已经发布不能修改！");
				j.setSuccess(false);
				return j;
			}
		}
		
		newsDto.setLastUpdateTime(new Date());
		newsService.updateNews(newsDto);
		j.setMsg("更新新闻成功！");
		j.setSuccess(true);
		return j;
	}
	
	@RequestMapping("/deleteNews")
	@ResponseBody
	public Json deleteNews(NewsDto newsDto){
		Json j = new Json();
		if(newsDto.getId()==null || newsDto.getId().equals("")){
			j.setMsg("新闻编号不能为空！");
			j.setSuccess(false);
			return j;
		}
		NewsDto newsDto2 = newsService.findById(newsDto.getId());
		//状态为1已发布的不能在修改
		if(newsDto2==null){
			j.setMsg("新闻不能为空！");
			j.setSuccess(false);
			return j;
		}else{
			int state=  newsDto2.getState();
			if(state==1){
				j.setMsg("该新闻已经发布不能删除！");
				j.setSuccess(false);
				return j;
			}
		}
		
		newsDto.setStat(0);
		newsDto.setLastUpdateTime(new Date());
		newsService.updateNews(newsDto);
		j.setMsg("删除新闻成功！");
		j.setSuccess(true);
		return j;
	}
	
	//新闻是否发布
	//点击发布，状态更新为1已发布， 不能再修改
	@RequestMapping("/publishNews")
	@ResponseBody
	public  Json  publishNews(NewsDto newsDto){
		Json j = new Json();
		if(newsDto.getId()==null || "".equals(newsDto.getId())){
			j.setMsg("新闻编号不能为空!");
			j.setSuccess(false);
			return j;
		}
		NewsDto newsDto2= newsService.findById(newsDto.getId());
		if(newsDto2.getState()==1){
			j.setMsg("该新闻已经发布！");
			j.setSuccess(false);
			return j;
		}
		if(newsDto2.getTextHtml()==null || "".equals(newsDto2.getTextHtml())){
			j.setMsg("正文不能为空!");
			j.setSuccess(false);
			return j;
		}
		newsDto.setState(1);
		newsDto.setNewsTime(new Date());
		newsService.updateNews(newsDto);
		j.setMsg("新闻发布成功！");
		j.setSuccess(true);
		return j;
	}
	
	@RequestMapping("/insertNewsInfo")
	@ResponseBody
	public Json insertNewsInfo(NewsDto newsDto,String newsid){
		Json j = new Json();
		
		if(newsDto.getTextHtml()==null || "".equals(newsDto.getTextHtml())){
			j.setMsg("新闻内容不能为空!");
			j.setSuccess(false);
			return j;
		}
		
		if(newsid==null || "".equals(newsid)){
			j.setMsg("新闻ID不能为空！");
			j.setSuccess(false);
			return j;
		}
		
		NewsDto newsDto2 = newsService.findById(newsid);
		//状态为1已发布的不能在修改
		if(newsDto2==null){
			j.setMsg("新闻不能为空！");
			j.setSuccess(false);
			return j;
		}else{
			int state=  newsDto2.getState();
			if(state==1){
				j.setMsg("该新闻已经发布不能修改！");
				j.setSuccess(false);
				return j;
			}
		}
		
		
		newsDto.setId(newsid);
		newsService.updateNews(newsDto);

		j.setMsg("填入新闻信息成功！");
		j.setSuccess(true);
		return j;
	}
	
	
	//上传图片
	
	@RequestMapping("/create")
	@ResponseBody
	public Json createImage(ImageInfoDto image, @RequestParam("image")MultipartFile myfile, HttpServletRequest request, HttpServletResponse response,String newsid) throws IOException {
		 logger.info("upload pic : " + image);
		 Json j = new Json();
		Map<String, Object> map= createImageService.createImage(image, myfile, request, response);
		 NewsDto  ndto = new NewsDto();
		 ndto.setId(newsid);
		 String urlmage = (String) map.get("image_url");
		 urlmage = "/upload/images/"+urlmage;
		 ndto.setListImageUrlId(urlmage);
		 ndto.setLastUpdateTime(new Date());
		 //如果已经有图片则更新image_url		
		 newsService.updateNews(ndto);

		j.setMsg((String) map.get("message"));
		j.setSuccess((boolean) map.get("success"));

    	return j;
	}
	
	@RequestMapping("/showPicture")
	@ResponseBody
	public void showPicture(HttpServletResponse response,String imageUrl) throws Exception{
		createImageService.showPicture(response, imageUrl);
	}
	
	@RequestMapping("/showText")
	public String showText(HttpServletRequest request,String id){
		 NewsDto newsDto = newsService.findById(id);
		 request.setAttribute("nDto", newsDto);
		 return "carte/news/newsShow";
	}
	
   }
