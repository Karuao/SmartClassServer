package team.qdu.smartclassserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyWebMvcConfigurer extends WebMvcConfigurerAdapter {

    //response中ContentType设置
    public static final String CONTENT_TYPE = "text/plain; charset=utf-8";

    //上传文件写入路径
    public static final String UPLOAD_PATH = System.getProperty("user.dir") + "/";
}
