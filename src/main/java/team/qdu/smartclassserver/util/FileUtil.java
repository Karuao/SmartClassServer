package team.qdu.smartclassserver.util;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import org.springframework.web.multipart.MultipartFile;
import team.qdu.smartclassserver.domain.ApiResponse;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.List;

public class FileUtil {

    /*
     * Java文件操作 获取文件扩展名
     *
     *  Created on: 2011-8-2
     *      Author: blueeagle
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    /*
     * Java文件操作 获取不带扩展名的文件名
     *
     *  Created on: 2011-8-2
     *      Author: blueeagle
     */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     * If a deletion fails, the method stops attempting to
     * delete and returns "false".
     */
    public static void deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                deleteDir(new File(dir, children[i]));
            }
        }
        // 目录此时为空，可以删除
        dir.delete();
    }

    //在服务器保存上传的文件
    public static boolean genaratorFiles(List<MultipartFile> files, PrintWriter out, String fullDir, String failMessage) {
        //新建文件夹存放上传的文件
        new File(fullDir).mkdirs();
        //文件处理
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = null;
            String filename = null;
            BufferedOutputStream stream = null;
            file = files.get(i);
            try {
                byte[] bytes = file.getBytes();
                filename = String.valueOf(i) + "." + FileUtil.getExtensionName(file.getOriginalFilename());
                stream = new BufferedOutputStream(new FileOutputStream(
                        new File(fullDir + filename)));
                stream.write(bytes);
                stream.close();
            } catch (Exception e) {
                e.printStackTrace();
                out.print(JSON.toJSONString(new ApiResponse<String>("1", failMessage)));
                out.close();
                return false;
            }
        }
        return true;
    }
}
