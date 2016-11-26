package com.ssic.game.app.controller.catering;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.base.dto.NextWeekCarteDto;
import com.ssic.catering.base.dto.NextWeekCarteDtos;
import com.ssic.game.app.service.NextMenuOperateSerivce;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.pojo.NextWeekCarte;
import com.ssic.catering.base.util.GetPercent;
import com.ssic.catering.base.util.PropertieUtil;
import com.ssic.catering.common.util.DateUtil;

/**		
 * <p>Title: NextMenuController </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author yuanbin	
 * @date 2015年8月5日 上午11:33:06	
 * @version 1.0
 * <p>修改人：yuanbin</p>
 * <p>修改时间：2015年8月5日 上午11:33:06</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping("/http/api/nextMenu")
public class NextMenuController {

	  @Autowired
	  private NextMenuOperateSerivce nextMenuOperateSerivce;


	  /**         
	   * findNextMenu：查询过滤后的下周菜单信息
	   * @param query
	   * @return
	   * @exception	
	   * @author yuanbin
	   * @date 2015年8月4日 下午1:52:34	  
	   */	
	    @RequestMapping(value = "/findNextMenu.do")
	    @ResponseBody  
	    public  List<NextWeekCarteDto> findAll( PageHelperDto phDto, HttpServletRequest request) 
	    {
	    	NextWeekCarteDto nextWeekCarteDto=new NextWeekCarteDto();
	    	nextWeekCarteDto.setDishName("红烧肉");
	    	nextWeekCarteDto.setCarteWeek("1");
	    	nextWeekCarteDto.setCarteTypeId("1");
	    	
	    	
	    	//显示菜单(id+dishname)对象
	    	List<NextWeekCarte> nextWeekCartesList=new ArrayList<NextWeekCarte>();
	    	NextWeekCarte nextWeekCartes=null;
	    	List<NextWeekCarteDto> nextMenuList=nextMenuOperateSerivce.findAll(nextWeekCarteDto, phDto);
	        for(NextWeekCarteDto nextWeekCarte:nextMenuList){
	         nextWeekCartes=new NextWeekCarte();
	        	
	        	//判断菜单创建时间为上周日期，并计算“选择比”：单个菜品/菜单总数量
	        	String CarteWeek=String.valueOf(Integer.parseInt(nextWeekCartes.getCarteWeek())-1);
	        	int week=DateUtil.localWeek(nextWeekCartes.getCreateTime());
	        	if(CarteWeek.equals(nextWeekCartes.getCarteWeek())){
	        		nextWeekCartes.setId(nextWeekCarte.getId());
		        	nextWeekCartes.setDishName(nextWeekCarte.getDishName());
		        	
	        	}	
	        	nextWeekCartesList.add(nextWeekCartes);
	        }
	        
	        //perMap选择比对象:统计获取下周菜单中菜品重复的数量,并获取选择比；
	        Map<String, Object> perMap = new HashMap<String, Object>();
	        List<NextWeekCarteDtos> nextWeekCarteDtos=nextMenuOperateSerivce.findNextWeekCarteList();
	        for(NextWeekCarteDtos nextWeekCarte:nextWeekCarteDtos){      	
	        	//per选择百分比
	        	String per=GetPercent.getPercent(nextWeekCarte.getCountDishName(),nextMenuOperateSerivce.count());
	        	perMap.put(nextWeekCarte.getId(), per);
	        	System.out.print(perMap);
	        }
	        
	        //将菜单对象和选择比对象合并
	        for (Map.Entry entry : perMap.entrySet()) {
	        	   String key = (String) entry.getKey();
	        	   Object value = entry.getValue();
			}
	        
	        try {
	        	//List<>转为map<>
	        	Map<String, List<Object>> nextWeekCartesmap=PropertieUtil.getPropertiesMap(nextWeekCartesList, nextWeekCartes.getId());
			} catch (IllegalAccessException | InvocationTargetException
					| NoSuchMethodException e) {
				//  对异常进行简要描述		
			}
	    	//将菜单名map和选择比map放到同一个Map里；
	        //nextWeekCartesmap
	        
	         //map<菜单+选择比对象>
	        List<NextWeekCarteDto> result = new ArrayList<NextWeekCarteDto>(); 
              
	    		return result;
	         }
}










