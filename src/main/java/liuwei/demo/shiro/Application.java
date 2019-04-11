package liuwei.demo.shiro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "liuwei.demo.shiro.dao")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

//		IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:shiro.ini");
//		SecurityManager securityManager = factory.getInstance();
//		SecurityUtils.setSecurityManager(securityManager);
	}

}
