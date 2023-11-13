package ${base.basePackage}.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Description：项目启动类
 */
@SpringBootApplication
public class ProjectWebStart {

    public static void main(String[] args) {
        SpringApplication.run(ProjectWebStart.class, args);
    }
}
