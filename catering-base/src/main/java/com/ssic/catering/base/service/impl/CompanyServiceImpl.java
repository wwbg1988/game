/**
 * 
 */
package com.ssic.catering.base.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssic.catering.base.dao.CommentDao;
import com.ssic.catering.base.dao.CompanyDao;
import com.ssic.catering.base.dto.CompanyDto;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.pojo.Company;
import com.ssic.catering.base.service.ICompanyService;
import com.ssic.util.BeanUtils;
import com.ssic.util.constants.DataStatus;

/**		
 * <p>Title: CompanyServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年8月6日 下午5:19:18	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年8月6日 下午5:19:18</p>
 * <p>修改备注：</p>
 */
@Service
public class CompanyServiceImpl implements ICompanyService
{
    @Autowired
    private CompanyDao companyDao;
    
    @Autowired
    private CommentDao commentDao;

    /** 
    * (non-Javadoc)   
    * @see com.ssic.catering.base.service.ICompanyService#findAll(com.ssic.catering.base.dto.CompanyDto)   
    */
    @Override
    public List<CompanyDto> findAll(CompanyDto companyDto)
    {
        List<CompanyDto> result = new ArrayList<CompanyDto>();
        Company company = new Company();
        BeanUtils.copyProperties(companyDto, company);
        List<Company> list = companyDao.findAllBy(company, null);
        if (list != null && list.size() > 0)
        {
            result = BeanUtils.createBeanListByTarget(list, CompanyDto.class);
            return result;
        }
        return result;
    }

    /**
     * @desc 分页查询公司信息
     * @author pengcheng.yang
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.ICompanyService#findCompanyAllBy(com.ssic.catering.base.pojo.Company, com.ssic.catering.base.dto.PageHelperDto)
     */
    @Override
    public List<CompanyDto> findCompanyAllBy(CompanyDto company, PageHelperDto ph)
    {

        List<CompanyDto> result = new ArrayList<CompanyDto>();
        Company com = new Company();
        BeanUtils.copyProperties(company, com);
        List<Company> list = companyDao.findCompanyAllBy(com, ph);
        if(list != null && list.size() > 0){
            result = BeanUtils.createBeanListByTarget(list, CompanyDto.class);
        }
        return result;
    }

    /**
     * @desc 统计公司信息数量
     * @author pengcheng.yang
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.ICompanyService#countCompanyBy()
     */
    @Override
    public int countCompanyBy()
    {
        int flag = companyDao.countCompanyBy();
        if(flag > 0){
            return flag;
        }
        return 0;
    }

    /**
     * @desc 添加公司信息
     * @author pengcheng.yang
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.ICompanyService#insertCompanyInfo(com.ssic.catering.base.pojo.Company)
     */
    @Override
    public int insertCompanyInfo(CompanyDto company)
    {
        if(!StringUtils.isEmpty(company)){
            Company com = new Company();
            BeanUtils.copyProperties(company, com);
            com.setCreateTime(new Date());
            com.setStat(DataStatus.ENABLED);
            return companyDao.insertCompanyInfo(com);
        }
        return 0;
    }

    /**
     * @desc 更改公司信息
     * @author pengcheng.yang
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.ICompanyService#updateCompanyInfo(com.ssic.catering.base.pojo.Company)
     */
    @Override
    public int updateCompanyInfo(Company company)
    {
        if(!StringUtils.isEmpty(company)){
            company.setStat(DataStatus.ENABLED);
            return companyDao.updateCompanyInfo(company);
        }
        return 0;
    }

    /**
     * @desc 删除公司信息
     * @author pengcheng.yang
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.ICompanyService#deleteCompanyInfo(com.ssic.catering.base.pojo.Company)
     */
    @Override
    public int deleteCompanyInfo(CompanyDto company)
    {
        if(!StringUtils.isEmpty(company)){
            Company com = new Company();
            BeanUtils.copyProperties(company, com);
            com.setLastUpdateTime(new Date());
            com.setStat(DataStatus.DISABLED);
            return companyDao.deleteCompanyInfo(com);
        }
        return 0;
    }

    /**
     * @desc 根据公司Id查询出公司信息
     * @author pengcheng.yang
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.ICompanyService#getCompanyInfoById(java.lang.String)
     */
    @Override
    public Company getCompanyInfoById(String companyId)
    {
           return companyDao.getCompanyInfoById(companyId);
    }

    /**
     * @desc 查询公司名称是否存在
     * @author pengcheng.yang
     * (non-Javadoc)   
     * @see com.ssic.catering.base.service.ICompanyService#findByName(java.lang.String)
     */
    @Override
    public CompanyDto findByName(String companyName)
    {
        Company company = new Company();
        company.setCompanyName(companyName);
        List<Company> list = companyDao.findByName(company);
        if(!CollectionUtils.isEmpty(list)){
            CompanyDto dto = new CompanyDto();
            BeanUtils.copyProperties(list.get(0),dto);
            return dto;
        }
        return null;
    }
    
    

}
