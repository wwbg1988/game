/**
 * 
 */
package com.ssic.game.app.service;

import java.util.List;

import com.ssic.game.common.dto.WorkSearchDto;
import com.ssic.util.model.Response;


/**		
 * <p>Title: UserOperateSerivce </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author milkteaye
 * @date 2015年6月24日 下午8:26:26	
 * @version 1.0
 */

public interface SearchService
{

   
   public Response<List<WorkSearchDto>> findAll(String userId,String projId,String searchDate,int beginRows,int endRows, int eventType);
}
