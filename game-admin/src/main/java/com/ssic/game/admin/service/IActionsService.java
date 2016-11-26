
/**   
 * bare_field_name   
 * com.ssic.game.admin.service	
 * @return  the bare_field_name 
 */   

package com.ssic.game.admin.service;

import java.util.List;

import com.ssic.game.admin.pageModel.DataGrid;
import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.admin.pageModel.Tree;
import com.ssic.game.common.dto.ActionsDto;

/**		
 * <p>Title: ActionsService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年6月24日 上午8:59:19	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年6月24日 上午8:59:19</p>
 * <p>修改备注：</p>
 */
public interface IActionsService
{
    public void insert(ActionsDto actionsDto);
    
    public List<ActionsDto> findAllBy(ActionsDto actionsDto); 
    
    public DataGrid dataGrid(ActionsDto actions, PageHelper ph);
    
    public void del(ActionsDto actionsDto);
    
    public void updateFun(ActionsDto actionsDto);
    
    public String findUserRole(String ids,String projId);
    
    public String findUserPers(String id,String projId);
    
    public List<Tree> tree();
    public List<Tree> allTree(String projId);
    
    public List<Tree> allUserTree();
    public List<Tree> userTree(String searchName,String ids);
    
    public List<Tree>fieldTree(String searchName,String formId);
    
    public List<Tree> userTreeBy(String searchName,List<String> userIds,String formId);
    
    public void grantUser(String actionsids, String rolesId,String ActionUserOwn);
    public String grant(String actionsids, String rolesId,String ActionRoleOwn);
    
}

