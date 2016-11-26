/**
 * 
 */
package com.ssic.catering.base.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssic.catering.base.dao.AddressDao;
import com.ssic.catering.base.dto.AddressDto;
import com.ssic.catering.base.dto.AddressUserDto;
import com.ssic.catering.base.dto.Tree;
import com.ssic.catering.base.pojo.Address;
import com.ssic.catering.base.service.IAddressService;
import com.ssic.catering.base.service.IAddressUserService;
import com.ssic.catering.base.util.MyComparator;
import com.ssic.game.common.dao.ProjectDao;
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.game.common.service.IImsUserService;
import com.ssic.util.BeanUtils;
import com.ssic.util.StringUtils;
import com.ssic.util.constants.DataStatus;

/**		
 * <p>Title: AddressServiceImpl </p>
 * <p>Description: 区域service实现层</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年8月7日 上午11:34:39	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年8月7日 上午11:34:39</p>
 * <p>修改备注：</p>
 */
@Service
public class AddressServiceImpl implements IAddressService
{

    private static final Logger log = Logger.getLogger(AddressServiceImpl.class);
    
    @Autowired
    private AddressDao addressDao;
    @Autowired
    private IAddressUserService addressUserService;
    @Autowired
    private IImsUserService iImsUserService;
    @Autowired
    private ProjectDao projectDao;

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.IAddressService#findById(java.lang.String)   
    */
    @Override
    public AddressDto findById(String id)
    {
        Address add = addressDao.findById(id);
        AddressDto addressDto = new AddressDto();
        BeanUtils.copyProperties(add, addressDto);
        addressDto.setPid(add.getParentId());
        return addressDto;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.IAddressService#findAll()   
    */
    @Override
    public List<AddressDto> findAll()
    {
        List<AddressDto> result = new ArrayList<AddressDto>();
        Address add = new Address();
        List<Address> list = addressDao.findAllBy(add, null);
        if (!CollectionUtils.isEmpty(list))
        {
            result = BeanUtils.createBeanListByTarget(list, AddressDto.class);
        }
        return result;
    }

    public List<AddressDto> findAllByProject(String projectId)
    {
        List<AddressDto> result = new ArrayList<AddressDto>();
        Address add = new Address();
        add.setProjId(projectId);
        List<Address> list = addressDao.findAllBy(add, null);
        if (!CollectionUtils.isEmpty(list))
        {
            result = BeanUtils.createBeanListByTarget(list, AddressDto.class);
        }
        return result;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.IAddressService#insert(com.ssic.catering.base.dto.AddressDto)   
    */
    @Override
    public void insert(AddressDto addressDto)
    {
        Address add = new Address();
        BeanUtils.copyProperties(addressDto, add);
        add.setParentId(addressDto.getPid());
        add.setCreateTime(new Date());
        add.setStat(DataStatus.ENABLED);
        addressDao.insert(add);
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.IAddressService#update(com.ssic.catering.base.dto.AddressDto)   
    */
    @Override
    public void update(AddressDto addressDto)
    {
        Address add = new Address();
        BeanUtils.copyProperties(addressDto, add);
        add.setCreateTime(addressDto.getCreateTime());
        add.setParentId(addressDto.getPid());
        add.setLastUpdateTime(new Date());
        add.setStat(DataStatus.ENABLED);
        addressDao.updateByPrimaryKey(add);

    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.IAddressService#delete(com.ssic.catering.base.dto.AddressDto)   
    */
    @Override
    public void delete(AddressDto addressDto)
    {
        Address add = new Address();
        BeanUtils.copyProperties(addressDto, add);
        add.setStat(DataStatus.DISABLED);
        addressDao.updateByPrimaryKey(add);

    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.IAddressService#vailDeptName(com.ssic.catering.base.dto.AddressDto)   
    */
    @Override
    public int vailAddressName(AddressDto addressDto)
    {
        Address add = new Address();
        BeanUtils.copyProperties(addressDto, add);
        add.setParentId(addressDto.getPid());
        List<Address> list = addressDao.vailAddressName(add);
        if (!CollectionUtils.isEmpty(list))
        {
            return 1;
        }
        return 0;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.IAddressService#treeGrid(com.ssic.catering.base.dto.AddressDto)   
    */
    @Override
    public List<AddressDto> treeGrid(AddressDto addressDto)
    {
        List<AddressDto> dtoList = new ArrayList<AddressDto>();
        List<AddressDto> addressDtoList = new ArrayList<AddressDto>();
        if (!StringUtils.isEmpty(addressDto.getProjId()))
        {
            dtoList = findAllByProject(addressDto.getProjId());
        }
        else
        {
            dtoList = findAll();
        }
        if (dtoList != null && dtoList.size() > 0)
        {
            for (AddressDto addressResult : dtoList)
            {
                ProjectDto project = projectDao.findById(addressResult.getProjId());
                addressResult.setProjName(project.getProjName());
                addressResult.setIconCls("bin");
                if (!StringUtils.isEmpty(addressResult.getParentId()))
                {
                    Address addredto = addressDao.findById(addressResult.getParentId());
                    addressResult.setPid(addressResult.getParentId());
                    addressResult.setPname(addredto.getAddressName());
                }
                if (!StringUtils.isEmpty(addressResult.getParentId()))
                {
                    Address addredto = addressDao.findById(addressResult.getParentId());
                    addressResult.setPid(addressResult.getParentId());
                    addressResult.setPname(addredto.getAddressName());
                }
                //查找区域负责人
                if (!StringUtils.isEmpty(addressResult.getId()))
                {
                    /*   AddressUserDto addressUserDto =
                           addressUserService.finAddressUserByAddressId(addressResult.getId());*/
                    AddressUserDto address_User = new AddressUserDto();
                    address_User.setAddressId(addressResult.getId());
                    if (!StringUtils.isEmpty(addressDto.getProjId()))
                    {
                        address_User.setProjId(addressDto.getProjId());
                    }
                    List<AddressUserDto> addressUsers = addressUserService.findAll(address_User);
                    if (!CollectionUtils.isEmpty(addressUsers))
                    {

                        for (AddressUserDto usersDto : addressUsers)
                        {
                            //如果负责人类型是食堂负责人，则返回；
                            if (usersDto.getAddressType().equals(4))
                            {
                                continue;
                            }
                            if (usersDto != null && !StringUtils.isEmpty(usersDto.getUserId()))
                            {
                                ImsUsersDto userDto = iImsUserService.findByUserId(usersDto.getUserId());
                                if (userDto != null && !StringUtils.isEmpty(userDto.getName()))
                                {
                                    addressResult.setUserName(userDto.getName());
                                    addressResult.setUserId(userDto.getId());
                                }
                            }
                        }
                    }
                }

                if (addressResult.getAddressCode().length() == 8)
                {//如果区域编码的长度为8，则为叶子节点
                    addressResult.setIsLeaf(1);
                }
                else
                {
                    addressResult.setIsLeaf(0);
                }
                addressDtoList.add(addressResult);

            }
        }
        Collections.sort(addressDtoList, new MyComparator());
        return addressDtoList;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.IAddressService#findChildListByParentId(java.lang.String)   
    */
    @Override
    public List<AddressDto> findChildListByParentId(String id)
    {
        List<AddressDto> result = new ArrayList<AddressDto>();
        List<Address> addressDtoList = addressDao.findChildListByParentId(id);
        if (!CollectionUtils.isEmpty(addressDtoList))
        {
            result = BeanUtils.createBeanListByTarget(addressDtoList, AddressDto.class);
        }
        return result;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.IAddressService#validDeptRootExists(java.lang.String, java.lang.String)   
    */
    @Override
    public AddressDto validAddressRootExists(AddressDto addressDto)
    {
        return addressDao.validAddressRootExists(addressDto);

    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.IAddressService#setAddressCode(com.ssic.catering.base.dto.AddressDto)   
    */
    @Override
    public void setAddressCode(AddressDto address)
    {
        //当前对象如果移动了，则需要重新更新他原来父节点下的子节点的部门编码信息;
        //当前部门对象;
        Address dto = addressDao.findById(address.getId());
        if (null != dto)
        {
            //当前部门原来的父部门对象;
            Address dto2 = addressDao.findById(dto.getParentId());
            if (dto2 != null)
            {
                //当前部门原来的父部门对象编码;
                String lastParentDeptCode = dto2.getAddressCode();
                //当前部门原来的父部门对象下的子部门集合;
                List<Address> dtoLists = addressDao.findChildListByParentId(dto.getParentId());
                List<AddressDto> newDtoList = new ArrayList<AddressDto>();
                for (Address dtos : dtoLists)
                {
                    addressDao.findChildListByParentId(dtos.getId());
                    if (!dtos.getId().equals(address.getId()))
                    {
                        AddressDto d = new AddressDto();
                        BeanUtils.copyProperties(dtos, d);
                        d.setPid(dtos.getParentId());
                        newDtoList.add(d);
                    }
                }
                if (!CollectionUtils.isEmpty(newDtoList))
                {
                    //如果原父部门下的子部门有3个（除当前部门外），则需要重新赋值deptCode;
                    //如：原部门下有:0001 0002 0003这3个，现在移动0002,则0001 0003需要变为0001,0002 ;
                    //如 原来父部门编码是00，有3个子部门，现在循环（3-1）次，当前子部门编码为："00"+(01)=0001,"00"+(02)=0002;
                    for (int i = 0; i < newDtoList.size(); i++)
                    {
                        AddressDto addressDto = newDtoList.get(i);
                        String dCode = lastParentDeptCode + ("0" + (i + 1));
                        addressDto.setAddressCode(dCode);
                        addressDto.setStat(1);
                        update(addressDto);
                        //更新原父部门下的子部门的编码;
                        updateChildAddress(addressDto.getId(), dCode);
                    }
                }
            }
        }
        if (StringUtils.isEmpty(address.getPid()))
        {
            //查找当前部门编码
            String localDeptCode = getRootDeptCode();
            address.setAddressCode(localDeptCode);
        } //如果不是父节点
        else
        { //如果当前节点是3级节点第2条：如00（1级）01（2级）02（当前），则父节点是0001;如果父节点下有3个子节点，
          //则第一个是000101，第二个是000102,第三个是000103;当前添加的则应该是父节点的第4个节点:000104;
            String parentDeptCode = addressDao.findById(address.getPid()).getAddressCode();
            String resultDeptCode = null;
            //查找父节点下的子节点;
            List<Address> dtoList = addressDao.findChildListByParentId(address.getPid());
            //List<DeptDto> dtoList2 = deptDao.findChildListByParentId(dept.getId());
            if (!CollectionUtils.isEmpty(dtoList))
            { //当前的code;
                String localDeptCode = "0" + String.valueOf(dtoList.size() + 1);
                resultDeptCode = parentDeptCode + localDeptCode;
                address.setAddressCode(resultDeptCode);
            }
            else
            {
                resultDeptCode = parentDeptCode + "01";
                address.setAddressCode(resultDeptCode);
            }
            //更新当前部门下的子部门的编码;
            updateChildAddress(address.getId(), resultDeptCode);
        }

    }

    public void updateChildAddress(String addressId, String parentAddressCode)
    {
        List<Address> childs = addressDao.findChildListByParentId(addressId);
        if (!CollectionUtils.isEmpty(childs))
        {
            for (int j = 0; j < childs.size(); j++)
            {
                String lastParentCode = parentAddressCode + ("0" + (j + 1));
                Address addressChild = childs.get(j);
                addressChild.setAddressCode(lastParentCode);
                addressChild.setStat(1);
                addressDao.updateByPrimaryKey(addressChild);
                updateChildAddress(addressChild.getId(), lastParentCode);

            }
        }
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
    @Override
    public Address findAddressById(String id)
    {
        return addressDao.findAddressById(id);
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
    @Override
    public List<Address> findAddressListByAddressCode(String addressCode)
    {

        return addressDao.findAddressListByAddressCode(addressCode);
    }

    public String getRootDeptCode()
    {
        String defaultCode = "01";
        List<AddressDto> dtoList = addressDao.findCodeByLastCreateTime();
        if (!CollectionUtils.isEmpty(dtoList))
        {//如果存在根节点的deptCode,如：08；则当前应给部门的部门编码为09
            String lastAddressCode = dtoList.get(0).getAddressCode();

            if (Integer.valueOf(lastAddressCode) < 9)
            {
                defaultCode = "0" + (Integer.valueOf(lastAddressCode) + 1);
            }
            else
            {
                defaultCode = String.valueOf((Integer.valueOf(lastAddressCode) + 1));
            }
        }
        return defaultCode;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.IAddressService#tree(java.lang.String)   
    */
    @Override
    public List<Tree> tree(String addressId, String projId)
    {
        List<AddressDto> deptDtoList = new ArrayList<AddressDto>();
        if (StringUtils.isEmpty(projId))
        {
            deptDtoList = findAll();
        }
        else
        {
            deptDtoList = findAllByProject(projId);
        }
        List<Address> filterDtoList = new ArrayList<Address>();
        List<Tree> lt = new ArrayList<Tree>();
        Map<String, AddressDto> maps = new HashMap<String, AddressDto>();
        for (AddressDto dto : deptDtoList)
        {
            maps.put(dto.getId(), dto);
        }

        if (!StringUtils.isEmpty(addressId))
        {
            Address localdto = addressDao.findById(addressId);
            //查找过滤不包括当前deptId的集合;
            List<Address> needFilterList = findChild(addressId, filterDtoList);
            needFilterList.add(localdto);
            for (Address dto : needFilterList)
            {
                if (maps.containsKey(dto.getId()))
                {
                    maps.remove(dto.getId());
                }
            }

        }
        if (!maps.isEmpty())
        {
            for (String id : maps.keySet())
            {
                Address deptDto = addressDao.findById(id);
                Tree tree = new Tree();
                BeanUtils.copyProperties(deptDto, tree);
                if (!StringUtils.isEmpty(deptDto.getParentId()))
                {
                    tree.setPid(deptDto.getParentId());
                }
                tree.setText(deptDto.getAddressName());
                tree.setIconCls("bin");
                lt.add(tree);
            }
        }
        return lt;
    }

    public List<Address> findChild(String parentId, List<Address> resultList)
    {
        List<Address> childList = addressDao.findChildListByParentId(parentId);
        if (!CollectionUtils.isEmpty(childList))
        {
            for (Address dto : childList)
            {
                resultList.add(dto);
                findChild(dto.getId(), resultList);
            }
        }
        return resultList;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.IAddressService#queryAddressId(java.lang.String)
    * @author pengcheng.yang   
    */
    @Override
    public AddressDto queryAddressId(String userId)
    {
        if (!StringUtils.isEmpty(userId))
        {
            return addressDao.queryAddressId(userId);
        }
        return null;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.IAddressService#queryAddressIdAndParentIds(java.lang.String)   
    * @author pengcheng.yang
    */
    @Override
    public List<AddressDto> queryAddressIdAndParentIds(String parentId)
    {
        if (!StringUtils.isEmpty(parentId))
        {
            return addressDao.queryAddressIdAndParentIds(parentId);
        }
        return null;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.IAddressService#queryCityId(java.lang.String)   
    * @author pengcheng.yang
    */
    @Override
    public List<AddressDto> queryCityId(String cityId)
    {
        if (!StringUtils.isEmpty(cityId))
        {
            return addressDao.queryCityId(cityId);
        }
        return null;
    }

    @Override
    public List<Address> findAddressByTypeAndAddressCode(Integer type, String addressCode, String projId)
    {
        return addressDao.findAddressByTypeAndAddressCode(type, addressCode, projId);
    }

    /**
     * findAddressByAddressCode：通过区域辅助码获取区域信息
     * @param addressCode 区域辅助码
     * @return
     * @exception	
     * @author 张亚伟
     * @date 2015年8月26日 上午11:16:41
     */
    @Override
    public Address findAddressByAddressCode(String addressCode)
    {
        // TODO 添加方法注释
        return addressDao.findAddressByAddressCode(addressCode);
    }

    @Override
    public List<Address> findAddressByTypeAndAddressCode(Integer type, String addressCode)
    {
        // TODO Auto-generated method stub
        return addressDao.findAddressByTypeAndAddressCode(type, addressCode);
    }
    
  /**
   * 
   * (non-Javadoc)   
   * @see com.ssic.catering.base.service.IAddressService#findAddressesBy(com.ssic.catering.base.dto.AddressDto)
   */
    @Override
    public List<AddressDto> findAddressesBy(AddressDto address)
    {
        if(address == null)
        {
            log.error("参数AddressDto不能为空");
            return null;
        }
        
        Address param = new Address();
        BeanUtils.copyProperties(address, param);
        
        List<Address> result = addressDao.findAddressesBy(param);
        if(!CollectionUtils.isEmpty(result))
        {
            return BeanUtils.createBeanListByTarget(result, AddressDto.class);
        }
        
        return null;
    }
}
