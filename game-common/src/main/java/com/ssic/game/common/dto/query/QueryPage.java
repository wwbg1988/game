/**
 * 
 */
package com.ssic.game.common.dto.query;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**		
 * <p>Title: QueryPage </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author rkzhang	
 * @date 2015年7月24日 下午1:39:42	
 * @version 1.0
 * <p>修改人：rkzhang</p>
 * <p>修改时间：2015年7月24日 下午1:39:42</p>
 * <p>修改备注：</p>
 */
@ToString
public class QueryPage {

    @Getter
    @Setter
    private String queryId;
      
    //查询条件列表
    @Getter
    @Setter
    private List<QueryConditionDto> conditions;
    
    //下一步动作按钮列表
    @Getter
    @Setter
    private List<QueryActionDto> actions;

    //返回表单表头字段列表
    @Getter
    @Setter
    
    private List<QueryResultDto> results;
}

