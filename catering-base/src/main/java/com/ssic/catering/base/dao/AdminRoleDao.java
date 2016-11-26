package com.ssic.catering.base.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssic.catering.base.dto.AdminRoleDto;
import com.ssic.catering.base.mapper.AdminRoleExMapper;
import com.ssic.catering.base.mapper.AdminRoleMapper;
import com.ssic.catering.base.pojo.AdminRole;
import com.ssic.catering.base.pojo.AdminRoleExample;
import com.ssic.util.StringUtils;
import com.ssic.util.base.MyBatisBaseDao;
import com.ssic.util.constants.DataStatus;

/**
 * 		
 * <p>Title: AdminRoleDao </p>
 * <p>Description: 团餐后台角色</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author zhuzhen	
 * @date 2015年11月25日 下午4:01:21	
 * @version 1.0
 * <p>修改人：zhuzhen</p>
 * <p>修改时间：2015年11月25日 下午4:01:21</p>
 * <p>修改备注：</p>
 */
@Repository
public class AdminRoleDao extends MyBatisBaseDao<AdminRole>
{
    @Autowired
    @Getter
    private AdminRoleMapper mapper;
    
    @Autowired
    private AdminRoleExMapper exMapper;
    
    /**
     * 
     * findAdminRoleDtosBy：一句话描述方法功能
     * @param param
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年12月3日 上午11:07:22
     */
    public List<AdminRoleDto> findAdminRoleDtosBy(AdminRoleDto param)
    {
        AdminRoleExample example = new AdminRoleExample();
        AdminRoleExample.Criteria criteria = example.createCriteria();
        
        if(param != null)
        {
          //id
            if(!StringUtils.isEmpty(param.getId()))
            {
              criteria.andIdEqualTo(param.getId());
            }
            //createTime
            //roleName
            if(!StringUtils.isEmpty(param.getRoleName()))
            {
              criteria.andRoleNameEqualTo(param.getRoleName());
            }
            //no
            if(null != param.getNo())
            {
              criteria.andNoEqualTo(param.getNo());
            }
            //pjNo
            if(!StringUtils.isEmpty(param.getPjNo()))
            {
              criteria.andPjNoEqualTo(param.getPjNo());
            }
            //postNo
            if(!StringUtils.isEmpty(param.getPostNo()))
            {
              criteria.andPostNoEqualTo(param.getPostNo());
            }
            //remark
            if(!StringUtils.isEmpty(param.getRemark()))
            {
              criteria.andRemarkEqualTo(param.getRemark());
            }
            //seq
            if(null != param.getSeq())
            {
              criteria.andSeqEqualTo(param.getSeq());
            }
            //pid
            if(!StringUtils.isEmpty(param.getPid()))
            {
              criteria.andPidEqualTo(param.getPid());
            }
            //stat
            if(null == param.getStat())
            {
              criteria.andStatEqualTo(DataStatus.ENABLED);
            }
        }
        else
        {
            criteria.andStatEqualTo(DataStatus.ENABLED);
        }
        
        
        return exMapper.selectAdminRoleDtoByExample(example, param.getProjectId());
    }
    
  
    /**
     * 
     * findMaxSeq：一句话描述方法功能
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年12月2日 下午3:13:26
     */
    public int findMaxSeq()
    {
        return exMapper.selectMaxSeq();
    }

    /**
     * 
     * findAdminRoleById：一句话描述方法功能
     * @param id
     * @return
     * @exception	
     * @author zhuzhen
     * @date 2015年12月2日 下午12:07:02
     */
    public AdminRole findAdminRoleById(String id)
    {   
        if(StringUtils.isEmpty(id))
        {
            return null;
        }
        
        return mapper.selectByPrimaryKey(id);
    }
}

