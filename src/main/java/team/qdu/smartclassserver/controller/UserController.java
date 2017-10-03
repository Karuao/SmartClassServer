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
        String email = request.getParameter("email");
        String password = request.getParameter("password");
//        return userService.login(email, password);
//        return "{\"event\": \"0\", \"msg\": \"success\"}";
//        try {
//            response.getWriter().write("{\"event\": \"0\", \"msg\": \"success\"}");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        PrintWriter out = response.getWriter();
        out.print("{\"event\":\"0\", \"msg\":null, \"obj\":null, \"objList\":null}");
        out.close();
    }
}
