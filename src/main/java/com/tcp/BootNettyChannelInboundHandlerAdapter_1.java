package com.tcp;


import com.controller.tcpController;
import com.dao.tcpDao;
import com.pojo.tcp;
import com.pojo.tcpOrder;
import com.service.tcpOrderService;
import com.service.tcpService;
import com.utils.DataContext;
import com.utils.DateUtils;
import com.utils.SpringUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * I/O数据读写处理类
 */
public class BootNettyChannelInboundHandlerAdapter extends ChannelInboundHandlerAdapter {


   private ApplicationContext applicationContext= SpringUtils.getApplicationContext();
    private tcpService tcpService1=applicationContext.getBean(tcpService.class);
   private tcpOrderService tcpOrderService=applicationContext.getBean(tcpOrderService.class);
    /*private  static  final Logger logger = (Logger) LoggerFactory.getLogger(BootNettyChannelInboundHandlerAdapter.class);*/



    /**
     * 从客户端收到新的数据时，这个方法会在收到消息时被调用
     *
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception, IOException {

        tcpOrder tcpOrder = new tcpOrder();
        tcpOrder.setCreateTime(new Date());
        tcpOrder.setText(msg+"");
        tcpOrder.setDirection("0");
        for( String key :NettyServer.map.keySet()){
            if( NettyServer.map.get(key)!=null &&  NettyServer.map.get(key).equals( ctx)){
                tcpOrder.setAddress(key);
            }
        }
        tcpOrderService.save(tcpOrder);

        String date2 = msg.toString().replace("my name is", "");
        String date1 = date2.replaceAll("\\s", "");
        String date = date1.substring(0, 5);
        if (!date.contains("ACK")) {
            if (NettyServer.map.get(date) != null && NettyServer.map.get(date).equals(ctx)) {
                //接收到服务端发来的数据进行业务处理
                System.out.println(("接收数据成功！" +msg+ DateUtils.convert(new Date())));

            } else {
                //接收到服务端发来的数据进行业务处//如果map中没有此ctx 将连接存入map中
                NettyServer.map.put(date, ctx);
                System.out.println("连接成功，加入map管理连接！" + "mn:" + date + DateUtils.convert(new Date()));
                /*logger.info("连接成功，加入map管理连接！"+"mn:" +date+" " +""+ DateUtils.convert(new Date()));*/
                InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
                int port = insocket.getPort();
                String clientIp = insocket.getAddress().getHostAddress();
                boolean savebyip = tcpService1.savebyip(clientIp, date, port + "");
                if (!savebyip){

                tcp tcp = new tcp();
                tcp.setIp(clientIp);
                tcp.setDuankou(port + "");
                tcp.setIslongin("1");
                tcp.setIslongin("1");
                tcp.setAddress(date);
                tcpService1.save(tcp);
                }
            }
        } else {
            String ack = date.replace("ACK", "");
            System.out.println(ack + "操作成功");
            /*logger.info(""+"不是监测数据"+ msg.toString()+" "+ DateUtils.convert(new Date()));*/
        }
    }

    /**
     * 从客户端收到新的数据、读取完成时调用
     *
     * @param ctx
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws IOException {
        System.out.println("channelReadComplete");
        ctx.flush();
    }

    /**
     * 当出现 Throwable 对象才会被调用，即当 Netty 由于 IO 错误或者处理器在处理事件时抛出的异常时
     *
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws IOException {
        System.out.println("exceptionCaught");
        cause.printStackTrace();
        ctx.close();//抛出异常，断开与客户端的连接
    }

    /**
     * 客户端与服务端第一次建立连接时 执行
     *
     * @param ctx
     * @throws Exception
     */


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception, IOException {


    }

    /**
     * 客户端与服务端 断连时 执行
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception, IOException {
        super.channelInactive(ctx);
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = insocket.getAddress().getHostAddress();
        ctx.close(); //断开连接时，必须关闭，否则造成资源浪费，并发量很大情况下可能造成宕机
        System.out.println("客户端断开连接:" + clientIp);
        for( String key :NettyServer.map.keySet()){
            if( NettyServer.map.get(key)!=null &&  NettyServer.map.get(key).equals( ctx)){
                NettyServer.map.remove(key);
            }
        }
        int port = insocket.getPort();
        tcpService1.close(clientIp,port+"");
    }

    /**
     * 服务端当read超时, 会调用这个方法
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception, IOException {
        super.userEventTriggered(ctx, evt);
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = insocket.getAddress().getHostAddress();
        ctx.close();//超时时断开连接
        System.out.println("userEventTriggered:" + clientIp);
    }


}
