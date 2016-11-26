/**
 * 
 */
package com.ssic.catering.base.service;

import java.util.List;

import com.ssic.catering.base.dto.CompanyDto;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.pojo.Company;

/**		
 * <p>Title: ICompanyService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年8月6日 下午5:19:07	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年8月6日 下午5:19:07</p>
 * <p>修改备注：</p>
 */
public interface ICompanyService
{

    
    /**     
     * findAll：一句话描述方法功能
     * @param companyDto
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年8月6日 下午5:22:10	 
     */
    List<CompanyDto> findAll(CompanyDto companyDto);
    
    /**
     * 
     * countCompanyBy：分页查询公司信息
     * @return
     * @exception   
     * @author pengcheng.yang
     * @date 2015年10月21日 上午11:05:28
     */
    List<CompanyDto> findCompanyAllBy(CompanyDto company, PageHelperDto ph);
    
    /**
     * 
     * countCompanyBy：统计公司信息的数量
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年10月21日 上午11:06:13
     */
    int countCompanyBy();
    
    /**
     * 
     * insertCompanyInfo：添加公司信息
     * @param company
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年10月21日 上午11:05:59
     */
    int insertCompanyInfo(CompanyDto company);
    
    /**
     * 
     * updateCompanyInfo：更改公司信息
     * @param company
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年10月21日 上午11:06:44
     */
    int updateCompanyInfo(Company company);
    
    /**
     * 
     * deleteCompanyInfo：删除公司信息
     * @param company
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年10月21日 上午11:07:01
     */
    int deleteCompanyInfo(CompanyDto company);
    
    /**
     * 
     * getCompanyInfoById：根据公司Id查询出公司信息
     * @param companyId
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年10月21日 上午11:40:05
     */
    Company getCompanyInfoById(String companyId);
    
    /**
     * 
     * findByName：查询公司名称是否存在
     * @param companyName
     * @return
     * @exception	
     * @author pengcheng.yang
     * @date 2015年10月22日 上午9:26:01
     */
    CompanyDto findByName(String companyName);
    
}

