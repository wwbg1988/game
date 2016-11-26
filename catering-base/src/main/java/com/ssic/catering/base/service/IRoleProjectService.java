/**
 * 
 */
package com.ssic.catering.base.service;

import java.util.List;

import com.ssic.catering.base.dto.RoleProjectDto;

/**		
 * <p>Title: IRoleProjectService </p>
 * <p>Description: 角色项目关系Service</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年10月26日 下午1:54:53	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年10月26日 下午1:54:53</p>
 * <p>修改备注：</p>
 */
public interface IRoleProjectService
{

    /**     
     * grantProject：角色赋权项目
     * @param role
     * @exception	
     * @author 刘博
     * @date 2015年10月26日 下午2:44:31	 
     */
    void grantProject(RoleProjectDto roleProjectDto);

    /**     
     * findAllBy：查找所有
     * @param roleProjectDto 角色项目dto对象
     * @exception   
     * @author 刘博
     * @date 2015年10月26日 下午2:44:31   
     */
    public List<RoleProjectDto> findAllBy(RoleProjectDto roleProjectDto);

    
    /**     
     * delete：删除项目角色关系
     * @param delete_dto
     * @exception	
     * @author 刘博
     * @date 2015年10月26日 下午4:10:14	 
     */
    void delete(RoleProjectDto delete_dto);

}
