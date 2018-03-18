package team.qdu.smartclassserver.controller;




import java.io.*;
import java.util.List;
import java.util.Map;


import com.google.gson.Gson;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import team.qdu.smartclassserver.config.MyWebMvcConfigurer;
import team.qdu.smartclassserver.domain.ApiResponse;
import team.qdu.smartclassserver.domain.User;
import team.qdu.smartclassserver.service.ClassService;
import team.qdu.smartclassserver.service.MaterialService;
import team.qdu.smartclassserver.service.UserService;
import team.qdu.smartclassserver.domain.Class;
import team.qdu.smartclassserver.util.FileUtil;
import team.qdu.smartclassserver.util.IdGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

@Controller
public class UploadController {

    @Autowired
    UserService userService;
    @Autowired
    ClassService classService;
    @Autowired
    MaterialService materialService;

    @RequestMapping("/upload")
    public String upload(HttpServletRequest request, HttpServletResponse response, Integer class_id) {
        HttpSession session = request.getSession();
        session.setAttribute("classid", class_id);
        // System.out.println(class_id);
        return "upload";

    }

    @RequestMapping("/uploadFile")
    public String uploadFile(@RequestParam("uploadFile") MultipartFile file,HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name=file.getName();
        if (!file.isEmpty()) {
            response.setContentType(MyWebMvcConfigurer.CONTENT_TYPE);
            String dir = IdGenerator.generateGUID();
            String fullDir = MyWebMvcConfigurer.UPLOAD_PATH + "resources/material/url/" + dir + "/";
            new File(fullDir).mkdirs();
            String filename = null;
            BufferedOutputStream stream = null;
            try {
                byte[] bytes = file.getBytes();
                filename = "." + FileUtil.getExtensionName(file.getOriginalFilename());
                stream = new BufferedOutputStream(new FileOutputStream(
                        new File(fullDir + filename)));
                stream.write(bytes);
                stream.close();
                HttpSession session = request.getSession();
                int classid=(int)session.getAttribute("classid");
                materialService.uploadFile(name,"material/url/" + dir,classid);
            } catch (Exception e) {
                e.printStackTrace();
               return"上传资源失败";
            }

        }

        return "message";

    }

    @RequestMapping(value = "/")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/weblogin")
    public String check(HttpServletRequest request, HttpServletResponse response, User user) throws IOException {
        String account = user.getAccount();
        String password = user.getPassword();
        HttpSession session = request.getSession();
        String responseJson = userService.login(account, password);
        int userid = userService.getUserIdByaccount(account);
        session.setAttribute("userid", userid);
        JSONObject jsonObj = JSONObject.fromObject(responseJson);
        String event = jsonObj.getString("event");
        if (event.equals("0")) {
            return "chooseClass";
        }
        if (event.equals("1")) {
            return "passError";
        } else
            return "notExist";
    }

    @ResponseBody
    @RequestMapping(value = "/chooseClass")
    public List<Class> getclass(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        int userid = (int) session.getAttribute("userid");
        List<Class> classList = classService.getClasses(userid);
        return classList;
    }
}




