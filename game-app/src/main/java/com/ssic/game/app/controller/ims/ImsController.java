/**
 * 
 */
package com.ssic.game.app.controller.ims;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.game.common.dto.DeptDto;
import com.ssic.game.common.dto.DeptUsersDto;
import com.ssic.game.common.dto.ParamConfigDto;
import com.ssic.game.common.service.DeptUserService;
import com.ssic.game.common.service.IDeptService;
import com.ssic.game.common.service.IParamConfigService;
import com.ssic.game.ims.model.ActionRecordModel;
import com.ssic.game.ims.model.DeptListDto;
import com.ssic.game.ims.model.LoadCompletionDto;
import com.ssic.game.ims.model.LoadProcess;
import com.ssic.game.ims.model.NewFormDataResult;
import com.ssic.game.ims.model.NewLoadTask;
import com.ssic.game.ims.model.TaskData;
import com.ssic.game.ims.service.ILoadProcessService;
import com.ssic.util.constants.DataStatus;
import com.ssic.util.model.RequestResult;
import com.ssic.util.model.Response;

/**		
 * <p>Title: ImsController </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author milkteaye	
 * @date 2015年7月9日 上午9:21:17	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年7月9日 上午9:21:17</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping("/http/api/ims/app/")
public class ImsController
{
    @Autowired
    private ILoadProcessService iLoadProcessService;

    @Autowired
    private IDeptService deptService;

    @Autowired
    private DeptUserService deptUserService;
    @Autowired
    private IParamConfigService paramConfigService;

    /**     
     * achieveForm：获取此用户能创建流程列表
     * @param request
     * @return
     * @exception   
     * @author milkteaye
     * @date 2015年6月25日 下午3:47:45   
     */
    @ResponseBody
    @RequestMapping(value = "/loadProcess.do", method = {RequestMethod.GET, RequestMethod.POST })
    public Response<List<LoadProcess>> loadProcess(String userId, String projId)
    {
        return iLoadProcessService.loadProcess(userId, projId);
    }

    /**     
     * loadSubDeptFormData：加载子部门的用户表单数据
     * @param request
     * @return
     * @exception   
     * @author 刘博
     * @date 2015年6月25日 下午3:47:45   
     */
    //    @ResponseBody
    //    @RequestMapping(value = "/loadSubDeptFormData.do", method = {RequestMethod.GET, RequestMethod.POST })
    //    public Response<List<LoadCompletionDto>> loadSubDeptFormData(String userId, String projId)
    //    {
    //        return iLoadProcessService.loadSubDeptFormData(userId, projId);
    //    }

    @ResponseBody
    @RequestMapping(value = "/loadFormData.do", method = {RequestMethod.GET, RequestMethod.POST })
    public Response<TaskData> loadFormData(String projId, String procId, String userId, String formId,
        String proinsId)
    {

        return iLoadProcessService.loadTaskData(userId, projId, procId, formId, proinsId);
    }

    /**     
     * achieveForm：获取未办理事务
     * @param request
     * @return
     * @exception   
     * @author milkteaye
     * @date 2015年6月25日 下午3:47:45   
     */
    //    @ResponseBody
    //    @RequestMapping(value="/loadAgency.do",method={RequestMethod.GET,RequestMethod.POST})
    //    public Response<List<LoadCompletionDto>> loadAgency(String userId,String projId,String procId){
    //        return iLoadProcessService.loadAgency(userId, projId,procId);
    //    }
    @ResponseBody
    @RequestMapping(value = "/loadAgency.do", method = {RequestMethod.GET, RequestMethod.POST })
    public Response<List<NewFormDataResult>> loadAgency(String userId, String projId, String procId,
        String procInstId)
    {
        return iLoadProcessService.loadNewAgency(userId, projId, procId, procInstId);
    }

    /**
     * 寻找已办理事项接口查询mengo
     */
    @ResponseBody
    @RequestMapping(value = "/loadCompletion.do", method = {RequestMethod.GET, RequestMethod.POST })
    public Response<List<LoadCompletionDto>> LoadCompletion(String userId, String projId, String procId,
        int beginRows, int endRows)
    {
        if (StringUtils.isEmpty(userId))
        {
            return new Response<List<LoadCompletionDto>>(DataStatus.HTTP_FAILE,
                "userId can not be null",
                null);
        }
        return iLoadProcessService.loadCompletion(userId, projId, procId, beginRows, endRows);
    }

    /**
     * 删除我的(请假，出差，报销)里面的某一条详情记录；
     */
    @ResponseBody
    @RequestMapping(value = "/deleteMyData.do", method = {RequestMethod.GET, RequestMethod.POST })
    public Response<RequestResult> deleteMyData(String projId, String procId, String procInsId)
    {
        if (StringUtils.isEmpty(procInsId))
        {
            return new Response<RequestResult>(DataStatus.HTTP_FAILE, "procInsId can not be null", null);
        }

        return iLoadProcessService.deleteMyData(projId, procId, procInsId);
    }

    /**
     * 查找所有的请假
     */
    @ResponseBody
    @RequestMapping(value = "/loadAllCompletion.do", method = {RequestMethod.GET, RequestMethod.POST })
    public Response<List<LoadCompletionDto>> loadAllCompletion(String userId, String projId, String procId,
        int beginRows, int endRows, String paramName)
    {
        //项目id判断
        if (StringUtils.isEmpty(projId))
        {
            return new Response<List<LoadCompletionDto>>(DataStatus.HTTP_FAILE, "项目id不能为空", null);
        }
        if (StringUtils.isEmpty(paramName))
        {
            return new Response<List<LoadCompletionDto>>(DataStatus.HTTP_FAILE, "参数值不能为空", null);
        }
        //读取参数表的请假，出差和报销的部门编码;
        String deptCode = getParamValueByParamName(projId, paramName);
        if (StringUtils.isEmpty(deptCode))
        {
            return new Response<List<LoadCompletionDto>>(DataStatus.HTTP_FAILE, "部门编码参数值为空", null);
        }
        DeptDto param = new DeptDto();
        param.setDeptCode(deptCode);
        List<DeptDto> deptList = deptService.findBy(param);
        if (CollectionUtils.isEmpty(deptList))
        {
            return new Response<List<LoadCompletionDto>>(DataStatus.HTTP_FAILE, "您没有权限查看全部", null);
        }
        String deptId = deptList.get(0).getId();
        DeptUsersDto deptUserDto = new DeptUsersDto();
        deptUserDto.setDeptId(deptId);
        deptUserDto.setIsAdmin("1");
        deptUserDto.setUserId(userId);
        List<DeptUsersDto> deptUserList = deptUserService.findAllBy(deptUserDto);
        if (CollectionUtils.isEmpty(deptUserList))
        {
            return new Response<List<LoadCompletionDto>>(DataStatus.HTTP_FAILE, "您没有权限查看全部", null);
        }

        return iLoadProcessService.loadCompletion(null, projId, procId, beginRows, endRows);
    }

    /**
     * 
     * 部门下人员列表
     * @param actionModel
     * @return
     * @exception	
     * @author milkteaye
     * @date 2015年8月7日 下午5:40:06
     */

    @ResponseBody
    @RequestMapping(value = "/deptUserList.do", method = {RequestMethod.GET, RequestMethod.POST })
    public Response<DeptListDto> loadDeptCompletion(String userId, String projId, String procId)
    {
        DeptListDto result = new DeptListDto();
        DeptUsersDto deptUserDto = new DeptUsersDto();
        deptUserDto.setUserId(userId);
        deptUserDto.setIsAdmin("1");
        deptUserDto.setProjId(projId);
        List<DeptUsersDto> deptTempList = deptUserService.findAllBy(deptUserDto);
        if (CollectionUtils.isEmpty(deptTempList))
        {
            return new Response<DeptListDto>(DataStatus.HTTP_FAILE, "您没有权限查看部门", null);
        }
        else
        {
            if (!"1".equals(deptTempList.get(0).getIsAdmin()))
            {
                return new Response<DeptListDto>(DataStatus.HTTP_FAILE, "您没有权限查看部门", null);
            }
        }
        List<DeptUsersDto> deptList = iLoadProcessService.findDeptUser(userId, projId);
        result.setDeptUsersDtoList(deptList);
        if (CollectionUtils.isEmpty(deptList))
        {
            return new Response<DeptListDto>(DataStatus.HTTP_SUCCESS, "", result);
        }
        String deptId = deptList.get(0).getDeptId();
        result.setLoadCompletionList(iLoadProcessService.loadDeptCompletion(deptId, projId, procId));
        return new Response<DeptListDto>(DataStatus.HTTP_SUCCESS, "", result);
    }

    //    @ResponseBody
    //    @RequestMapping(value = "/loadCompTask.do", method = {RequestMethod.GET, RequestMethod.POST })
    //    public Response<TaskData> loadCompTask(String projId, String procId, String userId, String formId,
    //        String proinsId)
    //    {
    //
    //        return iLoadProcessService.loadTaskData(userId, projId, procId, formId, proinsId);
    //    }
    //    

    @ResponseBody
    @RequestMapping(value = "/loadCompTask.do", method = {RequestMethod.GET, RequestMethod.POST })
    public Response<List<NewLoadTask>> loadCompTask(ActionRecordModel actionModel)
    {

        return iLoadProcessService.loadNewTaskData(actionModel);
    }

    private String getParamValueByParamName(String projId, String paramName)
    {
        ParamConfigDto paramConfigDto = new ParamConfigDto();
        //查找参数表的部门编码
        paramConfigDto.setParamType(3);
        //放入项目id
        paramConfigDto.setProjId(projId);
        Map<String, String> map = paramConfigService.getParametersBy(paramConfigDto);
        if (map != null && map.size() > 0)
        {
            for (String key : map.keySet())
            {
                if (key.equals(paramName))
                {
                    return map.get(key);
                }
            }
        }
        return null;
    }
}
