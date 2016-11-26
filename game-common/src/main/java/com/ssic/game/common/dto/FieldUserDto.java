/**
 * 
 */
package com.ssic.game.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: FieldRoleDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年6月26日 下午2:22:11	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年6月26日 下午2:22:11</p>
 * <p>修改备注：</p>
 */
public class FieldUserDto implements Serializable
{

    /**   
    * serialVersionUID: （一句话描述这个变量表示什么）      
    */

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String fieldId;

    @Getter
    @Setter
    private String projId;

    @Getter
    @Setter
    private String userId;

    @Getter
    @Setter
    private String roleId;

    @Getter
    @Setter
    private Integer stat;

    @Getter
    @Setter
    private Date lastUpdateTime;

    @Getter
    @Setter
    private Date createTime;

    @Getter
    @Setter
    private Integer readerWrite;
}
