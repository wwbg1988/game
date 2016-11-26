/**
 * 
 */
package com.ssic.game.sync.cache.test;

import java.io.Serializable;

import lombok.ToString;

/**		
 * <p>Title: Account </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author rkzhang	
 * @date 2015年6月29日 下午4:46:26	
 * @version 1.0
 * <p>修改人：rkzhang</p>
 * <p>修改时间：2015年6月29日 下午4:46:26</p>
 * <p>修改备注：</p>
 */
@ToString
public class Account implements Serializable {

    private static final long serialVersionUID = 358720757974614746L;

    private int id;

    private String name;

    public Account(String name) {
	this.name = name;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }
}
