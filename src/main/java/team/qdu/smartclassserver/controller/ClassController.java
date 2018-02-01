package team.qdu.smartclassserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import team.qdu.smartclassserver.service.ClassService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@Controller
public class ClassController {

    @Autowired
    ClassService classService;

    @RequestMapping(value = "/getJoinedClasses")
    public void getJoinedClasses(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain; charset=utf-8");
        int userId = Integer.parseInt(request.getParameter("userId"));
        PrintWriter out = response.getWriter();
        String responseJson = classService.getJoinedClasses(userId);
        out.print(responseJson);
        out.close();
    }

    @RequestMapping(value = "/jumpClass")
    public void jumpClass(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain; charset=utf-8");
        int classId = Integer.parseInt(request.getParameter("classId"));
        int userId = Integer.parseInt(request.getParameter("userId"));
        PrintWriter out = response.getWriter();
        String responseJson = classService.judgeTitle(classId, userId);
        out.print(responseJson);
        out.close();
    }

    @RequestMapping(value = "/addClass")
    public String addClass(HttpServletRequest request, HttpServletResponse response) throws IOException {
        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        String name = params.getParameter("name");
        System.out.println("name:" + name);
        String id = params.getParameter("id");
        System.out.println("id:" + id);
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    stream = new BufferedOutputStream(new FileOutputStream(
                            new File(file.getOriginalFilename())));
                    stream.write(bytes);
                    stream.close();
                } catch (Exception e) {
                    stream = null;
                    return "You failed to upload " + i + " => " + e.getMessage();
                }
            } else {
                return "You failed to upload " + i + " because the file was empty.";
            }
        }
        return "upload successful";
    }
}
