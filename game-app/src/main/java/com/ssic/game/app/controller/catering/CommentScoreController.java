package com.ssic.game.app.controller.catering;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.base.service.ICommentScoreService;
import com.ssic.catering.base.service.IPromptsWarningService;
import com.ssic.catering.base.service.ISensitiveWarningResultService;

/**
 * 		
 * <p>Title: CommentScoreController </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年8月5日 下午5:05:20	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年8月5日 下午5:05:20</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping(value = "/http/api/catering")
public class CommentScoreController
{
    
    @Autowired
    private ICommentScoreService commentScoreService; 
    @Autowired
    private IPromptsWarningService promptsWarningService;
    @Autowired
    private ISensitiveWarningResultService sensitiveResult;
    
    @ResponseBody
    @RequestMapping(value = "/submit.do",method = {RequestMethod.POST,RequestMethod.GET})
    public String submitData(HttpServletRequest request) {
        return  commentScoreService.insertsCommentScore(request);
    }
}

