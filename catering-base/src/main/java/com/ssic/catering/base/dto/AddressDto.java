/**
 * 
 */
package com.ssic.catering.base.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: AddressDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年8月7日 上午11:30:00	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年8月7日 上午11:30:00</p>
 * <p>修改备注：</p>
 */
public class AddressDto implements Serializable
{

    private static final long serialVersionUID = -5569907429916128428L;

    @Getter
    @Setter
    private String id;

    /**
     * 区域名称
     */
    @Getter
    @Setter
    private String addressName;

    /**
     * 区域编码
     */
    @Getter
    @Setter
    private String addressCode;

    /**
     * 区域父id
     */
    @Getter
    @Setter
    private String parentId;

    /**
     * 是否有效 1:有效，0：无效
     */
    @Getter
    @Setter
    private Integer stat;

    /**
     * 更新时间
     */
    @Getter
    @Setter
    private Date lastUpdateTime;

    /**
     * 创建时间
     */
    @Getter
    @Setter
    private Date createTime;

    @Getter
    @Setter
    private String pid;

    @Getter
    @Setter
    private String pname;

    @Getter
    @Setter
    private String iconCls;

    /**
     * 是否是叶子节点 ：1是；0：否;
     */
    @Getter
    @Setter
    private Integer isLeaf;
    /**
     * 区域负责人名称
     */
    @Getter
    @Setter
    private String userName;
    /**
     * 区域负责人id
     */
    @Getter
    @Setter
    private String userId;

    /**
     * 项目id
     */
    @Getter
    @Setter
    private String projId;
    /**
     * 所属项目
     */
    @Getter
    @Setter
    private String projName;
    /**
     * 区域负责人
     */
    @Getter
    @Setter
    private AddressUserDto addressUserDto;

}
