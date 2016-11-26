/**
 * 
 */
package com.ssic.game.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**		
 * <p>Title: SignDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年8月10日 下午1:18:43	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年8月10日 下午1:18:43</p>
 * <p>修改备注：</p>
 */
@ToString
public class SignDto implements Serializable
{
    
     /**   
     * serialVersionUID: （一句话描述这个变量表示什么）      
     */   
    
    private static final long serialVersionUID = -8803689372272646559L;

    @Getter
    @Setter
    private String id;
    
    @Getter
    @Setter
    private String userId;
    
    @Getter
    @Setter
    private String xPosition;
    
    @Getter
    @Setter
    private String yPosition;
    
    @Getter
    @Setter
    private String address;
    
    @Getter
    @Setter
    private String projectid;
    
    @Getter
    @Setter
    private String projectName;
    
    @Getter
    @Setter
    private String deptid;
    
    @Getter
    @Setter
    private Integer signType;    //1 考勤  2签到
    
    @Getter
    @Setter
    private String positionName;
    
    @Getter
    @Setter
    private String reason;
    
    @Getter
    @Setter
    private String pic;
    
    @Getter
    @Setter
    private Integer stat;
    
    @Getter
    @Setter
    private Date createTime;
    
    @Getter
    @Setter
    private Date lastUpdateTime;
    
    /**
     * 是否是部门长
     */
    @Getter
    @Setter
    private Integer isDept ;
    
    @Getter
    @Setter
    private String userName;
    @Getter
    @Setter
    private String deptName;
    
    /**
     * 时间字符串
     */
    @Getter
    @Setter
    private String timeStr;
    @Getter
    @Setter
    private String userAccount;
    @Getter
    @Setter
    private Integer isworktime;     // 0上班   1下班
    @Getter
    @Setter
    private Integer isCheck;      // 0 迟到   1 早退   2正常
}

