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
        String account = request.getParameter("account");
        String password = request.getParameter("password");

        PrintWriter out = response.getWriter();
        String responseJson = userService.login(account, password);
        out.print(responseJson);
        out.close();
    }
    @RequestMapping(value="/register")
    public void register(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("text/plain; charset=utf-8");
        String account=request.getParameter("account");
        String password=request.getParameter("password");
        String answer=request.getParameter("answer");
        String question=request.getParameter("question");
        PrintWriter out = response.getWriter();
        String responseJson=userService.register(account,password,question,answer);
        out.print(responseJson);
        out.close();
    }

    @RequestMapping(value = "/findPassOne")
    public void checkAccount(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain; charset=utf-8");
        String account = request.getParameter("account");

        PrintWriter out = response.getWriter();
        String responseJson = userService.checkAccount(account);
        out.print(responseJson);
        out.close();
    }

    @RequestMapping(value = "/searchById")
    public void getUserInforById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain; charset=utf-8");
        int userId=Integer.parseInt(request.getParameter("userId"));
        PrintWriter out = response.getWriter();
        String responseJson = userService.getUserInforById(userId);
        out.print(responseJson);
        out.close();
    }

    @RequestMapping(value = "/updatePass")
    public void updatePassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain; charset=utf-8");
        String account = request.getParameter("account");
        String newPass = request.getParameter("newPass");
        PrintWriter out = response.getWriter();
        String responseJson=userService.updatePassword(account,newPass);
        out.print(responseJson);
        out.close();
    }

    @RequestMapping(value = "/updateUserInformation")
    public void updateUserInformation(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain; charset=utf-8");
        String account = request.getParameter("account");
        String name = request.getParameter("name");
        String gender = request.getParameter("gender");
        String university = request.getParameter("university");
        String department = request.getParameter("department");
        String motto = request.getParameter("motto");
        PrintWriter out = response.getWriter();
        String responseJson=userService.updateUserInformation(account,name,gender,university,department,motto);
        out.print(responseJson);
        out.close();
    }

}
