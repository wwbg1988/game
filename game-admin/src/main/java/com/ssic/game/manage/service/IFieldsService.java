/**
 * 
 */
package com.ssic.game.manage.service;

import java.util.List;

import com.ssic.game.admin.pageModel.DataGrid;
import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.admin.pageModel.Tree;
import com.ssic.game.common.dto.FieldRoleDto;
import com.ssic.game.common.dto.FieldUserDto;
import com.ssic.game.common.dto.FieldsDto;
import com.ssic.game.common.pojo.FieldRole;
import com.ssic.game.common.pojo.FieldUser;

/**		
 * <p>Title: IFields </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年6月24日 上午10:22:07	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年6月24日 上午10:22:07</p>
 * <p>修改备注：</p>
 */
public interface IFieldsService
{

    
    /**     
     * dataGrid：字段grid表单
     * @param fieldsDto
     * @param ph
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年6月24日 上午11:16:43	 
     */
    DataGrid dataGrid(FieldsDto fieldsDto, PageHelper ph);

    
    /**     
     * vailFieldValue：一句话描述方法功能
     * @param tempFields
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年6月24日 下午1:48:34	 
     */
    int vaildField(FieldsDto tempFields);


    
    /**     
     * add：一句话描述方法功能
     * @param tempFields
     * @exception	
     * @author 刘博
     * @date 2015年6月24日 下午1:57:46	 
     */
    void add(FieldsDto tempFields);


    
    /**     
     * delete：一句话描述方法功能
     * @param tempField
     * @exception	
     * @author 刘博
     * @date 2015年6月24日 下午2:09:26	 
     */
    void delete(FieldsDto tempField);


    
    /**     
     * findById：一句话描述方法功能
     * @param id
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年6月24日 下午2:09:40	 
     */
    FieldsDto findById(String id);


    
    /**     
     * update：一句话描述方法功能
     * @param fieldDto
     * @exception	
     * @author 刘博
     * @date 2015年6月24日 下午2:41:51	 
     */
    void update(FieldsDto fieldDto);
    
    List<FieldsDto> findAllBy(FieldsDto fieldDto);
    
    List<FieldRole> findFieldRole(FieldRoleDto fieldRoleDto);
    List<FieldUser> findFieldUser(FieldUserDto FieldUserDto);
    
    public void grantWriter(String formId,String fieldId,String roleId,String projId,String procId,String ownRole);
    
    public void grantRead(String formId,String fieldId,String roleId,String projId,String procId);
    
    public void grantUser(String fieldId,String usersIds,String projId,String procId,String userOwn);
    
    public void grantUserRead(String fieldId,String usersIds,String projId,String procId);
    
    public void delGrant(String formId,String fieldId,String projId,int readWriter);
    
    public void delGrantByUser(String fieldId,String projId,int readWriter);
    
    public List<Tree> findReadTree(List<String> param);


    
    /**     
     * dataGrid：一句话描述方法功能
     * @param formId
     * @param ph
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年6月27日 上午10:31:53	 
     */
    DataGrid dataGrid(String formId, PageHelper ph);


    
    /**     
     * addExistFieldDataGrid：一句话描述方法功能
     * @param formId
     * @param ph
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年6月28日 上午8:58:35	 
     */
    DataGrid addExistFieldDataGrid(FieldsDto fieldsDto, PageHelper ph);


    


    
    /**     
     * addExistFields：一句话描述方法功能
     * @param fieldId
     * @param formId
     * @param formFieldId 
     * @exception	
     * @author Administrator
     * @date 2015年6月28日 上午9:45:41	 
     */
    void  insertExistFields(String fieldId, String formId, String formFieldId);


    
    /**     
     * clearRefrenceField：一句话描述方法功能
     * @param fieldId
     * @exception	
     * @author Administrator
     * @date 2015年6月28日 下午7:20:38	 
     */
    void clearRefrenceField(String fieldId);

}

