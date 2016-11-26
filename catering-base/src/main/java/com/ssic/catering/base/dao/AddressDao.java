/**
 * 
 */
package com.ssic.catering.base.dao;

import java.util.List;

import lombok.Getter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssic.catering.base.dto.AddressDto;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.mapper.AddressExMapper;
import com.ssic.catering.base.mapper.AddressMapper;
import com.ssic.catering.base.pojo.Address;
import com.ssic.catering.base.pojo.AddressExample;
import com.ssic.catering.base.pojo.AddressExample.Criteria;
import com.ssic.util.StringUtils;
import com.ssic.util.base.MyBatisBaseDao;
import com.ssic.util.constants.DataStatus;

/**		
 * <p>Title: AddressDao </p>
 * <p>Description: 区域Dao</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博
 * @date 2015年8月7日 上午11:32:28	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年8月7日 上午11:32:28</p>
 * <p>修改备注：</p>
 */
@Repository
public class AddressDao extends MyBatisBaseDao<Address>
{
    private static final Logger log = Logger.getLogger(AddressDao.class);

    @Autowired
    @Getter
    private AddressMapper mapper;
    @Autowired
    @Getter
    private AddressExMapper exMapper;

    /**     
     * findAllBy：一句话描述方法功能
     * @param Address
     * @return
     * @exception   
     * @author Administrator
     * @date 2015年8月4日 上午11:33:53    
     */
    public List<Address> findAllBy(Address param, PageHelperDto ph)
    {
        AddressExample example = new AddressExample();
        Criteria criteria = example.createCriteria();
        if (ph != null && !StringUtils.isEmpty(String.valueOf(ph.getBeginRow()))
            && !StringUtils.isEmpty(String.valueOf(ph.getRows())))
        {
            int beginRow = ph.getBeginRow();
            int rows = ph.getRows();
            example.setOrderByClause("create_time desc limit " + beginRow + "," + rows);
        }
        else
        {
            example.setOrderByClause("create_time desc");
        }
        if (!StringUtils.isEmpty(param.getId()))
        {
            criteria.andIdEqualTo(param.getId());
        }
        if (!StringUtils.isEmpty(param.getProjId()))
        {
            criteria.andProjIdEqualTo(param.getProjId());
        }
        if (!StringUtils.isEmpty(param.getAddressName()))
        {
            criteria.andAddressNameEqualTo(param.getAddressName());
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return mapper.selectByExample(example);
    }

    /**     
     * findCountBy：一句话描述方法功能
     * @param Address
     * @return
     * @exception   
     * @author Administrator
     * @date 2015年8月4日 上午11:41:13    
     */
    public int findCountBy(Address param)
    {
        AddressExample example = new AddressExample();
        Criteria criteria = example.createCriteria();

        criteria.andStatEqualTo(DataStatus.ENABLED);
        if (!StringUtils.isEmpty(param.getId()))
        {
            criteria.andIdEqualTo(param.getId());
        }

        int count = mapper.countByExample(example);
        return count;
    }

    /**     
     * findById：一句话描述方法功能
     * @param id
     * @return
     * @exception   
     * @author Administrator
     * @date 2015年8月4日 下午3:29:36     
     */
    public Address findById(String id)
    {
        AddressExample example = new AddressExample();
        Criteria criteria = example.createCriteria();
        example.setDistinct(true);
        criteria.andStatEqualTo(DataStatus.ENABLED);
        // criteria.andStatEqualTo(DataStatus.ENABLED);

        if (!StringUtils.isEmpty(id))
        {
            criteria.andIdEqualTo(id);
        }
        return mapper.selectByPrimaryKey(id);
    }

    /**     
     * findChildListByParentId：一句话描述方法功能
     * @param id
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年8月7日 下午1:23:49	 
     */
    public List<Address> findChildListByParentId(String id)
    {
        AddressExample example = new AddressExample();
        Criteria criteria = example.createCriteria();
        example.setDistinct(true);
        criteria.andStatEqualTo(DataStatus.ENABLED);
        if (!StringUtils.isEmpty(id))
        {
            criteria.andParentIdEqualTo(id);
        }
        return mapper.selectByExample(example);
    }

    /**
     * 
     * findAddressById：通过区域ID获取区域信息
     * @param id 区域id
     * @return 区域信息
     * @exception	
     * @author 张亚伟
     * @date 2015年8月10日 上午9:16:51
     */
    public Address findAddressById(String id)
    {
        return mapper.selectByPrimaryKey(id);
    }

    /**
     * 
     * findAddressListByAddressCode：通过区域辅助码获取所有相关联的区域
     * @param addressCode 地区辅助码
     * @return
     * @exception	
     * @author 张亚伟
     * @date 2015年8月10日 上午9:27:56
     */
    public List<Address> findAddressListByAddressCode(String addressCode)
    {
        AddressExample example = new AddressExample();
        Criteria criteria = example.createCriteria();
        criteria.andAddressCodeLike(addressCode + "%");
        return mapper.selectByExample(example);
    }

    /**     
     * findCodeByLastCreateTime：一句话描述方法功能
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年8月11日 下午1:55:11	 
     */
    public List<AddressDto> findCodeByLastCreateTime()
    {
        List<AddressDto> dto = exMapper.findCodeByLastCreateTime();
        return dto;
    }

    /**     
     * validAddressRootExists：一句话描述方法功能
     * @param id
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年8月11日 下午2:22:15	 
     */
    public AddressDto validAddressRootExists(AddressDto addressDto)
    {
        AddressDto dto = exMapper.validAddressRootExists(addressDto.getId(), addressDto.getProjId());
        return dto;
    }

    /**     
     * vailAddressName：一句话描述方法功能
     * @param add
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年8月11日 下午2:31:38	 
     */
    public List<Address> vailAddressName(Address add)
    {
        AddressExample example = new AddressExample();
        Criteria criteria = example.createCriteria();
        criteria.andStatEqualTo(DataStatus.ENABLED);
        example.setOrderByClause("create_time desc");
        if (!StringUtils.isEmpty(add.getId()))
        {
            criteria.andIdNotEqualTo(add.getId());
        }
        if (!StringUtils.isEmpty(add.getAddressName()))
        {
            criteria.andAddressNameEqualTo(add.getAddressName());
        }
        if (!StringUtils.isEmpty(add.getProjId()))
        {
            criteria.andProjIdEqualTo(add.getProjId());
        }
        return mapper.selectByExample(example);
    }

    /**
     * 
     * queryAddressId：根据用户ID查询addressId
     * @param userId
     * @return
     * @exception   
     * @author pengcheng.yang
     * @date 2015年8月13日 下午12:20:48
     */
    public AddressDto queryAddressId(String userId)
    {
        AddressDto dto = exMapper.queryAddressId(userId);
        return dto;
    }

    /**
     * 
     * queryAddressIdAndParentIds：更具addressId查询下面的子Id
     * @param parentId
     * @return
     * @exception   
     * @author Administrator
     * @date 2015年8月13日 下午12:23:24
     */
    public List<AddressDto> queryAddressIdAndParentIds(String parentId)
    {
        List<AddressDto> list = exMapper.queryAddressIdAndParentIds(parentId);
        if (list != null)
        {
            return list;
        }
        return null;
    }

    /**
     * 
     * queryCityId：查询城市Id
     * @param parentId
     * @return
     * @exception   
     * @author pengcheng.yang
     * @date 2015年8月13日 下午5:38:52
     */
    public List<AddressDto> queryCityId(String cityId)
    {
        List<AddressDto> list = exMapper.queryCityId(cityId);
        if (list != null)
        {
            return list;
        }
        return null;
    }

    /**
     * findAddressByTypeAndAddressCode：通过区域辅助码获取区域列表
     * @param type 获取类型(1为获取区域平级,2为获取区域下级)
     * @param addressCode 区域辅助码
     * @return
     * @exception	
     * @author 张亚伟
     * @date 2015年8月25日 下午1:19:33
     */
    public List<Address> findAddressByTypeAndAddressCode(Integer type, String addressCode, String projId)
    {
        AddressExample example = new AddressExample();
        Criteria criteria = example.createCriteria();
        if (type == 1)
        {
            if (addressCode.length() == 2)
            {
                criteria.andAddressCodeLike("__");
            }
            else
            {
                criteria.andAddressCodeLike(addressCode.substring(0, addressCode.length() - 3) + "__");
            }
        }
        else if (type == 2)
        {
            criteria.andAddressCodeLike(addressCode + "__");
        }
        if (projId != null && !"".equals(projId))
        {
            criteria.andProjIdEqualTo(projId);
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return mapper.selectByExample(example);
    }

    public List<Address> findAddressByTypeAndAddressCode(Integer type, String addressCode)
    {
        AddressExample example = new AddressExample();
        Criteria criteria = example.createCriteria();
        if (type == 1)
        {
            if (addressCode.length() == 2)
            {
                criteria.andAddressCodeLike("__");
            }
            else
            {
                criteria.andAddressCodeLike(addressCode.substring(0, addressCode.length() - 3) + "__");
            }
        }
        else if (type == 2)
        {
            criteria.andAddressCodeLike(addressCode + "__");
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return mapper.selectByExample(example);
    }

    /**
     * findAddressByAddressCode：通过区域辅助码获取区域信息
     * @param addressCode 区域辅助码
     * @return
     * @exception	
     * @author 张亚伟
     * @date 2015年8月26日 上午11:16:41
     */
    public Address findAddressByAddressCode(String addressCode)
    {
        AddressExample example = new AddressExample();
        Criteria criteria = example.createCriteria();
        criteria.andAddressCodeEqualTo(addressCode);
        List<Address> list = mapper.selectByExample(example);
        return list == null || list.size() == 0 ? null : list.get(0);
    }

    /**
     * 	 
     * @author 朱振	
     * @date 2015年10月31日 下午1:13:31	
     * @version 1.0
     * @param param 查询条件
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年10月31日 下午1:13:31</p>
     * <p>修改备注：</p>
     */
    public List<Address> findAddressesBy(Address param)
    {
        if (param == null)
        {
            log.error("参数Address为空");
        }

        AddressExample example = new AddressExample();
        AddressExample.Criteria criteria = example.createCriteria();

        //id
        if (!StringUtils.isEmpty(param.getId()))
        {
            criteria.andIdEqualTo(param.getId());
        }
        //like addressName
        if (!StringUtils.isEmpty(param.getAddressName()))
        {
            //          criteria.andAddressNameEqualTo(param.getAddressName());
            criteria.andAddressNameLike("%" + param.getAddressName() + "%");
        }
        //addressCode
        if (!StringUtils.isEmpty(param.getAddressCode()))
        {
            criteria.andAddressCodeEqualTo(param.getAddressCode());
        }
        //parentId
        if (!StringUtils.isEmpty(param.getParentId()))
        {
            criteria.andParentIdEqualTo(param.getParentId());
        }

        criteria.andStatEqualTo(DataStatus.ENABLED);

        //projId
        if (!StringUtils.isEmpty(param.getProjId()))
        {
            criteria.andProjIdEqualTo(param.getProjId());
        }

        example.setOrderByClause("create_time asc");

        return mapper.selectByExample(example);
    }
}
