/**
 * 
 */
package com.ssic.game.common.service;

import java.util.List;

import com.ssic.game.common.dto.DataGridDto;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.dto.ProcInstantsDto;


/**		
 * <p>Title: IProcInstanceService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年7月2日 上午9:54:31	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年7月2日 上午9:54:31</p>
 * <p>修改备注：</p>
 */
public interface IProcInstanceService
{
    /**     
     * findByInstanceId：一句话描述方法功能
     * @param instanceId
     * @return
     * @exception   
     * @author 刘博
     * @date 2015年6月30日 下午4:05:48    
     */
    ProcInstantsDto findByInstanceId(String instanceId);

    
    /**     
     * updateById：一句话描述方法功能
     * @param procInstanceId
     * @exception	
     * @author 刘博
     * @date 2015年7月2日 上午10:14:46	 
     */
    void updateProcInstance(ProcInstantsDto procInstanceDto);


    
    /**     
     * findByProcId：一句话描述方法功能
     * @param procId
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年7月2日 下午4:04:30	 
     */
    ProcInstantsDto findByProcId(String procId);
    
    
    List<ProcInstantsDto> findAllBy(ProcInstantsDto procInstantsDto);


     /**     
     * findAll：一句话描述方法功能
     * @param instanceDto
     * @param phDto
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年7月24日 下午4:00:59	 
     */
    DataGridDto findAll(ProcInstantsDto instanceDto, PageHelperDto phDto);
}

