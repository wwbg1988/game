/**
 * 
 */
package com.ssic.game.common.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.ssic.game.common.pojo.ImsUsers;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**		
 * <p>Title: DeptDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年7月11日 上午9:30:33	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年7月11日 上午9:30:33</p>
 * <p>修改备注：</p>
 */
@ToString
public class DeptDto implements Serializable
{
    
     /**   
     * serialVersionUID: （一句话描述这个变量表示什么）      
     */   
    
    private static final long serialVersionUID = 5900137867984199732L;

    /**   
    * serialVersionUID: （一句话描述这个变量表示什么）      
    */

  

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private Date createTime;

    @Getter
    @Setter
    private Date lastUpdateTime;

    @Getter
    @Setter
    private int deptAdmin;

    @Getter
    @Setter
    private String deptLevel;

    @Getter
    @Setter
    private String levelName;

    @Getter
    @Setter
    private String deptName;

    @Getter
    @Setter
    private String projId;

    @Getter
    @Setter
    private String projName;

    @Getter
    @Setter
    private int stat;

    @Getter
    @Setter
    private String searchName;
    
    @Getter
    @Setter
    private String deptCode;
    
    @Getter
    @Setter
    private String pid;
    
    @Getter
    @Setter
    private String pname;
    
    @Getter
    @Setter
    private String iconCls;
    
    @Getter
    @Setter
    private String projIds;
    @Getter
    @Setter
    private int isExistManager;   // 该部门是否存在部门长
    
    @Getter
    @Setter
    private List<ImsUsersDto> users;    //该部门下存在的用户
}

