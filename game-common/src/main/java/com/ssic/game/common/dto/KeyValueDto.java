package com.ssic.game.common.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: KeyValueDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振	
 * @date 2015年10月29日 上午10:44:44	
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年10月29日 上午10:44:44</p>
 * <p>修改备注：</p>
 */
public class KeyValueDto<T> implements Serializable
{

    
     /**   
     * serialVersionUID: （一句话描述这个变量表示什么）      
     */   
    
    private static final long serialVersionUID = 5557011659794991533L;
    
    
    @Getter
    @Setter
    private T key;
    
    
    @Getter
    @Setter
    private T value;

}

