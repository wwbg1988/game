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

import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.dto.query.QueryPage;
import com.ssic.game.common.dto.query.QueryPageList;
import com.ssic.game.ims.model.FormDataQuerys;
import com.ssic.game.ims.model.FormDataResults;
import com.ssic.game.ims.service.IQueryAchieveService;
import com.ssic.game.ims.service.IQuerySearchService;
import com.ssic.util.model.Response;

/**		
 * <p>Title: ImsQueryController </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author rkzhang	
 * @date 2015年7月23日 下午5:46:21	
 * @version 1.0
 * <p>修改人：rkzhang</p>
 * <p>修改时间：2015年7月23日 下午5:46:21</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping(value = "/http/api/ims")
public class ImsQueryController {
    
    @Autowired
    private IQueryAchieveService queryAchService;
   
    @Autowired
    private IQuerySearchService querySearchService;
    
    //查询结果集接口
    @ResponseBody
    @RequestMapping(value = "/search.do", method = {RequestMethod.GET, RequestMethod.POST })
    public Response<FormDataResults> searchFormData(FormDataQuerys query,PageHelperDto ph) {
	
	return querySearchService.findBy(query, ph);
    }
    
    //手机端组装表单接口
    @ResponseBody
    @RequestMapping(value = "/achieveQueryPage.do", method = {RequestMethod.GET, RequestMethod.POST })
    public Response<QueryPage> achieveQueryPage(String queryId) {
        
	return queryAchService.findQuery(queryId);
    }
    
    @RequestMapping(value = "/searchQueryList.do", method = {RequestMethod.GET, RequestMethod.POST })
    public Response<List<QueryPageList>> searchQueryList(String projectId) {
        
    return queryAchService.findList(projectId);
    }
}

