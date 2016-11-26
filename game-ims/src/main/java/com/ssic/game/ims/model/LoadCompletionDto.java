/**
 * 
 */
package com.ssic.game.ims.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

import com.ssic.game.common.dto.DeptUsersDto;

/**		
 * <p>Title: LoadCompletion </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年7月11日 上午11:23:34	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年7月11日 上午11:23:34</p>
 * <p>修改备注：</p>
 */

public class LoadCompletionDto
{
    //    /**   
    //    * procInstanceId: 流程实例ID     
    //    */
    //    @Getter
    //    @Setter
    //    private String procInstanceId;
    //
    //    /**   
    //     * processInsNama: 流程实例名称     
    //     */
    //    @Getter
    //    @Setter
    //    private String processInsName;

    @Getter
    @Setter
    Map<String, Object> fields;

    @Getter
    @Setter
    private String createUser;

    @Getter
    @Setter
    private String deptName;

    @Getter
    @Setter
    private String approval;

    @Getter
    @Setter
    private String approvalDept;

    @Getter
    @Setter
    private Date createDate;

    @Getter
    @Setter
    private String createDateForString;

    @Getter
    @Setter
    private int dateDiff;

    @Getter
    @Setter
    private String stat;

    @Getter
    @Setter
    private String url;

    @Getter
    @Setter
    private String procInsId;

    @Getter
    @Setter
    private String projId;

    @Getter
    @Setter
    private String procId;

    @Getter
    @Setter
    private String userImg;

}
