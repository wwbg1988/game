package com.ssic.game.app.controller.weixin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.game.common.HttpDataResponse;
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.game.common.service.IImsUserService;
import com.ssic.game.common.service.IProjectUsersService;
import com.ssic.util.model.Response;

/**
 * 		
 * <p>Title: WeixinLogin </p>
 * <p>Description: 微信登陆</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 张亚伟	
 * @date 2015年8月21日 上午10:34:47	
 * @version 1.0
 * <p>修改人：张亚伟</p>
 * <p>修改时间：2015年8月21日 上午10:34:47</p>
 * <p>修改备注：</p>
 */
@RequestMapping("/weixinLogin")
@Controller
public class WeixinLogin {

	@Autowired
	private IImsUserService iImsUserService;
	
	@Autowired
	private IProjectUsersService projectUsersService;

	/**
	 * 
	 * login：通过用户绑定的微信唯一标识进行登录
	 * @param openId 微信用户唯一标识
	 * @param encryption 微信标识加密进行连接身份验证
	 * @return
	 * @exception	
	 * @author 张亚伟
	 * @date 2015年8月21日 下午3:55:10
	 */
	@RequestMapping("/login")
	@ResponseBody
	public Response<ImsUsersDto> login(String openId/*, String encryption*/) {
		Response<ImsUsersDto> response = new Response<>();
//		//加密模块
//		String modulus = "101641832079052148259447072365740970613110438562828109384784448311695427155331840365585680593629613248220078332358209680468559921800724091342700460252145879024235907441039499610985675958337586071063455425969920685799619151392125907442289550229634700534982311434473832601874446533281515268961324368009017979231";
//
//		//公钥
//		String public_exponent = "65537";
//
//		//私钥
//		String private_exponent = "22320662332449128244349943779662542518941749420880146333610293118420443224736192479691000733990225275315979787895377477170201785168012285008531745791673149500124578932659388841718673822214939099103435108096308945629919485239435284611004795900861428503917193631550103994896222577865650354703177144351314796673";
//
//		//使用模和指数生成公钥和私钥  
//		RSAPrivateKey priKey = RSACoder
//				.getPrivateKey(modulus, private_exponent);
//
//		//解密加密信息进行账号身份验证
//		String mingw = "";
//		try {
//			mingw = RSACoder.decryptByPrivateKey(encryption, priKey);//获取解密后的文字
//		} catch (Exception e) {
//			response.setStatus(DataStatus.HTTP_FAILE);
//			response.setMessage("用户非法访问");
//			return response;
//		}
//		if (!openId.equals(mingw)) {
//			response.setStatus(DataStatus.HTTP_SUCCESS);
//			response.setMessage("账号身份验证错误");
//			return response;
//		}

		//通过微信用户唯一标识获取用户信息
//		ImsUsers imsUsers = iImsUserService.findImsUsersToWeixin(openId);
		
		
		if(StringUtils.isEmpty(openId))
        {
            response.setStatus(500);
            response.setMessage("微信未登录");
            return response;
        }
        
		
		ImsUsersDto param = new ImsUsersDto();
		param.setWeixin(openId);
		List<ImsUsersDto> datas =  iImsUserService.findBy(param);
		if(CollectionUtils.isEmpty(datas))
		{
		    response.setStatus(500);
            response.setMessage("请先绑定账号");
            return response;
		}
		else if(datas.size() != 1)
		{
		    response.setStatus(500);
            response.setMessage("此微信已绑定多个用户");
            return response;
		}
		
		//取第一个结果
		ImsUsersDto data  = datas.get(0);
		
		//查询项目信息
		ProjectDto project = projectUsersService.getProjectByIMSUserId(data.getId());
		if(project == null)
		{
		    response.setStatus(500);
            response.setMessage("该账号未分配到任何项目");
            return response;
		}
		
		//更新data
		data.setProjId(project.getId());
		data.setProjName(project.getProjName());
		
		
		response.setStatus(HttpDataResponse.HTTP_SUCCESS);
		response.setMessage("登录成功");
		response.setData(data);
		return response;
	}
}
