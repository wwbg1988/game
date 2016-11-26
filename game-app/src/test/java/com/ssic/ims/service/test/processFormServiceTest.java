/**
 * 
 */
package com.ssic.ims.service.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ssic.game.app.test.BaseTestCase;
import com.ssic.game.common.dto.FieldsDto;
import com.ssic.game.common.dto.FormsDto;
import com.ssic.game.common.service.IFieldsService;
import com.ssic.game.common.service.IFormsService;
import com.ssic.game.common.service.IImsUserService;
import com.ssic.game.common.service.IProcInstanceService;
import com.ssic.game.ims.model.ProcessFormRequest;
import com.ssic.game.ims.service.IProcessFormService;
import com.ssic.game.ims.service.impl.ProcessFormServiceImpl;
import com.ssic.game.ims.validator.ProcessFormValidator;

/**		
 * <p>Title: processFormServiceTest </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年7月2日 下午3:48:42	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年7月2日 下午3:48:42</p>
 * <p>修改备注：</p>
 */
public class processFormServiceTest extends BaseTestCase
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
    private IProcessFormService processFormService;
    @Autowired
    private ProcessFormValidator validator;
    @Autowired
    private ProcessFormServiceImpl processFormServiceImpl;

    protected static final Log logger = LogFactory.getLog(processFormServiceTest.class);

    @Test
    public void validFormFieldTest()
    {//获取请求参数;
        ProcessFormRequest formRequest = getFormRequest();
        List<FieldsDto> fieldList = fieldsService.findByFormId(formRequest.getFormId());

        //验证表单字段是否合法;
        //   RequestResult aa = validator.validFormField(fieldList, formRequest.getFields());
        //  logger.info("------------验证表单字段是否合法：--------------------" + aa);
        //验证表单请求值是否合法;
        //  RequestResult bb = validator.validate(formRequest);
        //  logger.info("------------验证表单是否合法：--------------------" + bb);
    }

    @Test
    public void processSaveOrUpdateFormTest()
    {
        //获取请求参数;
        ProcessFormRequest formRequest = getFormRequest();
        List<FieldsDto> fieldDtoList = fieldsService.findByFormId(formRequest.getFormId());
        FormsDto form = formsService.findByFormId(formRequest.getFormId());
        //Response<RequestResult> result = processFormService.processForm(formRequest);
        //  logger.info("result--------------------" + result);
        //保存表单
        //        processFormServiceImpl.saveOrUpdateForm(formRequest, fieldDtoList, form,1);

    }

    @Test
    public void revertFormTest()
    {
        //获取请求参数;
        ProcessFormRequest formRequest = getFormRequest();
        //form
        FormsDto form = formsService.findByFormId(formRequest.getFormId());
        //退回表单
        //   processFormServiceImpl.revertForm(form, formRequest, 1);

    }

    public ProcessFormRequest getFormRequest()
    {
        ProcessFormRequest formRequest = new ProcessFormRequest();
        formRequest.setFormId("08e38992-1205-4b60-8363-dc07c09055ff");
        formRequest.setUserId("0b421ac0-8ef1-4ab4-831a-070a947da4e2");
        formRequest.setProcInstanceId("1");
        formRequest.setActionId("214d25c7-4f51-4a9d-b3f4-749480cdb1f7");
        formRequest.setProjectId("cd96de7d-fcd0-4812-8ccf-99bdd2a1ebb7");
        Map<String, Object> fields = new HashMap<String, Object>();
        fields.put("number", "19937011521");
        formRequest.setFields(fields);

        return formRequest;
    }

    @Test
    public void regPatternTest()
    { //邮箱
      //String pattern="^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
      //手机号码

        String pattern = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        ProcessFormRequest formRequest = new ProcessFormRequest();
        Map<String, Object> fields = new HashMap<String, Object>();
        fields.put("number", "13937017164");
        formRequest.setFields(fields);
        // List<FieldsDto> fieldList = fieldsService.findByFormId("08e38992-1205-4b60-8363-dc07c09055ff");
        List<FieldsDto> fieldList = new ArrayList<FieldsDto>();
        FieldsDto d=new FieldsDto();
        d.setParamName("number");
        d.setParamDesc("数字");
        d.setPattern(pattern);
        fieldList.add(d);
        FieldsDto fieldDto = new FieldsDto();
        fieldDto.setParamDesc("数字");
        fieldDto.setParamName("number");
        fieldDto.setPattern(pattern);
        regExpString(fieldList, fields);
    }

    public boolean regExpString(List<FieldsDto> fieldList, Map<String, Object> fields)
    {
        boolean flag = false;
        //匹配标识符必须由字母、数字、下划线组成，不包含中文,且开头和结尾不能有下划线 
        for (FieldsDto fieldDto : fieldList)
        {

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
                        logger.info("正则验证结果:--------------------" + flag);
                    }
                    return flag;
                }
            }
        }
        return flag;
    }
}
