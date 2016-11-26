/**
 * 
 */
package com.ssic.game.app.controller.ims;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.base.dto.SignDeptUserDto;
import com.ssic.game.app.service.SignService;
import com.ssic.game.common.constant.SignTypeConstants;
import com.ssic.game.common.dto.DeptUsersDto;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.dto.SignDto;
import com.ssic.game.common.pojo.Sign;
import com.ssic.game.ims.model.ActionRecordModel;
import com.ssic.game.ims.model.FormDataQuerys;
import com.ssic.game.ims.model.FormDataResults;
import com.ssic.game.ims.model.LoadCompletionDto;
import com.ssic.game.ims.model.LoadProcess;
import com.ssic.game.ims.model.NewFormDataResult;
import com.ssic.game.ims.model.NewLoadTask;
import com.ssic.game.ims.model.TaskData;
import com.ssic.game.ims.service.ILoadProcessService;
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
@RequestMapping("/http/api/ims/sign/")
public class SignController
{
    @Autowired
    private SignService signService;
    
    //考勤，签到插入接口
    @ResponseBody
    @RequestMapping(value = "/insertSign.do", method = {RequestMethod.GET, RequestMethod.POST })
    public Response<SignDto> insertSign(SignDto signDto) {
        return signService.insert(signDto);
    }
    
    @ResponseBody
    @RequestMapping(value = "/searchSign.do", method = {RequestMethod.GET, RequestMethod.POST })
    public Response<SignDeptUserDto>search(SignDto signDto,int beginRows,int endRows) throws ParseException{
        return signService.find(signDto,beginRows,endRows);
    }
    
    //查看全部的记录
    @RequestMapping(value = "/findAllSign.do", method = {RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Response<List<SignDto>> findAllSign(SignDto signDto,int beginRows,int endRows) throws ParseException{
    	return signService.findAllSign(signDto, beginRows, endRows);
    }
    
}
