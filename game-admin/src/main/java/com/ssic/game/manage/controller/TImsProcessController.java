package com.ssic.game.manage.controller;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;





















import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;















import org.springframework.web.multipart.MultipartFile;

import com.ssic.game.admin.pageModel.DataGrid;
import com.ssic.game.admin.pageModel.Json;
import com.ssic.game.admin.pageModel.PageHelper;
import com.ssic.game.admin.pageModel.Role;
import com.ssic.game.admin.pageModel.Tree;
import com.ssic.game.admin.service.ProjectService;
import com.ssic.game.common.dto.ProcessDto;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.game.common.util.HttpDataResponse;
import com.ssic.game.manage.image.FileUtils;
import com.ssic.game.manage.image.ImageInfo;
import com.ssic.game.manage.image.ImageInfoDto;
import com.ssic.game.manage.image.JsonUtil;
import com.ssic.game.manage.image.PropertiesUtils;
import com.ssic.game.manage.image.RequestResult;
import com.ssic.game.manage.service.ITImsProcessService;
import com.ssic.util.BeanUtils;
import com.ssic.util.UUIDGenerator;
import com.ssic.util.model.Response;



@Controller
@RequestMapping("/tImsProcessController")
public class TImsProcessController {

	@Autowired
	private ITImsProcessService iTImsProcessService;
	
	@Autowired
	private ProjectService projectService;
	
	protected static final Log logger = LogFactory.getLog(TImsProcessController.class);
	
	@ResponseBody
	@RequestMapping("/findProcess")
	public List<ProcessDto> findProcess(ProcessDto processDto,HttpServletRequest request){
		return iTImsProcessService.findProcess(processDto);
	}
	
	@RequestMapping("/insertProcess")
	@ResponseBody
	public Json insertProcess(ProcessDto processDto,String startDateS,String endDateS,String projid) throws ParseException{

		Json j =new Json();
		
        if(processDto==null){
			j.setMsg("流程对象不能为空");
			j.setSuccess(false);
			return j;
		}
        if(startDateS==null||startDateS.equals("")){
        	j.setMsg("开始时间不能为空");
            j.setSuccess(false);
            return j;
        }
        if(endDateS==null||endDateS.equals("")){
        	j.setMsg("结束时间不能为空");
            j.setSuccess(false);
            return j;
        }
        if(projid==null||projid.equals("")){
        	j.setMsg("项目ID不能为空");
        	j.setSuccess(false);
        	return j;
        }
		if(processDto.getProcName()==null||processDto.getProcName().equals("")){
			j.setMsg("流程名称不能为空");
			j.setSuccess(false);
			return j;
		}
		if(processDto.getProcName().length()>50){
			j.setMsg("流程名称长度不能大于50");
			j.setSuccess(false);
			return j;
		}
		if(processDto.getDescribes()!=null){
			if(processDto.getDescribes().length()>150){
				j.setMsg("描述长度不能大于150");
				j.setSuccess(false);
				return j;
			}
		}
		
		processDto.setId(UUIDGenerator.getUUID());
		processDto.setStat(1);
		if(processDto.getStartTask()==null||processDto.getStartTask().equals("")){
			processDto.setIsdefine(0);
		}else{
			processDto.setIsdefine(1);
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		

	    Date startDate = sdf.parse(startDateS);  
	    Date endDate = sdf.parse(endDateS);
	    
	    if(startDate.getTime()>endDate.getTime()){
	    	j.setMsg("流程开始时间不能大于流程结束时间");
	    	j.setSuccess(false);;
	    	return j;
	    }
	    
	    
	    processDto.setStartDate(startDate);
	    processDto.setEndDate(endDate);		
	    processDto.setProjId(projid);
	    processDto.setStat(1);
	    processDto.setState(1);
	    processDto.setCreateTime(new Date());
		iTImsProcessService.insertPro(processDto);
		j.setMsg("创建流程成功");
		j.setSuccess(true);
		return j;
		
	}
	
	@RequestMapping("/updatePro")
	@ResponseBody
	public Json updatePro(ProcessDto processDto,String startDateS,String endDateS) throws ParseException{
		Json j =new Json();
		
		 if(processDto==null){
				j.setMsg("流程对象不能为空");
				j.setSuccess(false);
				return j;
			}
	        if(startDateS==null||startDateS.equals("")){
	        	j.setMsg("开始时间不能为空");
	            j.setSuccess(false);
	            return j;
	        }
	        if(endDateS==null||endDateS.equals("")){
	        	j.setMsg("结束时间不能为空");
	            j.setSuccess(false);
	            return j;
	        }
			if(processDto.getProcName()==null||processDto.getProcName().equals("")){
				j.setMsg("流程名称不能为空");
				j.setSuccess(false);
				return j;
			}
			if(processDto.getProcName().length()>50){
				j.setMsg("流程名称长度不能大于50");
				j.setSuccess(false);
				return j;
			}
			if(processDto.getDescribes()!=null){
				if(processDto.getDescribes().length()>150){
					j.setMsg("描述长度不能大于150");
					j.setSuccess(false);
					return j;
				}
			}
		processDto.setStat(1);
		if(processDto.getStartTask()==null||processDto.getStartTask().equals("")){
			processDto.setIsdefine(0);
		}else{
			processDto.setIsdefine(1);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    Date startDate = sdf.parse(startDateS);  
	    Date endDate = sdf.parse(endDateS);
	    
	    if(startDate.getTime()>endDate.getTime()){
	    	j.setMsg("流程开始时间不能大于流程结束时间");
	    	j.setSuccess(false);
	    	return j;
	    }
	    
	    
	    processDto.setStartDate(startDate);
	    processDto.setEndDate(endDate);		
	    processDto.setState(0);
	    processDto.setLastUpdateTime(new Date());
		iTImsProcessService.updatePro(processDto);
		j.setMsg("创建流程成功");
		j.setSuccess(true);
		return j;
	}
	
	@RequestMapping("/deletePro")
	@ResponseBody
	public Json deletePro(ProcessDto processDto){
		Json j = new Json();
	    List<ProcessDto> listinst=	iTImsProcessService.findInst(processDto);
	    if(listinst.size()>0){
	    	j.setMsg("该流程正在使用不能删除");
	        j.setSuccess(false);
	        return j;
	    }
		iTImsProcessService.deletePro(processDto);
		j.setMsg("删除流程成功");
		j.setSuccess(true);
		return j;
	}
	
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request){
		  //查询所有的项目
		   List<ProjectDto> listproject =  projectService.findAll();
		   request.setAttribute("listproject", listproject);
		return "ims/process";
	}
	
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request,String projid) {
		request.setAttribute("projid", projid);
		return "ims/processAdd";
		//return "ims/processUpLoad";
	}
	
	@RequestMapping("upLoadImage")
	public String  upLoadImage(HttpServletRequest request,String id){
		request.setAttribute("projid", id);
		return "ims/processUpLoad";
	}
	
	
	
	@RequestMapping("/editProcess")
	public String editProcess(HttpServletRequest request, String id){
	   ProcessDto processDto  =new ProcessDto();
	   ProcessDto pDto = new ProcessDto();
	   processDto.setId(id);
	     List<ProcessDto> listprocess =	iTImsProcessService.findProcess(processDto);
		if(listprocess!=null&&listprocess.size()>0){
			 pDto = listprocess.get(0);
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Date sta= pDto.getStartDate();
		Date end = pDto.getEndDate();
		String stad = sdf.format(sta);
		String endd = sdf.format(end);
		request.setAttribute("pDto", pDto);
		request.setAttribute("stad", stad);
		request.setAttribute("endd", endd);
		return "ims/processEdit";
	}
	
	@ResponseBody
	@RequestMapping("/dataGrid")
    public DataGrid dataGrid(ProcessDto processDto, PageHelper ph){
		DataGrid dataGrid = new DataGrid();
		
		String proid = processDto.getId();
		String projid =  processDto.getProjId();
		if(proid!=null && proid.length()<15){
			processDto.setId("");
		}
		if(projid!=null && projid.length()<15){
			processDto.setProjId("");
		}
		
		List<ProcessDto> list = iTImsProcessService.findProcessALL(processDto,ph);
		int count = iTImsProcessService.findCount(processDto);
		dataGrid.setRows(list);
		dataGrid.setTotal(Long.valueOf(count));
    	return dataGrid;
    }
	
	 @RequestMapping("/grantUserPage")
	 public String grantUserPage(String ids, HttpServletRequest request,HttpSession session,String projId)
	    {
	        request.setAttribute("ids", ids);
	        request.setAttribute("projId", projId);
	        session.setAttribute("grantUserPerss", ids);
	        Role u = new Role();
	        if (ids != null && !ids.equalsIgnoreCase(""))
	        {
	          
	            String result = iTImsProcessService.findUserPers(ids);
//	            String userPer = actionService.findUserPers(ids, projId);
	            u.setResourceIds(result);
//	            u.setUserIds(userPer);
	            request.setAttribute("role", u);
	        }
	        return "ims/processGrant";
	    }
	 
	    /**
	     * 角色树(只能看到自己拥有的角色)
	     * 
	     * @return
	     */
	    @RequestMapping("/userTree")
	    @ResponseBody
	    public List<Tree> userTree(String searchName,String projId)
	    {
	    	System.out.println("projId---------"+projId);
	        return iTImsProcessService.userTree(searchName,projId);
	    }
	    
	    /**
	     * 用户授权
	     * 
	     * @param ids
	     * @return
	     */
	    @RequestMapping("/grantUsers")
	    @ResponseBody
	    public Json grantUsers(String resourceIds,HttpSession session) {
	        Json j = new Json();

	        if(session.getAttribute("grantUserPerss")!=null){
	        	iTImsProcessService.grantUser(session.getAttribute("grantUserPerss").toString(), resourceIds);
	            j.setSuccess(true);
	            j.setMsg("授权成功！");
	            return j;
	        }else{
	            j.setSuccess(false);
	            j.setMsg("寻找不到动作id，请重新赋用户权限!");
	            return j;
	        }


	    }
	    
	 
	 
	    
	    
	    
	  
		@RequestMapping("/create")
		@ResponseBody
		public Json createImage(ImageInfoDto image, @RequestParam("image")MultipartFile myfile, HttpServletRequest request, HttpServletResponse response,String projid) throws IOException {
			 logger.info("upload pic : " + image);

			 Json j = new Json();
			 
			 if(projid==null){
				 j.setMsg("流程不存在");
				 j.setSuccess(false);
				 return j;
			 }
			 
			 
			 if(!myfile.isEmpty()){  
				 //上传的必须是图片
				 String[] fileType = myfile.getOriginalFilename().split("[.]");
				 String fType = fileType[1];
				 
				 if(!"jpg".equals(fType) && !"gif".equals(fType) && !"jpeg".equals(fType) && !"png".equals(fType)){
					 j.setMsg("图片格式必须是：jpg,gif,jpeg,png");
					 j.setSuccess(false);
					 return j;
				 }
				 
				printFileInfo(myfile);  
		     	uploadImage(image, myfile, request);
		     }else{
		    	 j.setMsg("图片不能为空");
		    	 j.setSuccess(false);
		    	 return j;
		     }
	       
			create(image);
			 System.out.println(image.getUrl());
			 ProcessDto pdto = new ProcessDto();
			 pdto.setId(projid);
			 pdto.setImageUrl(image.getUrl());
			 pdto.setLastUpdateTime(new Date());
			 //如果已经有图片则更新image_url
		
			 iTImsProcessService.insertImageUrl(pdto);
			
			j.setMsg("图片保存成功");
			j.setSuccess(true);

	    	return j;
		}
		
		private void printFileInfo(MultipartFile myfile) {
			logger.info("文件长度: " + myfile.getSize());  
			logger.info("文件类型: " + myfile.getContentType());  
			logger.info("文件名称: " + myfile.getName());  
			logger.info("文件原名: " + myfile.getOriginalFilename());  
			logger.info("========================================");
		}
		
		private void uploadImage(ImageInfoDto image, MultipartFile myfile,
				HttpServletRequest request) throws IOException {
			//如果只是上传一个文件，则只需要MultipartFile类型接收文件即可，而且无需显式指定@RequestParam注解  
	        //如果想上传多个文件，那么这里就要用MultipartFile[]类型来接收文件，并且还要指定@RequestParam注解  
			//String context = "upload";
	       // String realPath = "D:/upload";
	        
	    	SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			String context = "upload" + "/" + format.format(new Date());
	        String realPath = PropertiesUtils.getProperty("upload.url");
	        
	        String fileName = FileUtils.getUploadFileName(myfile.getOriginalFilename());
	        FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath + "/" + context, fileName));  
	        image.setUrl(context + "/" + fileName);
		}
		
		public void create(ImageInfoDto imageInfo) {
			ImageInfo image = BeanUtils.createBeanByTarget(imageInfo, ImageInfo.class);
			//imageInfoDao.insert(image);
		}
	
		//读取本地的图片
		@RequestMapping(value = "/showPicture", method = {RequestMethod.GET, RequestMethod.POST })
		@ResponseBody
		public void showPicture(HttpServletResponse response,String proid) throws Exception
	    {
	        
	           // String picId = getRequest().getParameter("picId");
	           // String pic_path = pointCardApplyManager.findPicturePath(picId);
	          //  System.out.println(pic_path);
			   // String pic_path = "D:/upLoad/upload/"+pid+".jpg";
			ProcessDto processDto = new ProcessDto();
			processDto.setId(proid);
			List<ProcessDto> listpro= iTImsProcessService.findProcess(processDto);
			String imageUrl="";
			if(listpro!=null && listpro.size()>0){
				imageUrl="E:/upLoad/"+ listpro.get(0).getImageUrl();
				System.out.println("imageUrl---"+imageUrl);
			}else{
				imageUrl="D:/upLoad/upload/4d2687f2ed88416aa63e0805f8948d83.jpg";
			}
			
			    Response<String> result = new Response<String>();
			   
			    logger.info("pic_path=" + imageUrl);
	            FileInputStream is = new FileInputStream(imageUrl);
	            int i = is.available(); // 得到文件大小
	            byte data[] = new byte[i];
	            is.read(data); // 读数据
	            is.close();
	            response.setContentType("image/*"); // 设置返回的文件类型
	            OutputStream toClient = response.getOutputStream(); // 得到向客户端输出二进制数据的对象
	            toClient.write(data); // 输出数据
	            toClient.close();
	            
	            result.setStatus(HttpDataResponse.HTTP_SUCCESS);
	            result.setMessage("读取图片成功");
	            //return result;
	    }
		
}
