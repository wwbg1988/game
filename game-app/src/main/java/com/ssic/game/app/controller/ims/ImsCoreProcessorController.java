package com.ssic.game.app.controller.ims;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.game.ims.model.AchieveFormRequest;
import com.ssic.game.ims.model.CreateProcRequest;
import com.ssic.game.ims.model.ProcessFormRequest;
import com.ssic.game.ims.model.TaskData;
import com.ssic.game.ims.service.IAchieveFormService;
import com.ssic.game.ims.service.ICreateFlowService;
import com.ssic.game.ims.service.ILoadProcessService;
import com.ssic.game.ims.service.IProcessFormService;
import com.ssic.util.ArrayUtils;
import com.ssic.util.StringUtils;
import com.ssic.util.constants.DataStatus;
import com.ssic.util.model.RequestResult;
import com.ssic.util.model.Response;

/**		
 * <p>Title: ImsCoreProcessorController </p>
 * <p>Description: 统一控制工作流跳转控制器</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author rkzhang	
 * @date 2015年6月25日 上午8:54:28	
 * @version 1.0
 * <p>修改人：rkzhang</p>
 * <p>修改时间：2015年6月25日 上午8:54:28</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping(value = "/http/api/ims")
public class ImsCoreProcessorController
{

    protected static final Log logger = LogFactory.getLog(ImsCoreProcessorController.class);

    @Autowired
    private ICreateFlowService createFlowService;

    @Autowired
    private IAchieveFormService achiveFromService;

    @Autowired
    private IProcessFormService processFormService;

    @Autowired
    private ILoadProcessService iLoadProcessService;

    /**     
     * createFlow：创建流程
     * @param request
     * @return
     * @exception	
     * @author rkzhang
     * @date 2015年6月25日 下午3:47:41	 
     */
    @ResponseBody
    @RequestMapping(value = "/createFlow.do", method = {RequestMethod.GET, RequestMethod.POST })
    public Response<TaskData> createFlow(CreateProcRequest request)
    {

        return createFlowService.createFlow(request);
    }

    /**     
     * achieveForm：获取节点信息
     * @param request
     * @return
     * @exception	
     * @author milkteaye
     * @date 2015年6月25日 下午3:47:45	 
     */
    @ResponseBody
    @RequestMapping(value = "/achieveTask.do", method = {RequestMethod.GET, RequestMethod.POST })
    public Response<TaskData> achieveTask(AchieveFormRequest request)
    {

        return achiveFromService.achieveTask(request);
    }

    /**     
     * processForm：表单信息处理
     * @param request
     * @return
     * @exception	
     * @author rkzhang
     * @date 2015年6月25日 下午3:47:48	 
     */
    @ResponseBody
    @RequestMapping(value = "/processForm.do", method = {RequestMethod.GET, RequestMethod.POST })
    public Response<RequestResult> processForm(HttpServletRequest request)
    {
        ProcessFormRequest formRequest = getParams(request);
        return processFormService.processForm(formRequest);
    }

    //组装请求处理表单对象
    private ProcessFormRequest getParams(HttpServletRequest request)
    {
        ProcessFormRequest formRequest = new ProcessFormRequest();

        //String account = "account";
        String userId = "userId";
        String procInstanceId = "procInstanceId";
        String formId = "formId";
        String actionId = "actionId";
        String projectId = "projectId";
        //指派人id
       // String assignUserId = "assignUserId";
       // formRequest.setAssignUserId(request.getParameter(assignUserId));
        formRequest.setUserId(request.getParameter(userId));
        formRequest.setProcInstanceId(request.getParameter(procInstanceId));
        formRequest.setFormId(request.getParameter(formId));
        formRequest.setActionId(request.getParameter(actionId));
        formRequest.setProjectId(request.getParameter(projectId));

        Map<String, Object> params = new HashMap<String, Object>();
        Enumeration<String> keys = request.getParameterNames();
        while (keys.hasMoreElements())
        {
            String key = keys.nextElement();
            String[] results = request.getParameterValues(key);
            if (ArrayUtils.isNotEmpty(results) && results.length > 1)
            {
                params.put(key, results);
            }
            else
            {
                params.put(key, request.getParameter(key));
            }

        }
        params.remove(projectId);
        params.remove(userId);
        params.remove(procInstanceId);
        params.remove(formId);
        params.remove(actionId);
        formRequest.setFields(params);
        return formRequest;
    }
}
