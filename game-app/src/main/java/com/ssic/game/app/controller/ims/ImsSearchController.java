/**
 * 
 */
package com.ssic.game.app.controller.ims;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.game.app.service.SearchService;
import com.ssic.game.common.dto.WorkSearchDto;
import com.ssic.util.model.Response;

/**		
 * <p>Title: ImsSearchController </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author milkteaye	
 * @date 2015年8月11日 下午5:49:05	
 * @version 1.0
 * <p>修改人：milkteaye</p>
 * <p>修改时间：2015年8月11日 下午5:49:05</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping("/http/api/ims/app/search/")
public class ImsSearchController
{
    @Autowired
    private SearchService searchService;
    

    /**
     * 寻找代办列表
     */
    @ResponseBody
    @RequestMapping(value = "/agency.do", method = {RequestMethod.GET, RequestMethod.POST })
    public Response<List<WorkSearchDto>> LoadCompletion(String userId, String projId, String searchDate,
        int beginRows, int endRows , int eventType)
    {
        return searchService.findAll(userId, projId, searchDate, beginRows, endRows,eventType);
    }
}

