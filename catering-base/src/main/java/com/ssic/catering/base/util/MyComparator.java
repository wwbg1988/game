/**
 * 
 */
package com.ssic.catering.base.util;

import java.util.Comparator;

import com.ssic.catering.base.dto.AddressDto;
import com.ssic.catering.base.dto.ConfigDto;
import com.ssic.game.common.dto.WorkSearchDto;

/**		
 * <p>Title: MyComparator </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博
 * @date 2015年7月19日 上午11:51:28	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年7月19日 上午11:51:28</p>
 * <p>修改备注：</p>
 */
public class MyComparator implements Comparator
{

    /** 
     * 这里的o1和o2就是list里任意的两个对象，然后按需求把这个方法填完整就行了
     */
    @Override
    public int compare(Object o1, Object o2)
    {
        //判断2个比较对象是否是菜单栏目对象；
        boolean isMenu = o1 instanceof ConfigDto && o2 instanceof ConfigDto;
        if (isMenu)
        {//如果是，强转2个菜单栏目对象，比较sort字段：按从小到大顺序比较;
            ConfigDto menuDto1 = (ConfigDto) o1;
            ConfigDto menuDto2 = (ConfigDto) o2;
            if (menuDto1.getId().compareTo(menuDto2.getId())<0)
            {
                return 1;
            }
            if (menuDto1.getId().equals(menuDto2.getId()))
            {
                return 0;
            }
        }
        //判断2个比较对象是否是菜单栏目对象；
        boolean isAddress = o1 instanceof AddressDto && o2 instanceof AddressDto;
        if (isAddress)
        {//如果是，强转2个菜单栏目对象，比较sort字段：按从小到大顺序比较;
            AddressDto addressDto1 = (AddressDto) o1;
            AddressDto addressDto2 = (AddressDto) o2;
            if (addressDto1.getAddressCode().compareTo(addressDto2.getAddressCode())<0)
            {
                return 1;
            }
            if (addressDto1.getAddressCode().equals(addressDto2.getAddressCode()))
            {
                return 0;
            }
        }
        boolean isWorkSearch = o1 instanceof WorkSearchDto && o2 instanceof WorkSearchDto;
        if (isWorkSearch)
        {
            WorkSearchDto workSearchDto1 = (WorkSearchDto) o1;
            WorkSearchDto workSearchDto2 = (WorkSearchDto) o2;
            if (workSearchDto1.getCreateTime().compareTo(workSearchDto2.getCreateTime()) < 0)
            {
                return 1;
            }
            if (workSearchDto1.getCreateTime().equals(workSearchDto2.getCreateTime()))
            {
                return 0;
            }
        }
        return -1;
    }

}
