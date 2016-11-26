/**
 * 
 */
package com.ssic.game.ims.service;

import com.ssic.game.ims.model.AchieveFormRequest;
import com.ssic.game.ims.model.TaskData;
import com.ssic.util.model.Response;

/**		
 * <p>Title: IAchiveFormService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author rkzhang	
 * @date 2015年6月25日 下午2:56:11	
 * @version 1.0
 * <p>修改人：rkzhang</p>
 * <p>修改时间：2015年6月25日 下午2:56:11</p>
 * <p>修改备注：</p>
 */
public interface IAchieveFormService {

    
    /**     
     * achieveForm：处理获取节点表单信息请求
     * @param request
     * @return
     * @exception	
     * @author rkzhang
     * @date 2015年6月25日 下午3:26:39	 
     */
    Response<TaskData> achieveTask(AchieveFormRequest request);

}

