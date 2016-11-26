/**
 * 
 */
package com.ssic.game.ims.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

import com.ssic.game.common.pojo.Actions;

/**		
 * <p>Title: NewFormDataResult </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年8月4日 下午3:43:36	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年8月4日 下午3:43:36</p>
 * <p>修改备注：</p>
 */
public class NewFormDataResult implements Serializable
{
    @Getter
    @Setter
    private Map<String, Object> fields;
    @Getter
    @Setter
    private List<Actions> actions;

    @Getter
    @Setter
    private String projectId;

    @Getter
    @Setter
    private String userId;

    @Getter
    @Setter
    private String procInstanceId;

    @Getter
    @Setter
    private String formId;

    @Getter
    @Setter
    private String userName;

    @Getter
    @Setter
    private int dateDiff;

    @Getter
    @Setter
    private Date createDate;

    @Getter
    @Setter
    private String createDateForString;

    @Getter
    @Setter
    private String userImg;

}
