package team.qdu.smartclassserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import team.qdu.smartclassserver.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class MemberController {
    @Autowired
    MemberService memberService;

    private static final String CONTENTTYPE= "text/plain; charset=utf-8";

    //获取用户班课列表
    @RequestMapping(value = "/getClassMembers")
    public void getClassMembers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(CONTENTTYPE);
        int classId = Integer.parseInt(request.getParameter("classId"));
        PrintWriter out = response.getWriter();
        String responseJson = memberService.getClassMembers(classId);
        out.print(responseJson);
        out.close();
    }
}
