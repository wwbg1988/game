/**
 * 
 */
package com.ssic.game.ims.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**		
 * <p>Title: Action </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author rkzhang	
 * @date 2015年6月25日 上午10:08:51	
 * @version 1.0
 * <p>修改人：rkzhang</p>
 * <p>修改时间：2015年6月25日 上午10:08:51</p>
 * <p>修改备注：</p>
 */
@ToString
public class Action {

    
     /**   
     * id: （一句话描述这个变量表示什么）      
     */   
    @Getter
    @Setter
    private String id;

    
     /**   
     * name: 任务节点动作名称
     */   
    @Getter
    @Setter
    private String name;

    
     /**   
     * type: 动作类型
     */   
    @Getter
    @Setter
    private Integer type;

    
     /**   
     * actionUrl: 自定义动作url
     */   
    @Getter
    @Setter
    private String actionUrl;
}

