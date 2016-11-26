/**
 * 
 */
package com.ssic.catering.base.dao;

import java.util.List;

import lombok.Getter;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssic.catering.base.dto.CafetoriumDto;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.mapper.AvgScoreWarningExMapper;
import com.ssic.catering.base.mapper.CafetoriumExMapper;
import com.ssic.catering.base.mapper.CafetoriumMapper;
import com.ssic.catering.base.pojo.Cafetorium;
import com.ssic.catering.base.pojo.CafetoriumExample;
import com.ssic.catering.base.pojo.CafetoriumExample.Criteria;
import com.ssic.util.StringUtils;
import com.ssic.util.base.MyBatisBaseDao;
import com.ssic.util.constants.DataStatus;

/**		
 * <p>Title: CafetoriumDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年8月6日 下午3:58:35	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年8月6日 下午3:58:35</p>
 * <p>修改备注：</p>
 */
@Repository
public class CafetoriumDao extends MyBatisBaseDao<Cafetorium>
{

    @Autowired
    @Getter
    private CafetoriumMapper mapper;

    @Autowired
    private CafetoriumExMapper exMapper;

    @Autowired
    private AvgScoreWarningExMapper avgMapper;

    /**     
     * findAllBy：一句话描述方法功能
     * @param cartType
     * @return
     * @exception   
     * @author Administrator
     * @date 2015年8月4日 上午11:33:53    
     */
    public List<Cafetorium> findAllBy(Cafetorium param, PageHelperDto ph)
    {
        CafetoriumExample example = new CafetoriumExample();
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
        if (!StringUtils.isEmpty(param.getCafeName()))
        {
            criteria.andCafeNameLike("%" + param.getCafeName() + "%");
        }
        if (!StringUtils.isEmpty(param.getCafeCode()))
        {
            criteria.andCafeCodeEqualTo(param.getCafeCode());
        }
        if (!StringUtils.isEmpty(param.getAddress()))
        {
            criteria.andAddressLike("%" + param.getAddress() + "%");
        }
        if (!StringUtils.isEmpty(param.getMobileNo()))
        {
            criteria.andMobileNoLike("%" + param.getMobileNo() + "%");
        }
        if (!StringUtils.isEmpty(param.getCompanyId()))
        {
            criteria.andCompanyIdEqualTo(param.getCompanyId());
        }
        if (!StringUtils.isEmpty(param.getForkId()))
        {
            criteria.andForkIdEqualTo(param.getForkId());
        }
        if (!StringUtils.isEmpty(param.getAddress()))
        {
            criteria.andAddressEqualTo(param.getAddress());
        }
        if (!StringUtils.isEmpty(param.getAddressId()))
        {
            criteria.andAddressIdEqualTo(param.getAddressId());
        }
        if (!StringUtils.isEmpty(param.getProjId()))
        {
            criteria.andProjIdEqualTo(param.getProjId());
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return mapper.selectByExample(example);
    }

    public List<Cafetorium> findAllByNoPage(Cafetorium param)
    {
        CafetoriumExample example = new CafetoriumExample();
        Criteria criteria = example.createCriteria();
        criteria.andStatEqualTo(DataStatus.ENABLED);
        if (!StringUtils.isEmpty(param.getCafeCode()))
        {
            criteria.andCafeCodeEqualTo(param.getCafeCode());
        }
        if (!StringUtils.isEmpty(param.getProjId()))
        {
            criteria.andProjIdEqualTo(param.getProjId());
        }
        return mapper.selectByExample(example);
    }

    /**     
     * findCountBy：一句话描述方法功能
     * @param cartType
     * @return
     * @exception   
     * @author Administrator
     * @date 2015年8月4日 上午11:41:13    
     */
    public int findCountBy(Cafetorium param)
    {
        CafetoriumExample example = new CafetoriumExample();
        Criteria criteria = example.createCriteria();

        criteria.andStatEqualTo(DataStatus.ENABLED);

        if (!StringUtils.isEmpty(param.getId()))
        {
            criteria.andIdEqualTo(param.getId());
        }
        if (!StringUtils.isEmpty(param.getCafeName()))
        {
            criteria.andCafeNameLike("%" + param.getCafeName() + "%");
        }
        if (!StringUtils.isEmpty(param.getCompanyId()))
        {
            criteria.andCompanyIdEqualTo(param.getCompanyId());
        }
        if (!StringUtils.isEmpty(param.getForkId()))
        {
            criteria.andForkIdEqualTo(param.getForkId());
        }
        if (!StringUtils.isEmpty(param.getAddress()))
        {
            criteria.andAddressEqualTo(param.getAddress());
        }
        if (!StringUtils.isEmpty(param.getProjId()))
        {
            criteria.andProjIdEqualTo(param.getProjId());
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
    public Cafetorium findById(String id)
    {
        CafetoriumExample example = new CafetoriumExample();
        Criteria criteria = example.createCriteria();
        example.setDistinct(true);
        criteria.andStatEqualTo(DataStatus.ENABLED);

        if (!StringUtils.isEmpty(id))
        {
            criteria.andIdEqualTo(id);
        }
        return mapper.selectByPrimaryKey(id);
    }

    //通过区域ID获取这个区域下所有的食堂
    public List<Cafetorium> findCafetoriumListByaddressId(String addressId)
    {
        CafetoriumExample example = new CafetoriumExample();
        Criteria criteria = example.createCriteria();
        criteria.andAddressIdEqualTo(addressId);
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return mapper.selectByExample(example);
    }

    /**
     * 
     * totalWarningMessages：查询每个餐厅当天的预警消息
     * @param cafetoriumId
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年8月10日 下午2:51:10
     */
    public int totalWarningMessages(String cafetoriumId)
    {
        if (!StringUtils.isEmpty(cafetoriumId))
        {
            return avgMapper.totalWarningMessages(cafetoriumId);
        }
        return -1;
    }

    /**     
     * findCafeCodeByLastCreateTime：一句话描述方法功能
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年8月18日 下午2:38:10	 
     */
    public Cafetorium findCafeCodeByLastCreateTime()
    {
        CafetoriumExample example = new CafetoriumExample();
        Criteria criteria = example.createCriteria();
       
        criteria.andStatEqualTo(DataStatus.ENABLED);
        example.setOrderByClause("create_time desc");
        List<Cafetorium> list = mapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list))
        {
            return list.get(0);
        }
        return null;
    }

    /**
     * getCafetoriumList：通过区域Id获取本区域下所有的食堂
     * @param addressId 区域ID
     * @return
     * @exception	
     * @author 张亚伟
     * @date 2015年8月10日 下午8:47:31
     */
    public List<Cafetorium> findCafetoriumAll()
    {
        CafetoriumExample example = new CafetoriumExample();
        return mapper.selectByExample(example);
    }

    /**
     * 
     * CafetoriumByProJectId：根据proId查询相关食堂信息
     * @param proId
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年10月27日 上午10:46:44
     */
    public List<String> CafetoriumByProJectId(String userId)
    {
        if (userId != null || !"".equals(userId))
        {
            return exMapper.CafetoriumByProJectId(userId);
        }
        return null;
    }

    /**
     * 
     * CafetoriumByProJectId：查询当前用户拥有的projId
     * @param userId
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年10月28日 上午9:47:07
     */
    public List<String> CafetoriumByProjId(String userId)
    {
        if (userId != null || !"".equals(userId))
        {
            return exMapper.CafetoriumByProjId(userId);
        }
        return null;
    }

    /**
     * 
     * countCafetoriumByPjoId：根据proId查询当前
     * @param proId
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年10月21日 下午4:04:54
     */
    public int countCafetoriumByCompanyId(String companyId)
    {
        return exMapper.countCafetoriumByCompanyId(companyId);
    }

    /**     
     * findByName：通过食堂名称查找食堂
     * @param cafetorium
     * @return
     * @exception	
     * @author 刘博
     * @date 2015年10月29日 上午11:50:25	 
     */
    public List<Cafetorium> findByName(Cafetorium cafetorium)
    {
        CafetoriumExample example = new CafetoriumExample();
        Criteria criteria = example.createCriteria();
        criteria.andStatEqualTo(DataStatus.ENABLED);
        if (!StringUtils.isEmpty(cafetorium.getProjId()))
        {
            criteria.andProjIdEqualTo(cafetorium.getProjId());
        }
        if (!StringUtils.isEmpty(cafetorium.getCafeName()))
        {
            criteria.andCafeNameEqualTo(cafetorium.getCafeName());
        }
        return mapper.selectByExample(example);
    }
    
    /**
     *  
     * @author 朱振	
     * @date 2015年10月31日 下午2:04:05	
     * @version 1.0
     * @param param 查询条件   
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年10月31日 下午2:04:05</p>
     * <p>修改备注：</p>
     */
    public List<Cafetorium> findBy(Cafetorium param)
    {
        if(param == null)
        {
            return null;
        }
        
        CafetoriumExample example = new CafetoriumExample();
        CafetoriumExample.Criteria criteria = example.createCriteria();
        
      //id
        if(!StringUtils.isEmpty(param.getId()))
        {
          criteria.andIdEqualTo(param.getId());
        }
        //cafeName
        if(!StringUtils.isEmpty(param.getCafeName()))
        {
          criteria.andCafeNameEqualTo(param.getCafeName());
        }
        //cafeCode
        if(!StringUtils.isEmpty(param.getCafeCode()))
        {
          criteria.andCafeCodeEqualTo(param.getCafeCode());
        }
        //mobileNo
        if(!StringUtils.isEmpty(param.getMobileNo()))
        {
          criteria.andMobileNoEqualTo(param.getMobileNo());
        }
        //companyId
        if(!StringUtils.isEmpty(param.getCompanyId()))
        {
          criteria.andCompanyIdEqualTo(param.getCompanyId());
        }
        //forkId
        if(!StringUtils.isEmpty(param.getForkId()))
        {
          criteria.andForkIdEqualTo(param.getForkId());
        }
        //address
        if(!StringUtils.isEmpty(param.getAddress()))
        {
          criteria.andAddressEqualTo(param.getAddress());
        }
        //email
        if(!StringUtils.isEmpty(param.getEmail()))
        {
          criteria.andEmailEqualTo(param.getEmail());
        }
        //addressId
        if(!StringUtils.isEmpty(param.getAddressId()))
        {
          criteria.andAddressIdEqualTo(param.getAddressId());
        }
        //projId
        if(!StringUtils.isEmpty(param.getProjId()))
        {
          criteria.andProjIdEqualTo(param.getProjId());
        }
        //createUserId
        if(!StringUtils.isEmpty(param.getCreateUserId()))
        {
          criteria.andCreateUserIdEqualTo(param.getCreateUserId());
        }
        
        criteria.andStatEqualTo(DataStatus.ENABLED);
        
        example.setOrderByClause("create_time asc");
        
        return mapper.selectByExample(example);
    }
    
    /**
     * 根据当前食堂id和微信号查找followedCafetoriums<BR>	 
     * @author 朱振	
     * @date 2015年11月2日 上午9:46:42	
     * @version 1.0
     * @param openId
     * @param projectId
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月2日 上午9:46:42</p>
     * <p>修改备注：</p>
     */
    public List<CafetoriumDto> findFollowedCafetoriumsByWeiXinIdAndProjectId(String openId, String projectId)
    {
        return exMapper.findFollowedCafetoriumsByWeiXinIdAndProjectId(openId, projectId);
    }
    
    
    /**
     * 根据微信号查找followedCafetoriums<BR>    	 
     * @author 朱振	
     * @date 2015年11月12日 上午10:20:40	
     * @version 1.0
     * @param openId
     * @param projectId
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月12日 上午10:20:40</p>
     * <p>修改备注：</p>
     */
    public List<CafetoriumDto> findFollowedCafetoriumsByWeiXinId(String openId)
    {
        return exMapper.findFollowedCafetoriumsByWeiXinId(openId);
    }


}
