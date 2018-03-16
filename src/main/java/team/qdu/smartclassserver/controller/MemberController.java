package team.qdu.smartclassserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import team.qdu.smartclassserver.config.MyWebMvcConfigurer;
import team.qdu.smartclassserver.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class MemberController {
    
    @Autowired
    MemberService memberService;

    //获取用户班课列表
    @RequestMapping(value = "/getClassMembers")
    public void getClassMembers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MyWebMvcConfigurer.CONTENT_TYPE);
        int classId = Integer.parseInt(request.getParameter("classId"));
        PrintWriter out = response.getWriter();
        String responseJson = memberService.getClassMembers(classId);
        out.print(responseJson);
        out.close();
    }

    //获取成员信息
    @RequestMapping(value = "/searchByClassUserId")
    public void getMemberInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MyWebMvcConfigurer.CONTENT_TYPE);
        int classUserId = Integer.parseInt(request.getParameter("classUserId"));
        PrintWriter out = response.getWriter();
        String responseJson = memberService.getMemberInfo(classUserId);
        out.print(responseJson);
        out.close();
    }

    //移出班课
    @RequestMapping(value = "/shiftClass")
    public void shiftClass(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MyWebMvcConfigurer.CONTENT_TYPE);
        int classUserId = Integer.parseInt(request.getParameter("classUserId"));
        PrintWriter out = response.getWriter();
        String responseJson = memberService.shiftClass(classUserId);
        out.print(responseJson);
        out.close();
    }
}
