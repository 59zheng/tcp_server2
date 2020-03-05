package com;

import com.tcp.NettyServer;
import com.utils.DataContext;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@MapperScan("com.dao")
@ComponentScan("com")
public class Application {



    private  static  final Logger logger= (Logger) LoggerFactory.getLogger(Application.class);

    public static void main(String[] args)throws Exception {

       SpringApplication.run(Application.class, args);
        //new run().bin(36956);
      /*  logger.info("项目启动成功");
        NettyServer.bind(12306);
        logger.info("服务启动成功");
        System.out.println( "Hello World!" );*/


   }

//    @Bean
//    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//        return args -> {
//
//            System.out.println("Let's inspect the beans provided by Spring Boot:");
//
//            String[] beanNames = ctx.getBeanDefinitionNames();
//            Arrays.sort(beanNames);
//            for (String beanName : beanNames) {
//                System.out.println(beanName);
//            }
//
//        };
//    }

}

