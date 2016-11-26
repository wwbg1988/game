/**
 * 
 */
package com.ssic.catering.base.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.catering.base.dto.AddressUserDto;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.mapper.AddressUserExMapper;
import com.ssic.catering.base.mapper.AddressUserMapper;
import com.ssic.catering.base.pojo.AddressUser;
import com.ssic.catering.base.pojo.AddressUserExample;
import com.ssic.catering.base.pojo.AddressUserExample.Criteria;
import com.ssic.util.base.MyBatisBaseDao;
import com.ssic.util.constants.DataStatus;

/**		
 * <p>Title: AddressUserDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年8月8日 上午11:11:12	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年8月8日 上午11:11:12</p>
 * <p>修改备注：</p>
 */
@Repository
public class AddressUserDao extends MyBatisBaseDao<AddressUser>
{
    @Autowired
    @Getter
    private AddressUserMapper mapper;

    @Autowired
    @Getter
    private AddressUserExMapper eXmapper;

    /**
     * getAddressUserByUserId：通过用户ID获取此用户负责的区域
     * @param userId 用户Id
     * @return 用户负责区域
     * @exception	
     * @author 张亚伟
     * @date 2015年8月8日 下午2:21:33
     */
    public AddressUser getAddressUserByUserId(String userId)
    {
        AddressUserExample example = new AddressUserExample();
        Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        AddressUser list = mapper.selectByExample(example).get(0);
        return list == null ? null : list;
    }

    /**     
     * finAddressListByUserId：一句话描述方法功能
     * @param userId
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年8月8日 上午11:19:45	 
     */
    public List<AddressUser> finAddressListByUserId(String userId)
    {
        AddressUserExample example = new AddressUserExample();
        Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(userId))
        {
            criteria.andUserIdEqualTo(userId);
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return mapper.selectByExample(example);
    }

    /**
     * 
     * findAddressUserByID：通过主键ID获取区域负责人信息
     * @param 主键id
     * @return
     * @exception	
     * @author 张亚伟
     * @date 2015年8月10日 下午4:56:46
     */
    public AddressUser findAddressUserByID(String id)
    {
        return mapper.selectByPrimaryKey(id);
    }

    /**     
     * finAddressListByAddressId：通过区域ID获取区域负责人集合
     * @param addressId
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年8月11日 下午2:49:23	 
     */
    public List<AddressUser> finAddressListByAddressId(String addressId)
    {
        AddressUserExample example = new AddressUserExample();
        Criteria criteria = example.createCriteria();
        criteria.andStatEqualTo(DataStatus.ENABLED);
        if (!StringUtils.isEmpty(addressId))
        {
            criteria.andAddressIdEqualTo(addressId);
        }
        return mapper.selectByExample(example);
    }

    public int countAddressByAddressId(AddressUserDto addressUser)
    {
        AddressUserExample example = new AddressUserExample();
        Criteria criteria = example.createCriteria();
        criteria.andStatEqualTo(DataStatus.ENABLED);
        if (!StringUtils.isEmpty(addressUser.getAddressId()))
        {
            criteria.andAddressIdEqualTo(addressUser.getAddressId());
        }
        if (!StringUtils.isEmpty(addressUser.getAddressType()))
        {
            criteria.andAddressTypeEqualTo(addressUser.getAddressType());
        }
        return mapper.countByExample(example);
    }

    /**
     * finAddressUserByAddressId：通过区域ID获取区域负责人信息
     * @param addressId 区域Id
     * @return
     * @exception	
     * @author 张亚伟
     * @date 2015年8月12日 上午10:10:50
     */
    public AddressUser finAddressUserByAddressId(String addressId)
    {
        AddressUserExample example = new AddressUserExample();
        Criteria criteria = example.createCriteria();
        criteria.andAddressIdEqualTo(addressId);
        criteria.andStatEqualTo(DataStatus.ENABLED);
        List<AddressUser> list = mapper.selectByExample(example);
        if (list != null && list.size() > 0)
        {
            return list.get(0);
        }
        return null;
    }

    public AddressUser finAddressUserByAddressId(String addressId, String projId,String addresstype)
    {
        AddressUserExample example = new AddressUserExample();
        Criteria criteria = example.createCriteria();
        criteria.andAddressIdEqualTo(addressId);
        if (projId != null && !"".equals(projId))
        {
            criteria.andProjIdEqualTo(projId);
        }
        if(addresstype!=null && !"".equals(addresstype)){
        	criteria.andAddressTypeEqualTo(Integer.parseInt(addresstype) );
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        List<AddressUser> list = mapper.selectByExample(example);
        if (list != null && list.size() > 0)
        {
            return list.get(0);
        }
        return null;
    }

    public List<AddressUser> findAllByPage(AddressUserDto addressUser, PageHelperDto phDto)
    {
        AddressUserExample example = new AddressUserExample();
        Criteria criteria = example.createCriteria();
        criteria.andStatEqualTo(DataStatus.ENABLED);
        if (!StringUtils.isEmpty(addressUser.getAddressId()))
        {
            criteria.andAddressIdEqualTo(addressUser.getAddressId());
        }
        if (!StringUtils.isEmpty(addressUser.getAddressType()))
        {
            criteria.andAddressTypeEqualTo(addressUser.getAddressType());
        }
        if (phDto.getRows() != 0)
        {

            example.setOrderByClause("  create_time desc limit " + phDto.getBeginRow() + ","
                + phDto.getRows());
        }
        return mapper.selectByExample(example);
    }

    /**     
     * findAll：一句话描述方法功能
     * @param addressUser
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年8月17日 下午4:22:53	 
     */
    public List<AddressUser> findAll(AddressUserDto addressUser)
    {
        AddressUserExample example = new AddressUserExample();
        Criteria criteria = example.createCriteria();
        criteria.andStatEqualTo(DataStatus.ENABLED);
        if (!StringUtils.isEmpty(addressUser.getAddressId()))
        {
            criteria.andAddressIdEqualTo(addressUser.getAddressId());
        }
        if (!StringUtils.isEmpty(addressUser.getAddressType()))
        {
            criteria.andAddressTypeEqualTo(addressUser.getAddressType());
        }
        if (!StringUtils.isEmpty(addressUser.getUserId()))
        {
            criteria.andUserIdEqualTo(addressUser.getUserId());
        }
        if (!StringUtils.isEmpty(addressUser.getProjId()))
        {
            criteria.andProjIdEqualTo(addressUser.getProjId());
        }
        if (!StringUtils.isEmpty(addressUser.getCafeCode()))
        {
            criteria.andCafeCodeEqualTo(addressUser.getCafeCode());
        }
        return mapper.selectByExample(example);
    }

    /**
     * finAddressUserByAddressIdAndCafeCode：通过区域ID和食堂编码获取食堂负责人信息
     * @param addressId 区域ID
     * @param cafeCode 食堂编码
     * @return
     * @exception	
     * @author 张亚伟
     * @date 2015年8月18日 下午2:58:19
     */
    public AddressUser finAddressUserByAddressIdAndCafeCode(String addressId, String cafeCode)
    {
        AddressUserExample example = new AddressUserExample();
        Criteria criteria = example.createCriteria();
        criteria.andAddressIdEqualTo(addressId);
        criteria.andCafeCodeEqualTo(cafeCode);
        criteria.andAddressTypeEqualTo(4);
        List<AddressUser> list = mapper.selectByExample(example);
        if(list!=null && list.size()>0){
        	return list.get(0);
        }else{
        	return null;
        }
       // return list == null ? null : list.get(0);
    }

    /**     
     * finAddressUserByCafeCode：一句话描述方法功能
     * @param cafeCode
     * @return
     * @exception	
     * @author yuanbin
     * @date 2015年8月26日 下午4:34:34	 
     */
    public AddressUserDto finAddressUserByCafeCode(String cafeCode)
    {
        // TODO 添加方法注释
        return eXmapper.finAddressUserByCafeCode(cafeCode);
    }
}
