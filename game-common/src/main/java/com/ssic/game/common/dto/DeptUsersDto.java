/**
 * 
 */
package com.ssic.game.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: DeptUsersDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年7月9日 下午5:54:57	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年7月9日 下午5:54:57</p>
 * <p>修改备注：</p>
 */
public class DeptUsersDto implements Serializable
{
    
     /**   
     * serialVersionUID: （一句话描述这个变量表示什么）      
     */   
    
    private static final long serialVersionUID = -5420428345732832860L;
    @Getter
    @Setter
    private String id;
    @Getter
    @Setter
    private String projId;

    @Getter
    @Setter
    private String deptId;

    @Getter
    @Setter
    private String userId;

    @Getter
    @Setter
    private int stat;

    @Getter
    @Setter
    private Date lastUpdateTime;

    @Getter
    @Setter
    private Date createTime;
    
    @Getter
    @Setter
    private String userName;
    @Getter
    @Setter
    private String deptName;
    @Getter
    @Setter
    private String projName;
    @Getter
    @Setter
    private String isAdmin;
}
