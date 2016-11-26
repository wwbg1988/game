/**
 * 
 */
package com.ssic.game.common.dto.query;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**		
 * <p>Title: UserAccountQuery </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author rkzhang	
 * @date 2015年6月30日 下午1:50:46	
 * @version 1.0
 * <p>修改人：rkzhang</p>
 * <p>修改时间：2015年6月30日 下午1:50:46</p>
 * <p>修改备注：</p>
 */
@ToString
public class UserAccountQuery {

    @Getter
    @Setter
    private String projectId;
    
    @Getter
    @Setter
    private String account;
    
}

