package com.ssic.catering.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;

import org.apache.commons.net.util.Base64;

import com.ssic.util.DateUtils;

/**		
 * <p>Title: UploadUtil </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author 朱振	
 * @date 2015年9月9日 下午3:17:08	
 * @version 1.0
 * <p>修改人：朱振</p>
 * <p>修改时间：2015年9月9日 下午3:17:08</p>
 * <p>修改备注：</p>
 */
public class UploadUtil
{
    /**
     *   
     * @author 朱振   
     * @date 2015年9月9日 下午2:42:56    
     * @version 1.0
     * @param base64Img
     * @param rootPath
     * @return realPath
     * <p>修改人：朱振</p>
     * <p>修改时间：2015年9月9日 下午2:42:56</p>
     * <p>修改备注：</p>
     */
    public static String uploadBase64Picture(String base64Img, String rootPath, String suffix)
    {
        //对字节数组字符串进行Base64解码并生成图片
        if (base64Img == null){//图像数据为空
            return null;
        }
        Date date = new Date();
        String fileDe = DateUtils.format(date, DateUtils.YMD);
        
        //StringBuffer rootPath = new StringBuffer(PropertiesUtils.getProperty("upload.url"));
        StringBuffer realPath = new StringBuffer(rootPath);
        realPath.append("/");
        realPath.append(fileDe);

        File file =new File(realPath.toString());
        //如果文件夹不存在则创建    
        if(!file.exists() && !file.isDirectory()) {
            file.mkdirs();    
        }
        Base64 decoder = new Base64();
        try {
            //Base64解码
            byte[] b = decoder.decode(base64Img);
//            System.out.println("---------"+b.length);
            for(int i=0;i<b.length;++i){
                if(b[i]<0) {//调整异常数据
                    b[i]+=256;
                }
            }
            //生成jpeg图片
            String fileName = new Date().getTime()+"."+suffix;//新生成的图片
            String filePath = realPath.append("/").append(fileName).toString();
            OutputStream out = new FileOutputStream(filePath);    
            out.write(b);
            out.flush();
            out.close();
            
            
            
            return filePath;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
        
    }
}

