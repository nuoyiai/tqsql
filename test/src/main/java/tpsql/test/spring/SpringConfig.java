package tpsql.test.spring;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan(basePackages = {"tpsql.*"})
@ImportResource(locations={"classpath:cfg/spring/*.xml"})
public class SpringConfig {
}
