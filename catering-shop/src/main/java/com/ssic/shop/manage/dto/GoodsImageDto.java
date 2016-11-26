package com.ssic.shop.manage.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
/**
 * 		
 * <p>Title: GoodsImageDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年9月21日 上午11:47:17	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年9月21日 上午11:47:17</p>
 * <p>修改备注：</p>
 */
public class GoodsImageDto implements Serializable
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
    private String goodsId;

    @Getter
    @Setter
    private String imageId;

    @Getter
    @Setter
    private Date createTime;

    @Getter
    @Setter
    private Date lastUpdateTime;

    @Getter
    @Setter
    private Integer stat;

}

