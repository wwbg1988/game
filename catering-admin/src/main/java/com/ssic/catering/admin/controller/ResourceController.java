package com.ssic.catering.admin.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.admin.dao.TImsMenuDao;
import com.ssic.catering.admin.dto.TImsMenuDto;
import com.ssic.catering.admin.pageModel.Json;
import com.ssic.catering.admin.pageModel.Resource;
import com.ssic.catering.admin.pageModel.SessionInfo;
import com.ssic.catering.admin.pageModel.Tree;
import com.ssic.catering.admin.service.ResourceServiceI;
import com.ssic.catering.admin.service.ResourceTypeServiceI;
import com.ssic.catering.admin.util.ConfigUtil;


/**
 * 资源控制器
 * 
 * @author 刘博
 * 
 */
@Controller
@RequestMapping("/resourceController")
public class ResourceController extends BaseController {

	@Autowired
	private ResourceServiceI resourceService;

	@Autowired
	private ResourceTypeServiceI resourceTypeService;
	
	@Autowired
	private TImsMenuDao menuDao;

	/**
	 * 获得资源树(资源类型为菜单类型)
	 * 
	 * 通过用户ID判断，他能看到的资源
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/tree")
	@ResponseBody
	public List<Tree> tree(HttpSession session,String tabType) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.SESSIONINFONAME);
		if(StringUtils.isEmpty(tabType)){
		    sessionInfo.setTabType(String.valueOf(0));
		}else{
		    sessionInfo.setTabType(tabType);
		}
		return resourceService.tree(sessionInfo);
	}
	
	
	@RequestMapping("/tabTree")
    @ResponseBody
    public List<Tree> tabTree(HttpSession session,String tabType) {
        SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.SESSIONINFONAME);
        if(StringUtils.isEmpty(tabType)){
            sessionInfo.setTabType(String.valueOf(1));
        }else{
            sessionInfo.setTabType(tabType);
        }
        return resourceService.tabTree(sessionInfo);
    }
	
	
	   @RequestMapping("/allsTree")
	    @ResponseBody
	    public List<Tree> allsTree(HttpSession session,String tabType) {
	        SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.SESSIONINFONAME);
	         return resourceService.allsTree(sessionInfo);
	    }
	   
	/**
	 * 获得资源树(包括所有资源类型)
	 * 
	 * 通过用户ID判断，他能看到的资源
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/allTree")
	@ResponseBody
	public List<Tree> allTree(HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.SESSIONINFONAME);
		return resourceService.allTree(sessionInfo);
	}

	/**
	 * 跳转到资源管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager() {
		return "admin/resource";
	}

	/**
	 * 跳转到资源添加页面
	 * 
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		request.setAttribute("menuTypeList", resourceTypeService.getMenuTypeList());
		Resource r = new Resource();
		r.setId(UUID.randomUUID().toString());
		request.setAttribute("menuType", r);
		return "admin/resourceAdd";
	}

	/**
	 * 添加资源
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(TImsMenuDto menuDto, HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.SESSIONINFONAME);
		Json j = new Json();
		resourceService.add(menuDto, sessionInfo);
		j.setSuccess(true);
		j.setMsg("添加成功！");
		return j;
	}

	/**
	 * 跳转到资源编辑页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		request.setAttribute("menuTypeList", resourceTypeService.getMenuTypeList());
		TImsMenuDto r = menuDao.findById(id);
		request.setAttribute("menuDto", r);
		return "admin/resourceEdit";
	}

	/**
	 * 编辑资源
	 * 
	 * @param resource
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(TImsMenuDto menuDto) {
		Json j = new Json();
		resourceService.edit(menuDto);
		j.setSuccess(true);
		j.setMsg("编辑成功！");
		return j;
	}

	/**
	 * 获得资源列表
	 * 
	 * 通过用户ID判断，他能看到的资源
	 * 
	 * @return
	 */
	@RequestMapping("/treeGrid")
	@ResponseBody
	public List<TImsMenuDto> treeGrid(HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.SESSIONINFONAME);
		return resourceService.treeGrid(sessionInfo);
	}

	/**
	 * 删除资源
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id) {
		Json j = new Json();
		resourceService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
