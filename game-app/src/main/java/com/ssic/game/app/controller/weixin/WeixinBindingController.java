package com.ssic.game.app.controller.weixin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.game.common.HttpDataResponse;
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.service.IImsUserService;
import com.ssic.util.constants.DataStatus;
import com.ssic.util.model.Response;

/**
 * 		
 * <p>Title: WeixinBindingController </p>
 * <p>Description: 微信绑定</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 张亚伟	
 * @date 2015年8月13日 上午10:46:08	
 * @version 1.0
 * <p>修改人：张亚伟</p>
 * <p>修改时间：2015年8月13日 上午10:46:08</p>
 * <p>修改备注：</p>
 */
@RequestMapping("/weixinBinding")
@Controller
public class WeixinBindingController {

	@Autowired
	private IImsUserService iImsUserService;

	/**
	 * 
	 * binding：用户微信绑定
	 * @param userAccount 用户ID
	 * @param encryption 加密后的账号
	 * @param openId 微信身份唯一标识
	 * @return
	 * @exception	
	 * @author 张亚伟
	 * @date 2015年8月13日 上午11:31:18
	 */
	@RequestMapping("/binding")
	@ResponseBody
	public Response<String> binding(@RequestParam("userId")String userId, /*String encryption,*/
	    @RequestParam("openId")String openId, @RequestParam(value="nickName",required=false)String nickName) {
		Response<String> response = new Response<String>();

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
//		if (!userAccount.equals(mingw)) {
//			response.setStatus(DataStatus.HTTP_SUCCESS);
//			response.setMessage("账号身份验证错误");
//			return response;
//		}
		
		//openId
		if(StringUtils.isEmpty(openId))
		{
		    response.setStatus(DataStatus.HTTP_FAILE);
            response.setMessage("微信未登录");
            return response;
		}

		//userId
		if(StringUtils.isEmpty(userId))
        {
            response.setStatus(DataStatus.HTTP_FAILE);
            response.setMessage("userId不能为空");
            return response;
        }

		
		//判断此用户是否存在
		ImsUsersDto isBinding = iImsUserService.findByUserId(userId);
		if(isBinding == null) 
		{
		    response.setStatus(DataStatus.HTTP_FAILE);
            response.setMessage("该用户不存在");
            return response;
		}
		
		//判断此用户是否绑定过微信
		if(!StringUtils.isEmpty(isBinding.getWeixin()))
        {
            response.setStatus(DataStatus.HTTP_FAILE);
            response.setMessage("已绑定微信");
            return response;
        }
		
		//判断微信是否绑定过用户
		ImsUsersDto param = new ImsUsersDto();
		param.setWeixin(openId);
		List<ImsUsersDto> resultSet = iImsUserService.findBy(param);
        if (!CollectionUtils.isEmpty(resultSet)) 
        {
          if(StringUtils.isEmpty(isBinding.getWeixin()))
          {
              response.setStatus(DataStatus.HTTP_FAILE);
              response.setMessage("该微信号已绑定其他用户");
              return response;
          }
        }
        
		//进行账号绑定
		ImsUsersDto newParam = new ImsUsersDto();
		newParam.setId(isBinding.getId());
		newParam.setWeixin(openId);
		newParam.setWeixinNickName(nickName==null?"":nickName);
		Integer state = iImsUserService.updateImsUsersBy(newParam);
		if (state==0) {
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("绑定失败");
			return response;
		}

		response.setStatus(DataStatus.HTTP_SUCCESS);
		response.setMessage("绑定成功");
		return response;
	}
	
	/**
	 * 解除微信绑定	 
	 * @author 朱振	
	 * @date 2015年10月30日 下午1:10:08	
	 * @version 1.0
	 * @param userId
	 * @return
	 * <p>修改人：朱振</p>
	 * <p>修改时间：2015年10月30日 下午1:10:08</p>
	 * <p>修改备注：</p>
	 */
	@RequestMapping("/unBinding.do")
    @ResponseBody
	public Response<String> unBinding(String userId)
	{
	    Response<String> response = new Response<String>();
	    
	    //传入参数UserId
	    if(StringUtils.isEmpty(userId))
	    {
	        response.setStatus(HttpDataResponse.HTTP_FAILE);
	        response.setMessage("userId不能为空");
	        return response;
	    }
	    
	    //查询用户
	    ImsUsersDto imsUser = iImsUserService.findByUserId(userId);
	    if(imsUser == null)
	    {
	        response.setStatus(HttpDataResponse.HTTP_FAILE);
            response.setMessage("用户不存在");
            return response;
	    }
	    
	    //是否绑定微信
	    if(StringUtils.isEmpty(imsUser.getWeixin()))
	    {
	        response.setStatus(DataStatus.HTTP_FAILE);
            response.setMessage("该用户未绑定微信");
            return response;
	    }

	    //要更新的属性值
	    ImsUsersDto newParam = new ImsUsersDto();
	    newParam.setId(imsUser.getId());
	    newParam.setWeixin("");//更新为""
	    newParam.setWeixinNickName("");

	    
	    //更新的条数不为1
	    if(1!=iImsUserService.updateImsUsersBy(newParam))
	    {
	        response.setStatus(DataStatus.HTTP_FAILE);
	        response.setMessage("解除绑定失败");
	        return response;
	    }
	    
        response.setStatus(DataStatus.HTTP_SUCCESS);
        response.setMessage("解除绑定成功");
        return response;
	}

//	public static void main(String[] args) throws Exception {
//		HashMap<String, Object> map = RSACoder.getKeys();
//		//生成公钥和私钥  
//		RSAPublicKey publicKey = (RSAPublicKey) map.get("public");
//		RSAPrivateKey privateKey = (RSAPrivateKey) map.get("private");
//
//		//模  
//		//String modulus = publicKey.getModulus().toString();
//		String modulus = "101641832079052148259447072365740970613110438562828109384784448311695427155331840365585680593629613248220078332358209680468559921800724091342700460252145879024235907441039499610985675958337586071063455425969920685799619151392125907442289550229634700534982311434473832601874446533281515268961324368009017979231";
//		//公钥指数  
//		//String public_exponent = publicKey.getPublicExponent().toString();
//		String public_exponent = "65537";
//		//私钥指数  
//		//String private_exponent = privateKey.getPrivateExponent().toString();
//		String private_exponent = "22320662332449128244349943779662542518941749420880146333610293118420443224736192479691000733990225275315979787895377477170201785168012285008531745791673149500124578932659388841718673822214939099103435108096308945629919485239435284611004795900861428503917193631550103994896222577865650354703177144351314796673";
//
//		System.out.println("modulus=" + modulus);
//		System.out.println("public_exponent=" + public_exponent);
//		System.out.println("private_exponent=" + private_exponent);
//
//		//明文  
//		String ming = "Oscar";
//		//使用模和指数生成公钥和私钥  
//		RSAPublicKey pubKey = RSACoder.getPublicKey(modulus, public_exponent);
//		RSAPrivateKey priKey = RSACoder
//				.getPrivateKey(modulus, private_exponent);
//		//加密后的密文  
//		String mi = RSACoder.encryptByPublicKey(ming, pubKey);
//		System.err.println("公钥加密密文 : " + mi);
//		//解密后的明文  
//		String mingw = RSACoder.decryptByPrivateKey("dkSwkGGYY3aHmbo-WFnEz6pRG65reHpqMgdJjEoDoUoydkwNol5ITEK1q_W_yteyz6q5bBfgm9n5pE0un5Jjv0P9fwVMs0RiBVejdXuoj0U-JY7cYAR70oCeXHpPo6Gxy7GSnl4xJVEqSCZXjSOhVGoQ78q5MgaboSkpKAM0Cy8", priKey);
//		System.err.println("私钥解密明文: " + mingw);
//	}
}
