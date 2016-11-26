package com.ssic.catering.lbs.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 		
 * <p>Title: TransportDriverDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年11月25日 上午10:47:14	
 * @version 1.0
 * <p>修改人：apple</p>
 * <p>修改时间：2015年11月25日 上午10:47:14</p>
 * <p>修改备注：</p>
 */
@ToString
public class TransportDriverDto implements Serializable  {
	
    
     /**   
     * serialVersionUID: （一句话描述这个变量表示什么）      
     */   
    
    private static final long serialVersionUID = -1613514007218759025L;

    /**
     * 主键
     */
	@Setter
	@Getter
    private String id;

	/**
	 * 供应商id
	 */
	@Setter
	@Getter
    private String adminUserId;

	/**
	 * 驾驶员账号
	 */
	@Setter
	@Getter
    private String account;

	/**
	 * 驾驶员密码
	 */
	@Setter
	@Getter
    private String password;
	
	/**
     * 驾驶员姓名
     */
	@Setter
    @Getter
	private String name;

	/**
	 * 驾驶员名称
	 */
	@Setter
	@Getter
    private String nickName;
	
	/**
	 * 全球移动设备身份识别码15位
	 */
	@Setter
    @Getter
	private String imei;

	/**
	 * 驾驶员手机号
	 */
	@Setter
	@Getter
    private String phone;
	
	/**
     * 驾驶员的状态
     * 1有任务
     * 0无任务
     */
    @Setter
    @Getter
	private Integer state;

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
     * 供应商
     */
    @Setter
    @Getter
	private String supplierName;
    
    
    /**
     * 项目id
     */
    @Setter
    @Getter
    private String projectId;
}

