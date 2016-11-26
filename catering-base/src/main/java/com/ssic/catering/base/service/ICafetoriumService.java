/**
 * 
 */
package com.ssic.catering.base.service;

import java.util.List;

import com.ssic.catering.base.dto.CafetoriumDto;
import com.ssic.catering.base.dto.PageHelperDto;

/**		
 * <p>Title: ICafetoriumService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年8月6日 下午4:15:45	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年8月6日 下午4:15:45</p>
 * <p>修改备注：</p>
 */
public interface ICafetoriumService
{

    
    /**     
     * findALL：一句话描述方法功能
     * @param cafetoriumDto
     * @param phDto
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年8月6日 下午4:26:51	 
     */
    List<CafetoriumDto> findALL(CafetoriumDto cafetoriumDto, PageHelperDto phDto);

    
    /**     
     * findCount：一句话描述方法功能
     * @param cafetoriumDto
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年8月6日 下午4:27:03	 
     */
    int findCount(CafetoriumDto cafetoriumDto);


    
    /**     
     * add：一句话描述方法功能
     * @param cafetoriumDto
     * @exception	
     * @author Administrator
     * @date 2015年8月6日 下午4:27:36	 
     */
    void add(CafetoriumDto cafetoriumDto);


    
    /**     
     * findById：一句话描述方法功能
     * @param id
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年8月6日 下午4:37:05	 
     */
    CafetoriumDto findById(String id);


    
    /**     
     * delete：一句话描述方法功能
     * @param cafetoriumDto
     * @exception	
     * @author Administrator
     * @date 2015年8月6日 下午4:38:14	 
     */
    void delete(CafetoriumDto cafetoriumDto);


    
    /**     
     * update：一句话描述方法功能
     * @param cafetoriumDto
     * @exception	
     * @author Administrator
     * @date 2015年8月6日 下午4:56:59	 
     */
    void update(CafetoriumDto cafetoriumDto);
    
    /**
     * 
     * totalWarningMessages：查询每个餐厅当天预警消息的总数
     * @param cafetoriumId
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月10日 下午2:55:39
     */
    int totalWarningMessages(String cafetoriumId);
    
    /**
     * 
     * findCafetoriumListByAddressId：通过区域id获取食堂列表,并封装每个食堂的平均分数
     * @param addressId 区域Id
     * @return
     * @exception	
     * @author 张亚伟
     * @date 2015年8月11日 上午9:07:57
     */
    List<CafetoriumDto> findCafetoriumListByAddressId(String addressId);


    
    /**     
     * getNewCafeCode：获取当前最新的食堂编码
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年8月18日 下午1:01:24	 
     */
    String getNewCafeCode(String projId);


    /**
	 * findCafetoriumAll：获取所有食堂和食堂的平均分
	 * @return
	 * @exception
	 * @author 张亚伟
	 * @date 2015年8月26日 下午4:02:09
	 */
	List<CafetoriumDto> findCafetoriumAll();
	
	/**
	 * 
	 * countCafetoriumByCompanyId：查询公司下面是否有食堂
	 * @param companyId
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年10月21日 下午4:24:14
	 */
	int countCafetoriumByCompanyId(String companyId);
	
	/**
	 * 
	 * CafetoriumByProJectId：根据proId查询相关食堂信息
	 * @param proId
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年10月27日 上午10:50:49
	 */
	List<String> CafetoriumByProJectId(String userId);
	
	/**
	 * 
	 * CafetoriumByProjId：查询当前用户拥有的projId
	 * @param userId
	 * @return
	 * @exception	
	 * @author pengcheng.yang
	 * @date 2015年10月28日 上午9:49:12
	 */
	List<String> CafetoriumByProjId(String userId);


    
    /**     
     * findByName：通过食堂名称查找食堂
     * @param cafeName
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年10月29日 上午11:45:25	 
     */
    CafetoriumDto findByName(String cafeName,String projId);
    
    /**
     * 	 
     * @author 朱振	
     * @date 2015年10月31日 下午2:00:18	
     * @version 1.0
     * @param cafetorium 查询条件
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年10月31日 下午2:00:18</p>
     * <p>修改备注：</p>
     */
    List<CafetoriumDto> findCafetoriumsBy(CafetoriumDto cafetorium);
    
    
    /**
     * 根据projectId和openId查找出用户的关注列表<BR> 	 
     * @author 朱振	
     * @date 2015年11月2日 上午9:44:51	
     * @version 1.0
     * @param openId 不能为空
     * @param projectId 不能为空
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月2日 上午9:44:51</p>
     * <p>修改备注：</p>
     */
    List<CafetoriumDto> getFollowedCafetoriumsByWeiXinIdAndProjectId(String openId, String projectId);
    
    
    /**
     * openId查找出用户的关注列表<BR>      
     * @author 朱振   
     * @date 2015年11月2日 上午9:44:51   
     * @version 1.0
     * @param openId 不能为空
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月2日 上午9:44:51</p>
     * <p>修改备注：</p>
     */
    List<CafetoriumDto> getFollowedCafetoriumsByWeiXinId(String openId);
}

