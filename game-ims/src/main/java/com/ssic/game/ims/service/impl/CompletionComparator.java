/**
 * 
 */
package com.ssic.game.ims.service.impl;

import java.util.Comparator;

import com.ssic.game.ims.model.LoadCompletionDto;
import com.ssic.game.ims.model.NewFormDataResult;

/**		
 * <p>Title: CompletionComparator </p>
 * <p>Description: 请假出差，报销的"我的"排序类</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年11月3日 上午10:40:44	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年11月3日 上午10:40:44</p>
 * <p>修改备注：</p>
 */
public class CompletionComparator implements Comparator
{
    @Override
    public int compare(Object o1, Object o2)
    {
        boolean isLoadCompletionDto = o1 instanceof LoadCompletionDto && o2 instanceof LoadCompletionDto;
        if (isLoadCompletionDto)
        {
            LoadCompletionDto loadCompletionDto1 = (LoadCompletionDto) o1;
            LoadCompletionDto loadCompletionDto2 = (LoadCompletionDto) o2;
            if (loadCompletionDto1.getCreateDateForString()
                .compareTo(loadCompletionDto2.getCreateDateForString()) < 0)
            {
                return 1;
            }
            if (loadCompletionDto1.getCreateDateForString()
                .equals(loadCompletionDto2.getCreateDateForString()))
            {
                return 0;
            }
        }
        
        boolean isNewFormDataResult = o1 instanceof NewFormDataResult && o2 instanceof NewFormDataResult;
        if (isNewFormDataResult)
        {
            NewFormDataResult newFormDataResult1 = (NewFormDataResult) o1;
            NewFormDataResult newFormDataResult2 = (NewFormDataResult) o2;
            if (newFormDataResult1.getCreateDateForString()
                .compareTo(newFormDataResult2.getCreateDateForString()) < 0)
            {
                return 1;
            }
            if (newFormDataResult1.getCreateDateForString()
                .equals(newFormDataResult2.getCreateDateForString()))
            {
                return 0;
            }
        }
        return -1;
    }
}
