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

    //教师获取签到历史记录
    @RequestMapping(value = "/getTeacherSignInHistory")
    public void getTeacherSignInHistory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MyWebMvcConfigurer.CONTENT_TYPE);
        int classId = Integer.parseInt(request.getParameter("classId"));
        PrintWriter out = response.getWriter();
        String responseJson = memberService.getTeacherSignInHistory(classId);
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

    //教师端开始签到操作
    @RequestMapping(value = "/beginSignInForTeacher")
    public void beginSignInForTeacher(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MyWebMvcConfigurer.CONTENT_TYPE);
        int classId = Integer.parseInt(request.getParameter("classId"));
        PrintWriter out = response.getWriter();
        String responseJson = memberService.beginSignInForTeacher(classId);
        out.print(responseJson);
        out.close();
    }

    //教师放弃本次签到
    @RequestMapping(value = "/giveUpSignIn")
    public void giveUpSignIn(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MyWebMvcConfigurer.CONTENT_TYPE);
        int attendanceId = Integer.parseInt(request.getParameter("attendanceId"));
        PrintWriter out = response.getWriter();
        String responseJson = memberService.giveUpSignIn(attendanceId);
        out.print(responseJson);
        out.close();
    }

    //教师结束本次签到
    @RequestMapping(value = "/endSignIn")
    public void endSignIn(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MyWebMvcConfigurer.CONTENT_TYPE);
        int attendanceId = Integer.parseInt(request.getParameter("attendanceId"));
        PrintWriter out = response.getWriter();
        String responseJson = memberService.endSignIn(attendanceId);
        out.print(responseJson);
        out.close();
    }
}
