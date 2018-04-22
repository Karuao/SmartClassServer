package team.qdu.smartclassserver.controller;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import team.qdu.smartclassserver.config.MyWebMvcConfigurer;
import team.qdu.smartclassserver.domain.ApiResponse;
import team.qdu.smartclassserver.service.HomeworkService;
import team.qdu.smartclassserver.util.FileUtil;
import team.qdu.smartclassserver.util.IdGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class HomeworkController {

    @Autowired
    HomeworkService homeworkService;

    @RequestMapping("/publishHomework")
    public void publishHomework(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MyWebMvcConfigurer.CONTENT_TYPE);
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

        if (!files.isEmpty()) {
            String dir = IdGenerator.generateGUID();    //新建文件夹名
            String fullDir = MyWebMvcConfigurer.UPLOAD_PATH + "resources/SmartClass/homework/url/" + dir + "/";
            if (!FileUtil.genaratorFiles(files, out, fullDir , "上传作业失败，请稍后再试")) {
                return;
            }
            homeworkService.publishHomework(title, detail, deadline, "SmartClass/homework/url/" + dir, files.size(), classId);
        } else {
            homeworkService.publishHomework(title, detail, deadline, null, 0, classId);
        }
        out.print(JSON.toJSONString(new ApiResponse<String>("0", "发布作业成功")));
        out.close();
    }

    //获取作业列表
    @RequestMapping("/getHomeworkList")
    public void getHomeworkList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MyWebMvcConfigurer.CONTENT_TYPE);
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
        response.setContentType(MyWebMvcConfigurer.CONTENT_TYPE);
        int homeworkId = Integer.parseInt(request.getParameter("homeworkId"));
        String homeworkStatus = request.getParameter("homeworkStatus");
        String homeworkTitle = request.getParameter("homeworkTitle");
        PrintWriter out = response.getWriter();
        String responseJson = homeworkService.changeHomeworkStatus(homeworkId, homeworkStatus, homeworkTitle);
        out.print(responseJson);
        out.close();
    }

    //学生获取作业详情
    @RequestMapping("/getStuHomeworkDetail")
    public void getStuHomeworkDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MyWebMvcConfigurer.CONTENT_TYPE);
        int homeworkAnswerId = Integer.parseInt(request.getParameter("homeworkAnswerId"));
        PrintWriter out = response.getWriter();
        String responseJson = homeworkService.getStuHomeworkDetail(homeworkAnswerId);
        out.print(responseJson);
        out.close();
    }


    //学生提交作业
    @RequestMapping("/commitHomework")
    public void commitHomework(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MyWebMvcConfigurer.CONTENT_TYPE);
        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("uploadfile");
        int homeworkAnswerId = Integer.parseInt(params.getParameter("homeworkAnswerId"));
        int homeworkId = Integer.parseInt(params.getParameter("homeworkId"));
        int classId = Integer.parseInt(params.getParameter("classId"));
        int userId = Integer.parseInt(params.getParameter("userId"));
        String ifSubmit = params.getParameter("ifSubmit");
        String homeworkTitle = params.getParameter("homeworkTitle");
        String detail = params.getParameter("detail");
        PrintWriter out = response.getWriter();
        String responseJson;
        //文件处理
        if (!files.isEmpty()) {
            //新建文件夹存放上传的文件
            String dir = IdGenerator.generateGUID();
            String fullDir = MyWebMvcConfigurer.UPLOAD_PATH + "resources/SmartClass/homework_answer/url/" + dir + "/";
            if (!FileUtil.genaratorFiles(files, out, fullDir, "提交作业失败，请稍后再试")) {
                return;
            }
            responseJson = homeworkService.commitHomework(homeworkAnswerId, homeworkId, classId, userId, ifSubmit, homeworkTitle,
                    detail, "SmartClass/homework_answer/url/" + dir, files.size());
        } else {
            responseJson = homeworkService.commitHomework(homeworkAnswerId, homeworkId, classId, userId, ifSubmit, homeworkTitle, detail, null, files.size());
        }

        out.print(responseJson);
        out.close();
        //删除之前的文件
        String delPhotoesUrl = params.getParameter("delPhotoesUrl");
        if (delPhotoesUrl != null) {
            FileUtil.deleteDir(new File(MyWebMvcConfigurer.UPLOAD_PATH + "resources/" + delPhotoesUrl));
        }
    }

    //获取作业内容和照片
    @RequestMapping("/getHomeworkDetail")
    public void getHomeworkDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MyWebMvcConfigurer.CONTENT_TYPE);
        int homeworkId = Integer.parseInt(request.getParameter("homeworkId"));
        PrintWriter out = response.getWriter();
        String responseJson = homeworkService.getHomeworkDetail(homeworkId);
        out.print(responseJson);
        out.close();
    }

    //获取某作业学生提交情况List
    @RequestMapping("/getHomeworkAnswerList")
    public void getHomeworkAnswerList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MyWebMvcConfigurer.CONTENT_TYPE);
        int homeworkId = Integer.parseInt(request.getParameter("homeworkId"));
        PrintWriter out = response.getWriter();
        String responseJson = homeworkService.getHomeworkAnswerList(homeworkId);
        out.print(responseJson);
        out.close();
    }

    //提交作业评价
    @RequestMapping("/commitHomeworkEvaluation")
    public void commitHomeworkEvaluation(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MyWebMvcConfigurer.CONTENT_TYPE);
        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("uploadfile");
        int homeworkAnswerId = Integer.parseInt(params.getParameter("homeworkAnswerId"));
        int exp = Integer.parseInt(params.getParameter("exp"));
        String remark = params.getParameter("remark");
        PrintWriter out = response.getWriter();
        //文件处理
        if (!files.isEmpty()) {
            //新建文件夹存放上传的文件
            String dir = IdGenerator.generateGUID();
            String fullDir = MyWebMvcConfigurer.UPLOAD_PATH + "resources/SmartClass/homework_answer/remark_url/" + dir + "/";
            if (!FileUtil.genaratorFiles(files, out, fullDir, "评价失败，请稍后再试")) {
                return;
            }
            homeworkService.commitHomeworkEvaluation(homeworkAnswerId, exp, remark, "SmartClass/homework_answer/remark_url/" + dir, files.size());
        } else {
            homeworkService.commitHomeworkEvaluation(homeworkAnswerId, exp, remark, null, files.size());
        }

        out.print(JSON.toJSONString(new ApiResponse<String>("0", "评价成功")));
        out.close();
        //删除之前的文件
        String delPhotoesUrl = params.getParameter("delPhotoesUrl");
        if (delPhotoesUrl != null) {
            FileUtil.deleteDir(new File(MyWebMvcConfigurer.UPLOAD_PATH + "resources/" + delPhotoesUrl));
        }
    }

    //获取某作业学生提交情况List
    @RequestMapping("/getNotEvaluateStuNum")
    public void getNotEvaluateStuNum(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MyWebMvcConfigurer.CONTENT_TYPE);
        int homeworkId = Integer.parseInt(request.getParameter("homeworkId"));
        PrintWriter out = response.getWriter();
        String responseJson = homeworkService.getNotEvaluateStuNum(homeworkId);
        out.print(responseJson);
        out.close();
    }

    //发布作业
//    @RequestMapping("/publishHomework")
//    public void publishHomework(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        response.setContentType(MyWebMvcConfigurer.CONTENT_TYPE);
//        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
//        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("uploadfile");
//        String title = params.getParameter("title");
//        Date deadline = null;
//        try {
//            deadline = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(params.getParameter("deadline"));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        String detail = params.getParameter("detail");
//        int classId = Integer.parseInt(params.getParameter("classId"));
//        PrintWriter out = response.getWriter();
//        String responseJson = null;
//
//        if (!files.isEmpty()) {
//            //文件处理
//            MultipartFile file = null;
//            String filename = null;
//            BufferedOutputStream stream = null;
//            file = files.get(0);
//            try {
//                byte[] bytes = file.getBytes();
//                filename = IdGenerator.generateGUID() + "." + FileUtil.getExtensionName(file.getOriginalFilename());
//                stream = new BufferedOutputStream(new FileOutputStream(
//                        new File(MyWebMvcConfigurer.UPLOAD_PATH + "resources/homework/url/" + filename)));
//                stream.write(bytes);
//                stream.close();
//                responseJson = homeworkService.publishHomework(title, detail, deadline, "homework/url/" + filename, classId);
//            } catch (Exception e) {
//                e.printStackTrace();
//                responseJson = JSON.toJSONString(new ApiResponse<String>("1", "上传作业信息失败"));
//            }
//        } else {
//            responseJson = homeworkService.publishHomework(title, detail, deadline, null, classId);
//        }
//        out.print(responseJson);
//        out.close();
//    }
}
