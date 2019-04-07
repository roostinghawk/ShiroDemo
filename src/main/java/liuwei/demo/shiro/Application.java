package liuwei.demo.shiro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

//		IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:shiro.ini");
//		SecurityManager securityManager = factory.getInstance();
//		SecurityUtils.setSecurityManager(securityManager);
	}

}
