package com.ssic.game.app.controller.shop;

import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.game.common.util.HttpDataResponse;
import com.ssic.shop.manage.dto.CarteUserDto;
import com.ssic.shop.manage.service.ICarteUserService;
import com.ssic.util.StringUtils;
import com.ssic.util.model.Response;

/**		
 * <p>Title: CarteShopUserController </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振	
 * @date 2015年9月15日 下午5:00:33	
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年9月15日 下午5:00:33</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping("/http/api/carte/shopUser")
public class CarteShopUserController
{
    private static Logger log = Logger.getLogger(CarteShopUserController.class);

    @Autowired
    private ICarteUserService carteUserService;
    
    /**
     * 生日录入
     * @author 朱振   
     * @date 2015年8月27日 下午2:54:43   
     * @version 1.0
     * @param carteUserDto
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年8月27日 下午2:54:43</p>
     * <p>修改备注：</p>
     */
    @ResponseBody
    @RequestMapping("/recordBirthday.do")
    public Response<String> recordBirthday(CarteUserDto carteUserDto)
    {
        Response<String> result = new Response<String>();
        
        //id
        if(StringUtils.isEmpty(carteUserDto.getId()))
        {
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("uuid未获取，请刷新重试");
            return result;
        }
        else if(carteUserDto.getId().length() > 36)
        {
            log.debug("uuid的长度不能大于36, 数据库");
            
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("uuid不合法");
            return result;
        }
        
        //name
        if(StringUtils.isEmpty(carteUserDto.getName()))
        {
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("姓名不能为空");
            return result;
        }
        else if(carteUserDto.getName().length() > 30)
        {
            log.debug("姓名的长度不能大于30， 数据库");
            
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("姓名的长度不能大于30");
            return result;
        }
        
      
        //phone
        Pattern pattern = Pattern.compile("(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}");
        if(StringUtils.isEmpty(carteUserDto.getPhone()))
        {
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("手机号码不能为空");
            return result;
        }
        else if(!pattern.matcher(carteUserDto.getPhone()).matches())
        {
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("手机号码不合法");
            return result;
        }
        
        //生日
        if(StringUtils.isEmpty(carteUserDto.getBirthday()))
        {
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("生日不能为空");
            return result;
        }
        
        
        //微信
        String wxUniqueId = carteUserDto.getOpenId();
        if(StringUtils.isEmpty(wxUniqueId))
        {
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("微信标识符不能为空");
            return result;
        }        
        
        //食堂id
        String cafetoriumId = carteUserDto.getCafetoriumId();
        if(StringUtils.isEmpty(cafetoriumId))
        {
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("食堂id不能为空");
            return result;
        }        
        
        
        CarteUserDto carteUser = carteUserService.findByWxUniqueIdAndCafetoriumId(wxUniqueId, cafetoriumId);
        if(carteUser != null)
        {
            //判断生日是否已录入
            if(StringUtils.isEmpty(carteUser.getBirthday()))
            {
                //使用id更新记录
                carteUserDto.setId(carteUser.getId());
                int n = carteUserService.updateCarteUser(carteUserDto);
                log.debug("更新了"+n+"条记录");
                if(n > 0)
                {
                    result.setStatus(HttpDataResponse.HTTP_SUCCESS);
                    result.setMessage("录入成功");
                    return result;
                }
            }
            
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("生日已录入");
            return result;
            
        }
        
        int count = carteUserService.insertCarteUser(carteUserDto);
        if(count > 0)
        {
            result.setStatus(HttpDataResponse.HTTP_SUCCESS);
            result.setMessage("录入成功");
            result.setData("录入了"+count+"条数据");
            return result;
        }
        
        log.warn("数据库插入出错");
        
        result.setStatus(HttpDataResponse.HTTP_FAILE);
        result.setMessage("录入失败");
        return result;
    }
    
    
    /**
     * 通过微信id查询carte_user	 
     * @author 朱振	
     * @date 2015年10月12日 上午9:52:59	
     * @version 1.0
     * @param openId
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年10月12日 上午9:52:59</p>
     * <p>修改备注：</p>
     */
    @ResponseBody
    @RequestMapping("/getCarteUserByWxUniqueIdAndCafetoriumId.do")
    public Response<CarteUserDto> getCarteUserByWxUniqueIdAndCafetoriumId(String openId, String cafetoriumId)
    {
        Response<CarteUserDto> result = new Response<>();
        
        if(StringUtils.isEmpty(openId))
        {
            result.setStatus(HttpDataResponse.HTTP_FAILE); 
            result.setMessage("微信未登录");
            return result;
        }
        
        if(StringUtils.isEmpty(cafetoriumId))
        {
            result.setStatus(HttpDataResponse.HTTP_FAILE); 
            result.setMessage("食堂id不能为空");
            return result;
        }

        CarteUserDto carteUser = carteUserService.findByWxUniqueIdAndCafetoriumId(openId, cafetoriumId);
        
        if(carteUser != null)
        {
            result.setStatus(HttpDataResponse.HTTP_SUCCESS); 
            result.setData(carteUser);
            result.setMessage("查询成功");
            return result;
        }
        
        log.debug("数据不存在，微信id="+openId);

        result.setStatus(HttpDataResponse.HTTP_FAILE); 
        result.setMessage("用户不存在");
        return result;
    }
}

