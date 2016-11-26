/**
 * 
 */
package com.ssic.game.ims.service;

import java.util.List;

import com.ssic.game.common.dto.query.QueryPage;
import com.ssic.game.common.dto.query.QueryPageList;
import com.ssic.util.model.Response;

/**		
 * <p>Title: IQuerySearhService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年7月28日 下午4:37:11	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年7月28日 下午4:37:11</p>
 * <p>修改备注：</p>
 */
public interface IQueryAchieveService
{
    Response<QueryPage> findQuery(String queryId);
    
    Response<List<QueryPageList>> findList(String project);
}

