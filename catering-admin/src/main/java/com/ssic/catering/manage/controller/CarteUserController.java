/**
 * 
 */
package com.ssic.catering.manage.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.admin.pageModel.DataGrid;
import com.ssic.catering.admin.pageModel.PageHelper;
import com.ssic.shop.manage.dto.CarteUserDto;
import com.ssic.shop.manage.dto.PageHelperDto;
import com.ssic.shop.manage.service.ICarteUserService;
import com.ssic.util.BeanUtils;

/**		
 * <p>Title: CarteUserController </p>
 * <p>Description: 商城用户controller</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年8月27日 上午10:19:00	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年8月27日 上午10:19:00</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping("/carteUserController")
public class CarteUserController
{
    @Autowired
    private ICarteUserService carteUserService;
    
    /**
     * 跳转到商城用户管理页面
     * 
     * @return
     */
    @RequestMapping("/manager")
    public String manager(HttpServletRequest request)
    {
        return "carteMall/carteUser/carteUser";
    }
    
    /**
     * 获取商城用户grid表单
     * 
     * @param fieldsDto
     * @return
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public DataGrid dataGrid(CarteUserDto carteUserDto, PageHelper ph)
    {
        DataGrid dataGrid = new DataGrid();
        PageHelperDto phDto = new PageHelperDto();
        Integer beginRow = (ph.getPage() - 1) * ph.getRows();

        BeanUtils.copyProperties(ph, phDto);
        phDto.setBeginRow(beginRow);
        List<CarteUserDto> list = carteUserService.findAllBy(carteUserDto, phDto);
        int count = carteUserService.findCount(carteUserDto);
        dataGrid.setRows(list);
        dataGrid.setTotal(Long.valueOf(count));
        return dataGrid;
    }
}

