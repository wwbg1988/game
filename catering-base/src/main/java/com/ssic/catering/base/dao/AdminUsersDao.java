package com.ssic.catering.base.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import lombok.Getter;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.AccessException;
import org.springframework.stereotype.Repository;

import com.ssic.catering.base.mapper.AdminUsersMapper;
import com.ssic.catering.base.pojo.AdminUsers;
import com.ssic.catering.base.pojo.AdminUsersExample;
import com.ssic.util.StringUtils;
import com.ssic.util.UUIDGenerator;
import com.ssic.util.base.MyBatisBaseDao;
import com.ssic.util.constants.DataStatus;

@Repository
public class AdminUsersDao extends MyBatisBaseDao<AdminUsers>
{
    @Autowired
    @Getter
    private AdminUsersMapper mapper;

    /**
     * 
     * findBy：一句话描述方法功能
     * 
     * @param param
     * @return
     * @exception
     * @author zhuzhen
     * @date 2015年12月2日 上午11:21:41
     */
    public List<AdminUsers> findBy(AdminUsers param)
    {
        AdminUsersExample example = new AdminUsersExample();
        AdminUsersExample.Criteria criteria = example.createCriteria();

        if (param != null)
        {
            // id
            if (!StringUtils.isEmpty(param.getId()))
            {
                criteria.andIdEqualTo(param.getId());
            }
            // age
            if (null != param.getAge())
            {
                criteria.andAgeEqualTo(param.getAge());
            }
            // createdatetime
            // deptId
            if (!StringUtils.isEmpty(param.getDeptId()))
            {
                criteria.andDeptIdEqualTo(param.getDeptId());
            }
            // email
            if (!StringUtils.isEmpty(param.getEmail()))
            {
                criteria.andEmailEqualTo(param.getEmail());
            }
            // gender
            if (null != param.getGender())
            {
                criteria.andGenderEqualTo(param.getGender());
            }
            // isadmin
            if (null != param.getIsadmin())
            {
                criteria.andIsadminEqualTo(param.getIsadmin());
            }
            // modifydatetime
            // name
            if (!StringUtils.isEmpty(param.getName()))
            {
                criteria.andNameEqualTo(param.getName());
            }
            // pjNo
            if (!StringUtils.isEmpty(param.getPjNo()))
            {
                criteria.andPjNoEqualTo(param.getPjNo());
            }
            // postNo
            if (!StringUtils.isEmpty(param.getPostNo()))
            {
                criteria.andPostNoEqualTo(param.getPostNo());
            }
            // password
            if (!StringUtils.isEmpty(param.getPassword()))
            {
                criteria.andPasswordEqualTo(param.getPassword());
            }
            // qjyAccount
            if (!StringUtils.isEmpty(param.getQjyAccount()))
            {
                criteria.andQjyAccountEqualTo(param.getQjyAccount());
            }
            // userAccount
            if (!StringUtils.isEmpty(param.getUserAccount()))
            {
                criteria.andUserAccountEqualTo(param.getUserAccount());
            }
            // userImage
            if (!StringUtils.isEmpty(param.getUserImage()))
            {
                criteria.andUserImageEqualTo(param.getUserImage());
            }
            // userNo
            if (!StringUtils.isEmpty(param.getUserNo()))
            {
                criteria.andUserNoEqualTo(param.getUserNo());
            }
            // stat
            if (null == param.getStat())
            {
                criteria.andStatEqualTo(DataStatus.ENABLED);
            }
            // isdelete
            if (null != param.getIsdelete())
            {
                criteria.andIsdeleteEqualTo(param.getIsdelete());
            }
        }
        else
        {
            criteria.andStatEqualTo(DataStatus.ENABLED);
        }

        return mapper.selectByExample(example);
    }

    /**
     * 
     * findByUserAccount：根据账号查询
     * 
     * @param account
     * @return
     * @throws SQLException
     * @exception
     * @author zhuzhen
     * @date 2015年12月2日 上午11:31:40
     */
    public AdminUsers findByUserAccount(String account) throws AccessException
    {
        AdminUsers param = new AdminUsers();
        param.setUserAccount(account);

        List<AdminUsers> users = this.findBy(param);
        if (CollectionUtils.isEmpty(users))
        {
            return null;
        }

        if (users.size() > 1)
        {
            throw new AccessException("账号重复");
        }

        return users.get(0);
    }

    @Override
    public int insert(AdminUsers obj)
    {
        if (StringUtils.isEmpty(obj.getId()))
        {
            obj.setId(UUIDGenerator.getUUID());
        }

        obj.setCreatedatetime(new Date());
        obj.setStat(1);

        return mapper.insert(obj);
    }

}
