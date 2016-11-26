/**
 * 
 */
package com.ssic.shop.manage.service;

import java.util.List;

import com.ssic.shop.manage.dto.WeixnUserCafetoriumDto;

/**		
 * <p>Title: IWeixnUserCafetoriumService </p>
 * <p>Description: 微信用户关注的食堂Service层 </p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年10月27日 下午4:10:15	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年10月27日 下午4:10:15</p>
 * <p>修改备注：</p>
 */
public interface IWeixnUserCafetoriumService
{

    public List<WeixnUserCafetoriumDto> findAllBy(WeixnUserCafetoriumDto param);

    /**     
     * insert：保存用户关注的食堂
     * @param dto
     * @exception	
     * @author 刘博
     * @date 2015年10月27日 下午4:24:25	 
     */
    public void insert(WeixnUserCafetoriumDto dto);

    /**     
     * update：更新用户关注食堂
     * @param cafe
     * @exception	
     * @author 刘博
     * @date 2015年10月27日 下午4:58:28	 
     */
    public void update(WeixnUserCafetoriumDto cafe);
    
    /**
     * 将cafetoriumId设置为默认，其他的设置为非默认<BR>	 
     * @author 朱振	
     * @date 2015年11月2日 上午11:24:41	
     * @version 1.0
     * @param openId
     * @param cafetoriumId
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月2日 上午11:24:41</p>
     * <p>修改备注：</p>
     */
    public Integer setDefaultCafetorium(String openId, String cafetoriumId);
}
