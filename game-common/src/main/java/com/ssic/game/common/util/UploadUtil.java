package com.ssic.game.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.util.Base64;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ssic.game.common.dto.UploadFileInfoDto;

/**		
 * <p>Title: UploadUtil </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振	
 * @date 2015年11月3日 下午4:21:50	
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年11月3日 下午4:21:50</p>
 * <p>修改备注：</p>
 */
public class UploadUtil
{
    private static final Logger log = Logger.getLogger(UploadUtil.class);
    
    /**
     * @author 朱振	
     * @date 2015年11月3日 下午5:01:49	
     * @version 1.0
     * @param multipartFile
     * @param absolutefilePath
     * @param expectedHeader
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月3日 下午5:01:49</p>
     * <p>修改备注：</p>
     */
    public static UploadFileInfoDto uploadMultipartFile(MultipartFile multipartFile, String absolutefilePath, String expectedHeader)
    {
        UploadFileInfoDto response = new UploadFileInfoDto();
        
        if(multipartFile == null)
        {
            log.error("上传文件不能为空");
            response.setSuccess(false);
            return response;
        }
        
        if(StringUtils.isEmpty(absolutefilePath))
        {
            log.error("目标文件不能为空");
            response.setSuccess(false);
            return response;
        }
        
        if(StringUtils.isEmpty(expectedHeader))
        {
            response.setExpectedUri(absolutefilePath.replace("\\", "/"));
            response.setActualUri(absolutefilePath.replace("\\", "/"));
        }
        
        //创建文件
        File target = UploadUtil.createEmptyFile(absolutefilePath);
        
        //更新为实际路径
        absolutefilePath = target.getAbsolutePath();
        //参数expectedHeader必须是absolutefilePath的一部分
        int beginIndex = -1;
        if((beginIndex =absolutefilePath.indexOf(expectedHeader)) == -1)
        {
            log.error("参数expectedHeader："+expectedHeader+"必须是参数absolutefilePath："+absolutefilePath+"的一部分");
            response.setSuccess(false);
            return response;
        }
        else
        {
            response.setExpectedUri(absolutefilePath.substring(beginIndex).replace("\\", "/"));
            response.setActualUri(absolutefilePath.replace("\\", "/"));
        }
        
        //文件创建失败
        if(StringUtils.isEmpty(target))
        {
            response.setSuccess(false);
            return response;
        }
        else
        {   //文件创建失败
            response.setOriginalFilename(multipartFile.getOriginalFilename());
            
            try
            {
                //同步
                synchronized(UploadUtil.class)
                {
                    FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), target);
                }
                
                
                response.setSuccess(true);
                return response;
            }
            catch (IOException e)
            {
               log.error("文件写入出错",e);
               response.setSuccess(false);
               return response;
            }
        }
    }
    
    /**
     * 上传base64File	 
     * @author 朱振	
     * @date 2015年11月3日 下午5:45:11	
     * @version 1.0
     * @param base64File
     * @param rootPath
     * @param suffix
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月3日 下午5:45:11</p>
     * <p>修改备注：</p>
     */
    public static UploadFileInfoDto uploadBase64File(String base64File, String absolutefilePath, String expectedHeader)
    {
        UploadFileInfoDto response = new UploadFileInfoDto();
        
        
        if (base64File == null){//文件为空
            log.error("base64字符串不能为空");
            response.setSuccess(false);
            return response;
        }
        
        if(StringUtils.isEmpty(absolutefilePath))
        {
            log.error("目标文件不能为空");
            response.setSuccess(false);
            return response;
        }
        
        if(StringUtils.isEmpty(expectedHeader))
        {
            response.setExpectedUri(absolutefilePath);
            response.setActualUri(absolutefilePath);
        }
        else
        {
          
            
         
        }
        
        File target = UploadUtil.createEmptyFile(absolutefilePath);
        
        //实际路径
        absolutefilePath = target.getAbsolutePath();
        
        //参数expectedHeader必须是absolutefilePath的一部分
          int beginIndex = -1;
          if((beginIndex =absolutefilePath.indexOf(expectedHeader)) == -1)
          {
              log.error("参数expectedHeader必须是参数absolutefilePath的一部分");
              response.setSuccess(false);
              return response;
          }
          else
          {
              response.setExpectedUri(absolutefilePath.substring(beginIndex));
              response.setActualUri(absolutefilePath);
          }
          
        //文件创建失败
        if(StringUtils.isEmpty(target))
        {
            response.setSuccess(false);
            return response;
        }
        else
        {
            Base64 decoder = new Base64();
            try {
                //Base64解码
                byte[] b = decoder.decode(base64File);
                for(int i=0;i<b.length;++i){
                    if(b[i]<0) {//调整异常数据
                        b[i]+=256;
                    }
                }
                
                //同步
                synchronized(UploadUtil.class)
                {
                    //写入文件
                    try(OutputStream out = new FileOutputStream(absolutefilePath))
                    {
                        out.write(b);
                        out.flush();
                    }
                    
                   
                }
               
                
                log.info("写入成功");
                
                response.setSuccess(true);
                return response;
            }catch (Exception e){
                log.error("写入数据出错", e);
                response.setSuccess(false);
                return response;
            }
                
        }
    
    }
    
    public static synchronized boolean deleteFile(String absolutefilePath)
    {
        if(StringUtils.isEmpty(absolutefilePath))
        {
            log.error("目标文件不能为空");
            return false;
        }
        
        File target = new File(absolutefilePath);
        if(target.exists())
        {
            log.info("删除文件"+target.getAbsolutePath());
            return target.delete();
        }
        
        return false;
    }
    
    /**
     * 	 
     * @author 朱振	
     * @date 2015年11月3日 下午5:56:56	
     * @version 1.0
     * @param absolutefilePath
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月3日 下午5:56:56</p>
     * <p>修改备注：</p>
     */
    public static synchronized File createEmptyFile(String absolutefilePath)
    {
        if(StringUtils.isEmpty(absolutefilePath))
        {
            log.error("目标文件不能为空");
            return null;
        }
        
        //创建文件
        File target = new File(absolutefilePath);
        
        //创建父目录
        String parentDirectory = target.getParent();
        if(!StringUtils.isEmpty(parentDirectory))
        {
            File parent = new File(parentDirectory);
            if(!parent.exists())
            {
                if(!parent.mkdirs())
                {
                    log.error("创建目录失败"+parent);
                    return null;
                }
                else
                {
                    log.info("创建目录成功——"+parent);
                }
            }
        }  
        
        //创建文件
        try
        {
            if(!target.exists())
            {
                if(!target.createNewFile())
                {
                    log.error("创建文件失败"+target);
                    return null;
                }
                else
                {
                    log.info("创建文件成功——"+target);
                    return target;
                }
            }
            else
            {
                log.info("文件已存在"+absolutefilePath);
                return target;
            }
            
        }
        catch (IOException e)
        {
            log.error("创建文件失败",e);
            return null;
        }
    }
}

