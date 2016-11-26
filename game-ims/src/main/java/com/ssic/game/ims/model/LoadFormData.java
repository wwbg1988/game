/**
 * 
 */
package com.ssic.game.ims.model;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: LoadFormData </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author milkteaye	
 * @date 2015年8月18日 下午2:13:49	
 * @version 1.0
 * <p>修改人：milkteaye</p>
 * <p>修改时间：2015年8月18日 下午2:13:49</p>
 * <p>修改备注：</p>
 */
public class LoadFormData
{
    @Getter
    @Setter
    private String deptId;
    
    @Getter
    @Setter
    private String procId;
    
    @Getter
    @Setter
    private String paramInstId;
    
    @Getter
    @Setter
    private String projId;
    
    @Getter
    @Setter
    private int beginRow=0;
    
    @Getter
    @Setter
    private int size=0;
    
}

