package com.ssic.shop.manage.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.springframework.format.annotation.DateTimeFormat;

/**		
 * <p>Title: VisitServiceDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振	
 * @date 2015年9月15日 下午5:15:20	
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年9月15日 下午5:15:20</p>
 * <p>修改备注：</p>
 */
@ToString
public class VisitServiceDto implements Serializable
{

    /**   
     * serialVersionUID: （一句话描述这个变量表示什么）      
     */   
    
    private static final long serialVersionUID = 34334461528097294L;

    /**
     * 主键
     */
    @Getter
    @Setter    
    private String id;

    /**
     * 菜品id
     */
    @Getter
    @Setter   
    private String dinnerSeriesId;

    /**
     * 预定服务的人的姓名
     */
    @Getter
    @Setter   
    private String name;

    /**
     * 预定服务的人的手机号码
     */
    @Getter
    @Setter   
    private String phone;

    /**
     * 上门地址
     */
    @Getter
    @Setter   
    private String address;

    /**
     * 上门日期
     */
    @Getter
    @Setter   
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date serviceTime;

    /**
     * 商城用户id
     */
    @Getter
    @Setter   
    private String openId;

    /**
     * 留言
     */
    @Getter
    @Setter   
    private String message;

    /**
     * 创建日期
     */
    @Getter
    @Setter   
    private Date createTime;

    /**
     * 最后更新日期
     */
    @Getter
    @Setter   
    private Date lastUpdateTime;

    /**
     * 是否有效
     */
    @Getter
    @Setter   
    private Integer stat;
}

