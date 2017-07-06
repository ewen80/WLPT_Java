package pw.ewen.WLPT;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by wenliang on 17-1-24.
 */
@SpringBootApplication(scanBasePackages="pw.ewen.WLPT")
public class Application {
    public static void main(String[] args) throws ClassNotFoundException {
        SpringApplication.run(Application.class, args);
    }
}
