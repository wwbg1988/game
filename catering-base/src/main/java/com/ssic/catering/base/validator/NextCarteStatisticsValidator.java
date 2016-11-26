/**
 * 
 */
package com.ssic.catering.base.validator;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssic.catering.common.util.AppResponse;
import com.ssic.util.model.RequestResult;

/**		
 * <p>Title: NextCarteStatisticsValidator </p>
 * <p>Description: 下周菜单验证器</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年8月8日 上午11:41:51	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年8月8日 上午11:41:51</p>
 * <p>修改备注：</p>
 */
@Service
public class NextCarteStatisticsValidator
{
    public RequestResult validate(String userId)
    {
        if (StringUtils.isEmpty(userId))
        {
            return new RequestResult(false, "用户id为空");
        }

        return new RequestResult(true, "用户id验证通过");
    }
}
