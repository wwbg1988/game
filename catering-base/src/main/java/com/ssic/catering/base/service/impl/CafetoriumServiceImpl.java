/**
 * 
 */
package com.ssic.catering.base.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ssic.catering.base.dao.CafetoriumDao;
import com.ssic.catering.base.dao.ConfigScoreDao;
import com.ssic.catering.base.dto.AddressDto;
import com.ssic.catering.base.dto.AddressUserDto;
import com.ssic.catering.base.dto.CafetoriumDto;
import com.ssic.catering.base.dto.CompanyDto;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.pojo.Cafetorium;
import com.ssic.catering.base.service.IAddressService;
import com.ssic.catering.base.service.IAddressUserService;
import com.ssic.catering.base.service.ICafetoriumService;
import com.ssic.catering.base.service.ICompanyService;
import com.ssic.game.common.dao.ProjectDao;
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.game.common.service.IImsUserService;
import com.ssic.util.BeanUtils;
import com.ssic.util.constants.DataStatus;

/**		
 * <p>Title: CafetoriumServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年8月6日 下午4:16:00	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年8月6日 下午4:16:00</p>
 * <p>修改备注：</p>
 */
@Service
public class CafetoriumServiceImpl implements ICafetoriumService
{
    private static final Logger log = Logger.getLogger(CafetoriumServiceImpl.class);

    @Autowired
    private CafetoriumDao cafetoriumDao;
    @Autowired
    private ICompanyService companyService;
    @Autowired
    private ConfigScoreDao configScoreDao;
    @Autowired
    private IAddressService addressService;
    @Autowired
    private IAddressUserService addressUserService;
    @Autowired
    private IImsUserService iImsUserService;
    @Autowired
    private ProjectDao projectDao;

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.ICafetoriumService#findALL(com.ssic.catering.base.dto.CafetoriumDto, com.ssic.catering.base.dto.PageHelperDto)   
    */
    @Override
    public List<CafetoriumDto> findALL(CafetoriumDto cafetoriumDto, PageHelperDto phDto)
    {
        List<CafetoriumDto> result = new ArrayList<CafetoriumDto>();
        Cafetorium cafetorium = new Cafetorium();
        BeanUtils.copyProperties(cafetoriumDto, cafetorium);
        List<Cafetorium> list = cafetoriumDao.findAllBy(cafetorium, phDto);
        if (list != null && list.size() > 0)
        {
            result = BeanUtils.createBeanListByTarget(list, CafetoriumDto.class);
            for (CafetoriumDto dto : result)
            {

                AddressUserDto addressUser = new AddressUserDto();
                addressUser.setCafeCode(dto.getCafeCode());
                addressUser.setAddressType(4);
                addressUser.setAddressId(dto.getAddressId());
                List<AddressUserDto> addreUserList = addressUserService.findAll(addressUser);
                if (!CollectionUtils.isEmpty(addreUserList))
                {
                    String userId = addreUserList.get(0).getUserId();
                    ImsUsersDto user = iImsUserService.findByUserId(userId);
                    if (user != null)
                    {
                        dto.setUserName(user.getName());
                    }
                }

                if (!StringUtils.isEmpty(dto.getCompanyId()))
                {
                    CompanyDto companyDto = new CompanyDto();
                    companyDto.setId(dto.getCompanyId());
                    List<CompanyDto> lists = companyService.findAll(companyDto);
                    if (!CollectionUtils.isEmpty(lists))
                    {
                        dto.setCompanyName(lists.get(0).getCompanyName());
                    }
                }

                if (!StringUtils.isEmpty(dto.getAddressId()))
                {
                    AddressDto aDt = addressService.findById(dto.getAddressId());
                    dto.setAddressName(aDt.getAddressName());
                }
                if (!StringUtils.isEmpty(dto.getProjId()))
                {
                    ProjectDto project = projectDao.findById(dto.getProjId());
                    dto.setProjName(project.getProjName());
                }

            }
            return result;
        }

        return result;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.ICafetoriumService#findCount(com.ssic.catering.base.dto.CafetoriumDto)   
    */
    @Override
    public int findCount(CafetoriumDto cafetoriumDto)
    {
        Cafetorium cafetorium = new Cafetorium();
        BeanUtils.copyProperties(cafetoriumDto, cafetorium);
        int counts = cafetoriumDao.findCountBy(cafetorium);
        return counts;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.ICafetoriumService#add(com.ssic.catering.base.dto.CafetoriumDto)   
    */
    @Override
    public void add(CafetoriumDto cafetoriumDto)
    {
        Cafetorium cafetorium = new Cafetorium();
        BeanUtils.copyProperties(cafetoriumDto, cafetorium);
        cafetorium.setStat(DataStatus.ENABLED);
        cafetorium.setCreateTime(new Date());
        cafetorium.setLastUpdateTime(new Date());
        cafetoriumDao.insert(cafetorium);

    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.ICafetoriumService#findById(java.lang.String)   
    */
    @Override
    public CafetoriumDto findById(String id)
    {
        Cafetorium cafetorium = cafetoriumDao.findById(id);
        if (cafetorium != null)
        {
            CafetoriumDto cafetoriumDto = new CafetoriumDto();
            BeanUtils.copyProperties(cafetorium, cafetoriumDto);
            return cafetoriumDto;
        }
        return null;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.ICafetoriumService#delete(com.ssic.catering.base.dto.CafetoriumDto)   
    */
    @Override
    public void delete(CafetoriumDto cafetoriumDto)
    {
        Cafetorium cafetorium = new Cafetorium();
        BeanUtils.copyProperties(cafetoriumDto, cafetorium);
        cafetorium.setStat(DataStatus.DISABLED);
        cafetorium.setLastUpdateTime(new Date());
        cafetoriumDao.updateByPrimaryKey(cafetorium);

    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.ICafetoriumService#update(com.ssic.catering.base.dto.CafetoriumDto)   
    */
    @Override
    public void update(CafetoriumDto cafetoriumDto)
    {
        Cafetorium cafetorium = new Cafetorium();
        BeanUtils.copyProperties(cafetoriumDto, cafetorium);
        cafetorium.setStat(DataStatus.ENABLED);
        cafetorium.setLastUpdateTime(new Date());
        cafetoriumDao.updateByPrimaryKey(cafetorium);

    }

    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.ICafetoriumService#totalWarningMessages(java.lang.String)
     */
    @Override
    public int totalWarningMessages(String cafetoriumId)
    {
        return cafetoriumDao.totalWarningMessages(cafetoriumId);
    }

    /**
     * getCafetoriumListByAddressId：通过区域id获取食堂列表,并封装每个食堂的平均分数
     * @param addressId 区域Id
     * @return
     * @exception	
     * @author 张亚伟
     * @date 2015年8月11日 上午9:07:57
     */
    @Override
    public List<CafetoriumDto> findCafetoriumListByAddressId(String addressId)
    {

        //获取食堂列表
        List<Cafetorium> cafetoriumList = cafetoriumDao.findCafetoriumListByaddressId(addressId);

        List<CafetoriumDto> cafetoriumDtoList = new ArrayList<>();
        //封装每个食堂的平均分
        for (Cafetorium cafetorium : cafetoriumList)
        {
            CafetoriumDto cafetoriumDto = new CafetoriumDto();
            BeanUtils.copyProperties(cafetorium, cafetoriumDto);
            //通过食堂Id获取食堂平均分

            //获取食堂评分条数
            int count = configScoreDao.findCafetoriumCountByCafetoriumId(cafetorium.getId(), new Date());

            if (count == 0)
            {
                cafetoriumDtoList.add(cafetoriumDto);

                continue;
            }
            //获取总分
            int score = configScoreDao.findCafetoriumScoreByCafetoriumId(cafetorium.getId(), new Date());

            if (score == 0)
            {
                cafetoriumDtoList.add(cafetoriumDto);
                continue;
            }

            //获取平均分
            double aveScore = Double.parseDouble(score + "") / Double.parseDouble(count + "");
            String avestr = String.valueOf(aveScore);
            if (avestr != null)
            {
                avestr = avestr.substring(0, 3);
            }
            cafetoriumDto.setScore(avestr);
            cafetoriumDtoList.add(cafetoriumDto);
        }

        return cafetoriumDtoList;
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.ICafetoriumService#getNewCafeCode()   
    */
    @Override
    public String getNewCafeCode(String projId)
    {
        String defaultCode = "1";
        Cafetorium cafetorium = cafetoriumDao.findCafeCodeByLastCreateTime();
        if (cafetorium != null)
        {
            //如果存在根节点的cafeCode,如：08；则当前应给食堂的食堂编码为09
            String lastCafeCode = cafetorium.getCafeCode();
            defaultCode = String.valueOf((Integer.valueOf(lastCafeCode) + 1));

        }
        return defaultCode;
    }

    @Override
    public List<CafetoriumDto> findCafetoriumAll()
    {
        //获取食堂列表
        List<Cafetorium> cafetoriumList = cafetoriumDao.findCafetoriumAll();

        List<CafetoriumDto> cafetoriumDtoList = new ArrayList<>();
        //封装每个食堂的平均分
        for (Cafetorium cafetorium : cafetoriumList)
        {
            CafetoriumDto cafetoriumDto = new CafetoriumDto();
            BeanUtils.copyProperties(cafetorium, cafetoriumDto);
            //通过食堂Id获取食堂平均分

            //获取食堂评分条数
            int count = configScoreDao.findCafetoriumCountByCafetoriumId(cafetorium.getId(), new Date());

            if (count == 0)
            {
                cafetoriumDtoList.add(cafetoriumDto);

                continue;
            }
            //获取总分
            int score = configScoreDao.findCafetoriumScoreByCafetoriumId(cafetorium.getId(), new Date());

            if (score == 0)
            {
                cafetoriumDtoList.add(cafetoriumDto);
                continue;
            }

            //获取平均分
            String aveScore = Double.parseDouble(score + "") / Double.parseDouble(count + "") + "";
            if (aveScore.length() > 3)
            {
                aveScore = aveScore.substring(0, 3);
            }
            cafetoriumDto.setScore(aveScore);
            cafetoriumDtoList.add(cafetoriumDto);
        }

        return cafetoriumDtoList;

    }

    /**
     * @desc 查询公司下是否用食堂
     * @author pengcheng.yang
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.ICafetoriumService#countCafetoriumByCompanyId(java.lang.String)
     */
    @Override
    public int countCafetoriumByCompanyId(String companyId)
    {
        return cafetoriumDao.countCafetoriumByCompanyId(companyId);
    }

    /**
     * @desc 根据proId查询相关食堂信息.
     * @author pengcheng.yang
     * @date 2015-10-27
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.ICafetoriumService#CafetoriumByProJectId(java.lang.String)
     */
    @Override
    public List<String> CafetoriumByProJectId(String userId)
    {
        return cafetoriumDao.CafetoriumByProJectId(userId);
    }

    /**
     * 
     * CafetoriumByProjId：查询当前用户拥有的projId
     * @param userId
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年10月28日 上午9:48:25
     */
    @Override
    public List<String> CafetoriumByProjId(String userId)
    {
        return cafetoriumDao.CafetoriumByProjId(userId);
    }

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.ICafetoriumService#findByName(java.lang.String)   
    */
    @Override
    public CafetoriumDto findByName(String cafeName, String projId)
    {
        Cafetorium cafetorium = new Cafetorium();
        cafetorium.setCafeName(cafeName);
        cafetorium.setProjId(projId);
        List<Cafetorium> list = cafetoriumDao.findByName(cafetorium);
        if (!CollectionUtils.isEmpty(list))
        {
            CafetoriumDto dto = new CafetoriumDto();
            BeanUtils.copyProperties(list.get(0), dto);
            return dto;
        }
        return null;
    }

    @Override
    public List<CafetoriumDto> findCafetoriumsBy(CafetoriumDto cafetorium)
    {
        if (cafetorium == null)
        {
            log.error("参数CafetoriumDto不能为空");
            return null;
        }

        Cafetorium param = new Cafetorium();
        BeanUtils.copyProperties(cafetorium, param);

        List<Cafetorium> result = cafetoriumDao.findBy(param);
        if (!CollectionUtils.isEmpty(result))
        {
            return BeanUtils.createBeanListByTarget(result, CafetoriumDto.class);
        }

        return null;
    }

    /**
    * 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.ICafetoriumService#getFollowedCafetoriumsByWeiXinIdAndProjectId(java.lang.String, java.lang.String)
    */
    @Override
    public List<CafetoriumDto> getFollowedCafetoriumsByWeiXinIdAndProjectId(String openId, String projectId)
    {
        if (StringUtils.isEmpty(openId))
        {
            log.error("微信id不能为空");
            return null;
        }

        if (StringUtils.isEmpty(projectId))
        {
            log.error("食堂id不能为空");
            return null;
        }

        return cafetoriumDao.findFollowedCafetoriumsByWeiXinIdAndProjectId(openId, projectId);
    }
    
    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.ICafetoriumService#getFollowedCafetoriumsByWeiXinId(java.lang.String)
     */
    @Override
    public List<CafetoriumDto> getFollowedCafetoriumsByWeiXinId(String openId)
    {
        if (StringUtils.isEmpty(openId))
        {
            log.error("微信id不能为空");
            return null;
        }

        return cafetoriumDao.findFollowedCafetoriumsByWeiXinId(openId);
    }
}
