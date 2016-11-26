package com.ssic.game.common.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**		
 * <p>Title: UploadFileInfoDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振	
 * @date 2015年11月3日 下午4:22:19	
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年11月3日 下午4:22:19</p>
 * <p>修改备注：</p>
 */
@ToString
public class UploadFileInfoDto implements Serializable
{

    
     /**   
     * serialVersionUID: （一句话描述这个变量表示什么）      
     */   
    
    private static final long serialVersionUID = -6679646083076804074L;
    
    /**
     * 上传的原始文件名
     */
    @Getter
    @Setter
    private String originalFilename;
    
    
    /**
     * 期望的uri
     */
    @Getter
    @Setter
    private String expectedUri;
    
    /**
     * 上传的文件实际存储的uri
     */
    @Getter
    @Setter
    private String actualUri;
    
    /**
     * 是否上传成功
     */
    @Getter
    @Setter
    private boolean success;
}

