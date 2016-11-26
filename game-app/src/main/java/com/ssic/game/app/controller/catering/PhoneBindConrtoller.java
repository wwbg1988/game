/**
 * 
 */
package com.ssic.game.app.controller.catering;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.service.CateringUserService;
import com.ssic.util.model.Response;

/**		
 * <p>Title: PhoneBind </p>
 * <p>Description: 手机号绑定</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author yuanbin	
 * @date 2015年8月11日 下午6:30:47	
 * @version 1.0
 * <p>修改人：yuanbin</p>
 * <p>修改时间：2015年8月11日 下午6:30:47</p>
 * <p>修改备注：</p>
 */

@Controller
@RequestMapping("/http/api/PhoneBind")
public class PhoneBindConrtoller {
	/*
	 * 需要短信验证码
	 * 
	 * 
	 * 
	 */
    @Autowired
    private CateringUserService cateringUserService;
    
    @RequestMapping(value = "/bind.do")
    @ResponseBody
    public Response<ImsUsersDto> bind(HttpSession session, @RequestParam(required = true) String phone,
    		@RequestParam(required = true) String userId,String codes) throws Exception
    {
        Response<ImsUsersDto> result = new Response<ImsUsersDto>();
        
        //短信、验证码验证
        ImsUsersDto imsUser = new ImsUsersDto();
        //imsUser.setSmsVerifyCode(smsVerifyCode);设置验证码字段
        
        //验证验证码的时效性
        /*
        String random = (String)request.getSession().getAttribute("rdnu");
        boolean validateSMS = validateUser(imsUser,random,request,respMap);
        if(!validateSMS){
            return ;
        }*/

        
        ImsUsersDto imsUsersDto=new ImsUsersDto();
        imsUsersDto.setPhone(phone);
        imsUsersDto.setId(userId);
        imsUsersDto.setStat(1);
        //？？？设置验证码；
        cateringUserService.updateUserByuserId(imsUsersDto);
        
		return result;  
        
    }
    /**
     * 绑定手机验证码效验
     * @author yuanbin	
     * @param bean          需验证对象
     * @param random        session中存验证码
     * @param request       请求对象
     * @return String
     * @throws Exception
     */
    /*
    private boolean validateUser(ImsUsersDto bean,String random,HttpServletRequest request,Map<String, Object> respMap){
        respMap.put(Constants.CTX_KEY_ERROR,Constant.FAILED_FLAG);

        //是否开启登录验证码、手机验证码验证
        if(SMS_LOGIN_VALIDATE_ENABLED){
            if(StringCommonUtils.isNull(bean.getVerificationCode())){
                respMap.put(Constants.CTX_KEY_MSG,"验证码不能为空");
                return false;
            }
            if(!bean.getVerificationCode().equals(random)){
                respMap.put(Constants.CTX_KEY_MSG,"验证码错误");
                return false;
            }

           
            //手机获取短信验证码登录
            //String oldTime = (String)request.getSession().getAttribute(Constants.SYS_SMSVERIFYSTARTTIME);
            //log.info("手机获取短信验证码登录，发送验证码时间，{}",oldTime);
            //if(StringUtils.isEmpty(oldTime)){
            //    respMap.put(Constants.CTX_KEY_MSG,"短信验证码不正确");
            //    return false;
            //}
            
            String now = String.valueOf(DateUtils.getToDay(DateUtils.DATE_TIME_FORMAT_NO_SPLIT));
            log.info("手机获取短信验证码登录,当前时间，{}",now);
            long between = DateUtils.getAroundMinute(oldTime,now);
            //手机验证码登录超时时间 （秒）
            long cacheTime = SMS_LOGIN_CACHE_TIME;
            // 获得某用户手机验证码
            String verifyCode = (String)request.getSession().getAttribute(bean.getId());
            if(between > cacheTime){
                request.getSession().removeAttribute(Constants.SYS_SMSVERIFYSTARTTIME);
                request.getSession().removeAttribute(String.valueOf(bean.getId()));
                respMap.put(Constants.CTX_KEY_MSG,"验证码超时,请重新获取!");
                return false;
            }
            if(StringUtils.isEmpty(bean.getSmsVerifyCode())){
                respMap.put(Constants.CTX_KEY_MSG,"请输入短信验证码!");
                return false;
            }
            if(!StringUtils.equals(bean.getSmsVerifyCode(),verifyCode)){
                respMap.put(Constants.CTX_KEY_MSG,"短信验证码输入不正确!");
                return false;
            }
        }
        return true;
    }
    */
}

