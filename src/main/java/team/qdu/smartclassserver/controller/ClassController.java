package team.qdu.smartclassserver.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import team.qdu.smartclassserver.domain.ApiResponse;
import team.qdu.smartclassserver.service.ClassService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.util.List;

import team.qdu.smartclassserver.util.FilenameUtil;
import team.qdu.smartclassserver.util.IdGenerator;

@Controller
public class ClassController {

    @Autowired
    ClassService classService;

    //获取用户班课列表
    @RequestMapping(value = "/getJoinedClasses")
    public void getJoinedClasses(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain; charset=utf-8");
        int userId = Integer.parseInt(request.getParameter("userId"));
        PrintWriter out = response.getWriter();
        String responseJson = classService.getJoinedClasses(userId);
        out.print(responseJson);
        out.close();
    }

    //进入班课判断用户是老师还是学生
    @RequestMapping(value = "/jumpClass")
    public void jumpClass(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain; charset=utf-8");
        int classId = Integer.parseInt(request.getParameter("classId"));
        int userId = Integer.parseInt(request.getParameter("userId"));
        PrintWriter out = response.getWriter();
        String responseJson = classService.judgeTitle(classId, userId);
        out.print(responseJson);
        out.close();
    }

    //创建班课
    @RequestMapping(value = "/createClass")
    public void createClass(HttpServletRequest request, HttpServletResponse response) throws IOException {
        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("uploadfile");
        String name = params.getParameter("name");
        String course = params.getParameter("course");
        int userId = Integer.parseInt(params.getParameter("userId"));
        PrintWriter out = response.getWriter();
        String responseJson = null;
        //文件处理
        MultipartFile file = null;
        String filename = null;
        BufferedOutputStream stream = null;
        file = files.get(0);
        try {
            byte[] bytes = file.getBytes();
            filename = IdGenerator.generateGUID() + "." + FilenameUtil.getExtensionName(file.getOriginalFilename());
            URL classpath = this.getClass().getResource("/");
            stream = new BufferedOutputStream(new FileOutputStream(
                    new File(classpath.getPath() + "resources/class/avatar/" + filename)));
            stream.write(bytes);
            stream.close();
            responseJson = classService.createClass(name, course, userId, "class/avatar/" + filename);
        } catch (Exception e) {
            e.printStackTrace();
            stream = null;
            responseJson = new Gson().toJson(new ApiResponse<String>("1", "上传班课信息失败"));
        }
        out.print(responseJson);
        out.close();
    }
}
