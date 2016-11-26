/**
 * 
 */
package com.ssic.game.sync.service;

import com.ssic.game.common.dto.FormsDto;

/**		
 * <p>Title: IFormsHandlerService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author rkzhang	
 * @date 2015年6月28日 上午9:29:13	
 * @version 1.0
 * <p>修改人：rkzhang</p>
 * <p>修改时间：2015年6月28日 上午9:29:13</p>
 * <p>修改备注：</p>
 */
public interface IFormsHandlerService {

    
    /**     
     * processCreate：处理新增更新Redis
     * @param form
     * @exception	
     * @author rkzhang
     * @date 2015年6月28日 上午9:41:38	 
     */
    void processCreate(FormsDto form);

    
    /**     
     * processUpdate：处理删除更新Redis
     * @param form
     * @exception	
     * @author rkzhang
     * @date 2015年6月28日 上午9:43:07	 
     */
    void processUpdate(FormsDto form);


    
    /**     
     * updateById：根据ID更新Redis
     * @param formId
     * @exception	
     * @author rkzhang
     * @date 2015年6月28日 下午7:06:02	 
     */
    void updateById(String formId);
    
    

}

