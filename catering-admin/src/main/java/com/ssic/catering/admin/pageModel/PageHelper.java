package com.ssic.catering.admin.pageModel;

/**
 * EasyUI 分页帮助类
 * 
 * @author 刘博
 * 
 */
public class PageHelper implements java.io.Serializable
{

    private int page;// 当前页
    private int rows;// 每页显示记录数
    private String sort;// 排序字段
    private String order;// asc/desc
    private int beginRow;//开始行:从第几条记录开始查找

    public int getPage()
    {
        return page;
    }

    public void setPage(int page)
    {
        this.page = page;
    }

    public int getRows()
    {
        return rows;
    }

    public void setRows(int rows)
    {
        this.rows = rows;
    }

    public String getSort()
    {
        return sort;
    }

    public void setSort(String sort)
    {
        this.sort = sort;
    }

    public String getOrder()
    {
        return order;
    }

    public void setOrder(String order)
    {
        this.order = order;
    }

    public int getBeginRow()
    {
        return beginRow;
    }

    public void setBeginRow(int beginRow)
    {
        this.beginRow = beginRow;
    }


   

}
