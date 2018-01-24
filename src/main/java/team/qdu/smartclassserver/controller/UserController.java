package team.qdu.smartclassserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import team.qdu.smartclassserver.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain; charset=utf-8");
        String account = request.getParameter("email");
        String password = request.getParameter("password");

        PrintWriter out = response.getWriter();
        String responseJson = userService.login(account, password);
        out.print(responseJson);
        out.close();
    }

    @RequestMapping(value = "/register")
    public void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain; charset=utf-8");
        String account = request.getParameter("email");
        String password = request.getParameter("password");

        PrintWriter out = response.getWriter();
        String responseJson = userService.register(account, password);
        out.print(responseJson);
        out.close();
    }
}
