package com.controller;


import com.pojo.tcpOrder;
import com.service.tcpOrderService;
import com.tcp.NettyServer;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tcpOrder")
public class tcpOrderController {



    @Autowired
    private tcpOrderService tcpOrederService1;


    @RequestMapping("/query")
    public  void save(tcpOrder tcpOrder){
//        Map<String, ChannelHandlerContext> map= NettyServer.map;
//        ChannelHandlerContext no1_1 = map.get(tcpOrder.getAddress());
//        Channel channel = no1_1.channel();
//        channel.write(tcpOrder.getText());
//        channel.flush();
        tcpOrder.setCreateTime(new Date());
        tcpOrder.setDirection("1");
        tcpOrederService1.save(tcpOrder);
    }

    @RequestMapping("/list")
    public List<tcpOrder> exportExcel() {

        List<tcpOrder> tcps = tcpOrederService1.tcpList();
        return tcps;
    }

}
