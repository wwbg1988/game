package com.ssic.catering.base.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gotye.entity.req.group.AddGroupMemberReq;
import com.gotye.entity.req.group.CreateGroupReq;
import com.gotye.entity.req.group.DelGroupMemberReq;
import com.gotye.entity.req.group.DismissGroupReq;
import com.gotye.entity.req.group.GetGroupsReq;
import com.gotye.entity.resp.group.AddGroupMemberResp;
import com.gotye.entity.resp.group.CreateGroupResp;
import com.gotye.entity.resp.group.DelGroupMemberResp;
import com.gotye.entity.resp.group.DismissGroupResp;
import com.gotye.entity.resp.group.GetGroupsResp;
import com.ssic.catering.base.dto.QjyCateringUserDto;
import com.ssic.catering.base.service.IQjyCateringService;
import com.ssic.catering.common.util.AppResponse;
import com.ssic.catering.common.util.QjyCateringConnect;
import com.ssic.game.im.util.ConnectQinJiaIm;
import com.ssic.gotye.GotyeApiClient.QJGroupApi;


@Service
public class QjyCateringServiceImpl implements IQjyCateringService{
	
	@Autowired
	private QJGroupApi qJGroupApi;
	
	 protected static final Log logger = LogFactory.getLog(QjyCateringServiceImpl.class);

	@Override
	public Map<String, String> sendMessage(QjyCateringUserDto qjyCateringUserDto) {


		Map<String,String> map = new HashMap<String, String>();
		
		if(qjyCateringUserDto.getToIdList()==null || qjyCateringUserDto.getToIdList().size()==0){
			if(qjyCateringUserDto.getToIds() == null || qjyCateringUserDto.getToIds().equals("")){
				map.put("Msg", "给发送的id不能空");
				map.put("Success", "false");
				return map;
			}
		}
		
		if(qjyCateringUserDto.getText() == null || qjyCateringUserDto.getText().equals("")){
			map.put("Msg", "信息内容不能为空");
			map.put("Success", "false");
			return map;
	
		}
		
		if(qjyCateringUserDto.getText().length()>150){
			map.put("Msg", "信息内容长度不能大于150");
			map.put("Success", "false");
			return map;
		}
		
		 Map<Object,Object> map_push_info = new HashMap<Object,Object>();
		   // map_push_info.put("email", "wuweitree@163.com"); // 开发者账号，必选项
	      //  map_push_info.put("devpwd", "wuwei123"); // 开发者密码，必选项
	       // map_push_info.put("appkey", "733a1dfc-d717-49e6-8ea9-3d700e1988b2"); // 应用的appkey，必选项
	        map_push_info.put("email", "cike534222598@qq.com"); // 开发者账号，必选项
	        map_push_info.put("devpwd", "Assassin"); // 开发者密码，必选项
	        map_push_info.put("appkey", "2debe681-ce19-4389-92cb-abc6475ea349"); // 应用的appkey，必选项
	        map_push_info.put("to_type", "0"); // 发送对象类型，必选项。0发往用户，1发往聊天室，2发往群
	        map_push_info.put("save", "1"); // 此条数据是否保存（离线消息和历史记录），必选项。0不保存；1保存
	        map_push_info.put("msg_type", "0"); // 发送消息类型，必选项。0文本
	        
	        //如果list不为空则需要给多个用户发送ID,如果list为空则只给一个用户发送
	        
	        if(qjyCateringUserDto.getToIdList().size()>100){
	        	map.put("Msg", "给发送的id不能大于100");
				map.put("Success", "false");
				return map;
	        }
	        
	        if(qjyCateringUserDto.getToIdList()!=null && qjyCateringUserDto.getToIdList().size()>0){
	        	 map_push_info.put("to_id", qjyCateringUserDto.getToIdList());
	        }else{
	        	List<String> to_id_list = new ArrayList<String>();
	   	        to_id_list.add(qjyCateringUserDto.getToIds());
	   	        map_push_info.put("to_id", to_id_list); // 给发送的id,50个以内为佳，最多不要超过100个
	        }
	        
	        map_push_info.put("from", "ims_system"); // 发送方账号，必选项，该账号必须存在
	       
	        String textInfo = "";
			try {
				textInfo = new String(qjyCateringUserDto.getText().getBytes(),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        map_push_info.put("text", textInfo); // 发送内容 
	      
	        
	        // map_push_info.put("text", QjySendMessage.Untreated);
	        
	        JSONObject param = new JSONObject();

	        String email = (String) map_push_info.get("email");
	        String devpwd = (String) map_push_info.get("devpwd");
	        String appkey = (String) map_push_info.get("appkey");
	        String from = (String) map_push_info.get("from");
	        String to_type = (String) map_push_info.get("to_type");
	        List<String> to_id =   (List<String>) map_push_info.get("to_id");
	        String save = (String) map_push_info.get("save");
	        String msg_type = (String) map_push_info.get("msg_type");
	        String text = (String) map_push_info.get("text");

	        param.put("email", email);
	        param.put("devpwd", devpwd);
	        param.put("appkey", appkey);
	        param.put("from", from);
	        param.put("to_type", to_type);
	        JSONArray jarray_id = JSONArray.fromObject(to_id);
	        param.put("to_id", jarray_id);
	        param.put("save", save);
	        param.put("msg_type", msg_type);
	        param.put("text", text);

	   
	        
	        logger.info("-------param=" + param);
	        QjyCateringConnect connectQinJia = new QjyCateringConnect();
	        
//            logger.info("----------开始查看，超过10秒停止连接亲加云继续下一步");
//            Map<String,String> msgMap = new HashMap<String, String>();
//            msgMap.put("url", "https://qplusapi.gotye.com.cn:8443/api/SendMsg");
//            msgMap.put("param",param.toString());
//            sendQJInfo(msgMap);
//	        
//            TimeOutCallMethod timeOutCallMethod = new TimeOutCallMethod();
//            timeOutCallMethod.callMethod("QjyImServiceImpl", "sendQJInfo", [Map.class], msgMap);
            
            
	        JSONObject hh = connectQinJia.requestHttps("https://qplusapi.gotye.com.cn:8443/api/SendMsg", param.toString());

	        logger.info("-------hh=" + hh);
	        
	        String error_code = hh.getString("errcode");
	     
	        
	        if ("200".equals(error_code))
	        {
	        	map.put("Msg", "亲加云连接成功");
	        	map.put("Success", "true");
	        	map.put("error_code", error_code);
	        	return map;
	        }
	        else
	        {
	        	map.put("Msg", "亲加云连接失败");
	        	map.put("Success", "false");
	        	map.put("error_code", error_code);
	        	return map;
	        }
	}

	@Override
	public JSONObject addFriend(QjyCateringUserDto qjyCateringUserDto) {

	   Map<String,String> map = new HashMap<String, String>();
  	   map.put("email", "cike534222598@qq.com");
  	   map.put("devpwd", "Assassin");
  	   map.put("appkey", "2debe681-ce19-4389-92cb-abc6475ea349");
  	   map.put("user_account", qjyCateringUserDto.getUserAccount());     //被操作的账号
  	   map.put("friend_account", qjyCateringUserDto.getFriendAccount());    //添加为好友的账号
  	   map.put("type", "0");    //操作类型，必选项。0为好友，1为黑名单
  	   JSONObject jsonObject =  JSONObject.fromObject(map);
  	   System.out.println("jsonObject-----"+jsonObject);
  	  
  	   ConnectQinJiaIm connectQinJiaIm = new ConnectQinJiaIm();
  	   JSONObject result = connectQinJiaIm.requestHttps("https://qplusapi.gotye.com.cn:8443/api/AddBlacklist", jsonObject.toString());
  	   
  	   System.out.println("hh------"+result);
  	   
		return result;
	}
	
	@Override
	public JSONObject deletFriend(QjyCateringUserDto qjyCateringUserDto) {
		
		 Map<String,String> map = new HashMap<String, String>();
	  	   map.put("email", "cike534222598@qq.com");
	  	   map.put("devpwd", "Assassin");
	  	   map.put("appkey", "2debe681-ce19-4389-92cb-abc6475ea349");
	  	   map.put("type", "0");    //操作类型，必选项。0为好友，1为黑名单
	  	   map.put("user_account", qjyCateringUserDto.getUserAccount());     //被操作的账号
	  	   map.put("friend_account", qjyCateringUserDto.getFriendAccount());    //删除好友的账号
	  	   JSONObject jsonObject =  JSONObject.fromObject(map);
	  	   System.out.println("jsonObject-----"+jsonObject);
	  	   
	  	   ConnectQinJiaIm connectQinJiaIm = new ConnectQinJiaIm();
	  	   JSONObject result = connectQinJiaIm.requestHttps("https://qplusapi.gotye.com.cn:8443/api/DelBlacklist", jsonObject.toString()); 
	  	   
           System.out.println("hh------"+result);
	  	   
			return result;
	}

	@Override
	public Map<String,Object> createGroup(QjyCateringUserDto qjyCateringUserDto) {
		// TODO Auto-generated method stub
		Map<String,Object> mapResult = new HashMap<String, Object>();
		if(qjyCateringUserDto.getOwnerAccount()==null || "".equals(qjyCateringUserDto.getOwnerAccount())){
			mapResult.put("Msg", "创建者ID不能为空!");
			mapResult.put("Success", "false");
			mapResult.put("data", null);
			return mapResult;
		}
		if(qjyCateringUserDto.getGroupName()==null || "".equals(qjyCateringUserDto.getGroupName())){
			mapResult.put("Msg", "群名称不能为空!");
			mapResult.put("Success", "false");
			mapResult.put("data", null);
			return mapResult;
		}
		
		 Map<String,String> map = new HashMap<String, String>();
	  	   map.put("email", "cike534222598@qq.com");
	  	   map.put("devpwd", "Assassin");
	  	   map.put("appkey", "2debe681-ce19-4389-92cb-abc6475ea349");
	  	   map.put("group_name", qjyCateringUserDto.getGroupName());    //群名称
	  	   map.put("group_head", null) ;  //群头像
	  	   map.put("owner_type", "0");  //所有类型，必选项。0为公开群，1为私有群
	  	   map.put("owner_account", qjyCateringUserDto.getOwnerAccount());  //所有者账号，必选项
	  	   map.put("approval", "0") ;   //加入类型，必选项。0为自由加入，1为需要群主验证
	  	   map.put("group_info", qjyCateringUserDto.getGroupInfo());   //群扩展信息，可选项。用于保存一些额外的信息，服务器不会对此信息做解析，在获取详情的时候可以拉取到
	
	  	   JSONObject jsonObject =  JSONObject.fromObject(map);
	  	   System.out.println("jsonObject-----"+jsonObject);
	  	   
	  	   ConnectQinJiaIm connectQinJiaIm = new ConnectQinJiaIm();
	  	   JSONObject result = connectQinJiaIm.requestHttps("https://qplusapi.gotye.com.cn:8443/api/CreateGroup", jsonObject.toString()); 
	  	   
           System.out.println("hh------"+result);
	       String error_code = result.getString("errcode");
	       mapResult.put("error_code", error_code);
	       mapResult.put("Success", "true");
	       mapResult.put("data", result);
	       return mapResult;
	}

	@Override
	public Map<String, Object> modifyGroup(QjyCateringUserDto qjyCateringUserDto) {
		// TODO Auto-generated method stub
		Map<String,Object> mapResult = new HashMap<String, Object>();
		if(qjyCateringUserDto.getGroupId()==null || "".equals(qjyCateringUserDto.getGroupId())){
			mapResult.put("Msg", "群ID不能为空!");
			mapResult.put("Success", "false");
			mapResult.put("data", null);
			return mapResult;
		}

		 Map<String,String> map = new HashMap<String, String>();
	  	   map.put("email", "cike534222598@qq.com");
	  	   map.put("devpwd", "Assassin");
	  	   map.put("appkey", "2debe681-ce19-4389-92cb-abc6475ea349");
	  	   map.put("group_id",qjyCateringUserDto.getGroupId());    //群ID
	  	   if(qjyCateringUserDto.getGroupName()!=null && !"".equals(qjyCateringUserDto.getGroupName())){
	  		  map.put("group_name", qjyCateringUserDto.getGroupName());   //群名称
	  	   }
	  	   if(qjyCateringUserDto.getGroupHead()!=null && !"".equals(qjyCateringUserDto.getGroupHead())){
	  		  map.put("group_head", qjyCateringUserDto.getGroupHead());   //群头像
	  	   }
	  	   map.put("owner_type", "0");   //所有类型，可选项。0为公开群，1为私有群
	  	   if(qjyCateringUserDto.getOwnerAccount()!=null && !"".equals(qjyCateringUserDto.getOwnerAccount())){
	  		  map.put("owner_account", qjyCateringUserDto.getOwnerAccount()); //所有者账号
	  	   }
	  	   map.put("approval", "0");    //加入类型，可选项。0为自由加入，1为需要群主验证
	  	   if(qjyCateringUserDto.getGroupInfo()!=null && !"".equals(qjyCateringUserDto.getGroupInfo())){
	  		  map.put("group_info", qjyCateringUserDto.getGroupInfo());
	  	   }
	  	
	  	   JSONObject jsonObject =  JSONObject.fromObject(map);
	  	   System.out.println("jsonObject-----"+jsonObject);
	  	   
	  	   ConnectQinJiaIm connectQinJiaIm = new ConnectQinJiaIm();
	  	   JSONObject result = connectQinJiaIm.requestHttps("https://qplusapi.gotye.com.cn:8443/api/ModifyGroup", jsonObject.toString()); 
	  	   
         System.out.println("hh------"+result);
	       String error_code = result.getString("errcode");
	       mapResult.put("error_code", error_code);
	       mapResult.put("Success", "true");
	       mapResult.put("data", result);
	       return mapResult;

	}

	@Override
	public Map<String, Object> getGroupUserList(QjyCateringUserDto qjyCateringUserDto) {
		// TODO Auto-generated method stub
		Map<String,Object> mapResult = new HashMap<String, Object>();
		if(qjyCateringUserDto.getGroupId()==null || "".equals(qjyCateringUserDto.getGroupId())){
			mapResult.put("Msg", "群ID不能为空!");
			mapResult.put("Success", "false");
			mapResult.put("data", null);
			return mapResult;
		}
		 Map<String,String> map = new HashMap<String, String>();
	  	   map.put("email", "cike534222598@qq.com");
	  	   map.put("devpwd", "Assassin");
	  	   map.put("appkey", "2debe681-ce19-4389-92cb-abc6475ea349");
	  	   map.put("group_id",qjyCateringUserDto.getGroupId());    //群ID
	  	   JSONObject jsonObject =  JSONObject.fromObject(map);
	  	   System.out.println("jsonObject-----"+jsonObject);
	  	   ConnectQinJiaIm connectQinJiaIm = new ConnectQinJiaIm();
	  	   JSONObject result = connectQinJiaIm.requestHttps("https://qplusapi.gotye.com.cn:8443/api/GetGroupUserList", jsonObject.toString()); 
	  	   
       System.out.println("hh------"+result);
	       String error_code = result.getString("errcode");
	       mapResult.put("error_code", error_code);
	       mapResult.put("Success", "true");
	       mapResult.put("data", result);
	       return mapResult;
	}

	@Override
	public Map<String, Object> addGroupMember(QjyCateringUserDto qjyCateringUserDto) {
		// TODO Auto-generated method stub
		Map<String,Object> mapResult = new HashMap<String, Object>();
		if(qjyCateringUserDto.getGroupId()==null || "".equals(qjyCateringUserDto.getGroupId())){
			mapResult.put("Msg", "群ID不能为空!");
			mapResult.put("Success", "false");
			mapResult.put("data", null);
			return mapResult;
		}
		if(qjyCateringUserDto.getUserAccount()==null || "".equals(qjyCateringUserDto.getUserAccount())){
			mapResult.put("Msg", "群成员ID不能为空!");
			mapResult.put("Success", "false");
			mapResult.put("data", null);
			return mapResult;
		}

		 Map<String,String> map = new HashMap<String, String>();
	  	   map.put("email", "cike534222598@qq.com");
	  	   map.put("devpwd", "Assassin");
	  	   map.put("appkey", "2debe681-ce19-4389-92cb-abc6475ea349");
	  	   map.put("group_id",qjyCateringUserDto.getGroupId());    //群ID
	  	   map.put("user_account", qjyCateringUserDto.getUserAccount());  //要添加的成员账号，必选项
	  	   JSONObject jsonObject =  JSONObject.fromObject(map);
	  	   System.out.println("jsonObject-----"+jsonObject);
	  	   ConnectQinJiaIm connectQinJiaIm = new ConnectQinJiaIm();
	  	   JSONObject result = connectQinJiaIm.requestHttps("https://qplusapi.gotye.com.cn:8443/api/AddGroupMember", jsonObject.toString()); 
	  	   
     System.out.println("hh------"+result);
	       String error_code = result.getString("errcode");
	       mapResult.put("error_code", error_code);
	       mapResult.put("Success", "true");
	       mapResult.put("data", result);
	       return mapResult;
	}

	@Override
	public Map<String, Object> delGroupMember(QjyCateringUserDto qjyCateringUserDto) {
		// TODO Auto-generated method stub
		Map<String,Object> mapResult = new HashMap<String, Object>();
		if(qjyCateringUserDto.getGroupId()==null || "".equals(qjyCateringUserDto.getGroupId())){
			mapResult.put("Msg", "群ID不能为空!");
			mapResult.put("Success", "false");
			mapResult.put("data", null);
			return mapResult;
		}
		if(qjyCateringUserDto.getUserAccount()==null || "".equals(qjyCateringUserDto.getUserAccount())){
			mapResult.put("Msg", "群成员ID不能为空!");
			mapResult.put("Success", "false");
			mapResult.put("data", null);
			return mapResult;
		}
		Map<String,String> map = new HashMap<String, String>();
	  	   map.put("email", "cike534222598@qq.com");
	  	   map.put("devpwd", "Assassin");
	  	   map.put("appkey", "2debe681-ce19-4389-92cb-abc6475ea349");
	  	   map.put("group_id",qjyCateringUserDto.getGroupId());    //群ID
	  	   map.put("user_account", qjyCateringUserDto.getUserAccount());  //要添加的成员账号，必选项
	  	   JSONObject jsonObject =  JSONObject.fromObject(map);
	  	   System.out.println("jsonObject-----"+jsonObject);
	  	   ConnectQinJiaIm connectQinJiaIm = new ConnectQinJiaIm();
	  	   JSONObject result = connectQinJiaIm.requestHttps("https://qplusapi.gotye.com.cn:8443/api/DelGroupMember", jsonObject.toString()); 
	  	   
  System.out.println("hh------"+result);
	       String error_code = result.getString("errcode");
	       mapResult.put("error_code", error_code);
	       mapResult.put("Success", "true");
	       mapResult.put("data", result);
	       return mapResult;
	}

	@Override
	public Map<String, Object> getGroups(QjyCateringUserDto qjyCateringUserDto) {
		// TODO Auto-generated method stub
		Map<String,Object> mapResult = new HashMap<String, Object>();
		Map<String,String> map = new HashMap<String, String>();
		
	  	   map.put("email", "cike534222598@qq.com");
	  	   map.put("devpwd", "Assassin");
	  	   map.put("appkey", "2debe681-ce19-4389-92cb-abc6475ea349");
	  	   map.put("last_group_id", "0");   //用于分页请求，第一页此项值为0，后续此项值为上一页中最后一个群的ID（即groupID）
	  	   map.put("count", "200");   //分页的条数，可选项。默认为20

	  	   JSONObject jsonObject =  JSONObject.fromObject(map);
	  	   System.out.println("jsonObject-----"+jsonObject);
	  	   ConnectQinJiaIm connectQinJiaIm = new ConnectQinJiaIm();
	  	   JSONObject result = connectQinJiaIm.requestHttps("https://qplusapi.gotye.com.cn:8443/api/GetGroups", jsonObject.toString()); 
           System.out.println("hh------"+result);
	       String error_code = result.getString("errcode");
	       mapResult.put("error_code", error_code);
	       mapResult.put("Success", "true");
	       mapResult.put("data", result);
	       return mapResult;
	}

	@Override
	public Map<String, Object> findNewGroups(
			QjyCateringUserDto qjyCateringUserDto) {
		// TODO Auto-generated method stub
		Map<String,Object> mapResult = new HashMap<String, Object>();
	//	QJGroupApi qJGroupApi = new QJGroupApi();
		GetGroupsReq getGroupsReq = new GetGroupsReq();
		GetGroupsResp getGroupsResp = qJGroupApi.getGroups(getGroupsReq);
	    String  error_code =	getGroupsResp.getStatus().toString();
	    mapResult.put("error_code", error_code);
	    mapResult.put("data", getGroupsResp.getEntities());
	    return mapResult;
		
	}
	
	public Map<String, Object> createGroupNew(QjyCateringUserDto qjyCateringUserDto){
		Map<String,Object> mapResult = new HashMap<String, Object>();
		
	    if(qjyCateringUserDto.getGroupName()==null || "".equals(qjyCateringUserDto.getGroupName())){
	    	mapResult.put("error_code", AppResponse.RETURN_FAILE);
	    	return mapResult;
		}
	    if(qjyCateringUserDto.getOwnerAccount()==null || "".equals(qjyCateringUserDto.getOwnerAccount())){
	    	mapResult.put("error_code", AppResponse.RETURN_FAILE);
	    	return mapResult;
	    }
		CreateGroupReq createGroupReq = new CreateGroupReq();
		createGroupReq.setGroupName(qjyCateringUserDto.getGroupName());
		createGroupReq.setOwnerAccount(qjyCateringUserDto.getOwnerAccount());
		createGroupReq.setGroupInfo(qjyCateringUserDto.getGroupInfo());
		CreateGroupResp createGroupResp = qJGroupApi.createGroup(createGroupReq);
		String error_code = createGroupResp.getStatus().toString();
		mapResult.put("error_code", error_code);
		mapResult.put("data", createGroupResp.getEntity());
		return mapResult;
	}
	
	
	//删除群成员
	public Map<String, Object> delGroupMemberNew(QjyCateringUserDto qjyCateringUserDto){
		Map<String,Object> mapResult = new HashMap<String, Object>();
		
		if(qjyCateringUserDto.getGroupId()==null || "".equals(qjyCateringUserDto.getGroupId())){
			mapResult.put("error_code", AppResponse.RETURN_FAILE);
			return mapResult;
		}
		if(qjyCateringUserDto.getUserAccount()==null || "".equals(qjyCateringUserDto.getUserAccount())){
			mapResult.put("error_code", AppResponse.RETURN_FAILE);
			return mapResult;
		}
		DelGroupMemberReq delGroupMemberReq = new DelGroupMemberReq();
		delGroupMemberReq.setGroupId(Long.parseLong(qjyCateringUserDto.getGroupId()));
		delGroupMemberReq.setUserAccount(qjyCateringUserDto.getUserAccount());
		DelGroupMemberResp delGroupMemberResp =  qJGroupApi.delGroupMember(delGroupMemberReq);
		String error_code = delGroupMemberResp.getStatus().toString();
		mapResult.put("error_code", error_code);
        return 		mapResult;
	}
	
	//添加群成员
	public Map<String, Object> addGroupMemberNew(QjyCateringUserDto qjyCateringUserDto){
		Map<String,Object> mapResult = new HashMap<String, Object>();
		if(qjyCateringUserDto.getGroupId()==null || "".equals(qjyCateringUserDto.getGroupId())){
			mapResult.put("error_code", AppResponse.RETURN_FAILE);
			return mapResult;
		}
		if(qjyCateringUserDto.getUserAccount()==null || "".equals(qjyCateringUserDto.getUserAccount())){
			mapResult.put("error_code", AppResponse.RETURN_FAILE);
			return mapResult;
		}
		AddGroupMemberReq addGroupMemberReq = new AddGroupMemberReq();
		String groupId = qjyCateringUserDto.getGroupId();
		addGroupMemberReq.setGroupId(Long.parseLong(groupId));
		addGroupMemberReq.setUserAccount(qjyCateringUserDto.getUserAccount());
		AddGroupMemberResp addGroupMemberResp =  qJGroupApi.addGroupMember(addGroupMemberReq);
		String error_code = addGroupMemberResp.getStatus().toString();
		mapResult.put("error_code", error_code);
		return mapResult;	
	}
	
	public Map<String, Object> dismissGroup(QjyCateringUserDto qjyCateringUserDto){
		Map<String,Object> mapResult = new HashMap<String, Object>();
		if(qjyCateringUserDto.getGroupId()==null && "".equals(qjyCateringUserDto.getGroupId())){
			mapResult.put("error_code", AppResponse.RETURN_FAILE);
			return mapResult;
		}
	    DismissGroupReq dismissGroupReq = new DismissGroupReq();
	    dismissGroupReq.setGroupId(Long.valueOf(qjyCateringUserDto.getGroupId()));
		DismissGroupResp dismissGroupResp  = qJGroupApi.dismissGroup(dismissGroupReq);
		String error_code = dismissGroupResp.getStatus().toString();
		mapResult.put("error_code", error_code);
        return mapResult;
	}
	
}
