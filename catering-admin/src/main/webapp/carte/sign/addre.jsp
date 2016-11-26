<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script src="${pageContext.request.contextPath}/jslib/jquery-1.8.3.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">

    function addjw(){
    	var xPosition = $("#jd").val();
    	var yPosition = $("#wd").val();
    	if(xPosition==null||xPosition==""){
    		alert("经度不能为空!");
    		return false;
    	}
    	
    	if(yPosition==null||yPosition==""){
    		alert("纬度不能为空!");
    		return false;
    	}
    	$("#xPosition").val(xPosition);
    	$("#yPosition").val(yPosition);
    	window.opener.location.reload();
    	window.close();
    }

</script>
</head>
<body>

<%@include file="dituchaxun.html"%>

       <form action="/caterSignController/insertSign" id="form" method="post" onsubmit="return addjw();" >
         <table>
         <tr>
          <input id="xPosition" name="xPosition" type="hidden" />
          <input id="yPosition" name="yPosition" type="hidden" />
          <td>
              <th>&nbsp;</th>
              <input id="getJW" name="getJW" type="submit"  title="获取经纬度" value="获取经纬度"/>
          </td>
         </tr>
         </table>
       </form>
</body>
</html>