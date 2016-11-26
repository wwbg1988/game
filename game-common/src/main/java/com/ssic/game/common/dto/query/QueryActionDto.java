/**
 * 
 */
package com.ssic.game.common.dto.query;

import java.io.Serializable;
import java.util.Date;

import com.ssic.game.common.dto.FieldsDto;
import com.ssic.game.common.dto.ProjectDto;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: QueryActionDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author rkzhang	
 * @date 2015年7月24日 下午1:37:01	
 * @version 1.0
 * <p>修改人：rkzhang</p>
 * <p>修改时间：2015年7月24日 下午1:37:01</p>
 * <p>修改备注：</p>
 */
public class QueryActionDto implements Serializable
{

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
    
    //动作名称
    @Getter
    @Setter
    private String name;
    
    //动作URL
    @Getter
    @Setter
    private String url;
    
    //状态 1可用 0不可用
    @Getter
    @Setter
    private int stat;
    
    @Getter
    @Setter
    private Date lastUpdateTime;
    
    @Getter
    @Setter
    private Date createTime;
    
    //项目DTO
    @Getter
    @Setter
    private ProjectDto projDto;
    
    @Getter
    @Setter
    private String projectName;
    
    @Getter
    @Setter
    private String queryName;
  

}
