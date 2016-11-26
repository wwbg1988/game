/**
 * 
 */
package com.ssic.catering.base.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**		
 * <p>Title: CafetoriumDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博
 * @date 2015年8月6日 下午3:45:40	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年8月6日 下午3:45:40</p>
 * <p>修改备注：</p>
 */
@ToString
public class CafetoriumDto implements Serializable
{

    /**   
    * serialVersionUID: （一句话描述这个变量表示什么）      
    */

    private static final long serialVersionUID = -1649121514854715173L;
    /**
     * 食堂ID
     */
    @Getter
    @Setter
    private String id;
    /**
     * 食堂名称
     */
    @Getter
    @Setter
    private String cafeName;
    /**
     * 食堂编码
     */
    @Getter
    @Setter
    private String cafeCode;
    /**
     * 食堂联系电话
     */
    @Getter
    @Setter
    private String mobileNo;
    /**
     * 公司id
     */
    @Getter
    @Setter
    private String companyId;
    /**
     * 公司名称
     */
    @Getter
    @Setter
    private String companyName;
    /**
     * 分包商id
     */
    @Getter
    @Setter
    private String forkId;
    /**
     * 食堂地址
     */
    @Getter
    @Setter
    private String address;
    /**
     * 食堂邮箱
     */
    @Getter
    @Setter
    private String email;
    /**
     * 是否有效:1有效,0：无效
     */
    @Getter
    @Setter
    private Boolean stat;
    /**
     * 最后更新时间
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
    /**
     * 食堂所属区域id
     */
    @Getter
    @Setter
    private String addressId;
    /**
     * 用户id
     */
    @Getter
    @Setter
    private String userId;
    /**
     * 食堂负责人
     */
    @Getter
    @Setter
    private String userName;

    /**
     * 负责人头像
     */
    @Getter
    @Setter
    private String userImg;
    /**
    * 区域名称
    */
    @Getter
    @Setter
    private String addressName;

    /**
     * 食堂得分
     */
    @Getter
    @Setter
    private String score;

    /**
     * 食堂分级地址(示例:华中大区-河南省-郑州市)
     */
    @Getter
    @Setter
    private String levelAddress;

    /**
     * 食堂所属项目id
     */
    @Getter
    @Setter
    private String projId;
    /**
     * 食堂名称
     */
    @Getter
    @Setter
    private String projName;
    /**
     * 创建用户id
     */
    @Getter
    @Setter
    private String createUserId;
    @Getter
    @Setter
    private String historyScore;
    
    /**
     * 是否是用户默认的餐厅，参考WeixnUserCafetoriumDto
     */
    @Getter
    @Setter
    private Integer isDefault;
}
