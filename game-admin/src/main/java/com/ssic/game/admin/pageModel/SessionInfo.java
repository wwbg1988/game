package com.ssic.game.admin.pageModel;

import java.util.List;

/**
 * session信息模型
 * 
 * @author 刘博
 * 
 */
public class SessionInfo implements java.io.Serializable {

	private String id;// 用户ID
	private String name;// 用户登录名
	private String ip;// 用户IP
	private String userAccount;//用户账号
	private String password;//密码
	private String tabType;//tab页面类型

	private List<String> resourceList;// 用户可以访问的资源地址列表

	public List<String> getResourceList() {
		return resourceList;
	}

	public void setResourceList(List<String> resourceList) {
		this.resourceList = resourceList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return this.name;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    public String getTabType()
    {
        return tabType;
    }

    public void setTabType(String tabType)
    {
        this.tabType = tabType;
    }

	
	
	
}
