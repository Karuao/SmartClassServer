package team.qdu.smartclassserver.service;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import team.qdu.smartclassserver.config.MyWebMvcConfigurer;
import team.qdu.smartclassserver.dao.ClassMapper;
import team.qdu.smartclassserver.dao.MaterialMapper;
import team.qdu.smartclassserver.dao.Material_UserMapper;
import team.qdu.smartclassserver.domain.Material;
import team.qdu.smartclassserver.domain.Material_User;
import team.qdu.smartclassserver.util.FileUtil;
import team.qdu.smartclassserver.util.IdGenerator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;


/**
 * Servlet implementation class UploadServlet
 */

@WebServlet(name="UploadServlet",urlPatterns = "/UploadServlet")
public class UploadServlet extends HttpServlet {
    @Autowired
    MaterialMapper materialMapper;
    @Autowired
    ClassMapper classMapper;
    @Autowired
    Material_UserMapper material_userMapper;

    private static final long serialVersionUID = 1L;

    // 上传文件存储目录
    private static final String UPLOAD_DIRECTORY = "material/url";

    // 上传配置
    private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

    /**
     * 上传数据及保存文件
     */
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        // 检测是否为多媒体上传


        if (!ServletFileUpload.isMultipartContent(request)) {
            // 如果不是则停止
            PrintWriter writer = response.getWriter();
            writer.println("Error: 表单必须包含 enctype=multipart/form-data");
            writer.flush();
            return;
        }


        // 配置上传参数
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // 设置临时存储目录
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        ServletFileUpload upload = new ServletFileUpload(factory);

        // 设置最大文件上传值
        upload.setFileSizeMax(MAX_FILE_SIZE);

        // 设置最大请求值 (包含文件和表单数据)
        upload.setSizeMax(MAX_REQUEST_SIZE);

        // 中文处理
        upload.setHeaderEncoding("UTF-8");
        HttpSession session = request.getSession();
        int classid = (int) session.getAttribute("classid");
        String responseJson = null;
        // 构造临时路径来存储上传的文件
        // 这个路径相对当前应用的目录
        String uploadPath = MyWebMvcConfigurer.UPLOAD_PATH + "resources/SmartClass" + File.separator + UPLOAD_DIRECTORY;


        // 如果目录不存在则创建
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        try {
            // 解析请求的内容提取文件数据
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(request);

            if (formItems != null && formItems.size() > 0) {
                // 迭代表单数据
                for (FileItem item : formItems) {
                    // 处理不在表单中的字段
                    if (!item.isFormField()) {
                        String generatorName = IdGenerator.generateGUID();
                        String fileName = new File(item.getName()).getName();
                        if (fileName.length() > 32) {
                            request.setAttribute("message", "上传文件名过长，请重试");
                            break;
                        }
                        String ext = FileUtil.getExtensionName(fileName);
                        String filePath = uploadPath + File.separator + generatorName + "." + ext;
                        File storeFile = new File(filePath);
                        // 保存文件到硬盘
                        item.write(storeFile);
                        Date date = new Date();
                        Material material = new Material();
                        material.setName(fileName);
                        material.setUrl("SmartClass/material/url/" + generatorName + "." + ext);
                        material.setClass_id(classid);
                        material.setCreate_date_time(date);
                        material.setModify_date_time(date);
                        int result1 = materialMapper.insertSelective(material);
                        int result3=classMapper.updateMaterialByClassId(classid);
                        Material_User material_user = new Material_User();
                        material_user.setMaterial_id(material.getMaterial_id());
                        material_user.setName(fileName);
                        material_user.setUrl("SmartClass/material/url/" + generatorName + "." + ext);
                        material_user.setClass_id(classid);
                        material_user.setCreate_date_time(date);
                        material_user.setModify_date_time(date);
                        material_user.setIf_download("否");
                        material_user.setIf_browse("否");
                        List<Integer> userIdlist = classMapper.selectUserIdByClassId(classid);
                        for (int i = 0; i < userIdlist.size(); i++) {
                            int userid = userIdlist.get(i);
                            material_user.setUser_id(userid);
                            int result2 = material_userMapper.insertSelective(material_user);
                        }
                        request.setAttribute("message",
                                fileName + "上传成功!");
                    }
                }
            }
        } catch (Exception ex) {
            request.setAttribute("message",
                    "错误信息: " + ex.getMessage());
        }
        // 跳转到 message.jsp
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/message.jsp").forward(
                request, response);
    }
}