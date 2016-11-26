package com.ssic.shop.manage.dto;

import java.io.Serializable;
import java.util.List;

import com.ssic.util.UUIDGenerator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 		
 * <p>Title: FormDataResults </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年8月3日 下午8:31:14	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年8月3日 下午8:31:14</p>
 * <p>修改备注：</p>
 */

@ToString
public class FindDataResults implements Serializable
{
   
    
     /**   
     * serialVersionUID: （一句话描述这个变量表示什么）      
     */   
    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private String cafetoriumId; 
    
    @Getter
    @Setter
    private List<GoodsDto> goodslimitList;
    
    @Getter
    @Setter
    private List<GoodsDto> goodsIsTurnList;
    
    @Getter
    @Setter
    private List<GoodsDto> goodsFineList;
    
    @Getter
    @Setter
    private List<GoodsDto> goodslimitStickList;
    
    @Getter
    @Setter
    private List<GoodsDto> goodsFineStickList;
}

