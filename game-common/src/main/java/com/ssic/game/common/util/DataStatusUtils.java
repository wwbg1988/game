/**
 * 
 */
package com.ssic.game.common.util;

import com.ssic.util.constants.DataStatus;

/**		
 * <p>Title: DataStatusUtils </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author rkzhang	
 * @date 2015年6月30日 下午4:39:52	
 * @version 1.0
 * <p>修改人：rkzhang</p>
 * <p>修改时间：2015年6月30日 下午4:39:52</p>
 * <p>修改备注：</p>
 */
public class DataStatusUtils {

    public static boolean isNotEnabled(Integer stat) {
	return stat == null || stat != DataStatus.ENABLED;
    }
}
