package com.ssic.catering.lbs.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 		
 * <p>Title: TransportDestDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年11月25日 上午10:32:11	
 * @version 1.0
 * <p>修改人：apple</p>
 * <p>修改时间：2015年11月25日 上午10:32:11</p>
 * <p>修改备注：</p>
 */
public class TransportDestDto implements Serializable
{

    /**   
    * serialVersionUID: （一句话描述这个变量表示什么）      
    */

    private static final long serialVersionUID = -1979695371347824663L;

    /**
     * 主键
     */
    @Setter
    @Getter
    private String id;

    /**
     * 项目id
     */
    @Setter
    @Getter
    private String projectId;

    /**
     * 项目名称
     */
    @Setter
    @Getter
    private String projectName;

    /**
     * 运送目的地
     */
    @Setter
    @Getter
    private String address;

    /**
     * 创建时间
     */
    @Setter
    @Getter
    private Date createTime;

    /**
     * 更新时间
     */
    @Setter
    @Getter
    private Date lastUpdateTime;

    /**
     * 是否有效
     */
    @Setter
    @Getter
    private Integer stat;
    
    /**
     * 经度
     */
    @Setter
    @Getter
    private String longitude;
    
    /**
     * 纬度
     */
    @Setter
    @Getter
    private String latitude;
}
