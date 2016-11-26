/**
 * 
 */
package com.ssic.catering.manage.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ssic.catering.admin.pageModel.DataGrid;
import com.ssic.catering.admin.pageModel.Json;
import com.ssic.catering.admin.pageModel.PageHelper;
import com.ssic.catering.base.dto.CafetoriumDto;
import com.ssic.catering.base.dto.CarteDto;
import com.ssic.catering.base.dto.CarteTypeDto;
import com.ssic.catering.base.dto.PageHelperDto;
import com.ssic.catering.base.service.ICafetoriumService;
import com.ssic.catering.base.service.ICarteService;
import com.ssic.catering.base.service.ICarteTypeService;
import com.ssic.catering.base.service.ICreateImageService;
import com.ssic.catering.common.image.ImageInfoDto;
import com.ssic.catering.common.util.DateUtil;
import com.ssic.game.common.dto.UploadFileInfoDto;
import com.ssic.game.common.service.IUploadService;
import com.ssic.game.common.util.UploadUtil;
import com.ssic.util.BeanUtils;
import com.ssic.util.PropertiesUtils;

/**		
 * <p>Title: CarteController </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 刘博	
 * @date 2015年8月4日 上午9:07:23	
 * @version 1.0
 * <p>修改人：刘博</p>
 * <p>修改时间：2015年8月4日 上午9:07:23</p>
 * <p>修改备注：</p>
 */

@Controller
@RequestMapping("/carteController")
public class CarteController
{

    @Autowired
    private ICarteService carteService;

    @Autowired
    private ICarteTypeService carteTypeService;

    @Autowired
    private ICafetoriumService cafetoriumService;

    @Autowired
    private ICreateImageService createImageService;
    
    @Autowired
    private IUploadService uploadService;

    protected static final Log logger = LogFactory.getLog(CarteController.class);

    /**
     * 菜品管理页面
     * 
     * @return
     */
    @RequestMapping("/manager")
    public String manager(HttpServletRequest request, String carteTypeId)
    {

        request.setAttribute("carteTypeId", carteTypeId);
        return "carte/carteType/carte";
    }

    /**
     * 
     * 菜品管理grid
     * @param fieldsDto
     * @return
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public DataGrid dataGrid(String carteTypeId, PageHelper ph)
    {
        DataGrid dataGrid = new DataGrid();
        PageHelperDto phDto = new PageHelperDto();
        Integer beginRow = (ph.getPage() - 1) * ph.getRows();

        BeanUtils.copyProperties(ph, phDto);
        phDto.setBeginRow(beginRow);
        List<CarteDto> list = carteService.findALL(carteTypeId, phDto);
        int count = carteService.findCount(carteTypeId);
        dataGrid.setRows(list);
        dataGrid.setTotal(Long.valueOf(count));
        return dataGrid;
    }

    /**
     * 跳转到添加菜品类型页面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/addPage")
    public String addPage(HttpServletRequest request)
    {
        CarteDto u = new CarteDto();
        u.setId(UUID.randomUUID().toString());
        u.setCarteWeek(DateUtil.localWeek(new Date()));
        request.setAttribute("cartDto", u);
        return "carte/carteType/carteAdd";
    }

    /**
     * 添加菜品类型
     * 
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Json add(@RequestParam("importExcel") MultipartFile myfile, HttpServletRequest request,
        HttpServletResponse response)
    {
        Json j = new Json();
        try
        {
            String fileName = myfile.getOriginalFilename();
            InputStream inputStream = myfile.getInputStream();
            List<CarteDto> list = parseExcel(fileName, inputStream);
            for (CarteDto dto : list)
            {
                if (dto.getCanImport() == 0)
                {
                    j.setMsg(dto.getCarteMessage());
                    j.setSuccess(false);
                    return j;
                }
            }
            // 导入菜品
            carteService.insertCartExcel(list);
            j.setSuccess(true);
            j.setMsg("导入菜品成功！");

        }
        catch (Exception e)
        {
            // e.printStackTrace();
            j.setMsg(e.getMessage());
        }
        return j;
    }

    /**
     * 菜品编辑页面
     * 
     * @return
     */
    @RequestMapping("/editPage")
    public String editPage(HttpServletRequest request, String id)
    {

        CarteDto r = carteService.findById(id);

        request.setAttribute("carteDto", r);
        return "carte/carteType/carteEdit";
    }

    /**
     * 编辑菜品
     * 
     * @param resource
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Json edit(CarteDto carteDto)
    {
        Json j = new Json();
        CarteDto r = carteService.findById(carteDto.getId());
        carteDto.setCreateTime(r.getCreateTime());
        carteService.update(carteDto);
        j.setSuccess(true);
        j.setMsg("编辑成功！");
        return j;
    }

    /**
     * 删除菜品
     * 
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Json delete(String id)
    {
        Json j = new Json();
        if (!StringUtils.isEmpty(id))
        {
            CarteDto tempCarte = carteService.findById(id);
            carteService.delete(tempCarte);
            j.setMsg("删除菜品类型成功！");
            j.setSuccess(true);
        }
        else
        {
            j.setMsg("删除菜品失败，id不存在！");
            j.setSuccess(false);
        }
        return j;
    }

    /**
     * 根据路径加载解析Excel
     * @param path
     * @return
     * @throws InvalidFormatException 
     * @throws EncryptedDocumentException 
     * @throws IOException 
     */
    public List<CarteDto> parseExcel(String path, InputStream inputStream) throws EncryptedDocumentException,
        InvalidFormatException, IOException
    {
        List<CarteDto> list = new ArrayList<CarteDto>();
        Workbook workBook = null;
        Sheet sheet = null;
        CarteDto c = new CarteDto();
        if (path != null && path.length() > 7)
        {
            //判断文件是否是Excel(2003、2007)
            String suffix = path.substring(path.lastIndexOf("."), path.length());

            //判断用户上传时间：只能是周1-周5
            if ((DateUtil.dayOfWeek(new Date()) - 1) > 5)
            {
                c.setCarteMessage("上传菜单时间只能为周1-周5.");
                c.setCanImport(0);
                list.add(c);
            }
            if (".xls".equals(suffix) || ".xlsx".equals(suffix))
            {

                workBook = WorkbookFactory.create(inputStream);

                if (workBook != null)
                {
                    int numberSheet = workBook.getNumberOfSheets();
                    if (numberSheet > 0)
                    {
                        sheet = workBook.getSheetAt(0);//获取第一个工作簿(Sheet)的内容【注意根据实际需要进行修改】
                        list = getExcelContent(sheet);
                    }
                    else
                    {
                        c.setCarteMessage("目标表格工作簿(Sheet)数目为0！");
                        c.setCanImport(0);
                        list.add(c);
                    }
                }

            }
            else
            {
                c.setCarteMessage("您上传的不是excel格式文件！");
                c.setCanImport(0);
                list.add(c);
            }
        }
        else
        {
            c.setCarteMessage("请选择要上传的excel文件！");
            c.setCanImport(0);
            list.add(c);
        }
        return list;
    }

    public List<CarteDto> getExcelContent(Sheet sheet)
    {
        List<CarteDto> list = new ArrayList<CarteDto>();
        int rowCount = sheet.getPhysicalNumberOfRows();//总行数
        String cafetoriumName = "";
        String cafetoriumId = "";

        if (rowCount > 1)
        {
            Row titleRow = sheet.getRow(0);//标题行
            cafetoriumName = titleRow.getCell(0).toString();
            CafetoriumDto cafetoriumDto = new CafetoriumDto();
            cafetoriumDto.setCafeName(cafetoriumName);
            List<CafetoriumDto> cafetoriumList = cafetoriumService.findALL(cafetoriumDto, null);
            if (!CollectionUtils.isEmpty(cafetoriumList))
            {
                cafetoriumId = cafetoriumList.get(0).getId();
                //根据食堂id,查询当前食堂的所有菜单类型;
                List<CarteTypeDto> typeDtoList = carteTypeService.finByCafetoriumId(cafetoriumId);
                Map<String, String> carteTypeMap = new HashMap<String, String>();
                for (CarteTypeDto typeDto : typeDtoList)
                {
                    carteTypeMap.put(typeDto.getCarteTypeName(), typeDto.getCafetoriumId());
                }

                for (int i = 0; i < rowCount; i++)
                {//遍历行，从第1行标题行开始遍历
                    Row row = sheet.getRow(i);

                    for (int j = 0; j < 17; j++)
                    {

                        Cell cell = row.getCell(j);
                        if (cell != null)
                        {
                            for (String carteType : carteTypeMap.keySet())
                            {
                                if (titleRow.getCell(j).toString().trim().equals(carteType))
                                {

                                    if (!cell.getStringCellValue().trim().equals(carteType))
                                    {
                                        CarteDto carteDto = new CarteDto();
                                        carteDto.setCafetoriumId(cafetoriumId);
                                        carteDto =
                                            setCarte(carteDto,
                                                titleRow.getCell(j).toString(),
                                                cell.getStringCellValue());
                                        list.add(carteDto);
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }

        return list;
    }

    private static CarteDto setCarte(CarteDto carteDto, String typeName, String cellName)
    {
        carteDto.setCarteName(cellName);
        carteDto.setCarteTypeName(typeName);
        carteDto.setCarteMessage("上传成功！");
        carteDto.setCanImport(1);
        return carteDto;
    }

    //上传图片
    @RequestMapping("/upLoadImage")
    public String upLoadImage(HttpServletRequest request, String id)
    {
        request.setAttribute("carteId", id);
        return "carte/carteType/carteUpLoad";
    }

    @RequestMapping("/create")
    @ResponseBody
    public Json createImage(ImageInfoDto image, @RequestParam("image") MultipartFile myfile,
        HttpServletRequest request, HttpServletResponse response, String carteId, HttpSession session)
        throws IOException
    {
        logger.info("upload pic : " + image);
        Json j = new Json();
        Map<String, Object> map = createImageService.createImage(image, myfile, request, response);
        //如果已经有图片则更新image_url		
        CarteDto carteDto = new CarteDto();
        carteDto.setId(carteId);
        carteDto.setLastUpdateTime(new Date());
        carteDto.setImg((String) map.get("image_url"));
        carteService.updateImage(carteDto);

        j.setMsg((String) map.get("message"));
        j.setSuccess((boolean) map.get("success"));

        return j;
    }
    
    /**
     * 	 
     * @author 朱振	
     * @date 2015年11月3日 下午10:14:10	
     * @version 1.0
     * @param image
     * @param myfile
     * @param request
     * @param response
     * @param carteId
     * @param session
     * @return
     * @throws IOException
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月3日 下午10:14:10</p>
     * <p>修改备注：</p>
     */
    @RequestMapping("/create1")
    @ResponseBody
    public Json createImage1(@RequestParam("image") MultipartFile multipartFile,@RequestParam("carteId")String carteId,
        HttpServletRequest request, HttpServletResponse response)
        throws IOException
    {
        logger.info("参数 MultipartFile:" + multipartFile+";参数carteId:"+carteId);
        
        Json result = new Json();
        
        if(multipartFile == null)
        {
            result.setMsg("参数image不能为空");
            result.setSuccess(false);
            return result;
        }
        
        if(StringUtils.isEmpty(carteId))
        {
            result.setMsg("参数carteId不能为空");
            result.setSuccess(false);
            return result;
        }
        
        CarteDto resultSet = carteService.findById(carteId);
        if(resultSet == null)
        {
            result.setSuccess(false);
            result.setMsg("该菜品不存在，数据");
            return result;
        }
       
        String rootPath = PropertiesUtils.getProperty("upload.url");
        if(rootPath == null)
        {
            rootPath = "";
        }
        
        UploadFileInfoDto upload = uploadService.uploadMultipartFileWithDate(multipartFile, rootPath);
        
        logger.info("上传文件："+upload);

        if(upload == null || StringUtils.isEmpty(upload.getExpectedUri()))
        {
            result.setSuccess(false);
            result.setMsg("上传失败");
            return result;
        }
        
        //如果已经有图片则更新image_url       
        CarteDto carteDto = new CarteDto();
        carteDto.setId(carteId);
        carteDto.setLastUpdateTime(new Date());
        carteDto.setImg(upload.getExpectedUri());
      

        if(true == upload.isSuccess())
        {
            if(carteService.updateBy(carteDto) <= 0)
            {
                logger.error("更新失败"+carteDto);
                //删除上传成功的图片
                UploadUtil.deleteFile(upload.getActualUri());
                result.setMsg("上传失败");
                result.setSuccess(false);
                result.setObj(upload);
                return result;
            }
            result.setMsg("上传成功");
            result.setSuccess(true);
            result.setObj(upload);
            return result;

        }
        else
        {
            result.setMsg("上传失败");
            result.setSuccess(false);
            return result;
        }
        
    }


}
