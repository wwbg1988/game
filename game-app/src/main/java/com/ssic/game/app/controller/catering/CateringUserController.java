package com.ssic.game.app.controller.catering;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.common.util.MD5Util;
import com.ssic.catering.common.util.UploadUtil;
import com.ssic.game.app.controller.dto.ImsUserBindingDto;
import com.ssic.game.common.constant.HttpConstants;
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.service.CateringUserService;
import com.ssic.game.common.service.IImsUserService;
import com.ssic.game.common.util.HttpDataResponse;
import com.ssic.util.BeanUtils;
import com.ssic.util.digest.MD5Coder;
import com.ssic.util.model.Response;

/**		   
 * <p>Title: AppUserController </p>
 * <p>Description: 用户相关操作类</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author yuanbin	
 * @date 2015年8月4日 下午15:20:11
 * @version 1.0
 * <p>修改人：yuanbin</p>
 * <p>修改时间：2015年8月4日 下午15:20:11</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping("/http/api/user")
public class CateringUserController  
{ 
     
    protected static final Log logger = LogFactory.getLog(CateringUserController.class);

    @Autowired
    private CateringUserService cateringUserService;
    
    @Autowired
    private IImsUserService imsUserService;

    /**         
     * login：APP用户登录
     * @param name  用户登录帐号
     * @param pwd   用户密码
     * @return  Response<UserInfoDto> 
     * @throws Exception 
     * @exception	
     * @author yuanbin
     * @date 2015年8月4日 下午15:12:11
     */
    @RequestMapping(value = "/login.do") 
    @ResponseBody
    public Response<ImsUsersDto> login(HttpSession session, @RequestParam(required = true) String name,
        @RequestParam(required = true) String pwd) throws Exception
    {
        logger.info("AppUserController user login name=" + name + "|pwd=" + pwd);
        Response<ImsUsersDto> result = new Response<ImsUsersDto>();
        
        //封装User基本信息
        ImsUsersDto imsUsersDto=new ImsUsersDto();
        imsUsersDto.setUserAccount(name);
        imsUsersDto.setPassword(pwd);
        //查用户信息
        List<ImsUsersDto> ud = cateringUserService.findUsers(imsUsersDto);  

        //判断用户是否存在
        if(null == ud || null == imsUsersDto){
            result.setMessage("登录失败，用户不存在，请联系管理员！");
            return result;
        }
        
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
        md5Pwd = MD5Coder.encodeMD5Hex(pwd);    
        /** 密码判断 */
        if(!StringUtils.equals(md5Pwd,imsUsersDto.getPassword())){
        	result.setMessage("登录失败，密码错误");
            return result;
        }
		return result;
    }

    /**     
     * modifyPwd：修改用户密码
     * @param name
     * @param oldPwd
     * @param newPwd
     * @return
     * @throws Exception 
     * @exception	
     * @author yuanbin
     * @date 2015年8月4日 下午15:12:11	 	 
     */
    	@RequestMapping("/editPassword.do")
    	@ResponseBody
    	public Response<String> upPassword(String oldPwd,String userId,String newPwd) throws Exception{
    		
            Response<String> result = new Response<String>();

    	    if(StringUtils.isEmpty(oldPwd))
    	    {
    	        result.setStatus(HttpDataResponse.HTTP_FAILE);
                result.setMessage("旧密码不能为空");
                return result;
    	    }
    	    
    	    if(StringUtils.isEmpty(userId))
            {
    	        result.setStatus(HttpDataResponse.HTTP_FAILE);
                result.setMessage("userId不能为空");
                return result;
            }
    	    
    	    if(StringUtils.isEmpty(newPwd))
            {
    	        result.setStatus(HttpDataResponse.HTTP_FAILE);
                result.setMessage("新密码不能为空");
                return result;
            }
    	    
    		//查用户信息
            ImsUsersDto ud = cateringUserService.findByUserId(userId);
            if(StringUtils.isEmpty(oldPwd)){
                result.setStatus(HttpDataResponse.HTTP_FAILE);
                result.setMessage("旧密码不能为空");
    	    	return result;
    	    }
            //判断旧密码
            String md5Pwd = "";
            md5Pwd = MD5Coder.encodeMD5Hex(oldPwd);    
            /** 密码判断 */
            if(!StringUtils.equals(md5Pwd,ud.getPassword())){
                result.setStatus(HttpDataResponse.HTTP_FAILE);
                result.setMessage("修改失败");
                result.setData("旧密码校验错误！");
                return result;
            }  
    		ImsUsersDto newUser =new ImsUsersDto();
    		BeanUtils.copyProperties(ud, newUser);
    		if(newUser.getPassword()==null || newUser.getPassword().equals("")){
                result.setStatus(HttpDataResponse.HTTP_FAILE);
                result.setMessage("修改失败");
    		    result.setData("新密码不能为空");
    	    	return result;
    	    }
    	    if (newUser.getPassword().length()<6){
                result.setStatus(HttpDataResponse.HTTP_FAILE);
                result.setMessage("修改失败");
    	        result.setData("密码长度不能小于六位");
    			return result;
    		}

            newUser.setModifydatetime(new Date());
            newUser.setPassword(MD5Util.md5(newPwd));
            cateringUserService.upPasswod(newUser);
            result.setMessage("修改成功");
            result.setStatus(HttpDataResponse.HTTP_SUCCESS);
    		return result;
    	}

    /**             
     * logout：用户登出帐号
     * @param session
     * @param name  
     * @return
     * @exception	
     * @author yuanbin
     * @date 2015年8月4日 下午15:12:11	  
     */
    @RequestMapping(value = "/logout.do")
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
    
    /**
     * <p>显示个人中心</p>
     * @author 朱振
     * @date 2015年8月12日 下午1:17:52
     * @param userId
     * @return
     */
    @RequestMapping(value="/userCenter.do", method={RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Response<ImsUsersDto> userCenter(String userId)
    {
    	Response<ImsUsersDto> result = new Response<ImsUsersDto>();
    	
    	ImsUsersDto ud = cateringUserService.findByUserId(userId);
    	
    	result.setStatus(HttpDataResponse.HTTP_SUCCESS);
    	result.setData(ud);
    	result.setMessage("");    			
    
    	return result;
    }
    
    
    
    /**
     * 
     * editImage：修改图片
     * @param userId
     * @param base64Img
     * @return
     * @exception	
     * @author 朱振
     * @date 2015年8月24日 下午5:48:26
     */
    @RequestMapping(value="/editImage.do", method={RequestMethod.GET, RequestMethod.POST})
    @ResponseBody 
    public Response<String> editImage(String userId, String base64Img, HttpSession session)
    {
        Response<String> result = new Response<>();
        if(StringUtils.isEmpty(userId))
        {
            result = new Response<String>();
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("用户id不能为空");
            
            return result;
        }
        
        if(StringUtils.isEmpty(base64Img))
        {
            result = new Response<String>();
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("用户图片不能为空");
            
            return result;
        }
        
        String rootPath = session.getServletContext().getRealPath("/");            
        String realPath = UploadUtil.uploadBase64Picture(base64Img, rootPath+"/uploadPic","jpg");
        
        if(!StringUtils.isEmpty(realPath))
        {
            ImsUsersDto imsUsersDto = new ImsUsersDto();
            imsUsersDto.setId(userId);
            imsUsersDto.setUserImage(realPath.substring(rootPath.length()+1));  
            
            cateringUserService.updateUserSelectiveById(imsUsersDto);
            return result;
        }  
        
        result.setStatus(HttpDataResponse.HTTP_SUCCESS);
        result.setMessage("修改失败");
        
        return result;
    }

    
    /**
     * 显示ims_user的绑定状态	 
     * @author 朱振	
     * @date 2015年10月30日 下午3:36:58	
     * @version 1.0
     * @param session
     * @param userId
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年10月30日 下午3:36:58</p>
     * <p>修改备注：</p>
     */
    @RequestMapping("/getBinding.do")
    @ResponseBody
    public Response<ImsUserBindingDto> showBinding(@RequestParam("userId") String userId, HttpServletRequest request)
    {
//        logger.info("userId=" + userId);
        Response<ImsUserBindingDto> response = new Response<>();
        
        //userId
        if(StringUtils.isEmpty(userId))
        {
            logger.error("userId不能为空");
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("userId不能为空");
            
            return response;
        }
        
        //查询用户是否存在
        ImsUsersDto imsUser = imsUserService.findByUserId(userId);
        if(imsUser == null)
        {
            logger.error("用户不存在");
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("用户不存在");
            
            return response;
        }
        
        ImsUserBindingDto data = new ImsUserBindingDto();
        
        data.setUserId(imsUser.getId());//设置userId
        
        //手机绑定状态
        if(imsUser.getMobileState() != null && imsUser.getMobileState() ==2 && !StringUtils.isEmpty(imsUser.getMobilePhone()))
        {
            data.setMobileBinding(1);
            data.setMobile(imsUser.getMobilePhone());
        }
        else
        {
            data.setMobileBinding(0);
            data.setMobile("");
        }
        
        //微信绑定状态
        if(!StringUtils.isEmpty(imsUser.getWeixin()))
        {
            data.setWeixinBinding(1);
            data.setWeixinNickname(imsUser.getWeixinNickName()==null?"":imsUser.getWeixinNickName());
        }
        else
        {
            data.setWeixinBinding(0);
            data.setWeixinNickname("");
        }
        
        response.setMessage("查询成功");
        response.setData(data);
        response.setStatus(HttpDataResponse.HTTP_SUCCESS);
        
        return response;        
    }
}
