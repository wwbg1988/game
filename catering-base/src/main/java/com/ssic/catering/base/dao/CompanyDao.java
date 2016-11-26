/**
 * 
 */
package com.ssic.catering.base.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssic.catering.base.dto.CompanyDto;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.mapper.CompanyMapper;
import com.ssic.catering.base.pojo.Company;
import com.ssic.catering.base.pojo.CompanyExample;
import com.ssic.catering.base.pojo.CompanyExample.Criteria;
import com.ssic.util.StringUtils;
import com.ssic.util.base.MyBatisBaseDao;
import com.ssic.util.constants.DataStatus;

/**		
 * <p>Title: CompanyDao </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年8月6日 下午5:17:57	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年8月6日 下午5:17:57</p>
 * <p>修改备注：</p>
 */
@Repository
public class CompanyDao extends MyBatisBaseDao<Company>
{
    @Autowired
    @Getter
    private CompanyMapper mapper;

    /**     
     * findAllBy：一句话描述方法功能
     * @param companyDto
     * @param phDto
     * @return 
     * @exception	
     * @author Administrator
     * @date 2015年8月6日 下午5:24:13	 
     */
    public List<Company> findAllBy(Company company, PageHelperDto ph)
    {
        CompanyExample example = new CompanyExample();
        Criteria criteria = example.createCriteria();
        if (ph != null && !StringUtils.isEmpty(String.valueOf(ph.getBeginRow()))
            && !StringUtils.isEmpty(String.valueOf(ph.getRows())))
        {
            int beginRow = ph.getBeginRow();
            int rows = ph.getRows();
            example.setOrderByClause("create_time desc limit " + beginRow + "," + rows);
        }
        else
        {
            example.setOrderByClause("create_time desc");
        }
        if (!StringUtils.isEmpty(company.getId()))
        {
            criteria.andIdEqualTo(company.getId());
        }
        if (!StringUtils.isEmpty(company.getProjId()))
        {
            criteria.andProjIdEqualTo(company.getProjId());
        }

        criteria.andStatEqualTo(DataStatus.ENABLED);
        return mapper.selectByExample(example);

    }

    /**
     * 
     * findCompanyAllBy：分页查询公司信息
     * @param company
     * @param ph
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年10月21日 上午10:45:24
     */
    public List<Company> findCompanyAllBy(Company company, PageHelperDto ph)
    {
        CompanyExample example = new CompanyExample();
        Criteria criteria = example.createCriteria();
        if (ph != null && !StringUtils.isEmpty(String.valueOf(ph.getBeginRow()))
            && !StringUtils.isEmpty(String.valueOf(ph.getRows())))
        {
            int beginRow = ph.getBeginRow();
            int rows = ph.getRows();
            example.setOrderByClause("create_time desc limit " + beginRow + "," + rows);
        }
        else
        {
            example.setOrderByClause("create_time desc");
        }
        if (!StringUtils.isEmpty(company.getId()))
        {
            criteria.andIdEqualTo(company.getId());
        }
        if (!StringUtils.isEmpty(company.getCompanyName()))
        {
            criteria.andCompanyNameLike("%" + company.getCompanyName() + "%");
        }
        if (!StringUtils.isEmpty(company.getProjId()))
        {
            criteria.andProjIdEqualTo(company.getProjId());
        }
        criteria.andStatEqualTo(1);
        return mapper.selectByExample(example);
    }

    /**
     * 
     * countCompanyBy：统计公司信息的数量
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年10月21日 上午10:50:40
     */
    public int countCompanyBy()
    {
        CompanyExample example = new CompanyExample();
        Criteria criteria = example.createCriteria();
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return mapper.countByExample(example);
    }

    /**
     * 
     * insertCompanyInfo：添加公司信息
     * @param company
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年10月21日 上午10:55:34
     */
    public int insertCompanyInfo(Company company)
    {
        if (company != null)
        {
            return mapper.insert(company);
        }
        return 0;
    }

    /**
     * 
     * updateCompanyInfo：修改公司信息
     * @param company
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年10月21日 上午10:59:18
     */
    public int updateCompanyInfo(Company company)
    {
        if (company != null)
        {
            return mapper.updateByPrimaryKey(company);
        }
        return 0;
    }

    /**
     * 
     * deleteCompanyInfo：删除公司信息
     * @param company
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年10月21日 上午11:02:45
     */
    public int deleteCompanyInfo(Company company)
    {
        if (company != null)
        {
            return mapper.updateByPrimaryKey(company);
        }
        return 0;
    }

    /**
     * 
     * getCompanyInfoById：根据公司Id查出公司信息
     * @param companyId
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年10月21日 上午11:36:48
     */
    public Company getCompanyInfoById(String companyId)
    {
        if (!StringUtils.isEmpty(companyId))
        {
            return mapper.selectByPrimaryKey(companyId);
        }
        return null;
    }

    /**
     * 
     * findByName：查询公司名称是否已经存在
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年10月22日 上午9:21:27
     */
    public List<Company> findByName(Company param)
    {
        CompanyExample example = new CompanyExample();
        Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(param.getId()))
        {
            criteria.andIdEqualTo(param.getId());
        }
        if (!StringUtils.isEmpty(param.getCompanyName()))
        {
            criteria.andCompanyNameEqualTo(param.getCompanyName());
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return mapper.selectByExample(example);
    }

}
