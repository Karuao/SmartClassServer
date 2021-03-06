package team.qdu.smartclassserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import team.qdu.smartclassserver.config.MyWebMvcConfigurer;
import team.qdu.smartclassserver.service.InformService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class InformController {

    @Autowired
    InformService informService;

    @RequestMapping(value = "/getInform")
    public void getInform(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MyWebMvcConfigurer.CONTENT_TYPE);
        int classId = Integer.parseInt(request.getParameter("classId"));
        PrintWriter out = response.getWriter();
        String responseJson = informService.getInform(classId);
        out.print(responseJson);
        out.close();
    }
    @RequestMapping(value = "/getReadPeople")
    public void getReadPeople(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MyWebMvcConfigurer.CONTENT_TYPE);
        int informId = Integer.parseInt(request.getParameter("informId"));
        PrintWriter out = response.getWriter();
        String responseJson = informService.getReadPeople(informId);
        out.print(responseJson);
        out.close();
    }
    @RequestMapping(value = "/getUnReadPeople")
    public void getUnReadPeople(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MyWebMvcConfigurer.CONTENT_TYPE);
        int informId = Integer.parseInt(request.getParameter("informId"));
        PrintWriter out = response.getWriter();
        String responseJson = informService.getUnReadPeople(informId);
        out.print(responseJson);
        out.close();
    }

    @RequestMapping(value = "/getUnReadNum")
    public void getUnReadNum(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MyWebMvcConfigurer.CONTENT_TYPE);
        int informId = Integer.parseInt(request.getParameter("informId"));
        PrintWriter out = response.getWriter();
        String responseJson = informService.getUnReadNum(informId);
        out.print(responseJson);
        out.close();
    }



    @RequestMapping(value = "/getUserInform")
    public void getUserInform(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MyWebMvcConfigurer.CONTENT_TYPE);
        int classId = Integer.parseInt(request.getParameter("classId"));
        int userId=Integer.parseInt(request.getParameter("userId"));
        PrintWriter out = response.getWriter();

        String responseJson = informService.getUserInform(classId,userId);
        out.print(responseJson);
        out.close();
    }
    @RequestMapping(value = "/createInform")
    public void createInform(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType(MyWebMvcConfigurer.CONTENT_TYPE);
        int classId = Integer.parseInt(request.getParameter("classId"));
        String detail=request.getParameter("detail");
        PrintWriter out = response.getWriter();
        String responseJson = informService.createInform(classId,detail);
        out.print(responseJson);
        out.close();
    }
    @RequestMapping(value = "/deleteInform")
    public void deleteInform(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType(MyWebMvcConfigurer.CONTENT_TYPE);
        int informId= Integer.parseInt(request.getParameter("informId"));
        PrintWriter out = response.getWriter();
        String responseJson = informService.deleteInform(informId);
        out.print(responseJson);
        out.close();
    }

    @RequestMapping(value = "/ClickInform")
    public void clickInform(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType(MyWebMvcConfigurer.CONTENT_TYPE);
        int informuserId = Integer.parseInt(request.getParameter("informuserId"));
        int classid = Integer.parseInt(request.getParameter("classId"));
        int userid = Integer.parseInt(request.getParameter("userId"));
        PrintWriter out = response.getWriter();
        String responseJson = informService.ClickInform(informuserId,classid,userid);
        out.print(responseJson);
        out.close();
    }

}
