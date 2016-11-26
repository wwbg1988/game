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
 * <p>
 * Title: CarteDto
 * </p>
 * <p>
 * Description: 菜单DTO
 * </p>
 * <p>
 * Copyright (c) 2015
 * </p>
 * <p>
 * Company: 上海天坊信息科技有限公司
 * </p>
 * 
 * @author 刘博
 * @date 2015年8月4日 上午9:22:12
 * @version 1.0
 *          <p>
 *          修改人：刘博
 *          </p>
 *          <p>
 *          修改时间：2015年8月4日 上午9:22:12
 *          </p>
 *          <p>
 *          修改备注：
 *          </p>
 */
@ToString
public class CarteDto implements Serializable
{

    /**
     * serialVersionUID: （一句话描述这个变量表示什么）
     */

    private static final long serialVersionUID = -1319571331645431323L;

    @Getter
    @Setter
    private String id;

    /**
     * 菜单类型ID
     */
    @Getter
    @Setter
    private String carteTypeId;

    /**
     * 菜单类型名称
     */
    @Getter
    @Setter
    private String carteTypeName;

    /**
     * 菜品名称
     */
    @Getter
    @Setter
    private String carteName;

    /**
     * 是否有效
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

    /**
     * 菜单所属周期(如:第一周，第二周，第三周,第四周)
     */
    @Getter
    @Setter
    private Integer carteWeek;

    /**
     * 开始时间
     */
    @Getter
    @Setter
    private Date startTime;

    /**
     * 结束时间
     */
    @Getter
    @Setter
    private Date endTime;

    /**
     * 菜品所属食堂ID
     */
    @Getter
    @Setter
    private String cafetoriumId;

    /**
     * 后台导入excel提示信息
     */
    @Getter
    @Setter
    private String carteMessage;

    /**
     * 后台导入excel是否成功 1是，0否
     */
    @Getter
    @Setter
    private Integer canImport;

    @Getter
    @Setter
    private String img; //图片地址

    /**
     * 热量
     */
    @Getter
    @Setter
    private Integer calorie;
    /**
     * 营养
     */
    @Getter
    @Setter
    private Integer sustenance;
    
    /**
     * 数量
     */
    @Getter
    @Setter
    private Integer count;
    
    /**
     * 菜品计量单位：克
     */
    @Getter
    @Setter
    private String grams;
    
    /**
     * 菜品描述
     */
    @Getter
    @Setter
    private String carteDescribe;
    
    /**
     * 菜品投票数量
     */
    @Getter
    @Setter
    private Integer vote;
    
    
    /**
     * 是否为新品
     */
    @Getter
    @Setter
    private Integer isNew;
}
