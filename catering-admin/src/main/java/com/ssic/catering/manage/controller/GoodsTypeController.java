/**
 * 
 */
package com.ssic.catering.manage.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.admin.pageModel.DataGrid;
import com.ssic.catering.admin.pageModel.Json;
import com.ssic.catering.admin.pageModel.PageHelper;
import com.ssic.catering.base.dto.CafetoriumDto;
import com.ssic.shop.manage.dto.GoodsDto;
import com.ssic.shop.manage.dto.GoodsTypeDto;
import com.ssic.shop.manage.dto.PageHelperDto;
import com.ssic.shop.manage.service.IGoodsService;
import com.ssic.shop.manage.service.IGoodsTypeService;
import com.ssic.util.BeanUtils;

/**		
 * <p>Title: GoodsTypeController </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年8月27日 上午11:36:30	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年8月27日 上午11:36:30</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping("/goodsTypeController")
public class GoodsTypeController
{

    @Autowired
    private IGoodsTypeService goodsTypeService;
    @Autowired
    private IGoodsService goodsService;

    /**
     * 跳转到商城用户管理页面
     * 
     * @return
     */
    @RequestMapping("/manager")
    public String manager(HttpServletRequest request)
    {
        return "carteMall/goodsType/goodsType";
    }

    /**
     * 查找所有商品类型
     * 
     * @return
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public List<GoodsTypeDto> findAll()
    {
        GoodsTypeDto goodsTypeDto = new GoodsTypeDto();
        return goodsTypeService.findAllBy(goodsTypeDto, null);
    }

    /**
     * 获取商城用户grid表单
     * 
     * @param fieldsDto
     * @return
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public DataGrid dataGrid(GoodsTypeDto goodsTypeDto, PageHelper ph)
    {
        DataGrid dataGrid = new DataGrid();
        PageHelperDto phDto = new PageHelperDto();
        Integer beginRow = (ph.getPage() - 1) * ph.getRows();
        BeanUtils.copyProperties(ph, phDto);
        phDto.setBeginRow(beginRow);
        List<GoodsTypeDto> list = goodsTypeService.findAllBy(goodsTypeDto, phDto);
        int count = goodsTypeService.findCount(goodsTypeDto);
        dataGrid.setRows(list);
        dataGrid.setTotal(Long.valueOf(count));
        return dataGrid;
    }

    /**
     * 跳转到添加配置项页面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/addPage")
    public String addPage(HttpServletRequest request)
    {
        GoodsTypeDto goodsTypeDto = new GoodsTypeDto();
        goodsTypeDto.setId(UUID.randomUUID().toString());
        request.setAttribute("goodsTypeDto", goodsTypeDto);
        return "carteMall/goodsType/goodsTypeAdd";
    }

    /**
     * 添加配置项
     * 
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Json add(GoodsTypeDto goodsTypeDto)
    {
        Json j = new Json();
        try
        {
            GoodsTypeDto dto =
                goodsTypeService.finByTypeName(goodsTypeDto.getTypeName(), goodsTypeDto.getId());
            if (dto != null)
            {
                j.setSuccess(false);
                j.setMsg("该商品类型已存在，请勿重复添加！");
                return j;
            }
            goodsTypeService.add(goodsTypeDto);
            j.setSuccess(true);
            j.setMsg("添加商品类型成功！");
            j.setObj(goodsTypeDto);
        }
        catch (Exception e)
        {
            // e.printStackTrace();
            j.setMsg(e.getMessage());
        }
        return j;
    }

    /**
     * 删除商品类型
     * 
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Json delete(String id)
    {
        Json j = new Json();
        if (!StringUtils.isEmpty(id))
        {
            GoodsDto param = new GoodsDto();
            param.setGoodsTypeId(id);
            List<GoodsDto> goodsList = goodsService.findAllBy(param, null);
            if (!CollectionUtils.isEmpty(goodsList))
            {
                j.setMsg("该商品类型下还有商品，不能删除！");
                j.setSuccess(false);
                return j;
            }
            GoodsTypeDto r = goodsTypeService.findGoodsTypeById(id);
            goodsTypeService.delete(r);
            j.setMsg("删除商品类型成功！");
            j.setSuccess(true);
        }
        else
        {
            j.setMsg("删除商品类型，id不存在！");
            j.setSuccess(false);
        }
        return j;
    }
}
