package com.ssic.game.app.controller.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.common.util.AppResponse;
import com.ssic.game.common.pageModel.PageHelper;
import com.ssic.shop.manage.dto.FindDataResults;
import com.ssic.shop.manage.dto.GoodsDto;
import com.ssic.shop.manage.dto.GoodsImageDto;
import com.ssic.shop.manage.service.IGoodsImageService;
import com.ssic.shop.manage.service.IGoodsService;
import com.ssic.shop.manage.service.IOrderService;
import com.ssic.util.model.Response;

/**
 * 		
 * <p>Title: QueryGoodsInfoController </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年9月16日 上午10:50:29	
 * @version 1.0
 * <p>修改人：pengcheng.yang</p>
 * <p>修改时间：2015年9月16日 上午10:50:29</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping(value = "/http/api/shop")
public class QueryGoodsInfoController
{
    
    @Autowired
    private IGoodsService goodsService;
    
    @Autowired
    private IGoodsImageService goodsImageService;
    
    @Autowired
    private IOrderService iOrderService;
    
    /**
     * 
     * findGoodsLimitedBy：查询详情页限时特购商品信息
     * @param cafetoriumId
     * @param page
     * @param rows
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年9月16日 下午1:49:27
     */
    @ResponseBody
    @RequestMapping(value = "/searchGoodsLimited.do",method = {RequestMethod.GET, RequestMethod.POST})
    public Response<FindDataResults> findGoodsLimitedBy(String cafetoriumId,int page, int rows){
        //分页对象;
        PageHelper ph = new PageHelper();
        ph.setPage(page);
        ph.setRows(rows);
        return goodsService.findGoodsLimitedBy(cafetoriumId,ph);
    }
    
    /**
     * 
     * findGoodsFineBy：查询详情页精品推荐商品信息
     * @param cafetoriumId
     * @param page
     * @param rows
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年9月16日 下午1:48:27
     */
    @ResponseBody
    @RequestMapping(value = "/searchGoodsFine.do",method = {RequestMethod.GET, RequestMethod.POST})
    public Response<FindDataResults> findGoodsFineBy(String cafetoriumId,int page, int rows){ 
        //分页对象;
        PageHelper ph = new PageHelper();
        ph.setPage(page);
        ph.setRows(rows);
        return goodsService.findGoodsFineBy(cafetoriumId,ph);
    }
    
    /**
     * 
     * queryGoodsIsTurnInfo：查询轮播商品信息
     * @param cafetoriumId
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年9月16日 上午11:28:07
     */
    @ResponseBody
    @RequestMapping(value = "/searchGoodsIsTurn.do",method = {RequestMethod.GET, RequestMethod.POST})
    public Response<FindDataResults> queryGoodsIsTurnInfo(String cafetoriumId){
//        return goodsService.findGoodsIsTurnBy("1");
        return goodsService.findGoodsIsTurnBy(cafetoriumId);
    }
    /**
     * 
     * findlimitStickAllBy：查询首页限时特购商品信息
     * @param cafetoriumId
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年9月16日 下午2:20:29
     */
    @ResponseBody
    @RequestMapping(value = "/searchlimitStick.do",method = {RequestMethod.GET, RequestMethod.POST})
    public Response<FindDataResults> findlimitStickAllBy(String cafetoriumId){
        return goodsService.findlimitStickAllBy(cafetoriumId);
//        return goodsService.findlimitStickAllBy("1");
    }
    
    /**
     * 
     * findlimitStickAllBy：查询首页精品推荐商品信息
     * @param cafetoriumId
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年9月16日 下午2:22:16
     */
    @ResponseBody
    @RequestMapping(value = "/searchFineStick.do",method = {RequestMethod.GET, RequestMethod.POST})
    public Response<FindDataResults> findFineStickAllBy(String cafetoriumId){
        return goodsService.findFineStickAllBy(cafetoriumId);
//        return goodsService.findFineStickAllBy("12");
    }
    
    /**
     * 
     * findGoodsById：根据商品Id查询具体某一件商品的详情信息
     * @param shopId
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年9月21日 上午9:50:54
     */
    @ResponseBody
    @RequestMapping(value = "/searchFindGoodsId.do",method = {RequestMethod.GET, RequestMethod.POST})
    public Response<GoodsDto> findGoodsById(String shopId,String cafetoriumId){
        Response<GoodsDto> result = new Response<GoodsDto>();
        GoodsDto goodsDto = goodsService.findGoodsById(shopId);
        if(goodsDto != null){
            goodsDto.setMonthlysales(countMonthlysales(shopId,cafetoriumId));
            result.setMessage("查询商品详情信息成功!");
            result.setData(goodsDto);
            result.setStatus(AppResponse.RETURN_SUCCESS);
            return result;
        }else{
            result.setMessage("查询商品详情信息失败!");
            result.setStatus(AppResponse.RETURN_FAILE);
            return result;
        }
    }
    
    /**
     * 
     * countMonthlysales：统计商品的月销量
     * @param shopId
     * @param cafetoriumId
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年10月8日 上午11:23:45
     */
    public int countMonthlysales(String shopId,String cafetoriumId){
        return iOrderService.countMonthlysales(shopId, cafetoriumId);
    }
    
    
    /**
     * 
     * findGoodsImageById：根据商品Id查询跟商品详情的轮播图片
     * @param shopId
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年9月21日 下午12:42:38
     */
    @ResponseBody
    @RequestMapping(value = "/searchGoodsImage.do",method = {RequestMethod.GET, RequestMethod.POST})
    public Response<List<GoodsImageDto>> findGoodsImageById(String shopId){
        Response<List<GoodsImageDto>> result = new Response<List<GoodsImageDto>>();
        List<GoodsImageDto> List = goodsImageService.findGoodsImageById(shopId);
        if(List != null){
            result.setMessage("查询商品详情轮播图片成功!");
            result.setData(List);
            result.setStatus(AppResponse.RETURN_SUCCESS);
            return result;
        }else{
            result.setMessage("查询商品详情轮播图片失败!");
            result.setStatus(AppResponse.RETURN_FAILE);
            return result;
        }
    }
}

