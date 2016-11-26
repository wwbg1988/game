/**
 * 
 */
package com.ssic.game.ims.model;

import java.util.List;

import com.ssic.game.common.dto.DictDto;
import com.ssic.game.common.pojo.FieldDict;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: Field </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author rkzhang	
 * @date 2015年6月25日 上午9:28:07	
 * @version 1.0
 * <p>修改人：rkzhang</p>
 * <p>修改时间：2015年6月25日 上午9:28:07</p>
 * <p>修改备注：</p>
 */
public class Field {

    /**   
    * paramDesc: 参数描述
    */
    @Getter
    @Setter
    private String paramDesc;

    /**   
    * paramName: 字段参数名称 与ID同名 唯一
    */
    @Getter
    @Setter
    private String paramName;

    /**   
    * fieldValue: 字段值
    */
    @Getter
    @Setter
    private String fieldValue;
    
     /**   
     * fieldDesc: 字段显示描述
     */   
    @Getter
    @Setter
    private String fieldDesc;

    
     /**   
     * pattern: 校验格式 正则校验
     */   
    @Getter
    @Setter
    private String pattern;

    
     /**   
     * isencrypt: 字段值是否加密 
     */   
    @Getter
    @Setter
    private Boolean isEncrypt;

    
     /**   
     * isuniline: 是否独占一行 
     */   
    @Getter
    @Setter
    private Boolean isUniline;

    
     /**   
     * isdiy: 是否为自定义字段 0是 1否 自定义字段可重复添加 规则为字段名+自增编号 1开始 主要针对text、checkbox、radio
     */   
    @Getter
    @Setter
    private Boolean isDiy;

    
     /**   
     * length: 长度
     */   
    @Getter
    @Setter
    private Integer length;

    
     /**   
     * height: 高度
     */   
    @Getter
    @Setter
    private Integer height;

    
     /**   
     * type: 字段类型 1:text 2:checkbox  3:hidden 4:img 5:password 6:radio 7:reset 8:file 9:label 10:select 11:textarea
     */   
    @Getter
    @Setter
    private Integer type;

    
     /**   
     * dataType: 字段数据类型 1:int 2:string 3:float 4:date
     */   
    @Getter
    @Setter
    private Integer dataType;

    
     /**   
     * required: 是否必填 
     */   
    @Getter
    @Setter
    private Boolean required;
    
    
    /**   
     * permission: 0只读    1可写
     */   
    @Getter
    @Setter
    private Integer permission;
    
     /**   
     * order: 排序   
     */   
    @Getter
    @Setter
    private Integer order;
    
    @Getter
    @Setter
    private List<DictDto> fieldsDict;
}
