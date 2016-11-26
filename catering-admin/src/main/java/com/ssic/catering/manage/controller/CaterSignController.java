package com.ssic.catering.manage.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssic.catering.admin.pageModel.DataGrid;
import com.ssic.catering.admin.pageModel.Json;
import com.ssic.catering.admin.service.ProjectService;
import com.ssic.catering.admin.service.UserServiceI;
import com.ssic.catering.base.pojo.PageHelper;
import com.ssic.catering.base.service.ICaterSignService;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.game.common.dto.SignDto;
import com.ssic.game.common.service.IDeptService;
import com.ssic.game.common.service.IImsUserService;
import com.ssic.util.StringUtils;


@Controller
@RequestMapping("/caterSignController")
public class CaterSignController {
    
    private static Logger log = Logger.getLogger(CaterSignController.class);

	@Autowired
	private ICaterSignService caterSignService;
	@Autowired
	private IImsUserService imsUserService;
	@Autowired
	private IDeptService deptService;
	
	@Autowired
	private UserServiceI userService;
	@Autowired
	private ProjectService projectService;
	
	
	public List<SignDto> findBy(SignDto signDto){
		return caterSignService.findBy(signDto);
	}
	
	public SignDto findById(String id){
		return caterSignService.findById(id);
	}
	
	@RequestMapping("/insertSign")
    @ResponseBody 
	public Json insertSign(SignDto signDto){
		Json j = new Json();
		if(signDto.getXPosition()==null || signDto.getXPosition()==""){
			j.setMsg("经度不能为空!");
			j.setSuccess(false);
			return j;
		}
		if(signDto.getYPosition()==null || signDto.getYPosition()==""){
			j.setMsg("纬度不能为空!");
			j.setSuccess(false);
			return j;
		}
		
		j.setMsg("新增地理位置成功！");
		j.setSuccess(true);
		return j;
		//caterSignService.insertSign(signDto);
	}
	
	public void updateSign(SignDto signDto){
		caterSignService.updateSign(signDto);
	}
	
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request){
		return "carte/sign/sign";
	}
	
	@RequestMapping("/searchHistory")
	public String searchHistory(HttpServletRequest request){
		  HttpSession session = request.getSession();
		    if(session == null)
		    {
		        log.error("session不能为空");
		    }
		    
		    List<ProjectDto>  pjdos = userService.getProjectBySession(session);
		    if(CollectionUtils.isEmpty(pjdos))
		    {
	            log.error("该用户没有对应的projectId");
		    }
		    
		    request.setAttribute("pjdos", pjdos);
		return "carte/sign/searchHistory";
	}
	
	
	@RequestMapping("/searchKQHistory")
	public String searchKQHistory(HttpServletRequest request){
		  HttpSession session = request.getSession();
		    if(session == null)
		    {
		        log.error("session不能为空");
		    }
		    
		    List<ProjectDto>  pjdos = userService.getProjectBySession(session);
		    if(CollectionUtils.isEmpty(pjdos))
		    {
	            log.error("该用户没有对应的projectId");
		    }
		    
		    request.setAttribute("pjdos", pjdos);
		return "carte/sign/searchKQHistory";
	}
	
	
	@RequestMapping("/searchKaoQ")
	@ResponseBody
	public DataGrid searchKaoQ(SignDto signDto,PageHelper ph, HttpServletRequest request) throws ParseException{
		 DataGrid dataGrid = new DataGrid();
		    
		    HttpSession session = request.getSession();
		    if(session == null)
		    {
		        log.error("session不能为空");
		        return dataGrid;
		    }
		    
		    String projectIds = userService.getProjectIdsBySession(session);
	        if(StringUtils.isEmpty(projectIds))
	        {
	            log.error("该用户没有对应的projectId");
	            return dataGrid;
	        }
	        else{
	            if(!StringUtils.isEmpty(signDto.getProjectid()) && projectIds.contains(signDto.getProjectid()))
	            {
	                //do nothing
	            }
	            else{
	                signDto.setProjectid(projectIds);
	            }
	        }
		   
		    signDto.setSignType(1);
			List<SignDto> list = caterSignService.findSignsBy(signDto, ph);
			//新增迟到
			if(!CollectionUtils.isEmpty(list)){
				for(SignDto sig1 : list){
					compareDate(sig1);
				}
			}
			
			Long count = 0L;
			if(!CollectionUtils.isEmpty(list))
			{
			    count = caterSignService.countSignsBy(signDto);
			    dataGrid.setRows(list);
		        dataGrid.setTotal(Long.valueOf(count));
			}
			
			
			return dataGrid;
	}
	
	
	@RequestMapping("/searchQiandao")
	@ResponseBody
	public DataGrid searchQiandao(SignDto signDto,PageHelper ph, HttpServletRequest request){
		 DataGrid dataGrid = new DataGrid();
		    
		    HttpSession session = request.getSession();
		    if(session == null)
		    {
		        log.error("session不能为空");
		        return dataGrid;
		    }
		    
		    String projectIds = userService.getProjectIdsBySession(session);
	        if(StringUtils.isEmpty(projectIds))
	        {
	            log.error("该用户没有对应的projectId");
	            return dataGrid;
	        }
	        else{
	            if(!StringUtils.isEmpty(signDto.getProjectid()) && projectIds.contains(signDto.getProjectid()))
	            {
	                //do nothing
	            }
	            else{
	                signDto.setProjectid(projectIds);
	            }
	        }
		   
		    signDto.setSignType(2);
			List<SignDto> list = caterSignService.findSignsBy(signDto, ph);
			
			Long count = 0L;
			if(!CollectionUtils.isEmpty(list))
			{
			    count = caterSignService.countSignsBy(signDto);
			    dataGrid.setRows(list);
		        dataGrid.setTotal(Long.valueOf(count));
			}
			
			
			return dataGrid;
	}
	
	
	
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(SignDto signDto,PageHelper ph, HttpServletRequest request){
	    
	    DataGrid dataGrid = new DataGrid();
	    
	    HttpSession session = request.getSession();
	    if(session == null)
	    {
	        log.error("session不能为空");
	        return dataGrid;
	    }
	    
	    String projectIds = userService.getProjectIdsBySession(session);
	    if(StringUtils.isEmpty(projectIds))
	    {
            log.error("该用户没有对应的projectId");
            return dataGrid;
	    }
	    else{
	        if(!StringUtils.isEmpty(signDto.getProjectid()) && projectIds.contains(signDto.getProjectid()))
	        {
	            //do nothing
	        }
	        else{
	            signDto.setProjectid(projectIds);
	        }
	    }
	   
		
		List<SignDto> list = caterSignService.findSignsBy(signDto, ph);
		
		Long count = 0L;
		if(!CollectionUtils.isEmpty(list))
		{
		    count = caterSignService.countSignsBy(signDto);
		    dataGrid.setRows(list);
	        dataGrid.setTotal(Long.valueOf(count));
		}
		
		
		return dataGrid;
	}
	
	@RequestMapping("/addAddress")
	public String addAddress(HttpServletRequest request,String id){
		request.setAttribute("id", id);
		//return "carte/sign/addAddress";
		return "carte/sign/addre";
	}
	
	//导出excel
	@RequestMapping("/exportExcel")
	public void exportExcel(SignDto signDto,PageHelper ph, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		
		// 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("签到地址信息");  
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        HSSFRow row = sheet.createRow((int) 0);  
        // 第四步，创建单元格，并设置值表头 设置表头居中  
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
        
        HSSFCell cell =row.createCell(0);  
        cell.setCellValue("用户名称");  
        cell.setCellStyle(style);  
        cell = row.createCell(1);  
        cell.setCellValue("部门名称");  
        cell.setCellStyle(style);  
        cell = row.createCell(2);  
        cell.setCellValue("登陆账号");  
        cell.setCellStyle(style);  
        cell = row.createCell(3);  
        cell.setCellValue("签到地址");  
        cell.setCellStyle(style); 
        cell = row.createCell(4);  
        cell.setCellValue("签到时间");  
        cell.setCellStyle(style); 
        cell = row.createCell(5);  
        cell.setCellValue("项目");  
        cell.setCellStyle(style); 
        
        //查询签到信息
        HttpSession session = request.getSession();
	    if(session == null)
	    {
	        log.error("session不能为空");
	        return;
	    }
	    
	    String projectIds = userService.getProjectIdsBySession(session);
        if(StringUtils.isEmpty(projectIds))
        {
            log.error("该用户没有对应的projectId");
            return;
        }
        else{
            if(!StringUtils.isEmpty(signDto.getProjectid()) && projectIds.contains(signDto.getProjectid()))
            {
                //do nothing
            }
            else{
                signDto.setProjectid(projectIds);
            }
        }
        
	    signDto.setSignType(2);
		List<SignDto> list = caterSignService.findByProjs(signDto);
        
		for (int i = 0; i < list.size(); i++)  
        {  
            row = sheet.createRow((int) i + 1);  
            SignDto stu = list.get(i);  
            // 第四步，创建单元格，并设置值  
            row.createCell(0).setCellValue(stu.getUserName());
            row.createCell(1).setCellValue(stu.getDeptName());
            row.createCell(2).setCellValue(stu.getUserAccount());
            row.createCell(3).setCellValue(stu.getAddress());
            row.createCell(4).setCellValue(new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(stu.getCreateTime()));
            row.createCell(5).setCellValue(stu.getProjectName());
        }  
		
	     
	     
	     ByteArrayOutputStream os = new ByteArrayOutputStream();

	        try {
	            wb.write(os);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        byte[] content = os.toByteArray();
	        InputStream is = new ByteArrayInputStream(content);

	        // 设置response参数，可以打开下载页面
	        response.reset();
	        response.setContentType("application/vnd.ms-excel;charset=utf-8");
	        response.setHeader("Content-Disposition", "attachment;filename=" + new String(("sign_info_"+System.currentTimeMillis()+ ".xls").getBytes(), "iso-8859-1"));

	        ServletOutputStream out = response.getOutputStream();

	        BufferedInputStream bis = null;
	        BufferedOutputStream bos = null;

	        try {

	            bis = new BufferedInputStream(is);
	            bos = new BufferedOutputStream(out);

	            byte[] buff = new byte[2048];
	            int bytesRead;

	            // Simple read/write loop.
	            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
	                bos.write(buff, 0, bytesRead);
	            }

	        } catch (final IOException e) {
	            throw e;
	        } finally {
	            if (bis != null)
	                bis.close();
	            if (bos != null)
	                bos.close();
	        }
		
	} 
	
	//导出excel
		@RequestMapping("/exportExcelKQ")
		public void exportExcelKQ(SignDto signDto,PageHelper ph, HttpServletRequest request, HttpServletResponse response)  throws Exception {
			Json j = new Json();
			
			// 第一步，创建一个webbook，对应一个Excel文件  
	        HSSFWorkbook wb = new HSSFWorkbook();  
	        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
	        HSSFSheet sheet = wb.createSheet("考勤地址信息");  
	        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
	        HSSFRow row = sheet.createRow((int) 0);  
	        // 第四步，创建单元格，并设置值表头 设置表头居中  
	        HSSFCellStyle style = wb.createCellStyle();  
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
	        
	        HSSFCell cell =row.createCell(0);  
	        cell.setCellValue("用户名称");  
	        cell.setCellStyle(style);  
	        cell = row.createCell(1);  
	        cell.setCellValue("部门名称");  
	        cell.setCellStyle(style);  
	        cell = row.createCell(2);  
	        cell.setCellValue("登陆账号");  
	        cell.setCellStyle(style);  
	        cell = row.createCell(3);  
	        cell.setCellValue("考勤地址");  
	        cell.setCellStyle(style); 
	        cell = row.createCell(4);  
	        cell.setCellValue("考勤类别");  
	        cell.setCellStyle(style); 
	        cell = row.createCell(5);  
	        cell.setCellValue("考勤结果");  
	        cell.setCellStyle(style); 
	        cell = row.createCell(6);  
	        cell.setCellValue("考勤时间");  
	        cell.setCellStyle(style); 
	        cell = row.createCell(7);  
	        cell.setCellValue("项目");  
	        cell.setCellStyle(style); 
	        
	        //查询签到信息
	        HttpSession session = request.getSession();
		    if(session == null)
		    {
		        log.error("session不能为空");
		        return;
		    }
		    String projectIds = userService.getProjectIdsBySession(session);
	        if(StringUtils.isEmpty(projectIds))
	        {
	            log.error("该用户没有对应的projectId");
	            return;
	        }
	        else{
	            if(!StringUtils.isEmpty(signDto.getProjectid()) && projectIds.contains(signDto.getProjectid()))
	            {
	                //do nothing
	            }
	            else{
	                signDto.setProjectid(projectIds);
	            }
	        }
		    signDto.setSignType(1);
			List<SignDto> list = caterSignService.findByProjs(signDto);
			//新增迟到
			if(!CollectionUtils.isEmpty(list)){
				for(SignDto sig1 : list){
					try {
						compareDate(sig1);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			for (int i = 0; i < list.size(); i++)  
	        {  
	            row = sheet.createRow((int) i + 1);  
	            SignDto stu = list.get(i);  
	            // 第四步，创建单元格，并设置值  
	            row.createCell(0).setCellValue(stu.getUserName());
	            row.createCell(1).setCellValue(stu.getDeptName());
	            row.createCell(2).setCellValue(stu.getUserAccount());
	            row.createCell(3).setCellValue(stu.getAddress());
	            if("0".equals(stu.getIsworktime()+"")){
	            	row.createCell(4).setCellValue("上班");
	            }else{
	            	row.createCell(4).setCellValue("下班");
	            }
	            if("0".equals(stu.getIsCheck()+"")){
	            	row.createCell(5).setCellValue("迟到");
	            }else if("1".equals(stu.getIsCheck()+"")){
	            	row.createCell(5).setCellValue("早退");
	            }else{
	            	row.createCell(5).setCellValue("正常");
	            }
	            row.createCell(6).setCellValue(new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(stu.getCreateTime()));
	            row.createCell(7).setCellValue(stu.getProjectName());
	        }  
			
		     ByteArrayOutputStream os = new ByteArrayOutputStream();

	            try {
	                wb.write(os);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }

	            byte[] content = os.toByteArray();
	            InputStream is = new ByteArrayInputStream(content);

	            // 设置response参数，可以打开下载页面
	            response.reset();
	            response.setContentType("application/vnd.ms-excel;charset=utf-8");
	            response.setHeader("Content-Disposition", "attachment;filename=" + new String(("check_info_"+System.currentTimeMillis()+ ".xls").getBytes(), "iso-8859-1"));

	            ServletOutputStream out = response.getOutputStream();

	            BufferedInputStream bis = null;
	            BufferedOutputStream bos = null;

	            try {

	                bis = new BufferedInputStream(is);
	                bos = new BufferedOutputStream(out);

	                byte[] buff = new byte[2048];
	                int bytesRead;

	                // Simple read/write loop.
	                while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
	                    bos.write(buff, 0, bytesRead);
	                }

	            } catch (final IOException e) {
	                throw e;
	            } finally {
	                if (bis != null)
	                    bis.close();
	                if (bos != null)
	                    bos.close();
	            }
			 
			
		} 
		
	
	//比较时间是否迟到早退正常
	public void compareDate(SignDto signDtos) throws ParseException{
		  //如果是考勤    isWorkTime 0上班   1下班   isCheck 0 迟到   1 早退   2正常
        if(signDtos.getSignType()==1){
        	String starWorkTime = "08:50:00";  
        	String endWorkTime = "18:10:00" ;
        	SimpleDateFormat sdfw=new SimpleDateFormat("HH:mm:ss");
            Date datestar = sdfw.parse(starWorkTime);
        	Date dateend = sdfw.parse(endWorkTime);
        	String strDate11 = sdfw.format(signDtos.getCreateTime());
			Date singcreat = sdfw.parse(strDate11);
			if(signDtos.getIsworktime()==0){
				if(singcreat.before(datestar)){
            		signDtos.setIsCheck(2);
            	}else{
            		signDtos.setIsCheck(0);
            	}
			}else{
				if(singcreat.after(dateend)){
					signDtos.setIsCheck(2);
				}else{
					signDtos.setIsCheck(1);
				}
			}
        }
	}
	
}
