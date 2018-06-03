package team.qdu.smartclassserver.config;

import com.alibaba.druid.support.http.WebStatFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * CreateDate: 2018/06/03
 *
 * @author Yanxu Dai
 * @version 1.0
 */

@WebFilter(filterName = "druidWebStatFilter", urlPatterns = "/*",
        initParams = {
                @WebInitParam(name = "exclusions", value = "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*") //忽略资源
        })
public class DruidStatFilter extends WebStatFilter {
}
