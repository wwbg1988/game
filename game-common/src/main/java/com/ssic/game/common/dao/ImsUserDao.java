/**   
 * bare_field_name   
 * com.ssic.game.common.dao	
 * @return  the bare_field_name 
 */

package com.ssic.game.common.dao;

import java.util.Date;
import java.util.List;

import lombok.Getter;

import org.apache.log4j.Logger;
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
import com.ssic.util.UUIDGenerator;
import com.ssic.util.base.MyBatisBaseDao;
import com.ssic.util.constants.DataStatus;

/**		
 * <p>Title: ActionsDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年6月24日 上午9:03:59	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年6月24日 上午9:03:59</p>
 * <p>修改备注：</p>
 */
@Repository
public class ImsUserDao extends MyBatisBaseDao<ImsUsers> {
	//对应表t_ims_action_role
    private static final Logger log = Logger.getLogger(ImsUsersDto.class);
    
	@Autowired
	@Getter
	private ImsUsersMapper mapper;
	@Autowired
	private ImsUsersExMapper imsUsersExMapper;

	public List<ImsUsers> findUserAll(ImsUsers param) {
		ImsUsersExample example = new ImsUsersExample();
		Criteria criteria = example.createCriteria();
		//        if(!StringUtils.isEmpty(param.getDeptId())){
		//            criteria.andDeptIdEqualTo(param.getDeptId());
		//        }
		if (!StringUtils.isEmpty(param.getName())) {
			criteria.andNameLike("%" + param.getName() + "%");
		}
		if (!StringUtils.isEmpty(param.getId())) {
			criteria.andIdEqualTo(param.getId());
		}

		criteria.andStatEqualTo(1);

		return mapper.selectByExample(example);
	}

	public List<ImsUsers> findUserNotBy(ImsUsers param, List<String> userIds) {
		ImsUsersExample example = new ImsUsersExample();
		Criteria criteria = example.createCriteria();
		//        if(!StringUtils.isEmpty(param.getDeptId())){
		//            criteria.andDeptIdEqualTo(param.getDeptId());
		//        }
		if (!StringUtils.isEmpty(param.getName())) {
			criteria.andNameLike("%" + param.getName() + "%");
		}
		if (!StringUtils.isEmpty(param.getId())) {
			criteria.andIdEqualTo(param.getId());
		}
		if (userIds != null && userIds.size() > 0) {
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
	 * @author rkzhang
	 * @date 2015年6月30日 下午2:07:25	 
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
	 * @author 刘博
	 * @date 2015年6月30日 下午2:07:25    
	 */
	public ImsUsers findByUserId(String userId) {
		ImsUsersExample example = new ImsUsersExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(userId);
		criteria.andStatEqualTo(DataStatus.ENABLED);
		List<ImsUsers> results = mapper.selectByExample(example);
		return CollectionUtils.isEmpty(results) ? null : results.get(0);
	}

	public List<ImsUsersDto> findUsers(ImsUsersDto imsUsersDto) {

		return imsUsersExMapper.findUsers(imsUsersDto);
	}

	public void insertUser(ImsUsersDto imsUsersDto) {
		imsUsersExMapper.insertUser(imsUsersDto);
	}

	public void updateUser(ImsUsersDto imsUsersDto) {
		imsUsersExMapper.updateUser(imsUsersDto);
	}

	public void deleteUser(ImsUsersDto imsUsersDto) {
		imsUsersExMapper.deleteUser(imsUsersDto);
	}

	public List<ImsUsersDto> findUsersAll(ImsUsersDto imsUsersDto,
			PageHelperDto ph) {
		return imsUsersExMapper.findUsersAll(imsUsersDto, ph);
	}

	public int findCount(ImsUsersDto imsUsersDto) {
		return imsUsersExMapper.findCount(imsUsersDto);
	}

	public int vailUserAccount(ImsUsersDto imsUsersDto) {
		return imsUsersExMapper.vailUserAccount(imsUsersDto);
	}

	public List<String> findRoleId(String userId) {
		return imsUsersExMapper.findRoleId(userId);
	}

	public void insertRole(List<String> roles, String ids) {
		String userId = ids;
		for (int i = 0; i < roles.size(); i++) {
			String roleId = roles.get(i);
			RoleUsersDto roleUsersDto = new RoleUsersDto();
			roleUsersDto.setUserId(userId);
			roleUsersDto.setRoleId(roleId);
			roleUsersDto.setId(UUIDGenerator.getUUID());
			roleUsersDto.setStat(1);
			roleUsersDto.setCreateTime(new Date());
			imsUsersExMapper.insertRoleUsers(roleUsersDto);
		}

	}

	public void deleteRole(String userId) {
		imsUsersExMapper.deleteRole(userId);
	}

	public void upPasswod(ImsUsersDto imsUsersDto) {
		imsUsersExMapper.upPasswod(imsUsersDto);
	}

	public void deleteproUser(ImsUsersDto imsUsersDto) {
		imsUsersExMapper.deleteproUser(imsUsersDto);
	}

	public void updateDept(DeptUsersDto deptUsersDto) {
		imsUsersExMapper.updateDept(deptUsersDto);
	}

	public void deleteDept(DeptUsersDto deptUsersDto) {
		imsUsersExMapper.deleteDept(deptUsersDto);
	}

	/**
	 * findImsUsersToWeixin：通过微信用户唯一标识获取用户信息
	 * @param openId 微信唯一标识
	 * @return
	 * @exception	
	 * @author 张亚伟
	 * @date 2015年8月13日 上午11:41:20
	 */
	public ImsUsers findImsUsersToWeixin(String openId) {
		ImsUsersExample example = new ImsUsersExample();
		Criteria criteria = example.createCriteria();
		criteria.andWeixinEqualTo(openId);
		List<ImsUsers> list = mapper.selectByExample(example);
		if ( list!= null&&list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * findImsUserByUserAccount：通过账号获取用户信息
	 * @param userAccount
	 * @return
	 * @exception	
	 * @author 张亚伟
	 * @date 2015年8月13日 下午1:50:30
	 */
	public ImsUsers findImsUserByUserAccount(String userAccount) {
		ImsUsersExample example = new ImsUsersExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(userAccount);
		//criteria.andUserAccountEqualTo(userAccount);
		List<ImsUsers> list = mapper.selectByExample(example);
		return list != null ? list.get(0) : null;
	}

	/**
	 * updateImsUsers：更新用户信息
	 * @param imsUsers
	 * @return
	 * @exception	
	 * @author 张亚伟
	 * @date 2015年8月13日 下午2:33:00
	 */
	public Integer updateImsUsers(ImsUsers imsUsers) {
		return mapper.updateByPrimaryKeySelective(imsUsers);
	}
	
	/**
	 * 	 
	 * @author 朱振	
	 * @date 2015年10月29日 下午2:47:44	
	 * @version 1.0
	 * @param imsUsers
	 * @return
	 * <p>修改人：朱振</p>
	 * <p>修改时间：2015年10月29日 下午2:47:44</p>
	 * <p>修改备注：</p>
	 */
	public List<ImsUsers> findBy(ImsUsers param)
	{
	    if(param == null)
	    {
	        log.error("不支持空参数查询");
	        return null;
	    }
	    
	    ImsUsersExample example = new ImsUsersExample();
	    ImsUsersExample.Criteria criteria = example.createCriteria();
	    
	  //id
	    if(!StringUtils.isEmpty(param.getId()))
	    {
	      criteria.andIdEqualTo(param.getId());
	    }
	    //age
	    if(null != param.getAge())
	    {
	      criteria.andAgeEqualTo(param.getAge());
	    }
	    //createdatetime
	    if(!StringUtils.isEmpty(param.getCreatedatetime()))
	    {
	      criteria.andCreatedatetimeEqualTo(param.getCreatedatetime());
	    }
	    //email
	    if(!StringUtils.isEmpty(param.getEmail()))
	    {
	      criteria.andEmailEqualTo(param.getEmail());
	    }
	    //gender
	    if(null != param.getGender())
	    {
	      criteria.andGenderEqualTo(param.getGender());
	    }
	    //isadmin
	    if(null != param.getIsadmin())
	    {
	      criteria.andIsadminEqualTo(param.getIsadmin());
	    }
	    //modifydatetime
	    if(!StringUtils.isEmpty(param.getModifydatetime()))
	    {
	      criteria.andModifydatetimeEqualTo(param.getModifydatetime());
	    }
	    //name
	    if(!StringUtils.isEmpty(param.getName()))
	    {
	      criteria.andNameEqualTo(param.getName());
	    }
	    //password
	    if(!StringUtils.isEmpty(param.getPassword()))
	    {
	      criteria.andPasswordEqualTo(param.getPassword());
	    }
	    //qjyAccount
	    if(!StringUtils.isEmpty(param.getQjyAccount()))
	    {
	      criteria.andQjyAccountEqualTo(param.getQjyAccount());
	    }
	    //userAccount
	    if(!StringUtils.isEmpty(param.getUserAccount()))
	    {
	      criteria.andUserAccountEqualTo(param.getUserAccount());
	    }
	    //userNo
	    if(!StringUtils.isEmpty(param.getUserNo()))
	    {
	      criteria.andUserNoEqualTo(param.getUserNo());
	    }
//	    //stat
//	    if(null != param.getStat())
//	    {
//	      criteria.andStatEqualTo(param.getStat());
//	    }
	    //isdelete
	    if(null != param.getIsdelete())
	    {
	      criteria.andIsdeleteEqualTo(param.getIsdelete());
	    }
	    //mobilePhone
	    if(!StringUtils.isEmpty(param.getMobilePhone()))
	    {
	      criteria.andMobilePhoneEqualTo(param.getMobilePhone());
	    }
	    //weixin
	    if(!StringUtils.isEmpty(param.getWeixin()))
	    {
	      criteria.andWeixinEqualTo(param.getWeixin());
	    }
	    //identifyingCode
	    if(!StringUtils.isEmpty(param.getIdentifyingCode()))
	    {
	      criteria.andIdentifyingCodeEqualTo(param.getIdentifyingCode());
	    }
	    //mobileState
	    if(null != param.getMobileState())
	    {
	      criteria.andMobileStateEqualTo(param.getMobileState());
	    }
	    
	    criteria.andStatEqualTo(DataStatus.ENABLED);

	    example.setOrderByClause("createdatetime desc");
	    
	    return mapper.selectByExample(example);
	}
	
	public List<ImsUsersDto> findByProjId(String projId){
		return imsUsersExMapper.findByProjId(projId);
	}
	
	
	public List<ImsUsersDto> findBySearchName(ImsUsersDto imsUsersDto){
		imsUsersDto.setSearchName("%"+imsUsersDto.getSearchName()+"%");
		return imsUsersExMapper.findBySearchName(imsUsersDto);
	}
}
