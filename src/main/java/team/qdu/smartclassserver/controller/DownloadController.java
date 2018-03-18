package team.qdu.smartclassserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import team.qdu.smartclassserver.config.MyWebMvcConfigurer;
import team.qdu.smartclassserver.service.MaterialService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@Controller
public class DownloadController {
    @Autowired
    MaterialService materialService;

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
        PrintWriter out = response.getWriter();
        String responseJson = materialService.getStuMaterial(classId);
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

}
