/**
 * 
 */
package com.ssic.catering.manage.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ssic.catering.admin.pageModel.DataGrid;
import com.ssic.catering.admin.pageModel.Json;
import com.ssic.catering.admin.pageModel.PageHelper;
import com.ssic.catering.admin.service.UserServiceI;
import com.ssic.catering.base.dto.CafetoriumDto;
import com.ssic.catering.base.service.ICafetoriumService;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.game.common.util.ExcelUtil;
import com.ssic.shop.manage.dto.GoodsDto;
import com.ssic.shop.manage.dto.GoodsTypeDto;
import com.ssic.shop.manage.dto.PageHelperDto;
import com.ssic.shop.manage.service.IGoodsService;
import com.ssic.shop.manage.service.IGoodsTypeService;
import com.ssic.util.BeanUtils;

/**		
 * <p>Title: GoodsController </p>
 * <p>Description: 商品controller</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年8月27日 上午11:19:48	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年8月27日 上午11:19:48</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping("/goodsController")
public class GoodsController
{
    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private ICafetoriumService cafetoriumService;

    @Autowired
    private IGoodsTypeService goodsTypeService;
    @Autowired
    private UserServiceI userService;

    protected static final Log logger = LogFactory.getLog(GoodsController.class);

    /**
     * 跳转到商城用户管理页面
     * 
     * @return
     */
    @RequestMapping("/manager")
    public String manager(HttpServletRequest request)
    {
        return "carteMall/goods/goods";
    }

    /**
     * 获取商城用户grid表单
     * 
     * @param fieldsDto
     * @return
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public DataGrid dataGrid(GoodsDto goodsDto, PageHelper ph, HttpSession session)
    {

        DataGrid dataGrid = new DataGrid();
        List<CafetoriumDto> cafeList = new ArrayList<CafetoriumDto>();
        List<ProjectDto> pjds = userService.getProjectBySession(session);
        if (CollectionUtils.isEmpty(pjds))
        {
            logger.error("该用户没有对应的projectId");
            return dataGrid;
        }
        if (pjds.size() > 1)
        {//超管
            CafetoriumDto cafetoriumDto = new CafetoriumDto();
            cafeList = cafetoriumService.findALL(cafetoriumDto, null);
        }
        else
        {
            CafetoriumDto cafetoriumDto = new CafetoriumDto();
            cafetoriumDto.setProjId(pjds.get(0).getId());
            cafeList = cafetoriumService.findALL(cafetoriumDto, null);
        }

        List<String> cafes = new ArrayList<String>();
        if (!CollectionUtils.isEmpty(cafeList))
        {
            for (CafetoriumDto cafeDto : cafeList)
            {
                cafes.add(cafeDto.getId());
            }
        }

        PageHelperDto phDto = new PageHelperDto();
        Integer beginRow = (ph.getPage() - 1) * ph.getRows();
        BeanUtils.copyProperties(ph, phDto);
        phDto.setBeginRow(beginRow);
        //放入当前项目下的所有
        goodsDto.setCafetoriumIds(cafes);
        List<GoodsDto> list = goodsService.findAllBy(goodsDto, phDto);
        //添加商品类型
        if (list != null && list.size() > 0)
        {
            for (GoodsDto goodsDto2 : list)
            {
                GoodsTypeDto goodsTypeDto = goodsTypeService.findGoodsTypeById(goodsDto2.getGoodsTypeId());
                if (goodsTypeDto != null)
                {
                    goodsDto2.setGoodsTypeName(goodsTypeDto.getTypeName());
                }
                CafetoriumDto cafe = cafetoriumService.findById(goodsDto2.getCafetoriumId());
                goodsDto2.setCafeName(cafe.getCafeName());
            }
        }
        int count = goodsService.findCount(goodsDto);
        dataGrid.setRows(list);
        dataGrid.setTotal(Long.valueOf(count));
        return dataGrid;
    }

    /**
     * 跳转到添加商品页面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/addPage")
    public String addPage(HttpServletRequest request, String goodsTypeId)
    {
        HttpSession session = request.getSession();
        if (session == null)
        {
            logger.error("session不能为空");

        }

        List<ProjectDto> pjds = userService.getProjectBySession(session);
        if (CollectionUtils.isEmpty(pjds))
        {
            logger.error("该用户没有对应的projectId");

        }

        GoodsDto goodsDto = new GoodsDto();
        goodsDto.setId(UUID.randomUUID().toString());
        goodsDto.setGoodsTypeId(goodsTypeId);
        request.setAttribute("goodsDto", goodsDto);
        List<CafetoriumDto> cafeList = new ArrayList<CafetoriumDto>();
        if (pjds.size() > 1)
        {
            cafeList = cafetoriumService.findCafetoriumAll();
        }
        else if (pjds.size() == 1)
        {
            CafetoriumDto cafetoriumDto = new CafetoriumDto();
            cafetoriumDto.setProjId(pjds.get(0).getId());
            cafeList = cafetoriumService.findALL(cafetoriumDto, null);
        }
        request.setAttribute("cafeList", cafeList);
        return "carteMall/goods/goodsAdd";
    }

    /**
     * 添加配置项
     * 
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Json add(GoodsDto goodsDto)
    {
        Json j = new Json();
        try
        {
            GoodsDto dto = goodsService.findByGoodsIdAndName(goodsDto.getId(), goodsDto.getGoodsName());
            if (dto != null)
            {
                j.setSuccess(false);
                j.setMsg("该商品已存在，请勿重复添加！");
                return j;
            }
            goodsService.add(goodsDto);
            j.setSuccess(true);
            j.setMsg("添加商品成功！");
            j.setObj(goodsDto);
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
            GoodsDto r = goodsService.findGoodsById(id);
            goodsService.delete(r);
            j.setMsg("删除商品成功！");
            j.setSuccess(true);
        }
        else
        {
            j.setMsg("删除商品，id不存在！");
            j.setSuccess(false);
        }
        return j;
    }
    
    /**
     * 显示上传execel的页面	 
     * @author 朱振	
     * @date 2015年11月13日 下午1:48:51	
     * @version 1.0
     * @param excel
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月13日 下午1:48:51</p>
     * <p>修改备注：</p>
     */
    @RequestMapping("/uploadExcelPage")
    public String showUploadExcel()
    {
        return "carteMall/goods/uploadExcel";
    }
    
    /**
     * 上传excel	 
     * @author 朱振	
     * @date 2015年11月13日 下午1:50:11	
     * @version 1.0
     * @param excel
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月13日 下午1:50:11</p>
     * <p>修改备注：</p>
     */
    @RequestMapping("/uploadExcel")
    @ResponseBody
    public Json uploadExcel(MultipartFile excel)
    {
        Json result = new Json();
        
        if(excel == null || excel.isEmpty())
        {
            result.setMsg("上传的excel不能为空");
            result.setSuccess(false);
            return result;
        }
        
        try
        {
            InputStream in = excel.getInputStream();
            if(in != null)
            {
                Workbook work = ExcelUtil.parseExcel(excel.getOriginalFilename(), in);
                if(work == null)
                {
                    result.setMsg("workbook不能为空");
                    result.setSuccess(false);
                    return result;
                }
                
                Sheet sheet = work.getSheetAt(0);//选取第一个工作薄
                if(sheet == null)
                {
                    result.setMsg("sheet不能为空");
                    result.setSuccess(false);
                    return result;
                }
                
                List<GoodsDto> goodsList = new ArrayList<>();
                
                int i = 1;//解析头部
                
                for(;i<sheet.getLastRowNum();i++)
                {
                    GoodsDto goods = new GoodsDto();
                    
                    Row row = sheet.getRow(i);
                    if(row == null)
                    {
                        result.setMsg("row不能为空");
                        result.setSuccess(false);
                        return result;
                    }
                    
                    for(int j=0;j<row.getLastCellNum();j++)
                    {
                        if(row.getCell(j) == null)
                        {
//                            result.setMsg("上传的excel内容区域不能为空");
//                            result.setSuccess(false);
//                            return result;
                            continue;
                        }
                        
                        switch (j)
                        {
                            case 0://食堂名
                                goods.setCafeName(row.getCell(j).getStringCellValue());
                                break;
                                
                            case 1://商品类型
                                goods.setGoodsTypeName(row.getCell(j).getStringCellValue());
                                break;
                                
                            case 2://商品名
                                goods.setGoodsName(row.getCell(j).getStringCellValue());
                                break;
                                
                            case 3://价格
                                goods.setPrice(row.getCell(j).getNumericCellValue());
                                break;
                                
                            case 4://实际价格
                                goods.setSalesPrice(row.getCell(j).getNumericCellValue());
                                break;
                                
                            case 5://是否轮播
                                if("是".equals(row.getCell(j).getStringCellValue()))
                                {
                                    goods.setIsTurn(1);
                                }
                                else
                                {
                                    goods.setIsTurn(0);
                                }
                                break;
                            case 6://最大购买量
                                goods.setCountLimit(new Double(row.getCell(j).getNumericCellValue()).intValue());
                                break;
                            case 7://库存量
                                goods.setGoodsStocks(new Double(row.getCell(j).getNumericCellValue()).intValue());
                                break;
                            case 8://商品图片
                                goods.setIcon(row.getCell(j).getStringCellValue());
                                break;
                            case 9://转轮图片
                                goods.setTurnIcon(row.getCell(j).getStringCellValue());
                                break;
                            case 10://详情图片
                                goods.setDetailsimgsrc(row.getCell(j).getStringCellValue());
                                break;
                            case 11://详情轮播图片
                                goods.setTurnDetailImg(row.getCell(j).getStringCellValue());
                                break;
                            default:
                                break;
                        }
                    }
                    
                    //根据食堂名字设置食堂id
                    if(!StringUtils.isEmpty(goods.getCafeName()))
                    {
                        //不区分项目
                        CafetoriumDto cafetoriumDto = cafetoriumService.findByName(goods.getCafeName(), null);
                        if(cafetoriumDto == null)
                        {
                            result.setMsg("上传的excel中的食堂不存在");
                            result.setSuccess(true);
                            return result;
                        }
                        
                        goods.setCafetoriumId(cafetoriumDto.getId());
                    
                    }
                    else
                    {
                        result.setMsg("上传的excel食堂名字不能为空");
                        result.setSuccess(true);
                        return result;
                    }
                    
                    
                    //根据商品类型名字设置根据商品类型id
                    if(!StringUtils.isEmpty(goods.getGoodsTypeName()))
                    {
                        //不区分食堂
                        GoodsTypeDto goodsTypeDto = goodsTypeService.finByTypeName(goods.getGoodsTypeName(), null);
                        if(goodsTypeDto == null)
                        {
                            result.setMsg("上传的excel中的食堂不存在");
                            result.setSuccess(true);
                            return result;
                        }
                        
                        goods.setGoodsTypeId(goodsTypeDto.getId());
                        
                    }
                    else
                    {
                        result.setMsg("上传的excel商品类型不能为空");
                        result.setSuccess(true);
                        return result;
                    }
                    
                    
                    //添加到结果集
                    goodsList.add(goods);
                    
                    
                    
                }
                
                if(CollectionUtils.isEmpty(goodsList))
                {
                    result.setMsg("上传的excel内容为空");
                    result.setSuccess(true);
                    return result;
                }
                
                
                if(goodsService.insertGoodsByExcel(goodsList)>0)
                {
                    result.setMsg("上传的成功");
                    result.setSuccess(true);
                    return result;
                }
                
                
                result.setMsg("上传失败");
                result.setSuccess(false);
                return result;
                
            }
            
            result.setMsg("上传的excel输入流不存在");
            result.setSuccess(false);
            return result;
            
        }
        catch (IOException e)
        {
            //  对异常进行简要描述
            
            result.setMsg("获取输入流异常");
            
            result.setSuccess(false);
            
            return result;
            
        }
        
    }

}
