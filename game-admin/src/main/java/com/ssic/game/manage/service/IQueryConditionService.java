/**
 * 
 */
package com.ssic.game.manage.service;

import com.ssic.game.admin.pageModel.DataGrid;
import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.common.dto.query.QueryConditionDto;

/**		
 * <p>Title: IQueryConditionService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年7月29日 下午3:17:04	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年7月29日 下午3:17:04</p>
 * <p>修改备注：</p>
 */
public interface IQueryConditionService
{

    
    /**     
     * dataGrid：一句话描述方法功能
     * @param conditionDto
     * @param ph
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年7月29日 下午3:21:34	 
     */
    DataGrid dataGrid(QueryConditionDto conditionDto, PageHelper ph);

    
    /**     
     * vaildField：一句话描述方法功能
     * @param tempConditionDto
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年7月29日 下午4:15:56	 
     */
    int vaildField(QueryConditionDto tempConditionDto);


    
    /**     
     * add：一句话描述方法功能
     * @param conditionDto
     * @exception	
     * @author Administrator
     * @date 2015年7月29日 下午4:16:07	 
     */
    void add(QueryConditionDto conditionDto);


    
    /**     
     * findById：一句话描述方法功能
     * @param id
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年7月29日 下午4:24:15	 
     */
    QueryConditionDto findById(String id);


    
    /**     
     * vaildOrder：一句话描述方法功能
     * @param tempConditionDto
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年7月30日 下午3:13:01	 
     */
    int vaildOrder(QueryConditionDto tempConditionDto);


    
    /**     
     * validConditionName：一句话描述方法功能
     * @param name
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年7月30日 下午4:02:26	 
     */
    int validConditionName(String name);


    
    /**     
     * update：一句话描述方法功能
     * @param conditionDto
     * @exception	
     * @author Administrator
     * @date 2015年7月30日 下午5:30:40	 
     */
    void update(QueryConditionDto conditionDto);


    
    /**     
     * validEditConditionName：一句话描述方法功能
     * @param name
     * @param id
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年7月30日 下午5:50:50	 
     */
    int validEditConditionName(String name, String id);


    
    /**     
     * delete：一句话描述方法功能
     * @param queryConditionId
     * @exception	
     * @author Administrator
     * @date 2015年7月31日 上午9:20:49	 
     */
    void delete(String queryConditionId);

}

