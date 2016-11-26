/**
 * 
 */
package com.ssic.game.common.dto;

import java.util.ArrayList;
import java.util.List;

/**		
 * <p>Title: DataGridDto </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年7月11日 上午9:45:38	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年7月11日 上午9:45:38</p>
 * <p>修改备注：</p>
 */
public class DataGridDto implements java.io.Serializable
{
    private Long total = 0L;
    private List rows = new ArrayList();

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}

