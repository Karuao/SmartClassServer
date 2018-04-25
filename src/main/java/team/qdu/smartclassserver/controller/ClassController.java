package team.qdu.smartclassserver.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import team.qdu.smartclassserver.config.MyWebMvcConfigurer;
import team.qdu.smartclassserver.domain.ApiResponse;
import team.qdu.smartclassserver.service.ClassService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

import team.qdu.smartclassserver.util.FileUtil;
import team.qdu.smartclassserver.util.IdGenerator;

@Controller
public class ClassController {

    @Autowired
    ClassService classService;

    //获取用户班课列表
    @RequestMapping(value = "/getJoinedClasses")
    public void getJoinedClasses(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MyWebMvcConfigurer.CONTENT_TYPE);
        int userId = Integer.parseInt(request.getParameter("userId"));
        PrintWriter out = response.getWriter();
        String responseJson = classService.getJoinedClasses(userId);
        out.print(responseJson);
        out.close();
    }


    //进入班课判断用户是老师还是学生
    @RequestMapping(value = "/jumpClass")
    public void jumpClass(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MyWebMvcConfigurer.CONTENT_TYPE);
        int classId = Integer.parseInt(request.getParameter("classId"));
        int userId = Integer.parseInt(request.getParameter("userId"));
        PrintWriter out = response.getWriter();
        String responseJson = classService.jumpClass(classId, userId);
        out.print(responseJson);
        out.close();
    }

    //创建班课
    @RequestMapping(value = "/createClass")
    public void createClass(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MyWebMvcConfigurer.CONTENT_TYPE);
        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("uploadfile");
        String name = params.getParameter("name");
        String course = params.getParameter("course");
        int userId = Integer.parseInt(params.getParameter("userId"));
        PrintWriter out = response.getWriter();
        String responseJson = null;

        if (!files.isEmpty()) {
            //文件处理
            MultipartFile file = null;
            String filename = null;
            BufferedOutputStream stream = null;
            file = files.get(0);
            try {
                byte[] bytes = file.getBytes();
                filename = IdGenerator.generateGUID() + "." + FileUtil.getExtensionName(file.getOriginalFilename());
                stream = new BufferedOutputStream(new FileOutputStream(
                        new File(MyWebMvcConfigurer.UPLOAD_PATH + "resources/SmartClass/class/avatar/" + filename)));
                stream.write(bytes);
                stream.close();
                responseJson = classService.createClass(name, course, userId, "SmartClass/class/avatar/" + filename);
            } catch (Exception e) {
                e.printStackTrace();
                stream = null;
                responseJson = JSON.toJSONString(new ApiResponse<String>("1", "上传班课信息失败"));
            }
        } else {
            responseJson = classService.createClass(name, course, userId, null);
        }
        out.print(responseJson);
        out.close();
    }

    //修改班课
    @RequestMapping("/modifyClass")
    public void modifyClass(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MyWebMvcConfigurer.CONTENT_TYPE);
        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("uploadfile");
        int classId = Integer.parseInt(params.getParameter("classId"));
        String className = params.getParameter("className");
        String course = params.getParameter("course");
        String university = params.getParameter("university");
        String department = params.getParameter("department");
        String goal = params.getParameter("goal");
        String exam = params.getParameter("exam");
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
                filename = IdGenerator.generateGUID() + "." + FileUtil.getExtensionName(file.getOriginalFilename());
                stream = new BufferedOutputStream(new FileOutputStream(
                        new File(MyWebMvcConfigurer.UPLOAD_PATH + "resources/SmartClass/class/avatar/" + filename)));
                stream.write(bytes);
                stream.close();
                responseJson = classService.modifyClass(classId, "SmartClass/class/avatar/" + filename, className, course, university, department, goal, exam);
            } catch (Exception e) {
                e.printStackTrace();
                responseJson = JSON.toJSONString(new ApiResponse<String>("1", "上传班课信息失败"));
            }
        }else {
            responseJson = classService.modifyClass(classId, null, className, course, university, department, goal, exam);
        }
        out.print(responseJson);
        out.close();
    }

    //加入班课
    @RequestMapping("/joinClass")
    public void joinClass(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MyWebMvcConfigurer.CONTENT_TYPE);
        int classId = Integer.parseInt(request.getParameter("classId"));
        int userId = Integer.parseInt(request.getParameter("userId"));
        PrintWriter out = response.getWriter();
        String responseJson = classService.joinClass(classId, userId);
        out.print(responseJson);
        out.close();
    }

    //确认加入班课
    @RequestMapping("/confirmJoinClass")
    public void confirmJoinClas(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MyWebMvcConfigurer.CONTENT_TYPE);
        int classId = Integer.parseInt(request.getParameter("classId"));
        int userId = Integer.parseInt(request.getParameter("userId"));
        PrintWriter out = response.getWriter();
        String responseJson = classService.confirmjoinClass(classId, userId);
        out.print(responseJson);
        out.close();
    }

    @RequestMapping(value = "/notAllowToJoin")
    public void notAllowToJoin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain; charset=utf-8");
        int classId = Integer.parseInt(request.getParameter("classId"));
        PrintWriter out = response.getWriter();
        String responseJson = classService.notAllowToJoin(classId);
        out.print(responseJson);
        out.close();
    }

    @RequestMapping(value = "/allowToJoin")
    public void allowToJoin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain; charset=utf-8");
        int classId = Integer.parseInt(request.getParameter("classId"));
        PrintWriter out = response.getWriter();
        String responseJson = classService.allowToJoin(classId);
        out.print(responseJson);
        out.close();
    }

    //获取班课信息
    @RequestMapping(value = "/getClassInfor")
    public void getClassInfor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain; charset=utf-8");
        int classId = Integer.parseInt(request.getParameter("classId"));
        PrintWriter out = response.getWriter();
        String responseJson = classService.getClassInfor(classId);
        out.print(responseJson);
        out.close();
    }

    @RequestMapping(value = "/finishClass")
    public void finishClass(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain; charset=utf-8");
        int classId = Integer.parseInt(request.getParameter("classId"));
        PrintWriter out = response.getWriter();
        String responseJson = classService.finishClass(classId);
        out.print(responseJson);
        out.close();
    }

    @RequestMapping(value = "/deleteClass")
    public void deleteClass(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain; charset=utf-8");
        int classId = Integer.parseInt(request.getParameter("classId"));
        PrintWriter out = response.getWriter();
        String responseJson = classService.deleteClass(classId);
        out.print(responseJson);
        out.close();
    }

    @RequestMapping(value = "/quitClass")
    public void quitClass(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain; charset=utf-8");
        int classId = Integer.parseInt(request.getParameter("classId"));
        int userId = Integer.parseInt(request.getParameter("userId"));
        PrintWriter out = response.getWriter();
        String responseJson = classService.quitClass(classId, userId);
        out.print(responseJson);
        out.close();
    }

    //查看有新推送的页面后进行操作
    @RequestMapping(value = "/readNew")
    public void readNew(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain; charset=utf-8");
        int classUserId = Integer.parseInt(request.getParameter("classUserId"));
        String whichPage = request.getParameter("whichPage");
        PrintWriter out = response.getWriter();
        String responseJson = classService.readNew(classUserId, whichPage);
        out.print(responseJson);
        out.close();
    }
}
