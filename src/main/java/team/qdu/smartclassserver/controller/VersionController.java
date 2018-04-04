package team.qdu.smartclassserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import team.qdu.smartclassserver.config.MyWebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author 代延绪
 * 响应客户端获取App版本信息
 */
@Controller
public class VersionController {

    //获取App版本信息
    @RequestMapping("/getVersionInfo")
    public void getVersionInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MyWebMvcConfigurer.CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        String responseJson;
        //从version.json中读取版本信息
        String versionFilePath = MyWebMvcConfigurer.UPLOAD_PATH + "resources/SmartClass/apk/version.json";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(versionFilePath)));
        StringBuilder stringBuilder = new StringBuilder();
        while ((responseJson = bufferedReader.readLine()) != null) {
            stringBuilder.append(responseJson);
        }
        bufferedReader.close();
        responseJson = stringBuilder.toString();

        out.println(responseJson);
    }
}
