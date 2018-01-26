package team.qdu.smartclassserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import team.qdu.smartclassserver.service.ClassService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class ClassController {

    @Autowired
    ClassService classService;

    @RequestMapping(value = "/getJoinedClasses")
    public void getJoinedClasses(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain; charset=utf-8");
        int userId = Integer.parseInt(request.getParameter("userId"));
    }
}
