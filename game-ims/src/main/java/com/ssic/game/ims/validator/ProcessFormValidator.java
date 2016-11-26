/**
 * 
 */
package com.ssic.game.ims.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ssic.game.common.dao.ActionUserPersDao;
import com.ssic.game.common.dto.ActionsDto;
import com.ssic.game.common.dto.FieldsDto;
import com.ssic.game.common.dto.FormsDto;
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.dto.ProcInstantsDto;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.game.common.pojo.ActionUser;
import com.ssic.game.common.service.IActionService;
import com.ssic.game.common.service.IFieldsService;
import com.ssic.game.common.service.IFormsService;
import com.ssic.game.common.service.IImsUserService;
import com.ssic.game.common.service.IProcInstanceService;
import com.ssic.game.common.service.ITasksService;
import com.ssic.game.common.service.ProjectServices;
import com.ssic.game.common.util.HttpDataResponse;
import com.ssic.game.im.dto.QjyImUserDto;
import com.ssic.game.im.service.IQjyImService;
import com.ssic.game.ims.eventBus.EventRegister;
import com.ssic.game.ims.model.ProcessFormRequest;
import com.ssic.util.model.RequestResult;

/**		
 * <p>Title: ProcessFormValidator </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author rkzhang	
 * @date 2015年6月30日 上午10:46:14	
 * @version 1.0
 * <p>修改人：rkzhang</p>
 * <p>修改时间：2015年6月30日 上午10:46:14</p>
 * <p>修改备注：</p>
 */
@Service
public class ProcessFormValidator
{

    @Autowired
    private IImsUserService imsUserService;

    @Autowired
    private IFormsService formsService;

    @Autowired
    private IFieldsService fieldsService;

    @Autowired
    private IProcInstanceService procInstanceService;

    @Autowired
    private ProjectServices projectService;

    @Autowired
    private ActionUserPersDao actionUserDao;

    @Autowired
    private IActionService actionService;

    @Autowired
    private IQjyImService iQJYService;

    @Autowired
    private ITasksService taskService;

    @Autowired
    private EventRegister er;

    /**     
     * validate：进行表单数据的权限以及完整性效验
     * @param formRequest
     * @return
     * @exception	
     * @author rkzhang
     * @date 2015年6月30日 上午10:48:07	 
     */
    public RequestResult validate(ProcessFormRequest formRequest)
    {

        ProjectDto projDto = projectService.findById(formRequest.getProjectId());
        if (projDto == null)
        {
            return new RequestResult(false, HttpDataResponse.PROJECT_IS_NULL);
        }
        ImsUsersDto user = imsUserService.findByUserId(formRequest.getUserId());
        if (user == null)
        {
            return new RequestResult(false, HttpDataResponse.User_IS_NULL);
        }
        FormsDto form = formsService.findByFormId(formRequest.getFormId());
        if (form == null)
        {
            return new RequestResult(false, HttpDataResponse.FORM_IS_NULL);
        }

        //根据流程id 查找对应的流程实例是否存在，如果不存在则返回false;
        ProcInstantsDto ProcInstanceDto = procInstanceService.findByProcId(form.getProcId());
        if (ProcInstanceDto == null)
        {
            return new RequestResult(false, HttpDataResponse.PROCINSTANCE_IS_NULL);
        }
        List<FieldsDto> fieldList = fieldsService.findByFormId(formRequest.getFormId());
        //验证表单字段是否合法;
        return validFormField(fieldList, formRequest.getFields());

    }

    public RequestResult validFormField(List<FieldsDto> fieldList, Map<String, Object> fields)
    {

        if (CollectionUtils.isEmpty(fieldList))
        {//如果字段集合为null,则直接返回Rejected,不做校验;
            return new RequestResult(false, HttpDataResponse.Fields_IS_NULL);
        }
        for (FieldsDto fieldDto : fieldList)
        {
            //正则校验：如果该字段有正则表达式,且不符合的则返回false;
            if (!StringUtils.isEmpty(fieldDto.getPattern()) && !regExpString(fieldDto, fields))
            {
                return new RequestResult(false, fieldDto.getParamDesc() + ":正则验证不合法。");
            }
            boolean isNullValue = paramNameIsNull(fieldDto, fields);
            //参数名是null,返回错误false;
            if (isNullValue)
            {
                return new RequestResult(false, HttpDataResponse.PARAM_VALUE_IS_NULL);
            }
        }
        return new RequestResult(true, HttpDataResponse.FORM_VALID_PASS);
    }

    /**     
     * regExpString：参数str是否符合正则表达式
     * @param str
     * 
     */
    public boolean regExpString(FieldsDto fieldDto, Map<String, Object> fields)
    {
        boolean flag = false;
        //匹配标识符必须由字母、数字、下划线组成，不包含中文,且开头和结尾不能有下划线 
        Pattern p = Pattern.compile(fieldDto.getPattern());
        for (String key : fields.keySet())
        {
            if (key.equals(fieldDto.getParamName()))
            {

                boolean isArray = fields.get(key) instanceof String[];
                if (!isArray)
                {
                    String strs = (String) fields.get(key);
                    Matcher m = p.matcher(strs);
                    flag = m.matches();

                }
                return flag;

            }
        }
        return flag;
    }

    /**     
     * getFieldDto：判断参数值为null的字段信息
     * @param field
     * @param key
     * @param fields
     */
    public boolean paramNameIsNull(FieldsDto field, Map<String, Object> fields)
    {
        for (String key : fields.keySet())
        {
            //如果是必填，则校验所有的paramName不能为null,否则返回false;
            if (!StringUtils.isEmpty(field.getIsneed()))
            {
                //如果是自定义字段，则循环所有paramName对象
                if (!StringUtils.isEmpty(field.getIsdiy()))
                {
                    for (int i = 0; i < 50; i++)
                    { //设定最多有50个参数按钮;如：地址参数是："address"，第二个地址是"address1",以此类推:"address2","address3"...
                        String paramName = null;
                        if (i == 0)
                        {
                            paramName = field.getParamName();
                        }
                        else
                        {
                            paramName = field.getParamName() + i;
                        }

                        boolean isNullValue = isNullValue(paramName, key, fields);
                        //循环每一个paramName,只有是空的时候才返回;
                        if (isNullValue)
                        {
                            return false;
                        }
                    }
                }
                //如果不是自定义,取当前字段的paramName;
                return isNullValue(field.getParamName(), key, fields);
            }
        }
        return false;

    }

    public boolean isNullValue(String paramName, String key, Map<String, Object> fields)
    {
        if (key.equals(paramName))
        {

            boolean isArray = fields.get(key) instanceof String[];
            if (!isArray)
            {
                String paramNameValue = (String) fields.get(key);
                if (StringUtils.isEmpty(paramNameValue))
                {
                    return true;
                }
            }
            else
            {

                String[] valueArray = (String[]) fields.get(key);
                for (String paramValue : valueArray)
                {
                    if (StringUtils.isEmpty(paramValue))
                    {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    public void sendMessageToUsers(String projId, String procId, String nextTaskId, String text)
    {

        ActionsDto dto = actionService.findAction(projId, procId, nextTaskId);
        ActionUser param = new ActionUser();
        List<String> toIdList = new ArrayList<String>();
        if (dto != null && !StringUtils.isEmpty(dto.getId()))
        {
            param.setActionId(dto.getId());
            List<ActionUser> list = actionUserDao.findUserAll(param);

            if (!CollectionUtils.isEmpty(list))
            {
                for (ActionUser actionUser : list)
                {
                    String userId = actionUser.getUserId();
                    ImsUsersDto userDto = imsUserService.findByUserId(userId);
                    toIdList.add(userDto.getQjyAccount());
                }
            }
        }
        if (!CollectionUtils.isEmpty(toIdList))
        {
            sendMessage(toIdList, text);
        }
    }

    //轻加云传输数据
    private void sendMessage(List<String> toIdList, String text)
    {
        QjyImUserDto param = new QjyImUserDto();
        param.setToIdList(toIdList);
        param.setText(text);
        //调用卿佳云
        er.getQjyEvent().post(param);
    }

}
