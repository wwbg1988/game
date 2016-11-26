package com.ssic.game.common.service;

import org.springframework.web.multipart.MultipartFile;

import com.ssic.game.common.dto.UploadFileInfoDto;

/**		
 * <p>Title: IUploadService </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振	
 * @date 2015年11月3日 下午6:39:57	
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年11月3日 下午6:39:57</p>
 * <p>修改备注：</p>
 */
public interface IUploadService
{
    /**
     * 上传file文件<BR>	 
     * @author 朱振	
     * @date 2015年11月3日 下午9:30:27	
     * @version 1.0
     * @param multipartFile
     * @param rootPath
     * @return
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年11月3日 下午9:30:27</p>
     * <p>修改备注：</p>
     */
    public UploadFileInfoDto uploadMultipartFileWithDate(MultipartFile multipartFile, String rootPath);
}

