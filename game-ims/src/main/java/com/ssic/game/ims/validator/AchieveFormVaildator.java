/**
 * 
 */
package com.ssic.game.ims.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssic.game.common.dao.ActionRoleDao;
import com.ssic.game.common.dao.ActionUserPersDao;
import com.ssic.game.common.dao.FieldsRoleDao;
import com.ssic.game.common.dao.FieldsUserDao;
import com.ssic.game.common.pojo.ActionRole;
import com.ssic.game.common.pojo.ActionUser;
import com.ssic.game.common.pojo.FieldRole;
import com.ssic.game.common.pojo.FieldUser;
import com.ssic.game.common.pojo.ImsRole;
import com.ssic.game.ims.model.AchieveFormRequest;
import com.ssic.game.ims.model.CreateProcRequest;
import com.ssic.util.model.RequestResult;

/**		
 * <p>Title: AchieveFormVaildator </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年7月2日 上午9:26:57	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年7月2日 上午9:26:57</p>
 * <p>修改备注：</p>
 */
@Service
public class AchieveFormVaildator
{
    
    @Autowired
    private ActionRoleDao actionRoleDao;
    
    @Autowired
    private ActionUserPersDao actionUserDao;
    
    @Autowired
    private FieldsUserDao fieldsUserDao;
    
    @Autowired
    private FieldsRoleDao fieldRoleDao;
    
    
    //校验actions
    public RequestResult validateActions(List<ImsRole> roleList,String userId,AchieveFormRequest request)
    {   
        
        ActionUser actionUser = new ActionUser();
//        actionUser.setProcId(request.getProcInstanceId());
        actionUser.setProjId(request.getProjectId());
        actionUser.setUserId(userId);
        List<ActionUser> userList = actionUserDao.findUserAll(actionUser);
        if(userList!=null&&userList.size()>0){
            return new RequestResult(true,"Action permission pass"); 
        }
        
        if(roleList!=null&&roleList.size()>0){
            for(int i =0;i<roleList.size();i++){
                ImsRole role = roleList.get(i);
                if(!StringUtils.isEmpty(role.getId())){
                  ActionRole actionRole = new ActionRole();
//                  actionRole.setProcId(request.getProcInstanceId());
                  actionRole.setProjId(request.getProjectId());
                  actionRole.setRoleId(role.getId());
                  List<ActionRole> tempList = actionRoleDao.findRoleAll(actionRole);
                  if(tempList!=null&&tempList.size()>0){
                      return new RequestResult(true,"Action permission pass"); 
                  }
                }
                
            }
        }
   
        return new RequestResult(false,"Action permission denied");
         
    }
    
    public RequestResult validateFields(List<ImsRole> roleList,String userId,AchieveFormRequest request,String fieldId){
        
        FieldUser fieldsUser = new FieldUser();
        fieldsUser.setUserId(userId);
//        fieldsUser.setProcId(request.getProcInstanceId());
        fieldsUser.setProjId(request.getProjectId());
        fieldsUser.setFieldId(fieldId);
        List<FieldUser> userList = fieldsUserDao.findUserAllBy(fieldsUser);
        if(userList!=null&&userList.size()>0){
            return new RequestResult(true,"1"); 
        }
        if(roleList!=null&&roleList.size()>0){
            for(int i =0;i<roleList.size();i++){
                ImsRole role = roleList.get(i);
                if(!StringUtils.isEmpty(role.getId())){
                    FieldRole frole = new FieldRole();
//                    frole.setProcId(request.getProcInstanceId());
                    frole.setProjId(request.getProjectId());
                    frole.setFieldId(fieldId);
                    frole.setRoleId(role.getId());
                    List<FieldRole> tempList = fieldRoleDao.findAllBy(frole);
                    if(tempList!=null&&tempList.size()>0){
                        return new RequestResult(true,"2"); 
                    }
               }
               
                
            }
        }
 
        return new RequestResult(false,"3"); 
        
      
    }
    
    public boolean isValiAction(List<ImsRole> roleList,String userId,String actionId){
        boolean flag = false;
        ActionUser actionUser = new ActionUser();
        actionUser.setActionId(actionId);
        actionUser.setUserId(userId);
        List<ActionUser> userList = actionUserDao.findUserAll(actionUser);
        if(userList!=null&&userList.size()>0){
            flag = true;
            return flag;
        }
        
        if(roleList!=null&&roleList.size()>0){
            for(int i =0;i<roleList.size();i++){
                ImsRole role = roleList.get(i);
                if(!StringUtils.isEmpty(role.getId())){
                  ActionRole actionRole = new ActionRole();
                  actionRole.setActionId(actionId);
                  actionRole.setRoleId(role.getId());
                  List<ActionRole> tempList = actionRoleDao.findRoleAll(actionRole);
                  if(tempList!=null&&tempList.size()>0){
                      flag = true;
                      return flag;
                  }
                }
                
            }
        }
        
        return flag;
        
    }
    
    //校验传入参数是否为空
    public RequestResult vailisEmpty(AchieveFormRequest request){
        if(StringUtils.isEmpty(request)){
              return new RequestResult(false, "achieveformRequest can not be null");
        }
        if(StringUtils.isEmpty(request.getUserId())){
            return new RequestResult(false, "userId can not be null");
        }
        if(StringUtils.isEmpty(request.getProcInstanceId())){
            return new RequestResult(false, "processInstanceId can not be null");
        }
        if(StringUtils.isEmpty(request.getProjectId())){
            return new RequestResult(false, "projectId can not be null");
        }
        if(StringUtils.isEmpty(request.getTaskId())){
            return new RequestResult(false, "taskId can not be null");
        }
        return new RequestResult(true, "");
    }
    
}

