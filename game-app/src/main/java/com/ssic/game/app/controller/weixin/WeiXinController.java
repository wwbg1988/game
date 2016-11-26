package com.ssic.game.app.controller.weixin;

import java.util.Date;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.base.dto.CafetoriumDto;
import com.ssic.catering.base.service.ICafetoriumService;
import com.ssic.game.common.dto.ParamConfigDto;
import com.ssic.game.common.dto.WeixinSignatureDto;
import com.ssic.game.common.service.IParamConfigService;
import com.ssic.game.common.service.IWeinXinService;
import com.ssic.game.common.util.HttpDataResponse;
import com.ssic.util.StringUtils;
import com.ssic.util.UUIDGenerator;
import com.ssic.util.model.Response;

/**		
 * <p>Title: WeiXinController </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振	
 * @date 2015年10月14日 下午2:02:33	
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年10月14日 下午2:02:33</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping("/weixin")
public class WeiXinController
{
    /**
     * t_ctr_param_config数据中的paramName
     * 微信功能是否开启 0 表示 关闭，  1表示 开启
     */
    private static final String TURNON = "weixin.valid";
    
    /**
     * 微信公众号appid  t_ctr_param_config数据中的paramName
     */
    private static final String APPID = "weixin.appId";
    
    /**
     * 微信公众号appSecret t_ctr_param_config数据中的paramName
     */
    private static final String APPSECRET = "weixin.appSecret";
    
    @Autowired
    private IWeinXinService weixinService;
    
    @Autowired
    private ICafetoriumService cafetoriumService;
    
    @Autowired
    private IParamConfigService paramConfigService;
    
    /**
     * 使用缓存	 
     * @author 朱振	
     * @date 2015年10月14日 下午3:19:41	
     * @version 1.0
     * @param url
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年10月14日 下午3:19:41</p>
     * <p>修改备注：</p>
     */
    @RequestMapping("/getWeiXinJSAuthority.do")
    @ResponseBody
    public Response<WeixinSignatureDto> getWeiXinJSAuthority(String url,String cafetoriumId)
    {
        Response<WeixinSignatureDto> response = new Response<>();
        
        //url
        if(StringUtils.isEmpty(url))
        {
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("url不能为空");
            return response;      
        }
        
        //cafetoriumId
        if(StringUtils.isEmpty(cafetoriumId))
        {
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("食堂ID不能为空");
            return response;      
        }

        CafetoriumDto cafetorium = cafetoriumService.findById(cafetoriumId);
        if(cafetorium == null)
        {
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("食堂不存在，不能获取项目信息和微信授权信息");
            return response;      
        }
        
        if(StringUtils.isEmpty(cafetorium.getProjId()))
        {
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("项目信息为空，不能获取微信授权信息");
            return response;      
        }
        
        //从数据库中获取微信的配置信息
        ParamConfigDto paramConfigDto = new ParamConfigDto();
        paramConfigDto.setProjId(cafetorium.getProjId());
        paramConfigDto.setParamName("weixin");//查询微信开头的
        Map<String, String> parameters = paramConfigService.getParametersBy(paramConfigDto);
        if(MapUtils.isEmpty(parameters))
        {
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("没有微信授权信息");
            return response;   
        }
        
        
        if(!"1".equals(parameters.get(TURNON)))
        {
            response.setStatus(405);
            response.setMessage("微信功能未开启");
            return response;      
        }
        
        //appId
        String appId = parameters.get(APPID);
        if(StringUtils.isEmpty(appId))
        {
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("配置出错，微信公众号appId不能为空");
            return response;           
        }
        
        //appSecret
        String appSecret = parameters.get(APPSECRET);
        if(StringUtils.isEmpty(appSecret))
        {
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("配置出错，微信公众号appSecret不能为空");
            return response;           
        }
        
        //随机字符串
        String noncestr = UUIDGenerator.getUUID32Bit();
        
        //时间戳
        Long timestamp= new Date().getTime();
        
        //所有的签名字段
        WeixinSignatureDto jsapi_signature = weixinService.getJSAPISignature(appId, appSecret, noncestr, timestamp, url);
        if(jsapi_signature != null)
        { 
            response.setStatus(HttpDataResponse.HTTP_SUCCESS);
            response.setData(jsapi_signature);
            response.setMessage("微信js授权成功");
            
            
            return response;
        }
        
        response.setStatus(HttpDataResponse.HTTP_FAILE);
        response.setMessage("微信js授权失败");
        
        return response;
    }
}

