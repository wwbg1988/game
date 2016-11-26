/**
 * 
 */
package com.ssic.catering.base.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: AddressUserDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年8月8日 上午11:07:00	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年8月8日 上午11:07:00</p>
 * <p>修改备注：</p>
 */
public class AddressUserDto implements Serializable
{
    
     /**   
     * serialVersionUID: （一句话描述这个变量表示什么）      
     */   
    
    private static final long serialVersionUID = -2621649407232746434L;

    @Getter
    @Setter
    private String id;

    /**
     * 区域id
     */
    @Getter
    @Setter
    private String addressId;

    /**
     * 区域类型：0:总公司,1：大区，2：省份，3：市，4：食堂
     */
    @Getter
    @Setter
    private Integer addressType;

    /**
     * 区域负责人id
     */
    @Getter
    @Setter
    private String userId;

    /**
     * 是否有效 1:是，0：否
     */
    @Getter
    @Setter
    private Integer stat;

    /**
     * 创建时间
     */
    @Getter
    @Setter
    private Date createTime;

    /**
     * 更新时间
     */
    @Getter
    @Setter
    private Date lastUpdateTime;
    
    /**
     * 本区平均分数
     */
    @Getter
    @Setter
    private String score;
    
    @Getter
    @Setter
    private String historyScore;  //历史平均分
    
    
    /**
     * 区域负责人电话
     */
    @Getter
    @Setter
    private String phone;
    
    /**
     * 区域负责人姓名
     */
    @Getter
    @Setter
    private String userName;
    
    /**
     * 区域负责人照片
     */
    @Getter
    @Setter
    private String userImg;
    
    @Getter
    @Setter
    private String sensitiveName;//预警关键字
    
    @Getter
    @Setter
    private String sensitivePer;
    
    @Getter
    @Setter
    private Float warningproportion;//关键字预警
    
    @Getter
    @Setter
    private List<SensitiveWarningReportDto> warnReportList;//关键词预警饼状图数据
    
    @Getter
    @Setter
    private String addressCafetorium;//大区、城市、
    
    @Getter
    @Setter
    private String Cafetorium;//食堂地址
    
    @Getter
    @Setter
    private String commentCount;//食堂对应的评论数量
    
    /**
     * 食堂编码
     */
    @Getter
    @Setter
    private String cafeCode;
    
    @Getter
    @Setter
    private String cafeName;
    
    @Getter
    @Setter
    private String cafePhone;
    @Getter
    @Setter
    private String projId;

}
