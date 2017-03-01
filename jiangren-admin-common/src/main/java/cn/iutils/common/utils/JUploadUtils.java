package cn.iutils.common.utils;

import cn.iutils.common.config.JConfig;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * 上传文件服务工具类
 * @author iutils.cn
 */
public class JUploadUtils {

    /**
     * 文件保存
     * @param request
     * @return
     */
    public static File save(MultipartFile file,HttpServletRequest request) throws Exception{
        // 获取本地存储路径
        String path = request.getSession().getServletContext()
                .getRealPath(JConfig.getConfig(JConfig.FILEUPLOAD));
        String fileName = file.getOriginalFilename();
        // 取得后缀
        String suffixString = fileName
                .substring(fileName.lastIndexOf(".") + 1);
        String ip = JStringUtils.getRemoteAddr(request);
        ip = ip.replaceAll(":", "_");//移除文件名中的冒号
        // 取得IP地址+时间戳 作为文件名 防止文件名重复
        String randomFileName = ip + "_" + System.currentTimeMillis();
        // 设定文件名称
        fileName = randomFileName + "." + suffixString;

        File targetFile = new File(path, fileName);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        file.transferTo(targetFile);
        return targetFile;
    }

    /**
     * 获取文件后缀
     * @param fileName
     * @return
     */
    public static String getSuffix(String fileName){
        // 取得后缀
       return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

}
