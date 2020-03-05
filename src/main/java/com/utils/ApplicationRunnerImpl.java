package com.utils;

import com.tcp.NettyServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 1)
public class ApplicationRunnerImpl implements ApplicationRunner {

    private  static  final Logger logger= (Logger) LoggerFactory.getLogger(ApplicationRunnerImpl.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //new run().bin(36956);
        logger.info("项目启动成功");
        NettyServer.bind(12306);
        logger.info("服务启动成功");
        System.out.println( "Hello World!" );
    }
}
