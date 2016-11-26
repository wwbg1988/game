package com.ssic.game.app.controller.lbs;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.lbs.dto.TransportDriverDto;
import com.ssic.catering.lbs.service.TransportDriverService;
import com.ssic.game.app.listener.InitApplicationContext;
import com.ssic.game.common.constant.HttpConstants;
import com.ssic.game.common.util.HttpDataResponse;
import com.ssic.util.digest.MD5Coder;
import com.ssic.util.model.Response;

/**
 * 		
 * <p>Title: LbsUserController </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年11月25日 上午9:53:33	
 * @version 1.0
 * <p>修改人：apple</p>
 * <p>修改时间：2015年11月25日 上午9:53:33</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping("/http/api/lbs")
public class AppLbsUserController {
	private static final Log logger = LogFactory.getLog(InitApplicationContext.class);

	@Autowired
	private TransportDriverService transportDriverService;
	
	/**
	 * 
	 * login：LBS APP用户登录
	 * @param session
	 * @param name
	 * @param pwd
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年11月25日 上午10:53:30
	 */
	@RequestMapping(value = "/login.do", method = {RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Response<TransportDriverDto> login(HttpSession session, @RequestParam(required = true) String username,
        @RequestParam(required = true) String pwd){
		//记录用户登录日志
		logger.info("LbsUserController user login name=" + username + "|pwd=" + pwd);
		
		Response<TransportDriverDto> result = new Response<TransportDriverDto>();
		
		try {
			if (StringUtils.isEmpty(username)) {
				result.setStatus(HttpDataResponse.NAME_IS_NULL);
				result.setMessage("用户帐号不能为空");
				return result;
			}
			if(StringUtils.isEmpty(pwd)){
				result.setStatus(HttpDataResponse.PWD_IS_NULL);
				result.setMessage("用户密码不能为空");
				return result;
			}
			//把传回的明文密码加密
			String md5Pwd = MD5Coder.encodeMD5Hex(pwd);
			//验证密码
			TransportDriverDto userDto = transportDriverService.findUserNameAndPwd(username, md5Pwd);
			if(userDto != null){
				//验证通过查询用户信息
				 session.setAttribute(HttpConstants.USER_INFO_SSESSION + userDto.getAccount(), userDto);
		         result.setStatus(HttpDataResponse.HTTP_SUCCESS);
		         result.setMessage("登录成功!");
		         result.setData(userDto);
		 		 return result;
			}else{
				result.setStatus(HttpDataResponse.NOT_FOUND_USER);
	            result.setMessage("账号密码错误，请重新输入！");
	            return result;
			}
			
		} catch (Exception e) {
			result.setStatus(HttpDataResponse.SYS_EXCEPTION);
            result.setMessage("系统异常请稍候再试!");
            return result;
		}
	}
}
