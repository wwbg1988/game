/**
 * 
 */
package com.ssic.game.ims.service;

import java.util.List;

import com.ssic.game.common.dto.DeptUsersDto;
import com.ssic.game.ims.model.ActionRecordModel;
import com.ssic.game.ims.model.LoadCompletionDto;
import com.ssic.game.ims.model.LoadProcess;
import com.ssic.game.ims.model.NewFormDataResult;
import com.ssic.game.ims.model.NewLoadTask;
import com.ssic.game.ims.model.TaskData;
import com.ssic.util.model.RequestResult;
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
public interface ILoadProcessService
{

    /**     
     * achieveForm：处理获取节点表单信息请求
     * @param request
     * @return
     * @exception	
     * @author milkteaye
     * @date 2015年6月25日 下午3:26:39	 
     */
    Response<List<LoadProcess>> loadProcess(String userId, String projId);

    /**     
     * achieveForm：处理获取节点表单信息请求
     * @param request
     * @return
     * @exception   
     * @author milkteaye
     * @date 2015年6月25日 下午3:26:39    
     */

    Response<List<NewFormDataResult>> loadNewAgency(String userId, String projId, String procId,
        String procInstId);

    /**
     * 已完成列表
     */

    Response<List<LoadCompletionDto>> loadCompletion(String userId, String projId, String procId,
        int beginRow, int size);

    /**
     * 
     * loadCompletion：查找所有部门下的人
     * @param userId
     * @param projId
     * @param procId
     * @param beginRow
     * @param size
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年8月7日 下午5:44:17
     */
    List<DeptUsersDto> findDeptUser(String userId, String projId);

    Response<TaskData> loadTaskData(String userId, String projId, String procId, String formId,
        String proinsId);

    Response<List<NewLoadTask>> loadNewTaskData(ActionRecordModel actionModel);

    List<LoadCompletionDto> loadDeptCompletion(String deptId, String projId, String procId);

    /**     
     * deleteMyData：删除我的tab页面(请假，出差，报销的)的某一条已办记录
     * @param projectId
     * @param procId
     * @param procInsId
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年9月17日 下午5:36:45	 
     */
    Response<RequestResult> deleteMyData(String projectId, String procId, String procInsId);

    /**     
     * loadSubDeptFormData：一句话描述方法功能
     * @param userId
     * @param projId
     * @param procInstanceId
     * @param size 
     * @param beginRow 
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年7月11日 下午3:14:23	 
     */
    //    Response<List<LoadCompletionDto>> loadSubDeptFormData(String userId, String projId);

}
