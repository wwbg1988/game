/**
 * 
 */
package com.ssic.game.common.util;

import java.util.Comparator;

import com.ssic.game.common.dto.DeptDto;
import com.ssic.game.common.dto.query.QueryConditionDto;

/**		
 * <p>Title: DeptComparator </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年7月28日 下午2:31:22	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年7月28日 下午2:31:22</p>
 * <p>修改备注：</p>
 */
public class DeptComparator implements Comparator
{

    /** 
     * 这里的o1和o2就是list里任意的两个对象，然后按需求把这个方法填完整就行了
     */
    @Override
    public int compare(Object o1, Object o2)
    {
        //判断2个比较对象是否是部门对象；
        boolean isDept = o1 instanceof DeptDto && o2 instanceof DeptDto;
        if (isDept)
        {//如果是，强转2个菜单栏目对象，比较sort字段：按从小到大顺序比较;
            DeptDto deptDto1 = (DeptDto) o1;
            DeptDto deptDto2 = (DeptDto) o2;
            if (Long.valueOf(deptDto1.getDeptCode().trim()) > Long.valueOf(deptDto2.getDeptCode().trim()))
            {
                return 1;
            }
            if (deptDto1.getDeptCode().trim().equals(deptDto2.getDeptCode().trim()))
            {
                return 0;
            }
        }
        
        //判断2个对象是否是字段过滤条件；
        boolean isQueryCondition = o1 instanceof QueryConditionDto && o2 instanceof QueryConditionDto;
        if (isQueryCondition)
        {
            QueryConditionDto conditionDto1 = (QueryConditionDto) o1;
            QueryConditionDto conditionDto2 = (QueryConditionDto) o2;
            if (Integer.valueOf(conditionDto1.getSerialNum()) > Integer.valueOf(conditionDto2.getSerialNum()))
            {
                return 1;
            }
            if (Integer.valueOf(conditionDto1.getSerialNum()).equals(Integer.valueOf(conditionDto2.getSerialNum())))
            {
                return 0;
            }
        }
        
        return -1;
    }

}
