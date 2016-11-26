import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ssic.base.datasource.DataSourceHolderUtil;
import com.ssic.catering.base.dao.CarteDao;
import com.ssic.catering.base.dao.CarteTypeDao;
import com.ssic.catering.base.mapper.CarteMapper;
import com.ssic.catering.base.mapper.CarteTypeMapper;
import com.ssic.catering.base.pojo.Carte;
import com.ssic.catering.base.pojo.CarteExample;
import com.ssic.catering.base.pojo.CarteType;
import com.ssic.catering.base.pojo.CarteTypeExample;
import com.ssic.shop.manage.dao.DinnerSeriesDao;
import com.ssic.shop.manage.dao.GoodsDao;
import com.ssic.shop.manage.dao.GoodsImageExDao;
import com.ssic.shop.manage.dao.IntegralExchangeDao;
import com.ssic.shop.manage.dao.IntegralExchangeTypeDao;
import com.ssic.shop.manage.dao.LuckyDrawDao;
import com.ssic.shop.manage.mapper.GoodsImageMapper;
import com.ssic.shop.manage.mapper.GoodsMapper;
import com.ssic.shop.manage.pojo.DinnerSeries;
import com.ssic.shop.manage.pojo.DinnerSeriesWithBLOBs;
import com.ssic.shop.manage.pojo.Goods;
import com.ssic.shop.manage.pojo.GoodsExample;
import com.ssic.shop.manage.pojo.GoodsImage;
import com.ssic.shop.manage.pojo.GoodsImageExample;
import com.ssic.shop.manage.pojo.IntegralExchange;
import com.ssic.shop.manage.pojo.IntegralExchangeType;
import com.ssic.shop.manage.pojo.LuckyDraw;
import com.ssic.util.StringUtils;
import com.ssic.util.UUIDGenerator;
import com.ssic.util.constants.DataStatus;

/**		
 * <p>Title: AppTest </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振	
 * @date 2015年11月6日 下午3:08:06	
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年11月6日 下午3:08:06</p>
 * <p>修改备注：</p>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:/spring-config/applicationContext.xml" })
public class CafetoriumDataAdd
{
    @Autowired
    private CarteDao carteDao;

    @Autowired
    private CarteMapper carteMapper;

    @Autowired
    private CarteTypeMapper carteTypeMapper;

    @Autowired
    private CarteTypeDao carteTypeDao;

    @Autowired
    private IntegralExchangeTypeDao integralExchangeTypeDao;

    @Autowired
    private IntegralExchangeDao integralExchangeDao;

    @Autowired
    private DinnerSeriesDao dinnerSeriesDao;

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsImageExDao goodsImageDao;

    @Autowired
    private GoodsImageMapper goodsImageMapper;
    @Autowired
    private LuckyDrawDao luckyDao;

    @Test
    public void insert()
    {
        List<String> cafeIdList = new ArrayList<String>();
        cafeIdList.add("0b3836dc-c6d2-49b4-a76a-741b0c3ef48e");
        cafeIdList.add("132e181d-3299-4dfe-8113-9efd2372e6dd");
        cafeIdList.add("17ab4162-99e7-44a5-85a1-72de4cb7310a");
        cafeIdList.add("180316d7-b688-47f5-9026-26f708c521e7");
        cafeIdList.add("1b9e2139-d370-478d-8d5e-0537c571225c");
        cafeIdList.add("25b31451-e881-4fe8-9464-5e8b61d27a7d");
        cafeIdList.add("2b16b5b2-ba9d-46da-bdd3-c0f65946c6f3");
        cafeIdList.add("49476e7e-a607-4365-8537-2ce231588287");
        cafeIdList.add("53cb1a09-e048-40a2-8d9f-ecabfdfd07ac");
        cafeIdList.add("6917f04b-bd7a-41ed-a0ae-7620698268dc");
        cafeIdList.add("6caf9b56-7a26-4956-a646-e3fe40e27d1c");
        cafeIdList.add("736a2fd0-f88d-4e1b-9141-8285f0f33086");
        cafeIdList.add("77b2ce40-cc4e-4816-b1b2-5dca94fe6fca");
        cafeIdList.add("8e784e57-9329-452d-acc8-1ff5fc22cf27");
        cafeIdList.add("b04dd8a2-6e18-437d-9110-11d02fd346b9");
        cafeIdList.add("cc97ffbd-4131-4d77-b6c8-85bf403e0b18");
        
        
        

        //写入下周菜单
        DataSourceHolderUtil.setToMaster();
        String oldCafetorium = "13bb9c5b-1101-4e4e-ad87-d2a4f52c2f08";
        
        for(String cafetorium:cafeIdList)
        {
            this.insertIntegralExchangeTypeAndIntegralExchange(oldCafetorium, cafetorium);//积分兑换
            this.insertDinnerSeries(oldCafetorium, cafetorium);//上门服务
            this.insertCarteTypeAndCarte(oldCafetorium, cafetorium);//下周菜单
            this.insertGoods(oldCafetorium, cafetorium);//商品
        }
    }

    @Transactional
    public int insertDinnerSeries(String oldCafetoriumId, String newCafetoriumId)
    {
        DinnerSeries dinnerSeriesParam = new DinnerSeries();
        dinnerSeriesParam.setCafetoriumId(oldCafetoriumId);
        List<DinnerSeriesWithBLOBs> dinnerSerieses = dinnerSeriesDao.findBy(dinnerSeriesParam);
        int count = 0;
        for (DinnerSeriesWithBLOBs dinnerSeries : dinnerSerieses)
        {
            //判断新食堂是否已有记录
            DinnerSeries newParam = new DinnerSeries();
            newParam.setCafetoriumId(newCafetoriumId);
            newParam.setName(dinnerSeries.getName());

            if (CollectionUtils.isEmpty(dinnerSeriesDao.findBy(newParam)))
            {
                dinnerSeries.setCafetoriumId(newCafetoriumId);
                dinnerSeries.setId(UUIDGenerator.getUUID());
                dinnerSeries.setCreateTime(new Date());

                count = count + dinnerSeriesDao.insert(dinnerSeries);
            }

        }

        return count;
    }

    /**
     * 写入积分	 
     * @author 朱振	
     * @date 2015年11月6日 下午5:08:36	
     * @version 1.0
     * @param oldCafetoriumId
     * @param newCafetoriumId
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月6日 下午5:08:36</p>
     * <p>修改备注：</p>
     */
    @Transactional
    public int insertIntegralExchangeTypeAndIntegralExchange(String oldCafetoriumId, String newCafetoriumId)
    {
        IntegralExchangeType integralExchangeTypeParam = new IntegralExchangeType();
        integralExchangeTypeParam.setCafetoriumId(oldCafetoriumId);

        int count = 0;

        List<IntegralExchangeType> integralExchangeTypes =
            integralExchangeTypeDao.findBy(integralExchangeTypeParam);
        for (IntegralExchangeType integralExchangeType : integralExchangeTypes)
        {
            String oldIntegralExchangeTypeId = integralExchangeType.getId();
            String newIntegralExchangeTypeId = UUIDGenerator.getUUID();
            integralExchangeType.setId(newIntegralExchangeTypeId);
            integralExchangeType.setCreateTime(new Date());
            integralExchangeType.setCafetoriumId(newCafetoriumId);

            IntegralExchangeType newIntegralExchangeType = new IntegralExchangeType();
            newIntegralExchangeType.setCafetoriumId(newCafetoriumId);
            newIntegralExchangeType.setName(integralExchangeType.getName());

            //判断新食堂的积分兑换是否已存在
            if (CollectionUtils.isEmpty(integralExchangeTypeDao.findBy(newIntegralExchangeType)))
            {
                count = count + integralExchangeTypeDao.insert(integralExchangeType);//写入
            }

            IntegralExchange integralExchangeParam = new IntegralExchange();
            integralExchangeParam.setExchangeTypeId(oldIntegralExchangeTypeId);
            List<IntegralExchange> integralExchanges = integralExchangeDao.findBy(integralExchangeParam);
            for (IntegralExchange integralExchange : integralExchanges)
            {
                integralExchange.setId(UUIDGenerator.getUUID());
                integralExchange.setCreateTime(new Date());
                integralExchange.setExchangeTypeId(newIntegralExchangeTypeId);

                IntegralExchange newIntegralExchange = new IntegralExchange();
                newIntegralExchange.setExchangeTypeId(newIntegralExchangeTypeId);
                newIntegralExchange.setExchangeName(integralExchange.getExchangeName());
                //判断新食堂的积分兑换是否已存在
                if (CollectionUtils.isEmpty(integralExchangeDao.findBy(newIntegralExchange)))
                {
                    count = count + integralExchangeDao.insert(integralExchange);
                    ;//写入
                }
            }

        }

        return count;
    }

    public void tt(CarteType param)
    {
        CarteTypeExample example = new CarteTypeExample();
        CarteTypeExample.Criteria criteria = example.createCriteria();

        //id
        if (!StringUtils.isEmpty(param.getId()))
        {
            criteria.andIdEqualTo(param.getId());
        }
        //carteTypeName
        if (!StringUtils.isEmpty(param.getCarteTypeName()))
        {
            criteria.andCarteTypeNameEqualTo(param.getCarteTypeName());
        }
        //upperLimit
        if (!StringUtils.isEmpty(param.getUpperLimit()))
        {
            criteria.andUpperLimitEqualTo(param.getUpperLimit());
        }
        //icon
        if (!StringUtils.isEmpty(param.getIcon()))
        {
            criteria.andIconEqualTo(param.getIcon());
        }
        //stat
        if (null != param.getStat())
        {
            criteria.andStatEqualTo(DataStatus.ENABLED);
        }
        //cafetoriumId
        if (!StringUtils.isEmpty(param.getCafetoriumId()))
        {
            criteria.andCafetoriumIdEqualTo(param.getCafetoriumId());
        }
        //icon2
        if (!StringUtils.isEmpty(param.getIcon2()))
        {
            criteria.andIcon2EqualTo(param.getIcon2());
        }

    }

    /**
     * 写入下周菜单	 
     * @author 朱振	
     * @date 2015年11月6日 下午5:07:13	
     * @version 1.0
     * @param oldCafetoriumId
     * @param newCafetoriumId
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月6日 下午5:07:13</p>
     * <p>修改备注：</p>
     */
    @Transactional
    public int insertCarteTypeAndCarte(String oldCafetoriumId, String newCafetoriumId)
    {
        CarteType newCartType = new CarteType();
        newCartType.setCafetoriumId(oldCafetoriumId);
        List<CarteType> carteTypes = this.findCarteTypeBy(newCartType);

        int count = 0;

        for (CarteType carteType : carteTypes)
        {
            String oldCarteType = carteType.getId();
            String newTypeId = UUIDGenerator.getUUID();
            carteType.setId(newTypeId);
            carteType.setCreateTime(new Date());
            carteType.setCafetoriumId(newCafetoriumId);

            CarteType newCarteType = new CarteType();
            newCarteType.setCarteTypeName(carteType.getCarteTypeName());
            newCarteType.setCafetoriumId(newCafetoriumId);

            // 判断新食堂菜品类型是否已存在
            if (CollectionUtils.isEmpty(this.findCarteTypeBy(newCarteType)))
            {
                //写入菜品类型
                count = count + carteTypeDao.insert(carteType);
            }

            Carte oldCarteParam = new Carte();
            oldCarteParam.setCafetoriumId(oldCafetoriumId);
            oldCarteParam.setCarteTypeId(oldCarteType);
            List<Carte> cartes = this.findCarteBy(oldCarteParam);
            for (Carte carte : cartes)
            {
                carte.setId(UUIDGenerator.getUUID());
                carte.setCafetoriumId(newCafetoriumId);
                carte.setCarteTypeId(newTypeId);

                Carte newCarte = new Carte();
                newCarte.setCafetoriumId(newCafetoriumId);
                newCarte.setCarteTypeId(newTypeId);
                newCarte.setCarteName(carte.getCarteName());

                // 判断新食堂菜品是否已存在
                if (CollectionUtils.isEmpty(this.findCarteBy(newCarte)))
                {
                    //写入菜品
                    count = count + carteDao.insert(carte);
                }

            }
        }

        return count;
    }

    public List<CarteType> findCarteTypeBy(CarteType param)
    {
        CarteTypeExample example = new CarteTypeExample();
        CarteTypeExample.Criteria criteria = example.createCriteria();

        //id
        if (!StringUtils.isEmpty(param.getId()))
        {
            criteria.andIdEqualTo(param.getId());
        }
        //carteTypeName
        if (!StringUtils.isEmpty(param.getCarteTypeName()))
        {
            criteria.andCarteTypeNameEqualTo(param.getCarteTypeName());
        }
        //upperLimit
        if (!StringUtils.isEmpty(param.getUpperLimit()))
        {
            criteria.andUpperLimitEqualTo(param.getUpperLimit());
        }
        //icon
        if (!StringUtils.isEmpty(param.getIcon()))
        {
            criteria.andIconEqualTo(param.getIcon());
        }
        //stat
        criteria.andStatEqualTo(1);
        //cafetoriumId
        if (!StringUtils.isEmpty(param.getCafetoriumId()))
        {
            criteria.andCafetoriumIdEqualTo(param.getCafetoriumId());
        }
        //icon2
        if (!StringUtils.isEmpty(param.getIcon2()))
        {
            criteria.andIcon2EqualTo(param.getIcon2());
        }

        example.setOrderByClause(" create_time asc");

        return carteTypeMapper.selectByExample(example);
    }

    public List<Carte> findCarteBy(Carte param)
    {
        CarteExample example = new CarteExample();
        CarteExample.Criteria criteria = example.createCriteria();
        //id
        if (!StringUtils.isEmpty(param.getId()))
        {
            criteria.andIdEqualTo(param.getId());
        }
        //carteName
        if (!StringUtils.isEmpty(param.getCarteName()))
        {
            criteria.andCarteNameEqualTo(param.getCarteName());
        }
        //sustenance
        if (null != param.getSustenance())
        {
            criteria.andSustenanceEqualTo(param.getSustenance());
        }
        //calorie
        if (null != param.getCalorie())
        {
            criteria.andCalorieEqualTo(param.getCalorie());
        }
        //carteTypeId
        if (!StringUtils.isEmpty(param.getCarteTypeId()))
        {
            criteria.andCarteTypeIdEqualTo(param.getCarteTypeId());
        }
        //cafetoriumId
        if (!StringUtils.isEmpty(param.getCafetoriumId()))
        {
            criteria.andCafetoriumIdEqualTo(param.getCafetoriumId());
        }
        //carteWeek
        if (null != param.getCarteWeek())
        {
            criteria.andCarteWeekEqualTo(param.getCarteWeek());
        }
        //lastUpdateTime
        //stat
        criteria.andStatEqualTo(1);
        //img
        if (!StringUtils.isEmpty(param.getImg()))
        {
            criteria.andImgEqualTo(param.getImg());
        }
        //count
        if (null != param.getCount())
        {
            criteria.andCountEqualTo(param.getCount());
        }
        //grams
        if (!StringUtils.isEmpty(param.getGrams()))
        {
            criteria.andGramsEqualTo(param.getGrams());
        }
        //carteDescribe
        if (!StringUtils.isEmpty(param.getCarteDescribe()))
        {
            criteria.andCarteDescribeEqualTo(param.getCarteDescribe());
        }
        //isNew
        if (null != param.getIsNew())
        {
            criteria.andIsNewEqualTo(param.getIsNew());
        }

        return carteMapper.selectByExample(example);
    }

    /**
     * 	 
     * @author 朱振	
     * @date 2015年11月11日 下午2:29:28	
     * @version 1.0
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月11日 下午2:29:28</p>
     * <p>修改备注：</p>
     */
    public List<Goods> findGoodsBy(Goods param)
    {
        GoodsExample example = new GoodsExample();
        GoodsExample.Criteria criteria = example.createCriteria();
        //id
        if (!StringUtils.isEmpty(param.getId()))
        {
            criteria.andIdEqualTo(param.getId());
        }
        //goodsName
        if (!StringUtils.isEmpty(param.getGoodsName()))
        {
            criteria.andGoodsNameEqualTo(param.getGoodsName());
        }
        //        //price
        //        if(!StringUtils.isEmpty(param.getPrice()))
        //        {
        //          criteria.andPriceEqualTo(param.getPrice());
        //        }
        //        //salesPrice
        //        if(!StringUtils.isEmpty(param.getSalesPrice()))
        //        {
        //          criteria.andSalesPriceEqualTo(param.getSalesPrice());
        //        }
        //icon
        if (!StringUtils.isEmpty(param.getIcon()))
        {
            criteria.andIconEqualTo(param.getIcon());
        }
        //detailIcon
        if (!StringUtils.isEmpty(param.getDetailIcon()))
        {
            criteria.andDetailIconEqualTo(param.getDetailIcon());
        }
        //turnIcon
        if (!StringUtils.isEmpty(param.getTurnIcon()))
        {
            criteria.andTurnIconEqualTo(param.getTurnIcon());
        }
        //introduce
        if (!StringUtils.isEmpty(param.getIntroduce()))
        {
            criteria.andIntroduceEqualTo(param.getIntroduce());
        }
        //isTurn
        if (null != param.getIsTurn())
        {
            criteria.andIsTurnEqualTo(param.getIsTurn());
        }
        //countLimit
        if (null != param.getCountLimit())
        {
            criteria.andCountLimitEqualTo(param.getCountLimit());
        }
        //percent
        if (null != param.getPercent())
        {
            criteria.andPercentEqualTo(param.getPercent());
        }
        //goodsTypeId
        if (!StringUtils.isEmpty(param.getGoodsTypeId()))
        {
            criteria.andGoodsTypeIdEqualTo(param.getGoodsTypeId());
        }
        //cafetoriumId
        if (!StringUtils.isEmpty(param.getCafetoriumId()))
        {
            criteria.andCafetoriumIdEqualTo(param.getCafetoriumId());
        }
        //goodsStocks
        if (null != param.getGoodsStocks())
        {
            criteria.andGoodsStocksEqualTo(param.getGoodsStocks());
        }
        //createTime
        //lastUpdateTime
        //stat
        if (null != param.getStat())
        {
            criteria.andStatEqualTo(DataStatus.ENABLED);
        }
        //isStick
        if (null != param.getIsStick())
        {
            criteria.andIsStickEqualTo(param.getIsStick());
        }
        //importdomestic
        if (!StringUtils.isEmpty(param.getImportdomestic()))
        {
            criteria.andImportdomesticEqualTo(param.getImportdomestic());
        }
        //placeorigin
        if (!StringUtils.isEmpty(param.getPlaceorigin()))
        {
            criteria.andPlaceoriginEqualTo(param.getPlaceorigin());
        }
        //distributionmode
        if (!StringUtils.isEmpty(param.getDistributionmode()))
        {
            criteria.andDistributionmodeEqualTo(param.getDistributionmode());
        }
        //unitsize
        if (!StringUtils.isEmpty(param.getUnitsize()))
        {
            criteria.andUnitsizeEqualTo(param.getUnitsize());
        }
        //productiondate
        if (!StringUtils.isEmpty(param.getProductiondate()))
        {
            criteria.andProductiondateEqualTo(param.getProductiondate());
        }
        //shelflife
        if (!StringUtils.isEmpty(param.getShelflife()))
        {
            criteria.andShelflifeEqualTo(param.getShelflife());
        }
        //purchase
        if (!StringUtils.isEmpty(param.getPurchase()))
        {
            criteria.andPurchaseEqualTo(param.getPurchase());
        }
        //detailsimgsrc
        if (!StringUtils.isEmpty(param.getDetailsimgsrc()))
        {
            criteria.andDetailsimgsrcEqualTo(param.getDetailsimgsrc());
        }

        return goodsMapper.selectByExample(example);

    }

    public List<GoodsImage> findGoodsImageBy(GoodsImage param)
    {
        GoodsImageExample example = new GoodsImageExample();
        GoodsImageExample.Criteria criteria = example.createCriteria();

        //id
        if (!StringUtils.isEmpty(param.getId()))
        {
            criteria.andIdEqualTo(param.getId());
        }
        //goodsId
        if (!StringUtils.isEmpty(param.getGoodsId()))
        {
            criteria.andGoodsIdEqualTo(param.getGoodsId());
        }
        //imageId
        if (!StringUtils.isEmpty(param.getImageId()))
        {
            criteria.andImageIdEqualTo(param.getImageId());
        }
        //createTime
        //lastUpdateTime
        //stat
        if (null != param.getStat())
        {
            criteria.andStatEqualTo(DataStatus.ENABLED);
        }

        return goodsImageMapper.selectByExample(example);
    }

    /**
     * 	 
     * @author 朱振	
     * @date 2015年11月11日 下午2:22:47	
     * @version 1.0
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月11日 下午2:22:47</p>
     * <p>修改备注：</p>
     */
    @Transactional
    public void insertGoods(String oldCafetoriumId, String newCafetoriumId)
    {
        Goods param = new Goods();
        param.setCafetoriumId(oldCafetoriumId);
        List<Goods> oldGoods = this.findGoodsBy(param);
        for (Goods good : oldGoods)
        {
            String oldGoodId = good.getId();
            Goods newParam = new Goods();
            newParam.setCafetoriumId(newCafetoriumId);
            newParam.setGoodsName(good.getGoodsName());
            newParam.setGoodsTypeId(good.getGoodsTypeId());

            List<Goods> g = this.findGoodsBy(newParam);
            if (CollectionUtils.isEmpty(g))
            {
                String newGoodsId = UUIDGenerator.getUUID();
                good.setId(newGoodsId);
                good.setCafetoriumId(newCafetoriumId);
                good.setCreateTime(new Date());
                goodsDao.insert(good);

                GoodsImage imageParam = new GoodsImage();
                imageParam.setGoodsId(oldGoodId);

                List<GoodsImage> oldImages = this.findGoodsImageBy(imageParam);
                for (GoodsImage image : oldImages)
                {
                    GoodsImage newImageParam = new GoodsImage();
                    newImageParam.setGoodsId(newGoodsId);
                    List<GoodsImage> g1 = this.findGoodsImageBy(newImageParam);
                    if (CollectionUtils.isEmpty(g1))
                    {
                        image.setId(UUIDGenerator.getUUID());
                        image.setGoodsId(newGoodsId);
                        image.setCreateTime(new Date());
//                        GoodsImageDto imgageDto = new GoodsImageDto();
//                        BeanUtils.copyProperties(image, imgageDto);
                        goodsImageDao.insert(image);
                    }
                }

            }
        }
    }

    /**
     * 微信商城:插入抽奖兑换记录 
     * @author 刘博   
     * @date 2015年11月6日 下午5:08:36   
     * @version 1.0
     * @param oldCafetoriumId
     * @param newCafetoriumId
     * @return
     * <p>修改人：刘博</p>
     * <p>修改时间：2015年11月6日 下午5:08:36</p>
     * <p>修改备注：</p>
     */
    @Transactional
    public void insertLuckDraw(String oldCafetoriumId, List<String> cafeIdList)
    {
        List<LuckyDraw> list = luckyDao.findByCafetoriumId(oldCafetoriumId);
        if (!CollectionUtils.isEmpty(list))
        {
            LuckyDraw draw = list.get(0);
            for (String cafeId : cafeIdList)
            {
                LuckyDraw luckDraw = new LuckyDraw();
                BeanUtils.copyProperties(draw, luckDraw);
                luckDraw.setCafetoriumId(cafeId);
                luckDraw.setId(UUID.randomUUID().toString());
                luckDraw.setCreateTime(new Date());
                luckDraw.setLastUpdateTime(new Date());
                luckyDao.insert(luckDraw);
            }
        }
    }

}
