package team.qdu.smartclassserver.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import team.qdu.smartclassserver.config.MyWebMvcConfigurer;
import team.qdu.smartclassserver.domain.ApiResponse;
import team.qdu.smartclassserver.service.UserService;
import team.qdu.smartclassserver.util.FileUtil;
import team.qdu.smartclassserver.util.IdGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MyWebMvcConfigurer.CONTENT_TYPE);
        String account = request.getParameter("account");
        String password = request.getParameter("password");

        PrintWriter out = response.getWriter();
        String responseJson = userService.login(account, password);
        out.print(responseJson);
        out.close();
    }

    @RequestMapping(value = "/register")
    public void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MyWebMvcConfigurer.CONTENT_TYPE);
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        String answer = request.getParameter("answer");
        String question = request.getParameter("question");
        PrintWriter out = response.getWriter();
        String responseJson = userService.register(account, password, question, answer);
        out.print(responseJson);
        out.close();
    }

    @RequestMapping(value = "/findPassOne")
    public void checkAccount(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MyWebMvcConfigurer.CONTENT_TYPE);
        String account = request.getParameter("account");

        PrintWriter out = response.getWriter();
        String responseJson = userService.checkAccount(account);
        out.print(responseJson);
        out.close();
    }

    @RequestMapping(value = "/searchById")
    public void getUserInforById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MyWebMvcConfigurer.CONTENT_TYPE);
        int userId = Integer.parseInt(request.getParameter("userId"));
        PrintWriter out = response.getWriter();
        String responseJson = userService.getUserInforById(userId);
        out.print(responseJson);
        out.close();
    }

    @RequestMapping(value = "/updatePass")
    public void updatePassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MyWebMvcConfigurer.CONTENT_TYPE);
        String account = request.getParameter("account");
        String newPass = request.getParameter("newPass");
        PrintWriter out = response.getWriter();
        String responseJson = userService.updatePassword(account, newPass);
        out.print(responseJson);
        out.close();
    }

    @RequestMapping(value = "/updateUserInformation")
    public void updateUserInformation(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MyWebMvcConfigurer.CONTENT_TYPE);
        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("uploadfile");
        String account = params.getParameter("account");
        String name = params.getParameter("name");
        String gender = params.getParameter("gender");
        String sno = params.getParameter("sno");
        String university = params.getParameter("university");
        String department = params.getParameter("department");
        String motto = params.getParameter("motto");
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
                        new File(MyWebMvcConfigurer.UPLOAD_PATH + "resources/user/avatar/" + filename)));
                stream.write(bytes);
                stream.close();
                responseJson = userService.updateUserInformation("user/avatar/" + filename, account, name, gender, sno, university, department, motto);
            } catch (Exception e) {
                e.printStackTrace();
                stream = null;
                responseJson = new Gson().toJson(new ApiResponse<String>("1", "上传个人信息失败"));
            }
        } else {
            responseJson = userService.updateUserInformation(null, account, name, gender, sno, university, department, motto);
        }
        out.print(responseJson);
        out.close();
    }

}
