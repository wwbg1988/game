package com.ssic.game.app.controller.user;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.common.util.AppResponse;
import com.ssic.game.app.controller.dto.ProcessDto;
import com.ssic.game.app.controller.dto.ProjectDto;
import com.ssic.game.app.controller.dto.UserInfoDto;
import com.ssic.game.app.listener.InitApplicationContext;
import com.ssic.game.app.service.IUserOperateSerivce;
import com.ssic.game.common.constant.HttpConstants;
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.dto.LTUserDto;
import com.ssic.game.common.service.IImsUserService;
import com.ssic.game.common.service.ILiaoTianService;
//import com.ssic.game.common.HttpConstants;
import com.ssic.game.common.util.HttpDataResponse;
import com.ssic.util.digest.MD5Coder;
import com.ssic.util.model.Response;

/**		   
 * <p>Title: AppUserController </p>
 * <p>Description: 用户相关操作类</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Vincent	
 * @date 2015年6月24日 下午8:12:11	
 * @version 1.0
 * <p>修改人：Vincent</p>
 * <p>修改时间：2015年6月24日 下午8:12:11</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping("/http/api/user")
public class AppUserController
{

    protected static final Log logger = LogFactory.getLog(InitApplicationContext.class);

    @Autowired
    private IUserOperateSerivce userOperateSerivce;
    @Autowired
    private ILiaoTianService liaoTianService;
    @Autowired
    private IImsUserService imsUserService;

    /**         
     * login：APP用户登录
     * @param name  用户登录帐号
     * @param pwd   用户密码
     * @return  Response<UserInfoDto> 
     * @exception	
     * @author Vincent
     * @date 2015年6月24日 下午8:12:11	 
     */
    @RequestMapping(value = "/login.do", method = {RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Response<UserInfoDto> login(HttpSession session, @RequestParam(required = true) String name,
        @RequestParam(required = true) String pwd)
    {
        logger.info("AppUserController user login name=" + name + "|pwd=" + pwd);
        Response<UserInfoDto> result = new Response<UserInfoDto>();
        if (StringUtils.isEmpty(name))
        {
            result.setStatus(HttpDataResponse.NAME_IS_NULL);
            result.setMessage("用户帐号不能为空");
            return result;
        }
        if (StringUtils.isEmpty(pwd))
        {
            result.setStatus(HttpDataResponse.PWD_IS_NULL);
            result.setMessage("用户密码不能为空");
            return result;
        }

        String md5Pwd = "";
        try
        {
            md5Pwd = MD5Coder.encodeMD5Hex(pwd);
        }
        catch (Exception e)
        {
            result.setStatus(HttpDataResponse.SYS_EXCEPTION);
            result.setMessage("系统异常请稍候再试!");
            return result;
        }

        UserInfoDto userInfo = userOperateSerivce.login(name, md5Pwd);

        if (null != userInfo && null != userInfo.getUserAccount())
        {

            List<ProjectDto> projDtoList = userOperateSerivce.queryProjectInfo(userInfo.getUserId());

            if (CollectionUtils.isEmpty(projDtoList))
            {
                result.setStatus(HttpDataResponse.USER_NOT_PROJECT);
                result.setMessage("用户没有项目授权!");
                return result;
            }
            if (projDtoList.size() > 1)
            {
                result.setStatus(HttpDataResponse.USER_NOT_PROJECT);
                result.setMessage("用户所属于多个项目!");
                return result;
            }
            session.setAttribute(HttpConstants.USER_INFO_SSESSION + userInfo.getUserId(), userInfo);
            userInfo.setProjList(projDtoList);
            result.setStatus(HttpDataResponse.HTTP_SUCCESS);
            //不向用户发那会密码
            result.setData(userInfo);
            result.setMessage("SUCCESS!");
            return result;
        }
        else
        {
            result.setStatus(HttpDataResponse.NOT_FOUND_USER);
            result.setMessage("账号密码错误，请重新输入！");
            return result;
        }
    }

    /**     
     * modifyPwd：修改用户密码
     * @param session
     * @param name
     * @param oldPwd
     * @param newPwd
     * @return
     * @exception	
     * @author Vincent
     * @date 2015年6月27日 下午8:17:01	 
     */
    @RequestMapping(value = "/modifypwd.do", method = {RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Response<String> modifyPwd(HttpSession session, @RequestParam(required = true) String userId,
        @RequestParam(required = true) String oldPwd, @RequestParam(required = true) String newPwd)
    {
        logger.info("AppUserController user modifypwd name=" + userId);
        Response<String> result = new Response<String>();
        
        if(newPwd==null || "".equals(newPwd) || oldPwd==null || "".equals(oldPwd)){
        	result.setMessage("密码不能为空");
        	result.setStatus(AppResponse.RETURN_FAILE);
        	return result;
        }

        if(userId==null || "".equals(userId)){
        	result.setMessage("用户ID不能为空");
        	result.setStatus(AppResponse.RETURN_FAILE);
        	return result;
        }
        
        UserInfoDto userInfo = (UserInfoDto) session.getAttribute(HttpConstants.USER_INFO_SSESSION + userId);

        if (null == userInfo)
        {
            result.setStatus(HttpDataResponse.HTTP_SUCCESS);
            result.setMessage("用户未登录!");
            return result;
        }

        String md5OldPwd = "";
        String md5NewPwd = "";
        try
        {
            md5OldPwd = MD5Coder.encodeMD5Hex(oldPwd);
            md5NewPwd = MD5Coder.encodeMD5Hex(newPwd);
        }
        catch (Exception e)
        {
            result.setStatus(HttpDataResponse.SYS_EXCEPTION);
            result.setMessage("系统异常请稍候再试!");
            return result;
        }

        if (userInfo.getPassword().equals(md5OldPwd))
        {
        	//修改IM聊天密码
        	ImsUsersDto userDto = imsUserService.findByUserId(userId);
        	if(userDto!=null){
        	   	LTUserDto lTUserDto = new LTUserDto();
            	lTUserDto.setUserAccount(userDto.getUserAccount());
            	lTUserDto.setNewPassword(md5NewPwd);
            	liaoTianService.EditLTPassWord(lTUserDto);
        	}
     
            //修改密码      
            boolean modify = userOperateSerivce.modifyPwd(userId, md5NewPwd);
            if (modify)
            {
                session.removeAttribute(HttpConstants.USER_INFO_SSESSION + userId);
                result.setStatus(HttpDataResponse.HTTP_SUCCESS);
                result.setData("");
                result.setMessage("密码修改成功，请重新登录!");
                return result;
            }
            else
            {
                result.setStatus(HttpDataResponse.SYS_EXCEPTION);
                result.setData("");
                result.setMessage("密码修改失败!");
                return result;
            }
        }
        else
        {
            //修改密码
            result.setStatus(HttpDataResponse.HTTP_SUCCESS);
            result.setData("");
            result.setMessage("密码不正确!");
            return result;
        }

    }

    /**             
     * logout：一句话描述方法功能
     * @param session
     * @param name  用户登出帐号
     * @return
     * @exception	
     * @author Vincent
     * @date 2015年6月29日 下午4:54:01	 
     */
    @RequestMapping(value = "/logout.do", method = {RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    //produces="text/html;charset=UTF-8"
    public Response<String> logout(HttpSession session, @RequestParam(required = true) String userId)
    {
        logger.info("AppUserController user logout name=" + userId);
        Response<String> result = new Response<String>();

        if (null != session.getAttribute(HttpConstants.USER_INFO_SSESSION + userId))
        {
            session.removeAttribute(HttpConstants.USER_INFO_SSESSION + userId);

        }
        result.setStatus(HttpDataResponse.HTTP_SUCCESS);
        result.setData("");
        result.setMessage("退出成功!");
        return result;
    }

    @RequestMapping(value = "/loadproj.do", method = {RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    //produces="text/html;charset=UTF-8"
    public Response<List<ProcessDto>> loadProjInfo(HttpSession session,
        @RequestParam(required = true) String userId, @RequestParam(required = true) String projId)
    {
        logger.info("AppUserController user loadProjInfo userId=" + userId);
        Response<List<ProcessDto>> result = new Response<List<ProcessDto>>();

        if (null == session.getAttribute(HttpConstants.USER_INFO_SSESSION + userId))
        {
            result.setStatus(HttpDataResponse.USER_NOT_ONLINE);
            result.setMessage("用户未登录!");
            return result;

        }

        List<ProcessDto> listProj = userOperateSerivce.queryProcessInfo(userId, projId);

        result.setStatus(HttpDataResponse.HTTP_SUCCESS);
        result.setData(listProj);
        result.setMessage("查询用户项目信息成功!");
        return result;
    }

}
