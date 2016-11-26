package com.ssic.game.app.controller.weixin;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ssic.catering.base.dto.AddressDto;
import com.ssic.catering.base.dto.CafetoriumDto;
import com.ssic.catering.base.service.IAddressService;
import com.ssic.catering.base.service.ICafetoriumService;
import com.ssic.game.common.util.HttpDataResponse;
import com.ssic.shop.manage.dto.WeixnUserCafetoriumDto;
import com.ssic.shop.manage.service.IWeixnUserCafetoriumService;
import com.ssic.util.HttpClientUtil;
import com.ssic.util.constants.DataStatus;
import com.ssic.util.model.Response;

/**
 * 微信服务端收发消息接口
 * 
 * @author 张亚伟
 *
 */
@RequestMapping("/weixin")
@Controller
public class WechatController
{

    @Autowired
    private ICafetoriumService iCafetoriumService;
    @Autowired
    private IWeixnUserCafetoriumService weixnUserCafetoriumService;

    @Autowired
    private IAddressService addressService;

    @Autowired
    private ICafetoriumService cafeToriumService;

    /**
     * 
     * getUrl：根据参数类型返回对应的网站,并获取用户的OpenId
     * @param json (格式:{type:选择类型,cafetoriumId:食堂ID})
     * @param request
     * @return
     * @exception   
     * @author 张亚伟
     * @date 2015年8月24日 上午10:15:58
     */
    @RequestMapping("/getUrl")
    public String getUrl(String json, HttpServletRequest request)
    {

        System.out.println(json);

        WeixinCode config = JSON.parseObject(json, WeixinCode.class);

        String cafetoriumId = "";

        String type = config.getType();

        //通过code获取用户的openid
        String code = request.getParameter("code");

        //获取用户的openId
        String appJson =
            HttpClientUtil.sendGetRequest("https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx42df7b4462210ba0&secret=54d3f9d1da015c201192b777785b3a2d&code="
                + code + "&grant_type=authorization_code",
                null);
        WeixinCode weixinCode = JSON.parseObject(appJson, WeixinCode.class);
        String openId = weixinCode.getOpenid();
        System.out.println(cafetoriumId);
        System.out.println(openId);
        WeixnUserCafetoriumDto param = new WeixnUserCafetoriumDto();
        param.setOpenId(openId);
        //查找用户关注过的食堂列表;
        List<WeixnUserCafetoriumDto> list = weixnUserCafetoriumService.findAllBy(param);
        if (!CollectionUtils.isEmpty(list))
        {
            for (WeixnUserCafetoriumDto dto : list)
            {
                if (dto.getIsDefault() == 1)
                {
                    cafetoriumId = dto.getCafetoriumId();
                }
            }
        }
        if (type.equals("1"))
        {
            return "redirect:/weixin/menu.html?cafetoriumId=" + cafetoriumId + "&openId=" + openId;
        }
        else if (type.equals("2"))
        {
            return "redirect:/weixin/view.html?cafetoriumId=" + cafetoriumId + "&openId=" + openId;
        }
        else if (type.equals("3"))
        {
            return "redirect:/weixin/whatoeat.html?cafetoriumId=" + cafetoriumId + "&openId=" + openId;
        }
        return null;
    }

    @RequestMapping("/in")
    public void in(HttpServletRequest request, HttpServletResponse response, String cafetoriumId)
    {
        try
        {

            // 设置输入和输出编码
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");

            String result = "";
            /** 判断是否是微信接入激活验证，只有首次接入验证时才会收到echostr参数，此时需要把它直接返回 */
            String echostr = request.getParameter("echostr");
            if (echostr != null && echostr.length() > 1)
            {
                result = echostr;
            }
            else
            {

                /** 读取接收到的xml消息 */
                StringBuffer sb = new StringBuffer();
                InputStream is = request.getInputStream();
                InputStreamReader isr = new InputStreamReader(is, "UTF-8");
                BufferedReader br = new BufferedReader(isr);
                String s = "";
                while ((s = br.readLine()) != null)
                {
                    sb.append(s);
                }
                String xml = sb.toString(); // 次即为接收到微信端发送过来的xml数据
                System.out.println("收到的信息" + xml);

                /** 解析xml数据 */
                ReceiveXmlEntity xmlEntity = new ReceiveXmlProcess().getMsgEntity(xml);

                String eventKey = xmlEntity.getEventKey();
                System.out.println("FromUserName---" + xmlEntity.getFromUserName());
                System.out.println("ToUserName---" + xmlEntity.getToUserName());
                System.out.println("eventKey---" + eventKey);

                if (eventKey != null && eventKey.length() > 0)
                {
                    eventKey = eventKey.replace("qrscene_", "");
                    followCafetorium(xmlEntity.getFromUserName(), eventKey);
                }
            }
            if (result != null && result.length() > 0)
            {
                // 输出配置信息
                OutputStream os = response.getOutputStream();
                os.write(result.getBytes("UTF-8"));
                os.flush();
                os.close();
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    /**
    * 
    * followCafetorium：用户关注食堂微信公账号后，保存关系记录;
    * @param openId 微信用户唯一id
    * @param cafeCode 食堂编码
    * @return
    * @exception   
    * @author 刘博
    * @date 2015年10月27日 下午15:52:58
    */
    @RequestMapping("/followCafetorium.do")
    public Response<String> followCafetorium(String openId, String cafeCode)
    {
        Response<String> response = new Response<String>();
        if (StringUtils.isEmpty(openId) || StringUtils.isEmpty(cafeCode))
        {
            response.setStatus(DataStatus.HTTP_FAILE);
            response.setMessage("用户微信唯一id或食堂编码为空");
            response.setData(null);
        }

        CafetoriumDto cafetoriumDto = new CafetoriumDto();
        cafetoriumDto.setCafeCode(cafeCode);
        List<CafetoriumDto> cafeList = iCafetoriumService.findALL(cafetoriumDto, null);
        if (!CollectionUtils.isEmpty(cafeList))
        {

            if (cafeList.size() > 1)
            {
                response.setStatus(DataStatus.HTTP_FAILE);
                response.setMessage("食堂编码重复，请联系管理员");
                response.setData(null);
            }
            CafetoriumDto cafe = cafeList.get(0);
            WeixnUserCafetoriumDto param = new WeixnUserCafetoriumDto();
            param.setOpenId(openId);
            //查找用户关注过的食堂列表;
            List<WeixnUserCafetoriumDto> list = weixnUserCafetoriumService.findAllBy(param);
            boolean isSave = true;
            if (!CollectionUtils.isEmpty(list))
            {
                for (WeixnUserCafetoriumDto dto : list)
                {
                    if (dto.getCafetoriumId().equals(cafe.getId()))
                    {
                        //更新数据库的isDefault=0;
                        updateIsDefault(openId, dto.getId());
                        dto.setIsDefault(DataStatus.ENABLED);
                        //如果用户关注的食堂已经保存，则更新时间;
                        dto.setLastUpdateTime(new Date());
                        weixnUserCafetoriumService.update(dto);
                        isSave = false;
                    }
                }
            }
            if (isSave)
            {
                //创建用户关注食堂DTO对象;
                WeixnUserCafetoriumDto dto = new WeixnUserCafetoriumDto();
                dto.setId(UUID.randomUUID().toString());
                dto.setOpenId(openId);
                dto.setCafetoriumId(cafe.getId());
                dto.setCreateTime(new Date());
                dto.setLastUpdateTime(new Date());
                dto.setIsDefault(DataStatus.ENABLED);
                dto.setStat(DataStatus.HTTP_SUCCESS);
                //保存微信用户关系食堂信息
                weixnUserCafetoriumService.insert(dto);
                //更新数据库的isDefault=0;
                updateIsDefault(openId, dto.getId());
            }
        }
        else
        {
            response.setStatus(DataStatus.HTTP_FAILE);
            response.setMessage("食堂不存在!");
            response.setData(null);
        }
        response.setStatus(DataStatus.HTTP_SUCCESS);
        response.setMessage("保存关系食堂成功");
        response.setData(null);
        return response;
    }

    /**
     * 
     * updateIsDefault：用户关注食堂记录的isDefault字段为0(不是默认食堂)
     * @param openId 微信用户唯一id
     * @param followId 用户关注食堂记录主键id
     * @return
     * @exception   
     * @author 刘博
     * @date 2015年10月30日 上午11:16:38
     */
    private void updateIsDefault(String openId, String followId)
    {//更新除了followId之外的其他记录的isDefault字段为0
        WeixnUserCafetoriumDto param = new WeixnUserCafetoriumDto();
        param.setOpenId(openId);
        //查找用户关注过的食堂列表;
        List<WeixnUserCafetoriumDto> list = weixnUserCafetoriumService.findAllBy(param);
        if (!CollectionUtils.isEmpty(list))
        {
            for (WeixnUserCafetoriumDto dto : list)
            {
                if (!dto.getId().equals(followId))
                {
                    //更新
                    dto.setIsDefault(DataStatus.DISABLED);
                    weixnUserCafetoriumService.update(dto);
                }
            }
        }
    }

    /**
     * 	 
     * @author 朱振	
     * @date 2015年11月2日 上午11:22:59	
     * @version 1.0
     * @param openId
     * @param cafetoriumId
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月2日 上午11:22:59</p>
     * <p>修改备注：</p>
     */
    @ResponseBody
    @RequestMapping("/setDefaultCafetorium.do")
    public Response<CafetoriumDto> setDefaultCafetorium(String openId, String cafetoriumId)
    {
        Response<CafetoriumDto> response = new Response<>();
        if (openId == null)
        {
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("非微信用户不能使用");
            return response;
        }

        if (cafetoriumId == null)
        {
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("用户的所属食堂不能为空");
            return response;
        }

        //更新数据库
        Integer count = weixnUserCafetoriumService.setDefaultCafetorium(openId, cafetoriumId);
        if (count > 0)
        {
            CafetoriumDto data = iCafetoriumService.findById(cafetoriumId);
            if(data == null)
            {
                response.setStatus(HttpDataResponse.HTTP_FAILE);
                response.setMessage("没有用该餐厅信息");
                return response;
            }
            
            response.setStatus(HttpDataResponse.HTTP_SUCCESS);
            response.setMessage("默认餐厅设置成功");
            response.setData(data);
            return response;
        }

        response.setStatus(HttpDataResponse.HTTP_FAILE);
        response.setMessage("默认餐厅设置失败");
        return response;
    }

    /**
     * @author 朱振	
     * @date 2015年10月31日 下午3:58:11	
     * @version 1.0
     * @param openId
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年10月31日 下午3:58:11</p>
     * <p>修改备注：</p>
     */
    @ResponseBody
    @RequestMapping("/getfollowCafetoriumList.do")
    public Response<List<CafetoriumDto>> getfollowCafetoriumList(String openId)
    {
        Response<List<CafetoriumDto>> response = new Response<>();
        if (openId == null)
        {
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("非微信用户不能查看");
            return response;
        }

        List<CafetoriumDto> data=
            iCafetoriumService.getFollowedCafetoriumsByWeiXinId(openId);
        if (CollectionUtils.isEmpty(data))
        {
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("没有关注过任何食堂");
            return response;
        }

        response.setStatus(HttpDataResponse.HTTP_SUCCESS);
        response.setMessage("查询成功");
        response.setData(data);
        return response;
    }

    /**
     * 根据城市名字获取食堂列表<BR>	 
     * @author 朱振	
     * @date 2015年10月31日 下午12:54:47	
     * @version 1.0
     * @param cityName
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年10月31日 下午12:54:47</p>
     * <p>修改备注：</p>
     */
    @ResponseBody
    @RequestMapping("/getCafetoriumListByCityName.do")
    public Response<List<CafetoriumDto>> getCafetoriumListByCityName(String cityName)
    {
        Response<List<CafetoriumDto>> response = new Response<>();

        if (cityName == null)
        {
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("用户所在的城市不能为空");
            return response;
        }
        
        AddressDto address = new AddressDto();
        address.setAddressName(cityName);
        List<AddressDto> addresses = addressService.findAddressesBy(address);

        if (CollectionUtils.isEmpty(addresses))
        {
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("用户所在的城市暂无餐厅信息，数据");
            return response;
        }

        //根据城市和项目
        CafetoriumDto param = new CafetoriumDto();
        param.setAddressId(addresses.get(0).getId());
        List<CafetoriumDto> resultset = cafeToriumService.findCafetoriumsBy(param);

        if (CollectionUtils.isEmpty(resultset))
        {
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("该城市没有合作食堂");
            return response;
        }
        

        response.setStatus(HttpDataResponse.HTTP_SUCCESS);
        response.setMessage("查询成功");
        response.setData(resultset);
        return response;
    }
    
    

    /**
     * 查看用户的默认餐厅     
     * @author 朱振   
     * @date 2015年11月3日 上午10:51:22  
     * @version 1.0
     * @param openId
     * @param cafetoriumId
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月3日 上午10:51:22</p>
     * <p>修改备注：</p>
     */
    @ResponseBody
    @RequestMapping("/getDefaultCafetorium.do")
    public Response<CafetoriumDto> getDefaultCafetorium(String openId)
    {
        Response<CafetoriumDto> response = new Response<>();
        if(StringUtils.isEmpty(openId))
        {
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("非微信用户不能查看");
            return response;
        }
        
        
        List<CafetoriumDto> datas = iCafetoriumService.getFollowedCafetoriumsByWeiXinId(openId);
        if(CollectionUtils.isEmpty(datas))
        {
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("没有关注过任何食堂");
            return response;
        }
        
        for(CafetoriumDto data:datas)
        {
            //==1
            if(data != null && data.getIsDefault() != null && data.getIsDefault() == 1)
            {
                response.setStatus(HttpDataResponse.HTTP_SUCCESS);
                response.setMessage("查询成功");
                response.setData(data);
                return response;
            }
        }
        
        response.setStatus(HttpDataResponse.HTTP_SUCCESS);
        response.setMessage("没有默认食堂");
//        response.setData(datas.get(0));
        return response;
    }
    
    
    /**
     * 	 
     * @author 朱振	
     * @date 2015年11月12日 下午2:38:49	
     * @version 1.0
     * @param openId
     * @param cafeCode
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月12日 下午2:38:49</p>
     * <p>修改备注：</p>
     */
    @RequestMapping("/followCafetorium1.do")
    @ResponseBody
    public Response<CafetoriumDto> followCafetorium1(String openId, String cafeCode)
    {
        Response<CafetoriumDto> response = new Response<>();

        Response<String> response1 = this.followCafetorium(openId, cafeCode);
        if(200!=response1.status)
        {
            response.setStatus(response1.getStatus());
            response.setMessage(response1.getMessage());
            return response;
        }
        
        
        
        
        List<CafetoriumDto> datas = iCafetoriumService.getFollowedCafetoriumsByWeiXinId(openId);
        if(CollectionUtils.isEmpty(datas))
        {
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("没有关注过任何食堂");
            return response;
        }
        
        for(CafetoriumDto data:datas)
        {
            //==1
            if(data != null && data.getIsDefault() != null && data.getIsDefault() == 1)
            {
                response.setStatus(HttpDataResponse.HTTP_SUCCESS);
                response.setMessage("查询成功");
                response.setData(data);
                return response;
            }
        }
        
        response.setStatus(HttpDataResponse.HTTP_SUCCESS);
        response.setMessage("查询失败");
//        response.setData(datas.get(0));
        return response;
        
    }
}
