/**
 * 
 */
package com.ssic.game.manage.service;

/**		
 * <p>Title: IFiledsCloneService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年6月28日 上午9:36:51	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年6月28日 上午9:36:51</p>
 * <p>修改备注：</p>
 */
public interface IFieldsCloneService
{

    
    /**     
     * addExistFields：一句话描述方法功能
     * @param fieldId
     * @param formId
     * @exception   
     * @author Administrator
     * @date 2015年6月28日 上午9:45:41    
     */
    String  insertExistFields(String fieldId,String formFieldId);

    
    /**     
     * deleteById：一句话描述方法功能
     * @param fieldsCloneId
     * @exception	
     * @author Administrator
     * @date 2015年6月28日 下午7:23:24	 
     */
    void deleteById(String fieldsCloneId);
}

