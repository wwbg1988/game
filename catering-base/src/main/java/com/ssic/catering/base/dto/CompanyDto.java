/**
 * 
 */
package com.ssic.catering.base.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: CompanyDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年8月6日 下午5:16:25	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年8月6日 下午5:16:25</p>
 * <p>修改备注：</p>
 */
public class CompanyDto  implements Serializable
{ 
    
     /**   
     * serialVersionUID: （一句话描述这个变量表示什么）      
     */   
    
    private static final long serialVersionUID = -692582214218834628L;

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String companyName;

    @Getter
    @Setter
    private String mobileNo;

    @Getter
    @Setter
    private String email;
    
    @Getter
    @Setter
    private String projName;
    
    @Getter
    @Setter
    private String projId;
    
    @Getter
    @Setter
    private Integer flag;

    @Getter
    @Setter
    private Integer stat;

    @Getter
    @Setter
    private Date lastUpdateTime;

    @Getter
    @Setter
    private Date createTime;
}

