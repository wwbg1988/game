<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
<head>
<title>亲加云</title>

<script type="text/javascript">
function selectForward(){  
	  var objForm = document.applyForm;
	  
	  var url="http://localhost:8081/qinJiaController/showPicture";
	   
	  var openStyle="dialogHeight:500px; dialogWidth:500px; status:no; help:no; scroll:auto";
	  var result = window.showModalDialog(url,window.document,openStyle);
	  
	  return true;
	 }

</script>
<body>
<div>
<form id="sendMessage" method="post" action="http://localhost:8081/qinJiaController/sendMessage">   
   <table class="table table-hover table-condensed">
				<tr>
					<th>开发者账号</th>
					<td><input id="email" name="email" type="text" value="wuweitree@163.com" class="easyui-validatebox span2" data-options="required:true" ></td>
				    <th>开发者密码</th>
					<td><input id="devpwd" name="devpwd" type="text" value="wuwei123" class="easyui-validatebox span2" data-options="required:true" ></td>
				</tr>
			
			    <tr>
			       <th>应用的appkey</th>
			       <td><input id="appkey" name="appkey" type="text" value="733a1dfc-d717-49e6-8ea9-3d700e1988b2" class="easyui-validatebox span2" data-options="required:true" ></td>
			       <th>发送方账号</th>
			       <td><input id="from" name="from" type="text" value="123456" class="easyui-validatebox span2" data-options="required:true" ></td>
			    </tr>
			    
			    <tr>
			       <th>发送对象类型</th>
			       <td><input id="toType" name="toType" type="text" value="0" class="easyui-validatebox span2" data-options="required:true" ></td>
			       <th>发送方账号</th>
			       <td><input id="toIds" name="toIds" type="text" value="asdf" class="easyui-validatebox span2" data-options="required:true" ></td>
			    </tr>
			    
			    <tr>
			       <th>此条数据是否保存</th>
			       <td><input id="save" name="save" type="text" value="1" class="easyui-validatebox span2" data-options="required:true" ></td>
			       <th>发送消息类型</th>
			       <td><input id="msg_type" name="msg_type" type="text" value="0" class="easyui-validatebox span2" data-options="required:true" ></td>
			    </tr>
			    
			    <tr>
			      <th>发送的消息内容</th>
			      <td><input  id="text" name="text"  value="影魔必须死" class="easyui-validatebox span2" data-options="required:true"></input></td>
			      <th>&nbsp;</th>
			      <td>发送消息<input id="a1" type="submit" title="发送消息" ></input></td>
			    </tr>
			    
			    
			    
			</table>
</form>  
<form id="addUser" method="post" action="http://localhost:8081/qinJiaController/addUser">
   <table class="table table-hover table-condensed">
                <tr>
					<th>开发者账号</th>
					<td><input id="email" name="email" type="text" value="wuweitree@163.com" class="easyui-validatebox span2" data-options="required:true" ></td>
				    <th>开发者密码</th>
					<td><input id="devpwd" name="devpwd" type="text" value="wuwei123" class="easyui-validatebox span2" data-options="required:true" ></td>
				</tr>
				 <tr>
			       <th>应用的appkey</th>
			       <td><input id="appkey" name="appkey" type="text" value="733a1dfc-d717-49e6-8ea9-3d700e1988b2" class="easyui-validatebox span2" data-options="required:true" ></td>
			       <th>&nbsp;</th>
			       <td>
			        添加用户 <input id="a2" type="submit" title="添加用户" >
			       </td>
			    </tr>
			
   </table>

</form>
<form  id="addFriend" method="post"  action="http://localhost:8081/qinJiaController/addFriend">
   <table class="table table-hover table-condensed">
                <tr>
					<th>开发者账号</th>
					<td><input id="email" name="email" type="text" value="wuweitree@163.com" class="easyui-validatebox span2" data-options="required:true" ></td>
				    <th>开发者密码</th>
					<td><input id="devpwd" name="devpwd" type="text" value="wuwei123" class="easyui-validatebox span2" data-options="required:true" ></td>
				</tr>
				 <tr>
			       <th>应用的appkey</th>
			       <td><input id="appkey" name="appkey" type="text" value="733a1dfc-d717-49e6-8ea9-3d700e1988b2" class="easyui-validatebox span2" data-options="required:true" ></td>
			       <th>&nbsp;</th>
			       <td>
			         添加好友<input id="a3" type="submit" title="添加好友" >
			       </td>
			    </tr>
   </table>
</form>

<form id="deletFriend" method="post"  action="http://localhost:8081/qinJiaController/deletFriend">
  <table class="table table-hover table-condensed">
                <tr>
					<th>开发者账号</th>
					<td><input id="email" name="email" type="text" value="wuweitree@163.com" class="easyui-validatebox span2" data-options="required:true" ></td>
				    <th>开发者密码</th>
					<td><input id="devpwd" name="devpwd" type="text" value="wuwei123" class="easyui-validatebox span2" data-options="required:true" ></td>
				</tr>
				 <tr>
			       <th>应用的appkey</th>
			       <td><input id="appkey" name="appkey" type="text" value="733a1dfc-d717-49e6-8ea9-3d700e1988b2" class="easyui-validatebox span2" data-options="required:true" ></td>
			       <th>&nbsp;</th>
			       <td>
			                      删除好友<input id="a3" type="submit" title="删除好友" >
			       </td>
			    </tr>
   </table>
</form>


<form id="getHistory" method="post"  action="http://localhost:8081/qinJiaController/getHistory">
  <table class="table table-hover table-condensed">
                <tr>
					<th>开发者账号</th>
					<td><input id="email" name="email" type="text" value="wuweitree@163.com" class="easyui-validatebox span2" data-options="required:true" ></td>
				    <th>开发者密码</th>
					<td><input id="devpwd" name="devpwd" type="text" value="wuwei123" class="easyui-validatebox span2" data-options="required:true" ></td>
				</tr>
				 <tr>
			       <th>应用的appkey</th>
			       <td><input id="appkey" name="appkey" type="text" value="733a1dfc-d717-49e6-8ea9-3d700e1988b2" class="easyui-validatebox span2" data-options="required:true" ></td>
			       <th>&nbsp;</th>
			       <td>
			                      获取聊天记录<input id="a4" type="submit" title="获取聊天记录" >
			       </td>
			    </tr>
   </table>
</form>

<form id="checkImage" method="post"  action="http://localhost:8081/tImsProcessController/checkImage">
  <table class="table table-hover table-condensed">
                <tr>
					<th>开发者账号</th>
					<td><input id="email" name="email" type="text" value="wuweitree@163.com" class="easyui-validatebox span2" data-options="required:true" ></td>
				    <th>图片</th>
					<td>
					  <div align="left">
                       <img hspace="2" vspace="2" border="1" align="middle" height="100" width="100"
                           src="http://localhost:8081/tImsProcessController/showPicture?proid=38793f31-032b-4c00-b573-4d1b5ce2065e" > 
                      <img hspace="2" vspace="2" border="1" align="middle" height="100" width="100"
                           src="http://localhost:8081/uploadPic/20150820/4e8ee8b2d9c6434cbd1b505f546f4060.jpg" > 
                      </div>
					</td>
				</tr>
				 <tr>
			       <th>应用的appkey</th>
			       <td>
			       <img src="/style/images/extjs_icons/key_add.png"/>
			       </td>
			       <th>&nbsp;</th>
			       <td>
			                      读取图片<input id="a4" type="submit" title="读取图片" >
			       </td>
			    </tr>
   </table>
</form>

<form id="UploadFile" method="post"  action="http://localhost:8081/qinJiaController/UploadFile">
  <table class="table table-hover table-condensed">
                <tr>
					<th>开发者账号</th>
					<td><input id="email" name="email" type="text" value="wuweitree@163.com" class="easyui-validatebox span2" data-options="required:true" ></td>
				    <th>开发者密码</th>
					<td><input id="devpwd" name="devpwd" type="text" value="wuwei123" class="easyui-validatebox span2" data-options="required:true" ></td>
				</tr>
				 <tr>
			       <th>应用的appkey</th>
			       <td><input id="appkey" name="appkey" type="text" value="733a1dfc-d717-49e6-8ea9-3d700e1988b2" class="easyui-validatebox span2" data-options="required:true" ></td>
			       <th>&nbsp;</th>
			       <td>
			                      上传文件<input id="a3" type="submit" title="上传文件" >
			       </td>
			    </tr>
   </table>
</form>

</div>

</body>
