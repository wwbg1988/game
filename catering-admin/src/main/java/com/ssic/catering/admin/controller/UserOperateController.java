
package com.ssic.catering.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.util.model.Response;

/**		
 * <p>Title: UserOperateController </p>
 * <p>Description: 用户操作类</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Vincent	
 * @date 2015年6月17日 上午10:19:51	
 * @version 1.0
 */

@Controller
@RequestMapping(value="/user/operate" )
public class UserOperateController
{
    
    @ResponseBody
    @RequestMapping(value="/addUser.do")
    public Response<String> addUserJoinTeam(@RequestParam("userId")String userId, @RequestParam("teamId")String teamId) {
        
        return null;// teamMemberService.addUserJoinTeam(userId, teamId);
    }
    
}

