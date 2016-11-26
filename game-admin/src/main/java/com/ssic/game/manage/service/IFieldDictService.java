/**
 * 
 */
package com.ssic.game.manage.service;

import java.util.List;

import com.ssic.game.admin.pageModel.DataGrid;
import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.common.dto.FieldDictDto;

/**		
 * <p>Title: IFieldDictService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年6月24日 上午10:24:23	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年6月24日 上午10:24:23</p>
 * <p>修改备注：</p>
 */
public interface IFieldDictService
{

    
    /**     
     * dataGrid：一句话描述方法功能
     * @param fieldId
     * @param ph
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年6月25日 下午2:14:22	 
     */
    DataGrid dataGrid(String fieldId, PageHelper ph);

    
    /**     
     * vaildIsExists：一句话描述方法功能
     * @param fieldId
     * @param dictId
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年6月25日 下午7:55:42	 
     */
    int vaildIsExists(String fieldId, String dictId);


    
    /**     
     * insert：一句话描述方法功能
     * @param fieldId
     * @param dictId
     * @exception	
     * @author 刘博
     * @date 2015年6月25日 下午7:55:55	 
     */
    void insert(String fieldId, String dictId);


    
    /**     
     * delete：一句话描述方法功能
     * @param fieldId
     * @param dictId
     * @exception	
     * @author 刘博
     * @date 2015年6月25日 下午7:56:07	 
     */
    void delete(String fieldId, String dictId);


    
    /**     
     * findByDictId：一句话描述方法功能
     * @param id
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年6月26日 下午8:56:00	 
     */
    List<FieldDictDto> findByDictId(String id);

}

