/**
 * 
 */
package com.ssic.game.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**		
 * <p>Title: SearchDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author milkteaye	
 * @date 2015年8月11日 下午5:50:40	
 * @version 1.0
 * <p>修改人：milkteaye</p>
 * <p>修改时间：2015年8月11日 下午5:50:40</p>
 * <p>修改备注：</p>
 */
@ToString
public class WorkSearchDto implements Serializable
{
    //消息
    @Getter
    @Setter
    private String message;

    //时间
    @Getter
    @Setter
    private Date createTime;

    //事务类型参考
    @Getter
    @Setter
    private int type;

    @Getter
    @Setter
    private String url;

    @Getter
    @Setter
    private String projId;

    @Getter
    @Setter
    private String userId;

    @Getter
    @Setter
    private String procId;

    @Getter
    @Setter
    private int beginRow;

    @Getter
    @Setter
    private int endRow;

    @Getter
    @Setter
    private String searchDate;

    @Getter
    @Setter
    private int isMeetCreate; //是否是会议创建者， 0不是  1是  
    @Getter
    @Setter
    private String meetMD;
    @Getter
    @Setter
    private String workName;
    @Getter
    @Setter
    private int firstPageCount;
}
