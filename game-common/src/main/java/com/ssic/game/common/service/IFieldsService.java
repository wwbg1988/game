/**
 * 
 */
package com.ssic.game.common.service;

import java.util.List;

import com.ssic.game.common.dto.FieldsDto;

/**		
 * <p>Title: IFieldsService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author rkzhang	
 * @date 2015年6月30日 下午4:49:12	
 * @version 1.0
 * <p>修改人：rkzhang</p>
 * <p>修改时间：2015年6月30日 下午4:49:12</p>
 * <p>修改备注：</p>
 */
public interface IFieldsService {

    
    /**     
     * findByFormId：一句话描述方法功能
     * @return
     * @exception	
     * @author rkzhang
     * @date 2015年6月30日 下午4:52:15	 
     */
    List<FieldsDto> findByFormId(String formId);

}

