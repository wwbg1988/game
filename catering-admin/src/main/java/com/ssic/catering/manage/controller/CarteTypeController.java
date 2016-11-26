/**
 * 
 */
package com.ssic.catering.manage.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.ssic.catering.admin.pageModel.DataGrid;
import com.ssic.catering.admin.pageModel.Json;
import com.ssic.catering.admin.pageModel.PageHelper;
import com.ssic.catering.admin.service.ProjectService;
import com.ssic.catering.admin.service.UserServiceI;
import com.ssic.catering.base.dto.CafetoriumDto;
import com.ssic.catering.base.dto.CarteTypeDto;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.pojo.Carte;
import com.ssic.catering.base.pojo.CarteType;
import com.ssic.catering.base.service.ICafetoriumService;
import com.ssic.catering.base.service.ICarteService;
import com.ssic.catering.base.service.ICarteTypeService;
import com.ssic.catering.base.service.ICreateImageService;
import com.ssic.catering.common.image.ImageInfoDto;
import com.ssic.catering.common.image.PropertiesUtils;
import com.ssic.catering.common.util.UploadUtil;
import com.ssic.game.common.dto.ImsUsersDto;
import com.ssic.game.common.dto.ProjectDto;
import com.ssic.game.common.dto.UploadFileInfoDto;
import com.ssic.game.common.service.IUploadService;
import com.ssic.game.common.util.HttpDataResponse;
import com.ssic.util.BeanUtils;
import com.ssic.util.model.Response;

/**		
 * <p>Title: CarteTypeController </p>
 * <p>Description: 菜品类型</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年8月4日 上午10:51:41	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年8月4日 上午10:51:41</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping("/carteTypeController")
public class CarteTypeController
{

    @Autowired
    private ICarteTypeService carteTypeService;
    @Autowired
    private ICafetoriumService cafetoriumService;
    @Autowired
    private ICarteService carteService;
    @Autowired
    private ICreateImageService createImageService;
    @Autowired
    private UserServiceI userServiceI;
    @Autowired
    private ProjectService projectService;

    @Autowired
    private IUploadService uploadService;

    protected static final Log logger = LogFactory.getLog(CarteTypeController.class);

    /**
     * 跳转到资源管理页面
     * 
     * @return
     */
    @RequestMapping("/manager")
    public String manager(HttpServletRequest request, HttpSession session)
    {
        List<CafetoriumDto> cafeList = new ArrayList<CafetoriumDto>();
        //获取用户拥有的项目权限
        List<ProjectDto> listProject = userServiceI.getProjectBySession(session);
        if (!CollectionUtils.isEmpty(listProject))
        { //循环遍历每一个项目，放入查找dto中对象
            if (listProject.size() > 1)
            {//超管
                CafetoriumDto cafetoriumDto = new CafetoriumDto();
                cafeList = cafetoriumService.findALL(cafetoriumDto, null);
            }
            else
            {//获取项目id
                String projId = listProject.get(0).getId();
                CafetoriumDto cafetoriumDto = new CafetoriumDto();
                cafetoriumDto.setProjId(projId);
                cafeList = cafetoriumService.findALL(cafetoriumDto, null);

            }
        }

        request.setAttribute("cafeList", cafeList);
        //        String nginxPath = PropertiesUtils.getProperty("nginx.url");
        //        request.setAttribute("nginxPath", nginxPath);
        return "carte/carteType/carteType";
    }

    /**
     * 获取字段grid表单
     * 
     * @param fieldsDto
     * @return
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public DataGrid dataGrid(CarteTypeDto carteTypeDto, PageHelper ph, HttpSession session)
    {
        DataGrid dataGrid = new DataGrid();
        PageHelperDto phDto = new PageHelperDto();
        //返回对象;
        List<CarteTypeDto> listResult = new ArrayList<CarteTypeDto>();
        //返回数量;
        int countResult = 0;
        Integer beginRow = (ph.getPage() - 1) * ph.getRows();
        String projId = "";
        BeanUtils.copyProperties(ph, phDto);
        phDto.setBeginRow(beginRow);
        //获取用户拥有的项目权限
        List<ProjectDto> listProject = userServiceI.getProjectBySession(session);
        if (!CollectionUtils.isEmpty(listProject))
        { //循环遍历每一个项目，放入查找dto中对象

            if (listProject.size() > 1)
            {//超管
                listResult = carteTypeService.findALL(carteTypeDto, phDto);
                for (CarteTypeDto type : listResult)
                {
                    CafetoriumDto cafeDto = cafetoriumService.findById(type.getCafetoriumId());
                    if (cafeDto != null && !StringUtils.isEmpty(cafeDto.getProjId()))
                    {
                        ProjectDto proj = projectService.findById(cafeDto.getProjId());
                        if (proj != null && !StringUtils.isEmpty(proj.getProjName()))
                        {
                            type.setProjName(proj.getProjName());
                        }
                    }
                }
                countResult = carteTypeService.findCount(carteTypeDto);
            }
            else
            {
                projId = listProject.get(0).getId();
                String projName = listProject.get(0).getProjName();
                CafetoriumDto cafetoriumDto = new CafetoriumDto();
                cafetoriumDto.setProjId(projId);
                List<CafetoriumDto> listDto = cafetoriumService.findALL(cafetoriumDto, null);
                for (CafetoriumDto cafeDto : listDto)
                {
                    carteTypeDto.setCafetoriumId(cafeDto.getId());
                    List<CarteTypeDto> list = carteTypeService.findALL(carteTypeDto, phDto);
                    for (CarteTypeDto type : list)
                    {
                        type.setProjName(projName);

                    }
                    listResult.addAll(list);
                    int count = carteTypeService.findCount(carteTypeDto);
                    countResult += count;

                }
            }
        }

        dataGrid.setRows(listResult);
        dataGrid.setTotal(Long.valueOf(countResult));
        return dataGrid;
    }

    /**
     * 跳转到添加菜品类型页面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/addPage")
    public String addPage(HttpServletRequest request, String caftrotiumId, String caftrotiumName)
    {
        // u.setCarteWeek(localWeek());
        try
        {
            CarteTypeDto u = new CarteTypeDto();
            u.setId(UUID.randomUUID().toString());
            u.setCafetoriumId(caftrotiumId);
            u.setCafetoriumName(caftrotiumName);
            request.setCharacterEncoding("utf-8");
            request.setAttribute("cartTypeDto", u);
        }
        catch (UnsupportedEncodingException e)
        {
            //  对异常进行简要描述

        }

        return "carte/carteType/carteTypeAdd";
    }

    /**
     * 添加菜品类型
     * 
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Json add(CarteTypeDto carteTypeDto)
    {
        Json j = new Json();
        try
        {
            CarteType cartType =
                carteTypeService.finByName(carteTypeDto.getCarteTypeName(), carteTypeDto.getCafetoriumId());
            if (cartType != null)
            {
                j.setSuccess(false);
                j.setMsg("该菜品类型名称已经存在，请勿重复添加！");
                return j;
            }
            carteTypeService.add(carteTypeDto);
            j.setSuccess(true);
            j.setMsg("添加菜品类型成功！");
            j.setObj(carteTypeDto);
        }
        catch (Exception e)
        {
            // e.printStackTrace();
            j.setMsg(e.getMessage());
        }
        return j;
    }

    /**
     * 删除菜品类型
     * 
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Json delete(String id)
    {
        Json j = new Json();
        CarteTypeDto tempCarteType = carteTypeService.findById(id);
        List<Carte> carteList = carteService.findALLByTypeId(id);
        if (!CollectionUtils.isEmpty(carteList))
        {
            j.setMsg("该菜品类型下还有菜品,不能删除！");
            j.setSuccess(false);
            return j;
        }
        carteTypeService.delete(tempCarteType);
        j.setMsg("删除菜品类型成功！");
        j.setSuccess(true);
        return j;
    }

    /**
     * 菜品类型编辑页面
     * 
     * @return
     */
    @RequestMapping("/editPage")
    public String editPage(HttpServletRequest request, String id)
    {

        CarteTypeDto r = carteTypeService.findById(id);

        request.setAttribute("carteTypeDto", r);
        return "carte/carteType/carteTypeEdit";
    }

    /**
     * 编辑菜品类型
     * 
     * @param resource
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Json edit(CarteTypeDto carteTypeDto)
    {
        Json j = new Json();
        CarteTypeDto r = carteTypeService.findById(carteTypeDto.getId());
        carteTypeDto.setCreateTime(r.getCreateTime());
        carteTypeService.update(carteTypeDto);
        j.setSuccess(true);
        j.setMsg("编辑成功！");
        return j;
    }

    //上传图片
    @RequestMapping("/upLoadImage")
    public String upLoadImage(HttpServletRequest request, String id)
    {
        request.setAttribute("typeid", id);
        return "carte/carteType/carteTypeUpLoad";
    }

    @RequestMapping("/create")
    @ResponseBody
    public Json createImage(ImageInfoDto image, @RequestParam("image") MultipartFile myfile,
        HttpServletRequest request, HttpServletResponse response, String typeid) throws IOException
    {
        logger.info("upload pic : " + image);
        Json j = new Json();
        Map<String, Object> map = createImageService.createImage(image, myfile, request, response);
        //如果已经有图片则更新image_url		
        CarteTypeDto carteTypeDto = new CarteTypeDto();
        String urlmage = (String) map.get("image_url");
        urlmage = "images/" + urlmage;
        carteTypeDto.setIcon(urlmage);
        carteTypeDto.setId(typeid);
        carteTypeDto.setLastUpdateTime(new Date());
        carteTypeService.update(carteTypeDto);

        j.setMsg((String) map.get("message"));
        j.setSuccess((boolean) map.get("success"));

        return j;
    }

    @RequestMapping(value = "/editImage.do", method = {RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Response<String> editImage(String userId, String base64Img, HttpSession session)
    {
        Response<String> result = new Response<>();
        if (StringUtils.isEmpty(userId))
        {
            result = new Response<String>();
            result.setStatus(HttpDataResponse.HTTP_FAILE);
            result.setMessage("用户id不能为空");

            return result;
        }

        String rootPath = session.getServletContext().getRealPath("/");
        String realPath = UploadUtil.uploadBase64Picture(base64Img, rootPath + "/uploadPic", "jpg");

        if (!StringUtils.isEmpty(realPath))
        {
            ImsUsersDto imsUsersDto = new ImsUsersDto();
            imsUsersDto.setId(userId);
            imsUsersDto.setUserImage(realPath.substring(rootPath.length() + 1));

            //  cateringUserService.updateUserSelectiveById(imsUsersDto);
            return result;
        }

        result.setStatus(HttpDataResponse.HTTP_SUCCESS);
        result.setMessage("修改失败");

        return result;
    }

    /**
     * 	 
     * @author 朱振	
     * @date 2015年11月4日 上午11:39:10	
     * @version 1.0
     * @param typeid
     * @param request image 多文件上传
     * @param response 
     * @return
     * @throws IOException
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月4日 上午11:39:10</p>
     * <p>修改备注：</p>
     */
    @RequestMapping("/create1")
    @ResponseBody
    public Json createImage1(@RequestParam("typeid") String typeid, HttpServletRequest request,
        HttpServletResponse response) throws IOException
    {
        Json result = new Json();

        if (StringUtils.isEmpty(typeid))
        {
            result.setMsg("参数typeid不能为空");
            result.setSuccess(false);
            return result;
        }

        //获取上传的文件
        List<MultipartFile> images = ((MultipartRequest) request).getFiles("image");
        if (CollectionUtils.isEmpty(images))
        {
            result.setMsg("上传的文件不能为空");
            result.setSuccess(false);
            return result;
        }

        //查找盖菜品类型是否存在
        CarteTypeDto resultSet = carteTypeService.findById(typeid);
        if (resultSet == null)
        {
            result.setSuccess(false);
            result.setMsg("该菜品类型不存在");
            return result;
        }

        String rootPath = PropertiesUtils.getProperty("upload.url");
        if (rootPath == null)
        {
            rootPath = "";
        }

        CarteTypeDto carteTypeDto = new CarteTypeDto();
        //        carteTypeDto.setIcon(upload.getExpectedUri());
        //        carteTypeDto.setIcon2(upload.getExpectedUri());
        carteTypeDto.setId(typeid);

        List<UploadFileInfoDto> uploadFileInfos = new ArrayList<>();

        for (int i = 0; i < images.size(); i++)
        {
            //最多上传2张图片
            if (2 <= i)
            {
                break;
            }

            if (images.get(i).isEmpty())
            {
                continue;
            }

            //上传图片
            UploadFileInfoDto upload = uploadService.uploadMultipartFileWithDate(images.get(i), rootPath);

            logger.info("上传文件：" + upload);

            if (upload == null || StringUtils.isEmpty(upload.getExpectedUri()))
            {
                result.setSuccess(false);
                result.setMsg("上传失败");
                return result;
            }

            if (0 == i && upload.isSuccess())
            {
                carteTypeDto.setIcon(upload.getExpectedUri());//默认图片
            }
            else if (1 == i && upload.isSuccess())
            {
                carteTypeDto.setIcon2(upload.getExpectedUri());//选中图片
            }

            uploadFileInfos.add(upload);//更新
        }

        //上传文件不能为空
        if (images.size() >= 2 && images.get(0).isEmpty() && images.get(1).isEmpty())
        {
            result.setSuccess(false);
            result.setMsg("上传文件不能全为空");
            return result;
        }

        //更新数据库
        if (carteTypeService.updateBy(carteTypeDto) <= 0)
        {

            for (UploadFileInfoDto info : uploadFileInfos)
            {
                //删除上传成功的图片
                com.ssic.game.common.util.UploadUtil.deleteFile(info.getActualUri());
            }

            result.setSuccess(false);
            result.setMsg("上传失败");
            return result;
        }

        result.setSuccess(true);
        result.setMsg("上传成功");
        return result;
    }

}
