package com.ssic.catering.base.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.AccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ssic.catering.base.dao.AdminRoleDao;
import com.ssic.catering.base.dao.AdminUsersDao;
import com.ssic.catering.base.dao.AdminUsersRoleDao;
import com.ssic.catering.base.dao.RoleProjectDao;
import com.ssic.catering.base.dto.AdminUsersDto;
import com.ssic.catering.base.dto.AdminUsersRoleDto;
import com.ssic.catering.base.pojo.AdminRole;
import com.ssic.catering.base.pojo.AdminUsers;
import com.ssic.catering.base.pojo.AdminUsersRole;
import com.ssic.catering.base.pojo.RoleProject;
import com.ssic.catering.base.service.IAdminUsersService;
import com.ssic.game.common.dao.ParamConfigDao;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.dto.ParamConfigDto;
import com.ssic.game.common.pojo.ParamConfig;
import com.ssic.util.BeanUtils;
import com.ssic.util.UUIDGenerator;

@Service
public class AdminUsersServiceImpl implements IAdminUsersService
{
    private static final Logger log = Logger.getLogger(AdminUsersServiceImpl.class);
    
    //供应商角色在参数表中的参数名
    private static final String SUPPLIERCONFIGNAME = "supplier_role_id";
    
    private static final Integer SUPPLIERCONFIGTYPE = 4;
    
    //供应商角色的父id
    private static final String  SUPPLIERROLEPARENTID = "0";//
    
    @Autowired
    private AdminUsersDao adminUsersDao;
    
    @Autowired
    private AdminRoleDao adminRoleDao;
    
    @Autowired
    private ParamConfigDao paramConfigDao;
    
    @Autowired
    private AdminUsersRoleDao adminUsersRoleDao;
    
    @Autowired
    private RoleProjectDao roleProjectDao;

    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.IAdminUsersService#addSupplier(com.ssic.catering.base.dto.AdminUsersDto)
     */
    @Override
    @Transactional
    public int addSupplier(AdminUsersDto newRecord)
    {
        if(newRecord != null)
        {
            
            if(StringUtils.isEmpty(newRecord.getProjectId()))
            {
                log.error("添加供应商：项目id不能为空");
                return 0;
            }
            
            //添加供应商后台登录账号
            if(StringUtils.isEmpty(newRecord.getUserAccount()))
            {
                log.error("账号不能为空");
                return 0;
            }
            
            //id
            if(StringUtils.isEmpty(newRecord.getId()))
            {
                newRecord.setId(UUIDGenerator.getUUID());
            }
            AdminUsers newUsers = new AdminUsers();
            BeanUtils.copyProperties(newRecord, newUsers);
            
            int usersCount = adminUsersDao.insert(newUsers);
            if(usersCount > 0)
            {
                //查询配置
                ParamConfigDto supllierConfig = this.findSupplierConfigByProjectId(newRecord.getProjectId());
                if(supllierConfig == null)
                {
                    //添加供应商角色
                    AdminRole newRole = new AdminRole();
                    newRole.setId(UUIDGenerator.getUUID());
                    newRole.setPid(SUPPLIERROLEPARENTID);//父节点
                    if(StringUtils.isEmpty(newRecord.getProjectName()))
                    {
                        newRole.setRoleName("供应商");//角色名称
                        newRole.setRemark("供应商");
                    }
                    else
                    {
                        newRole.setRoleName(newRecord.getProjectName()+"供应商");//角色名称
                        newRole.setRemark(newRecord.getProjectName()+"供应商");
                    }
                    
                    int maxSeq = adminRoleDao.findMaxSeq();
                    if(maxSeq >= Integer.MAX_VALUE)
                    {
                        log.error("供应商角色seq的最大值超过了int的最大值");
                        return 0;
                    }
                    
                    newRole.setSeq(maxSeq+1);
                    
                    int roleCount = adminRoleDao.insert(newRole);
                    if(roleCount > 0)
                    {
                        //添加用户与角色的关系
                        AdminUsersRole newUsersRole = new AdminUsersRole();
                        newUsersRole.setRoleId(newRole.getId());
                        newUsersRole.setUserId(newUsers.getId());
                        
                        int usersRoleCount =adminUsersRoleDao.insert(newUsersRole);
                        
                        //添加超管与角色的关系（项目设定——超管拥有所有角色）
                        AdminUsersRole superUserRole = new AdminUsersRole();
                        superUserRole.setRoleId(newRole.getId());
                        superUserRole.setUserId("0");
                        
                        int superUserRoleCount = adminUsersRoleDao.insert(superUserRole);
                        
                        if(usersRoleCount > 0 && superUserRoleCount>0)
                        {
                            //添加配置
                            ParamConfig param = new ParamConfig();
                            param.setParamName(SUPPLIERCONFIGNAME);//供应商角色
                            param.setParamType(SUPPLIERCONFIGTYPE);//配置类型
                            param.setProjId(newRecord.getProjectId());//项目id
                            param.setParamValue(newRole.getId());//角色id
                            param.setParamDescribe("自动生成：供应商角色配置");
                            
                            int paramCount = paramConfigDao.insertParamConfig(param);
                            if(paramCount > 0)
                            {
                                //添加项目角色关系
                                RoleProject roleProject = new RoleProject();
                                roleProject.setProjId(newRecord.getProjectId());
                                roleProject.setRoleId(newRole.getId());
                                int roleProjectCount = roleProjectDao.insert(roleProject);
                                if(roleProjectCount > 0)
                                {
                                    return usersRoleCount;
                                }
                                else
                                {
                                    log.error("角色项目关系表添加失败project:"+newRecord.getProjectId());
                                    return 0;
                                }
                            }
                            else
                            {
                                log.error("供应商角色配置项添加失败"+newRecord.getProjectId());
                                return 0;
                            }
                        }
                        else
                        {
                            log.error("添加用户与角色的关系失败"+newRecord.getProjectId());
                            return 0; 
                        }
                    }
                }
                else //供应商角色配置项存在
                {
                    String roleId = supllierConfig.getParamValue();
                    if(StringUtils.isEmpty(roleId))
                    {
                        log.error("供应商角色配置项错误"+newRecord.getProjectId());
                        return 0;
                    }
                    
                    //查询角色id
                    if(adminRoleDao.findAdminRoleById(roleId)==null)
                    {
                        log.error("供应商角色不存在roleId="+roleId);
                        return 0;
                    }                    
                    
                    //添加用户与角色的关系
                    AdminUsersRole newUsersRole = new AdminUsersRole();
                    newUsersRole.setRoleId(roleId);
                    newUsersRole.setUserId(newUsers.getId());
                    
                    int usersRoleCount = adminUsersRoleDao.insert(newUsersRole);
                    
                    if(usersRoleCount > 0)
                    {
                        //添加项目角色关系
                        RoleProject roleProject = new RoleProject();
                        roleProject.setProjId(newRecord.getProjectId());
                        roleProject.setRoleId(roleId);
                        int roleProjectCount = roleProjectDao.insert(roleProject);
                        if(roleProjectCount > 0)
                        {
                            return usersRoleCount;
                        }
                        else
                        {
                            log.error("角色项目关系表添加失败project:"+newRecord.getProjectId());
                            return 0;
                        }
                    }
                    else
                    {
                        log.error("添加用户与角色的关系失败"+newRecord.getProjectId());
                        return 0;
                    }
                }
                
            }
            else
            {
                log.error("添加失败");
                return 0;
            }
        }
            
            
        log.error("参数不能为空");
        return 0;
    }
    
    
    /**
     * 
     * findSupplierConfigByProjectId：从配置表中查找供应商角色
     * @param projectId
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年12月2日 上午10:14:18
     */
    public ParamConfigDto findSupplierConfigByProjectId(String projectId)
    {
        if(StringUtils.isEmpty(projectId))
        {
            log.error("项目id不能为空");
            return null;
        }
        
        ParamConfig param = new ParamConfig();
        param.setParamName(SUPPLIERCONFIGNAME);//供应商角色
        param.setParamType(SUPPLIERCONFIGTYPE);//配置类型
        param.setProjId(projectId);//项目id
        
        List<ParamConfig> params = paramConfigDao.findBy(param);
        if(CollectionUtils.isEmpty(params))
        {
            log.warn("没有供应商角色：项目"+projectId);
            return null;
        }
        
        if(params.size() > 1)
        {
            log.error("有多个供应商角色：项目"+projectId);
        }
        
        ParamConfigDto result = new ParamConfigDto();
        BeanUtils.copyProperties(params.get(0), result);
        
        return result;
    }
    
    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.IAdminUsersService#findAdminUsersDtoCountBy(com.ssic.catering.base.dto.AdminUsersRoleDto)
     */
    @Override
    public long findAdminUsersDtoCountBy(AdminUsersRoleDto param)
    {
        if(param != null)
        {
            AdminUsersRole record = new AdminUsersRole();
            BeanUtils.copyProperties(param, record);
            
            return adminUsersRoleDao.findAdminUsersDtoCountBy(record);
        }
        
        return adminUsersRoleDao.findAdminUsersDtoCountBy(null);
    }
    
    
    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.IAdminUsersService#findAdminUsersDtosBy(com.ssic.catering.base.dto.AdminUsersRoleDto, com.ssic.game.common.dto.PageHelperDto)
     */
    @Override
    public List<AdminUsersDto> findAdminUsersDtosBy(AdminUsersRoleDto param, PageHelperDto ph)
    {
        if(param != null)
        {
            AdminUsersRole record = new AdminUsersRole();
            BeanUtils.copyProperties(param, record);
            
            return adminUsersRoleDao.findAdminUsersDtosBy(record, ph);
        }
        
        return adminUsersRoleDao.findAdminUsersDtosBy(null, ph);
    }
    
    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.IAdminUsersService#findAdminUsersDtoByUserAccount(java.lang.String)
     */
    @Override
    public AdminUsersDto findAdminUsersDtoByUserAccount(String userAccount) throws AccessException
    {
        if(StringUtils.isEmpty(userAccount))
        {
            throw new AccessException("参数不能为空");
        }
        
        AdminUsers adminUsers = adminUsersDao.findByUserAccount(userAccount);
        if(adminUsers != null)
        {
            AdminUsersDto result = new AdminUsersDto();
            BeanUtils.copyProperties(adminUsers, result);
            return result;
        }
        
        return null;
    }
    
   /**
    * 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.IAdminUsersService#updateAdminUsers(com.ssic.catering.base.dto.AdminUsersDto)
    */
    @Override
    public int updateAdminUsers(AdminUsersDto adminUsersDto)
    {
        if(adminUsersDto != null)
        {
            AdminUsers result = new AdminUsers();
            BeanUtils.copyProperties(adminUsersDto, result);
            
            return adminUsersDao.updateByPrimaryKeySelective(result);
        }
        return 0;
    }
}

