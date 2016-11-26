/**
 * 
 */
package com.ssic.game.common.dto.query;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import com.ssic.game.common.dto.FieldsDto;
import com.ssic.game.common.dto.ProjectDto;

/**		
 * <p>Title: QueryResultDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author rkzhang	
 * @date 2015年7月24日 下午1:38:05	
 * @version 1.0
 * <p>修改人：rkzhang</p>
 * <p>修改时间：2015年7月24日 下午1:38:05</p>
 * <p>修改备注：</p>
 */
public class QueryResultDto implements Serializable{
    
    @Getter
    @Setter
    private String id;
    
    //项目ID
    @Getter
    @Setter
    private String projectId;
    
    //query表ID
    @Getter
    @Setter
    private String queryId;
    
    //字段ID
    @Getter
    @Setter
    private String fieldsId;
    
    //字段排序
    @Getter
    @Setter
    private Integer serialNum;
    
    //字段状态 1可用 0不可用
    @Getter
    @Setter
    private int stat;
    
    
    @Getter
    @Setter
    private Date lastUpdateTime;
    
    @Getter
    @Setter
    private Date createTime;
    
    //projectDTO
    @Getter
    @Setter
    private ProjectDto projDto;
    
    //fieldDto
    @Getter
    @Setter
    private FieldsDto fieldsDto;
    
    @Getter
    @Setter
    private String projectName;
    
    @Getter
    @Setter
    private String queryName;
    
    @Getter
    @Setter
    private String fieldsName;
  
}

