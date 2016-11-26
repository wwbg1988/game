package com.ssic.game.app.controller.shop;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.shop.manage.dto.DinnerSeriesDto;
import com.ssic.shop.manage.dto.VisitServiceDto;
import com.ssic.shop.manage.service.IDinnerSeriesService;
import com.ssic.shop.manage.service.IVisitServiceService;
import com.ssic.util.DateUtils;
import com.ssic.util.StringUtils;
import com.ssic.util.constants.HttpDataResponse;
import com.ssic.util.model.Response;

/**     
 * <p>Title: VisitServiceController </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振   
 * @date 2015年9月15日 下午4:57:50   
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年9月15日 下午4:57:50</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping("/http/api/carte/visitService")
public class VisitServiceController
{
    private static Logger log = Logger.getLogger(VisitServiceController.class);
    
    @Autowired
    private IDinnerSeriesService dsService;
    
    @Autowired
    private IVisitServiceService vsService;
    
    /**
     * 获取套餐信息    
     * @author 朱振   
     * @date 2015年9月17日 上午9:16:39   
     * @version 1.0
     * @param pageHelper
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年9月17日 上午9:16:39</p>
     * <p>修改备注：</p>
     */
    @ResponseBody
    @RequestMapping("/getDinnerSeries.do")
    public Response<List<DinnerSeriesDto>> getDinnerSeries(String cafetoriumId)
    {
        Response<List<DinnerSeriesDto>> result = new Response<>();
        
        log.debug("parameter:cafetoriumId="+cafetoriumId);
        
        DinnerSeriesDto param = null;
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
        
        param = new DinnerSeriesDto();
        param.setCafetoriumId(cafetoriumId);
        
        List<DinnerSeriesDto> data = dsService.getListBy(param, null);
        
        log.debug("查询了"+(data==null?0:data.size())+"条记录:cafetoriumId"+cafetoriumId);
        
        if(!CollectionUtils.isEmpty(data))
        {
            result.setStatus(HttpDataResponse.HTTP_SUCCESS);
            result.setData(data);
            result.setMessage("查询成功");
            return result;
        }
        
        result.setStatus(HttpDataResponse.HTTP_FAILE);
        result.setMessage("套餐信息不存在");
        return result;
    }
    
    /**
     * 保存大厨上门烧菜的信息   
     * @author 朱振   
     * @date 2015年9月17日 上午9:23:27   
     * @version 1.0
     * @param visitServiceDto
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年9月17日 上午9:23:27</p>
     * <p>修改备注：</p>
     */
    @ResponseBody
    @RequestMapping("/saveVisit.do")
    public Response<String> addVisitService(VisitServiceDto visitServiceDto, String cafetoriumId)
    {
        Response<String> result = new Response<>();       
        
        log.debug("paramerters:"+visitServiceDto+":"+cafetoriumId);
                
        //id
        if(StringUtils.isEmpty(visitServiceDto.getId()))
        {
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("uuid未获取，请刷新重试");
            return result;
        }
        else if(visitServiceDto.getId().length() > 36)
        {
            log.debug("uuid的长度不能大于36");
            
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("uuid不合法");
            return result;
        }
        
      //cafetoriumId
        if(StringUtils.isEmpty(cafetoriumId))
        {
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("cafetoriumId不合法");
            return result;
        }
        else if(visitServiceDto.getId().length() > 36)
        {
            log.debug("cafetoriumId的长度不能大于36");
            
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("cafetoriumId不合法");
            return result;
        }
        
        //name
        if(StringUtils.isEmpty(visitServiceDto.getName()))
        {
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("姓名不能为空");
            return result;
        }
        else if(visitServiceDto.getName().length() > 30)
        {
            log.debug("姓名的长度不能大于30，数据库");
            
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("姓名的长度不能大于30");
            return result;
        }
        
        //phone
        Pattern pattern = Pattern.compile("(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}");
        if(StringUtils.isEmpty(visitServiceDto.getPhone()))
        {
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("手机号码不能为空");
            return result;
        }
        else if(!pattern.matcher(visitServiceDto.getPhone()).matches())
        {
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("手机号码不合法");
            return result;
        }
        
        //将当前的日期去掉时分秒
        Date now = null;
        try
        {
            now = new SimpleDateFormat("yyyy-MM-dd").parse(DateUtils.format(new Date(), "yyyy-MM-dd"));
        }
        catch (ParseException e)
        {
            //  日期解析异常
            
        }
        
        //日期
        if(visitServiceDto.getServiceTime().before(now))
        {
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("上门服务日期不能早于当前日期");
            return result;
        }
        
        

        //message
        if(!StringUtils.isEmpty(visitServiceDto.getMessage()) && visitServiceDto.getMessage().length() > 30)
        {
            log.debug("留言的长度不能大于50，数据库");
            
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("地址的长度不能大于50");
            return result;
        }
        
        //dinnerSeriesId
        if(StringUtils.isEmpty(visitServiceDto.getDinnerSeriesId()))
        {
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("未选中的套餐");
            return result;
        }
        else if(visitServiceDto.getDinnerSeriesId().length() > 36)
        {
            log.debug("dinnerSeriesId的长度不能大于36， 数据库");
            
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("所选的套餐不合法");
            return result;
        }
        
        //openId
        if(StringUtils.isEmpty(visitServiceDto.getOpenId()))
        {
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("请关注上海天坊信息公众号");
            return result;
        }
        else if(visitServiceDto.getOpenId().length() > 36)
        {
            log.debug("dinnerSeriesId的长度不能大于36， 数据库");
            
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("所选的套餐不合法");
            return result;
        }
        
        DinnerSeriesDto param = new DinnerSeriesDto();
        param.setCafetoriumId(cafetoriumId);
        
        List<DinnerSeriesDto> DinnerSeriesDtos = dsService.getListBy(param, null);
        if(CollectionUtils.isEmpty(DinnerSeriesDtos))
        {
            log.debug("该食堂没有该套餐，cafetoriumId="+cafetoriumId+":"+visitServiceDto);
            
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("所选的套餐不合法");
            return result;
        }
        
        //保存记录
        int count = vsService.save(visitServiceDto);
        if(count != 1)
        {
            log.warn("数据库插入失败");
            
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("保存失败");
            return result;
        }
        
        //保存成功
        result.setStatus(HttpDataResponse.HTTP_SUCCESS);
        result.setMessage("保存成功");
        
        return result;
    }
}
