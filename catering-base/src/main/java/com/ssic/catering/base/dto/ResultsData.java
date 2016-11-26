/**
 * 
 */
package com.ssic.catering.base.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: ResultsData </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author pengcheng.yang	
 * @date 2015年8月14日 上午9:09:05	
 * @version 1.0
 * <p>修改人：pengcheng.yang</p>
 * <p>修改时间：2015年8月14日 上午9:09:05</p>
 * <p>修改备注：</p>
 */
public class ResultsData implements Serializable
{
    @Getter
    @Setter
    private String addressId;
    
    @Getter
    @Setter
    private String addressName;
    
    @Getter
    @Setter
    private List<SensitiveWarningReportDto> senList;
}

