/**
 * 
 */
package com.ssic.game.ims.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.ssic.ims.documents.FormData;

/**		
 * <p>Title: SubDeptFormDataDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年7月15日 下午2:55:03	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年7月15日 下午2:55:03</p>
 * <p>修改备注：</p>
 */
public class SubDeptFormDataDto
{

    /**   
    * procInstanceId: 流程实例id
    */
    @Getter
    @Setter
    private String procInstanceId;
    
    /**   
     * procInstanceName: 流程实例名称
     */
    @Getter
    @Setter
    private String procInstanceName;

    /**   
     * formDataList: 表单数据集合
     */
    @Getter
    @Setter
    private List<FormData> formDataList;
}
