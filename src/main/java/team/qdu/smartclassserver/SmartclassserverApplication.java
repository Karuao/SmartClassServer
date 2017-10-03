package team.qdu.smartclassserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("team.qdu.smartclassserver")
public class SmartclassserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartclassserverApplication.class, args);
	}
}
