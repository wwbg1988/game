package com.ssic.catering.lbs.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.ssic.catering.lbs.documents.TransportTrail;

/**
 * 		
 * <p>Title: TransportTrailMongoDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author zhuzhen	
 * @date 2015年11月27日 下午5:10:22	
 * @version 1.0
 * <p>修改人：zhuzhen</p>
 * <p>修改时间：2015年11月27日 下午5:10:22</p>
 * <p>修改备注：</p>
 */
@ToString
public class TransportTrailMongoDto extends TransportTrail
{

    
     
     /**   
     * serialVersionUID: （一句话描述这个变量表示什么）      
     */   
    
    private static final long serialVersionUID = -4901485522626155294L;
    
    /**
     * 项目
     */
    @Getter
    @Setter
    private String projectName;
    
    /**
     * 驾驶员
     */
    @Getter
    @Setter
    private String driverName;
    
    /**
     * 任务名称
     */
    @Getter
    @Setter
    private String taskName;
}

