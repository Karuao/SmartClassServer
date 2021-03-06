package team.qdu.smartclassserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ServletComponentScan
@MapperScan("team.qdu.smartclassserver")
public class SmartclassserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartclassserverApplication.class, args);
	}
}
