/**
 * 
 */
package com.ssic.catering.base.service;

import java.util.List;

import com.ssic.catering.base.dto.CarteDto;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.pojo.Carte;

/**		
 * <p>Title: ICarteService </p>
 * <p>Description: 菜单接口</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年8月4日 上午9:12:11	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年8月4日 上午9:12:11</p>
 * <p>修改备注：</p>
 */
public interface ICarteService
{

    /**     
     * findALL：一句话描述方法功能
     * @param carteTypeId
     * @param phDto
     * @return
     * @exception   
     * @author Administrator
     * @date 2015年8月4日 下午7:45:41     
     */
    List<CarteDto> findALL(String carteTypeId, PageHelperDto phDto);

    
    /**     
     * findCount：一句话描述方法功能
     * @param carteTypeId
     * @return
     * @exception   
     * @author Administrator
     * @date 2015年8月4日 下午7:45:46     
     */
    int findCount(String carteTypeId);


    
    /**     
     * findById：一句话描述方法功能
     * @param id
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年8月5日 下午1:04:15	 
     */
    CarteDto findById(String id);


    
    /**     
     * delete：一句话描述方法功能
     * @param tempCarteType
     * @exception	
     * @author Administrator
     * @date 2015年8月5日 下午1:04:25	 
     */
    void delete(CarteDto tempCarte);


    
    /**     
     * findALLByTypeId：一句话描述方法功能
     * @param id
     * @return
     * @exception	
     * @author Administrator
     * @date 2015年8月5日 下午1:12:36	 
     */
    List<Carte> findALLByTypeId(String id);


    

    /**
	 * 通过周数查询菜品列表
	 * @return
	 */
	public List<Carte> getCarteListByCarteWeek(CarteDto carteDto);
    

    /**     
     * insertCartExcel：一句话描述方法功能
     * @param list
     * @exception	
     * @author Administrator
     * @date 2015年8月6日 下午1:01:43	 
     */
    void insertCartExcel(List<CarteDto> list);
    

    /**     
     * findAllByCafetorId：一句话描述方法功能
     * @paramid
     * @exception   
     * @author Administrator
     * @date 2015年8月6日 下午1:01:43     
     */
    List<Carte> findAllByCafetorId(String id);
    
    void updateImage(CarteDto carteDto);


    
    /**     
     * update：一句话描述方法功能
     * @param carteDto
     * @exception	
     * @author 刘博
     * @date 2015年8月20日 下午2:46:04	 
     */
    void update(CarteDto carteDto);
    
    
    /**
     * 获取最近的菜单作为下周菜单	 
     * @author 朱振	
     * @date 2015年10月10日 下午2:17:55	
     * @version 1.0
     * @param cafetoriumId
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年10月10日 下午2:17:55</p>
     * <p>修改备注：</p>
     */
    public List<CarteDto> getLastMenuByCafetoriumId(String cafetoriumId);
    
    /**
     * 更新数据	 
     * @author 朱振	
     * @date 2015年11月4日 上午9:56:44	
     * @version 1.0
     * @param carteDto
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月4日 上午9:56:44</p>
     * <p>修改备注：</p>
     */
    int updateBy(CarteDto carteDto);
}

