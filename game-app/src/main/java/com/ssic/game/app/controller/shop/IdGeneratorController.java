package com.ssic.game.app.controller.shop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.game.common.util.HttpDataResponse;
import com.ssic.util.StringUtils;
import com.ssic.util.UUIDGenerator;
import com.ssic.util.model.Response;

/**		
 * <p>Title: IdGeneratorController </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振	
 * @date 2015年9月22日 下午3:41:39	
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年9月22日 下午3:41:39</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping("/http/api/carte/")
public class IdGeneratorController
{
    /**
     * 生成id
     * @author 朱振	
     * @date 2015年9月22日 下午3:46:41	
     * @version 1.0
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年9月22日 下午3:46:41</p>
     * <p>修改备注：</p>
     */
    @ResponseBody
    @RequestMapping("/getUUID.do")
    public Response<String> getUUID()
    {
        Response<String> result = new Response<>();
        
        String data = UUIDGenerator.getUUID();
        if(!StringUtils.isEmpty(data))
        {
            result.setStatus(HttpDataResponse.HTTP_SUCCESS);
            result.setData(data);
            
            return result;
        }
        
        
        result.setStatus(HttpDataResponse.HTTP_FAILE);
        result.setData(data);
        
        return result;
    }
    
    /**
     * 	 
     * @author 朱振	
     * @date 2015年9月22日 下午3:46:45	
     * @version 1.0
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年9月22日 下午3:46:45</p>
     * <p>修改备注：</p>
     */
    @ResponseBody
    @RequestMapping("/getUUID32Bit.do")
    public Response<String> getUUID32Bit()
    {
        Response<String> result = new Response<>();
        
        String data = UUIDGenerator.getUUID32Bit();
        if(!StringUtils.isEmpty(data))
        {
            result.setStatus(HttpDataResponse.HTTP_SUCCESS);
            result.setData(data);
            
            return result;
        }
        
        
        result.setStatus(HttpDataResponse.HTTP_FAILE);
        result.setData(data);
        
        return result;
    }
    
}

