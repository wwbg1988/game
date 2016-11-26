package com.ssic.game.common.util;


/**		
 * <p>Title: VerificationCodeUtil </p>
 * <p>Description: 验证码生成工具类</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振	
 * @date 2015年10月28日 上午10:28:08	
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年10月28日 上午10:28:08</p>
 * <p>修改备注：</p>
 */
public class VerificationCodeUtil
{
    /**
     * 生成除了数字的随机字符串，每个字符都可以重复
     * @author 朱振	
     * @date 2015年10月28日 上午10:32:12	
     * @version 1.0
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年10月28日 上午10:32:12</p>
     * <p>修改备注：</p>
     */
    public static final String generateMessageCode(Integer length)
    {
        String[] codes = new String[] {"0","1","2","3","4","5","6","7", 
            "8","9"}; 
       
       StringBuilder sb = new StringBuilder();
       
       for(int i=0; i<length; i++)
       {
           sb.append(codes[(int)(Math.random()*10)]);
       }
       
       return sb.toString();       
    }
}

