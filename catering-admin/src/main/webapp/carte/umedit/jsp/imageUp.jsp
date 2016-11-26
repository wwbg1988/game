    <%@ page language="java" contentType="text/html; charset=utf-8"
             pageEncoding="utf-8"%>
         <%@ page import="com.ssic.catering.manage.controller.UploaderController" %>
            <%
                            request.setCharacterEncoding("utf-8");
                        	response.setCharacterEncoding("utf-8");
                
                        	UploaderController up = new UploaderController(request);
                            up.setSavePath("upload");
                            String[] fileType = {".gif" , ".png" , ".jpg" , ".jpeg" , ".bmp"};
                            up.setAllowFiles(fileType);
                            up.setMaxSize(10000); //单位KB
                            up.upload();

                            String callback = request.getParameter("callback");

                            String result = "{\"name\":\""+ up.getFileName() +"\", \"originalName\": \""+ up.getOriginalName() +"\", \"size\": "+ up.getSize() +", \"state\": \""+ up.getState() +"\", \"type\": \""+ up.getType() +"\", \"url\": \""+ up.getUrl() +"\"}";

                            result = result.replaceAll( "\\\\", "\\\\" );

                            if( callback == null ){
                                response.getWriter().print( result );
                            }else{
                                response.getWriter().print("<script>"+ callback +"(" + result + ")</script>");
                            }
            %>
