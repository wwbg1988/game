package com.ssic.game.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**		
 * <p>Title: SerialNumberUtils </p>
 * <p>Description: 订单编号生成</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author yuanbin	
 * @date 2015年8月28日 下午12:28:49	
 * @version 1.0
 * <p>修改人：yuanbin</p>
 * <p>修改时间：2015年8月28日 下午12:28:49</p>
 * <p>修改备注：</p>
 */
public class SerialNumberUtils {	
    private static long orderNum = 0l;
    private static String date ;
    
    public static void main(String[] args) throws InterruptedException {
    	for (int i = 0; i < 10000; i++) {
			System.out.println(SerialNumberUtils.getOrderNo());
			Thread.sleep(1000);
		}
    }


    /**
     * 生成订单编号
     * @return
     */
    public static synchronized String getOrderNo() {
        String str = new SimpleDateFormat("yyMMddHHmm").format(new Date());
        if(date==null||!date.equals(str)){
        	date = str;
        	orderNum  = 0l;
        }
        orderNum ++;
        long orderNo = Long.parseLong((date)) * 10000;
        orderNo += orderNum;;
        return orderNo+"";
    }

	}  
	  
