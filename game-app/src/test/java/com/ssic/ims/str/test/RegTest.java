/**
 * 
 */
package com.ssic.ims.str.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ssic.util.digest.MD5Coder;

/**		
 * <p>Title: RegTest </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author rkzhang	
 * @date 2015年6月24日 下午4:04:15	
 * @version 1.0
 * <p>修改人：rkzhang</p>
 * <p>修改时间：2015年6月24日 下午4:04:15</p>
 * <p>修改备注：</p>
 */
public class RegTest {

    /**     
     * main：一句话描述方法功能
     * @param args
     * @exception	
     * @author rkzhang
     * @date 2015年6月24日 下午4:04:15	 
     */
    public static void main(String[] args) {
	String str = "adfecfeabe{wrecw}dabe{wrwr}ciew ";
	    Pattern p = Pattern.compile("\\{.*?\\}");  
	    Matcher m = p.matcher(str);  
	    while(m.find()){  
		String key = m.group();
		System.out.println(key);  
		System.out.println(str.replace(key,"AAA"));
	    }  	
	    
    }

}

