/**
 * 
 */
package com.ssic.game.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: DictDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年6月25日 下午2:34:59	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年6月25日 下午2:34:59</p>
 * <p>修改备注：</p>
 */
public class DictDto implements Serializable
{

    
     /**   
     * serialVersionUID: （一句话描述这个变量表示什么）      
     */   
    
    private static final long serialVersionUID = -932610537927597617L;

    //主键ID
    @Getter
    @Setter
    private String id;

    //所属项目编号
    @Getter
    @Setter
    private String projId;

    //字段值
    @Getter
    @Setter
    private String fieldValue;

    //字段显示描述
    @Getter
    @Setter
    private String fieldDesc;

    //0:无效 1：有效
    @Getter
    @Setter
    private Integer stat;

    //修改日期
    @Getter
    @Setter
    private Date lastUpdateTime;

    //添加日期
    @Getter
    @Setter
    private Date createTime;
    
    @Getter
    @Setter
    private String projName;

}

