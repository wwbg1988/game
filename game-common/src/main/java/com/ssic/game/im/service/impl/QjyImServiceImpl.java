package com.ssic.game.im.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
































import com.google.common.eventbus.AsyncEventBus;
import com.gotye.entity.RespSimpleGroup;
import com.gotye.entity.User;
import com.gotye.entity.req.group.GetGroupMembersReq;
import com.gotye.entity.req.group.GetGroupsReq;
import com.gotye.entity.req.user.GetFriendsReq;
import com.gotye.entity.req.user.ImportUsersReq;
import com.gotye.entity.resp.group.GetGroupMembersResp;
import com.gotye.entity.resp.group.GetGroupsResp;
import com.gotye.entity.resp.user.GetFriendsResp;
import com.gotye.entity.resp.user.ImportUsersResp;
import com.ssic.game.common.service.IMenuService;
import com.ssic.game.common.service.impl.ImsQjGroupAPIServiceImpl;
import com.ssic.game.im.dto.GetGroupsRespDto;
import com.ssic.game.im.dto.QjyImUserDto;
import com.ssic.game.im.dto.RespSimpleGroupDto;
import com.ssic.game.im.service.IQjyImService;
import com.ssic.game.im.util.ConnectQinJiaIm;
import com.ssic.game.im.util.QjySendMessage;
import com.ssic.util.BeanUtils;



@Service("qjyImServiceImpl")
public class QjyImServiceImpl implements IQjyImService{

	 protected static final Log logger = LogFactory.getLog(QjyImServiceImpl.class);
	 @Autowired
	 private IMenuService menuService;
	 @Autowired
	 private ImsQjGroupAPIServiceImpl qJGroupApi;
	 
//	 public static final AsyncEventBus eventBus = new AsyncEventBus(Executors.newCachedThreadPool());
//
//	 public static final EventBusListener eventBusListener = new EventBusListener();
//
//	    public AsyncEventBus getQjyEvent()
//	    {
//	        eventBus.register(eventBusListener);
//	        return eventBus;
//	    }
//
//	   
//	    public void onEvent(QjyImUserDto qjyImUserDto){
//	    	sendMessage(qjyImUserDto);
//	    }
	 
	@Override
	public Map<String, String> sendMessage(QjyImUserDto qjyImUserDto) {

		Map<String,String> map = new HashMap<String, String>();
		
		if(qjyImUserDto.getToIdList()==null || qjyImUserDto.getToIdList().size()==0){
			if(qjyImUserDto.getToIds() == null || qjyImUserDto.getToIds().equals("")){
				map.put("Msg", "给发送的id不能空");
				map.put("Success", "false");
				return map;
			}
		}
		
		if(qjyImUserDto.getText() == null || qjyImUserDto.getText().equals("")){
			map.put("Msg", "信息内容不能为空");
			map.put("Success", "false");
			return map;
	
		}
		
		if(qjyImUserDto.getText().length()>150){
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
	        map_push_info.put("appkey", "9d947bae-572d-4f90-ae78-b10e6f797138"); // 应用的appkey，必选项
	        map_push_info.put("to_type", "0"); // 发送对象类型，必选项。0发往用户，1发往聊天室，2发往群
	        map_push_info.put("save", "1"); // 此条数据是否保存（离线消息和历史记录），必选项。0不保存；1保存
	        map_push_info.put("msg_type", "0"); // 发送消息类型，必选项。0文本
	        
	        //如果list不为空则需要给多个用户发送ID,如果list为空则只给一个用户发送
	        
	        if(qjyImUserDto.getToIdList().size()>100){
	        	map.put("Msg", "给发送的id不能大于100");
				map.put("Success", "false");
				return map;
	        }
	        
	        if(qjyImUserDto.getToIdList()!=null && qjyImUserDto.getToIdList().size()>0){
	        	 map_push_info.put("to_id", qjyImUserDto.getToIdList());
	        }else{
	        	List<String> to_id_list = new ArrayList<String>();
	   	        to_id_list.add(qjyImUserDto.getToIds());
	   	        map_push_info.put("to_id", to_id_list); // 给发送的id,50个以内为佳，最多不要超过100个
	        }
	        
	        map_push_info.put("from", "ims_system"); // 发送方账号，必选项，该账号必须存在
	       
	        String textInfo = "";
			try {
				textInfo = new String(qjyImUserDto.getText().getBytes(),"UTF-8");
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
	        ConnectQinJiaIm connectQinJia = new ConnectQinJiaIm();
	        
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
	public Map<String, Object> getGroups() {
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
	
	public Map<String, Object> importUsers(QjyImUserDto qjyImUserDto){
		Map<String,Object> mapResult = new HashMap<String, Object>();
		ImportUsersReq importUsersReq = new ImportUsersReq();
		List<User> users = new ArrayList<User>();
		User user = new User();
		user.setAccount(qjyImUserDto.getUserAccount());
		user.setNickname(qjyImUserDto.getNickname());
		user.setPwd(qjyImUserDto.getPwd());
		users.add(user);
		importUsersReq.setUsers(users);
		ImportUsersResp importUsersResp = qJGroupApi.importUsers(importUsersReq);
	    String  error_code =	importUsersResp.getStatus().toString();
		mapResult.put("error_code", error_code);
		return mapResult;
	}
	

	@Override
	public Map<String, Object> findNewGroups(String qjAccount) {
		// TODO Auto-generated method stub
		Map<String,Object> mapResult = new HashMap<String, Object>();
		GetGroupsReq getGroupsReq = new GetGroupsReq();
		getGroupsReq.setUserAccount(qjAccount);
		GetGroupsResp getGroupsResp = qJGroupApi.getGroups(getGroupsReq);
	//	GetGroupsRespDto getGroupsRespDto =  findRadisGroup(qjAccount);
	//	List<RespSimpleGroupDto> listresp = getGroupsRespDto.getList();
	//	List<RespSimpleGroup> listen = BeanUtils.createBeanListByTarget(listresp, RespSimpleGroup.class);
	//	GetGroupsResp getGroupsResp =findRadisGroup(qjAccount);
	    String  error_code =	getGroupsResp.getStatus().toString();
	    mapResult.put("error_code", error_code);
	    mapResult.put("data", getGroupsResp.getEntities());
//		String  error_code = getGroupsRespDto.getStatus();
//		mapResult.put("error_code", error_code);
//		mapResult.put("data", listen);
	    return mapResult;
	}	

	//将获取的我的群放入缓存中
	@CacheEvict(value="default", key = "'com.gotye.entity.resp.group.GetGroupsResp:' + #qjAccount", beforeInvocation=true)
	public void insertRadisGroup(String qjAccount){
//		GetGroupsReq getGroupsReq = new GetGroupsReq();
//		getGroupsReq.setUserAccount(qjAccount);
//		GetGroupsResp getGroupsResp = qJGroupApi.getGroups(getGroupsReq);
	}
	
	@CacheEvict(value="default", key = "'com.gotye.entity.resp.group.GetGroupsResp:' + #qjAccount", beforeInvocation=true)
	public void deleteRadisGroup(String qjAccount){
		
	}
	
	
	//在缓存中根据我的亲加云账号查询我的群
	//第一次查询的时候就会自动的放入缓存中，如果缓存中存在则直接查缓存
	@Cacheable(value="default", key="'com.gotye.entity.resp.group.GetGroupsResp:' + #qjAccount")
	public GetGroupsRespDto findRadisGroup(String qjAccount){
		GetGroupsReq getGroupsReq = new GetGroupsReq();
		getGroupsReq.setUserAccount(qjAccount);
		GetGroupsResp getGroupsResp = qJGroupApi.getGroups(getGroupsReq);
		GetGroupsRespDto getGroupsRespDto = new GetGroupsRespDto();
		List<RespSimpleGroupDto> listresp = new ArrayList<RespSimpleGroupDto>();
		List<RespSimpleGroup> listen = getGroupsResp.getEntities();
		if(listen!=null){
			for(RespSimpleGroup resp2: listen){
				RespSimpleGroupDto getdto2 = new RespSimpleGroupDto();
				BeanUtils.copyProperties(resp2, getdto2);
				listresp.add(getdto2);
			}
		}
		getGroupsRespDto.setStatus(getGroupsResp.getStatus().toString());
		getGroupsRespDto.setList(listresp);
		return getGroupsRespDto;
	}
	
	
	
	public Map<String, Object> getGroupUserList(String groupId){
		Map<String,Object> mapResult = new HashMap<String, Object>();	
		GetGroupMembersReq getGroupMembersReq = new GetGroupMembersReq();
		getGroupMembersReq.setGroupId(Long.valueOf(groupId)  );
		GetGroupMembersResp getGroupMembersResp = qJGroupApi.GetGroupUserList(getGroupMembersReq);
		String error_code = getGroupMembersResp.getStatus().toString();
		mapResult.put("error_code", error_code);
		mapResult.put("data", getGroupMembersResp.getEntities());
		return mapResult;
	}
	
	
	//获取好友列表
	public Map<String, Object> getFriends(String qjAccount){
		Map<String,Object> mapResult = new HashMap<String, Object>();		
		GetFriendsReq getFriendsReq = new GetFriendsReq();
		getFriendsReq.setUserAccount(qjAccount);
		GetFriendsResp getFriendsResp=qJGroupApi.getFriends(getFriendsReq);
		String error_code = getFriendsResp.getStatus().toString();
		  mapResult.put("error_code", error_code);
		  mapResult.put("data", getFriendsResp.getEntities());
		  return mapResult;
	}
	
	
	
}
