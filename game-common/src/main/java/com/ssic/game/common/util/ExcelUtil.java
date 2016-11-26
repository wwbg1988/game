package com.ssic.game.common.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.ssic.util.StringUtils;

/**
 * <p>
 * Title: ExcelUtil
 * </p>
 * <p>
 * Description: 类描述
 * </p>
 * <p>
 * Copyright (c) 2015
 * </p>
 * <p>
 * Company: 上海天坊信息科技有限公司
 * </p>
 * 
 * @author 朱振
 * @date 2015年11月13日 下午1:57:29
 * @version 1.0
 *          <p>
 *          修改人：朱振
 *          </p>
 *          <p>
 *          修改时间：2015年11月13日 下午1:57:29
 *          </p>
 *          <p>
 *          修改备注：
 *          </p>
 */
public class ExcelUtil
{

    private static final Logger log = Logger.getLogger(ExcelUtil.class);

    /**
     * 
     * @author 朱振
     * @date 2015年11月13日 下午2:00:24
     * @version 1.0
     * @param fileName
     * @param inputStream
     * @return <p>
     *         修改人：朱振
     *         </p>
     *         <p>
     *         修改时间：2015年11月13日 下午2:00:24
     *         </p>
     *         <p>
     *         修改备注：
     *         </p>
     */
    public static Workbook parseExcel(String fileName, InputStream inputStream)
    {
        if (StringUtils.isEmpty(fileName))
        {
            log.error("上传的excel的源文件名不能为空");
            return null;
        }
        else
        {

            int beginIndex = fileName.lastIndexOf(".");
            if (beginIndex < 0)
            {
                log.error("上传的excel的源文件名必须包含后缀名");
                return null;
            }

            String suffix = fileName.substring(beginIndex, fileName.length());

            if (".xls".equals(suffix) || ".xlsx".equals(suffix))
            {
                if (inputStream != null)
                {
                    try
                    {
                        return WorkbookFactory.create(inputStream);
                    }
                    catch (EncryptedDocumentException | InvalidFormatException | IOException e)
                    {
                        // 对异常进行简要描述
                        log.error("解析excel出错", e);
                    }
                }
                else
                {
                    log.error("参数inputStream不能为空");
                }

            }

            return null;
        }
    }
//
//    /**
//     * 
//     * @author 朱振
//     * @date 2015年11月13日 下午2:55:38
//     * @version 1.0
//     * @param workbook
//     * @param clazz
//     * @return <p>
//     *         修改人：朱振
//     *         </p>
//     *         <p>
//     *         修改时间：2015年11月13日 下午2:55:38
//     *         </p>
//     *         <p>
//     *         修改备注：
//     *         </p>
//     * @throws IntrospectionException
//     */
//    public static <T> List<T>  getObjects(Workbook workbook, Class<T> clazz, Integer sheetNumber,
//        boolean hasHeader) throws IntrospectionException
//    {
//        List<T> result = new ArrayList<>();
//
//        Sheet sheet = workbook.getSheetAt(sheetNumber);
//
//        if (sheet == null)
//        {
//            log.error("excel的sheet1为空");
//            return null;
//        }
//        
//              
//        
//        int i = 0;
//        if (hasHeader)
//        {
//            i = 1;// 有标题
//        }
//
//        for (; i < sheet.getLastRowNum(); i++)
//        {
//            T item = null;
//            try
//            {
//                item = clazz.newInstance();
//            }
//            catch (InstantiationException | IllegalAccessException e1)
//            {
//                // 对异常进行简要描述
//
//            }
//
//            // 判断行
//            Row row = sheet.getRow(i);
//            if (row == null)
//            {
//                continue;
//            }
//
//            for (int j = 0; j < row.getLastCellNum();j++)
//            {
//                try
//                {
//                    Cell cell = row.getCell(j);
//                    Object cellValue = null;
//                    if (cell != null)
//                    {
//                       
//                        
//                        boolean canCasted = false;
//                        int type = cell.getCellType();
//
//                        switch (type)
//                        {
//                            case 0:
//                                // CELL_TYPE_NUMERIC
//                                cellValue = cell.getNumericCellValue();
//                                canCasted = true;
//                                break;
//                            case 1:
//                                // CELL_TYPE_STRING
//                                cellValue = cell.getStringCellValue();
//                                canCasted = true;
//                                break;
//                            case 2:
//                                // CELL_TYPE_FORMULA
//                                cellValue = cell.getCellFormula();
//                                break;
//                            case 3:
//                                // CELL_TYPE_BLANK
//                                cellValue = "";
//                                break;
//                            case 4:
//                                // CELL_TYPE_BOOLEAN
//                                cellValue = cell.getBooleanCellValue();
//                                break;
//                            case 5:
//                                log.error("excel异常" + cell.getErrorCellValue());
//                                // CELL_TYPE_ERROR
//                                break;
//                            default:
//                                break;
//                        }
//                        
//                        
//                        if(canCasted)
//                        {
//                            Class<?>[] clazzs = method.getParameterTypes();
//                            if(!ArrayUtils.isEmpty(clazzs) && clazzs.length ==1)
//                            {
//                                method.invoke(item, clazzs[0].cast(cellValue));
//                            }
//                        }
//                        
//
//                    }
//                }
//                catch (IllegalArgumentException | IllegalAccessException
//                    | InvocationTargetException e)
//                {
//                    //这里是excel的转换异常，可以不处理
//                    // 对异常进行简要描述
//                }
//            }
//            
//            //将整行数据加入
//            result.add(item);
//        }
//
//        return result;
//
//    }
    
    
}
