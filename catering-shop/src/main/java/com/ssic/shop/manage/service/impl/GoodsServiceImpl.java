/**
 * 
 */
package com.ssic.shop.manage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssic.game.common.pageModel.PageHelper;
import com.ssic.shop.manage.dao.GoodsDao;
import com.ssic.shop.manage.dao.GoodsImageExDao;
import com.ssic.shop.manage.dto.FindDataResults;
import com.ssic.shop.manage.dto.GoodsDto;
import com.ssic.shop.manage.dto.PageHelperDto;
import com.ssic.shop.manage.pojo.Goods;
import com.ssic.shop.manage.pojo.GoodsImage;
import com.ssic.shop.manage.service.IGoodsService;
import com.ssic.util.BeanUtils;
import com.ssic.util.UUIDGenerator;
import com.ssic.util.constants.DataStatus;
import com.ssic.util.model.Response;

/**		
 * <p>Title: GoodsServiceImpl </p>
 * <p>Description: 商品Service实现层</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年8月27日 上午10:15:06	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年8月27日 上午10:15:06</p>
 * <p>修改备注：</p>
 */
@Service
public class GoodsServiceImpl implements IGoodsService
{

    @Autowired
    private GoodsDao goodsDao;
    
    @Autowired
    private GoodsImageExDao goodsImageDao;

    /** 
     * (non-Javadoc)   
     * @see com.ssic.shop.manage.service.ICarteUserService#findAllBy(com.ssic.shop.manage.pojo.CarteUser, com.ssic.game.common.dto.PageHelperDto)   
     */
    @Override
    public List<GoodsDto> findAllBy(GoodsDto goodsDto, PageHelperDto ph)
    {
        List<GoodsDto> result = new ArrayList<GoodsDto>();
        Goods param = new Goods();
        BeanUtils.copyProperties(goodsDto, param);
        List<Goods> list = goodsDao.findAllBy(param, ph, goodsDto.getCafetoriumIds());
        if (!CollectionUtils.isEmpty(list))
        {
            result = BeanUtils.createBeanListByTarget(list, GoodsDto.class);
        }
        return result;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.shop.manage.service.ICarteUserService#findCount(com.ssic.shop.manage.dto.CarteUserDto)   
    */
    @Override
    public int findCount(GoodsDto goodsDto)
    {
        Goods param = new Goods();
        BeanUtils.copyProperties(goodsDto, param);
        int counts = goodsDao.findCountBy(param);
        return counts;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.shop.manage.service.IGoodsService#findByGoodsIdAndName(java.lang.String, java.lang.String)   
    */
    @Override
    public GoodsDto findByGoodsIdAndName(String goodsId, String goodsName)
    {
        Goods goods = goodsDao.findByGoodsIdAndName(goodsId, goodsName);
        if (goods != null)
        {
            GoodsDto goodsDto = new GoodsDto();
            BeanUtils.copyProperties(goods, goodsDto);
            return goodsDto;
        }
        return null;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.shop.manage.service.IGoodsTypeService#findGoodsTypeById(java.lang.String)   
    */
    @Override
    public GoodsDto findGoodsById(String id)
    {
        GoodsDto r = new GoodsDto();
        Goods goods = goodsDao.findGoodsById(id);
        BeanUtils.copyProperties(goods, r);
        return r;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.shop.manage.service.IGoodsTypeService#delete(com.ssic.shop.manage.dto.GoodsTypeDto)   
    */
    @Override
    public void delete(GoodsDto r)
    {
        Goods goods = new Goods();
        BeanUtils.copyProperties(r, goods);
        goods.setStat(DataStatus.DISABLED);
        goodsDao.updateByPrimaryKeySelective(goods);
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.shop.manage.service.IGoodsService#add(com.ssic.shop.manage.dto.GoodsDto)   
    */
    @Override
    public void add(GoodsDto goodsDto)
    {
        Goods goods = new Goods();
        BeanUtils.copyProperties(goodsDto, goods);
        goods.setStat(DataStatus.ENABLED);
        goods.setCreateTime(new Date());
        goodsDao.insert(goods);
    }

    /** 
     * (non-Javadoc)   
     * @see com.ssic.shop.manage.service.IGoodsService#findIsTurnAllBy(com.ssic.shop.manage.pojo.Goods) 
     * @author pengcheng.yang
     * @date 2015-08-28 上午 11:18  
     */
    @Override
    public List<GoodsDto> findIsTurnAllBy(GoodsDto goodsDto)
    {
        List<GoodsDto> result = new ArrayList<GoodsDto>();
        Goods param = new Goods();
        BeanUtils.copyProperties(goodsDto, param);
        List<Goods> list = goodsDao.findIsTurnAllBy(param);
        if (!CollectionUtils.isEmpty(list))
        {
            result = BeanUtils.createBeanListByTarget(list, GoodsDto.class);
        }
        return result;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.shop.manage.service.IGoodsService#findLimitedQualityAllBy(com.ssic.shop.manage.dto.GoodsDto, com.ssic.shop.manage.dto.PageHelperDto)   
    * @author pengcheng.yang
    * @date 2015-08-28 上午 11:25
    */
    @Override
    public List<GoodsDto> findLimitedQualityAllBy(GoodsDto goodsDto, PageHelper ph)
    {
        List<GoodsDto> result = new ArrayList<GoodsDto>();

        PageHelperDto phdto = new PageHelperDto();
        phdto.setPage(ph.getPage());
        phdto.setRows(ph.getRows());
        phdto.setBeginRow((ph.getPage() - 1) * ph.getRows());

        Goods param = new Goods();
        BeanUtils.copyProperties(goodsDto, param);
        List<Goods> list = goodsDao.findLimitedQualityAllBy(param, phdto);
        if (!CollectionUtils.isEmpty(list))
        {
            result = BeanUtils.createBeanListByTarget(list, GoodsDto.class);
        }
        return result;
    }

    /**
     * 
     * findGoodsBy：查询详情页面限时特购的商品信息
     * @param cafetoriumId
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年9月16日 上午11:16:07
     */
    @Override
    public Response<FindDataResults> findGoodsLimitedBy(String cafetoriumId, PageHelper ph)
    {
        FindDataResults findDataResults = new FindDataResults();
        if (!StringUtils.isEmpty(cafetoriumId))
        {
            if (!StringUtils.isEmpty(cafetoriumId))
            {
                //限时特购
                GoodsDto goodsDto = new GoodsDto();
                goodsDto.setCafetoriumId(cafetoriumId);
                goodsDto.setGoodsTypeId("1");
                List<GoodsDto> list = findLimitedQualityAllBy(goodsDto, ph);
                if (list.size() > 0)
                {
                    findDataResults.setGoodslimitList(list);
                }
                else
                {
                    return new Response<FindDataResults>(DataStatus.HTTP_FAILE, "无法查询到商品信息", null);
                }
            }
            else
            {
                return new Response<FindDataResults>(DataStatus.HTTP_FAILE, "该食堂下无商品", null);
            }

            return new Response<FindDataResults>(DataStatus.HTTP_SUCCESS, "", findDataResults);
        }
        else
        {
            return new Response<FindDataResults>(DataStatus.HTTP_FAILE, "无法查询到商品信息", null);
        }

    }

    /**
     * 
     * findGoodsFineBy：查询详情页面精品推荐的商品信息
     * @param cafetoriumId
     * @param ph
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年9月16日 上午11:56:23
     */
    @Override
    public Response<FindDataResults> findGoodsFineBy(String cafetoriumId, PageHelper ph)
    {
        FindDataResults findDataResults = new FindDataResults();
        if (!StringUtils.isEmpty(cafetoriumId))
        {
            if (!StringUtils.isEmpty(cafetoriumId))
            {
                //精品推荐
                GoodsDto goodsDto = new GoodsDto();
                goodsDto.setCafetoriumId(cafetoriumId);
                goodsDto.setGoodsTypeId("2");
                List<GoodsDto> list = findLimitedQualityAllBy(goodsDto, ph);
                if (list.size() > 0)
                {
                    findDataResults.setGoodsFineList(list);
                }
                else
                {
                    return new Response<FindDataResults>(DataStatus.HTTP_FAILE, "无法查询到商品信息", null);
                }
            }
            else
            {
                return new Response<FindDataResults>(DataStatus.HTTP_FAILE, "该食堂下无商品", null);
            }

            return new Response<FindDataResults>(DataStatus.HTTP_SUCCESS, "", findDataResults);
        }
        else
        {
            return new Response<FindDataResults>(DataStatus.HTTP_FAILE, "无法查询到商品信息", null);
        }

    }

    /**
     * 
     * findGoodsIsTurnBy：查询轮播商品信息
     * @param cafetoriumId
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年9月16日 上午11:17:01
     */
    @Override
    public Response<FindDataResults> findGoodsIsTurnBy(String cafetoriumId)
    {
        FindDataResults findDataResults = new FindDataResults();
        if (!StringUtils.isEmpty(cafetoriumId))
        {
            GoodsDto goodsDto = new GoodsDto();
            goodsDto.setCafetoriumId(cafetoriumId);
            goodsDto.setIsTurn(1);
            List<GoodsDto> list = findIsTurnAllBy(goodsDto);
            if (list.size() > 0)
            {
                findDataResults.setGoodsIsTurnList(list);
            }
            else
            {
                return new Response<FindDataResults>(DataStatus.HTTP_FAILE, "无法查询到商品信息", null);
            }
            return new Response<FindDataResults>(DataStatus.HTTP_SUCCESS, "", findDataResults);
        }
        else
        {
            return new Response<FindDataResults>(DataStatus.HTTP_FAILE, "无法查询到商品信息", null);
        }

    }

    /**
     * 
     * findGoodsIsTurnBy：查询首页限时特购和精品推荐商品信息
     * @param cafetoriumId
     * @return
     * @exception   
     * @author pengcheng.yang
     * @date 2015年9月16日 下午1:59:20
     */
    @Override
    public List<GoodsDto> findlimitAndFineStickAllBy(GoodsDto goodsDto)
    {
        List<GoodsDto> result = new ArrayList<GoodsDto>();
        Goods param = new Goods();
        BeanUtils.copyProperties(goodsDto, param);
        List<Goods> list = goodsDao.findlimitAndFineStickAllBy(param);
        if (!CollectionUtils.isEmpty(list))
        {
            result = BeanUtils.createBeanListByTarget(list, GoodsDto.class);
        }
        return result;
    }

    /**
     * 
     * findGoodsIsTurnBy：查询首页限时特购商品信息
     * @param cafetoriumId
     * @return
     * @exception   
     * @author pengcheng.yang
     * @date 2015年9月16日 下午2:09:40
     */
    @Override
    public Response<FindDataResults> findlimitStickAllBy(String cafetoriumId)
    {

        FindDataResults findDataResults = new FindDataResults();
        GoodsDto Dto = new GoodsDto();
        Dto.setCafetoriumId(cafetoriumId);
        Dto.setGoodsTypeId("1");
        List<GoodsDto> list = findlimitAndFineStickAllBy(Dto);
        if (list.size() > 0)
        {
            findDataResults.setGoodslimitStickList(list);
        }
        else
        {
            return new Response<FindDataResults>(DataStatus.HTTP_FAILE, "无限时特购商品信息", null);
        }
        return new Response<FindDataResults>(DataStatus.HTTP_SUCCESS, "", findDataResults);
    }

    /**
     * 
     * findGoodsIsTurnBy：查询首页精品推荐商品信息
     * @param cafetoriumId
     * @return
     * @exception   
     * @author pengcheng.yang
     * @date 2015年9月16日 下午2:10:01
     */
    @Override
    public Response<FindDataResults> findFineStickAllBy(String cafetoriumId)
    {
        FindDataResults findDataResults = new FindDataResults();
        GoodsDto Dto = new GoodsDto();
        Dto.setCafetoriumId(cafetoriumId);
        Dto.setGoodsTypeId("2");
        List<GoodsDto> list = findlimitAndFineStickAllBy(Dto);
        if (list.size() > 0)
        {
            findDataResults.setGoodsFineStickList(list);
        }
        else
        {
            return new Response<FindDataResults>(DataStatus.HTTP_FAILE, "无限时特购商品信息", null);
        }
        return new Response<FindDataResults>(DataStatus.HTTP_SUCCESS, "", findDataResults);
    }

    /**
     * 
     * update：更新商品库存
     * @param GoodsDto
     * @return
     * @exception   
     * @author yuanbin
     * @date 2015年9月17日 下午2:10:01
     */
    @Override
    public void update(GoodsDto goodsDto)
    {
        Goods record = new Goods();
        BeanUtils.copyProperties(goodsDto, record);
        goodsDao.update(record);
    }
    
    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.shop.manage.service.IGoodsService#insertGoodsByExcel(java.util.List)
     */
    @Transactional
    @Override
    public int insertGoodsByExcel(List<GoodsDto> goodsList)
    {
        int count = 0;
        if(!CollectionUtils.isEmpty(goodsList))
        {
            for(GoodsDto goodsDto:goodsList)
            {
                Goods param = new Goods();
                String goodsId = UUIDGenerator.getUUID();
                param.setId(goodsId);
                BeanUtils.copyProperties(goodsDto, param);
                count = count + goodsDao.insert(param);
                
                //商品详情轮播图
                GoodsImage goodsImage = new GoodsImage();
                goodsImage.setId(UUIDGenerator.getUUID());
                goodsImage.setGoodsId(goodsId);
                goodsImage.setImageId(goodsDto.getTurnDetailImg());
                goodsImageDao.insert(goodsImage);
            }
            
            
        }
        
        return count;
    }

}
