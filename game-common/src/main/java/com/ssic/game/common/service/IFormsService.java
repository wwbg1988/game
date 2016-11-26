/**
 * 
 */
package com.ssic.game.common.service;

import com.ssic.game.common.dto.FormsDto;

/**		
 * <p>Title: IFormsService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author rkzhang	
 * @date 2015年6月30日 下午3:56:59	
 * @version 1.0
 * <p>修改人：rkzhang</p>
 * <p>修改时间：2015年6月30日 下午3:56:59</p>
 * <p>修改备注：</p>
 */
public interface IFormsService {

    
    /**     
     * findByFormId：一句话描述方法功能
     * @param formId
     * @return
     * @exception	
     * @author rkzhang
     * @date 2015年6月30日 下午4:05:48	 
     */
    FormsDto findByFormId(String formId);

    
    /**     
     * findByTaskId：一句话描述方法功能
     * @param nowTaskId
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年9月9日 上午11:15:32	 
     */
    FormsDto findByTaskId(String nowTaskId);

}

