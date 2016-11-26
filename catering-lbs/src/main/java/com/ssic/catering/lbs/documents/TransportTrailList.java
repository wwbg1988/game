package com.ssic.catering.lbs.documents;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 		
 * <p>Title: TransportTrailList </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年12月3日 下午2:10:02	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年12月3日 下午2:10:02</p>
 * <p>修改备注：</p>
 */

public class TransportTrailList implements Serializable{
    
    private static final long serialVersionUID = -8519483834400798157L;

    @Getter
    @Setter
    private String id;
	/**
	 * 经度
	 */
	@Getter
	@Setter
	private String longitude;
	
	/**
	 * 纬度
	 */
	@Getter
	@Setter
	private String latitude;
	
	/**
	 * 剩余时间
	 */
	@Getter
	@Setter
	private String time;
	
	/**
	 * 距离
	 */
	@Getter
	@Setter
	private String distance;

}