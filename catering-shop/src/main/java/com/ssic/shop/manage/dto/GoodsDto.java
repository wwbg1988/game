/**
 * 
 */
package com.ssic.shop.manage.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**     
 * <p>Title: GoodsDto </p>
 * <p>Description: 商品Dto</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博
 * @date 2015年8月27日 上午9:42:45   
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年8月27日 上午9:42:45</p>
 * <p>修改备注：</p>
 */
public class GoodsDto implements Serializable
{

    
     /**   
     * serialVersionUID: （一句话描述这个变量表示什么）      
     */   
    
    private static final long serialVersionUID = 543552978908081544L;

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String goodsName;

    @Getter
    @Setter
    private Double price;

    @Getter
    @Setter
    private Double salesPrice;

    @Getter
    @Setter
    private String icon;

    @Getter
    @Setter
    private String detailIcon;

    @Getter
    @Setter
    private String turnIcon;

    @Getter
    @Setter
    private String introduce;

    @Getter
    @Setter
    private Integer isTurn;

    @Getter
    @Setter
    private Integer countLimit;

    @Getter
    @Setter
    private Integer percent;

    @Getter
    @Setter
    private String goodsTypeId;

    @Getter
    @Setter
    private String goodsTypeName;

    @Getter
    @Setter
    private String cafetoriumId;

    @Getter
    @Setter
    private List<String> cafetoriumIds;

    @Getter
    @Setter
    private String cafeName;

    @Getter
    @Setter
    private Integer goodsStocks;

    @Getter
    @Setter
    private Date createTime;

    @Getter
    @Setter
    private Date lastUpdateTime;

    @Getter
    @Setter
    private Integer stat;

    @Getter
    @Setter
    private Integer isStick;

    @Getter
    @Setter
    private String importdomestic;

    @Getter
    @Setter
    private String placeorigin;

    @Getter
    @Setter
    private String distributionmode;

    @Getter
    @Setter
    private String unitsize;

    @Getter
    @Setter
    private String productiondate;

    @Getter
    @Setter
    private String shelflife;

    @Getter
    @Setter
    private String purchase;

    @Getter
    @Setter
    private String detailsimgsrc;

    @Getter
    @Setter
    private int monthlysales;
    
    /**
     * 详情的轮播图片
     */
    @Getter
    @Setter
    private String turnDetailImg;

}
