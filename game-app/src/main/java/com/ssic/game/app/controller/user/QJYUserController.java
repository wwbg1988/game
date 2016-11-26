package com.ssic.game.app.controller.user;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.game.app.controller.dto.QJYSendFriDto;
import com.ssic.game.app.listener.InitApplicationContext;
import com.ssic.game.app.service.IQJYUserService;
import com.ssic.game.common.util.HttpDataResponse;
import com.ssic.game.im.dto.QjyImUserDto;
import com.ssic.game.im.service.IQjyImService;
import com.ssic.game.ims.eventBus.EventRegister;
import com.ssic.util.model.Response;


@Controller
@RequestMapping("/http/api/user")
public class QJYUserController {

	@Autowired
	private IQJYUserService qJYUserService;
	@Autowired
	private IQjyImService iQjyImService;
	@Autowired
	private EventRegister eventRegister;
	
	 protected static final Log logger = LogFactory.getLog(InitApplicationContext.class);
	 
	 @RequestMapping(value = "/findFriends.do", method = {RequestMethod.GET, RequestMethod.POST })
	    @ResponseBody
	    public Response<List> findFriends(HttpSession session,@RequestParam(required = false) String userId)
	    {
		
		 Response<List> result = new Response<List>();
		 
          if(userId==null || userId.equals("")){
        	  result.setStatus(HttpDataResponse.NOT_FOUND_USER);
        	  result.setMessage("用户ID不能为空");
        	  return result;
		 }
		 
          QJYSendFriDto qJYSendFriDto = new QJYSendFriDto();
          qJYSendFriDto.setUserId(userId);
		 List<QJYSendFriDto> list = qJYUserService.findFriends(qJYSendFriDto);
		 
		 if(list!=null && list.size()>0){
			 result.setData(list);
			 result.setStatus(HttpDataResponse.HTTP_SUCCESS);
			 result.setMessage("返回亲加云好友列表成功");
			 return result;
		   }else{
			 result.setStatus(HttpDataResponse.NOT_FOUND_USER);
			 result.setMessage("亲加云好友不存在");
			 return result;
		   }
	    }

	 @RequestMapping(value = "/sendMessage.do", method = {RequestMethod.GET, RequestMethod.POST })
	 @ResponseBody
	 public Response<String> sendMessage(HttpSession session,@RequestParam(required = false) QjyImUserDto qjyImUserDto) throws UnsupportedEncodingException{
		 
		
		 
		  qjyImUserDto = new QjyImUserDto();
			 List<String> toIdList = new ArrayList<String>();
			// toIdList.add("asdf");
			 toIdList.add("qjy_wuw");
			// toIdList.add("qjy_zhangf");
			 qjyImUserDto.setToIdList(toIdList);
			 //  qjyImUserDto.setText("您在0724-3的赛事执行方还有未办理的事项，请尽快办理");
		 
			    String text= new String("您在0724-3的赛事执行方还有未办理的事项，请尽快办理".getBytes(),"UTF-8");
			    System.out.println("text====-="+text);
			   qjyImUserDto.setText(text);
			   
			   
			   eventRegister.getQjyEvent().post(qjyImUserDto);
			 
		 Response<String> result = new Response<String>();
		 if(qjyImUserDto==null){
			 result.setStatus(HttpDataResponse.QJYUserDto_NOT_FOUND);
			 result.setMessage("亲加云用户信息不存在");
			 return result;
		 }
		 

//		 Map<String, String> messageResult= iQjyImService.sendMessage(qjyImUserDto);
//		 String error_code = messageResult.get("errcode");
//		    if("true".equals(messageResult.get("Success"))){
//		    	result.setStatus(HttpDataResponse.SENDMESSAGE_SUCCESS);
//		    	result.setMessage("亲加云发送信息成功");
//		    	return result;
//		    }else{
//		    	result.setMessage("亲加云发送信息失败");
//		    	result.setStatus(HttpDataResponse.SENDMESSAGE_FAIL);
//		    	return result;
//		    }
	
	   return null;

	 }
	
}
