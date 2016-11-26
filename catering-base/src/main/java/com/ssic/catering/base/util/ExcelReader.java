/**
 * 
 *//*
package com.ssic.catering.base.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ssic.catering.base.dto.CarteDto;
import com.ssic.catering.base.service.ICarteTypeService;

*//**		
 * <p>Title: ExcelImport </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年8月5日 下午2:11:17	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年8月5日 下午2:11:17</p>
 * <p>修改备注：</p>
 *//*
*//**		
 * <p>Title: ExcelImport </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年8月5日 下午2:11:17	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年8月5日 下午2:11:17</p>
 * <p>修改备注：</p>
 *//*

public class ExcelReader
{

    @Autowired
    private ICarteTypeService carteTypeService;

    *//**
     * 根据路径加载解析Excel
     * @param path
     * @return
     * @throws InvalidFormatException 
     * @throws EncryptedDocumentException 
     * @throws IOException 
     *//*
    public static List<CarteDto> parseExcel(String path, InputStream inputStream)
        throws EncryptedDocumentException, InvalidFormatException, IOException
    {
        List<CarteDto> list = new ArrayList<CarteDto>();
        Workbook workBook = null;
        Sheet sheet = null;
   
        if (path != null && path.length() > 7)
        {
            CarteDto c = new CarteDto();
            //判断文件是否是Excel(2003、2007)
            String suffix = path.substring(path.lastIndexOf("."), path.length());
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
        return list;
    }

    @SuppressWarnings("static-access")
    public static List<CarteDto> getExcelContent(Sheet sheet)
    {
        List<CarteDto> list = new ArrayList<CarteDto>();
        int rowCount = sheet.getPhysicalNumberOfRows();//总行数
        if (rowCount > 1)
        {
            Row titleRow = sheet.getRow(0);//标题行
            for (int i = 0; i < rowCount; i++)
            {//遍历行，略过标题行，从第二行开始
                Row row = sheet.getRow(i);

                for (int j = 0; j < 17; j++)
                {

                    Cell cell = row.getCell(j);
                    if (cell != null)
                    {

                        if (titleRow.getCell(j).toString().trim().equals("大荤2"))
                        {

                            if (!cell.getStringCellValue().trim().equals("大荤2"))
                            {
                                CarteDto carteDto = new CarteDto();
                                carteDto =
                                    setCarte(carteDto,
                                        titleRow.getCell(j).toString(),
                                        cell.getStringCellValue());
                                list.add(carteDto);
                            }
                        }
                        else if (titleRow.getCell(j).toString().trim().equals("大荤"))
                        {
                            if (!cell.getStringCellValue().trim().equals("大荤"))
                            {
                                CarteDto carteDto = new CarteDto();
                                carteDto =
                                    setCarte(carteDto,
                                        titleRow.getCell(j).toString(),
                                        cell.getStringCellValue());
                                list.add(carteDto);
                            }
                        }
                        else if (titleRow.getCell(j).toString().trim().equals("小荤"))
                        {
                            if (!cell.getStringCellValue().trim().equals("小荤"))
                            {
                                CarteDto carteDto = new CarteDto();
                                carteDto =
                                    setCarte(carteDto,
                                        titleRow.getCell(j).toString(),
                                        cell.getStringCellValue());
                                list.add(carteDto);

                            }
                        }
                        else if (titleRow.getCell(j).toString().trim().equals("蔬菜"))
                        {
                            if (!cell.getStringCellValue().trim().equals("蔬菜"))
                            {
                                CarteDto carteDto = new CarteDto();
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

        return list;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException,
        EncryptedDocumentException, InvalidFormatException
    {
        String path2007 = "";//Excel2007文件路径
        path2007 = "E://crate5.xlsx";
        String path2003 = "";//Excel2007文件路径
        path2003 = "E://crate3.xls";
        InputStream in = null;
        List<CarteDto> list2007 = parseExcel(path2007, in);
    }

    private static CarteDto setCarte(CarteDto carteDto, String typeName, String cellName)
    {
        carteDto.setCarteName(cellName);
        carteDto.setCarteTypeName(typeName);
        carteDto.setCarteMessage("上传成功！");
        carteDto.setCanImport(1);
        return carteDto;
    }
}
*/