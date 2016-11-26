/**
 * 
 */
package com.ssic.catering.base.dto;

import java.io.Serializable;
import java.util.List;

/**		
 * <p>Title: Tree </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年8月11日 下午3:05:36	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年8月11日 下午3:05:36</p>
 * <p>修改备注：</p>
 */
public class Tree implements Serializable
{

    /**   
    * serialVersionUID: （一句话描述这个变量表示什么）      
    */

    private static final long serialVersionUID = 6166849863144127346L;
    private String id;
    private String text;
    private String state = "open";// open,closed
    private boolean checked = false;
    private Object attributes;
    private List<Tree> children;
    private String iconCls;
    private String pid;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public boolean isChecked()
    {
        return checked;
    }

    public void setChecked(boolean checked)
    {
        this.checked = checked;
    }

    public Object getAttributes()
    {
        return attributes;
    }

    public void setAttributes(Object attributes)
    {
        this.attributes = attributes;
    }

    public List<Tree> getChildren()
    {
        return children;
    }

    public void setChildren(List<Tree> children)
    {
        this.children = children;
    }

    public String getIconCls()
    {
        return iconCls;
    }

    public void setIconCls(String iconCls)
    {
        this.iconCls = iconCls;
    }

    public String getPid()
    {
        return pid;
    }

    public void setPid(String pid)
    {
        this.pid = pid;
    }

}
