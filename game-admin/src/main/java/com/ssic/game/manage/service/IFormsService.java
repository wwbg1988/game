/**
 * 
 */
package com.ssic.game.manage.service;

import java.util.List;

import com.ssic.game.admin.pageModel.DataGrid;
import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.common.dto.FieldsDto;
import com.ssic.game.common.dto.FormsDto;

/**		
 * <p>Title: IFormsService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年6月24日 上午10:19:21	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年6月24日 上午10:19:21</p>
 * <p>修改备注：</p>
 */
public interface IFormsService
{

    
    /**     
     * dataGrid：一句话描述方法功能
     * @param fieldsDto
     * @param ph
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年6月24日 下午6:43:52	 
     */
    DataGrid dataGrid(FormsDto formsDto, PageHelper ph);

    
    /**     
     * vaildForms：一句话描述方法功能
     * @param forms
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年6月24日 下午6:44:04	 
     */
    int vaildForms(FormsDto forms);


    
    /**     
     * add：一句话描述方法功能
     * @param formDto
     * @exception	
     * @author 刘博
     * @date 2015年6月24日 下午6:44:10	 
     */
    void add(FormsDto formDto);


    
    /**     
     * delete：一句话描述方法功能
     * @param forms
     * @exception	
     * @author 刘博
     * @date 2015年6月24日 下午6:44:15	 
     */
    void delete(FormsDto forms);


    
    /**     
     * findById：一句话描述方法功能
     * @param id
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年6月24日 下午6:46:04	 
     */
    FormsDto findById(String id);


    
    /**     
     * update：一句话描述方法功能
     * @param formsDto
     * @exception	
     * @author 刘博
     * @date 2015年6月24日 下午6:47:20	 
     */
    void update(FormsDto formsDto);
    
    
    List<FormsDto> findAll(FormsDto formsDto);


    
    /**     
     * findFormsByFieldId：一句话描述方法功能
     * @param id
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年6月26日 下午8:39:46	 
     */
    FormsDto findFormsByFieldId(String id);


    
    /**     
     * findAllByNotInclude：一句话描述方法功能
     * @param formId
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年6月27日 下午8:41:46	 
     */
    List<FormsDto> findAllByNotInclude(String formId);


    
    /**     
     * findSameProcForm：一句话描述方法功能
     * @param formsDto
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年6月30日 上午8:41:44	 
     */
    List<FormsDto> findSameProcForms(String procId,String formId);

}

