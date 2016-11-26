package com.ssic.game.common.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssic.game.common.dto.UploadFileInfoDto;
import com.ssic.game.common.service.IUploadService;
import com.ssic.game.common.util.UploadUtil;
import com.ssic.util.StringUtils;
import com.ssic.util.UUIDGenerator;

/**		
 * <p>Title: UploadServiceImpl </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振	
 * @date 2015年11月3日 下午6:42:37	
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年11月3日 下午6:42:37</p>
 * <p>修改备注：</p>
 */
@Service
public class UploadServiceImpl implements IUploadService
{
    private static final Logger log = Logger.getLogger(UploadServiceImpl.class);

    /**
     * 
     * (non-Javadoc)   
     * @see com.ssic.game.common.service.IUploadService#uploadMultipartFileWithDate(org.springframework.web.multipart.MultipartFile, java.lang.String)
     */
    @Override
    public UploadFileInfoDto uploadMultipartFileWithDate(MultipartFile multipartFile, String rootPath)
    {
        UploadFileInfoDto response = null;
        
        if(multipartFile == null)
        {
            log.error("上传文件不能为空");
            response = new UploadFileInfoDto();
            response.setSuccess(false);;
            return response;
        }
        
        if(StringUtils.isEmpty(rootPath))
        {
            log.error("父目录不能为空");
            response = new UploadFileInfoDto();
            response.setSuccess(false);;
            return response;
        }
        
        String time = new SimpleDateFormat("yyyyMMdd").format(new Date());
        if(StringUtils.isEmpty(multipartFile.getOriginalFilename()))
        {
            log.error("上传文件的原始名字不合法"+multipartFile.getOriginalFilename());
            response = new UploadFileInfoDto();
            response.setSuccess(false);;
            return response;
        }
        
        String[] arr = multipartFile.getOriginalFilename().split("\\.");
        
        StringBuilder sb = new StringBuilder();
        sb.append(rootPath);
        sb.append("/");
        sb.append(time);
        sb.append("/");
        sb.append(UUIDGenerator.getUUID32Bit());
        
        if(arr.length >=1)
        {
            sb.append(".");
            sb.append(arr[arr.length-1]);

        }
        else
        {
            log.error("上传文件的原始名字没有后缀名"+multipartFile.getOriginalFilename());
            response = new UploadFileInfoDto();
            response.setSuccess(false);;
            return response;
        }
       
        return UploadUtil.uploadMultipartFile(multipartFile, sb.toString(), time);
    }

}

