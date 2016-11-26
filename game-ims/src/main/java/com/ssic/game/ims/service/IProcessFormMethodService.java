/**
 * 
 */
package com.ssic.game.ims.service;

import java.util.List;

import com.ssic.game.common.dto.FieldsDto;
import com.ssic.game.common.dto.FormsDto;
import com.ssic.game.ims.model.ProcessFormRequest;
import com.ssic.util.model.RequestResult;
import com.ssic.util.model.Response;

/**		
 * <p>Title: IProcessFormSubmitService </p>
 * <p>Description: 处理表单方法：保存，回退，提交</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博
 * @date 2015年7月27日 上午9:13:21	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年7月27日 上午9:13:21</p>
 * <p>修改备注：</p>
 */
public interface IProcessFormMethodService
{

    /**     
     * saveActionRecord：保存日志
     * @param formRequest
     * @param form
     * @param fieldDtoList
     * @param taskType
     * @param isCountSign
     * @exception   
     * @author 刘博
     * @date 2015年7月28日 下午3:42:53    
     */
    void saveActionRecord(ProcessFormRequest formRequest, FormsDto form, List<FieldsDto> fieldDtoList,
        Integer taskType, Integer isCountSign,Integer actionType);
    
    
    /**     
     * saveOrUpdateFormAndRecord：保存或更新表单和日志
     * @param formRequest
     * @param form
     * @param fieldDtoList
     * @param taskType
     * @param isCountSign
     * @param isFinish
     * @exception   
     * @author 刘博
     * @date 2015年7月28日 下午3:42:53    
     */
    void saveOrUpdateFormAndRecord(ProcessFormRequest formRequest, List<FieldsDto> fieldDtoList, FormsDto form,
        int isFinish, Integer taskType, Integer isCountSign);
    
    /**     
     * removeUserActionRecord：删除用户日志记录
     * @param userId
     * @param procInstanceId
     * @param formId
     * @exception   
     * @author 刘博
     * @date 2015年7月28日 下午3:42:53    
     */
    void removeUserActionRecord(String userId, String procInstanceId, String formId);

    
    /**     
     * mostRepeatSubmit：多人重复提交表单
     * @param formRequest
     * @param finished
     * @param countSign
     * @exception	
     * @author Administrator
     * @date 2015年7月28日 下午3:42:53	 
     */
    Response<RequestResult> mostRepeatSubmit(ProcessFormRequest formRequest, int finished, Integer countSign);
    
    /**     
     * updateProcInstance：更新流程实例
     * @param nowTaskId
     * @param procInstanceId
     * @exception   
     * @author 刘博
     * @date 2015年7月28日 下午3:42:53    
     */
    void updateProcInstance(String procInstanceId, String nowTaskId);
    
    
    /**     
     * updateProcInstance：更新流程实例和发送清佳云消息
     * @param formRequest
     * @exception   
     * @author 刘博
     * @date 2015年7月28日 下午3:42:53    
     */
    void updateInstanceAndSendMessage(ProcessFormRequest formRequest);

    
    /**     
     * findUserActionRecord：一句话描述方法功能
     * @param userId
     * @param procInstanceId
     * @param formId
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年7月28日 下午4:50:55	 
     */
    boolean findUserActionRecord(String userId, String procInstanceId, String formId);

    
}

