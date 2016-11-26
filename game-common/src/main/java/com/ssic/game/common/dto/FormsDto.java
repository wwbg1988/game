/**
 * 
 */
package com.ssic.game.common.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.ssic.base.redis.RedisKeyPrefix;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**		
 * <p>Title: FormsDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年6月24日 上午10:12:58	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年6月24日 上午10:12:58</p>
 * <p>修改备注：</p>
 */
@ToString
@RedisKeyPrefix(prefixValue="game.common.dto.FormsDto:{id}")
public class FormsDto implements Serializable
{

    private static final long serialVersionUID = -7428385775257570725L;

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String projId;

    @Getter
    @Setter
    private String taskId;
    
    @Getter
    @Setter
    private String procId;

    @Getter
    @Setter
    private Integer stat;
    @Getter
    @Setter
    private Date lastUpdateTime;

    @Getter
    @Setter
    private Date createTime;
    
    @Getter
    @Setter
    private Date createTimeStart;
    
    @Getter
    @Setter
    private Date createTimeEnd;
    
    @Getter
    @Setter
    private Date lastUpdateTimeStart;
    
    @Getter
    @Setter
    private Date lastUpdateTimeEnd;
    
    @Getter
    @Setter
    private String taskName;
    
    @Getter
    @Setter
    private String projName;
    
    @Getter
    @Setter
    private String procName;
    
    @Getter
    @Setter
    private List<FieldsDto> fields;
}
