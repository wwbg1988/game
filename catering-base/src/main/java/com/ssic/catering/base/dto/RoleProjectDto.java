/**
 * 
 */
package com.ssic.catering.base.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: RoleProjectDto </p>
 * <p>Description: 项目角色关系dto</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年10月26日 下午1:47:48	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年10月26日 下午1:47:48</p>
 * <p>修改备注：</p>
 */
public class RoleProjectDto implements Serializable
{

    /**   
    * serialVersionUID: （一句话描述这个变量表示什么）      
    */

    private static final long serialVersionUID = -2287081725629652501L;

    @Getter
    @Setter
    private String id;

    /**
     *角色id
     */
    @Getter
    @Setter
    private String roleId;

    /**
     *角色名称
     */
    @Getter
    @Setter
    private String roleName;

    /**
     *角色备注
     */
    @Getter
    @Setter
    private String roleMark;

    /**
     *项目id
     */
    @Getter
    @Setter
    private String projId;

    /**
     *创建时间
     */
    @Getter
    @Setter
    private Date createTime;

    /**
     *更新时间
     */
    @Getter
    @Setter
    private Date lastUpdateTime;

    /**
     *状态，是否有效： 1是；0:否;
     */
    @Getter
    @Setter
    private Integer stat;

}
