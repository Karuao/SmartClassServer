package team.qdu.smartclassserver.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import team.qdu.smartclassserver.domain.ApiResponse;
import team.qdu.smartclassserver.service.HomeworkService;
import team.qdu.smartclassserver.util.FilenameUtil;
import team.qdu.smartclassserver.util.IdGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class HomeworkController {

    private static final String CONTENTTYPE= "text/plain; charset=utf-8";

    @Autowired
    HomeworkService homeworkService;

    //发布作业
    @RequestMapping("/publishHomework")
    public void publishHomework(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(CONTENTTYPE);
        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("uploadfile");
        String title = params.getParameter("title");
        Date deadline = null;
        try {
            deadline = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(params.getParameter("deadline"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String detail = params.getParameter("detail");
        int classId = Integer.parseInt(params.getParameter("classId"));
        PrintWriter out = response.getWriter();
        String responseJson = null;
        //文件处理
        if (!files.isEmpty()) {
            MultipartFile file = null;
            String filename = null;
            BufferedOutputStream stream = null;
            file = files.get(0);
            try {
                byte[] bytes = file.getBytes();
                filename = IdGenerator.generateGUID() + "." + FilenameUtil.getExtensionName(file.getOriginalFilename());
                URL classpath = this.getClass().getResource("/");
                stream = new BufferedOutputStream(new FileOutputStream(
                        new File(classpath.getPath() + "resources/homework/url/" + filename)));
                stream.write(bytes);
                stream.close();
                responseJson = homeworkService.publishHomework(title, detail, deadline, "homework/url/" + filename, classId);
            } catch (Exception e) {
                e.printStackTrace();
                responseJson = new Gson().toJson(new ApiResponse<String>("1", "上传作业信息失败"));
            }
        } else {
            responseJson = homeworkService.publishHomework(title, detail, deadline, null, classId);
        }
        out.print(responseJson);
        out.close();
    }

    //获取作业列表
    @RequestMapping("/getHomeworkList")
    public void getHomeworkList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(CONTENTTYPE);
        int classId = Integer.parseInt(request.getParameter("classId"));
        int userId = Integer.parseInt(request.getParameter("userId"));
        String userTitle = request.getParameter("userTitle");
        String requestStatus = request.getParameter("requestStatus");
        PrintWriter out = response.getWriter();
        String responseJson = homeworkService.getHomeworkList(classId, userId, userTitle, requestStatus);
        out.print(responseJson);
        out.close();
    }

    //更改作业状态
    @RequestMapping("/changeHomeworkStatus")
    public void changeHomeworkStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(CONTENTTYPE);
        int homeworkId = Integer.parseInt(request.getParameter("homeworkId"));
        String homeworkStatus = request.getParameter("homeworkStatus");
        PrintWriter out = response.getWriter();
        String responseJson = homeworkService.changeHomeworkStatus(homeworkId, homeworkStatus);
        out.print(responseJson);
        out.close();
    }

    //学生获取作业详情
    @RequestMapping("/getStuHomeworkDetail")
    public void getStuHomeworkDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(CONTENTTYPE);
        int homeworkAnswerId = Integer.parseInt(request.getParameter("homeworkAnswerId"));
        PrintWriter out = response.getWriter();
        String responseJson = homeworkService.getStuHomeworkDetail(homeworkAnswerId);
        out.print(responseJson);
        out.close();
    }

    //学生提交作业
    @RequestMapping("/submitHomework")
    public void submitHomework(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(CONTENTTYPE);
        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("uploadfile");
        int homeworkAnswerId = Integer.parseInt(params.getParameter("homeworkAnswerId"));
        String detail = params.getParameter("detail");
        PrintWriter out = response.getWriter();
        String responseJson = null;
        //文件处理
        if (!files.isEmpty()) {
            MultipartFile file = null;
            String filename = null;
            BufferedOutputStream stream = null;
            file = files.get(0);
            try {
                byte[] bytes = file.getBytes();
                filename = IdGenerator.generateGUID() + "." + FilenameUtil.getExtensionName(file.getOriginalFilename());
                URL classpath = this.getClass().getResource("/");
                stream = new BufferedOutputStream(new FileOutputStream(
                        new File(classpath.getPath() + "resources/homework_answer/url/" + filename)));
                stream.write(bytes);
                stream.close();
                responseJson = homeworkService.submitHomework(homeworkAnswerId, detail, "homework_answer/url/" + filename);
            } catch (Exception e) {
                e.printStackTrace();
                responseJson = new Gson().toJson(new ApiResponse<String>("1", "上传作业信息失败"));
            }
        } else {
            responseJson = homeworkService.submitHomework(homeworkAnswerId, detail, null);
        }

        out.print(responseJson);
        out.close();
    }
}
