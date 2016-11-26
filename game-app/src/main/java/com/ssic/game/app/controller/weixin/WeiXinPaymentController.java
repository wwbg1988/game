package com.ssic.game.app.controller.weixin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ssic.game.common.dto.PayDto;
import com.ssic.game.common.weixinUtil.GetWxOrderno;
import com.ssic.game.common.weixinUtil.ReceiveXmlProcess;
import com.ssic.game.common.weixinUtil.RequestHandler;
import com.ssic.game.common.weixinUtil.TenpayUtil;
import com.ssic.shop.manage.dto.OrderDto;
import com.ssic.shop.manage.service.IOrderService;
import com.ssic.util.model.Response;

@RequestMapping("/weiXinPayment")
@Controller
public class WeiXinPaymentController
{

    @Autowired
    private IOrderService orderService;
    //下方参数需生成set/get
    private String openId;

    private String WIDtotal_fee;//总价

    protected static final Log log = LogFactory.getLog(WeiXinPaymentController.class);

    /**
         * 
         * 方法简要描述:获取预支付id  
         * 创建日期:   2014-12-3
         * 创建人：    zw
         * 修改说明：
     * @throws IOException 
         *
         */
    @RequestMapping("/pay")
    @ResponseBody
    public Response<SortedMap<String, String>> getWXPayPrepayid(HttpServletRequest request,
        HttpServletResponse response) throws IOException
    {
        Response<SortedMap<String, String>> r = new Response<>();
        //商户相关资料 
        String appid = "wx42df7b4462210ba0";
        String appsecret = "54d3f9d1da015c201192b777785b3a2d";
        String partner = "1266899501";
        String partnerkey = "5dec0d051cad4439b80efae495b6bf45";
        String openid = request.getParameter("openid");
        String orderNo = request.getParameter("orderNo");

        String goodsAmount = request.getParameter("goodsAmount");
        System.out.println("openid:" + openid);

        //商户订单号
        String product_id = orderNo;
        //金额
        String money = goodsAmount;//0.01
        //金额转化为分为单位
        float sessionmoney = Float.parseFloat(money);
        String finalmoney = String.format("%.2f", sessionmoney);
        finalmoney = finalmoney.replace(".", "");
        //获取openId后调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder
        String currTime = TenpayUtil.getCurrTime();
        //8位日期
        String strTime = currTime.substring(8, currTime.length());
        //四位随机数
        String strRandom = TenpayUtil.buildRandom(4) + "";
        //10位序列号,可以自行调整。
        String strReq = strTime + strRandom;
        //商户号
        String mch_id = partner;
        //随机数 
        String nonce_str = strReq;
        //商品描述根据情况修改
        String body = "美食";
        //附加数据
        String attach = "附加数据";
        //商户订单号
        String out_trade_no = product_id;
        //金额
        int intMoney = Integer.parseInt(finalmoney);
        //总金额以分为单位，不带小数点
        int total_fee = intMoney;
        //订单生成的机器 IP
        String spbill_create_ip = request.getRemoteAddr();
        //这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
        String notify_url = "http://tuancan.jujusports.cn/weiXinPayment/payTo.do";
        String trade_type = "JSAPI";//公众号支付

        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("appid", appid);
        packageParams.put("mch_id", mch_id);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("body", body);
        packageParams.put("attach", attach);
        packageParams.put("out_trade_no", out_trade_no);
        packageParams.put("total_fee", total_fee + "");
        packageParams.put("spbill_create_ip", spbill_create_ip);
        packageParams.put("notify_url", notify_url);
        packageParams.put("trade_type", trade_type);
        packageParams.put("openid", openid);
        RequestHandler reqHandler = new RequestHandler(request, response);
        reqHandler.init(appid, appsecret, partnerkey);
        String sign = reqHandler.createSign(packageParams);
        String xml =
            "<xml>" + "<appid>" + appid + "</appid>" + "<mch_id>" + mch_id + "</mch_id>" + "<nonce_str>"
                + nonce_str + "</nonce_str>" + "<sign>" + sign + "</sign>" + "<body><![CDATA[" + body
                + "]]></body>" + "<attach>" + attach + "</attach>" + "<out_trade_no>" + out_trade_no
                + "</out_trade_no>" + "<total_fee>" + total_fee + "</total_fee>" + "<spbill_create_ip>"
                + spbill_create_ip + "</spbill_create_ip>" + "<notify_url>" + notify_url + "</notify_url>"
                + "<trade_type>" + trade_type + "</trade_type>" + "<openid>" + openid + "</openid>"
                + "</xml>";
        System.out.println("生成预支付订单:" + xml);

        //生成预支付订单,并获取信息
        String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        Map<String, String> data = GetWxOrderno.getPayNo(createOrderURL, xml);
        String appId = appid;
        String timeStamp = new Date().getTime() + "";
        String nonceStr = nonce_str;
        String packageStr = "prepay_id=" + data.get("prepay_id");
        SortedMap<String, String> finalpackage = new TreeMap<String, String>();
        finalpackage.put("appId", appId);
        finalpackage.put("timeStamp", timeStamp);
        finalpackage.put("nonceStr", nonceStr);
        finalpackage.put("package", packageStr);
        finalpackage.put("signType", "MD5");
        String finalsign = reqHandler.createSign(finalpackage);
        finalpackage.put("paySign", finalsign);
        String json = JSON.toJSONString(finalpackage);
        System.out.println("返回的数据:" + json);
        //            response.getOutputStream().print(json);
        r.setData(finalpackage);
        return r;

    }

    /**
     * 
     * 方法简要描述:获取回调函数信息 
     * 创建日期:   2015-09-28
     * 创建人：   刘博
     * 修改说明：
    * @throws IOException 
     *
     */
    @RequestMapping("/payTo")
    public void callBackInfo(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        System.out.println("支付回调");
        // 设置输入和输出编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        /** 读取接收到的xml消息 */
        StringBuffer sb = new StringBuffer();
        InputStream is = request.getInputStream();
        InputStreamReader isr = new InputStreamReader(is, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String s = "";
        while ((s = br.readLine()) != null)
        {
            sb.append(s);
        }
        br.close();
        String xml = sb.toString(); // 接收到的微信端发送过来的xml数据
        System.out.println("回调返回的数据:" + xml);

        /** 解析xml数据 */
        PayDto payDto = new ReceiveXmlProcess().getMsgEntity(xml);
        //更新订单状态为已支付
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderNo(payDto.getOut_trade_no());
        //获取订单对象根据orderNo
        List<OrderDto> orderList = orderService.findBy(orderDto);
        if (!CollectionUtils.isEmpty(orderList))
        {
            OrderDto order_Dto = orderList.get(0);
            //更新订单状态为已完成:2;
            order_Dto.setOrderStatus(2);
            order_Dto.setPayTime(new Date());
            orderService.upateOrder(order_Dto);
        }
        if (payDto.getResult_code().equals("SUCCESS"))
        {
            response.getOutputStream().print("SUCCESS");
        }

    }

}
