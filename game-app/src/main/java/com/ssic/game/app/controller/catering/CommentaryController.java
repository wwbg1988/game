package com.ssic.game.app.controller.catering;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.base.dto.FindDataResults;
import com.ssic.catering.base.dto.ResultsData;
import com.ssic.catering.base.service.IConfigSearchService;
import com.ssic.catering.base.service.ISensitiveWarningReportService;
import com.ssic.shop.manage.dto.LuckyDrawHistoryDto;
import com.ssic.shop.manage.service.LuckyDrawHistoryService;
import com.ssic.util.constants.DataStatus;
import com.ssic.util.model.Response;

/**
 * 		
 * <p>Title: CommentaryController </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年8月4日 下午3:21:49	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年8月4日 下午3:21:49</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping(value = "/http/api/catering")
public class CommentaryController
{
    @Autowired
    private IConfigSearchService configSearchService;
    @Autowired
    private ISensitiveWarningReportService report;
    @Autowired
    private LuckyDrawHistoryService luckyDrawHistoryService;
    
    @ResponseBody
    @RequestMapping(value = "/search.do",method = {RequestMethod.GET, RequestMethod.POST})
    public Response<FindDataResults> search(String cafetoriumId,String userId){
        return configSearchService.findBy(cafetoriumId,userId);
    }
    
    @ResponseBody
    @RequestMapping(value = "/report.do",method = {RequestMethod.POST,RequestMethod.GET})
    public Response<List<ResultsData>> report(String userId){
        return report.querySensitiveWarning(userId);
    }
    
    @ResponseBody
    @RequestMapping(value = "/searchIphone.do",method = {RequestMethod.POST,RequestMethod.GET})
    public Response<String> searchIphone(String userId){
        String iphone = "";
        LuckyDrawHistoryDto dto = luckyDrawHistoryService.queryLuckyDrawHoistoryInfo(userId);
        Response<String> response = new Response<String>();
        if(!StringUtils.isEmpty(dto)){
            iphone = dto.getExchargePhone();
            response.setData(iphone);
            response.setStatus(DataStatus.HTTP_SUCCESS);
            return response;
        }else{
            response.setStatus(DataStatus.HTTP_FAILE);
            response.setMessage("未查到充值手机号");
            return response;
        }
    }

}

