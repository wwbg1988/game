<%@ page language="java" pageEncoding="UTF-8"%>
<ul>
	<li>USER SERVCIE属于UI(APP移动客户端)界面， USER用户是根据定义的流程进行相关的流程的新建启动，以及进行待办流程的查询和处理操作。</li>
	<li>HTTP API作为处理移动端(APP)请求的控制某块，通过HTTP API接口对外提供业务支持，所有API都是基于Spring MVC REST风格实现，HTTP开放平台使用UTF-8编码格式，仅支持POST方式接口访问。所有访问需以POST方式访问指定接口进行调用,数据格式采用JSON。</li> 
	<li>ADMIN SERVICE用户是对用户管理、流程信息、表单维护、各类权限进行定义配置，持久化到数据库中。主要是用户权限，表单权限，表单定义与流程状态的信息维护。</li> 
	<li>DBserver部分是用于保存工作流任务信息以及流程相关的任务实例和流程实例信息。该部分是所有的流程定义的存储信息。当用户进行流程实例的操作时相关的信息将会产生相应的存储数据进行保存。</li> 
</ul>