/**
 * 
 */
package com.ssic.game.manage.service;

import com.ssic.game.admin.pageModel.DataGrid;
import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.common.dto.DictDto;

/**		
 * <p>Title: IDictService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博
 * @date 2015年6月25日 下午2:40:20	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年6月25日 下午2:40:20</p>
 * <p>修改备注：</p>
 */
public interface IDictService
{

    
    /**     
     * dataGrid：一句话描述方法功能
     * @param dictDto
     * @param ph
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年6月25日 下午2:46:44	 
     */
    DataGrid dataGrid(DictDto dictDto, PageHelper ph);

    
    /**     
     * add：一句话描述方法功能
     * @param tempDict
     * @exception	
     * @author 刘博
     * @date 2015年6月25日 下午2:53:52	 
     */
    void add(DictDto tempDict);


    
    /**     
     * delete：一句话描述方法功能
     * @param tempDict
     * @exception	
     * @author 刘博
     * @date 2015年6月25日 下午2:54:27	 
     */
    void delete(DictDto tempDict);


    
    /**     
     * findById：一句话描述方法功能
     * @param id
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年6月25日 下午2:55:03	 
     */
    DictDto findById(String id);


    
    /**     
     * update：一句话描述方法功能
     * @param dictDto
     * @exception	
     * @author 刘博
     * @date 2015年6月25日 下午2:55:56	 
     */
    void update(DictDto dictDto);

}

