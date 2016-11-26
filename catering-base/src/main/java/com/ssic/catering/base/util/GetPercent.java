/**
 * 
 */
package com.ssic.catering.base.util;

import java.text.DecimalFormat;

/**		
 * <p>Title: GetPercent </p>
 * <p>Description: 百分比计算工具</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author yuanbin	
 * @date 2015年8月6日 下午4:30:34	
 * @version 1.0
 * <p>修改人：yuanbin</p>
 * <p>修改时间：2015年8月6日 下午4:30:34</p>
 * <p>修改备注：</p>
 */
public class GetPercent {
	public static String getPercent(int x,int total){  
		   String result="";//接受百分比的值  
		   double x_double=x*1.0;  
		   double tempresult=x/total;  
		   //NumberFormat nf   =   NumberFormat.getPercentInstance();     注释掉的也是一种方法  
		   //nf.setMinimumFractionDigits( 2 );        保留到小数点后几位  
		   DecimalFormat df1 = new DecimalFormat("0.00%");    //##.00%   百分比格式，后面不足2位的用0补齐  
		   //result=nf.format(tempresult);     
		   result= df1.format(tempresult);    
		   return result;  
		}  
}

