package com.ssic.game.admin.controller.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.ssic.game.admin.service.UserServiceI;

/**
 * 图表控制器
 * 
 * @author 刘博
 * 
 */
@Controller
@RequestMapping("/chartController")
public class ChartController extends BaseController {

	@Autowired
	private UserServiceI userService;

	/**
	 * 用户创建时间图表
	 * 
	 * @return
	 */
	@RequestMapping("/userCreateDatetimeChart")
	public String userCreateDatetimeChart(HttpServletRequest request) {
		request.setAttribute("userCreateDatetimeChart", JSON.toJSONString(userService.userCreateDatetimeChart()));
		return "charts/userCreateDatetimeChart";
	}

}
