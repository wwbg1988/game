package com.ssic.game.app.controller.shop;

import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.base.datasource.DataSourceHolderUtil;
import com.ssic.game.common.util.HttpDataResponse;
import com.ssic.shop.manage.dto.IntegralExchangeDto;
import com.ssic.shop.manage.dto.IntegralExchangeTypeDto;
import com.ssic.shop.manage.dto.UserIntegralExchangeDto;
import com.ssic.shop.manage.service.IIntegralExchangeService;
import com.ssic.shop.manage.service.IIntegralExchangeTypeService;
import com.ssic.shop.manage.service.IUserIntegralExchangeService;
import com.ssic.util.StringUtils;
import com.ssic.util.model.Response;

/**		
 * <p>Title: IntegralExchangeController </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振	
 * @date 2015年9月18日 下午5:14:35	
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年9月18日 下午5:14:35</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping("/http/api/carte/integral")
public class IntegralExchangeController
{
    private static Logger log = Logger.getLogger(IntegralExchangeController.class);
    
    @Autowired
    private IIntegralExchangeService integralExchangeService;
    
    @Autowired
    private IIntegralExchangeTypeService intergaralExchangeTypeService;
    
    @Autowired
    private IUserIntegralExchangeService iUserIntegralExchangeService;
    
    /**
     * 查询所有的兑换物品信息
     * @author 朱振	
     * @date 2015年9月18日 下午5:32:28	
     * @version 1.0
     * @param ph
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年9月18日 下午5:32:28</p>
     * <p>修改备注：</p>
     */
    @ResponseBody
    @RequestMapping("/getExchangesByCafetoriumId.do")
    public Response<List<IntegralExchangeDto>> getExchangesByCafetoriumId(String cafetoriumId)
    {
        log.debug("parameter:cafetoriumId="+cafetoriumId);
        
        Response<List<IntegralExchangeDto>> result = new Response<>();
        
        if(StringUtils.isEmpty(cafetoriumId))
        {
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("食堂id不能为空");
            return result;
        }
        else if(cafetoriumId.length() > 36)
        {
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("食堂id的长度不能大于36");
            return result;
        }
        
        List<IntegralExchangeDto> data = integralExchangeService.getIntegralExchangeByCafetoriumId(cafetoriumId);
        
        log.debug("查询了"+(data==null?0:data.size())+"条数据");
        
        if(!CollectionUtils.isEmpty(data))
        {
            result.setStatus(HttpDataResponse.HTTP_SUCCESS);
            result.setData(data);
            result.setMessage("查询成功");
            return result;
        }
        
        result.setStatus(HttpDataResponse.HTTP_FAILE);
        result.setMessage("没有兑换物品信息");
        return result;
       
    }
    
    /**
     * 通过id查询兑换物品的类别信息
     * @author 朱振	
     * @date 2015年9月22日 上午11:38:23	
     * @version 1.0
     * @param id
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年9月22日 上午11:38:23</p>
     * <p>修改备注：</p>
     */
    @ResponseBody
    @RequestMapping("/findExchangeTypeById.do")
    public Response<IntegralExchangeTypeDto> findExchangeTypeById(String id)
    {
        Response<IntegralExchangeTypeDto> result = new Response<>();
        
        if(StringUtils.isEmpty(id))
        {
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("积分兑换类型不能为空");
            return result;
        }
        
        IntegralExchangeTypeDto data = intergaralExchangeTypeService.findIntegralExchangeById(id);
        
        log.debug("查询了"+(data==null?0:1)+"条数据:id="+id);
        
        if(data != null)
        {
            result.setStatus(HttpDataResponse.HTTP_SUCCESS);
            result.setMessage("查询成功");
            result.setData(data);
            return result;  
        }
       
        
        result.setStatus(HttpDataResponse.HTTP_FAILE);
        result.setMessage("兑换物品的类别信息不存在");
        return result;                    
    }
    
    /**
     * 手机充值	 
     * @author 朱振	
     * @date 2015年10月9日 上午11:02:37	
     * @version 1.0
     * @param userIntegralExchangeDto
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年10月9日 上午11:02:37</p>
     * <p>修改备注：</p>
     */
    @ResponseBody
    @RequestMapping("/chargeExchange.do")
    public Response<String> chargeExchange(UserIntegralExchangeDto userIntegralExchangeDto)
    {
        Response<String> result = new Response<>();
        
        //id
        if(StringUtils.isEmpty(userIntegralExchangeDto.getId()))
        {
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("uuid未获取，请刷新重试");
            return result;
        }
        else if(userIntegralExchangeDto.getId().length() > 36)
        {
            log.debug("uuid的长度不能大于36");
            
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("uuid不合法");
            return result;
        }
        
        //要兑换的物品id
        if(StringUtils.isEmpty(userIntegralExchangeDto.getIntegralExchangeId()))
        {
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("未选中要兑换的物品");
            return result;
        }
        else if(userIntegralExchangeDto.getIntegralExchangeId().length() > 36)
        {
            log.debug("要兑换的物品id的长度不能大于36， 数据库");
            
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("选中物品出错");
            return result;
        }
        
        //商城用户id
        if(StringUtils.isEmpty(userIntegralExchangeDto.getCarteUserId()))
        {
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("非法商城用户");
            return result;
        }
        else if(userIntegralExchangeDto.getIntegralExchangeId().length() > 36)
        {
            log.debug("商户id的长度不能大于36， 数据库");

            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("非法商城用户");
            return result;
        }
        
        //手机号码    
        Pattern pattern = Pattern.compile("(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}");
        if(StringUtils.isEmpty(userIntegralExchangeDto.getPhoneNumber()))
        {
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("手机号码不能为空");
            return result;
        }
        else if(!pattern.matcher(userIntegralExchangeDto.getPhoneNumber()).matches())
        {
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("手机号码不合法");
            return result;
        }
        
        
        //使用@Transactional标签时必须绑定数据源
        DataSourceHolderUtil.setToMaster();
        int data = iUserIntegralExchangeService.addUserIntegralExchange(userIntegralExchangeDto);
        if(data == 1)
        {
            result.setStatus(HttpDataResponse.HTTP_SUCCESS);
            result.setMessage("充值成功");
            return result;
        }

        result.setStatus(HttpDataResponse.HTTP_FAILE);
        result.setMessage("充值失败");
        return result;
    }
    
    
    @ResponseBody
    @RequestMapping("/entityExchange.do")
    public Response<String> addUserIntegralExchange(UserIntegralExchangeDto userIntegralExchangeDto)
    {
        Response<String> result = new Response<>();
        
        //id
        if(StringUtils.isEmpty(userIntegralExchangeDto.getId()))
        {
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("uuid未获取，请刷新重试");
            return result;
        }
        else if(userIntegralExchangeDto.getId().length() > 36)
        {
            log.debug("uuid的长度不能大于36");
            
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("uuid不合法");
            return result;
        }
        
        //要兑换的物品id
        if(StringUtils.isEmpty(userIntegralExchangeDto.getIntegralExchangeId()))
        {
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("未选中要兑换的物品");
            return result;
        }
        else if(userIntegralExchangeDto.getIntegralExchangeId().length() > 36)
        {
            log.debug("要兑换的物品id的长度不能大于36， 数据库");
            
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("选中物品出错");
            return result;
        }
        
        //商城用户id
        if(StringUtils.isEmpty(userIntegralExchangeDto.getCarteUserId()))
        {
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("非法商城用户");
            return result;
        }
        else if(userIntegralExchangeDto.getIntegralExchangeId().length() > 36)
        {
            log.debug("商户id的长度不能大于36， 数据库");

            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("非法商城用户");
            return result;
        }
        
        
        //手机号码    
        Pattern pattern = Pattern.compile("(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}");
        if(StringUtils.isEmpty(userIntegralExchangeDto.getPhoneNumber()))
        {
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("手机号码不能为空");
            return result;
        }
        else if(!pattern.matcher(userIntegralExchangeDto.getPhoneNumber()).matches())
        {
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("手机号码不合法");
            return result;
        }
        
        //地址栏验证
        if(StringUtils.isEmpty(userIntegralExchangeDto.getAddress()))
        {
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("地址的长度不能为空");
            return result;
        }
        else if(userIntegralExchangeDto.getConsigneeName().length() > 30)
        {
            log.debug("地址的长度不能大于36， 数据库");

            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("收货人姓名的长度不能大于30");
            return result;
        }
        
        //收货人姓名
        if(StringUtils.isEmpty(userIntegralExchangeDto.getConsigneeName()))
        {
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("收货人姓名不能为空");
            return result;
        }
        else if(userIntegralExchangeDto.getConsigneeName().length() > 30 )
        {
            log.debug("收货人的姓名不能大于36， 数据库");

            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("收货人姓名的长度不能大于30");
            return result;
        }
        
        
        //收货人城市
        if(StringUtils.isEmpty(userIntegralExchangeDto.getConsigneeCity()))
        {
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("收货人城市不能为空");
            return result;
        }
        else if(userIntegralExchangeDto.getConsigneeCity().length() > 30 )
        {
            log.debug("收货人城市的长度不能大于30， 数据库");

            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("收货人城市的长度不能大于30");
            return result;
        }
        
        //使用@Transactional标签时必须绑定数据源
        DataSourceHolderUtil.setToMaster();
        int data = iUserIntegralExchangeService.addUserIntegralExchange(userIntegralExchangeDto);
        if(data == 1)
        {
            result.setStatus(HttpDataResponse.HTTP_SUCCESS);
            result.setMessage("兑换成功");
            return result;
        }
        
        log.warn("数据库插入失败");

        result.setStatus(HttpDataResponse.HTTP_FAILE);
        result.setMessage("兑换失败");
        return result;
    }
}

