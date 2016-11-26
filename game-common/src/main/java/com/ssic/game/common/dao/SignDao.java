/**
 * 
 */
package com.ssic.game.common.dao;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import lombok.Getter;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssic.game.common.dto.SignDto;
import com.ssic.game.common.mapper.SignExMapper;
import com.ssic.game.common.mapper.SignMapper;
import com.ssic.game.common.pojo.Sign;
import com.ssic.game.common.pojo.SignExample;
import com.ssic.game.common.pojo.SignExample.Criteria;
import com.ssic.util.UUIDGenerator;
import com.ssic.util.base.MyBatisBaseDao;
import com.ssic.util.constants.DataStatus;

/**		
 * <p>Title: SignDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年8月10日 下午1:35:19	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年8月10日 下午1:35:19</p>
 * <p>修改备注：</p>
 */
@Repository
public class SignDao extends MyBatisBaseDao<Sign>
{
    private static Logger log = Logger.getLogger(SignDao.class);
    
    @Autowired
    @Getter
    private SignMapper mapper;
    
    @Autowired
    private SignExMapper exMapper;

    public int insert(SignDto signDto)
    {
        Sign sign = new Sign();
        BeanUtils.copyProperties(signDto, sign);
        sign.setId(UUIDGenerator.getUUID());
        sign.setCreateTime(new Date());
        sign.setLastUpdateTime(new Date());
        sign.setStat(DataStatus.ENABLED);
        return mapper.insertSelective(sign);
    }

    public List<SignDto> findByDate(Sign sign,int beginRows,int endRows){
    	return exMapper.findByDate(sign,beginRows,endRows);
    }
    
    public List<SignDto> findByUserId(SignDto signDto){
    	return exMapper.findByUserId(signDto);
    }
    
    public List<Sign> findBy(Sign sign,int beginRows,int endRows)
    {
        SignExample example = new SignExample();
        Criteria criteria = example.createCriteria();
//        if (sign.getSignType() != 0)
//        {
//            criteria.andSignTypeEqualTo(sign.getSignType());
//        }else{
//            criteria.andSignTypeNotEqualTo(SignTypeConstants.SIGN_TYPE_SIGNER);
//        }
        if(sign.getSignType()!=0){
            criteria.andSignTypeEqualTo(sign.getSignType());
        }
        
        if (!StringUtils.isEmpty(sign.getUserId()))
        {
            criteria.andUserIdEqualTo(sign.getUserId());
        }
        if(!StringUtils.isEmpty(sign.getProjectid())){
            criteria.andProjectidEqualTo(sign.getProjectid());
        }
        if(!StringUtils.isEmpty(sign.getDeptid())){
            criteria.andDeptidEqualTo(sign.getDeptid());
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        if(endRows==0){
            example.setOrderByClause(" create_time desc");
        }else{
            example.setOrderByClause(" create_time desc limit "+beginRows+","+endRows);
        }
        return mapper.selectByExample(example);

    }

    public int findCount(Sign sign)
    {
        SignExample example = new SignExample();
        Criteria criteria = example.createCriteria();
        if(sign.getSignType()!=0){
            criteria.andSignTypeEqualTo(sign.getSignType());
        }
        if (!StringUtils.isEmpty(sign.getUserId()))
        {
            criteria.andUserIdEqualTo(sign.getUserId());
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return mapper.countByExample(example);
    }
    public Integer findCountByTime(Sign sign){
          return exMapper.findCount(sign);
    }

    public Sign findById(String id)
    {
        return mapper.selectByPrimaryKey(id);
    }
    
    public void delSignByPrimaryKey(String id){
        Sign sign = new Sign();
        sign.setId(id);
        sign.setStat(DataStatus.DISABLED);
        mapper.updateByPrimaryKeySelective(sign);
    }
    
    public List<SignDto> findSignDtosBy(Sign sign, int beginRows, int endRows,String userName , String userAccount)
    {
        log.debug("parameters:sign="+sign+";beginRows="+beginRows+";endRows="+endRows);
        
        if(sign==null)
        {
            log.error("parameters:sign="+sign);
            return null;
        }
        
        if(beginRows < 0)
        {
            log.error("parameters:beginRows="+beginRows);
            return null;
        }
        
        if(endRows < 0)
        {
            log.error("parameters:endRows="+endRows);
            return null;
        }
        
        SignExample example = new SignExample();
        Criteria criteria = example.createCriteria();
        
        //id
        if(!StringUtils.isEmpty(sign.getId()))
        {
            criteria.andIdEqualTo(sign.getId());
        }
        
        //userId
        if(!StringUtils.isEmpty(sign.getUserId()))
        {
            criteria.andIdEqualTo(sign.getUserId());
        }
        
        //xPosition
        if(!StringUtils.isEmpty(sign.getxPosition()))
        {
            criteria.andXPositionEqualTo(sign.getxPosition());
        }
        
        //yPosition
        if(!StringUtils.isEmpty(sign.getyPosition()))
        {
            criteria.andYPositionEqualTo(sign.getyPosition());
        }
        
        //address
        if(!StringUtils.isEmpty(sign.getAddress()))
        {
            criteria.andAddressEqualTo(sign.getAddress());
        }
        
        //positionName
        if(!StringUtils.isEmpty(sign.getPositionName()))
        {
            criteria.andPositionNameEqualTo(sign.getPositionName());
        }
        
        //reason
        if(!StringUtils.isEmpty(sign.getReason()))
        {
            criteria.andReasonEqualTo(sign.getReason());
        }
        
        //pic
        if(!StringUtils.isEmpty(sign.getPic()))
        {
            criteria.andPicEqualTo(sign.getPic());
        }
        
        //signType
        if(sign.getSignType() != null)
        {
            criteria.andSignTypeEqualTo(sign.getSignType());
        }
        
        // in projectid
        if(!StringUtils.isEmpty(sign.getProjectid()) && sign.getProjectid().contains(","))
        {
            criteria.andProjectidIn(Arrays.asList(sign.getProjectid().split(",")));
        }
        else{
            criteria.andProjectidEqualTo(sign.getProjectid());
        }
        
        //deptid
        if(!StringUtils.isEmpty(sign.getDeptid()))
        {
            criteria.andDeptidEqualTo(sign.getDeptid());
        }
        
        //isworktime
        if(sign.getIsworktime() != null)
        {
            criteria.andIsworktimeEqualTo(sign.getIsworktime());
        }
        
//        //createTime
//        if(!StringUtils.isEmpty(sign.getCreateTime()))
//        {
//            criteria.andCreateTimeEqualTo(sign.getCreateTime());
//        }
//        
//        //lastUpdateTime
//        if(!StringUtils.isEmpty(sign.getLastUpdateTime()))
//        {
//            criteria.andLastUpdateTimeEqualTo(sign.getLastUpdateTime());
//        }
           
        criteria.andStatEqualTo(DataStatus.ENABLED);
        if(endRows==0){
            example.setOrderByClause(" create_time desc");
        }else{
            example.setOrderByClause(" create_time desc limit "+beginRows+","+endRows);
        }
        
        return exMapper.findSignDtosBy(example,userName,userAccount);
    }
    
    public Long findSignDtosCountBy(Sign sign,String userName ,String userAccount)
    {
        log.debug("parameters:sign="+sign);
        
        if(sign==null)
        {
            log.error("parameters:sign="+sign);
            return null;
        }
        
        SignExample example = new SignExample();
        Criteria criteria = example.createCriteria();
        
        //id
        if(!StringUtils.isEmpty(sign.getId()))
        {
            criteria.andIdEqualTo(sign.getId());
        }
        
        //userId
        if(!StringUtils.isEmpty(sign.getUserId()))
        {
            criteria.andIdEqualTo(sign.getUserId());
        }
        
        //xPosition
        if(!StringUtils.isEmpty(sign.getxPosition()))
        {
            criteria.andXPositionEqualTo(sign.getxPosition());
        }
        
        //yPosition
        if(!StringUtils.isEmpty(sign.getyPosition()))
        {
            criteria.andYPositionEqualTo(sign.getyPosition());
        }
        
        //address
        if(!StringUtils.isEmpty(sign.getAddress()))
        {
            criteria.andAddressEqualTo(sign.getAddress());
        }
        
        //positionName
        if(!StringUtils.isEmpty(sign.getPositionName()))
        {
            criteria.andPositionNameEqualTo(sign.getPositionName());
        }
        
        //reason
        if(!StringUtils.isEmpty(sign.getReason()))
        {
            criteria.andReasonEqualTo(sign.getReason());
        }
        
        //pic
        if(!StringUtils.isEmpty(sign.getPic()))
        {
            criteria.andPicEqualTo(sign.getPic());
        }
        
        //signType
        if(sign.getSignType() != null)
        {
            criteria.andSignTypeEqualTo(sign.getSignType());
        }
        
        // in projectid
        if(!StringUtils.isEmpty(sign.getProjectid()) && sign.getProjectid().contains(","))
        {
            criteria.andProjectidIn(Arrays.asList(sign.getProjectid().split(",")));
        }
        else{
            criteria.andProjectidEqualTo(sign.getProjectid());
        }
        
        //deptid
        if(!StringUtils.isEmpty(sign.getDeptid()))
        {
            criteria.andDeptidEqualTo(sign.getDeptid());
        }
        
        //isworktime
        if(sign.getIsworktime() != null)
        {
            criteria.andIsworktimeEqualTo(sign.getIsworktime());
        }
        
//        //createTime
//        if(!StringUtils.isEmpty(sign.getCreateTime()))
//        {
//            criteria.andCreateTimeEqualTo(sign.getCreateTime());
//        }
//        
//        //lastUpdateTime
//        if(!StringUtils.isEmpty(sign.getLastUpdateTime()))
//        {
//            criteria.andLastUpdateTimeEqualTo(sign.getLastUpdateTime());
//        }
           
        criteria.andStatEqualTo(DataStatus.ENABLED);
        
        return exMapper.findSignDtosCountBy(example,userName,userAccount);
    }
    
    
    public List<SignDto> findByProjs(Sign sign,String userName,String userAccount)
    {
    	 SignExample example = new SignExample();
         Criteria criteria = example.createCriteria();
         
         //id
         if(!StringUtils.isEmpty(sign.getId()))
         {
             criteria.andIdEqualTo(sign.getId());
         }
         
         //userId
         if(!StringUtils.isEmpty(sign.getUserId()))
         {
             criteria.andIdEqualTo(sign.getUserId());
         }
         
         //xPosition
         if(!StringUtils.isEmpty(sign.getxPosition()))
         {
             criteria.andXPositionEqualTo(sign.getxPosition());
         }
         
         //yPosition
         if(!StringUtils.isEmpty(sign.getyPosition()))
         {
             criteria.andYPositionEqualTo(sign.getyPosition());
         }
         
         //address
         if(!StringUtils.isEmpty(sign.getAddress()))
         {
             criteria.andAddressEqualTo(sign.getAddress());
         }
         
         //positionName
         if(!StringUtils.isEmpty(sign.getPositionName()))
         {
             criteria.andPositionNameEqualTo(sign.getPositionName());
         }
         
         //reason
         if(!StringUtils.isEmpty(sign.getReason()))
         {
             criteria.andReasonEqualTo(sign.getReason());
         }
         
         //pic
         if(!StringUtils.isEmpty(sign.getPic()))
         {
             criteria.andPicEqualTo(sign.getPic());
         }
         
         //signType
         if(sign.getSignType() != null)
         {
             criteria.andSignTypeEqualTo(sign.getSignType());
         }
         
         // in projectid
         if(!StringUtils.isEmpty(sign.getProjectid()) && sign.getProjectid().contains(","))
         {
             criteria.andProjectidIn(Arrays.asList(sign.getProjectid().split(",")));
         }
         else{
             criteria.andProjectidEqualTo(sign.getProjectid());
         }
         
         //deptid
         if(!StringUtils.isEmpty(sign.getDeptid()))
         {
             criteria.andDeptidEqualTo(sign.getDeptid());
         }
         
         //isworktime
         if(sign.getIsworktime() != null)
         {
             criteria.andIsworktimeEqualTo(sign.getIsworktime());
         }
            
         criteria.andStatEqualTo(DataStatus.ENABLED);
    
         return exMapper.findSignDtosBy(example,userName,userAccount);
    }

}
