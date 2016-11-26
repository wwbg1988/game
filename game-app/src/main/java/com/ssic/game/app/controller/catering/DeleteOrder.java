package com.ssic.game.app.controller.catering;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.common.util.AppResponse;
import com.ssic.game.app.listener.InitApplicationContext;
import com.ssic.shop.manage.dto.ConsigneeDto;
import com.ssic.shop.manage.dto.OrderConsigneeDto;
import com.ssic.shop.manage.dto.OrderDetailDto;
import com.ssic.shop.manage.dto.OrderDto;
import com.ssic.shop.manage.service.IConsigneeService;
import com.ssic.shop.manage.service.IOrderConsigneeService;
import com.ssic.shop.manage.service.IOrderDetailService;
import com.ssic.shop.manage.service.IOrderService;
import com.ssic.util.BeanUtils;
import com.ssic.util.constants.DataStatus;
import com.ssic.util.model.Response;

@Controller
@RequestMapping("/ourOrder")
public class DeleteOrder {
	
	protected static final Log logger = LogFactory.getLog(InitApplicationContext.class);
	
	@Autowired
	private IOrderService iOrderService;
	
	@Autowired
	private IOrderDetailService iOrderDetailService;
	
	@Autowired
	private IConsigneeService iConsigneeService;
	
	@Autowired
	private IOrderConsigneeService iOrderConsigneeService;
	
	 /**     
     * deleteOrderById：删除未付款的订单
     * @param string 
     * @return
     * @exception	
     * @author yuanbin
     * @date 2015年10月12日 下午3:57:53	 
     */
    @RequestMapping(value = "/deleteOrder.do", method = {RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Response<String> deleteOrderById(String orderId){
    	Response<String> result = new Response<>();
        
        //删除物流信息
        OrderConsigneeDto orderConsigneeDto=new OrderConsigneeDto();
        orderConsigneeDto.setStat(DataStatus.ENABLED);
        orderConsigneeDto.setOrderId(orderId);
        List<OrderConsigneeDto> orderConsignee=iOrderConsigneeService.findBy(orderConsigneeDto);
        if(orderConsignee.size()>0){
        	for(OrderConsigneeDto orderConsignees:orderConsignee){
        		String consigneeId=orderConsignees.getConsigneeId();	
        		ConsigneeDto consigneeDto=iConsigneeService.findById(consigneeId);	
        		consigneeDto.setStat(DataStatus.DISABLED);
        		iConsigneeService.updateConsignee(consigneeDto);//DTO需要改
        		//删除物流订单关系
        		orderConsignees.setStat(DataStatus.DISABLED);
        		iOrderConsigneeService.updateOrderConsignee(orderConsignees);//DTO需要改
        	}
        }else{
        	logger.info("orderConsignee is null");
       	    result.setMessage("物流信息为空!");
        }
    	OrderDetailDto orderDetailDto = new OrderDetailDto();
        orderDetailDto.setStat(DataStatus.ENABLED);
        orderDetailDto.setOrderId(orderId);
        List<OrderDetailDto> OrderDetailList=iOrderDetailService.findBy(orderDetailDto);
        if(OrderDetailList.size()>0){
        	for(OrderDetailDto orderDetail:OrderDetailList){
        		//删除订单详情
        		orderDetail.setStat(DataStatus.DISABLED);
        		iOrderDetailService.updateOrderDetail(orderDetail);
        	}
        }else{
        	logger.info("orderDetail is null");
            result.setMessage("订单详情为空!");
        }

        OrderDto orderDto=iOrderService.findById(orderId);
        if(!StringUtils.isEmpty(orderDto)){
        	//删除订单
        	orderDto.setStat(DataStatus.DISABLED);
        	iOrderService.upateOrder(orderDto);
        	result.setMessage("删除订单成功!");
        	result.setStatus(AppResponse.RETURN_SUCCESS);
        	return result;
        }
        result.setMessage("删除订单失败!");
    	result.setStatus(AppResponse.RETURN_FAILE);
    	return result;
    }
}
