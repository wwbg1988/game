package com.ssic.game.common.service.impl;

import java.util.Map;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bcloud.msg.http.HttpSender;
import com.ssic.game.common.HttpDataResponse;
import com.ssic.game.common.constant.MobileMessageType;
import com.ssic.game.common.dto.MobileMessageSenderDto;
import com.ssic.game.common.service.IMobileService;
import com.ssic.util.ArrayUtils;
import com.ssic.util.model.Response;

/**
 * <p>
 * Title: MobileServiceImpl
 * </p>
 * <p>
 * Description: 类描述
 * </p>
 * <p>
 * Copyright (c) 2015
 * </p>
 * <p>
 * Company: 上海天坊信息科技有限公司
 * </p>
 * 
 * @author 朱振
 * @date 2015年10月28日 上午9:32:09
 * @version 1.0
 *          <p>
 *          修改人：朱振
 *          </p>
 *          <p>
 *          修改时间：2015年10月28日 上午9:32:09
 *          </p>
 *          <p>
 *          修改备注：
 *          </p>
 */
@Service
public class MobileServiceImpl implements IMobileService
{
    private static final Logger log = Logger.getLogger(IMobileService.class);

    /**
     * 
     * (non-Javadoc)
     * 
     * @see com.ssic.game.common.service.IMobileService#sendVerificationCode(com.ssic.game.common.dto.MobileMessageSenderDto)
     */
    @Override
    public boolean sendVerificationCode(MobileMessageSenderDto sender)
    {
        log.info("参数列表：" + sender);
        // sender
        if (sender == null)
        {
            log.error("参数：MobilePhoneMessageSenderDto不能为空");
            return false;
        }

        // url
        if (StringUtils.isEmpty(sender.getUrl()))
        {
            log.error("参数MobilePhoneMessageSenderDto:sender：url不能为空");
            return false;
        }

        // account
        if (StringUtils.isEmpty(sender.getAccount()))
        {
            log.error("参数MobilePhoneMessageSenderDto:sender：account不能为空");
            return false;
        }

        // password
        if (StringUtils.isEmpty(sender.getPassword()))
        {
            log.error("参数MobilePhoneMessageSenderDto:sender：password不能为空");
            return false;
        }

        // product
        if (StringUtils.isEmpty(sender.getProduct()))
        {
            log.error("参数MobilePhoneMessageSenderDto:sender：product不能为空");
            return false;
        }

        // 扩展码extno
        if (StringUtils.isEmpty(sender.getExtno()))
        {
            log.error("参数MobilePhoneMessageSenderDto:sender：extno不能为空");
            return false;
        }

        // needstatus

        // 手机号 mobile
        Pattern pattern = Pattern.compile("(86)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}");
        if (StringUtils.isEmpty(sender.getMobile()))
        {
            log.error("参数：mobilePhone不能为空");
            return false;
        }
        else if (!pattern.matcher(sender.getMobile()).matches())
        {
            log.error("手机号码不合法" + sender.getMobile());
            return false;
        }

        // content
        if (StringUtils.isEmpty(sender.getContent()))
        {
            log.error("参数MobilePhoneMessageSenderDto:sender：content不能为空");
            return false;
        }

        String response = null;
        try
        {
            // 第三方的工具
            response =
                HttpSender.batchSend(sender.getUrl(),
                    sender.getAccount(),
                    sender.getPassword(),
                    sender.getMobile(),
                    sender.getContent(),
                    sender.isNeedstatus(),
                    sender.getProduct(),
                    sender.getExtno());
        }
        catch (Exception e)
        {
            // 发送失败
            log.error(e.getMessage(), e);

            return false;
        }

        if (StringUtils.isEmpty(response))
        {
            log.error("发送失败");

            return false;
        }
        else
        // 发送成功时，会返回以，0为结尾的字符串
        {
            String[] responseArray = response.split("\n");
            if (!ArrayUtils.isEmpty(responseArray) && !StringUtils.isEmpty(responseArray[0])
                && responseArray[0].endsWith(",0"))
            {
                return true;
            }
        }

        return false;
    }

    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.game.common.service.IMobileService#getSender(java.util.Map, java.lang.String, java.lang.String, com.ssic.game.common.constant.MobileMessageType)
     */
    @Override
    public MobileMessageSenderDto getSender(Map<String, String> params, String code, String mobilePhone, MobileMessageType type)
    {
        
        Response<String> response = new Response<>();
        response.setStatus(HttpDataResponse.HTTP_SUCCESS);
        if(params == null || params.isEmpty())
        {
            log.error("手机短信的相关参数的为空");
            return null;
        }
        
        
        //url
        if(StringUtils.isEmpty(params.get("msg_url")))
        {
            log.error("参数params：url不能为空");
            return null;
        }
        
        //account
        if(StringUtils.isEmpty(params.get("msg_account")))
        {
            log.error("参数params：account不能为空");
            return null;
        }
        
        //password
        if(StringUtils.isEmpty(params.get("msg_password")))
        {
            log.error("参数params：password不能为空");
            return null;
        }
        
        //product
        if(StringUtils.isEmpty(params.get("msg_product")))
        {
            log.error("参数params：product不能为空");
            return null;
        }
        
        //扩展码extno
        if(StringUtils.isEmpty(params.get("msg_extno")))
        {
            log.error("参数params：extno不能为空");
            return null;
        }
        
        //needstatus
        
        //code
        if(StringUtils.isEmpty(code))
        {
            log.error("参数params：验证码不能为空");
            return null;
        }
        
        //mobilePhone
        if(StringUtils.isEmpty(mobilePhone))
        {
            log.error("参数params：mobilePhone不能为空");
            return null;
        }
        
        //手机绑定
        if(StringUtils.isEmpty(params.get("msg_binding")))
        {
            log.error("参数params：msg_binding不能为空");
            return null;
        }
        else if(!params.get("msg_binding").contains("${code}"))
        {
            log.error("参数params：msg_binding没有${code}占位符");
            return null;
        }
        
        MobileMessageSenderDto sender = new MobileMessageSenderDto();

        // 初始化sender
        sender.setNeedstatus(false);// 控制显示状态
        sender.setUrl(params.get("msg_url"));
        sender.setAccount(params.get("msg_account"));
        sender.setMobile(mobilePhone);
        sender.setPassword(params.get("msg_password"));
        sender.setProduct(params.get("msg_product"));
        sender.setExtno(params.get("msg_extno"));
        
        if(MobileMessageType.BINDING.equals(type))
        {
            sender.setContent(params.get("msg_binding").replace("${code}",code));
        }
        else if(MobileMessageType.UNBINDING.equals(type))
        {
            sender.setContent(params.get("msg_unbinding").replace("${code}",code));
        }           
        else
        {
            log.error("不支持该类型");
            return null;
        }

        return sender;
    }
    
    
    /**
     * 验证手机短信在收据库中的配置    
     * @author 朱振   
     * @date 2015年10月29日 下午4:32:42  
     * @version 1.0
     * @param params
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年10月29日 下午4:32:42</p>
     * <p>修改备注：</p>
     */
    public Response<String> validateParamConfig(Map<String, String> params)
    {
        Response<String> response = new Response<>();
        response.setStatus(HttpDataResponse.HTTP_SUCCESS);
        if(params == null || params.isEmpty())
        {
            log.error("手机短信的相关参数的为空");
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("手机短信参数配置出错");
            return response;
        }
        
        
        //url
        if(StringUtils.isEmpty(params.get("msg_url")))
        {
            log.error("参数params：url不能为空");
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("手机短信参数配置出错");
            return response;
        }
        
        //account
        if(StringUtils.isEmpty(params.get("msg_account")))
        {
            log.error("参数params：account不能为空");
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("手机短信参数配置出错");
            return response;
        }
        
        //password
        if(StringUtils.isEmpty(params.get("msg_password")))
        {
            log.error("参数params：password不能为空");
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("手机短信参数配置出错");
            return response;
        }
        
        //product
        if(StringUtils.isEmpty(params.get("msg_product")))
        {
            log.error("参数params：product不能为空");
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("手机短信参数配置出错");
            return response;
        }
        
        //扩展码extno
        if(StringUtils.isEmpty(params.get("msg_extno")))
        {
            log.error("参数params：extno不能为空");
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("手机短信参数配置出错");
            return response;
        }
        
        //needstatus
        
        return response;
    }
}
