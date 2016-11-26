/**
 * 
 */
package com.ssic.game.ims.service;

import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.ims.model.FormDataQuerys;
import com.ssic.game.ims.model.FormDataResults;
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
public interface IQuerySearchService
{
    Response<FormDataResults> findBy(FormDataQuerys query,PageHelperDto ph);
}

