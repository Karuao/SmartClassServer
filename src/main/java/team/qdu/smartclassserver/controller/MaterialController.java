package team.qdu.smartclassserver.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import team.qdu.smartclassserver.config.MyWebMvcConfigurer;
import team.qdu.smartclassserver.domain.Class;
import team.qdu.smartclassserver.service.ClassService;
import team.qdu.smartclassserver.service.MaterialService;
import team.qdu.smartclassserver.service.UserService;
import team.qdu.smartclassserver.util.FileUtil;
import team.qdu.smartclassserver.util.IdGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.List;

@Controller
public class MaterialController {

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
            String fullDir = MyWebMvcConfigurer.UPLOAD_PATH + "resources/SmartClass/material/url/" + dir + "/";
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

    @ResponseBody
    @RequestMapping(value = "/weblogin")
    public String check(HttpServletRequest request, HttpServletResponse response,String account,String password) throws IOException {
        HttpSession session = request.getSession();
        String event = userService.weblogin(account, password);
/*        JSONObject jsonObj = JSONObject.fromObject(responseJson);
        String event = jsonObj.getString("event");*/

        if(event.equals("0")) {
            int userid = userService.getUserIdByaccount(account);
            session.setAttribute("userid", userid);
        }


        return event;
    }
    @RequestMapping(value = "/chooseClass")
    public String showClass() {
        return "chooseClass";
    }


    @ResponseBody
    @RequestMapping(value = "/chooseClassRequest")
    public List<Class> getclass(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        int userid = (int) session.getAttribute("userid");
        List<Class> classList = classService.getClasses(userid);
        return classList;
    }
    //以上为上传资源

    @RequestMapping(value = "/getTeaMaterial")
    public void getTeaMaterial(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MyWebMvcConfigurer.CONTENT_TYPE);
        int classId = Integer.parseInt(request.getParameter("classId"));
        PrintWriter out = response.getWriter();
        String responseJson = materialService.getTeaMaterial(classId);
        out.print(responseJson);
        out.close();
    }
    @RequestMapping(value = "/getStuMaterial")
    public void getStuMaterial(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MyWebMvcConfigurer.CONTENT_TYPE);
        int classId = Integer.parseInt(request.getParameter("classId"));
        int userId=Integer.parseInt(request.getParameter("userId"));
        PrintWriter out = response.getWriter();
        String responseJson = materialService.getStuMaterial(classId,userId);
        out.print(responseJson);
        out.close();
    }
    @RequestMapping(value = "/deleteMaterial")
    public void deleteMaterial(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType(MyWebMvcConfigurer.CONTENT_TYPE);
        int materialId= Integer.parseInt(request.getParameter("materialId"));
        PrintWriter out = response.getWriter();
        String responseJson = materialService.deleteMaterial(materialId);
        out.print(responseJson);
        out.close();
    }
    @RequestMapping(value = "/DownloadMaterial")
    public void downloadMaterial(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType(MyWebMvcConfigurer.CONTENT_TYPE);
        Integer classid=Integer.parseInt(request.getParameter("classid"));
        Integer userid=Integer.parseInt(request.getParameter("userid"));
        String name= request.getParameter("name");
        Integer material_user_Id=Integer.parseInt(request.getParameter("materialuserid"));
        PrintWriter out = response.getWriter();
        String responseJson = materialService.downloadMaterial(classid,userid,name,material_user_Id);
        out.print(responseJson);
        out.close();
    }
}





