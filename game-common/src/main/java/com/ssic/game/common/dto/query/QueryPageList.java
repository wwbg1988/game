/**
 * 
 */
package com.ssic.game.common.dto.query;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**		
 * <p>Title: QueryPage </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author rkzhang	
 * @date 2015年7月24日 下午1:39:42	
 * @version 1.0
 * <p>修改人：rkzhang</p>
 * <p>修改时间：2015年7月24日 下午1:39:42</p>
 * <p>修改备注：</p>
 */
@ToString
public class QueryPageList {

    @Getter
    @Setter
    private String queryId;
    
    @Getter
    @Setter
    private String queryName;
    
    @Getter
    @Setter
    private String url;
    
}

