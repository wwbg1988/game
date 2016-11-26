/**
 * 
 */
package com.ssic.game.ims.service;

import com.ssic.game.ims.model.ProcessFormRequest;
import com.ssic.util.model.RequestResult;
import com.ssic.util.model.Response;

/**		
 * <p>Title: IProcessFormService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author rkzhang	
 * @date 2015年6月25日 下午3:06:08	
 * @version 1.0
 * <p>修改人：rkzhang</p>
 * <p>修改时间：2015年6月25日 下午3:06:08</p>
 * <p>修改备注：</p>
 */
public interface IProcessFormService {

    
    /**     
     * processForm：表单信息按处理类型处理
     * @param formRequest
     * @return
     * @exception	
     * @author rkzhang
     * @date 2015年6月25日 下午3:27:34	 
     */
    Response<RequestResult> processForm(ProcessFormRequest formRequest);

}

