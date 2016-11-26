/**   
 * bare_field_name   
 * com.ssic.game.common.dao	
 * @return  the bare_field_name 
 */

package com.ssic.game.common.dao;

import java.util.Date;
import java.util.List;

import lombok.Getter;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ssic.game.common.dto.DeptUsersDto;
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.dto.PageHelperDto;
import com.ssic.game.common.dto.RoleUsersDto;
import com.ssic.game.common.mapper.ImsUsersExMapper;
import com.ssic.game.common.mapper.ImsUsersMapper;
import com.ssic.game.common.pojo.ImsUsers;
import com.ssic.game.common.pojo.ImsUsersExample;
import com.ssic.game.common.pojo.ImsUsersExample.Criteria;
import com.ssic.util.BeanUtils;
import com.ssic.util.UUIDGenerator;
import com.ssic.util.base.MyBatisBaseDao;
import com.ssic.util.constants.DataStatus;


/**		
 * <p>Title: ActionsDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author yuanbin	
 * @date 2015年8月8日 上午9:57:59	
 * @version 1.0
 * <p>修改人：yuanbin</p>
 * <p>修改时间：2015年8月8日 上午9:57:59</p>
 * <p>修改备注：</p>
 */
@Repository
public class CateringUserDao extends MyBatisBaseDao<ImsUsers>
{

    @Autowired
    @Getter
    private ImsUsersMapper mapper;
    @Autowired
    private ImsUsersExMapper imsUsersExMapper;

  
    public List<ImsUsers> findUserAll(ImsUsers param){
        ImsUsersExample example = new ImsUsersExample();
        Criteria criteria = example.createCriteria();
//        if(!StringUtils.isEmpty(param.getDeptId())){
//            criteria.andDeptIdEqualTo(param.getDeptId());
//        }
        if(!StringUtils.isEmpty(param.getName())){
            criteria.andNameLike("%"+param.getName()+"%");
        }
        if(!StringUtils.isEmpty(param.getId())){
            criteria.andIdEqualTo(param.getId());
        }
        
        criteria.andStatEqualTo(1);
        
        return mapper.selectByExample(example);
    }
    
    public List<ImsUsers> findUserNotBy(ImsUsers param,List<String> userIds){
        ImsUsersExample example = new ImsUsersExample();
        Criteria criteria = example.createCriteria();
//        if(!StringUtils.isEmpty(param.getDeptId())){
//            criteria.andDeptIdEqualTo(param.getDeptId());
//        }
        if(!StringUtils.isEmpty(param.getName())){
            criteria.andNameLike("%"+param.getName()+"%");
        }
        if(!StringUtils.isEmpty(param.getId())){
            criteria.andIdEqualTo(param.getId());
        }
        if(userIds!=null&&userIds.size()>0){
            criteria.andIdNotIn(userIds);
        }
        
        criteria.andStatEqualTo(1);
        
        return mapper.selectByExample(example);
    }

    
    /**     
     * findbyUserAccount：根据用户帐号查询用户
     * @param account
     * @return
     * @exception	
     * @author yuanbin
     * @date 2015年8月8日 上午10:09:25	 
     */
    public ImsUsers findbyUserAccount(String account) {
	ImsUsersExample example = new ImsUsersExample();
        Criteria criteria = example.createCriteria();
        criteria.andUserAccountEqualTo(account);
        criteria.andStatEqualTo(DataStatus.ENABLED);
        List<ImsUsers> results = mapper.selectByExample(example);
	return CollectionUtils.isEmpty(results) ? null : results.get(0);
    }
   
    /**     
     * findbyUserAccount：根据用户ID查询用户
     * @param account
     * @return
     * @exception   
     * @author yuanbin
     * @date 2015年8月12日 上午8:49:25    
     */
    public ImsUsers findByUserId(String userId) {
        ImsUsers result = mapper.selectByPrimaryKey(userId);
        
    return result;
    }
    
    public ImsUsersDto userAccountByUser(String userAccount){
		return imsUsersExMapper.userAccountByUser(userAccount);
    }
    
    public List<ImsUsersDto> findUsers(ImsUsersDto imsUsersDto){
    	
    	return imsUsersExMapper.findUsers(imsUsersDto);
    }
    
    public void insertUser(ImsUsersDto imsUsersDto){
    	imsUsersExMapper.insertUser(imsUsersDto);
    }
    
    public void updateUser(ImsUsersDto imsUsersDto) {
    	imsUsersExMapper.updateUser(imsUsersDto);
    }
    
    public void updateUserByuserId(ImsUsersDto imsUsersDto) {
    	imsUsersExMapper.updateUserByuserId(imsUsersDto);
    }
    
    public void deleteUser(ImsUsersDto imsUsersDto) {
    	imsUsersExMapper.deleteUser(imsUsersDto);
    }
    
    public List<ImsUsersDto> findUsersAll(ImsUsersDto imsUsersDto,PageHelperDto ph){
    	return imsUsersExMapper.findUsersAll(imsUsersDto,ph);
    }
    
    public int findCount(ImsUsersDto imsUsersDto){
    	return imsUsersExMapper.findCount(imsUsersDto);
    }
    
    public int vailUserAccount(ImsUsersDto imsUsersDto){
    	return imsUsersExMapper.vailUserAccount(imsUsersDto);
    }
    
    public List<String> findRoleId(String userId){
    	return imsUsersExMapper.findRoleId(userId);
    }
    
    public void insertRole(List<String> roles,String ids){
    	String userId = ids;
    	for(int i =0;i<roles.size();i++){
    		String roleId  = roles.get(i);
    		RoleUsersDto roleUsersDto = new RoleUsersDto();
    		roleUsersDto.setUserId(userId);
    		roleUsersDto.setRoleId(roleId);
    		roleUsersDto.setId(UUIDGenerator.getUUID());
    		roleUsersDto.setStat(1);
    		roleUsersDto.setCreateTime(new Date());
    		imsUsersExMapper.insertRoleUsers(roleUsersDto);
    	}
    	
    }
    
    public void deleteRole(String userId){
    	imsUsersExMapper.deleteRole(userId);
    }
    
    public void upPasswod(ImsUsersDto imsUsersDto){
    	imsUsersExMapper.upPasswod(imsUsersDto);
    }
    public void deleteproUser(ImsUsersDto imsUsersDto){
    	imsUsersExMapper.deleteproUser(imsUsersDto);
    }
    
    public void updateDept( DeptUsersDto deptUsersDto){
    	imsUsersExMapper.updateDept(deptUsersDto);
    }
    
    public void deleteDept(DeptUsersDto deptUsersDto){
    	imsUsersExMapper.deleteDept(deptUsersDto);
    }
    
    
    public void updateUserSelectiveById(ImsUsersDto imsUsersDto)
    {        
        ImsUsers record = new ImsUsers();
        BeanUtils.copyProperties(imsUsersDto, record);    
        
        mapper.updateByPrimaryKeySelective(record);
    }
}
