/**
 * 
 */
package com.ssic.shop.manage.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: PageHelperDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博
 * @date 2015年8月27日 上午10:35:56	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年8月27日 上午10:35:56</p>
 * <p>修改备注：</p>
 */
public class PageHelperDto implements Serializable
{
    @Getter
    @Setter
    private int page;// 当前页
    @Getter
    @Setter
    private int rows;// 每页显示记录数
    @Getter
    @Setter
    private String sort;// 排序字段
    @Getter
    @Setter
    private String order;// asc/desc
    @Getter
    @Setter
    private Integer beginRow;
}
