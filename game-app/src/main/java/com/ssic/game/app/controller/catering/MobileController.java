package com.ssic.game.app.controller.catering;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.game.common.HttpDataResponse;
import com.ssic.game.common.constant.MobileMessageType;
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.dto.MobileMessageSenderDto;
import com.ssic.game.common.dto.ParamConfigDto;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.game.common.pojo.ImsUsers;
import com.ssic.game.common.service.IImsUserService;
import com.ssic.game.common.service.IMobileService;
import com.ssic.game.common.service.IParamConfigService;
import com.ssic.game.common.service.IProjectUsersService;
import com.ssic.game.common.util.VerificationCodeUtil;
import com.ssic.util.model.Response;

/**		
 * <p>Title: MobileController </p>
 * <p>Description: 手机</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振	
 * @date 2015年10月28日 下午1:14:50	
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年10月28日 下午1:14:50</p>
 * <p>修改备注：</p>
 */
@RequestMapping("/http/api/mobileController")
@Service
public class MobileController
{
    private static final Logger log = Logger.getLogger(MobileController.class);
    
    @Autowired
    private IParamConfigService paramConfigService;
    
    @Autowired
    private IMobileService mobileService;
    
    @Autowired
    private IImsUserService imsUserService;
    
    @Autowired
    private IProjectUsersService projectUsersService;
    
    
    /**
     * 手机绑定<BR>	 
     * @author 朱振	
     * @date 2015年10月30日 下午1:45:46	
     * @version 1.0
     * @param userId
     * @param code
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年10月30日 下午1:45:46</p>
     * <p>修改备注：</p>
     */
    @RequestMapping("/binding.do")
    @ResponseBody
    public Response<String> bindMobilePhone(@RequestParam("userId")String userId,@RequestParam("code")String code,@ModelAttribute("user")ImsUsers user)
    {
        Response<String> response = new Response<>();
        //userId
        if(StringUtils.isEmpty(userId))
        {
            log.error("参数userId不能为空");
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("userId不能为空");
            return response;
        }
        
        if(user == null)
        {
            log.error("参数不能为空");
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("user不能为空");
            return response;
        }
        
        //手机号码
        Pattern pattern = Pattern.compile("(86)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}");
        if(StringUtils.isEmpty(user.getMobilePhone()))
        {
            log.error("参数：mobilePhone不能为空");
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("手机短信参数配置出错");
            return response;
        }
        else if(!pattern.matcher(user.getMobilePhone()).matches())
        {
            log.error("手机号码不合法"+user.getMobilePhone());
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("手机短信参数配置出错");
            return response;
        }
        
        //code
        if(StringUtils.isEmpty(code))
        {
            log.error("验证码不能为空");
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("验证码不能为空");
            return response;
        }
        
        //验证用户是否存在
        ImsUsersDto imsUser = imsUserService.findByUserId(userId);
        if(imsUser == null)
        {
            log.error("在数据库中找不到该用户");
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("在数据库中找不到该用户");
            return response;
        }
        
        //判断是否绑定
        if(imsUser.getMobileState() != null && 2==imsUser.getMobileState())
        {
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("已绑定手机号");
            return response;
        }
       
        //验证发送验证码的手机与当前绑定的手机是否一致
        if(!user.getMobilePhone().equals(imsUser.getMobilePhone()))
        {
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("绑定手机必须是发送验证码的手机");
            return response;
        }
        
        //验证码是否相同
        if(code.equals(imsUser.getIdentifyingCode()))
        {
            //10分钟
            if((new Date().getTime()-imsUser.getModifydatetime().getTime())/(60*1000) > 10)
            {
//                param.setMobileState(0);//验证码超时
//                param.setIdentifyingCode("");//清空验证码
                response.setStatus(HttpDataResponse.HTTP_FAILE);
                response.setMessage("验证码超时，请重新发送");
                return response;
            }
            
            ImsUsersDto param = new ImsUsersDto();
          //更新数据库
            param.setId(imsUser.getId());
            param.setModifydatetime(new Date());
            param.setMobileState(2);//绑定成功
            param.setIdentifyingCode("");//清空验证码
            imsUserService.updateImsUsersBy(param);
           
            
            response.setStatus(HttpDataResponse.HTTP_SUCCESS);
            response.setMessage("绑定成功");
            return response;
        }
        else
        {
          //验证失败
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("验证码错误");
            return response;
        }
    }
   
   /**
    * 发送手机验证码，并返回验证码<BR>     	 
    * @author 朱振	
    * @date 2015年10月30日 下午1:45:56	
    * @version 1.0
    * @param userId
    * @param type
    * @param user
    * @return
    * <p>修改人：朱振</p>
    * <p>修改时间：2015年10月30日 下午1:45:56</p>
    * <p>修改备注：</p>
    */
    @RequestMapping("/sendCode.do")
    @ResponseBody
    public Response<String> sendCode(@RequestParam("userId")String userId,@RequestParam("messageType")String type,@ModelAttribute("user")ImsUsers user)
    {
        //验证传入参数
        Response<String> response = validateSendCodeParameters(userId,type,user);
        if(500==response.getStatus())
        {
            return response;
        }
        
        //查找用户是否存在
        ImsUsersDto imsUser = imsUserService.findByUserId(userId);
        if(imsUser==null)
        {
            log.error("用户不存在，userId="+user.getId());
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("用户不存在");
            return response;
        }
        
        //查找项目
        ProjectDto project = projectUsersService.getProjectByIMSUserId(userId);
        if(project == null)
        {
            log.error("在t_ims_project_users找不到user_id="+user.getId()+"的记录");
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("找不到与该用户关联的项目");
            return response;
        }
               
                
        //手机绑定
        if(MobileMessageType.BINDING.equals(MobileMessageType.valueOf(type.toUpperCase())))
        {
            //判断是否已绑定手机   
            if(imsUser.getMobileState() != null && 2==imsUser.getMobileState())
            {
                log.error("该用户已绑定手机");
                response.setStatus(HttpDataResponse.HTTP_FAILE);
                response.setMessage("该用户已绑定手机");
                return response;
            }
            
            // 通过手机号码和项目查找是否有匹配用户
            ImsUsersDto phoneParam = new ImsUsersDto();
            phoneParam.setMobilePhone(user.getMobilePhone());
            phoneParam.setProjId(project.getId());
            List<ImsUsersDto> resultSet = imsUserService.findBy(phoneParam);
            if(!CollectionUtils.isEmpty(resultSet))
            {
                
                for(ImsUsersDto bingUser:resultSet)
                {
                    //已绑定用户 2表示已绑定
                    if(bingUser != null && bingUser.getMobileState() != null && 2==bingUser.getMobileState())
                    {
                        response.setStatus(HttpDataResponse.HTTP_FAILE);
                        response.setMessage("该手机已绑定用户");
                        return response;
                    }
                }
            }        
            
        }//手机解绑类型
        else if(MobileMessageType.UNBINDING.equals(MobileMessageType.valueOf(type.toUpperCase())))
        {
            
            if(imsUser.getMobileState() == null || 2!=imsUser.getMobileState())
            {
                log.error("该用户未绑定手机");
                response.setStatus(HttpDataResponse.HTTP_FAILE);
                response.setMessage("该用户未绑定手机");
                return response;
            }            
            
        }
        else
        {
            log.error("不支持的类型"+type);
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("不支持的类型");
            return response;        
        }         
      
        
       //查询数据库中关于手机短信的配置
        ParamConfigDto paramConfigDto = new ParamConfigDto();
        //2代表手机短信的相关参数
        paramConfigDto.setParamType(2);
        paramConfigDto.setProjId(project.getId());
        Map<String, String> params = paramConfigService.getParametersBy(paramConfigDto);
        if(params == null)
        {
            log.error("数据库中参数为空");
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("手机短信配置错误");
            return response;    
        }
                  
        //生成4位随机验证码
        String code = VerificationCodeUtil.generateMessageCode(4);
        if(StringUtils.isEmpty(code))
        {
            log.error("生成验证码失败");
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("生成验证码失败");
            return response;
        }
        
    
        
        //构建发送手机绑定的短信的配置信息
        MobileMessageSenderDto sender = mobileService.getSender(params,code, user.getMobilePhone(), MobileMessageType.valueOf(type.toUpperCase()));
        if(sender == null)
        {
            log.error("获取手机绑定配置出错");
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("获取手机绑定配置出错");
            return response;
        }
        
        log.info("手机验证码："+sender.getContent());
        
        
       //向指定手机发送短信验证码
        if(mobileService.sendVerificationCode(sender))
        {
            ImsUsersDto newImsUsers= new ImsUsersDto();
            
            newImsUsers.setId(imsUser.getId());
            newImsUsers.setMobilePhone(user.getMobilePhone());
            //更新数据库
            newImsUsers.setModifydatetime(new Date());
            newImsUsers.setIdentifyingCode(code);
            newImsUsers.setMobileState(1);
            imsUserService.updateImsUsersBy(newImsUsers);
            
            response.setData(code);
            response.setStatus(HttpDataResponse.HTTP_SUCCESS);  
            response.setMessage("验证码已发送");
            return response;
        }
        
        return response;
    }
    
     
    /**
     * 解除绑定 
     * @author 朱振	
     * @date 2015年10月30日 下午1:46:29	
     * @version 1.0
     * @param userId
     * @param code
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年10月30日 下午1:46:29</p>
     * <p>修改备注：</p>
     */
    @RequestMapping("/unBinding.do")
    @ResponseBody
    public Response<String> unBindMobilePhone(@RequestParam("userId")String userId,@RequestParam("code")String code)
    {
        Response<String> response = new Response<>();
        
        //userId
        if(StringUtils.isEmpty(userId))
        {
            log.error("参数userId不能为空");
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("userId不能为空");
            return response;
        }
        
        //code
        if(StringUtils.isEmpty(code))
        {
            log.error("验证码不能为空");
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("验证码不能为空");
            return response;
        }
        
        //验证用户是否存在
        ImsUsersDto imsUser = imsUserService.findByUserId(userId);
        if(imsUser == null)
        {
            log.error("在数据库中找不到该用户");
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("在数据库中找不到该用户");
            return response;
        }
        
        //判断是否绑定
        if(imsUser.getMobileState() != null && 2==imsUser.getMobileState())
        {
          //验证码是否相同
            if(code.equals(imsUser.getIdentifyingCode()))
            {
                ImsUsersDto param = new ImsUsersDto();
                
                param.setId(imsUser.getId());
                //更新数据库
                param.setModifydatetime(new Date());
                param.setMobileState(0);//去掉绑定
                param.setMobilePhone("");//删除手机号
                imsUserService.updateImsUsersBy(param);
                
                response.setStatus(HttpDataResponse.HTTP_SUCCESS);
                response.setMessage("解绑成功");
                return response;
            }
            else
            {
                response.setStatus(HttpDataResponse.HTTP_FAILE);
                response.setMessage("验证码错误");
                return response;
            }
        }
        
        response.setStatus(HttpDataResponse.HTTP_FAILE);
        response.setMessage("未绑定手机");
        return response;
    }
    
    
    /**
     * 验证sendCode的传入参数	 
     * @author 朱振	
     * @date 2015年10月29日 下午4:33:13	
     * @version 1.0
     * @param user
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年10月29日 下午4:33:13</p>
     * <p>修改备注：</p>
     */
    private Response<String> validateSendCodeParameters(String userId,String type,ImsUsers user)
    {
        Response<String> response = new Response<>();
        response.setStatus(HttpDataResponse.HTTP_SUCCESS);
        
        if(user == null)
        {
            log.error("参数user不能为空");
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("user不能为空");
            return response;
        }
        
        if(StringUtils.isEmpty(userId))
        {
            log.error("参数userId不能为空");
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("userId不能为空");
            return response;
        }
        
        if(StringUtils.isEmpty(type))
        {
            log.error("手机短信类型不能为空");
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("手机短信类型不能为空");
            return response;
        }
        
        
        //手机号 mobile
        Pattern pattern = Pattern.compile("(86)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}");
        if(StringUtils.isEmpty(user.getMobilePhone()))
        {
            log.error("参数：mobilePhone不能为空");
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("手机短信参数配置出错");
            return response;
        }
        else if(!pattern.matcher(user.getMobilePhone()).matches())
        {
            log.error("手机号码不合法"+user.getMobilePhone());
            response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("手机短信参数配置出错");
            return response;
        }
        
        return response;
       
    }
}

