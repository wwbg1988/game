package com.ssic.shop.manage.mapper;

import org.apache.ibatis.annotations.Param;

public interface WeixnUserCafetoriumExMapper {
    /**
     *   
     * @author 朱振   
     * @date 2015年11月2日 上午11:30:58  
     * @version 1.0
     * @param openId
     * @param cafetoriumId
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月2日 上午11:30:58</p>
     * <p>修改备注：</p>
     */
    Integer updateDefaultCafetorium(@Param("openId")String openId, @Param("cafetoriumId")String cafetoriumId);
    
    /**
     * 	 
     * @author 朱振	
     * @date 2015年11月2日 下午12:40:47	
     * @version 1.0
     * @param openId
     * @param cafetoriumId
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月2日 下午12:40:47</p>
     * <p>修改备注：</p>
     */
    Integer updateUnDefaultOtherCafetorium(@Param("openId")String openId, @Param("cafetoriumId")String cafetoriumId);

    
}