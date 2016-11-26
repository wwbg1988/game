/**
 * 
 */
package com.ssic.game.ims.model;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: LoadProcess </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author milkteaye	
 * @date 2015年7月7日 下午3:36:26	
 * @version 1.0
 * <p>修改人：milkteaye</p>
 * <p>修改时间：2015年7月7日 下午3:36:26</p>
 * <p>修改备注：</p>
 */
public class LoadProcess
{
    @Getter
    @Setter
    private String userName;
    
    @Getter
    @Setter
    private String procName;
    
    @Getter
    @Setter
    private String projId;
    
    @Getter
    @Setter
    private String procId;
    
    //下一步动作的url地址
    @Getter
    @Setter
    private String actionUrl;
}

