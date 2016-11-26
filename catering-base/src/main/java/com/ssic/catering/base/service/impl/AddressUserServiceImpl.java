/**
 * 
 */
package com.ssic.catering.base.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssic.catering.base.dao.AddressDao;
import com.ssic.catering.base.dao.AddressUserDao;
import com.ssic.catering.base.dao.CafetoriumDao;
import com.ssic.catering.base.dto.AddressUserDto;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.pojo.Address;
import com.ssic.catering.base.pojo.AddressUser;
import com.ssic.catering.base.pojo.Cafetorium;
import com.ssic.catering.base.service.IAddressUserService;
import com.ssic.catering.base.service.IConfigSearchService;
import com.ssic.game.common.dao.ImsUserDao;
import com.ssic.game.common.dto.DataGridDto;
import com.ssic.game.common.pojo.ImsUsers;
import com.ssic.util.BeanUtils;
import com.ssic.util.constants.DataStatus;

/**		
 * <p>Title:AddressUserServiceImpl </p>
 * <p>Description: 区域用户service实现层 </p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年8月8日 上午11:12:33	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年8月8日 上午11:12:33</p>
 * <p>修改备注：</p>
 */
@Service
public class AddressUserServiceImpl implements IAddressUserService
{
    @Autowired
    private AddressUserDao addressUserDao;

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private IConfigSearchService iConfigSearchService;

    @Autowired
    private ImsUserDao imsUserDao;

    @Autowired
    private CafetoriumDao cafeDao;

    /**
     * getAddressUserByUserId：通过用户ID获取此用户负责的区域
     * @param userId 用户Id
     * @return 用户负责区域
     * @exception	
     * @author 张亚伟
     * @date 2015年8月8日 下午2:21:33
     */
    @Override
    public AddressUserDto getAddressUserByUserId(String userId)
    {
        //获取负责人信息
        AddressUser addressUser = addressUserDao.getAddressUserByUserId(userId);
        if (addressUser != null)
        {
            AddressUserDto addressUserDto = new AddressUserDto();
            BeanUtils.copyProperties(addressUser, addressUserDto);

            if(addressUserDto.getUserId()!=null){
            	//查询用户图片
            	ImsUsers imsUsers = imsUserDao.findByUserId(addressUserDto.getUserId());
            	if(imsUsers!=null){
            		addressUserDto.setUserImg(imsUsers.getUserImage());
            	}
            }
            
            if (addressUserDto.getAddressId() != null)
            {
                //通过负责人的区域获取所负责的所在的区域
                Address address = addressDao.findAddressById(addressUserDto.getAddressId());

                //通过辅助码获取这个区域的平均分数
                String score = iConfigSearchService.findConfigScoreByAddressCode(address.getAddressCode());
                if (score != null)
                {
                    score = score.substring(0, 3);
                    addressUserDto.setScore(score);
                }else{
                	score=0+"";
                	addressUserDto.setScore(score);
                }

                //获取历史平均分
                String historyScore = iConfigSearchService.findConfigHistoryScoreByAddressCode(address.getAddressCode());
                if( historyScore!=null ){
                	historyScore = historyScore.substring(0, 3);
                	addressUserDto.setHistoryScore(historyScore);
                }else{
                	historyScore=0+"";
                	addressUserDto.setHistoryScore(historyScore);
                }
                
                
            }
            else
            {
                return addressUserDto;
            }

            return addressUserDto;
        }
        else
        {
            return null;
        }
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.IAddressUserService#finAddressListByUserId(java.lang.String)   
    */
    @Override
    public List<AddressUserDto> finAddressListByUserId(String userId)
    {
        List<AddressUserDto> result = new ArrayList<AddressUserDto>();
        List<AddressUser> list = addressUserDao.finAddressListByUserId(userId);
        if (!CollectionUtils.isEmpty(list))
        {
            result = BeanUtils.createBeanListByTarget(list, AddressUserDto.class);

        }
        return result;
    }

    /**
     * getAddressUserByID：通过主键ID获取区域负责人信息
     * @param id 主键ID
     * @return
     * @exception	
     * @author 张亚伟
     * @date 2015年8月10日 下午4:52:55
     */
    public AddressUserDto findAddressUserByID(String id)
    {
        AddressUser addressUser = addressUserDao.findAddressUserByID(id);
        if (addressUser == null)
        {
            return null;
        }
        AddressUserDto addressUserDto = new AddressUserDto();
        BeanUtils.copyProperties(addressUser, addressUserDto);
        return addressUserDto;
    }

    @Override
    public List<AddressUserDto> finAddressListByAddressId(String addressId)
    {
        List<AddressUser> dtoList = addressUserDao.finAddressListByAddressId(addressId);
        if (!CollectionUtils.isEmpty(dtoList))
        {
            return BeanUtils.createBeanListByTarget(dtoList, AddressUserDto.class);
        }
        return null;
    }

    /**
     * finAddressUserByAddressId：通过区域ID获取区域负责人信息
     * @param addressId 区域Id
     * @return
     * @exception	
     * @author 张亚伟
     * @date 2015年8月12日 上午10:10:50
     */
    @Override
    public AddressUserDto finAddressUserByAddressId(String addressId)
    {
        AddressUser addressUser = addressUserDao.finAddressUserByAddressId(addressId);
        if (addressUser == null)
        {
            return null;
        }
        AddressUserDto addressUserDto = new AddressUserDto();
        BeanUtils.copyProperties(addressUser, addressUserDto);
        return addressUserDto;
    }

    
    @Override
    public AddressUserDto finAddressUserByAddressId(String addressId,String projId,String addresstype)
    {
        AddressUser addressUser = addressUserDao.finAddressUserByAddressId(addressId,projId,addresstype);
        if (addressUser == null)
        {
            return null;
        }
        AddressUserDto addressUserDto = new AddressUserDto();
        BeanUtils.copyProperties(addressUser, addressUserDto);
        return addressUserDto;
    }
    
    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.IAddressUserService#insert(com.ssic.catering.base.dto.AddressUserDto)   
    */
    @Override
    public void insert(AddressUserDto addressUserDto)
    {
        AddressUser addressUser = new AddressUser();
        BeanUtils.copyProperties(addressUserDto, addressUser);
        addressUser.setCreateTime(new Date());
        addressUser.setStat(DataStatus.ENABLED);
        addressUserDao.insert(addressUser);
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.IAddressUserService#findAll(com.ssic.catering.base.dto.AddressUserDto)   
    */
    @Override
    public List<AddressUserDto> findAll(AddressUserDto addressUser)
    {
        List<AddressUser> addressUsers = addressUserDao.findAll(addressUser);
        if (!CollectionUtils.isEmpty(addressUsers))
        {
            return BeanUtils.createBeanListByTarget(addressUsers, AddressUserDto.class);
        }
        return null;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.IAddressUserService#update(com.ssic.catering.base.dto.AddressUserDto)   
    */
    @Override
    public void update(AddressUserDto addressUserDto)
    {
        AddressUser addressUser = new AddressUser();
        BeanUtils.copyProperties(addressUserDto, addressUser);
        addressUser.setStat(DataStatus.ENABLED);
        addressUser.setLastUpdateTime(new Date());
        addressUserDao.updateByPrimaryKey(addressUser);

    }

    @Override
    public AddressUserDto finAddressUserByAddressIdAndCafeCode(String addressId, String cafeCode)
    {
        AddressUser addressUser = addressUserDao.finAddressUserByAddressIdAndCafeCode(addressId, cafeCode);
        if (addressUser != null)
        {
            AddressUserDto addressUserDto = new AddressUserDto();
            BeanUtils.copyProperties(addressUser, addressUserDto);
            return addressUserDto;
        }
        return null;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.IAddressUserService#findAllDataGrid(java.lang.String, com.ssic.game.common.dto.PageHelperDto)   
    */
    @Override
    public DataGridDto findAllDataGrid(String addressId, PageHelperDto phDto, String projId)
    {
        DataGridDto dataGridDto = new DataGridDto();
        List<AddressUserDto> resultList = new ArrayList<AddressUserDto>();
        AddressUserDto addressUser = new AddressUserDto();
        addressUser.setAddressId(addressId);
        addressUser.setAddressType(4);
        List<AddressUser> addList = addressUserDao.findAllByPage(addressUser, phDto);
        if (CollectionUtils.isEmpty(addList))
        {
            return null;
        }
        int counts = addressUserDao.countAddressByAddressId(addressUser);
        List<AddressUserDto> dtoList = BeanUtils.createBeanListByTarget(addList, AddressUserDto.class);
        for (AddressUserDto addDto : dtoList)
        {
            ImsUsers imsUsers = imsUserDao.findByUserId(addDto.getUserId());
            if (imsUsers != null)
            {
                addDto.setUserName(imsUsers.getName());
                addDto.setPhone(imsUsers.getMobilePhone() == null ? null : imsUsers.getMobilePhone());
            }
            else
            {
                continue;
            }
            Cafetorium param = new Cafetorium();
            if (!StringUtils.isEmpty(projId))
            {
                param.setProjId(projId);
            }
            param.setCafeCode(addDto.getCafeCode());
            List<Cafetorium> cafeList = cafeDao.findAllByNoPage(param);
            if (!CollectionUtils.isEmpty(cafeList))
            {
                addDto.setCafeName(cafeList.get(0).getCafeName());
                addDto.setCafePhone(cafeList.get(0).getMobileNo() == null ? null : cafeList.get(0)
                    .getMobileNo());
            }
            else
            {
                continue;
            }
            resultList.add(addDto);

        }
        dataGridDto.setRows(resultList);
        dataGridDto.setTotal(Long.valueOf(counts + ""));

        return dataGridDto;
    }

    /** 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.IAddressUserService#deleteByAddressId(java.lang.String)   
     */
    @Override
    public void deleteByAddressIdAndCafeCode(String addressId, String cafeCode)
    {
        List<AddressUser> addressUserList = addressUserDao.finAddressListByAddressId(addressId);
        for (AddressUser addressUser : addressUserList)
        {
            if (!StringUtils.isEmpty(addressUser.getCafeCode()) && addressUser.getCafeCode().equals(cafeCode))
            {
                addressUser.setStat(DataStatus.DISABLED);
                addressUser.setLastUpdateTime(new Date());
                addressUserDao.updateByPrimaryKeySelective(addressUser);
            }
        }
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.IAddressUserService#finAddressUserByCafeCode(java.lang.String)   
    * @author yuanbin
    */
    @Override
    public AddressUserDto finAddressUserByCafeCode(String cafeCode)
    {
        // TODO 添加方法注释

        return addressUserDao.finAddressUserByCafeCode(cafeCode);
    }

    
     /** 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.IAddressUserService#delete(com.ssic.catering.base.dto.AddressUserDto)   
     */
    @Override
    public void delete(AddressUserDto dto)
    {
        AddressUser addressUser = new AddressUser();
        BeanUtils.copyProperties(dto, addressUser);
        addressUser.setStat(DataStatus.DISABLED);
        addressUser.setLastUpdateTime(new Date());
        addressUserDao.updateByPrimaryKey(addressUser);
        
    }

}
