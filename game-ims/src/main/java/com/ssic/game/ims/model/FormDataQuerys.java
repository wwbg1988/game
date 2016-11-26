/**
 * 
 */
package com.ssic.game.ims.model;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: FormDataQuery </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author rkzhang	
 * @date 2015年7月24日 上午10:14:30	
 * @version 1.0
 * <p>修改人：rkzhang</p>
 * <p>修改时间：2015年7月24日 上午10:14:30</p>
 * <p>修改备注：</p>
 */
public class FormDataQuerys {
    
    
    
     /**   
     * queryId: 对应t_ims_query表中的id值
     */   
    @Getter
    @Setter
    public String queryId;

    
     /**   
     * fields: 查询条件的属性值
     */   
    @Getter
    @Setter
    public Map<String, Object> fields;
}

