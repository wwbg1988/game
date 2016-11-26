/**
 * 
 */
package com.ssic.game.app.controller.catering;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.service.CateringUserService;
import com.ssic.util.model.Response;

/**		
 * <p>Title: AccBindController </p>
 * <p>Description: 账号绑定</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author yuanbin	
 * @date 2015年8月11日 下午7:19:06	
 * @version 1.0
 * <p>修改人：yuanbin</p>
 * <p>修改时间：2015年8月11日 下午7:19:06</p>
 * <p>修改备注：</p>
 */

@Controller
@RequestMapping("/http/api/AccBind")
public class AccBindController {
	/*
	 *1.账号绑定页面，里面有手机号绑定和微信号绑定；
	 *2.请求参数：user_id，显示手机绑定和微信绑定选项；
	 *3.插入数据：users表中一条数据：id、mobile_phone、weixin;
	 */

    @Autowired
    private CateringUserService cateringUserService;

    /**         
     * bind：账号绑定界面
     * @param userId 用户Id
     * @return  Response<ImsUsersDto> 
     * @throws Exception 
     * @exception	
     * @author yuanbin
     * @date 2015年8月11日 下午15:12:11
     */
    @RequestMapping(value = "/bind.do")
    @ResponseBody
    public Response<ImsUsersDto> login(HttpSession session, @RequestParam(required = true) String userId) throws Exception
    {
        Response<ImsUsersDto> result = new Response<ImsUsersDto>();
        
        ImsUsersDto imsUsersDto=new ImsUsersDto();
        imsUsersDto.setId(userId);
        imsUsersDto.setStat(1);
        //cateringUserService.updateUserByuserId(imsUsersDto);    
        
		return result;
    }
	

}

