package com.sporticus;

import com.sporticus.util.logging.LogFactory;
import com.sporticus.util.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;

@SpringBootApplication
@PropertySource("classpath:default-application.properties")
@PropertySource("classpath:overridden.properties")
@PropertySource(ignoreResourceNotFound = false, value = "file:c:\\sporticus.properties")
@PropertySource(ignoreResourceNotFound = true, value="file:/etc/sporticus/db.properties")
@ComponentScan("com.sporticus.*")
public class Application extends SpringBootServletInitializer {

    private static final Logger LOGGER = LogFactory.getLogger(Application.class.getName());

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    @Bean
//    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//        if (false) {
//            return args -> {
//                System.out.println("Let's inspect the beans provided by Spring Boot:");
//                String[] beanNames = ctx.getBeanDefinitionNames();
//                Arrays.sort(beanNames);
//                for (String beanName : beanNames) {
//                    System.out.println(beanName);
//                }
//            };
//        }
//        return args -> {
//        };
//    }

    @PostConstruct
    void started() {
        // TimeZone.setDefault(TimeZone.getTimeZone("CEST"));
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
}

