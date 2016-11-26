/**
 * 
 */
package com.ssic.game.ims.model;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**		
 * <p>Title: FormDataResults </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author rkzhang	
 * @date 2015年7月24日 上午10:16:05	
 * @version 1.0
 * <p>修改人：rkzhang</p>
 * <p>修改时间：2015年7月24日 上午10:16:05</p>
 * <p>修改备注：</p>
 */
@ToString
public class FormDataResults {

    
    
    
    @Getter
    @Setter
    private List<Map<String, Object>> results;

}

