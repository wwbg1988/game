package com.ssic.catering.base.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssic.catering.base.dto.CafetoriumDto;

/**
 * 		
 * <p>Title: CafetoriumExMapper </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年10月21日 下午2:11:32	
 * @version 1.0
 * <p>修改人：pengcheng.yang</p>
 * <p>修改时间：2015年10月21日 下午2:11:32</p>
 * <p>修改备注：</p>
 */
public interface CafetoriumExMapper {
    /**
     * 
     * countCafetoriumByPjoId：根据pjoId查询cafetorium表中有没有数据
     * @param pjoId
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年10月21日 下午2:13:03
     */
   int countCafetoriumByCompanyId(@Param("companyId")String companyId);
   
   /**
    * 
    * CafetoriumByProJectId：根据projectId查询相关联的食堂信息
    * @param proId
    * @return
    * @exception	
    * @author pengcheng.yang
    * @date 2015年10月27日 上午10:42:57
    */
    List<String> CafetoriumByProJectId(@Param("userId")String userId);
    
    /**
     * 
     * CafetoriumByProjId：查询用户用后的projId
     * @param userId
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年10月28日 上午9:42:56
     */
    List<String> CafetoriumByProjId(@Param("userId")String userId);
    
   /**
    * 	 
    * @author 朱振	
    * @date 2015年11月2日 上午9:47:23	
    * @version 1.0
    * @param openId
    * @param cafetoriumId
    * @return
    * <p>修改人：朱振</p>
    * <p>修改时间：2015年11月2日 上午9:47:23</p>
    * <p>修改备注：</p>
    */
    List<CafetoriumDto> findFollowedCafetoriumsByWeiXinIdAndProjectId(@Param("openId")String openId,@Param("projectId")String projectId);
    
    
    /**
     * 	 
     * @author 朱振	
     * @date 2015年11月12日 上午10:17:52	
     * @version 1.0
     * @param openId
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月12日 上午10:17:52</p>
     * <p>修改备注：</p>
     */
    List<CafetoriumDto> findFollowedCafetoriumsByWeiXinId(@Param("openId")String openId);

}