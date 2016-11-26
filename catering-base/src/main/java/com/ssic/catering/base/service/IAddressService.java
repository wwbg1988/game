/**
 * 
 */
package com.ssic.catering.base.service;

import java.util.List;

import com.ssic.catering.base.dto.AddressDto;
import com.ssic.catering.base.dto.Tree;
import com.ssic.catering.base.pojo.Address;

/**		
 * <p>Title: IAddressService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年8月7日 上午11:34:27	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年8月7日 上午11:34:27</p>
 * <p>修改备注：</p>
 */
public interface IAddressService
{

    /**     
     * findById：一句话描述方法功能
     * @param id
     * @return
     * @exception   
     * @author Administrator
     * @date 2015年7月11日 上午10:12:33   
     */
    public AddressDto findById(String id);

    public List<AddressDto> findAll();


    public List<AddressDto> findAllByProject(String projectId);

    public void insert(AddressDto addressDto);

    public void update(AddressDto addressDto);

    public void delete(AddressDto addressDto);

    public int vailAddressName(AddressDto tempDept);

    /**     
      * treeGrid：一句话描述方法功能
      * @param deptDto 
      * @return
      * @exception   
      * @author Administrator
      * @date 2015年7月8日 上午10:19:20    
      */
    public List<AddressDto> treeGrid(AddressDto addressDto);

    public List<AddressDto> findChildListByParentId(String id);

    /**     
     * validAddressRootExists：一句话描述方法功能
     * @param id 
     * @param projId 
     * @return
     * @exception   
     * @author Administrator
     * @date 2015年7月8日 下午3:11:19     
     */
    public AddressDto validAddressRootExists(AddressDto addressDto);

    /**     
     * setDeptCode：一句话描述方法功能
     * @param dept
     * @return
     * @exception   
     * @author Administrator
     * @date 2015年7月8日 下午3:21:39     
     */
    public void setAddressCode(AddressDto addressDto);

    /**
     * 
     * findAddressById：通过区域ID获取区域信息
     * @param id 区域id
     * @return 区域信息
     * @exception	
     * @author 张亚伟
     * @date 2015年8月10日 上午9:16:51
     */
    public Address findAddressById(String id);

    /**
     * 
     * findAddressListByAddressCode：通过区域辅助码获取所有相关联的区域
     * @param addressCode 地区辅助码
     * @return
     * @exception	
     * @author 张亚伟
     * @date 2015年8月10日 上午9:27:56
     */
    public List<Address> findAddressListByAddressCode(String addressCode);

    /**     
     * tree：一句话描述方法功能
     * @param addressId
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年8月11日 下午3:04:03	 
     */
    List<Tree> tree(String addressId, String projId);

    /**
     * 
     * queryAddressId：根据用户ID查询addressId
     * @param userId
     * @return
     * @exception   
     * @author pengcheng.yang
     * @date 2015年8月13日 下午12:20:48
     */
    AddressDto queryAddressId(String userId);

    /**
     * 
     * queryAddressIdAndParentIds：更具addressId查询下面的子Id
     * @param parentId
     * @return
     * @exception   
     * @author pengcheng.yang
     * @date 2015年8月13日 下午12:23:24
     */
    List<AddressDto> queryAddressIdAndParentIds(String parentId);

	/**
	 * findAddressByTypeAndAddressCode：通过区域辅助码获取区域列表
	 * @param type 获取类型(1为获取区域平级,2为获取区域下级)
	 * @param addressCode 区域辅助码
	 * @return
	 * @exception	
	 * @author 张亚伟
	 * @date 2015年8月25日 下午1:19:33
	 */
	List<Address> findAddressByTypeAndAddressCode(Integer type,String addressCode,String projId);
	
	/**
     * queryCityId：查询城市Id
     * @param parentId
     * @return
     * @exception   
     * @author pengcheng.yang
     * @date 2015年8月13日 下午5:38:52
     */
    List<AddressDto> queryCityId(String cityId);

    /**
     * findAddressByTypeAndAddressCode：通过区域辅助码获取区域列表
     * @param type 获取类型(1为获取区域平级,2为获取区域下级)
     * @param addressCode 区域辅助码
     * @return
     * @exception	
     * @author 张亚伟
     * @date 2015年8月25日 下午1:19:33
     */
    List<Address> findAddressByTypeAndAddressCode(Integer type, String addressCode);

    /**
     * findAddressByAddressCode：通过区域辅助码获取区域信息
     * @param addressCode 区域辅助码
     * @return
     * @exception	
     * @author 张亚伟
     * @date 2015年8月26日 上午11:16:41
     */
    Address findAddressByAddressCode(String addressCode);
    
    
    /**
     * 根据参数查询地区	 
     * @author 朱振	
     * @date 2015年10月31日 下午1:10:02	
     * @version 1.0
     * @param address
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年10月31日 下午1:10:02</p>
     * <p>修改备注：</p>
     */
    List<AddressDto> findAddressesBy(AddressDto address);

}
