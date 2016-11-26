package com.ssic.game.manage.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.ssic.game.common.dto.QJHistoryMDto;
import com.ssic.game.im.util.ConnectQinJiaIm;
import com.ssic.game.manage.qinjia.QJConnectDto;
import com.ssic.game.manage.qinjia.QinJiaConnectInfo;
import com.ssic.game.manage.service.IQinJiaService;


@Service
public class QinJiaServiceImpl implements  IQinJiaService{

	@Override
	public JSONObject sendMessage(QJConnectDto qJConnectDto) {
		
           QinJiaConnectInfo qinJiaConnectInfo = new QinJiaConnectInfo();
		   Map<Object,Object> map_push_info = new HashMap<Object,Object>();
	       // map_push_info.put("email", "wuweitree@163.com"); // 开发者账号，必选项
	       // map_push_info.put("devpwd", "wuwei123"); // 开发者密码，必选项
	       // map_push_info.put("appkey", "733a1dfc-d717-49e6-8ea9-3d700e1988b2"); // 应用的appkey，必选项
	        map_push_info.put("email", "cike534222598@qq.com"); // 开发者账号，必选项
	        map_push_info.put("devpwd", "Assassin"); // 开发者密码，必选项
	        map_push_info.put("appkey", "9d947bae-572d-4f90-ae78-b10e6f797138"); // 应用的appkey，必选项
	        map_push_info.put("to_type", "0"); // 发送对象类型，必选项。0发往用户，1发往聊天室，2发往群
	        map_push_info.put("save", "1"); // 此条数据是否保存（离线消息和历史记录），必选项。0不保存；1保存
	        map_push_info.put("msg_type", "0"); // 发送消息类型，必选项。0文本
	        
	        //如果list不为空则需要给多个用户发送ID,如果list为空则只给一个用户发送
	        if(qJConnectDto.getToIdList()!=null && qJConnectDto.getToIdList().size()>0){
	        	 map_push_info.put("to_id", qJConnectDto.getToIdList());
	        }else{
	        	List<String> to_id_list = new ArrayList<String>();
	   	        to_id_list.add(qJConnectDto.getToIds());
	   	        map_push_info.put("to_id", to_id_list); // 给发送的id,50个以内为佳，最多不要超过100个
	        }
	        
	        map_push_info.put("from", qJConnectDto.getFrom()); // 发送方账号，必选项，该账号必须存在
	        map_push_info.put("text", qJConnectDto.getText()); // 发送内容

	        JSONObject result = qinJiaConnectInfo.pushInformation(map_push_info);	        
		
		return result;
	}

	@Override
	public JSONObject addUser(QJConnectDto qJConnectDto) {
	 
		 Map<Object,Object> mapUser = new HashMap<Object, Object>();
		 mapUser.put("users", qJConnectDto.getUserList());     //需要导入的用户
		 mapUser.put("email", "cike534222598@qq.com"); // 开发者账号，必选项
		 mapUser.put("devpwd", "Assassin"); // 开发者密码，必选项
		 mapUser.put("appkey", "9d947bae-572d-4f90-ae78-b10e6f797138"); // 应用的appkey，必选项
		 JSONObject jsonObject =  JSONObject.fromObject(mapUser);
		
		 System.out.println("jsonObject-----"+jsonObject);
		  
		 ConnectQinJiaIm connectQinJiaIm = new ConnectQinJiaIm();
		 JSONObject hh = connectQinJiaIm.requestHttps("https://qplusapi.gotye.com.cn:8443/api/ImportUsers", jsonObject.toString());
		  
		  System.out.println("hh------"+hh);
		return hh;
	}

	@Override
	public JSONObject addFriend(QJConnectDto qJConnectDto) {

	   Map<String,String> map = new HashMap<String, String>();
  	   map.put("email", "cike534222598@qq.com");
  	   map.put("devpwd", "Assassin");
  	   map.put("appkey", "9d947bae-572d-4f90-ae78-b10e6f797138");
  	   map.put("user_account", qJConnectDto.getUserAccount());     //被操作的账号
  	   map.put("friend_account", qJConnectDto.getFriendAccount());    //添加为好友的账号
  	   map.put("type", "0");    //操作类型，必选项。0为好友，1为黑名单
  	   JSONObject jsonObject =  JSONObject.fromObject(map);
  	   System.out.println("jsonObject-----"+jsonObject);
  	  
  	   ConnectQinJiaIm connectQinJiaIm = new ConnectQinJiaIm();
  	   JSONObject result = connectQinJiaIm.requestHttps("https://qplusapi.gotye.com.cn:8443/api/AddBlacklist", jsonObject.toString());
  	   
  	   System.out.println("hh------"+result);
  	   
		return result;
	}

	@Override
	public JSONObject deletFriend(QJConnectDto qJConnectDto) {
		
		 Map<String,String> map = new HashMap<String, String>();
	  	   map.put("email", "cike534222598@qq.com");
	  	   map.put("devpwd", "Assassin");
	  	   map.put("appkey", "9d947bae-572d-4f90-ae78-b10e6f797138");
	  	   map.put("type", "0");    //操作类型，必选项。0为好友，1为黑名单
	  	   map.put("user_account", qJConnectDto.getUserAccount());     //被操作的账号
	  	   map.put("friend_account", qJConnectDto.getFriendAccount());    //删除好友的账号
	  	   JSONObject jsonObject =  JSONObject.fromObject(map);
	  	   System.out.println("jsonObject-----"+jsonObject);
	  	   
	  	   ConnectQinJiaIm connectQinJiaIm = new ConnectQinJiaIm();
	  	   JSONObject result = connectQinJiaIm.requestHttps("https://qplusapi.gotye.com.cn:8443/api/DelBlacklist", jsonObject.toString()); 
	  	   
           System.out.println("hh------"+result);
	  	   
			return result;
	}

	@Override
	public JSONObject getHistory(QJConnectDto qJConnectDto) {
		 Map<String,String> map = new HashMap<String, String>();
	  	   map.put("email", "cike534222598@qq.com");
	  	   map.put("devpwd", "Assassin");
	  	   map.put("appkey", "9d947bae-572d-4f90-ae78-b10e6f797138");
	  	   map.put("receiver_type", "0");  //接收者类型，必选项。0表示单聊、1表示聊天室、2表示群
	  	   map.put("receiver_id", "test222");  //与receiver_type对应，必选项。聊天室和群填写ID，用户填写账号
	  	   map.put("index", "0");  //开始行号，必选项
	  	   map.put("count", "20");  //分页的条数，可选项。默认为20，最大为50
	  	   JSONObject jsonObject =  JSONObject.fromObject(map);
	  	   System.out.println("jsonObject-----"+jsonObject);
	  	   
	  	   ConnectQinJiaIm connectQinJiaIm = new ConnectQinJiaIm();
	  	   JSONObject result = connectQinJiaIm.requestHttps("https://qplusapi.gotye.com.cn:8443/api/GetMsgHistory", jsonObject.toString());
           System.out.println("hh------"+result);
	  	  
	  	     JSONArray array =   result.getJSONArray("msg_list");
	  	     List<QJHistoryMDto> list = new ArrayList<QJHistoryMDto>();
	  	 //base64进行解码
	  	     for(int i=0;i<array.size();i++){
	  	    	 JSONObject jsonobject = (JSONObject) array.get(i);
	  	    	 QJConnectDto qjdto=	(QJConnectDto) JSONObject.toBean(jsonobject, QJConnectDto.class);
	  	    	 String msg_content64 = qjdto.getMsg_content();
	  	    	 String msg = fromBase64(msg_content64);
	  	    	 System.out.println("msg="+msg);
	  	    	 String sender_date = qjdto.getSender_date();
	  	    	 String send = fromDate(sender_date);
	  	    	 System.out.println("send="+send);
	  	    	 //解码之后插入新的类
	  	    	 QJHistoryMDto qJHistoryMDto = new QJHistoryMDto();
	  	    	qJHistoryMDto.setExtra_data(qjdto.getExtra_data());
	  	    	qJHistoryMDto.setMsg_content(msg);
	  	    	qJHistoryMDto.setMsg_id(qjdto.getMsg_id());
	  	    	qJHistoryMDto.setMsg_type(qjdto.getMsg_type());
	  	    	qJHistoryMDto.setReceiver_id(qjdto.getReceiver_id());
	  	    	qJHistoryMDto.setReceiver_type(qjdto.getReceiver_type());
	  	    	qJHistoryMDto.setSender_account(qjdto.getSender_account());
	  	    	qJHistoryMDto.setSender_date(send);
	  	    	list.add(qJHistoryMDto);
	  	     }
	  	    
	  	     JSONObject result2 = new JSONObject();
	  	   result2.put("msg_list", list);
	  	   result2.put("errcode",result.get("errcode"));
	  	   System.out.println("result2-----"+result2);
		
		return result2;
	}

    //base64进行解码
    public String fromBase64(String s){
    	if(s==null){
    		return null;
    	}
    	 sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder(); 
    	 byte[] b = null;
		try {
			b = decoder.decodeBuffer(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return new String(b);
    }
    
    //解析时间戳
    public String fromDate(String s){
    	//时间戳转化为String  
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	format.setTimeZone(TimeZone.getTimeZone("GMT+8")); 
        //Long time=Long.valueOf(s);  
        String d = format.format(new Date(Long.parseLong(s)));  
    	return d;
    }

	@Override
	public JSONObject uploadFile(QJConnectDto qJConnectDto) {
		// TODO Auto-generated method stub
		 Map<String,Object> map = new HashMap<String, Object>();
		 map.put("appkey", qJConnectDto.getAppkey());
		 map.put("file", qJConnectDto.getFile());
		 map.put("type", 2);   //1语音2图片
		 JSONObject jsonObject =  JSONObject.fromObject(map);
	  	   System.out.println("jsonObject-----"+jsonObject);
	  	   ConnectQinJiaIm connectQinJiaIm = new ConnectQinJiaIm();
	  	   JSONObject result = connectQinJiaIm.requestHttps("https://rest.gotye.com.cn/api/UploadFile", jsonObject.toString());
           System.out.println("hh------"+result);
		return result;
	}

	@Override
	public JSONObject createGroup(QJConnectDto qJConnectDto) throws UnsupportedEncodingException {
		 Map<Object,Object> mapUser = new HashMap<Object, Object>();
		
		 mapUser.put("email", "cike534222598@qq.com"); // 开发者账号，必选项
		 mapUser.put("devpwd", "Assassin"); // 开发者密码，必选项
		 mapUser.put("appkey", "2debe681-ce19-4389-92cb-abc6475ea349"); // 应用的appkey，必选项
		 
		 byte[] b1 = "董事会".getBytes("UTF-8");
		 String name1 = new String(b1, "UTF-8"); //编码解码相同，正常显示
		 mapUser.put("group_name",name1 );
		 //群名称
		 mapUser.put("owner_type", 0);  //是否为私有群, 0为公开群，1为私有群, 默认值 0
		 mapUser.put("approval", 0);    //是否需要验证, 0为自由加入，1为需要群主验证, 默认值 0
		 mapUser.put("group_info", "wwwwwwww");     //群信息
		 mapUser.put("owner_account", "qjy_Taylor");    //管理员帐号
		 mapUser.put("group_head", null);     //群头像, 图片二进制流用base64编码生成的字符串
		 
		 JSONObject jsonObject =  JSONObject.fromObject(mapUser);
		
		 System.out.println("jsonObject-----"+jsonObject);
		  
//		 ConnectQinJiaIm connectQinJiaIm = new ConnectQinJiaIm();
		 JSONObject hh = ConnectQinJiaIm.requestHttps("https://qplusapi.gotye.com.cn:8443/api/CreateGroup", new String(jsonObject.toString().getBytes("utf-8"), "utf-8"));
		  
		  System.out.println("hh------"+hh);
		return hh;
	}
	
}
