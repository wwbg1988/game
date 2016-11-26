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
 * <p>Title: QueryConditionDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author rkzhang	
 * @date 2015年7月24日 下午1:37:22	
 * @version 1.0
 * <p>修改人：rkzhang</p>
 * <p>修改时间：2015年7月24日 下午1:37:22</p>
 * <p>修改备注：</p>
 */
public class QueryConditionDto implements Serializable{

  
    @Getter
    @Setter
    private String id;
    
    //项目ID
    @Getter
    @Setter
    private String projectId;
    
     //项目名称
    @Getter
    @Setter
    private String projName;
    
    //query表主键
    @Getter
    @Setter
    private String queryId;
    
    //query表名称
    @Getter
    @Setter
    private String queryName;
    
    //查询字段名称
    @Getter
    @Setter
    private String name;
    
    //字段ID
    @Getter
    @Setter
    private String fieldsId;
    
    //查询字段框参数名
    @Getter
    @Setter
    private String fieldParamName;
    
    @Getter
    @Setter
    private String fieldParamDesc;
    
    /**
     *  //自定义查询条件
    //等于=
    static final int EQ =1;
    //小于
    static final int LT = 2;
    //小于等于
    static final int LTE = 3;
    //大于
    static final int GT = 4;
    //大于等于
    static final int GTE = 5;
    //like
    static final int LIKE =6;
    //排序倒序
    static final int ORDERBY_DESC =7;
    //排序正序
    static final int ORDERBY_ASC =8;
     */
    @Getter
    @Setter
    private int opt;
    
    //状态 1可用 0不可用
    @Getter
    @Setter
    private int stat;
    
    //最后修改时间
    @Getter
    @Setter
    private  Date lastUpdateTime;
    
    //创建时间
    @Getter
    @Setter
    private Date createTime;
    
    //查询字段排序
    @Getter
    @Setter
    private Integer serialNum;
    
    //项目DTO
    @Getter
    @Setter
    private ProjectDto projDto;
    
    //字段DTO
    @Getter
    @Setter
    private FieldsDto fieldsDto;
}

